package com.hhwy.sp.common.sgjsAchievementAward.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.domain.TechManageCommon;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.mapper.SgjsAchievementAwardMapper;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-01-25 09:45:38
 * @remark
 */
@Service
public class SgjsAchievementAwardServiceImpl implements ISgjsAchievementAwardService {

    @Autowired
    private SgjsAchievementAwardMapper sgjsAchievementAwardMapper;


    public SgjsAchievementAward getSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        return sgjsAchievementAwardMapper.getSgjsAchievementAward(sgjsAchievementAward);
    }

    public List<SgjsAchievementAward> getSgjsAchievementAwardList(SgjsAchievementAward sgjsAchievementAward) {
        return sgjsAchievementAwardMapper.getSgjsAchievementAwardList(sgjsAchievementAward);
    }

    @Override
    public <T extends TechManageCommon> void setLedger(List<T> tList,Function<T,Long> getId,String belongBusiness){
        Map<Long, List<SgjsAchievementAward>> map = this.getMapByBelongBusiness(belongBusiness);
        for (T t : tList) {
            Long id = getId.apply(t);
            List<SgjsAchievementAward> awardList = map.get(id);
            if(CollectionUtils.isNotEmpty(awardList)){
                awardList = awardList.stream().sorted(Comparator.comparing(SgjsAchievementAward::getCreateTime)).collect(Collectors.toList());
                SgjsAchievementAward award = awardList.get(0);
                t.setApplyAward(award.getApplyAward());
                t.setAwardGrade(award.getAwardGrade());
                t.setAwardType(award.getAwardType());
                t.setGrantUnit(award.getGrantUnit());
                t.setAwardTime(award.getAwardTime());

                StringBuilder allAward = new StringBuilder();
                int i = 1;
                for (SgjsAchievementAward award1 : awardList) {
                    allAward.append(i).append("、");
                    this.append(allAward,award1.getApplyAward());
                    this.append(allAward,award1.getAwardGrade());
                    this.append(allAward,award1.getAwardType());
                    this.append(allAward,award1.getGrantUnit());
                    this.append(allAward,award1.getAwardTime());
                    allAward.append("\n");
                    i++;
                }
                t.setAllAward(allAward.toString());
            }
        }
    }

    private void append(StringBuilder source,Object append){
        if(append == null){
            return;
        }
        source.append(append).append("; ");
    }

    public <T> void setAwardList(T t, Function<T,Long> getId, BiConsumer<T,List<SgjsAchievementAward>> setAwardList){
        List<SgjsAchievementAward> awardList = this.getListByForeignId(getId.apply(t));
        setAwardList.accept(t,awardList);
    }

    @Override
    public List<SgjsAchievementAward> getListByForeignId(Long foreignId) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        SgjsAchievementAward query = new SgjsAchievementAward();
        query.setForeignId(foreignId);
        return sgjsAchievementAwardMapper.getSgjsAchievementAwardList(query);
    }

    @Override
    public List<SgjsAchievementAward> getListByForeignIds(Long[] foreignIds) {
        CommonAssert.notNull(foreignIds,"外键不能为空！");
        return sgjsAchievementAwardMapper.getListByForeignList(Arrays.asList(foreignIds));
    }

    @Override
    public Map<Long, List<SgjsAchievementAward>> getMapByBelongBusiness(String belongBusiness) {
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        SgjsAchievementAward query = new SgjsAchievementAward();
        query.setBelongBusiness(belongBusiness);
        List<SgjsAchievementAward> awardList = sgjsAchievementAwardMapper.getSgjsAchievementAwardList(query);
        DictUtil.dictValueToLabel(awardList,"award_type",SgjsAchievementAward::getAwardType,SgjsAchievementAward::setAwardType);
        return awardList.stream().collect(Collectors.groupingBy(SgjsAchievementAward::getForeignId));
    }

    @Override
    public void saveAchievementAward(Long foreignId, String belongBusiness, List<SgjsAchievementAward> awardList) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        //根据外键删除数据
        SgjsAchievementAward delParam = new SgjsAchievementAward();
        delParam.setForeignId(foreignId);
        sgjsAchievementAwardMapper.deleteSgjsAchievementAward(delParam);

        if(CollectionUtils.isEmpty(awardList)){
            return;
        }
        //插入数据
        for (SgjsAchievementAward award : awardList) {
            award.setId(IdWorker.createId());
            award.setForeignId(foreignId);
            award.setBelongBusiness(belongBusiness);
            award.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            award.setCreateUserName(SecurityUtils.getUserName());
            award.setCreateTime(DateUtils.getNowDate());
        }
        sgjsAchievementAwardMapper.insertSgjsAchievementAwardList(awardList);
    }

    @Transactional
    public int insertSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        sgjsAchievementAward.setId(IdWorker.createId());
        sgjsAchievementAward.setCreateUser(SecurityUtils.getUserName());
        sgjsAchievementAward.setCreateTime(DateUtils.getNowDate());
        return sgjsAchievementAwardMapper.insertSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int insertSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList) {
        for (SgjsAchievementAward sgjsAchievementAward : sgjsAchievementAwardList) {
            sgjsAchievementAward.setId(IdWorker.createId());
            sgjsAchievementAward.setCreateUser(SecurityUtils.getUserName());
            sgjsAchievementAward.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsAchievementAwardMapper.insertSgjsAchievementAwardList(sgjsAchievementAwardList);
    }

    @Transactional
    public int updateSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        sgjsAchievementAward.setUpdateUser(SecurityUtils.getUserName());
        sgjsAchievementAward.setUpdateTime(DateUtils.getNowDate());
        return sgjsAchievementAwardMapper.updateSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int updateSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList) {
        for (SgjsAchievementAward sgjsAchievementAward : sgjsAchievementAwardList) {
            sgjsAchievementAward.setUpdateUser(SecurityUtils.getUserName());
            sgjsAchievementAward.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsAchievementAwardMapper.updateSgjsAchievementAwardList(sgjsAchievementAwardList);
    }

    @Transactional
    public int deleteSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        return sgjsAchievementAwardMapper.deleteSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int deleteSgjsAchievementAwardByPks(List<Long> sgjsAchievementAwardPkList) {
        return sgjsAchievementAwardMapper.deleteSgjsAchievementAwardByPks(sgjsAchievementAwardPkList);
    }

    @Override
    public void deleteSgjsAchievementAwardByForeignId(Long foreignId) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        SgjsAchievementAward delParam = new SgjsAchievementAward();
        delParam.setForeignId(foreignId);
        sgjsAchievementAwardMapper.deleteSgjsAchievementAward(delParam);
    }
}
