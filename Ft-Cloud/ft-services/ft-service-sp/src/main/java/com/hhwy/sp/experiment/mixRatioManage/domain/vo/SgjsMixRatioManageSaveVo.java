package com.hhwy.sp.experiment.mixRatioManage.domain.vo;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaffRecord;
import lombok.Data;

import java.util.List;

/**
 *     
 *  *
 *  * @Author:       wk   
 *  * @CreateDate:   [ ]   
 *  * @UpdateUser:   []   
 *  * @UpdateDate:   [ ]   
 *  * @UpdateRemark: [说明本次修改内容]  
 *  * @Version:      [v1.0] 
 *  
 */
@Data
public class SgjsMixRatioManageSaveVo extends SgjsMixRatioManage {
    
    private Integer operType;
    
    //审批人username集合
    private List<String> approvalUserList ;
    
    private List<SgjsMixRatioManageStaffRecord> recordList;
    
//    private List<SgjsMixRatioManageStaffRecord> ;
    
    
    
    
}
