package com.hhwy.pm.qqch.wzch.internaladjust.mapper;

import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterialRange;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 可调拨材料可调拨范围(WzchAllotMaterialRange)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-03 16:16:56
 */
public interface WzchAllotMaterialRangeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WzchAllotMaterialRange queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wzchAllotMaterialRange 查询条件
     * @return 对象列表
     */
    List<WzchAllotMaterialRange> selectList(WzchAllotMaterialRange wzchAllotMaterialRange);

    /**
     * 统计总行数
     *
     * @param wzchAllotMaterialRange 查询条件
     * @return 总行数
     */
    long count(WzchAllotMaterialRange wzchAllotMaterialRange);

    /**
     * 新增数据
     *
     * @param wzchAllotMaterialRange 实例对象
     * @return 影响行数
     */
    int insert(WzchAllotMaterialRange wzchAllotMaterialRange);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WzchAllotMaterialRange> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WzchAllotMaterialRange> entities);


    /**
     * 修改数据
     *
     * @param wzchAllotMaterialRange 实例对象
     * @return 影响行数
     */
    int update(WzchAllotMaterialRange wzchAllotMaterialRange);
    int updateByProjectId(WzchAllotMaterialRange wzchAllotMaterialRange);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

