package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

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


    /**
     * 获取方案集合
     * @return
     */
    public QqchComparisonSchemeVo getQqchComparisonSchemeVo() {
        QqchComparisonSchemeVo qqchComparisonSchemeVo = new QqchComparisonSchemeVo();

        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeMapper.getQqchComparisonSchemeList();

        //获取所有表头
        QqchComparisonSchemeHeader qqchComparisonSchemeHeader = new QqchComparisonSchemeHeader();
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeader);

        //获取所有单元格
        QqchComparisonSchemeContent qqchComparisonSchemeContent = new QqchComparisonSchemeContent();
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContentList(qqchComparisonSchemeContent);

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
        qqchComparisonSchemeVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        //TODO 获取确认状态

        return qqchComparisonSchemeVo;
    }

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    public void save(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        this.editQqchComparisonSchemeList(qqchComparisonSchemeVo.getQqchComparisonSchemeList());
    }

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        this.editQqchComparisonSchemeList(qqchComparisonSchemeVo.getQqchComparisonSchemeList());

        //TODO 修改确认状态

    }

    /**
     * 批量编辑
     * @param qqchComparisonSchemeList
     * @return
     */
    @Transactional
    public int editQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList) {
        List<QqchComparisonScheme> insertList = new ArrayList<>();
        List<QqchComparisonScheme> updateList = new ArrayList<>();
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            Long id = qqchComparisonScheme.getId();
            if(id == null){
                insertList.add(qqchComparisonScheme);
            }else{
                updateList.add(qqchComparisonScheme);
            }
        }
        if(insertList.size() > 0){
            this.insertQqchComparisonSchemeList(insertList);
        }
        if(updateList.size() > 0){
            this.updateQqchComparisonSchemeList(updateList);
        }
        return 1;
    }

    /**
     * 批量插入方案
     * @param qqchComparisonSchemeList
     * @return
     */
    @Transactional
    public int insertQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList) {
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            Long schemeId = IdWorker.createId();

            //插入表头
            List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonScheme.getQqchComparisonSchemeHeaderList();
            if(!CollectionUtils.isEmpty(qqchComparisonSchemeHeaderList)){
                qqchComparisonSchemeHeaderService.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList,schemeId);
            }

            qqchComparisonScheme.setId(schemeId);
            qqchComparisonScheme.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonScheme.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonScheme.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeMapper.insertQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    /**
     * 批量修改方案
     * @param qqchComparisonSchemeList
     * @return
     */
    @Transactional
    public int updateQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList) {
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            Long schemeId = qqchComparisonScheme.getId();

            //编辑表头
            List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonScheme.getQqchComparisonSchemeHeaderList();
            if(!CollectionUtils.isEmpty(qqchComparisonSchemeHeaderList)){
                qqchComparisonSchemeHeaderService.editQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList,schemeId);
            }

            qqchComparisonScheme.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchComparisonScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeMapper.updateQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    /**
     * 删除方案
     * @param schemeId 方案id
     * @return
     */
    @Override
    @Transactional
    public int deleteQqchComparisonSchemeById(Long schemeId) {

        //删除表头
        QqchComparisonSchemeHeader qqchComparisonSchemeHeader = new QqchComparisonSchemeHeader();
        qqchComparisonSchemeHeader.setSchemeId(schemeId);
        qqchComparisonSchemeHeader.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);

        //删除单元格
        QqchComparisonSchemeContent qqchComparisonSchemeContent = new QqchComparisonSchemeContent();
        qqchComparisonSchemeContent.setSchemeId(schemeId);
        qqchComparisonSchemeContent.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeContentMapper.deleteQqchComparisonSchemeContent(qqchComparisonSchemeContent);

        //删除方案
        QqchComparisonScheme qqchComparisonScheme = new QqchComparisonScheme();
        qqchComparisonScheme.setId(schemeId);
        qqchComparisonScheme.setDelUser(SecurityUtils.getUserName());
        qqchComparisonScheme.setDelTime(DateUtils.getNowDate());
        return qqchComparisonSchemeMapper.deleteQqchComparisonScheme(qqchComparisonScheme);
    }

    /**
     * 删除行
     * @param schemeId
     * @param sorts
     * @return
     */
    @Override
    public int deleteLine(Long schemeId, String[] sorts) {
        //删除单元格
        return qqchComparisonSchemeContentMapper.deleteBySchemeIdAndSorts(schemeId,sorts);
    }

    /**
     * 删除列（多列）
     * @param headerIds
     * @return
     */
    @Override
    @Transactional
    public int deleteColumn(Long[] headerIds) {
        //删除单元格
        qqchComparisonSchemeContentMapper.deleteByHeaderIds(headerIds);

        //删除表头
        return qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeaderByPks(Arrays.asList(headerIds));
    }
}
