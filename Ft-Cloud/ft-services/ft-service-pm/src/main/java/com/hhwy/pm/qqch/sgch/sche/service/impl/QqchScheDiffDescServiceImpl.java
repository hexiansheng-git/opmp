package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiffDesc;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheDiffDescMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffDescService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:50
 * @remark
 */
@Service
public class QqchScheDiffDescServiceImpl implements IQqchScheDiffDescService {


    private static final String TN = "qqch_sche_diff_desc";

    @Autowired
    private QqchScheDiffDescMapper qqchScheDiffDescMapper;


    public QqchScheDiffDesc getQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc) {
        return qqchScheDiffDescMapper.getQqchScheDiffDesc(qqchScheDiffDesc);
    }

    public List<QqchScheDiffDesc> getQqchScheDiffDescList(QqchScheDiffDesc qqchScheDiffDesc) {
        return qqchScheDiffDescMapper.getQqchScheDiffDescList(qqchScheDiffDesc);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc) {
        qqchScheDiffDesc.setId(IdWorker.createId());
        qqchScheDiffDesc.setCreateUser(SecurityUtils.getUserName());
        qqchScheDiffDesc.setCreateTime(DateUtils.getNowDate());
        return qqchScheDiffDescMapper.insertQqchScheDiffDesc(qqchScheDiffDesc);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheDiffDescList(List<QqchScheDiffDesc> qqchScheDiffDescList) {
        for (QqchScheDiffDesc qqchScheDiffDesc : qqchScheDiffDescList) {
            qqchScheDiffDesc.setId(IdWorker.createId());
            qqchScheDiffDesc.setCreateUser(SecurityUtils.getUserName());
            qqchScheDiffDesc.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffDescMapper.insertQqchScheDiffDescList(qqchScheDiffDescList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc) {
        qqchScheDiffDesc.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiffDesc.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffDescMapper.updateQqchScheDiffDesc(qqchScheDiffDesc);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheDiffDescList(List<QqchScheDiffDesc> qqchScheDiffDescList) {
        for (QqchScheDiffDesc qqchScheDiffDesc : qqchScheDiffDescList) {
            qqchScheDiffDesc.setUpdateUser(SecurityUtils.getUserName());
            qqchScheDiffDesc.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffDescMapper.updateQqchScheDiffDescList(qqchScheDiffDescList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc) {
        qqchScheDiffDesc.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiffDesc.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffDescMapper.deleteQqchScheDiffDesc(qqchScheDiffDesc);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheDiffDescByPks(List<Long> qqchScheDiffDescPkList) {
        return qqchScheDiffDescMapper.deleteQqchScheDiffDescByPks(qqchScheDiffDescPkList);
    }

    @CompileAspect(type = CompileOptEnum.SAVE, tableName = TN)
    @Override
    public void save(QqchScheDiffDesc qqchScheDiffDesc) {
        if (qqchScheDiffDesc == null) return;
        qqchScheDiffDesc.setId(IdWorker.createId());
        this.qqchScheDiffDescMapper.insertQqchScheDiffDesc(qqchScheDiffDesc);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public QqchScheDiffDesc getDesc(QqchScheDiffDesc dealSaveDto) {
        List<QqchScheDiffDesc> qqchScheDiffDescList = this.qqchScheDiffDescMapper.getQqchScheDiffDescList(dealSaveDto);
        return CollectionUtils.isEmpty(qqchScheDiffDescList) ? new QqchScheDiffDesc() : qqchScheDiffDescList.get(0);
    }
}
