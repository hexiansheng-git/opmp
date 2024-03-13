package com.hhwy.pm.qqch.wzch.specialmaterial.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlanDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.mapper.WzchSpecialMaterialPlanDetailMapper;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanDetailService;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanService;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialRequestDetailService;
import com.hhwy.pm.qqch.wzch.specialmaterial.vo.WzchSpecialMaterialPlanDetailImportVO;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 专项物资发运策划-发运策划Service业务层处理
 * 
 * @author mls
 * @date 2022-12-07
 */
@Service
public class WzchSpecialMaterialPlanDetailServiceImpl implements IWzchSpecialMaterialPlanDetailService {
    @Autowired
    private WzchSpecialMaterialPlanDetailMapper wzchSpecialMaterialPlanDetailMapper;
    @Resource
    private IWzchSpecialMaterialPlanService wzchSpecialMaterialPlanService;
    @Resource
    private IWzchSpecialMaterialRequestDetailService wzchSpecialMaterialRequestDetailService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Resource
    private SystemApiService dictTypeService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private GenCodeService genCodeService;

    /**
     * 查询专项物资发运策划-发运策划
     * 
     * @param id 专项物资发运策划-发运策划ID
     * @return 专项物资发运策划-发运策划
     */
    @Override
    public WzchSpecialMaterialPlanDetail selectWzchSpecialMaterialPlanDetailById(Long id) {
        return wzchSpecialMaterialPlanDetailMapper.selectWzchSpecialMaterialPlanDetailById(id);
    }

    /**
     * 查询专项物资发运策划-发运策划列表
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 专项物资发运策划-发运策划
     */
    @Override
    public List<WzchSpecialMaterialPlanDetail> selectWzchSpecialMaterialPlanDetailList(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail) {
        return wzchSpecialMaterialPlanDetailMapper.selectWzchSpecialMaterialPlanDetailList(wzchSpecialMaterialPlanDetail);
    }

    /**
     * 新增专项物资发运策划-发运策划
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 结果
     */
    @Override
    public int insertWzchSpecialMaterialPlanDetail(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail) {

    wzchSpecialMaterialPlanDetail.setId(IdWorker.createId());

        wzchSpecialMaterialPlanDetail.setCreateTime(DateUtils.getNowDate());

        return wzchSpecialMaterialPlanDetailMapper.insertWzchSpecialMaterialPlanDetail(wzchSpecialMaterialPlanDetail);
    }

    /**
     * 修改专项物资发运策划-发运策划
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 结果
     */
    @Override
    public int updateWzchSpecialMaterialPlanDetail(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail) {
        wzchSpecialMaterialPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchSpecialMaterialPlanDetailMapper.updateWzchSpecialMaterialPlanDetail(wzchSpecialMaterialPlanDetail);
    }

