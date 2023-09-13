package com.hhwy.pm.qqch.wzch.source.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.utils.EasyExeclUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandTimeCountMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceApproachYearCountMapper;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceDetailMapper;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceDetailService;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.pm.qqch.wzch.source.vo.ReminderOfChangeDetailResponse;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailExportRequest;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailReminderOfChangeRequest;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailResponse;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 来源策划物资详情Service业务层处理
 * 
 * @author mls
 * @date 2022-11-21
 */
@Service
public class WzchSourceDetailServiceImpl implements IWzchSourceDetailService {
    @Resource
    private WzchSourceDetailMapper wzchSourceDetailMapper;
    @Resource
    private WzchTotalDemandTimeCountMapper wzchTotalDemandTimeCountMapper;
    @Resource
    private WzchSourceApproachYearCountMapper wzchSourceApproachYearCountMapper;
    @Resource
    private IWzchSourceService wzchSourceService;
    @Resource
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;
    @Resource
    private IWzchTotalDemandService wzchTotalDemandService;
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private SystemApiService systemApiService;



    /**
     * 查询来源策划物资详情
     * 
     * @param id 来源策划物资详情ID
     * @return 来源策划物资详情
     */
    @Override
    public WzchSourceDetail selectWzchSourceDetailById(Long id) {
        return wzchSourceDetailMapper.selectWzchSourceDetailById(id);
    }

    /**
     * 查询来源策划物资详情列表
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 来源策划物资详情
     */
    @Override
    public List<WzchSourceDetail> selectWzchSourceDetailList(WzchSourceDetail wzchSourceDetail) {
        return wzchSourceDetailMapper.selectWzchSourceDetailList(wzchSourceDetail);
    }

