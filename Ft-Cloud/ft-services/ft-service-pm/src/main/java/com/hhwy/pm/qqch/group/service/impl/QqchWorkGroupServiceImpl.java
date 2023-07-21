package com.hhwy.pm.qqch.group.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMapper;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;

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
    private XmslContractInfoMapper xmslContractInfoMapper;


    /**
     * 台账（历史记录）
     * @param qqchWorkGroup
     * @return
     */
    public List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup) {
        return qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
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
            qqchWorkGroup = new QqchWorkGroup();
            qqchWorkGroup.setVersion(BigDecimal.valueOf(1.0));
            qqchWorkGroup.setEffective(Valid.NO);
            qqchWorkGroup.setHistoryMark("0");
            qqchWorkGroup.setQqchWorkGroupMemberList(new ArrayList<>());
            this.setPlanUnit(qqchWorkGroup);
            return qqchWorkGroup;
        }

        //判断当前是否存在正在调整的数据（最新未生效版本数据）
        qqchWorkGroup = qqchWorkGroupMapper.getNoValidMaxVersionQqchWorkGroup();
        if(qqchWorkGroup == null){
            qqchWorkGroup = new QqchWorkGroup();
            /*
            调整
             */
            qqchWorkGroup.setId(id);
            //获取调整数据
            qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
            qqchWorkGroup.setId(null);
            qqchWorkGroup.setEffective(Valid.NO);
            BigDecimal version = qqchWorkGroup.getVersion();
            version = version.add(BigDecimal.valueOf(1));
            qqchWorkGroup.setVersion(version);

            //获取小组成员数据
            QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
            qqchWorkGroupMember.setWorkGroupId(id);
            List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroupMemberMapper.getQqchWorkGroupMemberList(qqchWorkGroupMember);
            qqchWorkGroup.setQqchWorkGroupMemberList(qqchWorkGroupMemberList);
        }

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
                    qqchWorkGroup = this.adjustQqchWorkGroup(null);
                }
            }
        }else {
            //直接查询
            qqchWorkGroup.setId(id);
            qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
        }

        //判断是否存在历史记录
        int count = qqchWorkGroupMapper.getWorkGroupCount();
        if(count > 1){
            qqchWorkGroup.setHistoryMark("1");
        }

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
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        Long id = IdWorker.createId();
        qqchWorkGroup.setId(id);

        //新增工作小组成员
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroup.getQqchWorkGroupMemberList();
        if(!CollectionUtils.isEmpty(qqchWorkGroupMemberList)){
            qqchWorkGroupMemberService.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);
        }

        if(StringUtils.isBlank(qqchWorkGroup.getEffective())){
            qqchWorkGroup.setEffective(Valid.NO);//是否有效默认为否
        }
        qqchWorkGroup.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setCreateUserName(SecurityUtils.getUserName());
        qqchWorkGroup.setIssueDate(DateUtils.getNowDate());
        qqchWorkGroup.setCreateTime(DateUtils.getNowDate());
        return qqchWorkGroupMapper.insertQqchWorkGroup(qqchWorkGroup);
    }

    /**
     * 设置策划主导单位和策划审批单位
     * @param qqchWorkGroup
     */
    public void setPlanUnit(QqchWorkGroup qqchWorkGroup){
        //获取合同关联项目信息-项目分类
        XmslContractInfo validMaxVersionContractInfo = xmslContractInfoMapper.getValidMaxVersionContractInfo();

//        if(validMaxVersionContractInfo == null){
//            return;
//        }
        //项目分类
//        String projectCategory = validMaxVersionContractInfo.getProjectCategory();

        //策划主导单位：I、II类项目，显示组织机构海外事业部层级名称 ；III、IV类型项目，显示项目所属单位名称
//        if("1".equals(projectCategory) || "2".equals(projectCategory)){
//            qqchWorkGroup.setPlanDominantUnit("海外事业部");
//        }else {
//            qqchWorkGroup.setPlanDominantUnit("项目名称");
//        }

        //策划审批单位：I、II、III类项目，显示组织机构海外事业部层级名称 ；IV类型项目，显示项目所属单位名称
//        if("4".equals(projectCategory)){
//            qqchWorkGroup.setPlanApprovalUnit("项目名称");
//        }else {
//            qqchWorkGroup.setPlanApprovalUnit("海外事业部");
//        }

        qqchWorkGroup.setPlanDominantUnit("海外事业部");
        qqchWorkGroup.setPlanApprovalUnit("项目名称");
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
        return qqchWorkGroupMapper.updateQqchWorkGroup(qqchWorkGroup);
    }

    /**
     * 提交
     * @param qqchWorkGroup
     * @return
     */
    @Override
    public void submit(QqchWorkGroup qqchWorkGroup) {
        Long id = qqchWorkGroup.getId();
        qqchWorkGroup.setTaskStatus("5");
        qqchWorkGroup.setEffective("1");
        if(id == null || id == 0){
            //插入数据
            this.insertQqchWorkGroup(qqchWorkGroup);
        } else {
            //修改数据
            this.updateQqchWorkGroup(qqchWorkGroup);
        }

        //TODO 发起流程
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
        return qqchWorkGroupMapper.deleteQqchWorkGroup(qqchWorkGroup);
    }
}
