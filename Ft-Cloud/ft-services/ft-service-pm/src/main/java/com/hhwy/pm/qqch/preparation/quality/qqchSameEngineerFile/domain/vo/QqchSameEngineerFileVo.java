package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFile;
import lombok.Data;

import java.util.List;

@Data
public class QqchSameEngineerFileVo extends PreparationEntity {

    private List<QqchSameEngineerFile> qqchSameEngineerFileList;
}
