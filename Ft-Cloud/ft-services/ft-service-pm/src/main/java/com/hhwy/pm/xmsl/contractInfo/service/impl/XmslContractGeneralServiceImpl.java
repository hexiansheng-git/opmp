package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractGeneralMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
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
 * @date 2023-07-10 14:17:30
 * @remark
 */
@Service
public class XmslContractGeneralServiceImpl implements IXmslContractGeneralService {

    @Autowired
    private XmslContractGeneralMapper xmslContractGeneralMapper;


    public List<XmslContractGeneral> getXmslContractGeneral(XmslContractGeneral xmslContractGeneral) {
        List<XmslContractGeneral> list = xmslContractGeneralMapper.getXmslContractGeneral(xmslContractGeneral);
        //转树列表
        List<XmslContractGeneral> treeList = ListTreeUtil.formatTree(list, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);
        return  treeList;
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

    /**
     *   批量新增修改
     *
     * @param xmslContractGeneralList
     * @return
     */
    @Transactional
    public int insertXmslContractGeneralList(List<XmslContractGeneral> xmslContractGeneralList) {
        if(CollectionUtils.isEmpty(xmslContractGeneralList)){
            return 0;
        }
        List<XmslContractGeneral> insertList = new ArrayList<>();
        List<XmslContractGeneral> updateList = new ArrayList<>();
        for (XmslContractGeneral xmslContractGeneral : xmslContractGeneralList) {
            this.recursionSubset(xmslContractGeneral, insertList, updateList);
        }

        if (insertList.size() > 0) {
            xmslContractGeneralMapper.insertXmslContractGeneralList(insertList);
        }
        if (updateList.size() > 0) {
            xmslContractGeneralMapper.updateXmslContractGeneralList(updateList);
        }
        return 1;
    }

    /**
     *  处理子集
     * @param xmslContractGeneral
     * @param insertList
     * @param updateList
     */
    private void recursionSubset(XmslContractGeneral xmslContractGeneral, List<XmslContractGeneral> insertList, List<XmslContractGeneral> updateList) {
        Long id = xmslContractGeneral.getId();
        if (id == null) {
            id = IdWorker.createId();
            xmslContractGeneral.setId(id);
            xmslContractGeneral.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractGeneral.setCreateUserName(SecurityUtils.getUserName());
            xmslContractGeneral.setCreateTime(DateUtils.getNowDate());
            insertList.add(xmslContractGeneral);
        } else {
            xmslContractGeneral.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractGeneral.setUpdateTime(DateUtils.getNowDate());
            updateList.add(xmslContractGeneral);
        }

        List<XmslContractGeneral> children = xmslContractGeneral.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (XmslContractGeneral child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
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
