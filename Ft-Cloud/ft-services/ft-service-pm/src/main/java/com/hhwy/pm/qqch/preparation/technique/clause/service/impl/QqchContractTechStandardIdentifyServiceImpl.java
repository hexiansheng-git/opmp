package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechStandardIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtils;
import com.hhwy.utils.tree.TreeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Service
public class QqchContractTechStandardIdentifyServiceImpl implements IQqchContractTechStandardIdentifyService {

    @Autowired
    private QqchContractTechStandardIdentifyMapper qqchContractTechStandardIdentifyMapper;


    public QqchContractTechStandardIdentify getQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        return qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    public List<QqchContractTechStandardIdentify> getQqchContractTechStandardIdentifyList(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        return qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int insertQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setId(IdWorker.createId());
        qqchContractTechStandardIdentify.setCreateUser(SecurityUtils.getUserName());
        qqchContractTechStandardIdentify.setCreateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .insertQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int insertQqchContractTechStandardIdentifyList(
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList) {
        for (QqchContractTechStandardIdentify qqchContractTechStandardIdentify : qqchContractTechStandardIdentifyList) {
            qqchContractTechStandardIdentify.setId(IdWorker.createId());
            qqchContractTechStandardIdentify.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setCreateUserName(SecurityUtils.getUserName());
            qqchContractTechStandardIdentify.setCreateTime(DateUtils.getNowDate());
        }
        return qqchContractTechStandardIdentifyMapper
            .insertQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyList);
    }

    @Transactional
    public int updateQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .updateQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int updateQqchContractTechStandardIdentifyList(
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList) {
        for (QqchContractTechStandardIdentify qqchContractTechStandardIdentify : qqchContractTechStandardIdentifyList) {
            qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchContractTechStandardIdentifyMapper
            .updateQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyList);
    }

    @Transactional
    public int deleteQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        qqchContractTechStandardIdentify.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchContractTechStandardIdentify.setUpdateTime(DateUtils.getNowDate());
        return qqchContractTechStandardIdentifyMapper
            .deleteQqchContractTechStandardIdentify(qqchContractTechStandardIdentify);
    }

    @Transactional
    public int deleteQqchContractTechStandardIdentifyByPks(List<Long> qqchContractTechStandardIdentifyPkList) {
        return qqchContractTechStandardIdentifyMapper
            .deleteQqchContractTechStandardIdentifyByPks(qqchContractTechStandardIdentifyPkList);
    }

    /**
     * 树列表查询
     * @param qqchContractTechStandardIdentify
     * @return
     */
    public List<? extends TreeVO> getTreeList(QqchContractTechStandardIdentify qqchContractTechStandardIdentify) {
        List<QqchContractTechStandardIdentify> list = qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentify);
        List<? extends TreeVO> treeVOS = TreeUtils.buildTree(list, 0l);
        return treeVOS;
    }
}
