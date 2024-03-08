package com.hhwy.sd.organManage.service;

import com.hhwy.sd.organManage.domain.KcsjOrganManage;
import com.hhwy.sd.organManage.domain.KcsjOrganManage4Update;

import java.util.Date;
import java.util.List;

/**
 * @author cjh
 * @date 2023-12-14 11:31:44
 * @remark
 */
public interface IKcsjOrganManageService {

    KcsjOrganManage getKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    List<KcsjOrganManage> getKcsjOrganManageList(KcsjOrganManage kcsjOrganManage);

    int insertKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int insertKcsjOrganManageList(List<KcsjOrganManage> kcsjOrganManageList);

    int updateKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int updateKcsjOrganManage(Long id, Date enterDate, Date leaveDate);

    int updateKcsjOrganManageList(List<KcsjOrganManage> kcsjOrganManageList);

    int deleteKcsjOrganManage(KcsjOrganManage kcsjOrganManage);

    int deleteKcsjOrganManageByPks(List<Long> kcsjOrganManagePkList);

    void sync();

    /**
     * lcf
     *
     * @param kcsjOrganManage4Update
     * @return
     */
    int newUpdateKcsjOrganManageList(KcsjOrganManage4Update kcsjOrganManage4Update);

    /**
     * 数据同步总部
     *
     * @param kcsjOrganManage4Update
     */
    void syncDataToGm(KcsjOrganManage4Update kcsjOrganManage4Update);
}
