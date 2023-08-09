package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import lombok.Data;

import java.util.List;

@Data
public class QqchSafeThreeTypePersonVo  extends PreparationEntity {
    private List<QqchSafeThreeTypePerson> list;
}
