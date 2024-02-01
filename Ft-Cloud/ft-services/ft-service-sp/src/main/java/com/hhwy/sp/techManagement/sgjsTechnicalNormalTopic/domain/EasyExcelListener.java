package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：导入监听
 * 作者: fushudong
 * 时间: 2024/01/31
 */
public class EasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    //表头
    private List<Map<Integer, String>> headList = new ArrayList<>();
    //数据
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headList.add(headMap);
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        dataList.add(integerStringMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Map<Integer, String>> getHeadList(){
        return headList;
    }

    public List<Map<Integer, String>> getDataList(){
        return dataList;
    }
}