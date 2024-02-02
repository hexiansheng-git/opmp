package com.hhwy.sp.techManagement.sgjsPatentDeclare.mapper;

import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark
 */
@Repository
public interface SgjsPatentDeclareMapper {

    SgjsPatentDeclare getSgjsPatentDeclareById(@Param("id") Long id);

    SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    List<SgjsPatentDeclare> getSgjsPatentDeclareList(PatentDeclareQueryVo queryVo);

    int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclareList(@Param("sgjsPatentDeclareList") List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int updateSgjsPatentDeclareList(@Param("sgjsPatentDeclareList") List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int deleteSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int deleteSgjsPatentDeclareByPks(@Param("sgjsPatentDeclarePkList") List<Long> sgjsPatentDeclarePkList);

    void deleteSgjsPatentDeclareById(@Param("id") Long id);

    int getCountByPatentNumberExpectId(@Param("id") Long id,@Param("patentNumber") String patentNumber);

    List<SgjsPatentDeclare> getListByIds(@Param("ids") List<Long> ids);

    void updatePatentDeclareProcess(@Param("id") Long id,@Param("currentState") String currentState,@Param("taskStatus") String taskStatus);
}
