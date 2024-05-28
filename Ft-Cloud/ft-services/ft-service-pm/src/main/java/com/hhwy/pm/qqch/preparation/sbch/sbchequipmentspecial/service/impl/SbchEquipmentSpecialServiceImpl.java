package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.mapper.SbchEquipmentSpecialMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 特种设备管理Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-05
 */
@Service
public class SbchEquipmentSpecialServiceImpl implements ISbchEquipmentSpecialService {
    @Autowired
    private SbchEquipmentSpecialMapper sbchEquipmentSpecialMapper;
    @Autowired
    private ISbchEquipmentSpecialDetailsService detailsService;

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
    /*@Override
    public CommonBaseEntity baseInfo(Map<String, String> map) {
        // 主键
        // 新增时候为空 两种解决方案:
        // 1. 用户提交流程时可以先保存数据,保存接口生成id返回给前端,前端拿到id再提交流程
        // 2. 前端在请求该接口的时候, 后台就将id生成给前端
        String id = map.get("id");
        // 操作类型 1-新增; 2-编辑; 3-详情; 4-调整
        String type = map.get("type");
        SbchEquipmentSpecial busData = new SbchEquipmentSpecial();

        if (ONE.equals(type)) {
            busData.setId(IdWorker.createId());
            String code = genCodeService.getCode(CodeEnum.EQU_SPECIAL);
            code += genCodeService.fillString(1, 2);
            busData.setAdjustCode(code);
            // 设置创建信息
            EntityUtils.setCreateUpdateInfo(busData);
            return busData;
        } else {
            long busId = Long.parseLong(id);
            BeanUtils.copyProperties(this.selectSbchEquipmentSpecialById(busId), busData);
            SbchEquipmentSpecialDetails detail = new SbchEquipmentSpecialDetails();
            detail.setMainId(busId);
            detail.setDelFlag("0");
            *//*协作单位详情*//*
            List<SbchEquipmentSpecialDetails> detailList = detailsService.selectSbchEquipmentSpecialDetailsList(detail);
            //redis中获取设备name
            Map<String, String> busAndMaterialMap = new HashMap<>();
            busAndMaterialMap.put("materialName", "materialName");
            List<SbchEquipmentSpecialDetails> detailsListVersion1 = wzchCommonService.setMaterialInfo(detailList, "materialCode", busAndMaterialMap);
            busData.setDetailsList(detailsListVersion1);
            return busData;
        }
    }*/

    /**
     * 查询特种设备管理
     * 
     * @param id 特种设备管理ID
     * @return 特种设备管理
     */
    @Override
    public SbchEquipmentSpecial selectSbchEquipmentSpecialById(Long id) {
        return sbchEquipmentSpecialMapper.selectSbchEquipmentSpecialById(id);
    }

    /**
     * 查询特种设备管理列表
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 特种设备管理
     */
    @SelfEmpty(clazz = SbchEquipmentSpecial.class)
    @Override
    //@CustomDatascope(alias = "c")
    public List<SbchEquipmentSpecial> selectSbchEquipmentSpecialList(SbchEquipmentSpecial sbchEquipmentSpecial) {
        return sbchEquipmentSpecialMapper.selectSbchEquipmentSpecialList(sbchEquipmentSpecial);
    }

