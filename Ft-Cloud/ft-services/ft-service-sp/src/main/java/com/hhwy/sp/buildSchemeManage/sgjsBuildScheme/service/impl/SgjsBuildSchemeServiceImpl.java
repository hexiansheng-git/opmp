package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.mapper.SgjsBuildSchemeMapper;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service.ISgjsBuildSchemeExpertSuggestService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.sp.common.FileUploadUtil;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.FlowInfoSearchUtilNonReqest;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.common.warn.SgjsWarnRecord;
import com.hhwy.sp.utils.http.HttpHeadersUtils;
import com.hhwy.sp.utils.http.RestTemplateUtils;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

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
    private SystemServiceApi systemServiceApi;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private ISgjsBuildSchemeReviewService sgjsBuildSchemeReviewService;

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
        //2获取最高版本数据
        if (result == null) result = sgjsBuildSchemeMapper.getMaxVersionData(new SgjsBuildScheme());
        /*最高版本、入参检索均未命中, 说明第一进入界面返回初始化数据*/
        if (result == null) {
            //返回初始化数据
            return this.getInitializeData();
        }
        /*按id返回结果*/
        Long id = result.getId();
        //流程信息
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.SGJS_BUILD_SCHEME);
        //子表
        SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
        sgjsBuildSchemeList.setForeignId(id);
        if (!result.getTaskStatus().equals("4")) {
            //未发起审批和未审批完成的 只展示本次调整的
            sgjsBuildSchemeList.setPtVar3("1");
        }
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeList);
        if (CollUtil.isNotEmpty(sgjsBuildSchemeListList)) {
            sgjsBuildSchemeListList.forEach(p -> {
                if (StrUtil.isNotBlank(p.getSchemeType())) {
                    String[] split = p.getSchemeType().split(",");
                    p.setSchemeTypeArr(split);
                }
            });
            result.setChildren(sgjsBuildSchemeListList);
        }
        /*返回项目领域类型标识，用于判断流程分支走向*/
        this.getBusinessAreas(result);
        //-----------给ptVar2赋值，以下逻辑用于给前端判断按钮显隐------------------
        //调整按钮显隐， 逻辑：当前请求数据如果是最高版本，并且流程结束即显示，否则不显示
        SgjsBuildScheme maxVersionData = sgjsBuildSchemeMapper.getMaxVersionData(new SgjsBuildScheme());
        FlowInfoSearchUtil.getFlowInfo(maxVersionData, FlowEnum.SGJS_BUILD_SCHEME);
        if (maxVersionData.getId().equals(result.getId()) && maxVersionData.getTaskStatus().equals("4")) {
            //可以调整
            result.setPtVar5("1");
        } else {
            //不能调整
            result.setPtVar5("2");
        }
        //历史记录按钮显隐，逻辑：所有数据中，只要有一条已审批完成即显示，否则不显示
        List<SgjsBuildScheme> allList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(new SgjsBuildScheme());
        List<SgjsBuildScheme> collect = allList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("5")).collect(Collectors.toList());
        result.setPtVar2(CollUtil.isEmpty(collect) ? "0" : "4");
        //查询审批意见
        SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest = new SgjsBuildSchemeExpertSuggest();
        sgjsBuildSchemeExpertSuggest.setForeignId(result.getId());
        List<SgjsBuildSchemeExpertSuggest> groupList = schemeExpertSuggestService.getGroupList(sgjsBuildSchemeExpertSuggest);
        result.setExpertSuggest(groupList);
        return result;
    }

    //获取项目领域类型标识
    private void getBusinessAreas(SgjsBuildScheme result) {
        //默认2：非房建
        result.setPtVar4("2");
        if (StrUtil.isBlank(result.getBusinessAreasAndProducts())) {
            return;
        }
        String businessAreasAndProducts = result.getBusinessAreasAndProducts();
        String[] split = businessAreasAndProducts.split(",");
        if (businessAreas.containsKey(split[split.length - 1])) {
            result.setPtVar4("1");
        }
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
        resultInit.setVersionStr("V1.0");
        resultInit.setCountryName(countryName);
        resultInit.setCountryCode(countryCode);
        resultInit.setTaskStatus("0");
        resultInit.setBusinessAreasAndProducts(projectDto.getBusinessAreasAndProducts());
        //固定值 0001
        resultInit.setListSerialNum("0001");
        resultInit.setPtVar1(businessAreasName);
        resultInit.setSubmisionPerson(SecurityUtils.getSysUser().getNickName());
        this.getBusinessAreas(resultInit);
        return resultInit;
    }

    //调整
    @Override
    public SgjsBuildScheme adjust(SgjsBuildScheme sgjsBuildSchemeParam) {
        //获取最高版本数据
        SgjsBuildScheme result = sgjsBuildSchemeMapper.getMaxVersionData(new SgjsBuildScheme());
        SgjsBuildScheme newDataResult = new SgjsBuildScheme();
        if (result == null) return newDataResult;
        String taskStatus = result.getTaskStatus();
        if (StrUtil.isNotBlank(taskStatus) && taskStatus.equals("5")) {
            //最高版本审批通过。基于它生成一条新的数据
            newDataResult = this.createNewData(result);
        } else {
            //最高版本未发起审批。返回它
            newDataResult = result;
        }
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
        result.setVersionStr("V" + result.getVersion());
        result.setTaskStatus("0");
        result.setListSerialNum("0001");
        result.setId(null);
        result.setPtVar3(null);
        result.setPtVar2(null);
        result.setValid("0");
        result.setSubmisionDate(lastData.getSubmisionDate());
        result.setCountryCode(lastData.getCountryCode());
        result.setCountryName(lastData.getCountryName());
        result.setBusinessAreasAndProducts(lastData.getBusinessAreasAndProducts());
        result.setPtVar1(lastData.getPtVar1());
        this.getBusinessAreas(result);
        result.setWinCertificate(lastData.getWinCertificate());
        result.setLeadEngineer(lastData.getLeadEngineer());
        result.setLeadEngineerName(lastData.getLeadEngineerName());
        result.setLeadEngineerPhoneNum(lastData.getLeadEngineerPhoneNum());
        //获取有效版本数据，用于按钮"查看整体方案"
        SgjsBuildScheme validVersionData = sgjsBuildSchemeMapper.getValidVersionData();
        if (validVersionData != null ) result.setPtVar5(String.valueOf(validVersionData.getId()));
        //附件组id更新
//        String auditRecordFile = lastData.getAuditRecordFile();
//        String auditRecordFileRegion = lastData.getAuditRecordFileRegion();
//        String projectSummaryFile = lastData.getProjectSummaryFile();
//        if (StringUtils.isNotEmpty(auditRecordFile)) {
//            result.setAuditRecordFile(fileUploadUtil.copyFile(auditRecordFile));
//        }
//        if (StringUtils.isNotEmpty(auditRecordFileRegion)) {
//            result.setAuditRecordFile(fileUploadUtil.copyFile(auditRecordFileRegion));
//        }
//        if (StringUtils.isNotEmpty(projectSummaryFile)) {
//            result.setProjectSummaryFile(fileUploadUtil.copyFile(projectSummaryFile));
//        }
        return result;
    }

    public SgjsBuildScheme getSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        return sgjsBuildSchemeMapper.getSgjsBuildScheme(sgjsBuildScheme);
    }

    //台账、历史记录
    public List<SgjsBuildScheme> getSgjsBuildSchemeList(SgjsBuildScheme sgjsBuildScheme) {
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(sgjsBuildScheme);
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildSchemeList, FlowEnum.SGJS_BUILD_SCHEME);
        //修改时间格式
        sgjsBuildSchemeList.forEach(p -> {
            Date updateTime = p.getUpdateTime();
            if (updateTime != null) {
                String format = DateUtil.format(updateTime, "yyyy年MM月dd日 HH:mm");
                p.setPtVar2(format);
            }
        });
        return sgjsBuildSchemeList;
    }

    //同步前期策划施工技术策划3.4.2


    //保存、提交
    @Transactional
    public Long insertSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        List<SgjsBuildSchemeList> children1 = sgjsBuildScheme.getChildren();
        JyDetailsUtil.jyDetails(children1, ValidationGroups.Save.class);
        sgjsBuildScheme.setProjectCode(SecurityUtils.getTenantKey());
        Long id = sgjsBuildScheme.getId();
        if (null == id) {
            //新增
            id = IdWorker.createId();
            sgjsBuildScheme.setId(id);
            sgjsBuildScheme.setPtVar3(null);
            sgjsBuildScheme.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setCreateTime(DateUtils.getNowDate());
            sgjsBuildSchemeMapper.insertSgjsBuildScheme(sgjsBuildScheme);
        } else {
            //修改
            sgjsBuildScheme.setPtVar3(null);
            this.updateSgjsBuildScheme(sgjsBuildScheme);
        }
        /*保存子表信息：方案清单、专家意见*/
        //只需要在流程未发起时处理
        sgjsBuildScheme.setId(id);
        Long foreignId = id;
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildScheme, FlowEnum.SGJS_BUILD_SCHEME);
        //方案清单
        List<SgjsBuildSchemeList> children = sgjsBuildScheme.getChildren();
        if (CollUtil.isNotEmpty(children)) {
            children.forEach(p -> {
                String[] schemeTypeArr = p.getSchemeTypeArr();
                if (ArrayUtil.isNotEmpty(schemeTypeArr)) {
                    String collect = Arrays.stream(schemeTypeArr).collect(Collectors.joining(","));
                    p.setSchemeType(collect);
                }
            });
        }
        sgjsBuildSchemeListService.insertSgjsBuildSchemeList(children, sgjsBuildScheme);
        if (!sgjsBuildScheme.getTaskStatus().equals("0")) {
            //专家意见
            List<SgjsBuildSchemeExpertSuggest> expertSuggest = sgjsBuildScheme.getExpertSuggest();
            expertSuggest.forEach(p -> {
                p.setPtVar5(sgjsBuildScheme.getProjectCode());
                p.setForeignId(foreignId);
            });
            schemeExpertSuggestService.insertSgjsBuildSchemeExpertSuggestList(expertSuggest);
        }
