package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
@Service
public class QqchComparisonSchemeContentServiceImpl implements IQqchComparisonSchemeContentService {

    @Autowired
    private QqchComparisonSchemeContentMapper qqchComparisonSchemeContentMapper;


    public List<QqchComparisonSchemeContent> getQqchComparisonSchemeContentList(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        return qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContentList(qqchComparisonSchemeContent);
    }

    /**
     * 批量编辑单元格
     * @param qqchComparisonSchemeContentList
     * @param schemeId
     * @param headerId
     */
    @Transactional
    public void editQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList, Long schemeId, Long headerId) {
        List<QqchComparisonSchemeContent> insertList = new ArrayList<>();
        List<QqchComparisonSchemeContent> updateList = new ArrayList<>();
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            Long id = qqchComparisonSchemeContent.getId();
            if(id == null){
                insertList.add(qqchComparisonSchemeContent);
            }else {
                updateList.add(qqchComparisonSchemeContent);
            }
        }
        if(insertList.size() > 0){
            this.insertQqchComparisonSchemeContentList(insertList, schemeId, headerId);
        }
        if(updateList.size() > 0){
            this.updateQqchComparisonSchemeContentList(updateList);
        }
    }

    /**
     * 批量插入单元格
     * @param qqchComparisonSchemeContentList
     * @param schemeId
     * @param headerId
     * @return
     */
    @Transactional
    public int insertQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList, Long schemeId, Long headerId) {
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setId(IdWorker.createId());
            qqchComparisonSchemeContent.setSchemeId(schemeId);
            qqchComparisonSchemeContent.setHeaderId(headerId);
            qqchComparisonSchemeContent.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeContent.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeContent.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeContentMapper.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }

    /**
     * 批量修改单元格
     * @param qqchComparisonSchemeContentList
     * @return
     */
    @Transactional
    public int updateQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList) {
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeContent.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeContentMapper.updateQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }
}
