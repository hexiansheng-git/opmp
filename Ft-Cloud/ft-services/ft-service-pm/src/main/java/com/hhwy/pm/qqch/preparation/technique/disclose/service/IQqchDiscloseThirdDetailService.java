package com.hhwy.pm.qqch.preparation.technique.disclose.service;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:47
 * @remark 3.5.2三级交底详情
 */
public interface IQqchDiscloseThirdDetailService {

    QqchDiscloseThirdDetail getQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    List<QqchDiscloseThirdDetail> getQqchDiscloseThirdDetailList(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int insertQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int insertQqchDiscloseThirdDetailList(List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList);

    int updateQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int updateQqchDiscloseThirdDetailList(List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList);

    int deleteQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail);

    int deleteQqchDiscloseThirdDetailByPks(List<Long> qqchDiscloseThirdDetailPkList);
}
