package com.hhwy.pm.qqch.sgch.managementPersonConfig.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
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
import java.util.List;

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
     * @param qqchManagementPersonConfigVo
     */
    @Override
    public void save(QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        String buttonMark = qqchManagementPersonConfigVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchManagementPersonConfigVo.getVersion();
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigVo.getQqchManagementPersonConfigList();

        this.insertQqchManagementPersonConfigList(qqchManagementPersonConfigList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchManagementPersonConfigVo.getMenuId();
            String stageIdentity = qqchManagementPersonConfigVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    /**
     *  新增
     *
     * @param qqchManagementPersonConfigList
     * @param version
     */
    @Transactional
    public void insertQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList,BigDecimal version) {
        //删除旧数据
        QqchManagementPersonConfig qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setVersion(version);
        qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfig(qqchManagementPersonConfig);

        if(CollectionUtils.isEmpty(qqchManagementPersonConfigList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        List<QqchManagementPersonConfig> configs = TreeUtil.treeToList(qqchManagementPersonConfigList);
        for (QqchManagementPersonConfig managementPersonConfig : configs) {
            managementPersonConfig.setValid(valid);
            managementPersonConfig.setVersion(version);
            managementPersonConfig.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            managementPersonConfig.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            managementPersonConfig.setCreateTime(DateUtils.getNowDate());
        }
        qqchManagementPersonConfigMapper.insertQqchManagementPersonConfigList(configs);
    }
}
