package com.hhwy.pm.qqch.preparation.measureexp.beton.service;

import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo.QqchExpBetonVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:49
 * @remark 3.7.5混凝土配合比
 */
public interface IQqchExpBetonService {

    /**
     * 树列表
     * @param version
     * @return
     */
    QqchExpBetonVo getTreeList(BigDecimal version);

    /**
     * 保存/确认/提交
     * @param qqchExpBetonVo
     */
    void batchSave(QqchExpBetonVo qqchExpBetonVo);
}
