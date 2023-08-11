package com.hhwy.pm.qqch.tax.qqchTaxIn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark
 */
@Service
public class QqchTaxInServiceImpl implements IQqchTaxInService {


    private final static String TN = "qqch_tax_in";

    @Autowired
    private IXmslContractPayinfoService contractPayinfoService;

    @Autowired
    private QqchTaxInMapper qqchTaxInMapper;

    @Autowired
    private IQqchTaxInDetailService detailService;


    public QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxIn(qqchTaxIn);
    }

    public List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setId(IdWorker.createId());
        qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
        qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.insertQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
        for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
            qqchTaxIn.setId(IdWorker.createId());
            qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
            qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInMapper.insertQqchTaxInList(qqchTaxInList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.updateQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
        for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
            qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxInMapper.updateQqchTaxInList(qqchTaxInList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.deleteQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxInByPks(List<Long> qqchTaxInPkList) {
        return qqchTaxInMapper.deleteQqchTaxInByPks(qqchTaxInPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileEntity<TaxInVO> list(QqchTaxIn qqchTaxInParam) {
        IQqchTaxInService bean = SpringUtils.getBean(IQqchTaxInService.class);

        CompileEntity entity = new CompileEntity();

        TaxInVO taxInVO = new TaxInVO();
        taxInVO.setYearList(this.getYearList());
        taxInVO.setCurrencyVOList(this.getCurrencyInfo());
        // 先查询主收入
        qqchTaxInParam.setDataType("1");
        taxInVO.setInList(bean.getInList(qqchTaxInParam));
        // 在查询其他收入
        qqchTaxInParam.setDataType("2");
        taxInVO.setOtherList(bean.getInList(qqchTaxInParam));

        entity.setModuleIdentity(qqchTaxInParam.getModuleIdentity());
        entity.setVersion(qqchTaxInParam.getVersion());
        entity.setDto(taxInVO);
        return entity;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CompileEntity<TaxInVO> qqchTaxInParam) {

        IQqchTaxInService bean = SpringUtils.getBean(IQqchTaxInService.class);

        // 保存主要
        List<QqchTaxIn> qqchTaxIns = CompileEntity.dealSaveDto(qqchTaxInParam.getVersion(), qqchTaxInParam.getSubmitFlag(), qqchTaxInParam.getDto().getInList());
        for (QqchTaxIn qqchTaxIn : qqchTaxIns) {
            qqchTaxIn.setDataType("1");
        }
        List<QqchTaxInDetail> masterDetailList = bean.saveInList(qqchTaxIns);

        // 保存其他
        List<QqchTaxIn> otherInList = CompileEntity.dealSaveDto(qqchTaxInParam.getVersion(), qqchTaxInParam.getSubmitFlag(), qqchTaxInParam.getDto().getOtherList());
        for (QqchTaxIn qqchTaxIn : otherInList) {
            qqchTaxIn.setDataType("2");
        }
        List<QqchTaxInDetail> otherDetailList = bean.saveInList(otherInList);


        // 保存年份数据
        ArrayList<QqchTaxInDetail> allDetails = new ArrayList<>();
        allDetails.addAll(masterDetailList);
        allDetails.addAll(otherDetailList);
        // 新增年份数据
        this.detailService.save(CompileEntity.dealSaveDto(qqchTaxInParam.getVersion(), qqchTaxInParam.getSubmitFlag(), allDetails));

    }


    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Override
    public List<QqchTaxInDetail> saveInList(List<QqchTaxIn> list) {
        List<QqchTaxInDetail> allDetails = new ArrayList<>();

        for (QqchTaxIn item : list) {
            List<QqchTaxInDetail> detailList = item.getDetailList();
            // 不为空才循环
            if (!CollectionUtils.isEmpty(detailList)) {
                for (QqchTaxInDetail qqchTaxInDetail : detailList) {
                    qqchTaxInDetail.setId(IdWorker.createId());
                    qqchTaxInDetail.setMasterId(item.getId());
                    qqchTaxInDetail.setDataType(item.getDataType());
                    qqchTaxInDetail.setVersion(item.getVersion());
                    qqchTaxInDetail.setValid(item.getValid());
                }
                allDetails.addAll(detailList);
            }

        }

        EntityUtils.setCreateUpdateInfo(list);
        EntityUtils.setCreateUpdateInfo(allDetails);
        // 新增数据
        this.qqchTaxInMapper.insertQqchTaxInList(list);

        return allDetails;
    }


    /**
     * 获取币种信息
     *
     * @return
     */
    private List<TaxInVO.CurrencyVO> getCurrencyInfo() {

        List<XmslContractPayinfo> payInfo = contractPayinfoService.getPayInfo();
        List<TaxInVO.CurrencyVO> res = payInfo.stream().map(item -> {
            TaxInVO.CurrencyVO currencyVO = new TaxInVO.CurrencyVO();


            return currencyVO;

        }).collect(Collectors.toList());
        // 假数据
        if (CollectionUtils.isEmpty(res)) {
            res = new ArrayList<>();
            res.add(new TaxInVO.CurrencyVO("RMB", "人民币", new BigDecimal("6.9")));
            res.add(new TaxInVO.CurrencyVO("USD", "美元", new BigDecimal("1")));
        }
        return res;
    }

    /**
     * 获取年份信息
     *
     * @return
     */
    private List<String> getYearList() {
        ArrayList<String> res = new ArrayList<>();
        res.add("2023");
        res.add("2024");
        res.add("2025");
        res.add("2026");
        return res;
    }


    /**
     * 获取收入信息
     *
     * @param qqchTaxIn
     * @return
     */
    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchTaxIn> getInList(QqchTaxIn qqchTaxIn) {
        List<QqchTaxIn> qqchTaxInList = this.qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
        if (CollectionUtils.isEmpty(qqchTaxInList)) return new ArrayList<>();
        List<Long> collect = qqchTaxInList.stream().map(QqchTaxIn::getId).collect(Collectors.toList());

        // 查询详情
        QqchTaxInDetail where = new QqchTaxInDetail();
        where.setMasterIdList(collect);
        List<QqchTaxInDetail> qqchTaxInDetailList = this.detailService.getQqchTaxInDetailList(where);

        // 分组
        Map<Long, List<QqchTaxInDetail>> idMap = qqchTaxInDetailList.stream().collect(Collectors.groupingBy(QqchTaxInDetail::getMasterId));

        // 挂到主数据上面
        for (QqchTaxIn taxIn : qqchTaxInList) {
            taxIn.setDetailList(idMap.get(taxIn.getId()));
        }
        return qqchTaxInList;
    }

}




