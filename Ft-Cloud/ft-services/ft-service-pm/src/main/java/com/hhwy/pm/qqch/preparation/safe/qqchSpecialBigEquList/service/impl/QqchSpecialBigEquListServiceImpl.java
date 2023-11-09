package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchInformationSheet;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchTransitionRecord;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo.QqchSpecialBigEquListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper.QqchSpecialBigEquListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchInformationSheetService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchSpecialBigEquListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchTransitionRecordService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialService;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-08-08 11:39:00
 * @remark
 */
@Service
public class QqchSpecialBigEquListServiceImpl implements IQqchSpecialBigEquListService {

    @Autowired
    private QqchSpecialBigEquListMapper qqchSpecialBigEquListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchInformationSheetService qqchInformationSheetService;
    @Autowired
    private IQqchTransitionRecordService qqchTransitionRecordService;
    @Autowired
    private ISbchEquipmentSpecialService equipmentSpecialService;


    public QqchSpecialBigEquList getQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        return qqchSpecialBigEquListMapper.getQqchSpecialBigEquList(qqchSpecialBigEquList);
    }

    @Transactional
    public int insertQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        qqchSpecialBigEquList.setId(IdWorker.createId());
        qqchSpecialBigEquList.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquList.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquListMapper.insertQqchSpecialBigEquList(qqchSpecialBigEquList);
    }


    @Transactional
    public int updateQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        qqchSpecialBigEquList.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquList.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquListMapper.updateQqchSpecialBigEquList(qqchSpecialBigEquList);
    }

    @Transactional
    public int updateQqchSpecialBigEquListList(List<QqchSpecialBigEquList> qqchSpecialBigEquListList) {
        for (QqchSpecialBigEquList qqchSpecialBigEquList : qqchSpecialBigEquListList) {
            qqchSpecialBigEquList.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialBigEquList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigEquListMapper.updateQqchSpecialBigEquListList(qqchSpecialBigEquListList);
    }

    @Transactional
    public int deleteQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        qqchSpecialBigEquList.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquList.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquListMapper.deleteQqchSpecialBigEquList(qqchSpecialBigEquList);
    }

    @Transactional
    public int deleteQqchSpecialBigEquListByPks(List<Long> qqchSpecialBigEquListPkList) {
        return qqchSpecialBigEquListMapper.deleteQqchSpecialBigEquListByPks(qqchSpecialBigEquListPkList);
    }


    /**
     * 列表接口
     * @param qqchSpecialBigEquList
     * @return
     */
    @Transactional
    public QqchSpecialBigEquListVo getQqchSpecialBigEquListList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        QqchSpecialBigEquListVo vo = new QqchSpecialBigEquListVo();
        BigDecimal version = VersionUtil.getVersion("qqch_special_big_equ_list",qqchSpecialBigEquList.getVersion());
        //查询7.6.1数据
        SbchEquipmentSpecial list = equipmentSpecialService.getList(version);
        List<SbchEquipmentSpecialDetails> detailsList = list.getDetailsList();
        //查询841设备
        List<QqchSpecialBigEquList> qqchSpecialBigEquListList = qqchSpecialBigEquListMapper.getQqchSpecialBigEquListList(qqchSpecialBigEquList);
        List<QqchInformationSheet> qqchInformationSheetList = qqchInformationSheetService.getQqchInformationSheetList(new QqchInformationSheet());
        Map<Long, List<QqchInformationSheet>> sheetMap = qqchInformationSheetList.stream().collect(Collectors.groupingBy(QqchInformationSheet::getOutId));
        List<QqchTransitionRecord> qqchTransitionRecordList = qqchTransitionRecordService.getQqchTransitionRecordList(new QqchTransitionRecord());
        Map<Long, List<QqchTransitionRecord>> recordMap = qqchTransitionRecordList.stream().collect(Collectors.groupingBy(QqchTransitionRecord::getOutId));
        //填充附件信息
        for (QqchSpecialBigEquList specialBigEquList : qqchSpecialBigEquListList) {
            specialBigEquList.setQqchInformationSheetList(sheetMap.get(specialBigEquList.getId()));
            specialBigEquList.setQqchTransitionRecordList(recordMap.get(specialBigEquList.getId()));
        }
        //将761新增的数据追加到841中
        Map<String, List<QqchSpecialBigEquList>> collect = qqchSpecialBigEquListList.stream().collect(Collectors.groupingBy(QqchSpecialBigEquList::getEquName));
        List<QqchSpecialBigEquList> addList = new ArrayList<>();
        for (SbchEquipmentSpecialDetails bean : detailsList ) {
            String materialName = bean.getMaterialName();
            if (!collect.containsKey(materialName)) {
                QqchSpecialBigEquList equList = new QqchSpecialBigEquList();
                equList.setSpec(bean.getMaterialSpec());
                equList.setEquSourse(bean.getSbPurchaseSource());
                equList.setProduceFactory(bean.getSbProductFactory());
                equList.setEquName(bean.getMaterialName());
                addList.add(equList);
            }
        }
        //保存新增的数据
        if (CollectionUtil.isNotEmpty(addList)){
            this.insertQqchSpecialBigEquListList(addList, version);
            qqchSpecialBigEquListList.addAll(addList);
        }
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSpecialBigEquListList(qqchSpecialBigEquListList);
        return vo;
    }

    /**
     * 只获取主表list
     * @param qqchSpecialBigEquList
     * @return
     */
    public QqchSpecialBigEquListVo getSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList) {
        QqchSpecialBigEquListVo vo = new QqchSpecialBigEquListVo();
        BigDecimal version = VersionUtil.getVersion("qqch_special_big_equ_list",qqchSpecialBigEquList.getVersion());
        QqchCompleteDesignHandover qqchCompleteDesignHandover = new QqchCompleteDesignHandover();
        qqchCompleteDesignHandover.setVersion(version);
        List<QqchSpecialBigEquList> qqchSpecialBigEquListList = qqchSpecialBigEquListMapper.getQqchSpecialBigEquListList(qqchSpecialBigEquList);
        vo.setQqchSpecialBigEquListList(qqchSpecialBigEquListList);
        return vo;
    }




    @Override
    public void save(QqchSpecialBigEquListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSpecialBigEquList> qqchSpecialBigEquListList = vo.getQqchSpecialBigEquListList();
        this.insertQqchSpecialBigEquListList(qqchSpecialBigEquListList, version);

        if (CollectionUtils.isEmpty(qqchSpecialBigEquListList)) {
            return;
        }
        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //校验数据必填
            JyDetailsUtil.jyDetails(qqchSpecialBigEquListList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void insertQqchSpecialBigEquListList(List<QqchSpecialBigEquList> qqchSpecialBigEquListList,BigDecimal version) {
        //删除旧数据
        QqchSpecialBigEquList qqchSpecialBigEquList = new QqchSpecialBigEquList();
        qqchSpecialBigEquList.setVersion(version);
        qqchSpecialBigEquListMapper.deleteQqchSpecialBigEquList(qqchSpecialBigEquList);
        if (CollectionUtils.isEmpty(qqchSpecialBigEquListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchInformationSheet> informationSheets = new ArrayList<>();
        List<QqchTransitionRecord>  transitionRecords= new ArrayList<>();
        for (QqchSpecialBigEquList specialBigEquList : qqchSpecialBigEquListList) {
            specialBigEquList.setId(IdWorker.createId());
            specialBigEquList.setValid(valid);
            specialBigEquList.setVersion(version);
            specialBigEquList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            specialBigEquList.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            specialBigEquList.setCreateTime(DateUtils.getNowDate());
            List<QqchInformationSheet> qqchInformationSheetList = specialBigEquList.getQqchInformationSheetList();
            List<QqchTransitionRecord> qqchTransitionRecordList = specialBigEquList.getQqchTransitionRecordList();
            if(CollectionUtils.isNotEmpty(qqchInformationSheetList)){
                for (QqchInformationSheet qqchInformationSheet : qqchInformationSheetList) {
                    qqchInformationSheet.setId(IdWorker.createId());
                    qqchInformationSheet.setOutId(specialBigEquList.getId());
                    EntityUtils.setCreateUpdateInfo(qqchInformationSheetList);
                    informationSheets.add(qqchInformationSheet);
                }
                List<QqchInformationSheet> sheetList = informationSheets.stream().distinct().collect(Collectors.toList());
                qqchInformationSheetService.insertQqchInformationSheetList(sheetList);
            }
            if(CollectionUtils.isNotEmpty(qqchTransitionRecordList)){
                for (QqchTransitionRecord transitionRecord : qqchTransitionRecordList) {
                    transitionRecord.setId(IdWorker.createId());
                    transitionRecord.setOutId(specialBigEquList.getId());
                    EntityUtils.setCreateUpdateInfo(transitionRecord);
                    transitionRecords.add(transitionRecord);
                }
                List<QqchTransitionRecord> recordList = transitionRecords.stream().distinct().collect(Collectors.toList());
                qqchTransitionRecordService.insertQqchTransitionRecordList(recordList);
            }
        }
        qqchSpecialBigEquListMapper.insertQqchSpecialBigEquListList(qqchSpecialBigEquListList);
    }
}
