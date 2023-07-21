package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchPostSettingMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
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
    @Autowired
    private CommonMapper commonMapper;

    public QqchPostSettingVo getQqchPostSettingList(String PostType, BigDecimal version) {
        QqchPostSettingVo vo = new QqchPostSettingVo();

        if (version == null) {
            version = commonMapper.selectMaxVersion("qqch_post_setting");
        }

        QqchPostSetting qqchPostSetting = new QqchPostSetting();
        qqchPostSetting.setPostType(PostType);
        qqchPostSetting.setVersion(version);
        List<QqchPostSetting> list = qqchPostSettingMapper.getQqchPostSettingList(qqchPostSetting);
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchPostSettingVo voParam, String postType) {
        // 先批量删除当前版本所有数据
        QqchPostSetting deleteParam = new QqchPostSetting();
        deleteParam.setPostType(postType);
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setDelFlag("1");
        qqchPostSettingMapper.deleteQqchPostSetting(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchPostSetting> insertList = TreeUtil.treeToList(voParam.getTreeList());
        for (QqchPostSetting insert : insertList) {
            insert.setPostType(postType);
            insert.setVersion(voParam.getVersion());
            insert.setValid(Valid.YES);
            insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            insert.setCreateUserName(SecurityUtils.getUserName());
            insert.setCreateTime(DateUtils.getNowDate());
        }

        if (insertList.size() > 0) {
            qqchPostSettingMapper.insertQqchPostSettingList(insertList);
        }
    }
}
