package com.hhwy.sp.experiment.sgjsExperProgressManage.service.impl;


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

import java.util.*;
import java.util.stream.Collectors;

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
        List<SgjsExperProgressManage> list = sgjsExperProgressManageMapper.getSgjsExperProgressManageListByCondition(sgjsTechnicalManage);
        for (SgjsExperProgressManage sgjsExperProgressManage : list) {
            sgjsExperProgressManage.setLeaf(sgjsExperProgressManage.getPtVar2());
        }
        vo.setTreeList(TreeUtil.newBuild(list));
        return vo;
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


    /**
     * 同步前期策划的数据
     *
     * @return
     */
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
        digui(list, treeToList);
        //设置返回值
        sgjsExperProgressManageVo.setTreeList(treeToList);

        return sgjsExperProgressManageVo;
    }

    private void digui(List<LinkedHashMap<String, Object>> list, List<SgjsExperProgressManage> treeToList) {

        //遍历集合填充数据
        for (LinkedHashMap<String, Object> map : list) {
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setExperimentalWorkItems(map.get("workItem") == null ? null : (String) map.get("workItem"));
            sgjsExperProgressManage.setMeasureUnit(map.get("unit") == null ? null : (String) map.get("unit"));
            sgjsExperProgressManage.setWorkload(map.get("workload") == null ? null : (Integer) map.get("workload"));
            sgjsExperProgressManage.setPlanStartDate(map.get("planBeginDate") == null ? null : FtDateUtils.parseDate(map.get("planBeginDate")));
            sgjsExperProgressManage.setPlanEndDate(map.get("planEndDate") == null ? null : FtDateUtils.parseDate(map.get("planEndDate")));
            sgjsExperProgressManage.setDataSource("1");
            //所有父节点的pid都设置为0
            sgjsExperProgressManage.setPid(map.get("pid") == null ? 0L : Long.parseLong((String) map.get("pid")));
            treeToList.add(sgjsExperProgressManage);
            //递归遍历子节点
            List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) map.get("children");
            if (children.size() > 0) {
                digui(children, treeToList);
            }
        }


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

        //获取删除的id集合
        List<String> delIdList = sgjsExperProgressManageVo.getDelIdList();
        //将集合转成long类型的集合
        List<Long> collect = delIdList.stream().map(t -> Long.parseLong(t)).collect(Collectors.toList());

        if (collect.size() > 0) {
            String delUser = SecurityUtils.getSysUser().getNickName();
            sgjsExperProgressManageMapper.deleteSgjsExperProgressManageByPks(collect, delUser);

            //将所有数据都查询出来 用map保存
            SgjsExperProgressManage sgjsExperProgressManage1 = new SgjsExperProgressManage();
            List<SgjsExperProgressManage> list = sgjsExperProgressManageMapper.getSgjsExperProgressManageList(sgjsExperProgressManage1);
            Map<String, SgjsExperProgressManage> map = new HashMap<>();
            //将所有数据分别放入两个map中，一个以id为key,另一个以pid为key, value都是实体对象
            list.stream().forEach(temp -> {
                map.put(temp.getId() + "", temp);
            });

            Map<String, SgjsExperProgressManage> pidMap = new HashMap<>();
            list.stream().forEach(temp -> {
                pidMap.put(temp.getPid() + "", temp);
            });

            //处理子级数据
            List<SgjsExperProgressManage> children = sgjsExperProgressManageMapper.getChildrenList(collect);
            //out集合存放所有需要删除的数据
            List<Long> out = new ArrayList<>();
            out = getChildren(map, pidMap, children, out);

            if (out.size() > 0) {
                String delUser1 = SecurityUtils.getSysUser().getNickName();
                sgjsExperProgressManageMapper.deleteSgjsExperProgressManageByPks(out, delUser1);
            }
        }

        //根据标志位判断是新增操作还是修改操作
        List<SgjsExperProgressManage> treeList = sgjsExperProgressManageVo.getTreeList();
        List<SgjsExperProgressManage> updateList = new ArrayList<>();
        List<SgjsExperProgressManage> insertList = new ArrayList<>();
        for (SgjsExperProgressManage sgjsExperProgressManage : treeList) {

            //处理新增数据
            if ("0".equals(sgjsExperProgressManage.getType())) {
                sgjsExperProgressManage.setCreateUserName(SecurityUtils.getUserName());
                sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserId() + "");
                sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
                sgjsExperProgressManage.setId(IdWorker.createId());
                sgjsExperProgressManage.setDelFlag("0");
                sgjsExperProgressManage.setDataSource("0");
                insertList.add(sgjsExperProgressManage);
            }else {
                sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserId() + "");
                sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
                sgjsExperProgressManage.setDelFlag("0");
                updateList.add(sgjsExperProgressManage);
            }
        }
        //批量进行修改和新增
        if (insertList.size() > 0) {
            sgjsExperProgressManageMapper.insertSgjsExperProgressManageList(insertList);
        }
        if (updateList.size() > 0) {
            sgjsExperProgressManageMapper.updateSgjsExperProgressManageList(updateList);
        }

        return AjaxResult.success();




    }

    private List<Long> getChildren(Map<String, SgjsExperProgressManage> map,Map<String, SgjsExperProgressManage> pidMap, List<SgjsExperProgressManage> children, List<Long> out) {
        //筛选出所有子级id
        List<Long> collectId = children.stream().map(i -> i.getId()).collect(Collectors.toList());

        for (Long id : collectId) {
            //将子节点加入集合
            out.add(id);
            //获取子节点的孩子节点
            if (pidMap.containsKey(id)){
                out.add(pidMap.get(id.toString()).getId());
                getChildren(map,pidMap,children,out);
            }
        }
        return out;
    }



    @Override
    public List<SgjsExperProgressManage> getIds(List<Long> ids) {
        return sgjsExperProgressManageMapper.getIds(ids);
    }
}
