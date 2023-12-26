package com.hhwy.sp.experiment.sgjsExperProgressManage.service.impl;


import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import com.hhwy.sp.experiment.sgjsExperProgressManage.mapper.SgjsExperProgressManageMapper;
import com.hhwy.sp.experiment.sgjsExperProgressManage.service.ISgjsExperProgressManageService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark
 */
@Slf4j
@Service
public class SgjsExperProgressManageServiceImpl implements ISgjsExperProgressManageService {

    @Autowired
    private SgjsExperProgressManageMapper sgjsExperProgressManageMapper;

    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        return sgjsExperProgressManageMapper.getSgjsExperProgressManage(sgjsExperProgressManage);
    }


    public List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage) {

        return sgjsExperProgressManageMapper.getSgjsExperProgressManageList(sgjsExperProgressManage);

    }

    /**
     * 列表查询 条件查询
     *
     * @param sgjsTechnicalManage
     * @return
     */
    @Override
    public SgjsExperProgressManageVo list(SgjsExperProgressManage sgjsTechnicalManage) {

        SgjsExperProgressManageVo vo = new SgjsExperProgressManageVo();
        //筛选条件  试验工作项  计划开始日期  实际开始日期
        //接收时间范围的字符串 处理之后赋值给对象属性
        if (StringUtils.isNotEmpty(sgjsTechnicalManage.getPlanStartDateStr())) {
            String planStartDateStr = sgjsTechnicalManage.getPlanStartDateStr();
            String[] split = planStartDateStr.split("~");
            sgjsTechnicalManage.setPlanStartDate1(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            sgjsTechnicalManage.setPlanStartDate2(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }

        if (StringUtils.isNotEmpty(sgjsTechnicalManage.getRealStartDateStr())) {
            String realStartDateStr = sgjsTechnicalManage.getRealStartDateStr();
            String[] split = realStartDateStr.split("~");
            sgjsTechnicalManage.setRealStartDate1(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            sgjsTechnicalManage.setRealStartDate2(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }

        //查询符合条件的数据
        List<SgjsExperProgressManage> sgjsExperProgressManageList = sgjsExperProgressManageMapper.getSgjsExperProgressManageListByCondition(sgjsTechnicalManage);

        List<SgjsExperProgressManage> list = new ArrayList<>();
        if (sgjsExperProgressManageList.size() > 0) {

            List<SgjsExperProgressManage> list1 = sgjsExperProgressManageList.stream().filter(p -> StringUtils.isNotEmpty(p.getPid().toString()) && !p.getPid().toString().equals("0")).collect(Collectors.toList());
            if (list1.size() > 0) {
                List<String> data = new ArrayList<>();
                SgjsExperProgressManage sgjsPlanMeasureManage1 = new SgjsExperProgressManage();
                for (int i = 0; i < list1.size(); i++) {
                    if (StringUtils.isEmpty(list1.get(i).getPath())) {
                        continue;
                    }
                    if (list1.get(i).getPath().contains("/")) {
                        String[] split = list1.get(i).getPath().split("/");
                        List allPath = Arrays.asList(split);
                        data.addAll(allPath);
                    } else {
                        data.add(list1.get(i).getPath());
                    }
                }
                sgjsPlanMeasureManage1.setPaths(data);
                List<SgjsExperProgressManage> sgjsExperProgressManages = sgjsExperProgressManageMapper.getSgjsExperProgressManageListByCondition(sgjsPlanMeasureManage1);
                sgjsExperProgressManageList.addAll(sgjsExperProgressManages);
            }
            List<SgjsExperProgressManage> collect = sgjsExperProgressManageList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SgjsExperProgressManage::getId))), ArrayList::new));
            list = collect.stream().sorted(Comparator.comparing(SgjsExperProgressManage::getSerialNumber)).collect(Collectors.toList());
        }
        vo.setTreeList(TreeUtil.newBuild(list));
        return vo;
    }


    /**
     * 同步前期策划的数据
     *
     * @return
     */
    @Transactional
    @Override
    public SgjsExperProgressManageVo sync() {


        SgjsExperProgressManageVo sgjsExperProgressManageVo = new SgjsExperProgressManageVo();
        List<SgjsExperProgressManage> treeToList = new ArrayList<>();
        //远程调用 前期策划的3.7.2同步数据试验计划的数据
        AjaxResult ajaxResult = pmServiceApi.feignPlanList();

        if (!ajaxResult.get("code").toString().equals(Constant.SUCCESS_CODE)) {
            throw new BaseException("同步前期策划数据失败");
        }
        //获取返回值数据，用map封装
        Map<String, Object> data = (Map<String, Object>) ajaxResult.get("data");
        List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) data.get("dto");

        //递归处理同步数据，构建树形关系
        if (!CollectionUtils.isEmpty(list)) {
            digui(list, treeToList);
        }

        //设置返回值
        sgjsExperProgressManageVo.setTreeList(treeToList);
        SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
        sgjsExperProgressManage.setUpdateTime(DateTime.now());
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserId() + "");
        //覆盖数据
        sgjsExperProgressManageMapper.deleteAll();
        return sgjsExperProgressManageVo;
    }

    private void digui(List<LinkedHashMap<String, Object>> list, List<SgjsExperProgressManage> treeToList) {

        //遍历集合填充数据
        for (LinkedHashMap<String, Object> map : list) {
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setExperimentalWorkItems(map.get("workItem") == null ? null : (String) map.get("workItem"));
            sgjsExperProgressManage.setMeasureUnit(map.get("unit") == null ? null : (String) map.get("unit"));
            sgjsExperProgressManage.setWorkload(map.get("workload") == null ? null : map.get("workload").toString());
            sgjsExperProgressManage.setPlanStartDate(map.get("planBeginDate") == null ? null : FtDateUtils.parseDate(map.get("planBeginDate")));
            sgjsExperProgressManage.setPlanEndDate(map.get("planEndDate") == null ? null : FtDateUtils.parseDate(map.get("planEndDate")));
            sgjsExperProgressManage.setRemark(map.get("remark") == null ? null : map.get("remark").toString());
            sgjsExperProgressManage.setDataSource("1");
            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setPid(0L);
            sgjsExperProgressManage.setType("0");
            sgjsExperProgressManage.setSyncId(Long.parseLong(map.get("id").toString()));
            //递归遍历子节点
            List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) map.get("children");
            if (children.size() > 0) {
                diguiChildren(children, sgjsExperProgressManage);
            }
            treeToList.add(sgjsExperProgressManage);
        }

    }

    private void diguiChildren(List<LinkedHashMap<String, Object>> children, SgjsExperProgressManage manage) {

        List<SgjsExperProgressManage> sgjsExperProgressManageList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setExperimentalWorkItems(children.get(i).get("workItem") == null ? null : children.get(i).get("workItem").toString());
            sgjsExperProgressManage.setMeasureUnit(children.get(i).get("unit") == null ? null : children.get(i).get("unit").toString());
            sgjsExperProgressManage.setWorkload(children.get(i).get("workload") == null ? null : children.get(i).get("workload").toString());
            sgjsExperProgressManage.setPlanStartDate(children.get(i).get("planBeginDate") == null ? null : FtDateUtils.parseDate(children.get(i).get("planBeginDate")));
            sgjsExperProgressManage.setPlanEndDate(children.get(i).get("planEndDate") == null ? null : FtDateUtils.parseDate(children.get(i).get("planEndDate")));
            sgjsExperProgressManage.setRemark(children.get(i).get("remark") == null ? null : children.get(i).get("remark").toString());

            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setPid(manage.getId());
            sgjsExperProgressManage.setSyncId(Long.parseLong(children.get(i).get("id").toString()));

            //同步标识
            sgjsExperProgressManage.setDataSource("1");
            sgjsExperProgressManage.setType("0");
            List<LinkedHashMap<String, Object>> children1 = (List<LinkedHashMap<String, Object>>) children.get(i).get("children");
            if (children1.size() > 0) {
                diguiChildren(children1, sgjsExperProgressManage);
            }
            sgjsExperProgressManageList.add(sgjsExperProgressManage);
        }
        manage.setChildren(sgjsExperProgressManageList);
    }


    /**
     * 批量新增
     *
     * @param sgjsExperProgressManageVo
     * @return
     */
    @Override
    @Transactional
    public AjaxResult batchAdd(SgjsExperProgressManageVo sgjsExperProgressManageVo) {


        //处理删除数据
        if (!CollectionUtils.isEmpty(sgjsExperProgressManageVo.getDelIdList())) {
            deleteByIds(sgjsExperProgressManageVo.getDelIdList());
        }

        List<SgjsExperProgressManage> treeToList = null;
        if (!CollectionUtils.isEmpty(sgjsExperProgressManageVo.getTreeList())) {
            treeToList = TreeUtil.treeToListWithoutId(sgjsExperProgressManageVo.getTreeList());
            //处理新增数据
            handleInsertList(treeToList);
            //处理更新的数据
            handleUpdate(treeToList);
        }
        return AjaxResult.success();
    }


    /**
     * 批量删除
     *
     * @param delIdList
     */
    public void deleteByIds(List<String> delIdList) {
        List<SgjsExperProgressManage> list = new ArrayList<>();
        //获取所有父子级数据
        List<SgjsExperProgressManage> ids = getIds(delIdList);
        for (int i = 0; i < ids.size(); i++) {
            SgjsExperProgressManage info = new SgjsExperProgressManage();
            info.setId(ids.get(i).getId());
            info.setUpdateUser(SecurityUtils.getUserId() + "");
            info.setUpdateTime(DateUtils.getNowDate());
            info.setDelFlag("1");
            list.add(info);
        }
        //删除
        if (!CollectionUtils.isEmpty(list)) {
            sgjsExperProgressManageMapper.deleteInfoData(list);
        }
    }





    /**
     * 获取传入集合的所有子级数据(包括自身)
     *
     * @param ids
     * @return
     */
    @Override
    public List<SgjsExperProgressManage> getIds(List<String> ids) {

        //查询出所有数据
        SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
        List<SgjsExperProgressManage> manageList = sgjsExperProgressManageMapper.getSgjsExperProgressManageListByCondition(sgjsExperProgressManage);
        Map<String, SgjsExperProgressManage> map = new HashMap<>();
        //将所有数据放进集合，id为key,对象为value
        manageList.stream().forEach(temp -> {
            map.put(temp.getId() + "", temp);
        });

        //该集合存放所有父子级数据
        List<SgjsExperProgressManage> total = new ArrayList<>();

        //查询所有符合条件的数据
        List<SgjsExperProgressManage> list = sgjsExperProgressManageMapper.getIds(ids);
        total.addAll(list);
        for (SgjsExperProgressManage experProgressManage : list) {
            findTotal(map, total, experProgressManage);
        }

        if (total.size() > 0) {
            total = TreeUtil.treeToListWithoutId(total);
            total = total.stream().distinct().sorted(Comparator.comparing(SgjsExperProgressManage::getSerialNumber)).collect(Collectors.toList());
        }

        return total;


        /*List<SgjsExperProgressManage> list = new ArrayList<>();
        List<SgjsExperProgressManage> list1 = sgjsExperProgressManageMapper.getIds(ids);
        for (int i = 0; i < list1.size(); i++) {

            //找到孩子节点
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setPid(list1.get(i).getId());
            List<SgjsExperProgressManage> list2 = sgjsExperProgressManageMapper.getSgjsExperProgressManageListByCondition(sgjsExperProgressManage);
            if (list2.size() > 0) {
                diguiList2(list2, list1.get(i));
            }
            list.add(list1.get(i));
        }
        if (list.size() > 0) {
            list = TreeUtil.treeToListWithoutId(list);
            list = list.stream().distinct().sorted(Comparator.comparing(SgjsExperProgressManage::getSerialNumber)).collect(Collectors.toList());
        }
        return list;*/
    }

    public void findTotal(Map<String, SgjsExperProgressManage> map, List<SgjsExperProgressManage> total, SgjsExperProgressManage experProgressManage) {
        //查找以当前数据的id为pid的数据
        Set<Map.Entry<String, SgjsExperProgressManage>> entries = map.entrySet();
        for (Map.Entry<String, SgjsExperProgressManage> entry : entries) {
            if (entry.getValue().getPid().equals(experProgressManage.getId())){
                SgjsExperProgressManage sgjsExperProgressManage = map.get(entry.getKey());
                total.add(sgjsExperProgressManage);
              findTotal(map,total,sgjsExperProgressManage);
            }
        }
    }



    /*private void diguiList2(List<SgjsExperProgressManage> list2, SgjsExperProgressManage manage) {
        List<SgjsExperProgressManage> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            SgjsExperProgressManage planMeasureManage = new SgjsExperProgressManage();
            planMeasureManage.setPid(list2.get(i).getId());
            List<SgjsExperProgressManage> list3 = sgjsExperProgressManageMapper.getSgjsExperProgressManageList(planMeasureManage);
            if (list3.size() > 0) {
                diguiList2(list3, list2.get(i));
            }
            list.add(list2.get(i));
        }
        manage.setChildren(list);
    }*/





    private void handleUpdate(List<SgjsExperProgressManage> treeToList) {
        //批量编辑
        List<SgjsExperProgressManage> updateList = treeToList.stream().filter(p -> (StringUtils.isEmpty(p.getType()) || StringUtils.isNotEmpty(p.getType()) && p.getType().equals("1"))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(updateList)) {
            List<SgjsExperProgressManage> newUpdateList = new ArrayList<>();
            for (int i = 0; i < updateList.size(); i++) {
                SgjsExperProgressManage sgjsExperProgressManage = updateList.get(i);
                sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserId() + "");
                sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
                sgjsExperProgressManage.setDelFlag("0");
                newUpdateList.add(sgjsExperProgressManage);
                //处理子节点，将当前数据的子节点加入集合
                handleChildren(newUpdateList, sgjsExperProgressManage);
            }
            sgjsExperProgressManageMapper.updateSgjsExperProgressManageList(newUpdateList);
        }
    }

    private void handleInsertList(List<SgjsExperProgressManage> treeToList) {
        List<SgjsExperProgressManage> insertList = treeToList.stream().filter(p -> StringUtils.isNotEmpty(p.getType()) && p.getType().equals("0")).collect(Collectors.toList());
        //批量入库
        if (!CollectionUtils.isEmpty(insertList)) {
            List<SgjsExperProgressManage> newInsertList = new ArrayList<>();
            for (int i = 0; i < insertList.size(); i++) {
                SgjsExperProgressManage sgjsExperProgressManage = insertList.get(i);
                sgjsExperProgressManage.setCreateUserName(SecurityUtils.getUserName());
                sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserId() + "");
                sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
                sgjsExperProgressManage.setDelFlag("0");
                sgjsExperProgressManage.setDataSource("0");
                newInsertList.add(sgjsExperProgressManage);
                //处理子节点，将当前数据的子节点加入集合
                handleChildren(newInsertList, sgjsExperProgressManage);
            }
            sgjsExperProgressManageMapper.insertSgjsExperProgressManageList(newInsertList);
        }
    }

    //将子节点递归加入集合
    private void handleChildren(List<SgjsExperProgressManage> newList, SgjsExperProgressManage sgjsExperProgressManage) {
        List<SgjsExperProgressManage> children = sgjsExperProgressManage.getChildren();
        if (null != children) {
            for (SgjsExperProgressManage child : children) {
                newList.add(child);
                handleChildren(newList, sgjsExperProgressManage);
            }
        }

    }


    @Transactional
    public int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setId(IdWorker.createId());
        sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.insertSgjsExperProgressManage(sgjsExperProgressManage);
    }

    @Transactional
    public int insertSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList) {
        for (SgjsExperProgressManage sgjsExperProgressManage : sgjsExperProgressManageList) {
            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserName());
            sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperProgressManageMapper.insertSgjsExperProgressManageList(sgjsExperProgressManageList);
    }

    @Transactional
    public int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.updateSgjsExperProgressManage(sgjsExperProgressManage);
    }

    @Transactional
    public int updateSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList) {
        for (SgjsExperProgressManage sgjsExperProgressManage : sgjsExperProgressManageList) {
            sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperProgressManageMapper.updateSgjsExperProgressManageList(sgjsExperProgressManageList);
    }

    @Transactional
    public int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.deleteSgjsExperProgressManage(sgjsExperProgressManage);
    }

    @Override
    public int deleteSgjsExperProgressManageByPks(List<Long> sgjsExperProgressManagePkList) {
        return 0;
    }


}
