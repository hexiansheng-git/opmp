package com.hhwy.pm.qqch.sgch.important.mapper;

import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark
 */
public interface QqchImportantMapper {

    QqchImportant getQqchImportant(QqchImportant qqchImportant);

    List<QqchImportant> getQqchImportantList(QqchImportant qqchImportant);

    int insertQqchImportant(QqchImportant qqchImportant);

    int insertQqchImportantList(@Param("qqchImportantList") List<QqchImportant> qqchImportantList);

    int updateQqchImportant(QqchImportant qqchImportant);

    int updateQqchImportantList(@Param("qqchImportantList") List<QqchImportant> qqchImportantList);

    int deleteQqchImportant(QqchImportant qqchImportant);

    int deleteQqchImportantByPks(@Param("qqchImportantPkList") List<Long> qqchImportantPkList);
}
