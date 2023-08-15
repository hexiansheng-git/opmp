package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpRangeMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveTreeList(List<QqchMeasureExpRange> expRangeList) {
        if (CollectionUtils.isEmpty(expRangeList)) return;
        for (QqchMeasureExpRange qqchMeasureExpRange : expRangeList) {
            qqchMeasureExpRange.setId(IdWorker.createId());
        }
        this.qqchMeasureExpRangeMapper.insertQqchMeasureExpRangeList(expRangeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(QqchMeasureExpDTO expVO) {
        IQqchMeasureExpRangeService thisBean = SpringUtils.getBean(IQqchMeasureExpRangeService.class);
        thisBean.saveTreeList(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(), expVO.getExpRangeList()));
        personService.saveList(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(), expVO.getPersonList()));
        QqchMeasureOrg org = expVO.getOrg();
        orgService.save(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(),org));
    }
}
