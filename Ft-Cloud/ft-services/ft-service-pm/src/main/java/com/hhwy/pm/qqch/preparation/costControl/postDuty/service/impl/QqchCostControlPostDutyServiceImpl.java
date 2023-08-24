package com.hhwy.pm.qqch.preparation.costControl.postDuty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.constant.DataSource;
import com.hhwy.pm.qqch.constant.WorkGroup;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo.QqchCostControlPostDutyVo;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.mapper.QqchCostControlPostDutyMapper;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.service.IQqchCostControlPostDutyService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.service.IQqchManagementPersonConfigService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark
 */
@Service
public class QqchCostControlPostDutyServiceImpl implements IQqchCostControlPostDutyService {

    @Autowired
    private QqchCostControlPostDutyMapper qqchCostControlPostDutyMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IQqchManagementPersonConfigService qqchManagementPersonConfigService;


    public QqchCostControlPostDuty getQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        return qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    public List<QqchCostControlPostDuty> getQqchExtendApplyWorkGroupList(QqchCostControlPostDuty qqchCostControlPostDuty) {
        return qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroupList(qqchCostControlPostDuty);
    }

    @Transactional
    public int insertQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setId(IdWorker.createId());
        qqchCostControlPostDuty.setCreateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setCreateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.insertQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.updateQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroupList(List<QqchCostControlPostDuty> qqchCostControlPostDutyList) {
        for (QqchCostControlPostDuty qqchCostControlPostDuty : qqchCostControlPostDutyList) {
            qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
            qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCostControlPostDutyMapper.updateQqchExtendApplyWorkGroupList(qqchCostControlPostDutyList);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroupByPks(List<Long> qqchExtendApplyWorkGroupPkList) {
        return qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroupByPks(qqchExtendApplyWorkGroupPkList);
    }

    /**
     * 获取Vo
     * @param qqchCostControlPostDuty
     * @return
     */
    @Override
    public QqchCostControlPostDutyVo getQqchExtendApplyWorkGroupVo(QqchCostControlPostDuty qqchCostControlPostDuty) {
        QqchCostControlPostDutyVo qqchCostControlPostDutyVo = new QqchCostControlPostDutyVo();

        BigDecimal version = qqchCostControlPostDuty.getVersion();
        version = VersionUtil.getVersion("qqch_extend_apply_work_group",version);

        qqchCostControlPostDuty.setVersion(version);
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroupList(qqchCostControlPostDuty);

        //转树列表
        List<QqchCostControlPostDuty> treeList = ListTreeUtil.formatTree(
                qqchCostControlPostDutyList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchCostControlPostDuty::getChildren,
                QqchCostControlPostDuty::setChildren);

        qqchCostControlPostDutyVo.setVersion(version);
        qqchCostControlPostDutyVo.setStageIdentity(qqchReviewService.getStage());
        qqchCostControlPostDutyVo.setList(treeList);
        return qqchCostControlPostDutyVo;
    }

    public List<QqchCostControlPostDuty> initializeStairStructure(){
        return null;
    }

    /**
     * 保存/确认/提交
     * @param qqchCostControlPostDutyVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchCostControlPostDutyVo qqchCostControlPostDutyVo) {
        String buttonMark = qqchCostControlPostDutyVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchCostControlPostDutyVo.getVersion();
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyVo.getList();

        List<QqchCostControlPostDuty> tileList = ListTreeUtil.formatList(
                qqchCostControlPostDutyList,
                QqchCostControlPostDuty::setId,
                QqchCostControlPostDuty::setPid,
                QqchCostControlPostDuty::setSort,
                QqchCostControlPostDuty::setLeaf,
                QqchCostControlPostDuty::getChildren,
                QqchCostControlPostDuty::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchCostControlPostDutyVo.getMenuId();
            String stageIdentity = qqchCostControlPostDutyVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchCostControlPostDuty> tileList, BigDecimal version) {
        //删除旧数据
        QqchCostControlPostDuty qqchCostControlPostDuty = new QqchCostControlPostDuty();
        qqchCostControlPostDuty.setVersion(version);
        qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroup(qqchCostControlPostDuty);

        this.insertQqchExtendApplyWorkGroupList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchCostControlPostDutyList
     * @param version
     */
    @Transactional
    public void insertQqchExtendApplyWorkGroupList(List<QqchCostControlPostDuty> qqchCostControlPostDutyList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchCostControlPostDutyList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchCostControlPostDuty qqchCostControlPostDuty : qqchCostControlPostDutyList) {
            qqchCostControlPostDuty.setValid(valid);
            qqchCostControlPostDuty.setVersion(version);
            qqchCostControlPostDuty.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCostControlPostDuty.setCreateUserName(SecurityUtils.getUserName());
            qqchCostControlPostDuty.setCreateTime(DateUtils.getNowDate());

            String source = qqchCostControlPostDuty.getSource();
            if(StringUtils.isBlank(source)){
                qqchCostControlPostDuty.setSource(DataSource.MANUAL_ADDITION);
            }
        }
        qqchCostControlPostDutyMapper.insertQqchExtendApplyWorkGroupList(qqchCostControlPostDutyList);
    }

    private static final String OVERALL_MANAGEMENT = "统筹管理";
    private static final String PROJECT_LEADER = "项目经理";

    /**
     * 同步人员总需计划
     * @param qqchCostControlPostDutyVo
     * @return
     */
    @Override
    @Transactional
    public void synchronization(QqchCostControlPostDutyVo qqchCostControlPostDutyVo) {
        List<QqchCostControlPostDuty> list = qqchCostControlPostDutyVo.getList();

        QqchCostControlPostDuty overallManagement = null;
        for (QqchCostControlPostDuty qqchCostControlPostDuty : list) {
            if(OVERALL_MANAGEMENT.equals(qqchCostControlPostDuty.getWorkGroup())){
                overallManagement = qqchCostControlPostDuty;
                list.remove(qqchCostControlPostDuty);
                break;
            }
        }

        List<QqchCostControlPostDuty> originalList = new ArrayList<>();
        if (overallManagement == null){
            overallManagement = new QqchCostControlPostDuty();
            overallManagement.setWorkGroup(OVERALL_MANAGEMENT);
        }else {
            originalList = overallManagement.getChildren();
        }
        List<QqchCostControlPostDuty> finalList = this.mergeData(originalList);
        overallManagement.setChildren(finalList);
        list.add(0,overallManagement);

        //入库
        this.insertQqchExtendApplyWorkGroupList(list,qqchCostControlPostDutyVo.getVersion());
    }

    /**
     * 合并数据
     * @param originalList 原始数据
     */
    public List<QqchCostControlPostDuty> mergeData(List<QqchCostControlPostDuty> originalList){

        //手动新增的数据集
        List<QqchCostControlPostDuty> manualAdditionList = new ArrayList<>();
        //原始数据中之前同步过的数据，封装成map
        Map<Long,QqchCostControlPostDuty> originalRelevancyIdMap = new HashMap<>();
        for (QqchCostControlPostDuty qqchCostControlPostDuty : originalList) {
            if(qqchCostControlPostDuty.getRelevancyId() != null){
                originalRelevancyIdMap.put(qqchCostControlPostDuty.getRelevancyId(),qqchCostControlPostDuty);
            }else {
                manualAdditionList.add(qqchCostControlPostDuty);
            }
        }

        //获取人员总需计划中层级为 ”项目领导层“ 的下级人员数据
        List<QqchManagementPersonConfig> projectLeadershipPersonList = qqchManagementPersonConfigService.getProjectLeadershipPersonList();

        //数据转换  overallManagementList：远程数据
        List<QqchCostControlPostDuty> overallManagementList = new ArrayList<>();
        for (QqchManagementPersonConfig qqchManagementPersonConfig : projectLeadershipPersonList) {
            QqchCostControlPostDuty qqchCostControlPostDuty = new QqchCostControlPostDuty();
            String post = qqchManagementPersonConfig.getPost();
            qqchCostControlPostDuty.setPost(post);
            qqchCostControlPostDuty.setName(qqchManagementPersonConfig.getName());
            qqchCostControlPostDuty.setSource(DataSource.CHOICE);
            qqchCostControlPostDuty.setRelevancyId(qqchManagementPersonConfig.getRelevancyId());
            if(PROJECT_LEADER.equals(post)){
                qqchCostControlPostDuty.setWorkGroup(WorkGroup.GROUP_LEADER);
            }else {
                qqchCostControlPostDuty.setWorkGroup(WorkGroup.ASSISTANT_GROUP_LEADER);
            }
            overallManagementList.add(qqchCostControlPostDuty);
        }

        List<QqchCostControlPostDuty> resultList = new ArrayList<>();
        //数据合并
        for (QqchCostControlPostDuty qqchCostControlPostDuty : overallManagementList) {
            Long relevancyId = qqchCostControlPostDuty.getRelevancyId();

            //在map中查询是否存在关联的原始数据
            QqchCostControlPostDuty original = originalRelevancyIdMap.get(relevancyId);
            if(original != null){
                //存在关联数据
                original.setWorkGroup(qqchCostControlPostDuty.getWorkGroup());
                original.setPost(qqchCostControlPostDuty.getPost());
                original.setName(qqchCostControlPostDuty.getName());
                resultList.add(original);
            }else {
                //不存在关联数据
                resultList.add(qqchCostControlPostDuty);
            }
        }

        resultList.addAll(manualAdditionList);
        return resultList;
    }
}
