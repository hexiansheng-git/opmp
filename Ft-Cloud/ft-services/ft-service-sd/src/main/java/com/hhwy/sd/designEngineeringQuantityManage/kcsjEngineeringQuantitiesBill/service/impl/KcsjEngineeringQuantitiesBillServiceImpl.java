package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wll
 * @date 2024-02-04 14:04:56
 * @remark 勘察设计-设计工程量管理-工程量清单
 */
@Service
public class KcsjEngineeringQuantitiesBillServiceImpl implements IKcsjEngineeringQuantitiesBillService {

    @Autowired
    private KcsjEngineeringQuantitiesBillMapper kcsjEngineeringQuantitiesBillMapper;

    @Autowired
    private KcsjEngineeringQuantitiesBillDetailMapper kcsjEngineeringQuantitiesBillDetailMapper;

    @Autowired
    private IKcsjEngineeringQuantitiesBillDetailService detailService;

    /**
     * 详情
     *
     * @param kcsjEngineeringQuantitiesBill
     * @return
     */
    public KcsjEngineeringQuantitiesBill getKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        //查询主表数据
        KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill1 = kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        //获取主表Id
        Long id = kcsjEngineeringQuantitiesBill.getId();
        //查询子表数据
        KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
        detail.setMainId(id);
        List<KcsjEngineeringQuantitiesBillDetail> detailList = kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(detail);
        kcsjEngineeringQuantitiesBill1.setDetailsList(TreeUtil.newBuild(detailList));
        return kcsjEngineeringQuantitiesBill1;
    }

    /**
     * 列表查询
     *
     * @param kcsjEngineeringQuantitiesBill
     * @return
     */
    public List<KcsjEngineeringQuantitiesBill> getKcsjEngineeringQuantitiesBillList(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        //设置搜索条件
        String submissionDateStr = kcsjEngineeringQuantitiesBill.getSubmissionDateStr();
        if (StringUtils.isNotEmpty(submissionDateStr)) {
            String[] split = submissionDateStr.split("-");
            kcsjEngineeringQuantitiesBill.setSubmissionDateBegin(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            kcsjEngineeringQuantitiesBill.setSubmissionDateEnd(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }
        return kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBill);
    }

    /**
     * 新增数据
     *
     * @param kcsjEngineeringQuantitiesBill
     * @return
     */
    @Transactional
    public AjaxResult insertKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {


        //新增主表数据
        kcsjEngineeringQuantitiesBill.setId(IdWorker.createId());
        kcsjEngineeringQuantitiesBill.setCreateUser(SecurityUtils.getUserId().toString());
        kcsjEngineeringQuantitiesBill.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        kcsjEngineeringQuantitiesBill.setCreateTime(DateUtils.getNowDate());
        kcsjEngineeringQuantitiesBillMapper.insertKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);

        if (!CollectionUtils.isEmpty(kcsjEngineeringQuantitiesBill.getDetailsList())) {
            List<KcsjEngineeringQuantitiesBillDetail> detailsList = kcsjEngineeringQuantitiesBill.getDetailsList();
            //子表数据处理
            handleInsertList(kcsjEngineeringQuantitiesBill, detailsList, kcsjEngineeringQuantitiesBill.getId());
        }
        return AjaxResult.success();
    }

    private void handleInsertList(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill, List<KcsjEngineeringQuantitiesBillDetail> insertList, Long mainId) {


        //处理子表数据
        //获取上一个版本的数据信息  处理V3.0字符串，获取V2.0版本信息  先查主表再查子表
        //处理版本数据
        BigDecimal value = new BigDecimal(0.0);
        String version = kcsjEngineeringQuantitiesBill.getVersion();
        String substring = version.substring(1, 2);
        int v = Integer.parseInt(substring);
        v++;
        if (v > 2) {
            String lastVersion = String.valueOf(v);
            String listLocation = kcsjEngineeringQuantitiesBill.getListLocation();
            KcsjEngineeringQuantitiesBill bill = new KcsjEngineeringQuantitiesBill();
            bill.setVersion(lastVersion);
            bill.setListLocation(listLocation);
            //根据版本和清单所属部位查询主表
            KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill1 = kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBill(bill);
            List<KcsjEngineeringQuantitiesBillDetail> detailsListNew = new ArrayList<>();
            Map<String, BigDecimal> map = new HashMap<>();
            List<KcsjEngineeringQuantitiesBillDetail> list = kcsjEngineeringQuantitiesBill.getDetailsList();
            KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
            if (!Objects.isNull(kcsjEngineeringQuantitiesBill1)) {

                detail.setMainId(kcsjEngineeringQuantitiesBill1.getId());
                //根据查询出的主表查询子表
                list = kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(detail);

                //设置新增数据的上一个版本的工程量字段
                list.stream().forEach(temp -> {
                    map.put(temp.getListCode(), temp.getWorkload());
                });
            }
            List<KcsjEngineeringQuantitiesBillDetail> newInsertList = new ArrayList<>();
            for (KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail : detailsListNew) {
                Set<Map.Entry<String, BigDecimal>> entries = map.entrySet();
                for (Map.Entry<String, BigDecimal> entry : entries) {
                    if (kcsjEngineeringQuantitiesBillDetail.getListCode().equals(entry.getKey())) {
                        if (v > 2) {
                            value = entry.getValue();
                        }
                        kcsjEngineeringQuantitiesBillDetail.setMainId(mainId);
                        kcsjEngineeringQuantitiesBillDetail.setId(IdWorker.createId());
                        kcsjEngineeringQuantitiesBillDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                        kcsjEngineeringQuantitiesBillDetail.setCreateUser(SecurityUtils.getUserId().toString());
                        kcsjEngineeringQuantitiesBillDetail.setCreateTime(DateUtils.getNowDate());
                        kcsjEngineeringQuantitiesBillDetail.setDelFlag("0");
                        kcsjEngineeringQuantitiesBillDetail.setDataSource("0");
                        kcsjEngineeringQuantitiesBillDetail.setPreviousQuantity(value);
                        BigDecimal previousQuantity = kcsjEngineeringQuantitiesBillDetail.getPreviousQuantity();
                        BigDecimal workload = kcsjEngineeringQuantitiesBillDetail.getWorkload();
                        kcsjEngineeringQuantitiesBillDetail.setQuantityDifference(workload.subtract(previousQuantity));
                        newInsertList.add(kcsjEngineeringQuantitiesBillDetail);
                        handleChildren(newInsertList, detail, detail.getId(), mainId, value);
                    }
                }
            }

            kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetailList(newInsertList);
        }


    }

    //将子节点递归加入集合
    private void handleChildren(List<KcsjEngineeringQuantitiesBillDetail> newList, KcsjEngineeringQuantitiesBillDetail detail, Long pId, Long mainId, BigDecimal v) {
        List<KcsjEngineeringQuantitiesBillDetail> children = detail.getChildren();
        if (null != children) {
            for (KcsjEngineeringQuantitiesBillDetail child : children) {
                child.setMainId(mainId);
                child.setPid(pId);
                child.setId(IdWorker.createId());
                child.setCreateUser(SecurityUtils.getUserId().toString());
                child.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                child.setCreateTime(DateUtils.getNowDate());
                child.setDelFlag("0");
                child.setDataSource("0");
                child.setPreviousQuantity(v);
                newList.add(child);
                handleChildren(newList, child, child.getId(), mainId, v);
            }
        }
    }


    @Transactional
    public int insertKcsjEngineeringQuantitiesBillList(List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList) {
        for (KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill : kcsjEngineeringQuantitiesBillList) {
            kcsjEngineeringQuantitiesBill.setId(IdWorker.createId());
            kcsjEngineeringQuantitiesBill.setCreateUser(SecurityUtils.getUserName());
            kcsjEngineeringQuantitiesBill.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjEngineeringQuantitiesBillMapper.insertKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillList);
    }

    @Transactional
    public int updateKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        kcsjEngineeringQuantitiesBill.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBill.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillMapper.updateKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
    }

    @Transactional
    public int updateKcsjEngineeringQuantitiesBillList(List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList) {
        for (KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill : kcsjEngineeringQuantitiesBillList) {
            kcsjEngineeringQuantitiesBill.setUpdateUser(SecurityUtils.getUserName());
            kcsjEngineeringQuantitiesBill.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEngineeringQuantitiesBillMapper.updateKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillList);
    }

    @Transactional
    public int deleteKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        kcsjEngineeringQuantitiesBill.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBill.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillMapper.deleteKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
    }

    @Transactional
    public int deleteKcsjEngineeringQuantitiesBillByPks(List<Long> kcsjEngineeringQuantitiesBillPkList) {
        return kcsjEngineeringQuantitiesBillMapper.deleteKcsjEngineeringQuantitiesBillByPks(kcsjEngineeringQuantitiesBillPkList);
    }
}
