package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisSvMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:27
 * @remark
 */
@Service
public class JdglDiffAnalysisSvServiceImpl implements IJdglDiffAnalysisSvService {

    @Autowired
    private JdglDiffAnalysisSvMapper jdglDiffAnalysisSvMapper;


    public JdglDiffAnalysisSv getJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        return jdglDiffAnalysisSvMapper.getJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    public List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvList(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        return jdglDiffAnalysisSvMapper.getJdglDiffAnalysisSvList(jdglDiffAnalysisSv);
    }

    @Transactional
    public int insertJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setId(IdWorker.createId());
        jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int insertJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList) {
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : jdglDiffAnalysisSvList) {
            jdglDiffAnalysisSv.setId(IdWorker.createId());
            jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisSv.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSvList(jdglDiffAnalysisSvList);
    }

    @Transactional
    public int updateJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.updateJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int updateJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList) {
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : jdglDiffAnalysisSvList) {
            jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisSvMapper.updateJdglDiffAnalysisSvList(jdglDiffAnalysisSvList);
    }

    @Transactional
    public int deleteJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int deleteJdglDiffAnalysisSvByPks(List<Long> jdglDiffAnalysisSvPkList) {
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSvByPks(jdglDiffAnalysisSvPkList);
    }
}
