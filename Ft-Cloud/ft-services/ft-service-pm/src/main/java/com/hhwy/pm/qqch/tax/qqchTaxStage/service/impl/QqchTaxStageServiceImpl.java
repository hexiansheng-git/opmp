package com.hhwy.pm.qqch.tax.qqchTaxStage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.tax.qqchTaxStage.domain.QqchTaxStage;
import com.hhwy.pm.qqch.tax.qqchTaxStage.dto.StageDTO;
import com.hhwy.pm.qqch.tax.qqchTaxStage.mapper.QqchTaxStageMapper;
import com.hhwy.pm.qqch.tax.qqchTaxStage.service.IQqchTaxStageService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-13 23:06:25
 * @remark
 */
@Service
public class QqchTaxStageServiceImpl implements IQqchTaxStageService {

    @Autowired
    private QqchTaxStageMapper qqchTaxStageMapper;


    public QqchTaxStage getQqchTaxStage(QqchTaxStage qqchTaxStage) {
        return qqchTaxStageMapper.getQqchTaxStage(qqchTaxStage);
    }

    public List<QqchTaxStage> getQqchTaxStageList(QqchTaxStage qqchTaxStage) {
        return qqchTaxStageMapper.getQqchTaxStageList(qqchTaxStage);
    }

    @Transactional
    public int insertQqchTaxStage(QqchTaxStage qqchTaxStage) {
        qqchTaxStage.setId(IdWorker.createId());
        qqchTaxStage.setCreateUser(SecurityUtils.getUserName());
        qqchTaxStage.setCreateTime(DateUtils.getNowDate());
        return qqchTaxStageMapper.insertQqchTaxStage(qqchTaxStage);
    }

    @Transactional
    public int insertQqchTaxStageList(List<QqchTaxStage> qqchTaxStageList) {
        for (QqchTaxStage qqchTaxStage : qqchTaxStageList) {
            qqchTaxStage.setId(IdWorker.createId());
            qqchTaxStage.setCreateUser(SecurityUtils.getUserName());
            qqchTaxStage.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxStageMapper.insertQqchTaxStageList(qqchTaxStageList);
    }

    @Transactional
    public int updateQqchTaxStage(QqchTaxStage qqchTaxStage) {
        qqchTaxStage.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxStage.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxStageMapper.updateQqchTaxStage(qqchTaxStage);
    }

    @Transactional
    public int updateQqchTaxStageList(List<QqchTaxStage> qqchTaxStageList) {
        for (QqchTaxStage qqchTaxStage : qqchTaxStageList) {
            qqchTaxStage.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxStage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxStageMapper.updateQqchTaxStageList(qqchTaxStageList);
    }

    @Transactional
    public int deleteQqchTaxStage(QqchTaxStage qqchTaxStage) {
        qqchTaxStage.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxStage.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxStageMapper.deleteQqchTaxStage(qqchTaxStage);
    }

    @Transactional
    public int deleteQqchTaxStageByPks(List<Long> qqchTaxStagePkList) {
        return qqchTaxStageMapper.deleteQqchTaxStageByPks(qqchTaxStagePkList);
    }

    @Override
    public void saveStage(Long recordId,String dataType) {
        QqchTaxStage qqchTaxStage = new QqchTaxStage();
        qqchTaxStage.setRecordId(recordId);
        qqchTaxStage.setDataType(dataType);
        // 废弃 qqchTaxStage.setRefreshFlag("0");
        qqchTaxStage.setId(IdWorker.createId());
        EntityUtils.setCreateUpdateInfo(qqchTaxStage);
        qqchTaxStageMapper.insertQqchTaxStage(qqchTaxStage);
    }
    
}
