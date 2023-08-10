package com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark 技术管理相关方管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchInterestedPartyManageVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：技术管理相关方管理集合
     */
    private List<QqchInterestedPartyManage> list;
}
