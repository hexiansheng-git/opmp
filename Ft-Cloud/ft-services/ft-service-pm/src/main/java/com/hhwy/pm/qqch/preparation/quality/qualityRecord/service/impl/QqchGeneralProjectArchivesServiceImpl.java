package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper.QqchGeneralProjectArchivesMapper;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchGeneralProjectArchivesService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:35
 * @remark
 */
@Service
public class QqchGeneralProjectArchivesServiceImpl implements IQqchGeneralProjectArchivesService {

    @Autowired
    private QqchGeneralProjectArchivesMapper qqchGeneralProjectArchivesMapper;


    public QqchGeneralProjectArchives getQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        return qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    public List<QqchGeneralProjectArchives> getQqchGeneralProjectArchivesList(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        return qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchives);
    }

    @Transactional
    public int insertQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setId(IdWorker.createId());
        qqchGeneralProjectArchives.setCreateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setCreateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int insertQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList) {
        for (QqchGeneralProjectArchives qqchGeneralProjectArchives : qqchGeneralProjectArchivesList) {
            qqchGeneralProjectArchives.setId(IdWorker.createId());
            qqchGeneralProjectArchives.setCreateUser(SecurityUtils.getUserName());
            qqchGeneralProjectArchives.setCreateTime(DateUtils.getNowDate());
        }
        return qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesList);
    }

    @Transactional
    public int updateQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.updateQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int updateQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList) {
        for (QqchGeneralProjectArchives qqchGeneralProjectArchives : qqchGeneralProjectArchivesList) {
            qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
            qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGeneralProjectArchivesMapper.updateQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesList);
    }

    @Transactional
    public int deleteQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.deleteQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int deleteQqchGeneralProjectArchivesByPks(List<Long> qqchGeneralProjectArchivesPkList) {
        return qqchGeneralProjectArchivesMapper.deleteQqchGeneralProjectArchivesByPks(qqchGeneralProjectArchivesPkList);
    }
}
