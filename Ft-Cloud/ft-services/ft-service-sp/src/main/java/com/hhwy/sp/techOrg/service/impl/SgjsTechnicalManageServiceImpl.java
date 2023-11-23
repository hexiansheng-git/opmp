package com.hhwy.sp.techOrg.service.impl;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageVo;
import com.hhwy.sp.techOrg.mapper.SgjsTechnicalManageMapper;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
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
        //删除库中所有数据
        SgjsTechnicalManage info=new SgjsTechnicalManage();
        info.setUpdateTime(DateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsTechnicalManageMapper.delectAll(info);
        if(!CollectionUtils.isEmpty(sgjsTechnicalManageVo.getTreeList())){
            treeToList= TreeUtil.treeToList(sgjsTechnicalManageVo.getTreeList());
            for (int i = 0; i < treeToList.size(); i++) {
                SgjsTechnicalManage manage = treeToList.get(i);
                String actualDateStr = manage.getActualDateStr();
                if(StringUtils.isNotEmpty(actualDateStr)){
                    String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
                    Date date = FtDateUtils.parseDate(str);
                    manage.setActualDate(date);
                }
            }
        }
        sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(treeToList);
        return AjaxResult.success();
    }

    /**
     * 数据保存校验
     *
     * @param list
     * @return
     */
    private AjaxResult validData(List<SgjsTechnicalManage>list) {
        String msg="";
        String s = validDataDigui(list, msg);
        if(StringUtils.isNotEmpty(s)){
            return AjaxResult.success(list);
        }
        return AjaxResult.error(msg);
    }

   private String validDataDigui(List<SgjsTechnicalManage>list,String msg){
        for (int i = 0; i < list.size(); i++) {
            Integer headCount = list.get(i).getHeadCount();
            if(headCount>0){
                String userName = list.get(i).getUserName();
                if(StringUtils.isEmpty(userName)){
                    msg=msg+list.get(i).getPostName()+"的姓名不能为空";
                }
                Date actualDate = list.get(i).getActualDate();
                if(null==actualDate){
                    msg=msg+list.get(i).getPostName()+"的姓名不能为空";
                }
            }
            if(!CollectionUtils.isEmpty(list.get(i).getChildren())){
                validDataDigui(list,msg);
            }
        }
        return msg;
    }

    @Override
    public AjaxResult sync() {
        List<QqchPostSetting> list = pmServiceApi.getTechDeptList();
        //递归处理
        digui(list);
        return AjaxResult.success(list);
    }

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
            //编制人数
            String headcount = info.getHeadcount();
            if(!StringUtils.isEmpty(str)){
                info.setPostName(str);
            }
            if(!CollectionUtils.isEmpty(info.getChildren())){
                digui(list);
            }
        }
    }
}
