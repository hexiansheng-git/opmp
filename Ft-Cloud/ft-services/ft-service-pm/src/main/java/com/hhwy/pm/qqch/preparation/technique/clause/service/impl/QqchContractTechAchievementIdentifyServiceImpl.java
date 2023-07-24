package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechAchievementIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechAchievementIdentifyVo;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechAchievementIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechAchievementIdentifyService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
@Service
public class QqchContractTechAchievementIdentifyServiceImpl implements IQqchContractTechAchievementIdentifyService {

    @Autowired
    private QqchContractTechAchievementIdentifyMapper qqchContractTechAchievementIdentifyMapper;

    /**
     * 树查询
     *
     * @param
     * @return
     */
    public QqchContractTechAchievementIdentifyVo getTreeList(BigDecimal version) {
        QqchContractTechAchievementIdentifyVo vo = new QqchContractTechAchievementIdentifyVo();
        version = VersionUtil.getVersion("qqch_contract_tech_achievement_identify", version);
        vo.setVersion(version);

        QqchContractTechAchievementIdentify qryParam = new QqchContractTechAchievementIdentify();
        qryParam.setVersion(version);
        List<QqchContractTechAchievementIdentify> list = qqchContractTechAchievementIdentifyMapper
            .getQqchContractTechAchievementIdentifyList(qryParam);
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchContractTechAchievementIdentifyVo voParam) {
        // 先批量删除当前版本所有数据
        QqchContractTechAchievementIdentify deleteParam = new QqchContractTechAchievementIdentify();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setDelFlag("1");
        qqchContractTechAchievementIdentifyMapper.updateQqchContractTechAchievementIdentify(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchContractTechAchievementIdentify> insertList = TreeUtil.treeToList(voParam.getTreeList());
        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchContractTechAchievementIdentify insert : insertList) {
                insert.setVersion(voParam.getVersion());
                insert.setValid(Valid.YES);
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }
        // 全量入库
        qqchContractTechAchievementIdentifyMapper.insertQqchContractTechAchievementIdentifyList(insertList);
    }
}
