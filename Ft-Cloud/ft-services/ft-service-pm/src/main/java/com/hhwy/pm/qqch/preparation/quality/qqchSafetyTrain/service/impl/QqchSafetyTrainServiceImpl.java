package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.mapper.QqchSafetyTrainMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.IQqchSafetyTrainService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author ldd
 * @date 2023-08-10 18:38:55
 * @remark 8.9 安全培训策划
 */
@Service
@Slf4j
public class QqchSafetyTrainServiceImpl implements IQqchSafetyTrainService {

    @Autowired
    private QqchSafetyTrainMapper qqchSafetyTrainMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;


    public QqchSafetyTrain getQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        return qqchSafetyTrainMapper.getQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int insertQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setId(IdWorker.createId());
        qqchSafetyTrain.setCreateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setCreateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.insertQqchSafetyTrain(qqchSafetyTrain);
    }


    @Transactional
    public int updateQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.updateQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int updateQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList) {
        for (QqchSafetyTrain qqchSafetyTrain : qqchSafetyTrainList) {
            qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
            qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafetyTrainMapper.updateQqchSafetyTrainList(qqchSafetyTrainList);
    }

    @Transactional
    public int deleteQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.deleteQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int deleteQqchSafetyTrainByPks(List<Long> qqchSafetyTrainPkList) {
        return qqchSafetyTrainMapper.deleteQqchSafetyTrainByPks(qqchSafetyTrainPkList);
    }

    /**
     *  列表接口
     * @param qqchSafetyTrain
     * @return
     */
    public QqchSafetyTrainVo getQqchSafetyTrainList(QqchSafetyTrain qqchSafetyTrain) {
        QqchSafetyTrainVo vo = new QqchSafetyTrainVo();

        BigDecimal version = qqchSafetyTrain.getVersion();
        version = VersionUtil.getVersion("qqch_safety_train", version);

        qqchSafetyTrain.setVersion(version);
        List<QqchSafetyTrain> qqchSafetyTrainList = qqchSafetyTrainMapper.getQqchSafetyTrainList(qqchSafetyTrain);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSafetyTrainList(qqchSafetyTrainList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchSafetyTrainVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSafetyTrain> qqchSafetyTrainList = vo.getQqchSafetyTrainList();

        this.insertQqchSafetyTrainList(qqchSafetyTrainList, version);
        if(CollectionUtils.isEmpty(qqchSafetyTrainList)){
            return;
        }

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //校验数据必填
            JyDetailsUtil.jyDetails(qqchSafetyTrainList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList,BigDecimal version) {
        //删除旧数据
        QqchSafetyTrain qqchSafetyTrain1 = new QqchSafetyTrain();
        qqchSafetyTrain1.setVersion(version);
        qqchSafetyTrainMapper.deleteQqchSafetyTrain(qqchSafetyTrain1);

        if(CollectionUtils.isEmpty(qqchSafetyTrainList)){
            return;
        }

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchSafetyTrain qqchSafetyTrain : qqchSafetyTrainList) {
            qqchSafetyTrain.setId(IdWorker.createId());
            qqchSafetyTrain.setValid(valid);
            qqchSafetyTrain.setVersion(version);
            if (qqchSafetyTrain.getCreateTime()==null) {
                EntityUtils.setCreateUpdateInfo(qqchSafetyTrain);
            } else {
                EntityUtils.setUpdateInfo(qqchSafetyTrain);
            }
        }
        qqchSafetyTrainMapper.insertQqchSafetyTrainList(qqchSafetyTrainList);
    }

    @Override
    public void workGroupSetUpWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        //所有的预警信息
        List<TWarn> qqchSafetyTrainListHave = new ArrayList<>();
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                if(projectInfo == null){
                    continue;
                }
                QqchSafetyTrain qqchSafetyTrain = new QqchSafetyTrain();
                QqchSafetyTrainVo qqchSafetyTrainVo = this.getQqchSafetyTrainList(qqchSafetyTrain);
                String nowDate=DateUtils.getDate();
                if (qqchSafetyTrainVo != null && qqchSafetyTrainVo.getQqchSafetyTrainList() != null && qqchSafetyTrainVo.getQqchSafetyTrainList().size() > 0) {
                    qqchSafetyTrainVo.getQqchSafetyTrainList().forEach(item->{
                        if (item.getTime() != null) {
                            String trainTime = DateUtils.parseDateToStr("yyyy-MM-dd", item.getTime());
                            LocalDate localDate = item.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            //提前三天进行预警
                            LocalDate threeDaysBefore = localDate.minusDays(3);
                            Date dateNew1 = Date.from(threeDaysBefore.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                            System.out.println("提前三天日期对象转date：" + dateNew1);
                            String tranTime = DateUtils.parseDateToStr("yyyy-MM-dd", dateNew1);
                            if (nowDate.equals(tranTime)) {
                                TWarn warn = new TWarn();
                                warn.setProjectName(projectInfo.getProjectName());
//                                warn.setBusinessId(item.getId());
                                warn.setCreateTime(DateUtils.getNowDate());
                                warn.setTenantKey(tenantKey);
                                warn.setWarnItem(WarnItem.AQCH_SAFETY_TRAIN.getWarnItem());
                                warn.setWarnItemId(WarnItem.AQCH_SAFETY_TRAIN.getWarnItemId());
                                warn.setWarnScopeType("4");
                                warn.setWarnScope("lead_engineer");
                                String warnContent = "您好，【"+projectInfo.getProjectName()+"】项目中培训类型【"+item.getTrainType()+"】即将开始培训，培训时间为【"+trainTime+"】，请提前做好准备";
                                warn.setWarnContent(warnContent);
                                warn.setWarnUrl("/preliminaryPlanning/SafetyPlan");
                                qqchSafetyTrainListHave.add(warn);
                            }
                        }
                    });
                }
            }
            if (qqchSafetyTrainListHave.size()>0) {
                log.info("预警信息列表为："+JSON.toJSONString(qqchSafetyTrainListHave));
                systemServiceApi.insertTWarnListToGm(qqchSafetyTrainListHave);
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
