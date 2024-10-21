package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo.QqchWeightEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service.IQqchWeightEngineeringControlService;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListHistory;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.mapper.QqchWeightEngineeringListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

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
     * 获取重难点工程清单涉及的wbs及其下级所有Id
     * @return
     */
    public Set<Long> getCurrentAndLowerLevelWbsIds() {
        //获取最新生效版本的重难点工程清单
        List<QqchWeightEngineeringList> engineeringListList = this.getEngineeringListByVersion(null);
        Set<Long> wbsIds = new HashSet<>();
        for (QqchWeightEngineeringList weightEngineeringList : engineeringListList) {
            String wbsId = weightEngineeringList.getWbsId();
            if(wbsId != null){
                String[] split = wbsId.split(",");
                wbsIds.add(Long.valueOf(wbsId));
                for (String arr : split){
                    Long[] childWbsIds = WbsRedisUtils.getChildWbsId(arr);
                    if(childWbsIds != null) {
                        wbsIds.addAll(Arrays.asList(childWbsIds));
                    }
                }

            }
        }
        return wbsIds;
    }

    /**
     * 获取最新生效版本重难点工程清单中选择的wbs以及其所有父级结构的集合
     * @return
     */
    @Override
    public List<XmslWbs> keyDifficultProjectInventoryWbsList() {
        //获取最新生效版本的重难点工程清单
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = this.getEngineeringListByVersion(null);

        //获取wbsId集合
        Set<String> wbsIds = this.getWbsIds(qqchWeightEngineeringListList);
        return WbsRedisUtils.getWbs(wbsIds);
    }

    /**
     * 获取wbsId集合
     * @param engineeringListList
     * @return
     */
    public Set<String> getWbsIds(List<QqchWeightEngineeringList> engineeringListList){
        //获取wbsId集合
        Set<String> wbsIds = new HashSet<>();
        for (QqchWeightEngineeringList weightEngineeringList : engineeringListList) {
            String wbsId = weightEngineeringList.getWbsId();
            String wbsAncestors = weightEngineeringList.getWbsAncestors();
            if(StringUtils.isNotBlank(wbsAncestors)){
                String[] wbsArrays = wbsAncestors.split(",");
                wbsIds.addAll(Arrays.asList(wbsArrays));
            }
            if(wbsId != null){
                String[] split = wbsId.split(",");
                for (String arr : split){
                    Long[] childWbsIds = WbsRedisUtils.getChildWbsId(arr);
                    if(childWbsIds != null) {
                        for (Long childWbsId : childWbsIds) {
                            wbsIds.add(String.valueOf(childWbsId));
                        }
                    }
                }
            }
        }
        return wbsIds;
    }

    /**
     * 获取最新生效版本重难点工程清单中选择的wbs编码集合
     * @return
     */
    public Set<String> keyDifficultProjectInventoryWbsCodeSet() {
        //获取最新生效版本的重难点工程清单
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = this.getEngineeringListByVersion(null);

        Set<String> wbsCodeSet = new HashSet<>();
        for (QqchWeightEngineeringList qqchWeightEngineeringList : qqchWeightEngineeringListList) {
            String wbsCode = qqchWeightEngineeringList.getWbsCode();
            if(StringUtils.isNotBlank(wbsCode)){
                wbsCodeSet.add(wbsCode);
            }
        }
        return wbsCodeSet;
    }

    /**
     * 根据版本获取重难点工程清单
     * @param version
     * @return
     */
    public List<QqchWeightEngineeringList> getEngineeringListByVersion(BigDecimal version) {
        version = VersionUtil.getVersion("qqch_weight_engineering_list", version);
        QqchWeightEngineeringList qqchWeightEngineeringList = new QqchWeightEngineeringList();
        qqchWeightEngineeringList.setVersion(version);
        return qqchWeightEngineeringListMapper.getQqchWeightEngineeringListList(qqchWeightEngineeringList);
    }

    /**
     * 列表接口
     */
    public QqchWeightEngineeringListVo getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        QqchWeightEngineeringListVo vo = new QqchWeightEngineeringListVo();

        BigDecimal sourceVersion = qqchWeightEngineeringList.getVersion();
        BigDecimal version = VersionUtil.getVersion("qqch_weight_engineering_list", sourceVersion);
        version = ObjectUtils.nvlBigDecimal(sourceVersion,version);
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = this.getEngineeringListByVersion(version);

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
        QqchWeightEngineeringControlVo firstControlVo = weightEngineControlService.getQqchWeightEngineeringControlList(control);
        List<QqchWeightEngineeringControl> firstControl = firstControlVo.getQqchWeightEngineeringControlList();

        if (CollectionUtils.isEmpty(firstControl)) {
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
            weightEngineControlService.insertList(objects, version, "save");
        } else {
            //界面传入数据和9.4.2数据都不为空

            //遍历界面传入数据：与9.4.2数据匹配，匹配成功修改，否则新增
            Map<Long, Long> OriCollect = firstControl.stream().collect(Collectors.toMap(QqchWeightEngineeringControl::getListId, QqchWeightEngineeringControl::getId));
            List<QqchWeightEngineeringControl> objects = new ArrayList<>();
            List<QqchWeightEngineeringControl> addObjects = new ArrayList<>();
            for (QqchWeightEngineeringList param : weightEngineeringList) {
                Long id = param.getId();
                QqchWeightEngineeringControl bean = new QqchWeightEngineeringControl();
                bean.setListId(id);
                bean.setName(param.getName());
                bean.setWbsName(param.getWbsName());
                bean.setPlannStartDate(param.getPlannStartDate());
                bean.setWorkGroup(param.getWorkGroup());
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
                weightEngineControlService.insertList(objects, version, "update");
            }
            if (CollectionUtils.isNotEmpty(addObjects)) {
                weightEngineControlService.insertList(addObjects, version, "save");
            }

            //遍历9.4.2数据：与传入数据匹配，匹配不成功删除
            Set<Long> newCollect = weightEngineeringList.stream().map(QqchWeightEngineeringList::getId).collect(Collectors.toSet());
            List<Long> oriIds = new ArrayList<>();
            for (QqchWeightEngineeringControl bean : firstControl) {
                Long listId = bean.getListId();
                if (newCollect.contains(listId)) {
                    continue;
                }
                oriIds.add(bean.getId());
                if (CollectionUtils.isNotEmpty(oriIds)) {
                    weightEngineControlService.deleteQqchWeightEngineeringControlByPks(oriIds);
                }
            }
        }
    }

    @Override
    public Map<String, List<QqchWeightEngineeringListHistory>> querySameProject(QqchWeightEngineeringListHistory param) {
        Map<String, List<QqchWeightEngineeringListHistory>> result = new HashMap<>();
        // 获取当前租户
        String currentTenantKey = SecurityUtils.getTenantKey();
        // 获取当前租户的项目
        ProjectBasicInfo currentProjectInfo = xmslProjectBasicInfoService.projectInfo();
        //根据 业务领域及产品 字段判断是否为同类项目
        String currentBusiness = currentProjectInfo.getBusinessAreasAndProducts();
        if (ObjectUtil.isEmpty(currentProjectInfo) || com.hhwy.common.core.utils.StringUtils.isBlank(currentBusiness)) {
            return null;
        }
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        // 获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try {
            for (SysTenant tenant : tenantList) {
                if (currentTenantKey.equals(tenant.getTenantKey())) {
                    continue;
                }
                // 切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取该租户项目信息
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                //项目为空或者不是同类项目则跳过
                if (ObjectUtil.isEmpty(projectInfo)
                        || StrUtil.hasBlank(projectInfo.getProjectName(), projectInfo.getBusinessAreasAndProducts())
//                        || !StrUtil.equalsIgnoreCase(currentBusiness, projectInfo.getBusinessAreasAndProducts())
                ) {
                    continue;
                }
                // 获取该租户项目已选择经营模式
                List<QqchWeightEngineeringListHistory> modelList = qqchWeightEngineeringListMapper.getHistoryManageModelList(param);
                if (CollectionUtil.isEmpty(modelList)) continue;
                if (StrUtil.isBlank(param.getProjectName())) {
                    modelList = modelList.stream().filter(p -> p.getProjectName().contains(param.getProjectName())).collect(Collectors.toList());
                }
                result.put(projectInfo.getProjectName(), modelList);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return result;
    }
}
