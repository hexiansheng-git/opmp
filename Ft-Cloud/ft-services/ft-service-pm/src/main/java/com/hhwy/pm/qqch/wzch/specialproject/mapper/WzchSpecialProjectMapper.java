package com.hhwy.pm.qqch.wzch.specialproject.mapper;


import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProject;

import java.util.List;

/**
 * 专项物资策划Mapper接口
 *
 * @author mls
 * @date 2022-12-11
 */
public interface WzchSpecialProjectMapper {
    /**
     * 查询专项物资策划
     *
     * @param id 专项物资策划ID
     * @return 专项物资策划
     */
    WzchSpecialProject selectWzchSpecialProjectById(Long id);

    /**
     * 查询专项物资策划列表
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 专项物资策划集合
     */
    List<WzchSpecialProject> selectWzchSpecialProjectList(WzchSpecialProject wzchSpecialProject);

    /**
     * 新增专项物资策划
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 结果
     */
    int insertWzchSpecialProject(WzchSpecialProject wzchSpecialProject);

    /**
     * 修改专项物资策划
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 结果
     */
    int updateWzchSpecialProject(WzchSpecialProject wzchSpecialProject);

    /**
     * 删除专项物资策划
     *
     * @param id 专项物资策划ID
     * @return 结果
     */
    int deleteWzchSpecialProjectById(Long id);

    /**
     * 批量删除专项物资策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialProjectByIds(String[] ids);

    int deleteDetailsBySpecialProjectIds(String[] ids);
}
