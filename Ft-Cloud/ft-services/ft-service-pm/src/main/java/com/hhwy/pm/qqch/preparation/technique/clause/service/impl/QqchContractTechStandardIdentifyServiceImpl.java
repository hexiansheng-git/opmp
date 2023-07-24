package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechStandardIdentifyVo;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechStandardIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
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
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Service
public class QqchContractTechStandardIdentifyServiceImpl implements IQqchContractTechStandardIdentifyService {

    @Autowired
    private QqchContractTechStandardIdentifyMapper qqchContractTechStandardIdentifyMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 树列表查询
     *
     * @param
     * @return
     */
    public QqchContractTechStandardIdentifyVo getTreeList(BigDecimal version) {
        QqchContractTechStandardIdentifyVo vo = new QqchContractTechStandardIdentifyVo();
        version = VersionUtil.getVersion("qqch_contract_tech_standard_identify", version);
        vo.setVersion(version);

        QqchContractTechStandardIdentify qryParam = new QqchContractTechStandardIdentify();
        qryParam.setVersion(version);
        List<QqchContractTechStandardIdentify> list = qqchContractTechStandardIdentifyMapper
            .getQqchContractTechStandardIdentifyList(qryParam);
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }

    /**
     * 批量保存
     *
     * @param voParam
     */
    @Transactional
    public void batchSave(QqchContractTechStandardIdentifyVo voParam) {
        // 先批量删除当前版本所有数据
        QqchContractTechStandardIdentify deleteParam = new QqchContractTechStandardIdentify();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setDelFlag("1");
        qqchContractTechStandardIdentifyMapper.updateQqchContractTechStandardIdentify(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchContractTechStandardIdentify> insertList = TreeUtil.treeToList(voParam.getTreeList());

        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchContractTechStandardIdentify insert : insertList) {
                insert.setVersion(voParam.getVersion());
                insert.setValid(Valid.YES);
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }
        // 全量入库
        qqchContractTechStandardIdentifyMapper.insertQqchContractTechStandardIdentifyList(insertList);

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
