package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.FileUploadUtil;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListDetailService;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
@Service
public class KcsjMaterialsListServiceImpl implements IKcsjMaterialsListService {

    @Autowired
    private KcsjMaterialsListMapper kcsjMaterialsListMapper;

    @Autowired
    private KcsjMaterialsListDetailMapper detailMapper;

    @Autowired
    private IKcsjMaterialsListDetailService detailService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 详情
     *
     * @param kcsjMaterialsList
     * @return
     */
    public KcsjMaterialsList getKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        //查询主表数据
        kcsjMaterialsList = kcsjMaterialsListMapper.getKcsjMaterialsList(kcsjMaterialsList);
        if ("1".equals(kcsjMaterialsList.getIsEdit())){
            if (StringUtils.isNotEmpty(kcsjMaterialsList.getFileGroupId())){
                kcsjMaterialsList.setFileGroupId(fileUploadUtil.copyFile(kcsjMaterialsList.getFileGroupId()));
            }
        }
        String fileGroupId = kcsjMaterialsList.getFileGroupId();
        if (StringUtils.isNotEmpty(fileGroupId)){
            kcsjMaterialsList.setFileGroupId(fileUploadUtil.copyFile(fileGroupId));
        }
        //查询子表数据
        List<KcsjMaterialsListDetail> detailList = detailMapper.getKcsjMaterialsListDetailListByMainId(kcsjMaterialsList.getId());
        //设置优化前设计量
        for (KcsjMaterialsListDetail detail : detailList) {
            BigDecimal designQuantity = detail.getDesignQuantity();
            designQuantity = designQuantity == null ? BigDecimal.ZERO : designQuantity;
            detail.setPreviousQuantity(designQuantity);
        }
        //组装返回值
        if (detailList.size() > 0) {
            kcsjMaterialsList.setDetailList(detailList);
        }
        return kcsjMaterialsList;
    }

    /**
     * 列表页查询
     *
     * @param kcsjMaterialsList
     * @return
     */
    public List<KcsjMaterialsList> getKcsjMaterialsListList(KcsjMaterialsList kcsjMaterialsList) {
        //获取搜索条件
        String submitDateStr = kcsjMaterialsList.getSubmitDateStr();
        if (StringUtils.isNotEmpty(submitDateStr)) {
            //处理搜索条件
            String[] split = submitDateStr.split("-");
            kcsjMaterialsList.setSubmitDateBegin(FtDateUtils.parseDate(split[0].replaceAll("(?:年|月|日)", "-")));
            kcsjMaterialsList.setSubmitDateEnd(FtDateUtils.parseDate(split[1].replaceAll("(?:年|月|日)", "-")));
        }
        //查询 返回结果
        return kcsjMaterialsListMapper.getKcsjMaterialsListList(kcsjMaterialsList);
    }

    /**
     * 新增
     *
     * @param kcsjMaterialsList
     * @return
     */
    @Transactional
    public int insertKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {

        //校验 项目控制损耗定额不能大于局损耗定额
        List<KcsjMaterialsListDetail> detailList = kcsjMaterialsList.getDetailList();
        for (KcsjMaterialsListDetail kcsjMaterialsListDetail : detailList) {
            BigDecimal projectLossQuota = kcsjMaterialsListDetail.getProjectLossQuota();
            BigDecimal localLossQuota = kcsjMaterialsListDetail.getLocalLossQuota();
            if (projectLossQuota != null && localLossQuota != null) {
                int i = projectLossQuota.compareTo(localLossQuota);
                if (i > 0) {
                    throw new BaseException("项目控制损耗定额不能大于局损耗定额");
                }
            }
        }
        //设置主表新增数据
        kcsjMaterialsList.setId(IdWorker.createId());
        kcsjMaterialsList.setCreateUser(SecurityUtils.getUserId().toString());
        kcsjMaterialsList.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        kcsjMaterialsList.setCreateTime(DateUtils.getNowDate());
        kcsjMaterialsList.setValid("1");
        kcsjMaterialsList.setDelFlag("0");

        //无效主表其它版本数据
        if (!kcsjMaterialsList.getVersion().equals("V1.0")) {
            //将其它版本设置为无效
            KcsjMaterialsList materialsList = new KcsjMaterialsList();
            materialsList.setListName(kcsjMaterialsList.getListName());
            materialsList.setValid("0");
            kcsjMaterialsListMapper.updateValid(materialsList);
        }
        //新增子表数据
        if (kcsjMaterialsList.getDetailList() != null && kcsjMaterialsList.getDetailList().size() > 0) {
            handleInsertDetails(kcsjMaterialsList);
        }
        //新增主表数据
        return kcsjMaterialsListMapper.insertKcsjMaterialsList(kcsjMaterialsList);
    }

    private void handleInsertDetails(KcsjMaterialsList kcsjMaterialsList) {
        //新增子表数据
        List<KcsjMaterialsListDetail> detailList = kcsjMaterialsList.getDetailList();
        if (detailList != null && detailList.size() > 0) {
            //获取上一个版本设计量
            KcsjMaterialsList old = new KcsjMaterialsList();
            String version = kcsjMaterialsList.getVersion();
            int indexStart = version.lastIndexOf("V");
            int indexEnd = version.lastIndexOf(".");
            String substring = version.substring(indexStart + 1, indexEnd);
            int v = Integer.parseInt(substring);
            String lastVersion = String.valueOf(--v);
            old.setVersion("V" + lastVersion + ".0");
            old.setListName(kcsjMaterialsList.getListName());
            //获取上一版本的主表数据
            KcsjMaterialsList oldMaterial = kcsjMaterialsListMapper.getKcsjMaterialsList(old);//将上一个版本的材料明细放入map中
            Map<String, BigDecimal> map = new HashMap<>();

            if (!Objects.isNull(oldMaterial)) {
                //获取上一个版本的主表数据明细
                List<KcsjMaterialsListDetail> details = detailMapper.getKcsjMaterialsListDetailListByMainId(oldMaterial.getId());
                if (details.size() > 0) {
                    details.stream().forEach(temp -> {
                        map.put(temp.getMaterialName(), temp.getDesignQuantity());
                    });
                }
            }
            for (KcsjMaterialsListDetail kcsjMaterialsListDetail : detailList) {
                //设置上一版设计量和设计量差值
                kcsjMaterialsListDetail.setMainId(kcsjMaterialsList.getId());
                BigDecimal oldDesign = map.get(kcsjMaterialsListDetail.getMaterialName());
                oldDesign = oldDesign == null ? BigDecimal.ZERO : oldDesign;
                kcsjMaterialsListDetail.setPreviousQuantity(oldDesign);
                BigDecimal designQuantity = kcsjMaterialsListDetail.getDesignQuantity();
                if (designQuantity != null) {
                    kcsjMaterialsListDetail.setQuantityDifference(designQuantity.subtract(oldDesign));
                }
            }
            kcsjMaterialsList.setDetailList(detailList);
            //插入子表数据
            detailService.insertKcsjMaterialsListDetailList(detailList);
        }
    }


    @Transactional
    public int insertKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList) {
        for (KcsjMaterialsList kcsjMaterialsList : kcsjMaterialsListList) {
            kcsjMaterialsList.setId(IdWorker.createId());
            kcsjMaterialsList.setCreateUser(SecurityUtils.getUserName());
            kcsjMaterialsList.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListMapper.insertKcsjMaterialsListList(kcsjMaterialsListList);
    }

    /**
     * 修改数据
     *
     * @param kcsjMaterialsList
     * @return
     */
    @Transactional
    public int updateKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {

        //校验 项目控制损耗定额不能大于局损耗定额
        List<KcsjMaterialsListDetail> detailList = kcsjMaterialsList.getDetailList();
        for (KcsjMaterialsListDetail kcsjMaterialsListDetail : detailList) {
            BigDecimal projectLossQuota = kcsjMaterialsListDetail.getProjectLossQuota();
            BigDecimal localLossQuota = kcsjMaterialsListDetail.getLocalLossQuota();
            if (projectLossQuota != null && localLossQuota != null) {
                int i = projectLossQuota.compareTo(localLossQuota);
                if (i > 0) {
                    throw new BaseException("项目控制损耗定额不能大于局损耗定额");
                }
            }
        }

        kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        //删除子表数据
        Long id = kcsjMaterialsList.getId();
        List<Long> list = new ArrayList<>();
        list.add(id);
        detailMapper.deleteKcsjMaterialsListDetailByMainId(list, SecurityUtils.getUserId().toString());
        //重新插入子表数据
        handleInsertDetails(kcsjMaterialsList);
        //修改主表数据
        return kcsjMaterialsListMapper.updateKcsjMaterialsList(kcsjMaterialsList);
    }

    @Transactional
    public int updateKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList) {
        for (KcsjMaterialsList kcsjMaterialsList : kcsjMaterialsListList) {
            kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
            kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListMapper.updateKcsjMaterialsListList(kcsjMaterialsListList);
    }


    @Transactional
    public int deleteKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        kcsjMaterialsList.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsList.setUpdateTime(DateUtils.getNowDate());
        return kcsjMaterialsListMapper.deleteKcsjMaterialsList(kcsjMaterialsList);
    }

    /**
     * 删除数据
     *
     * @param ids
     * @return
     */
    @Transactional
    public int deleteKcsjMaterialsListByPks(List<Long> ids) {
        //获取要删除的数据集合
        KcsjMaterialsList kcsjMaterialsList = new KcsjMaterialsList();
        kcsjMaterialsList.setDelIdList(ids);
        List<KcsjMaterialsList> list = kcsjMaterialsListMapper.getKcsjMaterialsListList(kcsjMaterialsList);
        List<String> listName = list.stream().map(e -> e.getListName()).collect(Collectors.toList());
        //删除主表数据
        kcsjMaterialsListMapper.deleteKcsjMaterialsListByPks(ids, SecurityUtils.getUserId().toString());
        //让其它版本的最新版变为有效
        kcsjMaterialsListMapper.updateNewVersion(listName);
        //删除子表数据
        return detailMapper.deleteKcsjMaterialsListDetailByMainId(ids, SecurityUtils.getUserId().toString());
    }
}
