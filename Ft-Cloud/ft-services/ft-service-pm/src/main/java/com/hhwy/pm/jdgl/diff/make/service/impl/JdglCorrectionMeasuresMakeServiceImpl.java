package com.hhwy.pm.jdgl.diff.make.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.mapper.JdglCorrectionMeasuresMakeMapper;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
@Service
public class JdglCorrectionMeasuresMakeServiceImpl implements IJdglCorrectionMeasuresMakeService {

    @Autowired
    private JdglCorrectionMeasuresMakeMapper jdglCorrectionMeasuresMakeMapper;
    @Autowired
    private IJdglCorrectionMeasuresMakeDetailService jdglCorrectionMeasuresMakeDetailService;

    /**
     * 查询单条数据-详情
     *
     * @param JdglCorrectionMeasuresMake
     * @return
     */
    public JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMake(
        JdglCorrectionMeasuresMake JdglCorrectionMeasuresMake) {
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper
            .getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake);
        if (make != null) {
            List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMakeDetailService
                .getDetailListByMakeId(make.getId());
            make.setDetailList(TreeUtil.build(detailList, null));
        }
        return make;
    }

    /**
     * 列表
     *
     * @param jdglCorrectionMeasuresMake
     * @return
     */
    public List<JdglCorrectionMeasuresMake> getJdglCorrectionMeasuresMakeList(
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        return jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMake);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        jdglCorrectionMeasuresMake.setId(IdWorker.createId());
        jdglCorrectionMeasuresMake.setCreateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList) {
        for (JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake : jdglCorrectionMeasuresMakeList) {
            jdglCorrectionMeasuresMake.setId(IdWorker.createId());
            jdglCorrectionMeasuresMake.setCreateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeList);
    }

    /**
     * 更新保存
     *
     * @param jdglCorrectionMeasuresMake
     * @return
     */
    @Transactional
    public void updateJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        if (jdglCorrectionMeasuresMake == null || jdglCorrectionMeasuresMake.getId() == null) {
            return;
        }
        jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMake.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            // 树转列表
            List<JdglCorrectionMeasuresMakeDetail> treeList = TreeUtil.treeToList(detailList);
            // 执行更改下操作
            jdglCorrectionMeasuresMakeDetailService.updateJdglCorrectionMeasuresMakeDetailList(treeList);
        }

    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList) {
        for (JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake : jdglCorrectionMeasuresMakeList) {
            jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeList);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMakeByPks(List<Long> jdglCorrectionMeasuresMakePkList) {
        return jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMakeByPks(jdglCorrectionMeasuresMakePkList);
    }
}
