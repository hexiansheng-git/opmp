package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchMajorConstructionComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchMajorConstructionComparisonMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchMajorConstructionComparisonService;
import com.hhwy.utils.tree.TreeUtils;
import java.math.BigDecimal;
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

    public QqchMajorConstructionComparisonVo getQqchMajorConstructionComparisonList(
        QqchMajorConstructionComparison qqchMajorConstructionComparison) {
        QqchMajorConstructionComparisonVo vo = new QqchMajorConstructionComparisonVo();

        QqchMajorConstructionComparison qryParam = new QqchMajorConstructionComparison();
        qryParam.setValid("1");
        qryParam.setVersion(qqchMajorConstructionComparisonMapper.getMaxVersion());
        List<QqchMajorConstructionComparison> list = qqchMajorConstructionComparisonMapper
            .getQqchMajorConstructionComparisonList(qqchMajorConstructionComparison);
        vo.setTreeList(TreeUtils.listToTree(list));
        return vo;
    }

    @Transactional
    public void batchSave(QqchMajorConstructionComparisonVo qqchMajorConstructionComparisonVo) {
        // 先批量删除当前版本所有数据
        QqchMajorConstructionComparison deleteParam = new QqchMajorConstructionComparison();
        deleteParam.setVersion(qqchMajorConstructionComparisonVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchMajorConstructionComparisonMapper.updateQqchMajorConstructionComparison(deleteParam);

        if (CollectionUtils.isEmpty(qqchMajorConstructionComparisonVo.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchMajorConstructionComparison> insertList = TreeUtils
            .splitTreeList(qqchMajorConstructionComparisonVo.getTreeList());

        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchMajorConstructionComparison insert : insertList) {
                insert.setVersion(new BigDecimal("1"));
                insert.setValid("1");
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }

        // 全量入库
        qqchMajorConstructionComparisonMapper.insertQqchMajorConstructionComparisonList(insertList);
    }
}
