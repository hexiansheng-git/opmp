package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo;


import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import lombok.Data;

import java.util.List;

@Data
public class QqchSpecialBigEquListVo extends PreparationEntity {

    private List<QqchSpecialBigEquList> qqchSpecialBigEquListList;

}
