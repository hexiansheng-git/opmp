package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.service;

import java.math.BigDecimal;
import java.util.List;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;

/**
 * @author zq
 * @date 2023-08-14 14:04:07
 * @remark 
 */
public interface IQqchSafeEnvriRiskManageService {
                                                                                                                                                                                                                                                                                                    
    QqchSafeEnvriRiskManage getQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    List<QqchSafeEnvriRiskManage> getQqchSafeEnvriRiskManageList(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int insertQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int insertQqchSafeEnvriRiskManageList(QqchSafeEnvriRiskManageVo qqchSafeEnvriRiskManageVo);

    int updateQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int updateQqchSafeEnvriRiskManageList(List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList);
    
    int deleteQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int deleteQqchSafeEnvriRiskManageByPks(List<Long> qqchSafeEnvriRiskManagePkList);

    QqchSafeEnvriRiskManageVo getList(BigDecimal version);
}
