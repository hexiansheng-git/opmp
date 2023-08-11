package com.hhwy.pm.qqch.tax.qqchTaxIn.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:35
 * @remark
 */
public interface QqchTaxInDetailMapper {

    QqchTaxInDetail getQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    List<QqchTaxInDetail> getQqchTaxInDetailList(QqchTaxInDetail qqchTaxInDetail);

    int insertQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    int insertQqchTaxInDetailList(@Param("qqchTaxInDetailList") List<QqchTaxInDetail> qqchTaxInDetailList);

    int updateQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    int updateQqchTaxInDetailList(@Param("qqchTaxInDetailList") List<QqchTaxInDetail> qqchTaxInDetailList);

    int deleteQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    int deleteQqchTaxInDetailByPks(@Param("qqchTaxInDetailPkList") List<Long> qqchTaxInDetailPkList);
}
