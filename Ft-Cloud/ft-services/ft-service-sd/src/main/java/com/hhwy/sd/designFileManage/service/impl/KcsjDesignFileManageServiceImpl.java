package com.hhwy.sd.designFileManage.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManageVo;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.tree.TreeUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.designFileManage.mapper.KcsjDesignFileManageMapper;
import com.hhwy.sd.designFileManage.service.IKcsjDesignFileManageService;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManage;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark
 */
@Service
public class KcsjDesignFileManageServiceImpl implements IKcsjDesignFileManageService {

    @Autowired
    private KcsjDesignFileManageMapper kcsjDesignFileManageMapper;


    public KcsjDesignFileManage getKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage) {
        return kcsjDesignFileManageMapper.getKcsjDesignFileManage(kcsjDesignFileManage);
    }

    /**
     * 列表list查询
     * @param kcsjDesignFileManage
     * @return
     */
    public KcsjDesignFileManageVo getKcsjDesignFileManageList(KcsjDesignFileManage kcsjDesignFileManage) {
        KcsjDesignFileManageVo kcsjDesignFileManageVo = new KcsjDesignFileManageVo();
        if(StringUtils.isNotEmpty(kcsjDesignFileManage.getRepleDateStr())){
            kcsjDesignFileManage.setRepleDate(FtDateUtils.parseDate(kcsjDesignFileManage.getRepleDateStr().replaceAll("(?:年|月|日)", "-")));
        }
        List<KcsjDesignFileManage> kcsjDesignFileManageList = kcsjDesignFileManageMapper.getKcsjDesignFileManageList(kcsjDesignFileManage);
        List<KcsjDesignFileManage> list = new ArrayList<>();
        if(kcsjDesignFileManageList.size()>0){
            kcsjDesignFileManageList.forEach(k->{
                k.setReceptionDateStr(k.getReceptionDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(k.getReceptionDate()));
                k.setRepleDateStr(k.getRepleDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(k.getRepleDate()));
                k.setReportDateStr(k.getReportDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(k.getReportDate()));
            });
            List<KcsjDesignFileManage> list1 = kcsjDesignFileManageList.stream().filter(d -> StringUtils.isNotEmpty(d.getPid().toString()) && !d.getPid().toString().equals("0")).collect(Collectors.toList());
            if(list1.size()>0){
                KcsjDesignFileManage designFileManage = new KcsjDesignFileManage();
                for (int i = 0; i < list1.size(); i++) {
                    String[] split = list1.get(i).getPath().split("/");
                    designFileManage.setPaths(split);
                    List<KcsjDesignFileManage> sgjsPlanMeasureManage2 = kcsjDesignFileManageMapper.getKcsjDesignFileManageList(designFileManage);
                    kcsjDesignFileManageList.addAll(sgjsPlanMeasureManage2);
                }
            }
            List<KcsjDesignFileManage> collect = kcsjDesignFileManageList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(KcsjDesignFileManage::getId))), ArrayList::new));

            list = collect.stream().sorted(Comparator.comparing(KcsjDesignFileManage::getSerialNumber)).collect(Collectors.toList());
        }
        kcsjDesignFileManageVo.setTreeList(TreeUtil.newBuild(list));
        return kcsjDesignFileManageVo;
    }

    @Transactional
    public int insertKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage) {
        kcsjDesignFileManage.setId(IdWorker.createId());
        kcsjDesignFileManage.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignFileManage.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignFileManageMapper.insertKcsjDesignFileManage(kcsjDesignFileManage);
    }

    @Transactional
    public AjaxResult insertKcsjDesignFileManageList(KcsjDesignFileManageVo kcsjDesignFileManageVo) {
        List<KcsjDesignFileManage> treeToList = null;
        if(!CollectionUtils.isEmpty(kcsjDesignFileManageVo.getTreeList())){
            treeToList = TreeUtil.treeToListWithoutId(kcsjDesignFileManageVo.getTreeList());
            for (int i = 0; i < treeToList.size(); i++) {
                KcsjDesignFileManage kcsjDesignFileManage = treeToList.get(i);
                kcsjDesignFileManage.setCreateTime(DateTime.now());
                kcsjDesignFileManage.setCreateUser(SecurityUtils.getUserId() + "");
                kcsjDesignFileManage.setCreateUserName(SecurityUtils.getUserName() + "");
                kcsjDesignFileManage.setUpdateTime(DateTime.now());
                kcsjDesignFileManage.setUpdateUser(SecurityUtils.getUserId() + "");
                kcsjDesignFileManage.setDelFlag("0");
                kcsjDesignFileManage.setReceptionDate(kcsjDesignFileManage.getReceptionDateStr() == null ? null : FtDateUtils.parseDate(kcsjDesignFileManage.getReceptionDateStr().replaceAll("(?:年|月|日)", "-")));
                kcsjDesignFileManage.setReportDate(kcsjDesignFileManage.getReportDateStr() == null ? null : FtDateUtils.parseDate(kcsjDesignFileManage.getReportDateStr().replaceAll("(?:年|月|日)", "-")));
                kcsjDesignFileManage.setRepleDate(kcsjDesignFileManage.getRepleDateStr() == null ? null : FtDateUtils.parseDate(kcsjDesignFileManage.getRepleDateStr().replaceAll("(?:年|月|日)", "-")));
            }
            List<KcsjDesignFileManage> insertList = treeToList.stream().filter(d -> StringUtils.isNotEmpty(d.getIsAdd()) && d.getIsAdd().equals("1")).collect(Collectors.toList());
            //批量入库
            if(!CollectionUtils.isEmpty(insertList)){
                kcsjDesignFileManageMapper.insertKcsjDesignFileManageList(insertList);
            }
            //批量编辑
            List<KcsjDesignFileManage> updateList = treeToList.stream().filter(d -> StringUtils.isEmpty(d.getIsAdd())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(updateList)){
                for (int i = 0; i < updateList.size(); i++) {
                    KcsjDesignFileManage designFileManage = updateList.get(i);
                    designFileManage.setDelFlag("0");
                }
                kcsjDesignFileManageMapper.updateKcsjDesignFileManageList(updateList);
            }
        }
        //批量删除
        if(!CollectionUtils.isEmpty(kcsjDesignFileManageVo.getDelIdList())){
            deleteByIds(kcsjDesignFileManageVo.getDelIdList());
        }
        return AjaxResult.success();
    }
    /**
     * 批量删除
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        List<KcsjDesignFileManage> list =new ArrayList<>();
        List<KcsjDesignFileManage> ids = getIds(delIdList);
        for (int i = 0; i < ids.size(); i++) {
            KcsjDesignFileManage info=new KcsjDesignFileManage();
            info.setId(ids.get(i).getId());
            info.setUpdateUser(SecurityUtils.getUserId()+"");
            info.setUpdateTime(DateUtils.getNowDate());
            info.setDelFlag("1");
            list.add(info);
        }
        //删除
        if(!CollectionUtils.isEmpty(list)){
            kcsjDesignFileManageMapper.deleteInfoData(list);
        }
    }

    private List<KcsjDesignFileManage> getIds(List<String> ids) {
        List<KcsjDesignFileManage> list = new ArrayList<>();
        List<KcsjDesignFileManage> list1 = kcsjDesignFileManageMapper.getIds(ids);
        for (int i = 0; i < list1.size(); i++) {
            KcsjDesignFileManage kcsjDesignFileManage = new KcsjDesignFileManage();
            kcsjDesignFileManage.setPid(list1.get(i).getId());
            List<KcsjDesignFileManage> list2 = kcsjDesignFileManageMapper.getKcsjDesignFileManageList(kcsjDesignFileManage);
            if(list2.size()>0){
                diguiList2(list2,list1.get(i));
            }
            list.add(list1.get(i));
        }
        if(list.size()>0){
            list = TreeUtil.treeToListWithoutId(list);
            list = list.stream().sorted(Comparator.comparing(KcsjDesignFileManage::getSerialNumber)).collect(Collectors.toList());
        }
        return list;

    }

    private void diguiList2(List<KcsjDesignFileManage> list2, KcsjDesignFileManage fileManage) {
        List<KcsjDesignFileManage> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            KcsjDesignFileManage designFileManage = new KcsjDesignFileManage();
            designFileManage.setPid(list2.get(i).getId());
            List<KcsjDesignFileManage> list3 = kcsjDesignFileManageMapper.getKcsjDesignFileManageList(designFileManage);
            if(list3.size()>0){
                diguiList2(list3,list2.get(i));
            }
            list.add(list2.get(i));
        }
        fileManage.setChildren(list);
    }

    @Transactional
    public int updateKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage) {
        kcsjDesignFileManage.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignFileManage.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignFileManageMapper.updateKcsjDesignFileManage(kcsjDesignFileManage);
    }

    @Transactional
    public int updateKcsjDesignFileManageList(List<KcsjDesignFileManage> kcsjDesignFileManageList) {
        for (KcsjDesignFileManage kcsjDesignFileManage : kcsjDesignFileManageList) {
            kcsjDesignFileManage.setUpdateUser(SecurityUtils.getUserName());
            kcsjDesignFileManage.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjDesignFileManageMapper.updateKcsjDesignFileManageList(kcsjDesignFileManageList);
    }

    @Transactional
    public int deleteKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage) {
        kcsjDesignFileManage.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignFileManage.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignFileManageMapper.deleteKcsjDesignFileManage(kcsjDesignFileManage);
    }

    @Transactional
    public int deleteKcsjDesignFileManageByPks(List<Long> kcsjDesignFileManagePkList) {
        return kcsjDesignFileManageMapper.deleteKcsjDesignFileManageByPks(kcsjDesignFileManagePkList);
    }
}
