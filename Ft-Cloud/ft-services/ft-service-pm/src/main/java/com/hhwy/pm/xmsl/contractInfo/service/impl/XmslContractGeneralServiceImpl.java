package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractGeneralMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
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
 * @date 2023-07-10 14:17:30
 * @remark
 */
@Service
public class XmslContractGeneralServiceImpl implements IXmslContractGeneralService {

    @Autowired
    private XmslContractGeneralMapper xmslContractGeneralMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private XmslContractInfoMapper xmslContractInfoMapper;


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
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                } else {
                    q.setPid(0l);
                }
            });
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


    /**
     *  提供接口
     * @param xmslContractGeneralParam
     * @return
     */
    @Override
    public List<XmslContractGeneral> provideList(XmslContractGeneral xmslContractGeneralParam) {
        //查询 最大  生效的数据  masterId
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        xmslContractInfo.setVersion(maxVersion);
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        if(xmslContractInfo1!=null){
            //无条件搜索
            XmslContractGeneral general = new XmslContractGeneral();
            general.setMasterId(xmslContractInfo1.getId());
            List<XmslContractGeneral> AllList = xmslContractGeneralMapper.getXmslContractGeneral(general); //这里搜索出来的是全量数据
            Map<Long, XmslContractGeneral> allMap = AllList.stream().collect(Collectors.toMap(XmslContractGeneral::getId, Function.identity()));
            //有条件搜索
            xmslContractGeneralParam.setMasterId(xmslContractInfo1.getId());
            List<XmslContractGeneral> list = xmslContractGeneralMapper.getXmslContractGeneral(xmslContractGeneralParam);//这里是根据前端传的条件搜索出来的结果
            List<XmslContractGeneral> newList = new ArrayList<>();
            newList.addAll(list);
            //遍历寻找父集
            for (XmslContractGeneral xmslContractGeneral : list) {
                //递归查询出他的父集
                List<XmslContractGeneral> xmslContractGenerals = this.selParent(xmslContractGeneral, new ArrayList<XmslContractGeneral>(), allMap);
                newList.addAll(xmslContractGenerals);
            }
            //转树列表
            List<XmslContractGeneral> treeList = ListTreeUtil.formatTree(newList, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);
            return  treeList;
        }
        return null;
    }

    //遍历查父集
     public  List<XmslContractGeneral> selParent(XmslContractGeneral xmslContractGeneral,List<XmslContractGeneral> newList,Map<Long, XmslContractGeneral> allMap){
        if(xmslContractGeneral.getPid()!=0){
             XmslContractGeneral general1 = allMap.get(xmslContractGeneral.getPid());
             newList.add(general1);
             this.selParent(general1,newList,allMap);
         }
         return newList;
     }
}