    /**
     * 删除专项物资发运策划-发运策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialPlanDetailByIds(List<Long> ids) {
        return wzchSpecialMaterialPlanDetailMapper.deleteWzchSpecialMaterialPlanDetailByIds(ids);
    }

    /**
     * 删除专项物资发运策划-发运策划信息
     * 
     * @param id 专项物资发运策划-发运策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialPlanDetailById(Long id) {
        return wzchSpecialMaterialPlanDetailMapper.deleteWzchSpecialMaterialPlanDetailById(id);
    }

    @Override
    public List<WzchSpecialMaterialPlanDetail> importPlanDetail(MultipartFile file) throws IOException {
        List<SysDictData> sysDictDataList = dictTypeService.selectDictDataByType("transport_mode");
        if(file==null){
            throw new BaseException("入参缺失");
        }
        List<WzchSpecialMaterialPlanDetail> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), WzchSpecialMaterialPlanDetailImportVO.class, new ReadListener<WzchSpecialMaterialPlanDetailImportVO>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                e.printStackTrace();
            }

//            @Override
//            public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
//                System.out.println(JSONObject.toJSONString(map));
//            }

            @Override
            public void invoke(WzchSpecialMaterialPlanDetailImportVO importVo, AnalysisContext analysisContext) {
                WzchSpecialMaterialPlanDetail detail = new WzchSpecialMaterialPlanDetail();
                BeanUtils.copyProperties(importVo,detail);
                sysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(detail.getTransportMode()))
                        .findFirst().ifPresent(val -> detail.setTransportMode(val.getDictValue()));
                list.add(detail);
                if(list.size() > 500){
                    throw new BaseException("单次导入仅能500条");
                }
            }

            @Override
            public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }

            @Override
            public boolean hasNext(AnalysisContext analysisContext) {
                return true;
            }

        }).sheet("发运策划").doReadSync();
        return list;
    }

    @Override
    @Transactional
    public Long save(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        fillWzchLocalTransportPlan(wzchSpecialMaterialPlan);
//        WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanService.selectWzchSpecialMaterialPlanById(wzchSpecialMaterialPlan.getId());
        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        wzchSpecialMaterialPlan.setTitle(tenantName+"-"+"专项物资发运策划");
        if(wzchSpecialMaterialPlan.getId()==null){
            wzchSpecialMaterialPlan.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(wzchSpecialMaterialPlan);
            wzchSpecialMaterialPlanService.insertWzchSpecialMaterialPlan(wzchSpecialMaterialPlan);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(wzchSpecialMaterialPlan);
            wzchSpecialMaterialPlanService.updateWzchSpecialMaterialPlan(wzchSpecialMaterialPlan);
            wzchSpecialMaterialPlanDetailMapper.deleteByPlanId(wzchSpecialMaterialPlan.getId());
            wzchSpecialMaterialRequestDetailService.deleteByPlanId(wzchSpecialMaterialPlan.getId());
        }
        if (CollectionUtils.isNotEmpty( wzchSpecialMaterialPlan.getPlanDetailList())) {
            List<WzchSpecialMaterialPlanDetail> list = wzchSpecialMaterialPlan.getPlanDetailList();
            for (int i = 0; i < list.size(); i++) {
                WzchSpecialMaterialPlanDetail temp = list.get(i);
                temp.setPlanId(wzchSpecialMaterialPlan.getId());
            }
            wzchSpecialMaterialPlanDetailMapper.batchInsert(wzchSpecialMaterialPlan.getPlanDetailList());
        }
        if (CollectionUtils.isNotEmpty(wzchSpecialMaterialPlan.getRequestDetailList())){
            wzchSpecialMaterialPlan.getRequestDetailList().stream().forEach(r->{
                r.setPlanId(wzchSpecialMaterialPlan.getId());
            });
            wzchSpecialMaterialRequestDetailService.batchInsert(wzchSpecialMaterialPlan.getRequestDetailList());
        }
        if (ButtonMark.CONFIRM.equals(wzchSpecialMaterialPlan.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchSpecialMaterialPlan.getMenuId();
            String stageIdentity = wzchSpecialMaterialPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return wzchSpecialMaterialPlan.getId();
    }

    @Override
    public List<WzchSpecialMaterialPlanDetail> exportPlanDetail(List<WzchSpecialMaterialPlanDetail> list) {
        wzchCommonService.setWzchtMaterialInfo(list);
        List<SysDictData> sysDictDataList = dictTypeService.selectDictDataByType("transport_mode");
        for (WzchSpecialMaterialPlanDetail detail : list) {
            sysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getTransportMode()))
                    .findFirst().ifPresent(val -> detail.setTransportMode(val.getDictLabel()));
        }

        return list;
    }

    @Override
    public int updateValidByPlanId(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail) {
        return wzchSpecialMaterialPlanDetailMapper.updateValidByPlanId(wzchSpecialMaterialPlanDetail);
    }

    private void fillWzchLocalTransportPlan(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        if (wzchSpecialMaterialPlan == null || wzchSpecialMaterialPlan.getVersion() == null) {
            throw new BaseException("入参缺失");
        }
        wzchSpecialMaterialPlan.setTitle(com.hhwy.utils.ObjectUtils.nvlString(wzchSpecialMaterialPlan.getTitle()));
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getPlanCode())) {
            String code = genCodeService.getCode(CodeEnum.EQU_SPECICAL_MATERIAL_PLAN);
            code += genCodeService.fillString(1, 2);
            wzchSpecialMaterialPlan.setPlanCode(code);
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getCreateUser())) {
            wzchSpecialMaterialPlan.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getCreateUserName())) {
            wzchSpecialMaterialPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if (ObjectUtils.isEmpty(wzchSpecialMaterialPlan.getCreateTime())) {
            wzchSpecialMaterialPlan.setCreateTime(DateUtils.getNowDate());
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getUpdateUser())) {
            wzchSpecialMaterialPlan.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getUpdateUserName())) {
            wzchSpecialMaterialPlan.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if (ObjectUtils.isEmpty(wzchSpecialMaterialPlan.getUpdateTime())) {
            wzchSpecialMaterialPlan.setUpdateTime(DateUtils.getNowDate());
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getDelFlag())) {
            wzchSpecialMaterialPlan.setDelFlag("0");
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getVersionCode())) {
            wzchSpecialMaterialPlan.setVersionCode("1.0");
        }
        if (StringUtils.isBlank(wzchSpecialMaterialPlan.getValid())) {
            wzchSpecialMaterialPlan.setValid("0");
        }
        if (CollectionUtils.isNotEmpty(wzchSpecialMaterialPlan.getPlanDetailList())) {
            for (WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail : wzchSpecialMaterialPlan.getPlanDetailList()) {
                if (wzchSpecialMaterialPlanDetail == null) {
                    continue;
                }
                Long detailId = wzchSpecialMaterialPlanDetail.getId();
                if (detailId == null) {
                    wzchSpecialMaterialPlanDetail.setId(IdWorker.createId());
                }
                if (wzchSpecialMaterialPlanDetail.getPlanId() == null) {
                    wzchSpecialMaterialPlanDetail.setPlanId(wzchSpecialMaterialPlan.getId());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getCreateUser())) {
                    wzchSpecialMaterialPlanDetail.setCreateUser(SecurityUtils.getUserId().toString());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getCreateUserName())) {
                    wzchSpecialMaterialPlanDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if (ObjectUtils.isEmpty(wzchSpecialMaterialPlanDetail.getCreateTime())) {
                    wzchSpecialMaterialPlanDetail.setCreateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getUpdateUser())) {
                    wzchSpecialMaterialPlanDetail.setUpdateUser(SecurityUtils.getUserId().toString());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getUpdateUserName())) {
                    wzchSpecialMaterialPlanDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if (ObjectUtils.isEmpty(wzchSpecialMaterialPlanDetail.getUpdateTime())) {
                    wzchSpecialMaterialPlanDetail.setUpdateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getDelFlag())) {
                    wzchSpecialMaterialPlanDetail.setDelFlag("0");
                }
                if (StringUtils.isBlank(wzchSpecialMaterialPlanDetail.getValid())) {
                    wzchSpecialMaterialPlanDetail.setValid("0");
                }

            }
        }
        if (CollectionUtils.isNotEmpty(wzchSpecialMaterialPlan.getRequestDetailList())) {
            for (WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail : wzchSpecialMaterialPlan.getRequestDetailList()) {

                if (wzchSpecialMaterialRequestDetail == null) {
                    continue;
                }
                Long detailId = wzchSpecialMaterialRequestDetail.getId();
                if (detailId == null) {
                    wzchSpecialMaterialRequestDetail.setId(IdWorker.createId());
                }
                if (wzchSpecialMaterialRequestDetail.getPlanId() == null) {
                    wzchSpecialMaterialRequestDetail.setPlanId(wzchSpecialMaterialPlan.getId());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getCreateUser())) {
                    wzchSpecialMaterialRequestDetail.setCreateUser(SecurityUtils.getUserId().toString());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getCreateUserName())) {
                    wzchSpecialMaterialRequestDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if (ObjectUtils.isEmpty(wzchSpecialMaterialRequestDetail.getCreateTime())) {
                    wzchSpecialMaterialRequestDetail.setCreateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getUpdateUser())) {
                    wzchSpecialMaterialRequestDetail.setUpdateUser(SecurityUtils.getUserId().toString());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getUpdateUserName())) {
                    wzchSpecialMaterialRequestDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if (ObjectUtils.isEmpty(wzchSpecialMaterialRequestDetail.getUpdateTime())) {
                    wzchSpecialMaterialRequestDetail.setUpdateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getDelFlag())) {
                    wzchSpecialMaterialRequestDetail.setDelFlag("0");
                }
                if (StringUtils.isBlank(wzchSpecialMaterialRequestDetail.getValid())) {
                    wzchSpecialMaterialRequestDetail.setValid("0");
                }

            }
        }

    }

}
