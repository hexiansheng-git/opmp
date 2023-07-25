package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark
 */
@Repository
public interface QqchRecordPigeonholeManageMapper {

    QqchRecordPigeonholeManage getQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    List<QqchRecordPigeonholeManage> getQqchRecordPigeonholeManageList(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int insertQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int insertQqchRecordPigeonholeManageList(@Param("qqchRecordPigeonholeManageList") List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList);

    int updateQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int updateQqchRecordPigeonholeManageList(@Param("list") List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList);

    int deleteQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage);

    int deleteQqchRecordPigeonholeManageByPks(@Param("qqchRecordPigeonholeManagePkList") List<Long> qqchRecordPigeonholeManagePkList);
}
