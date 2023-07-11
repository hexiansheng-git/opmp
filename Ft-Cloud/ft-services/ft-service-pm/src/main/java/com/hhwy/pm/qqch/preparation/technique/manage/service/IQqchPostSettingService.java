package com.hhwy.pm.qqch.preparation.technique.manage.service;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.utils.tree.TreeVO;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
public interface IQqchPostSettingService {

    List<? extends TreeVO> getQqchPostSettingList(QqchPostSetting qqchPostSetting);

    void batchSave(List<QqchPostSetting> qqchPostSettingList, String postType);

    int deleteQqchPostSettingByPks(List<Long> qqchPostSettingPkList);
}
