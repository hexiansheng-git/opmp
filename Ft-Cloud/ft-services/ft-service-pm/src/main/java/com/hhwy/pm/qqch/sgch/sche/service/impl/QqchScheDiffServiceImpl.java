package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheDiffMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffService;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (CollectionUtils.isEmpty(dealSaveDto)) return;
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

        if (CollectionUtils.isEmpty(dealSaveDto)) return;
        QqchScheDiff param1 = dealSaveDto.get(0);
        // 如果当前的提交状态是报错的话  就不做校验
        if ("0".equals(param1.getSubmitFlag())) return;
        StringBuilder errorMsg = new StringBuilder();

        // 不能为空
        List<QqchScheDiff> nullList = dealSaveDto.stream().filter(item -> item.getWarnLevel() == null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nullList)) throw new RuntimeException("进度差异化预警等级不能为空");


        // 不能重复
        List<String> collect1 = dealSaveDto.stream().map(QqchScheDiff::getWarnLevel).distinct().collect(Collectors.toList());
        if (collect1.size() != dealSaveDto.size()) throw new RuntimeException("进度差异化预警等级不能重复");
        // 按照差异化风险等级排序
        List<QqchScheDiff> collect = dealSaveDto.stream().sorted(Comparator.comparing(QqchScheDiff::getWarnLevel)).collect(Collectors.toList());
        // 按照顺序放在队列里面
        LinkedList<BigDecimal> scoreList = new LinkedList<>();

        // 获取字典
        LinkedHashMap<String, String> dictDataName = DictUtil.getDictDataName("warn_level");
        for (int i = 0; i < collect.size(); i++) {

            QqchScheDiff qqchScheDiff = collect.get(i);
            String label = dictDataName.get(qqchScheDiff.getWarnLevel());
            BigDecimal maxScore = qqchScheDiff.getMaxScore();
            BigDecimal minScore = qqchScheDiff.getMinScore();
            if (maxScore == null || minScore == null) {
                errorMsg.append("进度差异化预警等级为【").append(label).append("】的最高分或最低分不能为空;");
                // 打破本次循环
                continue;
            }
            if (i != 0) {
                if (!checkScore(scoreList, maxScore))
                    errorMsg.append("进度差异化预警等级为【").append(label).append("】的最高分不能大于上一等级的最低值");
                if (!checkScore(scoreList, minScore))
                    errorMsg.append("进度差异化预警等级为【").append(label).append("】的最低分不能大于最高分");
            }
            scoreList.push(maxScore);
            scoreList.push(minScore);

            if (!StringUtils.isEmpty(errorMsg.toString())) {
                throw new RuntimeException(errorMsg.toString());
            }
        }
    }

    boolean checkScore(LinkedList<BigDecimal> scoreList, BigDecimal score) {
        BigDecimal last = scoreList.getLast();
        scoreList.add(score);
        // 如果最后一个数据大于当前值的话判断当前值和最后一个元素的值的大小
        return last.compareTo(score) >= 0;
    }




}