    /**
     * 新增特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial) {

        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialDetails> detailList = sbchEquipmentSpecial.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentSpecial.setId(id);
        // 设置单据编码
        sbchEquipmentSpecial.setAdjustCode(genCodeService.getSetCode(CodeEnum.EQU_SPECIAL));
        EntityUtils.setCreateUpdateInfo(sbchEquipmentSpecial);
        // 新增
        this.sbchEquipmentSpecialMapper.insertSbchEquipmentSpecial(sbchEquipmentSpecial);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecial.getId(),false);

    }

    /**
     * 修改特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial) {
//        EntityUtils.setUpdateInfo(sbchEquipmentSpecial);
        // 获取前端传入的设备明细
        List<SbchEquipmentSpecialDetails> detailList = sbchEquipmentSpecial.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
        // 修改
        sbchEquipmentSpecialMapper.updateSbchEquipmentSpecial(sbchEquipmentSpecial);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentSpecial.getId(),false);

    }

    /**
     * 删除特种设备管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialByIds(String ids) {
        return sbchEquipmentSpecialMapper.deleteSbchEquipmentSpecialByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除特种设备管理信息
     * 
     * @param id 特种设备管理ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialById(Long id) {
        return sbchEquipmentSpecialMapper.deleteSbchEquipmentSpecialById(id);
    }

    @Override
    public SbchEquipmentSpecial getList(BigDecimal version) {
        SbchEquipmentSpecial returnVo = new SbchEquipmentSpecial();
        version = VersionUtil.getVersion("sbch_equipment_special", version);
        SbchEquipmentSpecial sbchEquipmentSpecial = new SbchEquipmentSpecial();
        sbchEquipmentSpecial.setVersionNo(version);
        List<SbchEquipmentSpecial> sbchEquipmentSpecials = sbchEquipmentSpecialMapper.selectSbchEquipmentSpecialList(sbchEquipmentSpecial);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecials)){
            SbchEquipmentSpecial sbchEquipmentSpecial1 = sbchEquipmentSpecials.get(0);
            returnVo = sbchEquipmentSpecial1;
            //详情列表
            SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails = new SbchEquipmentSpecialDetails();
            sbchEquipmentSpecialDetails.setMainId(sbchEquipmentSpecial1.getId());
            List<SbchEquipmentSpecialDetails> sbchEquipmentSpecialDetailList = detailsService.selectSbchEquipmentSpecialDetailsList(sbchEquipmentSpecialDetails);
            returnVo.setDetailsList(sbchEquipmentSpecialDetailList);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    @Transactional
    public AjaxResult batchSave(SbchEquipmentSpecial sbchEquipmentSpecial) {
        List<SbchEquipmentSpecialDetails> detailsList = sbchEquipmentSpecial.getDetailsList();

        Map<String, Long> mapgroup = detailsList.stream().collect(Collectors.groupingBy(s -> s.getManageCode(), Collectors.counting()));
        List<String> collect3 = mapgroup.keySet().stream().filter(key -> mapgroup.get(key) > 1).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect3)) {
            return AjaxResult.error("管理编号 "+collect3.stream().collect(Collectors.joining(","))+" 不能重复");
        }

        //校验数据必填
        if("1".equals(sbchEquipmentSpecial.getButtonMark())||"2".equals(sbchEquipmentSpecial.getButtonMark())){//确认
            if(!ObjectNullUtil.isEmpty(detailsList)){
                JyDetailsUtil.jyDetails(detailsList, ValidationGroups.Save.class);
            }
        }

        SbchEquipmentSpecial temp = new SbchEquipmentSpecial();
        temp.setVersion(sbchEquipmentSpecial.getVersion());
        List<SbchEquipmentSpecial> sbchEquipmentSpecials = sbchEquipmentSpecialMapper.selectSbchEquipmentSpecialList(temp);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSpecials)){
            sbchEquipmentSpecial.setId(sbchEquipmentSpecials.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentSpecial);
            sbchEquipmentSpecialMapper.updateSbchEquipmentSpecial(sbchEquipmentSpecial);
        }else{
            sbchEquipmentSpecial.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_SPECIAL);
            sbchEquipmentSpecial.setUnicode(code);
            sbchEquipmentSpecial.setTitleName("特种设备清单");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentSpecial);
            // 设置单据编码
            sbchEquipmentSpecial.setTitle(sbchEquipmentSpecial.getTitleName());
            sbchEquipmentSpecial.setAdjustCode(code+"-"+sbchEquipmentSpecial.getVersion().setScale(0));

            sbchEquipmentSpecialMapper.insertSbchEquipmentSpecial(sbchEquipmentSpecial);
        }
        //子表
        detailsService.insertOrEditBatchByMainId(detailsList,sbchEquipmentSpecial.getId(),false);
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchEquipmentSpecial.getButtonMark())){
            //插入确认记录
            String menuId = sbchEquipmentSpecial.getMenuId();
            String stageIdentity = sbchEquipmentSpecial.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
        return AjaxResult.success();
    }
}
