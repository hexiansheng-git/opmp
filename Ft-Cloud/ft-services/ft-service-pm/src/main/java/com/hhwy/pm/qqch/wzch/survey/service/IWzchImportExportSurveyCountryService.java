package com.hhwy.pm.qqch.wzch.survey.service;

import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCountry;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 进出口调查国家详情Service接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface IWzchImportExportSurveyCountryService {
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
     * 批量删除进出口调查国家详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCountryByIds(String ids);

    /**
     * 删除进出口调查国家详情信息
     * 
     * @param id 进出口调查国家详情ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCountryById(Long id);

    int deleteByIds(List<Long> ids);

    int batchInsert(List<WzchImportExportSurveyCountry> wzchImportExportSurveyCountryList);

    /**
     * 导入国家详情
     * @param file
     * @return
     */
    List<WzchImportExportSurveyCountry> importCountry(MultipartFile file) throws IOException;

    int deleteBySurveyId(Long surveyId);
}
