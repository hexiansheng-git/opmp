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
    @Transactional
    @Override
    public SgjsExperProgressManageVo sync() {

        //sgjsExperProgressManageMapper.deleteAll();

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
        if (!CollectionUtils.isEmpty(list)){
            digui(list, treeToList);
        }

        //设置返回值
        sgjsExperProgressManageVo.setTreeList(treeToList);
        SgjsExperProgressManage sgjsExperProgressManage=new SgjsExperProgressManage();
        sgjsExperProgressManage.setUpdateTime(DateTime.now());
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsExperProgressManageMapper.deleteAll();
        return sgjsExperProgressManageVo;
    }

    private void digui(List<LinkedHashMap<String, Object>> list, List<SgjsExperProgressManage> treeToList) {

        //遍历集合填充数据
        for (LinkedHashMap<String, Object> map : list) {
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setExperimentalWorkItems(map.get("workItem") == null ? null : (String) map.get("workItem"));
            sgjsExperProgressManage.setMeasureUnit(map.get("unit") == null ? null : (String) map.get("unit"));
            sgjsExperProgressManage.setWorkload(map.get("workload") == null ? null :  map.get("workload").toString());
            sgjsExperProgressManage.setPlanStartDate(map.get("planBeginDate") == null ? null : FtDateUtils.parseDate(map.get("planBeginDate")));
            sgjsExperProgressManage.setPlanEndDate(map.get("planEndDate") == null ? null : FtDateUtils.parseDate(map.get("planEndDate")));
            sgjsExperProgressManage.setDataSource("1");
            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setPid(0L);
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

        //删除操作
        delete(sgjsExperProgressManageVo);

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

    private void handleUpdate(List<SgjsExperProgressManage> treeToList) {
        //批量编辑
        List<SgjsExperProgressManage> updateList = treeToList.stream().filter(p -> StringUtils.isNotEmpty(p.getType()) && (!p.getType().equals("0"))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(updateList)) {
            List<SgjsExperProgressManage> newUpdateList = new ArrayList<>();
            for (int i = 0; i < updateList.size(); i++) {
                SgjsExperProgressManage sgjsExperProgressManage = updateList.get(i);
                sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserId() + "");
                sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
                sgjsExperProgressManage.setDelFlag("0");
                newUpdateList.add(sgjsExperProgressManage);
                //处理子节点
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
                //处理子节点
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

    /**
     * 递归删除
     * @param sgjsExperProgressManageVo
     */
    private void delete(SgjsExperProgressManageVo sgjsExperProgressManageVo) {
        //获取删除的id集合
        List<String> delIdList = sgjsExperProgressManageVo.getDelIdList();
        //将集合转成long类型的集合
        if (delIdList.size() > 0) {
            List<Long> collect = delIdList.stream().map(i -> Long.valueOf(i)).collect(Collectors.toList());
            if (collect.size() > 0) {
                String delUser = SecurityUtils.getSysUser().getNickName();
                sgjsExperProgressManageMapper.deleteSgjsExperProgressManageByPks(collect, delUser);
                //处理所有数据，找出父子关系
                List<Long> out = handleTotalData(collect);
                if (out.size() > 0) {
                    String delUser1 = SecurityUtils.getSysUser().getNickName();
                    sgjsExperProgressManageMapper.deleteSgjsExperProgressManageByPks(out, delUser1);
                }
            }
        }
    }

    /**
     * 传入父级数据，返回所有子父级数据的id集合
     * @param collect
     * @return
     */
    private List<Long> handleTotalData(List<Long> collect) {
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
        return out;
    }

    /**
     * 获取子级数据
     * @param map
     * @param pidMap
     * @param children
     * @param out
     * @return
     */
    private List<Long> getChildren(Map<String, SgjsExperProgressManage> map, Map<String, SgjsExperProgressManage> pidMap, List<SgjsExperProgressManage> children, List<Long> out) {
        //筛选出所有子级id
        List<Long> collectId = children.stream().map(i -> i.getId()).collect(Collectors.toList());

        for (Long id : collectId) {
            //将子节点加入集合
            out.add(id);
            //获取子节点的孩子节点
            if (pidMap.containsKey(id)) {
                out.add(pidMap.get(id.toString()).getId());
                getChildren(map, pidMap, children, out);
            }
        }
        return out;
    }


    /**
     * 选中导出，选中父级也需要导出子级
     * @param ids
     * @return
     */
    @Override
    public List<SgjsExperProgressManage> getIds(List<Long> ids) {

        //所有子父级数据
        List<SgjsExperProgressManage> total = new ArrayList<>();
        //获取父级数据
        List<SgjsExperProgressManage> list = sgjsExperProgressManageMapper.getIds(ids);
        total.addAll(list);

        //获取子级所有数据
        List<Long> out = handleTotalData(ids);
        //查询数据
        List<SgjsExperProgressManage> sgjsExperProgressManages = sgjsExperProgressManageMapper.getIds(out);
        total.addAll(sgjsExperProgressManages);

        return total;
    }
}
