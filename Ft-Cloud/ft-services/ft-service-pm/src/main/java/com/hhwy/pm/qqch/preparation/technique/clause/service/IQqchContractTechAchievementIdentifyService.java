package com.hhwy.pm.qqch.preparation.technique.clause.service;

import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechAchievementIdentifyVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
public interface IQqchContractTechAchievementIdentifyService {

    QqchContractTechAchievementIdentifyVo getTreeList(BigDecimal version);

    void batchSave(QqchContractTechAchievementIdentifyVo qqchContractTechAchievementIdentifyVo);
}
