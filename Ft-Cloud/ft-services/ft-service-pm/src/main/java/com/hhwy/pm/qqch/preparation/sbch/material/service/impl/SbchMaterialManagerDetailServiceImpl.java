package com.hhwy.pm.qqch.preparation.sbch.material.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManagerDetail;
import com.hhwy.pm.qqch.preparation.sbch.material.mapper.SbchMaterialManagerDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.material.service.ISbchMaterialManagerDetailService;
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
 * 设备现场管理Service业务层处理
 * 
 * @author zq
 * @date 2022-12-20
 */
@Service
public class SbchMaterialManagerDetailServiceImpl implements ISbchMaterialManagerDetailService {
    @Autowired
    private SbchMaterialManagerDetailMapper sbchMaterialManagerDetailMapper;

    /**
     * 查询设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 设备现场管理
     */
    @Override
    public SbchMaterialManagerDetail selectSbchMaterialManagerDetailById(Long id) {
        return sbchMaterialManagerDetailMapper.selectSbchMaterialManagerDetailById(id);
    }

    /**
     * 查询设备现场管理列表
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 设备现场管理
     */
    @Override
    public List<SbchMaterialManagerDetail> selectSbchMaterialManagerDetailList(SbchMaterialManagerDetail sbchMaterialManagerDetail) {
        return sbchMaterialManagerDetailMapper.selectSbchMaterialManagerDetailList(sbchMaterialManagerDetail);
    }

    /**
     * 新增设备现场管理
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 结果
     */
    @Override
    public int insertSbchMaterialManagerDetail(SbchMaterialManagerDetail sbchMaterialManagerDetail) {

    sbchMaterialManagerDetail.setId(IdWorker.createId());

        sbchMaterialManagerDetail.setCreateTime(DateUtils.getNowDate());

        return sbchMaterialManagerDetailMapper.insertSbchMaterialManagerDetail(sbchMaterialManagerDetail);
    }

    /**
     * 修改设备现场管理
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 结果
     */
    @Override
    public int updateSbchMaterialManagerDetail(SbchMaterialManagerDetail sbchMaterialManagerDetail) {
        sbchMaterialManagerDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchMaterialManagerDetailMapper.updateSbchMaterialManagerDetail(sbchMaterialManagerDetail);
    }

    /**
     * 删除设备现场管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchMaterialManagerDetailByIds(String ids) {
        return sbchMaterialManagerDetailMapper.deleteSbchMaterialManagerDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备现场管理信息
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    public int deleteSbchMaterialManagerDetailById(Long id) {
        return sbchMaterialManagerDetailMapper.deleteSbchMaterialManagerDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchMaterialManagerDetail> detailList) {
        if(!ObjectNullUtil.isEmpty(detailList)){
            StringBuffer str = new StringBuffer("");
            for (SbchMaterialManagerDetail sbchMaterialManagerDetail : detailList) {
                EntityUtils.setCreateInfo(sbchMaterialManagerDetail);
                sbchMaterialManagerDetail.setId(IdWorker.createId());
                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchMaterialManagerDetail, ValidationGroups.Save.class);
                if(!beanValidationResult.isSuccess()){
                    List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                    for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                        str=str.append(errorMessage.getMessage()+",");
                    }
                }
            }
//            if(!"".equals(str.toString())){
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,str.toString());
//            }
            sbchMaterialManagerDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    public List<SbchMaterialManagerDetail> selectValidDetailList(SbchMaterialManagerDetail sbchMaterialManagerDetail) {
        return sbchMaterialManagerDetailMapper.selectValidDetailList(sbchMaterialManagerDetail);
    }

    @Override
    @Transactional
    public void deleteSbchMaterialManagerDetailByInfoId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchMaterialManagerDetailMapper.deleteSbchMaterialManagerDetailByInfoId(id,userId,date);
    }
}
