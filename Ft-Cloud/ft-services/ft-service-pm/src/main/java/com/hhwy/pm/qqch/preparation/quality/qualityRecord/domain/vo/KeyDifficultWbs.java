package com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark qqch_key_difficult_project_archives
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyDifficultWbs {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;

    /**
     * 字段描述：wbs编号
     */
    @JsonProperty
    private String wbsCode;

    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    private String wbsName;

    /**
     * 字段描述：完工时间 根据WBS节点，自动代入总进度计划中该节点完工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date completeTime;
    /**
     * 字段描述：资料完成时间 完工时间的后五天
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date dataCompleteTime;

    private List<KeyDifficultWbs> children;

    private List<QqchKeyDifficultProjectArchives> sublist;
}
