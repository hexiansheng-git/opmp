package com.hhwy.pm.qqch.wzch.source.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;
import com.hhwy.pm.qqch.wzch.source.mapper.WzchSourceApproachYearCountMapper;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceApproachYearCountService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 来源策划物资详情年份数据,优先进场物资详情年份数据Service业务层处理
 * 
 * @author mls
 * @date 2022-11-21
 */
@Service
public class WzchSourceApproachYearCountServiceImpl implements IWzchSourceApproachYearCountService {
    @Autowired
    private WzchSourceApproachYearCountMapper wzchSourceApproachYearCountMapper;

    /**
     * 查询来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param id 来源策划物资详情年份数据,优先进场物资详情年份数据ID
     * @return 来源策划物资详情年份数据,优先进场物资详情年份数据
     */
    @Override
    public WzchSourceApproachYearCount selectWzchSourceApproachYearCountById(Long id) {
        return wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountById(id);
    }

    /**
     * 查询来源策划物资详情年份数据,优先进场物资详情年份数据列表
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 来源策划物资详情年份数据,优先进场物资详情年份数据
     */
    @Override
    public List<WzchSourceApproachYearCount> selectWzchSourceApproachYearCountList(WzchSourceApproachYearCount wzchSourceApproachYearCount) {
        return wzchSourceApproachYearCountMapper.selectWzchSourceApproachYearCountList(wzchSourceApproachYearCount);
    }

    /**
     * 新增来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 结果
     */
    @Override
    public int insertWzchSourceApproachYearCount(WzchSourceApproachYearCount wzchSourceApproachYearCount) {

    wzchSourceApproachYearCount.setId(IdWorker.createId());

        wzchSourceApproachYearCount.setCreateTime(DateUtils.getNowDate());

        return wzchSourceApproachYearCountMapper.insertWzchSourceApproachYearCount(wzchSourceApproachYearCount);
    }

    /**
     * 修改来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 结果
     */
    @Override
    public int updateWzchSourceApproachYearCount(WzchSourceApproachYearCount wzchSourceApproachYearCount) {
        wzchSourceApproachYearCount.setUpdateTime(DateUtils.getNowDate());
        return wzchSourceApproachYearCountMapper.updateWzchSourceApproachYearCount(wzchSourceApproachYearCount);
    }

    /**
     * 删除来源策划物资详情年份数据,优先进场物资详情年份数据信息
     * 
     * @param id 来源策划物资详情年份数据,优先进场物资详情年份数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSourceApproachYearCountById(Long id) {
        return wzchSourceApproachYearCountMapper.deleteWzchSourceApproachYearCountById(id);
    }
}
