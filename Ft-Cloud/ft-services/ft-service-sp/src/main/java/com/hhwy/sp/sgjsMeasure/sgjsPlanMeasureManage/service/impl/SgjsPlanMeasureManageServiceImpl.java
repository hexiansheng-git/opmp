package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.utils.Constant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.tree.TreeUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.mapper.SgjsPlanMeasureManageMapper;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.ISgjsPlanMeasureManageService;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark
 */
@Service
public class SgjsPlanMeasureManageServiceImpl implements ISgjsPlanMeasureManageService {

    @Autowired
    private SgjsPlanMeasureManageMapper sgjsPlanMeasureManageMapper;

    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsPlanMeasureManage getSgjsPlanMeasureManage(
        SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        return sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    public List<SgjsPlanMeasureManage> getSgjsPlanMeasureManageList(
        SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        return sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(sgjsPlanMeasureManage);
    }

    public SgjsPlanMeasureManageVo list(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = new SgjsPlanMeasureManageVo();
        //查询条件 时间字段处理
        if (StringUtils.isNotEmpty(sgjsPlanMeasureManage.getPlanStartDateStr())) {
            String planStartDateStr = sgjsPlanMeasureManage.getPlanStartDateStr();
            String[] split = planStartDateStr.split("~");
            sgjsPlanMeasureManage.setPlanStartDate(
                FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            sgjsPlanMeasureManage.setPlanEndDate(
                FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }
        if (StringUtils.isNotEmpty(sgjsPlanMeasureManage.getRealStartDateStr())) {
            String realStartDateStr = sgjsPlanMeasureManage.getRealStartDateStr();
            String[] split = realStartDateStr.split("~");
            sgjsPlanMeasureManage.setRealStartDate(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            sgjsPlanMeasureManage.setRealEndDate(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList = sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(
            sgjsPlanMeasureManage);
        if (sgjsPlanMeasureManageList.size() > 0) {
            sgjsPlanMeasureManageList.forEach(plan -> {
                plan.setPlanStartDateStr(FtDateUtils.formatDate(plan.getPlanStartDate()));
                plan.setPlanEndDateStr(FtDateUtils.formatDate(plan.getPlanEndDate()));
                plan.setRealStartDateStr(FtDateUtils.formatDate(plan.getRealStartDate()));
                plan.setRealEndDateStr(FtDateUtils.formatDate(plan.getRealEndDate()));
            });
        }
        sgjsPlanMeasureManageVo.setTreeList(TreeUtil.build(sgjsPlanMeasureManageList, 0L));
        return sgjsPlanMeasureManageVo;
    }

    @Override
    public SgjsPlanMeasureManageVo qqchMeasureExpPlanSelect() {
        SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = new SgjsPlanMeasureManageVo();
        List<SgjsPlanMeasureManage> treeToList = new ArrayList<>();
        AjaxResult ajaxResult = pmServiceApi.qqchMeasureExpPlanList();
        if (!ajaxResult.get("code").toString().equals(Constant.SUCCESS_CODE)) {
            throw new BaseException("同步前期策划数据失败");
        }
        Map<String, Object> dataMap = (Map<String, Object>) ajaxResult.get("data");
        List<LinkedHashMap<String, Object>> riskBigProjList = (List<LinkedHashMap<String, Object>>) dataMap.get(
            "dto");
        //递归处理3.6.2数据结果
        digui(riskBigProjList, treeToList);
        sgjsPlanMeasureManageVo.setTreeList(treeToList);
        return sgjsPlanMeasureManageVo;
    }


    private void digui(List<LinkedHashMap<String, Object>> list,
        List<SgjsPlanMeasureManage> treeToList) {
        for (LinkedHashMap<String, Object> l : list) {
            SgjsPlanMeasureManage sgjsPlanMeasureManage = new SgjsPlanMeasureManage();
            sgjsPlanMeasureManage.setMeasureName(
                l.get("workItem") == null ? null : (String) l.get("workItem"));
            sgjsPlanMeasureManage.setMeasureUnit(
                l.get("unit") == null ? null : (String) l.get("unit"));
            sgjsPlanMeasureManage.setWorkload(
                l.get("workload") == null ? null : (Integer) l.get("workload"));
            sgjsPlanMeasureManage.setPlanStartDate(l.get("planBeginDate") == null ? null
                : FtDateUtils.parseDate(l.get("planBeginDate")));
            sgjsPlanMeasureManage.setPlanEndDate(
                l.get("planEndDate") == null ? null : FtDateUtils.parseDate(l.get("planEndDate")));
            sgjsPlanMeasureManage.setPid(
                l.get("pid") == null ? 0L : Long.parseLong((String) l.get("pid")));
            treeToList.add(sgjsPlanMeasureManage);
            List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) l.get(
                "children");
            if (children.size() > 0) {
                digui(children, treeToList);
            }
        }
    }

    @Transactional
    public int insertSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setId(IdWorker.createId());
        sgjsPlanMeasureManage.setCreateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setCreateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.insertSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    @Transactional
    public AjaxResult batchAdd(SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo) {
        List<SgjsPlanMeasureManage> treeToList = null;
        if (CollectionUtils.isEmpty(sgjsPlanMeasureManageVo.getTreeList())) {
            return AjaxResult.error("数据异常");
        }
        //删除库中所有数据
        SgjsPlanMeasureManage info = new SgjsPlanMeasureManage();
        info.setUpdateTime(DateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId() + "");
        sgjsPlanMeasureManageMapper.deleteAll(info);
        //数据处理
        treeToList = TreeUtil.treeToList(sgjsPlanMeasureManageVo.getTreeList());
        for (int i = 0; i < treeToList.size(); i++) {
            SgjsPlanMeasureManage sgjsPlanMeasureManage = treeToList.get(i);
//            sgjsPlanMeasureManage.setPlanStartDate(FtDateUtils.parseDate(sgjsPlanMeasureManage.getPlanStartDateStr().replaceAll("(?:年|月|日)", "-")));
//            sgjsPlanMeasureManage.setPlanEndDate(FtDateUtils.parseDate(sgjsPlanMeasureManage.getPlanEndDateStr().replaceAll("(?:年|月|日)", "-")));
//            sgjsPlanMeasureManage.setRealStartDate(FtDateUtils.parseDate(sgjsPlanMeasureManage.getRealStartDateStr().replaceAll("(?:年|月|日)", "-")));
//            sgjsPlanMeasureManage.setRealEndDate(FtDateUtils.parseDate(sgjsPlanMeasureManage.getRealEndDateStr().replaceAll("(?:年|月|日)", "-")));
            sgjsPlanMeasureManage.setCreateTime(DateTime.now());
            sgjsPlanMeasureManage.setCreateUser(SecurityUtils.getUserId() + "");
            sgjsPlanMeasureManage.setCreateUserName(SecurityUtils.getUserName() + "");
        }
        sgjsPlanMeasureManageMapper.insertSgjsPlanMeasureManageList(treeToList);
        return AjaxResult.success();
    }

    @Transactional
    public int updateSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.updateSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    @Transactional
    public int updateSgjsPlanMeasureManageList(
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList) {
        for (SgjsPlanMeasureManage sgjsPlanMeasureManage : sgjsPlanMeasureManageList) {
            sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPlanMeasureManageMapper.updateSgjsPlanMeasureManageList(
            sgjsPlanMeasureManageList);
    }

    @Transactional
    public int deleteSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.deleteSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    @Transactional
    public int deleteSgjsPlanMeasureManageByPks(List<Long> sgjsPlanMeasureManagePkList) {
        return sgjsPlanMeasureManageMapper.deleteSgjsPlanMeasureManageByPks(
            sgjsPlanMeasureManagePkList);
    }
}
