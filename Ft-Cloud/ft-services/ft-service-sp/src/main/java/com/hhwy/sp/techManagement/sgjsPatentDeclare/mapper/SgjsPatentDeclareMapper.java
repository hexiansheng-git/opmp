package com.hhwy.sp.techManagement.sgjsPatentDeclare.mapper;

import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    List<SgjsPatentDeclare> getSgjsPatentDeclareList(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclareList(@Param("sgjsPatentDeclareList") List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

            int updateSgjsPatentDeclareList(@Param("sgjsPatentDeclareList") List<SgjsPatentDeclare> sgjsPatentDeclareList);
    
    int deleteSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

            int deleteSgjsPatentDeclareByPks(@Param("sgjsPatentDeclarePkList") List<Long> sgjsPatentDeclarePkList);
    }
