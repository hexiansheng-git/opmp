package com.hhwy.sd.designDocumentApproval.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApprovalVo;
import com.hhwy.sd.designDocumentApproval.mapper.KcsjDesignDocumentApprovalMapper;
import com.hhwy.sd.designDocumentApproval.service.IKcsjDesignDocumentApprovalService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList = kcsjDesignDocumentApprovalVo.getKcsjDesignDocumentApprovalList();
        if (kcsjDesignDocumentApprovalList.size()>0) {

            //处理新增数据
            List<KcsjDesignDocumentApproval> insertKcsjDesignDocumentApprovals = kcsjDesignDocumentApprovalList.stream().filter(p -> StringUtils.isNotEmpty(p.getIsAdd()) && p.getIsAdd().equals("1")).collect(Collectors.toList());
            if (insertKcsjDesignDocumentApprovals.size() > 0) {
                //填充数据记录有关字段
                for (KcsjDesignDocumentApproval kcsjDesignDocumentApproval : insertKcsjDesignDocumentApprovals) {
                    kcsjDesignDocumentApproval.setId(IdWorker.createId());
                    kcsjDesignDocumentApproval.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                    kcsjDesignDocumentApproval.setCreateUser(SecurityUtils.getUserId().toString());
                    kcsjDesignDocumentApproval.setCreateTime(DateUtils.getNowDate());
                    kcsjDesignDocumentApproval.setDelFlag("0");
                }
                kcsjDesignDocumentApprovalMapper.insertKcsjDesignDocumentApprovalList(insertKcsjDesignDocumentApprovals);

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
}
