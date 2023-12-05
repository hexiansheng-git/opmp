package com.hhwy.pm.qqch.preparation.technique.expert.service;

import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.vo.QqchTargetAdvisoryOrganVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark 外部目标咨询机构选择
 */
public interface IQqchTargetAdvisoryOrganService {

    QqchTargetAdvisoryOrgan getQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    List<QqchTargetAdvisoryOrgan> getQqchTargetAdvisoryOrganList(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int insertQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int updateQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int updateQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList);

    int deleteQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int deleteQqchTargetAdvisoryOrganByPks(List<Long> qqchTargetAdvisoryOrganPkList);

    /**
     * 获取外部目标咨询机构选择Vo
     * @param qqchTargetAdvisoryOrgan
     * @return
     */
    QqchTargetAdvisoryOrganVo getQqchTargetAdvisoryOrganVo(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    /**
     * 保存/确认/提交
     * @param qqchTargetAdvisoryOrganVo
     * @return
     */
    void save(QqchTargetAdvisoryOrganVo qqchTargetAdvisoryOrganVo);

    void addToQyzsEnquiryOrgLibrary(List<QqchTargetAdvisoryOrgan> list);
}
