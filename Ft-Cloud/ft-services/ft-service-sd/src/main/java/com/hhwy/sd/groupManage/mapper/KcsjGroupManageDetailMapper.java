package com.hhwy.sd.groupManage.mapper;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:13
 * @remark
 */
public interface KcsjGroupManageDetailMapper {

    KcsjGroupManageDetail getKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    List<KcsjGroupManageDetail> getKcsjGroupManageDetailList(KcsjGroupManageDetail kcsjGroupManageDetail);

    int insertKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int insertKcsjGroupManageDetailList(@Param("kcsjGroupManageDetailList") List<KcsjGroupManageDetail> kcsjGroupManageDetailList);

    int updateKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int updateKcsjGroupManageDetailList(@Param("list") List<KcsjGroupManageDetail> kcsjGroupManageDetailList);

    int deleteKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail);

    int deleteKcsjGroupManageDetailByPks(@Param("kcsjGroupManageDetailPkList") List<Long> kcsjGroupManageDetailPkList);
}
