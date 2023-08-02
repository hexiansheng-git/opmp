package com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark 技术管理项目沟通管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchProjectLinkupManageVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：技术管理项目沟通管理集合
     */
    private List<QqchProjectLinkupManage> qqchProjectLinkupManageList;
}
