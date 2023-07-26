package com.hhwy.pm.qqch.preparation.doc.dwg.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.doc.dwg.mapper.QqchDocDwgMapper;
import com.hhwy.pm.qqch.preparation.doc.dwg.service.IQqchDocDwgService;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark 
 */
@Service
public class QqchDocDwgServiceImpl implements IQqchDocDwgService{

    @Autowired
    private QqchDocDwgMapper qqchDocDwgMapper;

                                                                                                                                                                                                                                                                                                                                        
    public QqchDocDwg getQqchDocDwg(QqchDocDwg qqchDocDwg) {
        return qqchDocDwgMapper.getQqchDocDwg(qqchDocDwg);
    }

    public List<QqchDocDwg> getQqchDocDwgList(QqchDocDwg qqchDocDwg) {
        return qqchDocDwgMapper.getQqchDocDwgList(qqchDocDwg);
    }

    @Transactional
    public int insertQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setId(IdWorker.createId());
        qqchDocDwg.setCreateUser(SecurityUtils.getUserName());
        qqchDocDwg.setCreateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.insertQqchDocDwg(qqchDocDwg);
    }

    @Transactional
    public int insertQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList) {
        for (QqchDocDwg qqchDocDwg : qqchDocDwgList) {
            qqchDocDwg.setId(IdWorker.createId());
            qqchDocDwg.setCreateUser(SecurityUtils.getUserName());
            qqchDocDwg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocDwgMapper.insertQqchDocDwgList(qqchDocDwgList);
    }

    @Transactional
    public int updateQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
        qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.updateQqchDocDwg(qqchDocDwg);
    }

            @Transactional
        public int updateQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList) {
            for (QqchDocDwg qqchDocDwg : qqchDocDwgList) {
                qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
                qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDocDwgMapper.updateQqchDocDwgList(qqchDocDwgList);
        }
    
    @Transactional
    public int deleteQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
        qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.deleteQqchDocDwg(qqchDocDwg);
    }

            @Transactional
        public int deleteQqchDocDwgByPks(List<Long> qqchDocDwgPkList) {
            return qqchDocDwgMapper.deleteQqchDocDwgByPks(qqchDocDwgPkList);
        }
    }
