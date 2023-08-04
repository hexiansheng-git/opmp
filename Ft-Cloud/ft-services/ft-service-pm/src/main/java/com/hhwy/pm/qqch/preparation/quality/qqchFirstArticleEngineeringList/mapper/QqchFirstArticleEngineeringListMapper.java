package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
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

            int updateQqchFirstArticleEngineeringListList(@Param("qqchFirstArticleEngineeringListList") List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList);
    
    int deleteQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

            int deleteQqchFirstArticleEngineeringListByPks(@Param("qqchFirstArticleEngineeringListPkList") List<Long> qqchFirstArticleEngineeringListPkList);
    }
