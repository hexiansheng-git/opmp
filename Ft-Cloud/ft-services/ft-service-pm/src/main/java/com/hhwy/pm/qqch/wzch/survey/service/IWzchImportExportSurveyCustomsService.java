package com.hhwy.pm.qqch.wzch.survey.service;

import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCustoms;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 进出口调查海关详情Service接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface IWzchImportExportSurveyCustomsService {
    /**
     * 查询进出口调查海关详情
     * 
     * @param id 进出口调查海关详情ID
     * @return 进出口调查海关详情
     */
    WzchImportExportSurveyCustoms selectWzchImportExportSurveyCustomsById(Long id);

    /**
     * 查询进出口调查海关详情列表
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 进出口调查海关详情集合
     */
    List<WzchImportExportSurveyCustoms> selectWzchImportExportSurveyCustomsList(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms);

    /**
     * 新增进出口调查海关详情
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 结果
     */
    int insertWzchImportExportSurveyCustoms(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms);

    /**
     * 修改进出口调查海关详情
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 结果
     */
    int updateWzchImportExportSurveyCustoms(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms);

    /**
     * 批量删除进出口调查海关详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCustomsByIds(String ids);

    /**
     * 删除进出口调查海关详情信息
     * 
     * @param id 进出口调查海关详情ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyCustomsById(Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    int batchInsert(List<WzchImportExportSurveyCustoms> wzchImportExportSurveyCustomsList);

    List<WzchImportExportSurveyCustoms> importCustoms(MultipartFile file) throws IOException;

    int deleteBySurveyId(Long surveyId);
}
