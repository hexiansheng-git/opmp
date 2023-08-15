package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import lombok.Data;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-14 15:25
 */
@Data
public class QqchSafeMostEnvirRiskListVo extends PreparationEntity {
    private List<QqchSafeMostEnvirRiskList> list;
}
