package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;

/**
 * @author zq
 * @date 2023-08-14 14:04:07
 * @remark 
 */
public interface QqchSafeEnvriRiskManageMapper {
                                                                                                                                                                                                                                                                                                    
    QqchSafeEnvriRiskManage getQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    List<QqchSafeEnvriRiskManage> getQqchSafeEnvriRiskManageList(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int insertQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

    int insertQqchSafeEnvriRiskManageList(@Param("qqchSafeEnvriRiskManageList") List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList);

    int updateQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

            int updateQqchSafeEnvriRiskManageList(@Param("qqchSafeEnvriRiskManageList") List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList);
    
    int deleteQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage);

            int deleteQqchSafeEnvriRiskManageByPks(@Param("qqchSafeEnvriRiskManagePkList") List<Long> qqchSafeEnvriRiskManagePkList);
    }
