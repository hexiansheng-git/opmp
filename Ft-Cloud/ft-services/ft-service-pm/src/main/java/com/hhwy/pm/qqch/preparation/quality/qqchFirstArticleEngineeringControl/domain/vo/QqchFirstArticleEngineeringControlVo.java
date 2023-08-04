package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import lombok.Data;

import java.util.List;

@Data
public class QqchFirstArticleEngineeringControlVo extends PreparationEntity {

    private List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList;
}
