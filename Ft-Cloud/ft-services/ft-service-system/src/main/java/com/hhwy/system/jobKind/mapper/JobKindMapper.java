package com.hhwy.system.jobKind.mapper;

import com.hhwy.domain.base.system.jobKind.JobKind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * infoMapper接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface JobKindMapper {
    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    JobKind selectJobKindById(Long id);

    /**
     * 查询info列表
     * 
     * @param JobKind info
     * @return info集合
     */
    List<JobKind> selectJobKindList(JobKind JobKind);

    /**
     * 新增info
     * 
     * @param JobKind info
     * @return 结果
     */
    int insertJobKind(JobKind JobKind);

    /**
     * 修改info
     * 
     * @param JobKind info
     * @return 结果
     */
    int updateJobKind(JobKind JobKind);

    /**
     * 删除info
     * 
     * @param id infoID
     * @return 结果
     */
    int deleteJobKindById(Long id);

    /**
     * 全部删除
     *
     * @return 结果
     */
    int deleteJobKindAll();

    /**
     * 批量删除info
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJobKindByIds(String[] ids);

    /**
     * 根据currencyCode判断是否重复
     *
     * @param info
     * @return
     */
    List<JobKind> validRepeat(JobKind info);

    /**
     * 排序号最大
     *
     * @return
     */
    Integer selectMaxSort();

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param(value = "dataList") List<JobKind> list);
}
