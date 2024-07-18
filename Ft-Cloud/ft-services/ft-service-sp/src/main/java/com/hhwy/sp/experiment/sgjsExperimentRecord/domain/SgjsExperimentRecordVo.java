package com.hhwy.sp.experiment.sgjsExperimentRecord.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @date 20240718
 * @author lcf
 */
@Data
public class SgjsExperimentRecordVo extends BaseEntity {

    private List<SgjsExperimentRecord> list;

    private List<Long> delIdList;
}
