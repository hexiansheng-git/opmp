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
        sgjsPlanMeasureManageVo.setTreeList(TreeUtil.newBuild(sgjsPlanMeasureManageList));
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

    @Override
    public List<SgjsPlanMeasureManage> getIds(List<Long> ids) {
        return sgjsPlanMeasureManageMapper.getIds(ids);
    }


    private void digui(List<LinkedHashMap<String, Object>> list,
        List<SgjsPlanMeasureManage> treeToList) {
        for (LinkedHashMap<String, Object> l : list) {
            SgjsPlanMeasureManage sgjsPlanMeasureManage = new SgjsPlanMeasureManage();
            sgjsPlanMeasureManage.setMeasureName(
                l.get("workItem") == null ? null : l.get("workItem").toString());
            sgjsPlanMeasureManage.setMeasureUnit(
                l.get("unit") == null ? null : l.get("unit").toString());
            sgjsPlanMeasureManage.setWorkload(
                l.get("workload") == null ? null : l.get("workload").toString());
            sgjsPlanMeasureManage.setPlanStartDate(l.get("planBeginDate") == null ? null
                : FtDateUtils.parseDate(l.get("planBeginDate")));
            sgjsPlanMeasureManage.setPlanEndDate(
                l.get("planEndDate") == null ? null : FtDateUtils.parseDate(l.get("planEndDate")));
            sgjsPlanMeasureManage.setId(l.get("id") == null ? 0L : Long.parseLong(l.get("id").toString()));
            sgjsPlanMeasureManage.setPid(
                l.get("pid") == null ? 0L : Long.parseLong(l.get("pid").toString()));
            //同步标识
            sgjsPlanMeasureManage.setDataSource("1");
            sgjsPlanMeasureManage.setIsAdd("1");
            List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) l.get("children");
            if(children.size()>0){
                for (LinkedHashMap<String, Object> linkedHashMap : children) {
                    SgjsPlanMeasureManage sgjsPlanMeasureManage1 = new SgjsPlanMeasureManage();
                    sgjsPlanMeasureManage1.setMeasureName(
                        linkedHashMap.get("workItem") == null ? null : linkedHashMap.get("workItem").toString());
                    sgjsPlanMeasureManage1.setMeasureUnit(
                        linkedHashMap.get("unit") == null ? null : linkedHashMap.get("unit").toString());
                    sgjsPlanMeasureManage1.setWorkload(
                        linkedHashMap.get("workload") == null ? null : linkedHashMap.get("workload").toString());
                    sgjsPlanMeasureManage1.setPlanStartDate(linkedHashMap.get("planBeginDate") == null ? null
                        : FtDateUtils.parseDate(linkedHashMap.get("planBeginDate")));
                    sgjsPlanMeasureManage1.setPlanEndDate(
                        linkedHashMap.get("planEndDate") == null ? null : FtDateUtils.parseDate(linkedHashMap.get("planEndDate")));
                    sgjsPlanMeasureManage1.setId(linkedHashMap.get("id") == null ? IdWorker.createId() : Long.parseLong(linkedHashMap.get("id").toString()));
                    sgjsPlanMeasureManage1.setPid(
                        linkedHashMap.get("pid") == null ? 0L : Long.parseLong(linkedHashMap.get("pid").toString()));
                    //同步标识
                    sgjsPlanMeasureManage1.setDataSource("1");
                    sgjsPlanMeasureManage1.setIsAdd("1");
                    sgjsPlanMeasureManage.getChildren().add(sgjsPlanMeasureManage1);
                    List<LinkedHashMap<String, Object>> children1 = (List<LinkedHashMap<String, Object>>) linkedHashMap.get("children");
                    digui(children1, treeToList);
                }
            }
            treeToList.add(sgjsPlanMeasureManage);
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
        if (!CollectionUtils.isEmpty(sgjsPlanMeasureManageVo.getTreeList())) {
            treeToList = TreeUtil.treeToListWithoutId(sgjsPlanMeasureManageVo.getTreeList());
            List<SgjsPlanMeasureManage> insertList = treeToList.stream().filter(p -> StringUtils.isNotEmpty(p.getIsAdd()) && p.getIsAdd().equals("1")).collect(Collectors.toList());
            //批量入库
            if(!CollectionUtils.isEmpty(insertList)){
                for (int i = 0; i < insertList.size(); i++) {
                    SgjsPlanMeasureManage sgjsPlanMeasureManage = insertList.get(i);
                    sgjsPlanMeasureManage.setCreateTime(DateTime.now());
                    sgjsPlanMeasureManage.setCreateUser(SecurityUtils.getUserId() + "");
                    sgjsPlanMeasureManage.setCreateUserName(SecurityUtils.getUserName() + "");
                }
                sgjsPlanMeasureManageMapper.insertSgjsPlanMeasureManageList(insertList);
            }
            //批量编辑
            List<SgjsPlanMeasureManage> updateList = treeToList.stream().filter(p -> StringUtils.isEmpty(p.getIsAdd())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(updateList)){
                for (int i = 0; i < updateList.size(); i++) {
                    SgjsPlanMeasureManage sgjsPlanMeasureManage = updateList.get(i);
                    sgjsPlanMeasureManage.setUpdateTime(DateTime.now());
                    sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserId() + "");
                    sgjsPlanMeasureManage.setDelFlag("0");
                }
                sgjsPlanMeasureManageMapper.updateSgjsPlanMeasureManageList(updateList);
            }
        }
        //批量删除
        deleteByIds(sgjsPlanMeasureManageVo.getDelIdList());
        return AjaxResult.success();
    }

    /**
     * 批量删除
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        List<SgjsPlanMeasureManage> list =new ArrayList<>();
        for (int i = 0; i < delIdList.size(); i++) {
            SgjsPlanMeasureManage info=new SgjsPlanMeasureManage();
            info.setId(Long.parseLong(delIdList.get(i)));
            info.setUpdateUser(SecurityUtils.getUserId()+"");
            info.setUpdateTime(DateUtils.getNowDate());
            info.setDelFlag("1");
            list.add(info);
        }
        //删除
        if(!CollectionUtils.isEmpty(list)){
            sgjsPlanMeasureManageMapper.deleteInfoData(list);
        }
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
