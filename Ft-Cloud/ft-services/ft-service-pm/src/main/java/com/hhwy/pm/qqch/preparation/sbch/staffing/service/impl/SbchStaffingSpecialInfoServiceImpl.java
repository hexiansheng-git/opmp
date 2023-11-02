package com.hhwy.pm.qqch.preparation.sbch.staffing.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;

import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialDetail;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.mapper.SbchStaffingSpecialInfoMapper;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingSpecialDetailService;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingSpecialInfoService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.BusinessTaskResultUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.exception.CustomBusinessException;
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
import java.util.List;
import java.util.stream.Collectors;


/**
 * 设备人员配置-特种设备爱人员Service业务层处理
 * 
 * @author zq
 * @date 2022-11-30
 */
@Service
public class SbchStaffingSpecialInfoServiceImpl implements ISbchStaffingSpecialInfoService {
    @Autowired
    private SbchStaffingSpecialInfoMapper sbchStaffingSpecialInfoMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchStaffingSpecialDetailService staffingSpecialDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询设备人员配置-特种设备爱人员
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 设备人员配置-特种设备爱人员
     */
    @Override
    public SbchStaffingSpecialInfo selectSbchStaffingSpecialInfoById(Long id) {
        return sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoById(id);
    }

    /**
     * 新增设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    @Override
    @Transactional
    public Long insertSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo) {
        List<SbchStaffingSpecialDetail> detailList = sbchStaffingSpecialInfo.getDetailList();
        SbchStaffingSpecialInfo temp = new SbchStaffingSpecialInfo();
        temp.setVersionNo(sbchStaffingSpecialInfo.getVersion());
        List<SbchStaffingSpecialInfo> sbchStaffingSpecialInfos = sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoList(temp);
        if(!ObjectNullUtil.isEmpty(sbchStaffingSpecialInfos)){
            sbchStaffingSpecialInfo.setId(sbchStaffingSpecialInfos.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchStaffingSpecialInfo);
            sbchStaffingSpecialInfoMapper.updateSbchStaffingSpecialInfo(sbchStaffingSpecialInfo);
        }else{
            sbchStaffingSpecialInfo.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_STAFF_SPECIAL);
            sbchStaffingSpecialInfo.setVersionNo(new BigDecimal(1));
            sbchStaffingSpecialInfo.setUnicode(code);
            sbchStaffingSpecialInfo.setTitleName("特种设备人员配置策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchStaffingSpecialInfo);
            sbchStaffingSpecialInfoMapper.insertSbchStaffingSpecialInfo(sbchStaffingSpecialInfo);
        }
        //// 清空数据库表中数据
        staffingSpecialDetailService.deleteSbchStaffingSpecialDetailByInfoId(sbchStaffingSpecialInfo.getId());

        if(!ObjectNullUtil.isEmpty(detailList)){
            //校验数据必填
            if("1".equals(sbchStaffingSpecialInfo.getButtonMark())||"2".equals(sbchStaffingSpecialInfo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchStaffingSpecialDetail sbchStaffingSpecialDetail : detailList) {
                BeanUtils.copyProperties(sbchStaffingSpecialInfo,sbchStaffingSpecialDetail, "remark");
                sbchStaffingSpecialDetail.setId(IdWorker.createId());
                sbchStaffingSpecialDetail.setInfoId(sbchStaffingSpecialInfo.getId());
                sbchStaffingSpecialDetail.setDeptId(sbchStaffingSpecialInfo.getDeptId());
            }
            staffingSpecialDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchStaffingSpecialInfo.getButtonMark())){
            //插入确认记录
            String menuId = sbchStaffingSpecialInfo.getMenuId();
            String stageIdentity = sbchStaffingSpecialInfo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
        return sbchStaffingSpecialInfo.getId();
    }

    /**
     * 修改设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    @Override
    @Transactional
    public Long updateSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo) {
        //判断该数据是否为有效版本
        SbchStaffingSpecialInfo temp = sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoById(sbchStaffingSpecialInfo.getId());
        if("1".equals(temp.getIsValid())){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可进行编辑修改");
        }
//        EntityUtils.setUpdateInfo(sbchStaffingSpecialInfo);
        //删除子表数据
        staffingSpecialDetailService.deleteSbchStaffingSpecialDetailByInfoId(sbchStaffingSpecialInfo.getId());
        //新增子表数据
        List<SbchStaffingSpecialDetail> detailList = sbchStaffingSpecialInfo.getDetailList();
        for (SbchStaffingSpecialDetail sbchStaffingSpecialDetail : detailList) {
            BeanUtils.copyProperties(sbchStaffingSpecialInfo,sbchStaffingSpecialDetail);
            sbchStaffingSpecialDetail.setInfoId(sbchStaffingSpecialInfo.getId());
        }
        staffingSpecialDetailService.batchInsert(detailList);
        sbchStaffingSpecialInfoMapper.updateSbchStaffingSpecialInfo(sbchStaffingSpecialInfo);
        return sbchStaffingSpecialInfo.getId();
    }

    /**
     * 删除设备人员配置-特种设备爱人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchStaffingSpecialInfoByIds(String ids) {
        //查询删除的id中是否有版本为有效的
        SbchStaffingSpecialInfo sbchStaffingSpecialInfo = new SbchStaffingSpecialInfo();
        sbchStaffingSpecialInfo.setIds(Convert.toStrArray(ids));
        sbchStaffingSpecialInfo.setIsValid("1");
        List<SbchStaffingSpecialInfo> infoList = sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoList(sbchStaffingSpecialInfo);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<String> collect = infoList.stream().map(t -> t.getFormNo()).collect(Collectors.toList());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可删除："+ StringUtils.join(collect,","));
        }
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchStaffingSpecialInfoMapper.deleteSbchStaffingSpecialInfoByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除设备人员配置-特种设备爱人员信息
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 结果
     */
    public int deleteSbchStaffingSpecialInfoById(Long id) {
        return sbchStaffingSpecialInfoMapper.deleteSbchStaffingSpecialInfoById(id);
    }

