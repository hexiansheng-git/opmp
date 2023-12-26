package com.hhwy.pm.xmsl.project.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.idworker.IdWorker;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量导入类
 */
@Data
public class XmslProjectEngineeringAmountImportVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id = IdWorker.createId();
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 序号  导入用
     */
    @FtExcel(name = "序号",serialNumFlag = true)
    private String serialNum;
    /**
     * 字段描述：工程项目
     */
    @FtExcel(name = "工程项目")
    private String engineeringProject;
    /**
     * 字段描述：单位
     */
    @FtExcel(name = "单位")
    private String units;
    /**
     * 字段描述：数量
     */
    @FtExcel(name = "数量")
    private String amount;
    /**
     * 字段描述：备注/描述
     */
    @FtExcel(name = "备注/描述")
    private String remark;

    private List<XmslProjectEngineeringAmountImportVo> children;

}
