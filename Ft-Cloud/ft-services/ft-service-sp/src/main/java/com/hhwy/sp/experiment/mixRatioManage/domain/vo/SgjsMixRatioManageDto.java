package com.hhwy.sp.experiment.mixRatioManage.domain.vo;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaff;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@Data
public class SgjsMixRatioManageDto extends SgjsMixRatioManage {
    
    //是否显示个人评审意见
    private Integer personSuggFlag = 0;
    //是否显示评审意见表格
    private Integer sugguestionFlag = 0;

    //是否显示 配合比是否通过
    private Integer approvalFlag = 0;
    
    private List<SgjsMixRatioManageStaff> staffList;
    
    
    
    
    
    
    
}
