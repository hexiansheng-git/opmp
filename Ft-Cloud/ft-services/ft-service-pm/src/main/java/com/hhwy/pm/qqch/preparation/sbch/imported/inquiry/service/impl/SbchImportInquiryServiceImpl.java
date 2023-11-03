package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiry;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCountry;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCustoms;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.mapper.SbchImportInquiryMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryCountryService;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryCustomsService;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.MyPrepareBaseEntity;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备进口策划 进口调查Service业务层处理
 * 
 * @author zq
 * @date 2022-12-05
 */
@Service
public class SbchImportInquiryServiceImpl implements ISbchImportInquiryService {
    @Autowired
    private SbchImportInquiryMapper sbchImportInquiryMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchImportInquiryCountryService sbchImportInquiryCountryService;
    @Autowired
    private ISbchImportInquiryCustomsService sbchImportInquiryCustomsService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询设备进口策划 进口调查
     * 
     * @param id 设备进口策划 进口调查ID
     * @return 设备进口策划 进口调查
     */
    @Override
    public SbchImportInquiry selectSbchImportInquiryById(Long id) {
        return sbchImportInquiryMapper.selectSbchImportInquiryById(id);
    }

    /**
     * 查询设备进口策划 进口调查列表
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 设备进口策划 进口调查
     */
    @Override
    @SelfEmpty(clazz = SbchImportInquiry.class)
    //@CustomDatascope(alias = "inquiry")
    public List<SbchImportInquiry> selectSbchImportInquiryList(SbchImportInquiry sbchImportInquiry) {
        if(ObjectNullUtil.isEmpty(sbchImportInquiry.getIds())){
            sbchImportInquiry.setIds(null);
        }
        return sbchImportInquiryMapper.selectSbchImportInquiryList(sbchImportInquiry);
    }

    /**
     * 新增设备进口策划 进口调查
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchImportInquiry(SbchImportInquiry sbchImportInquiry) {
////        EntityUtils.setCreateInfo(sbchImportInquiry);
//        sbchImportInquiry.setId(IdWorker.createId());
//        String code = genCodeService.getSetCode(CodeEnum.EQU_IMPORT_INQUIRY);
//        sbchImportInquiry.setFormNo(code);
//
//        List<SbchImportInquiryCountry> countryList = sbchImportInquiry.getCountryList();
////        List<SbchImportInquiryCustoms> customsList = sbchImportInquiry.getCustomsList();
//        for (SbchImportInquiryCountry sbchImportInquiryCountry : countryList) {
//            BeanUtils.copyProperties(sbchImportInquiry,sbchImportInquiryCountry);
//            sbchImportInquiryCountry.setInquiryId(sbchImportInquiry.getId());
//        }
//
//        if(!ObjectNullUtil.isEmpty(countryList)){
//            //国家详情
//            sbchImportInquiryCountryService.batchInsert(countryList);
//            Map<String, Long> countryMap = countryList.stream().collect(Collectors.groupingBy(t -> t.getCountryCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getId())));
//
//            //处理港口详情数据
//            for (SbchImportInquiryCustoms sbchImportInquiryCustoms : customsList) {
//                String fileGroupId = sbchImportInquiryCustoms.getFileGroupId();
//                BeanUtils.copyProperties(sbchImportInquiry,sbchImportInquiryCustoms);
//                sbchImportInquiryCustoms.setInquiryId(sbchImportInquiry.getId());
//                sbchImportInquiryCustoms.setCountryId(countryMap.get(sbchImportInquiryCustoms.getCountryCode()));
//                sbchImportInquiryCustoms.setFileGroupId(fileGroupId);
//            }
//            //港口详情
//            customsList = customsList.stream().filter(t->{
//                if(!ObjectNullUtil.isEmpty(t.getCountryId())){
//                    return true;
//                }
//                return false;
//            }).collect(Collectors.toList());
//            sbchImportInquiryCustomsService.batchInsert(customsList);
//        }
//
//        return sbchImportInquiryMapper.insertSbchImportInquiry(sbchImportInquiry);
        return 0;
    }

    /**
     * 修改设备进口策划 进口调查
     * 
     * @param sbchImportInquiry 设备进口策划 进口调查
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchImportInquiry(SbchImportInquiry sbchImportInquiry) {
////        EntityUtils.setUpdateInfo(sbchImportInquiry);
//
//        List<SbchImportInquiryCountry> countryList = sbchImportInquiry.getCountryList();
////        List<SbchImportInquiryCustoms> customsList = sbchImportInquiry.getCustomsList();
//        for (SbchImportInquiryCountry sbchImportInquiryCountry : countryList) {
//            BeanUtils.copyProperties(sbchImportInquiry,sbchImportInquiryCountry);
//            sbchImportInquiryCountry.setInquiryId(sbchImportInquiry.getId());
//        }
//        //删除旧的国家详情和港口详情
//        sbchImportInquiryCountryService.deleteSbchImportInquiryCountryByInquiryId(sbchImportInquiry.getId());
//        sbchImportInquiryCustomsService.deleteSbchImportInquiryCustomsByInquiryId(sbchImportInquiry.getId());
//        if(!ObjectNullUtil.isEmpty(countryList)){
//            //国家详情
//            sbchImportInquiryCountryService.batchInsert(countryList);
//            Map<String, Long> countryMap = countryList.stream().collect(Collectors.groupingBy(t -> t.getCountryCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getId())));
//            //处理港口详情数据
////            for (SbchImportInquiryCustoms sbchImportInquiryCustoms : customsList) {
//                String fileGroupId = sbchImportInquiryCustoms.getFileGroupId();
//                BeanUtils.copyProperties(sbchImportInquiry,sbchImportInquiryCustoms);
//                sbchImportInquiryCustoms.setInquiryId(sbchImportInquiry.getId());
//                sbchImportInquiryCustoms.setCountryId(countryMap.get(sbchImportInquiryCustoms.getCountryCode()));
//                sbchImportInquiryCustoms.setFileGroupId(fileGroupId);
//            }
//            //港口详情
//            customsList = customsList.stream().filter(t->{
//                if(!ObjectNullUtil.isEmpty(t.getCountryId())){
//                    return true;
//                }
//                return false;
//            }).collect(Collectors.toList());
//            sbchImportInquiryCustomsService.batchInsert(customsList);
//        }
//        return sbchImportInquiryMapper.updateSbchImportInquiry(sbchImportInquiry);
        return 0;
    }

    /**
     * 删除设备进口策划 进口调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchImportInquiryByIds(String ids) {
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchImportInquiryMapper.deleteSbchImportInquiryByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除设备进口策划 进口调查信息
     * 
     * @param id 设备进口策划 进口调查ID
     * @return 结果
     */
    public int deleteSbchImportInquiryById(Long id) {
        return sbchImportInquiryMapper.deleteSbchImportInquiryById(id);
    }

