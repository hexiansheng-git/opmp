package com.hhwy.pm.qqch.wzch.survey.mapper;


import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCountry;

import java.util.List;

/**
 * 进出口调查国家详情Mapper接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface WzchImportExportSurveyCountryMapper {
    /**
     * 查询进出口调查国家详情
     * 
     * @param id 进出口调查国家详情ID
     * @return 进出口调查国家详情
     */
    WzchImportExportSurveyCountry selectWzchImportExportSurveyCountryById(Long id);

    /**
     * 查询进出口调查国家详情列表
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 进出口调查国家详情集合
     */
    List<WzchImportExportSurveyCountry> selectWzchImportExportSurveyCountryList(WzchImportExportSurveyCountry wzchImportExportSurveyCountry);

    /**
     * 新增进出口调查国家详情
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 结果
     */
    int insertWzchImportExportSurveyCountry(WzchImportExportSurveyCountry wzchImportExportSurveyCountry);

    /**
     * 修改进出口调查国家详情
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 结果
     */
    int updateWzchImportExportSurveyCountry(WzchImportExportSurveyCountry wzchImportExportSurveyCountry);

    /**
     * 删除进出口调查国家详情
     * 
     * @param id 进出口调查国家详情ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCountryById(Long id);

    /**
     * 批量删除进出口调查国家详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCountryByIds(String[] ids);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    int batchInsert(List<WzchImportExportSurveyCountry> wzchImportExportSurveyCountryList);

    int deleteBySurveyId(Long surveyId);
}
