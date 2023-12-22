package com.hhwy.sd.organManage.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.organManage.domain.KcsjOrganManage;
import com.hhwy.sd.organManage.mapper.KcsjOrganManageMapper;
import com.hhwy.sd.organManage.service.IKcsjOrganManageDetailService;
import com.hhwy.sd.organManage.service.IKcsjOrganManageService;
import com.hhwy.sd.organManage.util.TreeCountUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-12-14 11:31:44
 * @remark
 */
@Service
public class KcsjOrganManageServiceImpl implements IKcsjOrganManageService {

    @Autowired
    private KcsjOrganManageMapper kcsjOrganManageMapper;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private IKcsjOrganManageDetailService kcsjOrganManageDetailService;

    public KcsjOrganManage getKcsjOrganManage(KcsjOrganManage kcsjOrganManage) {
        return kcsjOrganManageMapper.getKcsjOrganManage(kcsjOrganManage);
    }

    public List<KcsjOrganManage> getKcsjOrganManageList(KcsjOrganManage kcsjOrganManage) {
        Map<String, Object> queryMap = new HashMap<>();
        String postName = kcsjOrganManage.getPostName();
        if(StringUtils.isNotEmpty(postName)) {
            queryMap.put("postName",postName);
            kcsjOrganManage.setPostName(null);
        }
        String postJob = kcsjOrganManage.getPostJob();
        if(StringUtils.isNotEmpty(postJob)) {
            queryMap.put("postJob",postJob);
            kcsjOrganManage.setPostJob(null);
        }
        Long userId = kcsjOrganManage.getUserId();
        if(userId != null) {
            queryMap.put("userId",userId);
            kcsjOrganManage.setUserId(null);
        }
        String userName = kcsjOrganManage.getUserName();
        if(StringUtils.isNotEmpty(userName)) {
            queryMap.put("userName",userName);
            kcsjOrganManage.setUserName(null);
        }
        Date actualEnterDate = kcsjOrganManage.getActualEnterDate();
        if(actualEnterDate != null) {
            queryMap.put("actualEnterDate",actualEnterDate);
            kcsjOrganManage.setActualEnterDate(null);
        }
        Long pid = kcsjOrganManage.getPid();
        List<KcsjOrganManage> kcsjOrganManageList = kcsjOrganManageMapper.getKcsjOrganManageList(kcsjOrganManage);
        if(CollectionUtils.isNotEmpty(kcsjOrganManageList) && queryMap.size() > 0) {
            TreeCountUtils<KcsjOrganManage> treeCountUtils = new TreeCountUtils<>();
            List<KcsjOrganManage> kcsjOrganManages = treeCountUtils.queryTree(kcsjOrganManageList, queryMap, pid);
            return TreeUtil.build(kcsjOrganManages, pid);
        }

        return TreeUtil.build(kcsjOrganManageList, pid);
    }

    @Transactional
    public int insertKcsjOrganManage(KcsjOrganManage kcsjOrganManage) {
        kcsjOrganManage.setId(IdWorker.createId());
        kcsjOrganManage.setCreateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManage.setCreateTime(DateUtils.getNowDate());
        return kcsjOrganManageMapper.insertKcsjOrganManage(kcsjOrganManage);
    }

