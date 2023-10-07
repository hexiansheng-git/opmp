package com.hhwy.pm.qqch.preparation.technique.disclose.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseFirstSecond;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseFirstSecondVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.RelateProjectVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseFirstSecondMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseFirstSecondService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.VoiceStatus;

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
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IXmslWbsService xmslWbsService;
    @Autowired
    private IQqchDangerConstructionListService qqchDangerConstructionListService;
    @Autowired
    private IQqchKeyDifficultConstructionBriefService qqchKeyDifficultConstructionBriefService;
    @Autowired
    private IQqchConstructionListService qqchConstructionListService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    public QqchDiscloseFirstSecondVo getQqchDiscloseFirstSecondList(BigDecimal version) {
        QqchDiscloseFirstSecondVo vo = new QqchDiscloseFirstSecondVo();
        version = VersionUtil.getVersion("qqch_disclose_first_second", version);
        vo.setVersion(version);

        QqchDiscloseFirstSecond qryParam = new QqchDiscloseFirstSecond();
        qryParam.setVersion(version);
        List<QqchDiscloseFirstSecond> list = qqchDiscloseFirstSecondMapper.getQqchDiscloseFirstSecondList(qryParam);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchDiscloseFirstSecondVo qqchDiscloseFirstSecondVo) {
        // 先批量删除当前版本所有数据
        QqchDiscloseFirstSecond deleteParam = new QqchDiscloseFirstSecond();
        deleteParam.setVersion(qqchDiscloseFirstSecondVo.getVersion());
        qqchDiscloseFirstSecondMapper.deleteQqchDiscloseFirstSecond(deleteParam);

        if (CollectionUtils.isNotEmpty(qqchDiscloseFirstSecondVo.getTreeList())) {
            // 树转list
            List<QqchDiscloseFirstSecond> insertList = TreeUtil.treeToList(qqchDiscloseFirstSecondVo.getTreeList());

            if (CollectionUtils.isNotEmpty(insertList)) {
                for (QqchDiscloseFirstSecond insert : insertList) {
                    insert.setVersion(qqchDiscloseFirstSecondVo.getVersion());
                    if (qqchDiscloseFirstSecondVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                        insert.setValid(Valid.YES);
                    }
                    insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    insert.setCreateUserName(SecurityUtils.getUserName());
                    insert.setCreateTime(DateUtils.getNowDate());
                }
            }

            // 全量入库
            qqchDiscloseFirstSecondMapper.insertQqchDiscloseFirstSecondList(insertList);
        }

        String buttonMark = qqchDiscloseFirstSecondVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchDiscloseFirstSecondVo.getMenuId();
            String stageIdentity = qqchDiscloseFirstSecondVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 根据关联wbs，查询wbs本级以及所有下级关联项目危大工程方案、重难点施工方案简述关联的wbs、以及关联的施工方案。
     *
     * @param id
     * @return
     */
    public RelateProjectVo getRelateProjectByWbs(Long id) {
        RelateProjectVo vo = new RelateProjectVo();
        if (id == null) {
            return vo;
        }

        // 查询本级以及所有子级wbs
        List<XmslWbs> wbsList = xmslWbsService.childListByIds(new Long[]{id}, true);
        // wbs编号
        List<String> codeList = wbsList.stream().map(XmslWbs::getCode).collect(Collectors.toList());
        String[] codes = codeList.toArray(new String[codeList.size()]);
        if (codes == null || codes.length == 0) {
            return vo;
        }

        // 根据wbs查询关联危大工程方案清单
        List<QqchDangerConstructionList> dangerList = qqchDangerConstructionListService.getByWbsCodes(codes);
        // 根据wbs查询关联重难点施工方案简述
        List<QqchKeyDifficultConstructionBrief> keyDifficultList = qqchKeyDifficultConstructionBriefService
            .getByWbsCodes(codes);
        // 根据wbs查询关联施工方案清单
        List<QqchConstructionList> constructionList = qqchConstructionListService.getByWbsCodes(codes);

        // 危大工程关联wbs编号
        List<String> dangerWbsCodeList =
            dangerList.stream().map(QqchDangerConstructionList::getWbsCode).collect(Collectors.toList());
        // 危大工程关联wbs名称
        List<String> dangerWbsNameList =
            dangerList.stream().map(QqchDangerConstructionList::getWbsName).collect(Collectors.toList());
        // 重难点施工方案简述关联wbs编号
        List<String> keyDifficultWbsCodeList =
            keyDifficultList.stream().map(QqchKeyDifficultConstructionBrief::getWbsCode)
                .collect(Collectors.toList());
        // 重难点施工方案简述关联wbs名称
        List<String> keyDifficultWbsNameList =
            keyDifficultList.stream().map(QqchKeyDifficultConstructionBrief::getWbsName).collect(Collectors.toList());
        // 施工方案名称
        List<String> constructionNameList = constructionList.stream().map(QqchConstructionList::getSchemeName)
            .collect(Collectors.toList());

        vo.setDangerProjectCode(StringUtils.join(dangerWbsCodeList.toArray(), ","));
        vo.setDangerProject(StringUtils.join(dangerWbsNameList.toArray(), ","));
        vo.setKeyDifficultProjectCode(StringUtils.join(keyDifficultWbsCodeList.toArray(), ","));
        vo.setKeyDifficultProject(StringUtils.join(keyDifficultWbsNameList.toArray(), ","));
        vo.setSchemeQuery(StringUtils.join(constructionNameList.toArray(), ","));
        //获取wbs计划开始时间
        XmslWbs wbs = WbsRedisUtils.getWbs(id);
        if(StringUtils.isBlank(wbs.getCode()))
            return vo;
        JdglMainPlanItem jdglMainPlanItemParam = new JdglMainPlanItem();
        jdglMainPlanItemParam.setWbsCode(wbs.getCode());
        JdglMainPlanItem jdglMainPlan = jdglMainPlanItemService.getUsing4One(jdglMainPlanItemParam);
        if(jdglMainPlan ==null)
            return vo;
        vo.setPlanImplementTime(jdglMainPlan.getStartDate());
        return vo;
    }
    
}
