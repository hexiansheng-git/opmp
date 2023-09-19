package com.hhwy.pm.qqch.wzch.scenemanage.mapper;

import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 现场物资管控策划(WzchSceneManage)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-18 11:24:32
 */
public interface WzchSceneManageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WzchSceneManage selectWzchSceneManageById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wzchSceneManage 查询条件
     * @return 对象列表
     */
    List<WzchSceneManage> selectWzchSceneManageList(WzchSceneManage wzchSceneManage);

    /**
     * 新增数据
     *
     * @param wzchSceneManage 实例对象
     * @return 影响行数
     */
    int insert(WzchSceneManage wzchSceneManage);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WzchSceneManage> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WzchSceneManage> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WzchSceneManage> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchSceneManage> entities);

    /**
     * 修改数据
     *
     * @param wzchSceneManage 实例对象
     * @return 影响行数
     */
    int update(WzchSceneManage wzchSceneManage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int deleteDetailsBySceneIds(String[] ids);

    int deleteByIds(String[] ids);
}

