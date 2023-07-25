package com.hhwy.pm.qqch.preparation.technique.techManage.service;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark
 */
public interface IQqchProjectLinkupManageService {

    QqchProjectLinkupManage getQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    List<QqchProjectLinkupManage> getQqchProjectLinkupManageList(QqchProjectLinkupManage qqchProjectLinkupManage);

    int insertQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int insertQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList);

    int updateQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int updateQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList);

    int deleteQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int deleteQqchProjectLinkupManageByPks(List<Long> qqchProjectLinkupManagePkList);
}
