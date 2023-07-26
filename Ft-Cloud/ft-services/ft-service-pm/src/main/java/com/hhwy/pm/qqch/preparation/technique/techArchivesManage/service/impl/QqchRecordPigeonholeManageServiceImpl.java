package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper.QqchRecordPigeonholeManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchRecordPigeonholeManageService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark 技术档案归档管理
 */
@Service
public class QqchRecordPigeonholeManageServiceImpl implements IQqchRecordPigeonholeManageService {

    @Autowired
    private QqchRecordPigeonholeManageMapper qqchRecordPigeonholeManageMapper;


    public QqchRecordPigeonholeManage getQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        return qqchRecordPigeonholeManageMapper.getQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    public List<QqchRecordPigeonholeManage> getQqchRecordPigeonholeManageList(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        return qqchRecordPigeonholeManageMapper.getQqchRecordPigeonholeManageList(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int insertQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setId(IdWorker.createId());
        qqchRecordPigeonholeManage.setCreateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setCreateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.insertQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int insertQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList) {
        for (QqchRecordPigeonholeManage qqchRecordPigeonholeManage : qqchRecordPigeonholeManageList) {
            qqchRecordPigeonholeManage.setId(IdWorker.createId());
            qqchRecordPigeonholeManage.setCreateUser(SecurityUtils.getUserName());
            qqchRecordPigeonholeManage.setCreateTime(DateUtils.getNowDate());
        }
        return qqchRecordPigeonholeManageMapper.insertQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageList);
    }

    @Transactional
    public int updateQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.updateQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int updateQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList) {
        for (QqchRecordPigeonholeManage qqchRecordPigeonholeManage : qqchRecordPigeonholeManageList) {
            qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
            qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchRecordPigeonholeManageMapper.updateQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageList);
    }

    @Transactional
    public int deleteQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.deleteQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int deleteQqchRecordPigeonholeManageByPks(List<Long> qqchRecordPigeonholeManagePkList) {
        return qqchRecordPigeonholeManageMapper.deleteQqchRecordPigeonholeManageByPks(qqchRecordPigeonholeManagePkList);
    }
}
