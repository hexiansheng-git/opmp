package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractGeneralMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtils;
import com.hhwy.utils.tree.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:30
 * @remark
 */
@Service
public class XmslContractGeneralServiceImpl implements IXmslContractGeneralService {

    @Autowired
    private XmslContractGeneralMapper xmslContractGeneralMapper;


    public List<? extends TreeVO> getXmslContractGeneral(XmslContractGeneral xmslContractGeneral) {
        List<XmslContractGeneral> list = xmslContractGeneralMapper.getXmslContractGeneral(xmslContractGeneral);
        List<? extends TreeVO> treeVOS = TreeUtils.buildTree(list, 0l);
        return  treeVOS;
    }

    public List<XmslContractGeneral> getXmslContractGeneralList(XmslContractGeneral xmslContractGeneral) {
        return xmslContractGeneralMapper.getXmslContractGeneralList(xmslContractGeneral);
    }

    @Transactional
    public int insertXmslContractGeneral(XmslContractGeneral xmslContractGeneral) {
        xmslContractGeneral.setId(IdWorker.createId());
        xmslContractGeneral.setCreateUser(SecurityUtils.getUserName());
        xmslContractGeneral.setCreateTime(DateUtils.getNowDate());
        return xmslContractGeneralMapper.insertXmslContractGeneral(xmslContractGeneral);
    }

    @Transactional
    public int insertXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList) {
        for (XmslContractGeneral xmslContractGeneral : xmslContractGeneralList) {
            xmslContractGeneral.setId(IdWorker.createId());
            xmslContractGeneral.setCreateUser(SecurityUtils.getUserName());
            xmslContractGeneral.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractGeneralMapper.insertXmslContractGeneralList(xmslContractGeneralList);
    }

    @Transactional
    public int updateXmslContractGeneral(XmslContractGeneral xmslContractGeneral) {
        xmslContractGeneral.setUpdateUser(SecurityUtils.getUserName());
        xmslContractGeneral.setUpdateTime(DateUtils.getNowDate());
        return xmslContractGeneralMapper.updateXmslContractGeneral(xmslContractGeneral);
    }

    @Transactional
    public int updateXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList) {
        for (XmslContractGeneral xmslContractGeneral : xmslContractGeneralList) {
            xmslContractGeneral.setUpdateUser(SecurityUtils.getUserName());
            xmslContractGeneral.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractGeneralMapper.updateXmslContractGeneralList(xmslContractGeneralList);
    }

    @Transactional
    public int deleteXmslContractGeneral(XmslContractGeneral xmslContractGeneral) {
        xmslContractGeneral.setUpdateUser(SecurityUtils.getUserName());
        xmslContractGeneral.setUpdateTime(DateUtils.getNowDate());
        return xmslContractGeneralMapper.deleteXmslContractGeneral(xmslContractGeneral);
    }

    @Transactional
    public int deleteXmslContractGeneralByPks(List<Long> xmslContractGeneralPkList) {
        return xmslContractGeneralMapper.deleteXmslContractGeneralByPks(xmslContractGeneralPkList);
    }
}
