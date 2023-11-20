package com.hhwy.sp.techOrg.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techOrg.domain.SgjsMeasurePosition;
import com.hhwy.sp.techOrg.mapper.SgjsMeasurePositionMapper;
import com.hhwy.sp.techOrg.service.ISgjsMeasurePositionService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author lcf
 * @date 2023-11-20 10:41:18
 * @remark
 */
@Service
public class SgjsMeasurePositionServiceImpl implements ISgjsMeasurePositionService {

    @Autowired
    private SgjsMeasurePositionMapper sgjsMeasurePositionMapper;


    public SgjsMeasurePosition getSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition) {
        return sgjsMeasurePositionMapper.getSgjsMeasurePosition(sgjsMeasurePosition);
    }

    public List<SgjsMeasurePosition> getSgjsMeasurePositionList(SgjsMeasurePosition sgjsMeasurePosition) {
        return sgjsMeasurePositionMapper.getSgjsMeasurePositionList(sgjsMeasurePosition);
    }

    @Transactional
    public int insertSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition) {
        sgjsMeasurePosition.setId(IdWorker.createId());
        sgjsMeasurePosition.setCreateUser(SecurityUtils.getUserName());
        sgjsMeasurePosition.setCreateTime(DateUtils.getNowDate());
        return sgjsMeasurePositionMapper.insertSgjsMeasurePosition(sgjsMeasurePosition);
    }

    @Transactional
    public int insertSgjsMeasurePositionList(List<SgjsMeasurePosition> sgjsMeasurePositionList) {
        for (SgjsMeasurePosition sgjsMeasurePosition : sgjsMeasurePositionList) {
            sgjsMeasurePosition.setId(IdWorker.createId());
            sgjsMeasurePosition.setCreateUser(SecurityUtils.getUserName());
            sgjsMeasurePosition.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsMeasurePositionMapper.insertSgjsMeasurePositionList(sgjsMeasurePositionList);
    }

    @Transactional
    public int updateSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition) {
        sgjsMeasurePosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsMeasurePosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsMeasurePositionMapper.updateSgjsMeasurePosition(sgjsMeasurePosition);
    }

    @Transactional
    public int updateSgjsMeasurePositionList(List<SgjsMeasurePosition> sgjsMeasurePositionList) {
        for (SgjsMeasurePosition sgjsMeasurePosition : sgjsMeasurePositionList) {
            sgjsMeasurePosition.setUpdateUser(SecurityUtils.getUserName());
            sgjsMeasurePosition.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsMeasurePositionMapper.updateSgjsMeasurePositionList(sgjsMeasurePositionList);
    }

    @Transactional
    public int deleteSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition) {
        sgjsMeasurePosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsMeasurePosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsMeasurePositionMapper.deleteSgjsMeasurePosition(sgjsMeasurePosition);
    }

    @Transactional
    public int deleteSgjsMeasurePositionByPks(List<Long> sgjsMeasurePositionPkList) {
        return sgjsMeasurePositionMapper.deleteSgjsMeasurePositionByPks(sgjsMeasurePositionPkList);
    }
}
