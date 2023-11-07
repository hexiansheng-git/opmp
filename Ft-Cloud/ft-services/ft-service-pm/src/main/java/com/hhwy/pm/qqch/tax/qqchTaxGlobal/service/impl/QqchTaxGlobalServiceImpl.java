package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper.QqchTaxGlobalMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark
 */
@Service
public class QqchTaxGlobalServiceImpl implements IQqchTaxGlobalService {

    @Resource
    private QqchTaxGlobalMapper qqchTaxGlobalMapper;

    @Resource
    private  CommonMapper commonMapper;
    
    @Resource
    private  IQqchModuleConfirmCaseService moduleConfirmCaseService;
    

    private static final String TN = "qqch_tax_global";


    public QqchTaxGlobal getQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobal(qqchTaxGlobal);
    }

    public List<QqchTaxGlobal> getQqchTaxGlobalList(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobalList(qqchTaxGlobal);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setId(IdWorker.createId());
        qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.insertQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
            qqchTaxGlobal.setId(IdWorker.createId());
            qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
            qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalMapper.insertQqchTaxGlobalList(qqchTaxGlobalList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.updateQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
            qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalMapper.updateQqchTaxGlobalList(qqchTaxGlobalList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.deleteQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxGlobalByPks(List<Long> qqchTaxGlobalPkList) {
        return qqchTaxGlobalMapper.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public CompileEntity<List<QqchTaxGlobal>> list(QqchTaxGlobal dto) throws IOException {
        CompileEntity<List<QqchTaxGlobal>> res = new CompileEntity<>();

        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalMapper.getQqchTaxGlobalListGroup(dto);
        if (CollectionUtils.isEmpty(qqchTaxGlobalList)) {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_4.json");
            String json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            qqchTaxGlobalList = JSONObject.parseArray(json, QqchTaxGlobal.class);
        }
        qqchTaxGlobalList = build(qqchTaxGlobalList, null);
        res.setDto(qqchTaxGlobalList);
        return res;
    }



    public static List<QqchTaxGlobal> build(List<QqchTaxGlobal> treeNodes, Long pid) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        treeNodes.forEach(treeVO -> {

            List<QqchTaxGlobal> nChildren = treeNodes.stream().filter((item) -> treeVO.getTreeId().equals(item.getTreePid()))
                    .collect(Collectors.toList());

            List<QqchTaxGlobal> oChildren = treeVO.getChildren();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(oChildren)) {
                nChildren = CollectionUtils.isEmpty(nChildren) ? new ArrayList<>() : nChildren;
                nChildren.addAll(oChildren);
            }
            if (ObjectNullUtil.isEmpty(nChildren)) {
                treeVO.setLeaf("1");
            }
            treeVO.setChildren(nChildren);
        });
        List<QqchTaxGlobal> collect;
        if (pid == null) {
            collect = treeNodes.stream().filter((item) -> item.getTreePid() == null)
                    .collect(Collectors.toList());
        } else {
            collect = treeNodes.stream().filter((item) -> pid.equals(item.getTreePid()))
                    .collect(Collectors.toList());
        }
        return collect;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<QqchTaxGlobal> dto) {
        
        if (CollectionUtils.isEmpty(dto)) return;
        QqchTaxGlobal qqchTaxGlobal = dto.get(0);
        
        if (PmConstant.ONE.equals(qqchTaxGlobal.getSubmitFlag())) {

            moduleConfirmCaseService.addConfirmRecord(qqchTaxGlobal.getModuleIdentity(), qqchTaxGlobal.getStageIdentity());
        }


        qqchTaxGlobalMapper.deleteByVersionAndYear(qqchTaxGlobal.getYear()+"", qqchTaxGlobal.getVersion());
        
        this.qqchTaxGlobalMapper.insertQqchTaxGlobalList(dto);
    }


}
