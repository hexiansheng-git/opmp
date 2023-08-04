package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service;

import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchSecondManageKeyPoint;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo.QqchSecondManageKeyPointVo;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo.SecondManageKeyPointPlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark
 */
public interface IQqchSecondManageKeyPointService {

    QqchSecondManageKeyPoint getQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    List<QqchSecondManageKeyPoint> getQqchSecondManageKeyPointList(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int insertQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int insertQqchSecondManageKeyPointList(List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList);

    int updateQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int updateQqchSecondManageKeyPointList(List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList);

    int deleteQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int deleteQqchSecondManageKeyPointByPks(List<Long> qqchSecondManageKeyPointPkList);

    /**
     * 获取二次经营要点识别Vo
     * @param qqchSecondManageKeyPoint
     * @return
     */
    QqchSecondManageKeyPointVo getQqchSecondManageKeyPointVo(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    /**
     * 保存/确认/提交
     * @param qqchSecondManageKeyPointVo
     * @return
     */
    void save(QqchSecondManageKeyPointVo qqchSecondManageKeyPointVo);

    /**
     * 获取普通要点策划/变更策划/索赔策划
     * @param qqchSecondManageKeyPoint
     * @return
     */
    List<SecondManageKeyPointPlanVo> getSecondManageKeyPointPlanVo(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

}
