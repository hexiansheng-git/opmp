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
import org.apache.commons.lang3.StringUtils;
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
        // if (PmConstant.ZERO.equals(qqchScheAnalyse.getSubmitFlag())) return;

        StringBuilder errorMsg = new StringBuilder();

        // 不能为空
        List<QqchScheAnalyse> nullList = dealSaveDto.stream().filter(item -> item.getScore() == null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nullList)) throw new RuntimeException("得分不能为空");

        // 得分不能重复
        List<BigDecimal> collect1 = dealSaveDto.stream().map(QqchScheAnalyse::getScore).distinct().collect(Collectors.toList());
        if (collect1.size() != dealSaveDto.size()) throw new RuntimeException("得分不能重复");

        // 按照得分排序
        List<QqchScheAnalyse> collect = dealSaveDto.stream().sorted(Comparator.comparing(QqchScheAnalyse::getScore).reversed()).collect(Collectors.toList());

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

        for (int i = 0; i < collect.size(); i++) {

            QqchScheAnalyse scheAnalyse = collect.get(i);
            String score = scheAnalyse.getScore() + "";

            // 校验差异性(%)[S曲线差异值]
            checkMin(scheAnalyse.getDiffMaxScore(),
                    scheAnalyse.getDiffMinScore(),
                    diffList,
                    errorMsg,
                    score,
                    "差异性(%)[S曲线差异值]",
                    i,
                    collect.size());

            // 校验关键形象进度线路(%)
            checkMin(scheAnalyse.getLineMaxScore(),
                    scheAnalyse.getLineMinScore(),
                    lineList,
                    errorMsg,
                    score,
                    "关键形象进度线路(%)",
                    i,
                    collect.size());

            // 校验累计计量产值/累计施工产值(%)
            checkMin(scheAnalyse.getSumMaxScore(),
                    scheAnalyse.getSumMinScore(),
                    sumProdList,
                    errorMsg,
                    score,
                    "累计计量产值/累计施工产值(%)",
                    i,
                    collect.size());


            //  (公路/市政/铁路)万美元年平均产值 
            checkMax(scheAnalyse.getRoadMaxScore(),
                    scheAnalyse.getRoadMinScore(),
                    roadList,
                    errorMsg,
                    score,
                    " (公路/市政/铁路)万美元年平均产值 ",
                    i,
                    collect.size());


            // 校验  (其他项目)万美元年平均产值 
            checkMax(scheAnalyse.getBuildMaxScore(),
                    scheAnalyse.getBuildMinScore(),
                    buildList,
                    errorMsg,
                    score,
                    " (其他项目)万美元年平均产值 ",
                    i,
                    collect.size());


        }

        if (StringUtils.isNotEmpty(errorMsg.toString())) {
            throw new RuntimeException(errorMsg.toString());
        }


    }


    /**
     * 由小到大校验
     *
     * @param max      大值
     * @param min      小值
     * @param list     用于存放数据方便校验
     * @param errorMsg 错误信息
     * @param score    分数
     * @param msg      功能
     * @param idx      下标
     */
    private void checkMin(BigDecimal max,
                          BigDecimal min,
                          LinkedList<BigDecimal> list,
                          StringBuilder errorMsg,
                          String score,
                          String msg,
                          int idx,
                          int size) {

        // 最大值和最小值都为空的时候 啥也不干
        if (max == null && min == null) return;
        // 有一个为null的时候
        if (max == null || min == null) {
            max = null2Max(max);
            min = null2Min(min);
        }

        if (idx != 0 && !checkScoreMin(list, min)) {
            errorMsg.append("得分为【").append(score).append("】的").append(msg).append("最小值不能小于上一等级的最大值; ");
        }
        if (max.compareTo(min) < 0) {
            errorMsg.append("得分为【").append(score).append("】的").append(msg).append("最大值不能小于最低值; ");
        }


        list.add(min);
        list.add(max);

    }

    /**
     * 由大到小校验
     *
     * @param max      大值
     * @param min      小值
     * @param list     用于存放数据方便校验
     * @param errorMsg 错误信息
     * @param score    分数
     * @param msg      功能
     * @param idx      下标
     */
    private void checkMax(BigDecimal max,
                          BigDecimal min,
                          LinkedList<BigDecimal> list,
                          StringBuilder errorMsg,
                          String score,
                          String msg,
                          int idx,
                          int size) {

        // 最大值和最小值都为空的时候 啥也不干
        if (max == null && min == null) return;
        // 有一个为null的时候
        if (max == null || min == null) {
            max = null2Max(max);
            min = null2Min(min);
        }
        if (idx != 0 && !checkScoreMax(list, max)) {
            errorMsg.append("得分为【").append(score).append("】的").append(msg).append("最大值不能大于上一等级的最小值; ");
        }
        if (max.compareTo(min) < 0) {
            errorMsg.append("得分为【").append(score).append("】的").append(msg).append("最大值不能小于最低值; ");
        }
        // 依次放在集合
        list.add(max);
        list.add(min);
         

    }


    BigDecimal null2Max(BigDecimal decimal) {
        return decimal == null ? PmConstant.MAX_LONG_DECIMAL : decimal;
    }

    BigDecimal null2Min(BigDecimal decimal) {
        return decimal == null ? PmConstant.MIN_LONG_DECIMAL : decimal;
    }


    boolean checkScoreMax(LinkedList<BigDecimal> scoreList, BigDecimal min) {
        BigDecimal last = scoreList.getLast();
        return last.compareTo(min) >= 0;
    }

    boolean checkScoreMin(LinkedList<BigDecimal> scoreList, BigDecimal min) {
        BigDecimal last = scoreList.getLast();
        return last.compareTo(min) <= 0;
    }
}
