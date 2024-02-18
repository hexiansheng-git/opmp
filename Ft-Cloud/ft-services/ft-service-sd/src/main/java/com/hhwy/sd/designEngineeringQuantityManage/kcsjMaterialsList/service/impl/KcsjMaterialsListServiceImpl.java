package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListService;
import com.hhwy.utils.date.FtDateUtils;
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

    @Autowired
    private KcsjMaterialsListDetailMapper detailMapper;
    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 详情
     * @param kcsjMaterialsList
     * @return
     */
    public KcsjMaterialsList getKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        //查询主表数据
         kcsjMaterialsList = kcsjMaterialsListMapper.getKcsjMaterialsList(kcsjMaterialsList);
        //查询子表数据
        List<KcsjMaterialsListDetail> detailList=detailMapper.getKcsjMaterialsListDetailListByMainId(kcsjMaterialsList.getId());
        //组装返回值
        if (detailList.size()>0){
            kcsjMaterialsList.setDetailList(detailList);
        }
        return kcsjMaterialsList;
    }

    /**
     * 列表页查询
     * @param kcsjMaterialsList
     * @return
     */
    public List<KcsjMaterialsList> getKcsjMaterialsListList(KcsjMaterialsList kcsjMaterialsList) {
        //获取搜索条件
        String submitDateStr = kcsjMaterialsList.getSubmitDateStr();
        if (StringUtils.isNotEmpty(submitDateStr)){
            //处理搜索条件
            String[] split = submitDateStr.split("-");
            kcsjMaterialsList.setSubmitDateBegin(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            kcsjMaterialsList.setSubmitDateEnd(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }
        //查询 返回结果
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
