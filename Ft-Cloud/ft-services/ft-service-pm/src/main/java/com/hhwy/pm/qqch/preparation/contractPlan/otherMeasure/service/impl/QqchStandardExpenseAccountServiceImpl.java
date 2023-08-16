package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchStandardExpenseAccount;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.mapper.QqchStandardExpenseAccountMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.IQqchStandardExpenseAccountService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-16 16:21:18
 * @remark
 */
@Service
public class QqchStandardExpenseAccountServiceImpl implements IQqchStandardExpenseAccountService {

    @Autowired
    private QqchStandardExpenseAccountMapper qqchStandardExpenseAccountMapper;


    public QqchStandardExpenseAccount getQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        return qqchStandardExpenseAccountMapper.getQqchStandardExpenseAccount(qqchStandardExpenseAccount);
    }

    public List<QqchStandardExpenseAccount> getQqchStandardExpenseAccountList(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList = qqchStandardExpenseAccountMapper.getQqchStandardExpenseAccountList(qqchStandardExpenseAccount);

        //转树列表
        List<QqchStandardExpenseAccount> treeList = ListTreeUtil.formatTree(
                qqchStandardExpenseAccountList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchStandardExpenseAccount::getChildren,
                QqchStandardExpenseAccount::setChildren);
        return treeList;
    }

    @Transactional
    public int insertQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        qqchStandardExpenseAccount.setId(IdWorker.createId());
        qqchStandardExpenseAccount.setCreateUser(SecurityUtils.getUserName());
        qqchStandardExpenseAccount.setCreateTime(DateUtils.getNowDate());
        return qqchStandardExpenseAccountMapper.insertQqchStandardExpenseAccount(qqchStandardExpenseAccount);
    }

    @Transactional
    public int insertQqchStandardExpenseAccountList(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList) {
        for (QqchStandardExpenseAccount qqchStandardExpenseAccount : qqchStandardExpenseAccountList) {
            qqchStandardExpenseAccount.setId(IdWorker.createId());
            qqchStandardExpenseAccount.setCreateUser(SecurityUtils.getUserName());
            qqchStandardExpenseAccount.setCreateTime(DateUtils.getNowDate());
        }
        return qqchStandardExpenseAccountMapper.insertQqchStandardExpenseAccountList(qqchStandardExpenseAccountList);
    }

    @Transactional
    public int updateQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        qqchStandardExpenseAccount.setUpdateUser(SecurityUtils.getUserName());
        qqchStandardExpenseAccount.setUpdateTime(DateUtils.getNowDate());
        return qqchStandardExpenseAccountMapper.updateQqchStandardExpenseAccount(qqchStandardExpenseAccount);
    }

    @Transactional
    public int updateQqchStandardExpenseAccountList(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList) {
        for (QqchStandardExpenseAccount qqchStandardExpenseAccount : qqchStandardExpenseAccountList) {
            qqchStandardExpenseAccount.setUpdateUser(SecurityUtils.getUserName());
            qqchStandardExpenseAccount.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchStandardExpenseAccountMapper.updateQqchStandardExpenseAccountList(qqchStandardExpenseAccountList);
    }

    @Transactional
    public int deleteQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        qqchStandardExpenseAccount.setUpdateUser(SecurityUtils.getUserName());
        qqchStandardExpenseAccount.setUpdateTime(DateUtils.getNowDate());
        return qqchStandardExpenseAccountMapper.deleteQqchStandardExpenseAccount(qqchStandardExpenseAccount);
    }

    @Transactional
    public int deleteQqchStandardExpenseAccountByPks(List<Long> qqchStandardExpenseAccountPkList) {
        return qqchStandardExpenseAccountMapper.deleteQqchStandardExpenseAccountByPks(qqchStandardExpenseAccountPkList);
    }
}
