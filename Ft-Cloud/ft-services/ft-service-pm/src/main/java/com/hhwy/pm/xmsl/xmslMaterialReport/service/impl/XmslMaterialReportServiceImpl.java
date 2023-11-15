package com.hhwy.pm.xmsl.xmslMaterialReport.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewMaterialService;
import com.hhwy.pm.xmsl.xmslMaterialReport.domain.XmslMaterialReport;
import com.hhwy.pm.xmsl.xmslMaterialReport.mapper.XmslMaterialReportMapper;
import com.hhwy.pm.xmsl.xmslMaterialReport.service.IXmslMaterialReportService;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 主材报表
 * @author wk
 * @date 2023-08-15 17:39:46
 * @remark
 */
@Service
public class XmslMaterialReportServiceImpl implements IXmslMaterialReportService {

    @Autowired
    private XmslMaterialReportMapper xmslMaterialReportMapper;
    @Autowired
    private IXmslDrawReviewMaterialService drawReviewMaterialService;


    public XmslMaterialReport getXmslMaterialReport(XmslMaterialReport xmslMaterialReport) {
        return xmslMaterialReportMapper.getXmslMaterialReport(xmslMaterialReport);
    }

    public List<XmslMaterialReport> getXmslMaterialReportList(XmslMaterialReport xmslMaterialReport) {
        return xmslMaterialReportMapper.getXmslMaterialReportList(xmslMaterialReport);
    }

    @Override
    public List<XmslMaterialReport> listForTotalDemand(XmslMaterialReport xmslMaterialReport) {
        List<XmslMaterialReport> list = xmslMaterialReportMapper.getXmslMaterialReportList(xmslMaterialReport);
        if(CollectionUtils.isEmpty(list))
            return list;
        //为前期策划物资总需计划(1.6)准备，需要将type换成物资信息的materialType
        Set<String> materCodeSet = list.stream().map(r->r.getCode()).collect(Collectors.toSet());
        Map<String,MaterialInfo> materialInfoMap = MaterialUtils.getMaterialInfoMapByCodes(materCodeSet);
        for (int i = 0; i < list.size(); i++) {
            XmslMaterialReport temp = list.get(i);
            MaterialInfo materialInfo = materialInfoMap.get(temp.getCode());
            temp.setType(materialInfo==null?temp.getType():materialInfo.getMaterialType());
        }
        return list;
    }

    @Transactional
    public int insertXmslMaterialReport(XmslMaterialReport xmslMaterialReport) {
        xmslMaterialReport.setId(IdWorker.createId());
        xmslMaterialReport.setCreateUser(SecurityUtils.getUserName());
        xmslMaterialReport.setCreateTime(DateUtils.getNowDate());
        return xmslMaterialReportMapper.insertXmslMaterialReport(xmslMaterialReport);
    }

    @Transactional
    public int insertXmslMaterialReportList(List<XmslMaterialReport> xmslMaterialReportList) {
        for (XmslMaterialReport xmslMaterialReport : xmslMaterialReportList) {
            xmslMaterialReport.setId(IdWorker.createId());
            xmslMaterialReport.setCreateUser(SecurityUtils.getUserName());
            xmslMaterialReport.setCreateTime(DateUtils.getNowDate());
        }
        return xmslMaterialReportMapper.insertXmslMaterialReportList(xmslMaterialReportList);
    }

    @Transactional
    public int updateXmslMaterialReport(XmslMaterialReport xmslMaterialReport) {
        xmslMaterialReport.setUpdateUser(SecurityUtils.getUserName());
        xmslMaterialReport.setUpdateTime(DateUtils.getNowDate());
        return xmslMaterialReportMapper.updateXmslMaterialReport(xmslMaterialReport);
    }


    @Transactional
    public int deleteXmslMaterialReport(XmslMaterialReport xmslMaterialReport) {
        xmslMaterialReport.setUpdateUser(SecurityUtils.getUserName());
        xmslMaterialReport.setUpdateTime(DateUtils.getNowDate());
        return xmslMaterialReportMapper.deleteXmslMaterialReport(xmslMaterialReport);
    }

    @Transactional
    public void sync(Long drawReviewId,String tenantKey) {
        //切换租户 真
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
        try {
            sync(drawReviewId);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }


    }

    public void sync(Long drawReviewId) {
        //1、获取所有图纸复核细目
        XmslDrawReviewMaterial queryMater = new XmslDrawReviewMaterial();
        queryMater.setMainId(drawReviewId);
        List<XmslDrawReviewMaterial> materialList = drawReviewMaterialService.getXmslDrawReviewMaterialList(queryMater);
        if(CollectionUtils.isEmpty(materialList))
            return ;
        //2、汇总细目数据  物资编码 : 物资信息
        Map<String,XmslDrawReviewMaterial> materialMap = new HashMap<>(materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            XmslDrawReviewMaterial tempMater = materialList.get(i);
            XmslDrawReviewMaterial sum = materialMap.get(tempMater.getCode());
            if(sum ==null){
                materialMap.put(tempMater.getCode(),tempMater);
                continue;
            }
            //累加理论用量
            sum.setTheoreticalDosage(BigDecimalUtils.sum(sum.getTheoreticalDosage(),tempMater.getTheoreticalDosage()));
        }
        //3、转换细目->主材报表
        List<XmslMaterialReport> addList = new ArrayList<>(materialMap.size());
        Iterator<XmslDrawReviewMaterial> materialIterator = materialMap.values().iterator();
        while(materialIterator.hasNext()){
            XmslDrawReviewMaterial tempMater = materialIterator.next();
            XmslMaterialReport materialReport = new XmslMaterialReport();
            BeanUtils.copyProperties(tempMater, materialReport);
            //重新获取物资类型
            MaterialInfo materialInfo = MaterialUtils.getMaterialInfoByCode(materialReport.getCode());
            if(materialInfo != null)
                materialReport.setType(materialInfo.getMaterialType());
            addList.add(materialReport);
        }
        //4、清理主材报表，然后插入
        this.xmslMaterialReportMapper.deleteAll();
        xmslMaterialReportMapper.insertXmslMaterialReportList(addList);
    }
}
