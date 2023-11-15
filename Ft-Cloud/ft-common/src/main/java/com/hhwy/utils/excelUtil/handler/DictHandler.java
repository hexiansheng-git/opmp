package com.hhwy.utils.excelUtil.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.system.api.domain.SysDictData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EasyExcel 字典项写入 支持大批量字典项
 * 1、new DictHandler().init(字典项map);
 * 2、new DictHandler(字典类型);
 */
public class DictHandler implements SheetWriteHandler {
    //索引列 : 字典项数组
    private Map<Integer,String[]> map = null;
    private static SystemServiceApi systemServiceApi;
    private int index;
    private char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


    static{
        systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
    }

    public DictHandler() {this.index=0;}
    public DictHandler init(Map<Integer, String[]> map) {
        this.map = map;
        return this;
    }
    public DictHandler(Map<Integer, String> dictNameMap) {
        //获取字典项，并格式化到map
        map = new HashMap<>(dictNameMap.size());
        for(Integer k:dictNameMap.keySet()){
            String dictType = dictNameMap.get(k);
            String[] vals = getDictValues(dictType);
            map.put(k,vals);
        }
        this.map = map;
        this.index=0;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        //数量大时会出问题，但不占用sheet页
//       Sheet sheet = writeSheetHolder.getSheet();
//       DataValidationHelper helper = sheet.getDataValidationHelper();
//       //创建下拉列表
//       for(Integer k : map.keySet()){
//           String[] v = map.get(k);
//           //约束数据
//           DataValidationConstraint constraint = helper.createExplicitListConstraint(v);
//           //设置下拉列，起始-结束行与列
//           CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1,65536,k,k);
//           //设置约束
//           DataValidation validation = helper.createValidation(constraint,cellRangeAddressList);
//           validation.setShowErrorBox(true);
//           validation.setSuppressDropDownArrow(true);
//           validation.createErrorBox("提示","此值和单元格定义格式不一致");
//           validation.createPromptBox("填写","填写内容只能为下拉数据集中的类型");
//           sheet.addValidationData(validation);
//       }
        // 需要设置下拉框的sheet页
        Sheet curSheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = curSheet.getDataValidationHelper();
        String dictSheetName = "字典sheet";

        Workbook workbook = writeWorkbookHolder.getWorkbook();
        // 数据字典的sheet页
        Sheet dictSheet = workbook.createSheet(dictSheetName);
        // 从第二个工作簿开始隐藏，为了用户的友好性，将字典sheet隐藏掉
        this.index++;
        // 设置隐藏
        workbook.setSheetHidden(this.index, true);
        for (Map.Entry<Integer,String[]> entry : map.entrySet()) {
            // 设置下拉单元格的首行、末行、首列、末列
            CellRangeAddressList rangeAddressList = new CellRangeAddressList(1, 65533, entry.getKey(), entry.getKey());
            int rowLen = entry.getValue().length;
            // 设置字典sheet页的值 每一列一个字典项
            for (int i = 0; i < rowLen; i++) {
                Row row = dictSheet.getRow(i);
                if (row == null) {
                    row = dictSheet.createRow(i);
                }
                row.createCell(entry.getKey()).setCellValue(entry.getValue()[i]);
            }
            String excelColumn = getExcelColumn(entry.getKey());
            // 下拉框数据来源 eg:字典sheet!$B1:$B2
            String refers = dictSheetName + "!$" + excelColumn + "$1:$" + excelColumn + "$" + rowLen;
            // 创建可被其他单元格引用的名称
            Name name = workbook.createName();
            // 设置名称的名字
            name.setNameName("dict" + entry.getKey());
            // 设置公式
            name.setRefersToFormula(refers);
            // 设置引用约束
            DataValidationConstraint constraint = helper.createFormulaListConstraint("dict" + entry.getKey());
            // 设置约束
            DataValidation validation = helper.createValidation(constraint, rangeAddressList);
            // 添加下拉框约束
            writeSheetHolder.getSheet().addValidationData(validation);
            //第一行>文本格式
            CellStyle cellStyle = writeSheetHolder.getSheet().getWorkbook().createCellStyle();
            cellStyle.setDataFormat((short)49);
            writeSheetHolder.getSheet().setDefaultColumnStyle(0,cellStyle);
        }
        
    }
    /**
     * desc: 将数字列转化成为字母列
     * date: 2022/12/26 15:32
     * @param num
     * @return {@link String}
     */
    private String getExcelColumn(int num) {
        String column = "";
        int len = alphabet.length - 1;
        int first = num / len;
        int second = num % len;
        if (num <= len) {
            column = alphabet[num] + "";
        } else {
            column = alphabet[first - 1] + "";
            if (second == 0) {
                column = column + alphabet[len] + "";
            } else {
                column = column + alphabet[second - 1] + "";
            }
        }
        return column;
    }


    private String[] getDictValues(String dictType){
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<SysDictData> list = JSONObject.parseArray(JSONObject.toJSONString(result.get("data")),SysDictData.class);
        String[] labels = list.stream().map(r->r.getDictLabel()).toArray(String[]::new);
        return labels;
    }
}
