package com.hhwy.pm.qqch.tax.qqchTaxCost.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCost.mapper.QqchTaxCostMapper;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.vo.TaxCostVO;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxStageService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
@Service
public class QqchTaxCostServiceImpl implements IQqchTaxCostService {

    @Resource
    private QqchTaxCostMapper qqchTaxCostMapper;

    @Resource
    private IQqchTaxInService qqchTaxInService;


    @Resource
    private IQqchTaxCostDetailService detailService;
    @Resource
    private IQqchReviewService reviewService;

    @Resource
    private IQqchTaxStageService qqchTaxStageService;
    private final static String TN = "qqch_tax_cost";

    public QqchTaxCost getQqchTaxCost(QqchTaxCost qqchTaxCost) {
        return qqchTaxCostMapper.getQqchTaxCost(qqchTaxCost);
    }

    public List<QqchTaxCost> getQqchTaxCostList(QqchTaxCost qqchTaxCost) {
        return qqchTaxCostMapper.getQqchTaxCostList(qqchTaxCost);
    }

    @Transactional
    public int insertQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setId(IdWorker.createId());
        qqchTaxCost.setCreateUser(SecurityUtils.getUserName());
        qqchTaxCost.setCreateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.insertQqchTaxCost(qqchTaxCost);
    }

    @Transactional
    public int insertQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList) {
        for (QqchTaxCost qqchTaxCost : qqchTaxCostList) {
            qqchTaxCost.setId(IdWorker.createId());
            qqchTaxCost.setCreateUser(SecurityUtils.getUserName());
            qqchTaxCost.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostMapper.insertQqchTaxCostList(qqchTaxCostList);
    }

    @Transactional
    public int updateQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.updateQqchTaxCost(qqchTaxCost);
    }

    @Transactional
    public int updateQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList) {
        for (QqchTaxCost qqchTaxCost : qqchTaxCostList) {
            qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostMapper.updateQqchTaxCostList(qqchTaxCostList);
    }

    @Transactional
    public int deleteQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.deleteQqchTaxCost(qqchTaxCost);
    }

    @Transactional
    public int deleteQqchTaxCostByPks(List<Long> qqchTaxCostPkList) {
        return qqchTaxCostMapper.deleteQqchTaxCostByPks(qqchTaxCostPkList);
    }

    @Override
    public CompileEntity<TaxCostVO> getList(QqchTaxCost taxCost) {

        IQqchTaxCostService bean = SpringUtils.getBean(IQqchTaxCostService.class);

        CompileEntity entity = new CompileEntity();

        TaxCostVO taxInVO = new TaxCostVO();
        taxInVO.setYearList(qqchTaxInService.getYearList());
        taxInVO.setCurrencyVOList(qqchTaxInService.getCurrencyInfo());
        // 先查询主收入
        taxCost.setDataType("1");
        taxInVO.setCostList(bean.getCostList(taxCost));
        // 在查询其他收入
        taxCost.setDataType("2");
        taxInVO.setOtherList(bean.getCostList(taxCost));

        entity.setStageIdentity(reviewService.getStage());
        entity.setVersion(VersionUtil.getVersion(TN,taxCost.getVersion()));
        entity.setDto(taxInVO);
        return entity;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CompileEntity<TaxCostVO> dto) {

        // 获取到bean 保证切面能够生效
        IQqchTaxCostService bean = SpringUtils.getBean(IQqchTaxCostService.class);


        ArrayList<QqchTaxCost> allList = new ArrayList<>();


        // 生成记录id // 分期会用到
        Long recordId = IdWorker.createId();

        // 当税费不为空的时候才能进行插入数据
        if (!CollectionUtils.isEmpty(dto.getDto().getTaxList())) {
            // 在分期中插入数据
            qqchTaxStageService.saveStage(recordId, "2");
        }

        if (!CollectionUtils.isEmpty(dto.getDto().getCostList())) {
            // 处理主要
            List<QqchTaxCost> qqchTaxCosts = CompileEntity.dealSaveDto(dto, dto.getDto().getCostList());
            for (QqchTaxCost cost : qqchTaxCosts) {
                cost.setDataType("1");

                BigDecimal rate = cost.getRate();
                // 计算美元价格
                cost.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(cost.getInnerAmt(), rate));
                cost.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(cost.getLocalAmt(), rate));
                cost.setUsdReqAmt(CommonServiceUtil.getUsdAmt(cost.getReqAmt(), rate));
                cost.setRecordId(recordId);
                allList.add(cost);
            }
        }


        if (!CollectionUtils.isEmpty(dto.getDto().getOtherList())) {
            // 处理其他
            List<QqchTaxCost> otherList = CompileEntity.dealSaveDto(dto, dto.getDto().getOtherList());
            for (QqchTaxCost cost : otherList) {
                cost.setDataType("2");

                BigDecimal rate = cost.getRate();
                // 计算美元价格
                cost.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(cost.getInnerAmt(), rate));
                cost.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(cost.getLocalAmt(), rate));
                cost.setUsdReqAmt(CommonServiceUtil.getUsdAmt(cost.getReqAmt(), rate));
                cost.setRecordId(recordId);
                allList.add(cost);
            }
        }


        // 处理税费
        if (!CollectionUtils.isEmpty(dto.getDto().getTaxList())) {
            List<QqchTaxCost> taxCostList = CompileEntity.dealSaveDto(dto, dto.getDto().getTaxList());
            for (QqchTaxCost cost : taxCostList) {
                cost.setDataType("3");

                BigDecimal rate = cost.getRate();
                // 计算美元价格
                cost.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(cost.getInnerAmt(), rate));
                cost.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(cost.getLocalAmt(), rate));
                cost.setUsdReqAmt(CommonServiceUtil.getUsdAmt(cost.getReqAmt(), rate));
                cost.setRecordId(recordId);
                allList.add(cost);
            }
        }


        // 费用数据入库
        List<QqchTaxCostDetail> allDetails = bean.saveCostList(allList);
        // 新增年份数据
        this.detailService.save(CompileEntity.dealSaveDto(dto, allDetails));


    }


    /**
     * 获取收入信息
     *
     * @param qqchTaxIn
     * @return
     */
    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchTaxCost> getCostList(QqchTaxCost qqchTaxIn) {
        List<QqchTaxCost> costList = this.qqchTaxCostMapper.getQqchTaxCostList(qqchTaxIn);
        if (CollectionUtils.isEmpty(costList)) return new ArrayList<>();
        List<Long> collect = costList.stream().map(QqchTaxCost::getId).collect(Collectors.toList());

        // 查询详情
        QqchTaxCostDetail where = new QqchTaxCostDetail();
        where.setMasterIdList(collect);
        List<QqchTaxCostDetail> qqchTaxInDetailList = this.detailService.getQqchTaxCostDetailList(where);

        // 分组
        Map<Long, List<QqchTaxCostDetail>> idMap = qqchTaxInDetailList.stream().collect(Collectors.groupingBy(QqchTaxCostDetail::getMasterId));

        // 挂到主数据上面
        for (QqchTaxCost taxIn : costList) {
            taxIn.setDetailList(idMap.get(taxIn.getId()));
        }
        return costList;
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public List<QqchTaxCostDetail> saveCostList(List<QqchTaxCost> list) {
        List<QqchTaxCostDetail> allDetails = new ArrayList<>();
        for (QqchTaxCost item : list) {
            List<QqchTaxCostDetail> detailList = item.getDetailList();
            // 不为空才循环
            if (!CollectionUtils.isEmpty(detailList)) {
                for (QqchTaxCostDetail detail : detailList) {
                    detail.setId(IdWorker.createId());
                    detail.setMasterId(item.getId());
                    detail.setDataType(item.getDataType());
                    detail.setVersion(item.getVersion());
                    detail.setValid(item.getValid());
                    BigDecimal rate = detail.getRate();

                    // 计算美元价格
                    detail.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(detail.getInnerAmt(), rate));
                    detail.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(detail.getLocalAmt(), rate));
                    detail.setUsdReqAmt(CommonServiceUtil.getUsdAmt(detail.getReqAmt(), rate));
                }
                allDetails.addAll(detailList);
            }

        }

        EntityUtils.setCreateUpdateInfo(list);
        EntityUtils.setCreateUpdateInfo(allDetails);
        // 新增数据
        this.qqchTaxCostMapper.insertQqchTaxCostList(list);
        return allDetails;
    }

    @Override
    public CompileEntity<TaxCostVO> taxList(QqchTaxCost taxCost) {
        IQqchTaxCostService bean = SpringUtils.getBean(IQqchTaxCostService.class);

        CompileEntity entity = new CompileEntity();

        TaxCostVO taxInVO = new TaxCostVO();
        taxInVO.setYearList(qqchTaxInService.getYearList());
        taxInVO.setCurrencyVOList(qqchTaxInService.getCurrencyInfo());
  
        // 在查询其他收入
        taxCost.setDataType("3");
        taxInVO.setTaxList(bean.getCostList(taxCost));

        entity.setStageIdentity(reviewService.getStage());
        entity.setVersion(VersionUtil.getVersion(TN,taxCost.getVersion()));
        entity.setDto(taxInVO);
        return entity;
    }


}
