package com.hhwy.pm.qqch.preparation.doc.techmae.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.doc.techmae.mapper.QqchDocTechMaeMapper;
import com.hhwy.pm.qqch.preparation.doc.techmae.service.IQqchDocTechMaeService;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark 
 */
@Service
public class QqchDocTechMaeServiceImpl implements IQqchDocTechMaeService{

    @Autowired
    private QqchDocTechMaeMapper qqchDocTechMaeMapper;

                                                                                                                                                                                                                                                                            
    public QqchDocTechMae getQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        return qqchDocTechMaeMapper.getQqchDocTechMae(qqchDocTechMae);
    }

    public List<QqchDocTechMae> getQqchDocTechMaeList(QqchDocTechMae qqchDocTechMae) {
        return qqchDocTechMaeMapper.getQqchDocTechMaeList(qqchDocTechMae);
    }

    @Transactional
    public int insertQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setId(IdWorker.createId());
        qqchDocTechMae.setCreateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setCreateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.insertQqchDocTechMae(qqchDocTechMae);
    }

    @Transactional
    public int insertQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList) {
        for (QqchDocTechMae qqchDocTechMae : qqchDocTechMaeList) {
            qqchDocTechMae.setId(IdWorker.createId());
            qqchDocTechMae.setCreateUser(SecurityUtils.getUserName());
            qqchDocTechMae.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMaeMapper.insertQqchDocTechMaeList(qqchDocTechMaeList);
    }

    @Transactional
    public int updateQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.updateQqchDocTechMae(qqchDocTechMae);
    }

            @Transactional
        public int updateQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList) {
            for (QqchDocTechMae qqchDocTechMae : qqchDocTechMaeList) {
                qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
                qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDocTechMaeMapper.updateQqchDocTechMaeList(qqchDocTechMaeList);
        }
    
    @Transactional
    public int deleteQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.deleteQqchDocTechMae(qqchDocTechMae);
    }

            @Transactional
        public int deleteQqchDocTechMaeByPks(List<Long> qqchDocTechMaePkList) {
            return qqchDocTechMaeMapper.deleteQqchDocTechMaeByPks(qqchDocTechMaePkList);
        }
    }
