package com.hhwy.pm.qqch.preparation.technique.techManage.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark
 */
@Repository
public interface QqchInterestedPartyManageMapper {

    QqchInterestedPartyManage getQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    List<QqchInterestedPartyManage> getQqchInterestedPartyManageList(QqchInterestedPartyManage qqchInterestedPartyManage);

    int insertQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int insertQqchInterestedPartyManageList(@Param("qqchInterestedPartyManageList") List<QqchInterestedPartyManage> qqchInterestedPartyManageList);

    int updateQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int updateQqchInterestedPartyManageList(@Param("list") List<QqchInterestedPartyManage> qqchInterestedPartyManageList);

    int deleteQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage);

    int deleteQqchInterestedPartyManageByPks(@Param("qqchInterestedPartyManagePkList") List<Long> qqchInterestedPartyManagePkList);
}
