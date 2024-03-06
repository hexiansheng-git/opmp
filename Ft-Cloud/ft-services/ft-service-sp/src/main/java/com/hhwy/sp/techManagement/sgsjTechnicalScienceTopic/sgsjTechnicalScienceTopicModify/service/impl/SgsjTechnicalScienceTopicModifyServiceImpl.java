package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.FileDto;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.mapper.SgsjTechnicalScienceTopicModifyMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 功能描述: 科技管理 - 科研课题研发管理 修改记录
 *
 * @author fsd
 * @date 2024-01-29 14:14:11
 * @remark
 */
@Service
public class SgsjTechnicalScienceTopicModifyServiceImpl implements ISgsjTechnicalScienceTopicModifyService {

    @Autowired
    private SgsjTechnicalScienceTopicModifyMapper sgsjTechnicalScienceTopicModifyMapper;


    public SgsjTechnicalScienceTopicModify getSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        return sgsjTechnicalScienceTopicModifyMapper.getSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    public List<SgsjTechnicalScienceTopicModify> getSgsjTechnicalScienceTopicModifyList(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        List<SgsjTechnicalScienceTopicModify> list = sgsjTechnicalScienceTopicModifyMapper.getSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModify);
//        for (SgsjTechnicalScienceTopicModify bean : list) {
//            if (StrUtil.isNotBlank(bean.getModifyContent()) && bean.getModifyContent().contains("附件")) {
//                bean.setBeforeModify(getFileName(bean.getBeforeModify()));
//                bean.setAfterModify(getFileName(bean.getAfterModify()));
//            }
//        }
        return list;
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setId(IdWorker.createId());
        sgsjTechnicalScienceTopicModify.setCreateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setCreateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.insertSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList) {
        return sgsjTechnicalScienceTopicModifyMapper.insertSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyList);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.updateSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList) {
        for (SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify : sgsjTechnicalScienceTopicModifyList) {
            sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicModifyMapper.updateSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyList);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.deleteSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopicModifyByPks(List<Long> sgsjTechnicalScienceTopicModifyPkList) {
        return sgsjTechnicalScienceTopicModifyMapper.deleteSgsjTechnicalScienceTopicModifyByPks(sgsjTechnicalScienceTopicModifyPkList);
    }

    @Override
    public SgsjTechnicalScienceTopicModify getMaxCreateTimeDataByModifyContent(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        return sgsjTechnicalScienceTopicModifyMapper.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
    }
}
