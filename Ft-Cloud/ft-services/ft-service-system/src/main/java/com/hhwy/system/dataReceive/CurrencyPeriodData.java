package com.hhwy.system.dataReceive;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.currency.service.ICurrencyInfoService;
import com.hhwy.system.period.service.IPeriodInfoService;
import com.hhwy.system.periodCurrency.service.IPeriodCurrencyService;
import com.hhwy.utils.http.HttpRequestUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 功能描述: 汇率批次
 */
@RestController
@Slf4j
public class CurrencyPeriodData {

    @Autowired
    private IPeriodInfoService periodInfoService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ICurrencyInfoService currencyInfoService;
    @Autowired
    private IPeriodCurrencyService periodCurrencyService;

    private static String caiwuyun_url = "http://esb.cfhec.net/env-101/por-1002/esb/haiwai_ju_caiwuyun/caiwuyun_url";
    private static String caiwuyun_apiKey = "duZXF5cW654rhAOeSJrfVSXrePw4d5gl";

    /***
     * 功能描述: 拉取期次汇率，每月底拉取一次,拉取下个月的数据
     * 作者: fushudong
     * 时间: 2023/12/21
     */
    @GetMapping("pullPeriodCurrency")
    public AjaxResult pullPeriodCurrency() {

        log.info("---------------------------------------------------------------");
        //请求参数
        String year = String.valueOf(DateUtil.thisYear());
        Date date = new Date();
        DateTime startDate = DateUtil.offsetDay(date,-10);
        DateTime endDate = DateUtil.offsetDay(date,19);
        String beginOfMonthStr = DateUtil.format(startDate, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN);
        String endOfMonthStr = DateUtil.format(endDate, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN);
        System.out.println(beginOfMonthStr);
        System.out.println(endOfMonthStr);
        Map<String, String> header = new HashMap<>();
        header.put("apikey", caiwuyun_apiKey);
        //数据拼装结果
        Map<String, Map<String, Object>> qicihuilv = new HashMap<>();
        //分页循环获取
        int pageNum = 1;
        boolean flag = true;
        while (flag){
            log.info("拉取期次、汇率开始， 页码：{}", pageNum);
            //发送请求
            String resp = HttpRequestUtils.post(caiwuyun_url, header, getReqParams(year, pageNum, beginOfMonthStr, endOfMonthStr));
            // 结果解析
            JSONObject jsonObject = JSONObject.parseObject(resp, JSONObject.class);
            if(!"success".equals(jsonObject.get("msg"))){
                log.error("拉取期次、汇率请求失败， 响应msg：{}", jsonObject.get("msg"));
                return  AjaxResult.error();
            }
            int totalPage = (int) jsonObject.get("TotalPage");
            List<JSONObject> data = (List<JSONObject>) jsonObject.get("data");
            if(CollectionUtils.isEmpty(data)){
                log.error("拉取期次、汇率数据为空， 响应msg：{}", jsonObject.get("msg"));
                return  AjaxResult.error();
            }
            BigDecimal zero = new BigDecimal(0);
            for(JSONObject temp : data){
                //"RATEVALUETYPE": "1"  区间汇率
                String RATEVALUETYPE = (String) temp.get("RATEVALUETYPE");//1期间汇率类型
                String DIRECTORINDIRECT = (String) temp.get("DIRECTORINDIRECT");//0:直接汇率 1：间接汇率
                String ZSBBH = (String) temp.get("ZSBBH");//折算币
                BigDecimal exchangeratefin = new BigDecimal(String.valueOf(temp.get("exchangeratefin")));//期末汇率
                BigDecimal EXCHANGERATEVALUE = new BigDecimal(String.valueOf(temp.get("EXCHANGERATEVALUE")));//区间汇率
                String BEGINDATE = year + temp.get("BEGINDATE");//开始日期
                String enddate = year + temp.get("enddate");//结束日期
                String YBBH = (String) temp.get("YBBH");//原币
                String FIYEAR = (String) temp.get("FIYEAR");//年份
                String no = FIYEAR + temp.get("PERIODNO");//期次编码
                no = no.replace("df-", "");

                if(!"1".equals(RATEVALUETYPE) || zero.compareTo(EXCHANGERATEVALUE) > -1){
//                    log.info("跳过，期间汇率类型不等于1或者区间汇率小于等于0；{}---{}", RATEVALUETYPE, EXCHANGERATEVALUE);
                    continue;
                }
                if("USD".equals(ZSBBH)){
                    //折算币是美元的
                    Map<String, Object> tt = qicihuilv.get(no);
                    if(tt == null){
                        tt = new HashMap<>();
                    }
                    tt.put("no", no);
                    tt.put("startDate", BEGINDATE);
                    tt.put("endDate", enddate);
                    Set list = (HashSet) tt.get("data");
                    if(CollectionUtils.isEmpty(list)){
                        list = new HashSet();
                    }
                    Map<String, Object> huilv = new HashMap<>();
                    huilv.put("bizhong", YBBH);
                    if("0".equals(DIRECTORINDIRECT)){
                        //直接汇率 1个当地币换算美元的多少 所以系统内需要换算
                        BigDecimal qjhl = new BigDecimal(1.00).divide(EXCHANGERATEVALUE, 6, BigDecimal.ROUND_HALF_UP);
                        huilv.put("qujianhuilv", qjhl);
                    }else{
                        huilv.put("qujianhuilv", EXCHANGERATEVALUE);
//                        huilv.put("qimohuilv", exchangeratefin);
                    }
                    list.add(huilv);
                    tt.put("data", list);
                    qicihuilv.put(no, tt);
                    //先临时插入redis缓存中
//                            redisUtils.hPut("qicihuilv", no, JSONObject.toJSONString(tt));
                }else if("USD".equals(YBBH)){
                    //原币是美元的
                    Map<String, Object> tt = qicihuilv.get(no);
                    if(tt == null){
                        tt = new HashMap<>();
                    }
                    tt.put("no", no);
                    tt.put("startDate", BEGINDATE);
                    tt.put("endDate", enddate);
                    Set list = (HashSet) tt.get("data");
                    if(CollectionUtils.isEmpty(list)){
                        list = new HashSet();
                    }
                    Map<String, Object> huilv = new HashMap<>();
                    huilv.put("bizhong", ZSBBH);
                    if("0".equals(DIRECTORINDIRECT)){
                        //直接汇率 1个当地币换算美元的多少 所以系统内需要换算
                        huilv.put("qujianhuilv", EXCHANGERATEVALUE);
//                        huilv.put("qimohuilv", exchangeratefin);
                    }else{
                        BigDecimal qjhl = new BigDecimal(1.00).divide(EXCHANGERATEVALUE, 6, BigDecimal.ROUND_HALF_UP);
                        huilv.put("qujianhuilv", qjhl);
                    }
                    list.add(huilv);
                    tt.put("data", list);
                    qicihuilv.put(no, tt);
                    //先临时插入redis缓存中
//                            redisUtils.hPut("qicihuilv", no, JSONObject.toJSONString(tt));
                }
            }
            if(pageNum >= totalPage){
                flag = false;
            }
            pageNum ++;
        }
        log.info("拉取期次、汇率执行结束, 分页数：{}", pageNum);
        log.info("结果数据--->"+JSONObject.toJSONString(qicihuilv));
        //期次汇率入库
        return handleData(qicihuilv);
    }



