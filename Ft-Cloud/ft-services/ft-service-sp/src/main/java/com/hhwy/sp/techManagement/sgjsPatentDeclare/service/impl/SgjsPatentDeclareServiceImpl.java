package com.hhwy.sp.techManagement.sgjsPatentDeclare.service.impl;

import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.constant.DataCurrentState;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.mapper.SgjsPatentDeclareMapper;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Override
    public SgjsPatentDeclare getSgjsPatentDeclareById(Long id, String type) {
        CommonAssert.notNull(id,"id不能为空！");
        SgjsPatentDeclare declare = sgjsPatentDeclareMapper.getSgjsPatentDeclareById(id);
        //TODO 设置专家数据
        List<SgjsExpertLibrary> libraryList = sgjsExpertLibraryService.getListByForeignId(id);
        declare.setLibraryList(libraryList);
        if("1".equals(type)){

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

    public List<SgjsPatentDeclare> getSgjsPatentDeclareList(PatentDeclareQueryVo queryVo) {
        return sgjsPatentDeclareMapper.getSgjsPatentDeclareList(queryVo);
    }

    @Transactional
    public int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
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
    public void deleteSgjsPatentDeclareById(Long id) {
        CommonAssert.notNull(id,"id不能为空");
        sgjsPatentDeclareMapper.deleteSgjsPatentDeclareById(id);

        //删除专家数据
        sgjsExpertLibraryService.deleteSgjsExpertLibraryByForeignId(id);

        //删除成果奖励数据
        sgjsAchievementAwardService.deleteSgjsAchievementAwardByForeignId(id);
    }

    @Transactional
    public int deleteSgjsPatentDeclareByPks(List<Long> sgjsPatentDeclarePkList) {
        return sgjsPatentDeclareMapper.deleteSgjsPatentDeclareByPks(sgjsPatentDeclarePkList);
    }

    /**
     * 保存
     *
     * @param patentDeclare
     * @return
     */
    @Override
    @Transactional
    public Long save(SgjsPatentDeclare patentDeclare) {
        String saveType = patentDeclare.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空");

//        String isSubmit = patentDeclare.getIsSubmit();
//        if("1".equals(isSubmit)){
//            patentDeclare.setCurrentState(DataCurrentState.APPLYING);
//        }

        Long id;
        if("1".equals(saveType) && patentDeclare.getId() == null){
            //新增
            id = IdWorker.createId();
            patentDeclare.setId(id);
            checkPatentNumberSingle(id,patentDeclare.getPatentNumber());
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            patentDeclare.setPtVar4(projectDto.getProjectCode());
            this.insertSgjsPatentDeclare(patentDeclare);
        }else if("2".equals(saveType) || patentDeclare.getId() != null){
            //修改
            id = patentDeclare.getId();
            checkPatentNumberSingle(id,patentDeclare.getPatentNumber());
            this.updateSgjsPatentDeclare(patentDeclare);
        }else {
            throw new RuntimeException("保存类型错误");
        }

        //保存专家数据
        List<SgjsExpertLibrary> libraryList = patentDeclare.getLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id,BelongBusiness.BELONG_BUSINESS_7,libraryList);

        //保存成果登记数据
        List<SgjsAchievementAward> awardList = patentDeclare.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id,BelongBusiness.BELONG_BUSINESS_7,awardList);

        String taskStatus = patentDeclare.getPtVar2();
        if("1".equals(taskStatus) || "5".equals(taskStatus)){
            patentDeclare.setProcessStatus("no");
            sysSyncInfoService4Sp.pushSgjsPatentDeclare(patentDeclare);
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

    /**
     * 校验专利号唯一
     * @param id 数据id
     * @param patentNumber 专利号
     */
    private void checkPatentNumberSingle(Long id, String patentNumber){
        if(StringUtils.isBlank(patentNumber)){
            return;
        }
        int count = sgjsPatentDeclareMapper.getCountByPatentNumberExpectId(id, patentNumber);
        if(count > 0){
            throw new RuntimeException("专利号已存在，请重新编辑！");
        }
    }

    @Override
    public List<SgjsPatentDeclare> getListByIds(List<Long> ids) {
        return sgjsPatentDeclareMapper.getListByIds(ids);
    }

    @Override
    public void updatePatentDeclareProcess(Long id, String pass) {
        if(StringUtils.isBlank(pass)){
            return;
        }
        String currentState;
        if("1".equals(pass)){
            currentState = DataCurrentState.PASS;
        }else {
            currentState = DataCurrentState.NO_PASS;
        }
        sgjsPatentDeclareMapper.updatePatentDeclareProcess(id,currentState,"5");

        SgjsPatentDeclare patentDeclare = sgjsPatentDeclareMapper.getSgjsPatentDeclareById(id);
        patentDeclare.setProcessStatus("end");
        sysSyncInfoService4Sp.pushSgjsPatentDeclare(patentDeclare);
    }

    @Override
    public void submitPatentDeclareProcess(Long id) {
        sgjsPatentDeclareMapper.updatePatentDeclareProcess(id,DataCurrentState.APPLYING,"1");

        SgjsPatentDeclare patentDeclare = this.getSgjsPatentDeclareById(id,"2");
        patentDeclare.setProcessStatus("submit");
        sysSyncInfoService4Sp.pushSgjsPatentDeclare(patentDeclare);
    }
}
