package com.hhwy.pm.qqch.preparation.qqchOrganizationList.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;

/**
 * @author hwj
 * @date 2023-07-24 17:02:12
 * @remark 
 */
public interface QqchOrganizationListMapper {
                                                                                                                                                    
    QqchOrganizationList getQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    List<QqchOrganizationList> getQqchOrganizationListList(QqchOrganizationList qqchOrganizationList);

    int insertQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    int insertQqchOrganizationListList(@Param("qqchOrganizationListList") List<QqchOrganizationList> qqchOrganizationListList);

    int updateQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

            int updateQqchOrganizationListList(@Param("qqchOrganizationListList") List<QqchOrganizationList> qqchOrganizationListList);
    
    int deleteQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

            int deleteQqchOrganizationListByPks(@Param("qqchOrganizationListPkList") List<Long> qqchOrganizationListPkList);
    }
