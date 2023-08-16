package com.hhwy.pm.xmsl.xmslMaterialReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewMaterialService;
import com.hhwy.pm.xmsl.xmslMaterialReport.domain.XmslMaterialReport;
import com.hhwy.pm.xmsl.xmslMaterialReport.mapper.XmslMaterialReportMapper;
import com.hhwy.pm.xmsl.xmslMaterialReport.service.IXmslMaterialReportService;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Override
    @Transactional
    public void sync(Long drawReviewId) {
        //1、获取所有图纸复核细目
        XmslDrawReviewMaterial queryMater = new XmslDrawReviewMaterial();
        queryMater.setMainId(drawReviewId);
        List<XmslDrawReviewMaterial> materialList = drawReviewMaterialService.getXmslDrawReviewMaterialList(queryMater);
        if(CollectionUtils.isEmpty(materialList))
            return ;
        //2、汇总细目数据
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
            addList.add(materialReport);
        }
        //4、清理主材报表，然后插入
        this.xmslMaterialReportMapper.deleteAll();
        xmslMaterialReportMapper.insertXmslMaterialReportList(addList);
    }
}
