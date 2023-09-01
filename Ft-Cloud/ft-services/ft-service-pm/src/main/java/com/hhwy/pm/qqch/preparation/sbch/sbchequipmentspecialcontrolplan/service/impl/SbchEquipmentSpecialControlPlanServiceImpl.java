package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlan;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.mapper.SbchEquipmentSpecialControlPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.ISbchEquipmentSpecialControlPlanDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.ISbchEquipmentSpecialControlPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
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
import java.util.List;
import java.util.Map;

/**
 * 特种设备过程管控策划Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-07
 */
@Service
public class SbchEquipmentSpecialControlPlanServiceImpl implements ISbchEquipmentSpecialControlPlanService {
    @Autowired
    private SbchEquipmentSpecialControlPlanMapper sbchEquipmentSpecialControlPlanMapper;
    @Autowired
    private ISbchEquipmentSpecialControlPlanDetailsService detailsService;
//    @Autowired
//    private WzchCommonService wzchCommonService;
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
//    @Override
//    public CommonBaseEntity baseInfo(Map<String, String> map) {
//        // 主键
//        // 新增时候为空 两种解决方案:
//        // 1. 用户提交流程时可以先保存数据,保存接口生成id返回给前端,前端拿到id再提交流程
//        // 2. 前端在请求该接口的时候, 后台就将id生成给前端
//        String id = map.get("id");
//        // 操作类型 1-新增; 2-编辑; 3-详情; 4-调整
//        String type = map.get("type");
//        SbchEquipmentSpecialControlPlan busData = new SbchEquipmentSpecialControlPlan();
//
//        if (ONE.equals(type)) {
//            busData.setId(IdWorker.createId());
//            String code = genCodeService.getCode(CodeEnum.EQU_SPECIALCONTROLPLAN);
//            code += genCodeService.fillString(1, 2);
//            busData.setAdjustCode(code);
//            // 设置创建信息
//            EntityUtils.setCreateUpdateInfo(busData);
//            return busData;
//        } else {
//            long busId = Long.parseLong(id);
//            BeanUtils.copyProperties(this.selectSbchEquipmentSpecialControlPlanById(busId), busData);
//            SbchEquipmentSpecialControlPlanDetails detail = new SbchEquipmentSpecialControlPlanDetails();
//            detail.setMainId(busId);
//            detail.setDelFlag("0");
//            /*协作单位详情*/
//            List<SbchEquipmentSpecialControlPlanDetails> detailList = detailsService.selectSbchEquipmentSpecialControlPlanDetailsList(detail);
//            busData.setDetailsList(detailList);
//            return busData;
//        }
//    }
    /**
     * 查询特种设备过程管控策划
     * 
     * @param id 特种设备过程管控策划ID
     * @return 特种设备过程管控策划
     */
    @Override
    public SbchEquipmentSpecialControlPlan selectSbchEquipmentSpecialControlPlanById(Long id) {
        return sbchEquipmentSpecialControlPlanMapper.selectSbchEquipmentSpecialControlPlanById(id);
    }

    /**
     * 查询特种设备过程管控策划列表
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 特种设备过程管控策划
     */
    @SelfEmpty(clazz = SbchEquipmentSpecialControlPlan.class)
    @Override
    //@CustomDatascope(alias = "c")
    public List<SbchEquipmentSpecialControlPlan> selectSbchEquipmentSpecialControlPlanList(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
        return sbchEquipmentSpecialControlPlanMapper.selectSbchEquipmentSpecialControlPlanList(sbchEquipmentSpecialControlPlan);
    }

