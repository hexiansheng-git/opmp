package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchStandardExpenseAccount;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.mapper.QqchStandardExpenseAccountMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.IQqchStandardExpenseAccountService;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    @Override
    @Transactional
    public List<QqchStandardExpenseAccount> getQqchStandardExpenseAccountList(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList = qqchStandardExpenseAccountMapper.getQqchStandardExpenseAccountList(new QqchStandardExpenseAccount());
        if(CollectionUtils.isEmpty(qqchStandardExpenseAccountList)){
            this.init();
        }
        List<QqchStandardExpenseAccount> list = qqchStandardExpenseAccountMapper.getQqchStandardExpenseAccountList(qqchStandardExpenseAccount);

        //转树列表
        List<QqchStandardExpenseAccount> treeList = ListTreeUtil.formatTree(
                list,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchStandardExpenseAccount::getChildren,
                QqchStandardExpenseAccount::setChildren);
        return treeList;
    }

    /**
     * 初始化数据
     */
    @Transactional
    public void init(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/4_4.json");
            String json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            List<QqchStandardExpenseAccount> list = JSONObject.parseArray(json, QqchStandardExpenseAccount.class);
            List<QqchStandardExpenseAccount> tileList = ListTreeUtil.formatList(
                    list,
                    QqchStandardExpenseAccount::setId,
                    QqchStandardExpenseAccount::setPid,
                    QqchStandardExpenseAccount::setSort,
                    QqchStandardExpenseAccount::setLeaf,
                    QqchStandardExpenseAccount::getChildren,
                    QqchStandardExpenseAccount::setChildren);
            this.insertQqchStandardExpenseAccountList(tileList);
        }catch (IOException e){
            throw new RuntimeException("初始化数据失败！");
        }
    }

    @Transactional
    public int insertQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount) {
        qqchStandardExpenseAccount.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchStandardExpenseAccount.setCreateUserName(SecurityUtils.getUserName());
        qqchStandardExpenseAccount.setCreateTime(DateUtils.getNowDate());
        return qqchStandardExpenseAccountMapper.insertQqchStandardExpenseAccount(qqchStandardExpenseAccount);
    }

    @Transactional
    public int insertQqchStandardExpenseAccountList(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList) {
        for (QqchStandardExpenseAccount qqchStandardExpenseAccount : qqchStandardExpenseAccountList) {
            qqchStandardExpenseAccount.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchStandardExpenseAccount.setCreateUserName(SecurityUtils.getUserName());
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
