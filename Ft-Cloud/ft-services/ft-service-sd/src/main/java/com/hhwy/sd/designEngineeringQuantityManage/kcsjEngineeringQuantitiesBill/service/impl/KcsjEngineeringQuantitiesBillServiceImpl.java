package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.FileUploadUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillService;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private FileUploadUtils fileUploadUtil;
    @Autowired
    private ISysSyncInfoService4Sd sysSyncInfoService4Sd;

    /**
     * 详情
     *
     * @param kcsjEngineeringQuantitiesBill
     * @return
     */
    public KcsjEngineeringQuantitiesBill getKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        String isEdit = kcsjEngineeringQuantitiesBill.getIsEdit();
        //查询主表数据
        kcsjEngineeringQuantitiesBill = kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        if (StringUtils.isNotEmpty(isEdit) && ("1".equals(isEdit))) {
            kcsjEngineeringQuantitiesBill.setIsEdit(isEdit);
            if (StringUtils.isNotEmpty(kcsjEngineeringQuantitiesBill.getFileGroupId())) {
                kcsjEngineeringQuantitiesBill.setFileGroupId(fileUploadUtil.copyFile(kcsjEngineeringQuantitiesBill.getFileGroupId()));
            }
        }


        //获取主表Id
        Long id = kcsjEngineeringQuantitiesBill.getId();
        //查询子表数据
        KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
        detail.setMainId(id);
        List<KcsjEngineeringQuantitiesBillDetail> detailList = kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(detail);
        //设置优化前工程量字段
        for (KcsjEngineeringQuantitiesBillDetail billDetail : detailList) {
            BigDecimal workload = billDetail.getWorkload();
            workload = workload == null ? BigDecimal.ZERO : workload;
            billDetail.setPreviousQuantity(workload);
        }
        //把数据构建成树形
        List<KcsjEngineeringQuantitiesBillDetail> treeList = ListTreeUtil.formatTree(
                detailList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                KcsjEngineeringQuantitiesBillDetail::getChildren,
                KcsjEngineeringQuantitiesBillDetail::setChildren);
        kcsjEngineeringQuantitiesBill.setDetailsList(treeList);
        return kcsjEngineeringQuantitiesBill;
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
        if (!kcsjEngineeringQuantitiesBill.getVersion().equals("V1.0")) {
            //将其它版本设置为无效
            KcsjEngineeringQuantitiesBill bill = new KcsjEngineeringQuantitiesBill();
            bill.setListLocation(kcsjEngineeringQuantitiesBill.getListLocation());
            bill.setValid("0");
            kcsjEngineeringQuantitiesBillMapper.updateValid(bill);
        }
        //新增主表数据
        kcsjEngineeringQuantitiesBill.setId(IdWorker.createId());
        kcsjEngineeringQuantitiesBill.setCreateUser(SecurityUtils.getUserId().toString());
        kcsjEngineeringQuantitiesBill.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        kcsjEngineeringQuantitiesBill.setCreateTime(DateUtils.getNowDate());
        kcsjEngineeringQuantitiesBill.setValid("1");
        kcsjEngineeringQuantitiesBill.setDelFlag("0");
        kcsjEngineeringQuantitiesBillMapper.insertKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        //新增子表
        List<KcsjEngineeringQuantitiesBillDetail> detailsList = kcsjEngineeringQuantitiesBill.getDetailsList();
        List<KcsjEngineeringQuantitiesBill> buildTreeList=new ArrayList<>();

        if (!CollectionUtils.isEmpty(detailsList)) {
            //子表数据处理
            //把树形数据拆分成普通列表
            detailsList = ListTreeUtil.formatList(
                    detailsList,
                    KcsjEngineeringQuantitiesBillDetail::setId,
                    KcsjEngineeringQuantitiesBillDetail::setPid,
                    KcsjEngineeringQuantitiesBillDetail::getChildren,
                    KcsjEngineeringQuantitiesBillDetail::setChildren);
            kcsjEngineeringQuantitiesBill.setDetailsList(detailsList);
            //处理子表上一个版本工程量字段、计算优化量差字段
            handleInsertList(kcsjEngineeringQuantitiesBill, kcsjEngineeringQuantitiesBill.getId());
        }
        //推送数据到mq
        if (kcsjEngineeringQuantitiesBill!=null){
            sysSyncInfoService4Sd.pushKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        }

        return AjaxResult.success();
    }

    private void handleInsertList(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill, Long mainId) {
        //处理子表数据 获取上一个版本的数据信息  例如：处理V3.0字符串，获取V2.0版本信息  先查主表再查子表
        //获取当前主表数据版本
        String version = kcsjEngineeringQuantitiesBill.getVersion();
        int indexStart = version.lastIndexOf("V");
        int indexEnd = version.lastIndexOf(".");
        String substring = version.substring(indexStart + 1, indexEnd);
        int v = Integer.parseInt(substring);
        String lastVersion = String.valueOf(--v);
        String listLocation = kcsjEngineeringQuantitiesBill.getListLocation();
        KcsjEngineeringQuantitiesBill bill = new KcsjEngineeringQuantitiesBill();
        bill.setVersion("V" + lastVersion + ".0");
        bill.setListLocation(listLocation);
        //根据版本和清单所属部位查询主表，获取上一个版本数据
        KcsjEngineeringQuantitiesBill oldBill = kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBill(bill);
        Map<String, BigDecimal> map = new HashMap<>();
        List<KcsjEngineeringQuantitiesBillDetail> list = null;
        KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
        if (!Objects.isNull(oldBill)) {
            detail.setMainId(oldBill.getId());
            //根据查询出的主表查询子表
            list = kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(detail);
            //将查询到的子表数据存入map,key为清单编码，value为工作量
            list.stream().forEach(temp -> {
                map.put(temp.getListCode(), temp.getWorkload());
            });
        }
        //新增数据子表数据
        List<KcsjEngineeringQuantitiesBillDetail> detailsListNew = kcsjEngineeringQuantitiesBill.getDetailsList();
        List<KcsjEngineeringQuantitiesBillDetail> details = new ArrayList<>();
        //遍历子表，去map中获取上一个版本的数据，设置给子表的上一个版本工程量字段
        for (KcsjEngineeringQuantitiesBillDetail temp : detailsListNew) {
            //上一版本清单工程量
            BigDecimal oldQuanlity = map.get(temp.getListCode());
            oldQuanlity = oldQuanlity == null ? BigDecimal.ZERO : oldQuanlity;
            temp.setMainId(mainId);
            temp.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            temp.setCreateUser(SecurityUtils.getUserId().toString());
            temp.setCreateTime(DateUtils.getNowDate());
            temp.setDelFlag("0");
            temp.setDataSource("0");
            temp.setPreviousQuantity(oldQuanlity);
            BigDecimal workload = temp.getWorkload();
            details.add(temp);
            if (workload != null) {
                temp.setQuantityDifference(workload.subtract(oldQuanlity));
            }
        }

        kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetailList(details);
    }


    @Transactional
    public AjaxResult updateKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {

        //修改主表数据
        kcsjEngineeringQuantitiesBill.setUpdateUser(SecurityUtils.getUserId().toString());
        kcsjEngineeringQuantitiesBill.setUpdateTime(DateUtils.getNowDate());

        //推送数据到mq
        if (kcsjEngineeringQuantitiesBill!=null){
            sysSyncInfoService4Sd.pushKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        }

        kcsjEngineeringQuantitiesBillMapper.updateKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBill);
        //处理子表删除的数据
        List<Long> delIdList = kcsjEngineeringQuantitiesBill.getDelIdList();
        if (delIdList.size() > 0) {
            detailService.deleteByIds(delIdList);
        }
        //处理子表新增的数据
        List<KcsjEngineeringQuantitiesBillDetail> detailsList = kcsjEngineeringQuantitiesBill.getDetailsList();
        kcsjEngineeringQuantitiesBill.setDetailsList(detailsList);
        //将树形拆成普通列表
        detailsList = ListTreeUtil.formatList(
                detailsList,
                KcsjEngineeringQuantitiesBillDetail::getIsAdd,
                KcsjEngineeringQuantitiesBillDetail::getId,
                KcsjEngineeringQuantitiesBillDetail::setId,
                KcsjEngineeringQuantitiesBillDetail::setPid,
                KcsjEngineeringQuantitiesBillDetail::getChildren,
                KcsjEngineeringQuantitiesBillDetail::setChildren);
        List<KcsjEngineeringQuantitiesBillDetail> addList = new ArrayList<>();
        addList = detailsList.stream().filter(d -> StringUtils.isNotEmpty(d.getIsAdd()) && d.getIsAdd().equals("1")).collect(Collectors.toList());
        kcsjEngineeringQuantitiesBill.setDetailsList(addList);
        if (addList.size() > 0) {
            handleInsertList(kcsjEngineeringQuantitiesBill, kcsjEngineeringQuantitiesBill.getId());
        }
        //修改子表数据
        List<KcsjEngineeringQuantitiesBillDetail> updateList = new ArrayList<>();
        updateList = detailsList.stream().filter(d -> StringUtils.isEmpty(d.getIsAdd()) || (!d.getIsAdd().equals("1"))).collect(Collectors.toList());
        if (updateList.size() > 0) {
            handleUpdate(updateList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetailList(updateList);
        }


        return AjaxResult.success();
    }

    private void handleUpdate(List<KcsjEngineeringQuantitiesBillDetail> updateList) {

        //批量编辑
        if (!CollectionUtils.isEmpty(updateList)) {
            List<KcsjEngineeringQuantitiesBillDetail> newUpdateList = new ArrayList<>();
            for (int i = 0; i < updateList.size(); i++) {
                KcsjEngineeringQuantitiesBillDetail detail = updateList.get(i);
                detail.setUpdateUser(SecurityUtils.getUserId() + "");
                detail.setUpdateTime(DateUtils.getNowDate());
                detail.setDelFlag("0");
                if (detail.getWorkload() != null && detail.getPreviousQuantity() != null) {
                    detail.setQuantityDifference(detail.getWorkload().subtract(detail.getPreviousQuantity()));
                }
                newUpdateList.add(detail);
                //处理子节点，将当前数据的子节点加入集合
                handleUpdateChildren(newUpdateList, detail);
            }
            kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetailList(newUpdateList);
        }
    }

    private void handleUpdateChildren(List<KcsjEngineeringQuantitiesBillDetail> newUpdateList, KcsjEngineeringQuantitiesBillDetail detail) {

        List<KcsjEngineeringQuantitiesBillDetail> children = detail.getChildren();
        if (children != null) {
            for (KcsjEngineeringQuantitiesBillDetail child : children) {
                child.setUpdateUser(SecurityUtils.getUserId().toString());
                child.setUpdateTime(DateUtils.getNowDate());
                if (child.getWorkload() != null && child.getPreviousQuantity() != null) {
                    child.setQuantityDifference(child.getWorkload().subtract(child.getPreviousQuantity()));
                }
                if (child.getChildren() != null) {
                    handleUpdateChildren(child.getChildren(), child);
                }
                newUpdateList.add(child);
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
    public int updateKcsjEngineeringQuantitiesBillList(List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList) {
        for (KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill : kcsjEngineeringQuantitiesBillList) {
            kcsjEngineeringQuantitiesBill.setUpdateUser(SecurityUtils.getUserName());
            kcsjEngineeringQuantitiesBill.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEngineeringQuantitiesBillMapper.updateKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillList);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Transactional
    public int deleteKcsjEngineeringQuantitiesBillByPks(List<Long> ids) {
        //获取删除的数据集合
        List<KcsjEngineeringQuantitiesBill> list = kcsjEngineeringQuantitiesBillMapper.getKcsjEngineeringQuantitiesBillPks(ids);
        List<String> listLocation = list.stream().map(e -> e.getListLocation()).collect(Collectors.toList());
        //删除主表数据
        kcsjEngineeringQuantitiesBillMapper.deleteKcsjEngineeringQuantitiesBillByPks(ids, SecurityUtils.getUserId().toString());
        //修改最新版数据
        kcsjEngineeringQuantitiesBillMapper.updateNewVersion(listLocation);
        //删除子表数据

        //推送删除的数据id集合到mq
        if (ids!=null){
            KcsjEngineeringQuantitiesBill bill=new KcsjEngineeringQuantitiesBill();
            bill.setDelIdList(ids);
            sysSyncInfoService4Sd.pushKcsjEngineeringQuantitiesBill(bill);
        }

        return kcsjEngineeringQuantitiesBillDetailMapper.deleteKcsjEngineeringQuantitiesBillDetailByMainId(ids, SecurityUtils.getUserId().toString());
    }


}
