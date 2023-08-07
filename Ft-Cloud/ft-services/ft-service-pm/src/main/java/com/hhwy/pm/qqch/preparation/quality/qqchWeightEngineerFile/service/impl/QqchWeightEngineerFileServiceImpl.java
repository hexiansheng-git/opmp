package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.QqchWeightEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.vo.QqchWeightEngineerFileVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.mapper.QqchWeightEngineerFileMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.service.IQqchWeightEngineerFileService;
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
 * @date 2023-08-07 09:51:59
 * @remark
 */
@Service
public class QqchWeightEngineerFileServiceImpl implements IQqchWeightEngineerFileService {

    @Autowired
    private QqchWeightEngineerFileMapper qqchWeightEngineerFileMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    public QqchWeightEngineerFile getQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile) {
        return qqchWeightEngineerFileMapper.getQqchWeightEngineerFile(qqchWeightEngineerFile);
    }


    @Transactional
    public int insertQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile) {
        qqchWeightEngineerFile.setId(IdWorker.createId());
        qqchWeightEngineerFile.setCreateUser(SecurityUtils.getUserName());
        qqchWeightEngineerFile.setCreateTime(DateUtils.getNowDate());
        return qqchWeightEngineerFileMapper.insertQqchWeightEngineerFile(qqchWeightEngineerFile);
    }



    @Transactional
    public int updateQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile) {
        qqchWeightEngineerFile.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineerFile.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineerFileMapper.updateQqchWeightEngineerFile(qqchWeightEngineerFile);
    }

    @Transactional
    public int updateQqchWeightEngineerFileList(List<QqchWeightEngineerFile> qqchWeightEngineerFileList) {
        for (QqchWeightEngineerFile qqchWeightEngineerFile : qqchWeightEngineerFileList) {
            qqchWeightEngineerFile.setUpdateUser(SecurityUtils.getUserName());
            qqchWeightEngineerFile.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWeightEngineerFileMapper.updateQqchWeightEngineerFileList(qqchWeightEngineerFileList);
    }

    @Transactional
    public int deleteQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile) {
        qqchWeightEngineerFile.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineerFile.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineerFileMapper.deleteQqchWeightEngineerFile(qqchWeightEngineerFile);
    }

    @Transactional
    public int deleteQqchWeightEngineerFileByPks(List<Long> qqchWeightEngineerFilePkList) {
        return qqchWeightEngineerFileMapper.deleteQqchWeightEngineerFileByPks(qqchWeightEngineerFilePkList);
    }

    /**
     * 列表接口
     * @param qqchWeightEngineerFile
     * @return
     */
    public QqchWeightEngineerFileVo getQqchWeightEngineerFileList(QqchWeightEngineerFile qqchWeightEngineerFile) {
        QqchWeightEngineerFileVo vo = new QqchWeightEngineerFileVo();

        BigDecimal version = qqchWeightEngineerFile.getVersion();
        version = VersionUtil.getVersion("qqch_weight_engineer_file", version);

        qqchWeightEngineerFile.setVersion(version);
        List<QqchWeightEngineerFile> qqchWeightEngineerFileList = qqchWeightEngineerFileMapper.getQqchWeightEngineerFileList(qqchWeightEngineerFile);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchWeightEngineerFileList(qqchWeightEngineerFileList);
        return vo;
    }


    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchWeightEngineerFileVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchWeightEngineerFile> qqchWeightEngineerFileList = vo.getQqchWeightEngineerFileList();

        this.insertQqchWeightEngineerFileList(qqchWeightEngineerFileList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void insertQqchWeightEngineerFileList(List<QqchWeightEngineerFile> qqchWeightEngineerFileList,BigDecimal version) {
        //删除旧数据
        QqchWeightEngineerFile qqchWeightEngineerFile = new QqchWeightEngineerFile();
        qqchWeightEngineerFile.setVersion(version);
        qqchWeightEngineerFileMapper.deleteQqchWeightEngineerFile(qqchWeightEngineerFile);

        if (CollectionUtils.isEmpty(qqchWeightEngineerFileList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchWeightEngineerFile weightEngineerFile : qqchWeightEngineerFileList) {
            weightEngineerFile.setId(IdWorker.createId());
            weightEngineerFile.setValid(valid);
            weightEngineerFile.setVersion(version);
            weightEngineerFile.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            weightEngineerFile.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            weightEngineerFile.setCreateTime(DateUtils.getNowDate());
        }
         qqchWeightEngineerFileMapper.insertQqchWeightEngineerFileList(qqchWeightEngineerFileList);
    }
}
