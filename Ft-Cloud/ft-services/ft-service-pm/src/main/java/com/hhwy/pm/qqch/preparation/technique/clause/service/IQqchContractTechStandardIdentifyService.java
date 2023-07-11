package com.hhwy.pm.qqch.preparation.technique.clause.service;

import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.utils.tree.TreeVO;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
public interface IQqchContractTechStandardIdentifyService {

    int deleteQqchContractTechStandardIdentifyByPks(List<Long> qqchContractTechStandardIdentifyPkList);

    List<? extends TreeVO> getTreeList(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    void batchSave(List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList);
}
