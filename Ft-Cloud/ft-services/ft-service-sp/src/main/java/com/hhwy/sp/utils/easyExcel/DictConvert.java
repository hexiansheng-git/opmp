package com.hhwy.sp.utils.easyExcel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DictConvert implements Converter<String> {

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Field field = contentProperty.getField();
        ExcelDict annotation = field.getAnnotation(ExcelDict.class);
//        annotation

        return Converter.super.convertToExcelData(value, contentProperty, globalConfiguration);
    }
}
