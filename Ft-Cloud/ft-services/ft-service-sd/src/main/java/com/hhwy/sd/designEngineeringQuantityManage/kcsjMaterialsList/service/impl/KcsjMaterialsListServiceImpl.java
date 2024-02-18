package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
@Service
public class KcsjMaterialsListServiceImpl implements IKcsjMaterialsListService {

    @Autowired
    private KcsjMaterialsListMapper kcsjMaterialsListMapper;


    public KcsjMaterialsList getKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        return kcsjMaterialsListMapper.getKcsjMaterialsList(kcsjMaterialsList);
    }

    public List<KcsjMaterialsList> getKcsjMaterialsListList(KcsjMaterialsList kcsjMaterialsList) {
        return kcsjMaterialsListMapper.getKcsjMaterialsListList(kcsjMaterialsList);
    }

    @Transactional
    public int insertKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        kcsjMaterialsList.setId(IdWorker.createId());
        kcsjMaterialsList.setCreateUser(SecurityUtils.getUserName());
        kcsjMaterialsList.setCreateTime(DateUtils.getNowDate());
        return kcsjMaterialsListMapper.insertKcsjMaterialsList(kcsjMaterialsList);
    }

    @Transactional
    public int insertKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList) {
        for (KcsjMaterialsList kcsjMaterialsList : kcsjMaterialsListList) {
            kcsjMaterialsList.setId(IdWorker.createId());
            kcsjMaterialsList.setCreateUser(SecurityUtils.getUserName());
            kcsjMaterialsList.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListMapper.insertKcsjMaterialsListList(kcsjMaterialsListList);
    }

    @Transactional
    public int updateKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        return kcsjMaterialsListMapper.updateKcsjMaterialsList(kcsjMaterialsList);
    }

    @Transactional
    public int updateKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList) {
        for (KcsjMaterialsList kcsjMaterialsList : kcsjMaterialsListList) {
            kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
            kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListMapper.updateKcsjMaterialsListList(kcsjMaterialsListList);
    }

    @Transactional
    public int deleteKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        return kcsjMaterialsListMapper.deleteKcsjMaterialsList(kcsjMaterialsList);
    }

    @Transactional
    public int deleteKcsjMaterialsListByPks(List<Long> kcsjMaterialsListPkList) {
        return kcsjMaterialsListMapper.deleteKcsjMaterialsListByPks(kcsjMaterialsListPkList);
    }
}
