package com.hhwy.pm.qqch.wzch.source.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceApproachYearCountMapper;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceDetailMapper;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceMapper;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.pm.qqch.wzch.source.vo.ProjectOfChangeInfoRequest;
import com.hhwy.pm.qqch.wzch.source.vo.ReminderOfChangeResponse;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 来源策划Service业务层处理
 * 
 * @author mls
 * @date 2022-11-21
 */
@Service
public class WzchSourceServiceImpl implements IWzchSourceService {
    @Resource
    private WzchSourceMapper wzchSourceMapper;
    @Resource
    private WzchSourceDetailMapper wzchSourceDetailMapper;
    @Resource
    private WzchSourceApproachYearCountMapper wzchSourceApproachYearCountMapper;
    @Resource
    private IWzchTotalDemandService wzchTotalDemandService;
    @Resource
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IQqchReviewService qqchReviewService;

    /**
     * 查询来源策划
     * 
     * @param id 来源策划ID
     * @return 来源策划
     */
    @Override
    public WzchSource selectWzchSourceById(Long id) {
        return wzchSourceMapper.selectWzchSourceById(id);
    }

    /**
     * 查询来源策划列表
     * 
     * @param wzchSource 来源策划
     * @return 来源策划
     */
    @Override
//    @CustomDatascope(alias = "wzch_source")
    public List<WzchSource> selectWzchSourceList(WzchSource wzchSource) {
        return wzchSourceMapper.selectWzchSourceList(wzchSource);
    }

    @Override
    public WzchSource selectWzchSourceByVersion(BigDecimal version) {
        return wzchSourceMapper.selectWzchSourceByVersion(version);
    }

