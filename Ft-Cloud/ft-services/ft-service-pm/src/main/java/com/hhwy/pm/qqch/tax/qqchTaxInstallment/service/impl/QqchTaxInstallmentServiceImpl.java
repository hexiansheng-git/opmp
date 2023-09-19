package com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCost.mapper.QqchTaxCostDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxInstallment;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.mapper.QqchTaxInstallmentMapper;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.mapper.QqchTaxStageMapper;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxInstallmentService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.vo.InstallmentVO;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-14 00:02:28
 * @remark
 */
@Service
public class QqchTaxInstallmentServiceImpl implements IQqchTaxInstallmentService {

    @Resource
    private QqchTaxInstallmentMapper qqchTaxInstallmentMapper;

    @Resource
    private IQqchReviewService reviewServicee;

    @Resource
    private QqchTaxInDetailMapper inDetailMapper;

    @Resource
    private QqchTaxCostDetailMapper costDetailMapper;

    @Resource
    private  IQqchModuleConfirmCaseService moduleConfirmCaseService;

    @Resource
    private QqchTaxStageMapper qqchTaxStageMapper;

    private static final String TN = "qqch_tax_installment";


    public QqchTaxInstallment getQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        return qqchTaxInstallmentMapper.getQqchTaxInstallment(qqchTaxInstallment);
    }

    public List<QqchTaxInstallment> getQqchTaxInstallmentList(QqchTaxInstallment qqchTaxInstallment) {
        return qqchTaxInstallmentMapper.getQqchTaxInstallmentList(qqchTaxInstallment);
    }

    @Transactional
    public int insertQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setId(IdWorker.createId());
        qqchTaxInstallment.setCreateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.insertQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int insertQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList) {
        for (QqchTaxInstallment qqchTaxInstallment : qqchTaxInstallmentList) {
            qqchTaxInstallment.setId(IdWorker.createId());
            qqchTaxInstallment.setCreateUser(SecurityUtils.getUserName());
            qqchTaxInstallment.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInstallmentMapper.insertQqchTaxInstallmentList(qqchTaxInstallmentList);
    }

    @Transactional
    public int updateQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.updateQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int updateQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList) {
        for (QqchTaxInstallment qqchTaxInstallment : qqchTaxInstallmentList) {
            qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxInstallmentMapper.updateQqchTaxInstallmentList(qqchTaxInstallmentList);
    }

    @Transactional
    public int deleteQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.deleteQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int deleteQqchTaxInstallmentByPks(List<Long> qqchTaxInstallmentPkList) {
        return qqchTaxInstallmentMapper.deleteQqchTaxInstallmentByPks(qqchTaxInstallmentPkList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(QqchTaxInstallment dto) {
        moduleConfirmCaseService.addConfirmRecord(dto.getModuleIdentity(),dto.getStageIdentity());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InstallmentVO refresh(QqchTaxInstallment params) {

        List<Long> longs = qqchTaxStageMapper.selectNewestRecordId();
        InstallmentVO installmentVO = new InstallmentVO();
        if (CollectionUtils.isEmpty(longs)) return installmentVO;
        // 查询到最新的数据Id
        Long inRecordId = longs.get(0);
        Long costRecordId = longs.size() == 2 ? longs.get(1) : null;
        installmentVO.setInRecordId(inRecordId);
        installmentVO.setCostRecordId(costRecordId);
        params.setCostRecordId(costRecordId);
        params.setInRecordId(inRecordId);

        ArrayList<InstallmentVO.ListVO> list = this.getList(params);
        installmentVO.setList(list);

        QqchTaxInstallment installment = new QqchTaxInstallment();
        installment.setId(IdWorker.createId());
        installment.setInRecordId(inRecordId);
        installment.setCostRecordId(costRecordId);
        EntityUtils.setCreateUpdateInfo(installment);
        this.qqchTaxInstallmentMapper.insertQqchTaxInstallment(installment);
        return installmentVO;
    }


    @Override
    public ArrayList<InstallmentVO.ListVO> getList(QqchTaxInstallment params) {
        String year = params.getYear();

        ArrayList<InstallmentVO.ListVO> resList = new ArrayList<>();

        Long inRecordId = params.getInRecordId();
        Long costRecordId = params.getCostRecordId();


        // 主收入
        InstallmentVO.ListVO mainInListVO = this.getInList(inRecordId, year, "1");
        // 其他收入
        InstallmentVO.ListVO otherListVO = this.getInList(inRecordId, year, "2");
        // 获取收入合计
        InstallmentVO.ListVO totalIn = this.getTotal("收入合计", Collections.singletonList(mainInListVO), Collections.singletonList(otherListVO));
        resList.add(totalIn);
        resList.add(mainInListVO);
        resList.add(otherListVO);

        //  成本
        List<InstallmentVO.ListVO> outList = this.getOutList(costRecordId, year, "1");
        List<InstallmentVO.ListVO> otherOutList = this.getOutList(costRecordId, year, "2");
        // 总成本
        InstallmentVO.ListVO totalOut = this.getTotal("成本合计", outList, otherOutList);

        resList.add(totalOut);
        resList.addAll(CollectionUtils.isEmpty(outList) ? new ArrayList<>() : outList);
        resList.addAll(CollectionUtils.isEmpty(otherOutList) ? new ArrayList<>() : otherOutList);

        // 利息
        InstallmentVO.ListVO profitList = this.getProfitList(totalIn, totalOut);
        resList.add(profitList);

        return resList;
    }

    @Override
    public InstallmentVO list(QqchTaxInstallment params) {
        List<QqchTaxInstallment> qqchTaxInstallmentList = this.getQqchTaxInstallmentList(params);
        qqchTaxInstallmentList = qqchTaxInstallmentList.stream().sorted(Comparator.comparing(QqchTaxInstallment::getCreateTime).reversed()).collect(Collectors.toList());
        InstallmentVO installmentVO = new InstallmentVO();

        if (!CollectionUtils.isEmpty(qqchTaxInstallmentList)) {
            QqchTaxInstallment installment = qqchTaxInstallmentList.get(0);
            Long costRecordId = installment.getCostRecordId();
            Long inRecordId = installment.getInRecordId();
            installmentVO.setInRecordId(inRecordId);
            installmentVO.setCostRecordId(costRecordId);
            params.setCostRecordId(costRecordId);
            params.setInRecordId(inRecordId);
            ArrayList<InstallmentVO.ListVO> list = this.getList(params);
            installmentVO.setList(list);
        }


        installmentVO.setStageIdentity(reviewServicee.getStage());

        return installmentVO;
    }

    private InstallmentVO.ListVO getProfitList(InstallmentVO.ListVO totalIn, InstallmentVO.ListVO totalOut) {
        InstallmentVO.ListVO listVO = new InstallmentVO.ListVO();
        listVO.setDigest("利润总额");
        BigDecimal amt = totalIn.getAmt();
        BigDecimal sum = BigDecimalUtils.sum(totalOut.getLocalAmt(), totalOut.getReqAmt(), totalOut.getInnerAmt());
        listVO.setAmt(BigDecimalUtils.sum(amt, sum));
        return listVO;
    }

    private InstallmentVO.ListVO getTotal(String d, List<InstallmentVO.ListVO> outList, List<InstallmentVO.ListVO> otherOutList) {
        InstallmentVO.ListVO listVO = new InstallmentVO.ListVO();
        listVO.setDigest(d);

        // 内账成本
        BigDecimal inner = null;
        BigDecimal req = null;
        BigDecimal local = null;


        BigDecimal in = null;

        if (!CollectionUtils.isEmpty(outList)) {
            for (InstallmentVO.ListVO vo : outList) {
                inner = BigDecimalUtils.sum(vo.getInnerAmt(), inner);
                req = BigDecimalUtils.sum(vo.getReqAmt(), req);
                local = BigDecimalUtils.sum(vo.getLocalAmt(), local);
                in = BigDecimalUtils.sum(vo.getAmt(), in);
            }
        }


        if (!CollectionUtils.isEmpty(otherOutList)) {
            for (InstallmentVO.ListVO vo : otherOutList) {
                inner = BigDecimalUtils.sum(vo.getInnerAmt(), inner);
                req = BigDecimalUtils.sum(vo.getReqAmt(), req);
                local = BigDecimalUtils.sum(vo.getLocalAmt(), local);
            }
        }


//        内账成本差异 = 内账成本 - 符合属地账要求成本
//        属地账成本 = 符合属地账要求成本 + 属地账策划成本

        listVO.setInnerAmt(inner);
        listVO.setReqAmt(req);
        listVO.setLocalAmt(local);
        // listVO.setAmt(in);

        listVO.setDiffAmt(BigDecimalUtils.subtract(inner, req));
        listVO.setLocAmt(BigDecimalUtils.sum(local, req));
        return listVO;
    }

    private InstallmentVO.ListVO getTotalIn(InstallmentVO.ListVO mainInListVO, InstallmentVO.ListVO otherListVO) {

        // TODO
        return null;
    }


    /**
     * 获取成本
     *
     * @param recordId
     * @param year
     * @return
     */
    private List<InstallmentVO.ListVO> getOutList(Long recordId, String year, String type) {

        QqchTaxCostDetail costWhere = new QqchTaxCostDetail();
        costWhere.setRecordId(recordId);
        costWhere.setYear(year);
        costWhere.setDataType(type);
        List<QqchTaxCostDetail> costNameList = costDetailMapper.getCostName(costWhere);
        List<QqchTaxCostDetail> amtList = costDetailMapper.getAmtByGroup(costWhere);
        if (!CollectionUtils.isEmpty(amtList) && !CollectionUtils.isEmpty(costNameList)) {
            Map<Long, List<QqchTaxCostDetail>> masterIdMap = amtList.stream().collect(Collectors.groupingBy(QqchTaxCostDetail::getMasterId));
            // 将年份数据挂到费用上
            ArrayList<InstallmentVO.ListVO> listVOArrayList = new ArrayList<>();
            for (QqchTaxCostDetail detail : costNameList) {
                InstallmentVO.ListVO listVO = new InstallmentVO.ListVO();
                listVO.setId(detail.getId());
                listVO.setPid(detail.getPid());
                listVO.setDigest(detail.getCurrency());
                List<QqchTaxCostDetail> detailList = masterIdMap.get(detail.getId());
                listVO.setLocalAmt(detail.getLocalAmt());
                listVO.setReqAmt(detail.getReqAmt());
                listVO.setInnerAmt(detail.getInnerAmt());
                listVO.setLevel(detail.getLevel());

                listVO.setLocAmt(BigDecimalUtils.sum(listVO.getLocalAmt(), listVO.getReqAmt()));
                listVO.setDiffAmt(BigDecimalUtils.subtract(listVO.getInnerAmt(), listVO.getReqAmt()));

                // 子集也转结果集
                if (!CollectionUtils.isEmpty(detailList)) {
                    List<InstallmentVO.ListVO> collect = detailList.stream().map(item -> {
                        InstallmentVO.ListVO detailListVo = new InstallmentVO.ListVO();
                        detailListVo.setLocalAmt(item.getLocalAmt());
                        detailListVo.setReqAmt(item.getReqAmt());
                        detailListVo.setInnerAmt(item.getInnerAmt());


                        detailListVo.setLocAmt(BigDecimalUtils.sum(detailListVo.getLocalAmt(), detailListVo.getReqAmt()));
                        detailListVo.setDiffAmt(BigDecimalUtils.sum(detailListVo.getInnerAmt(), detailListVo.getReqAmt()));
                        return detailListVo;
                    }).collect(Collectors.toList());
                    listVO.setChildren(collect);
                }

                listVOArrayList.add(listVO);
            }
            // 将数据转成树并计算
            List<InstallmentVO.ListVO> treeRes = build(listVOArrayList, null);
            // 转为结果

            return treeRes;
        }
        return null;
    }

    public List<InstallmentVO.ListVO> build(List<InstallmentVO.ListVO> tree, Long pid) {
        // 这里一定要先将等级排序
        List<InstallmentVO.ListVO> treeNodes = tree.stream().sorted(Comparator.comparing(InstallmentVO.ListVO::getLevel).reversed()).collect(Collectors.toList());
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        treeNodes.forEach(treeVO -> {
            List<InstallmentVO.ListVO> collect = treeNodes.stream().filter((item) -> treeVO.getId().equals(item.getPid())).collect(Collectors.toList());
            for (InstallmentVO.ListVO listVO : collect) {
                treeVO.setLocalAmt(BigDecimalUtils.sum(listVO.getLocalAmt(), treeVO.getLocalAmt()));
                treeVO.setReqAmt(BigDecimalUtils.sum(listVO.getReqAmt(), treeVO.getReqAmt()));
                treeVO.setInnerAmt(BigDecimalUtils.sum(listVO.getInnerAmt(), treeVO.getInnerAmt()));
                treeVO.setLocAmt(BigDecimalUtils.sum(treeVO.getLocalAmt(), treeVO.getReqAmt()));
                treeVO.setDiffAmt(BigDecimalUtils.subtract(treeVO.getInnerAmt(), treeVO.getReqAmt()));
            }
            if (!CollectionUtils.isEmpty(treeVO.getChildren())) {
                treeVO.setOChildren(treeVO.getChildren());
            }
            treeVO.setChildren(collect);
        });
        List<InstallmentVO.ListVO> collect;
        if (pid == null) {
            collect = treeNodes.stream().filter((item) -> item.getPid() == null)
                    .collect(Collectors.toList());
        } else {
            collect = treeNodes.stream().filter((item) -> pid.equals(item.getPid()))
                    .collect(Collectors.toList());
        }


        getLeaf(collect);


        return collect;
    }

    static InstallmentVO.ListVO getFirst(List<InstallmentVO.ListVO> a) {
        return CollectionUtils.isEmpty(a) ? new InstallmentVO.ListVO() : a.get(0);
    }


    public void getLeaf(List<InstallmentVO.ListVO> collect) {
        for (InstallmentVO.ListVO listVO : collect) {
            if (CollectionUtils.isEmpty(listVO.getChildren())) {
                List<InstallmentVO.ListVO> oChildren = listVO.getOChildren();
                if (!CollectionUtils.isEmpty(oChildren)) {

                    listVO.setLocalAmt(getFirst(oChildren).getLocalAmt());
                    listVO.setReqAmt(getFirst(oChildren).getReqAmt());
                    listVO.setInnerAmt(getFirst(oChildren).getInnerAmt());
                    listVO.setLocAmt(BigDecimalUtils.sum(listVO.getLocalAmt(), listVO.getReqAmt()));
                    listVO.setDiffAmt(BigDecimalUtils.subtract(listVO.getInnerAmt(), listVO.getReqAmt()));
                }
                continue;
            }
            getLeaf(listVO.getChildren());
        }
    }


    public static void main(String[] args) {
        QqchTaxCostDetail objectTreeNode1 = new QqchTaxCostDetail();

        QqchTaxCostDetail objectTreeNode2 = new QqchTaxCostDetail();

        ArrayList<QqchTaxCostDetail> objects = new ArrayList<>();
        QqchTaxCostDetail objectTreeNode3 = new QqchTaxCostDetail();
        QqchTaxCostDetail objectTreeNode3_1 = new QqchTaxCostDetail();
        QqchTaxCostDetail objectTreeNode3_2 = new QqchTaxCostDetail();

        objects.add(objectTreeNode3);
        objects.add(objectTreeNode3_1);
        objects.add(objectTreeNode3_2);

        objectTreeNode1.setChildren(Collections.singletonList(objectTreeNode2));
        objectTreeNode2.setChildren(objects);

        List<QqchTaxCostDetail> detailList = TreeUtil.treeToListWithLevel(Collections.singletonList(objectTreeNode1));
        System.out.println(objectTreeNode1);


    }


    private InstallmentVO.ListVO getInList(Long recordId, String year, String dataType) {
        // 获取主营业务收入
        QqchTaxInDetail inWhere = new QqchTaxInDetail();
        inWhere.setRecordId(recordId);
        inWhere.setDataType(dataType);
        inWhere.setYear(year);
        List<QqchTaxInDetail> qqchTaxInList = inDetailMapper.getAmtByGroup(inWhere);

        // 封装结果集
        InstallmentVO.ListVO listVO = new InstallmentVO.ListVO();
        Long id = IdWorker.createId();
        listVO.setId(id);
        String di = "1".equals(dataType) ? "主营业务收入合计" : "其他业务收入合计";
        listVO.setDigest(di);
        // 转收入详情 
        List<InstallmentVO.ListVO> children = qqchTaxInList.stream().map(item -> this.createListVO(item, id)).collect(Collectors.toList());
        CommonServiceUtil.setCurrentName(children, "digest", "digest");
        // 计算出收入详情 注意这里设置的是amt
        children.stream().filter(Objects::nonNull).map(InstallmentVO.ListVO::getUsdAmt).reduce(BigDecimal::add).ifPresent(listVO::setAmt);
        listVO.setChildren(children);
        return listVO;
    }

    /**
     * @param in
     * @return
     */
    private InstallmentVO.ListVO createListVO(QqchTaxInDetail in, Long id) {
        InstallmentVO.ListVO res = new InstallmentVO.ListVO();
        res.setId(IdWorker.createId());
        res.setPid(id);
        res.setDigest(in.getCurrency());
        res.setAmt(in.getAmt());
        res.setUsdAmt(in.getUsdAmt());
        return res;
    }


}
