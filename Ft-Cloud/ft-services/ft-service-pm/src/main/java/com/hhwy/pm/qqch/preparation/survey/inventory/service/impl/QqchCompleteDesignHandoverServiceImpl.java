package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.mapper.QqchCompleteDesignHandoverMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchCompleteDesignHandoverService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Service
public class QqchCompleteDesignHandoverServiceImpl implements IQqchCompleteDesignHandoverService {

    @Autowired
    private QqchCompleteDesignHandoverMapper qqchCompleteDesignHandoverMapper;

    /**
     * 完整设计交接情况台账
     * @param qqchCompleteDesignHandover
     * @return
     */
    public List<QqchCompleteDesignHandover> getQqchCompleteDesignHandoverList(QqchCompleteDesignHandover qqchCompleteDesignHandover) {
        return qqchCompleteDesignHandoverMapper.getQqchCompleteDesignHandoverList(qqchCompleteDesignHandover);
    }

    /**
     * 保存
     * @param qqchCompleteDesignHandoverListParam
     * @return
     */
    @Override
    @Transactional
    public int editQqchCompleteDesignHandoverList(List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverListParam) {
        List<QqchCompleteDesignHandover> insertList = new ArrayList<>();
        List<QqchCompleteDesignHandover> updateList = new ArrayList<>();
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverListParam) {
            Long id = qqchCompleteDesignHandover.getId();
            if(id == null){
                insertList.add(qqchCompleteDesignHandover);
            }else {
                updateList.add(qqchCompleteDesignHandover);
            }
        }
        if(insertList.size() > 0){
            this.insertQqchCompleteDesignHandoverList(insertList);
        }
        if(updateList.size() > 0){
            this.updateQqchCompleteDesignHandoverList(updateList);
        }
        return 1;
    }

    /**
     * 批量插入
     * @param qqchCompleteDesignHandoverList
     * @return
     */
    @Transactional
    public int insertQqchCompleteDesignHandoverList(List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList) {
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverList) {
            qqchCompleteDesignHandover.setId(IdWorker.createId());
            qqchCompleteDesignHandover.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCompleteDesignHandover.setCreateUserName(SecurityUtils.getUserName());
            qqchCompleteDesignHandover.setCreateTime(DateUtils.getNowDate());
        }
        return qqchCompleteDesignHandoverMapper.insertQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);
    }

    /**
     * 批量修改
     * @param qqchCompleteDesignHandoverList
     * @return
     */
    @Transactional
    public int updateQqchCompleteDesignHandoverList(List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList) {
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverList) {
            qqchCompleteDesignHandover.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCompleteDesignHandover.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCompleteDesignHandoverMapper.updateQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);
    }

    /**
     * 批量删除
     * @param qqchCompleteDesignHandoverPkList
     * @return
     */
    @Transactional
    public int deleteQqchCompleteDesignHandoverByPks(List<Long> qqchCompleteDesignHandoverPkList) {
        return qqchCompleteDesignHandoverMapper.deleteQqchCompleteDesignHandoverByPks(qqchCompleteDesignHandoverPkList);
    }
}
