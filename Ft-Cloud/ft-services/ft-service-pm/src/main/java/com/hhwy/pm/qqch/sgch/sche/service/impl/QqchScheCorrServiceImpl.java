package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheCorrMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheCorrService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 15:12:04
 * @remark
 */
@Service
public class QqchScheCorrServiceImpl implements IQqchScheCorrService {

    @Autowired
    private QqchScheCorrMapper qqchScheCorrMapper;


    private final static String TN = "qqch_sche_corr";


    public QqchScheCorr getQqchScheCorr(QqchScheCorr qqchScheCorr) {
        return qqchScheCorrMapper.getQqchScheCorr(qqchScheCorr);
    }

    public List<QqchScheCorr> getQqchScheCorrList(QqchScheCorr qqchScheCorr) {
        return qqchScheCorrMapper.getQqchScheCorrList(qqchScheCorr);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheCorr(QqchScheCorr qqchScheCorr) {
        qqchScheCorr.setId(IdWorker.createId());
        qqchScheCorr.setCreateUser(SecurityUtils.getUserName());
        qqchScheCorr.setCreateTime(DateUtils.getNowDate());
        return qqchScheCorrMapper.insertQqchScheCorr(qqchScheCorr);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheCorrList(List<QqchScheCorr> qqchScheCorrList) {
        for (QqchScheCorr qqchScheCorr : qqchScheCorrList) {
            qqchScheCorr.setId(IdWorker.createId());
            qqchScheCorr.setCreateUser(SecurityUtils.getUserName());
            qqchScheCorr.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheCorrMapper.insertQqchScheCorrList(qqchScheCorrList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheCorr(QqchScheCorr qqchScheCorr) {
        qqchScheCorr.setUpdateUser(SecurityUtils.getUserName());
        qqchScheCorr.setUpdateTime(DateUtils.getNowDate());
        return qqchScheCorrMapper.updateQqchScheCorr(qqchScheCorr);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheCorrList(List<QqchScheCorr> qqchScheCorrList) {
        for (QqchScheCorr qqchScheCorr : qqchScheCorrList) {
            qqchScheCorr.setUpdateUser(SecurityUtils.getUserName());
            qqchScheCorr.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheCorrMapper.updateQqchScheCorrList(qqchScheCorrList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheCorr(QqchScheCorr qqchScheCorr) {
        qqchScheCorr.setUpdateUser(SecurityUtils.getUserName());
        qqchScheCorr.setUpdateTime(DateUtils.getNowDate());
        return qqchScheCorrMapper.deleteQqchScheCorr(qqchScheCorr);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheCorrByPks(List<Long> qqchScheCorrPkList) {
        return qqchScheCorrMapper.deleteQqchScheCorrByPks(qqchScheCorrPkList);
    }


    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Override
    public void saveList(List<QqchScheCorr> dealSaveDto) {
        if (CollectionUtils.isEmpty(dealSaveDto) || !(dealSaveDto.get(0) instanceof QqchScheCorr)) return;
        this.checkData(dealSaveDto);
        this.qqchScheCorrMapper.insertQqchScheCorrList(dealSaveDto);

    }

    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchScheCorr> getList(QqchScheCorr dealSaveDto) {
        List<QqchScheCorr> qqchScheCorrList = this.getQqchScheCorrList(dealSaveDto);
        return qqchScheCorrList;
    }

    private void checkData(List<QqchScheCorr> dealSaveDto) {
        // 校验数据
    }
}
