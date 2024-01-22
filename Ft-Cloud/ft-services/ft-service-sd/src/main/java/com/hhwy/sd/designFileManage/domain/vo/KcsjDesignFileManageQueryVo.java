package com.hhwy.sd.designFileManage.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark kcsj_design_file_manage
 */
@Data
public class KcsjDesignFileManageQueryVo {

    private Long pid;

    /**
     * 字段描述：设计文件名称
     */
    private String designFileName;
    /**
     * 字段描述：设计部位范围简述
     */
    private String designScopeSketch;
    /**
     * 监理业主批复日期-开始
     */
    private String repleDateStart;
    /**
     * 监理业主批复日期-结束
     */
    private String repleDateEnd;

    private String path;

    private List<String> paths;

    private List<String> ids;
}
