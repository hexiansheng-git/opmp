package com.hhwy.pm.qqch.wzch.approach.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.utils.EasyExeclUtil;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachDetail;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachYearCount;
import com.hhwy.pm.qqch.wzch.approach.mapper.WzchPriorApproachDetailMapper;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachDetailService;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachService;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachYearCountService;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachDetailExportRequest;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachDetailResponse;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandTimeCountMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优先进场物资详情Service业务层处理
 * 
 * @author mls
 * @date 2022-11-30
 */
@Service
public class WzchPriorApproachDetailServiceImpl implements IWzchPriorApproachDetailService {
    @Resource
    private WzchPriorApproachDetailMapper wzchPriorApproachDetailMapper;
    @Resource
    private IWzchPriorApproachService wzchPriorApproachService;
    @Autowired
    private GenCodeService genCodeService;
    @Resource
    private IWzchPriorApproachYearCountService wzchPriorApproachYearCountService;
    @Resource
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;
    @Resource
    private WzchTotalDemandTimeCountMapper wzchTotalDemandTimeCountMapper;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private SystemApiService dictTypeService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 查询优先进场物资详情
     * 
     * @param id 优先进场物资详情ID
     * @return 优先进场物资详情
     */
    @Override
    public WzchPriorApproachDetail selectWzchPriorApproachDetailById(Long id) {
        return wzchPriorApproachDetailMapper.selectWzchPriorApproachDetailById(id);
    }

    /**
     * 查询优先进场物资详情列表
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 优先进场物资详情
     */
    @Override
    public List<WzchPriorApproachDetail> selectWzchPriorApproachDetailList(WzchPriorApproachDetail wzchPriorApproachDetail) {
        return wzchPriorApproachDetailMapper.selectWzchPriorApproachDetailList(wzchPriorApproachDetail);
    }

