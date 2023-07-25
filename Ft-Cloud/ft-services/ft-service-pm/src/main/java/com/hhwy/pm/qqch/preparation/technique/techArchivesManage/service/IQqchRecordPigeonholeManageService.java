package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark
 */
public interface IQqchRecordPigeonholeManageService {

    QqchRecordPigeonholeManage getQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    List<QqchRecordPigeonholeManage> getQqchRecordPigeonholeManageList(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int insertQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int insertQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList);

    int updateQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int updateQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList);

    int deleteQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int deleteQqchRecordPigeonholeManageByPks(List<Long> qqchRecordPigeonholeManagePkList);
}
