package com.hhwy.pm.qqch.wzch.internaladjust.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import com.hhwy.pm.qqch.wzch.internaladjust.mapper.WzchInternalAdjustDetailMapper;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchInternalAdjustDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 内部调剂材料策划物资详情Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchInternalAdjustDetailServiceImpl implements IWzchInternalAdjustDetailService {
    @Resource
    private WzchInternalAdjustDetailMapper wzchInternalAdjustDetailMapper;
    @Resource
    private WzchCommonService wzchCommonService;


    /**
     * 查询内部调剂材料策划物资详情
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 内部调剂材料策划物资详情
     */
    @Override
    public WzchInternalAdjustDetail selectWzchInternalAdjustDetailById(Long id) {
        return wzchInternalAdjustDetailMapper.selectWzchInternalAdjustDetailById(id);
    }

    /**
     * 查询内部调剂材料策划物资详情列表
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 内部调剂材料策划物资详情
     */
    @Override
    public List<WzchInternalAdjustDetail> selectWzchInternalAdjustDetailList(WzchInternalAdjustDetail wzchInternalAdjustDetail) {
        List<WzchInternalAdjustDetail> wzchInternalAdjustDetails = wzchInternalAdjustDetailMapper.selectWzchInternalAdjustDetailList(wzchInternalAdjustDetail);
        return wzchCommonService.setWzchtMaterialInfo(wzchInternalAdjustDetails);
    }

    /**
     * 新增内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    @Override
    public int insertWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail) {

        wzchInternalAdjustDetail.setId(IdWorker.createId());

        wzchInternalAdjustDetail.setCreateTime(DateUtils.getNowDate());

        return wzchInternalAdjustDetailMapper.insertWzchInternalAdjustDetail(wzchInternalAdjustDetail);
    }

    /**
     * 修改内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    @Override
    public int updateWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail) {
        wzchInternalAdjustDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchInternalAdjustDetailMapper.updateWzchInternalAdjustDetail(wzchInternalAdjustDetail);
    }

    /**
     * 删除内部调剂材料策划物资详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchInternalAdjustDetailByIds(String ids) {
        return wzchInternalAdjustDetailMapper.deleteWzchInternalAdjustDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部调剂材料策划物资详情信息
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchInternalAdjustDetailById(Long id) {
        return wzchInternalAdjustDetailMapper.deleteWzchInternalAdjustDetailById(id);
    }

    /**
     * 获取物资详情
     *
     * @param dto
     * @return
     */
    @Override
    public List<WzchInternalAdjustDetail> getMtlDetailList(WzchInternalAdjustDetail dto) {
        List<WzchInternalAdjustDetail> mtlDetailList = wzchInternalAdjustDetailMapper.getMtlDetailList(dto);
        return wzchCommonService.setWzchtMaterialInfo(mtlDetailList);
    }

    /**
     * 批量新增或者编辑
     *
     * @param detailList
     * @param adjustId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrUpdateBatch(List<WzchInternalAdjustDetail> detailList, Long adjustId) {
        if (CollectionUtils.isEmpty(detailList)) {
            return 0;
            // TODO throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
        }
        // 先将之前数据都进行删除
        wzchInternalAdjustDetailMapper.deleteByAdjustId(adjustId);

        // 集合类型转化 设置id 设置purchaseId
        List<WzchInternalAdjustDetail> insertOrUpdateData = detailList.stream().peek(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
//            item.setProjectId(projectId);
            item.setValid("0");
            item.setInternalAdjustId(adjustId);
            EntityUtils.setCreateUpdateInfo(item);
        }).collect(Collectors.toList());

        return wzchInternalAdjustDetailMapper.insertOrUpdateBatch(insertOrUpdateData);
    }

    @Override
    public int deleteByAdjustIds(String ids) {

        return wzchInternalAdjustDetailMapper.deleteByAdjustIds(Convert.toStrArray(ids));
    }
}