    /**
     * 新增特种设备过程管控策划
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchEquipmentSpecialControlPlan(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {

        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialControlPlanDetails> detailList = sbchEquipmentSpecialControlPlan.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentSpecialControlPlan.setId(id);
        // 设置单据编码
        sbchEquipmentSpecialControlPlan.setAdjustCode(genCodeService.getSetCode(CodeEnum.EQU_SPECIALCONTROLPLAN));
        EntityUtils.setCreateUpdateInfo(sbchEquipmentSpecialControlPlan);
        // 新增
        this.sbchEquipmentSpecialControlPlanMapper.insertSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecialControlPlan.getId(),false);
    }

    /**
     * 修改特种设备过程管控策划
     * 
     * @param sbchEquipmentSpecialControlPlan 特种设备过程管控策划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchEquipmentSpecialControlPlan(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
//        EntityUtils.setUpdateInfo(sbchEquipmentSpecialControlPlan);
        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialControlPlanDetails> detailList = sbchEquipmentSpecialControlPlan.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
        // 修改
        sbchEquipmentSpecialControlPlanMapper.updateSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecialControlPlan.getId(),false);
    }

    /**
     * 删除特种设备过程管控策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialControlPlanByIds(String ids) {
        return sbchEquipmentSpecialControlPlanMapper.deleteSbchEquipmentSpecialControlPlanByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除特种设备过程管控策划信息
     * 
     * @param id 特种设备过程管控策划ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialControlPlanById(Long id) {
        return sbchEquipmentSpecialControlPlanMapper.deleteSbchEquipmentSpecialControlPlanById(id);
    }

    @Override
    public SbchEquipmentSpecialControlPlan getList(BigDecimal version) {
        SbchEquipmentSpecialControlPlan returnVo = new SbchEquipmentSpecialControlPlan();
        version = VersionUtil.getVersion("sbch_equipment_special_control_plan", version);
        SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan = new SbchEquipmentSpecialControlPlan();
        sbchEquipmentSpecialControlPlan.setVersionNo(version);
        List<SbchEquipmentSpecialControlPlan> sbchEquipmentSpecialControlPlans = sbchEquipmentSpecialControlPlanMapper.selectSbchEquipmentSpecialControlPlanList(sbchEquipmentSpecialControlPlan);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecialControlPlans)){
            SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan1 = sbchEquipmentSpecialControlPlans.get(0);
            returnVo = sbchEquipmentSpecialControlPlan1;
            //详情列表
            SbchEquipmentSpecialControlPlanDetails sbchEquipmentSpecialControlPlanDetails = new SbchEquipmentSpecialControlPlanDetails();
            sbchEquipmentSpecialControlPlanDetails.setMainId(sbchEquipmentSpecialControlPlan1.getId());
            List<SbchEquipmentSpecialControlPlanDetails> sbchEquipmentSpecialControlPlanDetails1 = detailsService.selectSbchEquipmentSpecialControlPlanDetailsList(sbchEquipmentSpecialControlPlanDetails);
            returnVo.setDetailsList(sbchEquipmentSpecialControlPlanDetails1);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
        List<SbchEquipmentSpecialControlPlanDetails> detailsList = sbchEquipmentSpecialControlPlan.getDetailsList();
        SbchEquipmentSpecialControlPlan temp = new SbchEquipmentSpecialControlPlan();
        temp.setVersion(sbchEquipmentSpecialControlPlan.getVersion());
        List<SbchEquipmentSpecialControlPlan> sbchEquipmentSpecialControlPlans = sbchEquipmentSpecialControlPlanMapper.selectSbchEquipmentSpecialControlPlanList(temp);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecialControlPlans)){
            sbchEquipmentSpecialControlPlan.setId(sbchEquipmentSpecialControlPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentSpecialControlPlan);
            sbchEquipmentSpecialControlPlanMapper.updateSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan);
        }else{
            sbchEquipmentSpecialControlPlan.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_SPECIALCONTROLPLAN);
            sbchEquipmentSpecialControlPlan.setUnicode(code);
            sbchEquipmentSpecialControlPlan.setTitleName("特种设备风险识别与措施策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentSpecialControlPlan);
            // 设置单据编码
            sbchEquipmentSpecialControlPlan.setTitle(sbchEquipmentSpecialControlPlan.getTitleName());
            sbchEquipmentSpecialControlPlan.setAdjustCode(code+"-"+sbchEquipmentSpecialControlPlan.getVersion().setScale(0));
            sbchEquipmentSpecialControlPlanMapper.insertSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan);
        }
        //校验数据必填
        if("1".equals(sbchEquipmentSpecialControlPlan.getButtonMark())||"2".equals(sbchEquipmentSpecialControlPlan.getButtonMark())){//确认
            if(!ObjectNullUtil.isEmpty(detailsList)){
                JyDetailsUtil.jyDetails(detailsList, ValidationGroups.Save.class);
            }
        }
        //子表
        detailsService.insertOrEditBatchByMainId(detailsList,sbchEquipmentSpecialControlPlan.getId(),false);
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchEquipmentSpecialControlPlan.getButtonMark())){
            //插入确认记录
            String menuId = sbchEquipmentSpecialControlPlan.getMenuId();
            String stageIdentity = sbchEquipmentSpecialControlPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
