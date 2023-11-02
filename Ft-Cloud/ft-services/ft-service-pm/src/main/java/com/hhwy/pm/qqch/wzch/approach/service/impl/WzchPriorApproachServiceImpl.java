package com.hhwy.pm.qqch.wzch.approach.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachDetail;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachYearCount;
import com.hhwy.pm.qqch.wzch.approach.mapper.WzchPriorApproachMapper;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachDetailService;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachService;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachYearCountService;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachExportRequest;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandTimeCountMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 优先进场物资Service业务层处理
 * 
 * @author mls
 * @date 2022-11-28
 */
@Service
public class WzchPriorApproachServiceImpl implements IWzchPriorApproachService {
    @Autowired
    private WzchPriorApproachMapper wzchPriorApproachMapper;
    @Resource
    private IWzchPriorApproachDetailService wzchPriorApproachDetailService;
    @Resource
    private IWzchPriorApproachYearCountService wzchPriorApproachYearCountService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;
    @Resource
    private WzchTotalDemandTimeCountMapper wzchTotalDemandTimeCountMapper;
    @Resource
    private IQqchReviewService qqchReviewService;

    /**
     * 查询优先进场物资
     * 
     * @param id 优先进场物资ID
     * @return 优先进场物资
     */
    @Override
    public WzchPriorApproach selectWzchPriorApproachById(Long id) {
        return wzchPriorApproachMapper.selectWzchPriorApproachById(id);
    }

    /**
     * 查询优先进场物资列表
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 优先进场物资
     */
    @Override
//    @CustomDatascope(alias = "wzch_prior_approach")
    public List<WzchPriorApproach> selectWzchPriorApproachList(WzchPriorApproach wzchPriorApproach) {
        return wzchPriorApproachMapper.selectWzchPriorApproachList(wzchPriorApproach);
    }

    /**
     * 新增优先进场物资
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 结果
     */
    @Override
    public int insertWzchPriorApproach(WzchPriorApproach wzchPriorApproach) {

        wzchPriorApproach.setCreateTime(DateUtils.getNowDate());

        return wzchPriorApproachMapper.insertWzchPriorApproach(wzchPriorApproach);
    }

    /**
     * 修改优先进场物资
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 结果
     */
    @Override
    public int updateWzchPriorApproach(WzchPriorApproach wzchPriorApproach) {
        wzchPriorApproach.setUpdateTime(DateUtils.getNowDate());
        return wzchPriorApproachMapper.updateWzchPriorApproach(wzchPriorApproach);
    }

    @Override
    @Transactional
    public void sync(BigDecimal version) {
        this.wzchPriorApproachDetailService.deleteDirectByVersion(version);
        this.wzchPriorApproachDetailService.deleteYearDirectByVersion(version);
        //1、根据版本号从总需用、来源策划获取优先进场物资
        WzchTotalDemandDetail query = new WzchTotalDemandDetail("","1", null);
        query.setVersion(version);
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailService.selectWzchTotalDemandDetailList(query);
        if(CollectionUtils.isEmpty(wzchTotalDemandDetails))
            return ;
        List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> timeCounts = wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIds(detailIds);
        if(CollectionUtils.isEmpty(timeCounts))
            throw new BaseException("获取数据异常");
        //2、转换为优先进场
        List<WzchPriorApproachDetail> addList = new ArrayList<>();
        List<WzchPriorApproachYearCount> addDetailList = new ArrayList<>();
        for (int i = 0; i < wzchTotalDemandDetails.size(); i++) {
            WzchTotalDemandDetail temp = wzchTotalDemandDetails.get(i);
            WzchPriorApproachDetail tempApp = new WzchPriorApproachDetail();
            BeanUtils.copyProperties(temp,tempApp);
            new AddBaseInfoUtil<>().addBaseEntity(tempApp);
            addList.add(tempApp);
        }
        for (int i = 0; i < timeCounts.size(); i++) {
            WzchTotalDemandTimeCount temp = timeCounts.get(i);
            WzchPriorApproachYearCount tempYear = new WzchPriorApproachYearCount();
            tempYear.setDetailId(temp.getTotalDemandDetailId());
            tempYear.setId(IdWorker.createId());
            tempYear.setYear(temp.getYear());
            tempYear.setVersion(version);
            new AddBaseInfoUtil<>().addBaseEntity(tempYear);
            addDetailList.add(tempYear);
        }
        //3、删除当前版本数据，插入
        wzchPriorApproachDetailService.batchInsert(addList);
        wzchPriorApproachYearCountService.batchInsert(addDetailList);
    }

