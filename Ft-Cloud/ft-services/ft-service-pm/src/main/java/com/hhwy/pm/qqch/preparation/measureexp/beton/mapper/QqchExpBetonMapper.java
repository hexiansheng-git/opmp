package com.hhwy.pm.qqch.preparation.measureexp.beton.mapper;

import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:49
 * @remark 3.7.5混凝土配合比
 */
public interface QqchExpBetonMapper {

    QqchExpBeton getQqchExpBeton(QqchExpBeton qqchExpBeton);

    List<QqchExpBeton> getQqchExpBetonList(QqchExpBeton qqchExpBeton);

    int insertQqchExpBeton(QqchExpBeton qqchExpBeton);

    int insertQqchExpBetonList(@Param("qqchExpBetonList") List<QqchExpBeton> qqchExpBetonList);

    int updateQqchExpBeton(QqchExpBeton qqchExpBeton);

    int updateQqchExpBetonList(@Param("list") List<QqchExpBeton> qqchExpBetonList);

    int deleteQqchExpBeton(QqchExpBeton qqchExpBeton);

    int deleteQqchExpBetonByPks(@Param("qqchExpBetonPkList") List<Long> qqchExpBetonPkList);
}
