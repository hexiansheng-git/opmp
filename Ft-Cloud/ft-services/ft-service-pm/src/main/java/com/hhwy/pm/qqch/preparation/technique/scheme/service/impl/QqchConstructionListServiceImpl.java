package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.bouncycastle.jcajce.provider.util.SecretKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchConstructionListVo getQqchConstructionListList(QqchConstructionListVo paramVo) {
        QqchConstructionListVo vo = new QqchConstructionListVo();

        BigDecimal version = VersionUtil.getVersion("qqch_construction_list", paramVo.getVersion());
        vo.setVersion(version);
        if(StringUtils.isNotBlank(paramVo.getWbsCode())){
            QqchConstructionList qryParam = new QqchConstructionList();
            qryParam.setVersion(version);
            qryParam.setSchemeName(paramVo.getSchemeName());
            qryParam.setSchemeLevel(paramVo.getSchemeType());
            qryParam.setWbsCode(paramVo.getWbsCode());
            List<QqchConstructionList> list = qqchConstructionListMapper.getQqchConstructionListList(qryParam);
            vo.setList(list);
        }else{
            vo.setList(new ArrayList<>(2));
        }
        vo.setStageIdentity(qqchReviewService.getStage());
        return vo;
    }

    @Transactional
    public void batchSave(QqchConstructionListVo qqchConstructionListVo) {
        // 先批量删除当前版本所有数据
//        QqchConstructionList deleteParam = new QqchConstructionList();
//        deleteParam.setVersion(qqchConstructionListVo.getVersion());
//        deleteParam.setDelFlag("1");
//        qqchConstructionListMapper.updateQqchConstructionList(deleteParam);
        Set<String> delWbsCodeSet = new HashSet<>(); 
        if (!CollectionUtils.isEmpty(qqchConstructionListVo.getList())) {
            for (QqchConstructionList qqchConstructionList : qqchConstructionListVo.getList()) {
                if (StringUtils.isBlank(qqchConstructionList.getSchemeCode())) {
                    // 方案编号 = 项目编码 + 三位流水号
                    String code = genCodeService.getSetCode(CodeEnum.QQCH_CONSTRUCTION_LIST);
                    String newCode = code.replace(CodeEnum.QQCH_CONSTRUCTION_LIST.prefix(), "");
                    qqchConstructionList.setSchemeCode(SecurityUtils.getSysUser().getTenantKey() + newCode);
                }
                qqchConstructionList.setId(IdWorker.createId());
                qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
                qqchConstructionList.setCreateTime(DateUtils.getNowDate());

                qqchConstructionList.setVersion(qqchConstructionListVo.getVersion());
                if (qqchConstructionListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchConstructionList.setValid(Valid.YES);
                }
                delWbsCodeSet.add(qqchConstructionList.getWbsCode());
            }
            //删除原wbsCode对应的数据
            qqchConstructionListMapper.deleteByWbsCode(delWbsCodeSet);
            qqchConstructionListMapper.insertQqchConstructionListList(qqchConstructionListVo.getList());
        }
        
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
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        return qqchConstructionListMapper.getByWbsCodes(wbsCodes, maxVersion);
    }
}
