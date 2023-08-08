package com.hhwy.pm.qqch.preparation.safe.organ.mapper;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchGridDivide;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 13:52:31
 * @remark 8.1.4 格子划分
 */
public interface QqchGridDivideMapper {

    QqchGridDivide getQqchGridDivide(QqchGridDivide qqchGridDivide);

    List<QqchGridDivide> getQqchGridDivideList(QqchGridDivide qqchGridDivide);

    int insertQqchGridDivide(QqchGridDivide qqchGridDivide);

    int insertQqchGridDivideList(@Param("qqchGridDivideList") List<QqchGridDivide> qqchGridDivideList);

    int updateQqchGridDivide(QqchGridDivide qqchGridDivide);

    int updateQqchGridDivideList(@Param("list") List<QqchGridDivide> qqchGridDivideList);

    int deleteQqchGridDivide(QqchGridDivide qqchGridDivide);

    int deleteQqchGridDivideByPks(@Param("qqchGridDividePkList") List<Long> qqchGridDividePkList);
}
