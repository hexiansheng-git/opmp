package com.hhwy.pm.qqch.wzch.priorpurchase.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDetailDTO;
import com.hhwy.pm.qqch.wzch.priorpurchase.mapper.WzchPriorPurchaseDetailMapper;
import com.hhwy.pm.qqch.wzch.priorpurchase.service.IWzchPriorPurchaseDetailService;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.mapper.WzchPurchaseSupplyDetailMapper;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先进场物资设备采购策划物资详情Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchPriorPurchaseDetailServiceImpl implements IWzchPriorPurchaseDetailService {
    @Resource
    private WzchPriorPurchaseDetailMapper wzchPriorPurchaseDetailMapper;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private WzchPurchaseSupplyDetailMapper supplyDetailMapper;

    /**
     * 查询优先进场物资设备采购策划物资详情
     *
     * @param id 优先进场物资设备采购策划物资详情ID
     * @return 优先进场物资设备采购策划物资详情
     */
    @Override
    public WzchPriorPurchaseDetail selectWzchPriorPurchaseDetailById(Long id) {
        return wzchPriorPurchaseDetailMapper.selectWzchPriorPurchaseDetailById(id);
    }

    /**
     * 查询优先进场物资设备采购策划物资详情列表
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 优先进场物资设备采购策划物资详情
     */
    @Override
    public List<WzchPriorPurchaseDetailDTO> selectWzchPriorPurchaseDetailList(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {
        List<WzchPriorPurchaseDetailDTO> wzchPriorPurchaseDetailDTOS = wzchPriorPurchaseDetailMapper.selectWzchPriorPurchaseDetailList(wzchPriorPurchaseDetail);
        return wzchCommonService.setWzchtMaterialInfo(wzchPriorPurchaseDetailDTOS);
    }

    /**
     * 新增优先进场物资设备采购策划物资详情
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 结果
     */
    @Override
    public int insertWzchPriorPurchaseDetail(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {

        wzchPriorPurchaseDetail.setId(IdWorker.createId());

        wzchPriorPurchaseDetail.setCreateTime(DateUtils.getNowDate());

        return wzchPriorPurchaseDetailMapper.insertWzchPriorPurchaseDetail(wzchPriorPurchaseDetail);
    }

    /**
     * 修改优先进场物资设备采购策划物资详情
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 结果
     */
    @Override
    public int updateWzchPriorPurchaseDetail(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {
        wzchPriorPurchaseDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchPriorPurchaseDetailMapper.updateWzchPriorPurchaseDetail(wzchPriorPurchaseDetail);
    }

    /**
     * 删除优先进场物资设备采购策划物资详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorPurchaseDetailByIds(String ids) {
        return wzchPriorPurchaseDetailMapper.deleteWzchPriorPurchaseDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优先进场物资设备采购策划物资详情信息
     *
     * @param id 优先进场物资设备采购策划物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorPurchaseDetailById(Long id) {
        return wzchPriorPurchaseDetailMapper.deleteWzchPriorPurchaseDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByPurchaseId(List<WzchPriorPurchaseDetailDTO> detailList, Long purchaseId) {
        // 先将之前数据都进行删除
        wzchPriorPurchaseDetailMapper.deleteByPurchaseId(purchaseId);
        if (CollectionUtils.isEmpty(detailList)) {
            return 0;
        }

        // 集合类型转化 设置id 设置purchaseId
        List<WzchPriorPurchaseDetail> insertOrUpdateData = detailList.stream().map(item -> {
            Long id = item.getId() == null ? IdWorker.createId() : item.getId();
            item.setId(id);
            item.setPriorPurchaseId(purchaseId);
            item.setValid("0");
            item.setDelFlag("0");
            EntityUtils.setCreateUpdateInfo(item);
            return (WzchPriorPurchaseDetail) item;

        }).collect(Collectors.toList());

        return wzchPriorPurchaseDetailMapper.insertOrUpdateBatch(insertOrUpdateData);
    }

    @Override
    public List<WzchPriorPurchaseDetailDTO> getMtlDetailList(WzchPriorPurchaseDetail detail) {
        List<WzchPriorPurchaseDetailDTO> mtlDetailList = wzchPriorPurchaseDetailMapper.getMtlDetailList(detail);
        List<String> codeList = mtlDetailList.stream().map(WzchPriorPurchaseDetailDTO::getMaterialCode).distinct().collect(Collectors.toList());


        if (CollectionUtils.isNotEmpty(codeList)) {
            // 根据物资编码拿到该项目下的数据
            List<WzchPurchaseSupplyDetailDTO> detailDTOS = wzchPriorPurchaseDetailMapper.selectPurchaseSource(codeList);
            // 处理物资的采购来源
            for (WzchPriorPurchaseDetailDTO dto : mtlDetailList) {
                String materialCode = dto.getMaterialCode();
                String materialStandard = dto.getMaterialStandard();
                StringBuilder source = new StringBuilder("");
                StringBuilder sourceName = new StringBuilder("");
                // 筛选出数据
                detailDTOS.stream().filter(item ->
                        StringUtils.isNotEmpty(item.getMaterialCode())
                                && StringUtils.isNotEmpty(item.getMaterialStandard())
                                && item.getMaterialCode().equals(materialCode)
                                && item.getMaterialStandard().equals(materialStandard)
                ).findFirst().ifPresent(purchaseSource -> {
                    BigDecimal localNum = purchaseSource.getLocalNum();
                    BigDecimal internalNum = purchaseSource.getInternalNum();
                    BigDecimal otherStaseNum = purchaseSource.getOtherStateNum();
                    if (localNum != null && localNum.compareTo(BigDecimal.ZERO) > 0) {
                        source.append("0");
                        sourceName.append("当地采购");
                    }
                    if (internalNum != null && internalNum.compareTo(BigDecimal.ZERO) > 0) {
                        source.append(StringUtils.isEmpty(source.toString()) ? "1" : ",1");
                        sourceName.append(StringUtils.isEmpty(sourceName.toString()) ? "国内采购" : ",国内采购");
                    }
                    if (otherStaseNum != null && otherStaseNum.compareTo(BigDecimal.ZERO) > 0) {
                        source.append(StringUtils.isEmpty(source.toString()) ? "2" : ",2");
                        sourceName.append(StringUtils.isEmpty(sourceName.toString()) ? "第三国采购" : ",第三国采购");
                    }

                });
                dto.setSource(source.toString());
                dto.setSourceName(sourceName.toString());
            }
        }

        return wzchCommonService.setWzchtMaterialInfo(mtlDetailList);
    }

    /**
     * 根据单据id删除数据
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPriorPurchaseIds(String ids) {
        return wzchPriorPurchaseDetailMapper.deleteByPriorPurchaseIds(Convert.toStrArray(ids));
    }
}
