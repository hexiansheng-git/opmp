package com.hhwy.pm.qqch.preparation.technique.techManage.service;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo.QqchProjectLinkupManageVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark 技术管理项目沟通管理
 */
public interface IQqchProjectLinkupManageService {

    QqchProjectLinkupManage getQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    List<QqchProjectLinkupManage> getQqchProjectLinkupManageList(QqchProjectLinkupManage qqchProjectLinkupManage);

    int insertQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int updateQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int updateQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList);

    int deleteQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int deleteQqchProjectLinkupManageByPks(List<Long> qqchProjectLinkupManagePkList);

    /**
     * 获取技术管理项目沟通管理Vo
     * @param qqchProjectLinkupManage
     * @return
     */
    QqchProjectLinkupManageVo getQqchProjectLinkupManageVo(QqchProjectLinkupManage qqchProjectLinkupManage);

    /**
     * 保存/确认/提交
     * @param qqchProjectLinkupManageVo
     * @return
     */
    void save(QqchProjectLinkupManageVo qqchProjectLinkupManageVo);
}
