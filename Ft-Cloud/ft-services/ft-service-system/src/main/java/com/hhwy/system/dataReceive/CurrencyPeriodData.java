package com.hhwy.system.dataReceive;

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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 功能描述: 汇率批次
 */
@RestController
public class CurrencyPeriodData {

    @Autowired
    private IPeriodInfoService periodInfoService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ICurrencyInfoService currencyInfoService;
    @Autowired
    private IPeriodCurrencyService periodCurrencyService;

    private String caiwuyun_url = "http://esb.cfhec.net/env-101/por-1002/esb/haiwai_ju_caiwuyun/caiwuyun_url";
    private String caiwuyun_apiKey = "duZXF5cW654rhAOeSJrfVSXrePw4d5gl";

    @GetMapping("pullPeriodCurrency")
    public AjaxResult pullPeriodCurrency() {
        String year = String.valueOf(DateUtil.thisYear());
        int pageNum = 1;
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("apikey", caiwuyun_apiKey);

        Map<String, Map<String, Object>> qicihuilv = new HashMap<>();
        boolean flag = true;
        while (flag){
            System.out.println("开始执行------------------------------");
            String res = HttpRequestUtils.post(caiwuyun_url, headerMap, getReqParams(year, pageNum));
            // 结果解析
            JSONObject jsonObject = JSONObject.parseObject(res, JSONObject.class);
            if("success".equals(jsonObject.get("msg"))){
                int TotalPage = (int) jsonObject.get("TotalPage");
                int PageSize = (int) jsonObject.get("PageSize");
                int TotalNumber = (int) jsonObject.get("TotalNumber");

                List<JSONObject> data = (List<JSONObject>) jsonObject.get("data");
                //"RATEVALUETYPE": "1"  区间汇率
                if(CollectionUtils.isEmpty(data)){
                    return null;
                }
                BigDecimal zero = new BigDecimal(0);
                for(JSONObject temp : data){
                    String RATEVALUETYPE = (String) temp.get("RATEVALUETYPE");//1期间汇率类型
                    String DIRECTORINDIRECT = (String) temp.get("DIRECTORINDIRECT");//0:直接汇率 1：间接汇率
                    String ZSBBH = (String) temp.get("ZSBBH");//折算币
                    BigDecimal exchangeratefin = new BigDecimal(String.valueOf(temp.get("exchangeratefin")));//期末汇率
                    BigDecimal EXCHANGERATEVALUE = new BigDecimal(String.valueOf(temp.get("EXCHANGERATEVALUE")));//区间汇率
                    String BEGINDATE = year + temp.get("BEGINDATE");//开始日期
                    String enddate = year + temp.get("enddate");//结束日期
//                    String no = year + temp.get("PERIODNO");//期次编码

                    String YBBH = (String) temp.get("YBBH");//原币

                    String FIYEAR = (String) temp.get("FIYEAR");//年份
                    String no = FIYEAR + temp.get("PERIODNO");//期次编码
                    no = no.replace("df-", "");

//                    if("1".equals(RATEVALUETYPE) && "USD".equals(ZSBBH) && zero.compareTo(EXCHANGERATEVALUE) == -1){
                    if("1".equals(RATEVALUETYPE) && zero.compareTo(EXCHANGERATEVALUE) == -1){
                        if("USD".equals(ZSBBH)){
                            //折算币是美元的
                            Map<String, Object> tt = qicihuilv.get(no);
                            if(tt == null){
                                tt = new HashMap<>();
                            }
                            tt.put("no", no);
                            tt.put("startDate", BEGINDATE);
                            tt.put("endDate", enddate);
                            List list = (List) tt.get("data");
                            if(CollectionUtils.isEmpty(list)){
                                list = new ArrayList();
                            }
                            Map<String, Object> huilv = new HashMap<>();
                            huilv.put("bizhong", YBBH);
                            if("0".equals(DIRECTORINDIRECT)){
                                //直接汇率 1个当地币换算美元的多少 所以系统内需要换算
                                BigDecimal qjhl = new BigDecimal(1.00).divide(EXCHANGERATEVALUE, 6, BigDecimal.ROUND_HALF_UP);
                                huilv.put("qujianhuilv", qjhl);
                            }else{
                                huilv.put("qujianhuilv", EXCHANGERATEVALUE);
                                huilv.put("qimohuilv", exchangeratefin);
                            }
                            list.add(huilv);
                            tt.put("data", list);
                            qicihuilv.put(no, tt);
                            //先临时插入redis缓存中
//                            redisUtils.hPut("qicihuilv", no, JSONObject.toJSONString(tt));
                            System.out.println("放一次！！！！！");
                        }else if("USD".equals(YBBH)){
                            //原币是美元的
                            Map<String, Object> tt = qicihuilv.get(no);
                            if(tt == null){
                                tt = new HashMap<>();
                            }
                            tt.put("no", no);
                            tt.put("startDate", BEGINDATE);
                            tt.put("endDate", enddate);
                            List list = (List) tt.get("data");
                            if(CollectionUtils.isEmpty(list)){
                                list = new ArrayList();
                            }
                            Map<String, Object> huilv = new HashMap<>();
                            huilv.put("bizhong", ZSBBH);
                            if("0".equals(DIRECTORINDIRECT)){
                                //直接汇率 1个当地币换算美元的多少 所以系统内需要换算
                                huilv.put("qujianhuilv", EXCHANGERATEVALUE);
                                huilv.put("qimohuilv", exchangeratefin);
                            }else{
                                BigDecimal qjhl = new BigDecimal(1.00).divide(EXCHANGERATEVALUE, 6, BigDecimal.ROUND_HALF_UP);
                                huilv.put("qujianhuilv", qjhl);
                            }
                            list.add(huilv);
                            tt.put("data", list);
                            qicihuilv.put(no, tt);
                            //先临时插入redis缓存中
//                            redisUtils.hPut("qicihuilv", no, JSONObject.toJSONString(tt));
                            System.out.println("放一次！！！！！");

                        }
                    }
                }
                if(pageNum >= TotalPage){
                    flag = false;
                }
                pageNum ++;
                System.out.println("+1，+1"+pageNum);
            }
        }
        System.out.println("执行结束------------------------------"+pageNum);
        System.out.println("结果数据--->"+JSONObject.toJSONString(qicihuilv));
        handleData(qicihuilv);
        return AjaxResult.success();
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
        List<PeriodInfo> periodInfoList=new ArrayList<>();
        //汇率
        List<PeriodCurrency> periodCurrencyList=new ArrayList<>();
        List<CurrencyInfo> currencyInfoList = currencyInfoService.selectCurrencyInfoList(new CurrencyInfo());
        for (int j = month; j <= 12; j++) {
            String str=String.format("%02d",j);
            String key=year+""+str;
            Map<String, Object> map = qicihuilv.get(key);
            if(null==map){
                continue;
            }
            String no= map.get("no")+"";
            String endDate= map.get("endDate")+"";
            String startDate= map.get("startDate")+"";
            PeriodInfo periodInfo=new PeriodInfo();
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
                    BigDecimal qujianhuilv = (BigDecimal) dataMap.get("qujianhuilv");
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


    public static void main(String[] args) throws ParseException {
//        String str="20230331";
//        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
//        String format = sf.format(str);
//        Date date = sf.parse(format);
//        System.out.println("111");

        String str=String.format("%02d",78);
        System.out.println(str);
    }

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
    private Map<String, Object> getReqParams(String year, int pageNum){
        Map<String, Object> req = new HashMap<>();
        req.put("appInstanceCode", "10000");
        req.put("unitCode", "MDM");
        req.put("sourceSystem", "FHEB-SDJPM");
        req.put("dicCode", "MDM26");
        Map<String, Object> whereCondition = new HashMap<>();
        whereCondition.put("year", year);
        whereCondition.put("starttime", "2023-08-15T10:30:00.262+08:00");
        whereCondition.put("endtime", "2024-06-15T10:30:00.262+08:00");
        whereCondition.put("PageNum", pageNum);
        whereCondition.put("IFPUB", "2");
        whereCondition.put("ORGID", "101140128");
        whereCondition.put("fieldKey", "");
        whereCondition.put("fieldValue", "");
        req.put("whereCondition", whereCondition);
        return req;
    }
}
