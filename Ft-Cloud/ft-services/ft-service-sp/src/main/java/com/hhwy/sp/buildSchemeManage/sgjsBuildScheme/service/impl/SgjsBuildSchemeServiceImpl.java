package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
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
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:26
 * @remark
 */
@Service
public class SgjsBuildSchemeServiceImpl implements ISgjsBuildSchemeService {

    @Autowired
    private SgjsBuildSchemeMapper sgjsBuildSchemeMapper;
    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;
    @Autowired
    private ISgjsBuildSchemeExpertSuggestService schemeExpertSuggestService;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    //详情
    public SgjsBuildScheme detail(SgjsBuildScheme sgjsBuildScheme) {
        //获取有效版本数据
        SgjsBuildScheme result = sgjsBuildSchemeMapper.getValidVersionData();
        //获取最高版本数据
        if (result == null) result = sgjsBuildSchemeMapper.getMaxVersionData();
        if (sgjsBuildScheme != null && sgjsBuildScheme.getId() != null) {
            SgjsBuildScheme param = new SgjsBuildScheme();
            param.setId(sgjsBuildScheme.getId());
            result = sgjsBuildSchemeMapper.getSgjsBuildScheme(param);
        }
        /*有效版本、最高版本、入参查询均为命中*/
        if ( result == null ) {
            //返回初始化数据
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            String countryName = (String) prjInfo.get("countryName");
            String countryCode = (String) prjInfo.get("projectLocation");
            SgjsBuildScheme resultInit = new SgjsBuildScheme();
            resultInit.setVersion(BigDecimal.ONE);
            resultInit.setVersionStr("V1.00");
            resultInit.setCountryName(countryName);
            resultInit.setCountryCode(countryCode);
            resultInit.setTaskStatus("0");
            return resultInit;
        }
        Long id = sgjsBuildScheme.getId();
        SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
        sgjsBuildSchemeList.setForeignId(id);
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeList);
        if (CollUtil.isNotEmpty(sgjsBuildSchemeListList)) {
            result.setChildren(sgjsBuildSchemeListList);
        }
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.SGJS_BUILD_SCHEME);
        return result;
    }

    //调整
    @Override
    public SgjsBuildScheme adjust(SgjsBuildScheme sgjsBuildSchemeParam) {
        //获取有效版本数据
        SgjsBuildScheme result = sgjsBuildSchemeMapper.getValidVersionData();
        if (result == null) return result;
        String taskStatus = result.getTaskStatus();
        if (taskStatus.equals("5")) {
            //创建新的数据
            result.setVersion(result.getVersion().add(BigDecimal.ONE));
            result.setVersionStr("V" + result.getVersion());
            result.setTaskStatus("0");
            result.setListSerialNum("0001");
            result.setId(null);
            //附件组id更新
            String auditRecordFile = result.getAuditRecordFile();
            String projectSummaryFile = result.getProjectSummaryFile();
            if (StringUtils.isNotEmpty(auditRecordFile)) {
                result.setAuditRecordFile(fileUploadUtil.copyFile(auditRecordFile));
            }
            if (StringUtils.isNotEmpty(projectSummaryFile)) {
                result.setProjectSummaryFile(fileUploadUtil.copyFile(projectSummaryFile));
            }
        }
        //历史记录按钮显隐，逻辑：所有数据中，只要有一条已审批完成即显示，否则不显示
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(new SgjsBuildScheme());
        List<SgjsBuildScheme> collect = sgjsBuildSchemeList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("5")).collect(Collectors.toList());
        result.setPtVar2(CollUtil.isEmpty(collect) ? "0" : "4");
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
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildScheme, FlowEnum.SGJS_BUILD_SCHEME);
        if (!sgjsBuildScheme.getTaskStatus().equals("0")) {
            //方案清单
            List<SgjsBuildSchemeList> children = sgjsBuildScheme.getChildren();
            sgjsBuildSchemeListService.insertSgjsBuildSchemeList(children, id);
            //专家意见
            List<SgjsBuildSchemeExpertSuggest> expertSuggest = sgjsBuildScheme.getExpertSuggest();
            schemeExpertSuggestService.insertSgjsBuildSchemeExpertSuggestList(expertSuggest);
        }
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
}
