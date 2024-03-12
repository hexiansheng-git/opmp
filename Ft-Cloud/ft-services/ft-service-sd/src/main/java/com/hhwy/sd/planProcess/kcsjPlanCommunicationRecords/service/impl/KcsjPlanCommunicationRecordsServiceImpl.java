package com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.mapper.KcsjPlanCommunicationRecordsMapper;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.service.IKcsjPlanCommunicationRecordsService;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wll
 * @date 2023-12-15 10:37:09
 * @remark
 */
@Service
public class KcsjPlanCommunicationRecordsServiceImpl implements IKcsjPlanCommunicationRecordsService {

    @Autowired
    private KcsjPlanCommunicationRecordsMapper kcsjPlanCommunicationRecordsMapper;

    @Autowired
    private  ISysSyncInfoService4Sd sysSyncInfoService4Sd;

    public KcsjPlanCommunicationRecords getKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {
        return kcsjPlanCommunicationRecordsMapper.getKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecords);
    }

    /**
     * 分页查询&&条件查询
     *
     * @param kcsjPlanCommunicationRecords
     * @return
     */
    public List<KcsjPlanCommunicationRecords> getKcsjPlanCommunicationRecordsList(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {

        List<KcsjPlanCommunicationRecords> list = kcsjPlanCommunicationRecordsMapper.getKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecords);

        /*if(!CollectionUtils.isEmpty(list)) {
            //用户昵称处理
            List<String> userNameList = list.stream().map(e -> e.getCreateUserName()).collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            map.put("tenantKey", SecurityUtils.getTenantKey());
            map.put("user_name", String.join(",", userNameList));
            List<SysUser> sysUserList = systemServiceApi.selectUserInfoByNickNameAndTenant(map);
            for (int i = 0; i < list.size(); i++) {
                String userName = list.get(i).getCreateUserName();
                List<SysUser> userList = sysUserList.stream().filter(e -> e.getUserName().equals(userName)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(userList)) {
                    list.get(i).setPtVar1(userList.get(0).getNickName());
                }
            }
        }*/


        return list;

    }

    @Transactional
    public int insertKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {
        kcsjPlanCommunicationRecords.setId(IdWorker.createId());
        kcsjPlanCommunicationRecords.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        kcsjPlanCommunicationRecords.setCreateTime(DateUtils.getNowDate());
        return kcsjPlanCommunicationRecordsMapper.insertKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecords);
    }

    @Transactional
    public AjaxResult insertKcsjPlanCommunicationRecordsList
            (List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList) {

        //获取需要新增的数据集合
        List<KcsjPlanCommunicationRecords> insertList = new ArrayList<>();
        //获取需要修改的数据集合
        List<KcsjPlanCommunicationRecords> updateList = new ArrayList<>();

        for (KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords : kcsjPlanCommunicationRecordsList) {
            if ("0".equals(kcsjPlanCommunicationRecords.getType())) {
                insertList.add(kcsjPlanCommunicationRecords);
            } else {
                updateList.add(kcsjPlanCommunicationRecords);
            }

        }
        if (insertList.size() > 0) {
            for (KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords : insertList) {
                kcsjPlanCommunicationRecords.setId(IdWorker.createId());
                kcsjPlanCommunicationRecords.setCreateUser(SecurityUtils.getUserId().toString());
                kcsjPlanCommunicationRecords.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                kcsjPlanCommunicationRecords.setCreateTime(DateUtils.getNowDate());
                kcsjPlanCommunicationRecords.setDelFlag("0");
            }
            kcsjPlanCommunicationRecordsMapper.insertKcsjPlanCommunicationRecordsList(insertList);
        }

        if (updateList.size() > 0) {
            for (KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords : updateList) {
                kcsjPlanCommunicationRecords.setUpdateUser(SecurityUtils.getUserId().toString());
                kcsjPlanCommunicationRecords.setUpdateTime(DateUtils.getNowDate());
            }
            kcsjPlanCommunicationRecordsMapper.updateKcsjPlanCommunicationRecordsList(updateList);
        }

        //同步数据到总部
        List<KcsjPlanCommunicationRecords> all=kcsjPlanCommunicationRecordsMapper.getAll();
        if (all!=null){
            sysSyncInfoService4Sd.pushKcsjPlanCommunicationRecords(all);
        }

        return AjaxResult.success();

    }

    @Transactional
    public int updateKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {
        kcsjPlanCommunicationRecords.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanCommunicationRecords.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanCommunicationRecordsMapper.updateKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecords);
    }

    @Transactional
    public int updateKcsjPlanCommunicationRecordsList
            (List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList) {
        for (KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords : kcsjPlanCommunicationRecordsList) {
            kcsjPlanCommunicationRecords.setUpdateUser(SecurityUtils.getUserName());
            kcsjPlanCommunicationRecords.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjPlanCommunicationRecordsMapper.updateKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecordsList);
    }

    @Transactional
    public int deleteKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {
        kcsjPlanCommunicationRecords.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanCommunicationRecords.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanCommunicationRecordsMapper.deleteKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecords);
    }

    /**
     * 批量删除
     * @param kcsjPlanCommunicationRecordsPkList
     * @return
     */
    @Transactional
    public int deleteKcsjPlanCommunicationRecordsByPks(List<Long> kcsjPlanCommunicationRecordsPkList) {

        String delUser = SecurityUtils.getSysUser().getNickName();
         int i= kcsjPlanCommunicationRecordsMapper.deleteKcsjPlanCommunicationRecordsByPks(kcsjPlanCommunicationRecordsPkList, delUser);

        if (kcsjPlanCommunicationRecordsPkList.size()>0){
            //推送数据到总部
            List<KcsjPlanCommunicationRecords> all=kcsjPlanCommunicationRecordsMapper.getAll();
            if (all!=null) {
                sysSyncInfoService4Sd.pushKcsjPlanCommunicationRecords(all);
            }
        }
        return i;
    }


}
