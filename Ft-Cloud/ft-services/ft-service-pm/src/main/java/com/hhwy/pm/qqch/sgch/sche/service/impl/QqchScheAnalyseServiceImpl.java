package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheAnalyseMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheAnalyseService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (CollectionUtils.isEmpty(dealSaveDto)) return;
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
        if (CollectionUtils.isEmpty(dealSaveDto)) return;
        QqchScheAnalyse qqchScheAnalyse = dealSaveDto.get(0);
        // 保存不做校验
        if (PmConstant.ZERO.equals(qqchScheAnalyse.getSubmitFlag())) return;

        StringBuilder errorMsg = new StringBuilder();

        // 不能为空
        List<QqchScheAnalyse> nullList = dealSaveDto.stream().filter(item -> item.getScore() == null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nullList)) throw new RuntimeException("得分不能为空");

        // 得分不能重复
        List<BigDecimal> collect1 = dealSaveDto.stream().map(QqchScheAnalyse::getScore).distinct().collect(Collectors.toList());
        if (collect1.size() != dealSaveDto.size()) throw new RuntimeException("得分不能重复");

        // 按照得分排序
        List<QqchScheAnalyse> collect = dealSaveDto.stream().sorted(Comparator.comparing(QqchScheAnalyse::getScore)).collect(Collectors.toList());

        // 按照顺序放在队列里面
        // 差异性   越小得分越高
        LinkedList<BigDecimal> diffList = new LinkedList<>();
        // 进度线路 越小得分越高
        LinkedList<BigDecimal> lineList = new LinkedList<>();
        // 产值    越小得分越高
        LinkedList<BigDecimal> sumProdList = new LinkedList<>();
        // 公路产值 越大得分越高
        LinkedList<BigDecimal> roadList = new LinkedList<>();
        // 机场产值 越大得分越高
        LinkedList<BigDecimal> buildList = new LinkedList<>();

        for (QqchScheAnalyse scheAnalyse : collect) {
            BigDecimal diffMaxScore = null2Max(scheAnalyse.getDiffMaxScore());
            BigDecimal diffMinScore = null2Min(scheAnalyse.getDiffMinScore());

            BigDecimal lineMax = null2Max(scheAnalyse.getLineMaxScore());
            BigDecimal lineMin = null2Min(scheAnalyse.getLineMinScore());

            BigDecimal sumMaxScore = null2Max(scheAnalyse.getSumMaxScore());
            BigDecimal sumMinScore = null2Min(scheAnalyse.getSumMinScore());

            BigDecimal roadMaxScore = null2Max(scheAnalyse.getRoadMaxScore());
            BigDecimal roadMinScore = null2Min(scheAnalyse.getRoadMinScore());

            BigDecimal buildMaxScore = null2Max(scheAnalyse.getBuildMaxScore());
            BigDecimal buildMinScore = null2Min(scheAnalyse.getBuildMinScore());
            


        }


    }

    BigDecimal null2Max(BigDecimal decimal) {
        return decimal == null ? PmConstant.MAX_LONG_DECIMAL : decimal;
    }

    BigDecimal null2Min(BigDecimal decimal) {
        return decimal == null ? PmConstant.MIN_LONG_DECIMAL : decimal;
    }


}
