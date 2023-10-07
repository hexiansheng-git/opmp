package com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThird;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark qqch_disclose_third
 */
@Data
public class QqchDiscloseThirdVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：三级交底集合
     */
    private List<QqchDiscloseThird> treeList;

    /**
     * 字段描述：全部三级交底详情集合
     */
    private List<QqchDiscloseThirdDetail> allDetailTreeList;

    /**
     * 要删除的交底ID
     */
    private String delIds;
}
