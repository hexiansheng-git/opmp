package com.hhwy.pm.qqch.wzch.fund.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFundDetail;
import com.hhwy.pm.qqch.wzch.fund.mapper.WzchFundDetailMapper;
import com.hhwy.pm.qqch.wzch.fund.service.IWzchFundDetailService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资金策划详情Service业务层处理
 *
 * @author mls
 * @date 2022-12-08
 */
@Service
public class WzchFundDetailServiceImpl implements IWzchFundDetailService {
    @Resource
    private WzchFundDetailMapper wzchFundDetailMapper;

    /**
     * 查询资金策划详情
     *
     * @param id 资金策划详情ID
     * @return 资金策划详情
     */
    @Override
    public WzchFundDetail selectWzchFundDetailById(Long id) {
        return wzchFundDetailMapper.selectWzchFundDetailById(id);
    }

    /**
     * 查询资金策划详情列表
     *
     * @param wzchFundDetail 资金策划详情
     * @return 资金策划详情
     */
    @Override
    public List<WzchFundDetail> selectWzchFundDetailList(WzchFundDetail wzchFundDetail) {
        return wzchFundDetailMapper.selectWzchFundDetailList(wzchFundDetail);
    }

    /**
     * 新增资金策划详情
     *
     * @param wzchFundDetail 资金策划详情
     * @return 结果
     */
    @Override
    public int insertWzchFundDetail(WzchFundDetail wzchFundDetail) {

        wzchFundDetail.setId(IdWorker.createId());

        wzchFundDetail.setCreateTime(DateUtils.getNowDate());

        return wzchFundDetailMapper.insertWzchFundDetail(wzchFundDetail);
    }

    /**
     * 修改资金策划详情
     *
     * @param wzchFundDetail 资金策划详情
     * @return 结果
     */
    @Override
    public int updateWzchFundDetail(WzchFundDetail wzchFundDetail) {
        wzchFundDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchFundDetailMapper.updateWzchFundDetail(wzchFundDetail);
    }

    /**
     * 删除资金策划详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchFundDetailByIds(String ids) {
        return wzchFundDetailMapper.deleteWzchFundDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资金策划详情信息
     *
     * @param id 资金策划详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchFundDetailById(Long id) {
        return wzchFundDetailMapper.deleteWzchFundDetailById(id);
    }

    /**
     * 批量新增或者删除
     *
     * @param detailList
     * @param fundId
     * @return
     */
    @Override
    public int insertOrUpdateBatch(List<WzchFundDetail> detailList, Long fundId) {
        if (CollectionUtils.isEmpty(detailList)) {
            return 0;
            // TODO throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
        }

        Assert.notNull(fundId, "资金策划id不能为空");
        this.wzchFundDetailMapper.deleteByFundId(fundId);
        WzchFundDetail[] as = detailList.toArray(new WzchFundDetail[]{});
        CollectionUtils.reverseArray(as);
        detailList = Arrays.asList(as);
        List<WzchFundDetail> insertOrUpdateData = detailList.stream().map(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
            item.setFundId(fundId);
            item.setValid("0");
            EntityUtils.setCreateUpdateInfo(item);
            return item;
        }).collect(Collectors.toList());

        return wzchFundDetailMapper.insertOrUpdateBatch(insertOrUpdateData);

    }
}
