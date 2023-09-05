package com.hhwy.pm.qqch.tax.qqchTaxCost.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.constant.PmConstant;
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
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxStageService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeNode;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
        //  taxInVO.setCostList(TreeUtil.build(TreeUtil.treeToListWithLevel(taxInVO.getCostList()),null));
        // 在查询其他收入
        taxCost.setDataType("2");
        taxInVO.setOtherList(bean.getCostList(taxCost));

        entity.setStageIdentity(reviewService.getStage());
        entity.setVersion(VersionUtil.getVersion(TN, taxCost.getVersion()));
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
        ArrayList<QqchTaxCost> qqchTaxCosts = CompileEntity.dealSaveDto(dto, allList);
        List<QqchTaxCostDetail> allDetails = bean.saveCostList(qqchTaxCosts);
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

        if (CollectionUtils.isEmpty(costList)) {
            costList = this.getInitData(qqchTaxIn);
        }
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
        // dealDetailList(costList);
        return costList;
    }

    /**
     * 获取初始化数据
     *
     * @param qqchTaxIn
     * @return
     */
    private List<QqchTaxCost> getInitData(QqchTaxCost qqchTaxIn) {
        List<QqchTaxCost> currencyChildren = this.getCurrencyChildren();
        List<QqchTaxCost> costList = new ArrayList<>();

        InputStream resourceAsStream = null;
        String dataType = qqchTaxIn.getDataType();


        switch (dataType) {
            case "1":
                resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_3_4_1.json");
                break;
            case "2":
                resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_3_4_2.json");
                break;
            case "3":
                resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_3_5.json");
                break;
            default:
        }


        String json = "";
        try {
            json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        costList = JSONObject.parseArray(json, QqchTaxCost.class);

        List<QqchTaxCost> allChildren = new ArrayList<>();
        costList.stream().filter(ite -> PmConstant.ONE.equals(ite.getLeaf())).forEach(i -> {
            for (QqchTaxCost currencyChild : currencyChildren) {
                QqchTaxCost qqchTaxCost = new QqchTaxCost();
                BeanUtils.copyProperties(currencyChild, qqchTaxCost);
                qqchTaxCost.setPid(i.getId());
                qqchTaxCost.setId(IdWorker.createId());
                qqchTaxCost.setLeaf("1");
                i.setLeaf("0");
                allChildren.add(qqchTaxCost);
            }
        });

        costList.addAll(allChildren);
        return costList;
    }


    private List<QqchTaxCost> getCurrencyChildren() {
        List<TaxInVO.CurrencyVO> currencyInfo = qqchTaxInService.getCurrencyInfo();
        List<QqchTaxCost> collect = currencyInfo.stream().map(i -> {
            QqchTaxCost qqchTaxIn = new QqchTaxCost();
            qqchTaxIn.setId(IdWorker.createId());
            qqchTaxIn.setCurrency(i.getCurrency());
            qqchTaxIn.setFeeName(i.getCurrencyName());
            qqchTaxIn.setRate(i.getRate());
            return qqchTaxIn;

        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public List<QqchTaxCostDetail> saveCostList(List<QqchTaxCost> list) {
        if (CollectionUtils.isEmpty(list)) return new ArrayList<>();
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
        entity.setVersion(VersionUtil.getVersion(TN, taxCost.getVersion()));
        entity.setDto(taxInVO);
        return entity;
    }

    @Override
    public void downTemp(HttpServletResponse response, QqchTaxCost params) throws IOException {

        EasyExcel.write(response.getOutputStream())
                // 这里放入动态头
                .head(this.getHeaders(params))
                .sheet("模板")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(this.getData(params));
    }

    @Override
    public List<QqchTaxCost> importData(MultipartFile file, Map<String, Object> params) throws IOException {
        String dataType = String.valueOf(params.get("dataType"));
        CommonAssert.notBlank(dataType,"dataType不能为空");

        List<QqchTaxCost> dataList = new ArrayList<>();

        final int[] i = {1};

        List<String> yearList = qqchTaxInService.getYearList();


        // 使用EasyExcel进行数据读取
        EasyExcel.read(file.getInputStream(), new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
                // 处理每一行数据
                if (i[0] != 1) {
                    dataList.add(getCost(rowData, yearList, dataType));
                }
                i[0]++;
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.println("Data reading completed.");
            }
        }).sheet(0).doRead();


        return dealDetailList(dataList);
    }

    private List<QqchTaxCost> dealDetailList(List<QqchTaxCost> dataList) {
        List<String> currencyNameList = dataList.stream().map(QqchTaxCost::getFeeName).distinct().collect(Collectors.toList());

        Map<String, String> currencyInfoByNames = CommonServiceUtil.getCurrencyCodesByNames(currencyNameList);
        if (currencyInfoByNames == null  || currencyInfoByNames.size() == 0) throw new RuntimeException("请检查币种是否书写错误");
        List<String> codes = new ArrayList<>(currencyInfoByNames.values());

        Map<String, BigDecimal> rateMap = CommonServiceUtil.getRateByCodes(codes);

        for (QqchTaxCost qqchTaxCost : dataList) {
            String feeName = qqchTaxCost.getFeeName();
            String code = currencyInfoByNames.get(feeName);
            if (StringUtils.isNotEmpty(code)) {
                BigDecimal rate = rateMap.get(code) == null ? BigDecimal.ONE : rateMap.get(code);
                qqchTaxCost.setCurrency(code);
                qqchTaxCost.setRate(rate);

                List<QqchTaxCostDetail> detailList = qqchTaxCost.getDetailList();
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchTaxCostDetail detail : detailList) {
                        detail.setRate(rate);
                        detail.setCurrency(code);
                        detail.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(detail.getInnerAmt(), rate));
                        detail.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(detail.getLocalAmt(), rate));
                        detail.setUsdReqAmt(CommonServiceUtil.getUsdAmt(detail.getReqAmt(), rate));

                        // 给每一个币种计算多个年份之和
                        qqchTaxCost.setInnerAmt(BigDecimalUtils.sum(qqchTaxCost.getInnerAmt(), detail.getInnerAmt()));
                        qqchTaxCost.setLocalAmt(BigDecimalUtils.sum(qqchTaxCost.getLocalAmt(), detail.getLocalAmt()));
                        qqchTaxCost.setReqAmt(BigDecimalUtils.sum(qqchTaxCost.getReqAmt(), detail.getReqAmt()));
                    }
                }

                qqchTaxCost.setUsdInnerAmt(CommonServiceUtil.getUsdAmt(qqchTaxCost.getInnerAmt(), rate));
                qqchTaxCost.setUsdLocalAmt(CommonServiceUtil.getUsdAmt(qqchTaxCost.getLocalAmt(), rate));
                qqchTaxCost.setUsdReqAmt(CommonServiceUtil.getUsdAmt(qqchTaxCost.getReqAmt(), rate));
            }
        }

        return this.toTree(dataList, ".");
    }


    private List<QqchTaxCost> toTree(List<QqchTaxCost> ts, String serStr) {
        ArrayList<QqchTaxCost> res = new ArrayList<>();

        HashMap<Integer, List<QqchTaxCost>> lengthMap = new HashMap<>();
        for (QqchTaxCost t : ts) {
            // 如果是属于TreeNode才继续进行
            if (!(t instanceof TreeNode)) throw new RuntimeException("请继承TreeNode");
            ((TreeNode<?>) t).setId(IdWorker.createId());
            // 序号
            String serNum = t.getSerNum();
            String[] split = serNum.split(".".equals(serStr) ? "\\." : serStr);
            // 
            List<QqchTaxCost> lenList = lengthMap.get(split.length);
            // 如果当前数据为空 就new一个  然后
            lenList = org.apache.commons.collections4.CollectionUtils.isEmpty(lenList) ? new ArrayList<>() : lenList;
            lenList.add(t);
            // 放入map 等会儿用
            lengthMap.put(split.length, lenList);
        }

        // 由大到小
        String finalSerStr = serStr;
        lengthMap.keySet().stream().sorted(Comparator.comparing(Integer::intValue).reversed()).forEach(length -> {
            List<QqchTaxCost> lengthList = lengthMap.get(length);
            if (length == 1) {
                res.addAll(lengthList);
            } else {
                for (QqchTaxCost t : lengthList) {
                    String serNum = t.getSerNum();
                    String parentSerNum = getStrBefore(serNum, finalSerStr);
                    ts.stream().filter(item -> parentSerNum.equals(item.getSerNum())).findFirst().ifPresent(parent -> {
                        List<QqchTaxCost> children = parent.getChildren();
                        children = CollectionUtils.isEmpty(children) ? new ArrayList<>() : children;
                        t.setPid(parent.getId());
                        parent.setUsdReqAmt(BigDecimalUtils.sum(parent.getUsdReqAmt(), t.getUsdReqAmt()));
                        parent.setUsdInnerAmt(BigDecimalUtils.sum(parent.getUsdInnerAmt(), t.getUsdInnerAmt()));
                        parent.setUsdLocalAmt(BigDecimalUtils.sum(parent.getUsdLocalAmt(), t.getUsdLocalAmt()));

                        parent.setReqAmt(parent.getUsdReqAmt());
                        parent.setInnerAmt(parent.getUsdInnerAmt());
                        parent.setLocalAmt(parent.getUsdLocalAmt());
                        // 计算每一年的数据
                        List<QqchTaxCostDetail> detailList = parent.getDetailList();
                        for (QqchTaxCostDetail detail : detailList) {
                            String year = detail.getYear();
                            List<QqchTaxCostDetail> details = t.getDetailList();
                            details.stream().filter(ite -> year.equals(ite.getYear())).forEach(item -> {
                                detail.setUsdInnerAmt(BigDecimalUtils.sum(detail.getUsdInnerAmt(), item.getUsdInnerAmt()));
                                detail.setUsdLocalAmt(BigDecimalUtils.sum(detail.getUsdLocalAmt(), item.getUsdLocalAmt()));
                                detail.setUsdReqAmt(BigDecimalUtils.sum(detail.getUsdReqAmt(), item.getUsdReqAmt()));

                                detail.setInnerAmt(detail.getUsdInnerAmt());
                                detail.setLocalAmt(detail.getUsdLocalAmt());
                                detail.setReqAmt(detail.getUsdReqAmt());
                            });
                        }
                        children.add(t);
                        parent.setChildren(children);
                    });
                }
            }
        });

        return res;

    }


    private String getStrBefore(String strOrig, String str) {
        int lastIndex = strOrig.lastIndexOf(str);
        return strOrig.substring(0, lastIndex);

    }

    private QqchTaxCost getCost(Map<Integer, String> rowData, List<String> yearList, String dataType) {
        Set<Integer> integers = rowData.keySet();
        HashMap<Integer, QqchTaxCostDetail> detailHashMap = new HashMap<>(3);


        QqchTaxCost qqchTaxCost = new QqchTaxCost();


        for (Integer col : integers) {

            if (!"3".equals(dataType)) {
                switch (col) {

                    case 0:
                        qqchTaxCost.setSerNum(rowData.get(col));
                        break;
                    case 1:
                        // 费用名称
                        qqchTaxCost.setFeeName(rowData.get(col));
                        break;
                    case 2:
                        // 内账   
                        qqchTaxCost.setInnerAmt(getDecimalVal(rowData.get(col)));
                        break;
                    case 3:
                        // 符合账
                        qqchTaxCost.setReqAmt(getDecimalVal(rowData.get(col)));
                        break;
                    case 4:
                        // 属地账
                        qqchTaxCost.setLocalAmt(getDecimalVal(rowData.get(col)));
                        break;
                    default:
                }

                // 大于四列后 每三个成一组
                if (col > 4) {
                    int idx = (col - 5) / 3;
                    if (detailHashMap.get(idx) == null) {
                        QqchTaxCostDetail detail = new QqchTaxCostDetail();
                        String year = yearList.get(idx);
                        detail.setYear(year);
                        detail.setInnerAmt(getDecimalVal(rowData.get(col)));
                        detail.setReqAmt(getDecimalVal(rowData.get(col + 1)));
                        detail.setLocalAmt(getDecimalVal(rowData.get(col + 2)));
                        detailHashMap.put(idx, detail);
                    }
                }

            } else {

                switch (col) {

                    case 0:
                        qqchTaxCost.setSerNum(rowData.get(col));
                        break;
                    case 1:
                        // 费用名称
                        qqchTaxCost.setFeeName(rowData.get(col));
                        break;
                    case 2:
                        // 内账   
                        qqchTaxCost.setInnerAmt(getDecimalVal(rowData.get(col)));
                        break;
                    case 3:
                        // 属地账
                        qqchTaxCost.setLocalAmt(getDecimalVal(rowData.get(col)));
                        break;
                    default:
                }

                // 大于三列后 每二个成一组
                if (col > 3) {
                    int idx = (col - 4) / 2;
                    if (detailHashMap.get(idx) == null) {
                        QqchTaxCostDetail detail = new QqchTaxCostDetail();
                        String year = yearList.get(idx);
                        detail.setYear(year);
                        detail.setInnerAmt(getDecimalVal(rowData.get(col)));
                        detail.setLocalAmt(getDecimalVal(rowData.get(col + 1)));
                        detailHashMap.put(idx, detail);
                    }
                }
            }


        }
        List<QqchTaxCostDetail> values = new ArrayList<>(detailHashMap.values());

        qqchTaxCost.setDetailList(values);

        return qqchTaxCost;
    }


    private BigDecimal getDecimalVal(String s) {

        try {
            return StringUtils.isEmpty(s) ? BigDecimal.ZERO : new BigDecimal(s);
        } catch (Exception e) {
            throw new RuntimeException("金额必须为数字");
        }

    }

    private List getData(QqchTaxCost params) {
        List<QqchTaxCost> initDatas = this.getInitData(params);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();


        // 生成序号
        for (QqchTaxCost initDatum : initDatas) {
            if (!"1".equals(initDatum.getLeaf())) continue;
            initDatas.stream().filter(ite -> ite.getId().equals(initDatum.getPid())).findFirst().ifPresent(i -> {
                String serNum = i.getSerNum();
                Integer integer = stringIntegerHashMap.get(serNum);
                if (integer == null) {
                    stringIntegerHashMap.put(serNum, integer = 1);

                } else {
                    stringIntegerHashMap.put(serNum, ++integer);
                }
                initDatum.setSerNum(serNum + "." + integer);
            });
        }
        initDatas = initDatas.stream().sorted(Comparator.comparing(QqchTaxCost::getSerNum)).collect(Collectors.toList());

        List<List<String>> res = new ArrayList<>();
        for (QqchTaxCost initDatum : initDatas) {
            List<String> strings = new ArrayList<>();
            strings.add(initDatum.getSerNum());
            strings.add(initDatum.getFeeName());
            res.add(strings);
        }

        return res;
    }

    private List<List<String>> getHeaders(QqchTaxCost params) {
        List<List<String>> list = new ArrayList<>();
        List<String> xh = new ArrayList<>();
        xh.add("序号");
        list.add(xh);


        List<String> hMaeCode = new ArrayList<>();
        hMaeCode.add("费用名称");
        list.add(hMaeCode);


        List<String> hjd0 = new ArrayList<>();
        hjd0.add("合计");
        hjd0.add("内账成本");
        list.add(hjd0);

        String dataType = params.getDataType();
        if (!"3".equals(dataType)) {
            List<String> hjd1 = new ArrayList<>();
            hjd1.add("合计");
            hjd1.add("符合属地账要求成本");
            list.add(hjd1);
        }

        List<String> hjd2 = new ArrayList<>();
        hjd2.add("合计");
        hjd2.add("属地账策划成本");
        list.add(hjd2);

        List<String> yearList = qqchTaxInService.getYearList();

        for (String year : yearList) {

            List<String> nz = new ArrayList<>();
            nz.add(year);
            nz.add("内账成本");
            list.add(nz);

            if (!"3".equals(dataType)) {
                List<String> fh = new ArrayList<>();
                fh.add(year);
                fh.add("符合属地账要求成本");
                list.add(fh);
            }

            List<String> sd = new ArrayList<>();
            sd.add(year);
            sd.add("属地账策划成本");
            list.add(sd);

        }

        return list;
    }


    public static void main(String[] args) {

        System.out.println((7 - 4) / 3);
    }


}
