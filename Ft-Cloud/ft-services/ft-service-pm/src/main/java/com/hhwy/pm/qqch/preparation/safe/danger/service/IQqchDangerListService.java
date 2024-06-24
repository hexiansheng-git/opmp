package com.hhwy.pm.qqch.preparation.safe.danger.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
public interface IQqchDangerListService {

    /**
     * 列表
     *
     * @param version
     * @return
     */
    QqchDangerListVo getQqchDangerListList(BigDecimal version);

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerListVo
     * @return
     */
    void batchSave(QqchDangerListVo qqchDangerListVo);

    /**
     * 同步数据
     *
     * @return
     */
    void syncData(QqchDangerListVo qqchDangerListVo);

    //获取总部知识库危大工程清单
    AjaxResult getGmRiskBigProjList(QyzsSafeRiskBigProjItem qyzsSafeRiskBigProj);
}