    @Override
    @Transactional
    public Long changeVersion(SbchStaffingSpecialInfo sbchStaffingSpecialInfo) {
        //判断该数据是否为有效版本
        SbchStaffingSpecialInfo oldInfo = sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoById(sbchStaffingSpecialInfo.getId());
        if("0".equals(oldInfo.getIsValid())){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"请选择一条有效数据进行变更");
        }
        //oldInfo.setIsValid("0");
//        EntityUtils.setUpdateInfo(oldInfo);
//
//        EntityUtils.setCreateInfo(sbchStaffingSpecialInfo);
        sbchStaffingSpecialInfo.setId(IdWorker.createId());
        sbchStaffingSpecialInfo.setVersionNo(oldInfo.getVersionNo().add(new BigDecimal(1)));
        int i = oldInfo.getFormNo().lastIndexOf("-");
        String substring = oldInfo.getFormNo().substring(0, i);
        sbchStaffingSpecialInfo.setFormNo(substring+"-"+sbchStaffingSpecialInfo.getVersionNo().setScale(0));
        //修改旧版本
        sbchStaffingSpecialInfoMapper.updateSbchStaffingSpecialInfo(oldInfo);
        //新增新版本子表
        List<SbchStaffingSpecialDetail> detailList = sbchStaffingSpecialInfo.getDetailList();
        for (SbchStaffingSpecialDetail sbchStaffingSpecialDetail : detailList) {
            BeanUtils.copyProperties(sbchStaffingSpecialInfo,sbchStaffingSpecialDetail);
            sbchStaffingSpecialDetail.setInfoId(sbchStaffingSpecialInfo.getId());
        }
        staffingSpecialDetailService.batchInsert(detailList);
        //新增新版本主表
        sbchStaffingSpecialInfoMapper.insertSbchStaffingSpecialInfo(sbchStaffingSpecialInfo);
        return sbchStaffingSpecialInfo.getId();
    }

    @Override
    public SbchStaffingSpecialInfo getList(BigDecimal version) {
        SbchStaffingSpecialInfo sbchStaffingSpecialInfo = new SbchStaffingSpecialInfo();
        version = VersionUtil.getVersion("sbch_staffing_special_info", version);
        sbchStaffingSpecialInfo.setVersionNo(version);
        List<SbchStaffingSpecialInfo> sbchStaffingSpecialInfos = sbchStaffingSpecialInfoMapper.selectSbchStaffingSpecialInfoList(sbchStaffingSpecialInfo);
        if(!ObjectNullUtil.isEmpty(sbchStaffingSpecialInfos)){
            SbchStaffingSpecialInfo sbchStaffingSpecialInfo1 = sbchStaffingSpecialInfos.get(0);
            BeanUtils.copyProperties(sbchStaffingSpecialInfo1,sbchStaffingSpecialInfo);
            SbchStaffingSpecialDetail sbchStaffingSpecialDetail = new SbchStaffingSpecialDetail();
            sbchStaffingSpecialDetail.setInfoId(sbchStaffingSpecialInfo.getId());
            List<SbchStaffingSpecialDetail> sbchStaffingSpecialDetails = staffingSpecialDetailService.selectSbchStaffingSpecialDetailList(sbchStaffingSpecialDetail);
            sbchStaffingSpecialInfo.setDetailList(sbchStaffingSpecialDetails);
        }
        sbchStaffingSpecialInfo.setVersion(version);
        sbchStaffingSpecialInfo.setStageIdentity(qqchReviewService.getStage());
        return sbchStaffingSpecialInfo;
    }
}
