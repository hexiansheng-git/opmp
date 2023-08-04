package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.mapper.QqchFirstArticleEngineeringControlMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.IQqchFirstArticleEngineeringControlService;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark 
 */
@Service
public class QqchFirstArticleEngineeringControlServiceImpl implements IQqchFirstArticleEngineeringControlService{

    @Autowired
    private QqchFirstArticleEngineeringControlMapper qqchFirstArticleEngineeringControlMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchFirstArticleEngineeringControl getQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        return qqchFirstArticleEngineeringControlMapper.getQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

    public List<QqchFirstArticleEngineeringControl> getQqchFirstArticleEngineeringControlList(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        return qqchFirstArticleEngineeringControlMapper.getQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControl);
    }

    @Transactional
    public int insertQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        qqchFirstArticleEngineeringControl.setId(IdWorker.createId());
        qqchFirstArticleEngineeringControl.setCreateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringControl.setCreateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.insertQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

    @Transactional
    public int insertQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList) {
        for (QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl : qqchFirstArticleEngineeringControlList) {
            qqchFirstArticleEngineeringControl.setId(IdWorker.createId());
            qqchFirstArticleEngineeringControl.setCreateUser(SecurityUtils.getUserName());
            qqchFirstArticleEngineeringControl.setCreateTime(DateUtils.getNowDate());
        }
        return qqchFirstArticleEngineeringControlMapper.insertQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
    }

    @Transactional
    public int updateQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.updateQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

            @Transactional
        public int updateQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList) {
            for (QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl : qqchFirstArticleEngineeringControlList) {
                qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
                qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchFirstArticleEngineeringControlMapper.updateQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
        }
    
    @Transactional
    public int deleteQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.deleteQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

            @Transactional
        public int deleteQqchFirstArticleEngineeringControlByPks(List<Long> qqchFirstArticleEngineeringControlPkList) {
            return qqchFirstArticleEngineeringControlMapper.deleteQqchFirstArticleEngineeringControlByPks(qqchFirstArticleEngineeringControlPkList);
        }
    }