    /**
     * 1、批量插入汇率、期次、 币种信息从redis取数据
     * 当月及当月以后得数据 删除后再新增   当月之前的数据 不做处理 直接丢弃
     *
     * @param qicihuilv
     * @return
     */
    public AjaxResult handleData(Map<String, Map<String, Object>> qicihuilv){
        if(null==qicihuilv){
            return AjaxResult.error("数据处理异常");
        }
        //当前年 当前月
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        //期次
        List<PeriodInfo> periodInfoList = new ArrayList<>();
        //汇率
        List<PeriodCurrency> periodCurrencyList = new ArrayList<>();
        List<CurrencyInfo> currencyInfoList = currencyInfoService.selectCurrencyInfoList(new CurrencyInfo());
        for (int j = month; j <= 12; j++) {
            String str = String.format("%02d",j);
            String key = year +""+ str;
            Map<String, Object> map = qicihuilv.get(key);
            if(null == map){
                continue;
            }
            String no = map.get("no")+"";
            String endDate = map.get("endDate")+"";
            String startDate = map.get("startDate")+"";
            PeriodInfo periodInfo = new PeriodInfo();
            Long periodId = IdWorker.createId();
            periodInfo.setId(periodId);
            try {
                if(StringUtils.isNotBlank(startDate)){
                    periodInfo.setBeginDate(strParseData(startDate));
                }
                if(StringUtils.isNotBlank(endDate)){
                    periodInfo.setEndDate(strParseData(endDate));
                }
            }catch (Exception e){
                return AjaxResult.error("日期转换异常");
            }
            periodInfo.setPeriodCode(no);
            periodInfo.setCreateTime(DateUtils.getNowDate());
            //--------------处理汇率-------------
            if(null==map.get("data")){continue;};

            JSONArray data = JSONArray.parseArray(JSONObject.toJSONString(map.get("data")));
            for (int i = 0; i < data.size(); i++) {
                PeriodCurrency periodCurrency=new PeriodCurrency();
                periodCurrency.setId(IdWorker.createId());
                periodCurrency.setCreateTime(DateUtils.getNowDate());
                periodCurrency.setPeriodId(String.valueOf(periodId));
                periodCurrency.setPeriodCode(no);
                Map dataMap = (Map)data.get(i);
                if(null!=dataMap.get("bizhong")){//币种
                    String bizhong = (String) dataMap.get("bizhong");
                    List<CurrencyInfo> list = currencyInfoList.stream().filter(e -> e.getCurrencyCode().equals(bizhong)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(list)){
                        periodCurrency.setCurrencyCode(list.get(0).getCurrencyCode());
                        periodCurrency.setCurrencyId(String.valueOf(list.get(0).getId()));

                    }
                }
                if(null!=dataMap.get("qujianhuilv")){//期末汇率
                    BigDecimal qujianhuilv = new BigDecimal(String.valueOf(dataMap.get("qujianhuilv")));
                    if(null==qujianhuilv){
                        continue;
                    }
                    periodCurrency.setRate(qujianhuilv);
                }
                periodCurrencyList.add(periodCurrency);
            }
            periodInfoList.add(periodInfo);
        }
        //批量插入期次
        if(CollectionUtils.isNotEmpty(periodInfoList)){
            periodInfoService.dataSync(periodInfoList);
        }
        //批量插入
        if(CollectionUtils.isNotEmpty(periodCurrencyList)){
            periodCurrencyService.dataSync(periodCurrencyList);
        }
        return AjaxResult.success();
    }


//    public static void main(String[] args) throws ParseException {
////        String str="20230331";
////        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
////        String format = sf.format(str);
////        Date date = sf.parse(format);
////        System.out.println("111");
//
//        String str=String.format("%02d",78);
//        System.out.println(str);
//    }

