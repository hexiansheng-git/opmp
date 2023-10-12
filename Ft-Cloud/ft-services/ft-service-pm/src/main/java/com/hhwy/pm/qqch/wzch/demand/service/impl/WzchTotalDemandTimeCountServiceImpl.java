package com.hhwy.pm.qqch.wzch.demand.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandTimeCountMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandTimeCountService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物资总需用详情时间数据Service业务层处理
 * 
 * @author mls
 * @date 2022-11-16
 */
@Service
public class WzchTotalDemandTimeCountServiceImpl implements IWzchTotalDemandTimeCountService {
    @Autowired
    private WzchTotalDemandTimeCountMapper wzchTotalDemandTimeCountMapper;

    /**
     * 查询物资总需用详情时间数据
     * 
     * @param id 物资总需用详情时间数据ID
     * @return 物资总需用详情时间数据
     */
    @Override
    public WzchTotalDemandTimeCount selectWzchTotalDemandTimeCountById(Long id) {
        return wzchTotalDemandTimeCountMapper.selectWzchTotalDemandTimeCountById(id);
    }

    /**
     * 查询物资总需用详情时间数据列表
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 物资总需用详情时间数据
     */
    @Override
    public List<WzchTotalDemandTimeCount> selectWzchTotalDemandTimeCountList(WzchTotalDemandTimeCount wzchTotalDemandTimeCount) {
        return wzchTotalDemandTimeCountMapper.selectWzchTotalDemandTimeCountList(wzchTotalDemandTimeCount);
    }

    /**
     * 新增物资总需用详情时间数据
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 结果
     */
    @Override
    public int insertWzchTotalDemandTimeCount(WzchTotalDemandTimeCount wzchTotalDemandTimeCount) {

    wzchTotalDemandTimeCount.setId(IdWorker.createId());

        wzchTotalDemandTimeCount.setCreateTime(DateUtils.getNowDate());

        return wzchTotalDemandTimeCountMapper.insertWzchTotalDemandTimeCount(wzchTotalDemandTimeCount);
    }

    /**
     * 修改物资总需用详情时间数据
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 结果
     */
    @Override
    public int updateWzchTotalDemandTimeCount(WzchTotalDemandTimeCount wzchTotalDemandTimeCount) {
        wzchTotalDemandTimeCount.setUpdateTime(DateUtils.getNowDate());
        return wzchTotalDemandTimeCountMapper.updateWzchTotalDemandTimeCount(wzchTotalDemandTimeCount);
    }

    /**
     * 删除物资总需用详情时间数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandTimeCountByIds(List<Long> ids) {
        return wzchTotalDemandTimeCountMapper.deleteWzchTotalDemandTimeCountByIds(ids);
    }

    /**
     * 删除物资总需用详情时间数据信息
     * 
     * @param id 物资总需用详情时间数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandTimeCountById(Long id) {
        return wzchTotalDemandTimeCountMapper.deleteWzchTotalDemandTimeCountById(id);
    }

    @Override
    public int deleteByVersion(BigDecimal version) {
        if(version == null)
            return 0;
        return wzchTotalDemandTimeCountMapper.deleteByVersion(version);
    }

    @Override
    public List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIds(List<Long> totalDemandDetailIds) {
        return wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIds(totalDemandDetailIds);
    }

    @Override
    public List<WzchTotalDemandTimeCount> selectTotalDemandTimeCounts(List<Long> totalDemandDetailIds, String viewType) {
        return wzchTotalDemandTimeCountMapper.selectTotalDemandTimeCounts(totalDemandDetailIds,viewType);
    }

    @Override
    public int batchInsert(List<WzchTotalDemandTimeCount> list) {
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return wzchTotalDemandTimeCountMapper.batchInsert(list);
    }

    @Override
    public int batchUpdate(List<WzchTotalDemandTimeCount> fillWzchTotalDemandTimeCountList) {
        return wzchTotalDemandTimeCountMapper.batchUpdate(fillWzchTotalDemandTimeCountList);
    }

    @Override
    public int deleteWzchTotalDemandTimeCountByintTotalDemandDetailId(String[] idArr) {

        return 0;
    }

    @Override
    public int deleteByTotalDemandDetailIds(List<Long> detailIds) {
        return wzchTotalDemandTimeCountMapper.deleteByTotalDemandDetailIds(detailIds);
    }

    @Override
    public List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsAndYears(List<Long> detailIdList, List<String> countYears) {
        if(CollectionUtils.isEmpty(detailIdList) && CollectionUtils.isEmpty(countYears)){
            throw new BaseException("入参缺失");
        }
        return wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIdsAndYears(detailIdList,countYears);
    }

    @Override
    public List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsOfTotal(List<Long> detailIds) {
        return wzchTotalDemandTimeCountMapper.selectByTotalDemandDetailIdsOfTotal(detailIds);

    }
}
