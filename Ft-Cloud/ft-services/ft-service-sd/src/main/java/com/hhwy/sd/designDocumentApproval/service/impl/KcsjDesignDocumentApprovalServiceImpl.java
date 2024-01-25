package com.hhwy.sd.designDocumentApproval.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApprovalVo;
import com.hhwy.sd.designDocumentApproval.mapper.KcsjDesignDocumentApprovalMapper;
import com.hhwy.sd.designDocumentApproval.service.IKcsjDesignDocumentApprovalService;
import com.hhwy.system.api.RemoteNotifyService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark 勘察设计-设计文件报批
 */
@Service
public class KcsjDesignDocumentApprovalServiceImpl implements IKcsjDesignDocumentApprovalService {

    @Autowired
    private KcsjDesignDocumentApprovalMapper kcsjDesignDocumentApprovalMapper;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RemoteNotifyService remoteNotifyService;


    public KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        return kcsjDesignDocumentApprovalMapper.getKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    /**
     * 分页列表查询
     *
     * @param kcsjDesignDocumentApproval
     * @return
     */
    public List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {

        //设置搜索条件

        //报监理业主日期
        if (StringUtils.isNotEmpty(kcsjDesignDocumentApproval.getReportSupervisingOwnerDateStr())) {
            String reportSupervisingOwnerDateStr = kcsjDesignDocumentApproval.getReportSupervisingOwnerDateStr();
            String[] split = reportSupervisingOwnerDateStr.split("-");
            kcsjDesignDocumentApproval.setReportSupervisingOwnerDateBegin(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            kcsjDesignDocumentApproval.setReportSupervisingOwnerDateEnd(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }

        //下次跟进日期
        if (StringUtils.isNotEmpty(kcsjDesignDocumentApproval.getNextFollowupDateStr())) {
            String nextFollowupDateStr = kcsjDesignDocumentApproval.getNextFollowupDateStr();
            String[] split1 = nextFollowupDateStr.split("-");
            kcsjDesignDocumentApproval.setNextFollowupDateBegin(FtDateUtils.parseDate(split1[0].replaceAll("(?:年|月|日)", "-")));
            kcsjDesignDocumentApproval.setNextFollowupDateEnd(FtDateUtils.parseDate(split1[1].replaceAll("(?:年|月|日)", "-")));

        }
        //实际反馈日期
        if (StringUtils.isNotEmpty(kcsjDesignDocumentApproval.getActualFeedbackDateStr())) {
            String actualFeedbackDateStr = kcsjDesignDocumentApproval.getActualFeedbackDateStr();
            String[] split2 = actualFeedbackDateStr.split("-");
            kcsjDesignDocumentApproval.setActualFeedbackDateBegin(FtDateUtils.parseDate(split2[0].replaceAll("(?:年|月|日)", "-")));
            kcsjDesignDocumentApproval.setActualFeedbackDateEnd(FtDateUtils.parseDate(split2[1].replaceAll("(?:年|月|日)", "-")));

        }

        return kcsjDesignDocumentApprovalMapper.getKcsjDesignDocumentApprovalList(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setId(IdWorker.createId());
        kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int insertKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList) {
        for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : kcsjDesignDocumentApprovalList) {
            kcsjDesignDocumentApproval.setId(IdWorker.createId());
            kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserName());
            kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalList);
    }


    /**
     * 批量保存
     *
     * @param kcsjDesignDocumentApprovalVo
     * @return
     */
    @Transactional
    public AjaxResult saveKcsjDesignDocumentApprovalList(KcsjDesignDocumentApprovalVo kcsjDesignDocumentApprovalVo) {

        //处理删除数据
        List<String> delIdList = kcsjDesignDocumentApprovalVo.getDelIdList();
        List<Long> delIds = new ArrayList<>();
        if (kcsjDesignDocumentApprovalVo.getDelIdList().size() > 0) {
            for (String s : delIdList) {
                delIds.add(Long.valueOf(s));
            }
            String delUser = SecurityUtils.getSysUser().getNickName();
            kcsjDesignDocumentApprovalMapper.deleteKcsjDesignDocumentApprovalByIdPks(delIds, delUser);
        }

        List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList = kcsjDesignDocumentApprovalVo.getKcsjDesignDocumentApprovalList();
        if (kcsjDesignDocumentApprovalList.size() > 0) {
            //处理新增数据
            List<KcsjDesignDocumentApproval> insertKcsjDesignDocumentApprovals = kcsjDesignDocumentApprovalList.stream().filter(p -> StringUtils.isNotEmpty(p.getIsAdd()) && p.getIsAdd().equals("1")).collect(Collectors.toList());
            if(insertKcsjDesignDocumentApprovals.size()>0){
                //填充数据记录有关字段
                for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : insertKcsjDesignDocumentApprovals) {
                    kcsjDesignDocumentApproval.setId(IdWorker.createId());
                    kcsjDesignDocumentApproval.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                    kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserId().toString());
                    kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
                    kcsjDesignDocumentApproval.setPtVar1(kcsjDesignDocumentApproval.getManagerUserName());
                    kcsjDesignDocumentApproval.setDelFlag("0");
                }
                kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApprovalList(insertKcsjDesignDocumentApprovals);
            }
        }
        //处理更新数据
        List<KcsjDesignDocumentApproval> updateKcsjDesignDocumentApprovals = kcsjDesignDocumentApprovalList.stream().filter(p -> StringUtils.isEmpty(p.getIsAdd()) || (!p.getIsAdd().equals("1"))).collect(Collectors.toList());
        if (updateKcsjDesignDocumentApprovals.size() > 0) {
            for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : updateKcsjDesignDocumentApprovals) {
                kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
            }
            kcsjDesignDocumentApprovalMapper.updateKcsjDesignDocumentApprovalList(updateKcsjDesignDocumentApprovals);
        }
        return AjaxResult.success();
    }

    @Transactional
    public int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.updateKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int updateKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList) {
        for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : kcsjDesignDocumentApprovalList) {
            kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
            kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjDesignDocumentApprovalMapper.updateKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalList);
    }

    @Transactional
    public int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval) {
        kcsjDesignDocumentApproval.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignDocumentApproval.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignDocumentApprovalMapper.deleteKcsjDesignDocumentApproval(kcsjDesignDocumentApproval);
    }

    @Transactional
    public int deleteKcsjDesignDocumentApprovalByPks(List<Long> kcsjDesignDocumentApprovalPkList) {
        return kcsjDesignDocumentApprovalMapper.deleteKcsjDesignDocumentApprovalByPks(kcsjDesignDocumentApprovalPkList);
    }


    @Override
    public void designFileTask() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try{
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //超过跟踪日期后未进行填写实际反馈日期及反馈情况，发送消息提醒负责人进行跟进填写日期后允许删除，显示空白
                //nextFollowupDate 查下次跟进日期大于等于今日，且实际反馈内容和反馈日期不等于空的
                List<KcsjDesignDocumentApproval> list = kcsjDesignDocumentApprovalMapper.selectByFollowUpDate();
                for (KcsjDesignDocumentApproval approval: list) {
                    Date actualFeedbackDate = approval.getActualFeedbackDate();//实际反馈日期
                    String feedbackSituation = approval.getFeedbackSituation();//实际反馈情况
                    //二者只要有一个空 就发消息
                    if(null==actualFeedbackDate || StringUtils.isEmpty(feedbackSituation)){
                        //用户id   system   提示内容
                        String userName = approval.getPtVar1();
                        remoteNotifyService.publish(userName,"system","勘察设计--文件报批：您有未填写的内容，请尽快处理！");
                    }
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

    }
}
