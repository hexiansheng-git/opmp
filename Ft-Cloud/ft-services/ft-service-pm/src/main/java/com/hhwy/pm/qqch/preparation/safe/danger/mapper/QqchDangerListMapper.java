package com.hhwy.pm.qqch.preparation.safe.danger.mapper;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
public interface QqchDangerListMapper {

    QqchDangerList getQqchDangerList(QqchDangerList qqchDangerList);

    List<QqchDangerList> getQqchDangerListList(QqchDangerList qqchDangerList);

    int insertQqchDangerList(QqchDangerList qqchDangerList);

    int insertQqchDangerListList(@Param("qqchDangerListList") List<QqchDangerList> qqchDangerListList);

    int updateQqchDangerList(QqchDangerList qqchDangerList);

    int updateQqchDangerListList(@Param("list") List<QqchDangerList> qqchDangerListList);

    int deleteQqchDangerList(QqchDangerList qqchDangerList);

    int deleteQqchDangerListByPks(@Param("qqchDangerListPkList") List<Long> qqchDangerListPkList);
}
