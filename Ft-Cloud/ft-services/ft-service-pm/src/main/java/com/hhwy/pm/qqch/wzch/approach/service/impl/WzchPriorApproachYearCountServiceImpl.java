package com.hhwy.pm.qqch.wzch.approach.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachYearCount;
import com.hhwy.pm.qqch.wzch.approach.mapper.WzchPriorApproachYearCountMapper;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachYearCountService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优先进场物资详情年份数据Service业务层处理
 * 
 * @author mls
 * @date 2022-11-30
 */
@Service
public class WzchPriorApproachYearCountServiceImpl implements IWzchPriorApproachYearCountService {
    @Autowired
    private WzchPriorApproachYearCountMapper wzchPriorApproachYearCountMapper;

    /**
     * 查询优先进场物资详情年份数据
     * 
     * @param id 优先进场物资详情年份数据ID
     * @return 优先进场物资详情年份数据
     */
    @Override
    public WzchPriorApproachYearCount selectWzchPriorApproachYearCountById(Long id) {
        return wzchPriorApproachYearCountMapper.selectWzchPriorApproachYearCountById(id);
    }

    /**
     * 查询优先进场物资详情年份数据列表
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 优先进场物资详情年份数据
     */
    @Override
    public List<WzchPriorApproachYearCount> selectWzchPriorApproachYearCountList(WzchPriorApproachYearCount wzchPriorApproachYearCount) {
        return wzchPriorApproachYearCountMapper.selectWzchPriorApproachYearCountList(wzchPriorApproachYearCount);
    }

    /**
     * 新增优先进场物资详情年份数据
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 结果
     */
    @Override
    public int insertWzchPriorApproachYearCount(WzchPriorApproachYearCount wzchPriorApproachYearCount) {

    wzchPriorApproachYearCount.setId(IdWorker.createId());

        wzchPriorApproachYearCount.setCreateTime(DateUtils.getNowDate());

        return wzchPriorApproachYearCountMapper.insertWzchPriorApproachYearCount(wzchPriorApproachYearCount);
    }

    /**
     * 修改优先进场物资详情年份数据
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 结果
     */
    @Override
    public int updateWzchPriorApproachYearCount(WzchPriorApproachYearCount wzchPriorApproachYearCount) {
        wzchPriorApproachYearCount.setUpdateTime(DateUtils.getNowDate());
        return wzchPriorApproachYearCountMapper.updateWzchPriorApproachYearCount(wzchPriorApproachYearCount);
    }

    /**
     * 删除优先进场物资详情年份数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachYearCountByIds(String ids) {
        return wzchPriorApproachYearCountMapper.deleteWzchPriorApproachYearCountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优先进场物资详情年份数据信息
     * 
     * @param id 优先进场物资详情年份数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorApproachYearCountById(Long id) {
        return wzchPriorApproachYearCountMapper.deleteWzchPriorApproachYearCountById(id);
    }

    @Override
    public List<WzchPriorApproachYearCount> selectByDetailIds(List<Long> detailIds) {
        if(CollectionUtils.isEmpty(detailIds)){
            throw new BaseException("入参缺失");
        }
        return wzchPriorApproachYearCountMapper.selectByDetailIds(detailIds);
    }

    @Override
    public int deleteWzchPriorApproachYearCountByDetailIds(List<Long> detailIds) {
        if (CollectionUtils.isEmpty(detailIds)) {
            throw new BaseException("入参缺失");
        }
       return wzchPriorApproachYearCountMapper.deleteWzchPriorApproachYearCountByDetailIds(detailIds);
    }
    @Override
    public int deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BaseException("入参缺失");
        }
       return wzchPriorApproachYearCountMapper.deleteByIds(ids);
    }

    @Override
    public int deleteByDetails(List<Long> detailIds) {
        if (CollectionUtils.isEmpty(detailIds)) {
            throw new BaseException("入参缺失");
        }
        return wzchPriorApproachYearCountMapper.deleteByDetails(detailIds);

    }

    @Override
    public int batchInsert(List<WzchPriorApproachYearCount> wzchPriorApproachYearCounts) {
        if(CollectionUtils.isEmpty(wzchPriorApproachYearCounts)){
            throw new BaseException("入参缺失");
        }
        return wzchPriorApproachYearCountMapper.batchInsert(wzchPriorApproachYearCounts);
    }
}
