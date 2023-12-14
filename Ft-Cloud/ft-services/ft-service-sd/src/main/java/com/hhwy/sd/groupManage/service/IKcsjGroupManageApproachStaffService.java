package com.hhwy.sd.groupManage.service;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageApproachStaff;

import java.util.List;


/**
 * @author han
 * @date 2023-12-13 17:37:16
 * @remark
 */
public interface IKcsjGroupManageApproachStaffService {

    KcsjGroupManageApproachStaff getKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    List<KcsjGroupManageApproachStaff> getKcsjGroupManageApproachStaffList(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int insertKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int insertKcsjGroupManageApproachStaffList(List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList);

    int updateKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int updateKcsjGroupManageApproachStaffList(List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList);

    int deleteKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int deleteKcsjGroupManageApproachStaffByPks(List<Long> kcsjGroupManageApproachStaffPkList);
}
