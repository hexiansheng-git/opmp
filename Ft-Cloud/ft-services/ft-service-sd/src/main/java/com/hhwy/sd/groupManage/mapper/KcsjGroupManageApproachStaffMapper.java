package com.hhwy.sd.groupManage.mapper;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageApproachStaff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 17:37:16
 * @remark
 */
@Repository
public interface KcsjGroupManageApproachStaffMapper {

    KcsjGroupManageApproachStaff getKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    List<KcsjGroupManageApproachStaff> getKcsjGroupManageApproachStaffList(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int insertKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int insertKcsjGroupManageApproachStaffList(@Param("kcsjGroupManageApproachStaffList") List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList);

    int updateKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int updateKcsjGroupManageApproachStaffList(@Param("kcsjGroupManageApproachStaffList") List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList);

    int deleteKcsjGroupManageApproachStaff(KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff);

    int deleteKcsjGroupManageApproachStaffByPks(@Param("kcsjGroupManageApproachStaffPkList") List<Long> kcsjGroupManageApproachStaffPkList);
}
