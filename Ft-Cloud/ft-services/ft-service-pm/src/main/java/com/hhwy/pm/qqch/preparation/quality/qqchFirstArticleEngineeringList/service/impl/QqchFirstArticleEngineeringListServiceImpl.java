package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo.QqchFirstArticleEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.mapper.QqchFirstArticleEngineeringListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service.IQqchFirstArticleEngineeringListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 16:09:42
 * @remark
 */
@Service
public class QqchFirstArticleEngineeringListServiceImpl implements IQqchFirstArticleEngineeringListService {

    @Autowired
    private QqchFirstArticleEngineeringListMapper qqchFirstArticleEngineeringListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchFirstArticleEngineeringList getQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        return qqchFirstArticleEngineeringListMapper.getQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }
    

    @Transactional
    public int insertQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setId(IdWorker.createId());
        qqchFirstArticleEngineeringList.setCreateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setCreateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.insertQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }



    @Transactional
    public int updateQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.updateQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }

    @Transactional
    public int updateQqchFirstArticleEngineeringListList(List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList) {
        for (QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList : qqchFirstArticleEngineeringListList) {
            qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
            qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchFirstArticleEngineeringListMapper.updateQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        qqchFirstArticleEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringListByPks(List<Long> qqchFirstArticleEngineeringListPkList) {
        return qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringListByPks(qqchFirstArticleEngineeringListPkList);
    }

    /**
     *  列表接口
     * @param qqchFirstArticleEngineeringList
     * @return
     */
    public QqchFirstArticleEngineeringListVo getQqchFirstArticleEngineeringListList(QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList) {
        QqchFirstArticleEngineeringListVo vo = new QqchFirstArticleEngineeringListVo();

        BigDecimal version = qqchFirstArticleEngineeringList.getVersion();
        version = VersionUtil.getVersion("qqch_first_article_engineering_list", version);

        qqchFirstArticleEngineeringList.setVersion(version);
        List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList = qqchFirstArticleEngineeringListMapper.getQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
        return vo;
    }


    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchFirstArticleEngineeringListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList = vo.getQqchFirstArticleEngineeringListList();

        this.insertQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void insertQqchFirstArticleEngineeringListList(List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListList,BigDecimal version) {
        //删除旧数据
        QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList = new QqchFirstArticleEngineeringList();
        qqchFirstArticleEngineeringList.setVersion(version);
        qqchFirstArticleEngineeringListMapper.deleteQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringList);

        if (CollectionUtils.isEmpty(qqchFirstArticleEngineeringListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchFirstArticleEngineeringList firstArticleEngineeringList : qqchFirstArticleEngineeringListList) {
            firstArticleEngineeringList.setId(IdWorker.createId());
            firstArticleEngineeringList.setValid(valid);
            firstArticleEngineeringList.setVersion(version);
            firstArticleEngineeringList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            firstArticleEngineeringList.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            firstArticleEngineeringList.setCreateTime(DateUtils.getNowDate());
        }
        qqchFirstArticleEngineeringListMapper.insertQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListList);
    }
}
