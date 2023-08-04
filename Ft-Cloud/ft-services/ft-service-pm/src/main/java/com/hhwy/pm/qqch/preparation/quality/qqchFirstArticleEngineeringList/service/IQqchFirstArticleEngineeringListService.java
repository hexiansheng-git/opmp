package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service;

import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo.QqchFirstArticleEngineeringListVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 16:09:42
 * @remark 
 */
public interface IQqchFirstArticleEngineeringListService {
                                                                                                                                                                                                                                                                                                                                                                                                                                        
    QqchFirstArticleEngineeringList getQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    QqchFirstArticleEngineeringListVo getQqchFirstArticleEngineeringListList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int insertQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);


    int updateQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

    int updateQqchFirstArticleEngineeringListList(List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList);
    
    int deleteQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList);

     int deleteQqchFirstArticleEngineeringListByPks(List<Long> qqchFirstArticleEngineeringListPkList);

    void save(QqchFirstArticleEngineeringListVo vo);
}
