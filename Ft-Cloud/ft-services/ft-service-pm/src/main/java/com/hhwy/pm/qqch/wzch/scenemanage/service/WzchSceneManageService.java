package com.hhwy.pm.qqch.wzch.scenemanage.service;

import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManage;
import com.hhwy.pm.qqch.wzch.scenemanage.dto.WzchSceneManageDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 现场物资管控策划(WzchSceneManage)表服务接口
 *
 * @author makejava
 * @since 2022-12-18 11:24:40
 */
public interface WzchSceneManageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WzchSceneManage selectWzchSceneManageById(Long id);

    /**
     * 新增数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    long insert(WzchSceneManageDTO dto);

    /**
     * 修改数据
     *
     * @param wzchSceneManage 实例对象
     * @return 实例对象
     */
    WzchSceneManage update(WzchSceneManage wzchSceneManage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<WzchSceneManage> selectWzchSceneManageList(WzchSceneManage wzchSceneManage);

    /**
     * 新增 编辑 详情查询数据
     *
     * @param dto
     * @return
     */
    public WzchSceneManageDTO baseInfo(WzchSceneManageDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    long edit(WzchSceneManageDTO dto);


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(String ids);

}
