package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper.QqchLabourDemandPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.date.Getclasspath;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark
 */
@Service
public class QqchLabourDemandPlanServiceImpl implements IQqchLabourDemandPlanService{

    @Autowired
    private QqchLabourDemandPlanMapper qqchLabourDemandPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchLabourDemandPlan getQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        return qqchLabourDemandPlanMapper.getQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int insertQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setId(IdWorker.createId());
        qqchLabourDemandPlan.setCreateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setCreateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.insertQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int updateQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

    @Transactional
    public int updateQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList) {
        for (QqchLabourDemandPlan qqchLabourDemandPlan : qqchLabourDemandPlanList) {
            qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlanList(qqchLabourDemandPlanList);
    }

    @Transactional
    public int deleteQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

    @Transactional
    public int deleteQqchLabourDemandPlanByPks(List<Long> qqchLabourDemandPlanPkList) {
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList);
    }


    /**
     *  列表接口
     * @param qqchLabourDemandPlan
     * @return
     */
    public QqchLabourDemandPlanVo getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan) {
        QqchLabourDemandPlanVo labourDemandPlanVo = new QqchLabourDemandPlanVo();
        BigDecimal version = qqchLabourDemandPlan.getVersion();
        version = VersionUtil.getVersion("qqch_labour_demand_plan", version);
        qqchLabourDemandPlan.setVersion(version);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        List<QqchLabourDemandPlan> treeList = TreeUtil.build(qqchLabourDemandPlanList, 0l);
        labourDemandPlanVo.setVersion(version);
        labourDemandPlanVo.setStageIdentity(qqchReviewService.getStage());
        labourDemandPlanVo.setQqchLabourDemandPlanList(treeList);
        return labourDemandPlanVo;
    }

    /**
     *  保存/确认/提交
     * @param qqchLabourDemandPlanVo
     */
    @Override
    @Transactional
    public void save(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        String buttonMark = qqchLabourDemandPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchLabourDemandPlanVo.getVersion();
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanVo.getQqchLabourDemandPlanList();

        this.insertQqchLabourDemandPlanList(qqchLabourDemandPlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchLabourDemandPlanVo.getMenuId();
            String stageIdentity = qqchLabourDemandPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public List<QqchLabourDemandPlanDto> selectCount(QqchLabourDemandPlan qqchLabourDemandPlan) {
        List<Date> dates = Getclasspath.getmous(qqchLabourDemandPlan.getStartTime(), qqchLabourDemandPlan.getEndTime());
        List<String> jobNames = qqchLabourDemandPlan.getJobNames();
        //思路：
        // 根据工种名称拿到的id 即为子集的pid
        QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
        qqchLabourDemandPlan1.setJobNames(jobNames);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan1);

        List<QqchLabourDemandPlanDto> list = new ArrayList<>();
        for (QqchLabourDemandPlan labourDemandPlan : qqchLabourDemandPlanList) {
            qqchLabourDemandPlan.setPid(labourDemandPlan.getId());
            List<QqchLabourDemandPlanDto> list1= qqchLabourDemandPlanMapper.selectCount(qqchLabourDemandPlan);
            list.addAll(list1);
        }

        List<QqchLabourDemandPlanDto> list2 = new ArrayList<>();
        for (Date date : dates) {
            QqchLabourDemandPlanDto qqchLabourDemandPlanDto = new QqchLabourDemandPlanDto();
            qqchLabourDemandPlanDto.setTime(date);
            qqchLabourDemandPlanDto.setNum(new BigDecimal(0));
            list2.add(qqchLabourDemandPlanDto);
        }

        for (QqchLabourDemandPlanDto qqchLabourDemandPlanDto : list2) {
            for (QqchLabourDemandPlanDto labourDemandPlanDto : list) {
                String s = new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlanDto.getTime());
                String s1 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getStartTime());
                String s2 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getEndTime());
                if(s.equals(s1)&s.equals(s2)){
                    qqchLabourDemandPlanDto.setNum(qqchLabourDemandPlanDto.getNum().add(labourDemandPlanDto.getNum()));
                }
            }
        }

        return list2;
    }


    @Override
    public List<String> getAllWorkType(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        if(qqchLabourDemandPlanVo.getVersion()!=null){
            qqchLabourDemandPlan.setVersion(qqchLabourDemandPlanVo.getVersion());
        }
        qqchLabourDemandPlan.setPid(0l);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        List<String> strings = qqchLabourDemandPlanList.stream().map(QqchLabourDemandPlan::getJobName).collect(Collectors.toList());
        return strings;
    }


    public void insertQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList,BigDecimal version) {
        //删除旧数据
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        qqchLabourDemandPlan.setVersion(version);
        qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);

        if(CollectionUtils.isEmpty(qqchLabourDemandPlanList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        List<QqchLabourDemandPlan> configs = TreeUtil.treeToList(qqchLabourDemandPlanList);
        for (QqchLabourDemandPlan labourDemandPlan : configs) {
            labourDemandPlan.setValid(valid);
            labourDemandPlan.setVersion(version);
            labourDemandPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            labourDemandPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            labourDemandPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchLabourDemandPlanMapper.insertQqchLabourDemandPlanList(configs);
    }
}
