package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;
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
    public QqchCompleteDesignHandoverVo getQqchCompleteDesignHandoverVo(QqchCompleteDesignHandover qqchCompleteDesignHandover) {
        QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo = new QqchCompleteDesignHandoverVo();
        List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList = qqchCompleteDesignHandoverMapper.getQqchCompleteDesignHandoverList(qqchCompleteDesignHandover);
        qqchCompleteDesignHandoverVo.setQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);

        //TODO 获取确认状态

        return qqchCompleteDesignHandoverVo;
    }

    /**
     * 保存
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @Override
    public void save(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        this.editQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverVo.getQqchCompleteDesignHandoverList());
    }

    /**
     * 确认
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        this.editQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverVo.getQqchCompleteDesignHandoverList());

        //TODO 修改确认状态

    }

    /**
     * 批量编辑
     * @param qqchCompleteDesignHandoverListParam
     * @return
     */
    @Transactional
    public int editQqchCompleteDesignHandoverList(List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverListParam) {
        List<QqchCompleteDesignHandover> insertList = new ArrayList<>();
        List<QqchCompleteDesignHandover> updateList = new ArrayList<>();
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverListParam) {
            Long id = qqchCompleteDesignHandover.getId();
            if(id == null){
                qqchCompleteDesignHandover.setId(IdWorker.createId());
                qqchCompleteDesignHandover.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchCompleteDesignHandover.setCreateUserName(SecurityUtils.getUserName());
                qqchCompleteDesignHandover.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchCompleteDesignHandover);
            }else {
                qqchCompleteDesignHandover.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchCompleteDesignHandover.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchCompleteDesignHandover);
            }
        }
        if(insertList.size() > 0){
            qqchCompleteDesignHandoverMapper.insertQqchCompleteDesignHandoverList(insertList);
        }
        if(updateList.size() > 0){
            qqchCompleteDesignHandoverMapper.updateQqchCompleteDesignHandoverList(updateList);
        }
        return 1;
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
