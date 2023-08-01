package com.hhwy.utils.excelUtil;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description:
 * @author: ZHUZHIJIE
 * @create: 2022-11-11
 **/
public class MyDateConverter implements Converter<Date>{
    private static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    @Override
    public Class<?> supportJavaTypeKey() {
        return Converter.super.supportJavaTypeKey();
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return Converter.super.supportExcelTypeKey();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
        if(null != value){
            String dateValue = sdf.format(value);
            return new WriteCellData<>(dateValue);
        }else{
            return new WriteCellData<>("");
        }

    }

}
