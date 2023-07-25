package com.hhwy.pm.qqch.preparation.technique.techManage.service;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark
 */
public interface IQqchInterestedPartyManageService {

    QqchInterestedPartyManage getQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    List<QqchInterestedPartyManage> getQqchInterestedPartyManageList(QqchInterestedPartyManage qqchInterestedPartyManage);

    int insertQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int insertQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList);

    int updateQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int updateQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList);

    int deleteQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int deleteQqchInterestedPartyManageByPks(List<Long> qqchInterestedPartyManagePkList);
}
