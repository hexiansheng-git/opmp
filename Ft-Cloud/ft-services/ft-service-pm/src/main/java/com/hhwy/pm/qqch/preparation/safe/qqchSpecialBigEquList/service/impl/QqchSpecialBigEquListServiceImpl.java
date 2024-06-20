package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
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
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialService;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @Value("${gm.back-url}")
    private String gmUrl;

    @Override
    public AjaxResult getGmRiskBigProjList(String kind1, String kind2, String kind3) {
        String url = gmUrl + "/gm/qyzsSafeSpecialEquipment/list?kind1={kind1}&kind2={kind2}&kind3={kind3}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class,  ObjectUtils.toMap("kind1", kind1, "kind2", kind2, "kind3", kind3));
    }

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
        SbchEquipmentSpecial equipmentSpecial = equipmentSpecialService.getList(version);
        List<SbchEquipmentSpecialDetails> detailsList761 = equipmentSpecial.getDetailsList();
        //查询841设备
        List<QqchSpecialBigEquList> qqchSpecialBigEquListList = qqchSpecialBigEquListMapper.getQqchSpecialBigEquListList(qqchSpecialBigEquList);
        Map<String, List<QqchSpecialBigEquList>> map841 = qqchSpecialBigEquListList.stream().collect(Collectors.groupingBy(QqchSpecialBigEquList::getPtVar1));
        //将761新增的数据追加到841中
        List<QqchSpecialBigEquList> addList = new ArrayList<>();
        //根据ptvar1获取已同步过的数据，更新管理编号
        map841.forEach((k,v) ->{
            List<SbchEquipmentSpecialDetails> detailsHave = detailsList761.stream().filter(equ -> equ.getPtVar1().equals(k)).collect(Collectors.toList());
            if (detailsHave != null && detailsHave.size() > 0) {
                v.forEach(plan -> {
                    plan.setManageNum(detailsHave.get(0).getManageCode());
                });
                addList.addAll(v);
            };
        });

        for (SbchEquipmentSpecialDetails bean : detailsList761 ) {
            String idOne = bean.getPtVar1();//唯一标识
            if (!map841.containsKey(idOne)) {
                QqchSpecialBigEquList equList = new QqchSpecialBigEquList();
                equList.setEquName(bean.getMaterialName());
                equList.setSpec(bean.getMaterialSpec());
                equList.setProduceFactory(bean.getSbProductFactory());
                equList.setPlanEntryDate(bean.getPlanEntryTime());
                equList.setPlanExitDate(bean.getPlanExitTime());
                equList.setPersonName(bean.getOperatorName());
                equList.setFileGroupId(bean.getOperatorCertificate());
                equList.setEquSourse(bean.getSbPurchaseSource());
                equList.setWhetherFirst("1");
                equList.setManageNum(bean.getManageCode());
                equList.setUpdateTime(new Date());
                equList.setPtVar1(idOne);
                addList.add(equList);
            }
        }
//        时间排序
        addList.sort((t1, t2) -> t2.getUpdateTime().compareTo(t1.getUpdateTime()));
        //保存新增的数据
        if (CollectionUtil.isNotEmpty(addList)){
            this.insertQqchSpecialBigEquListList(addList, version);
        }
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSpecialBigEquListList(addList);
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
        for (QqchSpecialBigEquList specialBigEquList : qqchSpecialBigEquListList) {
            specialBigEquList.setId(IdWorker.createId());
            specialBigEquList.setValid(valid);
            specialBigEquList.setVersion(version);
            specialBigEquList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            specialBigEquList.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            specialBigEquList.setCreateTime(DateUtils.getNowDate());
        }
        qqchSpecialBigEquListMapper.insertQqchSpecialBigEquListList(qqchSpecialBigEquListList);
    }

    @Value("${WSPlatform}")
    private String WSPlatform;
    @Override
    public AjaxResult selfEquDetail(String bhEqu) {
        AjaxResult ajaxResult;
        String url = WSPlatform + "/basic-api/fms/xcsb/XcsbCheckEquInfo/getDetailInfoByManageCode";
        Map map = ObjectUtils.toMap("manageCode",bhEqu);
        try {
            String resp = HttpUtil.get(url, map, 3000);
            ajaxResult = JSON.parseObject(resp, AjaxResult.class);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult = AjaxResult.error("网络异常，请求无法到达物设系统");
        }
        return ajaxResult;
    }
}
