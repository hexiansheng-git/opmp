package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlaningArrangeMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlaningArrangeService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark 
 */
@Service
public class QqchWorkPlaningArrangeServiceImpl implements IQqchWorkPlaningArrangeService {

    @Autowired
    private QqchWorkPlaningArrangeMapper qqchWorkPlaningArrangeMapper;

                                                                                                                                                                                                                                                                                                                
    public QqchWorkPlaningArrange getQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    public List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeList(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrangeList(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setId(IdWorker.createId());
        qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrangeList(List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList) {
        for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
            qqchWorkPlaningArrange.setId(IdWorker.createId());
            qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
    }

    @Transactional
    public int updateQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int updateQqchWorkPlaningArrangeList(List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList) {
            for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
        }
    
    @Transactional
    public int deleteQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int deleteQqchWorkPlaningArrangeByPks(List<Long> qqchWorkPlaningArrangePkList) {
            return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrangeByPks(qqchWorkPlaningArrangePkList);
        }
    }
