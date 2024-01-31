package com.hhwy.sp.techManagement.sgjsPatentDeclare.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.mapper.SgjsPatentDeclareMapper;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark
 */
@Service
public class SgjsPatentDeclareServiceImpl implements ISgjsPatentDeclareService {

    @Autowired
    private SgjsPatentDeclareMapper sgjsPatentDeclareMapper;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;

    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;


    @Override
    public SgjsPatentDeclare getSgjsPatentDeclareById(Long id, String type) {
        CommonAssert.notNull(id,"id不能为空！");
        SgjsPatentDeclare declare = sgjsPatentDeclareMapper.getSgjsPatentDeclareById(id);
        //TODO 设置专家数据
        List<SgjsExpertLibrary> libraryList = sgjsExpertLibraryService.getListByForeignId(id);
        declare.setLibraryList(libraryList);
        if("1".equals(type)){

        }
        if("2".equals(type)){
            //设置成果数据
            sgjsAchievementAwardService.setAwardList(declare,SgjsPatentDeclare::getId,SgjsPatentDeclare::setAwardList);
        }
        return declare;
    }

    public SgjsPatentDeclare getSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        return sgjsPatentDeclareMapper.getSgjsPatentDeclare(sgjsPatentDeclare);
    }

    public List<SgjsPatentDeclare> getSgjsPatentDeclareList(PatentDeclareQueryVo queryVo) {
        List<SgjsPatentDeclare> patentDeclareList = sgjsPatentDeclareMapper.getSgjsPatentDeclareList(queryVo);
        sgjsAchievementAwardService.setLedger(patentDeclareList,SgjsPatentDeclare::getId, BelongBusiness.BELONG_BUSINESS_7);
        return patentDeclareList;
    }

    @Transactional
    public int insertSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        sgjsPatentDeclare.setCreateUser(SecurityUtils.getUserName());
        sgjsPatentDeclare.setCreateTime(DateUtils.getNowDate());
        return sgjsPatentDeclareMapper.insertSgjsPatentDeclare(sgjsPatentDeclare);
    }

    @Transactional
    public int insertSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList) {
        for (SgjsPatentDeclare sgjsPatentDeclare : sgjsPatentDeclareList) {
            sgjsPatentDeclare.setId(IdWorker.createId());
            sgjsPatentDeclare.setCreateUser(SecurityUtils.getUserName());
            sgjsPatentDeclare.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPatentDeclareMapper.insertSgjsPatentDeclareList(sgjsPatentDeclareList);
    }

    @Transactional
    public int updateSgjsPatentDeclare(SgjsPatentDeclare sgjsPatentDeclare) {
        sgjsPatentDeclare.setUpdateUser(SecurityUtils.getUserName());
        sgjsPatentDeclare.setUpdateTime(DateUtils.getNowDate());
        return sgjsPatentDeclareMapper.updateSgjsPatentDeclare(sgjsPatentDeclare);
    }

    @Transactional
    public int updateSgjsPatentDeclareList(List<SgjsPatentDeclare> sgjsPatentDeclareList) {
        for (SgjsPatentDeclare sgjsPatentDeclare : sgjsPatentDeclareList) {
            sgjsPatentDeclare.setUpdateUser(SecurityUtils.getUserName());
            sgjsPatentDeclare.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPatentDeclareMapper.updateSgjsPatentDeclareList(sgjsPatentDeclareList);
    }

    @Transactional
    public void deleteSgjsPatentDeclareById(Long id) {
        CommonAssert.notNull(id,"id不能为空");
        sgjsPatentDeclareMapper.deleteSgjsPatentDeclareById(id);

        //TODO 删除专家数据

        //删除成果奖励数据
        sgjsAchievementAwardService.deleteSgjsAchievementAwardByForeignId(id);
    }

    @Transactional
    public int deleteSgjsPatentDeclareByPks(List<Long> sgjsPatentDeclarePkList) {
        return sgjsPatentDeclareMapper.deleteSgjsPatentDeclareByPks(sgjsPatentDeclarePkList);
    }

    /**
     * 保存
     *
     * @param patentDeclare
     * @return
     */
    @Override
    @Transactional
    public Long save(SgjsPatentDeclare patentDeclare) {
        String saveType = patentDeclare.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空");

        Long id;
        if("1".equals(saveType)){
            //新增
            id = IdWorker.createId();
            patentDeclare.setId(id);
            checkPatentNumberSingle(id,patentDeclare.getPatentNumber());
            this.insertSgjsPatentDeclare(patentDeclare);
        }else if("2".equals(saveType)){
            //修改
            id = patentDeclare.getId();
            checkPatentNumberSingle(id,patentDeclare.getPatentNumber());
            this.updateSgjsPatentDeclare(patentDeclare);
        }else {
            throw new RuntimeException("保存类型错误");
        }

        //保存专家数据
        List<SgjsExpertLibrary> libraryList = patentDeclare.getLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id,BelongBusiness.BELONG_BUSINESS_7,libraryList);

        //保存成果登记数据
        List<SgjsAchievementAward> awardList = patentDeclare.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id,BelongBusiness.BELONG_BUSINESS_7,awardList);

        return id;
    }

    /**
     * 校验专利号唯一
     * @param id 数据id
     * @param patentNumber 专利号
     */
    private void checkPatentNumberSingle(Long id, String patentNumber){
        if(StringUtils.isBlank(patentNumber)){
            return;
        }
        int count = sgjsPatentDeclareMapper.getCountByPatentNumberExpectId(id, patentNumber);
        if(count > 0){
            throw new RuntimeException("专利号已存在，请重新编辑！");
        }
    }

    @Override
    public void submit(SgjsPatentDeclare patentDeclare) {

    }

    @Override
    public List<SgjsPatentDeclare> getListByIds(List<Long> ids) {
        List<SgjsPatentDeclare> patentDeclareList = sgjsPatentDeclareMapper.getListByIds(ids);
        sgjsAchievementAwardService.setLedger(patentDeclareList,SgjsPatentDeclare::getId, BelongBusiness.BELONG_BUSINESS_7);
        return patentDeclareList;
    }
}
