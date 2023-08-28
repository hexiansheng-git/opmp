package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbs;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbsVo;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper.QqchGeneralProjectArchivesMapper;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchGeneralProjectArchivesService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private IXmslWbsService xmslWbsService;


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

    /**
     * 获取台账Vo
     * @param qqchGeneralProjectArchives
     * @return
     */
    @Override
    public GeneralProjectArchivesWbsVo getGeneralProjectArchivesWbsVo(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo = new GeneralProjectArchivesWbsVo();

        //获取最顶级的wbs
        List<XmslWbs> wbsList = xmslWbsService.latestData(new XmslWbs());

        //获取一般工程档案清单
        BigDecimal version = qqchGeneralProjectArchives.getVersion();
        version = VersionUtil.getVersion("qqch_complete_design_handover",version);
        qqchGeneralProjectArchives.setVersion(version);
        List<QqchGeneralProjectArchives> generalProjectArchivesList = qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchives);

        List<GeneralProjectArchivesWbs> list = new ArrayList<>();
        for (XmslWbs wbs : wbsList) {
            GeneralProjectArchivesWbs generalProjectArchivesWbs = new GeneralProjectArchivesWbs();

            generalProjectArchivesWbs.setId(Long.valueOf(wbs.getId()));
            generalProjectArchivesWbs.setPid(Long.valueOf(wbs.getParentId()));
            generalProjectArchivesWbs.setWbsCode(wbs.getCode());
            generalProjectArchivesWbs.setWbsName(wbs.getName());

            List<QqchGeneralProjectArchives> sublist = new ArrayList<>();
            for (QqchGeneralProjectArchives generalProjectArchives : generalProjectArchivesList) {
//                if()
            }
        }
        return generalProjectArchivesWbsVo;
    }
}
