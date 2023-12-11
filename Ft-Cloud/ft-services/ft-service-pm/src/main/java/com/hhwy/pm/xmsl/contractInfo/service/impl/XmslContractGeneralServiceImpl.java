package com.hhwy.pm.xmsl.contractInfo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qyzs.manage.qyzsManageContCondition.controller.QyzsManageContConditionController;
import com.hhwy.pm.qyzs.manage.qyzsManageContCondition.domain.QyzsManageContCondition;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractGeneralVo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractGeneralMapper;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;
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
        List<XmslContractGeneral> treeList = ListTreeUtil.formatTree(list, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);
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
        Long masterId = xmslContractGeneral.getMasterId();
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
                child.setMasterId(masterId);
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
    public int deleteXmslContractGeneralByPks(List<Long> xmslContractGeneralPkList, Long masterId) {
        return xmslContractGeneralMapper.deleteXmslContractGeneralByPks(xmslContractGeneralPkList, masterId);
    }


    /**
     *  提供接口
     * @param xmslContractGeneralParam
     * @return
     */
    @Override
    public List<XmslContractGeneral> provideList(XmslContractGeneral xmslContractGeneralParam) {
        List<XmslContractGeneral> resultList = new ArrayList<>();
        //查询 最大  生效的数据  masterId
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setValid("1");
        xmslContractInfo.setVersion(maxVersion);
        XmslContractInfo contractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        if(contractInfo!=null){
            //无条件搜索
            XmslContractGeneral general = new XmslContractGeneral();
            general.setMasterId(contractInfo.getId());
            List<XmslContractGeneral> allList = xmslContractGeneralMapper.getXmslContractGeneral(general); //这里搜索出来的是全量数据

            String name = xmslContractGeneralParam.getName();
            String content = xmslContractGeneralParam.getContent();
            if(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(content)){
                //有条件搜索
                general.setName(name);
                general.setContent(content);
                List<XmslContractGeneral> subList = xmslContractGeneralMapper.getXmslContractGeneralList(general);//这里是根据前端传的条件搜索出来的结果
                resultList = ListTreeUtil.getUpListBySublistToTree(
                        subList,
                        allList,
                        XmslContractGeneral::getId,
                        XmslContractGeneral::getPid,
                        o -> o.getPid() == null,
                        (r, n) -> r.getId().equals(n.getPid()),
                        XmslContractGeneral::getChildren,
                        XmslContractGeneral::setChildren);
            }else {
                resultList = ListTreeUtil.formatTree(allList, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);
            }
        }
        return resultList;
    }

    @Override
    public List<XmslContractGeneral> dataHandler(XmslContractGeneralVo xmslContractGeneralVo) {
        //列表结构
        List<QyzsManageContCondition> knowledgeList = xmslContractGeneralVo.getKnowledgeList();
        //树形结构
        List<XmslContractGeneral> alreadyTreeList = xmslContractGeneralVo.getAlreadyList();
        //弹窗未选择数据直接返回
        if (CollectionUtil.isEmpty(knowledgeList)){
            return alreadyTreeList;
        }
        //todo 获取弹窗选中数据的所有父级和子集
        getParentAndChilderNode()

        //列表已有数据为空，返回弹窗选中的数据
        List<XmslContractGeneral> resultList = new ArrayList<>();
        if (CollectionUtil.isEmpty(alreadyTreeList)){
            //copy 对象
            knowledgeList.forEach(p -> {
                transferBean(resultList, p);
            });
            return ListTreeUtil.formatTree(resultList, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);
        }
        //树转list
        List<XmslContractGeneral> alreadyList = new ArrayList<>();
        treeToList(alreadyTreeList, alreadyList);
        Set<String> alreadyCode = alreadyList.stream().map(XmslContractGeneral::getCode).collect(Collectors.toSet());
        for (QyzsManageContCondition condition : knowledgeList){
            String contConditionNo = condition.getContConditionNo();
            if (alreadyCode.contains(contConditionNo)) {
                //列表中已存在的无需处理
                continue;
            }
            transferBean(resultList, condition);
        }
        //转树列表
        return ListTreeUtil.formatTree(resultList, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), XmslContractGeneral::getChildren, XmslContractGeneral::setChildren);

    }

    //对象拷贝
    private void transferBean(List<XmslContractGeneral> resultList, QyzsManageContCondition p) {
        XmslContractGeneral xmslContractGeneral = new XmslContractGeneral();
        xmslContractGeneral.setCode(p.getContConditionNo());
        xmslContractGeneral.setName(p.getChineseConditonName());
        xmslContractGeneral.setContent(p.getChineseConditionContent());
        xmslContractGeneral.setId(IdWorker.createId());
        xmslContractGeneral.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslContractGeneral.setCreateUserName(SecurityUtils.getUserName());
        xmslContractGeneral.setCreateTime(DateUtils.getNowDate());
        resultList.add(xmslContractGeneral);
    }

    //树转list
    private void treeToList(List<XmslContractGeneral> alreadyTreeList, List<XmslContractGeneral> alreadyList){
        for (XmslContractGeneral general : alreadyTreeList) {
            alreadyList.add(general);
            if (CollectionUtil.isEmpty(general.getChildren())) {
                continue;
            }
            treeToList(general.getChildren(), alreadyList);
        }
    }

    /***
     * 功能描述:
     * @param ids 节点
     * 作者: fushudong
     * 时间: 2023/12/11
     */
    public void getParentAndChilderNode(List<Long> ids){
        //获取知识库合同通用条件列表
        AjaxResult qyzsManageContConditionList = condition.getQyzsManageContConditionList(new QyzsManageContCondition());

        for (Long id : ids){

        }
    }

    @Autowired
    private QyzsManageContConditionController condition;
}
