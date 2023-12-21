package com.hhwy.sp.techOrg.service.impl;

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
        //筛选条件  实际进场日期，包含开始时间和结束时间
        if(StringUtils.isNotEmpty(sgjsTechnicalManage.getActualDateStr())){
            String actualDateStr = sgjsTechnicalManage.getActualDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsTechnicalManage.setActualDateBegin(FtDateUtils.parseDate(begin));
            sgjsTechnicalManage.setActualDateEnd(FtDateUtils.parseDate(end));
        }
        List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.getSgjsTechnicalManageList(sgjsTechnicalManage);
        //字典项处理   根据字典项的类型查找字典项对应的值，然后设置给对应数据的属性
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

        //组合显示根节点
        List<SgjsTechnicalManage> handleData = handleData(list);
        List<SgjsTechnicalManage> manages =TreeUtil.newBuild(handleData) ;
        vo.setTreeList(manages);
        return vo;
    }

    /**
     * 查询子节点的根节点
     *
     * @param list
     * @return
     */
    public List<SgjsTechnicalManage> handleData(List<SgjsTechnicalManage> list){
        List<String> data=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String path = list.get(i).getPath();
            if(StringUtils.isEmpty(path)){
                continue;
            }
            if(path.contains("/")){
                String[] split = path.split("/");
                List allPath = Arrays.asList(split);
                data.addAll(allPath);
            }else{
                data.add(path);
            }
        }
        List<SgjsTechnicalManage> manageList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(data)){
            SgjsTechnicalManage info=new SgjsTechnicalManage();
            info.setPathList(data);
            manageList = sgjsTechnicalManageMapper.getSgjsTechnicalManageList(info);
            for (int i = 0; i < manageList.size(); i++) {
                SgjsTechnicalManage manage = manageList.get(i);
                manage.setActualDateStr(FtDateUtils.formatDate(manage.getActualDate()));
            }
        }
        return manageList;
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
        }
        else {
            return result;
        }
        //数据处理
        treeToList= TreeUtil.treeToListWithoutNewId(sgjsTechnicalManageVo.getTreeList());
        for (int i = 0; i < treeToList.size(); i++) {
            SgjsTechnicalManage manage = treeToList.get(i);
            String actualDateStr = manage.getActualDateStr();
            if(StringUtils.isNotEmpty(actualDateStr)){
                String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
                Date date = FtDateUtils.parseDate(str);
                manage.setActualDate(date);
            }
            manage.setUpdateTime(DateUtils.getNowDate());
            manage.setUpdateUser(SecurityUtils.getUserId()+"");
            manage.setCreateTime(DateUtils.getNowDate());
            manage.setCreateUser(SecurityUtils.getUserId()+"");
        }
        List<SgjsTechnicalManage> insertList = treeToList.stream().filter(e -> StringUtils.isNotEmpty(e.getType()) && e.getType().equals("1")).collect(Collectors.toList());

        //批量入库
        if(!CollectionUtils.isEmpty(insertList)){
            sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(insertList);
        }
        //批量编辑
        List<SgjsTechnicalManage> updateList = treeToList.stream().filter(e -> StringUtils.isEmpty(e.getType())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(updateList)){
            sgjsTechnicalManageMapper.updateSgjsTechnicalManageList(updateList);
        }
        //批量删除
        deleteByIds(sgjsTechnicalManageVo.getDelIdList());

        //处理离场/进场记录
        List<String> delIdList = sgjsTechnicalManageVo.getDelIdList();
        if(!CollectionUtils.isEmpty(delIdList)){
            //传过来的可能是根节点id   查询根节点下左右子节点id
            List<Long> idLtr=delIdList.stream().map(Long::valueOf).collect(Collectors.toList());
            List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.batchSelect(idLtr);
            List<String> idList = list.stream().map(e -> e.getId()+"").collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(idList)) {
                int i = sgjsTechnicalManageInfoMapper.deleteInfoByTechIds(idList);
                logger.info("子表数据删除记录--->【{}】",i);
            }
            logger.info("子表未删除。。。。。。。。。。");
        }
        //同步总部数据
       // syncDataToGm(treeToList);
        return AjaxResult.success();
    }


    /**
     * 批量删除
     * 并删除根节点下的子节点
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        if(CollectionUtils.isEmpty(delIdList)){
            logger.error("暂无需要删除的数据");
            return;
        }
        //	List<String>转List<Long>
        List<Long> rootIds = delIdList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        //查出根节点下子节点
        List<SgjsTechnicalManage> batchSelect = sgjsTechnicalManageMapper.batchSelect(rootIds);
        if(CollectionUtils.isEmpty(batchSelect)){
            return;
        }
        //取出删除id
        List<String> delList = batchSelect.stream().map(e -> e.getId() + "").collect(Collectors.toList());
        List<SgjsTechnicalManage> list =new ArrayList<>();
        for (int i = 0; i < delList.size(); i++) {
            SgjsTechnicalManage info=new SgjsTechnicalManage();
            info.setId(Long.parseLong(delList.get(i)));
            info.setUpdateUser(SecurityUtils.getUserId()+"");
            info.setUpdateTime(DateUtils.getNowDate());
            info.setDelFlag("1");
            list.add(info);
        }
        sgjsTechnicalManageMapper.deleteInfoData(list);
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
        Set set=new HashSet(msgList);
        String msg = StringUtils.join(set, ",");
        return AjaxResult.error(msg);
    }

    /**
     * 校验最后层级的用户姓名必填即可
     *
     * @param list
     * @param msgList
     */
    private void validDataDigui(List<SgjsTechnicalManage>list,List<String> msgList){
        for (SgjsTechnicalManage info:list ) {
            List<SgjsTechnicalManage> children = info.getChildren();
            if(0==children.size() || null==children){
                String userName = info.getUserName();
                if(StringUtils.isEmpty(userName)){
                    msgList.add(info.getPostName()+"人员姓名不能为空");
                }
            }else{
                String userName = children.get(children.size() - 1).getUserName();
                if(StringUtils.isEmpty(userName)){
                    msgList.add(children.get(children.size() - 1).getPostName()+"人员姓名不能为空");
                }
            }
            if(!CollectionUtils.isEmpty(info.getChildren())){
                validDataDigui(info.getChildren(),msgList);
            }
        }
    }

    @Override
    public AjaxResult sync() {
        List<QqchPostSetting> list = pmServiceApi.getTechDeptList();
        logger.info("同步结果数据：【{}】",list);
        List<SgjsTechnicalManage> techList=new ArrayList<>();
        //递归处理
        digui(list,techList,"");

        List<SgjsTechnicalManage> build = TreeUtil.build(techList,0L);
        //全量删库 并重新入库
        SgjsTechnicalManage info=new SgjsTechnicalManage();
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        info.setUpdateTime(DateUtils.getNowDate());
        sgjsTechnicalManageMapper.delectAll(info);
        SgjsTechnicalManageInfo manageInfo=new SgjsTechnicalManageInfo();
        manageInfo.setUpdateTime(DateUtils.getNowDate());
        manageInfo.setUpdateUser(SecurityUtils.getUserId()+"");
        //删完主表删子表
        sgjsTechnicalManageInfoMapper.delectAll(manageInfo);
        List<SgjsTechnicalManage> manageList = TreeUtil.treeToList(build);
        logger.info("主表数据--->【{}】",manageList);
        if(!CollectionUtils.isEmpty(manageList)){
            sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(manageList);
        }
        return AjaxResult.success(techList);
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
        Map<String,Object> map=new HashMap<>();
        if(!CollectionUtils.isEmpty(techIdList)){
            info.setTechIdList(techIdList);
            List<SgjsTechnicalManageInfo> infoList = sgjsTechnicalManageInfoMapper.getSgjsTechnicalManageInfoList(info);
            // 主表、子表数据一起同步
            map.put("infoList",infoList);
        }
        map.put("techList",treeToList);

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
    private void digui(List<QqchPostSetting> list,List<SgjsTechnicalManage> techList,String path){
        for (int i=0;i<list.size();i++) {
            SgjsTechnicalManage manage=new SgjsTechnicalManage();
            QqchPostSetting info = list.get(i);
            //跟节点
            handleData(info,manage,path);
            List<QqchPostSetting> children = info.getChildren();
            if(CollectionUtils.isEmpty(children)){
                continue;
            }
            List<SgjsTechnicalManage> childrenList=new ArrayList();
            for (int j = 0; j < children.size(); j++) {
                SgjsTechnicalManage sgjsTechnicalManage=new SgjsTechnicalManage();
                //子节点
                handleData2(children.get(j),sgjsTechnicalManage,path,manage.getPath());
                childrenList.add(sgjsTechnicalManage);
            }
            manage.setChildren(childrenList);
            techList.add(manage);
            if(!CollectionUtils.isEmpty(info.getChildren())){
                digui(info.getChildren(),techList,path);
            }
        }
    }

    /**
     * 数据处理
     *
     * @param info
     * @param manage
     */
    private void handleData(QqchPostSetting info, SgjsTechnicalManage manage, String path){
        manage.setId(IdWorker.createId());
        //技术部门+技术岗位=岗位
        String strMsg="";
        if(!StringUtils.isEmpty(info.getTechDept()) && !StringUtils.isEmpty(info.getPostName())){
            strMsg=info.getTechDept()+info.getPostName();
        }
        if(StringUtils.isEmpty(info.getTechDept())){
            strMsg=info.getPostName();
        }
        if(StringUtils.isEmpty(info.getPostName())){
            strMsg=info.getTechDept();
        }
        if(null==info.getPid()){
            manage.setPid(0L);
        }
        if(!StringUtils.isEmpty(strMsg)){
            manage.setPostName(strMsg);
        }
        if(null!=info.getHeadcount()){
            manage.setHeadCount(Integer.parseInt(info.getHeadcount()));
        }
        if(StringUtils.isEmpty(manage.getPath())){
            manage.setPath(manage.getId()+"/");
        }else{
            String id=manage.getId()+"";
            manage.setPath(path+"/"+id);
        }

        manage.setPtVar5(info.getId()+"");
        manage.setCreateUser(SecurityUtils.getUserId()+"");
        manage.setCreateTime(DateUtils.getNowDate());
    }


    /**
     * 数据处理
     *
     * @param info
     * @param manage
     */
    private void handleData2(QqchPostSetting info, SgjsTechnicalManage manage, String path, String ter){
        manage.setId(IdWorker.createId());
        //技术部门+技术岗位=岗位
        String strMsg="";
        if(!StringUtils.isEmpty(info.getTechDept()) && !StringUtils.isEmpty(info.getPostName())){
            strMsg=info.getTechDept()+info.getPostName();
        }
        if(StringUtils.isEmpty(info.getTechDept())){
            strMsg=info.getPostName();
        }
        if(StringUtils.isEmpty(info.getPostName())){
            strMsg=info.getTechDept();
        }
        if(null==info.getPid()){
            manage.setPid(0L);
        }
        if(!StringUtils.isEmpty(strMsg)){
            manage.setPostName(strMsg);
        }
        if(null!=info.getHeadcount()){
            manage.setHeadCount(Integer.parseInt(info.getHeadcount()));
        }

            String id=manage.getId()+"";
            manage.setPath(ter+id);

        manage.setPtVar5(info.getId()+"");
        manage.setCreateUser(SecurityUtils.getUserId()+"");
        manage.setCreateTime(DateUtils.getNowDate());
    }
}
