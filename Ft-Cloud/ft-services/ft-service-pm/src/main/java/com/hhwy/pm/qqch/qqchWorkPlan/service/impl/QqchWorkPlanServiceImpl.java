package com.hhwy.pm.qqch.qqchWorkPlan.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.gencode.enums.CodeEnum;
import com.hhwy.gencode.service.GenCodeService;
import com.hhwy.pm.common.mapper.WzchCommonMapper;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.mapper.QqchWorkPlanMapper;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
@Service
public class QqchWorkPlanServiceImpl implements IQqchWorkPlanService {

    @Autowired
    private QqchWorkPlanMapper qqchWorkPlanMapper;
    @Autowired
    private WzchCommonMapper wzchCommonMapper;

    private final static String ONE = "1";//菜单进入
    private final static String TWO = "2";//详情和编辑
    private final static String THREE = "3";//调整
    @Override
    public BaseEntity baseInfo(Map<String, String> map) {
        // 主键
        String id = map.get("id");
        // 操作类型 1-菜单进入; 2-详情和编辑; 3-调整
        String type = map.get("type");
        CommonAssert.notBlank(type, "类型不能为空");

        QqchWorkPlan busData = new QqchWorkPlan();

        if (ONE.equals(type)) {
            /*此项目中的最大版本*/
            Long versionMax = wzchCommonMapper.selectCanAdjustOnly("qqch_work_plan");
            BigDecimal versionCode = null;
            if (ObjectNullUtil.isEmpty(versionMax)) {//代表 新增
                versionCode = new BigDecimal("1.0");
                busData.setId(IdWorker.createId());
                busData.setVersion(versionCode);
            } else {//代表编辑
                versionCode = new BigDecimal(versionMax);
                QqchWorkPlan workPlan = new QqchWorkPlan();
                workPlan.setVersion(versionCode);
                QqchWorkPlan plan = this.getQqchWorkPlan(workPlan);
                BeanUtils.copyProperties(plan,busData);
            }
            busData.setVersionStr("v" + versionCode);
            // 设置创建信息
            EntityUtils.setCreateUpdateInfo(busData);
            return busData;
        } else {
            CommonAssert.notBlank(id, "id不能为空");
            long busId = Long.parseLong(id);
            QqchWorkPlan qqchWorkPlan = new QqchWorkPlan();
            qqchWorkPlan.setId(busId);
            BeanUtils.copyProperties(this.getQqchWorkPlan(qqchWorkPlan), busData);
//            qqchWorkPlanD detail = new SbchEquipmentLeaseDetails();
//            detail.setMainId(busId);
//            detail.setDelFlag("0");
//            List<SbchEquipmentLeaseDetails> detailList = detailsService.selectSbchEquipmentLeaseDetailsList(detail);
//            busData.setDetailsList(detailList);
            // 如果用户的操作类型是调整就需要将单据编号的版本+1
            if (THREE.equals(type)) {
                EntityUtils.setCreateInfo(busData);
                BigDecimal versionCode = busData.getVersion().add(BigDecimal.ONE);
                busData.setVersion(versionCode);
                busData.setVersionStr("v" + versionCode);
                return busData;
            } else {
                busData.setVersionStr("v" + busData.getVersion().toString());
            }
            return busData;
        }
    }


    public QqchWorkPlan getQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        return qqchWorkPlanMapper.getQqchWorkPlan(qqchWorkPlan);
    }

    public List<QqchWorkPlan> getQqchWorkPlanList(QqchWorkPlan qqchWorkPlan) {
        return qqchWorkPlanMapper.getQqchWorkPlanList(qqchWorkPlan);
    }

    @Transactional
    public int insertQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        qqchWorkPlan.setId(IdWorker.createId());
        qqchWorkPlan.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlan.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlanMapper.insertQqchWorkPlan(qqchWorkPlan);
    }

    @Transactional
    public int insertQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList) {
        for (QqchWorkPlan qqchWorkPlan : qqchWorkPlanList) {
            qqchWorkPlan.setId(IdWorker.createId());
            qqchWorkPlan.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanMapper.insertQqchWorkPlanList(qqchWorkPlanList);
    }

    @Transactional
    public int updateQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        qqchWorkPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanMapper.updateQqchWorkPlan(qqchWorkPlan);
    }

    @Transactional
    public int updateQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList) {
        for (QqchWorkPlan qqchWorkPlan : qqchWorkPlanList) {
            qqchWorkPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchWorkPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanMapper.updateQqchWorkPlanList(qqchWorkPlanList);
    }

    @Transactional
    public int deleteQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        qqchWorkPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanMapper.deleteQqchWorkPlan(qqchWorkPlan);
    }

    @Transactional
    public int deleteQqchWorkPlanByPks(List<Long> qqchWorkPlanPkList) {
        return qqchWorkPlanMapper.deleteQqchWorkPlanByPks(qqchWorkPlanPkList);
    }
}
