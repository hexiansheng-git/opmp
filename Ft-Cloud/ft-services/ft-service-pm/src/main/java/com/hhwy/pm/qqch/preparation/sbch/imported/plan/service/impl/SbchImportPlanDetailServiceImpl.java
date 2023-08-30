package com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.mapper.SbchImportPlanDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.BeanValidationResult;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 进口方案详情Service业务层处理
 * 
 * @author zq
 * @date 2022-12-06
 */
@Service
public class SbchImportPlanDetailServiceImpl implements ISbchImportPlanDetailService {
    @Autowired
    private SbchImportPlanDetailMapper sbchImportPlanDetailMapper;
//    @Autowired
//    private IMaterialInfoService materialInfoService;

    /**
     * 查询进口方案详情
     * 
     * @param id 进口方案详情ID
     * @return 进口方案详情
     */
    @Override
    public SbchImportPlanDetail selectSbchImportPlanDetailById(Long id) {
        return sbchImportPlanDetailMapper.selectSbchImportPlanDetailById(id);
    }

    /**
     * 查询进口方案详情列表
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 进口方案详情
     */
    @Override
    public List<SbchImportPlanDetail> selectSbchImportPlanDetailList(SbchImportPlanDetail sbchImportPlanDetail) {
        return sbchImportPlanDetailMapper.selectSbchImportPlanDetailList(sbchImportPlanDetail);
    }

    /**
     * 新增进口方案详情
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 结果
     */
    @Override
    public int insertSbchImportPlanDetail(SbchImportPlanDetail sbchImportPlanDetail) {

    sbchImportPlanDetail.setId(IdWorker.createId());

        sbchImportPlanDetail.setCreateTime(DateUtils.getNowDate());

        return sbchImportPlanDetailMapper.insertSbchImportPlanDetail(sbchImportPlanDetail);
    }

    /**
     * 修改进口方案详情
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 结果
     */
    @Override
    public int updateSbchImportPlanDetail(SbchImportPlanDetail sbchImportPlanDetail) {
        sbchImportPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchImportPlanDetailMapper.updateSbchImportPlanDetail(sbchImportPlanDetail);
    }

    /**
     * 删除进口方案详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchImportPlanDetailByIds(String ids) {
        return sbchImportPlanDetailMapper.deleteSbchImportPlanDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除进口方案详情信息
     * 
     * @param id 进口方案详情ID
     * @return 结果
     */
    public int deleteSbchImportPlanDetailById(Long id) {
        return sbchImportPlanDetailMapper.deleteSbchImportPlanDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchImportPlanDetail> detailList) {
        if(null!=detailList && detailList.size()>0){
//            StringBuffer str = new StringBuffer("");
            for (SbchImportPlanDetail sbchImportPlanDetail : detailList) {
                EntityUtils.setCreateInfo(sbchImportPlanDetail);
//                sbchImportPlanDetail.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchImportPlanDetail, ValidationGroups.Save.class);
//                if(!beanValidationResult.isSuccess()){
//                    List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
//                    for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
//                        str=str.append(errorMessage.getMessage()+",");
//                    }
//                }
            }
//            if(!"".equals(str.toString())){
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,str.toString());
//            }
            sbchImportPlanDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    public List<Map<String,Object>> getSbchImportPlanDetailList(SbchImportPlanDetail sbchImportPlanDetail) {
        List<Map<String,Object>> list = sbchImportPlanDetailMapper.getSbchImportPlanDetailList(sbchImportPlanDetail);
        List<String> materialCodes = list.stream().map(t -> t.get("materialCodes").toString()).collect(Collectors.toList());
        ArrayList<String> materialCodeList = new ArrayList<>();
        for (String materialCode : materialCodes) {
            String[] split = materialCode.split(",");
            for (String s : split) {
                materialCodeList.add(s);
            }
        }
//        List<MaterialInfo> materialList = materialInfoService.selectMaterialInfoListByCodes(materialCodeList);
        List<MaterialInfo> materialList = new ArrayList<>();
        Map<String, String> materialNameMap = new HashMap<>();
        if(!ObjectNullUtil.isEmpty(materialList)){
            materialNameMap = materialList.stream().collect(Collectors.groupingBy(t -> t.getMaterialCode(), Collectors.collectingAndThen(Collectors.toList(), t -> t.get(0).getMaterialName())));
        }
        for (Map<String, Object> stringStringMap : list) {
            String[] materialCodeStr = stringStringMap.get("materialCodes").toString().split(",");
            String materialNames = "";
            for (String materialCode : materialCodeStr) {
                String materialName = materialNameMap.get(materialCode);
                if(!ObjectNullUtil.isEmpty(materialName)){
                    materialNames+=","+materialName;
                }
            }
            stringStringMap.put("id",String.valueOf(stringStringMap.get("id")));
            stringStringMap.put("planId",String.valueOf(stringStringMap.get("planId")));
            stringStringMap.put("materialNames",ObjectNullUtil.isEmpty(materialNames) ? materialNames :materialNames.substring(1));
        }
        return list;
    }

    @Override
    @Transactional
    public void deleteSbchImportPlanDetailByPlanId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchImportPlanDetailMapper.deleteSbchImportPlanDetailByPlanId(id,userId,date);
    }
}
