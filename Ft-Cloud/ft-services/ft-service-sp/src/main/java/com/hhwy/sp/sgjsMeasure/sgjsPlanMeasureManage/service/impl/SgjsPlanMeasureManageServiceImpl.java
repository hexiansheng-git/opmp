package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
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
import org.springframework.util.StreamUtils;

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

    public SgjsPlanMeasureManageVo list(SgjsPlanMeasureManage sgjsPlanMeasureManage)
        throws ParseException {
        SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = new SgjsPlanMeasureManageVo();
        //查询条件 时间字段处理
        if (StringUtils.isNotEmpty(sgjsPlanMeasureManage.getPlanStartDateStr())) {
            String planStartDateStr = sgjsPlanMeasureManage.getPlanStartDateStr();
            String[] split = planStartDateStr.split("~");
            sgjsPlanMeasureManage.setPlanStartDateStr(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy年MM月dd日").parse(split[0])));
            sgjsPlanMeasureManage.setPlanEndDateStr(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy年MM月dd日").parse(split[1])));
        }
        if (StringUtils.isNotEmpty(sgjsPlanMeasureManage.getRealStartDateStr())) {
            String realStartDateStr = sgjsPlanMeasureManage.getRealStartDateStr();
            String[] split = realStartDateStr.split("~");
            sgjsPlanMeasureManage.setRealStartDateStr(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy年MM月dd日").parse(split[0])));
            sgjsPlanMeasureManage.setRealEndDateStr(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy年MM月dd日").parse(split[1])));
        }
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList = sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(sgjsPlanMeasureManage);
        List<SgjsPlanMeasureManage> list = new ArrayList<>();
        if (sgjsPlanMeasureManageList.size() > 0) {
            sgjsPlanMeasureManageList.forEach(plan -> {
                plan.setPlanStartDateStr(plan.getPlanStartDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getPlanStartDate()));
                plan.setPlanEndDateStr(plan.getPlanEndDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getPlanEndDate()));
                plan.setRealStartDateStr(plan.getRealStartDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getRealStartDate()));
                plan.setRealEndDateStr(plan.getRealEndDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getRealEndDate()));
            });
            List<SgjsPlanMeasureManage> list1 = sgjsPlanMeasureManageList.stream().filter(p -> StringUtils.isNotEmpty(p.getPid().toString()) && !p.getPid().toString().equals("0")).collect(Collectors.toList());
            if(list1.size()>0){
                List<String> data=new ArrayList<>();
                SgjsPlanMeasureManage sgjsPlanMeasureManage1 = new SgjsPlanMeasureManage();
                for (int i = 0; i < list1.size(); i++) {
                    if(StringUtils.isEmpty(list1.get(i).getPath())){
                        continue;
                    }
                    if(list1.get(i).getPath().contains("/")){
                        String[] split = list1.get(i).getPath().split("/");
                        List allPath = Arrays.asList(split);
                        data.addAll(allPath);
                    }else{
                        data.add(list1.get(i).getPath());
                    }
                }
                sgjsPlanMeasureManage1.setPaths(data);
                List<SgjsPlanMeasureManage> sgjsPlanMeasureManage2 = sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(sgjsPlanMeasureManage1);
                sgjsPlanMeasureManageList.addAll(sgjsPlanMeasureManage2);
            }
            List<SgjsPlanMeasureManage> collect = sgjsPlanMeasureManageList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SgjsPlanMeasureManage::getId))), ArrayList::new));
            list = collect.stream().sorted(Comparator.comparing(SgjsPlanMeasureManage::getSerialNumber)).collect(Collectors.toList());
        }
        sgjsPlanMeasureManageVo.setTreeList(TreeUtil.newBuild(list));
        return sgjsPlanMeasureManageVo;
    }


    @Override
    public List<SgjsPlanMeasureManage> getIds(List<String> ids) {
        List<SgjsPlanMeasureManage> list = new ArrayList<>();
        List<SgjsPlanMeasureManage> list1 = sgjsPlanMeasureManageMapper.getIds(ids);
        for (int i = 0; i < list1.size(); i++) {
            list1.get(i);
            SgjsPlanMeasureManage sgjsPlanMeasureManage = new SgjsPlanMeasureManage();
            sgjsPlanMeasureManage.setPid(list1.get(i).getId());
            List<SgjsPlanMeasureManage> list2 = sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(sgjsPlanMeasureManage);
            if(list2.size()>0){
                diguiList2(list2,list1.get(i));
            }
            list.add(list1.get(i));
        }
        if(list.size()>0){
            list = TreeUtil.treeToListWithoutId(list);
            list = list.stream().sorted(Comparator.comparing(SgjsPlanMeasureManage::getSerialNumber)).collect(Collectors.toList());
        }
        return list;
    }

    private void diguiList2(List<SgjsPlanMeasureManage> list2, SgjsPlanMeasureManage manage) {
        List<SgjsPlanMeasureManage> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            SgjsPlanMeasureManage planMeasureManage = new SgjsPlanMeasureManage();
            planMeasureManage.setPid(list2.get(i).getId());
            List<SgjsPlanMeasureManage> list3 = sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(planMeasureManage);
            if(list3.size()>0){
                diguiList2(list3,list2.get(i));
            }
            list.add(list2.get(i));
        }
        manage.setChildren(list);
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
        List<LinkedHashMap<String, Object>> riskBigProjList = (List<LinkedHashMap<String, Object>>) dataMap.get("dto");
        //递归处理3.6.2数据结果
        if(!CollectionUtils.isEmpty(riskBigProjList)){
            digui(riskBigProjList, treeToList);
        }
        sgjsPlanMeasureManageVo.setTreeList(treeToList);
        SgjsPlanMeasureManage sgjsPlanMeasureManage = new SgjsPlanMeasureManage();
        sgjsPlanMeasureManage.setUpdateTime(DateTime.now());
        sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserId() + "");
        sgjsPlanMeasureManageMapper.delAll(sgjsPlanMeasureManage);
        return sgjsPlanMeasureManageVo;
    }

    private void digui(List<LinkedHashMap<String, Object>> list, List<SgjsPlanMeasureManage> treeToList) {
        for (int i = 0; i < list.size(); i++) {
            SgjsPlanMeasureManage manage = new SgjsPlanMeasureManage();
            manage.setMeasureName(list.get(i).get("workItem") == null ? null : list.get(i).get("workItem").toString());
            manage.setMeasureUnit(list.get(i).get("unit") == null ? null : list.get(i).get("unit").toString());
            manage.setWorkload(list.get(i).get("workload") == null ? null : list.get(i).get("workload").toString());
            manage.setPlanStartDate(list.get(i).get("planBeginDate") == null ? null : FtDateUtils.parseDate(list.get(i).get("planBeginDate")));
            manage.setPlanEndDate(list.get(i).get("planEndDate") == null ? null : FtDateUtils.parseDate(list.get(i).get("planEndDate")));
            manage.setRemark(list.get(i).get("remark") == null ? null : list.get(i).get("remark").toString());
            manage.setId(IdWorker.createId());
            manage.setPid(0L);
            manage.setSyncId(Long.parseLong(list.get(i).get("id").toString()));
            //同步标识
            manage.setDataSource("1");
            manage.setIsAdd("1");
            List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) list.get(i).get("children");
            if(children.size()>0){
                diguiChildren(children,manage);
            }
            treeToList.add(manage);
        }
    }
    private void diguiChildren(List<LinkedHashMap<String, Object>> children, SgjsPlanMeasureManage manage) {
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            SgjsPlanMeasureManage planMeasureManage = new SgjsPlanMeasureManage();
            planMeasureManage.setMeasureName(children.get(i).get("workItem") == null ? null : children.get(i).get("workItem").toString());
            planMeasureManage.setMeasureUnit(children.get(i).get("unit") == null ? null : children.get(i).get("unit").toString());
            planMeasureManage.setWorkload(children.get(i).get("workload") == null ? null : children.get(i).get("workload").toString());
            planMeasureManage.setPlanStartDate(children.get(i).get("planBeginDate") == null ? null : FtDateUtils.parseDate(children.get(i).get("planBeginDate")));
            planMeasureManage.setPlanEndDate(children.get(i).get("planEndDate") == null ? null : FtDateUtils.parseDate(children.get(i).get("planEndDate")));
            planMeasureManage.setRemark(children.get(i).get("remark") == null ? null : children.get(i).get("remark").toString());
            planMeasureManage.setId(IdWorker.createId());
            planMeasureManage.setPid(manage.getId());
            planMeasureManage.setSyncId(Long.parseLong(children.get(i).get("id").toString()));
            //同步标识
            planMeasureManage.setDataSource("1");
            planMeasureManage.setIsAdd("1");
            List<LinkedHashMap<String, Object>> children1 = (List<LinkedHashMap<String, Object>>) children.get(i).get("children");
            if(children1.size()>0){
                diguiChildren(children1,planMeasureManage);
            }
            sgjsPlanMeasureManageList.add(planMeasureManage);
        }
        manage.setChildren(sgjsPlanMeasureManageList);
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
        if(!CollectionUtils.isEmpty(sgjsPlanMeasureManageVo.getDelIdList())){
            deleteByIds(sgjsPlanMeasureManageVo.getDelIdList());
        }
        return AjaxResult.success();
    }

    /**
     * 批量删除
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        List<SgjsPlanMeasureManage> list =new ArrayList<>();
        List<SgjsPlanMeasureManage> ids = getIds(delIdList);
        for (int i = 0; i < ids.size(); i++) {
            SgjsPlanMeasureManage info=new SgjsPlanMeasureManage();
            info.setId(ids.get(i).getId());
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
