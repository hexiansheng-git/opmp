package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractSpecialMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSpecialService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtils;
import com.hhwy.utils.tree.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark
 */
@Service
public class XmslContractSpecialServiceImpl implements IXmslContractSpecialService {

    @Autowired
    private XmslContractSpecialMapper xmslContractSpecialMapper;


    public List<? extends TreeVO>   getXmslContractSpecial(XmslContractSpecial xmslContractSpecial) {
        List<XmslContractSpecial> list = xmslContractSpecialMapper.getXmslContractSpecial(xmslContractSpecial);
        List<? extends TreeVO> treeVOS = TreeUtils.buildTree(list, null);
        return treeVOS;
    }

    public List<XmslContractSpecial> getXmslContractSpecialList(XmslContractSpecial xmslContractSpecial) {
        return xmslContractSpecialMapper.getXmslContractSpecialList(xmslContractSpecial);
    }

    @Transactional
    public int insertXmslContractSpecial(XmslContractSpecial xmslContractSpecial) {
        xmslContractSpecial.setId(IdWorker.createId());
        xmslContractSpecial.setCreateUser(SecurityUtils.getUserName());
        xmslContractSpecial.setCreateTime(DateUtils.getNowDate());
        return xmslContractSpecialMapper.insertXmslContractSpecial(xmslContractSpecial);
    }

    @Transactional
    public int insertXmslContractSpecialList(List<XmslContractSpecial> xmslContractSpecialList) {
        for (XmslContractSpecial xmslContractSpecial : xmslContractSpecialList) {
            xmslContractSpecial.setId(IdWorker.createId());
            xmslContractSpecial.setCreateUser(SecurityUtils.getUserName());
            xmslContractSpecial.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractSpecialMapper.insertXmslContractSpecialList(xmslContractSpecialList);
    }

    @Transactional
    public int updateXmslContractSpecial(XmslContractSpecial xmslContractSpecial) {
        xmslContractSpecial.setUpdateUser(SecurityUtils.getUserName());
        xmslContractSpecial.setUpdateTime(DateUtils.getNowDate());
        return xmslContractSpecialMapper.updateXmslContractSpecial(xmslContractSpecial);
    }

    @Transactional
    public int updateXmslContractSpecialList(List<XmslContractSpecial> xmslContractSpecialList) {
        for (XmslContractSpecial xmslContractSpecial : xmslContractSpecialList) {
            xmslContractSpecial.setUpdateUser(SecurityUtils.getUserName());
            xmslContractSpecial.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractSpecialMapper.updateXmslContractSpecialList(xmslContractSpecialList);
    }

    @Transactional
    public int deleteXmslContractSpecial(XmslContractSpecial xmslContractSpecial) {
        xmslContractSpecial.setUpdateUser(SecurityUtils.getUserName());
        xmslContractSpecial.setUpdateTime(DateUtils.getNowDate());
        return xmslContractSpecialMapper.deleteXmslContractSpecial(xmslContractSpecial);
    }

    @Transactional
    public int deleteXmslContractSpecialByPks(List<Long> xmslContractSpecialPkList) {
        return xmslContractSpecialMapper.deleteXmslContractSpecialByPks(xmslContractSpecialPkList);
    }
}
