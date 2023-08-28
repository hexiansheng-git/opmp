package com.hhwy.pm.qqch.preparation.sbch.staffing.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingDetail;
import com.hhwy.pm.qqch.preparation.sbch.staffing.mapper.SbchStaffingDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingDetailService;
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
 * 设备人员配置策划--设备人员详情Service业务层处理
 * 
 * @author zq
 * @date 2022-11-28
 */
@Service
public class SbchStaffingDetailServiceImpl implements ISbchStaffingDetailService {
    @Autowired
    private SbchStaffingDetailMapper sbchStaffingDetailMapper;

    /**
     * 查询设备人员配置策划--设备人员详情
     * 
     * @param id 设备人员配置策划--设备人员详情ID
     * @return 设备人员配置策划--设备人员详情
     */
    @Override
    public SbchStaffingDetail selectSbchStaffingDetailById(Long id) {
        return sbchStaffingDetailMapper.selectSbchStaffingDetailById(id);
    }

    /**
     * 查询设备人员配置策划--设备人员详情列表
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 设备人员配置策划--设备人员详情
     */
    @Override
    public List<SbchStaffingDetail> selectSbchStaffingDetailList(SbchStaffingDetail sbchStaffingDetail) {
        return sbchStaffingDetailMapper.selectSbchStaffingDetailList(sbchStaffingDetail);
    }

    /**
     * 新增设备人员配置策划--设备人员详情
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 结果
     */
    @Override
    public int insertSbchStaffingDetail(SbchStaffingDetail sbchStaffingDetail) {

    sbchStaffingDetail.setId(IdWorker.createId());

        sbchStaffingDetail.setCreateTime(DateUtils.getNowDate());

        return sbchStaffingDetailMapper.insertSbchStaffingDetail(sbchStaffingDetail);
    }

    /**
     * 修改设备人员配置策划--设备人员详情
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 结果
     */
    @Override
    public int updateSbchStaffingDetail(SbchStaffingDetail sbchStaffingDetail) {
        sbchStaffingDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchStaffingDetailMapper.updateSbchStaffingDetail(sbchStaffingDetail);
    }

    /**
     * 删除设备人员配置策划--设备人员详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchStaffingDetailByIds(String ids) {
        return sbchStaffingDetailMapper.deleteSbchStaffingDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备人员配置策划--设备人员详情信息
     * 
     * @param id 设备人员配置策划--设备人员详情ID
     * @return 结果
     */
    public int deleteSbchStaffingDetailById(Long id) {
        return sbchStaffingDetailMapper.deleteSbchStaffingDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchStaffingDetail> sbchStaffingDetailList) {
        if(!ObjectNullUtil.isEmpty(sbchStaffingDetailList)){
//            StringBuffer str = new StringBuffer("");
//            for (SbchStaffingDetail sbchStaffingDetail : sbchStaffingDetailList) {
//                sbchStaffingDetail.setId(IdWorker.createId());
//                EntityUtils.setCreateInfo(sbchStaffingDetail);
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchStaffingDetail, ValidationGroups.Save.class);
//                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
//                for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
//                    str=str.append(errorMessage.getMessage()+",");
//                }
//            }
//            if(!"".equals(str.toString())){
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,str.toString());
//            }
            sbchStaffingDetailMapper.batchInsert(sbchStaffingDetailList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchStaffingDetailByStaffingId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchStaffingDetailMapper.deleteSbchStaffingDetailByStaffingId(id,userId,date);
    }
}
