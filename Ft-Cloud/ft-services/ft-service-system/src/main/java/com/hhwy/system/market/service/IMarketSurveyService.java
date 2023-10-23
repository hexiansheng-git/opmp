package com.hhwy.system.market.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.market.MarketSurvey;

import java.util.List;

/**
 * 市场调查Service接口
 * 
 * @author lcf
 * @date 2022-11-30
 */
public interface IMarketSurveyService {
    /**
     * 查询市场调查
     * 
     * @param id 市场调查ID
     * @return 市场调查
     */
    MarketSurvey selectMarketSurveyById(Long id);

    /**
     * 查询市场调查列表
     * 
     * @param marketSurvey 市场调查
     * @return 市场调查集合
     */
    List<MarketSurvey> selectMarketSurveyList(MarketSurvey marketSurvey);

    /**
     * 新增市场调查
     * 
     * @param marketSurvey 市场调查
     * @return 结果
     */
    int insertMarketSurvey(MarketSurvey marketSurvey);

    /**
     * 修改市场调查
     * 
     * @param marketSurvey 市场调查
     * @return 结果
     */
    int updateMarketSurvey(MarketSurvey marketSurvey);

    /**
     * 批量删除市场调查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMarketSurveyByIds(String ids);

    /**
     * 删除市场调查信息
     * 
     * @param id 市场调查ID
     * @return 结果
     */
    int deleteMarketSurveyById(Long id);

    /**
     * 市场信息查询
     *
     * @param marketSurvey
     * @return
     */
    List<MarketSurvey> selectMarketInfo(MarketSurvey marketSurvey);

    /**
     * 数据导入
     *
     * @param list
     * @return
     */
    AjaxResult importData(List<MarketSurvey> list);
}
