package com.hhwy.sp.designChangeList.vo;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 *  
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/4/16 11:13   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/4/16 11:13    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Data
public class ChangeManagSaveVo extends SgjsDesignChangeManage {

    public String submitFlag;
    
    @NotBlank(message = "WBS不能为空",groups = {ValidationGroups.Other.class})
    private List<SgjsDesignChangeWbs> wbsList;
//    @NotBlank(message = "清单不能为空",groups = {ValidationGroups.Other.class})
//    private List<SgjsDesignChangeList> list;
    
}
