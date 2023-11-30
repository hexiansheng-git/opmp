package com.hhwy.sp.techOrg.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageVo;
import com.hhwy.sp.techOrg.mapper.SgjsTechnicalManageInfoMapper;
import com.hhwy.sp.techOrg.mapper.SgjsTechnicalManageMapper;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lcf
 * @date 2023-11-17 11:29:23
 * @remark
 */
@Service
public class SgjsTechnicalManageServiceImpl implements ISgjsTechnicalManageService{

    @Autowired
    private SgjsTechnicalManageMapper sgjsTechnicalManageMapper;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private SgjsTechnicalManageInfoMapper sgjsTechnicalManageInfoMapper;

    private static final Logger logger= LoggerFactory.getLogger(SgjsTechnicalManageServiceImpl.class);



    public SgjsTechnicalManage getSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        return sgjsTechnicalManageMapper.getSgjsTechnicalManage(sgjsTechnicalManage);
    }

    public List<SgjsTechnicalManage> getSgjsTechnicalManageList(SgjsTechnicalManage sgjsTechnicalManage) {
        return sgjsTechnicalManageMapper.getSgjsTechnicalManageList(sgjsTechnicalManage);
    }

    @Override
    public SgjsTechnicalManageVo list(SgjsTechnicalManage sgjsTechnicalManage) {
        SgjsTechnicalManageVo vo =new SgjsTechnicalManageVo();
        //筛选条件
        if(StringUtils.isNotEmpty(sgjsTechnicalManage.getActualDateStr())){
            String actualDateStr = sgjsTechnicalManage.getActualDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsTechnicalManage.setActualDateBegin(FtDateUtils.parseDate(begin));
            sgjsTechnicalManage.setActualDateEnd(FtDateUtils.parseDate(end));
        }
        List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.getSgjsTechnicalManageList(sgjsTechnicalManage);
        //字典项处理
        AjaxResult result = systemServiceApi.dictType(DictType.WORK_OR_NOT);
        List<Map<String,Object>> dictDataList=null;
        if(result.get("code").toString().equals(Constant.SUCCESS_CODE)){
            dictDataList= (List<Map<String, Object>>) result.get("data");
        }
        List<Map<String, Object>> oneList = dictDataList.stream().filter(e -> e.get("dictValue").equals("0")).collect(Collectors.toList());
        List<Map<String, Object>> zeroList = dictDataList.stream().filter(e -> e.get("dictValue").equals("1")).collect(Collectors.toList());
        for (SgjsTechnicalManage info:list) {
            info.setActualDateStr(FtDateUtils.formatDate(info.getActualDate()));
            String one = (String)oneList.get(0).get("dictValue");
            String zero = (String)zeroList.get(0).get("dictValue");
            String workOrNot = info.getWorkOrNot();
            if(StringUtils.isEmpty(workOrNot)){
                continue;
            }
            if(workOrNot.equals(one)){
                String oneDictLabel = (String)oneList.get(0).get("dictLabel");
                info.setWorkOrNot(oneDictLabel);
            }
            if(workOrNot.equals(zero)){
                String zeroDictLabel = (String)zeroList.get(0).get("dictLabel");
                info.setWorkOrNot(zeroDictLabel);
            }
            info.setLeaf(info.getPtVar2());
        }
        vo.setTreeList(TreeUtil.build(list, 0L));
        return vo;
    }

    @Transactional
    public int insertSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setId(IdWorker.createId());
        sgjsTechnicalManage.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setCreateTime(DateUtils.getNowDate());
        String actualDateStr = sgjsTechnicalManage.getActualDateStr();
        if(StringUtils.isNotEmpty(actualDateStr)){
            String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
            Date date = FtDateUtils.parseDate(str);
            sgjsTechnicalManage.setActualDate(date);
        }
        return sgjsTechnicalManageMapper.insertSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int insertSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList) {
        for (SgjsTechnicalManage sgjsTechnicalManage : sgjsTechnicalManageList) {
            sgjsTechnicalManage.setId(IdWorker.createId());
            sgjsTechnicalManage.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(sgjsTechnicalManageList);
    }

    @Transactional
    public int updateSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageMapper.updateSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int updateSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList) {
        for (SgjsTechnicalManage sgjsTechnicalManage : sgjsTechnicalManageList) {
            sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalManageMapper.updateSgjsTechnicalManageList(sgjsTechnicalManageList);
    }

    @Transactional
    public int deleteSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageMapper.deleteSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int deleteSgjsTechnicalManageByPks(List<Long> sgjsTechnicalManagePkList) {
        //批量查詢刪除
        List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.batchSelect(sgjsTechnicalManagePkList);
        List<Long> idList = list.stream().map(e -> e.getId()).collect(Collectors.toList());
        if(null==idList || idList.size()==0){
            logger.info("数据空了,未删除成功");
            return 0;
        }
        return sgjsTechnicalManageMapper.deleteSgjsTechnicalManageByPks(idList);
    }

    @Override
    @Transactional
    public AjaxResult batchAdd(SgjsTechnicalManageVo sgjsTechnicalManageVo) {
        List<SgjsTechnicalManage> treeToList=null;
        //数据校验
        AjaxResult result=validData(sgjsTechnicalManageVo.getTreeList());
        if(result.get("code").toString().equals("200")){
            treeToList=(List<SgjsTechnicalManage>)result.get("data");
        }else {
            return result;
        }
        //获取项目信息
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        if(CollectionUtils.isEmpty(prjInfo)){
            return AjaxResult.error("获取项目信息异常");
        }
        //删除库中所有数据
        SgjsTechnicalManage info=new SgjsTechnicalManage();
        info.setUpdateTime(DateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsTechnicalManageMapper.delectAll(info);
        if(CollectionUtils.isEmpty(sgjsTechnicalManageVo.getTreeList())){
            return AjaxResult.error("数据异常");
        }
        //数据处理
        treeToList= TreeUtil.treeToList(sgjsTechnicalManageVo.getTreeList());
        for (int i = 0; i < treeToList.size(); i++) {
            SgjsTechnicalManage manage = treeToList.get(i);
            String actualDateStr = manage.getActualDateStr();
            if(StringUtils.isNotEmpty(actualDateStr)){
                String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
                Date date = FtDateUtils.parseDate(str);
                manage.setActualDate(date);
            }
            Long projectId = Long.parseLong(prjInfo.get("projectId")+"");
            manage.setProjectId(projectId);
            manage.setProjectName((String) prjInfo.get("projectName"));
            manage.setPtVar1((String)prjInfo.get("projectCode"));
        }
        //入库
        sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(treeToList);
        //处理离场/进场记录
        List<String> delIdList = sgjsTechnicalManageVo.getDelIdList();
        if(!CollectionUtils.isEmpty(delIdList)){
            //传过来的可能是根节点id   查询根节点下左右子节点id
            List<Long> idLtr=delIdList.stream().map(Long::valueOf).collect(Collectors.toList());
            List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.batchSelect(idLtr);
            List<String> idList = list.stream().map(e -> e.getId()+"").collect(Collectors.toList());
            int i = sgjsTechnicalManageInfoMapper.deleteInfoByTechIds(idList);
            logger.info("子表数据删除记录--->【{}】",i);
        }
        //同步总部数据
        syncDataToGm(treeToList);
        return AjaxResult.success();
    }

    /**
     * 数据保存校验
     *
     * @param list
     * @return
     */
    private AjaxResult validData(List<SgjsTechnicalManage>list) {
        List<String> msgList=new ArrayList<>();
        validDataDigui(list, msgList);
        if(CollectionUtils.isEmpty(msgList)){
            return AjaxResult.success(list);
        }
        String msg = StringUtils.join(msgList, ",");
        return AjaxResult.error(msg);
    }

   private void validDataDigui(List<SgjsTechnicalManage>list,List<String> msgList){
       for (SgjsTechnicalManage info:list ) {
           Integer headCount = info.getHeadCount();
           List<SgjsTechnicalManage> children = info.getChildren();
           //headCount的量  校验实际进场和人员姓名
           if(headCount==children.size()){
               for (int i = 0; i < children.size(); i++) {
                   if(null!=children.get(i).getHeadCount() && children.get(i).getHeadCount()>0){
                       //实际日期
                       String actualDateStr = children.get(i).getActualDateStr();
                       if(StringUtils.isEmpty(actualDateStr)){
                           msgList.add(info.getPostName()+"实际进场不能为空");
                       }
                       String userName = children.get(i).getUserName();
                       if(StringUtils.isEmpty(userName)){
                           msgList.add(info.getPostName()+"人员姓名不能为空");
                       }
                   }
               }
           }else{

           }

           if(!CollectionUtils.isEmpty(info.getChildren())){
               validDataDigui(info.getChildren(),msgList);
           }
       }
    }

    @Override
    public AjaxResult sync() {
        List<QqchPostSetting> list = pmServiceApi.getTechDeptList();
        //递归处理
        digui(list);
        return AjaxResult.success(list);
    }

    /**
     * 总部版数据同步
     *
     * @param treeToList
     * @return
     */
    @Override
    public void syncDataToGm(List<SgjsTechnicalManage> treeToList) {
        List<Long> techIdList=new ArrayList<>();
        diguiTechTree(techIdList,treeToList);
        //根据techId 批量查询进/离场记录
        SgjsTechnicalManageInfo info=new SgjsTechnicalManageInfo();
        info.setTechIdList(techIdList);
        List<SgjsTechnicalManageInfo> infoList = sgjsTechnicalManageInfoMapper.getSgjsTechnicalManageInfoList(info);
        // 主表、子表数据一起同步
        Map<String,Object> map=new HashMap<>();
        map.put("techList",treeToList);
        map.put("infoList",infoList);
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try {
            rocketMQTemplate.convertAndSend("sgjs_technical_group1:tenantSuccess", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            //3、更新syncInfo
            String ids = treeToList.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_technical_insert");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(ids);
            logger.error("sgjs_technical_insert同步失败【{}】,时间：【{}】",ids,System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    /**
     * 递归取id 查进/离场数据
     *
     * @param idList
     * @param treeToList
     */
    void diguiTechTree(List<Long> idList,List<SgjsTechnicalManage> treeToList){
        for (SgjsTechnicalManage info :treeToList) {
            Long id = info.getId();
            idList.add(id);
            if(!CollectionUtils.isEmpty(info.getChildren())){
                diguiTechTree(idList,treeToList);
            }
        }

    }


    /**
     * 递归前期策划
     *
     * @param list
     */
    private void digui(List<QqchPostSetting> list){
        for (QqchPostSetting info:list) {
            //技术部门+技术岗位=岗位
            String str="";
            if(!StringUtils.isEmpty(info.getTechDept()) && !StringUtils.isEmpty(info.getPostName())){
                str=info.getTechDept()+info.getPostName();
            }
            if(StringUtils.isEmpty(info.getTechDept())){
                str=info.getPostName();
            }
            if(StringUtils.isEmpty(info.getPostName())){
                str=info.getTechDept();
            }
            if(!StringUtils.isEmpty(str)){
                info.setPostName(str);
            }
            if(!CollectionUtils.isEmpty(info.getChildren())){
                digui(info.getChildren());
            }
        }
    }
}
