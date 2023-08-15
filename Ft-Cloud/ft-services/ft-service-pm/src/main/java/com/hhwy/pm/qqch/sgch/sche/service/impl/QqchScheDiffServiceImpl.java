package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheDiffMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
@Service
public class QqchScheDiffServiceImpl implements IQqchScheDiffService {


    private static final String TN = "qqch_sche_diff";

    @Resource
    private QqchScheDiffMapper qqchScheDiffMapper;


    public QqchScheDiff getQqchScheDiff(QqchScheDiff qqchScheDiff) {
        return qqchScheDiffMapper.getQqchScheDiff(qqchScheDiff);
    }

    public List<QqchScheDiff> getQqchScheDiffList(QqchScheDiff qqchScheDiff) {
        return qqchScheDiffMapper.getQqchScheDiffList(qqchScheDiff);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setId(IdWorker.createId());
        qqchScheDiff.setCreateUser(SecurityUtils.getUserName());
        qqchScheDiff.setCreateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.insertQqchScheDiff(qqchScheDiff);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList) {
        for (QqchScheDiff qqchScheDiff : qqchScheDiffList) {
            qqchScheDiff.setId(IdWorker.createId());
            qqchScheDiff.setCreateUser(SecurityUtils.getUserName());
            qqchScheDiff.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffMapper.insertQqchScheDiffList(qqchScheDiffList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.updateQqchScheDiff(qqchScheDiff);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList) {
        for (QqchScheDiff qqchScheDiff : qqchScheDiffList) {
            qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
            qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffMapper.updateQqchScheDiffList(qqchScheDiffList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.deleteQqchScheDiff(qqchScheDiff);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheDiffByPks(List<Long> qqchScheDiffPkList) {
        return qqchScheDiffMapper.deleteQqchScheDiffByPks(qqchScheDiffPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchScheDiff> dealSaveDto) {
        if (CollectionUtils.isEmpty(dealSaveDto)) throw new CustomBusinessException("进度差异化评定计算方法不能为空");
        this.checkData(dealSaveDto);
        for (QqchScheDiff qqchScheDiff : dealSaveDto) {
            qqchScheDiff.setId(IdWorker.createId());
        }
        this.qqchScheDiffMapper.insertQqchScheDiffList(dealSaveDto);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchScheDiff> getList(QqchScheDiff dealSaveDto) {
        List<QqchScheDiff> qqchScheDiffList = this.qqchScheDiffMapper.getQqchScheDiffList(dealSaveDto);
        return qqchScheDiffList;
    }

    /**
     * 校验数据
     *
     * @param dealSaveDto
     */
    private void checkData(List<QqchScheDiff> dealSaveDto) {

        // 主要是校验


    }
}
