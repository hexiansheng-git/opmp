package com.hhwy.pm.qqch.common.defaultData.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.CommonYesNo;
import com.hhwy.pm.qqch.common.defaultData.domain.QqchDefaultDataInitialize;
import com.hhwy.pm.qqch.common.defaultData.mapper.QqchDefaultDataInitializeMapper;
import com.hhwy.pm.qqch.common.defaultData.service.IQqchDefaultDataInitializeService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-21 10:02:06
 * @remark 默认数据初始化状态
 */
@Service
public class QqchDefaultDataInitializeServiceImpl implements IQqchDefaultDataInitializeService {

    @Autowired
    private QqchDefaultDataInitializeMapper qqchDefaultDataInitializeMapper;


    /**
     * 判断默认数据是否已经初始化过
     * @param moduleIdentity
     * @param version
     * @return
     */
    @Override
    @Transactional
    public boolean interpretInitializeStatus(String moduleIdentity, BigDecimal version) {
        CommonAssert.notBlank(moduleIdentity,"模块标识不能为空！");
        CommonAssert.notNull(version,"版本不能为空！");
        //查询初始化状态数据
        QqchDefaultDataInitialize qqchDefaultDataInitialize = new QqchDefaultDataInitialize();
        qqchDefaultDataInitialize.setModuleIdentity(moduleIdentity);
        qqchDefaultDataInitialize.setVersion(version);
        qqchDefaultDataInitialize.setInitializeStatus(CommonYesNo.YES);
        QqchDefaultDataInitialize initializeStatus = qqchDefaultDataInitializeMapper.getQqchDefaultDataInitialize(qqchDefaultDataInitialize);

        if(initializeStatus != null){
            return true;
        }

        //插入数据
        qqchDefaultDataInitialize.setId(IdWorker.createId());
        qqchDefaultDataInitialize.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchDefaultDataInitialize.setCreateUserName(SecurityUtils.getUserName());
        qqchDefaultDataInitialize.setCreateTime(DateUtils.getNowDate());
        qqchDefaultDataInitializeMapper.insertQqchDefaultDataInitialize(qqchDefaultDataInitialize);
        return false;
    }

    public QqchDefaultDataInitialize getQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize) {
        return qqchDefaultDataInitializeMapper.getQqchDefaultDataInitialize(qqchDefaultDataInitialize);
    }

    public List<QqchDefaultDataInitialize> getQqchDefaultDataInitializeList(QqchDefaultDataInitialize qqchDefaultDataInitialize) {
        return qqchDefaultDataInitializeMapper.getQqchDefaultDataInitializeList(qqchDefaultDataInitialize);
    }

    @Transactional
    public int insertQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize) {
        qqchDefaultDataInitialize.setId(IdWorker.createId());
        qqchDefaultDataInitialize.setCreateUser(SecurityUtils.getUserName());
        qqchDefaultDataInitialize.setCreateTime(DateUtils.getNowDate());
        return qqchDefaultDataInitializeMapper.insertQqchDefaultDataInitialize(qqchDefaultDataInitialize);
    }

    @Transactional
    public int insertQqchDefaultDataInitializeList(List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList) {
        for (QqchDefaultDataInitialize qqchDefaultDataInitialize : qqchDefaultDataInitializeList) {
            qqchDefaultDataInitialize.setId(IdWorker.createId());
            qqchDefaultDataInitialize.setCreateUser(SecurityUtils.getUserName());
            qqchDefaultDataInitialize.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDefaultDataInitializeMapper.insertQqchDefaultDataInitializeList(qqchDefaultDataInitializeList);
    }

    @Transactional
    public int updateQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize) {
        qqchDefaultDataInitialize.setUpdateUser(SecurityUtils.getUserName());
        qqchDefaultDataInitialize.setUpdateTime(DateUtils.getNowDate());
        return qqchDefaultDataInitializeMapper.updateQqchDefaultDataInitialize(qqchDefaultDataInitialize);
    }

    @Transactional
    public int updateQqchDefaultDataInitializeList(List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList) {
        for (QqchDefaultDataInitialize qqchDefaultDataInitialize : qqchDefaultDataInitializeList) {
            qqchDefaultDataInitialize.setUpdateUser(SecurityUtils.getUserName());
            qqchDefaultDataInitialize.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDefaultDataInitializeMapper.updateQqchDefaultDataInitializeList(qqchDefaultDataInitializeList);
    }

    @Transactional
    public int deleteQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize) {
        qqchDefaultDataInitialize.setUpdateUser(SecurityUtils.getUserName());
        qqchDefaultDataInitialize.setUpdateTime(DateUtils.getNowDate());
        return qqchDefaultDataInitializeMapper.deleteQqchDefaultDataInitialize(qqchDefaultDataInitialize);
    }

    @Transactional
    public int deleteQqchDefaultDataInitializeByPks(List<Long> qqchDefaultDataInitializePkList) {
        return qqchDefaultDataInitializeMapper.deleteQqchDefaultDataInitializeByPks(qqchDefaultDataInitializePkList);
    }
}
