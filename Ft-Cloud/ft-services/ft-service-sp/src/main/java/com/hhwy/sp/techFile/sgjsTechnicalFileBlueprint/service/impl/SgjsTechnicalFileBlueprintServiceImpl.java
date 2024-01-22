package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service.impl;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprintParam;
import com.hhwy.sp.utils.TreeNodeUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.mapper.SgjsTechnicalFileBlueprintMapper;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service.ISgjsTechnicalFileBlueprintService;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author fsd
 * @date 2024-01-22 08:54:05
 * @remark
 */
@Service
public class SgjsTechnicalFileBlueprintServiceImpl implements ISgjsTechnicalFileBlueprintService {

    @Autowired
    private SgjsTechnicalFileBlueprintMapper sgjsTechnicalFileBlueprintMapper;


    public SgjsTechnicalFileBlueprint getSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        return sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    public List<SgjsTechnicalFileBlueprint> getSgjsTechnicalFileBlueprintList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint) {
        List<SgjsTechnicalFileBlueprint> list = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprint);
        List<SgjsTechnicalFileBlueprint> TreeList = TreeUtil.build(list, null);
        if (CollUtil.isNotEmpty(list) && CollUtil.isEmpty(TreeList)) {
            List<SgjsTechnicalFileBlueprint> allList = sgjsTechnicalFileBlueprintMapper.getSgjsTechnicalFileBlueprintList(new SgjsTechnicalFileBlueprintParam());
            List<SgjsTechnicalFileBlueprint> ancestral = TreeNodeUtil.getAncestral(allList, list);
            TreeList = TreeUtil.build(ancestral, null);
        }
        return TreeList;
    }

    @Transactional
    public int insertSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        sgjsTechnicalFileBlueprint.setId(IdWorker.createId());
        sgjsTechnicalFileBlueprint.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalFileBlueprint.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalFileBlueprintMapper.insertSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int insertSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList) {
        if (CollUtil.isEmpty(sgjsTechnicalFileBlueprintList)) {
            sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprint(new SgjsTechnicalFileBlueprint());
            return 1;
        }
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprints = TreeUtil.treeToList(sgjsTechnicalFileBlueprintList);
        for (SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint : sgjsTechnicalFileBlueprints) {
            sgjsTechnicalFileBlueprint.setId(IdWorker.createId());
            sgjsTechnicalFileBlueprint.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalFileBlueprint.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalFileBlueprintMapper.insertSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprints);
    }

    @Transactional
    public int updateSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalFileBlueprintMapper.updateSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int updateSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList) {
        for (SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint : sgjsTechnicalFileBlueprintList) {
            sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalFileBlueprintMapper.updateSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintList);
    }

    @Transactional
    public int deleteSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint) {
        sgjsTechnicalFileBlueprint.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalFileBlueprint.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprint);
    }

    @Transactional
    public int deleteSgjsTechnicalFileBlueprintByPks(List<Long> sgjsTechnicalFileBlueprintPkList) {
        return sgjsTechnicalFileBlueprintMapper.deleteSgjsTechnicalFileBlueprintByPks(sgjsTechnicalFileBlueprintPkList);
    }
}
