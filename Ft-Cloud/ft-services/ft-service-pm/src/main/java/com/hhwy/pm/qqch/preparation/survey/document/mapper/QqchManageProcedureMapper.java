package com.hhwy.pm.qqch.preparation.survey.document.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序
 */
@Repository
public interface QqchManageProcedureMapper {

    QqchManageProcedure getQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    List<QqchManageProcedure> getQqchManageProcedureList(QqchManageProcedure qqchManageProcedure);

    int insertQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int insertQqchManageProcedureList(@Param("qqchManageProcedureList") List<QqchManageProcedure> qqchManageProcedureList);

    int updateQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int updateQqchManageProcedureList(@Param("list") List<QqchManageProcedure> qqchManageProcedureList);

    int deleteQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int deleteQqchManageProcedureByPks(@Param("qqchManageProcedurePkList") List<Long> qqchManageProcedurePkList);
}
