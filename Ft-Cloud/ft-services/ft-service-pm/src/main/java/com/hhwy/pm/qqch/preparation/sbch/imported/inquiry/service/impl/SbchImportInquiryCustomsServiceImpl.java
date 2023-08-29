package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCustoms;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.mapper.SbchImportInquiryCustomsMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryCustomsService;

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
 * 设备进口策划 进口调查-港口详情Service业务层处理
 * 
 * @author zq
 * @date 2022-12-05
 */
@Service
public class SbchImportInquiryCustomsServiceImpl implements ISbchImportInquiryCustomsService {
    @Autowired
    private SbchImportInquiryCustomsMapper sbchImportInquiryCustomsMapper;

    /**
     * 查询设备进口策划 进口调查-港口详情
     * 
     * @param id 设备进口策划 进口调查-港口详情ID
     * @return 设备进口策划 进口调查-港口详情
     */
    @Override
    public SbchImportInquiryCustoms selectSbchImportInquiryCustomsById(Long id) {
        return sbchImportInquiryCustomsMapper.selectSbchImportInquiryCustomsById(id);
    }

    /**
     * 查询设备进口策划 进口调查-港口详情列表
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 设备进口策划 进口调查-港口详情
     */
    @Override
    public List<SbchImportInquiryCustoms> selectSbchImportInquiryCustomsList(SbchImportInquiryCustoms sbchImportInquiryCustoms) {
        return sbchImportInquiryCustomsMapper.selectSbchImportInquiryCustomsList(sbchImportInquiryCustoms);
    }

    /**
     * 新增设备进口策划 进口调查-港口详情
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 结果
     */
    @Override
    public int insertSbchImportInquiryCustoms(SbchImportInquiryCustoms sbchImportInquiryCustoms) {

    sbchImportInquiryCustoms.setId(IdWorker.createId());

        sbchImportInquiryCustoms.setCreateTime(DateUtils.getNowDate());

        return sbchImportInquiryCustomsMapper.insertSbchImportInquiryCustoms(sbchImportInquiryCustoms);
    }

    /**
     * 修改设备进口策划 进口调查-港口详情
     * 
     * @param sbchImportInquiryCustoms 设备进口策划 进口调查-港口详情
     * @return 结果
     */
    @Override
    public int updateSbchImportInquiryCustoms(SbchImportInquiryCustoms sbchImportInquiryCustoms) {
        sbchImportInquiryCustoms.setUpdateTime(DateUtils.getNowDate());
        return sbchImportInquiryCustomsMapper.updateSbchImportInquiryCustoms(sbchImportInquiryCustoms);
    }

    /**
     * 删除设备进口策划 进口调查-港口详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchImportInquiryCustomsByIds(String ids) {
        return sbchImportInquiryCustomsMapper.deleteSbchImportInquiryCustomsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备进口策划 进口调查-港口详情信息
     * 
     * @param id 设备进口策划 进口调查-港口详情ID
     * @return 结果
     */
    public int deleteSbchImportInquiryCustomsById(Long id) {
        return sbchImportInquiryCustomsMapper.deleteSbchImportInquiryCustomsById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchImportInquiryCustoms> customsList) {
        if(null!=customsList && customsList.size()>0){
//            StringBuffer str = new StringBuffer("");
            for (SbchImportInquiryCustoms sbchImportInquiryCustoms : customsList) {
                EntityUtils.setCreateInfo(sbchImportInquiryCustoms);
//                sbchImportInquiryCustoms.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchImportInquiryCustoms, ValidationGroups.Save.class);
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
            sbchImportInquiryCustomsMapper.batchInsert(customsList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchImportInquiryCustomsByInquiryId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchImportInquiryCustomsMapper.deleteSbchImportInquiryCustomsByInquiryId(id,userId,date);
    }
}
