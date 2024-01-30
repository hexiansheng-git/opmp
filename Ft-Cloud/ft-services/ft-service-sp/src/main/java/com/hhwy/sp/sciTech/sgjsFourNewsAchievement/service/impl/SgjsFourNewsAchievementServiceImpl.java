package com.hhwy.sp.sciTech.sgjsFourNewsAchievement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.mapper.SgjsFourNewsAchievementMapper;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.service.ISgjsFourNewsAchievementService;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain.SgjsFourNewsAchievement;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-01-25 10:10:50
 * @remark
 */
@Service
public class SgjsFourNewsAchievementServiceImpl implements ISgjsFourNewsAchievementService {

    @Autowired
    private SgjsFourNewsAchievementMapper sgjsFourNewsAchievementMapper;

    /**
     * 专家服务
     */
    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;

    /**
     * 成果奖项服务
     */
    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;

    /**
     * 鉴定或评价
     */
    @Autowired
    private IShjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;


    public SgjsFourNewsAchievement getSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement) {
        SgjsFourNewsAchievement returnVO = sgjsFourNewsAchievementMapper.getSgjsFourNewsAchievement(sgjsFourNewsAchievement);
        if(returnVO == null) return returnVO;
        Long id = returnVO.getId();
        // 专家库
        SgjsExpertLibrary sgjsExpertLibrary = new SgjsExpertLibrary();
        sgjsExpertLibrary.setForeignId(id);
        returnVO.setSgjsExpertLibraryList(sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibrary));
        // 成果奖项
        SgjsAchievementAward sgjsAchievementAward = new SgjsAchievementAward();
        sgjsAchievementAward.setForeignId(id);
        returnVO.setSgjsAchievementAwardList(sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAward));
        // 鉴定或评价
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = new ShjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setForeignId(id);
        returnVO.setShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate));
        FlowInfoSearchUtil.getFlowInfo(returnVO, FlowEnum.SGJS_FOUR_NEWS_ACHIEVEMENT);
        return returnVO;
    }

    public List<SgjsFourNewsAchievement> getSgjsFourNewsAchievementList(SgjsFourNewsAchievement sgjsFourNewsAchievement) {
        List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList = sgjsFourNewsAchievementMapper.getSgjsFourNewsAchievementList(sgjsFourNewsAchievement);
        // 专家库
        SgjsExpertLibrary sgjsExpertLibrary = new SgjsExpertLibrary();
        sgjsExpertLibrary.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_5);
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibrary);
        // 成果奖项
        SgjsAchievementAward sgjsAchievementAward = new SgjsAchievementAward();
        sgjsAchievementAward.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_5);
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAward);
        // 鉴定或评价
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = new ShjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_5);
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate);
        if(CollectionUtils.isNotEmpty(sgjsFourNewsAchievementList)) {
            for (SgjsFourNewsAchievement sgjsFourNewsAchievement1: sgjsFourNewsAchievementList) {
                Long id = sgjsFourNewsAchievement1.getId();
                if(CollectionUtils.isNotEmpty(sgjsExpertLibraryList)) {
                    List<SgjsExpertLibrary> sgjsExpertLibraries = sgjsExpertLibraryList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsFourNewsAchievement1.setSgjsExpertLibraryList(sgjsExpertLibraries);
                }
                if(CollectionUtils.isNotEmpty(sgjsAchievementAwardList)) {
                    List<SgjsAchievementAward> sgjsAchievementAwards = sgjsAchievementAwardList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsFourNewsAchievement1.setSgjsAchievementAwardList(sgjsAchievementAwards);
                }
                if(CollectionUtils.isNotEmpty(shjsAuthenticateEvaluateList)) {
                    List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluates = shjsAuthenticateEvaluateList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsFourNewsAchievement1.setShjsAuthenticateEvaluateList(shjsAuthenticateEvaluates);
                }
            }
            FlowInfoSearchUtil.getFlowInfo(sgjsFourNewsAchievementList,FlowEnum.SGJS_FOUR_NEWS_ACHIEVEMENT);
        }
        return sgjsFourNewsAchievementList;
    }

    @Transactional
    public int insertSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement) {
        sgjsFourNewsAchievement.setId(IdWorker.createId());
        sgjsFourNewsAchievement.setCreateUser(SecurityUtils.getUserName());
        sgjsFourNewsAchievement.setCreateTime(DateUtils.getNowDate());
        return sgjsFourNewsAchievementMapper.insertSgjsFourNewsAchievement(sgjsFourNewsAchievement);
    }

    @Transactional
    public int insertSgjsFourNewsAchievementList(List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList) {
        for (SgjsFourNewsAchievement sgjsFourNewsAchievement : sgjsFourNewsAchievementList) {
            sgjsFourNewsAchievement.setId(IdWorker.createId());
            sgjsFourNewsAchievement.setCreateUser(SecurityUtils.getUserName());
            sgjsFourNewsAchievement.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsFourNewsAchievementMapper.insertSgjsFourNewsAchievementList(sgjsFourNewsAchievementList);
    }

    @Transactional
    public SgjsFourNewsAchievement updateSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement) {
        Long id = sgjsFourNewsAchievement.getId();
        if(id == null) {
            id = IdWorker.createId();
            sgjsFourNewsAchievement.setId(id);
            sgjsFourNewsAchievement.setCreateUser(SecurityUtils.getSysUser().getNickName());
            sgjsFourNewsAchievement.setCreateTime(DateUtils.getNowDate());
            sgjsFourNewsAchievementMapper.insertSgjsFourNewsAchievement(sgjsFourNewsAchievement);
        } else {
            sgjsFourNewsAchievement.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            sgjsFourNewsAchievement.setUpdateTime(DateUtils.getNowDate());
            sgjsFourNewsAchievementMapper.updateSgjsFourNewsAchievement(sgjsFourNewsAchievement);
        }
        // 成果奖项
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsFourNewsAchievement.getSgjsAchievementAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id, BelongBusiness.BELONG_BUSINESS_5, sgjsAchievementAwardList);
        // 鉴定或评价
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = sgjsFourNewsAchievement.getShjsAuthenticateEvaluateList();
        shjsAuthenticateEvaluateService.saveShjsAuthenticateEvaluateList(id, BelongBusiness.BELONG_BUSINESS_5,shjsAuthenticateEvaluateList);
        // 专家
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsFourNewsAchievement.getSgjsExpertLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id, BelongBusiness.BELONG_BUSINESS_5,sgjsExpertLibraryList);
        return sgjsFourNewsAchievement;
    }

    @Transactional
    public int updateSgjsFourNewsAchievementList(List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList) {
        for (SgjsFourNewsAchievement sgjsFourNewsAchievement : sgjsFourNewsAchievementList) {
            sgjsFourNewsAchievement.setUpdateUser(SecurityUtils.getUserName());
            sgjsFourNewsAchievement.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsFourNewsAchievementMapper.updateSgjsFourNewsAchievementList(sgjsFourNewsAchievementList);
    }

    @Transactional
    public int deleteSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement) {
        return sgjsFourNewsAchievementMapper.deleteSgjsFourNewsAchievement(sgjsFourNewsAchievement);
    }

    @Transactional
    public int deleteSgjsFourNewsAchievementByPks(List<Long> sgjsFourNewsAchievementPkList) {
        return sgjsFourNewsAchievementMapper.deleteSgjsFourNewsAchievementByPks(sgjsFourNewsAchievementPkList);
    }

    @Override
    public void updateTaskStatus(Long id) {
        SgjsFourNewsAchievement sgjsFourNewsAchievement = new SgjsFourNewsAchievement();
        sgjsFourNewsAchievement.setId(id);
        SgjsFourNewsAchievement existVo = sgjsFourNewsAchievementMapper.getSgjsFourNewsAchievement(sgjsFourNewsAchievement);
        if(existVo != null) {
            sgjsFourNewsAchievement.setTaskStatus("5");
            sgjsFourNewsAchievementMapper.updateSgjsFourNewsAchievement(sgjsFourNewsAchievement);
        }
    }
}
