package com.hhwy.sp.techManagement.sgjsPatentDeclare.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.mapper.SgjsPatentDeclareMapper;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark
 */
@Service
public class SgjsPatentDeclareServiceImpl implements ISgjsPatentDeclareService {

    @Autowired
    private SgjsPatentDeclareMapper sgjsPatentDeclareMapper;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;


    @Override
    public SgjsPatentDeclare getSgjsPatentDeclareById(Long id, String type) {
        SgjsPatentDeclare declare = sgjsPatentDeclareMapper.getSgjsPatentDeclareById(id);
        if("1".equals(type)){
            //TODO 设置专家数据
        }
        if("2".equals(type)){
            //设置成果数据
            sgjsAchievementAwardService.setAwardList(declare,SgjsPatentDeclare::getId,SgjsPatentDeclare::setAwardList);
        }
        return declare;
    }

    public SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        return sgjsPatentDeclareMapper.getSgjsPatentDeclare(sgjsPatentDeclare);
    }

    public List<SgjsPatentDeclare> getSgjsPatentDeclareList(SgjsPatentDeclare sgjsPatentDeclare) {
        return sgjsPatentDeclareMapper.getSgjsPatentDeclareList(sgjsPatentDeclare);
    }

    @Transactional
    public int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        sgjsPatentDeclare.setId(IdWorker.createId());
        sgjsPatentDeclare.setCreateUser(SecurityUtils.getUserName());
        sgjsPatentDeclare.setCreateTime(DateUtils.getNowDate());
        return sgjsPatentDeclareMapper.insertSgjsPatentDeclare(sgjsPatentDeclare);
    }

    @Transactional
    public int insertSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList) {
        for (SgjsPatentDeclare sgjsPatentDeclare : sgjsPatentDeclareList) {
            sgjsPatentDeclare.setId(IdWorker.createId());
            sgjsPatentDeclare.setCreateUser(SecurityUtils.getUserName());
            sgjsPatentDeclare.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPatentDeclareMapper.insertSgjsPatentDeclareList(sgjsPatentDeclareList);
    }

    @Transactional
    public int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        sgjsPatentDeclare.setUpdateUser(SecurityUtils.getUserName());
        sgjsPatentDeclare.setUpdateTime(DateUtils.getNowDate());
        return sgjsPatentDeclareMapper.updateSgjsPatentDeclare(sgjsPatentDeclare);
    }

    @Transactional
    public int updateSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList) {
        for (SgjsPatentDeclare sgjsPatentDeclare : sgjsPatentDeclareList) {
            sgjsPatentDeclare.setUpdateUser(SecurityUtils.getUserName());
            sgjsPatentDeclare.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPatentDeclareMapper.updateSgjsPatentDeclareList(sgjsPatentDeclareList);
    }

    @Transactional
    public int deleteSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        sgjsPatentDeclare.setUpdateUser(SecurityUtils.getUserName());
        sgjsPatentDeclare.setUpdateTime(DateUtils.getNowDate());
        return sgjsPatentDeclareMapper.deleteSgjsPatentDeclare(sgjsPatentDeclare);
    }

    @Transactional
    public int deleteSgjsPatentDeclareByPks(List<Long> sgjsPatentDeclarePkList) {
        return sgjsPatentDeclareMapper.deleteSgjsPatentDeclareByPks(sgjsPatentDeclarePkList);
    }
}
