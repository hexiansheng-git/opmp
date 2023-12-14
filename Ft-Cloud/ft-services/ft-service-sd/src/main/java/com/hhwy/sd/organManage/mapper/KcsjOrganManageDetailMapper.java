package com.hhwy.sd.organManage.mapper;

import java.util.List;

import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author cjh
 * @date 2023-12-14 11:31:53
 * @remark
 */
public interface KcsjOrganManageDetailMapper {

    KcsjOrganManageDetail getKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    List<KcsjOrganManageDetail> getKcsjOrganManageDetailList(KcsjOrganManageDetail kcsjOrganManageDetail);

    int insertKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int insertKcsjOrganManageDetailList(@Param("kcsjOrganManageDetailList") List<KcsjOrganManageDetail> kcsjOrganManageDetailList);

    int updateKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int updateKcsjOrganManageDetailList(@Param("list") List<KcsjOrganManageDetail> kcsjOrganManageDetailList);

    int deleteKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail);

    int deleteKcsjOrganManageDetailByOrganManageId(@Param("organManageId") Long organManageId);

    int deleteKcsjOrganManageDetailByPks(@Param("kcsjOrganManageDetailPkList") List<Long> kcsjOrganManageDetailPkList);

    void deleteKcsjOrganManageDetail4All();
}
