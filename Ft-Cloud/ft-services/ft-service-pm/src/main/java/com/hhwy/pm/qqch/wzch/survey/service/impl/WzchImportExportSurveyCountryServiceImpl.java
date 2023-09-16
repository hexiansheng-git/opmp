package com.hhwy.pm.qqch.wzch.survey.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCountry;
import com.hhwy.pm.qqch.wzch.survey.mapper.WzchImportExportSurveyCountryMapper;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCountryService;
import com.hhwy.pm.qqch.wzch.survey.vo.WzchImportExportSurveyCountryImportVo;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 进出口调查国家详情Service业务层处理
 * 
 * @author mls
 * @date 2022-12-05
 */
@Service
public class WzchImportExportSurveyCountryServiceImpl implements IWzchImportExportSurveyCountryService {
    @Autowired
    private WzchImportExportSurveyCountryMapper wzchImportExportSurveyCountryMapper;

    /**
     * 查询进出口调查国家详情
     * 
     * @param id 进出口调查国家详情ID
     * @return 进出口调查国家详情
     */
    @Override
    public WzchImportExportSurveyCountry selectWzchImportExportSurveyCountryById(Long id) {
        return wzchImportExportSurveyCountryMapper.selectWzchImportExportSurveyCountryById(id);
    }

    /**
     * 查询进出口调查国家详情列表
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 进出口调查国家详情
     */
    @Override
    public List<WzchImportExportSurveyCountry> selectWzchImportExportSurveyCountryList(WzchImportExportSurveyCountry wzchImportExportSurveyCountry) {
        return wzchImportExportSurveyCountryMapper.selectWzchImportExportSurveyCountryList(wzchImportExportSurveyCountry);
    }

    /**
     * 新增进出口调查国家详情
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 结果
     */
    @Override
    public int insertWzchImportExportSurveyCountry(WzchImportExportSurveyCountry wzchImportExportSurveyCountry) {

    wzchImportExportSurveyCountry.setId(IdWorker.createId());

        wzchImportExportSurveyCountry.setCreateTime(DateUtils.getNowDate());

        return wzchImportExportSurveyCountryMapper.insertWzchImportExportSurveyCountry(wzchImportExportSurveyCountry);
    }

    /**
     * 修改进出口调查国家详情
     * 
     * @param wzchImportExportSurveyCountry 进出口调查国家详情
     * @return 结果
     */
    @Override
    public int updateWzchImportExportSurveyCountry(WzchImportExportSurveyCountry wzchImportExportSurveyCountry) {
        wzchImportExportSurveyCountry.setUpdateTime(DateUtils.getNowDate());
        return wzchImportExportSurveyCountryMapper.updateWzchImportExportSurveyCountry(wzchImportExportSurveyCountry);
    }

    /**
     * 删除进出口调查国家详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyCountryByIds(String ids) {
        return wzchImportExportSurveyCountryMapper.deleteWzchImportExportSurveyCountryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除进出口调查国家详情信息
     * 
     * @param id 进出口调查国家详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyCountryById(Long id) {
        return wzchImportExportSurveyCountryMapper.deleteWzchImportExportSurveyCountryById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCountryMapper.deleteByIds(ids);
    }

    @Override
    public int batchInsert(List<WzchImportExportSurveyCountry> wzchImportExportSurveyCountryList) {
        if(CollectionUtils.isEmpty(wzchImportExportSurveyCountryList)){
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCountryMapper.batchInsert(wzchImportExportSurveyCountryList);
    }

    @Override
    public List<WzchImportExportSurveyCountry> importCountry(MultipartFile file) throws IOException {
        List<WzchImportExportSurveyCountry> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), WzchImportExportSurveyCountryImportVo.class, new ReadListener<WzchImportExportSurveyCountryImportVo>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                    e.printStackTrace();
            }

//            @Override
//            public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
//                System.out.println(JSONObject.toJSONString(map));
//            }

            @Override
            public void invoke(WzchImportExportSurveyCountryImportVo importVo, AnalysisContext analysisContext) {
                WzchImportExportSurveyCountry country = new WzchImportExportSurveyCountry();
                BeanUtils.copyProperties(importVo,country);
                list.add(country);
                if(list.size() > 500){
                    throw new BaseException("单次导入仅能500条");
                }
            }

            @Override
            public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }

            @Override
            public boolean hasNext(AnalysisContext analysisContext) {
                return true;
            }

        }).sheet("国家详情").doReadSync();
        return list;
    }

    @Override
    public int deleteBySurveyId(Long surveyId) {
        if(surveyId==null){
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCountryMapper.deleteBySurveyId(surveyId);
    }



}
