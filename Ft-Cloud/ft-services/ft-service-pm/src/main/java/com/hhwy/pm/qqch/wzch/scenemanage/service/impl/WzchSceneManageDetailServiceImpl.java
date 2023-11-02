package com.hhwy.pm.qqch.wzch.scenemanage.service.impl;

import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;
import com.hhwy.pm.qqch.wzch.scenemanage.mapper.WzchSceneManageDetailMapper;
import com.hhwy.pm.qqch.wzch.scenemanage.service.WzchSceneManageDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 现场物资管控策划详情(WzchSceneManageDetail)表服务实现类
 *
 * @author makejava
 * @since 2022-12-18 11:24:46
 */
@Service("wzchSceneManageDetailService")
public class WzchSceneManageDetailServiceImpl implements WzchSceneManageDetailService {
    @Resource
    private WzchSceneManageDetailMapper wzchSceneManageDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WzchSceneManageDetail selectDetailById(Long id) {
        return this.wzchSceneManageDetailDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wzchSceneManageDetail 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WzchSceneManageDetail> selectDetailList(WzchSceneManageDetail wzchSceneManageDetail) {
        return this.wzchSceneManageDetailDao.selectDetailList(wzchSceneManageDetail);
    }

    /**
     * 新增数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 实例对象
     */
    @Override
    public WzchSceneManageDetail insert(WzchSceneManageDetail wzchSceneManageDetail) {
        this.wzchSceneManageDetailDao.insert(wzchSceneManageDetail);
        return wzchSceneManageDetail;
    }

    /**
     * 修改数据
     *
     * @param wzchSceneManageDetail 实例对象
     * @return 实例对象
     */
    @Override
    public WzchSceneManageDetail update(WzchSceneManageDetail wzchSceneManageDetail) {
        this.wzchSceneManageDetailDao.update(wzchSceneManageDetail);
        return this.selectDetailById(wzchSceneManageDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wzchSceneManageDetailDao.deleteById(id) > 0;
    }

    @Override
    public int insertOrUpdateBatch(List<WzchSceneManageDetail> detailList, Long sceneId) {

        Assert.notNull(sceneId, "资金策划id不能为空");

        this.wzchSceneManageDetailDao.deleteBySceneId(sceneId);
        if (CollectionUtils.isEmpty(detailList)) {
            return 0;
        }
        List<WzchSceneManageDetail> collect = detailList.stream().map(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
            item.setSceneManageId(sceneId);
            EntityUtils.setCreateUpdateInfo(item);
            return item;
        }).collect(Collectors.toList());


        return this.wzchSceneManageDetailDao.insertOrUpdateBatch(detailList);
    }
}
