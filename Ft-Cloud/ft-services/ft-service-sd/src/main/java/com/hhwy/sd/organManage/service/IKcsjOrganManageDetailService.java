package com.hhwy.sd.organManage.service;

import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail;
import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail4Update;

import java.util.List;

/**
 * @author cjh
 * @date 2023-12-14 11:31:53
 * @remark
 */
public interface IKcsjOrganManageDetailService {

    KcsjOrganManageDetail getKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    List<KcsjOrganManageDetail> getKcsjOrganManageDetailList(KcsjOrganManageDetail kcsjOrganManageDetail);

    int insertKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int insertKcsjOrganManageDetailList(List<KcsjOrganManageDetail> kcsjOrganManageDetailList);

    int updateKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int updateKcsjOrganManageDetailList(List<KcsjOrganManageDetail> kcsjOrganManageDetailList);

    int updateKcsjOrganManageDetailList(KcsjOrganManageDetail4Update kcsjOrganManageDetail4Update);

    int deleteKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int deleteKcsjOrganManageDetailByPks(List<Long> kcsjOrganManageDetailPkList);

    void deleteKcsjOrganManageDetail4All();
}
