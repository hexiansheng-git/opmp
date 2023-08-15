package com.hhwy.pm.qqch.tax.qqchTaxInstallment.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class InstallmentDTO implements Serializable {
    private String version;
    private Long inRecordId;
    private Long costRecordId;
    private String submitFlag;
    private String year;
}
