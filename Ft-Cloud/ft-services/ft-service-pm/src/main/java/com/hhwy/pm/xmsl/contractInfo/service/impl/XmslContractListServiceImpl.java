package com.hhwy.pm.xmsl.contractInfo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.excel.Util;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ContractListQueryVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListDto;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractListMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
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
import java.util.stream.Collectors;

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
    @Autowired
    private IXmslDrawReviewListService xmslDrawReviewListService;


    /**
     * 获取最新生效版本的主合同清单
     * @return
     */
    @Override
    public List<XmslContractList> getValidMaxVersionContractInventoryList() {
        List<XmslContractList> xmslContractListList = new ArrayList<>();

        XmslContractInfo contractInfo = xmslContractInfoMapper.getValidMaxVersionContractInfo();
        if(contractInfo == null){
            return xmslContractListList;
        }

        Long masterId = contractInfo.getId();
        XmslContractList xmslContractList = new XmslContractList();
        xmslContractList.setMasterId(masterId);
        xmslContractListList = xmslContractListMapper.getXmslContractList(xmslContractList);
        return xmslContractListList;
    }

    public List<XmslContractList> getXmslContractList(XmslContractList xmslContractList) {
        List<XmslContractList> xmslContractList1 = xmslContractListMapper.getXmslContractList(xmslContractList);
        xmslContractList1.forEach(p -> p.setHaveChildren(null));
        List<XmslContractList> treeList = ListTreeUtil.formatTree(xmslContractList1, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), XmslContractList::getChildren, XmslContractList::setChildren);
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
//            XmslContractList xmslContractList = new XmslContractList();
            xmslContractListParam.setMasterId(xmslContractInfo1.getId());
            List<XmslContractList> xmslContractList2 = this.getXmslContractList2(xmslContractListParam);
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
        XmslContractInfo effectCon = this.xmslContractInfoMapper.getValidMaxVersionContractInfo();
        if(CollectionUtils.isEmpty(codeSet) || effectCon == null)
            return new ArrayList<>(2);
        return xmslContractListMapper.getByCodes(effectCon.getId(),codeSet);
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
        Map<String, List<XmslContractListVo>> collect = xmslContractListList.stream().collect(Collectors.groupingBy(XmslContractListVo::getCode));
        if (xmslContractListList.size() != collect.size()) {
            return 500;
        }
        List<XmslContractListVo> insertList = new ArrayList<>();
        List<XmslContractListVo> updateList = new ArrayList<>();
        for (XmslContractListVo xmslContract : xmslContractListList) {
            this.recursionSubset(xmslContract, insertList, updateList);
        }
        //数据校验
        List<XmslContractListVo> adllList = new ArrayList<>();
        adllList.addAll(insertList);
        adllList.addAll(updateList);
        List<XmslContractListVo> collect1 = adllList.stream().filter(p -> p.getHaveChildren() == 0 ).collect(Collectors.toList());
        List<XmslContractListVo> collect2 = collect1.stream()
                .filter(p -> StrUtil.isBlank(p.getCode()) || p.getWinNum() == null || p.getWinUnitPrice() == null)
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect2))
           return 400;
        if (insertList.size() > 0) {
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                }
            });
            xmslContractListMapper.insertXmslContractListList(insertList);
        }
        if (updateList.size() > 0) {
            xmslContractListMapper.updateXmslContractListList(updateList);
        }
        //维护祖籍id
        this.handlerAncestors(null, null);
        //回填有效合同金额
        insertList.addAll(updateList);
        List<XmslContractListVo> lastChild = insertList.stream()
                .filter(p -> 0 == p.getHaveChildren() && "1".equals(p.getListType()))
                .collect(Collectors.toList());
        BigDecimal effectiveAmout = lastChild.stream().map(p -> p.getWinAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setId(xmslContractListList.get(0).getMasterId());
        xmslContractInfo.setEffectiveAmout(NumberUtil.null2Zero(effectiveAmout));
        xmslContractInfoMapper.updateXmslContractInfo(xmslContractInfo);
        return 1;
    }

    /**
     *   递归
     * @param xmslContract
     * @param insertList
     * @param updateList
     */
    private void recursionSubset(XmslContractListVo xmslContract, List<XmslContractListVo> insertList, List<XmslContractListVo> updateList) {
        String id =xmslContract.getId() ;
        Long masterId = xmslContract.getMasterId();
        List<XmslContractListVo> children = xmslContract.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            xmslContract.setHaveChildren(0);
        }else {
            xmslContract.setHaveChildren(1);
        }
        if (id == null) {
            id = IdWorker.createId()+"";
            xmslContract.setId(id);
            xmslContract.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContract.setCreateUserName(SecurityUtils.getUserName());
            xmslContract.setCreateTime(DateUtils.getNowDate());
            insertList.add(xmslContract);
        } else {
            xmslContract.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslContract.setUpdateTime(DateUtils.getNowDate());
            updateList.add(xmslContract);
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (XmslContractListVo child : children) {
                child.setPid(Long.valueOf(id));
                child.setMasterId(masterId);
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
    public int deleteXmslContractListByPks(List<Long> xmslContractListPkList, Long masterId) {
        return xmslContractListMapper.deleteXmslContractListByPks(xmslContractListPkList, masterId);
    }

    @Override
    public void handlerAncestors() {
        handlerAncestors(null, null);
    }
    /**'
     * 填充祖籍id name
     * @param func
     */
    public void handlerAncestors(Function<XmslContractList,XmslContractList> func, String masterId) {
        long begin = System.currentTimeMillis();
        try{
            List<XmslContractList> list = this.xmslContractListMapper.getXmslContractList(new XmslContractList());
            //祖级id、名称map
            Map<Long,List<Long>> parentIdMap = new HashMap<>(list.size());
//            Map<Long,List<String>> parentNameMap = new HashMap<>(list.size());
//            Map<Long,String> idNameMap = new HashMap<>(list.size());
            //是否为父级
            Function<String,Boolean> isParentFunc = (s)->{return StringUtils.isBlank(s) || StringUtils.equalsAny(s,"-1","0");};
            //遍历，获取祖级id、名称
            for (XmslContractList temp : list) {
                if (func != null) {
                    func.apply(temp);
                }
//                idNameMap.put(temp.getId(), temp.getChineseName().trim());
                //若有父级，则放入parentIdMap、parentNameMap
                if (isParentFunc.apply(String.valueOf(temp.getPid()))) {
                    parentIdMap.put(temp.getId(), ListUtil.toList(temp.getId()));
//                    parentNameMap.put(temp.getId(), ListUtil.toList(temp.getChineseName()));
                    continue;
                }
                Long pid = temp.getPid();
//                String pname = idNameMap.get(pid);
//                if (StringUtils.isBlank(pname))
//                    logger.warn("合同清单同步祖级名称ID时，未找到父级名称,子级ID:{},父级ID:{}", temp.getId(), pid);
                List<Long> pidList = ListUtils.defaultIfNull(parentIdMap.get(pid), new ArrayList<>());
//                List<String> pnameList = ListUtils.defaultIfNull(parentNameMap.get(pid), new ArrayList<>());
                parentIdMap.put(temp.getId(), copyAndAdd(pidList, temp.getId()));
//                parentNameMap.put(temp.getId(), copyAndAdd(pnameList, temp.getChineseName()));
            }
            //填充祖级id、名称
            for (XmslContractList temp : list) {
                if (isParentFunc.apply(StrUtil.utf8Str(temp.getPid()))) {
//                    temp.setAncestors(String.valueOf(temp.getId()));
//                    temp.setAncestorsName(temp.getChineseName());
                    continue;
                }
                temp.setAncestors(StringUtils.join(parentIdMap.get(temp.getId()), ","));
//                temp.setAncestorsName(StringUtils.join(parentNameMap.get(temp.getId()), ","));
            }
            xmslContractListMapper.updateXmslContractListList1(list);
        }finally{
            long usemills = System.currentTimeMillis()-begin;
            logger.debug("合同清单同步祖级名称ID，耗时:{}毫秒",usemills);
        }
    }

    private <T> List<T> copyAndAdd(List<T> list,T str){
        List<T> result = new ArrayList<>(list);
        result.add(str);
        return result;
    }

    /**
     * 查询有效合同金额：（主合同清单，清单类型是普通清单的所有末级节点的含税金额的合计）
     */
    @Override
    public XmslContractList getContractPriceByListtype(XmslContractList xmslContractList) {
        return xmslContractListMapper.getContractPriceByListtype(xmslContractList);
    }


    /**
     * 4.1.4合同清单弹窗
     * @param queryVo
     * @return
     */
    @Override
    public List<XmslContractList> popUpWindows(ContractListQueryVo queryVo) {
        List<XmslContractList> resultList = new ArrayList<>();

        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        xmslContractInfo.setVersion(maxVersion);
        //查询有效的合同信息
        xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);

        if(xmslContractInfo != null){
            Long masterId = xmslContractInfo.getId();
            XmslContractList xmslContractList = new XmslContractList();
            xmslContractList.setMasterId(masterId);
            /*全量数据*/
            List<XmslContractList> allList = xmslContractListMapper.getXmslContractList(xmslContractList);

            String code = queryVo.getCode();
            String chineseName = queryVo.getChineseName();
            if(StringUtils.isNotBlank(code) || StringUtils.isNotBlank(chineseName)){
                xmslContractList.setCode(code);
                xmslContractList.setChineseName(chineseName);
                /*根据条件查询出来的数据*/
                List<XmslContractList> subList = xmslContractListMapper.getXmslContractList(xmslContractList);
                //组装祖级id
                Set<String> ancestorsSet = new HashSet<>();
                for (XmslContractList contractList : subList) {
                    String ancestors = contractList.getAncestors();
                    if(StringUtils.isNotBlank(ancestors)){
                        String[] split = ancestors.split(",");
                        ancestorsSet.addAll(Arrays.asList(split));
                    }
                }
                StringBuilder ancestors = new StringBuilder();
                for (String s : ancestorsSet) {
                    ancestors.append(s).append(",");
                }
                //根据祖级id查询数据
                List<XmslContractList> rList = xmslContractListMapper.getByAncestors(masterId,ancestors.toString());
                this.setDrawReview(rList);
                resultList = ListTreeUtil.formatTree(
                        rList,
                        o -> o.getPid() == null,
                        (r, n) -> r.getId().equals(n.getPid()),
                        XmslContractList::getChildren,
                        XmslContractList::setChildren);
            }else {
                this.setDrawReview(allList);
                resultList = ListTreeUtil.formatTree(
                        allList,
                        o -> o.getPid() == null,
                        (r, n) -> r.getId().equals(n.getPid()),
                        XmslContractList::getChildren,
                        XmslContractList::setChildren);
            }
        }
        return resultList;
    }

    /**
     * 设置所属wbs和图纸复核数量
     * @param list
     */
    public void setDrawReview(List<XmslContractList> list){
        /*全量图纸复核数据*/
        List<XmslDrawReviewList> drawReviewListList = xmslDrawReviewListService.getFullList();

        for (XmslContractList contractList : list) {
            String code = contractList.getCode();
            StringBuilder wbsCodes = new StringBuilder();
            StringBuilder wbsNames = new StringBuilder();
            BigDecimal listCheckNum = BigDecimal.ZERO;

            for (XmslDrawReviewList drawReviewList : drawReviewListList) {
                if(code.equals(drawReviewList.getListCode())){
                    String wbsCode = drawReviewList.getWbsCode();
                    String wbsName = drawReviewList.getWbsCode();
                    if(StringUtils.isNotBlank(wbsCode)){
                        wbsCodes.append(wbsCode).append(",");
                        wbsNames.append(wbsName).append(",");
                    }

                    BigDecimal checkNum = drawReviewList.getCheckNum();
                    if(checkNum != null){
                        listCheckNum = listCheckNum.add(checkNum);
                    }
                }
            }
            contractList.setWbsCodes(wbsCodes.toString());
            contractList.setWbsNames(wbsNames.toString());
            contractList.setListCheckNum(listCheckNum);
        }
    }

    /***
     * 功能描述:  处理导入数据的层级结构
     * 作者: fushudong
     * 时间: 2023/10/17
     */
    @Override
    public List<ImportXmslContractListVo> parseLevelStruct(List<ImportXmslContractListVo> importXmslContractListVos) {
        Util util = new Util();
        importXmslContractListVos.forEach(p -> {
            //设置DataFrom("new") 用于前端保存时清空id，因为保存时接口会根据id判断做修改还是新增
            p.setDataFrom("new");
            //字段值翻译
            String s = util.reverseDict("list_type", p.getListType());
            p.setListType(s);
            if (p.getWinUnitPrice() == null) {
                p.setWinUnitPrice(BigDecimal.ZERO);
            }
        });
        Map<String, ImportXmslContractListVo> collect = importXmslContractListVos.stream()
                .filter(p -> StrUtil.isNotBlank(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< importXmslContractListVos.size(); i++) {
            ImportXmslContractListVo importXmslContractListVo = importXmslContractListVos.get(i);
            importXmslContractListVo.setId(IdUtil.getSnowflakeNextId());
            String innerCode = importXmslContractListVo.getInnerCode();
            if (StrUtil.isBlank(innerCode)) continue;
            if (!innerCode.contains("-")) {
                //第一层级
                importXmslContractListVo.setSort(Integer.valueOf(innerCode));
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.length() - 1);
            //获取当前数据的父层级
            ImportXmslContractListVo parent = collect.get(parentCode);
            Assert.notNull(parent, "层级码：{} 未找到父层级：{}，请确认是否存在", innerCode, parentCode);
            //获取父层级的children，将当前记录add进去
            List<ImportXmslContractListVo> children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            importXmslContractListVo.setSort(Integer.valueOf(curentCode));
            importXmslContractListVo.setPid(parent.getId());
            children.add(importXmslContractListVo);
        }
        return new ArrayList<>(collect.values());
    }

    @Override
    public void updateToRemoveDisable(Long id) {
        xmslContractListMapper.updateToRemoveDisable(id);
    }
}
