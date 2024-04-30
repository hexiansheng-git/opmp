package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper.JdglMainPlanItemPreMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
@Service
public class JdglMainPlanItemPreServiceImpl implements IJdglMainPlanItemPreService {

    private static Map<String,String> typeMap = new HashMap<>();
    static {
        typeMap.put("Finish to Start", "0");
        typeMap.put("Start to Start", "1");
        typeMap.put("Finish to Finish", "2");
        typeMap.put("Start to Finish", "3");
    }

    @Autowired
    private JdglMainPlanItemPreMapper jdglMainPlanItemPreMapper;


    public JdglMainPlanItemPre getJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre) {
        return jdglMainPlanItemPreMapper.getJdglMainPlanItemPre(jdglMainPlanItemPre);
    }

    public List<JdglMainPlanItemPre> getJdglMainPlanItemPreList(JdglMainPlanItemPre jdglMainPlanItemPre) {
        List<JdglMainPlanItemPre> jdglMainPlanItemPreList = jdglMainPlanItemPreMapper.getJdglMainPlanItemPreList(jdglMainPlanItemPre);
        if(!CollectionUtils.isEmpty(jdglMainPlanItemPreList)) {
            for (JdglMainPlanItemPre jdglMainPlanItemPre1 : jdglMainPlanItemPreList) {
                jdglMainPlanItemPre1.setTarget(jdglMainPlanItemPre1.getItemId());
                jdglMainPlanItemPre1.setSource(jdglMainPlanItemPre1.getPredecessorItemId());
                jdglMainPlanItemPre1.setType(typeMap.get(jdglMainPlanItemPre1.getType()));
            }
        }
        return jdglMainPlanItemPreList;
    }

    @Transactional
    public int insertJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre) {
        jdglMainPlanItemPre.setId(IdWorker.createId());
        jdglMainPlanItemPre.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlanItemPre.setCreateTime(DateUtils.getNowDate());
        return jdglMainPlanItemPreMapper.insertJdglMainPlanItemPre(jdglMainPlanItemPre);
    }

    @Transactional
    public int insertJdglMainPlanItemPreList(List<JdglMainPlanItemPre> jdglMainPlanItemPreList) {
        if(CollectionUtils.isEmpty(jdglMainPlanItemPreList)) {
            return 0;
        }
        for (JdglMainPlanItemPre jdglMainPlanItemPre : jdglMainPlanItemPreList) {
            jdglMainPlanItemPre.setId(IdWorker.createId());
//            jdglMainPlanItemPre.setCreateUser(SecurityUtils.getUserName());
            jdglMainPlanItemPre.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanItemPreMapper.insertJdglMainPlanItemPreList(jdglMainPlanItemPreList);
    }

    @Transactional
    public int updateJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre) {
        jdglMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanItemPreMapper.updateJdglMainPlanItemPre(jdglMainPlanItemPre);
    }

    @Transactional
    public int updateJdglMainPlanItemPreList(List<JdglMainPlanItemPre> jdglMainPlanItemPreList) {
        for (JdglMainPlanItemPre jdglMainPlanItemPre : jdglMainPlanItemPreList) {
            jdglMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
            jdglMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanItemPreMapper.updateJdglMainPlanItemPreList(jdglMainPlanItemPreList);
    }

    @Transactional
    public int deleteJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre) {
//        jdglMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
//        jdglMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanItemPreMapper.deleteJdglMainPlanItemPre(jdglMainPlanItemPre);
    }

    @Transactional
    public int deleteJdglMainPlanItemPreByPks(List<Long> jdglMainPlanItemPrePkList) {
        return jdglMainPlanItemPreMapper.deleteJdglMainPlanItemPreByPks(jdglMainPlanItemPrePkList);
    }
}
