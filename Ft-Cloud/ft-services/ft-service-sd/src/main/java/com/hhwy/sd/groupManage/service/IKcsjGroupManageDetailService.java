package com.hhwy.sd.groupManage.service;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageDetail;

import java.util.List;


/**
 * @author han
 * @date 2023-12-13 15:27:13
 * @remark
 */
public interface IKcsjGroupManageDetailService {

    KcsjGroupManageDetail getKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    List<KcsjGroupManageDetail> getKcsjGroupManageDetailList(KcsjGroupManageDetail kcsjGroupManageDetail);

    int insertKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int insertKcsjGroupManageDetailList(List<KcsjGroupManageDetail> kcsjGroupManageDetailList);

    int updateKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int updateKcsjGroupManageDetailList(List<KcsjGroupManageDetail> kcsjGroupManageDetailList);

    int deleteKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int deleteKcsjGroupManageDetailByPks(List<Long> kcsjGroupManageDetailPkList);
}
