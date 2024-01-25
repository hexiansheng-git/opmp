package com.hhwy.sp.techData.sgjsTechnicalDataCatalog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techData.sgjsTechnicalData.service.ISgjsTechnicalDataService;
import com.hhwy.sp.techData.util.TreeCountUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.mapper.SgjsTechnicalDataCatalogMapper;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.service.ISgjsTechnicalDataCatalogService;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-01-22 09:26:41
 * @remark
 */
@Service
public class SgjsTechnicalDataCatalogServiceImpl implements ISgjsTechnicalDataCatalogService {

    @Autowired
    private SgjsTechnicalDataCatalogMapper sgjsTechnicalDataCatalogMapper;

    @Autowired
    private ISgjsTechnicalDataService sgjsTechnicalDataService;

    public SgjsTechnicalDataCatalog getSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog) {
        return sgjsTechnicalDataCatalogMapper.getSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalog);
    }

    public List<SgjsTechnicalDataCatalog> getSgjsTechnicalDataCatalogList(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog) {
        Map<String, Object> queryMap = new HashMap<>();
        String catalogName = sgjsTechnicalDataCatalog.getCatalogName();
        if(StringUtils.isNotEmpty(catalogName)) {
            queryMap.put("catalogName",catalogName);
            sgjsTechnicalDataCatalog.setCatalogName(null);
        }
        Long pid = sgjsTechnicalDataCatalog.getPid();
        List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList = sgjsTechnicalDataCatalogMapper.getSgjsTechnicalDataCatalogList(sgjsTechnicalDataCatalog);
        if(CollectionUtils.isNotEmpty(sgjsTechnicalDataCatalogList) && queryMap.size() > 0) {
            TreeCountUtils<SgjsTechnicalDataCatalog> treeCountUtils = new TreeCountUtils<>();
            List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogs = treeCountUtils.queryTree(sgjsTechnicalDataCatalogList, queryMap, pid);
            if(CollectionUtils.isNotEmpty(sgjsTechnicalDataCatalogs)) {
                return TreeUtil.build(sgjsTechnicalDataCatalogs, pid);
            }
        }
        return TreeUtil.build(sgjsTechnicalDataCatalogList, pid);
    }

    @Transactional
    public int insertSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog) {
        sgjsTechnicalDataCatalog.setId(IdWorker.createId());
        sgjsTechnicalDataCatalog.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalDataCatalog.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataCatalogMapper.insertSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalog);
    }

    @Transactional
    public int insertSgjsTechnicalDataCatalogList(List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList) {
        for (SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog : sgjsTechnicalDataCatalogList) {
            sgjsTechnicalDataCatalog.setId(IdWorker.createId());
            sgjsTechnicalDataCatalog.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalDataCatalog.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalDataCatalogMapper.insertSgjsTechnicalDataCatalogList(sgjsTechnicalDataCatalogList);
    }

    @Transactional
    public int updateSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog) {
        sgjsTechnicalDataCatalog.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalDataCatalog.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataCatalogMapper.updateSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalog);
    }

    @Transactional
    public int updateSgjsTechnicalDataCatalogList(List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList) {
        if(CollectionUtils.isEmpty(sgjsTechnicalDataCatalogList)) {
            return 1;
        }
        List<SgjsTechnicalDataCatalog> addList = new ArrayList<>();
        List<SgjsTechnicalDataCatalog> updateList = new ArrayList<>();
        List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogs = TreeUtil.treeToListWithoutId(sgjsTechnicalDataCatalogList);
        for (SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog : sgjsTechnicalDataCatalogs) {
            if("1".equals(sgjsTechnicalDataCatalog.getIsAdd())) {
                sgjsTechnicalDataCatalog.setCreateUser(SecurityUtils.getSysUser().getNickName());
                sgjsTechnicalDataCatalog.setCreateTime(DateUtils.getNowDate());
                addList.add(sgjsTechnicalDataCatalog);
            } else {
                sgjsTechnicalDataCatalog.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                sgjsTechnicalDataCatalog.setUpdateTime(DateUtils.getNowDate());
                updateList.add(sgjsTechnicalDataCatalog);
            }
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i += sgjsTechnicalDataCatalogMapper.insertSgjsTechnicalDataCatalogList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i += sgjsTechnicalDataCatalogMapper.updateSgjsTechnicalDataCatalogList(updateList);
        }
        return i;
    }

    @Transactional
    public int deleteSgjsTechnicalDataCatalog(SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog) {
        sgjsTechnicalDataCatalog.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalDataCatalog.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataCatalogMapper.deleteSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalog);
    }

    @Transactional
    public int deleteSgjsTechnicalDataCatalogByPks(List<Long> sgjsTechnicalDataCatalogPkList) {
        if(CollectionUtils.isEmpty(sgjsTechnicalDataCatalogPkList)) return 0;
        for (Long id : sgjsTechnicalDataCatalogPkList) {
            sgjsTechnicalDataService.deleteSgjsTechnicalDataByCatalog(id);
        }
        return sgjsTechnicalDataCatalogMapper.deleteSgjsTechnicalDataCatalogByPks(sgjsTechnicalDataCatalogPkList);
    }
}
