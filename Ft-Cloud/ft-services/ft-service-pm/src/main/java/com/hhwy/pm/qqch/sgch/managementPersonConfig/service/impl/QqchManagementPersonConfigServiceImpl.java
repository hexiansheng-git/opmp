package com.hhwy.pm.qqch.sgch.managementPersonConfig.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.IQqchOrganizationListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.mapper.QqchManagementPersonConfigMapper;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.service.IQqchManagementPersonConfigService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark
 */
@Service
public class QqchManagementPersonConfigServiceImpl implements IQqchManagementPersonConfigService {

    @Autowired
    private QqchManagementPersonConfigMapper qqchManagementPersonConfigMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchOrganizationListService qqchOrganizationListService;

    public QqchManagementPersonConfig getQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        return qqchManagementPersonConfigMapper.getQqchManagementPersonConfig(qqchManagementPersonConfig);
    }


    @Transactional
    public int insertQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setId(IdWorker.createId());
        qqchManagementPersonConfig.setCreateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setCreateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.insertQqchManagementPersonConfig(qqchManagementPersonConfig);
    }


    @Transactional
    public int updateQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.updateQqchManagementPersonConfig(qqchManagementPersonConfig);
    }

    @Transactional
    public int updateQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList) {
        for (QqchManagementPersonConfig qqchManagementPersonConfig : qqchManagementPersonConfigList) {
            qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
            qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchManagementPersonConfigMapper.updateQqchManagementPersonConfigList(qqchManagementPersonConfigList);
    }

    @Transactional
    public int deleteQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfig(qqchManagementPersonConfig);
    }

    @Transactional
    public int deleteQqchManagementPersonConfigByPks(List<Long> qqchManagementPersonConfigPkList) {
        return qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfigByPks(qqchManagementPersonConfigPkList);
    }


    /**
     * 数据同步
     */
    @Override
    public QqchManagementPersonConfigVo synchData(QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        this.insertQqchManagementPersonConfigList(qqchManagementPersonConfigVo.getQqchManagementPersonConfigList(),qqchManagementPersonConfigVo.getVersion());
        QqchManagementPersonConfigVo vo = new QqchManagementPersonConfigVo();
        //获取1.1项目组织
        QqchOrganizationListVo qqchOrganizationListVo = qqchOrganizationListService.getQqchOrganizationListVo(null);
        //需求：1.1的项目组织的子集为本功能的父集
        //如果存在 继续保留，如果不存在新增，如果修改，先删除后新增（原有数据会被清空）
        QqchOrganizationList qqchOrganizationList = new QqchOrganizationList();
        qqchOrganizationList.setVersion(qqchOrganizationListVo.getVersion());
        List<QqchOrganizationList> organizationLists = qqchOrganizationListService.getQqchOrganizationListList2(qqchOrganizationList);
        List<String> organizationListsnames = organizationLists.stream().map(QqchOrganizationList::getOrganization).collect(Collectors.toList());

        QqchManagementPersonConfig managementPersonConfig = new QqchManagementPersonConfig();
        managementPersonConfig.setPid(0l);
        managementPersonConfig.setVersion(qqchOrganizationListVo.getVersion());
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(managementPersonConfig);
        List<QqchManagementPersonConfig> build = TreeUtil.build(qqchManagementPersonConfigList, 0L);
        List<String> qqchManagementPersonConfigNames = build.stream().map(QqchManagementPersonConfig::getPost).collect(Collectors.toList());
        //1111111111111111111111111
        Iterator<QqchOrganizationList> iterator = organizationLists.iterator();
        while (iterator.hasNext()) {
            QqchOrganizationList organizationList = iterator.next();
            if (!qqchManagementPersonConfigNames.contains(organizationList.getOrganization())) {
                //如果不存在则新增
                qqchManagementPersonConfigNames.add(organizationList.getOrganization());
            }
        }
        Iterator<String> iterator1 = qqchManagementPersonConfigNames.iterator();
        while (iterator1.hasNext()) {
            String element = iterator1.next();
            if (!organizationListsnames.contains(element)) {
                iterator1.remove();
            }
        }
        //222222222222222222222222222
        List<String> collect = build.stream().map(QqchManagementPersonConfig::getPost).collect(Collectors.toList());
        Iterator<String> iterator3 = qqchManagementPersonConfigNames.iterator();
        while (iterator3.hasNext()) {
            String qqchManagementPersonConfigName = iterator3.next();
            if (!collect.contains(qqchManagementPersonConfigName)) {
                QqchManagementPersonConfig managementPersonConfig1 = new QqchManagementPersonConfig();
                managementPersonConfig1.setPid(0l);
                managementPersonConfig1.setPost(qqchManagementPersonConfigName);
                build.add(managementPersonConfig1);
            }
        }

        Iterator<QqchManagementPersonConfig> iterator4 = build.iterator();
        while (iterator4.hasNext()) {
            QqchManagementPersonConfig qqchManagementPersonConfig = iterator4.next();
            if (!qqchManagementPersonConfigNames.contains(qqchManagementPersonConfig.getPost())) {
                iterator4.remove();
            }
        }
        vo.setVersion(qqchOrganizationListVo.getVersion());
        vo.setStageIdentity(qqchReviewService.getStage());
        // build  查询出子集
        this.recursion(build);

        vo.setQqchManagementPersonConfigList(build);
        List<QqchManagementPersonConfig> configs = TreeUtil.treeToList(build);
        this.insertQqchManagementPersonConfigList(configs,qqchManagementPersonConfigVo.getVersion());
        return vo;
    }

    private void recursion(List<QqchManagementPersonConfig> build) {
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(new QqchManagementPersonConfig());
        Map<Long, List<QqchManagementPersonConfig>> listMap = qqchManagementPersonConfigList.stream().collect(Collectors.groupingBy(QqchManagementPersonConfig::getPid));
        if(CollectionUtils.isNotEmpty(build)){
            for (QqchManagementPersonConfig managementPersonConfig : build) {
                if(managementPersonConfig.getId()!=null){
                    List<QqchManagementPersonConfig> qqchManagementPersonConfigs = listMap.get(managementPersonConfig.getId());
                    managementPersonConfig.setChildren(qqchManagementPersonConfigs);
                }
            }
        }
    }


    /**
     * 列表接口
     *
     * @param qqchManagementPersonConfig
     * @return
     */
    public QqchManagementPersonConfigVo getQqchManagementPersonConfigList(QqchManagementPersonConfig qqchManagementPersonConfig) {

        QqchManagementPersonConfigVo qqchManagementPersonConfigVo = new QqchManagementPersonConfigVo();
        BigDecimal version = qqchManagementPersonConfig.getVersion();
        version = VersionUtil.getVersion("qqch_management_person_config", version);
        qqchManagementPersonConfig.setVersion(version);
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(qqchManagementPersonConfig);
        List<QqchManagementPersonConfig> treeList = TreeUtil.build(qqchManagementPersonConfigList, 0l);
        qqchManagementPersonConfigVo.setVersion(version);
        qqchManagementPersonConfigVo.setStageIdentity(qqchReviewService.getStage());
        qqchManagementPersonConfigVo.setQqchManagementPersonConfigList(treeList);
        return qqchManagementPersonConfigVo;
    }


    /**
     * 保存/确认/提交
     *
     * @param qqchManagementPersonConfigVo
     */
    @Override
    public void save(QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        String buttonMark = qqchManagementPersonConfigVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchManagementPersonConfigVo.getVersion();
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigVo.getQqchManagementPersonConfigList();

        this.insertQqchManagementPersonConfigList(qqchManagementPersonConfigList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchManagementPersonConfigVo.getMenuId();
            String stageIdentity = qqchManagementPersonConfigVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    /**
     * 新增
     *
     * @param qqchManagementPersonConfigList
     * @param version
     */
    @Transactional
    public void insertQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList, BigDecimal version) {
        //删除旧数据
        QqchManagementPersonConfig qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setVersion(version);
        qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfig(qqchManagementPersonConfig);

        if (CollectionUtils.isEmpty(qqchManagementPersonConfigList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchManagementPersonConfig> configs = TreeUtil.treeToList(qqchManagementPersonConfigList);
        for (QqchManagementPersonConfig managementPersonConfig : configs) {
            managementPersonConfig.setValid(valid);
            managementPersonConfig.setVersion(version);
            managementPersonConfig.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            managementPersonConfig.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            managementPersonConfig.setCreateTime(DateUtils.getNowDate());
            if(managementPersonConfig.getPid()==null){
                managementPersonConfig.setPid(0l);
            }
        }
        qqchManagementPersonConfigMapper.insertQqchManagementPersonConfigList(configs);
    }
}
