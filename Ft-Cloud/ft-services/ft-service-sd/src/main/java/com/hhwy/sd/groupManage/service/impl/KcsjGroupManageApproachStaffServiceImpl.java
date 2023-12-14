package com.hhwy.sd.groupManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageApproachStaff;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageApproachStaffMapper;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageApproachStaffService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 17:37:16
 * @remark
 */
@Service
public class KcsjGroupManageApproachStaffServiceImpl implements IKcsjGroupManageApproachStaffService {

    @Autowired
    private KcsjGroupManageApproachStaffMapper kcsjGroupManageApproachStaffMapper;


    public KcsjGroupManageApproachStaff getKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff) {
        return kcsjGroupManageApproachStaffMapper.getKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaff);
    }

    public List<KcsjGroupManageApproachStaff> getKcsjGroupManageApproachStaffList(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff) {
        return kcsjGroupManageApproachStaffMapper.getKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaff);
    }

    @Transactional
    public int insertKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff) {
        kcsjGroupManageApproachStaff.setId(IdWorker.createId());
        kcsjGroupManageApproachStaff.setCreateUser(SecurityUtils.getUserName());
        kcsjGroupManageApproachStaff.setCreateTime(DateUtils.getNowDate());
        return kcsjGroupManageApproachStaffMapper.insertKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaff);
    }

    @Transactional
    public int insertKcsjGroupManageApproachStaffList(List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList) {
        for (KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff : kcsjGroupManageApproachStaffList) {
            kcsjGroupManageApproachStaff.setId(IdWorker.createId());
            kcsjGroupManageApproachStaff.setCreateUser(SecurityUtils.getUserName());
            kcsjGroupManageApproachStaff.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageApproachStaffMapper.insertKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffList);
    }

    @Transactional
    public int updateKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff) {
        kcsjGroupManageApproachStaff.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageApproachStaff.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageApproachStaffMapper.updateKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaff);
    }

    @Transactional
    public int updateKcsjGroupManageApproachStaffList(List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList) {
        for (KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff : kcsjGroupManageApproachStaffList) {
            kcsjGroupManageApproachStaff.setUpdateUser(SecurityUtils.getUserName());
            kcsjGroupManageApproachStaff.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageApproachStaffMapper.updateKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffList);
    }

    @Transactional
    public int deleteKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff) {
        kcsjGroupManageApproachStaff.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageApproachStaff.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageApproachStaffMapper.deleteKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaff);
    }

    @Transactional
    public int deleteKcsjGroupManageApproachStaffByPks(List<Long> kcsjGroupManageApproachStaffPkList) {
        return kcsjGroupManageApproachStaffMapper.deleteKcsjGroupManageApproachStaffByPks(kcsjGroupManageApproachStaffPkList);
    }
}
