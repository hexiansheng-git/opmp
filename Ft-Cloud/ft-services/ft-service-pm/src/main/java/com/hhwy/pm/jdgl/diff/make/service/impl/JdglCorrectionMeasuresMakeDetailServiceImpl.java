package com.hhwy.pm.jdgl.diff.make.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.mapper.JdglCorrectionMeasuresMakeDetailMapper;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark 纠偏措施制定详情
 */
@Service
public class JdglCorrectionMeasuresMakeDetailServiceImpl implements IJdglCorrectionMeasuresMakeDetailService {

    @Autowired
    private JdglCorrectionMeasuresMakeDetailMapper jdglCorrectionMeasuresMakeDetailMapper;

    public JdglCorrectionMeasuresMakeDetail getJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        return jdglCorrectionMeasuresMakeDetailMapper
            .getJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    public List<JdglCorrectionMeasuresMakeDetail> getJdglCorrectionMeasuresMakeDetailList(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        return jdglCorrectionMeasuresMakeDetailMapper.getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        jdglCorrectionMeasuresMakeDetail.setId(IdWorker.createId());
        jdglCorrectionMeasuresMakeDetail.setCreateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMakeDetail.setCreateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper
            .insertJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList) {
        for (JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail : jdglCorrectionMeasuresMakeDetailList) {
//            jdglCorrectionMeasuresMakeDetail.setId(IdWorker.createId());
//            jdglCorrectionMeasuresMakeDetail.setCreateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMakeDetail.setCreateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeDetailMapper
            .insertJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailList);
    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper
            .updateJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList) {
        for (JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail : jdglCorrectionMeasuresMakeDetailList) {
            jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeDetailMapper
            .updateJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailList);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper
            .deleteJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMakeDetailByPks(List<Long> jdglCorrectionMeasuresMakeDetailPkList) {
        return jdglCorrectionMeasuresMakeDetailMapper
            .deleteJdglCorrectionMeasuresMakeDetailByPks(jdglCorrectionMeasuresMakeDetailPkList);
    }

    @Override
    public List<JdglCorrectionMeasuresMakeDetail> getDetailListByMakeId(JdglCorrectionMeasuresMake make) {
        Long userId = SecurityUtils.getUserId();
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail = new JdglCorrectionMeasuresMakeDetail();
        jdglCorrectionMeasuresMakeDetail.setMakeId(make.getId());
        //只能查看、编辑自己负责的数据，除非当前记录流程已结束
        if (StrUtil.isNotBlank(make.getTaskStatus()) &&  !make.getTaskStatus().equals("5")) {
            jdglCorrectionMeasuresMakeDetail.setDirectorId(String.valueOf(userId));
        }
        return jdglCorrectionMeasuresMakeDetailMapper.getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetail);
    }
}
