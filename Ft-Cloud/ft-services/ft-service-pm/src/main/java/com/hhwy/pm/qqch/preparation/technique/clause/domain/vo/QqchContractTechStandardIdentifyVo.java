package com.hhwy.pm.qqch.preparation.technique.clause.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Data
public class QqchContractTechStandardIdentifyVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：合同执行技术标准识别集合
     */
    private List<QqchContractTechStandardIdentify> treeList;
}
