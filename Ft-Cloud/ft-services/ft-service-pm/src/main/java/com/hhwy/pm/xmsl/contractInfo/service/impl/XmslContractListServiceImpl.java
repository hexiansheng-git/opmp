package com.hhwy.pm.xmsl.contractInfo.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListDto;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractListMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
@Service
public class XmslContractListServiceImpl implements IXmslContractListService {

    private Logger logger= LoggerFactory.getLogger(XmslContractListServiceImpl.class);

    @Autowired
    private XmslContractListMapper xmslContractListMapper;
    @Autowired
    private XmslContractInfoMapper xmslContractInfoMapper;
    @Autowired
    private CommonMapper commonMapper;


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
        //查询 最大  生效的数据  masterId
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        xmslContractInfo.setVersion(maxVersion);
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        if(xmslContractInfo1!=null){
            XmslContractList xmslContractList = new XmslContractList();
            xmslContractList.setMasterId(xmslContractInfo1.getId());
            List<XmslContractList> xmslContractList2 = this.getXmslContractList2(xmslContractList);
            return xmslContractList2;
        }
        return null;
    }



    @Override
    public List<XmslContractList> getByIds(Long[] ids) {
        if(ArrayUtils.isEmpty(ids))
            return new ArrayList<>(2);
        return xmslContractListMapper.getByIds(ids);
    }

    @Override
    public List<XmslContractList> getByCodes(Set<String> codeSet) {
        if(CollectionUtils.isEmpty(codeSet))
            return new ArrayList<>(2);
        return xmslContractListMapper.getByCodes(codeSet);
    }

    @Transactional
    public int insertXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setId(IdWorker.createId()+"");
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
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                } else {
                    q.setPid(0l);
                }
            });
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
        String id =xmslContractList.getId() ;
        if (id == null) {
            id = IdWorker.createId()+"";
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
                child.setPid(Long.valueOf(id));
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }

    @Transactional
    public void updateXmslContractList(XmslContractListDto dto) {
        List<XmslContractListVo> list = dto.getList();
        List<XmslContractListVo> addList = new ArrayList<>();
        List<XmslContractListVo> updateList = new ArrayList<>();
        //前端新增数据的ID都为uid,需要替换为后端生成的id
        Map<String,String> idRepalceMap = new ConcurrentHashMap<>(list.size()/2);
        list.parallelStream().forEach(temp->{
            if(temp.getId().length()< 21){
                new AddBaseInfoUtil<>().updateBaseEntity(temp);
                updateList.add(temp);
                return;
            }
            String id = getSnowId(temp.getId(),idRepalceMap);
            temp.setId(id);
//            //替换祖级id
            String[] ances = temp.getAncestors().split(",");
            List<String> anceList = new ArrayList<>(ances.length);
            for (int i = 0; i < ances.length; i++) {
                String snowId = getSnowId(ances[i],idRepalceMap);
                anceList.add(snowId);
            }
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            String charStr = StringUtils.isBlank(temp.getAncestors())?"":",";
            temp.setAncestors(temp.getAncestors()+charStr+temp.getId());
            addList.add(temp);
        });
        if(CollectionUtils.isNotEmpty(addList))
            this.xmslContractListMapper.insertXmslContractListList(addList);
        if(CollectionUtils.isNotEmpty(updateList))
            this.xmslContractListMapper.updateXmslContractListList(updateList);
        //删除
        if(StringUtils.isNotBlank(dto.getDelIds())){
            this.xmslContractListMapper.deleteByIds(Arrays.asList(Convert.toLongArray(dto.getDelIds())));
        }
    }

    private String getSnowId(String id, Map<String, String> idRepalceMap) {
        if(id.length() < 21)
            return id;
        String temp = idRepalceMap.get(id);
        return temp == null?IdWorker.createId()+"":temp;
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

    @Override
    public void handlerAncestors() {
        handlerAncestors(null);
    }
    /**'
     * 填充祖籍id name
     * @param func
     */
    public void handlerAncestors(Function<XmslContractList,XmslContractList> func) {
        long begin = System.currentTimeMillis();
        try{
            List<XmslContractList> list = this.xmslContractListMapper.getXmslContractList(new XmslContractList());
            //祖级id、名称map
            Map<String,List<String>> parentIdMap = new HashMap<>(list.size());
            Map<String,List<String>> parentNameMap = new HashMap<>(list.size());
            Map<String,String> idNameMap = new HashMap<>(list.size());
            //是否为父级
            Function<String,Boolean> isParentFunc = (s)->{return StringUtils.isBlank(s) || StringUtils.equalsAny(s,"-1","0");};
            //遍历，获取祖级id、名称
            for (int i = 0; i < list.size(); i++) {
                XmslContractList temp = list.get(i);
                if(func != null){
                    func.apply(temp);
                }
                idNameMap.put(temp.getId(),temp.getChineseName().trim());
                //若有父级，则放入parentIdMap、parentNameMap
                if(isParentFunc.apply(temp.getPid()+"")){
                    parentIdMap.put(temp.getId(), ListUtil.toList(temp.getId()));
                    parentNameMap.put(temp.getId(),ListUtil.toList(temp.getChineseName()));
                    continue;
                }
                String pid = temp.getPid()+"";
                String pname = idNameMap.get(pid);
                if(StringUtils.isBlank(pname))
                    logger.warn("合同清单同步祖级名称ID时，未找到父级名称,子级ID:{},父级ID:{}",temp.getId(),pid);
                List<String> pidList = ListUtils.defaultIfNull(parentIdMap.get(pid),new ArrayList<>());
                List<String> pnameList = ListUtils.defaultIfNull(parentNameMap.get(pid),new ArrayList<>());
                parentIdMap.put(temp.getId(),copyAndAdd(pidList,temp.getId()));
                parentNameMap.put(temp.getId(),copyAndAdd(pnameList,temp.getChineseName()));
            }
            //填充祖级id、名称
            for (int i = 0; i < list.size(); i++) {
                XmslContractList temp = list.get(i);
                if(isParentFunc.apply(temp.getPid()+"")){
                    temp.setAncestors(temp.getId());
                    temp.setAncestorsName(temp.getChineseName());
                    continue;
                }
                temp.setAncestors(StringUtils.join(parentIdMap.get(temp.getId()),","));
                temp.setAncestorsName(StringUtils.join(parentNameMap.get(temp.getId()),","));
            }
            xmslContractListMapper.updateXmslContractListList1(list);
        }finally{
            long usemills = System.currentTimeMillis()-begin;
            logger.debug("合同清单同步祖级名称ID，耗时:{}毫秒",usemills);
        }
    }

    private List<String> copyAndAdd(List<String> list,String str){
        List<String> result = new ArrayList<>(list);
        result.add(str);
        return result;
    }


}
