package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpRangeMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.utils.idworker.IdWorker;

import javax.annotation.Resource;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark
 */
@Service
public class QqchMeasureExpRangeServiceImpl implements IQqchMeasureExpRangeService {

    private final static String TN = "qqch_measure_exp_range";
    @Resource
    private IQqchMeasureOrgService orgService;

    @Resource
    private IQqchMeasureExpPersonService personService;

    @Resource
    private QqchMeasureExpRangeMapper qqchMeasureExpRangeMapper;


    public QqchMeasureExpRange getQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRange(qqchMeasureExpRange);
    }

    public List<QqchMeasureExpRange> getQqchMeasureExpRangeList(QqchMeasureExpRange qqchMeasureExpRange) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRangeList(qqchMeasureExpRange);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setId(IdWorker.createId());
        qqchMeasureExpRange.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.insertQqchMeasureExpRange(qqchMeasureExpRange);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList) {
        for (QqchMeasureExpRange qqchMeasureExpRange : qqchMeasureExpRangeList) {
            qqchMeasureExpRange.setId(IdWorker.createId());
            qqchMeasureExpRange.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpRange.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpRangeMapper.insertQqchMeasureExpRangeList(qqchMeasureExpRangeList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.updateQqchMeasureExpRange(qqchMeasureExpRange);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList) {
        for (QqchMeasureExpRange qqchMeasureExpRange : qqchMeasureExpRangeList) {
            qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpRangeMapper.updateQqchMeasureExpRangeList(qqchMeasureExpRangeList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.deleteQqchMeasureExpRange(qqchMeasureExpRange);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpRangeByPks(List<Long> qqchMeasureExpRangePkList) {
        return qqchMeasureExpRangeMapper.deleteQqchMeasureExpRangeByPks(qqchMeasureExpRangePkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchMeasureExpRange> getQqchMeasureExpRangeListByVersion(QqchMeasureExpRange qqchMeasureExpRangeParam) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRangeList(qqchMeasureExpRangeParam);
    }


    @Override
    @CompileAspect(type = CompileOptEnum.SAVE, tableName = TN)
    public void saveTreeList(List<QqchMeasureExpRange> expRangeList) {
        for (QqchMeasureExpRange qqchMeasureExpRange : expRangeList) {
            qqchMeasureExpRange.setId(IdWorker.createId());
        }
        this.qqchMeasureExpRangeMapper.insertQqchMeasureExpRangeList(expRangeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(QqchMeasureExpDTO expVO) {
        IQqchMeasureExpRangeService thisBean = SpringUtils.getBean(IQqchMeasureExpRangeService.class);
        expVO.getExpRangeList().forEach(item -> {
            item.setVersion(expVO.getVersion());
            item.setSubmitFlag(expVO.getSubmitFlag());
        });
        expVO.getPersonList().forEach(item -> {
            item.setVersion(expVO.getVersion());
            item.setSubmitFlag(expVO.getSubmitFlag());
        });
        thisBean.saveTreeList(expVO.getExpRangeList());
        
        personService.saveList(expVO.getPersonList());
        QqchMeasureOrg org = expVO.getOrg();
        org.setVersion(expVO.getVersion());
        org.setSubmitFlag(expVO.getSubmitFlag());
        orgService.save(org);
    }
}
