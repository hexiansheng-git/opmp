package com.hhwy.sp.experiment.mixRatioManage.mapper;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaffRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 施工方案管理-施工方案评审-人员意见记录Mapper接口
 * 
 * @author wk
 * @date 2024-04-29
 */
public interface SgjsMixRatioManageStaffRecordMapper {
    /**
     * 查询施工方案管理-施工方案评审-人员意见记录
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 施工方案管理-施工方案评审-人员意见记录
     */
    public SgjsMixRatioManageStaffRecord selectSgjsMixRatioManageStaffRecordById(Long id);

    /**
     * 查询施工方案管理-施工方案评审-人员意见记录列表
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 施工方案管理-施工方案评审-人员意见记录集合
     */
    public List<SgjsMixRatioManageStaffRecord> selectSgjsMixRatioManageStaffRecordList(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    /**
     * 新增施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    public int insertSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    public int batchInsert(@Param("dataList")List<SgjsMixRatioManageStaffRecord> list);

    /**
     * 修改施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    public int updateSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    @Update("update sgjs_mix_ratio_manage_staff_record set del_flag = 0 where main_id = #{mainId} and staff_id = #{staffId}")
    public int deleteRecord(@Param("mainId")Long mainId,@Param("staffId")Long staffId); 
    
    /**
     * 删除施工方案管理-施工方案评审-人员意见记录
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffRecordById(Long id);

    /**
     * 批量删除施工方案管理-施工方案评审-人员意见记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffRecordByIds(String[] ids);
}
