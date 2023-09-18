package com.hhwy.pm.qqch.wzch.survey.service;



import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurvey;

import java.util.List;

/**
 * 进出口调查Service接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface IWzchImportExportSurveyService {
    /**
     * 查询进出口调查
     * 
     * @param id 进出口调查ID
     * @return 进出口调查
     */
    WzchImportExportSurvey selectWzchImportExportSurveyById(Long id);

    /**
     * 查询进出口调查列表
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 进出口调查集合
     */
    List<WzchImportExportSurvey> selectWzchImportExportSurveyList(WzchImportExportSurvey wzchImportExportSurvey);

    /**
     * 新增进出口调查
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 结果
     */
    int insertWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey);

    /**
     * 修改进出口调查
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 结果
     */
    int updateWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey);

    /**
     * 批量删除进出口调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyByIds(String ids);

    /**
     * 删除进出口调查信息
     * 
     * @param id 进出口调查ID
     * @return 结果
     */
    int deleteWzchImportExportSurveyById(Long id);

    /**
     * 编辑
     * @param wzchImportExportSurvey
     * @return
     */
    WzchImportExportSurvey edit(WzchImportExportSurvey wzchImportExportSurvey);

    /**
     * 保存
     * @param wzchImportExportSurvey
     */
    Long save(WzchImportExportSurvey wzchImportExportSurvey);

}
