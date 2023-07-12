package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchTechManageModeComparisonMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchTechManageModeComparisonService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Service
public class QqchTechManageModeComparisonServiceImpl implements IQqchTechManageModeComparisonService {

    @Autowired
    private QqchTechManageModeComparisonMapper qqchTechManageModeComparisonMapper;

    public List<QqchTechManageModeComparison> getQqchTechManageModeComparisonList(
        QqchTechManageModeComparison qqchTechManageModeComparison) {
        return qqchTechManageModeComparisonMapper.getQqchTechManageModeComparisonList(qqchTechManageModeComparison);
    }

    @Transactional
    public void batchSave(List<QqchTechManageModeComparison> qqchTechManageModeComparisonList) {

        // 拟选模式为单选，先将数据库中所有数据的拟选模式初始化为0
        QqchTechManageModeComparison updateParam = new QqchTechManageModeComparison();
        updateParam.setSelectMode("0");
        this.updateQqchTechManageModeComparison(updateParam);

        List<QqchTechManageModeComparison> insertList = new ArrayList<>();
        List<QqchTechManageModeComparison> updateList = new ArrayList<>();

        // 技术重点
        if (!CollectionUtils.isEmpty(qqchTechManageModeComparisonList)) {
            for (QqchTechManageModeComparison qqchTechManageModeComparison : qqchTechManageModeComparisonList) {
                if (qqchTechManageModeComparison.getId() == null) {
                    qqchTechManageModeComparison.setId(IdWorker.createId());
                    qqchTechManageModeComparison.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchTechManageModeComparison.setCreateUserName(SecurityUtils.getUserName());
                    qqchTechManageModeComparison.setCreateTime(DateUtils.getNowDate());
                    insertList.add(qqchTechManageModeComparison);
                } else {
                    qqchTechManageModeComparison.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchTechManageModeComparison.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(qqchTechManageModeComparison);
                }
            }
        }

        if (insertList.size() > 0) {
            qqchTechManageModeComparisonMapper.insertQqchTechManageModeComparisonList(insertList);
        }
        if (updateList.size() > 0) {
            qqchTechManageModeComparisonMapper.updateQqchTechManageModeComparisonList(updateList);
        }
    }

    @Transactional
    public int updateQqchTechManageModeComparison(QqchTechManageModeComparison qqchTechManageModeComparison) {
        qqchTechManageModeComparison.setUpdateUser(SecurityUtils.getUserName());
        qqchTechManageModeComparison.setUpdateTime(DateUtils.getNowDate());
        return qqchTechManageModeComparisonMapper.updateQqchTechManageModeComparison(qqchTechManageModeComparison);
    }

    @Transactional
    public int deleteQqchTechManageModeComparisonByPks(List<Long> qqchTechManageModeComparisonPkList) {
        return qqchTechManageModeComparisonMapper
            .deleteQqchTechManageModeComparisonByPks(qqchTechManageModeComparisonPkList);
    }
}
