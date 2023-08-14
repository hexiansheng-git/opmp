package com.hhwy.pm.qqch.tax.qqchTaxStage.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class StageDTO implements Serializable {
    private String version;
    private Long inRecordId;
    private Long costRecordId;
    private String submitFlag;
}
