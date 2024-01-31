package com.hhwy.sp.sciTech.sgjsTechMethod.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
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
import com.hhwy.sp.sciTech.sgjsTechMethod.mapper.SgjsTechMethodMapper;
import com.hhwy.sp.sciTech.sgjsTechMethod.service.ISgjsTechMethodService;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark
 */
@Service
public class SgjsTechMethodServiceImpl implements ISgjsTechMethodService {

    @Autowired
    private SgjsTechMethodMapper sgjsTechMethodMapper;

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


    public SgjsTechMethod getSgjsTechMethod(SgjsTechMethod sgjsTechMethod) {
        SgjsTechMethod returnVO = sgjsTechMethodMapper.getSgjsTechMethod(sgjsTechMethod);
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
        FlowInfoSearchUtil.getFlowInfo(returnVO, FlowEnum.SGJS_TECH_METHOD);
        return returnVO;
    }

    public List<SgjsTechMethod> getSgjsTechMethodList(SgjsTechMethod sgjsTechMethod) {
        List<SgjsTechMethod> sgjsTechMethodList = sgjsTechMethodMapper.getSgjsTechMethodList(sgjsTechMethod);
        // 专家库
        SgjsExpertLibrary sgjsExpertLibrary = new SgjsExpertLibrary();
        sgjsExpertLibrary.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_6);
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibrary);
        // 成果奖项
        SgjsAchievementAward sgjsAchievementAward = new SgjsAchievementAward();
        sgjsAchievementAward.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_6);
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAward);
        // 鉴定或评价
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = new ShjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setBelongBusiness(BelongBusiness.BELONG_BUSINESS_6);
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate);
        if (CollectionUtils.isNotEmpty(sgjsTechMethodList)) {
            for (SgjsTechMethod sgjsTechMethod1: sgjsTechMethodList) {
                Long id = sgjsTechMethod1.getId();
                if(CollectionUtils.isNotEmpty(sgjsExpertLibraryList)) {
                    List<SgjsExpertLibrary> sgjsExpertLibraries = sgjsExpertLibraryList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsTechMethod1.setSgjsExpertLibraryList(sgjsExpertLibraries);
                }
                if(CollectionUtils.isNotEmpty(sgjsAchievementAwardList)) {
                    List<SgjsAchievementAward> sgjsAchievementAwards = sgjsAchievementAwardList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsTechMethod1.setSgjsAchievementAwardList(sgjsAchievementAwards);
                }
                if(CollectionUtils.isNotEmpty(shjsAuthenticateEvaluateList)) {
                    List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluates = shjsAuthenticateEvaluateList.stream().filter(vo -> id.equals(vo.getForeignId())).collect(Collectors.toList());
                    sgjsTechMethod1.setShjsAuthenticateEvaluateList(shjsAuthenticateEvaluates);
                }
            }
            FlowInfoSearchUtil.getFlowInfo(sgjsTechMethodList, FlowEnum.SGJS_TECH_METHOD);
        }
        return sgjsTechMethodList;
    }

    @Transactional
    public int insertSgjsTechMethod(SgjsTechMethod sgjsTechMethod) {
        sgjsTechMethod.setId(IdWorker.createId());
        sgjsTechMethod.setCreateUser(SecurityUtils.getUserName());
        sgjsTechMethod.setCreateTime(DateUtils.getNowDate());
        return sgjsTechMethodMapper.insertSgjsTechMethod(sgjsTechMethod);
    }

    @Transactional
    public int insertSgjsTechMethodList(List<SgjsTechMethod> sgjsTechMethodList) {
        for (SgjsTechMethod sgjsTechMethod : sgjsTechMethodList) {
            sgjsTechMethod.setId(IdWorker.createId());
            sgjsTechMethod.setCreateUser(SecurityUtils.getUserName());
            sgjsTechMethod.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechMethodMapper.insertSgjsTechMethodList(sgjsTechMethodList);
    }

    @Transactional
    public SgjsTechMethod updateSgjsTechMethod(SgjsTechMethod sgjsTechMethod) {
        Long id = sgjsTechMethod.getId();
        if (id == null) {
            id = IdWorker.createId();
            sgjsTechMethod.setId(id);
            sgjsTechMethod.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            sgjsTechMethod.setUpdateTime(DateUtils.getNowDate());
            sgjsTechMethodMapper.insertSgjsTechMethod(sgjsTechMethod);
        } else {
            sgjsTechMethod.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            sgjsTechMethod.setUpdateTime(DateUtils.getNowDate());
            sgjsTechMethodMapper.updateSgjsTechMethod(sgjsTechMethod);
        }
        // 成果奖项
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsTechMethod.getSgjsAchievementAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id, BelongBusiness.BELONG_BUSINESS_6, sgjsAchievementAwardList);
        // 鉴定或评价
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = sgjsTechMethod.getShjsAuthenticateEvaluateList();
        shjsAuthenticateEvaluateService.saveShjsAuthenticateEvaluateList(id, BelongBusiness.BELONG_BUSINESS_6,shjsAuthenticateEvaluateList);
        // 专家
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsTechMethod.getSgjsExpertLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id, BelongBusiness.BELONG_BUSINESS_6,sgjsExpertLibraryList);
        return sgjsTechMethod;
    }

    @Transactional
    public int updateSgjsTechMethodList(List<SgjsTechMethod> sgjsTechMethodList) {
        for (SgjsTechMethod sgjsTechMethod : sgjsTechMethodList) {
            sgjsTechMethod.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechMethod.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechMethodMapper.updateSgjsTechMethodList(sgjsTechMethodList);
    }

    @Transactional
    public int deleteSgjsTechMethod(SgjsTechMethod sgjsTechMethod) {
        return sgjsTechMethodMapper.deleteSgjsTechMethod(sgjsTechMethod);
    }

    @Transactional
    public int deleteSgjsTechMethodByPks(List<Long> sgjsTechMethodPkList) {
        return sgjsTechMethodMapper.deleteSgjsTechMethodByPks(sgjsTechMethodPkList);
    }

    @Override
    public void updateTaskStatus(Long id, String isPass) {
        SgjsTechMethod sgjsTechMethod = new SgjsTechMethod();
        sgjsTechMethod.setId(id);
        SgjsTechMethod existVo = sgjsTechMethodMapper.getSgjsTechMethod(sgjsTechMethod);
        if(existVo != null) {
            sgjsTechMethod.setTaskStatus("5");
            if(StringUtils.isNotEmpty(isPass)) {
                if("1".equals(isPass)) {
                    sgjsTechMethod.setDataCurrentState("3");
                }
                if("0".equals(isPass)) {
                    sgjsTechMethod.setDataCurrentState("4");
                }
            } else {
                sgjsTechMethod.setDataCurrentState("2");
            }
            sgjsTechMethodMapper.updateSgjsTechMethod(sgjsTechMethod);
        }
    }
}
