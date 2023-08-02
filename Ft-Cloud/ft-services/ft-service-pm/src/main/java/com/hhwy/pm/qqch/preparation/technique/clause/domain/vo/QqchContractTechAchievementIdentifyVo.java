package com.hhwy.pm.qqch.preparation.technique.clause.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechAchievementIdentify;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
@Data
public class QqchContractTechAchievementIdentifyVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：合同要求提交的技术文件成果识别集合
     */
    private List<QqchContractTechAchievementIdentify> treeList;
}
