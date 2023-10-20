package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchDangerConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
@Service
public class QqchDangerConstructionListServiceImpl implements IQqchDangerConstructionListService {

    @Autowired
    private QqchDangerConstructionListMapper qqchDangerConstructionListMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchConstructionListService qqchConstructionListService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchDangerListService qqchDangerListService;

    public QqchDangerConstructionListVo getQqchDangerConstructionListList(BigDecimal version) {
        QqchDangerConstructionListVo vo = new QqchDangerConstructionListVo();
        version = VersionUtil.getVersion("qqch_danger_construction_list", version);
        vo.setVersion(version);

        QqchDangerConstructionList qryParam = new QqchDangerConstructionList();
        qryParam.setVersion(version);
        List<QqchDangerConstructionList> list = qqchDangerConstructionListMapper
            .getQqchDangerConstructionListList(qryParam);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchDangerConstructionListVo qqchDangerConstructionListVo) {
        // 清空数据库表中数据
        QqchDangerConstructionList deleteParam = new QqchDangerConstructionList();
        deleteParam.setVersion(qqchDangerConstructionListVo.getVersion());
        qqchDangerConstructionListMapper.deleteQqchDangerConstructionList(deleteParam);

        // 危大工程清单
        QqchDangerListVo qqchDangerListVo = new QqchDangerListVo();
        List<QqchDangerList> list = new ArrayList<>();
        int sort = 1;
        List<QqchDangerConstructionList> dangerConstructionListVoList = qqchDangerConstructionListVo.getList();
        for (QqchDangerConstructionList qqchDangerConstructionList : dangerConstructionListVoList) {
            qqchDangerConstructionList.setId(IdWorker.createId());
            qqchDangerConstructionList.setVersion(qqchDangerConstructionListVo.getVersion());
            if (qqchDangerConstructionListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                qqchDangerConstructionList.setValid(Valid.YES);
            }
            qqchDangerConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDangerConstructionList.setCreateUserName(SecurityUtils.getUserName());
            qqchDangerConstructionList.setCreateTime(DateUtils.getNowDate());
            qqchDangerConstructionList.setSort(sort++);

            QqchDangerList qqchDangerList = new QqchDangerList();
            qqchDangerList.setSchemeCode(qqchDangerConstructionList.getSchemeCode());
            qqchDangerList.setSchemeName(qqchDangerConstructionList.getSchemeName());
            qqchDangerList.setDangerLevel(qqchDangerConstructionList.getDangerLevel());
            qqchDangerList.setWbsCode(qqchDangerConstructionList.getWbsCode());
            qqchDangerList.setWbsName(qqchDangerConstructionList.getWbsName());
            list.add(qqchDangerList);
            qqchDangerListVo.setList(list);
            qqchDangerListVo.setVersion(qqchDangerConstructionListVo.getVersion());
        }

        if (CollectionUtils.isNotEmpty(dangerConstructionListVoList)) {
            qqchDangerConstructionListMapper
                .insertQqchDangerConstructionListList(dangerConstructionListVoList);
        }

        // 同步到8.3.1 危大工程清单
        qqchDangerListService.syncData(qqchDangerListVo);

        String buttonMark = qqchDangerConstructionListVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchDangerConstructionListVo.getMenuId();
            String stageIdentity = qqchDangerConstructionListVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void syncData(QqchDangerConstructionListVo qqchDangerConstructionListVo) {
        BigDecimal version = qqchDangerConstructionListVo.getVersion();
        // 获取施工方案清单中危大等级为危大、超危大的方案数据
        List<QqchConstructionList> constructionList = qqchConstructionListService.getBigDangerLevelConstructionList();

        List<QqchDangerConstructionList> dangerList = qqchDangerConstructionListVo.getList();
        Map<String, QqchDangerConstructionList> map = dangerList.stream().collect(Collectors.toMap(QqchDangerConstructionList::getSchemeCode,o -> o));

        // 构造新的list
        List<QqchDangerConstructionList> insertList = new ArrayList<>();
        constructionList.stream().forEach(construction -> {
            QqchDangerConstructionList insert = new QqchDangerConstructionList();
            BeanUtils.copyProperties(construction, insert);
            insert.setId(IdWorker.createId());
            insert.setCreateUser(SecurityUtils.getUserName());
            insert.setCreateTime(DateUtils.getNowDate());
            insert.setVersion(version);
            insert.setValid(Valid.YES);
            String schemeCode = construction.getSchemeCode();
            QqchDangerConstructionList qqchDangerConstructionList = map.get(schemeCode);
            if(qqchDangerConstructionList != null){
                insert.setKeySpecialProcesses(qqchDangerConstructionList.getKeySpecialProcesses());
                insert.setBriefDescription(qqchDangerConstructionList.getBriefDescription());
                insert.setMainMeasure(qqchDangerConstructionList.getMainMeasure());
            }
            insertList.add(insert);
        });

        qqchDangerConstructionListVo.setList(insertList);
        this.batchSave(qqchDangerConstructionListVo);
    }

    @Override
    public List<QqchDangerConstructionList> getByWbsCodes(String[] wbsCodes) {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_danger_construction_list");
        return qqchDangerConstructionListMapper.getByWbsCodes(wbsCodes, maxVersion);
    }
}
