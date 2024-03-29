package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.SysUser;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.mapper.SgjsBuildSchemeExpertSuggestMapper;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service.ISgjsBuildSchemeExpertSuggestService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.Assert;

/**
 * @author fsd
 * @date 2024-03-20 18:18:03
 * @remark
 */
@Service
public class SgjsBuildSchemeExpertSuggestServiceImpl implements ISgjsBuildSchemeExpertSuggestService {

    @Autowired
    private SgjsBuildSchemeExpertSuggestMapper sgjsBuildSchemeExpertSuggestMapper;

    //台账查询
    @Override
    public List<SgjsBuildSchemeExpertSuggest> getGroupList(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        Long foreignId = sgjsBuildSchemeExpertSuggestParam.getForeignId();
        Assert.isTrue(foreignId!=null, "foreignId不能为空");
        List<SgjsBuildSchemeExpertSuggest> groupList = sgjsBuildSchemeExpertSuggestMapper.getGroupList(foreignId);
        groupList.forEach(p -> {
            Date submitTime = p.getSubmitTime();
            if (submitTime != null) {
                String format = DateUtil.format(submitTime, "yyyy-MM-dd HH");
                p.setPtVar2(format + ":00");
            }
        });
        return groupList;
    }

    public SgjsBuildSchemeExpertSuggest getSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest) {
        return sgjsBuildSchemeExpertSuggestMapper.getSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggest);
    }

    //聚合查询
    public List<SgjsBuildSchemeExpertSuggest> getSgjsBuildSchemeExpertSuggestList(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest) {
        List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList = sgjsBuildSchemeExpertSuggestMapper.getSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggest);
        if (CollUtil.isEmpty(sgjsBuildSchemeExpertSuggestList)) return new ArrayList<>();
        return sgjsBuildSchemeExpertSuggestList;
    }

    @Transactional
    public int insertSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest) {
        sgjsBuildSchemeExpertSuggest.setId(IdWorker.createId());
        sgjsBuildSchemeExpertSuggest.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeExpertSuggest.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeExpertSuggestMapper.insertSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggest);
    }

    //保存
    @Transactional
    public int insertSgjsBuildSchemeExpertSuggestList(List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList) {
        if (CollUtil.isEmpty(sgjsBuildSchemeExpertSuggestList)) return 0;
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = SecurityUtils.getSysUser();
        for (SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest : sgjsBuildSchemeExpertSuggestList) {
            sgjsBuildSchemeExpertSuggest.setPersonId(String.valueOf(userId));
            sgjsBuildSchemeExpertSuggest.setPersonName(sysUser.getNickName());
            sgjsBuildSchemeExpertSuggest.setSubmitTime(DateUtils.getNowDate());
            sgjsBuildSchemeExpertSuggest.setId(IdWorker.createId());
            sgjsBuildSchemeExpertSuggest.setCreateUser(sysUser.getUserName());
            sgjsBuildSchemeExpertSuggest.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeExpertSuggestMapper.insertSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestList);
    }

    @Transactional
    public int updateSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest) {
        sgjsBuildSchemeExpertSuggest.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeExpertSuggest.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeExpertSuggestMapper.updateSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggest);
    }

    @Transactional
    public int updateSgjsBuildSchemeExpertSuggestList(List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList) {
        for (SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest : sgjsBuildSchemeExpertSuggestList) {
            sgjsBuildSchemeExpertSuggest.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeExpertSuggest.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeExpertSuggestMapper.updateSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest) {
        sgjsBuildSchemeExpertSuggest.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeExpertSuggest.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeExpertSuggestMapper.deleteSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggest);
    }

    @Transactional
    public int deleteSgjsBuildSchemeExpertSuggestByPks(List<Long> sgjsBuildSchemeExpertSuggestPkList) {
        return sgjsBuildSchemeExpertSuggestMapper.deleteSgjsBuildSchemeExpertSuggestByPks(sgjsBuildSchemeExpertSuggestPkList);
    }

    @Override
    public List<SgjsBuildSchemeExpertSuggest> getListByforeignList(Set<Long> foreignId) {
        return sgjsBuildSchemeExpertSuggestMapper.getListByforeignList(foreignId);
    }
}
