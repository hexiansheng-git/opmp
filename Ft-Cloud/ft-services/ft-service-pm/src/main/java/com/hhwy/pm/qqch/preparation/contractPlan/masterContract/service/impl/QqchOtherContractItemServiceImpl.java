package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchOtherContractItem;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchOtherContractItemVo;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.mapper.QqchOtherContractItemMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.IQqchOtherContractItemService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析
 */
@Service
public class QqchOtherContractItemServiceImpl implements IQqchOtherContractItemService {

    @Autowired
    private QqchOtherContractItemMapper qqchOtherContractItemMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchOtherContractItem getQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        return qqchOtherContractItemMapper.getQqchOtherContractItem(qqchOtherContractItem);
    }

    public List<QqchOtherContractItem> getQqchOtherContractItemList(QqchOtherContractItem qqchOtherContractItem) {
        return qqchOtherContractItemMapper.getQqchOtherContractItemList(qqchOtherContractItem);
    }

    @Transactional
    public int insertQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setId(IdWorker.createId());
        qqchOtherContractItem.setCreateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setCreateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.insertQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public void insertQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList, BigDecimal version) {
        //删除旧数据
        QqchOtherContractItem qqchOtherContractItem = new QqchOtherContractItem();
        qqchOtherContractItem.setVersion(version);
        qqchOtherContractItemMapper.deleteQqchOtherContractItem(qqchOtherContractItem);

        if(CollectionUtils.isEmpty(qqchOtherContractItemList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchOtherContractItem otherContractItem : qqchOtherContractItemList) {
            otherContractItem.setValid(valid);
            otherContractItem.setVersion(version);
            otherContractItem.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            otherContractItem.setCreateUserName(SecurityUtils.getUserName());
            otherContractItem.setCreateTime(DateUtils.getNowDate());
        }
       qqchOtherContractItemMapper.insertQqchOtherContractItemList(qqchOtherContractItemList);
    }

    @Transactional
    public int updateQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.updateQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public int updateQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList) {
        for (QqchOtherContractItem qqchOtherContractItem : qqchOtherContractItemList) {
            qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
            qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOtherContractItemMapper.updateQqchOtherContractItemList(qqchOtherContractItemList);
    }

    @Transactional
    public int deleteQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.deleteQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public int deleteQqchOtherContractItemByPks(List<Long> qqchOtherContractItemPkList) {
        return qqchOtherContractItemMapper.deleteQqchOtherContractItemByPks(qqchOtherContractItemPkList);
    }

    /**
     * 获取其他合同事项分析Vo
     * @param qqchOtherContractItem
     * @return
     */
    @Override
    public QqchOtherContractItemVo getQqchOtherContractItemVo(QqchOtherContractItem qqchOtherContractItem) {
        QqchOtherContractItemVo qqchOtherContractItemVo = new QqchOtherContractItemVo();

        BigDecimal version = qqchOtherContractItem.getVersion();
        version = VersionUtil.getVersion("qqch_other_contract_item",version);

        qqchOtherContractItem.setVersion(version);
        List<QqchOtherContractItem> qqchOtherContractItemList = qqchOtherContractItemMapper.getQqchOtherContractItemList(qqchOtherContractItem);

        //转树列表
        List<QqchOtherContractItem> treeList = ListTreeUtil.formatTree(
                qqchOtherContractItemList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchOtherContractItem::getChildren,
                QqchOtherContractItem::setChildren);

        qqchOtherContractItemVo.setVersion(version);
        qqchOtherContractItemVo.setStageIdentity(qqchReviewService.getStage());
        qqchOtherContractItemVo.setList(treeList);
        return qqchOtherContractItemVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchOtherContractItemVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchOtherContractItemVo qqchOtherContractItemVo) {
        String buttonMark = qqchOtherContractItemVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchOtherContractItemVo.getVersion();
        List<QqchOtherContractItem> qqchOtherContractItemList = qqchOtherContractItemVo.getList();

        List<QqchOtherContractItem> tileList = ListTreeUtil.formatList(
                qqchOtherContractItemList,
                QqchOtherContractItem::setId,
                QqchOtherContractItem::setPid,
                QqchOtherContractItem::setSort,
                QqchOtherContractItem::setLeaf,
                QqchOtherContractItem::getChildren,
                QqchOtherContractItem::setChildren);

        //校验非空
        if(!ButtonMark.SAVE.equals(buttonMark)){
            JyDetailsUtil.jyDetails(tileList, QqchOtherContractItem::getLeaf, ValidationGroups.Save.class);
        }

        //处理数据
        this.insertQqchOtherContractItemList(qqchOtherContractItemList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchOtherContractItemVo.getMenuId();
            String stageIdentity = qqchOtherContractItemVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }

    }
}
