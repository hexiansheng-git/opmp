package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTask;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTaskMain;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.GatherPlanTaskQueryVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.QqchGatherPlanTaskMainVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.QqchGatherPlanTaskVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.mapper.QqchGatherPlanTaskMainMapper;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.mapper.QqchGatherPlanTaskMapper;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.IQqchGatherPlanTaskMainService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-08-23 11:10:38
 * @remark
 */
@Service
public class QqchGatherPlanTaskMainServiceImpl implements IQqchGatherPlanTaskMainService {

    @Autowired
    private QqchGatherPlanTaskMainMapper qqchGatherPlanTaskMainMapper;

    @Autowired
    private QqchGatherPlanTaskMapper qqchGatherPlanTaskMapper;

    @Autowired
    private IXmslWbsService xmslWbsService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;

    private static final String TN = "qqch_gather_plan_task_main";


    public QqchGatherPlanTaskMain getQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain) {
        return qqchGatherPlanTaskMainMapper.getQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    public List<QqchGatherPlanTaskMain> getQqchGatherPlanTaskMainList(QqchGatherPlanTaskMain qqchGatherPlanTaskMain) {
        return qqchGatherPlanTaskMainMapper.getQqchGatherPlanTaskMainList(qqchGatherPlanTaskMain);
    }

    @Transactional
    public int insertQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain) {
        qqchGatherPlanTaskMain.setId(IdWorker.createId());
        qqchGatherPlanTaskMain.setCreateUser(SecurityUtils.getUserName());
        qqchGatherPlanTaskMain.setCreateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMainMapper.insertQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    @Transactional
    public int insertQqchGatherPlanTaskMainList(List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList) {
        for (QqchGatherPlanTaskMain qqchGatherPlanTaskMain : qqchGatherPlanTaskMainList) {
            qqchGatherPlanTaskMain.setId(IdWorker.createId());
            qqchGatherPlanTaskMain.setCreateUser(SecurityUtils.getUserName());
            qqchGatherPlanTaskMain.setCreateTime(DateUtils.getNowDate());
        }
        return qqchGatherPlanTaskMainMapper.insertQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainList);
    }

    @Transactional
    public int updateQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain) {
        qqchGatherPlanTaskMain.setUpdateUser(SecurityUtils.getUserName());
        qqchGatherPlanTaskMain.setUpdateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMainMapper.updateQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    @Transactional
    public int updateQqchGatherPlanTaskMainList(List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList) {
        for (QqchGatherPlanTaskMain qqchGatherPlanTaskMain : qqchGatherPlanTaskMainList) {
            qqchGatherPlanTaskMain.setUpdateUser(SecurityUtils.getUserName());
            qqchGatherPlanTaskMain.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGatherPlanTaskMainMapper.updateQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainList);
    }

    @Transactional
    public int deleteQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain) {
        qqchGatherPlanTaskMain.setUpdateUser(SecurityUtils.getUserName());
        qqchGatherPlanTaskMain.setUpdateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMainMapper.deleteQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    @Transactional
    public int deleteQqchGatherPlanTaskMainByPks(List<Long> qqchGatherPlanTaskMainPkList) {
        return qqchGatherPlanTaskMainMapper.deleteQqchGatherPlanTaskMainByPks(qqchGatherPlanTaskMainPkList);
    }

    /**
     * 获取成本数据采集计划任务表Vo
     *
     * @param queryVo @return
     */
    @Override
    public QqchGatherPlanTaskMainVo getQqchGatherPlanTaskMainVo(GatherPlanTaskQueryVo queryVo) {
        QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo = new QqchGatherPlanTaskMainVo();

        BigDecimal version = queryVo.getVersion();
        this.checkExistsData(version);
        Long wbsMainId = queryVo.getWbsMainId();
        String parentId = queryVo.getParentId();

        if(wbsMainId == null && version != null){
            //根据版本查询成本数据采集计划任务主表信息
            QqchGatherPlanTaskMain gatherPlanTaskMain = this.getQqchGatherPlanTaskMainByVersion(version);
            if(gatherPlanTaskMain != null){
                wbsMainId = gatherPlanTaskMain.getWbsMainId();
            }
        }
        XmslWbs xmslWbs = new XmslWbs();
        xmslWbs.setMainId(wbsMainId);
        xmslWbs.setParentId(parentId);
        Map map = xmslWbsService.listData(xmslWbs);
        List<XmslWbs> wbsList = (List<XmslWbs>) map.get("list");
        Object mainIdObj = map.get("mainId");
        if(ObjectUtils.isNotBlank(mainIdObj)){
            wbsMainId = (Long) mainIdObj;
        }
        Object versionObj = map.get("version");
        Integer wbsVersion = null;
        if(ObjectUtils.isNotEmpty(versionObj)){
            wbsVersion= (Integer) versionObj;
        }

        version = VersionUtil.getVersion(TN, version);
        QqchGatherPlanTask qqchGatherPlanTask = new QqchGatherPlanTask();
        qqchGatherPlanTask.setVersion(version);
        List<QqchGatherPlanTask> qqchGatherPlanTaskList = qqchGatherPlanTaskMapper.getQqchGatherPlanTaskList(qqchGatherPlanTask);

        //获取p6计划数据
        String wbsCodes = wbsList.stream().map(XmslWbs::getCode).collect(Collectors.joining(","));
        List<QqchMainPlanItem> mainPlanItemList = qqchMainPlanItemService.getListByItemCodes(wbsCodes);

        List<QqchGatherPlanTaskVo> qqchGatherPlanTaskVoList = new ArrayList<>();

        for (XmslWbs wbs : wbsList) {
            QqchGatherPlanTaskVo qqchGatherPlanTaskVo = new QqchGatherPlanTaskVo();
            qqchGatherPlanTaskVo.setId(Long.valueOf(wbs.getId()));
            qqchGatherPlanTaskVo.setWbsId(Long.valueOf(wbs.getId()));
            qqchGatherPlanTaskVo.setWbsCode(wbs.getCode());
            qqchGatherPlanTaskVo.setHaveChildren(wbs.getHaveChildren());
            qqchGatherPlanTaskVo.setPartCode(wbs.getPartCode());
            qqchGatherPlanTaskVo.setPartName(wbs.getName());
            qqchGatherPlanTaskVo.setType(wbs.getNodeType());

            for (QqchGatherPlanTask gatherPlanTask : qqchGatherPlanTaskList) {
                if(qqchGatherPlanTaskVo.getWbsCode().equals(gatherPlanTask.getWbsCode())){
                    qqchGatherPlanTaskVo.setGatherId(gatherPlanTask.getGatherId());
                    qqchGatherPlanTaskVo.setGatherer(gatherPlanTask.getGatherer());
                    qqchGatherPlanTaskVo.setVerifier(gatherPlanTask.getVerifier());
                    qqchGatherPlanTaskVo.setRemark(gatherPlanTask.getRemark());
                    break;
                }
            }

            for (QqchMainPlanItem qqchMainPlanItem : mainPlanItemList) {
                if(qqchGatherPlanTaskVo.getWbsCode().equals(qqchMainPlanItem.getItemCode())){
                    qqchGatherPlanTaskVo.setStartWorkTime(qqchMainPlanItem.getStartDate());
                    qqchGatherPlanTaskVo.setEndWorkTime(qqchMainPlanItem.getFinishDate());
                }
            }

            qqchGatherPlanTaskVoList.add(qqchGatherPlanTaskVo);
        }

        qqchGatherPlanTaskMainVo.setWbsMainId(wbsMainId);
        qqchGatherPlanTaskMainVo.setWbsVersion(wbsVersion);
        qqchGatherPlanTaskMainVo.setVersion(version);
        qqchGatherPlanTaskMainVo.setStageIdentity(qqchReviewService.getStage());
        qqchGatherPlanTaskMainVo.setQqchGatherPlanTaskVoList(qqchGatherPlanTaskVoList);
        return qqchGatherPlanTaskMainVo;
    }

    public void checkExistsData(BigDecimal version){
        boolean exists = CommonServiceUtil.checkExistsByVersion(TN, version);
        if(exists){
            return;
        }
        BigDecimal oldVersion = VersionUtil.getVersion(TN, version);
        if(oldVersion.equals(version)){
            return;
        }
        //查询主子表数据
        QqchGatherPlanTaskMain gatherPlanTaskMain = this.getQqchGatherPlanTaskMainByVersion(oldVersion);
        if(gatherPlanTaskMain == null){
            return;
        }

        //插入主表数据
        gatherPlanTaskMain.setId(IdWorker.createId());
        gatherPlanTaskMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        gatherPlanTaskMain.setCreateUserName(SecurityUtils.getUserName());
        gatherPlanTaskMain.setCreateTime(DateUtils.getNowDate());
        qqchGatherPlanTaskMainMapper.insertQqchGatherPlanTaskMain(gatherPlanTaskMain);

        QqchGatherPlanTask qqchGatherPlanTask = new QqchGatherPlanTask();
        qqchGatherPlanTask.setVersion(version);
        List<QqchGatherPlanTask> qqchGatherPlanTaskList = qqchGatherPlanTaskMapper.getQqchGatherPlanTaskList(qqchGatherPlanTask);
        if(CollectionUtils.isEmpty(qqchGatherPlanTaskList)){
            return;
        }
        for (QqchGatherPlanTask gatherPlanTask : qqchGatherPlanTaskList) {
            gatherPlanTask.setGatherId(IdWorker.createId());
            qqchGatherPlanTask.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchGatherPlanTask.setCreateUserName(SecurityUtils.getUserName());
            qqchGatherPlanTask.setCreateTime(DateUtils.getNowDate());
        }
        qqchGatherPlanTaskMapper.insertQqchGatherPlanTaskList(qqchGatherPlanTaskList);
    }

    /**
     * 根据版本查询成本数据采集计划任务主表数据
     * @param version
     * @return
     */
    public QqchGatherPlanTaskMain getQqchGatherPlanTaskMainByVersion(BigDecimal version){
        QqchGatherPlanTaskMain qqchGatherPlanTaskMain = new QqchGatherPlanTaskMain();
        qqchGatherPlanTaskMain.setVersion(version);
        return qqchGatherPlanTaskMainMapper.getQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    /**
     * 保存/确认/提交
     * @param qqchGatherPlanTaskMainVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo) {
        String buttonMark = qqchGatherPlanTaskMainVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        //处理数据
        this.disposeData(qqchGatherPlanTaskMainVo);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchGatherPlanTaskMainVo.getMenuId();
            String stageIdentity = qqchGatherPlanTaskMainVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param qqchGatherPlanTaskMainVo
     */
    @Transactional
    public void disposeData(QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo) {
        //处理主表数据
        this.disposePrimaryTable(qqchGatherPlanTaskMainVo.getWbsMainId(),qqchGatherPlanTaskMainVo.getWbsVersion(),qqchGatherPlanTaskMainVo.getVersion());

        //处理子表数据
        this.disposeSublistData(qqchGatherPlanTaskMainVo.getQqchGatherPlanTaskVoList(),qqchGatherPlanTaskMainVo.getVersion());
    }

    /**
     * 处理主表
     * @param wbsMainId
     * @param wbsVersion
     * @param version
     */
    @Transactional
    public void disposePrimaryTable(Long wbsMainId, Integer wbsVersion, BigDecimal version) {
        //删除主表旧数据
        QqchGatherPlanTaskMain qqchGatherPlanTaskMain = new QqchGatherPlanTaskMain();
        qqchGatherPlanTaskMain.setVersion(version);
        qqchGatherPlanTaskMainMapper.deleteQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);

        //插入新数据
        if(version.compareTo(BigDecimal.ONE) == 0){
            qqchGatherPlanTaskMain.setValid(Valid.YES);
        }
        qqchGatherPlanTaskMain.setWbsMainId(wbsMainId);
        qqchGatherPlanTaskMain.setWbsVersion(wbsVersion);
        qqchGatherPlanTaskMain.setId(IdWorker.createId());
        qqchGatherPlanTaskMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchGatherPlanTaskMain.setCreateUserName(SecurityUtils.getUserName());
        qqchGatherPlanTaskMain.setCreateTime(DateUtils.getNowDate());
        qqchGatherPlanTaskMainMapper.insertQqchGatherPlanTaskMain(qqchGatherPlanTaskMain);
    }

    /**
     * 处理子表数据
     * @param qqchGatherPlanTaskVoList
     * @param version
     */
    @Transactional
    public void disposeSublistData(List<QqchGatherPlanTaskVo> qqchGatherPlanTaskVoList, BigDecimal version) {
        List<QqchGatherPlanTask> insertList = new ArrayList<>();
        List<QqchGatherPlanTask> updateList = new ArrayList<>();

        for (QqchGatherPlanTaskVo qqchGatherPlanTaskVo : qqchGatherPlanTaskVoList) {
            QqchGatherPlanTask qqchGatherPlanTask = new QqchGatherPlanTask();
            qqchGatherPlanTask.setWbsId(qqchGatherPlanTaskVo.getWbsId());
            qqchGatherPlanTask.setWbsCode(qqchGatherPlanTaskVo.getWbsCode());
            qqchGatherPlanTask.setGatherer(qqchGatherPlanTaskVo.getGatherer());
            qqchGatherPlanTask.setGathererId(qqchGatherPlanTaskVo.getGathererId());
            qqchGatherPlanTask.setVerifier(qqchGatherPlanTaskVo.getVerifier());
            qqchGatherPlanTask.setVerifierId(qqchGatherPlanTaskVo.getVerifierId());
            qqchGatherPlanTask.setRemark(qqchGatherPlanTaskVo.getRemark());
            qqchGatherPlanTask.setVersion(version);

            Long gatherId = qqchGatherPlanTaskVo.getGatherId();
            if(gatherId == null){
                qqchGatherPlanTask.setGatherId(IdWorker.createId());
                qqchGatherPlanTask.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchGatherPlanTask.setCreateUserName(SecurityUtils.getUserName());
                qqchGatherPlanTask.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchGatherPlanTask);
            }else {
                qqchGatherPlanTask.setGatherId(gatherId);
                qqchGatherPlanTask.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchGatherPlanTask.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchGatherPlanTask);
            }
        }

        if(insertList.size() > 0){
            qqchGatherPlanTaskMapper.insertQqchGatherPlanTaskList(insertList);
        }
        if(updateList.size() > 0){
            qqchGatherPlanTaskMapper.updateQqchGatherPlanTaskList(updateList);
        }
    }
}
