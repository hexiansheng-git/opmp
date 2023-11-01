package com.hhwy.pm.qqch.preparation.sbch.single.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheckDetail;
import com.hhwy.pm.qqch.preparation.sbch.single.mapper.SbchSingleCheckDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.single.service.ISbchSingleCheckDetailService;
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
 * 单机核算策划Service业务层处理
 * 
 * @author zq
 * @date 2022-12-22
 */
@Service
public class SbchSingleCheckDetailServiceImpl implements ISbchSingleCheckDetailService {
    @Autowired
    private SbchSingleCheckDetailMapper sbchSingleCheckDetailMapper;

    /**
     * 查询单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 单机核算策划
     */
    @Override
    public SbchSingleCheckDetail selectSbchSingleCheckDetailById(Long id) {
        return sbchSingleCheckDetailMapper.selectSbchSingleCheckDetailById(id);
    }

    /**
     * 查询单机核算策划列表
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 单机核算策划
     */
    @Override
    public List<SbchSingleCheckDetail> selectSbchSingleCheckDetailList(SbchSingleCheckDetail sbchSingleCheckDetail) {
        return sbchSingleCheckDetailMapper.selectSbchSingleCheckDetailList(sbchSingleCheckDetail);
    }

    /**
     * 新增单机核算策划
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 结果
     */
    @Override
    public int insertSbchSingleCheckDetail(SbchSingleCheckDetail sbchSingleCheckDetail) {

    sbchSingleCheckDetail.setId(IdWorker.createId());

        sbchSingleCheckDetail.setCreateTime(DateUtils.getNowDate());

        return sbchSingleCheckDetailMapper.insertSbchSingleCheckDetail(sbchSingleCheckDetail);
    }

    /**
     * 修改单机核算策划
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 结果
     */
    @Override
    public int updateSbchSingleCheckDetail(SbchSingleCheckDetail sbchSingleCheckDetail) {
        sbchSingleCheckDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchSingleCheckDetailMapper.updateSbchSingleCheckDetail(sbchSingleCheckDetail);
    }

    /**
     * 删除单机核算策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchSingleCheckDetailByIds(String ids) {
        return sbchSingleCheckDetailMapper.deleteSbchSingleCheckDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除单机核算策划信息
     * 
     * @param id 单机核算策划ID
     * @return 结果
     */
    public int deleteSbchSingleCheckDetailById(Long id) {
        return sbchSingleCheckDetailMapper.deleteSbchSingleCheckDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchSingleCheckDetail> detailList) {
        if(!ObjectNullUtil.isEmpty(detailList)){
//            StringBuffer str = new StringBuffer("");
//            for (SbchSingleCheckDetail sbchSingleCheckDetail : detailList) {
//                EntityUtils.setCreateInfo(sbchSingleCheckDetail);
//                sbchSingleCheckDetail.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchSingleCheckDetail, ValidationGroups.Save.class);
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
            sbchSingleCheckDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchSingleCheckDetailByInfoId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchSingleCheckDetailMapper.deleteSbchSingleCheckDetailByInfoId(id,userId,date);
    }

    @Override
    public List<SbchSingleCheckDetail> validList(SbchSingleCheckDetail sbchSingleCheckDetail) {
        return sbchSingleCheckDetailMapper.validList(sbchSingleCheckDetail);
    }
}
