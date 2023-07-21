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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        if (!CollectionUtils.isEmpty(localWorkerSupplyList)) {
            result.setCurrencyWorker(localWorkerSupplyList.get(0).getCurrency());
        }

        XmslLocalMaterialsSupply xmslLocalMaterialsSupply = new XmslLocalMaterialsSupply();
        List<XmslLocalMaterialsSupply> localMaterialsSupplyList = xmslLocalMaterialsSupplyMapper
            .getXmslLocalMaterialsSupplyList(xmslLocalMaterialsSupply);
        if (!CollectionUtils.isEmpty(localMaterialsSupplyList)) {
            result.setCurrencyMaterials(localMaterialsSupplyList.get(0).getCurrency());
        }

        XmslLocalEquipmentSupply xmslLocalEquipmentSupply = new XmslLocalEquipmentSupply();
        List<XmslLocalEquipmentSupply> localEquipmentSupplyList =
            xmslLocalEquipmentSupplyMapper.getXmslLocalEquipmentSupplyList(xmslLocalEquipmentSupply);
        if (!CollectionUtils.isEmpty(localEquipmentSupplyList)) {
            result.setCurrencyEquipment(localEquipmentSupplyList.get(0).getCurrency());
        }

        result.setLocalWorkerSupplyList(localWorkerSupplyList);
        result.setLocalMaterialsSupplyList(localMaterialsSupplyList);
        result.setLocalEquipmentSupplyList(localEquipmentSupplyList);
        return result;
    }

    @Transactional
    public void save(LocalResourceSupply localResourceSupply) {
        // 先清库旧数据，当地工人供应情况
        XmslLocalWorkerSupply workerDeleteParam = new XmslLocalWorkerSupply();
        workerDeleteParam.setDelFlag("1");
        xmslLocalWorkerSupplyMapper.updateXmslLocalWorkerSupply(workerDeleteParam);

        // 先清库旧数据，当地物资供应情况
        XmslLocalMaterialsSupply materialsDeleteParam = new XmslLocalMaterialsSupply();
        materialsDeleteParam.setDelFlag("1");
        xmslLocalMaterialsSupplyMapper.updateXmslLocalMaterialsSupply(materialsDeleteParam);

        // 先清库旧数据，当地设备供应情况
        XmslLocalEquipmentSupply equipmentDeleteParam = new XmslLocalEquipmentSupply();
        equipmentDeleteParam.setDelFlag("1");
        xmslLocalEquipmentSupplyMapper.updateXmslLocalEquipmentSupply(equipmentDeleteParam);

        // 当地工人供应情况集合
        if (!CollectionUtils.isEmpty(localResourceSupply.getLocalWorkerSupplyList())) {
            for (XmslLocalWorkerSupply work : localResourceSupply.getLocalWorkerSupplyList()) {
                work.setId(IdWorker.createId());
                work.setCurrency(localResourceSupply.getCurrencyWorker());
                work.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                work.setCreateUserName(SecurityUtils.getUserName());
                work.setCreateTime(DateUtils.getNowDate());
            }
            xmslLocalWorkerSupplyMapper.insertXmslLocalWorkerSupplyList(localResourceSupply.getLocalWorkerSupplyList());
        }

        // 当地物资供应情况集合
        if (!CollectionUtils.isEmpty(localResourceSupply.getLocalMaterialsSupplyList())) {
            for (XmslLocalMaterialsSupply materials : localResourceSupply.getLocalMaterialsSupplyList()) {
                materials.setId(IdWorker.createId());
                materials.setCurrency(localResourceSupply.getCurrencyMaterials());
                materials.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                materials.setCreateUserName(SecurityUtils.getUserName());
                materials.setCreateTime(DateUtils.getNowDate());
            }
            xmslLocalMaterialsSupplyMapper
                .insertXmslLocalMaterialsSupplyList(localResourceSupply.getLocalMaterialsSupplyList());
        }

        // 当地设备供应情况集合
        if (!CollectionUtils.isEmpty(localResourceSupply.getLocalEquipmentSupplyList())) {
            for (XmslLocalEquipmentSupply equipment : localResourceSupply.getLocalEquipmentSupplyList()) {
                equipment.setId(IdWorker.createId());
                equipment.setCurrency(localResourceSupply.getCurrencyEquipment());
                equipment.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                equipment.setCreateUserName(SecurityUtils.getUserName());
                equipment.setCreateTime(DateUtils.getNowDate());
            }
            xmslLocalEquipmentSupplyMapper
                .insertXmslLocalEquipmentSupplyList(localResourceSupply.getLocalEquipmentSupplyList());
        }
    }

}
