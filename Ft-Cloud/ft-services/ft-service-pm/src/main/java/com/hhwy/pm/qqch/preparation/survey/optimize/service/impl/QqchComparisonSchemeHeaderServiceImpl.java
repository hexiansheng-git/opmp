package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
@Service
public class QqchComparisonSchemeHeaderServiceImpl implements IQqchComparisonSchemeHeaderService {

    @Autowired
    private QqchComparisonSchemeHeaderMapper qqchComparisonSchemeHeaderMapper;

    @Autowired
    private QqchComparisonSchemeContentServiceImpl qqchComparisonSchemeContentService;


    public QqchComparisonSchemeHeader getQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        return qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);
    }

    public List<QqchComparisonSchemeHeader> getQqchComparisonSchemeHeaderList(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        return qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeader);
    }

    /**
     * 批量编辑表头
     * @param qqchComparisonSchemeHeaderList
     * @param schemeId
     * @return
     */
    public int editQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList,Long schemeId){
        List<QqchComparisonSchemeHeader> insertList = new ArrayList<>();
        List<QqchComparisonSchemeHeader> updateList = new ArrayList<>();
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            Long headerId = qqchComparisonSchemeHeader.getId();
            if(headerId == null){
                insertList.add(qqchComparisonSchemeHeader);
            }else{
                updateList.add(qqchComparisonSchemeHeader);
            }
        }
        if(insertList.size() > 0){
            this.insertQqchComparisonSchemeHeaderList(insertList, schemeId);
        }
        if(updateList.size() > 0){
            this.updateQqchComparisonSchemeHeaderList(updateList, schemeId);
        }
        return 1;
    }

    /**
     * 批量插入表头
     * @param qqchComparisonSchemeHeaderList
     * @param schemeId
     */
    public void insertQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList, Long schemeId) {
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            Long headerId = IdWorker.createId();

            //编辑单元格
            List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeHeader.getQqchComparisonSchemeContentList();
            if(!CollectionUtils.isEmpty(qqchComparisonSchemeContentList)){
                qqchComparisonSchemeContentService.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList, schemeId , headerId);
            }

            qqchComparisonSchemeHeader.setId(headerId);
            qqchComparisonSchemeHeader.setSchemeId(schemeId);
            qqchComparisonSchemeHeader.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeHeader.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeHeader.setCreateTime(DateUtils.getNowDate());
        }
        qqchComparisonSchemeHeaderMapper.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList);
    }

    /**
     * 批量修改表头
     * @param qqchComparisonSchemeHeaderList
     * @param schemeId
     */
    public void updateQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList, Long schemeId) {
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            Long headerId = qqchComparisonSchemeHeader.getId();

            //编辑单元格
            List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeHeader.getQqchComparisonSchemeContentList();
            if(!CollectionUtils.isEmpty(qqchComparisonSchemeContentList)){
                qqchComparisonSchemeContentService.editQqchComparisonSchemeContentList(qqchComparisonSchemeContentList, schemeId, headerId);
            }

            qqchComparisonSchemeHeader.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeHeader.setUpdateTime(DateUtils.getNowDate());
        }
        qqchComparisonSchemeHeaderMapper.updateQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList);
    }
}