    /**
     * yyyyMMdd转yyyy-MM-dd
     *
     * @param str
     * @return
     * @throws Exception
     */
    private Date strParseData(String str) throws Exception{
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
        Date parse = sf.parse(str);
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        String format = sd.format(parse);
        Date parse1 = sd.parse(format);
        return parse1;
    }

    /**
     * 请求汇率接口传参处理
     *
     * @param year
     * @param pageNum
     * @return
     */
    private static Map<String, Object> getReqParams(String year, int pageNum, String starttime, String endtime){
        Map<String, Object> req = new HashMap<>();
        req.put("appInstanceCode", "10000");
        req.put("unitCode", "MDM");
        req.put("sourceSystem", "FHEB-SDJPM");
        req.put("dicCode", "MDM26");
        Map<String, Object> whereCondition = new HashMap<>();
        whereCondition.put("year", year);
        whereCondition.put("starttime", starttime);
        whereCondition.put("endtime", endtime);
        whereCondition.put("PageNum", pageNum);
        whereCondition.put("IFPUB", "2");
        whereCondition.put("ORGID", "101140128");
        whereCondition.put("fieldKey", "");
        whereCondition.put("fieldValue", "");
        req.put("whereCondition", whereCondition);
        return req;
    }

    public static void main(String[] args) {
        String year = String.valueOf(DateUtil.thisYear());
        Date date = new Date();
        DateTime endDate = DateUtil.offsetDay(date, 29);
        String beginOfMonthStr = DateUtil.format(date, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN);
//        String beginOfMonthStr = "2023-12-01T10:30:00.262+08:00";
//        String endOfMonthStr = "2023-12-20T10:30:00.262+08:00";
        String endOfMonthStr = DateUtil.format(endDate, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN);
        System.out.println(beginOfMonthStr);
        System.out.println(endOfMonthStr);
        Map<String, String> header = new HashMap<>();
        header.put("apikey", caiwuyun_apiKey);
        //发送请求
        String resp = HttpRequestUtils.post(caiwuyun_url, header, getReqParams(year, 1, beginOfMonthStr, endOfMonthStr));
        // 结果解析
        JSONObject jsonObject = JSONObject.parseObject(resp, JSONObject.class);
        if(!"success".equals(jsonObject.get("msg"))){
            log.error("拉取期次、汇率请求失败， 响应msg：{}", jsonObject.get("msg"));
        }
        int totalPage = (int) jsonObject.get("TotalPage");
        List<JSONObject> data = (List<JSONObject>) jsonObject.get("data");
        data.forEach(System.out::println);
    }
}
