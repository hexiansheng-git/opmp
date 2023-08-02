package com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseFirstSecond;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-20 15:07:49
 * @remark 3.5.1一、二级交底
 */
@Data
public class QqchDiscloseFirstSecondVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：一、二级交底集合
     */
    private List<QqchDiscloseFirstSecond> treeList;
}
