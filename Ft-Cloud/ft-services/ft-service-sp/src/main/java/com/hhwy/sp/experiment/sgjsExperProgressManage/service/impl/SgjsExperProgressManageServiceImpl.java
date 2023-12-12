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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark
 */
@Service
public class SgjsExperProgressManageServiceImpl implements ISgjsExperProgressManageService {

    @Autowired
    private SgjsExperProgressManageMapper sgjsExperProgressManageMapper;

    @Autowired
    private PmServiceApi pmServiceApi;

    private static final Logger logger = LoggerFactory.getLogger(SgjsExperProgressManageServiceImpl.class);


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
        digui(list,treeToList);
        //设置返回值
        sgjsExperProgressManageVo.setTreeList(treeToList);

        return sgjsExperProgressManageVo;
    }

    private void digui(List<LinkedHashMap<String, Object>> list, List<SgjsExperProgressManage> treeToList) {

        //遍历集合填充数据
        for (LinkedHashMap<String, Object> map : list) {
            SgjsExperProgressManage sgjsExperProgressManage = new SgjsExperProgressManage();
            sgjsExperProgressManage.setExperimentalWorkItems(map.get("workItem") == null ? null : (String) map.get("workItem"));
            sgjsExperProgressManage.setMeasureUnit(map.get("unit") == null ? null : (String) map.get("unit"));
            sgjsExperProgressManage.setWorkload(map.get("workload") == null ? null : (Integer) map.get("workload"));
            sgjsExperProgressManage.setPlanStartDate(map.get("planBeginDate") == null ? null : FtDateUtils.parseDate(map.get("planbeginDate")));
            sgjsExperProgressManage.setPlanEndDate(map.get("planEndDate") == null ? null : FtDateUtils.parseDate(map.get("planEndDate")));
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

        List<SgjsExperProgressManage> treeToList = null;
        if (CollectionUtils.isEmpty(sgjsExperProgressManageVo.getTreeList())) {
            return AjaxResult.error("数据异常");
        }
        //删除库中所有数据
        SgjsExperProgressManage info = new SgjsExperProgressManage();
        info.setUpdateTime(DateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId() + "");
        sgjsExperProgressManageMapper.deleteAll(info);
        //数据处理
        treeToList = TreeUtil.treeToList(sgjsExperProgressManageVo.getTreeList());
        for (int i = 0; i < treeToList.size(); i++) {
            SgjsExperProgressManage sgjsExperProgressManage = treeToList.get(i);
            sgjsExperProgressManage.setCreateTime(DateTime.now());
            sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserId() + "");
            sgjsExperProgressManage.setCreateUserName(SecurityUtils.getUserName() + "");
        }
        sgjsExperProgressManageMapper.insertSgjsExperProgressManageList(treeToList);
        return AjaxResult.success();

    }
}
