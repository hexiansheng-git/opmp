package com.hhwy.pm.qqch.sgch.milestone.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.sgch.milestone.domain.QqchMilestone;
import com.hhwy.pm.qqch.sgch.milestone.mapper.QqchMilestoneMapper;
import com.hhwy.pm.qqch.sgch.milestone.service.IQqchMilestoneService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:18:27
 * @remark
 */
@Service
public class QqchMilestoneServiceImpl implements IQqchMilestoneService {

    private final static String TN = "qqch_milestone";

    @Autowired
    private QqchMilestoneMapper qqchMilestoneMapper;

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemServicel;


    public QqchMilestone getQqchMilestone(QqchMilestone qqchMilestone) {
        return qqchMilestoneMapper.getQqchMilestone(qqchMilestone);
    }

    public List<QqchMilestone> getQqchMilestoneList(QqchMilestone qqchMilestone) {
//        BigDecimal version = VersionUtil.getVersion(QqchMilestone.TABLE_NAME, qqchMilestone.getVersion());
//        qqchMilestone.setVersion(version);
        return qqchMilestoneMapper.getQqchMilestoneList(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setId(IdWorker.createId());
        qqchMilestone.setCreateUser(SecurityUtils.getUserName());
        qqchMilestone.setCreateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.insertQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMilestoneList(List<QqchMilestone> qqchMilestoneList) {
        if(CollectionUtils.isEmpty(qqchMilestoneList)) {
            return 0;
        }
        for (QqchMilestone qqchMilestone : qqchMilestoneList) {
            qqchMilestone.setId(IdWorker.createId());
            qqchMilestone.setCreateUser(SecurityUtils.getUserName());
            qqchMilestone.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMilestoneMapper.insertQqchMilestoneList(qqchMilestoneList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
        qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.updateQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMilestoneList(List<QqchMilestone> qqchMilestoneList) {
        if(CollectionUtils.isEmpty(qqchMilestoneList)) {
            return 0;
        }
        for (QqchMilestone qqchMilestone : qqchMilestoneList) {
            qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
            qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMilestoneMapper.updateQqchMilestoneList(qqchMilestoneList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
        qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.deleteQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMilestoneByPks(List<Long> qqchMilestonePkList) {
        return qqchMilestoneMapper.deleteQqchMilestoneByPks(qqchMilestonePkList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMilestoneByVos(List<QqchMilestone> qqchMilestoneList) {
        if(CollectionUtils.isEmpty(qqchMilestoneList)) {
            return 0;
        }
        List<Long> ids = new ArrayList<>();
        for (QqchMilestone qqchMilestone : qqchMilestoneList) {
            ids.add(qqchMilestone.getId());
        }
        return qqchMilestoneMapper.deleteQqchMilestoneByPks(ids);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileEntity list(QqchMilestone qqchMilestoneParam) {

        CompileEntity compileEntity = new CompileEntity();
//        List<QqchMilestone> qqchMilestoneList = getQqchMilestoneList(qqchMilestoneParam);
//        compileEntity.setDto(qqchMilestoneList);
        compileEntity.setVersion(BigDecimal.ONE);
        compileEntity.setStageIdentity("1");
        return compileEntity;
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Transactional(rollbackFor = Exception.class)
    public void save(List<QqchMilestone> list) {
        if(CollectionUtils.isEmpty(list)) {
            return;
        }
        for (QqchMilestone qqchMilestone : list) {
            if(qqchMilestone.getJobCode() != null) qqchMilestone.setId(IdWorker.createId());
        }
        this.qqchMilestoneMapper.insertQqchMilestoneList(list);
    }

    /**
     * 从总体进度计划中获取p6数据
     * @param qqchMilestoneParam
     * @return
     */
    @Override
    public List<QqchMilestone> saveDataFromMainP6(QqchMilestone qqchMilestoneParam) {

        BigDecimal version = VersionUtil.getVersion(QqchMilestone.TABLE_NAME, qqchMilestoneParam.getVersion());
        QqchMilestone query = new QqchMilestone();
        query.setVersion(version);
        List<QqchMilestone> qqchMilestoneList = getQqchMilestoneList(query);

        List<QqchMilestone> returnList = new ArrayList<>();

        List<QqchMainPlanItem> milestoneList = qqchMainPlanItemServicel.getMilestoneList(null);

        if (CollectionUtils.isEmpty(milestoneList)) {
            return returnList;
        }

        if(!CollectionUtils.isEmpty(qqchMilestoneList))deleteQqchMilestoneByVos(qqchMilestoneList);

        for (QqchMainPlanItem qqchMainPlanItem: milestoneList) {
            QqchMilestone qqchMilestone = new QqchMilestone();
            qqchMilestone.setId(IdWorker.createId());
            qqchMilestone.setVersion(version);
            qqchMilestone.setJob(qqchMainPlanItem.getItemName());
            String itemCode = qqchMainPlanItem.getItemCode();
            qqchMilestone.setJobCode(itemCode);
            qqchMilestone.setBeginDate(qqchMainPlanItem.getStartDate());
            qqchMilestone.setEndDate(qqchMainPlanItem.getFinishDate());
            if(!CollectionUtils.isEmpty(qqchMilestoneList)) {
                QqchMilestone qqchMilestone1 = qqchMilestoneList.stream().filter(vo -> itemCode != null && itemCode.equals(vo.getJobCode())).findFirst().orElse(null);
                if(qqchMilestone1 != null) qqchMilestone.setRemark(qqchMilestone1.getRemark());
            }
            qqchMilestone.setValid("0");
            returnList.add(qqchMilestone);
        }

        insertQqchMilestoneList(returnList);

        return returnList;
    }
}
