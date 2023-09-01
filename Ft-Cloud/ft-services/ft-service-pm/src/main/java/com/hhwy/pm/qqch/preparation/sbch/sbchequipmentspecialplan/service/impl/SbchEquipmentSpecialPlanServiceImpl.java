package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlan;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.mapper.SbchEquipmentSpecialPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 特种设备风险识别和措施策划Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-06
 */
@Service
public class SbchEquipmentSpecialPlanServiceImpl implements ISbchEquipmentSpecialPlanService {
    @Autowired
    private SbchEquipmentSpecialPlanMapper sbchEquipmentSpecialPlanMapper;

    @Autowired
    private ISbchEquipmentSpecialPlanDetailsService detailsService;
    
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";

    /**
     * 查询特种设备风险识别和措施策划
     * 
     * @param id 特种设备风险识别和措施策划ID
     * @return 特种设备风险识别和措施策划
     */
    @Override
    public SbchEquipmentSpecialPlan selectSbchEquipmentSpecialPlanById(Long id) {
        return sbchEquipmentSpecialPlanMapper.selectSbchEquipmentSpecialPlanById(id);
    }

    /**
     * 查询特种设备风险识别和措施策划列表
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 特种设备风险识别和措施策划
     */
    @Override
    //@CustomDatascope(alias = "c")
    public List<SbchEquipmentSpecialPlan> selectSbchEquipmentSpecialPlanList(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
        return sbchEquipmentSpecialPlanMapper.selectSbchEquipmentSpecialPlanList(sbchEquipmentSpecialPlan);
    }

    /**
     * 新增特种设备风险识别和措施策划
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchEquipmentSpecialPlan(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {

        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialPlanDetails> detailList = sbchEquipmentSpecialPlan.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentSpecialPlan.setId(id);
        // 设置单据编码
        sbchEquipmentSpecialPlan.setAdjustCode(genCodeService.getSetCode(CodeEnum.EQU_SPECIAL));
        EntityUtils.setCreateUpdateInfo(sbchEquipmentSpecialPlan);
        // 新增
        this.sbchEquipmentSpecialPlanMapper.insertSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecialPlan.getId(),false);
    }

    /**
     * 修改特种设备风险识别和措施策划
     * 
     * @param sbchEquipmentSpecialPlan 特种设备风险识别和措施策划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchEquipmentSpecialPlan(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
//        EntityUtils.setUpdateInfo(sbchEquipmentSpecialPlan);
        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialPlanDetails> detailList = sbchEquipmentSpecialPlan.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
        // 修改
        sbchEquipmentSpecialPlanMapper.updateSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecialPlan.getId(),false);
    }

    /**
     * 删除特种设备风险识别和措施策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialPlanByIds(String ids) {
        return sbchEquipmentSpecialPlanMapper.deleteSbchEquipmentSpecialPlanByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除特种设备风险识别和措施策划信息
     * 
     * @param id 特种设备风险识别和措施策划ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialPlanById(Long id) {
        return sbchEquipmentSpecialPlanMapper.deleteSbchEquipmentSpecialPlanById(id);
    }

    @Override
    public SbchEquipmentSpecialPlan getList(BigDecimal version) {
        SbchEquipmentSpecialPlan returnVo = new SbchEquipmentSpecialPlan();
        version = VersionUtil.getVersion("sbch_equipment_special_plan", version);
        SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan = new SbchEquipmentSpecialPlan();
        sbchEquipmentSpecialPlan.setVersionNo(version);
        List<SbchEquipmentSpecialPlan> sbchEquipmentSpecialPlans = sbchEquipmentSpecialPlanMapper.selectSbchEquipmentSpecialPlanList(sbchEquipmentSpecialPlan);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecialPlans)){
            SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan1 = sbchEquipmentSpecialPlans.get(0);
            returnVo = sbchEquipmentSpecialPlan1;
            //详情列表
            SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails = new SbchEquipmentSpecialPlanDetails();
            sbchEquipmentSpecialPlanDetails.setMainId(sbchEquipmentSpecialPlan1.getId());
            List<SbchEquipmentSpecialPlanDetails> detailsList = detailsService.selectSbchEquipmentSpecialPlanDetailsList(sbchEquipmentSpecialPlanDetails);
            returnVo.setDetailsList(detailsList);
        }else{
            SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails = new SbchEquipmentSpecialPlanDetails();
            sbchEquipmentSpecialPlanDetails.setMainId(0L);
            List<SbchEquipmentSpecialPlanDetails> detailsList = detailsService.selectSbchEquipmentSpecialPlanDetailsList(sbchEquipmentSpecialPlanDetails);
            returnVo.setDetailsList(detailsList);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
        List<SbchEquipmentSpecialPlanDetails> detailsList = sbchEquipmentSpecialPlan.getDetailsList();
        SbchEquipmentSpecialPlan temp = new SbchEquipmentSpecialPlan();
        temp.setVersion(sbchEquipmentSpecialPlan.getVersion());
        List<SbchEquipmentSpecialPlan> sbchEquipmentSpecialPlans = sbchEquipmentSpecialPlanMapper.selectSbchEquipmentSpecialPlanList(temp);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecialPlans)){
            sbchEquipmentSpecialPlan.setId(sbchEquipmentSpecialPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentSpecialPlan);
            sbchEquipmentSpecialPlanMapper.updateSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan);
        }else{
            sbchEquipmentSpecialPlan.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_SPECIALPLAN);
            sbchEquipmentSpecialPlan.setUnicode(code);
            sbchEquipmentSpecialPlan.setTitleName("特种设备风险识别与措施策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentSpecialPlan);
            // 设置单据编码
            sbchEquipmentSpecialPlan.setTitle(sbchEquipmentSpecialPlan.getTitleName());
            sbchEquipmentSpecialPlan.setAdjustCode(code+"-"+sbchEquipmentSpecialPlan.getVersion().setScale(0));
            sbchEquipmentSpecialPlanMapper.insertSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan);
        }
        //校验数据必填
        if("1".equals(sbchEquipmentSpecialPlan.getButtonMark())||"2".equals(sbchEquipmentSpecialPlan.getButtonMark())){//确认
            if(!ObjectNullUtil.isEmpty(detailsList)){
                JyDetailsUtil.jyDetails(detailsList, ValidationGroups.Save.class);
            }
        }
        //子表
        detailsService.insertOrEditBatchByMainId(detailsList,sbchEquipmentSpecialPlan.getId(),false);
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchEquipmentSpecialPlan.getButtonMark())){
            //插入确认记录
            String menuId = sbchEquipmentSpecialPlan.getMenuId();
            String stageIdentity = sbchEquipmentSpecialPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
