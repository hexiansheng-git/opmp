package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Service
public class QqchComparisonSchemeServiceImpl implements IQqchComparisonSchemeService {

    @Autowired
    private QqchComparisonSchemeMapper qqchComparisonSchemeMapper;

    @Autowired
    private QqchComparisonSchemeHeaderServiceImpl qqchComparisonSchemeHeaderService;

    @Autowired
    private QqchComparisonSchemeHeaderMapper qqchComparisonSchemeHeaderMapper;

    @Autowired
    private QqchComparisonSchemeContentMapper qqchComparisonSchemeContentMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    /**
     * 获取方案集合
     * @return
     * @param version
     */
    @Override
    public QqchComparisonSchemeVo getQqchComparisonSchemeVo(BigDecimal version) {
        QqchComparisonSchemeVo qqchComparisonSchemeVo = new QqchComparisonSchemeVo();

        version = VersionUtil.getVersion("qqch_comparison_scheme",version);
        qqchComparisonSchemeVo.setVersion(version);

        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeMapper.getQqchComparisonSchemeList(version);

        //获取所有表头
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeaderList(version);

        //获取所有单元格
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContentList(version);

        //表头单元格组装
        for (QqchComparisonSchemeHeader comparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            Long id = comparisonSchemeHeader.getId();
            List<QqchComparisonSchemeContent> contentList = new ArrayList<>();
            for (QqchComparisonSchemeContent comparisonSchemeContent : qqchComparisonSchemeContentList) {
                Long headerId = comparisonSchemeContent.getHeaderId();
                if(headerId.equals(id)){
                    contentList.add(comparisonSchemeContent);
                }
            }
            comparisonSchemeHeader.setQqchComparisonSchemeContentList(contentList);
        }

        //方案组装表头
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            Long id = qqchComparisonScheme.getId();
            List<QqchComparisonSchemeHeader> headerList = new ArrayList<>();
            for (QqchComparisonSchemeHeader comparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
                Long schemeId = comparisonSchemeHeader.getSchemeId();
                if(id.equals(schemeId)){
                    headerList.add(comparisonSchemeHeader);
                }
            }
            qqchComparisonScheme.setQqchComparisonSchemeHeaderList(headerList);
        }

        qqchComparisonSchemeVo.setQqchComparisonSchemeList(qqchComparisonSchemeList);

        //TODO 获取确认状态

        return qqchComparisonSchemeVo;
    }

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        //删除旧数据
        this.deleteOldDataByVersion(qqchComparisonSchemeVo.getVersion());

        //插入新数据
        this.insertQqchComparisonSchemeList(qqchComparisonSchemeVo.getQqchComparisonSchemeList(),qqchComparisonSchemeVo.getVersion());
    }

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        this.save(qqchComparisonSchemeVo);

        String buttonMark = qqchComparisonSchemeVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchComparisonSchemeVo.getMenuId();
            String stageIdentity = qqchComparisonSchemeVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入方案
     * @param qqchComparisonSchemeList
     * @return
     */
    @Transactional
    public int insertQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList, BigDecimal version) {
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            Long schemeId = IdWorker.createId();

            //插入表头
            List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonScheme.getQqchComparisonSchemeHeaderList();
            if(!CollectionUtils.isEmpty(qqchComparisonSchemeHeaderList)){
                qqchComparisonSchemeHeaderService.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList, schemeId, version);
            }

            qqchComparisonScheme.setId(schemeId);
            qqchComparisonScheme.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchComparisonScheme.setValid(Valid.YES);
            }
            qqchComparisonScheme.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonScheme.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonScheme.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeMapper.insertQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    /**
     * 根据版本删除旧数据
     * @param version 版本
     */
    @Transactional
    public void deleteOldDataByVersion(BigDecimal version) {
        //删除表头
        QqchComparisonSchemeHeader qqchComparisonSchemeHeader = new QqchComparisonSchemeHeader();
        qqchComparisonSchemeHeader.setVersion(version);
        qqchComparisonSchemeHeader.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);

        //删除单元格
        QqchComparisonSchemeContent qqchComparisonSchemeContent = new QqchComparisonSchemeContent();
        qqchComparisonSchemeContent.setVersion(version);
        qqchComparisonSchemeContent.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeContentMapper.deleteQqchComparisonSchemeContent(qqchComparisonSchemeContent);

        //删除方案
        QqchComparisonScheme qqchComparisonScheme = new QqchComparisonScheme();
        qqchComparisonScheme.setVersion(version);
        qqchComparisonScheme.setDelUser(SecurityUtils.getUserName());
        qqchComparisonScheme.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeMapper.deleteQqchComparisonScheme(qqchComparisonScheme);
    }
}
