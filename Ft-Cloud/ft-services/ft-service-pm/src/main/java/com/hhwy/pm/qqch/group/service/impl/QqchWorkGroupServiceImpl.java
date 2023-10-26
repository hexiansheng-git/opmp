package com.hhwy.pm.qqch.group.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.CommonYesNo;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.FlowStatusEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMapper;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
@Service
public class QqchWorkGroupServiceImpl implements IQqchWorkGroupService {

    @Autowired
    private QqchWorkGroupMapper qqchWorkGroupMapper;

    @Autowired
    private QqchWorkGroupMemberServiceImpl qqchWorkGroupMemberService;

    @Autowired
    private QqchWorkGroupMemberMapper qqchWorkGroupMemberMapper;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    ISysSyncInfoService sysSyncInfoService;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private WarnService warnService;


    /**
     * 台账（历史记录）
     * @param qqchWorkGroup
     * @return
     */
    public List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup) {
        List<QqchWorkGroup> qqchWorkGroupList = qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
        for (QqchWorkGroup workGroup : qqchWorkGroupList) {
            workGroup.setVersionStr("v" + workGroup.getVersion());
        }
        FlowInfoSearchUtil.getFlowInfo(qqchWorkGroupList, FlowEnum.QQCH_WORK_GROUP);
        return qqchWorkGroupList;
    }

    /**
     * 调整
     * @param id
     * @return
     */
    @Override
    public QqchWorkGroup adjustQqchWorkGroup(Long id) {
        QqchWorkGroup qqchWorkGroup;

        if(id == null){
            //第一次新增
            return this.InitWorkGroup();
        }

        //判断当前是否存在正在调整的数据（最新未生效版本数据）
        qqchWorkGroup = qqchWorkGroupMapper.getNoValidMaxVersionQqchWorkGroup();
        if(qqchWorkGroup != null) {
            //设置版本字符串
            qqchWorkGroup.setVersionStr("v" + qqchWorkGroup.getVersion());
            //设置历史记录按钮
            this.setHistoryMark(qqchWorkGroup);
            //设置工作小组成员数据
            this.setWorkGroupMember(qqchWorkGroup);
            return qqchWorkGroup;
        }

        /* 调整 */
        qqchWorkGroup = new QqchWorkGroup();
        qqchWorkGroup.setId(id);
        //获取调整数据
        qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
        //设置历史记录按钮
        this.setHistoryMark(qqchWorkGroup);
        //设置工作小组成员数据
        this.setWorkGroupMember(qqchWorkGroup);
        //设置策划审批单位和策划主导单位
        this.setPlanUnit(qqchWorkGroup);
        qqchWorkGroup.setId(null);
        qqchWorkGroup.setTaskStatus("0");
        qqchWorkGroup.setEffective(Valid.NO);
        BigDecimal version = qqchWorkGroup.getVersion();
        version = version.add(BigDecimal.ONE);
        qqchWorkGroup.setVersionStr("v" + version);
        qqchWorkGroup.setVersion(version);
        qqchWorkGroup.setIssueDate(null);
        qqchWorkGroup.setCreateTime(null);
        qqchWorkGroup.setCreateUser(null);

        return qqchWorkGroup;
    }

    /**
     * 首次新增，初始化数据
      * @return
     */
    public QqchWorkGroup InitWorkGroup(){
        QqchWorkGroup qqchWorkGroup = new QqchWorkGroup();
        qqchWorkGroup.setVersion(BigDecimal.valueOf(1.0));
        qqchWorkGroup.setVersionStr("v1.0");
        qqchWorkGroup.setEffective(Valid.NO);
        qqchWorkGroup.setHistoryMark(CommonYesNo.NO);
        qqchWorkGroup.setQqchWorkGroupMemberList(new ArrayList<>());
        this.setPlanUnit(qqchWorkGroup);
        return qqchWorkGroup;
    }

    /**
     * 根据id获取工作小组信息
     * @param id
     * @return
     */
    @Override
    public QqchWorkGroup getQqchWorkGroupById(Long id) {

        QqchWorkGroup qqchWorkGroup = new QqchWorkGroup();

        if(id == null){
            //点击页面进入，查询当前最新生效版本
            qqchWorkGroup = qqchWorkGroupMapper.getValidMaxVersionQqchWorkGroup();
            if(qqchWorkGroup == null){
                //获取最新（未生效）版本（理论上最多只存在一条数据）
                qqchWorkGroup = qqchWorkGroupMapper.getNoValidMaxVersionQqchWorkGroup();
                if(qqchWorkGroup == null){
                    qqchWorkGroup = this.InitWorkGroup();
                }
            }else {
                //设置调整按钮
                qqchWorkGroup.setAdjustMark(CommonYesNo.YES);
            }
        }else {
            //直接查询
            qqchWorkGroup.setId(id);
            qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);

            //获取最新生效数据
            QqchWorkGroup validMaxVersionWorkGroup = qqchWorkGroupMapper.getValidMaxVersionQqchWorkGroup();
            if(validMaxVersionWorkGroup != null && id.equals(validMaxVersionWorkGroup.getId())){
                qqchWorkGroup.setAdjustMark(CommonYesNo.YES);
            }
        }

        //设置版本字符串
        qqchWorkGroup.setVersionStr("v" + qqchWorkGroup.getVersion());

        //设置历史记录按钮
        this.setHistoryMark(qqchWorkGroup);

        //设置工作小组
        this.setWorkGroupMember(qqchWorkGroup);

        return qqchWorkGroup;
    }

    /**
     * 设置工作小组
     * @param qqchWorkGroup
     */
    public void setWorkGroupMember(QqchWorkGroup qqchWorkGroup){
        Long id = qqchWorkGroup.getId();
        if(id != null){
            //获取工作小组成员
            QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
            qqchWorkGroupMember.setWorkGroupId(id);
            List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroupMemberMapper.getQqchWorkGroupMemberList(qqchWorkGroupMember);
            qqchWorkGroup.setQqchWorkGroupMemberList(qqchWorkGroupMemberList);
        }else {
            qqchWorkGroup.setQqchWorkGroupMemberList(new ArrayList<>());
        }
    }

    /**
     * 设置历史记录按钮
     * @param qqchWorkGroup
     */
    public void setHistoryMark(QqchWorkGroup qqchWorkGroup){
        int count = qqchWorkGroupMapper.getWorkGroupCount();
        if(count > 1){
            qqchWorkGroup.setHistoryMark(CommonYesNo.YES);
        }
    }

    /**
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        BigDecimal version = qqchWorkGroup.getVersion();
        QqchWorkGroup query = new QqchWorkGroup();
        query.setVersion(version);
        QqchWorkGroup getByVersion = qqchWorkGroupMapper.getQqchWorkGroup(query);
        if(getByVersion != null){
            throw new RuntimeException("该版本已经存在，请勿重复保存！");
        }
        Long id = IdWorker.createId();
        qqchWorkGroup.setId(id);

        //新增工作小组成员
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroup.getQqchWorkGroupMemberList();
        qqchWorkGroupMemberService.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);

        if(StringUtils.isBlank(qqchWorkGroup.getEffective())){
            qqchWorkGroup.setEffective(Valid.NO);//是否有效默认为否
        }
        qqchWorkGroup.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        qqchWorkGroup.setCreateTime(DateUtils.getNowDate());

        //获取项目信息
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        if(projectInfo != null){
            qqchWorkGroup.setProjectId(projectInfo.getProjectId());
            qqchWorkGroup.setProjectName(projectInfo.getProjectName());
        }
        int result = qqchWorkGroupMapper.insertQqchWorkGroup(qqchWorkGroup);
        sysSyncInfoService.pushQqchWorkGroup(qqchWorkGroup);
        return result;
    }

    /**
     * 设置策划主导单位和策划审批单位
     * @param qqchWorkGroup
     */
    public void setPlanUnit(QqchWorkGroup qqchWorkGroup){
        //获取项目信息
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        if(projectInfo == null){
            qqchWorkGroup.setPlanDominantUnit("海外事业部");
            qqchWorkGroup.setPlanApprovalUnit("项目名称");
            return;
        }

        //项目分类
        String projectCategory = projectInfo.getProjectCategory();

        //策划主导单位：I、II类项目，显示组织机构海外事业部层级名称 ；III、IV类型项目，显示项目所属区域中心
        if("1".equals(projectCategory) || "2".equals(projectCategory)){
            qqchWorkGroup.setPlanDominantUnit("海外事业部");
        }else {
            qqchWorkGroup.setPlanDominantUnit(projectInfo.getRegionName());
        }

        //策划审批单位：I、II、III类项目，显示组织机构海外事业部层级名称 ；IV类型项目，显示项目所属区域中心
        if("4".equals(projectCategory)){
            qqchWorkGroup.setPlanApprovalUnit(projectInfo.getRegionName());
        }else {
            qqchWorkGroup.setPlanApprovalUnit("海外事业部");
        }
    }

    /**
     * 修改工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int updateQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {

        //工作小组成员
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroup.getQqchWorkGroupMemberList();
        qqchWorkGroupMemberService.editQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);

        qqchWorkGroup.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setUpdateTime(DateUtils.getNowDate());
        
        int result = qqchWorkGroupMapper.updateQqchWorkGroup(qqchWorkGroup);
        sysSyncInfoService.pushQqchWorkGroup(qqchWorkGroup);
        return result;
    }

    /**
     * 提交
     * @param qqchWorkGroup
     * @return
     */
    @Override
    @Transactional
    public void submit(QqchWorkGroup qqchWorkGroup) {
        Long id = qqchWorkGroup.getId();
        qqchWorkGroup.setTaskStatus(FlowStatusEnum.FLOW_STATUS_AUDITING.getKey());
        if(id == null || id == 0){
            //插入数据
            this.insertQqchWorkGroup(qqchWorkGroup);
        } else {
            //修改数据
            this.updateQqchWorkGroup(qqchWorkGroup);
        }

        //推送到总部
        sysSyncInfoService.pushQqchWorkGroup(qqchWorkGroup);
    }

    /**
     * 删除工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {

        //删除工作小组成员
        QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
        qqchWorkGroupMember.setWorkGroupId(qqchWorkGroup.getId());
        qqchWorkGroupMemberService.deleteQqchWorkGroupMember(qqchWorkGroupMember);

        qqchWorkGroup.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setUpdateTime(DateUtils.getNowDate());
        int result =qqchWorkGroupMapper.deleteQqchWorkGroup(qqchWorkGroup);
        rocketMQTemplate.convertAndSend("qqch_work_group_delete:delete", qqchWorkGroup.getId());
        return result;
    }

    /**
     * 获取最大有效版本数据
     * @return
     */
    @Override
    public QqchWorkGroup getValidMaxVersionQqchWorkGroup() {
        return qqchWorkGroupMapper.getValidMaxVersionQqchWorkGroup();
    }

    /**
     * 获取最新数据
     * @return
     */
    @Override
    public QqchWorkGroup getMaxVersionQqchWorkGroup(){
        return qqchWorkGroupMapper.getMaxVersionQqchWorkGroup();
    }

    @Override
    public List<QqchWorkGroup> gmList(QqchWorkGroup qqchWorkGroup) {
        if(StringUtils.isBlank(qqchWorkGroup.getPtVar5()))
            return new ArrayList<>(2);
        String[] tenantKeys = Convert.toStrArray(qqchWorkGroup.getPtVar5());
        List<QqchWorkGroup> list = null;
        //TODO 直到租户能用前，都直接获取master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            qqchWorkGroup.setPtVar5(null);
            list = qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
            for (int i = 0; i < list.size(); i++) {
                QqchWorkGroup temp =  list.get(i);
                temp.setProjectName("master");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
//        for (int i = 0; i < tenantKeys.length; i++) {
//            String tenantKey = tenantKeys[i];
//            //切换到master
//            String oldDataSource = DynamicDataSourceContextHolder.peek();
//            DynamicDataSourceContextHolder.push(tenantKey); 
//            try {
//                qqchWorkGroup.setPtVar5(null);
//                list = qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
//        for (int i = 0; i < list.size(); i++) {
//            QqchWorkGroup temp =  list.get(i);
//            temp.setProjectName(tenantKey);
//        }
//            }catch (Exception e){
//                e.printStackTrace();
//                throw new CustomBusinessException(e.getMessage());
//            }finally {
//                DynamicDataSourceContextHolder.poll();
//                DynamicDataSourceContextHolder.push(oldDataSource);
//            }    
//        }
        
        return list;
    }

    @Override
    public void updateWorkGroupProcess(Long id) {
        //所有都改为无效
        qqchWorkGroupMapper.updateAllToInvalid();

        QqchWorkGroup query = new QqchWorkGroup();
        query.setId(id);
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(query);
        qqchWorkGroup.setEffective(CommonYesNo.YES);
        qqchWorkGroup.setIssueDate(DateUtils.getNowDate());
        qqchWorkGroup.setTaskStatus("5");//流程结束
        qqchWorkGroupMapper.updateQqchWorkGroup(qqchWorkGroup);
        //推送到总部
        rocketMQTemplate.convertAndSend("qqch_work_group_effect:effect", id);
    }

    @Override
    public void workGroupSetUpWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                if(projectInfo == null){
                    continue;
                }
                /*中标日期*/
                Date winTheBiddingDate = projectInfo.getWinTheBiddingDate();
                if(winTheBiddingDate == null){
                    continue;
                }
                Date nowDate = DateUtils.getNowDate();
                Long diffDays = FtDateUtils.getDays(winTheBiddingDate, nowDate);
                if(diffDays > 10){
                    /*判断工作小组是否已成立并完成审批*/
                    QqchWorkGroup workGroup = qqchWorkGroupMapper.getValidMaxVersionQqchWorkGroup();
                    String projectCategory = projectInfo.getProjectCategory();
                    if(workGroup == null && StringUtils.isNotBlank(projectCategory)){
                        /*发送预警*/
                        String warnScope = "";
                        //根据项目分类给不同的角色发送预警
                        if("1".equals(projectCategory) || "2".equals(projectCategory)){
                            warnScope = "cons_plan_supervisor";
                        }else {
                            warnScope = "cons_assistant_manager";
                        }
                        warnService.addWarn(WarnItem.WORK_GROUP_SET_UP,WarnScopeType.ROLE,null,warnScope,tenantKey);
                    }
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
