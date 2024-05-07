package com.hhwy.sp.experiment.mixRatioManage.service.impl;

import java.util.Date;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaff;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.SgjsMixRatioManageSaveVo;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageStaffMapper;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageStaffRecordMapper;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaffRecord;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageStaffRecordService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 施工方案管理-施工方案评审-人员意见记录Service业务层处理
 * 
 * @author wk
 * @date 2024-04-29
 */
@Service
public class SgjsMixRatioManageStaffRecordServiceImpl implements ISgjsMixRatioManageStaffRecordService {
    @Autowired
    private SgjsMixRatioManageStaffRecordMapper sgjsMixRatioManageStaffRecordMapper;
    @Autowired
    private SgjsMixRatioManageStaffMapper sgjsMixRatioManageStaffMapper;

    /**
     * 查询施工方案管理-施工方案评审-人员意见记录
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 施工方案管理-施工方案评审-人员意见记录
     */
    @Override
    public SgjsMixRatioManageStaffRecord selectSgjsMixRatioManageStaffRecordById(Long id) {
        return sgjsMixRatioManageStaffRecordMapper.selectSgjsMixRatioManageStaffRecordById(id);
    }

    /**
     * 查询施工方案管理-施工方案评审-人员意见记录列表
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 施工方案管理-施工方案评审-人员意见记录
     */
    @Override
    public List<SgjsMixRatioManageStaffRecord> selectSgjsMixRatioManageStaffRecordList(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord) {
        return sgjsMixRatioManageStaffRecordMapper.selectSgjsMixRatioManageStaffRecordList(sgjsMixRatioManageStaffRecord);
    }

    /**
     * 新增施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    @Override
    public int insertSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord) {
        sgjsMixRatioManageStaffRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageStaffRecordMapper.insertSgjsMixRatioManageStaffRecord(sgjsMixRatioManageStaffRecord);
    }

    @Override
    @Transactional
    public void saveRecord(SgjsMixRatioManageSaveVo mixRatioManage) {
        Assert.notNull(mixRatioManage,"数据缺失");
        Assert.notNull(mixRatioManage.getId(),"数据缺失");
        SgjsMixRatioManageStaff query = new SgjsMixRatioManageStaff();
        query.setReviewStaffId(SecurityUtils.getUserName());
        query.setMainId(mixRatioManage.getId());
        List<SgjsMixRatioManageStaff> staffList = sgjsMixRatioManageStaffMapper.selectSgjsMixRatioManageStaffList(query);
        Assert.isTrue(!CollectionUtils.isEmpty(staffList),"未获取到审批记录信息，当前用户可能无权限提交审批意见");
        SgjsMixRatioManageStaff staff = staffList.get(0);
        Date today = new Date();
        staff.setSubmitTime(today);
        new AddBaseInfoUtil<>().updateBaseEntity(staff);
        sgjsMixRatioManageStaffMapper.updateSgjsMixRatioManageStaff(staff);
        //清理评审意见
        sgjsMixRatioManageStaffRecordMapper.deleteRecord(mixRatioManage.getId(),staff.getId());
        List<SgjsMixRatioManageStaffRecord> recordList = mixRatioManage.getRecordList();
        for (int i = 0; i < recordList.size(); i++) {
            SgjsMixRatioManageStaffRecord temp = recordList.get(i);
            temp.setId(IdWorker.createId());
            temp.setMainId(mixRatioManage.getId());
            temp.setStaffId(staff.getId());
            temp.setSubmitTime(today);
            new AddBaseInfoUtil<>().addBaseEntity(temp);
        }
        this.sgjsMixRatioManageStaffRecordMapper.batchInsert(recordList);
    }

    /**
     * 修改施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    @Override
    public int updateSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord) {
        sgjsMixRatioManageStaffRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageStaffRecordMapper.updateSgjsMixRatioManageStaffRecord(sgjsMixRatioManageStaffRecord);
    }

    /**
     * 删除施工方案管理-施工方案评审-人员意见记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsMixRatioManageStaffRecordByIds(String ids) {
        return sgjsMixRatioManageStaffRecordMapper.deleteSgjsMixRatioManageStaffRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除施工方案管理-施工方案评审-人员意见记录信息
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffRecordById(Long id) {
        return sgjsMixRatioManageStaffRecordMapper.deleteSgjsMixRatioManageStaffRecordById(id);
    }

    @Override
    @Transactional
    public int batchInsert(List<SgjsMixRatioManageStaffRecord> list) {
        if(CollectionUtils.isEmpty(list))
            return 0; 
        return sgjsMixRatioManageStaffRecordMapper.batchInsert(list);
    }
}
