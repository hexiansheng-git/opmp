package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.domain.QyzsSafeSpecialEquipment;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo.QqchSpecialBigEquListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchSpecialBigEquListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.vo.QqchSpecialBigEquRiskMeasureVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.mapper.QqchSpecialBigEquRiskMeasureMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service.IQqchSpecialBigEquRiskMeasureService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlan;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.jsonwebtoken.lang.Assert;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-08-08 15:22:12
 * @remark
 */
@Service
public class QqchSpecialBigEquRiskMeasureServiceImpl implements IQqchSpecialBigEquRiskMeasureService {

    @Autowired
    private QqchSpecialBigEquRiskMeasureMapper qqchSpecialBigEquRiskMeasureMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchSpecialBigEquListService specialBigEquListService;
    @Autowired
    private ISbchEquipmentSpecialPlanService sbchEquipmentSpecialPlanService;

    @Value("${gm.back-url}")
    private String gmUrl;


    public QqchSpecialBigEquRiskMeasure getQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        return qqchSpecialBigEquRiskMeasureMapper.getQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int insertQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setId(IdWorker.createId());
        qqchSpecialBigEquRiskMeasure.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.insertQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }



    @Transactional
    public int updateQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.updateQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int updateQqchSpecialBigEquRiskMeasureList(List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList) {
        for (QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure : qqchSpecialBigEquRiskMeasureList) {
            qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigEquRiskMeasureMapper.updateQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureList);
    }

    @Transactional
    public int deleteQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int deleteQqchSpecialBigEquRiskMeasureByPks(List<Long> qqchSpecialBigEquRiskMeasurePkList) {
        return qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasureByPks(qqchSpecialBigEquRiskMeasurePkList);
    }

    /**
     * 列表接口
     * <p>
     * 查询逻辑：
     * 1获取841所有数据
     * 2根据841中的特种设备类型去总部特种设备库查询当前设备拥有的风险信息，管控措施等内容
     * 3返回结果，设备与设备存在的风险是1:N关系：设备名称1 风险内容1 管控措施1
     *                                            风险内容2 管控措施2
     */
    public QqchSpecialBigEquRiskMeasureVo getQqchSpecialBigEquRiskMeasureList(QqchSpecialBigEquRiskMeasure requestParam) {
        QqchSpecialBigEquRiskMeasureVo vo = new QqchSpecialBigEquRiskMeasureVo();
        List<QqchSpecialBigEquRiskMeasure> result = new ArrayList<>();
        BigDecimal version = requestParam.getVersion();
        version = VersionUtil.getVersion("qqch_special_big_equ_risk_measure", version);
        //获取8.4.1中有所有的设备
        QqchSpecialBigEquList param = new QqchSpecialBigEquList();
        param.setVersion(version);
        QqchSpecialBigEquListVo specialBigEquList = specialBigEquListService.getSpecialBigEquList(param);
        List<QqchSpecialBigEquList> qqchSpecialBigEquListList = specialBigEquList.getQqchSpecialBigEquListList();
        if (CollectionUtil.isNotEmpty(qqchSpecialBigEquListList)) {
            //总部知识库查询条件 ，{设备类型}
            Set<String> equTypeList = qqchSpecialBigEquListList.stream().map(QqchSpecialBigEquList::getEquType).filter(StrUtil::isNotBlank).collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(equTypeList)) {
                String url = gmUrl + "/gm/qyzsSafeSpecialEquipment/getChilderByKind3?kind3Arr={kind3Arr}";
                HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
                HashMap<String, Object> mapParam = new HashMap<>();
                //841“设备类型” 等于 总部特种设备“品种”字段
                mapParam.put("kind3Arr", equTypeList.toArray());
                AjaxResult ajaxResult = RestTemplateUtils.get(url, httpEntity, AjaxResult.class, mapParam);
                Assert.isTrue(AjaxResult.isSuccess(ajaxResult), "请求总部接口失败:" + ajaxResult.get(AjaxResult.MSG_TAG));
                Assert.isTrue(ajaxResult.get(AjaxResult.DATA_TAG) != null, "请求总部接口失败:" + ajaxResult.get(AjaxResult.MSG_TAG));
                String jsonString = JSON.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG));
                List<QyzsSafeSpecialEquipment> qyzsSafeSpecialEquipments = JSON.parseArray(jsonString, QyzsSafeSpecialEquipment.class);
                //总部知识库特种设备，按设备类型分组
                Map<String, List<QyzsSafeSpecialEquipment>> collect = qyzsSafeSpecialEquipments.stream().collect(Collectors.groupingBy(QyzsSafeSpecialEquipment::getKind3));
                //遍历841数据
                for (QqchSpecialBigEquList p : qqchSpecialBigEquListList) {
                    //根据特种设备类型获取当前设备的其它信息,包括风险内容，措施等
                    List<QyzsSafeSpecialEquipment> qyzsSafeSpecialEquipments1 = collect.get(p.getEquType());
                    if (CollUtil.isEmpty(qyzsSafeSpecialEquipments1)) {
                        QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure = new QqchSpecialBigEquRiskMeasure();
                        qqchSpecialBigEquRiskMeasure.setEquName(p.getEquName());
                        result.add(qqchSpecialBigEquRiskMeasure);
                        continue;
                    }
                    //遍历当前设备类型下的所有风险信息
                    for (QyzsSafeSpecialEquipment qyzsSafeSpecialEquipment : qyzsSafeSpecialEquipments1) {
                        QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure = new QqchSpecialBigEquRiskMeasure();
                        qqchSpecialBigEquRiskMeasure.setControlMeasures(qyzsSafeSpecialEquipment.getControlMeasure());
                        qqchSpecialBigEquRiskMeasure.setEquName(p.getEquName());
                        qqchSpecialBigEquRiskMeasure.setRiskContent(qyzsSafeSpecialEquipment.getRiskEvent());
                        result.add(qqchSpecialBigEquRiskMeasure);
                    }
                }
            }
        }
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(result);
        return vo;
    }

    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchSpecialBigEquRiskMeasureVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSpecialBigEquRiskMeasure> riskMeasureList = vo.getList();
        if(CollectionUtils.isNotEmpty(riskMeasureList)){
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(riskMeasureList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchSpecialBigEquRiskMeasureList(riskMeasureList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchSpecialBigEquRiskMeasureList(List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList,BigDecimal version) {
        //删除旧数据
        QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure1 = new QqchSpecialBigEquRiskMeasure();
        qqchSpecialBigEquRiskMeasure1.setVersion(version);
        qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure1);
        if (CollectionUtils.isEmpty(qqchSpecialBigEquRiskMeasureList)) {
            return 0;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure : qqchSpecialBigEquRiskMeasureList) {
            qqchSpecialBigEquRiskMeasure.setValid(valid);
            qqchSpecialBigEquRiskMeasure.setVersion(version);
            qqchSpecialBigEquRiskMeasure.setId(IdWorker.createId());
            qqchSpecialBigEquRiskMeasure.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchSpecialBigEquRiskMeasure.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigEquRiskMeasureMapper.insertQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureList);
    }
}
