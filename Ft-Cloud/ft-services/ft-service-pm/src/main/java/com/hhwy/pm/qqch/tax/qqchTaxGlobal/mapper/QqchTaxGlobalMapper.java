package com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark 
 */
public interface QqchTaxGlobalMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchTaxGlobal getQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    List<QqchTaxGlobal> getQqchTaxGlobalList(QqchTaxGlobal qqchTaxGlobal);

    int insertQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    int insertQqchTaxGlobalList(@Param("qqchTaxGlobalList") List<QqchTaxGlobal> qqchTaxGlobalList);

    int updateQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

            int updateQqchTaxGlobalList(@Param("qqchTaxGlobalList") List<QqchTaxGlobal> qqchTaxGlobalList);
    
    int deleteQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

            int deleteQqchTaxGlobalByPks(@Param("qqchTaxGlobalPkList") List<Long> qqchTaxGlobalPkList);
    }
