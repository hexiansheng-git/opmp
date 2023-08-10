package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.QqchCareerHealthRiskList;

/**
 * @author ldd
 * @date 2023-08-09 15:01:27
 * @remark 
 */
public interface QqchCareerHealthRiskListMapper {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchCareerHealthRiskList getQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    List<QqchCareerHealthRiskList> getQqchCareerHealthRiskListList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int insertQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int insertQqchCareerHealthRiskListList(@Param("qqchCareerHealthRiskListList") List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList);

    int updateQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

            int updateQqchCareerHealthRiskListList(@Param("qqchCareerHealthRiskListList") List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList);
    
    int deleteQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

            int deleteQqchCareerHealthRiskListByPks(@Param("qqchCareerHealthRiskListPkList") List<Long> qqchCareerHealthRiskListPkList);
    }
