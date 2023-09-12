package com.hhwy.pm.qqch.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EasyExeclUtil {


    public static void export(HttpServletResponse response, List<List<String>> head,List<List<Object>> data,String fileName,String sheetName){

        //WriteSheet sheet = EasyExcel.writerSheet("来源策划详情").head(head).sheetNo(1).build();
        try {

           // String fileName = new String("来源策划详情".getBytes("gb2312"), "ISO8859-1") + ".xlsx";
            fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName );
            EasyExcel.write(response.getOutputStream()).head(head).excelType(ExcelTypeEnum.XLSX)
                     //               .registerWriteHandler(new LoopMergeStrategy(maxColNum, 0))  // 设置第一列每maxColNum行合并
                    //                .registerWriteHandler(new LoopMergeStrategy(maxColNum, 1))  // 设置第二列每maxColNum行合并
                    //                .registerWriteHandler(new LoopMergeStrategy(maxColNum, 2))  // 设置第三列每maxColNum行合并
                    .sheet(sheetName).doWrite(data);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
