package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.QqchSubpackageBidPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.QqchSubpackageInventory;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.vo.SubpackageInventoryCollectVo;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.mapper.QqchSubpackageBidPlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.mapper.QqchSubpackageInventoryMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service.IQqchSubpackageInventoryService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:47
 * @remark
 */
@Service
public class QqchSubpackageInventoryServiceImpl implements IQqchSubpackageInventoryService {

    @Autowired
    private QqchSubpackageInventoryMapper qqchSubpackageInventoryMapper;

    @Autowired
    private QqchSubpackageBidPlanMapper qqchSubpackageBidPlanMapper;


    public QqchSubpackageInventory getQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        return qqchSubpackageInventoryMapper.getQqchSubpackageInventory(qqchSubpackageInventory);
    }

    public List<QqchSubpackageInventory> getQqchSubpackageInventoryList(QqchSubpackageInventory qqchSubpackageInventory) {
        return qqchSubpackageInventoryMapper.getQqchSubpackageInventoryList(qqchSubpackageInventory);
    }

    @Transactional
    public int insertQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setId(IdWorker.createId());
        qqchSubpackageInventory.setCreateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setCreateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.insertQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int insertQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList) {
        for (QqchSubpackageInventory qqchSubpackageInventory : qqchSubpackageInventoryList) {
            qqchSubpackageInventory.setId(IdWorker.createId());
            qqchSubpackageInventory.setCreateUser(SecurityUtils.getUserName());
            qqchSubpackageInventory.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageInventoryMapper.insertQqchSubpackageInventoryList(qqchSubpackageInventoryList);
    }

    @Transactional
    public int updateQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.updateQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int updateQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList) {
        for (QqchSubpackageInventory qqchSubpackageInventory : qqchSubpackageInventoryList) {
            qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
            qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageInventoryMapper.updateQqchSubpackageInventoryList(qqchSubpackageInventoryList);
    }

    @Transactional
    public int deleteQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.deleteQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int deleteQqchSubpackageInventoryByPks(List<Long> qqchSubpackageInventoryPkList) {
        return qqchSubpackageInventoryMapper.deleteQqchSubpackageInventoryByPks(qqchSubpackageInventoryPkList);
    }

    /**
     * 分包清单汇总
     * @param qqchSubpackageInventory
     * @return
     */
    @Override
    public List<SubpackageInventoryCollectVo> getSubpackageInventoryCollectVoList(QqchSubpackageInventory qqchSubpackageInventory) {
        List<SubpackageInventoryCollectVo> resultList = new ArrayList<>();

        BigDecimal version = qqchSubpackageInventory.getVersion();
        version = VersionUtil.getVersion("qqch_subpackage_bid_plan",version);
        //获取所有二级分包清单
        qqchSubpackageInventory.setVersion(version);
        qqchSubpackageInventory.setLevel(2);
        List<QqchSubpackageInventory> secondLevelInventoryList = qqchSubpackageInventoryMapper.getQqchSubpackageInventoryList(qqchSubpackageInventory);

        if(CollectionUtils.isEmpty(secondLevelInventoryList)){
            return resultList;
        }

        //获取所有三级分包清单
        qqchSubpackageInventory.setLevel(3);
        List<QqchSubpackageInventory> thirdLevelInventoryList = qqchSubpackageInventoryMapper.getQqchSubpackageInventoryList(qqchSubpackageInventory);

        //获取分包招标策划集合
        QqchSubpackageBidPlan qqchSubpackageBidPlan = new QqchSubpackageBidPlan();
        qqchSubpackageBidPlan.setVersion(version);
        List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList = qqchSubpackageBidPlanMapper.getQqchSubpackageBidPlanList(qqchSubpackageBidPlan);


        for (QqchSubpackageInventory inventory : secondLevelInventoryList) {
            Long masterId = inventory.getMasterId();
            String masterContractInventoryCode = inventory.getMasterContractInventoryCode();
            SubpackageInventoryCollectVo collectVo = new SubpackageInventoryCollectVo();
            collectVo.setSubpackageInventoryCode(inventory.getMasterContractInventoryCode());
            collectVo.setSubpackageInventoryName(inventory.getMasterContractInventoryName());

            /*设置二级班组名称*/
            StringBuilder className = new StringBuilder();
            for (QqchSubpackageBidPlan subpackageBidPlan : qqchSubpackageBidPlanList) {
                if(masterId.equals(subpackageBidPlan.getId())){
                    className.append(subpackageBidPlan.getName()).append("、");
                }
            }
            collectVo.setClassName(className.toString());

            /*设置三级子表*/
            List<SubpackageInventoryCollectVo> thirdLevelCollectVoList = new ArrayList<>();
            for (QqchSubpackageInventory subpackageInventory : thirdLevelInventoryList) {
                if(masterContractInventoryCode.equals(subpackageInventory.getUpMasterContractInventoryCode())){
                    SubpackageInventoryCollectVo thirdCollectVo = new SubpackageInventoryCollectVo();
                    thirdCollectVo.setSubpackageInventoryCode(subpackageInventory.getSubpackageInventoryCode());
                    thirdCollectVo.setSubpackageInventoryName(subpackageInventory.getSubpackageInventoryName());
                    thirdCollectVo.setUnits(subpackageInventory.getUnits());
                    thirdCollectVo.setSubpackageGuidePrice(subpackageInventory.getSubpackageGuidePrice());
                    thirdLevelCollectVoList.add(thirdCollectVo);
                }
            }
            collectVo.setChildren(thirdLevelCollectVoList);
            resultList.add(collectVo);
        }

        return resultList;
    }
}
