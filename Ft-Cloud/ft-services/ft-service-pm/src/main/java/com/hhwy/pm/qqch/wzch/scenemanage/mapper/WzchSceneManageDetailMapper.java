package com.hhwy.pm.qqch.wzch.scenemanage.mapper;

import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 现场物资管控策划详情(WzchSceneManageDetail)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-18 11:24:42
 */
public interface WzchSceneManageDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WzchSceneManageDetail queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wzchSceneManageDetail 查询条件
     * @return 对象列表
     */
    List<WzchSceneManageDetail> selectDetailList(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 统计总行数
     *
     * @param wzchSceneManageDetail 查询条件
     * @return 总行数
     */
    long count(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 新增数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 影响行数
     */
    int insert(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WzchSceneManageDetail> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WzchSceneManageDetail> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WzchSceneManageDetail> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchSceneManageDetail> entities);

    /**
     * 修改数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 影响行数
     */
    int update(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int deleteBySceneId(@Param("sceneId") Long sceneId);
}

