package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service.impl;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmitVo;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.utils.FileUtils;
import com.hhwy.sp.utils.ZipUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.mapper.SgjsReportMeasureSubmitMapper;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service.ISgjsReportMeasureSubmitService;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
@Service
public class SgjsReportMeasureSubmitServiceImpl implements ISgjsReportMeasureSubmitService{

    @Autowired
    private SgjsReportMeasureSubmitMapper sgjsReportMeasureSubmitMapper;


    public SgjsReportMeasureSubmit getSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        return sgjsReportMeasureSubmitMapper.getSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    public SgjsReportMeasureSubmitVo list(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo = new SgjsReportMeasureSubmitVo();
        //判断日期
        if (StringUtils.isNotEmpty(sgjsReportMeasureSubmit.getRealStartDateStr())){
            String realStartDateStr = sgjsReportMeasureSubmit.getRealStartDateStr();
            String[] split = realStartDateStr.split(",");
            sgjsReportMeasureSubmit.setRealStartDateStr(split[0].replaceAll("(?:年|月|日)", "-"));
            sgjsReportMeasureSubmit.setRealEndDateStr(split[1].replaceAll("(?:年|月|日)", "-"));
        }
        List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList = sgjsReportMeasureSubmitMapper.getSgjsReportMeasureSubmitList(sgjsReportMeasureSubmit);
        for (SgjsReportMeasureSubmit info:sgjsReportMeasureSubmitList) {
            info.setPlanStartDateStr(info.getPlanStartDate() == null ? null : FtDateUtils.formatDate(info.getPlanStartDate()));
            info.setRealStartDateStr(info.getRealStartDate() == null ? null : FtDateUtils.formatDate(info.getRealStartDate()));
        }
        sgjsReportMeasureSubmitVo.setTreeList(sgjsReportMeasureSubmitList);
        return sgjsReportMeasureSubmitVo;
    }

    @Transactional
    public int insertSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setCreateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setCreateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.insertSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    @Transactional
    public AjaxResult batchAdd(SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo) {
        if (CollectionUtils.isEmpty(sgjsReportMeasureSubmitVo.getTreeList())) {
            return AjaxResult.error("数据异常");
        }
        for (SgjsReportMeasureSubmit sgjsReportMeasureSubmit : sgjsReportMeasureSubmitVo.getTreeList()) {
            sgjsReportMeasureSubmit.setRealStartDate(sgjsReportMeasureSubmit.getRealStartDateStr() == null ? null : FtDateUtils.parseDate(sgjsReportMeasureSubmit.getRealStartDateStr().replaceAll("(?:年|月|日)", "-")));
            sgjsReportMeasureSubmit.setPlanStartDate(sgjsReportMeasureSubmit.getPlanStartDateStr() == null ? null : FtDateUtils.parseDate(sgjsReportMeasureSubmit.getPlanStartDateStr().replaceAll("(?:年|月|日)", "-")));
            sgjsReportMeasureSubmit.setCreateUser(SecurityUtils.getUserName());
            sgjsReportMeasureSubmit.setCreateTime(DateUtils.getNowDate());
            sgjsReportMeasureSubmit.setId(IdWorker.createId());
        }
        List<SgjsReportMeasureSubmit> insertList = sgjsReportMeasureSubmitVo.getTreeList().stream().filter(r -> StringUtils.isNotEmpty(r.getIsAdd()) && r.getIsAdd().equals("1")).collect(Collectors.toList());
        //批量入库
        if(!CollectionUtils.isEmpty(insertList)){
            sgjsReportMeasureSubmitMapper.batchAdd(insertList);
        }
        //批量编辑
        List<SgjsReportMeasureSubmit> updateList = sgjsReportMeasureSubmitVo.getTreeList().stream().filter(r -> StringUtils.isEmpty(r.getIsAdd())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(updateList)){
            sgjsReportMeasureSubmitMapper.updateSgjsReportMeasureSubmitList(updateList);
        }
        //批量删除
        deleteByIds(sgjsReportMeasureSubmitVo.getDelIdList());
        return AjaxResult.success();
    }

    /**
     * 批量删除
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        List<SgjsReportMeasureSubmit> list =new ArrayList<>();
        for (int i = 0; i < delIdList.size(); i++) {
            SgjsReportMeasureSubmit info=new SgjsReportMeasureSubmit();
            info.setId(Long.parseLong(delIdList.get(i)));
            info.setUpdateUser(SecurityUtils.getUserId()+"");
            info.setUpdateTime(DateUtils.getNowDate());
            list.add(info);
        }
        //删除
        if(!CollectionUtils.isEmpty(list)){
            sgjsReportMeasureSubmitMapper.deleteInfoData(list);
        }
    }

    @Transactional
    public int updateSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.updateSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    @Transactional
    public int updateSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList) {
        for (SgjsReportMeasureSubmit sgjsReportMeasureSubmit : sgjsReportMeasureSubmitList) {
            sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
            sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsReportMeasureSubmitMapper.updateSgjsReportMeasureSubmitList(sgjsReportMeasureSubmitList);
    }

    @Transactional
    public int deleteSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.deleteSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    @Transactional
    public int deleteSgjsReportMeasureSubmitByPks(List<Long> sgjsReportMeasureSubmitPkList) {
        return sgjsReportMeasureSubmitMapper.deleteSgjsReportMeasureSubmitByPks(sgjsReportMeasureSubmitPkList);
    }

    @Override
    public List<SgjsReportMeasureSubmit> getIds(List<Long> ids) {
        return sgjsReportMeasureSubmitMapper.getIds(ids);
    }


    @Override
    public void bathExportZip(HttpServletResponse response, SgjsReportMeasureSubmit submit) {
        List<SgjsReportMeasureSubmit> list = sgjsReportMeasureSubmitMapper.getSgjsReportMeasureSubmitList(submit);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        List<String> fileGroupIdList = list.stream().map(e -> e.getFileGroupId()).collect(Collectors.toList());

        FileUtils fileUtils=new FileUtils();
        List<File> fileList = fileUtils.getFileByGroupIds(fileGroupIdList);

        ZipUtils.zipFile(fileList,response);
    }



}
