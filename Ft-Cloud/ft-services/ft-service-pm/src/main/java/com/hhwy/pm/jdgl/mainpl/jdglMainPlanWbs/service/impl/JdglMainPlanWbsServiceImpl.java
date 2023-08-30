package com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.mapper.JdglMainPlanWbsMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.service.IJdglMainPlanWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.domain.JdglMainPlanWbs;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:35
 * @remark
 */
@Service
public class JdglMainPlanWbsServiceImpl implements IJdglMainPlanWbsService {

    @Autowired
    private JdglMainPlanWbsMapper jdglMainPlanWbsMapper;


    public JdglMainPlanWbs getJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs) {
        return jdglMainPlanWbsMapper.getJdglMainPlanWbs(jdglMainPlanWbs);
    }

    public List<JdglMainPlanWbs> getJdglMainPlanWbsList(JdglMainPlanWbs jdglMainPlanWbs) {
        return jdglMainPlanWbsMapper.getJdglMainPlanWbsList(jdglMainPlanWbs);
    }

    @Transactional
    public int insertJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs) {
        jdglMainPlanWbs.setId(IdWorker.createId());
        jdglMainPlanWbs.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlanWbs.setCreateTime(DateUtils.getNowDate());
        return jdglMainPlanWbsMapper.insertJdglMainPlanWbs(jdglMainPlanWbs);
    }

    @Transactional
    public int insertJdglMainPlanWbsList(List<JdglMainPlanWbs> jdglMainPlanWbsList) {
        for (JdglMainPlanWbs jdglMainPlanWbs : jdglMainPlanWbsList) {
            jdglMainPlanWbs.setId(IdWorker.createId());
            jdglMainPlanWbs.setCreateUser(SecurityUtils.getUserName());
            jdglMainPlanWbs.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanWbsMapper.insertJdglMainPlanWbsList(jdglMainPlanWbsList);
    }

    @Transactional
    public int updateJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs) {
        jdglMainPlanWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlanWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanWbsMapper.updateJdglMainPlanWbs(jdglMainPlanWbs);
    }

    @Transactional
    public int updateJdglMainPlanWbsList(List<JdglMainPlanWbs> jdglMainPlanWbsList) {
        for (JdglMainPlanWbs jdglMainPlanWbs : jdglMainPlanWbsList) {
            jdglMainPlanWbs.setUpdateUser(SecurityUtils.getUserName());
            jdglMainPlanWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanWbsMapper.updateJdglMainPlanWbsList(jdglMainPlanWbsList);
    }

    @Transactional
    public int deleteJdglMainPlanWbs(JdglMainPlanWbs jdglMainPlanWbs) {
        jdglMainPlanWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlanWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanWbsMapper.deleteJdglMainPlanWbs(jdglMainPlanWbs);
    }

    @Transactional
    public int deleteJdglMainPlanWbsByPks(List<Long> jdglMainPlanWbsPkList) {
        return jdglMainPlanWbsMapper.deleteJdglMainPlanWbsByPks(jdglMainPlanWbsPkList);
    }
}
