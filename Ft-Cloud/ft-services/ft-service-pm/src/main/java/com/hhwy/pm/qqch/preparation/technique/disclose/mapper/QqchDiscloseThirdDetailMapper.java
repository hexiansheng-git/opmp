package com.hhwy.pm.qqch.preparation.technique.disclose.mapper;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:47
 * @remark 3.5.2三级交底详情
 */
public interface QqchDiscloseThirdDetailMapper {

    QqchDiscloseThirdDetail getQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    List<QqchDiscloseThirdDetail> getQqchDiscloseThirdDetailList(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int insertQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int insertQqchDiscloseThirdDetailList(
        @Param("qqchDiscloseThirdDetailList") List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList);

    int updateQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int updateQqchDiscloseThirdDetailList(@Param("list") List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList);

    int deleteQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int deleteQqchDiscloseThirdDetailByPks(
        @Param("qqchDiscloseThirdDetailPkList") List<Long> qqchDiscloseThirdDetailPkList);
}
