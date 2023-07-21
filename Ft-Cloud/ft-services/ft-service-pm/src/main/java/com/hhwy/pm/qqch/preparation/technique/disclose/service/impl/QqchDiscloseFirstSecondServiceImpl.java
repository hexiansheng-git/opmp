package com.hhwy.pm.qqch.preparation.technique.disclose.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseFirstSecond;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseFirstSecondVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseFirstSecondMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseFirstSecondService;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-20 15:07:49
 * @remark 3.5.1一、二级交底
 */
@Service
public class QqchDiscloseFirstSecondServiceImpl implements IQqchDiscloseFirstSecondService {

    @Autowired
    private QqchDiscloseFirstSecondMapper qqchDiscloseFirstSecondMapper;
    @Autowired
    private CommonMapper commonMapper;

    public QqchDiscloseFirstSecondVo getQqchDiscloseFirstSecondList(BigDecimal version) {
        QqchDiscloseFirstSecondVo vo = new QqchDiscloseFirstSecondVo();
        if(version == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_disclose_first_second");
        }
        vo.setVersion(version);

        QqchDiscloseFirstSecond qryParam = new QqchDiscloseFirstSecond();
        qryParam.setVersion(version);
        List<QqchDiscloseFirstSecond> list = qqchDiscloseFirstSecondMapper.getQqchDiscloseFirstSecondList(qryParam);
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchDiscloseFirstSecondVo qqchDiscloseFirstSecondVo) {
        if (qqchDiscloseFirstSecondVo.getVersion() == null) {
            // 获取最大版本号
            BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_disclose_first_second");
            qqchDiscloseFirstSecondVo.setVersion(maxVersion);
        }

        // 先批量删除当前版本所有数据
        QqchDiscloseFirstSecond deleteParam = new QqchDiscloseFirstSecond();
        deleteParam.setVersion(qqchDiscloseFirstSecondVo.getVersion());
        qqchDiscloseFirstSecondMapper.deleteQqchDiscloseFirstSecond(deleteParam);

        if (CollectionUtils.isEmpty(qqchDiscloseFirstSecondVo.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchDiscloseFirstSecond> insertList = TreeUtil.treeToList(qqchDiscloseFirstSecondVo.getTreeList());

        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchDiscloseFirstSecond insert : insertList) {
                insert.setVersion(qqchDiscloseFirstSecondVo.getVersion());
                insert.setValid(Valid.YES);
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }

        // 全量入库
        qqchDiscloseFirstSecondMapper.insertQqchDiscloseFirstSecondList(insertList);
    }
}
