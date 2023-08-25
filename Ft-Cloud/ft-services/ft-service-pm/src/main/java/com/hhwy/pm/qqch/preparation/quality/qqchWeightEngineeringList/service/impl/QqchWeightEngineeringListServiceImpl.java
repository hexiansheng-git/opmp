package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo.QqchWeightEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service.IQqchWeightEngineeringControlService;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.mapper.QqchWeightEngineeringListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark
 */
@Service
public class QqchWeightEngineeringListServiceImpl implements IQqchWeightEngineeringListService {

    @Autowired
    private QqchWeightEngineeringListMapper qqchWeightEngineeringListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchWeightEngineeringControlService weightEngineControlService;


    public QqchWeightEngineeringList getQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        return qqchWeightEngineeringListMapper.getQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int insertQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setId(IdWorker.createId());
        qqchWeightEngineeringList.setCreateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setCreateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.insertQqchWeightEngineeringList(qqchWeightEngineeringList);
    }



    @Transactional
    public int updateQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.updateQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int updateQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList) {
        for (QqchWeightEngineeringList qqchWeightEngineeringList : qqchWeightEngineeringListList) {
            qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
            qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWeightEngineeringListMapper.updateQqchWeightEngineeringListList(qqchWeightEngineeringListList);
    }

    @Transactional
    public int deleteQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int deleteQqchWeightEngineeringListByPks(List<Long> qqchWeightEngineeringListPkList) {
        return qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringListByPks(qqchWeightEngineeringListPkList);
    }



    /**
     * 列表接口
     */
    public QqchWeightEngineeringListVo getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        QqchWeightEngineeringListVo vo = new QqchWeightEngineeringListVo();

        BigDecimal version = qqchWeightEngineeringList.getVersion();
        version = VersionUtil.getVersion("qqch_weight_engineering_list", version);

        qqchWeightEngineeringList.setVersion(version);
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = qqchWeightEngineeringListMapper.getQqchWeightEngineeringListList(qqchWeightEngineeringList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchWeightEngineeringListList(qqchWeightEngineeringListList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchWeightEngineeringListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = vo.getQqchWeightEngineeringListList();

        this.insertQqchWeightEngineeringListList(qqchWeightEngineeringListList, version);

        //向9.4.2数据同步
        this.dataSync(qqchWeightEngineeringListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList,BigDecimal version) {
        //删除旧数据
        QqchWeightEngineeringList qqchWeightEngineeringList = new QqchWeightEngineeringList();
        qqchWeightEngineeringList.setVersion(version);
        qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringList(qqchWeightEngineeringList);
        if (CollectionUtils.isEmpty(qqchWeightEngineeringListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchWeightEngineeringList engineeringList : qqchWeightEngineeringListList) {
            engineeringList.setId(IdWorker.createId());
            engineeringList.setValid(valid);
            engineeringList.setVersion(version);
            engineeringList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            engineeringList.setCreateUserName(SecurityUtils.getUserName());
            engineeringList.setCreateTime(DateUtils.getNowDate());
        }
        qqchWeightEngineeringListMapper.insertQqchWeightEngineeringListList(qqchWeightEngineeringListList);
    }

    /***
     * 功能描述: //向9.4.2数据同步
     *         //逻辑: 1.如果页面传入数据为空，则清空9.4.2数据，否则进入2
     *         //     2.界面传入不为空，原9.4.2数据为空，则新增数据，否则进入3
     *         //     3.界面传入数据和9.4.2数据都不为空
     *         //     遍历界面传入数据：与9.4.2数据匹配，匹配成功修改，否则新增；
     *         //     遍历9.4.2数据：与传入数据匹配，匹配不成功删除
     * 作者: fushudong
     * 时间: 2023/8/24
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void dataSync(List<QqchWeightEngineeringList> weightEngineeringList, BigDecimal version) {
        //界面传入数据为空，删除所有9.4.2数据
        if (CollectionUtils.isEmpty(weightEngineeringList)) {
            QqchWeightEngineeringControl param = new QqchWeightEngineeringControl();
            param.setVersion(version);
            weightEngineControlService.deleteQqchWeightEngineeringControl(param);
            return;
        }
        // 查询9.4.2数据
        QqchWeightEngineeringControl control = new QqchWeightEngineeringControl();
        control.setVersion(version);
        QqchWeightEngineeringControlVo controlVo = weightEngineControlService.getQqchWeightEngineeringControlList(control);
        List<QqchWeightEngineeringControl> controlList = controlVo.getQqchWeightEngineeringControlList();

        if (CollectionUtils.isEmpty(controlList)) {
            //界面传入不为空，9.4.2数据为空，新增数据
            List<QqchWeightEngineeringControl> objects = new ArrayList<>();
            weightEngineeringList.forEach(param -> {
                QqchWeightEngineeringControl bean = new QqchWeightEngineeringControl();
                bean.setListId(param.getId());
                bean.setName(param.getName());
                bean.setWbsName(param.getWbsName());
                bean.setPlannStartDate(param.getPlannStartDate());
                bean.setWorkGroup(param.getWorkGroup());
                objects.add(bean);
            });
            weightEngineControlService.insertList(objects, version);
        } else {
            //界面传入数据和9.4.2数据都不为空

            //遍历界面传入数据：与9.4.2数据匹配，匹配成功修改，否则新增
            Map<Long, Long> oriCollect = controlList.stream().collect(Collectors.toMap(QqchWeightEngineeringControl::getListId, QqchWeightEngineeringControl::getId));
            List<Long> ids = new ArrayList<>();
            List<QqchWeightEngineeringControl> objects = new ArrayList<>();
            for (QqchWeightEngineeringList param : weightEngineeringList) {
                Long id = param.getId();
                if (oriCollect.containsKey(id)) {
                    //执行修改
                    QqchWeightEngineeringControl bean = new QqchWeightEngineeringControl();
                    bean.setId(oriCollect.get(id));
                    bean.setListId(id);
                    bean.setName(param.getName());
                    bean.setWbsName(param.getWbsName());
                    bean.setPlannStartDate(param.getPlannStartDate());
                    bean.setWorkGroup(param.getWorkGroup());
                    objects.add(bean);
                } else {
                    //执行删除
                    ids.add(oriCollect.get(id));
                }
            }
            if (CollectionUtils.isNotEmpty(objects)) {
                weightEngineControlService.updateQqchWeightEngineeringControlList(objects);
            }
            if (CollectionUtils.isNotEmpty(ids)) {
                weightEngineControlService.deleteQqchWeightEngineeringControlByPks(ids);
            }

            //遍历9.4.2数据：与传入数据匹配，匹配不成功删除
            Set<Long> newCollect = weightEngineeringList.stream().map(QqchWeightEngineeringList::getId).collect(Collectors.toSet());
            List<Long> oriIds = new ArrayList<>();
            for (QqchWeightEngineeringControl quality : controlList) {
                Long listId = quality.getListId();
                if (newCollect.contains(listId)) {
                    continue;
                }
                oriIds.add(quality.getId());
                if (CollectionUtils.isNotEmpty(oriIds)) {
                    weightEngineControlService.deleteQqchWeightEngineeringControlByPks(ids);
                }
            }
        }
    }

}
