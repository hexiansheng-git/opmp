package com.hhwy.pm.qqch.qqchChange.mapper;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
public interface QqchChangeMapper {

    /**
     * 获取前期策划最大版本号
     * @return
     */
    BigDecimal effectVersion();

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
     * @param qqchChangeDetail
     * @return
     */
    Integer countEditQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int insertQqchChange(QqchChange qqchChange);

    int insertQqchChangeList(@Param("qqchChangeList") List<QqchChange> qqchChangeList);

    int updateQqchChange(QqchChange qqchChange);

    int updateQqchChangeList(@Param("qqchChangeList") List<QqchChange> qqchChangeList);

    /**
     * 更改明细的评审完成日期
     * @param list
     * @return
     */
    int updateReviewFinishTime(@Param("idList") List<Long> list);

    int updateSubFinishNum(QqchChange qqchChange);

    int deleteQqchChange(QqchChange qqchChange);

    int deleteQqchChangeByPks(@Param("qqchChangePkList") List<Long> qqchChangePkList);

    int deleteDetail(Long mainId);

}