    /**
     * 删除优先进场物资对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachByIds(String ids) {
        return wzchPriorApproachMapper.deleteWzchPriorApproachByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优先进场物资信息
     * 
     * @param id 优先进场物资ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachById(Long id) {
        return wzchPriorApproachMapper.deleteWzchPriorApproachById(id);
    }

    @Override
    public List<WzchPriorApproach> exportData(WzchPriorApproachExportRequest request) {
        if(request==null){
            return wzchPriorApproachMapper.selectByIdsAndTitleAndProjectNameAndRegionName(new WzchPriorApproachExportRequest());
        }
        return wzchPriorApproachMapper.selectByIdsAndTitleAndProjectNameAndRegionName(request);
    }

    @Override
    @Transactional
    public boolean remove(WzchPriorApproach wzchPriorApproach) {
        if(wzchPriorApproach==null||wzchPriorApproach.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchPriorApproach approach = wzchPriorApproachMapper.selectWzchPriorApproachById(wzchPriorApproach.getId());
        if(approach==null){
            throw new BaseException("删除失败，数据不存在");
        }
        wzchPriorApproachMapper.deleteWzchPriorApproachById(approach.getId());

        List<WzchPriorApproachDetail> wzchPriorApproachDetails = wzchPriorApproachDetailService.selectWzchPriorApproachDetailList(new WzchPriorApproachDetail(approach.getId()));
        if(CollectionUtils.isEmpty(wzchPriorApproachDetails)){
            return true;
        }
        List<Long> detailIds = wzchPriorApproachDetails.stream().map(WzchPriorApproachDetail::getId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(detailIds)){
            return true;
        }
        wzchPriorApproachDetailService.deleteWzchPriorApproachDetailByIds(detailIds);
        wzchPriorApproachYearCountService.deleteWzchPriorApproachYearCountByDetailIds(detailIds);
        return true;
    }

    @Override
    public WzchPriorApproach edit(Long id) {
        if(id ==null){
            throw new BaseException("编辑失败，入参为空");
        }
        WzchPriorApproach wzchPriorApproach = wzchPriorApproachMapper.selectWzchPriorApproachById(id);
        if(wzchPriorApproach==null){
            throw new BaseException("编辑失败，数据不存在！");
        }
        List<WzchPriorApproachDetail> wzchPriorApproachDetails = wzchPriorApproachDetailService.selectWzchPriorApproachDetailList(new WzchPriorApproachDetail(id));
        if(CollectionUtils.isEmpty(wzchPriorApproachDetails)){
            throw new BaseException("编辑失败，数据缺失！");
        }
        List<Long> detailIds = wzchPriorApproachDetails.stream().map(WzchPriorApproachDetail::getId).collect(Collectors.toList());

        List<WzchPriorApproachYearCount> wzchPriorApproachYearCounts =  wzchPriorApproachYearCountService.selectByDetailIds(detailIds);
        List<String> years = wzchPriorApproachYearCounts.stream().map(WzchPriorApproachYearCount::getYear).collect(Collectors.toList());
        years = years.stream().distinct().sorted().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(wzchPriorApproachYearCounts)){
            throw new BaseException("编辑失败，数据缺失！");
        }
        Map<Long, List<WzchPriorApproachYearCount>> map = wzchPriorApproachYearCounts.stream().collect(Collectors.groupingBy(WzchPriorApproachYearCount::getDetailId));
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproachDetails) {
            if(wzchPriorApproachDetail==null){
                throw new BaseException("编辑失败，数据缺失！");
            }
            wzchPriorApproachDetail.setYearList(years);
            for(Map.Entry<Long, List<WzchPriorApproachYearCount>> entry:map.entrySet()){
                if(entry.getKey().equals(wzchPriorApproachDetail.getId())){
                    wzchPriorApproachDetail.setWzchPriorApproachYearCountList(entry.getValue());
                }
            }
        }
        wzchCommonService.setWzchtMaterialInfo(wzchPriorApproachDetails);
        wzchPriorApproachDetails = wzchPriorApproachDetails.stream().sorted(Comparator.comparing(WzchPriorApproachDetail::getMaterialCode)).collect(Collectors.toList());
        wzchPriorApproach.setWzchPriorApproachDetailList(wzchPriorApproachDetails);
        return wzchPriorApproach;
    }

    @Override
    public WzchPriorApproach detail(WzchPriorApproach approach) {
        BigDecimal version = VersionUtil.getVersion("wzch_prior_approach_detail", approach.getVersion());
        approach.setVersion(version);
        approach.setStageIdentity(qqchReviewService.getStage());

        WzchPriorApproachDetail queryDetail = new WzchPriorApproachDetail();
        queryDetail.setVersion(version);
        List<WzchPriorApproachDetail> wzchPriorApproachDetails = wzchPriorApproachDetailService.selectWzchPriorApproachDetailList(queryDetail);
        if(CollectionUtils.isEmpty(wzchPriorApproachDetails)){
            approach.setWzchPriorApproachDetailList(wzchPriorApproachDetails);
            return approach;
        }
        List<Long> detailIds = wzchPriorApproachDetails.stream().map(WzchPriorApproachDetail::getId).collect(Collectors.toList());

        List<WzchPriorApproachYearCount> wzchPriorApproachYearCounts =  wzchPriorApproachYearCountService.selectByDetailIds(detailIds);
        List<String> years = wzchPriorApproachYearCounts.stream().map(WzchPriorApproachYearCount::getYear).collect(Collectors.toList());
        years = years.stream().distinct().sorted().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(wzchPriorApproachYearCounts)){
            throw new BaseException("数据缺失！");
        }
        Map<Long, List<WzchPriorApproachYearCount>> map = wzchPriorApproachYearCounts.stream().collect(Collectors.groupingBy(WzchPriorApproachYearCount::getDetailId));
        for (WzchPriorApproachDetail wzchPriorApproachDetail : wzchPriorApproachDetails) {
            if(wzchPriorApproachDetail==null){
                throw new BaseException("数据缺失！");
            }
            wzchPriorApproachDetail.setYearList(years);
            for(Map.Entry<Long, List<WzchPriorApproachYearCount>> entry:map.entrySet()){
                if(entry.getKey().equals(wzchPriorApproachDetail.getId())){
                    wzchPriorApproachDetail.setWzchPriorApproachYearCountList(entry.getValue());
                }
            }
        }
        wzchCommonService.setWzchtMaterialInfo(wzchPriorApproachDetails);
        wzchPriorApproachDetails = wzchPriorApproachDetails.stream().sorted(Comparator.comparing(WzchPriorApproachDetail::getMaterialCode)).collect(Collectors.toList());
        approach.setWzchPriorApproachDetailList(wzchPriorApproachDetails);
        return approach;
    }
}
