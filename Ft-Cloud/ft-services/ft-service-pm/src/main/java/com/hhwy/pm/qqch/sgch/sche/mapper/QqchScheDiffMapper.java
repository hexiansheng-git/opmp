package com.hhwy.pm.qqch.sgch.sche.mapper;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
public interface QqchScheDiffMapper {

    QqchScheDiff getQqchScheDiff(QqchScheDiff qqchScheDiff);

    List<QqchScheDiff> getQqchScheDiffList(QqchScheDiff qqchScheDiff);

    int insertQqchScheDiff(QqchScheDiff qqchScheDiff);

    int insertQqchScheDiffList(@Param("qqchScheDiffList") List<QqchScheDiff> qqchScheDiffList);

    int updateQqchScheDiff(QqchScheDiff qqchScheDiff);

    int updateQqchScheDiffList(@Param("qqchScheDiffList") List<QqchScheDiff> qqchScheDiffList);

    int deleteQqchScheDiff(QqchScheDiff qqchScheDiff);

    int deleteQqchScheDiffByPks(@Param("qqchScheDiffPkList") List<Long> qqchScheDiffPkList);
}
