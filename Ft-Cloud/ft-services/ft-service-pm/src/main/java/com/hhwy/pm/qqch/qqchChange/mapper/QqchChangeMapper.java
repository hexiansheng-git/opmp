package com.hhwy.pm.qqch.qqchChange.mapper;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
public interface QqchChangeMapper {

    QqchChange getQqchChange(QqchChange qqchChange);

    List<QqchChange> getQqchChangeList(QqchChange qqchChange);

    /**
     * 获取工作计划最大版本号Id
     * @return
     */
    Long getWorkplanMaxVersion();

    Integer countQqchChange(QqchChange qqchChange);

    Integer countUnValidReview();

    /**
     * 统计编制内容为是的明细数量
     * @param mainId
     * @return
     */
    Integer countEditQqchChangeDetail(Long mainId);

    int insertQqchChange(QqchChange qqchChange);

    int insertQqchChangeList(@Param("qqchChangeList") List<QqchChange> qqchChangeList);

    int updateQqchChange(QqchChange qqchChange);

    int updateQqchChangeList(@Param("qqchChangeList") List<QqchChange> qqchChangeList);

    int deleteQqchChange(QqchChange qqchChange);

    int deleteQqchChangeByPks(@Param("qqchChangePkList") List<Long> qqchChangePkList);

    int deleteDetail(Long mainId);

}
