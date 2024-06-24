package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.nacos.common.http.param.MediaType;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerListMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
@Service
public class QqchDangerListServiceImpl implements IQqchDangerListService {

    @Autowired
    private QqchDangerListMapper qqchDangerListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    @Value("${gm.back-url}")
    private String gmUrl;

    //获取总部知识库危大工程清单
    public AjaxResult getGmRiskBigProjList(QyzsSafeRiskBigProjItem qyzsSafeRiskBigProj){
        String url = gmUrl + "/gm/qyzsSafeRiskBigProj/list";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        HashMap<String, String> map  = new HashMap<>();
        map.put("riskProjType", qyzsSafeRiskBigProj.getRiskProjType());
        map.put("judgmentCondition", qyzsSafeRiskBigProj.getJudgmentCondition());
        map.put("riskEvent", qyzsSafeRiskBigProj.getRiskEvent());
        map.put("possibleConsequence", qyzsSafeRiskBigProj.getPossibleConsequence());
        HttpEntity<Map<String,String>> httpEntity = new HttpEntity<>(map, headers);
        return RestTemplateUtils.post(url, httpEntity, AjaxResult .class);
    }


    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchDangerListVo getQqchDangerListList(BigDecimal version) {
        QqchDangerListVo vo = new QqchDangerListVo();
        version = VersionUtil.getVersion("qqch_danger_list", version);

        QqchDangerList qryParam = new QqchDangerList();
        qryParam.setVersion(version);
        List<QqchDangerList> list = qqchDangerListMapper.getQqchDangerListList(qryParam);

        this.setDictData(list);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    public void setDictData(List<QqchDangerList> list){
        LinkedHashMap<String, String> dangerLevelMap = DictUtil.getDictDataName("danger_level");
        for (QqchDangerList qqchDangerList : list) {
            String dangerLevel = qqchDangerList.getDangerLevel();
            String dangerLevelLabel = dangerLevelMap.get(dangerLevel);
            qqchDangerList.setDangerLevelLabel(dangerLevelLabel);
        }
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchDangerListVo voParam) {

        // 清空数据库表中数据
        QqchDangerList deleteParam = new QqchDangerList();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerListMapper.deleteQqchDangerList(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerList qqchDangerList : voParam.getList()) {
                qqchDangerList.setId(IdWorker.createId());
                qqchDangerList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerList.setValid(Valid.YES);
                }
                qqchDangerList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerList.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerList.setCreateTime(DateUtils.getNowDate());
            }
            qqchDangerListMapper.insertQqchDangerListList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 同步数据
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchDangerListVo voParam) {
        // 从数据库查出数据
        List<QqchDangerList> dbList = this.getQqchDangerListList(voParam.getVersion()).getList();

        // 清空数据库表中数据
        QqchDangerList deleteParam = new QqchDangerList();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerListMapper.deleteQqchDangerList(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerList qqchDangerList : voParam.getList()) {
                qqchDangerList.setId(IdWorker.createId());
                qqchDangerList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerList.setValid(Valid.YES);
                }
                qqchDangerList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerList.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerList.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchDangerList db : dbList) {
                        String dbCode = db==null?"":db.getSchemeCode();
                        String scheCode = qqchDangerList==null?"":qqchDangerList.getSchemeCode();
                        if (StringUtils.equals(dbCode, scheCode)) {
                            qqchDangerList.setDecisionCondition(db.getDecisionCondition());
                        }
                    }
                }
            }
            qqchDangerListMapper.insertQqchDangerListList(voParam.getList());
        }
    }
}
