package com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchVideoMonitorInfo;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 视频监控信息
 */
@Data
public class QqchVideoMonitorInfoVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 附件组id
     */
    private String fileGroupId;

    /**
     * 附件上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fileUploadDate;

    /**
     * 字段描述：视频监控信息集合
     */
    private List<QqchVideoMonitorInfo> list;
}
