package com.hhwy.sp.buildScheme.sgjsBuildScheme.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.buildScheme.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildScheme.sgjsBuildScheme.mapper.SgjsBuildSchemeMapper;
import com.hhwy.sp.buildScheme.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.sp.buildScheme.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildScheme.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:26
 * @remark
 */
@Service
public class SgjsBuildSchemeServiceImpl implements ISgjsBuildSchemeService {

    @Autowired
    private SgjsBuildSchemeMapper sgjsBuildSchemeMapper;

    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;

    //详情
    public SgjsBuildScheme detail(SgjsBuildScheme sgjsBuildScheme) {
        Long id = sgjsBuildScheme.getId();
        SgjsBuildScheme result = sgjsBuildSchemeMapper.getSgjsBuildScheme(sgjsBuildScheme);
        if ( result == null ) return sgjsBuildScheme;
        SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
        sgjsBuildSchemeList.setForeignId(id);
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeList);
        if (CollUtil.isNotEmpty(sgjsBuildSchemeListList)) {
            result.setChildren(sgjsBuildSchemeListList);
        }
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.SGJS_BUILD_SCHEME);
        return result;
    }

    public SgjsBuildScheme getSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        return sgjsBuildSchemeMapper.getSgjsBuildScheme(sgjsBuildScheme);
    }

    //台账、历史记录
    public List<SgjsBuildScheme> getSgjsBuildSchemeList(SgjsBuildScheme sgjsBuildScheme) {
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeMapper.getSgjsBuildSchemeList(sgjsBuildScheme);
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildSchemeList, FlowEnum.SGJS_BUILD_SCHEME);
        return sgjsBuildSchemeList;
    }

    //同步前期策划施工技术策划3.4.2


    //保存、提交
    @Transactional
    public Long insertSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        Long id = sgjsBuildScheme.getId();
        if (null == id) {
            //新增
            id = IdWorker.createId();
            sgjsBuildScheme.setId(id);
            sgjsBuildScheme.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setCreateTime(DateUtils.getNowDate());
            sgjsBuildSchemeMapper.insertSgjsBuildScheme(sgjsBuildScheme);
        } else {
            //修改
            this.updateSgjsBuildScheme(sgjsBuildScheme);
        }
        /*保存方案清单*/
        //只需要在流程未发起时处理
        sgjsBuildScheme.setId(id);
        FlowInfoSearchUtil.getFlowInfo(sgjsBuildScheme, FlowEnum.SGJS_BUILD_SCHEME);
        if (sgjsBuildScheme.getTaskStatus().equals("0")) {
            List<SgjsBuildSchemeList> children = sgjsBuildScheme.getChildren();
            sgjsBuildSchemeListService.insertSgjsBuildSchemeList(children, id);
        }
        return id;
    }

    @Transactional
    public int insertSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList) {
        for (SgjsBuildScheme sgjsBuildScheme : sgjsBuildSchemeList) {
            sgjsBuildScheme.setId(IdWorker.createId());
            sgjsBuildScheme.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeMapper.insertSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int updateSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeMapper.updateSgjsBuildScheme(sgjsBuildScheme);
    }

    @Transactional
    public int updateSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList) {
        for (SgjsBuildScheme sgjsBuildScheme : sgjsBuildSchemeList) {
            sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeMapper.updateSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int deleteSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme) {
        sgjsBuildScheme.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildScheme.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeMapper.deleteSgjsBuildScheme(sgjsBuildScheme);
    }

    @Transactional
    public int deleteSgjsBuildSchemeByPks(List<Long> sgjsBuildSchemePkList) {
        return sgjsBuildSchemeMapper.deleteSgjsBuildSchemeByPks(sgjsBuildSchemePkList);
    }
}
