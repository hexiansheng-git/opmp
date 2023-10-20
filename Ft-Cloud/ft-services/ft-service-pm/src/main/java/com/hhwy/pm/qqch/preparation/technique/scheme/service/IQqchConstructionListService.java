package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
public interface IQqchConstructionListService {

    QqchConstructionListVo getQqchConstructionListList(QqchConstructionListVo qqchConstructionListParamVo);
    
    List<QqchConstructionList> list(QqchConstructionList list);

    /**
     * 获取最新的施工方案清单数据
     * @return
     */
    List<QqchConstructionList> getLatest();

    void batchSave(QqchConstructionListVo qqchConstructionListVo);

    List<QqchConstructionList> getByWbsCodes(String[] wbsCodes);
    
    void importData(List<QqchConstructionList> list, BigDecimal version);

    /**
     * 获取施工方案清单中危大等级为危大、超危大的方案数据
     * @return
     */
    List<QqchConstructionList> getBigDangerLevelConstructionList();
}
