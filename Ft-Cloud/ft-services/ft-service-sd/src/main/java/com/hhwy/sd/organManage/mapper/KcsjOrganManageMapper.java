package com.hhwy.sd.organManage.mapper;

import java.util.List;

import com.hhwy.sd.organManage.domain.KcsjOrganManage;
import org.apache.ibatis.annotations.Param;

/**
 * @author cjh
 * @date 2023-12-14 11:31:44
 * @remark
 */
public interface KcsjOrganManageMapper {

    KcsjOrganManage getKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    List<KcsjOrganManage> getKcsjOrganManageList(KcsjOrganManage kcsjOrganManage);

    int insertKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int insertKcsjOrganManageList(@Param("kcsjOrganManageList") List<KcsjOrganManage> kcsjOrganManageList);

    int updateKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int updateKcsjOrganManageList(@Param("list") List<KcsjOrganManage> kcsjOrganManageList);

    int deleteKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int deleteKcsjOrganManageByPks(@Param("kcsjOrganManagePkList") List<Long> kcsjOrganManagePkList);

    void deleteKcsjOrganManage4All();
}
