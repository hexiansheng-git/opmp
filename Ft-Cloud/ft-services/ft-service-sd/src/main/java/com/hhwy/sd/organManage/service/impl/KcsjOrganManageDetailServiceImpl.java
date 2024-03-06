package com.hhwy.sd.organManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail;
import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail4Update;
import com.hhwy.sd.organManage.mapper.KcsjOrganManageDetailMapper;
import com.hhwy.sd.organManage.service.IKcsjOrganManageDetailService;
import com.hhwy.sd.organManage.service.IKcsjOrganManageService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author cjh
 * @date 2023-12-14 11:31:53
 * @remark
 */
@Service
public class KcsjOrganManageDetailServiceImpl implements IKcsjOrganManageDetailService {

    @Autowired
    private KcsjOrganManageDetailMapper kcsjOrganManageDetailMapper;

    @Autowired
    private IKcsjOrganManageService kcsjOrganManageService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;

    private Logger logger= LoggerFactory.getLogger(KcsjOrganManageDetailServiceImpl.class);

    public KcsjOrganManageDetail getKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail) {
        return kcsjOrganManageDetailMapper.getKcsjOrganManageDetail(kcsjOrganManageDetail);
    }

    public List<KcsjOrganManageDetail> getKcsjOrganManageDetailList(KcsjOrganManageDetail kcsjOrganManageDetail) {
        return kcsjOrganManageDetailMapper.getKcsjOrganManageDetailList(kcsjOrganManageDetail);
    }

    public List<KcsjOrganManageDetail> getKcsjOrganManageDetailList() {
        return kcsjOrganManageDetailMapper.getKcsjOrganManageDetailList(new KcsjOrganManageDetail());
    }

    @Transactional
    public int insertKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail) {
        kcsjOrganManageDetail.setId(IdWorker.createId());
        kcsjOrganManageDetail.setCreateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManageDetail.setCreateTime(DateUtils.getNowDate());
        return kcsjOrganManageDetailMapper.insertKcsjOrganManageDetail(kcsjOrganManageDetail);
    }

    @Transactional
    public int insertKcsjOrganManageDetailList(List<KcsjOrganManageDetail> kcsjOrganManageDetailList) {
        for (KcsjOrganManageDetail kcsjOrganManageDetail : kcsjOrganManageDetailList) {
            kcsjOrganManageDetail.setId(IdWorker.createId());
            kcsjOrganManageDetail.setCreateUser(SecurityUtils.getSysUser().getNickName());
            kcsjOrganManageDetail.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjOrganManageDetailMapper.insertKcsjOrganManageDetailList(kcsjOrganManageDetailList);
    }

    @Transactional
    public int updateKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail) {
        kcsjOrganManageDetail.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManageDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjOrganManageDetailMapper.updateKcsjOrganManageDetail(kcsjOrganManageDetail);
    }

    @Transactional
    public int updateKcsjOrganManageDetailList(List<KcsjOrganManageDetail> kcsjOrganManageDetailList) {
        for (KcsjOrganManageDetail kcsjOrganManageDetail : kcsjOrganManageDetailList) {
            kcsjOrganManageDetail.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            kcsjOrganManageDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjOrganManageDetailMapper.updateKcsjOrganManageDetailList(kcsjOrganManageDetailList);
    }

    @Override
    public int updateKcsjOrganManageDetailList(KcsjOrganManageDetail4Update kcsjOrganManageDetail4Update) {
        Long organManageId = kcsjOrganManageDetail4Update.getOrganManageId();
        if(organManageId == null) {
            return 0;
        }
        int i = deleteKcsjOrganManageDetailByOrganManageId(organManageId);
        List<KcsjOrganManageDetail> kcsjOrganManageDetailList = kcsjOrganManageDetail4Update.getKcsjOrganManageDetailList();

        Date enterDate = null;
        Date leaveDate = null;
        if(CollectionUtils.isNotEmpty(kcsjOrganManageDetailList)) {
            for (KcsjOrganManageDetail vo: kcsjOrganManageDetailList) {
                vo.setOrganManageId(organManageId);
                Date entryDate = vo.getEntryDate();
                if(enterDate == null) {
                    enterDate = entryDate;
                } else {
                    if(entryDate != null) {
                        if(entryDate.before(enterDate)) enterDate = entryDate;
                    }
                }
                Date leaveDate1 = vo.getLeaveDate();
                if(leaveDate1 == null) {
                    leaveDate = leaveDate1;
                } else {
                    if(leaveDate == null) {
                        leaveDate = leaveDate1;
                    } else {
                        if(leaveDate1.after(leaveDate)) leaveDate = leaveDate1;
                    }
                }
            }
            i = insertKcsjOrganManageDetailList(kcsjOrganManageDetailList);
        }

        kcsjOrganManageService.updateKcsjOrganManage(organManageId, enterDate, leaveDate);
        syncDataToGm(kcsjOrganManageDetail4Update);
        return i;
    }

    /**
     * 总部版同步
     *
     * @param kcsjOrganManageDetail4Update
     */
    private void syncDataToGm(KcsjOrganManageDetail4Update kcsjOrganManageDetail4Update) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("kcsj_organ_manage_detail:tenantSuccess", JSONObject.toJSONString(kcsjOrganManageDetail4Update));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("kcsj_organ_manage_detail");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(kcsjOrganManageDetail4Update));
            logger.error("kcsj_organ_manage_detail同步失败【{}】,时间：【{}】",JSONObject.toJSONString(kcsjOrganManageDetail4Update),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    public int deleteKcsjOrganManageDetailByOrganManageId(Long organManageId) {
        return kcsjOrganManageDetailMapper.deleteKcsjOrganManageDetailByOrganManageId(organManageId);
    };

    @Transactional
    public int deleteKcsjOrganManageDetail(KcsjOrganManageDetail kcsjOrganManageDetail) {
        kcsjOrganManageDetail.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        kcsjOrganManageDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjOrganManageDetailMapper.deleteKcsjOrganManageDetail(kcsjOrganManageDetail);
    }

    @Transactional
    public int deleteKcsjOrganManageDetailByPks(List<Long> kcsjOrganManageDetailPkList) {
        return kcsjOrganManageDetailMapper.deleteKcsjOrganManageDetailByPks(kcsjOrganManageDetailPkList);
    }

    @Override
    public void deleteKcsjOrganManageDetail4All() {
        kcsjOrganManageDetailMapper.deleteKcsjOrganManageDetail4All();
    }
}
