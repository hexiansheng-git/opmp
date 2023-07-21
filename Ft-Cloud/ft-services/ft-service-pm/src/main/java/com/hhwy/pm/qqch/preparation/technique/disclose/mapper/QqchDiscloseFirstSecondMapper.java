package com.hhwy.pm.qqch.preparation.technique.disclose.mapper;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseFirstSecond;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-20 15:07:49
 * @remark 3.5.1一、二级交底
 */
public interface QqchDiscloseFirstSecondMapper {

    QqchDiscloseFirstSecond getQqchDiscloseFirstSecond(QqchDiscloseFirstSecond qqchDiscloseFirstSecond);

    List<QqchDiscloseFirstSecond> getQqchDiscloseFirstSecondList(QqchDiscloseFirstSecond qqchDiscloseFirstSecond);

    int insertQqchDiscloseFirstSecond(QqchDiscloseFirstSecond qqchDiscloseFirstSecond);

    int insertQqchDiscloseFirstSecondList(
        @Param("qqchDiscloseFirstSecondList") List<QqchDiscloseFirstSecond> qqchDiscloseFirstSecondList);

    int updateQqchDiscloseFirstSecond(QqchDiscloseFirstSecond qqchDiscloseFirstSecond);

    int updateQqchDiscloseFirstSecondList(@Param("list") List<QqchDiscloseFirstSecond> qqchDiscloseFirstSecondList);

    int deleteQqchDiscloseFirstSecond(QqchDiscloseFirstSecond qqchDiscloseFirstSecond);

    int deleteQqchDiscloseFirstSecondByPks(
        @Param("qqchDiscloseFirstSecondPkList") List<Long> qqchDiscloseFirstSecondPkList);
}
