package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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
     *
     * @param qqchComparisonSchemeContentList
     * @param schemeId
     * @param rownum
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList, Long schemeId, Integer rownum, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchComparisonSchemeContentList)){
            return;
        }
        int sort = 1;
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setId(IdWorker.createId());
            qqchComparisonSchemeContent.setSchemeId(schemeId);
            qqchComparisonSchemeContent.setRownum(rownum);
            qqchComparisonSchemeContent.setVersion(version);
            qqchComparisonSchemeContent.setSort(sort++);
            qqchComparisonSchemeContent.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeContent.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeContent.setCreateTime(DateUtils.getNowDate());
        }
        qqchComparisonSchemeContentMapper.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }
}
