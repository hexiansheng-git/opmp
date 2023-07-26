package com.hhwy.pm.qqch.preparation.technique.techManage.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark 技术管理项目沟通管理
 */
@Repository
public interface QqchProjectLinkupManageMapper {

    QqchProjectLinkupManage getQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    List<QqchProjectLinkupManage> getQqchProjectLinkupManageList(QqchProjectLinkupManage qqchProjectLinkupManage);

    int insertQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int insertQqchProjectLinkupManageList(@Param("qqchProjectLinkupManageList") List<QqchProjectLinkupManage> qqchProjectLinkupManageList);

    int updateQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int updateQqchProjectLinkupManageList(@Param("list") List<QqchProjectLinkupManage> qqchProjectLinkupManageList);

    int deleteQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage);

    int deleteQqchProjectLinkupManageByPks(@Param("qqchProjectLinkupManagePkList") List<Long> qqchProjectLinkupManagePkList);
}
