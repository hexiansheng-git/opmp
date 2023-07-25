package com.hhwy.pm.qqch.preparation.measureexp.beton.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;

/**
 * @author mls
 * @date 2023-07-25 18:31:38
 * @remark 
 */
public interface QqchExpBetonMapper {
                                                                                                                                                                                                                                                                                                                                                    
    QqchExpBeton getQqchExpBeton(QqchExpBeton qqchExpBeton);

    List<QqchExpBeton> getQqchExpBetonList(QqchExpBeton qqchExpBeton);

    int insertQqchExpBeton(QqchExpBeton qqchExpBeton);

    int insertQqchExpBetonList(@Param("qqchExpBetonList") List<QqchExpBeton> qqchExpBetonList);

    int updateQqchExpBeton(QqchExpBeton qqchExpBeton);

            int updateQqchExpBetonList(@Param("qqchExpBetonList") List<QqchExpBeton> qqchExpBetonList);
    
    int deleteQqchExpBeton(QqchExpBeton qqchExpBeton);

            int deleteQqchExpBetonByPks(@Param("qqchExpBetonPkList") List<Long> qqchExpBetonPkList);
    }
