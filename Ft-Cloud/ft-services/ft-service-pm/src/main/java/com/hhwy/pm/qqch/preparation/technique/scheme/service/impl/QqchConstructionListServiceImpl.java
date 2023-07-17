package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
@Service
public class QqchConstructionListServiceImpl implements IQqchConstructionListService {

    @Autowired
    private QqchConstructionListMapper qqchConstructionListMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private GenCodeService genCodeService;

    public QqchConstructionListVo getQqchConstructionListList() {
        QqchConstructionListVo vo = new QqchConstructionListVo();

        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        vo.setVersion(maxVersion);

        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(maxVersion);
        List<QqchConstructionList> list = qqchConstructionListMapper.getQqchConstructionListList(qryParam);
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchConstructionListVo qqchConstructionListVo) {
        if (qqchConstructionListVo.getVersion() == null) {
            throw new RuntimeException("版本号不能为空！");
        }

        // 先批量删除当前版本所有数据
        QqchConstructionList deleteParam = new QqchConstructionList();
        deleteParam.setVersion(qqchConstructionListVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchConstructionListMapper.updateQqchConstructionList(deleteParam);

        if (CollectionUtils.isEmpty(qqchConstructionListVo.getList())) {
            return;
        }

        List<QqchConstructionList> insertList = new ArrayList<>();

        // 技术重点
        for (QqchConstructionList qqchConstructionList : qqchConstructionListVo.getList()) {
            // 方案编号 = 项目编码 + 三位流水号
            String code = genCodeService.getSetCode(CodeEnum.QQCH_CONSTRUCTION_LIST);
            String newCode = code.replace(CodeEnum.QQCH_CONSTRUCTION_LIST.prefix(), "");
            qqchConstructionList.setSchemeCode(qqchConstructionList.getSchemeCode() + newCode);

            qqchConstructionList.setId(IdWorker.createId());
            qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
            qqchConstructionList.setCreateTime(DateUtils.getNowDate());

            qqchConstructionList.setVersion(qqchConstructionListVo.getVersion());
            qqchConstructionList.setValid("1");
            insertList.add(qqchConstructionList);
        }

        if (insertList.size() > 0) {
            qqchConstructionListMapper.insertQqchConstructionListList(insertList);
        }
    }
}
