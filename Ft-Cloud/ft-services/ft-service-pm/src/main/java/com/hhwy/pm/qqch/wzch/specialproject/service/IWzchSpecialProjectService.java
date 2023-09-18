package com.hhwy.pm.qqch.wzch.specialproject.service;

import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProject;
import com.hhwy.pm.qqch.wzch.specialproject.dto.WzchSpecialProjectDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 专项物资策划Service接口
 *
 * @author mls
 * @date 2022-12-11
 */
public interface IWzchSpecialProjectService {
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
     * 批量删除专项物资策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialProjectByIds(String ids);

    /**
     * 删除专项物资策划信息
     *
     * @param id 专项物资策划ID
     * @return 结果
     */
    int deleteWzchSpecialProjectById(Long id);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    long insert(WzchSpecialProjectDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    long edit(WzchSpecialProjectDTO dto);

    /**
     * 调整
     *
     * @param dto
     * @return
     */
    long adjust(WzchSpecialProjectDTO dto);

    long save(WzchSpecialProjectDTO dto);
    
    /**
     * 进入新增  编辑 调整页面时候查询数据
     *
     * @param dto
     * @return
     */
    WzchSpecialProjectDTO baseInfo(WzchSpecialProjectDTO dto);

    int updateValidStatus(String businessId);
}