    /**
     * 新增来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    @Override
    public int insertWzchSourceDetail(WzchSourceDetail wzchSourceDetail) {

    wzchSourceDetail.setId(IdWorker.createId());

        wzchSourceDetail.setCreateTime(DateUtils.getNowDate());

        return wzchSourceDetailMapper.insertWzchSourceDetail(wzchSourceDetail);
    }

    /**
     * 修改来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    @Override
    public int updateWzchSourceDetail(WzchSourceDetail wzchSourceDetail) {
        wzchSourceDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchSourceDetailMapper.updateWzchSourceDetail(wzchSourceDetail);
    }


    @Override
    public List<WzchSourceDetail> selectDetailList(WzchSourceDetail wzchSourceDetail) {
        if(wzchSourceDetail == null){
            throw new CustomBusinessException("查询失败");
        }
      return   querryWzchSourceDetailList(wzchSourceDetail.getSourceId());

    }

    @Override
    @Transactional(rollbackFor = {Exception.class,BaseException.class})
    public boolean save(WzchSource wzchSource) {
        checkWzchSource( wzchSource);
        fillWzchSource(wzchSource);
        fillWzchSourceDetail(wzchSource);
        WzchSource source = wzchSourceService.selectWzchSourceById(wzchSource.getId());
        if(source==null) {
            wzchSourceService.insertWzchSource(wzchSource);
        }else{
            wzchSourceService.updateWzchSource(wzchSource);
        }
        wzchSourceDetailMapper.deleteWzchSourceDetailBySourdeId(wzchSource.getId());
        List<WzchSourceDetail> detailList = wzchSource.getWzchSourceDetailList();
        wzchSourceDetailMapper.batchInsert(detailList);
        List<Long> detailIds = detailList.stream().map(WzchSourceDetail::getId).collect(Collectors.toList());
        wzchSourceApproachYearCountMapper.deleteWzchSourceApproachYearCountByDetailIds(detailIds);
        List<WzchSourceApproachYearCount> yearCounts = new ArrayList<>();
        for (WzchSourceDetail wzchSourceDetail : detailList) {
            yearCounts.addAll(wzchSourceDetail.getWzchSourceApproachYearCountList());
        }
        wzchSourceApproachYearCountMapper.batchInsert(yearCounts);
        return true;
    }

    private void fillWzchSourceDetail(WzchSource wzchSource) {
        for (WzchSourceDetail wzchSourceDetail : wzchSource.getWzchSourceDetailList()) {
            if(wzchSourceDetail==null ||
                    CollectionUtils.isEmpty(wzchSourceDetail.getWzchSourceApproachYearCountList())) {
                throw new BaseException("【保存数据失败】请完善表格数据");
            }
            Long detailId = wzchSourceDetail.getId();
            if(detailId==null){
                detailId = IdWorker.createId();
                wzchSourceDetail.setId(detailId);
            }
            if(wzchSourceDetail.getSourceId()==null){
                wzchSourceDetail.setSourceId(wzchSource.getId());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getCreateUser())){
                wzchSourceDetail.setCreateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getCreateUserName())){
                wzchSourceDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchSourceDetail.getCreateTime())){
                wzchSourceDetail.setCreateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getUpdateUser())){
                wzchSourceDetail.setUpdateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getUpdateUserName())){
                wzchSourceDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchSourceDetail.getUpdateTime())){
                wzchSourceDetail.setUpdateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getDelFlag())){
                wzchSourceDetail.setDelFlag("0");
            }
            if(StringUtils.isBlank(wzchSourceDetail.getProjectName())){
                wzchSourceDetail.setProjectName(wzchSource.getProjectName());
            }
            if(wzchSourceDetail.getProjectId()==null){
                wzchSourceDetail.setProjectId(wzchSource.getProjectId());
            }
            if(StringUtils.isBlank(wzchSourceDetail.getValid())){
                wzchSourceDetail.setValid("0");
            }
            BigDecimal big = new BigDecimal(0);
            for(WzchSourceApproachYearCount wzchSourceApproachYearCount : wzchSourceDetail.getWzchSourceApproachYearCountList()){
                if (wzchSourceApproachYearCount==null) {
                    throw new BaseException("【保存数据失败】请完善表格数据");
                }
                if(wzchSourceApproachYearCount.getId()==null){
                    wzchSourceApproachYearCount.setId(IdWorker.createId());
                }
                if(wzchSourceApproachYearCount.getDetailId()==null){
                    wzchSourceApproachYearCount.setDetailId(detailId);
                }
                if(StringUtils.isBlank(wzchSourceApproachYearCount.getCreateUser())){
                    wzchSourceApproachYearCount.setCreateUser(SecurityUtils.getUserId().toString());
                }

                if(ObjectUtils.isEmpty(wzchSourceApproachYearCount.getCreateTime())){
                    wzchSourceApproachYearCount.setCreateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(wzchSourceApproachYearCount.getUpdateUser())){
                    wzchSourceApproachYearCount.setUpdateUser(SecurityUtils.getUserId().toString());
                }
                if(StringUtils.isBlank(wzchSourceApproachYearCount.getMaterialCode())){
                    wzchSourceApproachYearCount.setMaterialCode(wzchSourceDetail.getMaterialCode());
                }
                if(ObjectUtils.isEmpty(wzchSourceApproachYearCount.getUpdateTime())){
                    wzchSourceApproachYearCount.setUpdateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(wzchSourceApproachYearCount.getDelFlag())){
                    wzchSourceApproachYearCount.setDelFlag("0");
                }
                if(new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)))==0 ){
                    continue;
                }
                if( new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceApproachYearCount.getInnerAdjustNum()).orElse(new BigDecimal(0)))==0
                        && new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceApproachYearCount.getOtherStateNum()).orElse(new BigDecimal(0)))==0
                        && new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceApproachYearCount.getLocalNum()).orElse(new BigDecimal(0)))==0
                        && new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceApproachYearCount.getInternalNum()).orElse(new BigDecimal(0)))==0
                        && new BigDecimal(0).compareTo(Optional.ofNullable(wzchSourceApproachYearCount.getRentNum()).orElse(new BigDecimal(0)))==0
                ){
                    continue;
                }
                BigDecimal bigDecimal = new BigDecimal(0);
                bigDecimal = bigDecimal.add(Optional.ofNullable(wzchSourceApproachYearCount.getInnerAdjustNum()).orElse(new BigDecimal(0)))
                        .add(Optional.ofNullable(wzchSourceApproachYearCount.getOtherStateNum()).orElse(new BigDecimal(0)))
                        .add(Optional.ofNullable(wzchSourceApproachYearCount.getLocalNum()).orElse(new BigDecimal(0)))
                        .add(Optional.ofNullable(wzchSourceApproachYearCount.getInternalNum()).orElse(new BigDecimal(0)))
                        .add(Optional.ofNullable(wzchSourceApproachYearCount.getRentNum()).orElse(new BigDecimal(0)));
                big = big.add(bigDecimal);
            }
            if(new BigDecimal(0).compareTo(big)==0){
                continue;
            }
            int i = big.compareTo(wzchSourceDetail.getTotalDemandAmount());
            if(i!=0){
                throw new BaseException("第【"+wzchSourceDetail.getOrderNo()+"】行的来源数量与总需数量不符");
            }
        }

    }

    private void  fillWzchSource(WzchSource wzchSource) {
        if(StringUtils.isBlank(wzchSource.getSourceCode())){
            String code = genCodeService.getSetCode(CodeEnum.EQU_SOURCE);
            code += genCodeService.fillString(1, 2);
            wzchSource.setSourceCode(code);
        }
        if(StringUtils.isBlank(wzchSource.getCreateUser())){
            wzchSource.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchSource.getCreateUserName())){
            wzchSource.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchSource.getCreateTime())){
            wzchSource.setCreateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchSource.getUpdateUser())){
            wzchSource.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchSource.getUpdateUserName())){
            wzchSource.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchSource.getUpdateTime())){
            wzchSource.setUpdateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchSource.getDelFlag())){
            wzchSource.setDelFlag("0");
        }
        if(StringUtils.isNotBlank(wzchSource.getDemandNewVersion())){
            wzchSource.setDemandVersion(wzchSource.getDemandNewVersion());
        } else {
            Long projectId = wzchSource.getProjectId();
            WzchTotalDemand wzchTotalDemand = this.wzchTotalDemandService.selectMaxValidVersionCodeWzchTotalDemandByProjectId(projectId);
            String versionCode = wzchTotalDemand.getVersionCode();
            wzchSource.setDemandVersion(versionCode);
            wzchSource.setDemandNewVersion(versionCode);
        }

    }


    private void checkWzchSource(WzchSource wzchSource){
        if(wzchSource == null){
            throw new BaseException("500","入参缺失");
        }
        if (CollectionUtils.isEmpty(wzchSource.getWzchSourceDetailList())) {
            throw new BaseException("数据缺失");
        }
    }
    @Override
    public List<WzchSourceDetailResponse> getProjectTotalDemandDetail() {
        
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail("1",null));
        if(CollectionUtils.isEmpty(wzchTotalDemandDetails)){
            return new ArrayList<>();
        }
        Long id = wzchTotalDemandDetails.get(0).getTotalDemandId();
        WzchTotalDemand wzchTotalDemands = wzchTotalDemandService.selectWzchTotalDemandById(id);
        List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> timeCounts = wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIds(detailIds);
        if(CollectionUtils.isEmpty(timeCounts)){
            throw new BaseException("获取数据异常");
        }
        List<String> yesrs = timeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());


        List<WzchSourceDetailResponse> wzchSourceDetailResponseList = new ArrayList<>();
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails) {
            WzchSourceDetailResponse detailResponse = new WzchSourceDetailResponse();
            BeanUtils.copyProperties(wzchTotalDemandDetail,detailResponse);
            detailResponse.setYearList(yesrs);
            List<WzchSourceApproachYearCount> wzchSourceApproachYearCountList = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(yesrs)){
                yesrs.stream().forEach(y->{
                    WzchSourceApproachYearCount yearCount = new WzchSourceApproachYearCount();
                    wzchSourceApproachYearCountList.add(yearCount);
                });
            }
            detailResponse.setWzchSourceApproachYearCountList(wzchSourceApproachYearCountList);
            detailResponse.setDemandVersion(wzchTotalDemands.getVersionCode());
            detailResponse.setDemandNewVersion(wzchTotalDemands.getVersionCode());
            detailResponse.setDemandValidDate(wzchTotalDemands.getUpdateTime());
            wzchSourceDetailResponseList.add(detailResponse);
        }
        if(CollectionUtils.isNotEmpty(wzchSourceDetailResponseList)){
            wzchCommonService.setWzchtMaterialInfo(wzchSourceDetailResponseList);
            wzchSourceDetailResponseList = wzchSourceDetailResponseList.stream().sorted(Comparator.comparing(WzchSourceDetailResponse::getMaterialCode)).collect(Collectors.toList());
        }
        return wzchSourceDetailResponseList;
    }

    @Override
    public boolean reminderOfChange(WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest) {
        if (reminderOfChangeRequest == null) {
            throw new BaseException("入参缺失");
        }

        List<WzchSource> wzchSources = wzchSourceService.selectWzchSourceList(new WzchSource(reminderOfChangeRequest.getSourceId(), reminderOfChangeRequest.getProjectId()));
        if (CollectionUtils.isEmpty(wzchSources)) {
            throw new BaseException("数据查询失败");
        }
        WzchSource wzchSource = wzchSources.get(0);
        if (wzchSource.getDemandVersion().equals(wzchSource.getDemandNewVersion())) {
            return false;
        }
        return true;
    }

    @Override
    public void export(List<WzchSourceDetail> wzchSourceDetails, HttpServletResponse response) {
        //List<WzchSourceDetail> wzchSourceDetails = exportData(request);
        List<String> yesrList = new ArrayList<>();
        if (CollectionUtils.isEmpty(wzchSourceDetails)) {

        }
        for (WzchSourceDetail wzchSourceDetail : wzchSourceDetails) {
            List<WzchSourceApproachYearCount> countList = wzchSourceDetail.getWzchSourceApproachYearCountList();
            if (CollectionUtils.isEmpty(countList)) {
                continue;
            }
            yesrList.addAll( countList.stream().map(WzchSourceApproachYearCount::getYear).collect(Collectors.toList()));

        }
        List<String> yesrs = yesrList.stream().distinct().sorted().collect(Collectors.toList());
        List<List<String>> head = head(yesrs);
        System.out.println(JSONObject.toJSONString(head));
        List<List<Object>> data = getData(wzchSourceDetails, yesrs);
        System.out.println(JSONObject.toJSONString(data));
        EasyExeclUtil.export(response,head,data,"来源策划详情.xlsx","来源策划详情");

    }

    @Override
    public List<WzchSourceTotalDemandDetailVO> reminderOfChangeDetail(WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest) {
        if (reminderOfChangeRequest==null) {
            throw new BaseException("入参缺失");
        }
        List<WzchSource> wzchSources = wzchSourceService.selectWzchSourceList(new WzchSource(reminderOfChangeRequest.getSourceId(), reminderOfChangeRequest.getProjectId()));
        if (CollectionUtils.isEmpty(wzchSources)) {
            throw new BaseException("数据查询失败");
        }
        WzchSource wzchSource = wzchSources.get(0);
        if(wzchSource.getDemandVersion().equals(wzchSource.getDemandNewVersion())){
            return null;
        }

        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandService.selectWzchTotalDemandList(new WzchTotalDemand(wzchSource.getDemandNewVersion(), wzchSource.getProjectId()));
        if(CollectionUtils.isEmpty(wzchTotalDemands)){
            throw new BaseException("查询物资总需最新版本数据失败");
        }
        WzchTotalDemand wzchTotalDemand = wzchTotalDemands.get(0);
        List<WzchTotalDemandDetail> demandNewVersionDetailList = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(wzchTotalDemand.getId()));
        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailList(new WzchSourceDetail(wzchSource.getProjectId()));
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            throw new BaseException("获取来源信息数据失败");
        }
        List<Long> detailIds = wzchSourceDetails.stream().map(WzchSourceDetail::getId).collect(Collectors.toList());
        List<WzchSourceApproachYearCount> yearCounts = wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountByDetailIds(detailIds);
        if(CollectionUtils.isNotEmpty(yearCounts)){
            Map<Long, List<WzchSourceApproachYearCount>> yearCountMap = yearCounts.stream().collect(Collectors.groupingBy(WzchSourceApproachYearCount::getDetailId));
            for (WzchSourceDetail wzchSourceDetail : wzchSourceDetails) {
                for ( Map.Entry<Long, List<WzchSourceApproachYearCount>> entry : yearCountMap.entrySet()) {
                    if(wzchSourceDetail.getId().equals(entry.getKey())){
                        wzchSourceDetail.setWzchSourceApproachYearCountList(entry.getValue());
                    }
                }
            }
        }
        List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = new ArrayList<>();
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            return wzchSourceTotalDemandDetailVOS;
        }
        List<Long> demandDetailIds = demandNewVersionDetailList.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIds(demandDetailIds);
        List<String> years = wzchTotalDemandTimeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());
        for (WzchTotalDemandDetail newVersionDetail : demandNewVersionDetailList) {
            WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
            List<ReminderOfChangeDetailResponse> detailResponses = new ArrayList<>();
            BeanUtils.copyProperties(newVersionDetail,wzchSourceTotalDemandDetailVO);
            wzchSourceTotalDemandDetailVO.setYesrList(years);
            int a = 0;
            Iterator<WzchSourceDetail> iterator = wzchSourceDetails.iterator();
            while(iterator.hasNext()){
                WzchSourceDetail backVersionDetail = iterator.next();
                if(newVersionDetail.getMaterialCode().equals(backVersionDetail.getMaterialCode()) && newVersionDetail.getMaterialStandard().equals(backVersionDetail.getMaterialStandard())){
                    //修改数据
                    if(Optional.ofNullable(newVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)))!=0
                            || Optional.ofNullable(newVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)))!=0) {
                        wzchSourceTotalDemandDetailVO.setBackTotalDemandAmount(backVersionDetail.getTotalDemandAmount());
                        wzchSourceTotalDemandDetailVO.setBackSelfDemandAmount(backVersionDetail.getSelfDemandAmount());
                        if(backVersionDetail.getTotalDemandAmount()!=null && backVersionDetail.getSelfDemandAmount()!=null){
                            BigDecimal subtract = backVersionDetail.getTotalDemandAmount().subtract(backVersionDetail.getSelfDemandAmount());
                            wzchSourceTotalDemandDetailVO.setBackNonSelfAmount(subtract);
                        }
                        wzchSourceTotalDemandDetailVO.setHandleType("2");
                        List<WzchSourceApproachYearCount> wzchSourceApproachYearCountList = backVersionDetail.getWzchSourceApproachYearCountList();
                        if(CollectionUtils.isNotEmpty(wzchSourceApproachYearCountList)){
                            for (WzchSourceApproachYearCount yearCount : wzchSourceApproachYearCountList) {
                                ReminderOfChangeDetailResponse response = new ReminderOfChangeDetailResponse();
                                BeanUtils.copyProperties(yearCount,response);
                                detailResponses.add(response);
                            }
                        }
                        wzchSourceTotalDemandDetailVO.setDetailResponses(detailResponses);
                        wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
                        a=1;
                    }else {
                        a=2;
                    }
                    iterator.remove();
                }

            }
            if(a==0){
                //新增数据
                wzchSourceTotalDemandDetailVO.setHandleType("0");
                wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
            }

        }
        if(CollectionUtils.isEmpty(wzchSourceTotalDemandDetailVOS)){
            return wzchSourceTotalDemandDetailVOS;
        }

        //已删除的数据
        if(CollectionUtils.isNotEmpty(wzchSourceDetails)){
            for (WzchSourceDetail backVersionDetail : wzchSourceDetails) {
                WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
                List<ReminderOfChangeDetailResponse> detailResponseList = new ArrayList<>();
                wzchSourceTotalDemandDetailVO.setMaterialCode(backVersionDetail.getMaterialCode());
                wzchSourceTotalDemandDetailVO.setMaterialStandard(backVersionDetail.getMaterialStandard());
                wzchSourceTotalDemandDetailVO.setCategoryName(backVersionDetail.getCategoryName());
                wzchSourceTotalDemandDetailVO.setId(backVersionDetail.getId());
                wzchSourceTotalDemandDetailVO.setYesrList(years);
                wzchSourceTotalDemandDetailVO.setHandleType("1");
                wzchSourceTotalDemandDetailVO.setMaterialTechParam(backVersionDetail.getMaterialTechParam());
                for (String year : years) {
                    ReminderOfChangeDetailResponse response = new ReminderOfChangeDetailResponse();
                    response.setYear(year);
                    detailResponseList.add(response);
                }
                wzchSourceTotalDemandDetailVO.setDetailResponses(detailResponseList);
                wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
            }
        }
        if(CollectionUtils.isNotEmpty(wzchSourceTotalDemandDetailVOS)){
            wzchCommonService.setWzchtMaterialInfo(wzchSourceTotalDemandDetailVOS);
            wzchSourceTotalDemandDetailVOS = wzchSourceTotalDemandDetailVOS.stream().sorted(Comparator.comparing(WzchSourceTotalDemandDetailVO::getMaterialCode)).collect(Collectors.toList());
        }
        fillNullOfYear(wzchSourceTotalDemandDetailVOS,years);
        return wzchSourceTotalDemandDetailVOS;
    }

    private void fillNullOfYear(List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS,List<String> yearList){
        for (WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO : wzchSourceTotalDemandDetailVOS) {
            List<ReminderOfChangeDetailResponse> detailResponses = wzchSourceTotalDemandDetailVO.getDetailResponses();
            if (CollectionUtils.isEmpty(detailResponses)) {
                detailResponses = new ArrayList<>();
                for (String year : yearList) {
                    ReminderOfChangeDetailResponse response = new ReminderOfChangeDetailResponse();
                    response.setYear(year);
                    detailResponses.add(response);
                }

            }else{
                List<String> responseYears = detailResponses.stream().map(ReminderOfChangeDetailResponse::getYear).collect(Collectors.toList());
                for (String year : yearList) {
                    if(!responseYears.contains(year)){
                        ReminderOfChangeDetailResponse response = new ReminderOfChangeDetailResponse();
                        response.setYear(year);
                        detailResponses.add(response);
                    }
                }

            }
            wzchSourceTotalDemandDetailVO.setDetailResponses(detailResponses);
        }

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

    @Override
    public List<WzchSourceDetail> importData(MultipartFile file) throws IOException {
        if(file==null){
            throw new BaseException("请传入需要导入的文件");
        }
        List<WzchSourceDetail> sourceDetails = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        List<Map<String,String>> list = EasyExcel.read(inputStream).headRowNumber(0).sheet().doReadSync();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String, String> head0Map = list.get(0);
        List<String> head0List = new ArrayList<>();
        for(Map.Entry<String,String> entry : head0Map.entrySet()){
            head0List.add(entry.getValue());
        }
        //截取一级头部 年
        List<String> head0s = subList(head0List, 9, head0List.size());
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
        int headSize = head1Map.size();
        List<String> head1List = new ArrayList<>();
        for(Map.Entry<String, String> entry : head1Map.entrySet()){
            head1List.add(entry.getValue());
        }
        //截取二级头部
        List<String> head1s = subList(head1List, 9, head1List.size());
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

        List<SysDictData> tSysDictDataList = systemApiService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = systemApiService.selectDictDataByType("material_standard");
        int dataFlag = 0;
        for (Map<String,String> param : list) {

            if(dataFlag<=1 || dataFlag>list.size()-1 ){
                dataFlag++;
                continue;
            }
            WzchSourceDetail detail = fillWzchSourceDetailBaseInfo(yearList, param,tSysDictDataList,mSysDictDataList);
            List<WzchSourceApproachYearCount> yearCountList = new ArrayList<>();

            Map<String, String> dataMap = list.get(dataFlag);
            List<String> dataList = new ArrayList<>();
            for(Map.Entry<String, String> entry : dataMap.entrySet()){
                dataList.add(entry.getValue());
            }
            if(dataList.size()<headSize){
                int size = headSize - dataList.size();
                List<String> nList = new ArrayList<>();
                for(int i = 0 ; i<size ; i++){
                    nList.add(null);
                }
                dataList.addAll(nList);
            }
            //截取数据
            List<String> datas = subList(dataList, 9, head1List.size());

            for(String year : yearList){
                Map<String, Object> stringObjectMap = headBig.get(year);
                WzchSourceApproachYearCount yearCount = new WzchSourceApproachYearCount();
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

            detail.setWzchSourceApproachYearCountList(yearCountList);
            sourceDetails.add(detail);
            dataFlag++;
        }
        return sourceDetails;
    }

    private WzchSourceDetail fillWzchSourceDetailBaseInfo(List<String> yearList,Map<String,String> param, List<SysDictData> tSysDictDataList,List<SysDictData> mSysDictDataList){
        WzchSourceDetail detail= new WzchSourceDetail();
        detail.setMaterialCode(param.get(0));
        detail.setMaterialName(param.get(1));
        detail.setMaterialSpec(param.get(2));
        detail.setMaterialTechParam(param.get(3));
        if(CollectionUtils.isNotEmpty(mSysDictDataList) && StringUtils.isNotBlank(param.get(4))){
            mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(4)))
                    .findFirst().ifPresent(val -> detail.setMaterialStandard(val.getDictValue()));
        }else{
            detail.setMaterialStandard(param.get(4));
        }
        detail.setUnit(param.get(5));
        detail.setTotalDemandAmount(param.get(6)==null?new BigDecimal(0):new BigDecimal(param.get(6)));
        detail.setSelfDemandAmount(param.get(7)==null?new BigDecimal(0):new BigDecimal(param.get(7)));

        if(CollectionUtils.isNotEmpty(tSysDictDataList) && StringUtils.isNotBlank(param.get(8))){
            tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(8)))
                    .findFirst().ifPresent(val -> detail.setCategoryName(val.getDictValue()));
        }else{
            detail.setCategoryName(param.get(8));
        }
        detail.setYearList(yearList);
        return detail;
    }

    public List<List<Object>> getData(List<WzchSourceDetail> wzchSourceDetails, List<String> yesrs ){
        List<List<Object>> data=new ArrayList<>();
        List<SysDictData> tSysDictDataList = systemApiService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = systemApiService.selectDictDataByType("material_standard");
        for(WzchSourceDetail detail : wzchSourceDetails){
            List<Object> list=new ArrayList<>();
            list.add(detail.getMaterialCode());
            list.add(detail.getMaterialName());
            list.add(detail.getMaterialSpec());
            list.add(detail.getMaterialTechParam());
            if(CollectionUtils.isNotEmpty(mSysDictDataList) && StringUtils.isNotBlank(detail.getMaterialStandard())){
                mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getMaterialStandard()))
                        .findFirst().ifPresent(val ->  list.add(val.getDictLabel()));
            }else{
                list.add(detail.getMaterialStandard());
            }
            list.add(detail.getUnit());
            list.add(detail.getTotalDemandAmount());
            list.add(detail.getSelfDemandAmount());
            if(CollectionUtils.isNotEmpty(tSysDictDataList) && StringUtils.isNotBlank(detail.getCategoryName())){
                tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getCategoryName()))
                        .findFirst().ifPresent(val ->  list.add(val.getDictLabel()));
            }else{
                list.add(detail.getCategoryName());
            }

            List<WzchSourceApproachYearCount> yearCountList = detail.getWzchSourceApproachYearCountList();

           for(String year:yesrs){
               for(WzchSourceApproachYearCount yearCount:yearCountList){
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

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        list.add(head4);
        list.add(head5);
        list.add(head6);
        list.add(head7);
        list.add(head8);

        List<String> productHead= new ArrayList<>();
        productHead.add("国内采购");
        productHead.add("第三国采购");
        productHead.add("当地采购");
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


    public List<WzchSourceDetail> exportData(WzchSourceDetailExportRequest request) {
        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectBySourceIdAndIdList(request);
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            throw new BaseException("导出数据异常");
        }
        List<Long> detailIds = wzchSourceDetails.stream().map(WzchSourceDetail::getId).collect(Collectors.toList());
        List<WzchSourceApproachYearCount> yearCounts = wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountByDetailIds(detailIds);
        if (CollectionUtils.isEmpty(yearCounts)) {
            return wzchSourceDetails;
        }
       // annotationToField( yearCounts ); 修改 实体类注解值
        Map<Long, List<WzchSourceApproachYearCount>> map = yearCounts.stream().collect(Collectors.groupingBy(WzchSourceApproachYearCount::getDetailId));
        for (WzchSourceDetail wzchSourceDetail : wzchSourceDetails) {
            if(wzchSourceDetail==null){
                continue;
            }
            for(Map.Entry<Long, List<WzchSourceApproachYearCount>> entry :map.entrySet()){
                if(wzchSourceDetail.getId().equals(entry.getKey())){
                    wzchSourceDetail.setWzchSourceApproachYearCountList(entry.getValue());
                }
            }

        }
        return wzchSourceDetails;
    }

    private void annotationToField(List<WzchSourceApproachYearCount> yearCounts ) {
        for (WzchSourceApproachYearCount yearCount : yearCounts) {

                getExcelProperty(  yearCount , "internalNum","国内采购");
                getExcelProperty(  yearCount , "otherStateNum","第三国采购");
                getExcelProperty(  yearCount , "localNum","当地采购");
                getExcelProperty(  yearCount , "rentNum","租赁数量");
                getExcelProperty(  yearCount , "innerAdjustNum","内部调剂");
        }
    }


    public void getExcelProperty( WzchSourceApproachYearCount yearCount ,String fieldStr,String value){
        try{
            Field field = yearCount.getClass().getDeclaredField(fieldStr);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            java.lang.reflect.InvocationHandler handler = Proxy.getInvocationHandler(excelProperty);
            Field hField = handler.getClass().getDeclaredField("memberValues");
            hField.setAccessible(true);
            Map memberValues = (Map) hField.get(handler);
            memberValues.put("value", new String[]{yearCount.getYear(),value});
            field.setAccessible(true);
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            System.out.println(property.value());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    /**
     * 封装WzchSourceDetailList
     * @param sourceId
     * @return
     */
    public List<WzchSourceDetail> querryWzchSourceDetailList(Long sourceId){
        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailBySourceId(sourceId);
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            throw new CustomBusinessException("编辑失败");
        }
        List<Long> detailIds = wzchSourceDetails.stream().map(WzchSourceDetail::getSourceId).collect(Collectors.toList());
        List<WzchSourceApproachYearCount> yearCounts =  wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountByDetailIds(detailIds);
        if(CollectionUtils.isEmpty(yearCounts)){
            throw new CustomBusinessException("编辑失败");
        }
        //根据详情Id分组
        Map<Long, List<WzchSourceApproachYearCount>> collect = yearCounts.stream().collect(Collectors.groupingBy(WzchSourceApproachYearCount::getDetailId));
        for(WzchSourceDetail wzchSourceDetail:wzchSourceDetails){
            for(Map.Entry<Long, List<WzchSourceApproachYearCount>> map : collect.entrySet()){
                if(wzchSourceDetail.getId().equals(map.getKey())){
                    wzchSourceDetail.setWzchSourceApproachYearCountList(map.getValue());
                }
            }
        }
        return wzchSourceDetails;
    }

    public static void main(String[] args) throws ParseException, JsonProcessingException {
        WzchSource wzchSource = new WzchSource();
        wzchSource.setId(Long.parseLong("1234"));
        wzchSource.setCreateTime(new Date());
        System.out.println(new ObjectMapper().writeValueAsString(wzchSource));
    }
}
