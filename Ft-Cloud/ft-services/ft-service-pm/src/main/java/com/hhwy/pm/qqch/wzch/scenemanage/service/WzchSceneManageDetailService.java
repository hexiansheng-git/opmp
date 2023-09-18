package com.hhwy.pm.qqch.wzch.scenemanage.service;


import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;

import java.util.List;

/**
 * 现场物资管控策划详情(WzchSceneManageDetail)表服务接口
 *
 * @author makejava
 * @since 2022-12-18 11:24:44
 */
public interface WzchSceneManageDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WzchSceneManageDetail selectDetailById(Long id);

    /**
     * 分页查询
     *
     * @param wzchSceneManageDetail 筛选条件
     * @return 查询结果
     */
    List<WzchSceneManageDetail> selectDetailList(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 新增数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 实例对象
     */
    WzchSceneManageDetail insert(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 修改数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 实例对象
     */
    WzchSceneManageDetail update(WzchSceneManageDetail wzchSceneManageDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量新增或者更新
     *
     * @param detailList
     * @param sceneId
     * @return
     */
    int insertOrUpdateBatch(List<WzchSceneManageDetail> detailList, Long sceneId);

}
