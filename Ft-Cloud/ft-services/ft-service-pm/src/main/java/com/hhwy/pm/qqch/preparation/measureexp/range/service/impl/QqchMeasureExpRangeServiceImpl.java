package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpRangeMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Autowired
    private IQqchChangeService qqchChangeService;


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
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchMeasureExpRange> getQqchMeasureExpRangeListByVersion(QqchMeasureExpRange qqchMeasureExpRangeParam) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRangeList(qqchMeasureExpRangeParam);
    }


    @Override
    public void saveTreeList(QqchMeasureExpDTO expVO) {
        //删除旧数据
        QqchMeasureExpRange delParam = new QqchMeasureExpRange();
        delParam.setVersion(expVO.getVersion());
        delParam.setDataType(expVO.getDataType());
        qqchMeasureExpRangeMapper.deleteQqchMeasureExpRange(delParam);

        List<QqchMeasureExpRange> expRangeList = expVO.getExpRangeList();
        if(CollectionUtils.isEmpty(expRangeList)){
            return;
        }
        List<QqchMeasureExpRange> qqchMeasureExpRanges = CompileEntity.dealSaveDto(expVO, expRangeList);
        if (CollectionUtils.isEmpty(qqchMeasureExpRanges)) return;
        for (QqchMeasureExpRange qqchMeasureExpRange : qqchMeasureExpRanges) {
            qqchMeasureExpRange.setDataType(expVO.getDataType());
        }
        this.qqchMeasureExpRangeMapper.insertQqchMeasureExpRangeList(qqchMeasureExpRanges);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(QqchMeasureExpDTO expVO) {
//        IQqchMeasureExpRangeService bean = (IQqchMeasureExpRangeService) AopContext.currentProxy();
//        // TODO 找黄真要
//        List<QqchMeasureExpRange> qqchMeasureExpRanges = CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(),"asasasasa", expVO.getExpRangeList());
//        bean.saveTreeList(qqchMeasureExpRanges);
//        personService.saveList(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(), "asasasasa",expVO.getPersonList()));
//        QqchMeasureOrg org = expVO.getOrg();
//        orgService.save(CompileEntity.dealSaveDto(expVO.getVersion(), "asasasasa",expVO.getSubmitFlag(),org));
    }

    @Override
    public BigDecimal getMaxVersion(BigDecimal version, String dataType) {
        if (version == null) {
            version  = qqchChangeService.effectVersion();
        }
        /*查询当前最接近（小于等于）指定版本的版本号*/
        version = qqchMeasureExpRangeMapper.selectLessOrEqualAssignVersion(version,dataType);
        if(version == null){
            version = BigDecimal.ONE;
        }
        return version;
    }
}
