package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.mapper.SgjsBuildSchemeMapper;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service.ISgjsBuildSchemeExpertSuggestService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.sp.common.FileUploadUtil;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.FlowInfoSearchUtilNonReqest;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:26
 * @remark
 */
@Service
@Slf4j
public class SgjsBuildSchemeServiceImpl implements ISgjsBuildSchemeService {

    @Autowired
    private SgjsBuildSchemeMapper sgjsBuildSchemeMapper;
    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;
    @Autowired
    private ISgjsBuildSchemeExpertSuggestService schemeExpertSuggestService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    private static final Map<String, String> businessAreas = new HashMap<>();

    static {
        businessAreas.put("D02P08", "大跨度场馆");
        businessAreas.put("D02P07", "超高层建筑");
        businessAreas.put("D02P06", "医疗建筑");
        businessAreas.put("D02P05", "酒店建筑");
        businessAreas.put("D02P04", "金融建筑");
        businessAreas.put("D02P03", "大型综合体建筑");
        businessAreas.put("D02P02", "一般公共建筑");
        businessAreas.put("D02P01", "居住建筑");
    }

    //详情
    public SgjsBuildScheme detail(SgjsBuildScheme sgjsBuildScheme) {
        SgjsBuildScheme result = null;
        //1按照界面传入id获取数据
        if (sgjsBuildScheme != null && sgjsBuildScheme.getId() != null) {
            SgjsBuildScheme param = new SgjsBuildScheme();
            param.setId(sgjsBuildScheme.getId());
            result = sgjsBuildSchemeMapper.getSgjsBuildScheme(param);
        }
        //2获取有效版本数据
        if (result == null) result = sgjsBuildSchemeMapper.getValidVersionData();
        //3获取最高版本数据
        if (result == null) result = sgjsBuildSchemeMapper.getMaxVersionData();
        /*有效版本、最高版本、入参检索均未命中, 说明第一进入界面返回初始化数据*/
        if (result == null) {
            //返回初始化数据
            return this.getInitializeData();
        }
        /*按id返回结果*/
        Long id = result.getId();
        //子表
        SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
        sgjsBuildSchemeList.setForeignId(id);
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeList);
        if (CollUtil.isNotEmpty(sgjsBuildSchemeListList)) {
            result.setChildren(sgjsBuildSchemeListList);
        }
        //流程信息
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.SGJS_BUILD_SCHEME);
        /*返回项目领域类型标识，用于判断流程分支走向*/
        result.setPtVar4("2");
        if (StrUtil.isNotBlank(result.getBusinessAreasAndProducts())) {
            String businessAreasAndProducts = result.getBusinessAreasAndProducts();
            String[] split = businessAreasAndProducts.split(",");
            if (businessAreas.containsKey(split[split.length - 1])) {
                result.setPtVar4("1");
            }
        }
        return result;
    }

    //第一次访问界面需要返回的数据，详情接口使用
    private SgjsBuildScheme getInitializeData() {
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        String countryName = (String) prjInfo.get("countryName");
        String countryCode = (String) prjInfo.get("projectLocation");
        String businessAreasName = (String) prjInfo.get("businessAreasAndProductsLabel");
        SgjsBuildScheme resultInit = new SgjsBuildScheme();
        resultInit.setVersion(BigDecimal.ONE);
        resultInit.setVersionStr("V1.00");
        resultInit.setCountryName(countryName);
        resultInit.setCountryCode(countryCode);
        resultInit.setTaskStatus("0");
        resultInit.setBusinessAreasAndProducts(projectDto.getBusinessAreasAndProducts());
        //固定值 0001
        resultInit.setListSerialNum("0001");
        resultInit.setPtVar1(businessAreasName);
        return resultInit;
    }

    //调整
    @Override
    public SgjsBuildScheme adjust(SgjsBuildScheme sgjsBuildSchemeParam) {
        //获取有效版本数据
        SgjsBuildScheme result = sgjsBuildSchemeMapper.getValidVersionData();
        if (result == null) return new SgjsBuildScheme();
        //基于上一版本生成一条新的数据
        SgjsBuildScheme newDataResult = this.createNewData(result);
        //历史记录按钮显隐，逻辑：所有数据中，只要有一条已审批完成即显示，否则不显示
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(new SgjsBuildScheme());
        List<SgjsBuildScheme> collect = sgjsBuildSchemeList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("5")).collect(Collectors.toList());
        newDataResult.setPtVar2(CollUtil.isEmpty(collect) ? "0" : "4");
        return newDataResult;
    }

    //基于上一版本生成一条新的数据，调整时用
    private SgjsBuildScheme createNewData(SgjsBuildScheme lastData) {
        SgjsBuildScheme result = new SgjsBuildScheme();
        result.setVersion(lastData.getVersion().add(BigDecimal.ONE));
        result.setVersionStr("V" + lastData.getVersion());
        result.setTaskStatus("0");
        result.setListSerialNum("0001");
        result.setId(null);
        //附件组id更新
        String auditRecordFile = lastData.getAuditRecordFile();
        String projectSummaryFile = lastData.getProjectSummaryFile();
        if (StringUtils.isNotEmpty(auditRecordFile)) {
            result.setAuditRecordFile(fileUploadUtil.copyFile(auditRecordFile));
        }
        if (StringUtils.isNotEmpty(projectSummaryFile)) {
            result.setProjectSummaryFile(fileUploadUtil.copyFile(projectSummaryFile));
        }
        return result;
    }

    public SgjsBuildScheme getSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        return sgjsBuildSchemeMapper.getSgjsBuildScheme(sgjsBuildScheme);
    }

    //台账、历史记录
    public List<SgjsBuildScheme> getSgjsBuildSchemeList(SgjsBuildScheme sgjsBuildScheme) {
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(sgjsBuildScheme);
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildSchemeList, FlowEnum.SGJS_BUILD_SCHEME);
        return sgjsBuildSchemeList;
    }

    //同步前期策划施工技术策划3.4.2


    //保存、提交
    @Transactional
    public Long insertSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        Long id = sgjsBuildScheme.getId();
        if (null == id) {
            //新增
            id = IdWorker.createId();
            sgjsBuildScheme.setId(id);
            sgjsBuildScheme.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setCreateTime(DateUtils.getNowDate());
            sgjsBuildSchemeMapper.insertSgjsBuildScheme(sgjsBuildScheme);
        } else {
            //修改
            this.updateSgjsBuildScheme(sgjsBuildScheme);
        }
        /*保存子表信息：方案清单、专家意见*/
        //只需要在流程未发起时处理
        sgjsBuildScheme.setId(id);
        Long foreignId = id;
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildScheme, FlowEnum.SGJS_BUILD_SCHEME);
        if (sgjsBuildScheme.getTaskStatus().equals("0")) {
            //方案清单
            List<SgjsBuildSchemeList> children = sgjsBuildScheme.getChildren();
            children.forEach(p -> p.setPtVar5(sgjsBuildScheme.getProjectCode()));
            sgjsBuildSchemeListService.insertSgjsBuildSchemeList(children, id);
        }
        if (!sgjsBuildScheme.getTaskStatus().equals("0")) {
            //专家意见
            List<SgjsBuildSchemeExpertSuggest> expertSuggest = sgjsBuildScheme.getExpertSuggest();
            expertSuggest.forEach(p -> {
                p.setPtVar5(sgjsBuildScheme.getProjectCode());
                p.setForeignId(foreignId);
            });
            schemeExpertSuggestService.insertSgjsBuildSchemeExpertSuggestList(expertSuggest);
        }
        SysUser sysUser = SecurityUtils.getSysUser();
        String tenantKey = SecurityUtils.getTenantKey();
        ThreadPoolUtil.execute(() -> doSendGm(sysUser, tenantKey));
        return id;
    }

    @Transactional
    public int insertSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList) {
        for (SgjsBuildScheme sgjsBuildScheme : sgjsBuildSchemeList) {
            sgjsBuildScheme.setId(IdWorker.createId());
            sgjsBuildScheme.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeMapper.insertSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    //状态修改
    @Transactional
    public int updateSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        int i = sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
        String tenantKey = SecurityUtils.getTenantKey();
        SysUser sysUser = SecurityUtils.getSysUser();
        this.doSendGm(sysUser, tenantKey);
        return i;
    }

    @Transactional
    public int updateSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList) {
        for (SgjsBuildScheme sgjsBuildScheme : sgjsBuildSchemeList) {
            sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeMapper.updateSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int deleteSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeMapper.deleteSgjsBuildScheme(sgjsBuildScheme);
    }

    @Transactional
    public int deleteSgjsBuildSchemeByPks(List<Long> sgjsBuildSchemePkList) {
        return sgjsBuildSchemeMapper.deleteSgjsBuildSchemeByPks(sgjsBuildSchemePkList);
    }

    //发送总部版
    public void doSendGm(SysUser sysUser, String tenantKey) {
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        try {
            String dataSourceNameByTenantKey = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
            DynamicDataSourceContextHolder.push(dataSourceNameByTenantKey);
            Thread.sleep(3000);
            //全量推送，（已发起审批的）
            SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
            List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(sgjsBuildScheme);
            if (CollUtil.isEmpty(sgjsBuildSchemeList)) {
                sendEmpty(tenantKey);
                return;
            }
            FlowInfoSearchUtilNonReqest.getFlowInfo(sgjsBuildSchemeList, FlowEnum.SGJS_BUILD_SCHEME, tenantKey, sysUser.getUserName());
            List<SgjsBuildScheme> collect1 = sgjsBuildSchemeList.stream().filter(p -> !p.getTaskStatus().equals("0")).collect(Collectors.toList());
            if (CollUtil.isEmpty(collect1)) {
                log.warn("施工方案清单 - 无已发起审批的数据");
                sendEmpty(tenantKey);
                return;
            }
            //主表
            Set<Long> collect = sgjsBuildSchemeList.stream().map(SgjsBuildScheme::getId).collect(Collectors.toSet());
            List<SgjsBuildSchemeList> childrenlist = sgjsBuildSchemeListService.getListByforeignList(collect);
            //子表  清单
            Map<Long, List<SgjsBuildSchemeList>> childrenMap = childrenlist.stream().collect(Collectors.groupingBy(SgjsBuildSchemeList::getForeignId));
            //子表，专家建议
            List<SgjsBuildSchemeExpertSuggest> suggestList = schemeExpertSuggestService.getListByforeignList(collect);
            Map<Long, List<SgjsBuildSchemeExpertSuggest>> suggestMap = suggestList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeExpertSuggest::getForeignId));
            sgjsBuildSchemeList.forEach(p -> {
                p.setPtVar5(p.getProcessTaskMan());
                p.setChildren(childrenMap.get(p.getId()));
                p.setExpertSuggest(suggestMap.get(p.getId()));
            });
            log.info("施工方案清单,推送数据：" + JSON.toJSONString(sgjsBuildSchemeList));
            rocketMQTemplate.convertAndSend("sgjs_build_scheme_list:tenantSuccess", sgjsBuildSchemeList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    //推送空数据
    private void sendEmpty(String tenantKey) {
        List<SgjsBuildScheme> objects = new ArrayList<>();
        SgjsBuildScheme param = new SgjsBuildScheme();
        param.setProjectCode(tenantKey);
        objects.add(param);
        log.info("施工方案清单,推送数据：" + JSON.toJSONString(objects));
        rocketMQTemplate.convertAndSend("sgjs_technical_science_topic:tenantSuccess", objects);
    }
}
