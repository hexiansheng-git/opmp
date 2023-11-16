package com.hhwy.pm.qqch.wzch.revolverent.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDetailDTO;
import com.hhwy.pm.qqch.wzch.revolverent.mapper.WzchRevolveRentDetailMapper;
import com.hhwy.pm.qqch.wzch.revolverent.service.IWzchRevolveRentDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 周转材租赁策划物资详情Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchRevolveRentDetailServiceImpl implements IWzchRevolveRentDetailService {
    @Resource
    private WzchRevolveRentDetailMapper wzchRevolveRentDetailMapper;
    @Resource
    private WzchCommonService wzchCommonService;

    /**
     * 查询周转材租赁策划物资详情
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 周转材租赁策划物资详情
     */
    @Override
    public WzchRevolveRentDetail selectWzchRevolveRentDetailById(Long id) {
        return wzchRevolveRentDetailMapper.selectWzchRevolveRentDetailById(id);
    }

    /**
     * 查询周转材租赁策划物资详情列表
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 周转材租赁策划物资详情
     */
    @Override
    public List<WzchRevolveRentDetailDTO> selectWzchRevolveRentDetailList(WzchRevolveRentDetail wzchRevolveRentDetail) {
        List<WzchRevolveRentDetailDTO> wzchRevolveRentDetailDTOS = wzchRevolveRentDetailMapper.selectWzchRevolveRentDetailList(wzchRevolveRentDetail);
        return wzchCommonService.setWzchtMaterialInfo(wzchRevolveRentDetailDTOS);
    }

    /**
     * 新增周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    @Override
    public int insertWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail) {

        wzchRevolveRentDetail.setId(IdWorker.createId());

        wzchRevolveRentDetail.setCreateTime(DateUtils.getNowDate());

        return wzchRevolveRentDetailMapper.insertWzchRevolveRentDetail(wzchRevolveRentDetail);
    }

    /**
     * 修改周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    @Override
    public int updateWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail) {
        wzchRevolveRentDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchRevolveRentDetailMapper.updateWzchRevolveRentDetail(wzchRevolveRentDetail);
    }

    /**
     * 删除周转材租赁策划物资详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchRevolveRentDetailByIds(String ids) {
        return wzchRevolveRentDetailMapper.deleteWzchRevolveRentDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周转材租赁策划物资详情信息
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchRevolveRentDetailById(Long id) {
        return wzchRevolveRentDetailMapper.deleteWzchRevolveRentDetailById(id);
    }

    /**
     * 根据项目id获取物资详情
     * 来源策划中类型为周转材，来源为租赁的数据
     *
     * @param dto
     * @return
     */
    @Override
    public List<WzchRevolveRentDetailDTO> getMtlDetailList(WzchRevolveRentDetailDTO dto) {
        List<WzchRevolveRentDetailDTO> mtlDetailList = wzchRevolveRentDetailMapper.getMtlDetailList(dto);
        return wzchCommonService.setWzchtMaterialInfo(mtlDetailList);
    }

    @Override
    public int insertOrUpdateBatch(List<WzchRevolveRentDetailDTO> detailList, Long rentId) {
        Assert.notNull(rentId, "周转租赁不能为空");
        this.wzchRevolveRentDetailMapper.deleteByRentId(rentId);
        if (CollectionUtils.isEmpty(detailList)) {
            return 0;
            // TODO throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
        }
        List<WzchRevolveRentDetail> insertOrUpdateData = detailList.stream().map(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
            item.setRevolveRentId(rentId);
            item.setValid("0");
            EntityUtils.setCreateUpdateInfo(item);
            return (WzchRevolveRentDetail) item;
        }).collect(Collectors.toList());
        return wzchRevolveRentDetailMapper.insertOrUpdateBatch(insertOrUpdateData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByRentIds(String ids) {

        return wzchRevolveRentDetailMapper.deleteByRentIds(Convert.toStrArray(ids));
    }
}
