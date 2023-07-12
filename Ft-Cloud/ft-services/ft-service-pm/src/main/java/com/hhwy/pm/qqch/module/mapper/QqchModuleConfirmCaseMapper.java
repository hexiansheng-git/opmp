package com.hhwy.pm.qqch.module.mapper;

import java.util.List;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-11 15:23:04
 * @remark
 */
@Repository
public interface QqchModuleConfirmCaseMapper {

    QqchModuleConfirmCase getQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    List<QqchModuleConfirmCase> getQqchModuleConfirmCaseList(QqchModuleConfirmCase qqchModuleConfirmCase);

    int insertQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int insertQqchModuleConfirmCaseList(@Param("qqchModuleConfirmCaseList") List<QqchModuleConfirmCase> qqchModuleConfirmCaseList);

    int updateQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int updateQqchModuleConfirmCaseList(@Param("list") List<QqchModuleConfirmCase> qqchModuleConfirmCaseList);

    int deleteQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int deleteQqchModuleConfirmCaseByPks(@Param("qqchModuleConfirmCasePkList") List<Long> qqchModuleConfirmCasePkList);
}
