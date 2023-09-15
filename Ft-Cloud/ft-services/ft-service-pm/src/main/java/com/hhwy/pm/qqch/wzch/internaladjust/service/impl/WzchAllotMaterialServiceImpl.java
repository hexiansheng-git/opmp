package com.hhwy.pm.qqch.wzch.internaladjust.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterial;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterialRange;
import com.hhwy.pm.qqch.wzch.internaladjust.mapper.WzchAllotMaterialMapper;
import com.hhwy.pm.qqch.wzch.internaladjust.mapper.WzchAllotMaterialRangeMapper;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchAllotMaterialService;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 可调拨材料Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchAllotMaterialServiceImpl implements IWzchAllotMaterialService {
    @Resource
    private WzchAllotMaterialMapper wzchAllotMaterialMapper;
//    @Resource
//    private IMaterialInfoService materialInfoService;
    @Resource
    private SystemServiceApi systemServiceApi;

    @Resource
    private WzchCommonService wzchCommonService;


    @Resource
    private WzchAllotMaterialRangeMapper rangeMapper;




    private final static String ONE = "1";
    private final static String TWO = "2";

    /**
     * 查询可调拨材料
     *
     * @param id 可调拨材料ID
     * @return 可调拨材料
     */
    @Override
    public WzchAllotMaterial selectWzchAllotMaterialById(Long id) {
        return wzchAllotMaterialMapper.selectWzchAllotMaterialById(id);
    }

    /**
     * 查询可调拨材料列表
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 可调拨材料
     */
    @Override
    public List<WzchAllotMaterial> selectWzchAllotMaterialList(WzchAllotMaterial wzchAllotMaterial) {
        Map<String, Object> params = wzchAllotMaterial.getParams();
        if (StringUtils.isNotEmpty(wzchAllotMaterial.getMaterialName()) || StringUtils.isNotEmpty(wzchAllotMaterial.getMaterialSpec())) {
            MaterialInfo materialInfo = new MaterialInfo();
            materialInfo.setMaterialName(wzchAllotMaterial.getMaterialName());
            materialInfo.setMaterialSpec(wzchAllotMaterial.getMaterialSpec());

//            List<MaterialInfo> materialInfos = materialInfoService.selectMaterialInfoList(materialInfo);
            List<MaterialInfo> materialInfos = null;
            if (CollectionUtils.isNotEmpty(materialInfos)) {
                if (params == null || params.size() == 0) {
                    params = new HashMap<>(1);
                }
                List<String> materialCodes = materialInfos.stream().map(MaterialInfo::getMaterialCode).collect(Collectors.toList());
                params.put("materialCodes", materialCodes);
            }
        }
        wzchAllotMaterial.setParams(params);
        List<WzchAllotMaterial> wzchAllotMaterials = wzchAllotMaterialMapper.selectWzchAllotMaterialList(wzchAllotMaterial);
        return wzchCommonService.setWzchtMaterialInfo(wzchAllotMaterials);
    }

    /**
     * 新增可调拨材料
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 结果
     */
    @Override
    public int insertWzchAllotMaterial(WzchAllotMaterial wzchAllotMaterial) {

        wzchAllotMaterial.setId(IdWorker.createId());

        wzchAllotMaterial.setCreateTime(DateUtils.getNowDate());

        return wzchAllotMaterialMapper.insertWzchAllotMaterial(wzchAllotMaterial);
    }

    /**
     * 修改可调拨材料
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 结果
     */
    @Override
    public int updateWzchAllotMaterial(WzchAllotMaterial wzchAllotMaterial) {
        wzchAllotMaterial.setUpdateTime(DateUtils.getNowDate());
        return wzchAllotMaterialMapper.updateWzchAllotMaterial(wzchAllotMaterial);
    }

    /**
     * 删除可调拨材料对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchAllotMaterialByIds(String ids) {
        return wzchAllotMaterialMapper.deleteWzchAllotMaterialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除可调拨材料信息
     *
     * @param id 可调拨材料ID
     * @return 结果
     */
    @Override
    public int deleteWzchAllotMaterialById(Long id) {
        return wzchAllotMaterialMapper.deleteWzchAllotMaterialById(id);
    }

    @Override
    public long save(WzchAllotMaterial wzchAllotMaterial) {
        long id = wzchAllotMaterial.getId() == null ? IdWorker.createId() : wzchAllotMaterial.getId();
        wzchAllotMaterial.setId(id);
        // 设置projectIds
        // wzchAllotMaterial.setProjectIds(this.getAllotProjectId(wzchAllotMaterial));
        EntityUtils.setUpdateInfo(wzchAllotMaterial);
        int i = this.updateWzchAllotMaterial(wzchAllotMaterial);
        if (i == 0) {
//            EntityUtils.setCreateUpdateInfo(wzchAllotMaterial);
//            this.wzchAllotMaterialMapper.insertWzchAllotMaterial(wzchAllotMaterial);
            throw new RuntimeException("更新失败");
        }
        return id;
    }

    @Override
    public long saveList(String range, String projectId, String projectName, List<String> countryCodes, List<WzchAllotMaterial> mtlInfoList) {
        WzchAllotMaterial allotMaterial = new WzchAllotMaterial();
        allotMaterial.setRange(range);
        allotMaterial.setProjectId(Long.parseLong(projectId));
        String countryCodesStr = CollectionUtils.isNotEmpty(countryCodes)? String.join(",", countryCodes) : "";
        allotMaterial.setCountryCodes(countryCodesStr);
        // 获取id
        String projectIds = this.getAllotProjectId(allotMaterial);
        for (WzchAllotMaterial wzchAllotMaterial : mtlInfoList) {
            wzchAllotMaterial.setProjectId(Long.parseLong(projectId));
            wzchAllotMaterial.setProjectName(projectName);
            wzchAllotMaterial.setId(IdWorker.createId());
            wzchAllotMaterial.setProjectIds(projectIds);
            EntityUtils.setUpdateInfo(wzchAllotMaterial);
        }
        return this.wzchAllotMaterialMapper.batchInsert(mtlInfoList);
    }

    @Override
    public int changeRange(WzchAllotMaterial wzchAllotMaterial) {
        List<String> countryCodeList = wzchAllotMaterial.getCountryCodeList();
        if (CollectionUtils.isEmpty(countryCodeList) && "2".equals(wzchAllotMaterial.getRange())) throw new CustomBusinessException("请选择国家");
        // 处理国家
        String countryCodes = countryCodeList.stream().distinct().collect(Collectors.joining(","));
        // 设置编码
        wzchAllotMaterial.setCountryCodes(countryCodes);
        // 查询可调拨项目id
        String allotProjectIds = this.getAllotProjectId(wzchAllotMaterial);

        int i;
        // 设置更新参数
        WzchAllotMaterialRange range = new WzchAllotMaterialRange();
        range.setProjectId(wzchAllotMaterial.getProjectId());
        range.setDelFlag("0");
        // 根据项目id查询数据
        List<WzchAllotMaterialRange> wzchAllotMaterialRanges = rangeMapper.selectList(range);

        // 设置项目id集合 编辑 新增的时候使用
        range.setProjectIds(allotProjectIds);
        // 设置可调拨范围
        range.setAdjustRange(wzchAllotMaterial.getRange());
        // 设置国家集合
        range.setCountryCodes(wzchAllotMaterial.getCountryCodes());

        if (CollectionUtils.isEmpty(wzchAllotMaterialRanges)) {
            // 为空则新增
            EntityUtils.setCreateUpdateInfo(range);
            range.setId(IdWorker.createId());
            i = rangeMapper.insert(range);
        } else {
            EntityUtils.setUpdateInfo(range);
            // 如果调拨范围为全区域 则将国家清空
            if (ONE.equals(wzchAllotMaterial.getRange())){
                range.setCountryCodes("");
            }
            // 不为空则编辑
            i = rangeMapper.updateByProjectId(range);
        }
        if (i != 1) throw new CustomBusinessException("更新异常");
        return i;
    }

    /**
     * 查询范围
     *
     * @param wzchAllotMaterial
     * @return
     */
    @Override
    public WzchAllotMaterialRange selectRange(WzchAllotMaterialRange wzchAllotMaterial) {
        Long projectId = wzchAllotMaterial.getProjectId();
        WzchAllotMaterialRange searchRange = new WzchAllotMaterialRange();
        searchRange.setProjectId(projectId);
        searchRange.setDelFlag("0");
        List<WzchAllotMaterialRange> ranges = rangeMapper.selectList(searchRange);
        if (CollectionUtils.isEmpty(ranges)) {
            searchRange.setAdjustRange("1");
        } else {
            if (ranges.size() != 1) throw new CustomBusinessException("获取调拨范围为多个, 请检查数据");
            searchRange = ranges.get(0);
            // 设置选中的国家 当调拨范围是指定国家的时候 才设置国家编码
            searchRange.setCountryCodeList(new ArrayList<>());
            if (TWO.equals(searchRange.getAdjustRange())) {
                searchRange.setCountryCodeList(Arrays.asList(searchRange.getCountryCodes().split(",")));
            }
        }
        return searchRange;
    }


    /**
     * 根据选择的 可调拨范围 去获取项目id
     * 全区域: 获取区域下的所有的项目
     * 指定国家: 获取该国家下的所有的项目
     *
     * @param wzchAllotMaterial
     * @return
     */
    private String getAllotProjectId(WzchAllotMaterial wzchAllotMaterial) {
        // 可调拨范围
        return "";
//        String range = wzchAllotMaterial.getRange();
//        if (StringUtils.isEmpty(range))
//            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "可调拨范围必须选择一个");
//
//        switch (range) {
//            case ONE:
//                // 全区域: 获取区域下的所有的项目
//                AtomicReference<String> projectIds = new AtomicReference<>("");
//                SysDept sysDept = new SysDept();
//                sysDept.setPtVar1(String.valueOf(wzchAllotMaterial.getProjectId()));
//                List<SysDept> sysDepts = deptService.selectDeptList(sysDept);
//                if (CollectionUtils.isNotEmpty(sysDepts)) {
//                    SysDept dept = sysDepts.get(0);
//                    // 获取到选择项目的祖级 id
//                    String ancestors = dept.getAncestors();
//                    Long[] deptIds = Arrays.stream(ancestors.split(",")).filter(StringUtils::isNotEmpty).map(Long::parseLong).toArray(Long[]::new);
//                    // 获取所有祖级的数据信息
//                    List<SysDept> ancestorDepts = deptMapper.selectDeptListByIds(deptIds);
//                    // 将区域信息筛选出来
//                    ancestorDepts.stream().filter(i -> "region".equals(i.getDeptType())).findFirst().ifPresent(dep -> {
//                        // 获取所有区域下面的所有子集
//                        List<SysDept> childrenDeptById = deptMapper.selectChildrenDeptById(dep.getDeptId());
//                        // 筛选并设置所有的项目id
//                        projectIds.set(childrenDeptById.stream()
//                                .filter(i -> "prjInfo".equals(i.getDeptType()))
//                                .map(BaseEntity::getPtVar1).distinct()
//                                .collect(Collectors.joining(",")));
//
//                    });
//                }
//                return projectIds.get();
//            case TWO:
//                String countryCodes = wzchAllotMaterial.getCountryCodes();
//                // 选择可调拨范围是指定国家时, 必需选中一个国家
//                if (StringUtils.isEmpty(countryCodes)) {
//                    throw new CustomBusinessException("请选择国家");
//                }
//                // 指定国家: 获取该国家下的所有的项目
//                ProjectInfo projectInfo = new ProjectInfo();
//                HashMap<String, Object> where = new HashMap<>();
//                where.put("countryCodes", countryCodes);
//                projectInfo.setParams(where);
//                List<ProjectInfo> projectInfos = projectInfoService.selectProjectInfoList(projectInfo);
//                if (CollectionUtils.isEmpty(projectInfos)) {
//                    throw new CustomBusinessException("请检查选择的国家，没有查询到与该国家相关的项目信息");
//                }
//                return projectInfos.stream().map(i -> String.valueOf(i.getId())).distinct().collect(Collectors.joining(","));
//            default:
//                throw new CustomBusinessException("请选择正确的可调拨范围选项");
//        }


    }


    @Override
    public int importData(List<WzchAllotMaterial> wzchAllotMaterials, WzchAllotMaterial wzchAllotMaterial) {
        String allotProjectIds = this.getAllotProjectId(wzchAllotMaterial);
        for (WzchAllotMaterial allotMaterial : wzchAllotMaterials) {
            allotMaterial.setId(IdWorker.createId());
            wzchAllotMaterial.setProjectName(wzchAllotMaterial.getProjectName());
            wzchAllotMaterial.setProjectId(wzchAllotMaterial.getProjectId());
            allotMaterial.setProjectIds(allotProjectIds);
            EntityUtils.setCreateUpdateInfo(allotMaterial);
        }
        return this.wzchAllotMaterialMapper.batchInsert(wzchAllotMaterials);
    }

    @Override
    public List<WzchAllotMaterial> adjustMtlList(String materialCode, String projectId) {
        Map<String, Object> params = new HashMap<>(3);
        if (StringUtils.isNotEmpty(materialCode)) {
            params.put("codes", materialCode);
        }
        WzchAllotMaterial wzchAllotMaterial = new WzchAllotMaterial();
        params.put("projectId", projectId);
        wzchAllotMaterial.setParams(params);
        List<WzchAllotMaterial> wzchAllotMaterials = this.wzchAllotMaterialMapper.selectWzchAllotMaterialByProjectId(wzchAllotMaterial);
        return wzchCommonService.setWzchtMaterialInfo(wzchAllotMaterials);
    }
}