    /**
     * 新增优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    @Override
    public int insertWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail) {

    wzchPriorApproachDetail.setId(IdWorker.createId());

        wzchPriorApproachDetail.setCreateTime(DateUtils.getNowDate());

        return wzchPriorApproachDetailMapper.insertWzchPriorApproachDetail(wzchPriorApproachDetail);
    }

    @Override
    @Transactional
    public int batchInsert(List<WzchPriorApproachDetail> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return wzchPriorApproachDetailMapper.batchInsert(list);
    }

    /**
     * 修改优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    @Override
    public int updateWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail) {
        wzchPriorApproachDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchPriorApproachDetailMapper.updateWzchPriorApproachDetail(wzchPriorApproachDetail);
    }

    /**
     * 删除优先进场物资详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachDetailByIds(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException("入参缺失");
        }
        return wzchPriorApproachDetailMapper.deleteWzchPriorApproachDetailByIds(ids);
    }

    /**
     * 删除优先进场物资详情信息
     * 
     * @param id 优先进场物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachDetailById(Long id) {
        return wzchPriorApproachDetailMapper.deleteWzchPriorApproachDetailById(id);
    }

    @Override
    @Transactional
    public void save(WzchPriorApproach wzchPriorApproach) {
        if(wzchPriorApproach==null || wzchPriorApproach.getVersion() == null){
            throw new BaseException("入参缺失");
        }
        fillWzchPriorApproach(wzchPriorApproach);
        fillWzchPriorApproachDetail(wzchPriorApproach);
        if (wzchPriorApproach.getDeptId() == null) {
            wzchPriorApproach.setDeptId(SecurityUtils.getSysUser().getDeptId());
        }
        //处理主表
        if(wzchPriorApproach.getId() == null){
            wzchPriorApproach.setId(IdWorker.createId());
            wzchPriorApproach.setTitle(wzchPriorApproach.getVersion()+"");
            wzchPriorApproach.setPrjCode(SecurityUtils.getTenantKey());
            wzchPriorApproach.setDeptId(SecurityUtils.getSysUser().getDeptId());
            wzchPriorApproach.setValid("0");
            new AddBaseInfoUtil().addBaseEntity(wzchPriorApproach);
            this.wzchPriorApproachService.insertWzchPriorApproach(wzchPriorApproach);
        }else{
            new AddBaseInfoUtil().updateBaseEntity(wzchPriorApproach);
            this.wzchPriorApproachService.updateWzchPriorApproach(wzchPriorApproach);
        }
//        WzchPriorApproach approach = wzchPriorApproachService.selectWzchPriorApproachById(wzchPriorApproach.getId());
        List<WzchPriorApproachYearCount> wzchPriorApproachYearCounts = new ArrayList<>();
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproach.getWzchPriorApproachDetailList()) {
            wzchPriorApproachDetail.setPriorApproachId(wzchPriorApproach.getId());
            wzchPriorApproachDetail.setVersion(wzchPriorApproach.getVersion());
            wzchPriorApproachDetail.setId(IdWorker.createId());
            //设置version
            List<WzchPriorApproachYearCount> list = wzchPriorApproachDetail.getWzchPriorApproachYearCountList();
            for (int i = 0; i < list.size(); i++){
                list.get(i).setVersion(wzchPriorApproach.getVersion());
                list.get(i).setId(IdWorker.createId());
            }
            wzchPriorApproachYearCounts.addAll(wzchPriorApproachDetail.getWzchPriorApproachYearCountList());
        }
//        List<Long> detialIds = wzchPriorApproach.getWzchPriorApproachDetailList().stream().map(WzchPriorApproachDetail::getId).collect(Collectors.toList());
        wzchPriorApproachDetailMapper.deleteDirectByVersion(wzchPriorApproach.getVersion());
//        List<Long> countIds = wzchPriorApproachYearCounts.stream().map(WzchPriorApproachYearCount::getId).collect(Collectors.toList());
        wzchPriorApproachDetailMapper.deleteYearDirectByVersion(wzchPriorApproach.getVersion());
        if(CollectionUtils.isNotEmpty(wzchPriorApproach.getWzchPriorApproachDetailList()))
            wzchPriorApproachDetailMapper.batchInsert(wzchPriorApproach.getWzchPriorApproachDetailList());
        if(CollectionUtils.isNotEmpty(wzchPriorApproachYearCounts))
            wzchPriorApproachYearCountService.batchInsert(wzchPriorApproachYearCounts);
        if (ButtonMark.CONFIRM.equals(wzchPriorApproach.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchPriorApproach.getMenuId();
            String stageIdentity = wzchPriorApproach.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
    
    @Override
    public WzchPriorApproach buildDefaultApproach(BigDecimal version){
        WzchPriorApproach main = new WzchPriorApproach();
        main.setId(IdWorker.createId());
        main.setApproachCode("");
        main.setTitle("");
        main.setVersion(version);
//        main.setProjectId();
//        main.setProjectName(SecurityUtils.getTenantKey());
        main.setPrjCode(SecurityUtils.getTenantKey());
        new AddBaseInfoUtil().addBaseEntity(main);
        return main;
    }

    @Override
    public void export(List<WzchPriorApproachDetail> wzchPriorApproachDetailList, HttpServletResponse response) {
       // List<WzchPriorApproachDetail> wzchPriorApproachDetailList = exportData(request);
        List<String> yesrList = new ArrayList<>();
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproachDetailList) {
            List<WzchPriorApproachYearCount> countList = wzchPriorApproachDetail.getWzchPriorApproachYearCountList();
            if (CollectionUtils.isEmpty(countList)) {
                continue;
            }
            yesrList.addAll( countList.stream().map(WzchPriorApproachYearCount::getYear).collect(Collectors.toList()));

        }
        List<String> yesrs = yesrList.stream().distinct().sorted().collect(Collectors.toList());
        List<List<String>> head = head(yesrs);
        System.out.println(JSONObject.toJSONString(head));
        List<List<Object>> data = getData(wzchPriorApproachDetailList, yesrs);
     //  System.out.println(JSONObject.toJSONString(data));
        EasyExeclUtil.export(response,head,data,"优先进场物资台账详情.xlsx","优先进场物资台账详情");


    }

    @Override
    public List<WzchPriorApproachDetailResponse> selectList(WzchPriorApproachDetail wzchPriorApproachDetail) {
        //是否优先进场
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail("1","1", wzchPriorApproachDetail.getProjectId()));
        if(CollectionUtils.isEmpty(wzchTotalDemandDetails)){
            return new ArrayList<>();
        }
        List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> timeCounts = wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIds(detailIds);
        if(CollectionUtils.isEmpty(timeCounts)){
            throw new BaseException("获取数据异常");
        }
        List<String> years = timeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());
        wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetails);
        List<WzchPriorApproachDetailResponse> wzchPriorApproachDetailResponses = new ArrayList<>();
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails) {
            WzchPriorApproachDetailResponse wzchPriorApproachDetailResponse = new WzchPriorApproachDetailResponse();
            BeanUtils.copyProperties(wzchTotalDemandDetail,wzchPriorApproachDetailResponse);
            wzchPriorApproachDetailResponse.setYearList(years);

            List<WzchPriorApproachYearCount> wzchPriorApproachYearCountList = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(years)){
                years.stream().forEach(y->{
                    WzchPriorApproachYearCount wzchPriorApproachYearCount = new WzchPriorApproachYearCount();
                    wzchPriorApproachYearCountList.add(wzchPriorApproachYearCount);
                });
            }
            wzchPriorApproachDetailResponse.setWzchPriorApproachYearCountList(wzchPriorApproachYearCountList);
            wzchPriorApproachDetailResponses.add(wzchPriorApproachDetailResponse);
        }
        return wzchPriorApproachDetailResponses;
    }

    @Override
    public List<WzchPriorApproachDetail> importData(MultipartFile file) throws IOException {
        if(file==null){
            throw new BaseException("请传入需要导入的文件");
        }
        List<WzchPriorApproachDetail> approachDetails = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        List<Map<String,String>> list = EasyExcel.read(inputStream).headRowNumber(0).excelType(ExcelTypeEnum.XLSX).sheet().doReadSync();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String, String> head0Map = list.get(0);
        List<String> head0List = new ArrayList<>();
        for(Map.Entry<String,String> entry : head0Map.entrySet()){
            head0List.add(entry.getValue());
        }
        //截取一级头部 年
        List<String> head0s = subList(head0List, 12, head0List.size());
        //给年分组，可以得值年对应的月数据或季度数据的个数
        Map<String, Long> head0YearMap = head0s.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> head0YearTreeMap = new TreeMap<>();
        //年排序
        List<String> yearList = new ArrayList<>();
        head0YearTreeMap.putAll(head0YearMap);
        for( Map.Entry<String, Long> entry:head0YearTreeMap.entrySet()){
            yearList.add(entry.getKey());
        }
        yearList = yearList.stream().sorted().collect(Collectors.toList());
        //二级头部
        Map<String, String> head1Map = list.get(1);
        List<String> head1List = new ArrayList<>();
        for(Map.Entry<String, String> entry : head1Map.entrySet()){
            head1List.add(entry.getValue());
        }
        //截取二级头部
        List<String> head1s = subList(head1List, 12, head1List.size());
        Map<String,Map<String,Object>> headBig = new TreeMap<>();
        //给年分配二级头部
        int subStart = 0;
        for(String year : yearList){
            int subEnd  = head0YearMap.get(year).intValue()+subStart;
            Map<String,Object> map = new HashMap<>();
            map.put("subStart",subStart);
            map.put("subEnd",subEnd);
            subStart=subEnd;
            headBig.put(year,map);
        }

        List<SysDictData> tSysDictDataList = dictTypeService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = dictTypeService.selectDictDataByType("material_standard");
        int dataFlag = 0;
        for (Map<String,String> param : list) {

            if(dataFlag<=1 || dataFlag>list.size()-1 ){
                dataFlag++;
                continue;
            }
            WzchPriorApproachDetail detail = fillWzchPriorApproachDetailBaseInfo(yearList, param,tSysDictDataList,mSysDictDataList);
            List<WzchPriorApproachYearCount> yearCountList = new ArrayList<>();

            Map<String, String> dataMap = list.get(dataFlag);
            List<String> dataList = new ArrayList<>();
            for(Map.Entry<String, String> entry : dataMap.entrySet()){
                dataList.add(entry.getValue());
            }
            if(dataList.size()<head1Map.size()){
                int size = head1Map.size() - dataList.size();
                List<String> nList = new ArrayList<>();
                for(int i = 0 ; i<size ; i++){
                    nList.add(null);
                }
                dataList.addAll(nList);
            }
            //截取数据
            List<String> datas = subList(dataList, 12, head1List.size());

            for(String year : yearList){
                Map<String, Object> stringObjectMap = headBig.get(year);
                WzchPriorApproachYearCount yearCount = new WzchPriorApproachYearCount();
                yearCount.setId(IdWorker.createId());
                yearCount.setYear(year);
                Integer sStart = (Integer) stringObjectMap.get("subStart");
                Integer sEnd = (Integer) stringObjectMap.get("subEnd");
                List<String> vlist = subList(datas, sStart, sEnd);
                yearCount.setInternalNum(StringUtils.isBlank(vlist.get(0)) ? null : new BigDecimal(vlist.get(0)));
                yearCount.setOtherStateNum(StringUtils.isBlank(vlist.get(1)) ? null : new BigDecimal(vlist.get(1)));
                yearCount.setLocalNum(StringUtils.isBlank(vlist.get(2)) ? null : new BigDecimal(vlist.get(2)));
                yearCount.setRentNum(StringUtils.isBlank(vlist.get(3)) ? null : new BigDecimal(vlist.get(3)));
                yearCount.setInnerAdjustNum(StringUtils.isBlank(vlist.get(4)) ? null : new BigDecimal(vlist.get(4)));
                yearCountList.add(yearCount);

            }

            detail.setWzchPriorApproachYearCountList(yearCountList);
            approachDetails.add(detail);
            dataFlag++;
        }
        return approachDetails;
    }

    private WzchPriorApproachDetail fillWzchPriorApproachDetailBaseInfo(List<String> yearList,Map<String,String> param,List<SysDictData> tSysDictDataList ,List<SysDictData> mSysDictDataList ){
        WzchPriorApproachDetail detail= new WzchPriorApproachDetail();
        detail.setMaterialCode(param.get(0));
        detail.setMaterialName(param.get(1));
        detail.setMaterialSpec(param.get(2));
        detail.setMaterialTechParam(param.get(3));
        if(CollectionUtils.isNotEmpty(mSysDictDataList)){
            mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(4)))
                    .findFirst().ifPresent(val -> detail.setMaterialStandard(val.getDictValue()));
        }else{
            detail.setMaterialStandard(param.get(4));
        }
        detail.setUnit(param.get(5));
        detail.setTotalDemandAmount(param.get(6)==null?new BigDecimal(0):new BigDecimal(param.get(6)));
        detail.setSelfDemandAmount(param.get(7)==null?new BigDecimal(0):new BigDecimal(param.get(7)));
        if(CollectionUtils.isNotEmpty(tSysDictDataList)){
            tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(8)))
                    .findFirst().ifPresent(val -> detail.setCategoryName(val.getDictValue()));
        }else{
            detail.setCategoryName(param.get(8));
        }
        detail.setPriorApproachNum(param.get(9)==null?new BigDecimal(0):new BigDecimal(param.get(9)));
        detail.setEarliestReqTime(StringUtils.isBlank(param.get(10))?null:DateUtils.dateTime("yyyy-MM-dd",param.get(10).replace("/","-")));
        detail.setPresentTime(StringUtils.isBlank(param.get(11))?null:DateUtils.dateTime("yyyy-MM-dd",param.get(11).replace("/","-")));
        detail.setYearList(yearList);
        return detail;
    }

    private  List<String> subList(List<String> list, int start,int end) {
        List<String> resultList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(list)){
            return resultList;
        }
        if(start >= list.size()){
            return list;
        }
        if(start < list.size()){
            return list.subList(start, end);
        }
        return resultList;
    }

    public List<WzchPriorApproachDetail> exportData(WzchPriorApproachDetailExportRequest request) {
        List<WzchPriorApproachDetail> wzchPriorApproachDetailList = wzchPriorApproachDetailMapper.selectByPriorApproachIdAndIdList(request);
        if(CollectionUtils.isEmpty(wzchPriorApproachDetailList)){
            throw new BaseException("导出数据异常");
        }
        List<Long> detailIds = wzchPriorApproachDetailList.stream().map(WzchPriorApproachDetail::getId).collect(Collectors.toList());
        List<WzchPriorApproachYearCount> yearCounts = wzchPriorApproachYearCountService.selectByDetailIds(detailIds);
        if (CollectionUtils.isEmpty(yearCounts)) {
            return wzchPriorApproachDetailList;
        }
        // annotationToField( yearCounts ); 修改 实体类注解值
        Map<Long, List<WzchPriorApproachYearCount>> map = yearCounts.stream().collect(Collectors.groupingBy(WzchPriorApproachYearCount::getDetailId));
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproachDetailList) {
            if(wzchPriorApproachDetail==null){
                continue;
            }
            for(Map.Entry<Long, List<WzchPriorApproachYearCount>> entry :map.entrySet()){
                if(wzchPriorApproachDetail.getId().equals(entry.getKey())){
                    wzchPriorApproachDetail.setWzchPriorApproachYearCountList(entry.getValue());
                }
            }

        }
        return wzchPriorApproachDetailList;
    }

    private List<List<String>> head(List<String> yesrs){
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<>();
        head0.add("物资编码" );

        List<String> head1 = new ArrayList<>();
        head1.add("物资名称");

        List<String> head2 = new ArrayList<>();
        head2.add("规格型号");

        List<String> head3 = new ArrayList<>();
        head3.add("技术参数" );

        List<String> head4 = new ArrayList<>();
        head4.add("执行标准");

        List<String> head5 = new ArrayList<>();
        head5.add("单位");

        List<String> head6 = new ArrayList<>();
        head6.add("总需用量" );

        List<String> head7 = new ArrayList<>();
        head7.add("自采需用量");

        List<String> head8 = new ArrayList<>();
        head8.add("类型");

        List<String> head9 = new ArrayList<>();
        head9.add("优先到场数量");

        List<String> head10 = new ArrayList<>();
        head10.add("最早需用日期");

        List<String> head11 = new ArrayList<>();
        head11.add("要求到场日期");

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        list.add(head4);
        list.add(head5);
        list.add(head6);
        list.add(head7);
        list.add(head8);
        list.add(head9);
        list.add(head10);
        list.add(head11);

        List<String> productHead= new ArrayList<>();
        productHead.add("国内采购");
        productHead.add("第三国采购");
        productHead.add("本地采购");
        productHead.add("租赁数量");
        productHead.add("内部调剂");

        List<String> headDa = new ArrayList<>();
        for (String year : yesrs) {
            for (String d : productHead) {
                headDa = new ArrayList<>();
                headDa.add(year);
                headDa.add(d);
                list.add(headDa);
            }
        }
        return list;
    }

    public List<List<Object>> getData(List<WzchPriorApproachDetail> wzchPriorApproachDetails, List<String> yesrs ){
        List<List<Object>> data=new ArrayList<>();
        List<SysDictData> tSysDictDataList = dictTypeService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = dictTypeService.selectDictDataByType("material_standard");
        for(WzchPriorApproachDetail detail : wzchPriorApproachDetails){
            List<Object> list=new ArrayList<>();
            list.add(detail.getMaterialCode());
            list.add(detail.getMaterialName());
            list.add(detail.getMaterialSpec());
            list.add(detail.getMaterialTechParam());
            if(CollectionUtils.isNotEmpty(mSysDictDataList) && StringUtils.isNotBlank(detail.getMaterialStandard())){
                mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getMaterialStandard()))
                        .findFirst().ifPresent(val -> list.add(val.getDictLabel()));
            }else{
                list.add(detail.getMaterialStandard());
            }
            list.add(detail.getUnit());
            list.add(detail.getTotalDemandAmount());
            list.add(detail.getSelfDemandAmount());
            if(CollectionUtils.isNotEmpty(tSysDictDataList) && StringUtils.isNotBlank(detail.getCategoryName())){
                tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getCategoryName()))
                        .findFirst().ifPresent(val -> list.add(val.getDictLabel()));
            }else{
                list.add(detail.getCategoryName());
            }
            list.add(detail.getPriorApproachNum());
            list.add(new SimpleDateFormat("yyyy-MM-dd").format(detail.getEarliestReqTime()));
            list.add(new SimpleDateFormat("yyyy-MM-dd").format(detail.getPresentTime()));
            List<WzchPriorApproachYearCount> yearCountList = detail.getWzchPriorApproachYearCountList();

            for(String year:yesrs){
                for(WzchPriorApproachYearCount yearCount:yearCountList){
                    if(!yesrs.contains(yearCount.getYear())){
                        list.add(null);
                        list.add(null);
                        list.add(null);
                        list.add(null);
                        list.add(null);
                    }
                    if(year.equals(yearCount.getYear())){
                        list.add(yearCount.getInternalNum());
                        list.add(yearCount.getOtherStateNum());
                        list.add(yearCount.getLocalNum());
                        list.add(yearCount.getRentNum());
                        list.add(yearCount.getInnerAdjustNum());
                        break;
                    }
                }

            }
            data.add(list);
        }

        return data;

    }


    private void fillWzchPriorApproachDetail(WzchPriorApproach wzchPriorApproach) {
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproach.getWzchPriorApproachDetailList()) {
            if(wzchPriorApproachDetail==null ||
                    CollectionUtils.isEmpty(wzchPriorApproachDetail.getWzchPriorApproachYearCountList())) {
                throw new BaseException("【保存数据失败】请完善表格数据");
            }
            Long detailId = wzchPriorApproachDetail.getId();
            if(detailId==null){
                detailId = IdWorker.createId();
                wzchPriorApproachDetail.setId(detailId);
            }
            if(wzchPriorApproachDetail.getPriorApproachId()==null){
                wzchPriorApproachDetail.setPriorApproachId(wzchPriorApproach.getId());
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getCreateUser())){
                wzchPriorApproachDetail.setCreateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getCreateUserName())){
                wzchPriorApproachDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchPriorApproachDetail.getCreateTime())){
                wzchPriorApproachDetail.setCreateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getUpdateUser())){
                wzchPriorApproachDetail.setUpdateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getUpdateUserName())){
                wzchPriorApproachDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchPriorApproachDetail.getUpdateTime())){
                wzchPriorApproachDetail.setUpdateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getDelFlag())){
                wzchPriorApproachDetail.setDelFlag("0");
            }
            if(StringUtils.isBlank(wzchPriorApproachDetail.getProjectName())){
                wzchPriorApproachDetail.setProjectName(wzchPriorApproach.getProjectName());
            }
            if(wzchPriorApproachDetail.getProjectId()==null){
                wzchPriorApproachDetail.setProjectId(wzchPriorApproach.getProjectId());
            }
            if(wzchPriorApproachDetail.getEarliestReqTime()!=null && wzchPriorApproachDetail.getPresentTime()!=null){
                if( wzchPriorApproachDetail.getPresentTime().after(wzchPriorApproachDetail.getEarliestReqTime())){
                    throw new BaseException("第【"+wzchPriorApproachDetail.getOrderNo()+"】行最早需要日期不得早于要求到场日期");
                }
            }
            if(Optional.ofNullable(wzchPriorApproachDetail.getPriorApproachNum()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(wzchPriorApproachDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)))==1){
                throw new BaseException("第【"+wzchPriorApproachDetail.getOrderNo()+"】行优先到场数量应小于或等于自采需用量");
            }
            for(WzchPriorApproachYearCount wzchPriorApproachYearCount : wzchPriorApproachDetail.getWzchPriorApproachYearCountList()){
                if (wzchPriorApproachYearCount==null) {
                    continue;
                }
                if(wzchPriorApproachYearCount.getId()==null){
                    wzchPriorApproachYearCount.setId(IdWorker.createId());
                }
                if(wzchPriorApproachYearCount.getDetailId()==null){
                    wzchPriorApproachYearCount.setDetailId(detailId);
                }
                if(StringUtils.isBlank(wzchPriorApproachYearCount.getCreateUser())){
                    wzchPriorApproachYearCount.setCreateUser(SecurityUtils.getUserId().toString());
                }

                if(ObjectUtils.isEmpty(wzchPriorApproachYearCount.getCreateTime())){
                    wzchPriorApproachYearCount.setCreateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(wzchPriorApproachYearCount.getUpdateUser())){
                    wzchPriorApproachYearCount.setUpdateUser(SecurityUtils.getUserId().toString());
                }

                if(ObjectUtils.isEmpty(wzchPriorApproachYearCount.getUpdateTime())){
                    wzchPriorApproachYearCount.setUpdateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(wzchPriorApproachYearCount.getDelFlag())){
                    wzchPriorApproachYearCount.setDelFlag("0");
                }
                if(wzchPriorApproachYearCount.getMaterialCode()==null){
                    wzchPriorApproachYearCount.setMaterialCode(wzchPriorApproachDetail.getMaterialCode());
                }

            }
        }

    }

    private void  fillWzchPriorApproach(WzchPriorApproach wzchPriorApproach) {

        if(StringUtils.isBlank(wzchPriorApproach.getApproachCode())){
            String code = genCodeService.getCode(CodeEnum.EQU_TOTAL_DEMAND);
            code += genCodeService.fillString(1, 2);
            wzchPriorApproach.setApproachCode(code);
        }
        if(StringUtils.isBlank(wzchPriorApproach.getCreateUser())){
            wzchPriorApproach.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchPriorApproach.getCreateUserName())){
            wzchPriorApproach.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchPriorApproach.getCreateTime())){
            wzchPriorApproach.setCreateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchPriorApproach.getUpdateUser())){
            wzchPriorApproach.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchPriorApproach.getUpdateUserName())){
            wzchPriorApproach.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchPriorApproach.getUpdateTime())){
            wzchPriorApproach.setUpdateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchPriorApproach.getDelFlag())){
            wzchPriorApproach.setDelFlag("0");
        }
    }

    @Override
    public int deleteDirectByVersion(BigDecimal version) {
        if(version == null)
            return 0;
        return this.wzchPriorApproachDetailMapper.deleteDirectByVersion(version);
    }

    @Override
    public int deleteYearDirectByVersion(BigDecimal version) {
        if(version == null)
            return 0;
        return this.wzchPriorApproachDetailMapper.deleteYearDirectByVersion(version);
    }
}
