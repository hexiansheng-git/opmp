package com.hhwy.pm.qqch.preparation.doc.tech.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.doc.tech.mapper.QqchDocTechMapper;
import com.hhwy.pm.qqch.preparation.doc.tech.service.IQqchDocTechService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark 
 */
@Service
public class QqchDocTechServiceImpl implements IQqchDocTechService{

    @Autowired
    private QqchDocTechMapper qqchDocTechMapper;

                                                                                                                                                                                                                                                                            
    public QqchDocTech getQqchDocTech(QqchDocTech qqchDocTech) {
        return qqchDocTechMapper.getQqchDocTech(qqchDocTech);
    }

    public List<QqchDocTech> getQqchDocTechList(QqchDocTech qqchDocTech) {
        return qqchDocTechMapper.getQqchDocTechList(qqchDocTech);
    }

    @Transactional
    public int insertQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setId(IdWorker.createId());
        qqchDocTech.setCreateUser(SecurityUtils.getUserName());
        qqchDocTech.setCreateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.insertQqchDocTech(qqchDocTech);
    }

    @Transactional
    public int insertQqchDocTechList(List<QqchDocTech> qqchDocTechList) {
        for (QqchDocTech qqchDocTech : qqchDocTechList) {
            qqchDocTech.setId(IdWorker.createId());
            qqchDocTech.setCreateUser(SecurityUtils.getUserName());
            qqchDocTech.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMapper.insertQqchDocTechList(qqchDocTechList);
    }

    @Transactional
    public int updateQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTech.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.updateQqchDocTech(qqchDocTech);
    }

            @Transactional
        public int updateQqchDocTechList(List<QqchDocTech> qqchDocTechList) {
            for (QqchDocTech qqchDocTech : qqchDocTechList) {
                qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
                qqchDocTech.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDocTechMapper.updateQqchDocTechList(qqchDocTechList);
        }
    
    @Transactional
    public int deleteQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTech.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.deleteQqchDocTech(qqchDocTech);
    }

            @Transactional
        public int deleteQqchDocTechByPks(List<Long> qqchDocTechPkList) {
            return qqchDocTechMapper.deleteQqchDocTechByPks(qqchDocTechPkList);
        }
    }
