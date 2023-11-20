package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchPostSettingMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
@Service
public class QqchPostSettingServiceImpl implements IQqchPostSettingService {

    @Autowired
    private QqchPostSettingMapper qqchPostSettingMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchPostSettingVo getTreeList(BigDecimal version) {
        QqchPostSettingVo vo = new QqchPostSettingVo();
        version = VersionUtil.getVersion("qqch_post_setting", version);
        vo.setVersion(version);

        QqchPostSetting qqchPostSettingParam = new QqchPostSetting();
        qqchPostSettingParam.setVersion(version);

        List<QqchPostSetting> list = qqchPostSettingMapper.getQqchPostSettingList(qqchPostSettingParam);
        // 项目技术管理部门及岗位设置集合
        List<QqchPostSetting> techDeptList = new ArrayList<>();
        // 工区技术岗位设置集合
        List<QqchPostSetting> workAreaList = new ArrayList<>();

        for (QqchPostSetting qqchPostSetting : list) {
            if ("1".equals(qqchPostSetting.getPostType())) {
                techDeptList.add(qqchPostSetting);
            }
            if ("2".equals(qqchPostSetting.getPostType())) {
                workAreaList.add(qqchPostSetting);
            }
        }

        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setTechDeptTreeList(TreeUtil.build(techDeptList, null));
        vo.setWorkAreaTreeList(TreeUtil.build(workAreaList, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchPostSettingVo voParam) {
        // 先批量删除当前版本所有数据
        QqchPostSetting deleteParam = new QqchPostSetting();
        deleteParam.setVersion(voParam.getVersion());
        qqchPostSettingMapper.deleteQqchPostSetting(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getTechDeptTreeList()) && CollectionUtils
            .isEmpty(voParam.getWorkAreaTreeList())) {
            return;
        }

        List<QqchPostSetting> insertList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(voParam.getTechDeptTreeList())) {
            // 项目技术管理部门及岗位设置树转list
            List<QqchPostSetting> techDeptTreeList = TreeUtil.treeToList(voParam.getTechDeptTreeList());
            for (QqchPostSetting insertTechDept : techDeptTreeList) {
                insertTechDept.setPostType("1");
                insertTechDept.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    insertTechDept.setValid(Valid.YES);
                }
                insertTechDept.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insertTechDept.setCreateUserName(SecurityUtils.getUserName());
                insertTechDept.setCreateTime(DateUtils.getNowDate());
                insertList.add(insertTechDept);
            }
        }

        if (!CollectionUtils.isEmpty(voParam.getWorkAreaTreeList())) {
            // 工区技术岗位设置树转list
            List<QqchPostSetting> workAreaTreeList = TreeUtil.treeToList(voParam.getWorkAreaTreeList());
            for (QqchPostSetting insertWorkArea : workAreaTreeList) {
                insertWorkArea.setPostType("2");
                insertWorkArea.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    insertWorkArea.setValid(Valid.YES);
                }
                insertWorkArea.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insertWorkArea.setCreateUserName(SecurityUtils.getUserName());
                insertWorkArea.setCreateTime(DateUtils.getNowDate());
                insertList.add(insertWorkArea);
            }
        }

        if (insertList.size() > 0) {
            qqchPostSettingMapper.insertQqchPostSettingList(insertList);
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 获取项目技术管理部门及岗位设置表
     * @return
     */
    @Override
    public List<QqchPostSetting> getTechDeptList() {
        BigDecimal version = VersionUtil.getVersion("qqch_post_setting", null);
        QqchPostSetting qqchPostSettingParam = new QqchPostSetting();
        qqchPostSettingParam.setVersion(version);
        qqchPostSettingParam.setPostType("1");
        List<QqchPostSetting> list = qqchPostSettingMapper.getQqchPostSettingList(qqchPostSettingParam);
        return TreeUtil.build(list, null);
    }
}
