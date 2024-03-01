package com.hhwy.sp.utils.easyExcel;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
* 功能描述: easyExcel导出字典翻译
* 作者: fushudong
* 时间: 2024/2/29
*/
public class DictConvert implements Converter<String> {

    private static final SystemApiService systemApiService = SpringUtil.getBean(SystemApiService.class);

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (StrUtil.isBlank(value)) return null;
        Field field = contentProperty.getField();
        ExcelDict annotation = field.getAnnotation(ExcelDict.class);
        String dictType = annotation.dictType();
        if (StrUtil.isBlank(dictType)) return new WriteCellData<>(value);
        List<SysDictData> sysDictData = systemApiService.selectDictDataByType(dictType);
        String collect = sysDictData.stream().filter(p -> p.getDictValue().equals(value)).map(SysDictData::getDictLabel).collect(Collectors.joining());
        return new WriteCellData<>(collect);
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) throws Exception {
        return Converter.super.convertToExcelData(context);
    }
}
