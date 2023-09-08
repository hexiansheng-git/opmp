package com.hhwy.pm.jdgl.diff.make.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
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
        List<JdglCorrectionMeasuresMake> list =
            jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMake);
        if (CollectionUtils.isNotEmpty(list)) {
            for (JdglCorrectionMeasuresMake make : list) {
                String tenantKey = SecurityUtils.getTenantKey();
                FtActBusiness flowInfo = FlowInfoSearchUtil
                    .getFlowInfo(FlowEnum.JDGL_CORRECTION_MEASURES_MAKE.getTableName(), String.valueOf(make.getId()),
                        tenantKey);
                make.setTaskStatus(flowInfo.getName());
            }
        }
        return list;
    }

    /**
     * 新增保存
     *
     * @param jdglCorrectionMeasuresMake
     */
    @Transactional
    public void insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        jdglCorrectionMeasuresMake.setId(IdWorker.createId());
        jdglCorrectionMeasuresMake.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        jdglCorrectionMeasuresMake.setCreateUserName(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMake.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            for (JdglCorrectionMeasuresMakeDetail detail : detailList) {
                detail.setId(IdWorker.createId());
                detail.setMakeId(jdglCorrectionMeasuresMake.getId());
                detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                detail.setCreateUserName(SecurityUtils.getUserName());
                detail.setCreateTime(DateUtils.getNowDate());
            }
            jdglCorrectionMeasuresMakeDetailService.insertJdglCorrectionMeasuresMakeDetailList(detailList);
        }
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
            List<JdglCorrectionMeasuresMakeDetail> treeList = TreeUtil.treeToListWithoutId(detailList);
            for (JdglCorrectionMeasuresMakeDetail detail : treeList) {
                detail.setMakeId(jdglCorrectionMeasuresMake.getId());
                detail.setUpdateUser(SecurityUtils.getUserName());
                detail.setUpdateTime(DateUtils.getNowDate());
            }

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

    /**
     * 批量删除
     *
     * @param jdglCorrectionMeasuresMakePkList
     * @return
     */
    @Transactional
    public int deleteJdglCorrectionMeasuresMakeByPks(List<Long> jdglCorrectionMeasuresMakePkList) {
        return jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMakeByPks(jdglCorrectionMeasuresMakePkList);
    }
}
