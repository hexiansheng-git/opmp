package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprintParam;
import com.hhwy.sp.utils.TreeNodeUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.mapper.SgjsTechnicalFileBlueprintMapper;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service.ISgjsTechnicalFileBlueprintService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.Assert;

/***
 * 功能描述: 技术文件管理 - 施工环节图纸管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsTechnicalFileBlueprintServiceImpl implements ISgjsTechnicalFileBlueprintService {

    @Autowired
    private SgjsTechnicalFileBlueprintMapper sgjsTechnicalFileBlueprintMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;
    private static ProjectDto projectInfo;

    //向总部推送数据用
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 2, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));

    private ProjectDto getProjectDto(){
        if (projectInfo != null) return projectInfo;
        return pmServiceApi.getProjectDto();
    }

    public SgjsTechnicalFileBlueprint getSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        return sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    public List<SgjsTechnicalFileBlueprint> getTreeList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint) {
        List<SgjsTechnicalFileBlueprint> list = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprint);
        List<SgjsTechnicalFileBlueprint> TreeList = TreeUtil.build(list, null);
        if (CollUtil.isNotEmpty(list) && CollUtil.isEmpty(TreeList)) {
            List<SgjsTechnicalFileBlueprint> allList = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(new SgjsTechnicalFileBlueprintParam());
            List<SgjsTechnicalFileBlueprint> ancestral = TreeNodeUtil.getAncestral(allList, list);
            TreeList = TreeUtil.build(ancestral, null);
        }
        return TreeList;
    }
    public List<SgjsTechnicalFileBlueprint> getList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint) {
        return sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int insertSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        sgjsTechnicalFileBlueprint.setId(IdWorker.createId());
        sgjsTechnicalFileBlueprint.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalFileBlueprint.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalFileBlueprintMapper.insertSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public void insertSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList) {
        if (CollUtil.isEmpty(sgjsTechnicalFileBlueprintList)) {
            sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprint(new SgjsTechnicalFileBlueprint());
            doSendGm();
            return;
        }
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprints = TreeUtil.treeToListWithoutNewId(sgjsTechnicalFileBlueprintList);
        //校验数据必填, 只校验叶子层级
        List<SgjsTechnicalFileBlueprint> leafList = sgjsTechnicalFileBlueprints.stream().filter(p -> p.getLeaf().equals("1")).collect(Collectors.toList());
        JyDetailsUtil.jyDetails(leafList, ValidationGroups.Save.class);
        List<SgjsTechnicalFileBlueprint> save = new ArrayList<>();
        List<SgjsTechnicalFileBlueprint> update = new ArrayList<>();
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        for (SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint : sgjsTechnicalFileBlueprints) {
            String isAdd = sgjsTechnicalFileBlueprint.getIsAdd();
            if (StrUtil.isBlank(isAdd)) {
                sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
                sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
                update.add(sgjsTechnicalFileBlueprint);
                continue;
            }
            sgjsTechnicalFileBlueprint.setRegionId(projectDto.getRegionId());
            sgjsTechnicalFileBlueprint.setRegionName(projectDto.getRegionName());
            sgjsTechnicalFileBlueprint.setProjectId(projectDto.getProjectId());
            sgjsTechnicalFileBlueprint.setPtVar5(projectDto.getProjectCode());
            sgjsTechnicalFileBlueprint.setProjectName(projectDto.getProjectName());
            sgjsTechnicalFileBlueprint.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalFileBlueprint.setCreateTime(DateUtils.getNowDate());
            save.add(sgjsTechnicalFileBlueprint);
        }
        if (CollUtil.isNotEmpty(save)){
            //图纸编码唯一性校验
            List<SgjsTechnicalFileBlueprint> alreadyData = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(new SgjsTechnicalFileBlueprintParam());
            if (CollUtil.isNotEmpty(alreadyData)) {
                alreadyData.addAll(save);
                //代码唯一性校验
                Map<String, List<SgjsTechnicalFileBlueprint>> map = alreadyData.stream().collect(Collectors.groupingBy(SgjsTechnicalFileBlueprint::getBlueprintNum));
                List<String> repeatCode = new ArrayList<>();
                map.forEach((k, v) -> {
                    if (v.size() > 1) {
                        repeatCode.add(String.valueOf(k));
                    }
                });
                Assert.isTrue(CollUtil.isEmpty(repeatCode), "图纸编号不能重复, 请检查：" + String.join(",", repeatCode));
            }
            sgjsTechnicalFileBlueprintMapper.insertSgjsTechnicalFileBlueprintList(save);
        }
        if (CollUtil.isNotEmpty(update)){
            sgjsTechnicalFileBlueprintMapper.updateSgjsTechnicalFileBlueprintList(update);
        }
//        executorService.execute(this::doSendGm);
        doSendGm();
    }

    @Transactional
    public int updateSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalFileBlueprintMapper.updateSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int updateSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList) {
        for (SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint : sgjsTechnicalFileBlueprintList) {
            sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalFileBlueprintMapper.updateSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintList);
    }

    @Transactional
    public int deleteSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        return sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int deleteSgjsTechnicalFileBlueprintByPks(List<Long> sgjsTechnicalFileBlueprintPkList) {
        return sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprintByPks(sgjsTechnicalFileBlueprintPkList);
    }

    public int deleteWithChildren(List<Long> sgjsTechnicalFileBlueprintPkList) {
        return sgjsTechnicalFileBlueprintMapper.deleteWithChildren(sgjsTechnicalFileBlueprintPkList);
    }

    //数据推送总部版
    public void doSendGm(){
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(new SgjsTechnicalFileBlueprintParam());
        if (CollUtil.isEmpty(sgjsTechnicalFileBlueprintList)) {
            //集合为空，推送一个项目编号
            String projectCode = getProjectDto().getProjectCode();
            Long projectId = getProjectDto().getProjectId();
            SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint = new SgjsTechnicalFileBlueprint();
            sgjsTechnicalFileBlueprint.setProjectId(projectId);
            sgjsTechnicalFileBlueprint.setPtVar5(projectCode);
            sgjsTechnicalFileBlueprintList.add(sgjsTechnicalFileBlueprint);
        }
        rocketMQTemplate.convertAndSend("sgjs_technical_file_blueprint:tenantSuccess", sgjsTechnicalFileBlueprintList);
    }

    @Override
    public AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList) {
        List<SgjsTechnicalFileBlueprint> importData = new ArrayList<>();
        for (Map<Integer, String> map : dataList) {
            String innerCode = map.get(0);
            String blueprintNum = map.get(1);
            String blueprintName = map.get(2);
            String version = map.get(3);
            Integer blueprintCount = map.get(4) == null ? null : Integer.valueOf(map.get(3));
            String startDatePlan = map.get(5);
            String senderName = map.get(6);
            String sendDate = map.get(7);
            String receiverName = map.get(8);
            String changeOr = map.get(9);
            Assert.isTrue(StrUtil.isNotBlank(innerCode), "层级编码不能为空");
            Assert.isTrue(StrUtil.isNotBlank(blueprintNum), "图纸编码不能为空");
//            Assert.isTrue(StrUtil.isNotBlank(blueprintName), "图纸名称不能为空");
//            Assert.isTrue(StrUtil.isNotBlank(senderName), "图纸发放人不能为空");
//            Assert.isTrue(StrUtil.isNotBlank(receiverName),"图纸接收人不能为空");
            SgjsTechnicalFileBlueprint technicalFileBlueprint = new SgjsTechnicalFileBlueprint();
            technicalFileBlueprint.setBlueprintNum(StrUtil.isBlank(blueprintNum)?"":blueprintNum);
            technicalFileBlueprint.setBlueprintName(blueprintName);
            technicalFileBlueprint.setVersion(StrUtil.isBlank(version)?"":version);
            technicalFileBlueprint.setBlueprintCount(blueprintCount);
            technicalFileBlueprint.setStartDatePlan(startDatePlan == null ? null : DateUtil.parseDate(startDatePlan));
            technicalFileBlueprint.setSenderName(StrUtil.isBlank(senderName)?"":senderName);
            technicalFileBlueprint.setSendDate(sendDate == null ? null : DateUtil.parseDate(sendDate));
            technicalFileBlueprint.setReceiverName(receiverName);
            technicalFileBlueprint.setChangeOr(StrUtil.isBlank(changeOr)? "" : changeOr.equals("是")?"1":"0");
            technicalFileBlueprint.setIsAdd("1");
            technicalFileBlueprint.setBlueprintValid("1");
            technicalFileBlueprint.setInnerCode(innerCode);
            importData.add(technicalFileBlueprint);
        }
        //图纸编码唯一性校验
        Map<String, List<SgjsTechnicalFileBlueprint>> mapBlueNum = importData.stream().collect(Collectors.groupingBy(SgjsTechnicalFileBlueprint::getBlueprintNum));
        List<String> repeatNum = new ArrayList<>();
        mapBlueNum.forEach((k, v) -> {
            if (v.size() > 1) {
                repeatNum.add(String.valueOf(k));
            }
        });
        Assert.isTrue(CollUtil.isEmpty(repeatNum), "图纸编号不能重复, 请检查：" + String.join(",", repeatNum));
        //层级编码唯一性校验
        Map<String, List<SgjsTechnicalFileBlueprint>> mapInnerCode = importData.stream().collect(Collectors.groupingBy(SgjsTechnicalFileBlueprint::getInnerCode));
        List<String> repeatCode = new ArrayList<>();
        mapInnerCode.forEach((k, v) -> {
            if (v.size() > 1) {
                repeatCode.add(String.valueOf(k));
            }
        });
        Assert.isTrue(CollUtil.isEmpty(repeatCode), "层级编号不能重复, 请检查：" + String.join(",", repeatCode));
        //
        Map<String, SgjsTechnicalFileBlueprint> mapByInnerCode = importData.stream()
                .filter(p -> StrUtil.isNotBlank(p.getInnerCode()))
                .collect(Collectors.toMap(SgjsTechnicalFileBlueprint::getInnerCode, value -> value, (v1, v2) -> v1));
        for (SgjsTechnicalFileBlueprint fileBlue : importData) {
            fileBlue.setId(IdWorker.createId());
            String innerCode = fileBlue.getInnerCode();
            if (StrUtil.isBlank(innerCode)) continue;
            if (!innerCode.contains("-")) {
                //第一层级
                fileBlue.setSort(Integer.valueOf(innerCode));
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") + 1);
            //获取当前数据的父层级
            SgjsTechnicalFileBlueprint parent = mapByInnerCode.get(parentCode);
            cn.hutool.core.lang.Assert.notNull(parent, "层级码：{} 未找到父层级：{}，请确认是否存在", innerCode, parentCode);
            //获取父层级的children，将当前记录add进去
            List children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            fileBlue.setSort(Integer.valueOf(curentCode));
            fileBlue.setPid(parent.getId());
            children.add(fileBlue);
        }
        List<SgjsTechnicalFileBlueprint> fileBlues = new ArrayList<>(mapByInnerCode.values());
        return AjaxResult.success(TreeUtil.build(fileBlues, null));
    }
}
