package com.hhwy.pm.xmsl.xmslEngineeringReport.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.pm.xmsl.xmslEngineeringReport.mapper.XmslEngineeringReportMapper;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.MySecurityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.tree.TreeUtil;
import io.lettuce.core.protocol.RedisProtocolException;
import io.netty.handler.codec.http.QueryStringDecoder;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 工程量报表
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark
 */
@Service
public class XmslEngineeringReportServiceImpl implements IXmslEngineeringReportService {
    @Autowired
    private XmslEngineeringReportMapper xmslEngineeringReportMapper;
    @Autowired
    private IXmslDrawReviewWbsService drawReviewWbsService;
    @Autowired
    private IXmslDrawReviewListService drawReviewListService;
    @Autowired
    private IXmslDrawReviewService drawReviewService;
    @Autowired
    private IXmslContractListService contractListService;
    @Autowired
    private RedisUtils redisUtils;

    @Transactional
    public void sync(String tenantKey) {
        //切换租户 真
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
        try {
            syncInner(tenantKey);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }


    public void syncInner(String tenantKey) {
        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview == null || drawReview.getId()==null)
            return;
        xmslEngineeringReportMapper.deleteAll();
        //1、获取图纸复核WBS。以及清单
        List<XmslEngineeringReport> addList = new ArrayList<>();
        Map<String,XmslEngineeringReport> addMap = new HashMap<>();
        BiFunction<XmslEngineeringReport,Integer,Boolean> addReportFunc = (r,type)->{
            String prefix = r.getPtVar2()+(type+"");
            addList.add(r);
            addMap.put(prefix,r);
            return true;
        };
        //改为加载全量的wbs和清单
        List<XmslWbs> wbsList = WbsRedisUtils.allWbs(tenantKey);
        List<XmslContractList> contractLists = contractListService.getEffectList(new XmslContractList());
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
        Map<String,XmslContractList> contractListMap = new HashMap<>(contractLists.size());
        //1、加载wbs
        for (int i = 0; i < wbsList.size(); i++) {
            XmslWbs temp = wbsList.get(i);
            XmslEngineeringReport report = instanceWbs(temp,drawReview.getId());
            addReportFunc.apply(report,1);
        }
        //2、清单
        for (int i = 0; i < contractLists.size(); i++) {
            XmslContractList temp = contractLists.get(i);
            if(temp.getPid() ==null || temp.getPid().equals(0L))
                temp.setPid(-1L);
            contractListMap.put(temp.getCode(),temp);
            XmslEngineeringReport report = instanceList(temp);
            addReportFunc.apply(report,2);
        }
        //3、wbs挂接清单 <> 清单下wbs
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            if(StringUtils.isBlank(temp.getWbsCode()) || StringUtils.isBlank(temp.getListCode()))
                continue;
            //清单-WBS
            XmslWbs wbs = WbsRedisUtils.getWbsByCode(temp.getWbsCode());
            XmslEngineeringReport listReport = instanceWbs(wbs,drawReview.getId());
            listReport.setId(Long.valueOf(wbs.getId()));
            XmslEngineeringReport parentListReport = addMap.get(temp.getListCode()+2);
            if(parentListReport != null){
                parentListReport.setHaveChildren(1);
                listReport.setReportType(2);
                listReport.setHaveChildren(0);
                listReport.setParentId(parentListReport.getId());
                listReport.setCheckQuanlity(temp.getCheckNum());
                listReport.setImageProgress(temp.getImageProgress());
                addReportFunc.apply(listReport,2);
            }
            //wbs-清单
            XmslContractList contractList = contractListMap.get(temp.getListCode());
            XmslEngineeringReport tempReport = instanceList(contractList);
            tempReport.setCheckQuanlity(temp.getCheckNum());
            XmslEngineeringReport parentReport = addMap.get(temp.getWbsCode()+1);
            if(parentReport != null){
                parentReport.setHaveChildren(1);
                tempReport.setReportType(1);
                tempReport.setHaveChildren(0);
                tempReport.setParentId(parentReport.getId());
                tempReport.setImageProgress(temp.getImageProgress());
                addReportFunc.apply(tempReport,1);
            }
        }
        if(CollectionUtils.isNotEmpty(addList))
            this.xmslEngineeringReportMapper.insertXmslEngineeringReportList(addList);
    }
    private XmslEngineeringReport instanceWbs(XmslWbs wbs,Long mainId){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setReportType(1);
        report.setMainId(mainId);
        report.setParentId(ObjectUtils.nvlLong(wbs.getParentId(),-1L));
        report.setWbsId(Long.valueOf(wbs.getId()));
        report.setWbsCode(wbs.getCode());
        report.setWbsName(wbs.getName());
        report.setNodeType(wbs.getNodeType());
        report.setHaveChildren(wbs.getHaveChildren());
        report.setAncestors(wbs.getAncestors());
        report.setAncestorsName(wbs.getAncestorsName());
        report.setPartCode(wbs.getPartCode());
        report.setWbsUnit(wbs.getUnit());
        report.setLevel(wbs.getLevel());
        report.setDesignQuanlity(wbs.getDesignQuanlity());
        report.setSort(wbs.getSort());
        report.setId(report.getWbsId());
        report.setPtVar2(wbs.getCode());
//        new AddBaseInfoUtil<>().addBaseEntity(report);
//        report.setId(wbs.getId());
        return report;
    }
    private XmslEngineeringReport instanceList(XmslContractList list){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setParentId(ObjectUtils.nvlLong(list.getPid()));
        report.setListCode(list.getCode());
        report.setListName(list.getChineseName());
        report.setListId(list.getId());
        report.setUnit(list.getUnit());
        report.setUnitCode(list.getUnitCode());
        report.setDesignQuanlity(list.getWinNum());
        report.setId(list.getId());
        report.setReportType(2);
        report.setPtVar1(list.getPtVar1()); //是否直接挂接了wbs
        report.setPtVar2(list.getCode());
        report.setHaveChildren(list.getHaveChildren());
        return report;
    }

    public XmslEngineeringReport getXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        return xmslEngineeringReportMapper.getXmslEngineeringReport(xmslEngineeringReport);
    }

    @Override
    public List<XmslEngineeringReport> getList(XmslEngineeringReport report) {
        return xmslEngineeringReportMapper.getXmslEngineeringReportList(report);
    }

    public List<XmslEngineeringReport> getXmslEngineeringReportList(XmslEngineeringReport report) {
        report.setParentId(ObjectUtils.nvlLong(report.getParentId(),-1L));
        boolean hasCondition = StringUtils.isNotBlank(report.getWbsCode()) || StringUtils.isNotBlank(report.getWbsName())
                || StringUtils.isNotBlank(report.getListCode()) || StringUtils.isNotBlank(report.getListName()) ;
//        if(hasCondition && ( (StringUtils.trim(report.getWbsCode())+StringUtils.trim(report.getWbsName())).length() < 3
//                            && (StringUtils.trim(report.getListCode())+StringUtils.trim(report.getListName())).length() < 3) )
//            throw new RuntimeException("搜索参数过小");
        if(!hasCondition){
            return xmslEngineeringReportMapper.getXmslEngineeringReportList(report);
        }
        //如果是懒加载,找出满足条件的id，扔redis
        String key = "engineeringReport::lazySearch_"+ MySecurityUtils.getTenantKey()+StringUtils.join(new String[]{
                report.getWbsCode(),report.getWbsName(),report.getListCode(),report.getListName()  
        }, ",");
        //获取ids
        Set<String> idSet = null;
        if(!redisUtils.hasKey(key) ){
            List<XmslEngineeringReport> list = xmslEngineeringReportMapper.getId(report);
            final Set<String> resuIdSet = new ConcurrentHashSet<>();
            list.parallelStream().forEach(r->{
                resuIdSet.add(r.getId()+"");
                if(StringUtils.isBlank(r.getAncestors()))
                    return;
                resuIdSet.addAll(Arrays.asList(Convert.toStrArray(r.getAncestors())));
            });
            if(resuIdSet.size() < 1)
                resuIdSet.add("-1");
            redisUtils.sAdd(key,resuIdSet.toArray(new String[]{}));
            redisUtils.expire(key,10, TimeUnit.MINUTES);
            idSet = resuIdSet;
        }else{
            idSet = redisUtils.sMembers(key);
        }
        XmslEngineeringReport query = new XmslEngineeringReport();
        query.setParentId(report.getParentId());
        query.setParams(ObjectUtils.toMap("ids", idSet));
        return xmslEngineeringReportMapper.getXmslEngineeringReportList(query);
    }



    public List<XmslEngineeringReport> getTreeListByPid(XmslEngineeringReport report) {
        if (report.getParams().get("pids") == null || "".equals(report.getParams().get("pids"))) {
            report.setParentId(ObjectUtils.nvlLong(report.getParentId(), -1L));
        }
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportMapper.getXmslEngineeringReportList(report);
        // 当parentIdb为空的时候 才转树
        if (report.getParentId() == null){
            xmslEngineeringReportList = TreeUtil.build(xmslEngineeringReportList, -1L);
        }
        
        return xmslEngineeringReportList;
    }

    @Transactional
    public int insertXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setId(IdWorker.createId());
        xmslEngineeringReport.setCreateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setCreateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.insertXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int insertXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList) {
        for (XmslEngineeringReport xmslEngineeringReport : xmslEngineeringReportList) {
            xmslEngineeringReport.setId(IdWorker.createId());
            xmslEngineeringReport.setCreateUser(SecurityUtils.getUserName());
            xmslEngineeringReport.setCreateTime(DateUtils.getNowDate());
        }
        return xmslEngineeringReportMapper.insertXmslEngineeringReportList(xmslEngineeringReportList);
    }

    @Transactional
    public int updateXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.updateXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int updateXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList) {
        for (XmslEngineeringReport xmslEngineeringReport : xmslEngineeringReportList) {
            xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
            xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslEngineeringReportMapper.updateXmslEngineeringReportList(xmslEngineeringReportList);
    }

    @Transactional
    public int deleteXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.deleteXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int deleteXmslEngineeringReportByPks(List<Long> xmslEngineeringReportPkList) {
        return xmslEngineeringReportMapper.deleteXmslEngineeringReportByPks(xmslEngineeringReportPkList);
    }
}
