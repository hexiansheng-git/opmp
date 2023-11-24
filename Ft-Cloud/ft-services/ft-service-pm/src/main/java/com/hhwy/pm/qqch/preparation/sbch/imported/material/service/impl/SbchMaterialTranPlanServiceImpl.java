package com.hhwy.pm.qqch.preparation.sbch.imported.material.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlan;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.mapper.SbchMaterialTranPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.service.ISbchMaterialTranPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.service.ISbchMaterialTranPlanService;
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

/**
 * 大型成套设备运输方案Service业务层处理
 * 
 * @author zq
 * @date 2022-12-12
 */
@Service
public class SbchMaterialTranPlanServiceImpl implements ISbchMaterialTranPlanService {
    @Autowired
    private SbchMaterialTranPlanMapper sbchMaterialTranPlanMapper;
    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private ISbchMaterialTranPlanDetailService sbchMaterialTranPlanDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询大型成套设备运输方案
     * 
     * @param id 大型成套设备运输方案ID
     * @return 大型成套设备运输方案
     */
    @Override
    public SbchMaterialTranPlan selectSbchMaterialTranPlanById(Long id) {
        return sbchMaterialTranPlanMapper.selectSbchMaterialTranPlanById(id);
    }

    /**
     * 查询大型成套设备运输方案列表
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 大型成套设备运输方案
     */
    @Override
    @SelfEmpty(clazz = SbchMaterialTranPlan.class)
    //@CustomDatascope(alias = "material")
    public List<SbchMaterialTranPlan> selectSbchMaterialTranPlanList(SbchMaterialTranPlan sbchMaterialTranPlan) {
        return sbchMaterialTranPlanMapper.selectSbchMaterialTranPlanList(sbchMaterialTranPlan);
    }

    /**
     * 新增大型成套设备运输方案
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchMaterialTranPlan(SbchMaterialTranPlan sbchMaterialTranPlan) {
//        EntityUtils.setCreateInfo(sbchMaterialTranPlan);
        sbchMaterialTranPlan.setId(IdWorker.createId());
        String code = genCodeService.getSetCode(CodeEnum.EQU_MATERIAL_TRAN);
        sbchMaterialTranPlan.setFormNo(code);

        List<SbchMaterialTranPlanDetail> detailList = sbchMaterialTranPlan.getDetailList();
        for (SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail : detailList) {
            String fileGroupId = sbchMaterialTranPlanDetail.getFileGroupId();
            BeanUtils.copyProperties(sbchMaterialTranPlan,sbchMaterialTranPlanDetail);
            sbchMaterialTranPlanDetail.setPlanId(sbchMaterialTranPlan.getId());
            sbchMaterialTranPlanDetail.setFileGroupId(fileGroupId);
        }
        sbchMaterialTranPlanDetailService.batchInsert(detailList);
        return sbchMaterialTranPlanMapper.insertSbchMaterialTranPlan(sbchMaterialTranPlan);
    }

    /**
     * 修改大型成套设备运输方案
     * 
     * @param sbchMaterialTranPlan 大型成套设备运输方案
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchMaterialTranPlan(SbchMaterialTranPlan sbchMaterialTranPlan) {
//        EntityUtils.setUpdateInfo(sbchMaterialTranPlan);
        List<SbchMaterialTranPlanDetail> detailList = sbchMaterialTranPlan.getDetailList();
        for (SbchMaterialTranPlanDetail detail : detailList) {
            String fileGroupId = detail.getFileGroupId();
            BeanUtils.copyProperties(sbchMaterialTranPlan,detail);
            detail.setPlanId(sbchMaterialTranPlan.getId());
            detail.setFileGroupId(fileGroupId);
        }
        //删除旧的数据
        sbchMaterialTranPlanDetailService.deleteSbchMaterialTranPlanByPlanId(sbchMaterialTranPlan.getId());
        //添加新的数据
        sbchMaterialTranPlanDetailService.batchInsert(detailList);
        return sbchMaterialTranPlanMapper.updateSbchMaterialTranPlan(sbchMaterialTranPlan);
    }

    /**
     * 删除大型成套设备运输方案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchMaterialTranPlanByIds(String ids) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchMaterialTranPlanMapper.deleteSbchMaterialTranPlanByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除大型成套设备运输方案信息
     * 
     * @param id 大型成套设备运输方案ID
     * @return 结果
     */
    public int deleteSbchMaterialTranPlanById(Long id) {
        return sbchMaterialTranPlanMapper.deleteSbchMaterialTranPlanById(id);
    }

