package com.hhwy.pm.qqch.sgch.important.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import com.hhwy.pm.qqch.sgch.important.mapper.QqchImportantMapper;
import com.hhwy.pm.qqch.sgch.important.service.IQqchImportantService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark
 */
@Service
public class QqchImportantServiceImpl implements IQqchImportantService {

    private static final String TN = "qqch_important";

    @Autowired
    private QqchImportantMapper qqchImportantMapper;


    public QqchImportant getQqchImportant(QqchImportant qqchImportant) {
        return qqchImportantMapper.getQqchImportant(qqchImportant);
    }

    public List<QqchImportant> getQqchImportantList(QqchImportant qqchImportant) {
        return qqchImportantMapper.getQqchImportantList(qqchImportant);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchImportant(QqchImportant qqchImportant) {
        qqchImportant.setId(IdWorker.createId());
        qqchImportant.setCreateUser(SecurityUtils.getUserName());
        qqchImportant.setCreateTime(DateUtils.getNowDate());
        return qqchImportantMapper.insertQqchImportant(qqchImportant);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchImportantList(List<QqchImportant> qqchImportantList) {
        for (QqchImportant qqchImportant : qqchImportantList) {
            qqchImportant.setId(IdWorker.createId());
            qqchImportant.setCreateUser(SecurityUtils.getUserName());
            qqchImportant.setCreateTime(DateUtils.getNowDate());
        }
        return qqchImportantMapper.insertQqchImportantList(qqchImportantList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchImportant(QqchImportant qqchImportant) {
        qqchImportant.setUpdateUser(SecurityUtils.getUserName());
        qqchImportant.setUpdateTime(DateUtils.getNowDate());
        return qqchImportantMapper.updateQqchImportant(qqchImportant);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchImportantList(List<QqchImportant> qqchImportantList) {
        for (QqchImportant qqchImportant : qqchImportantList) {
            qqchImportant.setUpdateUser(SecurityUtils.getUserName());
            qqchImportant.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchImportantMapper.updateQqchImportantList(qqchImportantList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchImportant(QqchImportant qqchImportant) {
        qqchImportant.setUpdateUser(SecurityUtils.getUserName());
        qqchImportant.setUpdateTime(DateUtils.getNowDate());
        return qqchImportantMapper.deleteQqchImportant(qqchImportant);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchImportantByPks(List<Long> qqchImportantPkList) {
        return qqchImportantMapper.deleteQqchImportantByPks(qqchImportantPkList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchImportant> dtos) {
        if (CollectionUtils.isEmpty(dtos)) return;
        for (QqchImportant dto : dtos) {
            dto.setId(IdWorker.createId());
        }
        this.qqchImportantMapper.insertQqchImportantList(dtos);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileEntity list(QqchImportant qqchImportantParam) {


        CompileEntity compileEntity = new CompileEntity();
        List<QqchImportant> qqchImportantList = this.qqchImportantMapper.getQqchImportantList(qqchImportantParam);
        compileEntity.setVersion(BigDecimal.ONE);
//        compileEntity.setStageIdentity("1");
        compileEntity.setDto(qqchImportantList);
        
        return compileEntity;
    }
}
