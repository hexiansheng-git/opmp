package com.hhwy.sp.techManagement.sgjsPaperPublish.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.FlowInfoSearchUtil;
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
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.domain.SgjsPaperPublishSpecialistReview;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.service.ISgjsPaperPublishSpecialistReviewService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private ISgjsPaperPublishSpecialistReviewService sgjsPaperPublishSpecialistReviewService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;

    @Autowired
    private PmServiceApi pmServiceApi;

    //详情
    @Override
    public SgjsPaperPublish getSgjsPaperPublishById(Long id, String type) {
        SgjsPaperPublish paperPublish = sgjsPaperPublishMapper.getSgjsPaperPublishById(id);
        //设置专家数据
        SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview = new SgjsPaperPublishSpecialistReview();
        sgjsPaperPublishSpecialistReview.setForeignId(id);
        List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList = sgjsPaperPublishSpecialistReviewService.getSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReview);
        paperPublish.setLibraryList(sgjsPaperPublishSpecialistReviewList);
        if("1".equals(type)){

        }
        if("2".equals(type)){
            //设置成果数据
            sgjsAchievementAwardService.setAwardList(paperPublish, SgjsPaperPublish::getId,SgjsPaperPublish::setAwardList);
        }
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        String regionName = projectDto.getRegionName();
        //1直管、2非直管  流程分支使用
        if (StrUtil.isNotBlank(regionName) && regionName.contains("直管")){
            paperPublish.setRegionFlag("1");
        }else {
            paperPublish.setRegionFlag("2");
        }
        return paperPublish;
    }

    public SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        return sgjsPaperPublishMapper.getSgjsPaperPublish(sgjsPaperPublish);
    }

    //台账
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
        String reviewFile = paperPublish.getReviewFile();
        String reviewResult = paperPublish.getReviewResult();
        String reviewSuggest = paperPublish.getReviewSuggest();
        SgjsPaperPublishSpecialistReview param = new SgjsPaperPublishSpecialistReview();
        param.setReviewFile(reviewFile);
        param.setReviewResult(reviewResult);
        param.setReviewSuggest(reviewSuggest);
        param.setForeignId(id);
        sgjsPaperPublishSpecialistReviewService.insertSgjsPaperPublishSpecialistReview(param);

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
        SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview = new SgjsPaperPublishSpecialistReview();
        sgjsPaperPublishSpecialistReview.setForeignId(id);
        sgjsPaperPublishSpecialistReviewService.deleteSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReview);
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


    @Value("${warn.paperPublishProcEnd.url}")
    private String paperPublishProcEndUrl;
    @Value("${warn.paperPublishProcEnd.switch}")
    private String paperPublishProcEndSwitch;

    @Override
    public void updatePaperPublishProcess(Long id, String pass) {
        log.info("论文申请审批完成； id：{}，isPass：{}", id, pass);
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
        //发送总部
        SgjsPaperPublish paperPublish = sgjsPaperPublishMapper.getSgjsPaperPublishById(id);
        paperPublish.setProcessStatus("end");
        sysSyncInfoService4Sp.pushSgjsPaperPublish(paperPublish);
        //保存到论文评分表
        if("1".equals(pass)){
            ArrayList<SgjsPaperScore> objects = new ArrayList<>();
            SgjsPaperScore sgjsPaperScore = new SgjsPaperScore();
            BeanUtil.copyProperties(paperPublish, sgjsPaperScore, "createTime", "updateTime" ,"updateUser");
            sgjsPaperScore.setPtVar3(String.valueOf(sgjsPaperScore.getId()));
            sgjsPaperScore.setPaperCode(sgjsPaperScore.getPtVar5());
            sgjsPaperScore.setPtVar5(null);
            objects.add(sgjsPaperScore);
            sgjsPaperScoreService.insertSgjsPaperScoreList(objects);
            log.info("论文申请-写入论文评分完成: {}", JSON.toJSONString(objects));
        }
        /*0716增加需求
         流程审批完成后给发起人发通知：
         您的【论文名称】申请已通过专家审核，进入终评阶段。
         */
        //判断开关状态
        if (StrUtil.isNotBlank(paperPublishProcEndSwitch) && paperPublishProcEndSwitch.equals("on")) {
            this.sendProcessCompleteNotice(id, pass, paperPublish);
        }else {
            log.info("预警开关状态未开启，状态：{}", paperPublishProcEndSwitch);
        }
    }

    public void sendProcessCompleteNotice(Long id, String pass, SgjsPaperPublish paperPublish) {
        //获取发起人信息
        List<Map<String, String>> flowHistoryInfo = FlowInfoSearchUtil.getFlowHistoryInfo(id, null);
        if (CollUtil.isEmpty(flowHistoryInfo)) {
            log.error("查询流程审批记录未找到，id：{}", id);
            return;
        }
        List<Map<String, String>> collect = flowHistoryInfo.stream().limit(1).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            log.error("过滤流程审批记录异常，元数据：{}", flowHistoryInfo);
            return;
        }
        String userNames = collect.stream().map(key -> key.get("assignee")).collect(Collectors.joining(","));
        //发送预警
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        String projectCode = projectDto.getProjectCode();
        String projectName = projectDto.getProjectName();
        TWarn tWarn = new TWarn();
        tWarn.setWarnItem(projectName + "-论文申请");
        tWarn.setWarnItemId("sgjs_paper_publish");
        tWarn.setWarnScope(userNames);
        tWarn.setWarnUrl(paperPublishProcEndUrl);
        tWarn.setBusinessId(id);
        tWarn.setWarnScopeType("3");
        String paperName = paperPublish.getPaperName();
        String warnContent = pass.equals("0")?"-您的" + paperName + "审批未通过，请调整后重新发起。":"-您的" + paperName + "申请已通过专家审核，进入终评阶段。";
        tWarn.setWarnContent(projectName + warnContent);
        tWarn.setProjectName(projectName);
        tWarn.setTenantKey(projectCode);
        AjaxResult ajaxResult = systemServiceApi.addWarnNonGm(tWarn);
        if (!AjaxResult.isSuccess(ajaxResult)) {
            log.info("预警服务异常, 租户：{}\n请求参数：{}\n响应结果：{}", projectName, JSON.toJSONString(tWarn), JSON.toJSONString(ajaxResult));
            return;
        }
        log.info("论文申请审批结束预警执行完成。租户：{}\n请求参数：{}\n预警服务响应：{}", projectName, JSON.toJSONString(tWarn), JSON.toJSONString(ajaxResult));
    }

    @Override
    public void submitPaperPublishProcess(Long id) {
        sgjsPaperPublishMapper.updatePaperPublishProcess(id,DataCurrentState.APPLYING,"1");

        SgjsPaperPublish paperPublish = this.getSgjsPaperPublishById(id,"2");
        paperPublish.setProcessStatus("submit");
        sysSyncInfoService4Sp.pushSgjsPaperPublish(paperPublish);
    }
}
