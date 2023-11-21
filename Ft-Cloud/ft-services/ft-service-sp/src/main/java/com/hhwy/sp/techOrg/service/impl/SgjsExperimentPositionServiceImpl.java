package com.hhwy.sp.techOrg.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.domain.CommonQqchMeasureExpRange;
import com.hhwy.sp.techOrg.domain.SgjsExperimentPosition;
import com.hhwy.sp.techOrg.mapper.SgjsExperimentPositionMapper;
import com.hhwy.sp.techOrg.service.ISgjsExperimentPositionService;
import com.hhwy.utils.idworker.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lcf
 * @date 2023-11-20 11:39:49
 * @remark
 */
@Service
public class SgjsExperimentPositionServiceImpl implements ISgjsExperimentPositionService {

    @Autowired
    private SgjsExperimentPositionMapper sgjsExperimentPositionMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    private Logger logger= LoggerFactory.getLogger(SgjsExperimentPositionServiceImpl.class);


    public SgjsExperimentPosition getSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        return sgjsExperimentPositionMapper.getSgjsExperimentPosition(sgjsExperimentPosition);
    }

    public List<SgjsExperimentPosition> getSgjsExperimentPositionList(SgjsExperimentPosition sgjsExperimentPosition) {
        return sgjsExperimentPositionMapper.getSgjsExperimentPositionList(sgjsExperimentPosition);
    }

    @Transactional
    public int insertSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setId(IdWorker.createId());
        sgjsExperimentPosition.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.insertSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int insertSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList) {
        for (SgjsExperimentPosition sgjsExperimentPosition : sgjsExperimentPositionList) {
            sgjsExperimentPosition.setId(IdWorker.createId());
            sgjsExperimentPosition.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentPosition.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentPositionMapper.insertSgjsExperimentPositionList(sgjsExperimentPositionList);
    }

    @Transactional
    public int updateSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.updateSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int updateSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList) {
        for (SgjsExperimentPosition sgjsExperimentPosition : sgjsExperimentPositionList) {
            sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentPositionMapper.updateSgjsExperimentPositionList(sgjsExperimentPositionList);
    }

    @Transactional
    public int deleteSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.deleteSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int deleteSgjsExperimentPositionByPks(List<Long> sgjsExperimentPositionPkList) {
        return sgjsExperimentPositionMapper.deleteSgjsExperimentPositionByPks(sgjsExperimentPositionPkList);
    }

    @Override
    public AjaxResult sync(Map<String, Object> map) {
        CommonQqchMeasureExpRange info=new CommonQqchMeasureExpRange();
        String dataType=map.get("dataType")+"";
        if(StringUtils.isEmpty(dataType)){
            logger.error("dataType不能为空");
            return AjaxResult.error("dataType不能为空");
        }
        info.setDataType(dataType);
        AjaxResult result = pmServiceApi.qqchMeasureExpRangeList(info);
        return result;
    }
}
