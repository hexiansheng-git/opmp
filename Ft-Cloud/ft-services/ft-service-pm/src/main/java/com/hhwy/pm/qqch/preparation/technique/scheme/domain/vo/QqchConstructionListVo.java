package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-13 14:40:32
 * @remark 3.4.2施工方案清单
 */
@Data
public class QqchConstructionListVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：项目编码（保存时生成方案编号用）
     */
    private String projectCode;

    /**
     * 字段描述：方案名称（筛选条件）
     */
    private String schemeName;

    /**
     * 字段描述：方案类型（字典类型scheme_type）（筛选条件）
     */
    private String schemeType;

    /**
     * 字段描述：关联WBS编码（筛选条件）
     */
    private String wbsCode;
    
    //要删除的数据ID，逗号隔开
    private String delIds;
    //当前最大流水号
    private String flowCode;
    

    /**
     * 字段描述：施工方案清单集合
     */
    private List<QqchConstructionList> list;
}
