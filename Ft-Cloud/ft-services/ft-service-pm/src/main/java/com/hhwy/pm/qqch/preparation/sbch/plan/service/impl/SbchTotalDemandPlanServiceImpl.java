package com.hhwy.pm.qqch.preparation.sbch.plan.service.impl;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.sql.SqlUtils;
import com.hhwy.common.core.web.page.PageDomain;
import com.hhwy.common.core.web.page.TableSupport;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.SbchTotalDemandPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.PageFuncUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.myUtilPrepare.SetMaterialNameUtils;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-23 16:39
 */
@Service
public class SbchTotalDemandPlanServiceImpl implements SbchTotalDemandPlanService {
    @Autowired
    private SbchTotalDemandPlanMapper sbchTotalDemandPlanMapper;
    @Autowired
    private SbchTotalDemandPlanDetailMapper sbchTotalDemandPlanDetailMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private SetMaterialNameUtils setMaterialNameUtils;


    @Override
    public SbchTotalDemandPlan getList(BigDecimal version) {
        return this.getSbchTotalDemandPlan(version, null);
    }

    @Override
    public SbchTotalDemandPlan getLeaderList(SbchTotalDemandPlanDetail param) {
        return this.getSbchTotalDemandPlan(param.getVersion(), param);
    }

    private SbchTotalDemandPlan getSbchTotalDemandPlan(BigDecimal version, SbchTotalDemandPlanDetail param) {
        SbchTotalDemandPlan result = new SbchTotalDemandPlan();
        List<SbchTotalDemandPlanDetail> resultList = new ArrayList<>();
        version = VersionUtil.getVersion("sbch_total_demand_plan", version);
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(version);
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(sbchTotalDemandPlan);
        if (!ObjectNullUtil.isEmpty(sbchTotalDemandPlans)) {
            SbchTotalDemandPlan sbchTotalDemandPlan1 = sbchTotalDemandPlans.get(0);
            result = sbchTotalDemandPlan1;

            if (ObjectUtils.isEmpty(param)) {
                param = new SbchTotalDemandPlanDetail();
                param.setPlanId(sbchTotalDemandPlan1.getId());
                resultList = sbchTotalDemandPlanDetailMapper.selectSbchTotalDemandPlanDetailList(param);
            }else {
                Integer pageNum = param.getPageNum();
                Integer pageSize = param.getPageSize();
                if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
//                String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
                    PageHelper.startPage(pageNum, pageSize, null);
                }
                param.setPlanId(sbchTotalDemandPlan1.getId());
                resultList = sbchTotalDemandPlanDetailMapper.selectSbchTotalDemandPlanDetailLeaderList(param);
            }
            if (!ObjectNullUtil.isEmpty(resultList)) {
                Map<String, String> busAndMaterialMap = new HashMap<>();
                busAndMaterialMap.put("materialName", "materialName");
                busAndMaterialMap.put("materialSpec", "materialSpec");
                resultList = setMaterialNameUtils.setMaterialInfo(resultList, "materialCode", busAndMaterialMap);
                Map<String, String> busAndCategoryMap = new HashMap<>();
                busAndCategoryMap.put("ptVar1", "categoryName");
                resultList = setMaterialNameUtils.setCategoryInfo(resultList, "materialType", busAndCategoryMap);
            }
        }
        result.setPlanDetailList(resultList);
        result.setVersion(version);
        result.setStageIdentity(qqchReviewService.getStage());
        return result;
    }

    @Override
    public void batchSave(SbchTotalDemandPlan vo) {
        List<SbchTotalDemandPlanDetail> list = vo.getPlanDetailList();
        SbchTotalDemandPlan temp = new SbchTotalDemandPlan();
        temp.setVersion(vo.getVersion());
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(temp);
        if (!ObjectNullUtil.isEmpty(sbchTotalDemandPlans)) {
            vo.setId(sbchTotalDemandPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(vo);
            sbchTotalDemandPlanMapper.updateSbchTotalDemandPlan(vo);
        } else {
            vo.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_TOTAL_PLAN);
            vo.setUnicode(setCode);
            vo.setTitleName("设备总需");
            MyUtilPrepareUtil.setCreateUpdateInfo(vo);
            sbchTotalDemandPlanMapper.insertSbchTotalDemandPlan(vo);
        }

        if (!ObjectNullUtil.isEmpty(list)) {
            //校验数据必填
            if ("1".equals(vo.getButtonMark()) || "2".equals(vo.getButtonMark())) {//确认
                JyDetailsUtil.jyDetails(list, ValidationGroups.Save.class);
            }
            for (SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail : list) {
                sbchTotalDemandPlanDetail.setId(IdWorker.createId());
                sbchTotalDemandPlanDetail.setPlanId(vo.getId());
                sbchTotalDemandPlanDetail.setProjectId(vo.getProjectId());
                sbchTotalDemandPlanDetail.setProjectName(vo.getProjectName());
                EntityUtils.setCreateInfo(sbchTotalDemandPlanDetail);
            }
        }
        // 清空数据库表中数据
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(vo.getVersion());
        sbchTotalDemandPlanDetailMapper.deleteSbchTotalDemandPlanDetailByPlanId(vo.getId(), SecurityUtils.getSysUser().getUserId(), new Date());
        if (!ObjectNullUtil.isEmpty(list)) {
            sbchTotalDemandPlanDetailMapper.batchInsert(list);
        }
        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(vo.getButtonMark())) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    @SelfEmpty(clazz = SbchTotalDemandPlanDetail.class)
    public List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail) {
        ArrayList<SbchTotalDemandPlanDetail> returnList = new ArrayList<>();

        return returnList;
    }
}
