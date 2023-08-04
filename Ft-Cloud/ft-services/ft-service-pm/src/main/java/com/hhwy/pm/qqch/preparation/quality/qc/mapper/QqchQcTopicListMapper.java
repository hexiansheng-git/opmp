package com.hhwy.pm.qqch.preparation.quality.qc.mapper;

import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
public interface QqchQcTopicListMapper {

    QqchQcTopicList getQqchQcTopicList(QqchQcTopicList qqchQcTopicList);

    List<QqchQcTopicList> getQqchQcTopicListList(QqchQcTopicList qqchQcTopicList);

    int insertQqchQcTopicList(QqchQcTopicList qqchQcTopicList);

    int insertQqchQcTopicListList(@Param("qqchQcTopicListList") List<QqchQcTopicList> qqchQcTopicListList);

    int updateQqchQcTopicList(QqchQcTopicList qqchQcTopicList);

    int updateQqchQcTopicListList(@Param("list") List<QqchQcTopicList> qqchQcTopicListList);

    int deleteQqchQcTopicList(QqchQcTopicList qqchQcTopicList);

    int deleteQqchQcTopicListByPks(@Param("qqchQcTopicListPkList") List<Long> qqchQcTopicListPkList);
}
