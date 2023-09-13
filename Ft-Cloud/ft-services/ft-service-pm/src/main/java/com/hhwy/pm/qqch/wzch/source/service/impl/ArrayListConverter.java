package com.hhwy.pm.qqch.wzch.source.service.impl;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.ArrayList;

public class ArrayListConverter implements Converter<ArrayList> {


        @Override
        public Class supportJavaTypeKey() {
            return ArrayList.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public ArrayList convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            String stringValue = cellData.getStringValue();
            String[] split = stringValue.split(",");
            ArrayList<String> enterpriseList = new ArrayList<>();
            for(int i = 0; i < split.length; i++){
                enterpriseList.add(split[i]);
            }
            return enterpriseList;
        }

        @Override
        public WriteCellData convertToExcelData(ArrayList list, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            StringBuilder stringBuilder = new StringBuilder();
            list.forEach(o -> {
                String s = o.toString();
                stringBuilder.append(s+",");
            });
            return new WriteCellData(stringBuilder.toString());
        }
    }

