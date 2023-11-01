package com.hhwy.pm.qqch.preparation.sbch.imported.material.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.mapper.SbchMaterialTranPlanDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.service.ISbchMaterialTranPlanDetailService;
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

import java.util.Date;
import java.util.List;

/**
 * 大型成套设备运输方案详情Service业务层处理
 * 
 * @author zq
 * @date 2022-12-12
 */
@Service
public class SbchMaterialTranPlanDetailServiceImpl implements ISbchMaterialTranPlanDetailService {
    @Autowired
    private SbchMaterialTranPlanDetailMapper sbchMaterialTranPlanDetailMapper;

    /**
     * 查询大型成套设备运输方案详情
     * 
     * @param id 大型成套设备运输方案详情ID
     * @return 大型成套设备运输方案详情
     */
    @Override
    public SbchMaterialTranPlanDetail selectSbchMaterialTranPlanDetailById(Long id) {
        return sbchMaterialTranPlanDetailMapper.selectSbchMaterialTranPlanDetailById(id);
    }

    /**
     * 查询大型成套设备运输方案详情列表
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 大型成套设备运输方案详情
     */
    @Override
    public List<SbchMaterialTranPlanDetail> selectSbchMaterialTranPlanDetailList(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail) {
        return sbchMaterialTranPlanDetailMapper.selectSbchMaterialTranPlanDetailList(sbchMaterialTranPlanDetail);
    }

    /**
     * 新增大型成套设备运输方案详情
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 结果
     */
    @Override
    public int insertSbchMaterialTranPlanDetail(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail) {

    sbchMaterialTranPlanDetail.setId(IdWorker.createId());

        sbchMaterialTranPlanDetail.setCreateTime(DateUtils.getNowDate());

        return sbchMaterialTranPlanDetailMapper.insertSbchMaterialTranPlanDetail(sbchMaterialTranPlanDetail);
    }

    /**
     * 修改大型成套设备运输方案详情
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 结果
     */
    @Override
    public int updateSbchMaterialTranPlanDetail(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail) {
        sbchMaterialTranPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchMaterialTranPlanDetailMapper.updateSbchMaterialTranPlanDetail(sbchMaterialTranPlanDetail);
    }

    /**
     * 删除大型成套设备运输方案详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchMaterialTranPlanDetailByIds(String ids) {
        return sbchMaterialTranPlanDetailMapper.deleteSbchMaterialTranPlanDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除大型成套设备运输方案详情信息
     * 
     * @param id 大型成套设备运输方案详情ID
     * @return 结果
     */
    public int deleteSbchMaterialTranPlanDetailById(Long id) {
        return sbchMaterialTranPlanDetailMapper.deleteSbchMaterialTranPlanDetailById(id);
    }

    @Transactional
    @Override
    public void batchInsert(List<SbchMaterialTranPlanDetail> detailList) {
        if(!ObjectNullUtil.isEmpty(detailList)){
//            StringBuffer str = new StringBuffer("");
//            for (SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail : detailList) {
//                EntityUtils.setCreateInfo(sbchMaterialTranPlanDetail);
//                sbchMaterialTranPlanDetail.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchMaterialTranPlanDetail, ValidationGroups.Save.class);
//                if(!beanValidationResult.isSuccess()){
//                    List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
//                    for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
//                        str=str.append(errorMessage.getMessage()+",");
//                    }
//                }
//            }
//            if(!"".equals(str.toString())){
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,str.toString());
//            }
            sbchMaterialTranPlanDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchMaterialTranPlanByPlanId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchMaterialTranPlanDetailMapper.deleteSbchMaterialTranPlanByPlanId(id,userId,date);
    }
}
