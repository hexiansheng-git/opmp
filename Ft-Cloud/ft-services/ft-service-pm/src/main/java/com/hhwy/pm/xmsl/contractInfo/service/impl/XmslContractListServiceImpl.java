package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractListMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
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
 * @date 2023-07-10 14:17:42
 * @remark
 */
@Service
public class XmslContractListServiceImpl implements IXmslContractListService {

    @Autowired
    private XmslContractListMapper xmslContractListMapper;
    @Autowired
    private XmslContractInfoMapper xmslContractInfoMapper;


    public List<XmslContractList> getXmslContractList(XmslContractList xmslContractList) {
        List<XmslContractList> xmslContractList1 = xmslContractListMapper.getXmslContractList(xmslContractList);
        List<XmslContractList> treeList = ListTreeUtil.formatTree(xmslContractList1, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractList::getChildren, XmslContractList::setChildren);
        return treeList;
    }

    @Override
    public List<XmslContractList> getXmslContractList2(XmslContractList xmslContractListParam) {
        List<XmslContractList> xmslContractList1 = xmslContractListMapper.getXmslContractList(xmslContractListParam);
        return xmslContractList1;
    }

    public List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList) {
        return xmslContractListMapper.getXmslContractListList(xmslContractList);
    }

    /**
     *  获取生效的清单列表
     *
     * @param xmslContractListParam
     * @return
     */
    @Override
    public List<XmslContractList> getEffectList(XmslContractList xmslContractListParam) {
        //查询生效的数据 masterId
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        XmslContractList xmslContractList = new XmslContractList();
        xmslContractList.setMasterId(xmslContractInfo1.getId());
        List<XmslContractList> xmslContractList2 = this.getXmslContractList2(xmslContractList);
        return xmslContractList2;
    }

    @Transactional
    public int insertXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setId(IdWorker.createId());
        xmslContractList.setCreateUser(SecurityUtils.getUserName());
        xmslContractList.setCreateTime(DateUtils.getNowDate());
        return xmslContractListMapper.insertXmslContractList(xmslContractList);
    }

    @Transactional
    public int insertXmslContractListList(List<XmslContractListVo> xmslContractListList) {
        if(CollectionUtils.isEmpty(xmslContractListList)){
            return 0;
        }

        List<XmslContractListVo> insertList = new ArrayList<>();
        List<XmslContractListVo> updateList = new ArrayList<>();
        for (XmslContractListVo xmslContractList : xmslContractListList) {
            this.recursionSubset(xmslContractList, insertList, updateList);
        }

        if (insertList.size() > 0) {
            xmslContractListMapper.insertXmslContractListList(insertList);
        }
        if (updateList.size() > 0) {
            xmslContractListMapper.updateXmslContractListList(updateList);
        }
        return 1;
    }

    /**
     *   递归
     * @param xmslContractList
     * @param insertList
     * @param updateList
     */
    private void recursionSubset(XmslContractListVo xmslContractList, List<XmslContractListVo> insertList, List<XmslContractListVo> updateList) {
        Long id = xmslContractList.getId();
        if (id == null) {
            id = IdWorker.createId();
            xmslContractList.setId(id);
            xmslContractList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractList.setCreateUserName(SecurityUtils.getUserName());
            xmslContractList.setCreateTime(DateUtils.getNowDate());
            insertList.add(xmslContractList);
        } else {
            xmslContractList.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContractList.setUpdateTime(DateUtils.getNowDate());
            updateList.add(xmslContractList);
        }

        List<XmslContractListVo> children = xmslContractList.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (XmslContractListVo child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }

    @Transactional
    public int updateXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setUpdateUser(SecurityUtils.getUserName());
        xmslContractList.setUpdateTime(DateUtils.getNowDate());
        return xmslContractListMapper.updateXmslContractList(xmslContractList);
    }


    @Transactional
    public int deleteXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setUpdateUser(SecurityUtils.getUserName());
        xmslContractList.setUpdateTime(DateUtils.getNowDate());
        return xmslContractListMapper.deleteXmslContractList(xmslContractList);
    }

    @Transactional
    public int deleteXmslContractListByPks(List<Long> xmslContractListPkList) {
        return xmslContractListMapper.deleteXmslContractListByPks(xmslContractListPkList);
    }


}
