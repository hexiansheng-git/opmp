package com.hhwy.pm.jdgl.diff.track.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2023-08-24 16:51:08
 * @remark
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressCorrectionTrackQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：期次
     */
    private String period;
    /**
     * 字段描述：项目编码
     */
    private String projectCode;
}
