package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechAchievementIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechAchievementIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechAchievementIdentifyService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtils;
import com.hhwy.utils.tree.TreeVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
@Service
public class QqchContractTechAchievementIdentifyServiceImpl implements IQqchContractTechAchievementIdentifyService {

    @Autowired
    private QqchContractTechAchievementIdentifyMapper qqchContractTechAchievementIdentifyMapper;


    public QqchContractTechAchievementIdentify getQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        return qqchContractTechAchievementIdentifyMapper
            .getQqchContractTechAchievementIdentify(qqchContractTechAchievementIdentify);
    }

    public List<QqchContractTechAchievementIdentify> getQqchContractTechAchievementIdentifyList(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        return qqchContractTechAchievementIdentifyMapper
            .getQqchContractTechAchievementIdentifyList(qqchContractTechAchievementIdentify);
    }

    @Transactional
    public int insertQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        qqchContractTechAchievementIdentify.setId(IdWorker.createId());
        qqchContractTechAchievementIdentify.setCreateUser(SecurityUtils.getUserName());
        qqchContractTechAchievementIdentify.setCreateTime(DateUtils.getNowDate());
        return qqchContractTechAchievementIdentifyMapper
            .insertQqchContractTechAchievementIdentify(qqchContractTechAchievementIdentify);
    }

    @Transactional
    public int insertQqchContractTechAchievementIdentifyList(
        List<QqchContractTechAchievementIdentify> qqchContractTechAchievementIdentifyList) {
        for (QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify : qqchContractTechAchievementIdentifyList) {
            qqchContractTechAchievementIdentify.setId(IdWorker.createId());
            qqchContractTechAchievementIdentify.setCreateUser(SecurityUtils.getUserName());
            qqchContractTechAchievementIdentify.setCreateTime(DateUtils.getNowDate());
        }
        return qqchContractTechAchievementIdentifyMapper
            .insertQqchContractTechAchievementIdentifyList(qqchContractTechAchievementIdentifyList);
    }

    @Transactional
    public int updateQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        qqchContractTechAchievementIdentify.setUpdateUser(SecurityUtils.getUserName());
        qqchContractTechAchievementIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechAchievementIdentifyMapper
            .updateQqchContractTechAchievementIdentify(qqchContractTechAchievementIdentify);
    }

    @Transactional
    public int updateQqchContractTechAchievementIdentifyList(
        List<QqchContractTechAchievementIdentify> qqchContractTechAchievementIdentifyList) {
        for (QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify : qqchContractTechAchievementIdentifyList) {
            qqchContractTechAchievementIdentify.setUpdateUser(SecurityUtils.getUserName());
            qqchContractTechAchievementIdentify.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchContractTechAchievementIdentifyMapper
            .updateQqchContractTechAchievementIdentifyList(qqchContractTechAchievementIdentifyList);
    }

    @Transactional
    public int deleteQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        qqchContractTechAchievementIdentify.setUpdateUser(SecurityUtils.getUserName());
        qqchContractTechAchievementIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechAchievementIdentifyMapper
            .deleteQqchContractTechAchievementIdentify(qqchContractTechAchievementIdentify);
    }

    @Transactional
    public int deleteQqchContractTechAchievementIdentifyByPks(List<Long> qqchContractTechAchievementIdentifyPkList) {
        return qqchContractTechAchievementIdentifyMapper
            .deleteQqchContractTechAchievementIdentifyByPks(qqchContractTechAchievementIdentifyPkList);
    }

    /**
     * 树查询
     *
     * @param qqchContractTechAchievementIdentify
     * @return
     */
    public List<? extends TreeVO> getTreeList(QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify) {
        List<QqchContractTechAchievementIdentify> list = qqchContractTechAchievementIdentifyMapper
            .getQqchContractTechAchievementIdentifyList(qqchContractTechAchievementIdentify);
        List<? extends TreeVO> treeVOS = TreeUtils.buildTree(list, null);
        return treeVOS;
    }

    @Transactional
    public void batchSave(List<QqchContractTechAchievementIdentify> qqchContractTechAchievementIdentifyList) {
        if (CollectionUtils.isEmpty(qqchContractTechAchievementIdentifyList)) {
            return;
        }

        List<QqchContractTechAchievementIdentify> insertList = new ArrayList<>();
        List<QqchContractTechAchievementIdentify> updateList = new ArrayList<>();
        for (QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify : qqchContractTechAchievementIdentifyList) {
            this.recursionSubset(qqchContractTechAchievementIdentify, insertList, updateList);
        }

        if (insertList.size() > 0) {
            qqchContractTechAchievementIdentifyMapper.insertQqchContractTechAchievementIdentifyList(insertList);
        }
        if (updateList.size() > 0) {
            qqchContractTechAchievementIdentifyMapper.updateQqchContractTechAchievementIdentifyList(updateList);
        }
    }

    /**
     * 递归处理子节点
     *
     * @param qqchContractTechAchievementIdentify
     * @param insertList
     * @param updateList
     */
    public void recursionSubset(QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify,
        List<QqchContractTechAchievementIdentify> insertList,
        List<QqchContractTechAchievementIdentify> updateList) {
        Long id = qqchContractTechAchievementIdentify.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchContractTechAchievementIdentify.setId(id);
            qqchContractTechAchievementIdentify.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechAchievementIdentify.setCreateUserName(SecurityUtils.getUserName());
            qqchContractTechAchievementIdentify.setCreateTime(DateUtils.getNowDate());
            insertList.add(qqchContractTechAchievementIdentify);
        } else {
            qqchContractTechAchievementIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechAchievementIdentify.setUpdateTime(DateUtils.getNowDate());
            updateList.add(qqchContractTechAchievementIdentify);
        }

        List<QqchContractTechAchievementIdentify> childList = qqchContractTechAchievementIdentify.getChildList();
        if (!CollectionUtils.isEmpty(childList)) {
            for (QqchContractTechAchievementIdentify child : childList) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }
}