//        String userName = SecurityUtils.getUserName();
//        String tenantKey = SecurityUtils.getTenantKey();
//        ThreadPoolUtil.execute(() -> doSendGm(tenantKey, userName));
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

    //修改
    @Transactional
    public int updateSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
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

    //流程监听，状态修改
    @Override
    @Transactional
    public void updateTaskStatus(Long id, String isPass) {
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setTaskStatus("5");
        sgjsBuildScheme.setId(id);
        sgjsBuildScheme.setPtVar3(isPass);
        if (StrUtil.isBlank(isPass)) {
            //设置为无效
            sgjsBuildScheme.setValid("0");
            //修改当前记录状态
            sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
        } else {
            //0不通过 1通过
            sgjsBuildScheme.setValid(isPass);
            if (isPass.equals("0")) {
                //不通过 修改当前记录状态
                sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
            } else {
                //通过 修改原有效数据为无效
                sgjsBuildSchemeMapper.updateNonValid(new SgjsBuildScheme());
                //修改当前记录状态
                sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
                //发送总部版， 只有生效得数据需要推送
                String userName = SecurityUtils.getUserName();
                String tenantKey = SecurityUtils.getTenantKey();
                ThreadPoolUtil.execute(() -> doSendGm(tenantKey, userName));
            }
        }
    }

    //发送总部版
    public void doSendGm(String tenantKey, String loginUserName) {
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
            FlowInfoSearchUtilNonReqest.getFlowInfo(sgjsBuildSchemeList, FlowEnum.SGJS_BUILD_SCHEME, tenantKey, loginUserName);
            List<SgjsBuildScheme> sendList = sgjsBuildSchemeList.stream().filter(p -> !p.getTaskStatus().equals("0")).collect(Collectors.toList());
            if (CollUtil.isEmpty(sendList)) {
                log.warn("施工方案清单 - 无已发起审批的数据");
                sendEmpty(tenantKey);
                return;
            }
            //主表
            Set<Long> collect = sendList.stream().map(SgjsBuildScheme::getId).collect(Collectors.toSet());
            List<SgjsBuildSchemeList> childrenlist = sgjsBuildSchemeListService.getListByforeignList(collect);
            //子表  清单
            Map<Long, List<SgjsBuildSchemeList>> childrenMap = childrenlist.stream().collect(Collectors.groupingBy(SgjsBuildSchemeList::getForeignId));
            //子表，专家建议
            List<SgjsBuildSchemeExpertSuggest> suggestList = schemeExpertSuggestService.getListByforeignList(collect);
            Map<Long, List<SgjsBuildSchemeExpertSuggest>> suggestMap = suggestList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeExpertSuggest::getForeignId));
            sendList.forEach(p -> {
                p.setPtVar5(p.getProcessTaskMan());
                p.setChildren(childrenMap.get(p.getId()));
                p.setExpertSuggest(suggestMap.get(p.getId()));
            });
            log.info("施工方案清单,推送数据：" + JSON.toJSONString(sendList));
            rocketMQTemplate.convertAndSend("sgjs_build_scheme:tenantSuccess", sendList);
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
        rocketMQTemplate.convertAndSend("sgjs_build_scheme:tenantSuccess", objects);
    }

    @Value("${gm.url}")
    private String gmUrl;
    @Value("${warn.schemeListUrl}")
    private String schemeListUrl;

    //预警消息发送
    @Override
    public void warnMessage() {
        //从总部获取预警配置信息
        String url = gmUrl + "/gm/sgjsWarnConfig/list?warnSubject={warnSubject}";
        String warnItemId = WarnItem.SGJS_BUILD_SCHEME_LIST.getWarnItemId();
        SgjsWarnConfig sgjsWarnConfig = CommonBusiness.getSgjsWarnConfig(url, warnItemId);
        if (null == sgjsWarnConfig) return;
        /*遍历所有租户发送预警*/
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
            for (SysTenant tenant : tenantList) {
                /*查询施工方案评审数据*/
                SgjsBuildSchemeReview sgjsBuildSchemeReview = new SgjsBuildSchemeReview();
                List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList = sgjsBuildSchemeReviewService.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
                if (CollUtil.isEmpty(sgjsBuildSchemeReviewList)) {
                    log.info("施工方案评审数据无数据");
                    return;
                }
                /*查询流程，过滤得到未发起审批的数据*/
                List<SgjsBuildSchemeReview> list23 = sgjsBuildSchemeReviewList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getSchemeLevel()) && (p.getSchemeLevel().equals("2") || p.getSchemeLevel().equals("3")))
                        .collect(Collectors.toList());
                List<SgjsBuildSchemeReview> list4 = sgjsBuildSchemeReviewList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getSchemeLevel()) && (p.getSchemeLevel().equals("4")))
                        .collect(Collectors.toList());
                //不同方案级别走不同的流程,级别1不走流程
                FlowInfoSearchUtil.getFlowInfo(list23, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
                FlowInfoSearchUtil.getFlowInfo(list4, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
                ArrayList<SgjsBuildSchemeReview> allList = new ArrayList<>();
                allList.addAll(list23);
                allList.addAll(list4);
                //未发起审批的数据
                List<SgjsBuildSchemeReview> warnList = allList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("0")).collect(Collectors.toList());
                /*提前七天提醒一次，超期后每两天进行告警*/
                //预警触发标识
                boolean triggerFlag = false;
                Date nowDate = new Date();
                for (SgjsBuildSchemeReview schemeList : warnList) {
                    Date planCompletionTime = schemeList.getPlanCompletionTime();
                    long between = DateUtil.between(nowDate, planCompletionTime, DateUnit.DAY, false);
                    if (between == 7 || (between <= 0 && between % 2 == 0)) {
                        //有一条数据满足条件则修改标识
                        triggerFlag = true;
                        break;
                    }
                }
                if (!triggerFlag) {
                    log.info("施工方案清单预警执行, 无需预警。。。。");
                    return;
                }
                /*执行预警*/
                //根据角色获取用户
                List<SysUser> sysUsers = CommonBusiness.getSysUsers(sgjsWarnConfig);
                if (CollUtil.isEmpty(sysUsers)) return;
                String userNames = sysUsers.stream().map(p -> String.valueOf(p.getUserName())).collect(Collectors.joining(","));
                //发送预警
                ArrayList<TWarn> objects = new ArrayList<>();
                TWarn tWarn = new TWarn();
                tWarn.setWarnItem(sgjsWarnConfig.getWarnSubject());
                tWarn.setWarnItemId(WarnItem.SGJS_BUILD_SCHEME_LIST.getWarnItemId());
                tWarn.setWarnScope(userNames);
                tWarn.setWarnUrl(schemeListUrl);
                tWarn.setWarnScopeType("3");
                String warnContent = CommonBusiness.warnMessageHandle(sgjsWarnConfig.getWarnMassage(), tenant.getTenantName(), sgjsWarnConfig.getPtVar1(), sgjsWarnConfig.getWarnRule());
                tWarn.setWarnContent(warnContent);
                tWarn.setProjectName(tenant.getTenantName());
                tWarn.setTenantKey(tenant.getTenantKey());
                objects.add(tWarn);
                //预警记录保存
                List<SgjsWarnRecord> warnRecordList = new ArrayList<>();
                sysUsers.forEach(p -> {
                    //预警记录
                    SgjsWarnRecord sgjsWarnRecord = new SgjsWarnRecord();
                    sgjsWarnRecord.setProjectCode(tenant.getTenantKey());
                    sgjsWarnRecord.setProjectName(tenant.getTenantName());
                    sgjsWarnRecord.setWarnContent(warnContent);
                    sgjsWarnRecord.setWarnUserId(String.valueOf(p.getUserId()));
                    sgjsWarnRecord.setWarnUser(p.getUserName());
                    warnRecordList.add(sgjsWarnRecord);
                });
                //发送预警
                if (CollUtil.isNotEmpty(objects)) {
                    systemServiceApi.insertTWarnList(objects);
                }
                log.info("施工方案清单预警执行完成。。。。共:{}", objects.size());
                /*推送总部*/
                if (CollUtil.isNotEmpty(warnRecordList)) {
                    log.info("施工方案清单预警记录推送数据：" + JSON.toJSONString(warnRecordList));
                    rocketMQTemplate.convertAndSend("sgjs_build_scheme_list_warn:tenantSuccess", warnRecordList);
                }
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

}
