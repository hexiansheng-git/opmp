package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.vo.QqchQualityRiskControlMeasuresVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.service.IQqchQualityRiskControlMeasuresService;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.vo.QqchQualityRiskListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.mapper.QqchQualityRiskListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service.IQqchQualityRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.quality.qyzsQualityRisk.domain.QyzsQualityRisk;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ldd
 * @date 2023-08-04 10:02:39
 * @remark  9.3.1 质量风险清单
 */
@Service
public class QqchQualityRiskListServiceImpl implements IQqchQualityRiskListService {

    @Autowired
    private QqchQualityRiskListMapper qqchQualityRiskListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchQualityRiskControlMeasuresService qualityRiskService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public QqchQualityRiskList getQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        return qqchQualityRiskListMapper.getQqchQualityRiskList(qqchQualityRiskList);
    }


    @Transactional
    public int insertQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setId(IdWorker.createId());
        qqchQualityRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.insertQqchQualityRiskList(qqchQualityRiskList);
    }


    @Transactional
    public int updateQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.updateQqchQualityRiskList(qqchQualityRiskList);
    }

    @Transactional
    public int updateQqchQualityRiskListList(List<QqchQualityRiskList> qqchQualityRiskListList) {
        for (QqchQualityRiskList qqchQualityRiskList : qqchQualityRiskListList) {
            qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchQualityRiskListMapper.updateQqchQualityRiskListList(qqchQualityRiskListList);
    }

    @Transactional
    public int deleteQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.deleteQqchQualityRiskList(qqchQualityRiskList);
    }

    @Transactional
    public int deleteQqchQualityRiskListByPks(List<Long> qqchQualityRiskListPkList) {
        return qqchQualityRiskListMapper.deleteQqchQualityRiskListByPks(qqchQualityRiskListPkList);
    }


    /**
     * 列表接口
     *
     * @param qqchQualityRiskList
     * @return
     */
    @Override
    public QqchQualityRiskListVo getQqchQualityRiskListList(QqchQualityRiskList qqchQualityRiskList) {
        QqchQualityRiskListVo vo = new QqchQualityRiskListVo();

        BigDecimal version = qqchQualityRiskList.getVersion();
        version = VersionUtil.getVersion("qqch_quality_risk_list", version);

        qqchQualityRiskList.setVersion(version);
        List<QqchQualityRiskList> qqchQualityRiskListList = qqchQualityRiskListMapper.getQqchQualityRiskListList(qqchQualityRiskList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchQualityRiskListList(qqchQualityRiskListList);
        return vo;
    }


    @Override
    @Transactional
    public void save(QqchQualityRiskListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchQualityRiskList> qqchQualityRiskListList = vo.getQqchQualityRiskListList();

        this.insertQqchQualityRiskListList(qqchQualityRiskListList, version);

        //与9.3.2数据同步
        this.dataSync(qqchQualityRiskListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);

            //推送总部版知识库
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                //过滤掉 “质量风险内容”为空的数据
                List<QqchQualityRiskList> nonNullList = qqchQualityRiskListList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getContent()))
                        .collect(Collectors.toList());
                List<QyzsQualityRisk> pushData = new ArrayList<>();
                nonNullList.forEach(p -> {
                    QyzsQualityRisk qyzsQualityRisk = new QyzsQualityRisk();
                    qyzsQualityRisk.setRiskContent(p.getContent());
                    qyzsQualityRisk.setRiskReason(p.getReason());
                    qyzsQualityRisk.setConsequence(p.getResult());
                    qyzsQualityRisk.setRemark(p.getRemark());
                    qyzsQualityRisk.setEditer(SecurityUtils.getUserName());
                    qyzsQualityRisk.setEditDate(DateUtil.date());
                    pushData.add(qyzsQualityRisk);
                });
                rocketMQTemplate.convertAndSend("qqch_quality_risk_list:tenantSuccess", pushData);
            });
        }
    }

    /***
     * 功能描述: //与9.3.2数据同步
     *         //逻辑: 1.如果页面传入数据为空，则清空风险管控措施数据，否则进入2
     *         //     2.界面传入不为空，原风险管控数据为空，则新增数据，否则进入3
     *         //     3.界面传入数据和原风险管控数据都不为空
     *         //     遍历界面传入数据：与原有的风险管控措施数据匹配，匹配成功修改，否则新增；
     *         //     遍历原风险管控数据：与传入数据匹配，匹配不成功删除
     * 作者: fushudong
     * 时间: 2023/8/24
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void dataSync(List<QqchQualityRiskList> qqchQualityRiskListList,  BigDecimal version) {
        //界面传入数据为空，删除所有9.3.2数据
        if (CollectionUtils.isEmpty(qqchQualityRiskListList)) {
            QqchQualityRiskControlMeasures param = new QqchQualityRiskControlMeasures();
            param.setVersion(version);
            qualityRiskService.deleteQqchQualityRiskControlMeasures(param);
            return;
        }
        // 查询9.3.2数据
        QqchQualityRiskControlMeasures control = new QqchQualityRiskControlMeasures();
        control.setVersion(version);
        QqchQualityRiskControlMeasuresVo firstControlVo = qualityRiskService.getQqchQualityRiskControlMeasuresList(control);
        List<QqchQualityRiskControlMeasures> firstControl = firstControlVo.getQqchQualityRiskControlMeasuresList();

        if (CollectionUtils.isEmpty(firstControl)) {
            //界面传入不为空，9.3.2数据为空，新增数据
            List<QqchQualityRiskControlMeasures> objects = new ArrayList<>();
            qqchQualityRiskListList.forEach(param -> {
                QqchQualityRiskControlMeasures bean = new QqchQualityRiskControlMeasures();
                bean.setListId(param.getId());
                bean.setContent(param.getContent());
                bean.setReason(param.getReason());
                bean.setRiskLevel(param.getRiskLevel());
                objects.add(bean);
            });
            qualityRiskService.insertList(objects, version, "save");
        } else {
            //界面传入数据和9.3.2数据都不为空

            //遍历界面传入数据：与9.3.2数据匹配，匹配成功修改，否则新增
            Map<Long, Long> OriCollect = firstControl.stream().collect(Collectors.toMap(QqchQualityRiskControlMeasures::getListId, QqchQualityRiskControlMeasures::getId));
            List<QqchQualityRiskControlMeasures> objects = new ArrayList<>();
            List<QqchQualityRiskControlMeasures> addObjects = new ArrayList<>();
            for (QqchQualityRiskList param : qqchQualityRiskListList) {
                Long id = param.getId();
                QqchQualityRiskControlMeasures bean = new QqchQualityRiskControlMeasures();
                bean.setListId(id);
                bean.setListId(param.getId());
                bean.setContent(param.getContent());
                bean.setReason(param.getReason());
                bean.setRiskLevel(param.getRiskLevel());
                if (OriCollect.containsKey(id)) {
                    //执行修改
                    bean.setId(OriCollect.get(id));
                    objects.add(bean);
                } else {
                    //执行新增
                    addObjects.add(bean);
                }
            }
            if (CollectionUtils.isNotEmpty(objects)) {
                qualityRiskService.insertList(objects, version, "update");
            }
            if (CollectionUtils.isNotEmpty(addObjects)) {
                qualityRiskService.insertList(addObjects, version, "save");
            }

            //遍历9.3.2数据：与传入数据匹配，匹配不成功删除
            Set<Long> newCollect = qqchQualityRiskListList.stream().map(QqchQualityRiskList::getId).collect(Collectors.toSet());
            List<Long> oriIds = new ArrayList<>();
            for (QqchQualityRiskControlMeasures bean : firstControl) {
                Long listId = bean.getListId();
                if (newCollect.contains(listId)) {
                    continue;
                }
                oriIds.add(bean.getId());
                if (CollectionUtils.isNotEmpty(oriIds)) {
                    qualityRiskService.deleteQqchQualityRiskControlMeasuresByPks(oriIds);
                }
            }
        }
    }


    public void insertQqchQualityRiskListList(List<QqchQualityRiskList> qqchQualityRiskListList, BigDecimal version) {
        //删除旧数据
        QqchQualityRiskList qqchQualityRiskList = new QqchQualityRiskList();
        qqchQualityRiskList.setVersion(version);
        qqchQualityRiskListMapper.deleteQqchQualityRiskList(qqchQualityRiskList);

        if (CollectionUtils.isEmpty(qqchQualityRiskListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchQualityRiskList riskList : qqchQualityRiskListList) {
            riskList.setId(IdWorker.createId());
            riskList.setValid(valid);
            riskList.setVersion(version);
            riskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            riskList.setCreateUserName(SecurityUtils.getUserName());
            riskList.setCreateTime(DateUtils.getNowDate());
            // TODO: 2023/8/4
            //
            // 如果是从知识库选择的数据，允许修改，但不再进入知识库，勾选框直接置灰；如果是自己手动输入的数据，可以选择是否入库，选择入库的话，在点击确认后直接同步到知识库
            // 判断是否入知识库库，如果为空，这说明数据本身就是库里的不存在改操作，
            //如果为1 则判断库里是否有，没有则入库风险库
            //如果为0 则不入库
            if(riskList.getWarehousing()!=null){
                if(riskList.getWarehousing().equals("1")){
                    //入库
                }
            }

        }
        qqchQualityRiskListMapper.insertQqchQualityRiskListList(qqchQualityRiskListList);
    }
}