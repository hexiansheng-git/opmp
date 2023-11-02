package com.hhwy.pm.qqch.preparation.sbch.staffing.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingDetail;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.mapper.SbchStaffingInfoMapper;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingDetailService;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingInfoService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.BusinessTaskResultUtil;
import com.hhwy.utils.EntityUtils;

import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
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
 * 设备人员配置策划Service业务层处理
 * 
 * @author zq
 * @date 2022-11-28
 */
@Service
public class SbchStaffingInfoServiceImpl implements ISbchStaffingInfoService {
    @Autowired
    private SbchStaffingInfoMapper sbchStaffingInfoMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchStaffingDetailService sbchStaffingDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询设备人员配置策划
     * 
     * @param id 设备人员配置策划ID
     * @return 设备人员配置策划
     */
    @Override
    public SbchStaffingInfo selectSbchStaffingInfoById(Long id) {
        return sbchStaffingInfoMapper.selectSbchStaffingInfoById(id);
    }

    /**
     * 查询设备人员配置策划列表
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 设备人员配置策划
     */
    @Override
    @SelfEmpty(clazz = SbchStaffingInfo.class)
    //@CustomDatascope(alias = "staff")
    public List<SbchStaffingInfo> selectSbchStaffingInfoList(SbchStaffingInfo sbchStaffingInfo) {
        if(ObjectNullUtil.isEmpty(sbchStaffingInfo.getIds())){
            sbchStaffingInfo.setIds(null);
        }
        List<SbchStaffingInfo> sbchStaffingInfos = sbchStaffingInfoMapper.selectSbchStaffingInfoList(sbchStaffingInfo);
        return sbchStaffingInfos;
    }

