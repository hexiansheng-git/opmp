package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
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
    private GenCodeService genCodeService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchConstructionListVo getQqchConstructionListList(BigDecimal version) {
        QqchConstructionListVo vo = new QqchConstructionListVo();
        version = VersionUtil.getVersion("qqch_construction_list", version);
        vo.setVersion(version);

        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(version);
        List<QqchConstructionList> list = qqchConstructionListMapper.getQqchConstructionListList(qryParam);
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchConstructionListVo qqchConstructionListVo) {
        // 先批量删除当前版本所有数据
        QqchConstructionList deleteParam = new QqchConstructionList();
        deleteParam.setVersion(qqchConstructionListVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchConstructionListMapper.updateQqchConstructionList(deleteParam);

        if (CollectionUtils.isEmpty(qqchConstructionListVo.getList())) {
            return;
        }

        for (QqchConstructionList qqchConstructionList : qqchConstructionListVo.getList()) {
            // 方案编号 = 项目编码 + 三位流水号
            String code = genCodeService.getSetCode(CodeEnum.QQCH_CONSTRUCTION_LIST);
            String newCode = code.replace(CodeEnum.QQCH_CONSTRUCTION_LIST.prefix(), "");
            qqchConstructionList.setSchemeCode(qqchConstructionListVo.getProjectCode() + newCode);

            qqchConstructionList.setId(IdWorker.createId());
            qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
            qqchConstructionList.setCreateTime(DateUtils.getNowDate());

            qqchConstructionList.setVersion(qqchConstructionListVo.getVersion());
            qqchConstructionList.setValid(Valid.YES);
        }

        qqchConstructionListMapper.insertQqchConstructionListList(qqchConstructionListVo.getList());

        String buttonMark = qqchConstructionListVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchConstructionListVo.getMenuId();
            String stageIdentity = qqchConstructionListVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    public List<QqchConstructionList> getByWbsCodes(String[] wbsCodes) {
        return qqchConstructionListMapper.getByWbsCodes(wbsCodes);
    }
}
