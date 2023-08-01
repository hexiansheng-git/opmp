package com.hhwy.utils.excelUtil;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * @description:
 * @author: han
 * @create: 2023-7-31
 **/
public class AutoHeadColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        //表头自适应列宽
        if (isHead) {
            int length = cell.getStringCellValue().getBytes().length;
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), length * 300);
        }
    }
}
