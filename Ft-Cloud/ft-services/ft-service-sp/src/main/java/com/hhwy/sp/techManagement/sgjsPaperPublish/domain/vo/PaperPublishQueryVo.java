package com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo;

import lombok.Data;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark sgjs_paper_publish
 */
@Data
public class PaperPublishQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：论文编号
     */
    private String paperCode;
    /**
     * 字段描述：论文名称
     */
    private String paperName;
    /**
     * 字段描述：当前状态
     */
    private String currentState;
}
