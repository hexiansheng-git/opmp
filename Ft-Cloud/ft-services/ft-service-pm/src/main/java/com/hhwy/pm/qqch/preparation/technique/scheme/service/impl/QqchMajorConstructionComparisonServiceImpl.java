package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchMajorConstructionComparisonMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchMajorConstructionComparisonService;
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
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
@Service
public class QqchMajorConstructionComparisonServiceImpl implements IQqchMajorConstructionComparisonService {

    @Autowired
    private QqchMajorConstructionComparisonMapper qqchMajorConstructionComparisonMapper;


    public QqchMajorConstructionComparison getQqchMajorConstructionComparison(
        QqchMajorConstructionComparison qqchMajorConstructionComparison) {
        return qqchMajorConstructionComparisonMapper
            .getQqchMajorConstructionComparison(qqchMajorConstructionComparison);
    }

    public List<QqchMajorConstructionComparison> getQqchMajorConstructionComparisonList(
        QqchMajorConstructionComparison qqchMajorConstructionComparison) {
        List<QqchMajorConstructionComparison> list = qqchMajorConstructionComparisonMapper
            .getQqchMajorConstructionComparisonList(qqchMajorConstructionComparison);
        return ToTreeUtils.listToTree(list);
    }

    @Transactional
    public void batchSave(List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonList) {
        if (CollectionUtils.isEmpty(qqchMajorConstructionComparisonList)) {
            return;
        }

        List<QqchMajorConstructionComparison> insertList = new ArrayList<>();
        List<QqchMajorConstructionComparison> updateList = new ArrayList<>();
        for (QqchMajorConstructionComparison qqchMajorConstructionComparison : qqchMajorConstructionComparisonList) {
            this.recursionSubset(qqchMajorConstructionComparison, insertList, updateList);
        }

        if (insertList.size() > 0) {
            qqchMajorConstructionComparisonMapper.insertQqchMajorConstructionComparisonList(insertList);
        }
        if (updateList.size() > 0) {
            qqchMajorConstructionComparisonMapper.updateQqchMajorConstructionComparisonList(updateList);
        }
    }

    @Transactional
    public int deleteQqchMajorConstructionComparisonByPks(List<Long> qqchMajorConstructionComparisonPkList) {
        return qqchMajorConstructionComparisonMapper
            .deleteQqchMajorConstructionComparisonByPks(qqchMajorConstructionComparisonPkList);
    }

    /**
     * 递归处理子节点
     *
     * @param qqchMajorConstructionComparison
     * @param insertList
     * @param updateList
     */
    public void recursionSubset(QqchMajorConstructionComparison qqchMajorConstructionComparison,
        List<QqchMajorConstructionComparison> insertList, List<QqchMajorConstructionComparison> updateList) {
        Long id = qqchMajorConstructionComparison.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchMajorConstructionComparison.setId(id);
            qqchMajorConstructionComparison.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchMajorConstructionComparison.setCreateUserName(SecurityUtils.getUserName());
            qqchMajorConstructionComparison.setCreateTime(DateUtils.getNowDate());
            insertList.add(qqchMajorConstructionComparison);
        } else {
            qqchMajorConstructionComparison.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchMajorConstructionComparison.setUpdateTime(DateUtils.getNowDate());
            updateList.add(qqchMajorConstructionComparison);
        }

        List<QqchMajorConstructionComparison> children = qqchMajorConstructionComparison.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (QqchMajorConstructionComparison child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }
}
