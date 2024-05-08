package com.hhwy.sp.experiment.mixRatioManage.service.impl;

import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.FlowStatusEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.buildSchemeManage.review.constant.ReviewFlowNodeMark;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageService;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaff;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaffRecord;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageQueryVo;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.SgjsMixRatioManageDto;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.SgjsMixRatioManageSaveVo;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageMapper;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageMaterialMapper;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageService;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageStaffRecordService;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageStaffService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark
 */
@Service
public class SgjsMixRatioManageServiceImpl implements ISgjsMixRatioManageService {
    private static final Logger log = LoggerFactory.getLogger(SgjsMixRatioManageServiceImpl.class);
    @Autowired
    private SgjsMixRatioManageMapper sgjsMixRatioManageMapper;

    @Autowired
    private SgjsMixRatioManageMaterialMapper sgjsMixRatioManageMaterialMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;
    @Autowired
    private ISgjsMixRatioManageStaffService staffService;
    @Autowired
    private ISgjsMixRatioManageStaffRecordService recordService;
    @Autowired
    private SystemApiService systemApiService;


    public SgjsMixRatioManage getSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        return sgjsMixRatioManageMapper.getSgjsMixRatioManage(sgjsMixRatioManage);
    }

    public List<SgjsMixRatioManage> getSgjsMixRatioManageList(SgjsMixRatioManage sgjsMixRatioManage) {
        return sgjsMixRatioManageMapper.getSgjsMixRatioManageList(sgjsMixRatioManage);
    }

    @Transactional
    public int insertSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        sgjsMixRatioManage.setCreateUserName(SecurityUtils.getUserName());
        sgjsMixRatioManage.setCreateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.insertSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int insertSgjsMixRatioManageList(List<SgjsMixRatioManage> sgjsMixRatioManageList) {
        for (SgjsMixRatioManage sgjsMixRatioManage : sgjsMixRatioManageList) {
            sgjsMixRatioManage.setId(IdWorker.createId());
            sgjsMixRatioManage.setCreateUser(SecurityUtils.getUserName());
            sgjsMixRatioManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMapper.insertSgjsMixRatioManageList(sgjsMixRatioManageList);
    }

    @Transactional
    public int updateSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.updateSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int updateSgjsMixRatioManageList(List<SgjsMixRatioManage> sgjsMixRatioManageList) {
        for (SgjsMixRatioManage sgjsMixRatioManage : sgjsMixRatioManageList) {
            sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMapper.updateSgjsMixRatioManageList(sgjsMixRatioManageList);
    }

    @Transactional
    public int deleteSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.deleteSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int deleteSgjsMixRatioManageByPks(List<Long> sgjsMixRatioManagePkList) {
        return sgjsMixRatioManageMapper.deleteSgjsMixRatioManageByPks(sgjsMixRatioManagePkList);
    }

    @Override
    public List<SgjsMixRatioManage> getListByQueryVo(MixRatioManageQueryVo queryVo) {
        return sgjsMixRatioManageMapper.getListByQueryVo(queryVo);
    }

    @Override
    public SgjsMixRatioManage getById(Long id) {
        SgjsMixRatioManage mixRatioManage = sgjsMixRatioManageMapper.getById(id);
        if(mixRatioManage != null){
            List<SgjsMixRatioManageMaterial> materialList = sgjsMixRatioManageMaterialMapper.getListByForeignId(id);
            mixRatioManage.setMaterialList(materialList);
        }
        return mixRatioManage;
    }

    @Override
    public SgjsMixRatioManageDto getByIdWithFlag(Long id) {
        SgjsMixRatioManage mixRatioManage = sgjsMixRatioManageMapper.getById(id);
        if(mixRatioManage == null)
            return null;
        SgjsMixRatioManageDto dto = new SgjsMixRatioManageDto();
        BeanUtils.copyProperties(mixRatioManage,dto);
        List<SgjsMixRatioManageMaterial> materialList = sgjsMixRatioManageMaterialMapper.getListByForeignId(id);
        dto.setMaterialList(materialList);
        FlowInfoSearchUtil.getFlowInfo(dto, FlowEnum.SGJS_MIX_MANAGE);
        Integer nodeNum = 0;
        if(StringUtils.isNotBlank(dto.getNextNodeId())){
            Matcher matcher = Pattern.compile("\\d+").matcher(dto.getNextNodeId());
            nodeNum = matcher.find()?Integer.parseInt(matcher.group()):0;
        }
        if(StringUtils.equals(dto.getNextNodeId(),"task_suggestion_4")){ //专家填写意见节点
            dto.setPersonSuggFlag(1);
        }else if(StringUtils.equals(dto.getIsFirstNode(),"1")){               //被驳回到发起人
            dto.setSugguestionFlag(1);
            dto.setPtVar2("0");
            //加载上一次意见
            SgjsMixRatioManage query = new SgjsMixRatioManage();
            query.setPtVar1(id+"");
            List<SgjsMixRatioManage> list = this.sgjsMixRatioManageMapper.getSgjsMixRatioManageList(query);
            if(CollectionUtils.isNotEmpty(list)){
                fillRecordData(list.get(0).getId(), dto);
            }
        }else if(FlowStatusEnum.FLOW_STATUS_END.equals(dto.getTaskStatus())){  //流程已结束
            dto.setSugguestionFlag(1);
        }else if(nodeNum > 3){     //流程到了节点三之后,回显本次评审意见
            dto.setSugguestionFlag(1);
        }
        fillRecordData(dto.getId(),dto);
        return dto;
    }
    
    private void fillRecordData(Long id,SgjsMixRatioManageDto dto){
        if(dto.getSugguestionFlag() != 1 || CollectionUtils.isNotEmpty(dto.getStaffList()))
            return ;
        SgjsMixRatioManageStaff query = new SgjsMixRatioManageStaff();
        query.setMainId(id);
        List<SgjsMixRatioManageStaff> staffList = this.staffService.selectSgjsMixRatioManageStaffList(query);
        Map<Long,SgjsMixRatioManageStaff> staffMap = staffList.stream().collect(Collectors.toMap(r->r.getId(), r->r));
        SgjsMixRatioManageStaffRecord recordQuery = new SgjsMixRatioManageStaffRecord();
        recordQuery.setMainId(id);
        List<SgjsMixRatioManageStaffRecord> recordList = recordService.selectSgjsMixRatioManageStaffRecordList(recordQuery);
        for (int i = 0; i < recordList.size(); i++) {
            SgjsMixRatioManageStaffRecord temp = recordList.get(i);
            SgjsMixRatioManageStaff staff = staffMap.get(temp.getStaffId());
            if(staff == null){
                log.error("获取配合比人员记录失败，记录id:{}", temp.getId());
                continue;
            }
            staff.setRecordList(staff.getRecordList()==null?new ArrayList<>():staff.getRecordList());
            staff.getRecordList().add(0,temp);
        }
        dto.setStaffList(staffList);
    }

    @Override
    @Transactional
    public void save(SgjsMixRatioManageSaveVo mixRatioManage) {
        String saveType = mixRatioManage.getSaveType();
        Long id = mixRatioManage.getId();
        //校验
        check(mixRatioManage);
        mixRatioManage.setApproveOrNot(mixRatioManage.getExternalApprovalDate() == null?"0":"1");
        mixRatioManage.setPtVar2("0");
        if("add".equals(saveType) || id == null){
            id = IdWorker.createId();
            mixRatioManage.setId(id);
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            mixRatioManage.setRegionId(projectDto.getRegionId());
            mixRatioManage.setRegionName(projectDto.getRegionName());
            mixRatioManage.setProjectId(projectDto.getProjectId());
            mixRatioManage.setProjectName(projectDto.getProjectName());
            mixRatioManage.setProjectCode(projectDto.getProjectCode());
            this.insertSgjsMixRatioManage(mixRatioManage);
        }else {
            this.updateSgjsMixRatioManage(mixRatioManage);
        }

        List<SgjsMixRatioManageMaterial> materialList = mixRatioManage.getMaterialList();
        this.saveMaterialList(id, materialList);
        //推送到总部版
        sysSyncInfoService4Sp.pushSgjsMixRatioManage(mixRatioManage);
    }

    @Override
    @Transactional
    public void saveApproval(SgjsMixRatioManageSaveVo mixRatioManage) {
        Assert.notNull(mixRatioManage,"数据缺失");
        Assert.notNull(mixRatioManage.getId(),"数据缺失");
        Assert.isTrue(CollectionUtils.isNotEmpty(mixRatioManage.getApprovalUserList()),"评审专家不能为空" );
        Assert.isTrue(mixRatioManage.getApprovalUserList().size()<6,"评审专家最多只能选五个人");
        
        this.sgjsMixRatioManageMapper.deleteStaff(mixRatioManage.getId());
        this.sgjsMixRatioManageMapper.deleteStaffRecord(mixRatioManage.getId());
//        this. updateStaffLastFlag2Zero
        Long sign = new Date().getTime();
        List<SgjsMixRatioManageStaff> staffList = new ArrayList<>();
        List<SysUser> userList = systemApiService.selectUserListByUsernames(StringUtils.join(mixRatioManage.getApprovalUserList(), ","));
        Map<String,String> userNameMap = userList.stream().collect(Collectors.toMap(r->r.getUserName(), r->r.getNickName()));
        for (int i = 0; i < mixRatioManage.getApprovalUserList().size(); i++) {
            String uname = mixRatioManage.getApprovalUserList().get(i);
            String nickName = ObjectUtils.nvlString(userNameMap.get(uname),uname);
            SgjsMixRatioManageStaff staff = new SgjsMixRatioManageStaff();
            staff.setId(IdWorker.createId());
            staff.setMainId(mixRatioManage.getId());
            staff.setCreateTimeSign(sign);
            staff.setProjectCode(SecurityUtils.getTenantKey());
            staff.setReviewStaffId(uname);
            staff.setReviewStaffName(nickName);
            staff.setSort(i+1);      
            staff.setLatestFlag(1);
            new AddBaseInfoUtil<>().addBaseEntity(staff);
            staffList.add(staff);
        }
        staffService.batchInsert(staffList);
    }
    
    private void check(SgjsMixRatioManage mixRatioManage){
        int count = sgjsMixRatioManageMapper.getByMixRatioCodeExceptId(mixRatioManage.getMixRatioCode(),mixRatioManage.getId());
        if(count >= 1){
            throw new RuntimeException("配合比编号已存在，请重新编辑！");
        }
        //校验混凝土等级
        if(mixRatioManage.getMixRatioType().charAt(0) == '1'){ //水泥，必填
            Assert.isTrue(StringUtils.isNotBlank(mixRatioManage.getMixLevel()), "配合比类型为水泥混凝土时，混凝土等级必填");
            Matcher matcher = Pattern.compile("\\d+").matcher(mixRatioManage.getMixLevel().trim());
            Assert.isTrue(matcher.find(),"混凝土等级填写有误，未获取到数字，填写格式类似于C30,C35");
        }else{          
            mixRatioManage.setMixLevel("");
        }
    }

    private void saveMaterialList(Long foreignId, List<SgjsMixRatioManageMaterial> materialList){
        sgjsMixRatioManageMaterialMapper.deleteByForeignId(foreignId);

        if(CollectionUtils.isEmpty(materialList)){
            return;
        }

        for (SgjsMixRatioManageMaterial material : materialList) {
            material.setId(IdWorker.createId());
            material.setForeignId(foreignId);
            material.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            material.setCreateUserName(SecurityUtils.getUserName());
            material.setCreateTime(DateUtils.getNowDate());
        }

        sgjsMixRatioManageMaterialMapper.insertSgjsMixRatioManageMaterialList(materialList);
    }

    @Override
    public List<SgjsMixRatioManage> getListByIds(List<Long> ids) {
        return sgjsMixRatioManageMapper.getListByIds(ids);
    }


    @Override
    @Transactional
    public void reject(SgjsMixRatioManageSaveVo saveVo) {
        Assert.notNull(saveVo, "数据缺失");
        Assert.notNull(saveVo.getId(), "数据缺失");
        //插入数据到历史
        Long newId = IdWorker.createId();
        //人员信息
        SgjsMixRatioManageStaff queryStaff = new SgjsMixRatioManageStaff();
        queryStaff.setMainId(saveVo.getId());
        List<SgjsMixRatioManageStaff> staffList = staffService.selectSgjsMixRatioManageStaffList(queryStaff);
        Map<Long,Long> staffIdMap = new HashMap<>(); 
        for (int i = 0; i < staffList.size(); i++) {
            SgjsMixRatioManageStaff temp = staffList.get(i);
            Long tempStaffId = IdWorker.createId();
            staffIdMap.put(temp.getId(),tempStaffId);
            temp.setId(tempStaffId);
            temp.setMainId(newId);
            temp.setPtVar1(saveVo.getId()+""); //源数据mainId
            new AddBaseInfoUtil<>().addBaseEntity(temp);
        }
        //人员意见
        SgjsMixRatioManageStaffRecord queryRecord = new SgjsMixRatioManageStaffRecord();
        queryRecord.setMainId(saveVo.getId());
        List<SgjsMixRatioManageStaffRecord> recordList = recordService.selectSgjsMixRatioManageStaffRecordList(queryRecord);
        for (int i = 0; i < recordList.size(); i++) {
            SgjsMixRatioManageStaffRecord temp = recordList.get(i);
            Long newStaffId = staffIdMap.get(temp.getStaffId());
            Assert.notNull(newStaffId, "未获取到新人员id,数据可能有误");
            temp.setId(IdWorker.createId());
            temp.setMainId(newId);
            temp.setStaffId(newStaffId);
            temp.setPtVar1(saveVo.getId()+""); //源数据mainId
        }
        //更新主表 是否存在历史记录标志 
        SgjsMixRatioManage updateMix = new SgjsMixRatioManage();
        updateMix.setId(saveVo.getId());
        updateMix.setPtVar2("1");
        sgjsMixRatioManageMapper.updateSgjsMixRatioManage(updateMix);
        this.sgjsMixRatioManageMapper.insertOldData(newId, saveVo.getId(), SecurityUtils.getUserName());
        staffService.batchInsert(staffList);
        recordService.batchInsert(recordList);
        
    }

    @Override
    public List<SgjsMixRatioManage> historyList(Long id) {
        SgjsMixRatioManage query = new SgjsMixRatioManage();
        query.setPtVar1(id+"");
        List<SgjsMixRatioManage> hisList = sgjsMixRatioManageMapper.getSgjsMixRatioManageList(query);
        if(CollectionUtils.isEmpty(hisList))
            return hisList;
        hisList.sort((v1,v2)->{
            return v1.getId()==v2.getId()?0:(v1.getId()>v2.getId()?1:-1);
        });
        Map<Long,SgjsMixRatioManage> map = hisList.stream().collect(Collectors.toMap(r->r.getId(), r->r));
        SgjsMixRatioManageStaff queryStaff = new SgjsMixRatioManageStaff();
        queryStaff.setParams(ObjectUtils.toMap("mainIds", map.keySet()));
        List<SgjsMixRatioManageStaff> staffList = staffService.selectSgjsMixRatioManageStaffList(queryStaff);
        Map<Long,SgjsMixRatioManageStaff> staffMap = new HashMap<>();
        for (int s = 0; s < staffList.size(); s++) {
            SgjsMixRatioManageStaff temp = staffList.get(s);
            staffMap.put(temp.getId(), temp);
            SgjsMixRatioManage main = map.get(temp.getMainId());
            main.setStaffList(CollectionUtils.isEmpty(main.getStaffList())?new ArrayList<>():main.getStaffList());
            main.getStaffList().add(temp);
        }
        SgjsMixRatioManageStaffRecord queryRecord = new SgjsMixRatioManageStaffRecord();
        queryRecord.setParams(ObjectUtils.toMap("mainIds", map.keySet()));
        List<SgjsMixRatioManageStaffRecord> recordList = recordService.selectSgjsMixRatioManageStaffRecordList(queryRecord);
        for (int s = 0; s < recordList.size(); s++) {
            SgjsMixRatioManageStaffRecord temp = recordList.get(s);
            SgjsMixRatioManageStaff staff = staffMap.get(temp.getStaffId());
            staff.setRecordList(CollectionUtils.isEmpty(staff.getRecordList())?new ArrayList<>():staff.getRecordList());
            staff.getRecordList().add(temp);
        }
        return hisList;
    }
}
