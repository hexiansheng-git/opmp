package com.hhwy.pm.qqch.preparation.technique.techManage.service;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo.QqchInterestedPartyManageVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark 技术管理相关方管理
 */
public interface IQqchInterestedPartyManageService {

    QqchInterestedPartyManage getQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    List<QqchInterestedPartyManage> getQqchInterestedPartyManageList(QqchInterestedPartyManage qqchInterestedPartyManage);

    int insertQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int updateQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int updateQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList);

    int deleteQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int deleteQqchInterestedPartyManageByPks(List<Long> qqchInterestedPartyManagePkList);

    /**
     * 获取技术管理相关方管理Vo
     * @param qqchInterestedPartyManage
     * @return
     */
    QqchInterestedPartyManageVo getQqchInterestedPartyManageVo(QqchInterestedPartyManage qqchInterestedPartyManage);

    /**
     * 保存/确认/提交
     * @param qqchInterestedPartyManageVo
     * @return
     */
    void save(QqchInterestedPartyManageVo qqchInterestedPartyManageVo);
}
