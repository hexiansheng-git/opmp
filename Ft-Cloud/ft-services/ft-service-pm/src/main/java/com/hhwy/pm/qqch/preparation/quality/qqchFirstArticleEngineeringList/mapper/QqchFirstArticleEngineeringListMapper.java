package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo.QqchFirstArticleEngineeringListHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 16:09:42
 * @remark
 */
public interface QqchFirstArticleEngineeringListMapper {

    QqchFirstArticleEngineeringList getQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    List<QqchFirstArticleEngineeringList> getQqchFirstArticleEngineeringListList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int insertQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int insertQqchFirstArticleEngineeringListList(@Param("qqchFirstArticleEngineeringListList") List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList);

    int updateQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int updateQqchFirstArticleEngineeringListList(@Param("list") List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList);

    int deleteQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int deleteQqchFirstArticleEngineeringListByPks(@Param("list") List<Long> qqchFirstArticleEngineeringListPkList);

    List<QqchFirstArticleEngineeringListHistory> getHistoryManageModelList(QqchFirstArticleEngineeringListHistory param);
}
