package com.hhwy.utils.excelUtil;/**
 * @description TODO
 * @date 2022-11-24 16:40
 * @author zq
 */

import com.hhwy.common.core.utils.file.FileUtils;
import com.hhwy.utils.excel.FtExcelEnum;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zq
 * @date 2022年11月24日 16:40
 */
@Component
public class DownTemplate {

    public void downloadExcel(HttpServletRequest request, HttpServletResponse res, String templateName, String excelName) throws Exception {
        //封装下拉字典项
        Map<String, List> map = getPullLists(templateName);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/" + templateName);
        if (inputStream == null)
            return;
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
        //获取创建的工作簿第一页
        HSSFSheet sheet = workBook.getSheetAt(0);
        //获取当前sheet最后一行数据对应的行索引
        int currentLastRowIndex = sheet.getLastRowNum();
        int currentLastCellIndex = sheet.getRow(0).getPhysicalNumberOfCells();
        HSSFRow row = null;
        HSSFCell cell = null;
        //获取list或者value 的key值
        List<String> cellsnameList = new ArrayList<>();
        row = sheet.getRow(currentLastRowIndex);
        //下拉选项写入模板
        int hiddenIndex = 1;
        for (int i2 = 1; i2 < currentLastCellIndex; i2++) {
            cell = row.getCell(i2);
            if (cell == null) {
                continue;
            }

            //设置单元格类型
            cell.setCellType(CellType.STRING);
            String keyName = cell.getStringCellValue();
            cellsnameList.add(keyName);
            if (!ObjectNullUtil.isEmpty(keyName)) {
                List vals = map.get(keyName);
                if (null == vals || vals.size() == 0) {
                    continue;
                }
                setxiala(hiddenIndex, workBook, sheet, vals, i2);
                hiddenIndex++;
            }
        }

        sheet.removeRow(row);
        OutputStream outputStream = null;
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("multipart/form-data");
            res.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, excelName));
            outputStream = res.getOutputStream();
            workBook.write(outputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public void downloadTemplateWithSuffix(HttpServletRequest request, HttpServletResponse response, String templateName, String exportName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/" + templateName);
        if (inputStream == null) {
            throw new RuntimeException("找不到对应文件！");
        }

        Workbook workBook;
        String filenameExtension = StringUtils.getFilenameExtension(templateName);

        if ("xls".equals(filenameExtension)) {
            workBook = new HSSFWorkbook(inputStream);
//            exportName = exportName + ".xls";
        } else if ("xlsx".equals(filenameExtension)) {
            workBook = new XSSFWorkbook(inputStream);
//            exportName = exportName + ".xlsx";
        } else {
            throw new RemoteException("文件格式不符合规范！");
        }

        /*封装下拉字典项*/
        //获取字典集合
        Map<String, List> map = getPullLists(templateName);
        //获取创建的工作簿第一页
        Sheet sheet = workBook.getSheetAt(0);
        //获取当前sheet最后一行数据对应的行索引
        int currentLastRowIndex = sheet.getLastRowNum();
        //获取每行单元格数量
        int currentLastCellIndex = sheet.getRow(0).getPhysicalNumberOfCells();
        //获取最后一行数据
        Row row = sheet.getRow(currentLastRowIndex);
        //获取list或者value 的key值
//        List<String> cellsnameList = new ArrayList<>();
        //下拉选项写入模板
        int hiddenIndex = 1;
        CellBase cell;
        for (int i = 1; i < currentLastCellIndex; i++) {
            cell = (CellBase) row.getCell(i);
            if (cell == null) {
                continue;
            }
            //设置单元格类型
            cell.setCellType(CellType.STRING);
            String keyName = cell.getStringCellValue();
//            cellsnameList.add(keyName);
            if (!ObjectNullUtil.isEmpty(keyName)) {
                List<String> valueList = map.get(keyName);
                if (null == valueList || valueList.size() == 0) {
                    continue;
                }
                setComboBox(hiddenIndex, workBook, sheet, valueList, i);
                hiddenIndex++;
            }
        }

        sheet.removeRow(row);
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, templateName));
            outputStream = response.getOutputStream();
            workBook.write(outputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void setComboBox(int sheetTotal, Workbook workbook, Sheet sheet, List<String> dataList, int columnIndex) {
        //新建一个sheet页
        String hiddenSheetName = "hiddenSheet" + sheetTotal;
        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);
        Cell cell;
        String[] explicitListValues = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            String name = dataList.get(i);
            //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
            Row row = hiddenSheet.createRow(i);
            //创建每一行中的第一个单元格
            cell = row.createCell(0);
            //然后将数组中的元素赋值给这个单元格
            cell.setCellValue(name);
            explicitListValues[i] = name;
        }

        // 创建名称，可被其他单元格引用
        Name namedCell = workbook.createName();
        String namename = "hidden" + sheetTotal;
        namedCell.setNameName(namename);
        // 设置名称引用的公式
        namedCell.setRefersToFormula(hiddenSheetName + "!$A$1:$A$" + dataList.size());
        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(namename);

        // 设置第一列的3-65534行为下拉列表
        // (3, 65534, 0, 0) ====> (起始行,结束行,起始列,结束列)
        CellRangeAddressList regions = new CellRangeAddressList(1, 10000, columnIndex, columnIndex);
        // 将设置下拉选的位置和数据的对应关系 绑定到一起

        DataValidation dataValidation;
        DataValidationHelper dataValidationHelper;
        if (workbook instanceof HSSFWorkbook) {
            dataValidationHelper = new HSSFDataValidationHelper((HSSFSheet) sheet);
        } else {
            dataValidationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        }

        DataValidationConstraint dataValidationConstraint = dataValidationHelper.createExplicitListConstraint(explicitListValues);
        dataValidation = dataValidationHelper.createValidation(dataValidationConstraint, regions);

        //将第二个sheet设置为隐藏
        workbook.setSheetHidden(sheetTotal, true);
        //将数据赋给下拉列表
        sheet.addValidationData(dataValidation);
    }

    //封装下拉选项
    public Map<String, List> getPullLists(String type) {
        Map<String, List> map = new HashMap<>();
        for (MyDownTemplateEnum myDownTemplateType : MyDownTemplateEnum.values()) {
            String name = myDownTemplateType.getName();
            if (type.equals(name)) {
                map = myDownTemplateType.pullLists();
                break;
            }
        }
        return map;
    }

    public void setxiala(int sheetTotal, HSSFWorkbook workbook, HSSFSheet sheet, List<String> dataList, int columnIndex) {
        //新建一个sheet页
        String hiddenSheetName = "hiddenSheet" + sheetTotal;
        HSSFSheet hiddenSheet = workbook.createSheet(hiddenSheetName);
        Cell cell = null;
        for (int i = 0; i < dataList.size(); i++) {
            String name = dataList.get(i);
            //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
            Row row = hiddenSheet.createRow(i);
            //创建每一行中的第一个单元格
            cell = row.createCell(0);
            //然后将数组中的元素赋值给这个单元格
            cell.setCellValue(name);
        }

        // 创建名称，可被其他单元格引用
        Name namedCell = workbook.createName();
        String namename = "hidden" + sheetTotal;
        namedCell.setNameName(namename);
        // 设置名称引用的公式
        namedCell.setRefersToFormula(hiddenSheetName + "!$A$1:$A$" + dataList.size());
        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(namename);

        // 设置第一列的3-65534行为下拉列表
        // (3, 65534, 0, 0) ====> (起始行,结束行,起始列,结束列)
        CellRangeAddressList regions = new CellRangeAddressList(1, 10000, columnIndex, columnIndex);
        // 将设置下拉选的位置和数据的对应关系 绑定到一起
        DataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        //将第二个sheet设置为隐藏
        workbook.setSheetHidden(sheetTotal, true);
        //将数据赋给下拉列表
        sheet.addValidationData(dataValidation);
        //最后将文件导出就可以了，后面的代码就不写了，我只写一些这个问题相关的代码
    }

    //封装下拉选项
    public Map<String, List> getPullListsByParams(Map<String, String> params) {
        String type = params.get("templateName") + ".xls";
        Map<String, List> map = new HashMap<>();
        for (MyDownTemplateEnum myDownTemplateType : MyDownTemplateEnum.values()) {
            String name = myDownTemplateType.getName();
            if (type.equals(name)) {
                map = myDownTemplateType.getPullListsByParams(params);
                break;
            }
        }
        return map;
    }


    public void downloadTemplateWithParam(HttpServletRequest request, HttpServletResponse res, Map<String, String> params) throws IOException {
        String templateName = params.get("templateName") + ".xls";
        String excelName = params.get("name") + ".xls";

        //封装下拉字典项
        Map<String, List> map = getPullListsByParams(params);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/" + templateName);
        if (inputStream == null)
            return;
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
        //获取创建的工作簿第一页
        HSSFSheet sheet = workBook.getSheetAt(0);
        //获取当前sheet最后一行数据对应的行索引
        int currentLastRowIndex = sheet.getLastRowNum();
        int currentLastCellIndex = sheet.getRow(0).getPhysicalNumberOfCells();
        HSSFRow row = null;
        HSSFCell cell = null;
        //获取list或者value 的key值
        List<String> cellsnameList = new ArrayList<>();
        row = sheet.getRow(currentLastRowIndex);
        //下拉选项写入模板
        int hiddenIndex = 1;
        for (int i2 = 1; i2 < currentLastCellIndex; i2++) {
            cell = row.getCell(i2);
            String keyName = cell.getStringCellValue();
            cellsnameList.add(keyName);
            if (!ObjectNullUtil.isEmpty(keyName)) {
                List vals = map.get(keyName);
                if (null == vals || vals.size() == 0) {
                    continue;
                }
                setxiala(hiddenIndex, workBook, sheet, vals, i2);
                hiddenIndex++;
            }
        }

        sheet.removeRow(row);
        OutputStream outputStream = null;
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("multipart/form-data");
            res.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, excelName));
            outputStream = res.getOutputStream();
            workBook.write(outputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public void ftDownLoad(HttpServletRequest request, HttpServletResponse response, String fileName, String exportName) {
        try {
            for (FtExcelEnum value : FtExcelEnum.values()) {
                if (!value.getTemplateName().equals(fileName)) continue;
                String clazzName = value.getClazzName();
                Class<?> aClass = Class.forName(clazzName);
                FtExcelUtil<?> ftExcelUtil = new FtExcelUtil<>(aClass);
                exportName = (null != exportName && exportName != "") ? exportName : value.getExportName();
                if (value.getFunction() == null) {
                    ftExcelUtil.downloadTemplate(request, response, fileName, exportName);
                } else {
                    ftExcelUtil.downloadTemplate(request, response, fileName, value.getFunction(), exportName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
