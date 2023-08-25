package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.vo.QqchFirstArticleEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.IQqchFirstArticleEngineeringControlService;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo.QqchFirstArticleEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.mapper.QqchFirstArticleEngineeringListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service.IQqchFirstArticleEngineeringListService;
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
 * @date 2023-08-04 16:09:42
 * @remark
 */
@Service
public class QqchFirstArticleEngineeringListServiceImpl implements IQqchFirstArticleEngineeringListService {

    @Autowired
    private QqchFirstArticleEngineeringListMapper qqchFirstArticleEngineeringListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchFirstArticleEngineeringControlService firstArticleEngineService;


    public QqchFirstArticleEngineeringList getQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        return qqchFirstArticleEngineeringListMapper.getQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }
    

    @Transactional
    public int insertQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setId(IdWorker.createId());
        qqchFirstArticleEngineeringList.setCreateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setCreateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.insertQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }



    @Transactional
    public int updateQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.updateQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }

    @Transactional
    public int updateQqchFirstArticleEngineeringListList(List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList) {
        for (QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList : qqchFirstArticleEngineeringListList) {
            qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
            qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchFirstArticleEngineeringListMapper.updateQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringListByPks(List<Long> qqchFirstArticleEngineeringListPkList) {
        return qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringListByPks(qqchFirstArticleEngineeringListPkList);
    }

    /**
     *  列表接口
     * @param qqchFirstArticleEngineeringList
     * @return
     */
    public QqchFirstArticleEngineeringListVo getQqchFirstArticleEngineeringListList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        QqchFirstArticleEngineeringListVo vo = new QqchFirstArticleEngineeringListVo();

        BigDecimal version = qqchFirstArticleEngineeringList.getVersion();
        version = VersionUtil.getVersion("qqch_first_article_engineering_list", version);

        qqchFirstArticleEngineeringList.setVersion(version);
        List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList = qqchFirstArticleEngineeringListMapper.getQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
        return vo;
    }


    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchFirstArticleEngineeringListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList = vo.getQqchFirstArticleEngineeringListList();

        this.insertQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList, version);
        
        //向9.5.1同步数据
        this.dataSync(qqchFirstArticleEngineeringListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /***
     * 功能描述: //向9.5.2数据同步
     *         //逻辑: 1.如果页面传入数据为空，则清空9.5.2数据，否则进入2
     *         //     2.界面传入不为空，原9.5.2数据为空，则新增数据，否则进入3
     *         //     3.界面传入数据和9.5.2数据都不为空
     *         //     遍历界面传入数据：与9.5.2数据匹配，匹配成功修改，否则新增；
     *         //     遍历9.5.2数据：与传入数据匹配，匹配不成功删除
     * 作者: fushudong
     * 时间: 2023/8/24
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void dataSync(List<QqchFirstArticleEngineeringList> firstArticleEngineeringList, BigDecimal version) {
        //界面传入数据为空，删除所有9.5.2数据
        if (CollectionUtils.isEmpty(firstArticleEngineeringList)) {
            QqchFirstArticleEngineeringControl param = new QqchFirstArticleEngineeringControl();
            param.setVersion(version);
            firstArticleEngineService.deleteQqchFirstArticleEngineeringControl(param);
            return;
        }
        // 查询9.5.2数据
        QqchFirstArticleEngineeringControl control = new QqchFirstArticleEngineeringControl();
        control.setVersion(version);
        QqchFirstArticleEngineeringControlVo firstControlVo = firstArticleEngineService.getQqchFirstArticleEngineeringControlList(control);
        List<QqchFirstArticleEngineeringControl> firstControl = firstControlVo.getQqchFirstArticleEngineeringControlList();

        if (CollectionUtils.isEmpty(firstControl)) {
            //界面传入不为空，9.5.2数据为空，新增数据
            List<QqchFirstArticleEngineeringControl> objects = new ArrayList<>();
            firstArticleEngineeringList.forEach(param -> {
                QqchFirstArticleEngineeringControl bean = new QqchFirstArticleEngineeringControl();
                bean.setListId(param.getId());
                bean.setName(param.getName());
                bean.setWbsName(param.getWbsName());
                bean.setPlanStartTime(param.getPlanStartTime());
                bean.setWorkGroup(param.getWorkGroup());
                bean.setFirstPersonId(param.getPersonId());
                bean.setFirstPersonName(param.getPersonName());
                objects.add(bean);
            });
            firstArticleEngineService.insertList(objects, version);
        } else {
            //界面传入数据和9.5.2数据都不为空

            //遍历界面传入数据：与9.5.2数据匹配，匹配成功修改，否则新增
            Map<Long, Long> OriCollect = firstControl.stream().collect(Collectors.toMap(QqchFirstArticleEngineeringControl::getListId, QqchFirstArticleEngineeringControl::getId));
            List<Long> ids = new ArrayList<>();
            List<QqchFirstArticleEngineeringControl> objects = new ArrayList<>();
            for (QqchFirstArticleEngineeringList param : firstArticleEngineeringList) {
                Long id = param.getId();
                if (OriCollect.containsKey(id)) {
                    //执行修改
                    QqchFirstArticleEngineeringControl bean = new QqchFirstArticleEngineeringControl();
                    bean.setId(OriCollect.get(id));
                    bean.setListId(id);
                    bean.setName(param.getName());
                    bean.setWbsName(param.getWbsName());
                    bean.setPlanStartTime(param.getPlanStartTime());
                    bean.setWorkGroup(param.getWorkGroup());
                    bean.setFirstPersonId(param.getPersonId());
                    bean.setFirstPersonName(param.getPersonName());
                    objects.add(bean);
                } else {
                    //执行删除
                    ids.add(OriCollect.get(id));
                }
            }
            if (CollectionUtils.isNotEmpty(objects)) {
                firstArticleEngineService.updateQqchFirstArticleEngineeringControlList(objects);
            }
            if (CollectionUtils.isNotEmpty(ids)) {
                firstArticleEngineService.deleteQqchFirstArticleEngineeringControlByPks(ids);
            }

            //遍历9.5.2数据：与传入数据匹配，匹配不成功删除
            Set<Long> newCollect = firstArticleEngineeringList.stream().map(QqchFirstArticleEngineeringList::getId).collect(Collectors.toSet());
            List<Long> oriIds = new ArrayList<>();
            for (QqchFirstArticleEngineeringControl bean : firstControl) {
                Long listId = bean.getListId();
                if (newCollect.contains(listId)) {
                    continue;
                }
                oriIds.add(bean.getId());
                if (CollectionUtils.isNotEmpty(oriIds)) {
                    firstArticleEngineService.deleteQqchFirstArticleEngineeringControlByPks(ids);
                }
            }
        }
    }

    @Transactional
    public void insertQqchFirstArticleEngineeringListList(List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList,BigDecimal version) {
        //删除旧数据
        QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList = new QqchFirstArticleEngineeringList();
        qqchFirstArticleEngineeringList.setVersion(version);
        qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);

        if (CollectionUtils.isEmpty(qqchFirstArticleEngineeringListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchFirstArticleEngineeringList firstArticleEngineeringList : qqchFirstArticleEngineeringListList) {
            firstArticleEngineeringList.setId(IdWorker.createId());
            firstArticleEngineeringList.setValid(valid);
            firstArticleEngineeringList.setVersion(version);
            firstArticleEngineeringList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            firstArticleEngineeringList.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            firstArticleEngineeringList.setCreateTime(DateUtils.getNowDate());
        }
        qqchFirstArticleEngineeringListMapper.insertQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
    }
}
