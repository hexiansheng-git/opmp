package com.hhwy.utils.excel;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.reflect.ReflectUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.field.FieldUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FtExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(FtExcelUtil.class);
    private String sheetName;
    private String templateName;
    private FtExcel.Type type;
    private Workbook wb;
    private Sheet sheet;
    private Map<String, CellStyle> styles;
    private List<T> list;
    private List<String> customFieldList;
    private List<Object[]> fields;
    public Class<?> clazz;
    public Map<String, Map<String, String>> dictsMap = new HashedMap<>();
    public Map<String, List> dicTypeAndLabelMap = new HashedMap<>();

    public FtExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public FtExcelUtil(String className) throws Exception {
        Class<?> aClass = Class.forName(className);
        this.clazz = aClass;
        ;
    }


    public void init(List<T> list, String sheetName, FtExcel.Type type) {


        if (list == null) {
            list = new ArrayList<>();
        }

        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.createExcelFieldAndSetDict();
        this.createWorkbook();
    }

    public void init(List<T> list, String sheetName, FtExcel.Type type, List<String> customFieldList) {
        if (list == null) {
            list = new ArrayList();
        }
        this.list = (List) list;
        this.sheetName = sheetName;
        this.type = type;
        this.customFieldList = customFieldList;
        this.createExcelFieldAndSetDict();
        this.createWorkbook();
    }

    public void initWithTemp(List<T> list, String sheetName, String tempName, FtExcel.Type type) {


        if (list == null) {
            list = new ArrayList<>();
        }
        this.templateName = tempName;
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.createExcelFieldAndSetDict();
        this.createWorkbookWithTemp();

    }

    public void createWorkbookWithTemp() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/" + templateName);
        if (inputStream == null) return;
        try {
            this.wb = WorkbookFactory.create(inputStream);
            //获取创建的工作簿第一页
            this.sheet = this.wb.getSheetAt(0);
        } catch (IOException e) {
            throw new RuntimeException("模板不存在!!!!!");
        }
    }

    public List<T> importExcel(InputStream is) throws Exception {
        return this.importExcel("", is);
    }

    public List<T> importExcel(String sheetName, InputStream is) throws Exception {
        this.type = FtExcel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<>();
        Sheet sheet;
        if (StringUtils.isNotEmpty(sheetName)) {
            sheet = this.wb.getSheet(sheetName);
        } else {
            sheet = this.wb.getSheetAt(0);
        }

        // 为空 抛异常
        if (sheet == null) throw new IOException("文件sheet不存在");

        int rows = sheet.getPhysicalNumberOfRows();
        // 没数据 直接返回空集合
        if (rows <= 0) return this.list;

        Map<String, Integer> cellMap = new HashMap<>();
        Row head = sheet.getRow(0);

        for (int i = 0; i < head.getPhysicalNumberOfCells(); ++i) {
            String value = this.getCellValue(head, i).toString();
            if (StringUtils.isNotEmpty(value)) {
                cellMap.put(value, i);
            }
        }

        Field[] allFields = this.clazz.getDeclaredFields();
        Map<Integer, Field> fieldsMap = new HashMap<>();

        for (Field field : allFields) {
            FtExcel attr = field.getAnnotation(FtExcel.class);
            if (attr != null && (attr.type() == FtExcel.Type.ALL || attr.type() == this.type)) {
                field.setAccessible(true);
                Integer column = cellMap.get(attr.name());
                if (column != null) {
                    fieldsMap.put(column, field);
                }
            }
        }

        for (int i = 1; i < rows; ++i) {
            Row row = sheet.getRow(i);
            T entity = null;

            for (Entry<Integer, Field> integerFieldEntry : fieldsMap.entrySet()) {
                Object val = this.getCellValue(row, integerFieldEntry.getKey());
                entity = entity == null ? (T) this.clazz.newInstance() : entity;
                Field field = fieldsMap.get(integerFieldEntry.getKey());
                Class<?> fieldType = field.getType();
                FtExcel attr = field.getAnnotation(FtExcel.class);
                // 对不同类型的属性进行处理
                if (String.class == fieldType) {
                    String s = Convert.toStr(val);
                    if (StringUtils.endsWith(s, ".0")) {
                        val = StringUtils.substringBefore(s, ".0");
                    } else {
                        val = Convert.toStr(val);
                    }
                } else if (Integer.TYPE != fieldType && Integer.class != fieldType) {
                    if (Long.TYPE != fieldType && Long.class != fieldType) {
                        if (Double.TYPE != fieldType && Double.class != fieldType) {
                            if (Float.TYPE != fieldType && Float.class != fieldType) {
                                if (BigDecimal.class == fieldType) {
                                    val = Convert.toBigDecimal(val);
                                } else if (Date.class == fieldType) {
                                    if (val instanceof String) {
                                        val = DateUtils.parseDate(val);
                                    } else if (val instanceof Double) {
                                        val = DateUtil.getJavaDate((Double) val);
                                    }
                                }
                            } else {
                                val = Convert.toFloat(val);
                            }
                        } else {
                            val = Convert.toDouble(val);
                        }
                    } else {
                        val = Convert.toLong(val);
                    }
                }else if(StringUtils.isNotEmpty(attr.readConverterExp())){ //int类型的字段使用了readConvertExp直接进入else BUG修复 
                    
                } else {
                    val = Convert.toInt(val);
                }

                
                String propertyName = field.getName();
                if (StringUtils.isNotEmpty(attr.targetAttr())) {
                    propertyName = field.getName() + "." + attr.targetAttr();
                } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                    val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                } else if (StringUtils.isNotEmpty(attr.dictType())) {
                    val = this.reverseByDictTypeForImport(String.valueOf(val), attr.dictType());
                }

                ReflectUtils.invokeSetter(entity, propertyName, val);
            }

            list.add(entity);
        }

        return list;
    }

    public void exportExcel(HttpServletResponse response, String sheetName) {
        this.exportExcel(response, new ArrayList<>(2), sheetName, sheetName);
    }

    public void exportExcel(HttpServletResponse response, String sheetName, String fileName) {
        this.exportExcel(response, new ArrayList<>(2), sheetName, fileName);
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) {
        this.exportExcel(response, list, sheetName, sheetName);
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String fileName) {
        this.templateName = fileName;
        this.init(list, sheetName, FtExcel.Type.EXPORT);
        this.exportExcel(response);
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String fileName, List<String> customFieldList) throws IOException {
        this.templateName = fileName;
        this.init(list, sheetName, FtExcel.Type.EXPORT, customFieldList);
        this.exportExcel(response);
    }


    public void exportExcel(HttpServletResponse response) {

        try {
            double sheetNo = Math.ceil((double) (this.list.size() / 65536));

            for (int index = 0; (double) index <= sheetNo; ++index) {
                this.createSheet(sheetNo, index);
                Row row = this.sheet.createRow(0);
                int column = 0;

                for (Object[] os : this.fields) {
                    FtExcel excel = (FtExcel) os[1];
                    this.createCell(excel, row, column++);
                }

                if (FtExcel.Type.EXPORT.equals(this.type)) {
                    this.fillExcelData(index, row);
                }
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(this.templateName, "UTF-8"));
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            this.wb.write(response.getOutputStream());
        } catch (Exception var20) {
            log.error("导出Excel异常{}", var20.getMessage());
            throw new RuntimeException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (this.wb != null) {
                try {
                    this.wb.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }
    }


    public void fillExcelData(int index, Row row) {
        int startNo = index * 65536;
        int endNo = Math.min(startNo + 65536, this.list.size());

        for (int i = startNo; i < endNo; ++i) {
            row = this.sheet.createRow(i + 1 - startNo);
            T vo = this.list.get(i);
            int column = 0;

            for (Object[] os : this.fields) {
                Field field = (Field) os[0];
                FtExcel excel = (FtExcel) os[1];
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++);
            }
        }

    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle) styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);
        return styles;
    }

    public Cell createCell(FtExcel attr, Row row, int column) {
        Cell cell = row.createCell(column);
        cell.setCellValue(attr.name());
        this.setDataValidation(attr, row, column);
        cell.setCellStyle((CellStyle) this.styles.get("header"));
        return cell;
    }

    public void setCellVo(Object value, FtExcel attr, Cell cell) {
        //20230703 bigdecimal字段导出带出很多0问题
        if (value instanceof BigDecimal) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value == null ? "" : new DecimalFormat("0.00").format((BigDecimal) value));
            return;
        }
        if (FtExcel.ColumnType.STRING == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
        } else if (FtExcel.ColumnType.NUMERIC == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((double) Integer.parseInt(value + ""));
        }

    }

    public void setDataValidation(FtExcel attr, Row row, int column) {
        if (attr.name().contains("注：")) {
            this.sheet.setColumnWidth(column, 6000);
        } else {
            this.sheet.setColumnWidth(column, (int) ((attr.width() + 0.72D) * 256.0D));
            row.setHeight((short) ((int) (attr.height() * 20.0D)));
        }

        if (StringUtils.isNotEmpty(attr.prompt())) {
            this.setXSSFPrompt(this.sheet, "", attr.prompt(), 1, 100, column, column);
        }

        if (attr.combo().length > 0) {
            this.setXSSFValidation(this.sheet, attr.combo(), 1, 100, column, column);
        }
        if (StringUtils.isNotEmpty(attr.dictType())) {
            if (!this.dictsMap.containsKey(attr.dictType()))
                this.dictsMap.put(attr.dictType(), DictUtil.getDictDataName(attr.dictType()));
            this.setXSSFValidation(this.sheet, this.dictsMap.get(attr.dictType()).values().toArray(new String[]{}), 1, 100, column, column);
        }
    }

    public Cell addCell(FtExcel attr, Row row, T vo, Field field, int column) {
        Cell cell = null;

        try {
            row.setHeight((short) ((int) (attr.height() * 20.0D)));
            if (attr.isExport()) {
                cell = row.createCell(column);
                CellStyle cellStyle = this.wb.createCellStyle();
                cellStyle.cloneStyleFrom((CellStyle) this.styles.get("data"));
                cellStyle.setAlignment(attr.alignment());
                cell.setCellStyle(cellStyle);
                Object value = this.getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String dictType = attr.dictType();
                String resolveMethod = attr.resolveMethod();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                } else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(this.convertByDictTypeForExport(String.valueOf(value), dictType));
                } else if (StringUtils.isNotEmpty(attr.processKey())) {

                    this.setCellVo(value, attr, cell);
                } else if (StringUtils.isNotEmpty(resolveMethod) && StringUtils.isNotNull(value)) {
                    int index = resolveMethod.lastIndexOf(".");
                    String classStr = resolveMethod.substring(0, index);
                    String methodStr = resolveMethod.substring(index + 1);
                    if (methodStr.contains("(")) {
                        methodStr = methodStr.substring(0, methodStr.indexOf("("));
                    }

                    Class clazz = Class.forName(classStr);
                    Method method = clazz.getDeclaredMethod(methodStr, Object.class);
                    Object val = method.invoke(clazz.newInstance(), value);
                    if (val != null) {
                        cell.setCellValue(val.toString());
                    }
                } else {
                    this.setCellVo(value, attr, cell);
                }
            }
        } catch (Exception var20) {
            log.error("导出Excel失败{}", var20);
        }

        return cell;
    }

    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    public static String convertByExp(String propertyValue, String converterExp) throws Exception {
        String[] convertSource = converterExp.split(",");

        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[0].equals(propertyValue)) {
                return itemArray[1];
            }
        }

        return propertyValue;
    }

    public String convertByDictTypeForExport(String propertyValue, String dictType) {
        Map<String, String> dictMap = this.dictsMap.get(dictType);
        String dictLabel = "";
        if (dictMap != null) {
            dictLabel = dictMap.get(propertyValue);
        }
        return StringUtils.isEmpty(dictLabel) ? propertyValue : dictLabel;
    }

    public static String reverseByExp(String propertyValue, String converterExp) {
        String[] convertSource = converterExp.split(",");

        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[1].equals(propertyValue)) {
                return itemArray[0];
            }
        }

        return propertyValue;
    }


    public String reverseByDictTypeForImport(String propertyValue, String dictType) {


        Map<String, String> dictMap = this.dictsMap.get(dictType);
        if (dictMap == null) {
            this.dictsMap.put(dictType, dictMap = DictUtil.getDictData(dictType));
        }
        return dictMap.get(propertyValue);
    }


    private Object getTargetValue(T vo, Field field, FtExcel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");

                for (String name : targets) {
                    o = this.getValue(o, name);
                }
            } else {
                o = this.getValue(o, target);
            }
        }

        return o;
    }

    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }

        return o;
    }

    private void createExcelFieldAndSetDict() {
        this.fields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(this.clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(this.clazz.getDeclaredFields()));
        //若存在自定义导出列，按照自定义列顺序导出
        if (CollectionUtils.isNotEmpty(customFieldList)) {
            Map<String, Field> fieldMap = tempFields.stream().filter(r -> r.isAnnotationPresent(FtExcel.class))
                    .collect(Collectors.toMap(r -> r.getAnnotation(FtExcel.class).name().trim(), r -> r));
            tempFields.clear();
            for (int i = 0; i < customFieldList.size(); i++) {
                Field field = fieldMap.get(customFieldList.get(i).trim());
                if (field == null)
                    continue;
                tempFields.add(field);
            }
        }
        for (Field tempField : tempFields) {
            if (tempField.isAnnotationPresent(FtExcel.class)) {
                FtExcel annotation = tempField.getAnnotation(FtExcel.class);
                this.putToField(tempField, annotation);
                // 设置字典项
                this.setDictsMap(annotation.dictType());
            }
        }
    }

    private void setDictsMap(String dictType) {
        // 如果字典项不为空  就去获取字典项目
        if (StringUtils.isNotEmpty(dictType) && this.list.size() > 0) {

            // 先从内存中获取有没有字典项目
            Map<String, String> dicMap = this.dictsMap.get(dictType);
            // 有就直接 return
            if (dicMap != null) return;
            // 没有从数据库中获取
            this.dictsMap.put(dictType, DictUtil.getDictDataName(dictType));
        }
    }

    private void putToField(Field field, FtExcel attr) {
        if (attr != null && (attr.type() == FtExcel.Type.ALL || attr.type() == this.type)) {
            this.fields.add(new Object[]{field, attr});
        }

    }

    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    public void createSheet(double sheetNo, int index) {
        this.sheet = this.wb.createSheet();
        this.styles = this.createStyles(this.wb);
        if (sheetNo == 0.0D) {
            this.wb.setSheetName(index, this.sheetName);
        } else {
            this.wb.setSheetName(index, this.sheetName + index);
        }

    }

    public Object getCellValue(Row row, int column) {
        // 为空 直接返回
        if (row == null) return null;
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            // 为空 直接返回
            if (cell == null) return null;

            // 不为空 继续
            if (cell.getCellType() != CellType.NUMERIC && cell.getCellType() != CellType.FORMULA) {
                if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            } else {
                val = cell.getNumericCellValue();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    val = DateUtil.getJavaDate((Double) val);
                } else if ((Double) val % 1.0D > 0.0D) {
                    val = (new DecimalFormat("0.00")).format(val);
                } else {
                    val = (new DecimalFormat("0")).format(val);
                }
            }
            return val;
        } catch (Exception var5) {
            return val;
        }
    }


    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String templateName) throws IOException {
        downloadTemplate(request, response, templateName, HashMap::new, null);
    }

    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String templateName, String fileName) throws IOException {
        downloadTemplate(request, response, templateName, HashMap::new, fileName);
    }

    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String templateName, ExcelFunction excelFunction, String fileName) throws IOException {
        fileName = StringUtils.isEmpty(fileName) ? templateName : fileName;
        // 先将 要导入的字段以及字典项进行初始化
        this.initDicMap();
        // 获取其他除了字典项之外的下拉数据
        Map<String, List> otherSelectDatas = excelFunction.initSelectList();
        Workbook sheets = null;
        InputStream inputStream = null;
        if (templateName == null || "".equals(templateName)) {
            sheets = WorkbookFactory.create(true);
            sheets.createSheet();
        } else {
            inputStream = getClass().getClassLoader().getResourceAsStream("template/" + templateName);
            sheets = WorkbookFactory.create(inputStream);
        }
        //获取创建的工作簿第一页
        Sheet shee = sheets.getSheetAt(0);
        //获取当前sheet最后一行数据对应的行索引
        int currentLastRowIndex = shee.getLastRowNum();
        int currentLastCellIndex = shee.getRow(0).getPhysicalNumberOfCells();
        Row row = null;
        Cell cell = null;
        //获取list或者value 的key值

        row = shee.getRow(currentLastRowIndex);


        //下拉选项写入模板
        int hiddenIndex = 1;
        for (int i2 = 1; i2 < currentLastCellIndex; i2++) {
            cell = row.getCell(i2);
            if (cell == null) continue;
            String keyName = cell.getStringCellValue();
            if (!StringUtils.isEmpty(keyName)) {
                List vals = dicTypeAndLabelMap.get(keyName);
                vals = CollectionUtils.isNotEmpty(vals) ? vals : otherSelectDatas.get(keyName);
                getSelectDatas(shee, vals, i2);
                hiddenIndex++;
            }
        }


        shee.removeRow(row);
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception e) {
            }
        }


    }


    private void initDicMap() {
        this.fields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(this.clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(this.clazz.getDeclaredFields()));

        List<String> dictTypes = new ArrayList<>();
        for (Field tempField : tempFields) {
            if (tempField.isAnnotationPresent(FtExcel.class)) {
                FtExcel annotation = tempField.getAnnotation(FtExcel.class);
                this.putToField(tempField, annotation);
                dictTypes.add(annotation.dictType());
            }
        }
        // 设置字典项
        this.setDicTypeAndLabelMap(dictTypes);
    }

    // 这里本来想一次获取 但是框架中没有提供这种方法  就循环进行查询吧  也不多
    private void setDicTypeAndLabelMap(List<String> dictTypes) {

        for (String dic : dictTypes) {
            List labels = dicTypeAndLabelMap.get(dic);
            // 内存中没有 就去查询数据 并放入内存
            if (labels == null) dicTypeAndLabelMap.put(dic, DictUtil.getDictValueList(dic));
        }

    }


    public static void getSelectDatas(Sheet sheet, List<String> dataList, int columnIndex) {
        if (CollectionUtils.isEmpty(dataList)) return;
        // 示例为第一个单元格
        try {
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 1000, columnIndex, columnIndex);
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            // 构造下拉框和数据
            DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(dataList.toArray(new String[0]));
            // 绑定下拉框和区域
            DataValidation validation = dataValidationHelper.createValidation(constraint, cellRangeAddressList);
            // 为sheet添加验证
            sheet.addValidationData(validation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void exportWithTemplate(HttpServletResponse response, List<T> list, int startRow, String templateName, String sheetName) {
        this.initWithTemp(list, sheetName, templateName, FtExcel.Type.EXPORT);
        this.exportWithTemplate(response, startRow);
    }

    private void exportWithTemplate(HttpServletResponse response, int startRow) {
        // 减掉一行
        startRow--;
        // excel表格 和 导入的数据 其实都可以看成二维数组
        FieldUtils fieldUtils = FieldUtils.init();
        try {
            for (int i = 0; i < this.list.size(); i++) {
                T t = list.get(i);
                // 每条数据都创建一行excel
                Row row = this.sheet.createRow(i + startRow);
                for (int fieldIndex = 0; fieldIndex < this.fields.size(); fieldIndex++) {
                    Object[] objects = this.fields.get(fieldIndex);
                    Field field = (Field) objects[0];
                    FtExcel anno = (FtExcel) objects[1];
                    int decimalScale = anno.decimalScale();
                    Object fieldVal = fieldUtils.getFieldVal(field.getName(), t);
                    if (fieldVal instanceof BigDecimal) {
                        BigDecimal fieldValBig = (BigDecimal) fieldVal;
                        fieldVal = fieldValBig.setScale(decimalScale, RoundingMode.DOWN);
                    }
                    Cell cell = row.createCell(fieldIndex);
                    cell.setCellValue(fieldVal == null ? "" : fieldVal + "");
                }
            }
            this.wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照实体类的字段顺序和指定行数导入数据并处理字典项()适合复杂表头的数据导入
     *
     * @param is
     * @param startRow
     * @return
     * @throws Exception
     */
    public List<T> importExcel(InputStream is, int startRow) throws Exception {
        return this.importExcel(is, "", startRow);
    }

    private List<T> importExcel(InputStream is, String sheetName, int startRow) throws Exception {
        this.type = FtExcel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<>();
        startRow = startRow - 1;
        Sheet sheet;
        if (StringUtils.isNotEmpty(sheetName)) {
            sheet = this.wb.getSheet(sheetName);
        } else {
            sheet = this.wb.getSheetAt(0);
        }

        // 为空 抛异常
        if (sheet == null) throw new IOException("文件sheet不存在");

        int rows = sheet.getPhysicalNumberOfRows();
        // 没数据 直接返回空集合
        if (rows <= 0) return this.list;

        // 字段
        Field[] allFields = this.clazz.getDeclaredFields();

        // key - 字段下标; v - 字段信息
        ArrayList<Field> pmsFields = new ArrayList<>();

        // 获取到字段加有注解的字段并放在Map中 这里要注意字段的顺序
        for (int i = 0; i < allFields.length; i++) {

            Field field = allFields[i];
            FtExcel anno = field.getAnnotation(FtExcel.class);
            if (anno != null && (anno.type() == FtExcel.Type.ALL || anno.type() == this.type)) {
                pmsFields.add(field);
            }

        }

        for (int i = startRow; i < rows; ++i) {
            Row row = sheet.getRow(i);
            T entity = null;
            for (int filedIndex = 0; filedIndex < pmsFields.size(); filedIndex++) {
                Object val = this.getCellValue(row, filedIndex);
                entity = entity == null ? (T) this.clazz.newInstance() : entity;
                Field field = pmsFields.get(filedIndex);
                Class<?> fieldType = field.getType();
                if (String.class == fieldType) {
                    String s = Convert.toStr(val);
                    if (StringUtils.endsWith(s, ".0")) {
                        val = StringUtils.substringBefore(s, ".0");
                    } else {
                        val = Convert.toStr(val);
                    }
                } else {
                    // 对不同类型的属性进行处理
                    val = FieldUtils.convertFiledVal(field, val);
                }
                FtExcel attr = field.getAnnotation(FtExcel.class);
                String propertyName = field.getName();
                if (StringUtils.isNotEmpty(attr.targetAttr())) {
                    propertyName = field.getName() + "." + attr.targetAttr();
                } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                    val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                } else if (StringUtils.isNotEmpty(attr.dictType())) {
                    val = this.reverseByDictTypeForImport(String.valueOf(val), attr.dictType());
                }

                ReflectUtils.invokeSetter(entity, propertyName, val);
            }
            list.add(entity);
        }

        return list;
    }

    public List<T> importTreeExcel(InputStream inputStream) throws Exception {
        return importTreeExcel(inputStream,null);
    }


    public List<T> importTreeExcel(InputStream inputStream, Integer startRow) throws Exception {

        List<T> ts;
        if (startRow == null) {
            ts = importExcel(inputStream);
        } else {
            ts = importExcel(inputStream, startRow);
        }

        this.init(list, sheetName, FtExcel.Type.IMPORT);
        List<Object[]> fieldsAnno = this.fields;
        String serFieldName = null;
        String childrenFieldName = null;
        String serStr = null;

        for (Object[] objects : fieldsAnno) {
            FtExcel ftExcel = (FtExcel) objects[1];
            if (ftExcel.serialNumFlag()) {
                // 如果当前的字段是序号列  就先存起来 等下用
                serFieldName = ((Field) objects[0]).getName();
                serStr = ftExcel.serialStr();
                childrenFieldName = ftExcel.childrenFieldName();
                break;
            }
        }
        // 没有指定序号列 抛出异常
        if (serFieldName == null) throw new RuntimeException("请指定序号列");
        // 
        FieldUtils init = FieldUtils.init();

        // 树形结果
        List<T> res = new ArrayList<>();
        String finalSerFieldName = serFieldName;


        // 根据序号拍个序先 不大好用
//        ts.sort((o1, o2) -> {
//            Integer length1 = String.valueOf(init.getFieldVal(finalSerFieldName, o1)).split("\\.").length;
//            Integer length2 = String.valueOf(init.getFieldVal(finalSerFieldName, o2)).split("\\.").length;
//            return length1.compareTo(length2) == 0 ? -1 : length1.compareTo(length2);
//        });

        HashMap<Integer, List<T>> lengthMap = new HashMap<>();
        for (T t : ts) {
            // 如果是属于TreeNode才继续进行
            if (!(t instanceof TreeNode)) throw new RuntimeException("请继承TreeNode");
            ((TreeNode<?>) t).setId(IdWorker.createId());
            // 序号
            String serNum = init.getFieldVal(serFieldName, t) + "";
            String[] split = serNum.split(".".equals(serStr) ? "\\." : serStr);
            // 
            List<T> lenList = lengthMap.get(split.length);
            // 如果当前数据为空 就new一个  然后
            lenList = CollectionUtils.isEmpty(lenList) ? new ArrayList<>() : lenList;
            lenList.add(t);
            // 放入map 等会儿用
            lengthMap.put(split.length, lenList);
        }

        // 由大到小
        String finalSerStr = serStr;
        String finalChildrenFieldName = childrenFieldName;
        lengthMap.keySet().stream().sorted(Comparator.comparing(Integer::intValue).reversed()).forEach(length -> {
            List<T> lengthList = lengthMap.get(length);
            if (length == 1) {
                res.addAll(lengthList);
            } else {
                for (T t : lengthList) {
                    String serNum = init.getFieldVal(finalSerFieldName, t) + "";
                    String parentSerNum = getStrBefore(serNum, finalSerStr);
                    ts.stream().filter(item -> parentSerNum.equals(init.getFieldVal(finalSerFieldName, item))).findFirst().ifPresent(i -> {
                        TreeNode treeNode = (TreeNode) i;
                        List children = treeNode.getChildren();
                        children = CollectionUtils.isEmpty(children) ? new ArrayList<>() : children;
                        init.setFieldVal("pid", ((TreeNode<?>) i).getId(), t);
                        init.setFieldVal(finalChildrenFieldName, children, i);
                        children.add(t);
                    });
                }
            }
        });
        return res;
    }

    public static void main(String[] args) {

    }


    private String getStrBefore(String strOrig, String str) {
        int lastIndex = strOrig.lastIndexOf(str);
        return strOrig.substring(0, lastIndex);

    }

}
