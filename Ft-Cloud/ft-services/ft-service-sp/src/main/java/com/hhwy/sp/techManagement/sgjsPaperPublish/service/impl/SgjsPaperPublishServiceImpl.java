package com.hhwy.sp.techManagement.sgjsPaperPublish.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishExportVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.mapper.SgjsPaperPublishMapper;
import com.hhwy.sp.techManagement.sgjsPaperPublish.service.ISgjsPaperPublishService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark
 */
@Service
public class SgjsPaperPublishServiceImpl implements ISgjsPaperPublishService {

    @Autowired
    private SgjsPaperPublishMapper sgjsPaperPublishMapper;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;

    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;


    @Override
    public SgjsPaperPublish getSgjsPaperPublishById(Long id, String type) {
        SgjsPaperPublish paperPublish = sgjsPaperPublishMapper.getSgjsPaperPublishById(id);
        //TODO 设置专家数据
        List<SgjsExpertLibrary> libraryList = sgjsExpertLibraryService.getListByForeignId(id);
        paperPublish.setLibraryList(libraryList);
        if("1".equals(type)){

        }
        if("2".equals(type)){
            //设置成果数据
            sgjsAchievementAwardService.setAwardList(paperPublish, SgjsPaperPublish::getId,SgjsPaperPublish::setAwardList);
        }
        return paperPublish;
    }

    public SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        return sgjsPaperPublishMapper.getSgjsPaperPublish(sgjsPaperPublish);
    }

    public List<SgjsPaperPublish> getSgjsPaperPublishList(PaperPublishQueryVo queryVo) {
        return sgjsPaperPublishMapper.getSgjsPaperPublishList(queryVo);
    }

    @Transactional
    public int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.insertSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int insertSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setId(IdWorker.createId());
            sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.insertSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.updateSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int updateSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.updateSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.deleteSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int deleteSgjsPaperPublishByPks(List<Long> sgjsPaperPublishPkList) {
        return sgjsPaperPublishMapper.deleteSgjsPaperPublishByPks(sgjsPaperPublishPkList);
    }

    @Override
    @Transactional
    public Long save(SgjsPaperPublish paperPublish) {
        String saveType = paperPublish.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空");

        String isSubmit = paperPublish.getIsSubmit();
        if("1".equals(isSubmit)){
            paperPublish.setCurrentState("2");
        }

        Long id;
        if("1".equals(saveType)){
            //新增
            id = IdWorker.createId();
            paperPublish.setId(id);
            this.insertSgjsPaperPublish(paperPublish);
        }else if("2".equals(saveType)){
            //修改
            id = paperPublish.getId();
            this.updateSgjsPaperPublish(paperPublish);
        }else {
            throw new RuntimeException("保存类型错误");
        }

        //保存专家数据
        List<SgjsExpertLibrary> libraryList = paperPublish.getLibraryList();
        sgjsExpertLibraryService.saveSgjsExpertLibraryList(id, BelongBusiness.BELONG_BUSINESS_8,libraryList);

        //保存成果登记数据
        List<SgjsAchievementAward> awardList = paperPublish.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id,BelongBusiness.BELONG_BUSINESS_8,awardList);

        return id;
    }

    @Override
    public void deleteSgjsPaperPublishById(Long id) {
        CommonAssert.notNull(id,"id不能为空");
        sgjsPaperPublishMapper.deleteSgjsPaperPublishById(id);
        //删除专家数据
        sgjsExpertLibraryService.deleteSgjsExpertLibraryByForeignId(id);
        //删除成果奖励数据
        sgjsAchievementAwardService.deleteSgjsAchievementAwardByForeignId(id);
    }

    @Override
    public List<SgjsPaperPublish> getListByIds(List<Long> ids) {
        return sgjsPaperPublishMapper.getListByIds(ids);
    }

    @Override
    public List<PaperPublishExportVo> getExportVoList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        List<PaperPublishExportVo> exportVoList = new ArrayList<>();
        for (SgjsPaperPublish paperPublish : sgjsPaperPublishList) {
            PaperPublishExportVo exportVo = new PaperPublishExportVo();
            BeanUtils.copyProperties(paperPublish,exportVo);
            exportVoList.add(exportVo);
        }
        sgjsAchievementAwardService.setAllAwards(exportVoList,PaperPublishExportVo::getId,PaperPublishExportVo::setAllAward,BelongBusiness.BELONG_BUSINESS_8);
        return exportVoList;
    }
}
