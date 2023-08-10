package com.hhwy.pm.qqch.preparation.costControl.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchKeyInventoryContent;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo.QqchKeyInventoryContentVo;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper.QqchKeyInventoryContentMapper;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchKeyInventoryContentService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.DataCheckUtil;
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
 * @date 2023-08-03 13:34:18
 * @remark
 */
@Service
public class QqchKeyInventoryContentServiceImpl implements IQqchKeyInventoryContentService {

    @Autowired
    private QqchKeyInventoryContentMapper qqchKeyInventoryContentMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchKeyInventoryContent getQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent) {
        return qqchKeyInventoryContentMapper.getQqchKeyInventoryContent(qqchKeyInventoryContent);
    }

    public List<QqchKeyInventoryContent> getQqchKeyInventoryContentList(QqchKeyInventoryContent qqchKeyInventoryContent) {
        return qqchKeyInventoryContentMapper.getQqchKeyInventoryContentList(qqchKeyInventoryContent);
    }

    @Transactional
    public int insertQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent) {
        qqchKeyInventoryContent.setId(IdWorker.createId());
        qqchKeyInventoryContent.setCreateUser(SecurityUtils.getUserName());
        qqchKeyInventoryContent.setCreateTime(DateUtils.getNowDate());
        return qqchKeyInventoryContentMapper.insertQqchKeyInventoryContent(qqchKeyInventoryContent);
    }

    @Transactional
    public void insertQqchKeyInventoryContentList(List<QqchKeyInventoryContent> qqchKeyInventoryContentList, BigDecimal version) {
        //删除旧数据
        QqchKeyInventoryContent qqchKeyInventoryContent = new QqchKeyInventoryContent();
        qqchKeyInventoryContent.setVersion(version);
        qqchKeyInventoryContentMapper.deleteQqchKeyInventoryContent(qqchKeyInventoryContent);

        if(CollectionUtils.isEmpty(qqchKeyInventoryContentList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchKeyInventoryContent keyInventoryContent : qqchKeyInventoryContentList) {
            keyInventoryContent.setValid(valid);
            keyInventoryContent.setVersion(version);
            keyInventoryContent.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            keyInventoryContent.setCreateUserName(SecurityUtils.getUserName());
            keyInventoryContent.setCreateTime(DateUtils.getNowDate());
        }
        qqchKeyInventoryContentMapper.insertQqchKeyInventoryContentList(qqchKeyInventoryContentList);
    }

    @Transactional
    public int updateQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent) {
        qqchKeyInventoryContent.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyInventoryContent.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyInventoryContentMapper.updateQqchKeyInventoryContent(qqchKeyInventoryContent);
    }

    @Transactional
    public int updateQqchKeyInventoryContentList(List<QqchKeyInventoryContent> qqchKeyInventoryContentList) {
        for (QqchKeyInventoryContent qqchKeyInventoryContent : qqchKeyInventoryContentList) {
            qqchKeyInventoryContent.setUpdateUser(SecurityUtils.getUserName());
            qqchKeyInventoryContent.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchKeyInventoryContentMapper.updateQqchKeyInventoryContentList(qqchKeyInventoryContentList);
    }

    @Transactional
    public int deleteQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent) {
        qqchKeyInventoryContent.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyInventoryContent.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyInventoryContentMapper.deleteQqchKeyInventoryContent(qqchKeyInventoryContent);
    }

    @Transactional
    public int deleteQqchKeyInventoryContentByPks(List<Long> qqchKeyInventoryContentPkList) {
        return qqchKeyInventoryContentMapper.deleteQqchKeyInventoryContentByPks(qqchKeyInventoryContentPkList);
    }

    /**
     * 获取须重点关注的清单及内容Vo
     * @param qqchKeyInventoryContent
     * @return
     */
    @Override
    public QqchKeyInventoryContentVo getQqchKeyInventoryContentVo(QqchKeyInventoryContent qqchKeyInventoryContent) {
        QqchKeyInventoryContentVo qqchKeyInventoryContentVo = new QqchKeyInventoryContentVo();

        BigDecimal version = qqchKeyInventoryContent.getVersion();
        version = VersionUtil.getVersion("qqch_key_inventory_content",version);

        qqchKeyInventoryContent.setVersion(version);
        List<QqchKeyInventoryContent> qqchKeyInventoryContentList = qqchKeyInventoryContentMapper.getQqchKeyInventoryContentList(qqchKeyInventoryContent);

        //转树列表
        List<QqchKeyInventoryContent> treeList = ListTreeUtil.formatTree(
                qqchKeyInventoryContentList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchKeyInventoryContent::getChildren,
                QqchKeyInventoryContent::setChildren);

        qqchKeyInventoryContentVo.setVersion(version);
        qqchKeyInventoryContentVo.setStageIdentity(qqchReviewService.getStage());
        qqchKeyInventoryContentVo.setList(treeList);
        return qqchKeyInventoryContentVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchKeyInventoryContentVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchKeyInventoryContentVo qqchKeyInventoryContentVo) {
        String buttonMark = qqchKeyInventoryContentVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchKeyInventoryContentVo.getVersion();
        List<QqchKeyInventoryContent> qqchKeyInventoryContentList = qqchKeyInventoryContentVo.getList();

        List<QqchKeyInventoryContent> tileList = ListTreeUtil.formatList(
                qqchKeyInventoryContentList,
                QqchKeyInventoryContent::setId,
                QqchKeyInventoryContent::setPid,
                QqchKeyInventoryContent::setSort,
                QqchKeyInventoryContent::setLeaf,
                QqchKeyInventoryContent::getChildren,
                QqchKeyInventoryContent::setChildren);

        //校验唯一
        DataCheckUtil.checkSingle(tileList,QqchKeyInventoryContent::getInventoryCode);

        //处理数据
        this.insertQqchKeyInventoryContentList(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchKeyInventoryContentVo.getMenuId();
            String stageIdentity = qqchKeyInventoryContentVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
