package com.hhwy.sd.designDocumentApproval.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designDocumentApproval.mapper.KcsjDesignDocumentApprovalMapper;
import com.hhwy.sd.designDocumentApproval.service.IKcsjDesignDocumentApprovalService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark 
 */
@Service
public class KcsjDesignDocumentApprovalServiceImpl implements IKcsjDesignDocumentApprovalService {

    @Autowired
    private KcsjDesignDocumentApprovalMapper kcsjDesignDocumentApprovalMapper;

                                                                                                                                                                                                                                                                                                                                                                                                    
    public KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        return kcsjDesignDocumentApprovalMapper.getKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    public List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        return kcsjDesignDocumentApprovalMapper.getKcsjDesignDocumentApprovalList(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setId(IdWorker.createId());
        kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int insertKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList) {
        for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : kcsjDesignDocumentApprovalList) {
            kcsjDesignDocumentApproval.setId(IdWorker.createId());
            kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserName());
            kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalList);
    }

    @Transactional
    public int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.updateKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

            @Transactional
        public int updateKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList) {
            for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : kcsjDesignDocumentApprovalList) {
                kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
                kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
            }
            return kcsjDesignDocumentApprovalMapper.updateKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalList);
        }
    
    @Transactional
    public int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.deleteKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

            @Transactional
        public int deleteKcsjDesignDocumentApprovalByPks(List<Long> kcsjDesignDocumentApprovalPkList) {
            return kcsjDesignDocumentApprovalMapper.deleteKcsjDesignDocumentApprovalByPks(kcsjDesignDocumentApprovalPkList);
        }
    }
