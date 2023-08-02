package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchTaxLawMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxLawService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:48
 * @remark 10.2.1税务监管环境概述-税法
 */
@Service
public class QqchTaxLawServiceImpl implements IQqchTaxLawService {

    @Autowired
    private QqchTaxLawMapper qqchTaxLawMapper;

    public QqchTaxLaw getQqchTaxLaw(QqchTaxLaw qqchTaxLaw) {
        return qqchTaxLawMapper.getQqchTaxLaw(qqchTaxLaw);
    }

    public List<QqchTaxLaw> getQqchTaxLawList(QqchTaxLaw qqchTaxLaw) {
        return qqchTaxLawMapper.getQqchTaxLawList(qqchTaxLaw);
    }

    @Transactional
    public int insertQqchTaxLaw(QqchTaxLaw qqchTaxLaw) {
        qqchTaxLaw.setId(IdWorker.createId());
        qqchTaxLaw.setCreateUser(SecurityUtils.getUserName());
        qqchTaxLaw.setCreateTime(DateUtils.getNowDate());
        return qqchTaxLawMapper.insertQqchTaxLaw(qqchTaxLaw);
    }

    @Transactional
    public int insertQqchTaxLawList(List<QqchTaxLaw> qqchTaxLawList, BigDecimal version) {
        for (QqchTaxLaw qqchTaxLaw : qqchTaxLawList) {
            qqchTaxLaw.setId(IdWorker.createId());
            qqchTaxLaw.setVersion(version);
            if (qqchTaxLaw.getVersion().compareTo(BigDecimal.ONE) == 0) {
                qqchTaxLaw.setValid(Valid.YES);
            }
            qqchTaxLaw.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchTaxLaw.setCreateUserName(SecurityUtils.getUserName());
            qqchTaxLaw.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxLawMapper.insertQqchTaxLawList(qqchTaxLawList);
    }

    @Transactional
    public int updateQqchTaxLaw(QqchTaxLaw qqchTaxLaw) {
        qqchTaxLaw.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxLaw.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxLawMapper.updateQqchTaxLaw(qqchTaxLaw);
    }

    @Transactional
    public int updateQqchTaxLawList(List<QqchTaxLaw> qqchTaxLawList) {
        for (QqchTaxLaw qqchTaxLaw : qqchTaxLawList) {
            qqchTaxLaw.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxLaw.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxLawMapper.updateQqchTaxLawList(qqchTaxLawList);
    }

    @Transactional
    public int deleteQqchTaxLaw(QqchTaxLaw qqchTaxLaw) {
        return qqchTaxLawMapper.deleteQqchTaxLaw(qqchTaxLaw);
    }

    @Transactional
    public int deleteQqchTaxLawByPks(List<Long> qqchTaxLawPkList) {
        return qqchTaxLawMapper.deleteQqchTaxLawByPks(qqchTaxLawPkList);
    }
}