    /**
     * 新增来源策划9
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    @Override
    public int insertWzchSource(WzchSource wzchSource) {

        wzchSource.setCreateTime(DateUtils.getNowDate());

        return wzchSourceMapper.insertWzchSource(wzchSource);
    }

    /**
     * 修改来源策划
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    @Override
    public int updateWzchSource(WzchSource wzchSource) {
        wzchSource.setUpdateTime(DateUtils.getNowDate());
        return wzchSourceMapper.updateWzchSource(wzchSource);
    }

    /**
     * 删除来源策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSourceByIds(String ids) {
        return wzchSourceMapper.deleteWzchSourceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除来源策划信息
     * 
     * @param id 来源策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchSourceById(Long id) {
        return wzchSourceMapper.deleteWzchSourceById(id);
    }

    @Override
    public WzchSource detail(WzchSourceTotalDemandVO vo) {
//        WzchSource wzchSource = wzchSourceMapper.selectWzchSourceById(id);
//        if(wzchSource==null){
//            throw new BaseException("未查询到数据");
//        }
        WzchSource wzchSource = new WzchSource();
        BigDecimal version = VersionUtil.getVersion("wzch_source_detail", vo.getVersion());
        wzchSource.setVersion(version);
        wzchSource.setStageIdentity(qqchReviewService.getStage());
        if(version == null){
            wzchSource.setWzchSourceDetailList(new ArrayList<>(2));    
            return wzchSource;
        }
        List<WzchSource> list = wzchSourceMapper.selectWzchSourceList(wzchSource);
        if(CollectionUtils.isEmpty(list)){
            wzchSource.setWzchSourceDetailList(new ArrayList<>(2));
            return wzchSource;
        }
        wzchSource = list.get(0);
        wzchSource.setVersion(version);
        wzchSource.setStageIdentity(qqchReviewService.getStage());
        List<WzchSourceDetail> wzchSourceDetails = queryWzchSourceDetailList(version);
//        wzchSourceDetails = wzchSourceDetails.stream().sorted(Comparator.comparing(WzchSourceDetail::getMaterialCode)).collect(Collectors.toList());
        wzchSource.setWzchSourceDetailList(wzchSourceDetails);
        return wzchSource;
    }

    public  List<WzchSourceTotalDemandVO> reminderOfChange2() {
        List<WzchSource> wzchSources = wzchSourceMapper.selectProjectIdAndDemandVersion();
        if(CollectionUtils.isEmpty(wzchSources)){
            return new ArrayList<>();
        }

        List<Long> projectIds = wzchSources.stream().map(WzchSource::getProjectId).collect(Collectors.toList());
        List<WzchTotalDemand> wzchTotalDemands =  wzchTotalDemandService.selectWzchTotalDemandsByProjectIds(projectIds);
        if (CollectionUtils.isEmpty(wzchTotalDemands)) {
            return new ArrayList<>();
        }
        //根据项目ID分组
        Map<Long, List<WzchTotalDemand>> map = wzchTotalDemands.stream().collect(Collectors.groupingBy(WzchTotalDemand::getProjectId));
        List<WzchSourceTotalDemandVO> wzchSourceTotalDemandVOS = new ArrayList<>();
        for (WzchSource wzchSource : wzchSources) {
            for(Map.Entry<Long, List<WzchTotalDemand>> entry : map.entrySet()){
                if (wzchSource.getProjectId().equals(entry.getKey())) {
                    List<WzchTotalDemand> demands = entry.getValue();
                    List<WzchTotalDemand> demandList = demands.stream().filter(d -> d.getVersionCode().equals(wzchSource.getDemandVersion())).collect(Collectors.toList());
                    if(CollectionUtils.isEmpty(demandList)){
                         break;

                    }
                    WzchTotalDemand wzchTotalDemand = demandList.get(0);
                    //如果有效
                    if("1".equals(wzchTotalDemand.getValid())){
                       break;
                    }
                    if("0".equals(wzchTotalDemand.getValid())){
                        WzchSourceTotalDemandVO wzchSourceTotalDemandVO = new WzchSourceTotalDemandVO();
                       // 找出最新有效版本
                        WzchTotalDemand demand = demands.stream().max(Comparator.comparing(WzchTotalDemand::getVersionCode)).get();
                        wzchSourceTotalDemandVO.setVersionCode(demand.getVersionCode());
                        wzchSourceTotalDemandVO.setProjectName(demand.getProjectName());
                        wzchSourceTotalDemandVO.setValidDate(demand.getUpdateTime());
                        wzchSourceTotalDemandVO.setNewId(demand.getId());
                        //找出最新有效版本
                        String backVersion = new BigDecimal(demand.getVersionCode()).subtract(new BigDecimal("1.0")).toString();
                        WzchTotalDemand backDemand = demands.stream().filter(d -> d.getVersionCode().equals(backVersion) && "0".equals(d.getValid())).collect(Collectors.toList()).get(0);
                        wzchSourceTotalDemandVO.setBackId(backDemand.getId());
                        wzchSourceTotalDemandVOS.add(wzchSourceTotalDemandVO);
                    }

                }
            }

        }
        if(CollectionUtils.isEmpty(wzchSourceTotalDemandVOS)){
            return null;
        }

        List<Long> totalDemandIds = wzchSourceTotalDemandVOS.stream().map(WzchSourceTotalDemandVO::getNewId).collect(Collectors.toList());
        totalDemandIds.addAll(wzchSourceTotalDemandVOS.stream().map(WzchSourceTotalDemandVO::getBackId).collect(Collectors.toList()));
        List<WzchTotalDemandDetail> wzchTotalDemandDetails =  wzchTotalDemandDetailService.selectDetailByTotalDemandIds(totalDemandIds);
        for(WzchSourceTotalDemandVO wzchSourceTotalDemandVO :wzchSourceTotalDemandVOS){
            List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOList = new ArrayList<>();
            WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
            Date validDate = null;
            for(WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails){
                if(wzchSourceTotalDemandVO.getNewId().equals(wzchTotalDemandDetail.getTotalDemandId())){
                    BeanUtils.copyProperties(wzchTotalDemandDetail,wzchSourceTotalDemandDetailVO);
                }
                if(wzchSourceTotalDemandVO.getBackId().equals(wzchTotalDemandDetail.getTotalDemandId())){
                    wzchSourceTotalDemandDetailVO.setBackNonSelfAmount(wzchTotalDemandDetail.getNonSelfAmount());
                    wzchSourceTotalDemandDetailVO.setBackSelfDemandAmount(wzchTotalDemandDetail.getSelfDemandAmount());
                    wzchSourceTotalDemandDetailVO.setBackTotalDemandAmount(wzchTotalDemandDetail.getTotalDemandAmount());
                }
                validDate= wzchTotalDemandDetail.getValidDate();
                wzchSourceTotalDemandDetailVOList.add(wzchSourceTotalDemandDetailVO);
            }
            wzchSourceTotalDemandVO.setValidDate(validDate);
            wzchSourceTotalDemandVO.setWzchSourceTotalDemandDetailVOList(wzchSourceTotalDemandDetailVOList);
        }
        //通过无效的版本项目ID获取最先版本和上一版本的数据
        return wzchSourceTotalDemandVOS;
    }

    @Override
    public List<ReminderOfChangeResponse> reminderOfChange() {
        List<WzchSource> wzchSources = this.selectWzchSourceList(new WzchSource());
        if (CollectionUtils.isEmpty(wzchSources)) {
            return new ArrayList<>();
        }
        List<ReminderOfChangeResponse> reminderOfChangeResponses = new ArrayList<>();
        wzchSources.forEach(w ->{
            if(StringUtils.isNotBlank(w.getDemandNewVersion()) && StringUtils.isNotBlank(w.getDemandVersion())
                    && !w.getDemandNewVersion().equals(w.getDemandVersion()) ){
                ReminderOfChangeResponse reminderOfChangeResponse = new ReminderOfChangeResponse();
                reminderOfChangeResponse.setProjectId(w.getProjectId());
                reminderOfChangeResponse.setProjectName(w.getProjectName());
                reminderOfChangeResponse.setDemandNewVersion(w.getDemandNewVersion());
                reminderOfChangeResponse.setDemandValidDate(w.getDemandValidDate());
                reminderOfChangeResponses.add(reminderOfChangeResponse);
            }
        });
        return reminderOfChangeResponses;
    }

    //@Override
    public List<WzchSourceTotalDemandDetailVO> projectOfChangeInfo2(ProjectOfChangeInfoRequest request) {
        if (request==null) {
            throw new BaseException("入参缺失");
        }
        List<Long> wzchTotalDemandIds = wzchTotalDemandService.selectIdsByProjectIdAndVersionCodes(request.getProjectId(),Arrays.asList(request.getDemandNewVersion()));
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectDetailByTotalDemandIds(wzchTotalDemandIds);
        if(CollectionUtils.isEmpty(wzchTotalDemandDetails)){
            throw new BaseException("查询数据失败");
        }
        //根据版本分组
        Map<String, List<WzchTotalDemandDetail>> map = wzchTotalDemandDetails.stream().collect(Collectors.groupingBy(WzchTotalDemandDetail::getValid));
        //最新版本数据
        List<WzchTotalDemandDetail> newVersionDetails = map.get("1");
        if(CollectionUtils.isEmpty(newVersionDetails)){
            throw new BaseException("未获取到最新版本数据");
        }
        List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = new ArrayList<>();
        List<WzchTotalDemandDetail> editVersionDetails = new ArrayList<>();
        List<WzchTotalDemandDetail> backVersionDetails = map.get("0");
        for (WzchTotalDemandDetail newVersionDetail : newVersionDetails) {
            WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
            BeanUtils.copyProperties(newVersionDetail,wzchSourceTotalDemandDetailVO);
            int a = 0;
            Iterator<WzchTotalDemandDetail> iterator = backVersionDetails.iterator();
            while(iterator.hasNext()){
                WzchTotalDemandDetail backVersionDetail = iterator.next();
                if(newVersionDetail.getMaterialCode().equals(backVersionDetail.getMaterialCode()) && newVersionDetail.getMaterialStandard().equals(backVersionDetail.getMaterialStandard())){
                    //修改数据
                    if(Optional.ofNullable(newVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)))!=0
                            || Optional.ofNullable(newVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)))!=0
                            || Optional.ofNullable(newVersionDetail.getNonSelfAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getNonSelfAmount()).orElse(new BigDecimal(0)))!=0 ){
                        wzchSourceTotalDemandDetailVO.setBackTotalDemandAmount(backVersionDetail.getTotalDemandAmount());
                        wzchSourceTotalDemandDetailVO.setBackSelfDemandAmount(backVersionDetail.getSelfDemandAmount());
                        wzchSourceTotalDemandDetailVO.setBackNonSelfAmount(backVersionDetail.getNonSelfAmount());
                        wzchSourceTotalDemandDetailVO.setHandleType("2");
                        wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
                        a=1;
                    }else{
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
        //已删除数据
        if(CollectionUtils.isNotEmpty(backVersionDetails)){
            List<WzchSourceDetail> removeWzchSourceDetail = new ArrayList<>();
            for (WzchTotalDemandDetail backVersionDetail : backVersionDetails) {
                WzchSourceDetail sourceDetail = new WzchSourceDetail();
                sourceDetail.setMaterialCode(backVersionDetail.getMaterialCode());
                sourceDetail.setMaterialStandard(backVersionDetail.getMaterialStandard());
                removeWzchSourceDetail.add(sourceDetail);
            }
            if(CollectionUtils.isEmpty(removeWzchSourceDetail)){
                wzchCommonService.setWzchtMaterialInfo(wzchSourceTotalDemandDetailVOS);
                return wzchSourceTotalDemandDetailVOS;
            }
            List<WzchSourceDetail> wzchSources = wzchSourceDetailMapper.selectByMaterialCodeAndMaterialStandard(removeWzchSourceDetail);
            for (WzchTotalDemandDetail backVersionDetail : backVersionDetails) {
                WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
                BeanUtils.copyProperties(backVersionDetail,wzchSourceTotalDemandDetailVO);
                wzchSourceTotalDemandDetailVO.setHandleType("1");
                wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
            }
        }
        wzchCommonService.setWzchtMaterialInfo(wzchSourceTotalDemandDetailVOS);
        return wzchSourceTotalDemandDetailVOS;
    }

    @Override
    public List<WzchSourceTotalDemandDetailVO> projectOfChangeInfo(ProjectOfChangeInfoRequest request) {
        if (request==null) {
            throw new BaseException("入参缺失");
        }
        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandService.selectWzchTotalDemandList(new WzchTotalDemand(request.getDemandNewVersion(), request.getProjectId()));
        if(CollectionUtils.isEmpty(wzchTotalDemands)){
            throw new BaseException("查询物资总需失败");
        }
        WzchTotalDemand wzchTotalDemand = wzchTotalDemands.get(0);
        List<WzchTotalDemandDetail> demandNewVersionDetailList = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(wzchTotalDemand.getId()));
        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailList(new WzchSourceDetail(request.getProjectId()));
        List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = new ArrayList<>();
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            return wzchSourceTotalDemandDetailVOS;
        }
        for (WzchTotalDemandDetail newVersionDetail : demandNewVersionDetailList) {
            WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
            BeanUtils.copyProperties(newVersionDetail,wzchSourceTotalDemandDetailVO);
            int a = 0;
            Iterator<WzchSourceDetail> iterator = wzchSourceDetails.iterator();
            while(iterator.hasNext()){
                WzchSourceDetail backVersionDetail = iterator.next();
                if(newVersionDetail.getMaterialCode().equals(backVersionDetail.getMaterialCode()) && newVersionDetail.getMaterialStandard().equals(backVersionDetail.getMaterialStandard())){
                    //修改数据
                    if(Optional.ofNullable(newVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getTotalDemandAmount()).orElse(new BigDecimal(0)))!=0
                            || Optional.ofNullable(newVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)).compareTo(Optional.ofNullable(backVersionDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)))!=0){
                        wzchSourceTotalDemandDetailVO.setBackTotalDemandAmount(backVersionDetail.getTotalDemandAmount());
                        wzchSourceTotalDemandDetailVO.setBackSelfDemandAmount(backVersionDetail.getSelfDemandAmount());
                        if(backVersionDetail.getTotalDemandAmount()!=null && backVersionDetail.getSelfDemandAmount()!=null){
                            BigDecimal subtract = backVersionDetail.getTotalDemandAmount().subtract(backVersionDetail.getSelfDemandAmount());
                            wzchSourceTotalDemandDetailVO.setBackNonSelfAmount(subtract);
                        }
                        wzchSourceTotalDemandDetailVO.setHandleType("2");
                        wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
                        a=1;
                    }else{
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
        //已删除数据
        if(CollectionUtils.isNotEmpty(wzchSourceDetails)){
            for (WzchSourceDetail backVersionDetail : wzchSourceDetails) {
                WzchSourceTotalDemandDetailVO wzchSourceTotalDemandDetailVO = new WzchSourceTotalDemandDetailVO();
                wzchSourceTotalDemandDetailVO.setMaterialCode(backVersionDetail.getMaterialCode());
                wzchSourceTotalDemandDetailVO.setMaterialStandard(backVersionDetail.getMaterialStandard());
                wzchSourceTotalDemandDetailVO.setHandleType("1");
                wzchSourceTotalDemandDetailVOS.add(wzchSourceTotalDemandDetailVO);
            }
        }
        if(CollectionUtils.isNotEmpty(wzchSourceTotalDemandDetailVOS)){
            wzchCommonService.setWzchtMaterialInfo(wzchSourceTotalDemandDetailVOS);
            wzchSourceTotalDemandDetailVOS = wzchSourceTotalDemandDetailVOS.stream().sorted(Comparator.comparing(WzchSourceTotalDemandDetailVO::getMaterialCode)).collect(Collectors.toList());
        }
        return wzchSourceTotalDemandDetailVOS;
    }

    @Override
    @Transactional
    public boolean remove(String id) {
        if(StringUtils.isBlank(id)){
            throw new BaseException("入参缺失");
        }
        long sourceId = Long.parseLong(id);
        WzchSource wzchSource = wzchSourceMapper.selectWzchSourceById(sourceId);
        if(wzchSource==null){
            throw new BaseException("删除失败，数据不存在");
        }
        wzchSourceMapper.deleteWzchSourceById(wzchSource.getId());

        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailBySourceId(wzchSource.getId());
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            return true;
        }
        List<Long> detailIds = wzchSourceDetails.stream().map(WzchSourceDetail::getId).collect(Collectors.toList());
        wzchSourceDetailMapper.deleteWzchSourceDetailByIds(detailIds);
        List<WzchSourceApproachYearCount> yearCounts = wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountByDetailIds(detailIds);
        if(CollectionUtils.isEmpty(yearCounts)){
            return true;
        }
        List<Long> yearCountsIds = yearCounts.stream().map(WzchSourceApproachYearCount::getId).collect(Collectors.toList());
        wzchSourceApproachYearCountMapper.deleteWzchSourceApproachYearCountByIds(yearCountsIds);
        return false;
    }

    /**
     * 封装WzchSourceDetailList
     * @param version
     * @return
     */
    public List<WzchSourceDetail> queryWzchSourceDetailList(BigDecimal version){
        WzchSourceDetail queryDetail = new WzchSourceDetail();
        queryDetail.setVersion(version);
        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailList(queryDetail);
//        List<WzchSourceDetail> wzchSourceDetails = wzchSourceDetailMapper.selectWzchSourceDetailBySourceId(sourceId);
        if(CollectionUtils.isEmpty(wzchSourceDetails)){
            return new ArrayList<>(2);
        }
        List<Long> detailIds = wzchSourceDetails.stream().map(WzchSourceDetail::getId).collect(Collectors.toList());
        List<WzchSourceApproachYearCount> yearCounts =  wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountByDetailIds(detailIds);
        if(CollectionUtils.isEmpty(yearCounts)){
            yearCounts = new ArrayList<>(2);
        }
        List<String> yearList = yearCounts.stream().map(WzchSourceApproachYearCount::getYear).collect(Collectors.toList());
         yearList = yearList.stream().distinct().sorted().collect(Collectors.toList());
        //根据详情Id分组
        Map<Long, List<WzchSourceApproachYearCount>> collect = yearCounts.stream().collect(Collectors.groupingBy(WzchSourceApproachYearCount::getDetailId));
        for(WzchSourceDetail wzchSourceDetail:wzchSourceDetails){
            for(Map.Entry<Long, List<WzchSourceApproachYearCount>> map : collect.entrySet()){
                if(wzchSourceDetail.getId().equals(map.getKey())){
                    //按照年份排序
                    map.getValue().sort((r,r1)->{
                        Integer y1 = Integer.parseInt(r.getYear());
                        Integer y2 = Integer.parseInt(r1.getYear());
                        return y1==y2?0:(y1>y2?1:-1);
                    });
                    wzchSourceDetail.setWzchSourceApproachYearCountList(map.getValue());
                }
            }
            wzchSourceDetail.setYearList(yearList);
        }
        wzchCommonService.setWzchtMaterialInfo(wzchSourceDetails);
        return wzchSourceDetails;
    }
}
