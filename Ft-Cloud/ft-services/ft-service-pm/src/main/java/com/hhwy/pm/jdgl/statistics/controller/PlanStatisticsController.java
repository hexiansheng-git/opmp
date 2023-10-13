package com.hhwy.pm.jdgl.statistics.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.jdgl.statistics.domain.*;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/planStatistics")
public class PlanStatisticsController  {

    @Autowired
    private IPlanStatisticsService iPlanStatisticsService;

    @PostMapping("/getValueCompData")
    public AjaxResult getValueCompData(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        return AjaxResult.success(iPlanStatisticsService.getValueCompData(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getWbsValueList")
    public AjaxResult getWbsValueList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        return AjaxResult.success(iPlanStatisticsService.getWbsValueList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getBillValueList")
    public AjaxResult getBillValueList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getBillValueList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getImageWbsList")
    public AjaxResult getImageWbsList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getImageWbsList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getYearValueCompareList")
    public AjaxResult getYearValueCompareList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getYearValueCompareList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getDayScheduleCalendarList")
    public AjaxResult getDayScheduleCalendarList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getDayScheduleCalendarList(iPlanStatisticsQueryVO));
    };

//    @ResponseBody
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        List<PlanStatisticsValueCompVO> valueCompList = iPlanStatisticsService.getValueCompData4VO(iPlanStatisticsQueryVO);
        List<PlanStatisticsWbsValueVO> wbsValueList = iPlanStatisticsService.getWbsValueList(iPlanStatisticsQueryVO);
        List<PlanStatisticsBillValueVO> billValueList = iPlanStatisticsService.getBillValueList(iPlanStatisticsQueryVO);
        List<PlanStatisticsWbsImageVO> imageWbsList = iPlanStatisticsService.getImageWbsList(iPlanStatisticsQueryVO);

        List<SheetInfoBean> sheetInfoList = new LinkedList<>();

        sheetInfoList.add(new SheetInfoBean("基本信息", PlanStatisticsValueCompVO.class, valueCompList));
        sheetInfoList.add(new SheetInfoBean("WBS汇总", PlanStatisticsWbsValueVO.class, wbsValueList));
        sheetInfoList.add(new SheetInfoBean("清单汇总", PlanStatisticsBillValueVO.class, billValueList));
        sheetInfoList.add(new SheetInfoBean("形象汇总", PlanStatisticsWbsImageVO.class, imageWbsList));

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

        WriteSheet writeSheet;
        for (SheetInfoBean bean : sheetInfoList) {
            // 构建sheet对象
            writeSheet = EasyExcel.writerSheet(bean.getSheetName()).head(bean.getHeadClass()).build();
            // 写出sheet数据
            excelWriter.write(bean.getDataList(), writeSheet);
        }
        excelWriter.finish();
    }

}
