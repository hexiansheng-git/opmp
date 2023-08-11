package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchSubpackageBidPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.vo.QqchSubpackageBidPlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper.QqchFacilityPlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper.QqchStaffPlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper.QqchSubpackageBidPlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.mapper.QqchSubpackageInventoryMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchFacilityPlanService;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchStaffPlanService;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchSubpackageBidPlanService;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchSubpackageInventoryService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:44
 * @remark
 */
@Service
public class QqchSubpackageBidPlanServiceImpl implements IQqchSubpackageBidPlanService {

    @Autowired
    private QqchSubpackageBidPlanMapper qqchSubpackageBidPlanMapper;

    @Autowired
    private IQqchSubpackageInventoryService qqchSubpackageInventoryService;

    @Autowired
    private IQqchStaffPlanService qqchStaffPlanService;

    @Autowired
    private IQqchFacilityPlanService qqchFacilityPlanService;

    @Autowired
    private QqchSubpackageInventoryMapper qqchSubpackageInventoryMapper;

    @Autowired
    private QqchStaffPlanMapper qqchStaffPlanMapper;

    @Autowired
    private QqchFacilityPlanMapper qqchFacilityPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchSubpackageBidPlan getQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        return qqchSubpackageBidPlanMapper.getQqchSubpackageBidPlan(qqchSubpackageBidPlan);
    }

    public List<QqchSubpackageBidPlan> getQqchSubpackageBidPlanList(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        return qqchSubpackageBidPlanMapper.getQqchSubpackageBidPlanList(qqchSubpackageBidPlan);
    }

    @Transactional
    public int insertQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        qqchSubpackageBidPlan.setId(IdWorker.createId());
        qqchSubpackageBidPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSubpackageBidPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSubpackageBidPlanMapper.insertQqchSubpackageBidPlan(qqchSubpackageBidPlan);
    }

    @Transactional
    public int insertQqchSubpackageBidPlanList(List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList) {
        for (QqchSubpackageBidPlan qqchSubpackageBidPlan : qqchSubpackageBidPlanList) {
            qqchSubpackageBidPlan.setId(IdWorker.createId());
            qqchSubpackageBidPlan.setCreateUser(SecurityUtils.getUserName());
            qqchSubpackageBidPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageBidPlanMapper.insertQqchSubpackageBidPlanList(qqchSubpackageBidPlanList);
    }

    @Transactional
    public int updateQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        qqchSubpackageBidPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageBidPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageBidPlanMapper.updateQqchSubpackageBidPlan(qqchSubpackageBidPlan);
    }

    @Transactional
    public int updateQqchSubpackageBidPlanList(List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList) {
        for (QqchSubpackageBidPlan qqchSubpackageBidPlan : qqchSubpackageBidPlanList) {
            qqchSubpackageBidPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchSubpackageBidPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageBidPlanMapper.updateQqchSubpackageBidPlanList(qqchSubpackageBidPlanList);
    }

    @Transactional
    public int deleteQqchSubpackageBidPlan(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        qqchSubpackageBidPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageBidPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageBidPlanMapper.deleteQqchSubpackageBidPlan(qqchSubpackageBidPlan);
    }

    @Transactional
    public int deleteQqchSubpackageBidPlanByPks(List<Long> qqchSubpackageBidPlanPkList) {
        return qqchSubpackageBidPlanMapper.deleteQqchSubpackageBidPlanByPks(qqchSubpackageBidPlanPkList);
    }

    /**
     * 获取分包招标策划Vo
     * @param qqchSubpackageBidPlan
     * @return
     */
    @Override
    public QqchSubpackageBidPlanVo getQqchSubpackageBidPlanVo(QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        QqchSubpackageBidPlanVo qqchSubpackageBidPlanVo = new QqchSubpackageBidPlanVo();

        BigDecimal version = qqchSubpackageBidPlan.getVersion();
        version = VersionUtil.getVersion("qqch_subpackage_bid_plan",version);

        //获取主表数据
        qqchSubpackageBidPlan.setVersion(version);
        List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList = qqchSubpackageBidPlanMapper.getQqchSubpackageBidPlanList(qqchSubpackageBidPlan);

        //设置子表数据
//        this.setSublist(qqchSubpackageBidPlanList,version);

        //转树列表
        List<QqchSubpackageBidPlan> treeList = ListTreeUtil.formatTree(
                qqchSubpackageBidPlanList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchSubpackageBidPlan::getChildren,
                QqchSubpackageBidPlan::setChildren);

        qqchSubpackageBidPlanVo.setVersion(version);
        qqchSubpackageBidPlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchSubpackageBidPlanVo.setList(treeList);
        return qqchSubpackageBidPlanVo;
    }

    /**
     * 查询子表数据
     * @param qqchSubpackageBidPlanList
     * @param version
     */
//    public void setSublist(List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList,BigDecimal version){
//        //分包清单
//        QqchSubpackageInventory qqchSubpackageInventory = new QqchSubpackageInventory();
//        qqchSubpackageInventory.setVersion(version);
//        List<QqchSubpackageInventory> qqchSubpackageInventoryList = qqchSubpackageInventoryService.getQqchSubpackageInventoryList(qqchSubpackageInventory);
//
//        //转树列表
//        List<QqchSubpackageInventory> inventoryTreeList = ListTreeUtil.formatTree(
//                qqchSubpackageInventoryList,
//                o -> o.getPid() == null,
//                (r, n) -> r.getId().equals(n.getPid()),
//                QqchSubpackageInventory::getChildren,
//                QqchSubpackageInventory::setChildren);
//
//        //人员策划
//        QqchStaffPlan qqchStaffPlan = new QqchStaffPlan();
//        qqchStaffPlan.setVersion(version);
//        List<QqchStaffPlan> qqchStaffPlanList = qqchStaffPlanService.getQqchStaffPlanList(qqchStaffPlan);
//
//        //设备策划
//        QqchFacilityPlan qqchFacilityPlan = new QqchFacilityPlan();
//        qqchFacilityPlan.setVersion(version);
//        List<QqchFacilityPlan> qqchFacilityPlanList = qqchFacilityPlanService.getQqchFacilityPlanList(qqchFacilityPlan);
//
//        for (QqchSubpackageBidPlan qqchSubpackageBidPlan : qqchSubpackageBidPlanList) {
//            Long id = qqchSubpackageBidPlan.getId();
//
//            List<QqchSubpackageInventory> inventoryList = new ArrayList<>();
//            List<QqchStaffPlan> staffPlanList = new ArrayList<>();
//            List<QqchFacilityPlan> facilityPlanList = new ArrayList<>();
//
//            for (QqchSubpackageInventory subpackageInventory : inventoryTreeList) {
//                if(id.equals(subpackageInventory.getMasterId())){
//                    inventoryList.add(subpackageInventory);
//                }
//            }
//            for (QqchStaffPlan staffPlan : qqchStaffPlanList) {
//                if(id.equals(staffPlan.getMasterId())){
//                    staffPlanList.add(staffPlan);
//                }
//            }
//            for (QqchFacilityPlan facilityPlan : qqchFacilityPlanList) {
//                if(id.equals(facilityPlan.getMasterId())){
//                    facilityPlanList.add(facilityPlan);
//                }
//            }
//
//            qqchSubpackageBidPlan.setQqchSubpackageInventoryList(inventoryList);
//            qqchSubpackageBidPlan.setQqchStaffPlanList(staffPlanList);
//            qqchSubpackageBidPlan.setQqchFacilityPlanList(facilityPlanList);
//        }
//    }

    /**
     * 保存/确认/提交
     * @param qqchSubpackageBidPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchSubpackageBidPlanVo qqchSubpackageBidPlanVo) {
        String buttonMark = qqchSubpackageBidPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchSubpackageBidPlanVo.getVersion();
        List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList = qqchSubpackageBidPlanVo.getList();

        List<QqchSubpackageBidPlan> tileList = ListTreeUtil.formatList(
                qqchSubpackageBidPlanList,
                QqchSubpackageBidPlan::setId,
                QqchSubpackageBidPlan::setPid,
                QqchSubpackageBidPlan::setSort,
                QqchSubpackageBidPlan::setLeaf,
                QqchSubpackageBidPlan::getChildren,
                QqchSubpackageBidPlan::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchSubpackageBidPlanVo.getMenuId();
            String stageIdentity = qqchSubpackageBidPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchSubpackageBidPlan> tileList, BigDecimal version) {
        //根据版本删除数据
        this.deleteDate(version);

        if(CollectionUtils.isEmpty(tileList)){
            return;
        }

//        List<QqchSubpackageInventory> inventoryList = new ArrayList<>();
//        List<QqchStaffPlan> staffPlanList = new ArrayList<>();
//        List<QqchFacilityPlan> facilityPlanList = new ArrayList<>();

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSubpackageBidPlan qqchSubpackageBidPlan : tileList) {
            //分包清单
//            this.disposeQqchSubpackageInventory(version,qqchSubpackageBidPlan,inventoryList);

            //人员策划
//            this.disposeQqchStaffPlan(version,qqchSubpackageBidPlan,staffPlanList);

            //设备策划
//            this.disposeQqchFacilityPlan(version,qqchSubpackageBidPlan,facilityPlanList);

//            qqchSubpackageBidPlan.setQqchSubpackageInventoryList(null);
//            qqchSubpackageBidPlan.setQqchStaffPlanList(null);
//            qqchSubpackageBidPlan.setQqchFacilityPlanList(null);
            qqchSubpackageBidPlan.setValid(valid);
            qqchSubpackageBidPlan.setVersion(version);
            qqchSubpackageBidPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSubpackageBidPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchSubpackageBidPlan.setCreateTime(DateUtils.getNowDate());
        }

        //插入数据
        qqchSubpackageBidPlanMapper.insertQqchSubpackageBidPlanList(tileList);

        //插入分包清单
//        if(CollectionUtils.isNotEmpty(inventoryList)){
//            qqchSubpackageInventoryMapper.insertQqchSubpackageInventoryList(inventoryList);
//        }

        //插入人员策划
//        if(CollectionUtils.isNotEmpty(staffPlanList)){
//            qqchStaffPlanMapper.insertQqchStaffPlanList(staffPlanList);
//        }

        //插入设备策划
//        if(CollectionUtils.isNotEmpty(facilityPlanList)){
//            qqchFacilityPlanMapper.insertQqchFacilityPlanList(facilityPlanList);
//        }
    }

    /**
     * 删除数据
     * @param version
     */
    @Transactional
    public void deleteDate(BigDecimal version){
        //删除主表数据
        QqchSubpackageBidPlan qqchSubpackageBidPlan = new QqchSubpackageBidPlan();
        qqchSubpackageBidPlan.setVersion(version);
        qqchSubpackageBidPlanMapper.deleteQqchSubpackageBidPlan(qqchSubpackageBidPlan);

        //删除分包清单
//        QqchSubpackageInventory qqchSubpackageInventory = new QqchSubpackageInventory();
//        qqchSubpackageInventory.setVersion(version);
//        qqchSubpackageInventoryService.deleteQqchSubpackageInventory(qqchSubpackageInventory);

        //删除人员策划
//        QqchStaffPlan qqchStaffPlan = new QqchStaffPlan();
//        qqchStaffPlan.setVersion(version);
//        qqchStaffPlanService.deleteQqchStaffPlan(qqchStaffPlan);

        //删除设备策划
//        QqchFacilityPlan qqchFacilityPlan = new QqchFacilityPlan();
//        qqchFacilityPlan.setVersion(version);
//        qqchFacilityPlanService.deleteQqchFacilityPlan(qqchFacilityPlan);
    }

    /**
     * 处理分包清单
     * @param version
     * @param qqchSubpackageBidPlan
     * @param inventoryList
     */
//    public void disposeQqchSubpackageInventory(BigDecimal version,QqchSubpackageBidPlan qqchSubpackageBidPlan,List<QqchSubpackageInventory> inventoryList){
//        Long masterId = qqchSubpackageBidPlan.getId();
//        //分包清单
//        List<QqchSubpackageInventory> qqchSubpackageInventoryList = qqchSubpackageBidPlan.getQqchSubpackageInventoryList();
//        //分包清单平铺
//        List<QqchSubpackageInventory> inventoryTileList = this.formatList(
//                qqchSubpackageInventoryList,
//                QqchSubpackageInventory::setId,
//                QqchSubpackageInventory::setPid,
//                QqchSubpackageInventory::setSort,
//                QqchSubpackageInventory::setLeaf,
//                QqchSubpackageInventory::getLevel,
//                QqchSubpackageInventory::setLevel,
//                QqchSubpackageInventory::getMasterContractInventoryCode,
//                QqchSubpackageInventory::setUpMasterContractInventoryCode,
//                QqchSubpackageInventory::getChildren,
//                QqchSubpackageInventory::setChildren);
//
//        for (QqchSubpackageInventory inventory : inventoryTileList) {
//            inventory.setMasterId(masterId);
//            inventory.setVersion(version);
//            inventory.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
//            inventory.setCreateUserName(SecurityUtils.getUserName());
//            inventory.setCreateTime(DateUtils.getNowDate());
//        }
//        inventoryList.addAll(inventoryTileList);
//    }

    /**
     * 树形列表转线性列表，加排序号，加叶子节点，加层级
     * @param source 数据源
     * @param setId 如何设置id
     * @param setPid 如何设置pid
     * @param setSort 如何设置排序号
     * @param setLeaf 如何设置叶子节点
     * @param setLevel 如何设置层级
     * @param getMasterContractInventoryCode 如何获取主合同清单编码
     * @param setUpMasterContractInventoryCode 如何设置上级主合同清单编码
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
//    private <T> List<T> formatList(
//            List<T> source,
//            BiConsumer<T,Long> setId,
//            BiConsumer<T,Long> setPid,
//            BiConsumer<T,Integer> setSort,
//            BiConsumer<T,String> setLeaf,
//            Function<T, Integer> getLevel,
//            BiConsumer<T,Integer> setLevel,
//            Function<T, String> getMasterContractInventoryCode,
//            BiConsumer<T,String> setUpMasterContractInventoryCode,
//            Function<T, List<T>> getChildren,
//            BiConsumer<T, List<T>> setChildren) {
//        List<T> resultList = new ArrayList<>();
//        int sort = 1;
//        for (T node : source) {
//            setSort.accept(node,sort++);
//            setLevel.accept(node,1);
//            recur(node, resultList, setId, setPid, setSort, setLeaf,getLevel, setLevel,getMasterContractInventoryCode, setUpMasterContractInventoryCode, getChildren, setChildren);
//        }
//        return resultList;
//    }

//    private <T> void recur(
//            T node,
//            List<T> resultList,
//            BiConsumer<T,Long> setId,
//            BiConsumer<T,Long> setPid,
//            BiConsumer<T,Integer> setSort,
//            BiConsumer<T,String> setLeaf,
//            Function<T, Integer> getLevel,
//            BiConsumer<T,Integer> setLevel,
//            Function<T, String> getMasterContractInventoryCode,
//            BiConsumer<T,String> setUpMasterContractInventoryCode,
//            Function<T, List<T>> getChildren,
//            BiConsumer<T, List<T>> setChildren) {
//        Long id = IdWorker.createId();
//        int sort = 1;
//        setId.accept(node,id);
//        resultList.add(node);
//
//        Integer parentLevel = getLevel.apply(node);
//        Integer myLevel = parentLevel + 1;
//        String masterContractInventoryCode = getMasterContractInventoryCode.apply(node);
//        List<T> children = getChildren.apply(node);
//        setChildren.accept(node, null);
//
//        if(!CollectionUtils.isEmpty(children)){
//            for (T child : children) {
//                setPid.accept(child,id);
//                setUpMasterContractInventoryCode.accept(child,masterContractInventoryCode);
//                setSort.accept(child,sort++);
//                setLevel.accept(child,myLevel);
//                recur(child, resultList, setId, setPid, setSort, setLeaf,getLevel, setLevel, getMasterContractInventoryCode, setUpMasterContractInventoryCode, getChildren, setChildren);
//            }
//        }else {
//            setLeaf.accept(node, CommonYesNo.YES);
//        }
//    }


    /**
     * 处理人员策划
     * @param version
     * @param qqchSubpackageBidPlan
     * @param staffPlanList
     */
//    public void disposeQqchStaffPlan(BigDecimal version,QqchSubpackageBidPlan qqchSubpackageBidPlan,List<QqchStaffPlan> staffPlanList){
//        Long masterId = qqchSubpackageBidPlan.getId();
//        //人员策划
//        List<QqchStaffPlan> qqchStaffPlanList = qqchSubpackageBidPlan.getQqchStaffPlanList();
//        for (QqchStaffPlan qqchStaffPlan : qqchStaffPlanList) {
//            qqchStaffPlan.setId(IdWorker.createId());
//            qqchStaffPlan.setMasterId(masterId);
//            qqchStaffPlan.setVersion(version);
//            qqchStaffPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
//            qqchStaffPlan.setCreateUserName(SecurityUtils.getUserName());
//            qqchStaffPlan.setCreateTime(DateUtils.getNowDate());
//        }
//        staffPlanList.addAll(qqchStaffPlanList);
//    }

    /**
     * 处理设备策划
     * @param version
     * @param qqchSubpackageBidPlan
     * @param facilityPlanList
     */
//    public void disposeQqchFacilityPlan(BigDecimal version,QqchSubpackageBidPlan qqchSubpackageBidPlan,List<QqchFacilityPlan> facilityPlanList){
//        Long masterId = qqchSubpackageBidPlan.getId();
//        //设备策划
//        List<QqchFacilityPlan> qqchFacilityPlanList = qqchSubpackageBidPlan.getQqchFacilityPlanList();
//        for (QqchFacilityPlan qqchFacilityPlan : qqchFacilityPlanList) {
//            qqchFacilityPlan.setId(IdWorker.createId());
//            qqchFacilityPlan.setMasterId(masterId);
//            qqchFacilityPlan.setVersion(version);
//            qqchFacilityPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
//            qqchFacilityPlan.setCreateUserName(SecurityUtils.getUserName());
//            qqchFacilityPlan.setCreateTime(DateUtils.getNowDate());
//        }
//        facilityPlanList.addAll(qqchFacilityPlanList);
//    }
}
