package com.hhwy.pm.qqch.sgch.sche.mapper;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiffDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:50
 * @remark
 */
public interface QqchScheDiffDescMapper {

    QqchScheDiffDesc getQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    List<QqchScheDiffDesc> getQqchScheDiffDescList(QqchScheDiffDesc qqchScheDiffDesc);

    int insertQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int insertQqchScheDiffDescList(@Param("qqchScheDiffDescList") List<QqchScheDiffDesc> qqchScheDiffDescList);

    int updateQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int updateQqchScheDiffDescList(@Param("qqchScheDiffDescList") List<QqchScheDiffDesc> qqchScheDiffDescList);

    int deleteQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int deleteQqchScheDiffDescByPks(@Param("qqchScheDiffDescPkList") List<Long> qqchScheDiffDescPkList);
}
