package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFileWbs;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.vo.QqchSameEngineerFileVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.mapper.QqchSameEngineerFileMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.IQqchSameEngineerFileService;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.IQqchSameEngineerFileWbsService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:52:57
 * @remark
 */
@Service
public class QqchSameEngineerFileServiceImpl implements IQqchSameEngineerFileService {

    @Autowired
    private QqchSameEngineerFileMapper qqchSameEngineerFileMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchSameEngineerFileWbsService qqchSameEngineerFileWbsService;


    public QqchSameEngineerFile getQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile) {
        return qqchSameEngineerFileMapper.getQqchSameEngineerFile(qqchSameEngineerFile);
    }


    @Transactional
    public int insertQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile) {
        qqchSameEngineerFile.setId(IdWorker.createId());
        qqchSameEngineerFile.setCreateUser(SecurityUtils.getUserName());
        qqchSameEngineerFile.setCreateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileMapper.insertQqchSameEngineerFile(qqchSameEngineerFile);
    }



    @Transactional
    public int updateQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile) {
        qqchSameEngineerFile.setUpdateUser(SecurityUtils.getUserName());
        qqchSameEngineerFile.setUpdateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileMapper.updateQqchSameEngineerFile(qqchSameEngineerFile);
    }

    @Transactional
    public int updateQqchSameEngineerFileList(List<QqchSameEngineerFile> qqchSameEngineerFileList) {
        for (QqchSameEngineerFile qqchSameEngineerFile : qqchSameEngineerFileList) {
            qqchSameEngineerFile.setUpdateUser(SecurityUtils.getUserName());
            qqchSameEngineerFile.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSameEngineerFileMapper.updateQqchSameEngineerFileList(qqchSameEngineerFileList);
    }

    @Transactional
    public int deleteQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile) {
        qqchSameEngineerFile.setUpdateUser(SecurityUtils.getUserName());
        qqchSameEngineerFile.setUpdateTime(DateUtils.getNowDate());
        return qqchSameEngineerFileMapper.deleteQqchSameEngineerFile(qqchSameEngineerFile);
    }

    @Transactional
    public int deleteQqchSameEngineerFileByPks(List<Long> qqchSameEngineerFilePkList) {
        return qqchSameEngineerFileMapper.deleteQqchSameEngineerFileByPks(qqchSameEngineerFilePkList);
    }


    public QqchSameEngineerFileVo getQqchSameEngineerFileList(QqchSameEngineerFile qqchSameEngineerFile) {
        QqchSameEngineerFileVo vo = new QqchSameEngineerFileVo();

        BigDecimal version = qqchSameEngineerFile.getVersion();
        version = VersionUtil.getVersion("qqch_same_engineer_file", version);

        qqchSameEngineerFile.setVersion(version);
        List<QqchSameEngineerFile> qqchSameEngineerFileList = qqchSameEngineerFileMapper.getQqchSameEngineerFileList(qqchSameEngineerFile);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSameEngineerFileList(qqchSameEngineerFileList);
        return vo;
    }

    @Override
    @Transactional
    public void save(QqchSameEngineerFileVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSameEngineerFile> qqchSameEngineerFileList = vo.getQqchSameEngineerFileList();

        this.insertQqchSameEngineerFileList(qqchSameEngineerFileList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchSameEngineerFileList(List<QqchSameEngineerFile> qqchSameEngineerFileList,BigDecimal version) {
        //删除旧数据
        QqchSameEngineerFile qqchSameEngineerFile = new QqchSameEngineerFile();
        qqchSameEngineerFile.setVersion(version);
        qqchSameEngineerFileMapper.deleteQqchSameEngineerFile(qqchSameEngineerFile);

        if (CollectionUtils.isEmpty(qqchSameEngineerFileList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchSameEngineerFile sameEngineerFile : qqchSameEngineerFileList) {
            sameEngineerFile.setId(IdWorker.createId());
            sameEngineerFile.setValid(valid);
            sameEngineerFile.setVersion(version);
            sameEngineerFile.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            sameEngineerFile.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            sameEngineerFile.setCreateTime(DateUtils.getNowDate());
        }
    qqchSameEngineerFileMapper.insertQqchSameEngineerFileList(qqchSameEngineerFileList);
    }

    /**
     *  同步wbs数据
     */
    public  void  syndata(){
        List<XmslWbs> xmslWbs = WbsRedisUtils.allWbs();
        List<QqchSameEngineerFileWbs> list = new ArrayList<>();
        for (XmslWbs xmslWb : xmslWbs) {
            QqchSameEngineerFileWbs qqchSameEngineerFileWbs = new QqchSameEngineerFileWbs();
            if(xmslWb.getId()!=null)
            qqchSameEngineerFileWbs.setId(Long.valueOf(xmslWb.getId()));
            qqchSameEngineerFileWbs.setCode(xmslWb.getCode());
            qqchSameEngineerFileWbs.setName(xmslWb.getName());
            list.add(qqchSameEngineerFileWbs);
        }
        qqchSameEngineerFileWbsService.insertQqchSameEngineerFileWbsList(list);
    }

}
