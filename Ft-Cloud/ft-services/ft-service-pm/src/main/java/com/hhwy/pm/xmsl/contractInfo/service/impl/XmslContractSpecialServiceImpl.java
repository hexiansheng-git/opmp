package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractSpecialMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSpecialService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark
 */
@Service
public class XmslContractSpecialServiceImpl implements IXmslContractSpecialService {

    @Autowired
    private XmslContractSpecialMapper xmslContractSpecialMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private XmslContractInfoMapper xmslContractInfoMapper;


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

    @Override
    public List<XmslContractSpecial> provideList(XmslContractSpecial xmslContractSpecialParam) {
        //查询 最大  生效的数据  masterId
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        xmslContractInfo.setVersion(maxVersion);
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        if(xmslContractInfo1!=null){
            //无条件搜索
            XmslContractSpecial special = new XmslContractSpecial();
            special.setMasterId(xmslContractInfo1.getId());
            List<XmslContractSpecial> AllList = xmslContractSpecialMapper.getXmslContractSpecialList(special); //这里搜索出来的是全量数据
            Map<Long, XmslContractSpecial> allMap = AllList.stream().collect(Collectors.toMap(XmslContractSpecial::getId, Function.identity()));
            //有条件搜索
            xmslContractSpecialParam.setMasterId(xmslContractInfo1.getId());
            List<XmslContractSpecial> list = xmslContractSpecialMapper.getXmslContractSpecialList(xmslContractSpecialParam);//这里是根据前端传的条件搜索出来的结果
            List<XmslContractSpecial> newList = new ArrayList<>();
            newList.addAll(list);
            //遍历寻找父集
            for (XmslContractSpecial xmslContractSpecial : list) {
                //递归查询出他的父集
                List<XmslContractSpecial> xmslContractGenerals = this.selParent(xmslContractSpecial, new ArrayList<XmslContractSpecial>(), allMap);
                newList.addAll(xmslContractGenerals);
            }
            //转树列表
            List<XmslContractSpecial> treeList = ListTreeUtil.formatTree(newList, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractSpecial::getChildren, XmslContractSpecial::setChildren);
            return  treeList;
        }
        return null;
    }


    //遍历查父集
    public  List<XmslContractSpecial> selParent(XmslContractSpecial xmslContractSpecial,List<XmslContractSpecial> newList,Map<Long, XmslContractSpecial> allMap){
        if(xmslContractSpecial.getPid()!=0){
            XmslContractSpecial special1 = allMap.get(xmslContractSpecial.getPid());
            newList.add(special1);
            this.selParent(special1,newList,allMap);
        }
        return newList;
    }
}
