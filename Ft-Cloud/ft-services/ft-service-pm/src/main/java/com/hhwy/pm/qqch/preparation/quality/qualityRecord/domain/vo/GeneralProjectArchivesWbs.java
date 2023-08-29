package com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark qqch_key_difficult_project_archives
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralProjectArchivesWbs {
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
     * 字段描述：重难点工程清单标识（1：是，0：不是）
     */
    private String keyDifficultPointFlag;

    private List<GeneralProjectArchivesWbs> children;

    //一般工程档案列表
    private List<QqchGeneralProjectArchives> generalSublist;

    //重难点工程档案列表
    private List<QqchKeyDifficultProjectArchives> difficultSublist;
}
