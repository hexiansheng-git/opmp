package com.hhwy.pm.qqch.preparation.qqchOrganizationList.service;

import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hwj
 * @date 2023-07-24 17:02:12
 * @remark
 */
public interface IQqchOrganizationListService {

    QqchOrganizationList getQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    List<QqchOrganizationList>  getQqchOrganizationListList2(QqchOrganizationList qqchOrganizationList);

    List<QqchOrganizationList> getQqchOrganizationListList(QqchOrganizationList qqchOrganizationList);

    QqchOrganizationListVo getQqchOrganizationListVo(BigDecimal version);

    List<QqchOrganizationList> getList4Word();

    int insertQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    int insertQqchOrganizationListList(List<QqchOrganizationList> qqchOrganizationListList);

    int updateQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    int updateQqchOrganizationListList(List<QqchOrganizationList> qqchOrganizationListList);

    int deleteQqchOrganizationList(QqchOrganizationList qqchOrganizationList);

    int deleteQqchOrganizationListByPks(List<Long> qqchOrganizationListPkList);

    int insertQqchOrganizationListVo(QqchOrganizationListVo qqchOrganizationListVo);


    /**
     * @param qqchImportants 
     * @param qqchOrganizationLists
     * @return
     */
    List<QqchOrganizationList> mergeData(List<QqchOrganizationList> qqchImportants, List<QqchOrganizationList> qqchOrganizationLists);
}
