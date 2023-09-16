package com.hhwy.pm.qqch.wzch.specialproject.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProjectDetail;
import com.hhwy.pm.qqch.wzch.specialproject.mapper.WzchSpecialProjectDetailMapper;
import com.hhwy.pm.qqch.wzch.specialproject.service.IWzchSpecialProjectDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 专项物资策划物资详情Service业务层处理
 *
 * @author mls
 * @date 2022-12-11
 */
@Service
public class WzchSpecialProjectDetailServiceImpl implements IWzchSpecialProjectDetailService {
    @Resource
    private WzchSpecialProjectDetailMapper wzchSpecialProjectDetailMapper;

    @Resource
    private IWzchTotalDemandDetailService totalDemandDetailService;

    @Resource
    private WzchCommonService wzchCommonService;

    /**
     * 查询专项物资策划物资详情
     *
     * @param id 专项物资策划物资详情ID
     * @return 专项物资策划物资详情
     */
    @Override
    public WzchSpecialProjectDetail selectWzchSpecialProjectDetailById(Long id) {
        return wzchSpecialProjectDetailMapper.selectWzchSpecialProjectDetailById(id);
    }

    /**
     * 查询专项物资策划物资详情列表
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 专项物资策划物资详情
     */
    @Override
    public List<WzchSpecialProjectDetail> selectWzchSpecialProjectDetailList(WzchSpecialProjectDetail wzchSpecialProjectDetail) {
        return wzchSpecialProjectDetailMapper.selectWzchSpecialProjectDetailList(wzchSpecialProjectDetail);
    }

    /**
     * 新增专项物资策划物资详情
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 结果
     */
    @Override
    public int insertWzchSpecialProjectDetail(WzchSpecialProjectDetail wzchSpecialProjectDetail) {

        wzchSpecialProjectDetail.setId(IdWorker.createId());

        wzchSpecialProjectDetail.setCreateTime(DateUtils.getNowDate());

        return wzchSpecialProjectDetailMapper.insertWzchSpecialProjectDetail(wzchSpecialProjectDetail);
    }

    /**
     * 修改专项物资策划物资详情
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 结果
     */
    @Override
    public int updateWzchSpecialProjectDetail(WzchSpecialProjectDetail wzchSpecialProjectDetail) {
        wzchSpecialProjectDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchSpecialProjectDetailMapper.updateWzchSpecialProjectDetail(wzchSpecialProjectDetail);
    }

    /**
     * 删除专项物资策划物资详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialProjectDetailByIds(String ids) {
        return wzchSpecialProjectDetailMapper.deleteWzchSpecialProjectDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除专项物资策划物资详情信息
     *
     * @param id 专项物资策划物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialProjectDetailById(Long id) {
        return wzchSpecialProjectDetailMapper.deleteWzchSpecialProjectDetailById(id);
    }

    /**
     * 批量新增或者更新
     *
     * @param detailList
     * @param specialProjectId
     * @return
     */
    @Override
    public int insertOrUpdateBatch(List<WzchSpecialProjectDetail> detailList, Long specialProjectId) {

        if (CollectionUtils.isEmpty(detailList)) {
            // TODO throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
        }

        Assert.notNull(specialProjectId, "专项物资策划id不能为空");
        this.wzchSpecialProjectDetailMapper.deleteBySpecialProjectId(specialProjectId);

        List<WzchSpecialProjectDetail> insertOrUpdateData = detailList.stream().map(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
            item.setSpecialProjectId(specialProjectId);
            item.setValid("0");
            EntityUtils.setCreateUpdateInfo(item);
            return item;
        }).collect(Collectors.toList());
        return wzchSpecialProjectDetailMapper.insertOrUpdateBatch(insertOrUpdateData);
    }

    @Override
    public List<WzchSpecialProjectDetail> getMtlDetailList(WzchSpecialProjectDetail dto) {
        WzchTotalDemandDetail demandDetail = new WzchTotalDemandDetail();
        demandDetail.setProjectId(dto.getProjectId());
        demandDetail.setValid("1");
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = totalDemandDetailService.selectWzchTotalDemandDetailList(demandDetail);
        // 为空就直接返回
        if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) return Collections.emptyList();

        // 数据查询到之后 重新封装
        List<WzchSpecialProjectDetail> resList = wzchTotalDemandDetails.stream().map(item -> {
            WzchSpecialProjectDetail detail = new WzchSpecialProjectDetail();
            detail.setMaterialCode(item.getMaterialCode());
            detail.setMaterialStandard(item.getMaterialStandard());
            detail.setMaterialTechParam(item.getMaterialTechParam());
            return detail;
        }).collect(Collectors.toList());

        Map<String, String> busAndMaterialMap = new HashMap<>(2);
        busAndMaterialMap.put("materialName", "materialName");
        busAndMaterialMap.put("unit", "unit");
        // 从redis中获取物资名 单位 并进行封装
        resList = wzchCommonService.setMaterialInfo(resList, "materialCode", busAndMaterialMap);
        return resList;
    }
}
