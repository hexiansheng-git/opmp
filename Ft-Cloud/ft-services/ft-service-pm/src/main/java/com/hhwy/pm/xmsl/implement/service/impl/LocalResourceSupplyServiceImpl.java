package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.LocalResourceSupply;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalEquipmentSupply;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalMaterialsSupply;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalWorkerSupply;
import com.hhwy.pm.xmsl.implement.mapper.XmslLocalEquipmentSupplyMapper;
import com.hhwy.pm.xmsl.implement.mapper.XmslLocalMaterialsSupplyMapper;
import com.hhwy.pm.xmsl.implement.mapper.XmslLocalWorkerSupplyMapper;
import com.hhwy.pm.xmsl.implement.service.ILocalResourceSupplyService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-04 13:15:00
 * @remark 当地资源供应
 */
@Service
public class LocalResourceSupplyServiceImpl implements ILocalResourceSupplyService {

    @Autowired
    private XmslLocalWorkerSupplyMapper xmslLocalWorkerSupplyMapper;

    @Autowired
    private XmslLocalMaterialsSupplyMapper xmslLocalMaterialsSupplyMapper;

    @Autowired
    private XmslLocalEquipmentSupplyMapper xmslLocalEquipmentSupplyMapper;

    public LocalResourceSupply getList(LocalResourceSupply localResourceSupply) {

        LocalResourceSupply result = new LocalResourceSupply();

        XmslLocalWorkerSupply xmslLocalWorkerSupply = new XmslLocalWorkerSupply();
        List<XmslLocalWorkerSupply> localWorkerSupplyList = xmslLocalWorkerSupplyMapper
            .getXmslLocalWorkerSupplyList(xmslLocalWorkerSupply);

        XmslLocalMaterialsSupply xmslLocalMaterialsSupply = new XmslLocalMaterialsSupply();
        List<XmslLocalMaterialsSupply> localMaterialsSupplyList = xmslLocalMaterialsSupplyMapper
            .getXmslLocalMaterialsSupplyList(xmslLocalMaterialsSupply);

        XmslLocalEquipmentSupply xmslLocalEquipmentSupply = new XmslLocalEquipmentSupply();
        List<XmslLocalEquipmentSupply> localEquipmentSupplyList =
            xmslLocalEquipmentSupplyMapper.getXmslLocalEquipmentSupplyList(xmslLocalEquipmentSupply);

        result.setLocalWorkerSupplyList(localWorkerSupplyList);
        result.setLocalMaterialsSupplyList(localMaterialsSupplyList);
        result.setLocalEquipmentSupplyList(localEquipmentSupplyList);
        return result;
    }

    @Transactional
    public void save(LocalResourceSupply localResourceSupply) {

        // 当地工人供应情况集合
        if (localResourceSupply.getLocalWorkerSupplyList() != null
            && localResourceSupply.getLocalWorkerSupplyList().size() != 0) {
            List<XmslLocalWorkerSupply> insertWorkerList = new ArrayList<>();
            List<XmslLocalWorkerSupply> updateWorkerList = new ArrayList<>();
            for (XmslLocalWorkerSupply work : localResourceSupply.getLocalWorkerSupplyList()) {
                if (work.getId() == null) {
                    work.setId(IdWorker.createId());
                    work.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    work.setCreateUserName(SecurityUtils.getUserName());
                    work.setCreateTime(DateUtils.getNowDate());
                    insertWorkerList.add(work);
                } else {
                    work.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    work.setUpdateTime(DateUtils.getNowDate());
                    updateWorkerList.add(work);
                }
            }
            if (insertWorkerList.size() > 0) {
                xmslLocalWorkerSupplyMapper.insertXmslLocalWorkerSupplyList(insertWorkerList);
            }
            if (updateWorkerList.size() > 0) {
                xmslLocalWorkerSupplyMapper.updateXmslLocalWorkerSupplyList(updateWorkerList);
            }

        }

        // 当地物资供应情况集合
        if (localResourceSupply.getLocalMaterialsSupplyList() != null
            && localResourceSupply.getLocalMaterialsSupplyList().size() != 0) {
            List<XmslLocalMaterialsSupply> insertMaterialsList = new ArrayList<>();
            List<XmslLocalMaterialsSupply> updateMaterialsList = new ArrayList<>();
            for (XmslLocalMaterialsSupply materials : localResourceSupply.getLocalMaterialsSupplyList()) {
                if (materials.getId() == null) {
                    materials.setId(IdWorker.createId());
                    materials.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    materials.setCreateTime(DateUtils.getNowDate());
                    insertMaterialsList.add(materials);
                } else {
                    materials.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    materials.setUpdateTime(DateUtils.getNowDate());
                    updateMaterialsList.add(materials);
                }
            }
            if (insertMaterialsList.size() > 0) {
                xmslLocalMaterialsSupplyMapper.insertXmslLocalMaterialsSupplyList(insertMaterialsList);
            }
            if (updateMaterialsList.size() > 0) {
                xmslLocalMaterialsSupplyMapper.updateXmslLocalMaterialsSupplyList(updateMaterialsList);
            }
        }

        // 当地设备供应情况集合
        if (localResourceSupply.getLocalEquipmentSupplyList() != null
            && localResourceSupply.getLocalEquipmentSupplyList().size() != 0) {
            List<XmslLocalEquipmentSupply> insertEquipmentList = new ArrayList<>();
            List<XmslLocalEquipmentSupply> updateEquipmentList = new ArrayList<>();
            for (XmslLocalEquipmentSupply equipment : localResourceSupply.getLocalEquipmentSupplyList()) {
                if (equipment.getId() == null) {
                    equipment.setId(IdWorker.createId());
                    equipment.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    equipment.setCreateTime(DateUtils.getNowDate());
                    insertEquipmentList.add(equipment);
                } else {
                    equipment.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    equipment.setUpdateTime(DateUtils.getNowDate());
                    updateEquipmentList.add(equipment);
                }
            }
            if (insertEquipmentList.size() > 0) {
                xmslLocalEquipmentSupplyMapper.insertXmslLocalEquipmentSupplyList(insertEquipmentList);
            }
            if (updateEquipmentList.size() > 0) {
                xmslLocalEquipmentSupplyMapper.updateXmslLocalEquipmentSupplyList(updateEquipmentList);
            }
        }
    }

}
