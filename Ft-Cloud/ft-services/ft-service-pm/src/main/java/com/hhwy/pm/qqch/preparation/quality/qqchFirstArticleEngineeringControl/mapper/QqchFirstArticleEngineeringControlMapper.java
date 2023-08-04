package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark 
 */
public interface QqchFirstArticleEngineeringControlMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchFirstArticleEngineeringControl getQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    List<QqchFirstArticleEngineeringControl> getQqchFirstArticleEngineeringControlList(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int insertQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int insertQqchFirstArticleEngineeringControlList(@Param("qqchFirstArticleEngineeringControlList") List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList);

    int updateQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

            int updateQqchFirstArticleEngineeringControlList(@Param("qqchFirstArticleEngineeringControlList") List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList);
    
    int deleteQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

            int deleteQqchFirstArticleEngineeringControlByPks(@Param("qqchFirstArticleEngineeringControlPkList") List<Long> qqchFirstArticleEngineeringControlPkList);
    }
