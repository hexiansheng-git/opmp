package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import lombok.Data;

import java.util.List;

@Data
public class QqchFirstArticleEngineeringListVo extends PreparationEntity {

    private List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList;
}