    @Transactional
    public int insertKcsjOrganManageList(List<KcsjOrganManage> kcsjOrganManageList) {
        for (KcsjOrganManage kcsjOrganManage : kcsjOrganManageList) {
//            kcsjOrganManage.setId(IdWorker.createId());
            kcsjOrganManage.setCreateUser(SecurityUtils.getSysUser().getNickName());
            kcsjOrganManage.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjOrganManageMapper.insertKcsjOrganManageList(kcsjOrganManageList);
    }

    @Transactional
    public int updateKcsjOrganManage(KcsjOrganManage kcsjOrganManage) {
        kcsjOrganManage.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManage.setUpdateTime(DateUtils.getNowDate());
        return kcsjOrganManageMapper.updateKcsjOrganManage(kcsjOrganManage);
    }

    @Override
    public int updateKcsjOrganManage(Long id, Date enterDate, Date leaveDate) {

        KcsjOrganManage kcsjOrganManage = new KcsjOrganManage();

        kcsjOrganManage.setId(id);
//        kcsjOrganManage.setActualEnterDate(enterDate);
        kcsjOrganManage.setActualExitDate(leaveDate);
        if(leaveDate == null && enterDate != null) {
            kcsjOrganManage.setWorkOrNot("1");
        } else {
            kcsjOrganManage.setWorkOrNot("0");
        }

        return updateKcsjOrganManage(kcsjOrganManage);
    }

    @Transactional
    public int updateKcsjOrganManageList(List<KcsjOrganManage> kcsjOrganManageList) {
        if(CollectionUtils.isEmpty(kcsjOrganManageList)) {
            return 0;
        }
        List<KcsjOrganManage> addList = new ArrayList<>();
        List<KcsjOrganManage> updateList = new ArrayList<>();

        List<KcsjOrganManage> kcsjOrganManages = TreeUtil.treeToListSupplyId(kcsjOrganManageList);
        int validNum = 0;
        for (KcsjOrganManage kcsjOrganManage : kcsjOrganManages) {
            if(StringUtils.isNotEmpty(kcsjOrganManage.getUserName()) && kcsjOrganManage.getActualEnterDate() == null) {
                validNum ++;
            }
            if(StringUtils.isEmpty(kcsjOrganManage.getPostName()) && StringUtils.isEmpty(kcsjOrganManage.getUserName())) {
                validNum ++;
            }
            if("1".equals(kcsjOrganManage.getIsAdd())) {
                kcsjOrganManage.setCreateUser(SecurityUtils.getSysUser().getNickName());
                kcsjOrganManage.setCreateTime(DateUtils.getNowDate());
                kcsjOrganManage.setDataSource("自建");
                addList.add(kcsjOrganManage);
            } else {
                kcsjOrganManage.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                kcsjOrganManage.setUpdateTime(DateUtils.getNowDate());
                updateList.add(kcsjOrganManage);
            }
        }
        if(validNum > 0) {
            throw new RuntimeException("必填字段为空!");
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(updateList)) {
            i += kcsjOrganManageMapper.updateKcsjOrganManageList(updateList);
        }
        if(CollectionUtils.isNotEmpty(addList)) {
            i += kcsjOrganManageMapper.insertKcsjOrganManageList(addList);
        }
        return i;
    }

    @Transactional
    public int deleteKcsjOrganManage(KcsjOrganManage kcsjOrganManage) {
        kcsjOrganManage.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManage.setUpdateTime(DateUtils.getNowDate());
        return kcsjOrganManageMapper.deleteKcsjOrganManage(kcsjOrganManage);
    }

    @Transactional
    public int deleteKcsjOrganManageByPks(List<Long> kcsjOrganManagePkList) {
        return kcsjOrganManageMapper.deleteKcsjOrganManageByPks(kcsjOrganManagePkList);
    }

    @Override
    public void sync() {
        AjaxResult result = pmServiceApi.getQqchSurveyOrganizationList();
        if(!result.get("code").toString().equals("200")){
            AjaxResult.error("同步异常");
        }
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")));
        JSONArray array = JSONObject.parseArray(JSONObject.toJSONString(data.get("qqchSurveyOrganizationList")));
        if(CollectionUtils.isEmpty(array)) {
            AjaxResult.error("同步异常!");
        }
        List<KcsjOrganManage> list=new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)));
            KcsjOrganManage info=new KcsjOrganManage();
            String surveyDesignGroup = ObjectUtils.toString(object.get("surveyDesignGroup"));
            String groupRole = ObjectUtils.toString(object.get("groupRole"));
            if(StringUtils.isNotEmpty(surveyDesignGroup)) info.setPostName(surveyDesignGroup);
            if(StringUtils.isNotEmpty(groupRole)) info.setPostName(groupRole);
            info.setPostJob(ObjectUtils.toString(object.get("postDuty")));
            int num = 0;
            try {
                String staffEstablish = ObjectUtils.toString(object.get("staffEstablish"));
                if(StringUtils.isNotEmpty(staffEstablish)) num += Integer.valueOf(staffEstablish);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                String actorPersonAsk = ObjectUtils.toString(object.get("actorPersonAsk"));
                if(StringUtils.isNotEmpty(actorPersonAsk)) num += Integer.valueOf(actorPersonAsk);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Long id = Long.valueOf(ObjectUtils.toString(object.get("id")));
            Long pid = object.get("pid") == null ? null : Long.valueOf(ObjectUtils.toString(object.get("pid")));
            if(pid != null && pid == 0L) pid = null;
            info.setId(id);
            info.setPid(pid);
            info.setHeadCount(num);
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getSysUser().getNickName());
            list.add(info);
        }
        if(CollectionUtils.isNotEmpty(list)) {
            deleteKcsjOrganManage4All();
            insertKcsjOrganManageList(list);
        }
    }

    private void deleteKcsjOrganManage4All() {
        kcsjOrganManageMapper.deleteKcsjOrganManage4All();
        kcsjOrganManageDetailService.deleteKcsjOrganManageDetail4All();
    }
}