    @Override
    public SbchMaterialTranPlan getList(BigDecimal version) {
        SbchMaterialTranPlan returnVo = new SbchMaterialTranPlan();
        version = VersionUtil.getVersion("sbch_material_tran_plan", version);

        SbchMaterialTranPlan sbchMaterialTranPlan = new SbchMaterialTranPlan();
        sbchMaterialTranPlan.setVersionNo(version);
        List<SbchMaterialTranPlan> sbchMaterialTranPlans = sbchMaterialTranPlanMapper.selectSbchMaterialTranPlanList(sbchMaterialTranPlan);
        if(!ObjectNullUtil.isEmpty(sbchMaterialTranPlans)){
            SbchMaterialTranPlan sbchMaterialTranPlan1 = sbchMaterialTranPlans.get(0);
            returnVo = sbchMaterialTranPlan1;
            //详情列表
            SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail = new SbchMaterialTranPlanDetail();
            sbchMaterialTranPlanDetail.setPlanId(sbchMaterialTranPlan1.getId());
            List<SbchMaterialTranPlanDetail> sbchMaterialTranPlanDetails = sbchMaterialTranPlanDetailService.selectSbchMaterialTranPlanDetailList(sbchMaterialTranPlanDetail);
            returnVo.setDetailList(sbchMaterialTranPlanDetails);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchMaterialTranPlan sbchMaterialTranPlan) {
        List<SbchMaterialTranPlanDetail> detailList = sbchMaterialTranPlan.getDetailList();
        SbchMaterialTranPlan temp = new SbchMaterialTranPlan();
        temp.setVersion(sbchMaterialTranPlan.getVersion());
        List<SbchMaterialTranPlan> sbchMaterialTranPlans = sbchMaterialTranPlanMapper.selectSbchMaterialTranPlanList(temp);
        if(!ObjectNullUtil.isEmpty(sbchMaterialTranPlans)){
            sbchMaterialTranPlan.setId(sbchMaterialTranPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchMaterialTranPlan);
            sbchMaterialTranPlanMapper.updateSbchMaterialTranPlan(sbchMaterialTranPlan);
        }else{
            sbchMaterialTranPlan.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_MATERIAL_TRAN);
            sbchMaterialTranPlan.setUnicode(code);
            sbchMaterialTranPlan.setTitleName("大型成套设备运输方案");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchMaterialTranPlan);
            sbchMaterialTranPlanMapper.insertSbchMaterialTranPlan(sbchMaterialTranPlan);
        }
        //删除子表
        sbchMaterialTranPlanDetailService.deleteSbchMaterialTranPlanByPlanId(sbchMaterialTranPlan.getId());
        if(!ObjectNullUtil.isEmpty(detailList)){
            //校验数据必填
            if("1".equals(sbchMaterialTranPlan.getButtonMark())||"2".equals(sbchMaterialTranPlan.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail : detailList) {
                BeanUtils.copyProperties(sbchMaterialTranPlan,sbchMaterialTranPlanDetail, "fileGroupId");
                sbchMaterialTranPlanDetail.setPlanId(sbchMaterialTranPlan.getId());
                EntityUtils.setCreateInfo(sbchMaterialTranPlanDetail);
                sbchMaterialTranPlanDetail.setId(IdWorker.createId());
            }
            //添加新的子表数据
            sbchMaterialTranPlanDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchMaterialTranPlan.getButtonMark())){
            //插入确认记录
            String menuId = sbchMaterialTranPlan.getMenuId();
            String stageIdentity = sbchMaterialTranPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
