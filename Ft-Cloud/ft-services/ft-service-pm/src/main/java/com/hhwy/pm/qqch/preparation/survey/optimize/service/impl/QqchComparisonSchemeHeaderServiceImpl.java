package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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


    /**
     * 批量插入表头
     * @param qqchComparisonSchemeHeaderList
     * @param schemeId
     * @param version
     */
    public void insertQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList, Long schemeId, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchComparisonSchemeHeaderList)){
            return;
        }
        int sort = 1;
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            Long headerId = IdWorker.createId();

            //插入单元格
            List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeHeader.getQqchComparisonSchemeContentList();
            qqchComparisonSchemeContentService.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList, schemeId , headerId, version);

            qqchComparisonSchemeHeader.setId(headerId);
            qqchComparisonSchemeHeader.setSchemeId(schemeId);
            qqchComparisonSchemeHeader.setVersion(version);
            qqchComparisonSchemeHeader.setSort(String.valueOf(sort++));
            qqchComparisonSchemeHeader.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeHeader.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeHeader.setCreateTime(DateUtils.getNowDate());
        }
        qqchComparisonSchemeHeaderMapper.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList);
    }
}
