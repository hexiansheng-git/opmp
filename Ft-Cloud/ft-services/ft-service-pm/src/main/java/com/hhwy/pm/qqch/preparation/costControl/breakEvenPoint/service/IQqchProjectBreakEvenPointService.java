package com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.service;

import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.domain.QqchProjectBreakEvenPoint;
import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.domain.vo.QqchProjectBreakEvenPointVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:59
 * @remark
 */
public interface IQqchProjectBreakEvenPointService {

    QqchProjectBreakEvenPoint getQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    List<QqchProjectBreakEvenPoint> getQqchProjectBreakEvenPointList(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int insertQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int updateQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int updateQqchProjectBreakEvenPointList(List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList);

    int deleteQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int deleteQqchProjectBreakEvenPointByPks(List<Long> qqchProjectBreakEvenPointPkList);

    /**
     * 获取项目主要盈亏点分析Vo
     * @param qqchProjectBreakEvenPoint
     * @return
     */
    QqchProjectBreakEvenPointVo getQqchProjectBreakEvenPointVo(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    /**
     * 保存/确认/提交
     * @param qqchProjectBreakEvenPointVo
     * @return
     */
    void save(QqchProjectBreakEvenPointVo qqchProjectBreakEvenPointVo);
}
