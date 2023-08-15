package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheAnalyseMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheAnalyseService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark
 */
@Service
public class QqchScheAnalyseServiceImpl implements IQqchScheAnalyseService {


    private final static String TN = "qqch_sche_analyse";
    @Autowired
    private QqchScheAnalyseMapper qqchScheAnalyseMapper;


    public QqchScheAnalyse getQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        return qqchScheAnalyseMapper.getQqchScheAnalyse(qqchScheAnalyse);
    }

    public List<QqchScheAnalyse> getQqchScheAnalyseList(QqchScheAnalyse qqchScheAnalyse) {
        return qqchScheAnalyseMapper.getQqchScheAnalyseList(qqchScheAnalyse);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setId(IdWorker.createId());
        qqchScheAnalyse.setCreateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setCreateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.insertQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList) {
        for (QqchScheAnalyse qqchScheAnalyse : qqchScheAnalyseList) {
            qqchScheAnalyse.setId(IdWorker.createId());
            qqchScheAnalyse.setCreateUser(SecurityUtils.getUserName());
            qqchScheAnalyse.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheAnalyseMapper.insertQqchScheAnalyseList(qqchScheAnalyseList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.updateQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList) {
        for (QqchScheAnalyse qqchScheAnalyse : qqchScheAnalyseList) {
            qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
            qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheAnalyseMapper.updateQqchScheAnalyseList(qqchScheAnalyseList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.deleteQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheAnalyseByPks(List<Long> qqchScheAnalysePkList) {
        return qqchScheAnalyseMapper.deleteQqchScheAnalyseByPks(qqchScheAnalysePkList);
    }

    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Override
    public void saveList(List<QqchScheAnalyse> dealSaveDto) {
        if (CollectionUtils.isEmpty(dealSaveDto)) throw new CustomBusinessException("进度分析要素不能为空");;
        this.checkData(dealSaveDto);
        this.qqchScheAnalyseMapper.insertQqchScheAnalyseList(dealSaveDto);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchScheAnalyse> getList(QqchScheAnalyse dealSaveDto) {
        List<QqchScheAnalyse> qqchScheAnalyseList = this.getQqchScheAnalyseList(dealSaveDto);
        return qqchScheAnalyseList;
    }

    private void checkData(List<QqchScheAnalyse> dealSaveDto) {
        // 校验区间值

    }
}
