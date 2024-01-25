package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.mapper.SgjsTechnicalNormalTopicMapper;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.ISgjsTechnicalNormalTopicService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark 
 */
@Service
public class SgjsTechnicalNormalTopicServiceImpl implements ISgjsTechnicalNormalTopicService{

    @Autowired
    private SgjsTechnicalNormalTopicMapper sgjsTechnicalNormalTopicMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                        
    public SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        return sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    public List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        return sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setId(IdWorker.createId());
        sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            sgjsTechnicalNormalTopic.setId(IdWorker.createId());
            sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

            @Transactional
        public int updateSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
            for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
                sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
                sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
        }
    
    @Transactional
    public int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

            @Transactional
        public int deleteSgjsTechnicalNormalTopicByPks(List<Long> sgjsTechnicalNormalTopicPkList) {
            return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopicByPks(sgjsTechnicalNormalTopicPkList);
        }
    }
