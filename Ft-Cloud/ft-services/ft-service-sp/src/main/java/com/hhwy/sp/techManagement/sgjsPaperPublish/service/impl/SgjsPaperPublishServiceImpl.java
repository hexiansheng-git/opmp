package com.hhwy.sp.techManagement.sgjsPaperPublish.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.constant.DataCurrentState;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishExportVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.mapper.SgjsPaperPublishMapper;
import com.hhwy.sp.techManagement.sgjsPaperPublish.service.ISgjsPaperPublishService;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.impl.SgjsPaperScoreServiceImpl;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark
 */
@Service
@Slf4j
public class SgjsPaperPublishServiceImpl implements ISgjsPaperPublishService {

    @Autowired
    private SgjsPaperPublishMapper sgjsPaperPublishMapper;
    @Autowired
    private ISgjsPaperScoreService sgjsPaperScoreService;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;

    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Override
    public SgjsPaperPublish getSgjsPaperPublishById(Long id, String type) {
        SgjsPaperPublish paperPublish = sgjsPaperPublishMapper.getSgjsPaperPublishById(id);
        //TODO 设置专家数据
        List<SgjsExpertLibrary> libraryList = sgjsExpertLibraryService.getListByForeignId(id);
        paperPublish.setLibraryList(libraryList);
        if("1".equals(type)){

        }
        if("2".equals(type)){
            //设置成果数据
            sgjsAchievementAwardService.setAwardList(paperPublish, SgjsPaperPublish::getId,SgjsPaperPublish::setAwardList);
        }
        return paperPublish;
    }

    public SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        return sgjsPaperPublishMapper.getSgjsPaperPublish(sgjsPaperPublish);
    }

    public List<SgjsPaperPublish> getSgjsPaperPublishList(PaperPublishQueryVo queryVo) {
        return sgjsPaperPublishMapper.getSgjsPaperPublishList(queryVo);
    }

    @Transactional
    public int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.insertSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int insertSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setId(IdWorker.createId());
            sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.insertSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.updateSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int updateSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.updateSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.deleteSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int deleteSgjsPaperPublishByPks(List<Long> sgjsPaperPublishPkList) {
        return sgjsPaperPublishMapper.deleteSgjsPaperPublishByPks(sgjsPaperPublishPkList);
    }

    @Override
    @Transactional
    public Long save(SgjsPaperPublish paperPublish) {
        String saveType = paperPublish.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空");

//        String isSubmit = paperPublish.getIsSubmit();
//        if("1".equals(isSubmit)){
//            paperPublish.setCurrentState(DataCurrentState.APPLYING);
//        }

        Long id;
        if("1".equals(saveType) && paperPublish.getId() == null){
            //新增
            id = IdWorker.createId();
            paperPublish.setId(id);
            this.insertSgjsPaperPublish(paperPublish);
        }else if("2".equals(saveType) || paperPublish.getId() != null){
            //修改
            id = paperPublish.getId();
            this.updateSgjsPaperPublish(paperPublish);
        }else {
            throw new RuntimeException("保存类型错误");
        }

        //保存专家数据
        List<SgjsExpertLibrary> libraryList = paperPublish.getLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id, BelongBusiness.BELONG_BUSINESS_8,libraryList);

        //保存成果登记数据
        List<SgjsAchievementAward> awardList = paperPublish.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id,BelongBusiness.BELONG_BUSINESS_8,awardList);

        String taskStatus = paperPublish.getPtVar2();
        if("1".equals(taskStatus) || "5".equals(taskStatus)){
            paperPublish.setProcessStatus("no");
            sysSyncInfoService4Sp.pushSgjsPaperPublish(paperPublish);
        }

        return id;
    }

    @Override
    public AjaxResult messagePublic(String message) {
        // todo 指定角色暂不确定
        String[] roles = {"area_handler", "regionDutyPerson", "common"};
        AjaxResult ajaxResult = systemServiceApi.selectByRoleAndTenant(roles, SecurityUtils.getTenantKey());
        Integer code = (Integer) ajaxResult.get("code");
        Assert.isTrue(code == 200, "获取用户列表失败");
        String s = JSON.toJSONString(ajaxResult.get("data"));
        List<SysUser> sysUsers = JSON.parseArray(s, SysUser.class);
        String clientIds = sysUsers.stream().map(SysUser::getUserName).collect(Collectors.joining(","));
        String topic = "system";
        // todo 消息体内容暂不确定
        R r = systemServiceApi.batchPublish(clientIds, topic, message);
        if (r.getCode() == 200) {
            return AjaxResult.success("消息发布成功");
        }else {
            return AjaxResult.error("消息发布失败");
        }
    }

    @Override
    public void deleteSgjsPaperPublishById(Long id) {
        CommonAssert.notNull(id,"id不能为空");
        sgjsPaperPublishMapper.deleteSgjsPaperPublishById(id);
        //删除专家数据
        sgjsExpertLibraryService.deleteSgjsExpertLibraryByForeignId(id);
        //删除成果奖励数据
        sgjsAchievementAwardService.deleteSgjsAchievementAwardByForeignId(id);
    }

    @Override
    public List<SgjsPaperPublish> getListByIds(List<Long> ids) {
        return sgjsPaperPublishMapper.getListByIds(ids);
    }

    @Override
    public List<PaperPublishExportVo> getExportVoList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        List<PaperPublishExportVo> exportVoList = new ArrayList<>();
        for (SgjsPaperPublish paperPublish : sgjsPaperPublishList) {
            PaperPublishExportVo exportVo = new PaperPublishExportVo();
            BeanUtils.copyProperties(paperPublish,exportVo);
            exportVoList.add(exportVo);
        }
        sgjsAchievementAwardService.setAllAwards(exportVoList,PaperPublishExportVo::getId,PaperPublishExportVo::setAllAward,BelongBusiness.BELONG_BUSINESS_8);
        return exportVoList;
    }

    @Override
    public void updatePaperPublishProcess(Long id, String pass) {
        if(StringUtils.isBlank(pass)){
            return;
        }
        String currentState;
        if("1".equals(pass)){
            currentState = DataCurrentState.PASS;
        }else {
            currentState = DataCurrentState.NO_PASS;
        }
        sgjsPaperPublishMapper.updatePaperPublishProcess(id,currentState,"5");

        SgjsPaperPublish paperPublish = sgjsPaperPublishMapper.getSgjsPaperPublishById(id);
        paperPublish.setProcessStatus("end");
        sysSyncInfoService4Sp.pushSgjsPaperPublish(paperPublish);
        //保存到论文评分表
        if("1".equals(pass)){
            ArrayList<SgjsPaperScore> objects = new ArrayList<>();
            SgjsPaperScore sgjsPaperScore = new SgjsPaperScore();
            BeanUtil.copyProperties(paperPublish, sgjsPaperScore, "createTime", "updateTime" ,"updateUser");
            objects.add(sgjsPaperScore);
            sgjsPaperScoreService.insertSgjsPaperScoreList(objects);
            log.info("论文申请-写入论文评分完成: {}", JSON.toJSONString(objects));
        }
        log.info("论文申请-流程审批完成");
    }

    @Override
    public void submitPaperPublishProcess(Long id) {
        sgjsPaperPublishMapper.updatePaperPublishProcess(id,DataCurrentState.APPLYING,"1");

        SgjsPaperPublish paperPublish = this.getSgjsPaperPublishById(id,"2");
        paperPublish.setProcessStatus("submit");
        sysSyncInfoService4Sp.pushSgjsPaperPublish(paperPublish);
    }
}
