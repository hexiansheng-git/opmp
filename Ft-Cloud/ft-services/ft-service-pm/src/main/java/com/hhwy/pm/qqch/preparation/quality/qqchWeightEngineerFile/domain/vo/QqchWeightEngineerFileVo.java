package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.QqchWeightEngineerFile;
import lombok.Data;

import java.util.List;

@Data
public class QqchWeightEngineerFileVo extends PreparationEntity {

    private List<QqchWeightEngineerFile> qqchWeightEngineerFileList;
}
