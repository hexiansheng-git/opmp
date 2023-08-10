package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service;

import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchSecondManageExtend;

import java.util.List;

/**
 * @author han
 * @date 2023-08-10 09:14:55
 * @remark
 */
public interface IQqchSecondManageExtendService {

    QqchSecondManageExtend getQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    List<QqchSecondManageExtend> getQqchSecondManageExtendList(QqchSecondManageExtend qqchSecondManageExtend);

    int insertQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int insertQqchSecondManageExtendList(List<QqchSecondManageExtend> qqchSecondManageExtendList);

    int updateQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int updateQqchSecondManageExtendList(List<QqchSecondManageExtend> qqchSecondManageExtendList);

    int deleteQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int deleteQqchSecondManageExtendByPks(List<Long> qqchSecondManageExtendPkList);
}
