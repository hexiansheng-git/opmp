package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchPostSettingMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ToTreeUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
@Service
public class QqchPostSettingServiceImpl implements IQqchPostSettingService {

    @Autowired
    private QqchPostSettingMapper qqchPostSettingMapper;

    public List<QqchPostSetting> getQqchPostSettingList(QqchPostSetting qqchPostSetting) {
        List<QqchPostSetting> list = qqchPostSettingMapper.getQqchPostSettingList(qqchPostSetting);
        return ToTreeUtils.listToTree(list);
    }

    @Transactional
    public void batchSave(List<QqchPostSetting> qqchPostSettingList, String postType) {
        if (CollectionUtils.isEmpty(qqchPostSettingList)) {
            return;
        }

        List<QqchPostSetting> insertList = new ArrayList<>();
        List<QqchPostSetting> updateList = new ArrayList<>();
        for (QqchPostSetting qqchPostSetting : qqchPostSettingList) {
            qqchPostSetting.setPostType(postType);
            this.recursionSubset(qqchPostSetting, insertList, updateList);
        }

        if (insertList.size() > 0) {
            qqchPostSettingMapper.insertQqchPostSettingList(insertList);
        }
        if (updateList.size() > 0) {
            qqchPostSettingMapper.updateQqchPostSettingList(updateList);
        }
    }

    @Transactional
    public int deleteQqchPostSettingByPks(List<Long> qqchPostSettingPkList) {
        return qqchPostSettingMapper.deleteQqchPostSettingByPks(qqchPostSettingPkList);
    }

    /**
     * 递归处理子节点
     *
     * @param qqchPostSetting
     * @param insertList
     * @param updateList
     */
    public void recursionSubset(QqchPostSetting qqchPostSetting,
        List<QqchPostSetting> insertList, List<QqchPostSetting> updateList) {
        Long id = qqchPostSetting.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchPostSetting.setId(id);
            qqchPostSetting.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchPostSetting.setCreateUserName(SecurityUtils.getUserName());
            qqchPostSetting.setCreateTime(DateUtils.getNowDate());
            insertList.add(qqchPostSetting);
        } else {
            qqchPostSetting.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchPostSetting.setUpdateTime(DateUtils.getNowDate());
            updateList.add(qqchPostSetting);
        }

        List<QqchPostSetting> children = qqchPostSetting.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (QqchPostSetting child : children) {
                child.setPid(id);
                child.setPostType(qqchPostSetting.getPostType());
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }
}
