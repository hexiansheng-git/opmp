package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractSpecialMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSpecialService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


    public List<XmslContractSpecial>   getXmslContractSpecial(XmslContractSpecial xmslContractSpecial) {
        List<XmslContractSpecial> list = xmslContractSpecialMapper.getXmslContractSpecial(xmslContractSpecial);
        //转树列表
        List<XmslContractSpecial> treeList = ListTreeUtil.formatTree(list, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractSpecial::getChildren, XmslContractSpecial::setChildren);
        return treeList;
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
        if(CollectionUtils.isEmpty(xmslContractSpecialList)){
            return 0;
        }
        List<XmslContractSpecial> insertList = new ArrayList<>();
        List<XmslContractSpecial> updateList = new ArrayList<>();
        for (XmslContractSpecial xmslContractSpecial : xmslContractSpecialList) {
            this.recursionSubset(xmslContractSpecial, insertList, updateList);
        }
        if (insertList.size() > 0) {
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                } else {
                    q.setPid(0l);
                }
            });
            xmslContractSpecialMapper.insertXmslContractSpecialList(insertList);
        }
        if (updateList.size() > 0) {
            xmslContractSpecialMapper.updateXmslContractSpecialList(updateList);
        }
        return 1;
    }

    private void recursionSubset(XmslContractSpecial xmslContractSpecial, List<XmslContractSpecial> insertList, List<XmslContractSpecial> updateList) {
        Long id = xmslContractSpecial.getId();
        if (id == null) {
            id = IdWorker.createId();
            xmslContractSpecial.setId(id);
            xmslContractSpecial.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractSpecial.setCreateUserName(SecurityUtils.getUserName());
            xmslContractSpecial.setCreateTime(DateUtils.getNowDate());
            insertList.add(xmslContractSpecial);
        } else {
            xmslContractSpecial.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractSpecial.setUpdateTime(DateUtils.getNowDate());
            updateList.add(xmslContractSpecial);
        }

        List<XmslContractSpecial> children = xmslContractSpecial.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (XmslContractSpecial child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
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
