package com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClearDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.mapper.SbchImportCustomsClearDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearDetailService;
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
 * 清关档案策划详情Service业务层处理
 * 
 * @author zq
 * @date 2022-12-14
 */
@Service
public class SbchImportCustomsClearDetailServiceImpl implements ISbchImportCustomsClearDetailService {
    @Autowired
    private SbchImportCustomsClearDetailMapper sbchImportCustomsClearDetailMapper;

    /**
     * 查询清关档案策划详情
     * 
     * @param id 清关档案策划详情ID
     * @return 清关档案策划详情
     */
    @Override
    public SbchImportCustomsClearDetail selectSbchImportCustomsClearDetailById(Long id) {
        return sbchImportCustomsClearDetailMapper.selectSbchImportCustomsClearDetailById(id);
    }

    /**
     * 查询清关档案策划详情列表
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 清关档案策划详情
     */
    @Override
    public List<SbchImportCustomsClearDetail> selectSbchImportCustomsClearDetailList(SbchImportCustomsClearDetail sbchImportCustomsClearDetail) {
        return sbchImportCustomsClearDetailMapper.selectSbchImportCustomsClearDetailList(sbchImportCustomsClearDetail);
    }

    /**
     * 新增清关档案策划详情
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 结果
     */
    @Override
    public int insertSbchImportCustomsClearDetail(SbchImportCustomsClearDetail sbchImportCustomsClearDetail) {

    sbchImportCustomsClearDetail.setId(IdWorker.createId());

        sbchImportCustomsClearDetail.setCreateTime(DateUtils.getNowDate());

        return sbchImportCustomsClearDetailMapper.insertSbchImportCustomsClearDetail(sbchImportCustomsClearDetail);
    }

    /**
     * 修改清关档案策划详情
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 结果
     */
    @Override
    public int updateSbchImportCustomsClearDetail(SbchImportCustomsClearDetail sbchImportCustomsClearDetail) {
        sbchImportCustomsClearDetail.setUpdateTime(DateUtils.getNowDate());
        return sbchImportCustomsClearDetailMapper.updateSbchImportCustomsClearDetail(sbchImportCustomsClearDetail);
    }

    /**
     * 删除清关档案策划详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchImportCustomsClearDetailByIds(String ids) {
        return sbchImportCustomsClearDetailMapper.deleteSbchImportCustomsClearDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除清关档案策划详情信息
     * 
     * @param id 清关档案策划详情ID
     * @return 结果
     */
    public int deleteSbchImportCustomsClearDetailById(Long id) {
        return sbchImportCustomsClearDetailMapper.deleteSbchImportCustomsClearDetailById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchImportCustomsClearDetail> detailList) {
        if(!ObjectNullUtil.isEmpty(detailList)){
            StringBuffer str = new StringBuffer("");
            for (SbchImportCustomsClearDetail sbchImportCustomsClearDetail : detailList) {
                EntityUtils.setCreateInfo(sbchImportCustomsClearDetail);
                sbchImportCustomsClearDetail.setId(IdWorker.createId());
                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchImportCustomsClearDetail, ValidationGroups.Save.class);
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
            sbchImportCustomsClearDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    public List<SbchImportCustomsClearDetail> selectDetailList(SbchImportCustomsClearDetail sbchImportCustonsClearDetail) {
        return sbchImportCustomsClearDetailMapper.selectDetailValidList(sbchImportCustonsClearDetail);
    }

    @Override
    @Transactional
    public void deleteSbchImportCustomsClearDetailByInfoId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchImportCustomsClearDetailMapper.deleteSbchImportCustomsClearDetailByInfoId(id,userId,date);
    }
}
