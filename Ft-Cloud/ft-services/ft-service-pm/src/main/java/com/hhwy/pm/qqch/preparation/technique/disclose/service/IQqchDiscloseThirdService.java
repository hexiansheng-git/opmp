package com.hhwy.pm.qqch.preparation.technique.disclose.service;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
public interface IQqchDiscloseThirdService {

    QqchDiscloseThirdVo getQqchDiscloseThirdList(BigDecimal version);

    /**
     * 获取交底明细
     * @param masterId 交底Id
     * @return
     */
    List<QqchDiscloseThirdDetail> getDetailList(Long masterId);

    @Deprecated
    void batchSave(QqchDiscloseThirdVo qqchDiscloseThirdVo);

    void saveData(QqchDiscloseThirdVo vo);

    /**
     * 保存
     * @param qqchDiscloseThirdVo
     */
    void save(QqchDiscloseThirdVo qqchDiscloseThirdVo);
}
