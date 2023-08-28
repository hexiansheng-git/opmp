package com.hhwy.pm.qqch.preparation.sbch.staffing.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialDetail;
import com.hhwy.pm.qqch.preparation.sbch.staffing.mapper.SbchStaffingSpecialDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingSpecialDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.BeanValidationResult;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 设备人员配置策划--特种设备人员详情Service业务层处理
 * 
 * @author zq
 * @date 2022-11-30
 */
@Service
public class SbchStaffingSpecialDetailServiceImpl implements ISbchStaffingSpecialDetailService {
    @Autowired
    private SbchStaffingSpecialDetailMapper sbchStaffingSpecialDetailMapper;

    /**
     * 查询设备人员配置策划--特种设备人员详情
     * 
     * @param id 设备人员配置策划--特种设备人员详情ID
     * @return 设备人员配置策划--特种设备人员详情
     */
    @Override
    public SbchStaffingSpecialDetail selectSbchStaffingSpecialDetailById(Long id) {
        return sbchStaffingSpecialDetailMapper.selectSbchStaffingSpecialDetailById(id);
    }

    /**
     * 查询设备人员配置策划--特种设备人员详情列表
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 设备人员配置策划--特种设备人员详情
     */
    @Override
    public List<SbchStaffingSpecialDetail> selectSbchStaffingSpecialDetailList(SbchStaffingSpecialDetail sbchStaffingSpecialDetail) {
        return sbchStaffingSpecialDetailMapper.selectSbchStaffingSpecialDetailList(sbchStaffingSpecialDetail);
    }

    /**
     * 新增设备人员配置策划--特种设备人员详情
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 结果
     */
    @Override
    public int insertSbchStaffingSpecialDetail(SbchStaffingSpecialDetail sbchStaffingSpecialDetail) {

    sbchStaffingSpecialDetail.setId(IdWorker.createId());

        sbchStaffingSpecialDetail.setCreateTime(DateUtils.getNowDate());

        return sbchStaffingSpecialDetailMapper.insertSbchStaffingSpecialDetail(sbchStaffingSpecialDetail);
    }

    /**
     * 修改设备人员配置策划--特种设备人员详情
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 结果
     */
    @Override
    public int updateSbchStaffingSpecialDetail(SbchStaffingSpecialDetail sbchStaffingSpecialDetail) {
        sbchStaffingSpecialDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchStaffingSpecialDetailMapper.updateSbchStaffingSpecialDetail(sbchStaffingSpecialDetail);
    }

    /**
     * 删除设备人员配置策划--特种设备人员详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchStaffingSpecialDetailByIds(String ids) {
        return sbchStaffingSpecialDetailMapper.deleteSbchStaffingSpecialDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备人员配置策划--特种设备人员详情信息
     * 
     * @param id 设备人员配置策划--特种设备人员详情ID
     * @return 结果
     */
    public int deleteSbchStaffingSpecialDetailById(Long id) {
        return sbchStaffingSpecialDetailMapper.deleteSbchStaffingSpecialDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchStaffingSpecialDetail> detailList) {
        if(null!=detailList && detailList.size()>0){
//            StringBuffer str = new StringBuffer("");
//            for (SbchStaffingSpecialDetail sbchStaffingSpecialDetail : detailList) {
//                EntityUtils.setCreateInfo(sbchStaffingSpecialDetail);
//                sbchStaffingSpecialDetail.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchStaffingSpecialDetail, ValidationGroups.Save.class);
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
            sbchStaffingSpecialDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchStaffingSpecialDetailByInfoId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchStaffingSpecialDetailMapper.deleteSbchStaffingSpecialDetailByInfoId(id,userId,date);
    }
}