    /**
     * 新增设备人员配置策划
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 结果
     */
    @Override
    @Transactional
    public Long insertSbchStaffingInfo(SbchStaffingInfo sbchStaffingInfo) {
        List<SbchStaffingDetail> sbchStaffingDetailList = sbchStaffingInfo.getSbchStaffingDetailList();
        SbchStaffingInfo temp = new SbchStaffingInfo();
        temp.setVersionNo(sbchStaffingInfo.getVersion());
        List<SbchStaffingInfo> sbchStaffingInfos = sbchStaffingInfoMapper.selectSbchStaffingInfoList(temp);
        if(!ObjectNullUtil.isEmpty(sbchStaffingInfos)){
            sbchStaffingInfo.setId(sbchStaffingInfos.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchStaffingInfo);
            sbchStaffingInfoMapper.updateSbchStaffingInfo(sbchStaffingInfo);
        }else{
            sbchStaffingInfo.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_STAFFING);
            sbchStaffingInfo.setVersionNo(new BigDecimal(1));
            sbchStaffingInfo.setUnicode(code);
            sbchStaffingInfo.setTitleName("设备人员配置策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchStaffingInfo);
            sbchStaffingInfoMapper.insertSbchStaffingInfo(sbchStaffingInfo);
        }
        // 清空数据库表中数据
        sbchStaffingDetailService.deleteSbchStaffingDetailByStaffingId(sbchStaffingInfo.getId());

        if(!ObjectNullUtil.isEmpty(sbchStaffingDetailList)){
            //校验数据必填
            if("1".equals(sbchStaffingInfo.getButtonMark())||"2".equals(sbchStaffingInfo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(sbchStaffingDetailList, ValidationGroups.Save.class);
            }

            for (SbchStaffingDetail sbchStaffingDetail : sbchStaffingDetailList) {
                BeanUtils.copyProperties(sbchStaffingInfo,sbchStaffingDetail, "remark");
                sbchStaffingDetail.setId(IdWorker.createId());
                sbchStaffingDetail.setDeptId(sbchStaffingInfo.getDeptId());
                sbchStaffingDetail.setStaffingId(sbchStaffingInfo.getId());
            }
            sbchStaffingDetailService.batchInsert(sbchStaffingDetailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchStaffingInfo.getButtonMark())){
            //插入确认记录
            String menuId = sbchStaffingInfo.getMenuId();
            String stageIdentity = sbchStaffingInfo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
        return sbchStaffingInfo.getId();
    }

    /**
     * 修改设备人员配置策划
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchStaffingInfo(SbchStaffingInfo sbchStaffingInfo) {
        //判断该表是否为有效版本
        SbchStaffingInfo temp = selectSbchStaffingInfoById(sbchStaffingInfo.getId());
        if("1".equals(temp.getIsValid())){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可进行编辑修改");
        }
//        EntityUtils.setUpdateInfo(sbchStaffingInfo);
        //删除子表数据
        sbchStaffingDetailService.deleteSbchStaffingDetailByStaffingId(sbchStaffingInfo.getId());
        //添加子表数据
        List<SbchStaffingDetail> sbchStaffingDetailList = sbchStaffingInfo.getSbchStaffingDetailList();
        for (SbchStaffingDetail sbchStaffingDetail : sbchStaffingDetailList) {
            BeanUtils.copyProperties(sbchStaffingInfo,sbchStaffingDetail);
            sbchStaffingDetail.setStaffingId(sbchStaffingInfo.getId());
        }
        sbchStaffingDetailService.batchInsert(sbchStaffingDetailList);
        sbchStaffingInfoMapper.updateSbchStaffingInfo(sbchStaffingInfo);
        return 1;

    }

    /**
     * 删除设备人员配置策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchStaffingInfoByIds(String ids) {
        //查询删除的id中是否有版本为有效的
        SbchStaffingInfo sbchStaffingInfo = new SbchStaffingInfo();
        sbchStaffingInfo.setIds(Convert.toStrArray(ids));
        sbchStaffingInfo.setIsValid("1");
        List<SbchStaffingInfo> sbchStaffingInfos = sbchStaffingInfoMapper.selectSbchStaffingInfoList(sbchStaffingInfo);
        if(!ObjectNullUtil.isEmpty(sbchStaffingInfos)){
            List<String> collect = sbchStaffingInfos.stream().map(t -> t.getFormNo()).collect(Collectors.toList());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可删除："+ StringUtils.join(collect,","));
        }
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchStaffingInfoMapper.deleteSbchStaffingInfoByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除设备人员配置策划信息
     * 
     * @param id 设备人员配置策划ID
     * @return 结果
     */
    public int deleteSbchStaffingInfoById(Long id) {
        return sbchStaffingInfoMapper.deleteSbchStaffingInfoById(id);
    }

    @Override
    @Transactional
    public Long changeVersionSave(SbchStaffingInfo sbchStaffingInfo) {
        //判断该版本是否有效
        SbchStaffingInfo oldInfo = sbchStaffingInfoMapper.selectSbchStaffingInfoById(sbchStaffingInfo.getId());
        if(oldInfo.getIsValid().equals("0")){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"请选择一条有效数据进行变更");
        }
        //oldInfo.setIsValid("0");
//        EntityUtils.setUpdateInfo(oldInfo);
//        EntityUtils.setCreateInfo(sbchStaffingInfo);
        sbchStaffingInfo.setId(IdWorker.createId());
        sbchStaffingInfo.setVersionNo(oldInfo.getVersionNo().add(new BigDecimal(1)));
        int i = oldInfo.getFormNo().lastIndexOf("-");
        String substring = oldInfo.getFormNo().substring(0, i);
        sbchStaffingInfo.setFormNo(substring+"-"+sbchStaffingInfo.getVersionNo().setScale(0));
        //修改旧版本
        sbchStaffingInfoMapper.updateSbchStaffingInfo(oldInfo);
        //新增新版本主表
        sbchStaffingInfoMapper.insertSbchStaffingInfo(sbchStaffingInfo);
        //新增新版本子表
        List<SbchStaffingDetail> sbchStaffingDetailList = sbchStaffingInfo.getSbchStaffingDetailList();
        for (SbchStaffingDetail sbchStaffingDetail : sbchStaffingDetailList) {
            sbchStaffingDetail.setStaffingId(sbchStaffingInfo.getId());
        }
        sbchStaffingDetailService.batchInsert(sbchStaffingDetailList);
        return sbchStaffingInfo.getId();
    }

    @Override
    public SbchStaffingInfo getList(BigDecimal version) {
        SbchStaffingInfo sbchStaffingInfo = new SbchStaffingInfo();
        version = VersionUtil.getVersion("sbch_staffing_info", version);
        sbchStaffingInfo.setVersionNo(version);
        List<SbchStaffingInfo> sbchStaffingInfos = sbchStaffingInfoMapper.selectSbchStaffingInfoList(sbchStaffingInfo);
        if(!ObjectNullUtil.isEmpty(sbchStaffingInfos)){
            SbchStaffingInfo sbchStaffingInfo1 = sbchStaffingInfos.get(0);
            BeanUtils.copyProperties(sbchStaffingInfo1,sbchStaffingInfo);
            SbchStaffingDetail sbchStaffingDetail = new SbchStaffingDetail();
            sbchStaffingDetail.setStaffingId(sbchStaffingInfo1.getId());
            List<SbchStaffingDetail> sbchStaffingDetails = sbchStaffingDetailService.selectSbchStaffingDetailList(sbchStaffingDetail);
            sbchStaffingInfo.setSbchStaffingDetailList(sbchStaffingDetails);
        }
        sbchStaffingInfo.setVersion(version);
        sbchStaffingInfo.setStageIdentity(qqchReviewService.getStage());
        return sbchStaffingInfo;
    }
}
