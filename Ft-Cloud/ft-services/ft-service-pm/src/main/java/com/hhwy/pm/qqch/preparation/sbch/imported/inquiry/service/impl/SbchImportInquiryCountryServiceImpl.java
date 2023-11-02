package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCountry;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.mapper.SbchImportInquiryCountryMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryCountryService;
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
 * 设备进口策划 进口调查-国家详情Service业务层处理
 * 
 * @author zq
 * @date 2022-12-05
 */
@Service
public class SbchImportInquiryCountryServiceImpl implements ISbchImportInquiryCountryService {
    @Autowired
    private SbchImportInquiryCountryMapper sbchImportInquiryCountryMapper;

    /**
     * 查询设备进口策划 进口调查-国家详情
     * 
     * @param id 设备进口策划 进口调查-国家详情ID
     * @return 设备进口策划 进口调查-国家详情
     */
    @Override
    public SbchImportInquiryCountry selectSbchImportInquiryCountryById(Long id) {
        return sbchImportInquiryCountryMapper.selectSbchImportInquiryCountryById(id);
    }

    /**
     * 查询设备进口策划 进口调查-国家详情列表
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 设备进口策划 进口调查-国家详情
     */
    @Override
    public List<SbchImportInquiryCountry> selectSbchImportInquiryCountryList(SbchImportInquiryCountry sbchImportInquiryCountry) {
        return sbchImportInquiryCountryMapper.selectSbchImportInquiryCountryList(sbchImportInquiryCountry);
    }

    /**
     * 新增设备进口策划 进口调查-国家详情
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 结果
     */
    @Override
    public int insertSbchImportInquiryCountry(SbchImportInquiryCountry sbchImportInquiryCountry) {

    sbchImportInquiryCountry.setId(IdWorker.createId());

        sbchImportInquiryCountry.setCreateTime(DateUtils.getNowDate());

        return sbchImportInquiryCountryMapper.insertSbchImportInquiryCountry(sbchImportInquiryCountry);
    }

    /**
     * 修改设备进口策划 进口调查-国家详情
     * 
     * @param sbchImportInquiryCountry 设备进口策划 进口调查-国家详情
     * @return 结果
     */
    @Override
    public int updateSbchImportInquiryCountry(SbchImportInquiryCountry sbchImportInquiryCountry) {
        sbchImportInquiryCountry.setUpdateTime(DateUtils.getNowDate());
        return sbchImportInquiryCountryMapper.updateSbchImportInquiryCountry(sbchImportInquiryCountry);
    }

    /**
     * 删除设备进口策划 进口调查-国家详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchImportInquiryCountryByIds(String ids) {
        return sbchImportInquiryCountryMapper.deleteSbchImportInquiryCountryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备进口策划 进口调查-国家详情信息
     * 
     * @param id 设备进口策划 进口调查-国家详情ID
     * @return 结果
     */
    public int deleteSbchImportInquiryCountryById(Long id) {
        return sbchImportInquiryCountryMapper.deleteSbchImportInquiryCountryById(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<SbchImportInquiryCountry> countryList) {
        if(null!=countryList && countryList.size()>0){
//            StringBuffer str = new StringBuffer("");
//            for (SbchImportInquiryCountry sbchImportInquiryCountry : countryList) {
//                EntityUtils.setCreateInfo(sbchImportInquiryCountry);
//                sbchImportInquiryCountry.setId(IdWorker.createId());
//                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(sbchImportInquiryCountry, ValidationGroups.Save.class);
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
            sbchImportInquiryCountryMapper.batchInsert(countryList);
        }
    }

    @Override
    @Transactional
    public void deleteSbchImportInquiryCountryByInquiryId(Long id) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        sbchImportInquiryCountryMapper.deleteSbchImportInquiryCountryByInquiryId(id,userId,date);
    }
}
