package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchSecondManageExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-10 09:14:55
 * @remark
 */
@Repository
public interface QqchSecondManageExtendMapper {

    QqchSecondManageExtend getQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    List<QqchSecondManageExtend> getQqchSecondManageExtendList(QqchSecondManageExtend qqchSecondManageExtend);

    int insertQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int insertQqchSecondManageExtendList(@Param("qqchSecondManageExtendList") List<QqchSecondManageExtend> qqchSecondManageExtendList);

    int updateQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int updateQqchSecondManageExtendList(@Param("list") List<QqchSecondManageExtend> qqchSecondManageExtendList);

    int deleteQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend);

    int deleteQqchSecondManageExtendByPks(@Param("qqchSecondManageExtendPkList") List<Long> qqchSecondManageExtendPkList);

    /**
     * 查询最大有效版本号，如果查不到，版本号赋默认值1.0
     * @param keyPointType 要点类型
     * @return
     */
    BigDecimal selectMaxVersion(@Param("keyPointType") BigDecimal keyPointType);

    /**
     * 查询最接近（小于等于）指定版本的版本号
     * @param keyPointType 要点类型
     * @param version 版本
     * @return
     */
    BigDecimal selectLessOrEqualAssignVersion(@Param("keyPointType") String keyPointType,@Param("version") BigDecimal version);
}
