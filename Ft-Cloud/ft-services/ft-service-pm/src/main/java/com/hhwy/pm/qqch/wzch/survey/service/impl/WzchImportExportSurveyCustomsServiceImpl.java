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
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCustoms;
import com.hhwy.pm.qqch.wzch.survey.mapper.WzchImportExportSurveyCustomsMapper;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCustomsService;
import com.hhwy.pm.qqch.wzch.survey.vo.WzchImportExportSurveyCustomsImportVo;
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
 * 进出口调查海关详情Service业务层处理
 * 
 * @author mls
 * @date 2022-12-05
 */
@Service
public class WzchImportExportSurveyCustomsServiceImpl implements IWzchImportExportSurveyCustomsService {
    @Autowired
    private WzchImportExportSurveyCustomsMapper wzchImportExportSurveyCustomsMapper;

    /**
     * 查询进出口调查海关详情
     * 
     * @param id 进出口调查海关详情ID
     * @return 进出口调查海关详情
     */
    @Override
    public WzchImportExportSurveyCustoms selectWzchImportExportSurveyCustomsById(Long id) {
        return wzchImportExportSurveyCustomsMapper.selectWzchImportExportSurveyCustomsById(id);
    }

    /**
     * 查询进出口调查海关详情列表
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 进出口调查海关详情
     */
    @Override
    public List<WzchImportExportSurveyCustoms> selectWzchImportExportSurveyCustomsList(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms) {
        return wzchImportExportSurveyCustomsMapper.selectWzchImportExportSurveyCustomsList(wzchImportExportSurveyCustoms);
    }

    /**
     * 新增进出口调查海关详情
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 结果
     */
    @Override
    public int insertWzchImportExportSurveyCustoms(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms) {

    wzchImportExportSurveyCustoms.setId(IdWorker.createId());

        wzchImportExportSurveyCustoms.setCreateTime(DateUtils.getNowDate());

        return wzchImportExportSurveyCustomsMapper.insertWzchImportExportSurveyCustoms(wzchImportExportSurveyCustoms);
    }

    /**
     * 修改进出口调查海关详情
     * 
     * @param wzchImportExportSurveyCustoms 进出口调查海关详情
     * @return 结果
     */
    @Override
    public int updateWzchImportExportSurveyCustoms(WzchImportExportSurveyCustoms wzchImportExportSurveyCustoms) {
        wzchImportExportSurveyCustoms.setUpdateTime(DateUtils.getNowDate());
        return wzchImportExportSurveyCustomsMapper.updateWzchImportExportSurveyCustoms(wzchImportExportSurveyCustoms);
    }

    /**
     * 删除进出口调查海关详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyCustomsByIds(String ids) {
        return wzchImportExportSurveyCustomsMapper.deleteWzchImportExportSurveyCustomsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除进出口调查海关详情信息
     * 
     * @param id 进出口调查海关详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyCustomsById(Long id) {
        return wzchImportExportSurveyCustomsMapper.deleteWzchImportExportSurveyCustomsById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCustomsMapper.deleteByIds(ids);
    }

    @Override
    public int batchInsert(List<WzchImportExportSurveyCustoms> wzchImportExportSurveyCustomsList) {
        if (CollectionUtils.isEmpty(wzchImportExportSurveyCustomsList)) {
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCustomsMapper.batchInsert(wzchImportExportSurveyCustomsList);
    }

    @Override
    public List<WzchImportExportSurveyCustoms> importCustoms(MultipartFile file) throws IOException {
        List<WzchImportExportSurveyCustoms> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), WzchImportExportSurveyCustomsImportVo.class, new ReadListener<WzchImportExportSurveyCustomsImportVo>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                e.printStackTrace();
            }

//            @Override
//            public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
//                System.out.println(JSONObject.toJSONString(map));
//            }

            @Override
            public void invoke(WzchImportExportSurveyCustomsImportVo importVo, AnalysisContext analysisContext) {
                WzchImportExportSurveyCustoms customs = new WzchImportExportSurveyCustoms();
                BeanUtils.copyProperties(importVo,customs);
                list.add(customs);
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

        }).sheet("海关详情").doReadSync();
        return list;
    }

    @Override
    public int deleteBySurveyId(Long surveyId) {
        if(surveyId==null){
            throw new BaseException("入参缺失");
        }
        return wzchImportExportSurveyCustomsMapper.deleteBySurveyId(surveyId);
    }

}
