package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
@Service
public class QqchConstructionListServiceImpl implements IQqchConstructionListService {

    @Autowired
    private QqchConstructionListMapper qqchConstructionListMapper;

    public List<QqchConstructionList> getQqchConstructionListList(QqchConstructionList qqchConstructionList) {
        return qqchConstructionListMapper.getQqchConstructionListList(qqchConstructionList);
    }

    @Transactional
    public void batchSave(List<QqchConstructionList> qqchConstructionListList) {
        List<QqchConstructionList> insertList = new ArrayList<>();
        List<QqchConstructionList> updateList = new ArrayList<>();

        // 技术重点
        if (!CollectionUtils.isEmpty(qqchConstructionListList)) {
            for (QqchConstructionList qqchConstructionList : qqchConstructionListList) {
                if (qqchConstructionList.getId() == null) {
                    qqchConstructionList.setId(IdWorker.createId());
                    qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
                    qqchConstructionList.setCreateTime(DateUtils.getNowDate());
                    insertList.add(qqchConstructionList);
                } else {
                    qqchConstructionList.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchConstructionList.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(qqchConstructionList);
                }
            }
        }

        if (insertList.size() > 0) {
            qqchConstructionListMapper.insertQqchConstructionListList(insertList);
        }
        if (updateList.size() > 0) {
            qqchConstructionListMapper.updateQqchConstructionListList(updateList);
        }
    }

    @Transactional
    public int deleteQqchConstructionListByPks(List<Long> qqchConstructionListPkList) {
        return qqchConstructionListMapper.deleteQqchConstructionListByPks(qqchConstructionListPkList);
    }
}
