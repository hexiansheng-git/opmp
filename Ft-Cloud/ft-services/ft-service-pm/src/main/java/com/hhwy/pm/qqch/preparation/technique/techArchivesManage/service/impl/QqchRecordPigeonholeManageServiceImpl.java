package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo.QqchRecordPigeonholeManageVo;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper.QqchRecordPigeonholeManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchRecordPigeonholeManageService;
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
 * @date 2023-07-25 10:49:08
 * @remark 技术档案归档管理
 */
@Service
public class QqchRecordPigeonholeManageServiceImpl implements IQqchRecordPigeonholeManageService {

    @Autowired
    private QqchRecordPigeonholeManageMapper qqchRecordPigeonholeManageMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchRecordPigeonholeManage getQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        return qqchRecordPigeonholeManageMapper.getQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    public List<QqchRecordPigeonholeManage> getQqchRecordPigeonholeManageList(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        return qqchRecordPigeonholeManageMapper.getQqchRecordPigeonholeManageList(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int insertQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setId(IdWorker.createId());
        qqchRecordPigeonholeManage.setCreateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setCreateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.insertQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public void insertQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList, BigDecimal version) {
        //删除旧数据
        QqchRecordPigeonholeManage qqchRecordPigeonholeManage = new QqchRecordPigeonholeManage();
        qqchRecordPigeonholeManage.setVersion(version);
        qqchRecordPigeonholeManageMapper.deleteQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);

        if(CollectionUtils.isEmpty(qqchRecordPigeonholeManageList)){
            return;
        }
        List<QqchRecordPigeonholeManage> insertList = ListTreeUtil.formatList(
                qqchRecordPigeonholeManageList,
                QqchRecordPigeonholeManage::setId,
                QqchRecordPigeonholeManage::setPid,
                QqchRecordPigeonholeManage::setSort,
                QqchRecordPigeonholeManage::getChildren,
                QqchRecordPigeonholeManage::setChildren);

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchRecordPigeonholeManage recordPigeonholeManage : insertList) {
            recordPigeonholeManage.setValid(valid);
            recordPigeonholeManage.setVersion(version);
            recordPigeonholeManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            recordPigeonholeManage.setCreateUserName(SecurityUtils.getUserName());
            recordPigeonholeManage.setCreateTime(DateUtils.getNowDate());
        }
        qqchRecordPigeonholeManageMapper.insertQqchRecordPigeonholeManageList(insertList);
    }

    @Transactional
    public int updateQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.updateQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int updateQqchRecordPigeonholeManageList(List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList) {
        for (QqchRecordPigeonholeManage qqchRecordPigeonholeManage : qqchRecordPigeonholeManageList) {
            qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
            qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchRecordPigeonholeManageMapper.updateQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageList);
    }

    @Transactional
    public int deleteQqchRecordPigeonholeManage(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        qqchRecordPigeonholeManage.setUpdateUser(SecurityUtils.getUserName());
        qqchRecordPigeonholeManage.setUpdateTime(DateUtils.getNowDate());
        return qqchRecordPigeonholeManageMapper.deleteQqchRecordPigeonholeManage(qqchRecordPigeonholeManage);
    }

    @Transactional
    public int deleteQqchRecordPigeonholeManageByPks(List<Long> qqchRecordPigeonholeManagePkList) {
        return qqchRecordPigeonholeManageMapper.deleteQqchRecordPigeonholeManageByPks(qqchRecordPigeonholeManagePkList);
    }

    /**
     * 获取技术档案归档管理Vo
     * @param qqchRecordPigeonholeManage
     * @return
     */
    @Override
    public QqchRecordPigeonholeManageVo getQqchRecordPigeonholeManageVo(QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        QqchRecordPigeonholeManageVo qqchRecordPigeonholeManageVo = new QqchRecordPigeonholeManageVo();

        BigDecimal version = qqchRecordPigeonholeManage.getVersion();
        version = VersionUtil.getVersion("qqch_record_pigeonhole_manage",version);

        qqchRecordPigeonholeManage.setVersion(version);
        List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList = qqchRecordPigeonholeManageMapper.getQqchRecordPigeonholeManageList(qqchRecordPigeonholeManage);

        //转换树列表
        List<QqchRecordPigeonholeManage> treeList = ListTreeUtil.formatTree(
                qqchRecordPigeonholeManageList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchRecordPigeonholeManage::getChildren,
                QqchRecordPigeonholeManage::setChildren);

        qqchRecordPigeonholeManageVo.setVersion(version);
        qqchRecordPigeonholeManageVo.setStageIdentity(qqchReviewService.getStage());
        qqchRecordPigeonholeManageVo.setList(treeList);
        return qqchRecordPigeonholeManageVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchRecordPigeonholeManageVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchRecordPigeonholeManageVo qqchRecordPigeonholeManageVo) {
        String buttonMark = qqchRecordPigeonholeManageVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchRecordPigeonholeManageVo.getVersion();
        List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList = qqchRecordPigeonholeManageVo.getList();

        //处理数据
        this.insertQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchRecordPigeonholeManageVo.getMenuId();
            String stageIdentity = qqchRecordPigeonholeManageVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
