package com.hhwy.sp.techManagement.sgjsPatentDeclare.service;

import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark
 */
public interface ISgjsPatentDeclareService {

    SgjsPatentDeclare getSgjsPatentDeclareById(Long id, String type);

    SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    List<SgjsPatentDeclare> getSgjsPatentDeclareList(PatentDeclareQueryVo queryVo);

    int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int insertSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList);

    int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare);

    int updateSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList);

    void deleteSgjsPatentDeclareById(Long id);

    int deleteSgjsPatentDeclareByPks(List<Long> sgjsPatentDeclarePkList);

    /**
     * 保存
     * @param patentDeclare
     * @return
     */
    void save(SgjsPatentDeclare patentDeclare);

    void submit(SgjsPatentDeclare patentDeclare);

    List<SgjsPatentDeclare> getListByIds(List<Long> ids);
}
