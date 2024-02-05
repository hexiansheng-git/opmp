package com.hhwy.sp.utils.easyExcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 功能：单元格合并策略
 * 作者: fushudong
 * 时间: 2024/02/05
 */
public class CustomMergeStrategy implements CellWriteHandler {

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) return;
        if (relativeRowIndex == 0) return;
        Sheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int rowIndexPrev = rowIndex - 1;
        Row rowPrev = sheet.getRow(rowIndexPrev);
        Cell cellPrev = rowPrev.getCell(cell.getColumnIndex());
        String cellValue = cell.getStringCellValue();
        String cellValuePrev = cellPrev.getStringCellValue();
        if (!cellValue.equals(cellValuePrev))return;
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        boolean merged = false;
        for (int i = 0; i < mergedRegions.size(); i++) {
            CellRangeAddress cellAddresses = mergedRegions.get(i);
            if (cellAddresses.isInRange(rowIndex, cell.getColumnIndex())) {
                sheet.removeMergedRegion(i);
                cellAddresses.setLastRow(rowIndex);
                sheet.addMergedRegion(cellAddresses);
                break;
            }
        }
        if (!merged) {
            CellRangeAddress cellAddresses = new CellRangeAddress(rowIndexPrev, rowIndex, cell.getColumnIndex(), cell.getColumnIndex());
            sheet.addMergedRegion(cellAddresses);
        }
    }

}