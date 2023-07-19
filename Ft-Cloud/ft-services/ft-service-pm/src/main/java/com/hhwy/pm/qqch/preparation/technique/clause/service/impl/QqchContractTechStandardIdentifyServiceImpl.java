package com.hhwy.pm.qqch.preparation.technique.clause.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechStandardIdentifyVo;
import com.hhwy.pm.qqch.preparation.technique.clause.mapper.QqchContractTechStandardIdentifyMapper;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
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
    private CommonMapper commonMapper;

    /**
     * 树列表查询
     *
     * @param
     * @return
     */
    public QqchContractTechStandardIdentifyVo getTreeList() {
        QqchContractTechStandardIdentifyVo vo = new QqchContractTechStandardIdentifyVo();

        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_contract_tech_standard_identify");
        vo.setVersion(maxVersion);

        QqchContractTechStandardIdentify qryParam = new QqchContractTechStandardIdentify();
        qryParam.setVersion(maxVersion);
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
        if (voParam.getVersion() == null) {
            // 获取最大版本号
            BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_contract_tech_standard_identify");
            voParam.setVersion(maxVersion);
        }

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
    }
}
