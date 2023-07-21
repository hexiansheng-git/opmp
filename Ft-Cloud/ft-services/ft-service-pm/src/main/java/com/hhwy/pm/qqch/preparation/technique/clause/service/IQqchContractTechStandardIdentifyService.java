package com.hhwy.pm.qqch.preparation.technique.clause.service;

import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechStandardIdentifyVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
public interface IQqchContractTechStandardIdentifyService {

    QqchContractTechStandardIdentifyVo getTreeList(BigDecimal version);

    void batchSave(QqchContractTechStandardIdentifyVo qqchContractTechStandardIdentifyVo);
}