    @Override
    public Map selectInquiryDetailList(Long id) {
        HashMap<String, Object> returnMap = new HashMap<>();
        //国家列表
        SbchImportInquiryCountry sbchImportInquiryCountry = new SbchImportInquiryCountry();
        sbchImportInquiryCountry.setInquiryId(id);
        List<SbchImportInquiryCountry> countryList = sbchImportInquiryCountryService.selectSbchImportInquiryCountryList(sbchImportInquiryCountry);
        returnMap.put("countryList",countryList);
        //港口列表
        SbchImportInquiryCustoms sbchImportInquiryCustoms = new SbchImportInquiryCustoms();
        sbchImportInquiryCustoms.setInquiryId(id);
        List<SbchImportInquiryCustoms> customsList = sbchImportInquiryCustomsService.selectSbchImportInquiryCustomsList(sbchImportInquiryCustoms);
        if(!ObjectNullUtil.isEmpty(customsList)){
            Map<String, List<SbchImportInquiryCustoms>> customsListMap = customsList.stream().collect(Collectors.groupingBy(t -> t.getCountryCode()));
            returnMap.put("customsListMap",customsListMap);
        }
        return returnMap;
    }

    @Override
    public SbchImportInquiry getList(BigDecimal version) {
        SbchImportInquiry returnVo = new SbchImportInquiry();
        version = VersionUtil.getVersion("sbch_import_inquiry", version);
        SbchImportInquiry sbchImportInquiry = new SbchImportInquiry();
        sbchImportInquiry.setVersion(version);
        List<SbchImportInquiry> sbchImportInquiries = sbchImportInquiryMapper.selectSbchImportInquiryList(sbchImportInquiry);
        if(!ObjectNullUtil.isEmpty(sbchImportInquiries)){
            SbchImportInquiry sbchImportInquiry1 = sbchImportInquiries.get(0);
            returnVo = sbchImportInquiry1;
            //国家列表
            SbchImportInquiryCountry sbchImportInquiryCountry = new SbchImportInquiryCountry();
            sbchImportInquiryCountry.setInquiryId(returnVo.getId());
            List<SbchImportInquiryCountry> countryList = sbchImportInquiryCountryService.selectSbchImportInquiryCountryList(sbchImportInquiryCountry);
            returnVo.setCountryList(countryList);
            //港口列表
            SbchImportInquiryCustoms sbchImportInquiryCustoms = new SbchImportInquiryCustoms();
            sbchImportInquiryCustoms.setInquiryId(returnVo.getId());
            List<SbchImportInquiryCustoms> customsList = sbchImportInquiryCustomsService.selectSbchImportInquiryCustomsList(sbchImportInquiryCustoms);
            if(!ObjectNullUtil.isEmpty(customsList)){
                Map<Long, List<SbchImportInquiryCustoms>> customsListMap = customsList.stream().collect(Collectors.groupingBy(t -> t.getCountryId()));
                for (SbchImportInquiryCountry bean : countryList) {
                    bean.setCustomsList(customsListMap.get(bean.getId()));
                }
            }
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchImportInquiry vo) {
        //国家列表
        List<SbchImportInquiryCountry> countryList = vo.getCountryList();
        //港口列表
//        List<SbchImportInquiryCustoms> customsList = vo.getCustomsList();

        SbchImportInquiry temp = new SbchImportInquiry();
        temp.setVersion(vo.getVersion());
        List<SbchImportInquiry> sbchImportInquiries = sbchImportInquiryMapper.selectSbchImportInquiryList(temp);
        if(!ObjectNullUtil.isEmpty(sbchImportInquiries)){
            vo.setId(sbchImportInquiries.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(vo);
            sbchImportInquiryMapper.updateSbchImportInquiry(vo);
        }else{
            vo.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_IMPORT_INQUIRY);
            vo.setUnicode(code);
            vo.setTitleName("进口调查");
            MyUtilPrepareUtil.setCreateUpdateInfo(vo);
            sbchImportInquiryMapper.insertSbchImportInquiry(vo);
        }
        //删除老旧数据
        sbchImportInquiryCountryService.deleteSbchImportInquiryCountryByInquiryId(vo.getId());
        sbchImportInquiryCustomsService.deleteSbchImportInquiryCustomsByInquiryId(vo.getId());

        //国家详情
        if(!ObjectNullUtil.isEmpty(countryList)){
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(countryList, ValidationGroups.Save.class);
            }
            List<SbchImportInquiryCustoms> saveList = null;
            for (SbchImportInquiryCountry sbchImportInquiryCountry : countryList) {
                BeanUtils.copyProperties(vo,sbchImportInquiryCountry);
                sbchImportInquiryCountry.setInquiryId(vo.getId());
                EntityUtils.setCreateInfo(sbchImportInquiryCountry);
                sbchImportInquiryCountry.setId(IdWorker.createId());

                //港口数据
                List<SbchImportInquiryCustoms> customsList = sbchImportInquiryCountry.getCustomsList();
                if(!ObjectNullUtil.isEmpty(customsList)){
                    //校验数据必填
//                    if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
//                        JyDetailsUtil.jyDetails(customsList, ValidationGroups.Save.class);
//                    }
                    //处理港口详情数据
                    for (SbchImportInquiryCustoms sbchImportInquiryCustoms : customsList) {
                        String fileGroupId = sbchImportInquiryCustoms.getFileGroupId();
                        BeanUtils.copyProperties(vo,sbchImportInquiryCustoms);
                        sbchImportInquiryCustoms.setId(IdWorker.createId());
                        sbchImportInquiryCustoms.setInquiryId(vo.getId());
                        sbchImportInquiryCustoms.setCountryId(sbchImportInquiryCustoms.getId());
                        sbchImportInquiryCustoms.setFileGroupId(fileGroupId);
                    }
                    saveList.addAll(customsList);
                }
            }
            sbchImportInquiryCountryService.batchInsert(countryList);
            if (CollectionUtil.isNotEmpty(saveList)){
                sbchImportInquiryCustomsService.batchInsert(saveList);
            }
        }

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(vo.getButtonMark())){
            JyDetailsUtil.jyDetails(countryList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
