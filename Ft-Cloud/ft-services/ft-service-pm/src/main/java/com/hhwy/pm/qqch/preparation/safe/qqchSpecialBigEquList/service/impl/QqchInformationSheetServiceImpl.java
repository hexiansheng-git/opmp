package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchInformationSheet;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper.QqchInformationSheetMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchInformationSheetService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:36:31
 * @remark
 */
@Service
public class QqchInformationSheetServiceImpl implements IQqchInformationSheetService {

    @Autowired
    private QqchInformationSheetMapper qqchInformationSheetMapper;


    public QqchInformationSheet getQqchInformationSheet(QqchInformationSheet qqchInformationSheet) {
        return qqchInformationSheetMapper.getQqchInformationSheet(qqchInformationSheet);
    }

    public List<QqchInformationSheet> getQqchInformationSheetList(QqchInformationSheet qqchInformationSheet) {
        return qqchInformationSheetMapper.getQqchInformationSheetList(qqchInformationSheet);
    }

    @Transactional
    public int insertQqchInformationSheet(QqchInformationSheet qqchInformationSheet) {
        qqchInformationSheet.setId(IdWorker.createId());
        qqchInformationSheet.setCreateUser(SecurityUtils.getUserName());
        qqchInformationSheet.setCreateTime(DateUtils.getNowDate());
        return qqchInformationSheetMapper.insertQqchInformationSheet(qqchInformationSheet);
    }

    @Transactional
    public int insertQqchInformationSheetList(List<QqchInformationSheet> qqchInformationSheetList) {
        for (QqchInformationSheet qqchInformationSheet : qqchInformationSheetList) {
            qqchInformationSheet.setId(IdWorker.createId());
            qqchInformationSheet.setCreateUser(SecurityUtils.getUserName());
            qqchInformationSheet.setCreateTime(DateUtils.getNowDate());
        }
        return qqchInformationSheetMapper.insertQqchInformationSheetList(qqchInformationSheetList);
    }

    @Transactional
    public int updateQqchInformationSheet(QqchInformationSheet qqchInformationSheet) {
        qqchInformationSheet.setUpdateUser(SecurityUtils.getUserName());
        qqchInformationSheet.setUpdateTime(DateUtils.getNowDate());
        return qqchInformationSheetMapper.updateQqchInformationSheet(qqchInformationSheet);
    }

    @Transactional
    public int updateQqchInformationSheetList(List<QqchInformationSheet> qqchInformationSheetList) {
        for (QqchInformationSheet qqchInformationSheet : qqchInformationSheetList) {
            qqchInformationSheet.setUpdateUser(SecurityUtils.getUserName());
            qqchInformationSheet.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchInformationSheetMapper.updateQqchInformationSheetList(qqchInformationSheetList);
    }

    @Transactional
    public int deleteQqchInformationSheet(QqchInformationSheet qqchInformationSheet) {
        qqchInformationSheet.setUpdateUser(SecurityUtils.getUserName());
        qqchInformationSheet.setUpdateTime(DateUtils.getNowDate());
        return qqchInformationSheetMapper.deleteQqchInformationSheet(qqchInformationSheet);
    }

    @Transactional
    public int deleteQqchInformationSheetByPks(List<Long> qqchInformationSheetPkList) {
        return qqchInformationSheetMapper.deleteQqchInformationSheetByPks(qqchInformationSheetPkList);
    }
}
