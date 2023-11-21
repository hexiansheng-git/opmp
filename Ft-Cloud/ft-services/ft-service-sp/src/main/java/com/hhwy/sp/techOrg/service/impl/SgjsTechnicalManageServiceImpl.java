package com.hhwy.sp.techOrg.service.impl;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageVo;
import com.hhwy.sp.techOrg.mapper.SgjsTechnicalManageMapper;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lcf
 * @date 2023-11-17 11:29:23
 * @remark
 */
@Service
public class SgjsTechnicalManageServiceImpl implements ISgjsTechnicalManageService{

    @Autowired
    private SgjsTechnicalManageMapper sgjsTechnicalManageMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    private static final Logger logger= LoggerFactory.getLogger(SgjsTechnicalManageServiceImpl.class);

    public SgjsTechnicalManage getSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        return sgjsTechnicalManageMapper.getSgjsTechnicalManage(sgjsTechnicalManage);
    }

    public List<SgjsTechnicalManage> getSgjsTechnicalManageList(SgjsTechnicalManage sgjsTechnicalManage) {
        return sgjsTechnicalManageMapper.getSgjsTechnicalManageList(sgjsTechnicalManage);
    }

    @Override
    public SgjsTechnicalManageVo list(SgjsTechnicalManage sgjsTechnicalManage) {
        SgjsTechnicalManageVo vo =new SgjsTechnicalManageVo();
        //筛选条件
        if(StringUtils.isNotEmpty(sgjsTechnicalManage.getActualDateStr())){
            String actualDateStr = sgjsTechnicalManage.getActualDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsTechnicalManage.setActualDateBegin(FtDateUtils.parseDate(begin));
            sgjsTechnicalManage.setActualDateEnd(FtDateUtils.parseDate(end));
        }
        List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.getSgjsTechnicalManageList(sgjsTechnicalManage);
        for (SgjsTechnicalManage info:list) {
            info.setActualDateStr(FtDateUtils.formatDate(info.getActualDate()));
        }
        vo.setTreeList(TreeUtil.build(list, 0L));
        return vo;
    }

    @Transactional
    public int insertSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setId(IdWorker.createId());
        sgjsTechnicalManage.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setCreateTime(DateUtils.getNowDate());
        String actualDateStr = sgjsTechnicalManage.getActualDateStr();
        if(StringUtils.isNotEmpty(actualDateStr)){
            String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
            Date date = FtDateUtils.parseDate(str);
            sgjsTechnicalManage.setActualDate(date);
        }
        return sgjsTechnicalManageMapper.insertSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int insertSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList) {
        for (SgjsTechnicalManage sgjsTechnicalManage : sgjsTechnicalManageList) {
            sgjsTechnicalManage.setId(IdWorker.createId());
            sgjsTechnicalManage.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(sgjsTechnicalManageList);
    }

    @Transactional
    public int updateSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageMapper.updateSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int updateSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList) {
        for (SgjsTechnicalManage sgjsTechnicalManage : sgjsTechnicalManageList) {
            sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalManageMapper.updateSgjsTechnicalManageList(sgjsTechnicalManageList);
    }

    @Transactional
    public int deleteSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage) {
        sgjsTechnicalManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageMapper.deleteSgjsTechnicalManage(sgjsTechnicalManage);
    }

    @Transactional
    public int deleteSgjsTechnicalManageByPks(List<Long> sgjsTechnicalManagePkList) {
        //批量查詢刪除
        List<SgjsTechnicalManage> list = sgjsTechnicalManageMapper.batchSelect(sgjsTechnicalManagePkList);
        List<Long> idList = list.stream().map(e -> e.getId()).collect(Collectors.toList());
        if(null==idList || idList.size()==0){
            logger.info("数据空了,未删除成功");
            return 0;
        }
        return sgjsTechnicalManageMapper.deleteSgjsTechnicalManageByPks(idList);
    }

    @Override
    @Transactional
    public int batchAdd(SgjsTechnicalManageVo sgjsTechnicalManageVo) {
        //删除库中所有数据
        SgjsTechnicalManage info=new SgjsTechnicalManage();
        info.setUpdateTime(DateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsTechnicalManageMapper.delectAll(info);
        List<SgjsTechnicalManage> treeToList=null;
        if(!CollectionUtils.isEmpty(sgjsTechnicalManageVo.getTreeList())){
            treeToList= TreeUtil.treeToList(sgjsTechnicalManageVo.getTreeList());
            for (int i = 0; i < treeToList.size(); i++) {
                SgjsTechnicalManage manage = treeToList.get(i);
                String actualDateStr = manage.getActualDateStr();
                if(StringUtils.isNotEmpty(actualDateStr)){
                    String str = actualDateStr.replaceAll("(?:年|月|日)", "-");
                    Date date = FtDateUtils.parseDate(str);
                    manage.setActualDate(date);
                }
            }
        }
        sgjsTechnicalManageMapper.insertSgjsTechnicalManageList(treeToList);
        return 0;
    }

    @Override
    public AjaxResult sync() {
        List<QqchPostSetting> list = pmServiceApi.getTechDeptList();
        for (QqchPostSetting info:list) {
            String str=info.getTechDept()+info.getPostName();
        }
        return AjaxResult.success(list);
    }
}
