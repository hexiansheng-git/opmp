package com.hhwy.pm.qqch.module.mapper;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    List<QqchModuleConfirmCase> getModuleConfirmInfo(QqchModuleConfirmCase qqchModuleConfirmCase);

    /**
     * 根据阶段和菜单id集合获取该阶段已确认页面数量
     * @param stage
     * @param menuIdList
     * @return
     */
    int getConfirmNumByStage(@Param("stage") String stage,@Param("menuIdList") List<String> menuIdList);
}
