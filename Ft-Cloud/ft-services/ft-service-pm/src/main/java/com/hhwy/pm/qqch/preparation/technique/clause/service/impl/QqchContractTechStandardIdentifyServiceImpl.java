package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechStandardIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ToTreeUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Service
public class QqchContractTechStandardIdentifyServiceImpl implements IQqchContractTechStandardIdentifyService {

    @Autowired
    private QqchContractTechStandardIdentifyMapper qqchContractTechStandardIdentifyMapper;


    public QqchContractTechStandardIdentify getQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        return qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    public List<QqchContractTechStandardIdentify> getQqchContractTechStandardIdentifyList(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        return qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int insertQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setId(IdWorker.createId());
        qqchContractTechStandardIdentify.setCreateUser(SecurityUtils.getUserName());
        qqchContractTechStandardIdentify.setCreateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .insertQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int insertQqchContractTechStandardIdentifyList(
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList) {
        for (QqchContractTechStandardIdentify qqchContractTechStandardIdentify : qqchContractTechStandardIdentifyList) {
            qqchContractTechStandardIdentify.setId(IdWorker.createId());
            qqchContractTechStandardIdentify.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setCreateUserName(SecurityUtils.getUserName());
            qqchContractTechStandardIdentify.setCreateTime(DateUtils.getNowDate());
        }
        return qqchContractTechStandardIdentifyMapper
            .insertQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyList);
    }

    @Transactional
    public int updateQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .updateQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int updateQqchContractTechStandardIdentifyList(
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList) {
        for (QqchContractTechStandardIdentify qqchContractTechStandardIdentify : qqchContractTechStandardIdentifyList) {
            qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchContractTechStandardIdentifyMapper
            .updateQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyList);
    }

    @Transactional
    public int deleteQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .deleteQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int deleteQqchContractTechStandardIdentifyByPks(List<Long> qqchContractTechStandardIdentifyPkList) {
        return qqchContractTechStandardIdentifyMapper
            .deleteQqchContractTechStandardIdentifyByPks(qqchContractTechStandardIdentifyPkList);
    }

    /**
     * 树列表查询
     *
     * @param qqchContractTechStandardIdentify
     * @return
     */
    public List<QqchContractTechStandardIdentify> getTreeList(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        List<QqchContractTechStandardIdentify> list = qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentify);
        List<QqchContractTechStandardIdentify> treeList = ToTreeUtils.listToTree(list, "pid", "id");
        return treeList;
    }

    /**
     * 批量保存
     *
     * @param qqchContractTechStandardIdentifyList
     */
    @Transactional
    public void batchSave(List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList) {
        if (CollectionUtils.isEmpty(qqchContractTechStandardIdentifyList)) {
            return;
        }

        List<QqchContractTechStandardIdentify> insertList = new ArrayList<>();
        List<QqchContractTechStandardIdentify> updateList = new ArrayList<>();
        for (QqchContractTechStandardIdentify qqchContractTechStandardIdentify : qqchContractTechStandardIdentifyList) {
            this.recursionSubset(qqchContractTechStandardIdentify, insertList, updateList);
        }

        if (insertList.size() > 0) {
            qqchContractTechStandardIdentifyMapper.insertQqchContractTechStandardIdentifyList(insertList);
        }
        if (updateList.size() > 0) {
            qqchContractTechStandardIdentifyMapper.updateQqchContractTechStandardIdentifyList(updateList);
        }
    }

    /**
     * 递归处理子节点
     *
     * @param qqchContractTechStandardIdentify
     * @param insertList
     * @param updateList
     */
    public void recursionSubset(QqchContractTechStandardIdentify qqchContractTechStandardIdentify,
        List<QqchContractTechStandardIdentify> insertList, List<QqchContractTechStandardIdentify> updateList) {
        Long id = qqchContractTechStandardIdentify.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchContractTechStandardIdentify.setId(id);
            qqchContractTechStandardIdentify.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setCreateUserName(SecurityUtils.getUserName());
            qqchContractTechStandardIdentify.setCreateTime(DateUtils.getNowDate());
            insertList.add(qqchContractTechStandardIdentify);
        } else {
            qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
            updateList.add(qqchContractTechStandardIdentify);
        }

        List<QqchContractTechStandardIdentify> childList = qqchContractTechStandardIdentify.getChildren();
        if (!CollectionUtils.isEmpty(childList)) {
            for (QqchContractTechStandardIdentify child : childList) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }
}
