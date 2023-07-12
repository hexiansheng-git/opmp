package com.hhwy.pm.qqch.preparation.technique.manage.mapper;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
public interface QqchPostSettingMapper {

    QqchPostSetting getQqchPostSetting(QqchPostSetting qqchPostSetting);

    List<QqchPostSetting> getQqchPostSettingList(QqchPostSetting qqchPostSetting);

    int insertQqchPostSetting(QqchPostSetting qqchPostSetting);

    int insertQqchPostSettingList(@Param("qqchPostSettingList") List<QqchPostSetting> qqchPostSettingList);

    int updateQqchPostSetting(QqchPostSetting qqchPostSetting);

    int updateQqchPostSettingList(@Param("list") List<QqchPostSetting> qqchPostSettingList);

    int deleteQqchPostSetting(QqchPostSetting qqchPostSetting);

    int deleteQqchPostSettingByPks(@Param("qqchPostSettingPkList") List<Long> qqchPostSettingPkList);
}
