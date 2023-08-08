package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFileWbs;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.mapper.QqchSameEngineerFileWbsMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.IQqchSameEngineerFileWbsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 11:28:39
 * @remark 
 */
@Service
public class QqchSameEngineerFileWbsServiceImpl implements IQqchSameEngineerFileWbsService{

    @Autowired
    private QqchSameEngineerFileWbsMapper qqchSameEngineerFileWbsMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    public QqchSameEngineerFileWbs getQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs) {
        return qqchSameEngineerFileWbsMapper.getQqchSameEngineerFileWbs(qqchSameEngineerFileWbs);
    }

    public List<QqchSameEngineerFileWbs> getQqchSameEngineerFileWbsList(QqchSameEngineerFileWbs qqchSameEngineerFileWbs) {
        return qqchSameEngineerFileWbsMapper.getQqchSameEngineerFileWbsList(qqchSameEngineerFileWbs);
    }

    @Transactional
    public int insertQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs) {
        qqchSameEngineerFileWbs.setId(IdWorker.createId());
        qqchSameEngineerFileWbs.setCreateUser(SecurityUtils.getUserName());
        qqchSameEngineerFileWbs.setCreateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileWbsMapper.insertQqchSameEngineerFileWbs(qqchSameEngineerFileWbs);
    }

    @Transactional
    public int insertQqchSameEngineerFileWbsList(List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList) {
        for (QqchSameEngineerFileWbs qqchSameEngineerFileWbs : qqchSameEngineerFileWbsList) {
            qqchSameEngineerFileWbs.setId(IdWorker.createId());
            qqchSameEngineerFileWbs.setCreateUser(SecurityUtils.getUserName());
            qqchSameEngineerFileWbs.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSameEngineerFileWbsMapper.insertQqchSameEngineerFileWbsList(qqchSameEngineerFileWbsList);
    }

    @Transactional
    public int updateQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs) {
        qqchSameEngineerFileWbs.setUpdateUser(SecurityUtils.getUserName());
        qqchSameEngineerFileWbs.setUpdateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileWbsMapper.updateQqchSameEngineerFileWbs(qqchSameEngineerFileWbs);
    }

            @Transactional
        public int updateQqchSameEngineerFileWbsList(List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList) {
            for (QqchSameEngineerFileWbs qqchSameEngineerFileWbs : qqchSameEngineerFileWbsList) {
                qqchSameEngineerFileWbs.setUpdateUser(SecurityUtils.getUserName());
                qqchSameEngineerFileWbs.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchSameEngineerFileWbsMapper.updateQqchSameEngineerFileWbsList(qqchSameEngineerFileWbsList);
        }
    
    @Transactional
    public int deleteQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs) {
        qqchSameEngineerFileWbs.setUpdateUser(SecurityUtils.getUserName());
        qqchSameEngineerFileWbs.setUpdateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileWbsMapper.deleteQqchSameEngineerFileWbs(qqchSameEngineerFileWbs);
    }

            @Transactional
        public int deleteQqchSameEngineerFileWbsByPks(List<Long> qqchSameEngineerFileWbsPkList) {
            return qqchSameEngineerFileWbsMapper.deleteQqchSameEngineerFileWbsByPks(qqchSameEngineerFileWbsPkList);
        }
    }
