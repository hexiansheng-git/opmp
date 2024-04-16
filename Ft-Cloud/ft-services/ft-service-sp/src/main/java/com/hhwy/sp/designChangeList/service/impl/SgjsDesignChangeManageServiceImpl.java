package com.hhwy.sp.designChangeList.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import jdk.nashorn.internal.ir.ContinueNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.designChangeList.mapper.SgjsDesignChangeManageMapper;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 施工技术管理-设计变更管理Service业务层处理
 * 
 * @author wk
 * @date 2024-04-16
 */
@Service
public class SgjsDesignChangeManageServiceImpl implements ISgjsDesignChangeManageService {
    @Autowired
    private SgjsDesignChangeManageMapper sgjsDesignChangeManageMapper;
    @Autowired
    private ISgjsDesignChangeWbsService changeWbsService;
    @Autowired
    private ISgjsDesignChangeListService changeListService;

    /**
     * 查询施工技术管理-设计变更管理
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 施工技术管理-设计变更管理
     */
    @Override
    public SgjsDesignChangeManage selectSgjsDesignChangeManageById(Long id) {
        return sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageById(id);
    }

    /**
     * 查询施工技术管理-设计变更管理列表
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 施工技术管理-设计变更管理
     */
    @Override
    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageList(SgjsDesignChangeManage sgjsDesignChangeManage) {
        return sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageList(sgjsDesignChangeManage);
    }

    /**
     * 新增施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    @Override
    public int insertSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage) {
        sgjsDesignChangeManage.setCreateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageMapper.insertSgjsDesignChangeManage(sgjsDesignChangeManage);
    }

    @Override
    @Transactional
    public void save(ChangeManagSaveVo saveVo) {
        boolean isNew = saveVo.getId() == null;
        if(isNew){
            new AddBaseInfoUtil<>(saveVo);
            saveVo.setId(IdWorker.createId());
            sgjsDesignChangeManageMapper.insertSgjsDesignChangeManage(saveVo);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(saveVo);
            sgjsDesignChangeManageMapper.updateSgjsDesignChangeManage(saveVo);
        }
        if(!isNew)
            sgjsDesignChangeManageMapper.deleteWbsByMainId(saveVo.getId());
        List<SgjsDesignChangeWbs> addWbsList = new ArrayList<>();
        List<SgjsDesignChangeList> addList = new ArrayList<>();
        List<String> wbsCodeList = new ArrayList<>();
        handlerWbsList(saveVo,null,saveVo.getWbsList(),addWbsList,wbsCodeList,addList);
        changeListService.deleteByWbsCodes(saveVo.getId(),"1",wbsCodeList);
        changeWbsService.batchInsert(addWbsList);
        changeListService.batchInsert(addList);
    }
    private void handlerWbsList(ChangeManagSaveVo saveVo, SgjsDesignChangeWbs parent,List<SgjsDesignChangeWbs> wbsList 
            , List<SgjsDesignChangeWbs> addWbsList,List<String> deleteWbsCodeList, List<SgjsDesignChangeList> addList){
        Integer level = parent==null?1:parent.getLevel()+1;
        for (int i = 0; i < wbsList.size(); i++) {
            SgjsDesignChangeWbs wbs = wbsList.get(i);
            if(StringUtils.equals(saveVo.getSubmitFlag(),"1")){
                JyDetailsUtil.jy(wbs, new Class[]{ValidationGroups.Other.class} );
            }
            wbs.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(wbs);
            wbs.setMainId(saveVo.getId());
            wbs.setLevel(level);
            wbs.setPtVar1("");
            addWbsList.add(wbs);
            deleteWbsCodeList.add(wbs.getCode());
            //处理清单
            handlerList(saveVo,wbs,saveVo.getList(),addList);
        }
    }

    private void handlerList(ChangeManagSaveVo saveVo, SgjsDesignChangeWbs wbs,List<SgjsDesignChangeList> list,
                             List<SgjsDesignChangeList> addList){
        if(!StringUtils.equals(wbs.getPtVar1(),"1") || CollectionUtils.isEmpty(list))
            return;
        for (int j = 0; j < list.size(); j++) {
            SgjsDesignChangeList temp = list.get(j);
            if(StringUtils.equals(saveVo.getSubmitFlag(),"1")){
                JyDetailsUtil.jy(temp, new Class[]{ValidationGroups.Other.class} );
            }
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            temp.setId(IdWorker.createId());
            temp.setMainId(saveVo.getId());
            temp.setWbsCode(wbs.getCode());
            temp.setWbsId(wbs.getId());
            addList.add(temp);
            handlerList(saveVo,wbs,temp.getChildren(),addList);
        }
    }

    /**
     * 修改施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    @Override
    public int updateSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage) {
        sgjsDesignChangeManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageMapper.updateSgjsDesignChangeManage(sgjsDesignChangeManage);
    }

    /**
     * 删除施工技术管理-设计变更管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsDesignChangeManageByIds(String ids) {
        return sgjsDesignChangeManageMapper.deleteSgjsDesignChangeManageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除施工技术管理-设计变更管理信息
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageById(Long id) {
        return sgjsDesignChangeManageMapper.deleteSgjsDesignChangeManageById(id);
    }
}
