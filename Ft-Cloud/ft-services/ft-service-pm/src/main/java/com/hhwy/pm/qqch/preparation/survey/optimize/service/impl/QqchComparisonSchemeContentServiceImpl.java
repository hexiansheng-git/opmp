package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
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


    /**
     * 批量插入单元格
     * @param qqchComparisonSchemeContentList
     * @param schemeId
     * @param headerId
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList, Long schemeId, Long headerId, BigDecimal version) {
        int sort = 1;
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setId(IdWorker.createId());
            qqchComparisonSchemeContent.setSchemeId(schemeId);
            qqchComparisonSchemeContent.setHeaderId(headerId);
            qqchComparisonSchemeContent.setVersion(version);
            qqchComparisonSchemeContent.setSort(String.valueOf(sort++));
            qqchComparisonSchemeContent.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeContent.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeContent.setCreateTime(DateUtils.getNowDate());
        }
        qqchComparisonSchemeContentMapper.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }
}
