package com.hhwy.sp.techData.sgjsTechnicalData.service.impl;

import java.util.*;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.techData.util.TreeCountUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techData.sgjsTechnicalData.mapper.SgjsTechnicalDataMapper;
import com.hhwy.sp.techData.sgjsTechnicalData.service.ISgjsTechnicalDataService;
import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-01-22 09:26:47
 * @remark
 */
@Service
public class SgjsTechnicalDataServiceImpl implements ISgjsTechnicalDataService {

    @Autowired
    private SgjsTechnicalDataMapper sgjsTechnicalDataMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    public SgjsTechnicalData getSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData) {
        return sgjsTechnicalDataMapper.getSgjsTechnicalData(sgjsTechnicalData);
    }

    public List<SgjsTechnicalData> getList(SgjsTechnicalData sgjsTechnicalData) {
        return sgjsTechnicalDataMapper.getSgjsTechnicalDataList(sgjsTechnicalData);
    }

    public List<SgjsTechnicalData> getSgjsTechnicalDataList(SgjsTechnicalData sgjsTechnicalData) {
        Map<String, Object> queryMap = new HashMap<>();
        String dataName = sgjsTechnicalData.getDataName();
        if(StringUtils.isNotEmpty(dataName)) {
            queryMap.put("dataName",dataName);
            sgjsTechnicalData.setDataName(null);
        }
        Date dataAddDate = sgjsTechnicalData.getDataAddDate();
        if(dataAddDate != null) {
            queryMap.put("dataAddDate",dataAddDate);
            sgjsTechnicalData.setDataAddDate(null);
        }
        Long pid = sgjsTechnicalData.getPid();
        List<SgjsTechnicalData> sgjsTechnicalDataList = sgjsTechnicalDataMapper.getSgjsTechnicalDataList(sgjsTechnicalData);
        if(CollectionUtils.isNotEmpty(sgjsTechnicalDataList) && queryMap.size() > 0) {
            TreeCountUtils<SgjsTechnicalData> treeCountUtils = new TreeCountUtils<>();
            List<SgjsTechnicalData> sgjsTechnicalData1 = treeCountUtils.queryTree(sgjsTechnicalDataList, queryMap, pid);
            return TreeUtil.build(sgjsTechnicalData1, pid);
        }
        return TreeUtil.build(sgjsTechnicalDataList, pid);
    }

    @Transactional
    public int insertSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData) {
        sgjsTechnicalData.setId(IdWorker.createId());
        sgjsTechnicalData.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalData.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataMapper.insertSgjsTechnicalData(sgjsTechnicalData);
    }

    @Transactional
    public int insertSgjsTechnicalDataList(List<SgjsTechnicalData> sgjsTechnicalDataList) {
        for (SgjsTechnicalData sgjsTechnicalData : sgjsTechnicalDataList) {
            sgjsTechnicalData.setId(IdWorker.createId());
            sgjsTechnicalData.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalData.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalDataMapper.insertSgjsTechnicalDataList(sgjsTechnicalDataList);
    }

    @Transactional
    public int updateSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData) {
        sgjsTechnicalData.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalData.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataMapper.updateSgjsTechnicalData(sgjsTechnicalData);
    }

    @Transactional
    public int updateSgjsTechnicalDataList(Long dataCatalogId,List<SgjsTechnicalData> sgjsTechnicalDataList) {
        if(CollectionUtils.isEmpty(sgjsTechnicalDataList)) {
            return 1;
        }
        List<SgjsTechnicalData> addList = new ArrayList<>();
        List<SgjsTechnicalData> updateList = new ArrayList<>();
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        List<SgjsTechnicalData> sgjsTechnicalData1 = TreeUtil.treeToListWithoutId(sgjsTechnicalDataList);
        for (SgjsTechnicalData sgjsTechnicalData : sgjsTechnicalData1) {
            if("1".equals(sgjsTechnicalData.getIsAdd())) {
                sgjsTechnicalData.setDataCatalogId(dataCatalogId);
                sgjsTechnicalData.setCreateUser(SecurityUtils.getSysUser().getNickName());
                sgjsTechnicalData.setCreateTime(DateUtils.getNowDate());
                sgjsTechnicalData.setRegionId(projectDto.getRegionId());
                sgjsTechnicalData.setRegionName(projectDto.getRegionName());
                sgjsTechnicalData.setProjectId(projectDto.getProjectId());
                sgjsTechnicalData.setPtVar5(projectDto.getProjectCode());
                addList.add(sgjsTechnicalData);
            } else {
                sgjsTechnicalData.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                sgjsTechnicalData.setUpdateTime(DateUtils.getNowDate());
                updateList.add(sgjsTechnicalData);
            }
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i += sgjsTechnicalDataMapper.insertSgjsTechnicalDataList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i += sgjsTechnicalDataMapper.updateSgjsTechnicalDataList(updateList);
        }
        return i;
    }

    @Transactional
    public int deleteSgjsTechnicalData(SgjsTechnicalData sgjsTechnicalData) {
        return sgjsTechnicalDataMapper.deleteSgjsTechnicalData(sgjsTechnicalData);
    }

    @Override
    public int deleteSgjsTechnicalDataByCatalog(Long dataCatalogId) {
        if(dataCatalogId == null) {
            return 1;
        }
        SgjsTechnicalData sgjsTechnicalData = new SgjsTechnicalData();
        sgjsTechnicalData.setDataCatalogId(dataCatalogId);
        return deleteSgjsTechnicalData(sgjsTechnicalData);
    }

    @Transactional
    public int deleteSgjsTechnicalDataByPks(List<Long> sgjsTechnicalDataPkList) {
        return sgjsTechnicalDataMapper.deleteSgjsTechnicalDataByPks(sgjsTechnicalDataPkList);
    }
}
