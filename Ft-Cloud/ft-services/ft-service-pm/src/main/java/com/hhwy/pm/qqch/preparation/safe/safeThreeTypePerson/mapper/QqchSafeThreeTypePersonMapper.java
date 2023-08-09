package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark
 */
public interface QqchSafeThreeTypePersonMapper {

    QqchSafeThreeTypePerson getQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    List<QqchSafeThreeTypePerson> getQqchSafeThreeTypePersonList(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int insertQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int insertQqchSafeThreeTypePersonList(@Param("qqchSafeThreeTypePersonList") List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList);

    int updateQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int updateQqchSafeThreeTypePersonList(@Param("qqchSafeThreeTypePersonList") List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList);

    int deleteQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int deleteQqchSafeThreeTypePersonByPks(@Param("qqchSafeThreeTypePersonPkList") List<Long> qqchSafeThreeTypePersonPkList);

    void batchRefresh(@Param("list") List<QqchSafeThreeTypePerson> list, @Param("version") BigDecimal version);
}
