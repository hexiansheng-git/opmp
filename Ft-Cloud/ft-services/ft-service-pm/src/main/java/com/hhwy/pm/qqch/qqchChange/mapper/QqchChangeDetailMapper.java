package com.hhwy.pm.qqch.qqchChange.mapper;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark
 */
public interface QqchChangeDetailMapper {

    QqchChangeDetail getQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    List<QqchChangeDetail> getQqchChangeDetailList(QqchChangeDetail qqchChangeDetail);

    int insertQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int insertQqchChangeDetailList(@Param("qqchChangeDetailList") List<QqchChangeDetail> qqchChangeDetailList);

    int updateQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int updateQqchChangeDetailList(@Param("qqchChangeDetailList") List<QqchChangeDetail> qqchChangeDetailList);

    int deleteQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int deleteQqchChangeDetailByPks(@Param("qqchChangeDetailPkList") List<Long> qqchChangeDetailPkList);
}
