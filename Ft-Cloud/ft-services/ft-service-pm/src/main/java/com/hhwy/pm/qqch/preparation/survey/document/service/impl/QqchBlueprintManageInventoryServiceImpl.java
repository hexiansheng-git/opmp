package com.hhwy.pm.qqch.preparation.survey.document.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchBlueprintManageInventoryVo;
import com.hhwy.pm.qqch.preparation.survey.document.mapper.QqchBlueprintManageInventoryMapper;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchBlueprintManageInventoryService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
@Service
public class QqchBlueprintManageInventoryServiceImpl implements IQqchBlueprintManageInventoryService {

    @Autowired
    private QqchBlueprintManageInventoryMapper qqchBlueprintManageInventoryMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchBlueprintManageInventory getQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        return qqchBlueprintManageInventoryMapper.getQqchBlueprintManageInventory(qqchBlueprintManageInventory);
    }

    /**
     * 勘察设计图纸管理清单Vo
     * @return
     * @param version
     */
    public QqchBlueprintManageInventoryVo getQqchBlueprintManageInventoryVo(BigDecimal version) {
        QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo = new QqchBlueprintManageInventoryVo();

        version = VersionUtil.getVersion("qqch_blueprint_manage_inventory",version);
        QqchBlueprintManageInventory qqchBlueprintManageInventory = new QqchBlueprintManageInventory();
        qqchBlueprintManageInventory.setVersion(version);
        List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList = qqchBlueprintManageInventoryMapper.getQqchBlueprintManageInventoryList(qqchBlueprintManageInventory);

        qqchBlueprintManageInventoryVo.setVersion(version);
        qqchBlueprintManageInventoryVo.setStageIdentity(qqchReviewService.getStage());
        qqchBlueprintManageInventoryVo.setQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryList);
        return qqchBlueprintManageInventoryVo;
    }

    /**
     * 保存
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo) {
        //删除旧数据
        QqchBlueprintManageInventory qqchBlueprintManageInventory = new QqchBlueprintManageInventory();
        qqchBlueprintManageInventory.setVersion(qqchBlueprintManageInventoryVo.getVersion());
        qqchBlueprintManageInventoryMapper.deleteQqchBlueprintManageInventory(qqchBlueprintManageInventory);

        //插入新数据
        this.insertQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryVo.getQqchBlueprintManageInventoryList(),qqchBlueprintManageInventoryVo.getVersion());
    }

    /**
     * 确认
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
    @Override
    public void confirm(QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo) {
        this.save(qqchBlueprintManageInventoryVo);

        String buttonMark = qqchBlueprintManageInventoryVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchBlueprintManageInventoryVo.getMenuId();
            String stageIdentity = qqchBlueprintManageInventoryVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchBlueprintManageInventoryList
     * @param version
     */
    @Transactional
    public void insertQqchBlueprintManageInventoryList(List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList, BigDecimal version) {
        for (QqchBlueprintManageInventory qqchBlueprintManageInventory : qqchBlueprintManageInventoryList) {
            qqchBlueprintManageInventory.setId(IdWorker.createId());
            qqchBlueprintManageInventory.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchBlueprintManageInventory.setValid(Valid.YES);
            }
            qqchBlueprintManageInventory.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchBlueprintManageInventory.setCreateUserName(SecurityUtils.getUserName());
            qqchBlueprintManageInventory.setCreateTime(DateUtils.getNowDate());
        }
        qqchBlueprintManageInventoryMapper.insertQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryList);
    }
}
