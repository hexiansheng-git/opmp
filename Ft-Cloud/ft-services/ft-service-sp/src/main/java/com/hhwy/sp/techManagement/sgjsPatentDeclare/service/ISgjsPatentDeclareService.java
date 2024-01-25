package com.hhwy.sp.techManagement.sgjsPatentDeclare.service;

import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark
 */
public interface ISgjsPatentDeclareService {

    SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    List<SgjsPatentDeclare> getSgjsPatentDeclareList(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int updateSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int deleteSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int deleteSgjsPatentDeclareByPks(List<Long> sgjsPatentDeclarePkList);
}
