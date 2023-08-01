package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo.QqchRecordPigeonholeManageVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark 技术档案归档管理
 */
public interface IQqchRecordPigeonholeManageService {

    QqchRecordPigeonholeManage getQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    List<QqchRecordPigeonholeManage> getQqchRecordPigeonholeManageList(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int insertQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int updateQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int updateQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList);

    int deleteQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int deleteQqchRecordPigeonholeManageByPks(List<Long> qqchRecordPigeonholeManagePkList);

    /**
     * 获取技术档案归档管理Vo
     * @param qqchRecordPigeonholeManage
     * @return
     */
    QqchRecordPigeonholeManageVo getQqchRecordPigeonholeManageVo(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    /**
     * 保存/确认/提交
     * @param qqchRecordPigeonholeManageVo
     * @return
     */
    void save(QqchRecordPigeonholeManageVo qqchRecordPigeonholeManageVo);
}
