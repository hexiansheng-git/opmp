package com.hhwy.pm.qqch.tax.qqchTaxIn.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark 
 */
public interface QqchTaxInMapper {
                                                                                                                                                                                                                                                                                                                                                                                        
    QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn);

    List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn);

    int insertQqchTaxIn(QqchTaxIn qqchTaxIn);

    int insertQqchTaxInList(@Param("qqchTaxInList") List<QqchTaxIn> qqchTaxInList);

    int updateQqchTaxIn(QqchTaxIn qqchTaxIn);

            int updateQqchTaxInList(@Param("qqchTaxInList") List<QqchTaxIn> qqchTaxInList);
    
    int deleteQqchTaxIn(QqchTaxIn qqchTaxIn);

            int deleteQqchTaxInByPks(@Param("qqchTaxInPkList") List<Long> qqchTaxInPkList);
    }
