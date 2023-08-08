package com.hhwy.pm.qqch.preparation.safe.organ.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchGridDivide;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 13:52:31
 * @remark 格子划分
 */
@Data
public class QqchGridDivideVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：格子划分集合
     */
    private List<QqchGridDivide> list;
}
