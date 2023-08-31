package com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlan;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.mapper.SbchImportPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 进口方案Service业务层处理
 * 
 * @author zq
 * @date 2022-12-06
 */
@Service
public class SbchImportPlanServiceImpl implements ISbchImportPlanService {
    @Autowired
    private SbchImportPlanMapper sbchImportPlanMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchImportPlanDetailService sbchImportPlanDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询进口方案
     * 
     * @param id 进口方案ID
     * @return 进口方案
     */
    @Override
    public SbchImportPlan selectSbchImportPlanById(Long id) {
        return sbchImportPlanMapper.selectSbchImportPlanById(id);
    }

    /**
     * 查询进口方案列表
     * 
     * @param sbchImportPlan 进口方案
     * @return 进口方案
     */
    @Override
    @SelfEmpty(clazz = SbchImportPlan.class)
    //@CustomDatascope(alias = "plan")
    public List<SbchImportPlan> selectSbchImportPlanList(SbchImportPlan sbchImportPlan) {
        if(ObjectNullUtil.isEmpty(sbchImportPlan.getIds())){
            sbchImportPlan.setIds(null);
        }
        return sbchImportPlanMapper.selectSbchImportPlanList(sbchImportPlan);
    }

    /**
     * 新增进口方案
     * 
     * @param sbchImportPlan 进口方案
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchImportPlan(SbchImportPlan sbchImportPlan) {

//        EntityUtils.setCreateInfo(sbchImportPlan);
        sbchImportPlan.setId(IdWorker.createId());
        String code = genCodeService.getSetCode(CodeEnum.EQU_IMPORT_PLAN);
        sbchImportPlan.setFormNo(code);

        List<SbchImportPlanDetail> detailList = sbchImportPlan.getDetailList();
        for (SbchImportPlanDetail sbchImportPlanDetail : detailList) {
            BeanUtils.copyProperties(sbchImportPlan,sbchImportPlanDetail);
            sbchImportPlanDetail.setPlanId(sbchImportPlan.getId());
        }
        sbchImportPlanDetailService.batchInsert(detailList);
        return sbchImportPlanMapper.insertSbchImportPlan(sbchImportPlan);
    }

    /**
     * 修改进口方案
     * 
     * @param sbchImportPlan 进口方案
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchImportPlan(SbchImportPlan sbchImportPlan) {
//        EntityUtils.setUpdateInfo(sbchImportPlan);
        List<SbchImportPlanDetail> detailList = sbchImportPlan.getDetailList();
        for (SbchImportPlanDetail sbchImportPlanDetail : detailList) {
            BeanUtils.copyProperties(sbchImportPlan,sbchImportPlanDetail);
            sbchImportPlanDetail.setPlanId(sbchImportPlan.getId());
        }
        //删除旧的子表数据
        sbchImportPlanDetailService.deleteSbchImportPlanDetailByPlanId(sbchImportPlan.getId());
        //添加新的子表数据
        sbchImportPlanDetailService.batchInsert(detailList);
        return sbchImportPlanMapper.updateSbchImportPlan(sbchImportPlan);
    }

    /**
     * 删除进口方案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchImportPlanByIds(String ids) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchImportPlanMapper.deleteSbchImportPlanByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除进口方案信息
     * 
     * @param id 进口方案ID
     * @return 结果
     */
    public int deleteSbchImportPlanById(Long id) {
        return sbchImportPlanMapper.deleteSbchImportPlanById(id);
    }

    @Override
    public SbchImportPlan getList(BigDecimal version) {
        SbchImportPlan returnVo = new SbchImportPlan();
        version = VersionUtil.getVersion("sbch_import_plan", version);
        SbchImportPlan sbchImportPlan = new SbchImportPlan();
        sbchImportPlan.setVersionNo(version);
        List<SbchImportPlan> sbchImportPlans = sbchImportPlanMapper.selectSbchImportPlanList(sbchImportPlan);
        if(!ObjectNullUtil.isEmpty(sbchImportPlans)){
            SbchImportPlan sbchImportPlan1 = sbchImportPlans.get(0);
            returnVo = sbchImportPlan1;
            //详情列表
            SbchImportPlanDetail sbchImportPlanDetail = new SbchImportPlanDetail();
            sbchImportPlanDetail.setPlanId(sbchImportPlan1.getId());
            List<Map<String,Object>> sbchImportPlanDetails = sbchImportPlanDetailService.getSbchImportPlanDetailList(sbchImportPlanDetail);
            returnVo.setSbchImportPlanDetails(sbchImportPlanDetails);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchImportPlan sbchImportPlan) {
        List<SbchImportPlanDetail> detailList = sbchImportPlan.getDetailList();
        SbchImportPlan temp = new SbchImportPlan();
        temp.setVersion(sbchImportPlan.getVersion());
        List<SbchImportPlan> sbchImportPlans = sbchImportPlanMapper.selectSbchImportPlanList(temp);
        if(!ObjectNullUtil.isEmpty(sbchImportPlans)){
            sbchImportPlan.setId(sbchImportPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchImportPlan);
            sbchImportPlanMapper.updateSbchImportPlan(sbchImportPlan);
        }else{
            sbchImportPlan.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_IMPORT_PLAN);
            sbchImportPlan.setUnicode(code);
            sbchImportPlan.setTitleName("进口方案");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchImportPlan);
            sbchImportPlanMapper.insertSbchImportPlan(sbchImportPlan);
        }
        //删除
        sbchImportPlanDetailService.deleteSbchImportPlanDetailByPlanId(sbchImportPlan.getId());
        if(!ObjectNullUtil.isEmpty(detailList)){
            //校验数据必填
            if("1".equals(sbchImportPlan.getButtonMark())||"2".equals(sbchImportPlan.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchImportPlanDetail sbchImportPlanDetail : detailList) {
                BeanUtils.copyProperties(sbchImportPlan,sbchImportPlanDetail);
                sbchImportPlanDetail.setPlanId(sbchImportPlan.getId());
                EntityUtils.setCreateInfo(sbchImportPlanDetail);
                sbchImportPlanDetail.setId(IdWorker.createId());
            }
            //添加新的子表数据
            sbchImportPlanDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchImportPlan.getButtonMark())){
            //插入确认记录
            String menuId = sbchImportPlan.getMenuId();
            String stageIdentity = sbchImportPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
