package com.hhwy.utils.excelUtil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  导出工具类
 *
 * @author ldd
 * @date 2022-12-10
 */
public class ExcelUtilByTemplate {


    /**
     * 导出固定表头Excel
     * Author wang.lch
     * @param response
     * @param list
     * @param name
     * @param sheetName
     * @param model
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, List list,
                                   String name, String sheetName, BaseRowModel model) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String date = sdf.format(new Date());
        String fileName = new String(name.getBytes()) + date + ".xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream(), model.getClass()).sheet(sheetName).doWrite(list);
    }
    /**
     * 导出复杂表头的Excel 先单组数据填充，再多组数据填充
     * @param response
     * @param list 多组数据List
     * @param map 单组数据Map
     * @param outFileName 导出的Excel名称
     * @param inputStream Excel模板的路径名称
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, List  list, Map<String,Object> map,
                                   String outFileName, InputStream inputStream ) throws Exception{
        //告诉response下载的是excel文件
        response.setContentType("application/vnd.ms-excel");
        //告诉response使用utf-8编码格式
        response.setCharacterEncoding("utf-8");
        //.withTemplate(templateFileName)就是读取模板
        //.write(ExcelUtil.getOutputStream(outFileName, response))是将数据写入文件，并交给response
        ExcelWriter excelWriter = EasyExcel.write(ExcelUtilByTemplate.getOutputStream(outFileName, response))
                .withTemplate(inputStream)
                .build();
        //创建Sheet
        //设置excel Sheet为第几张并设置名称
        //.writerSheet(0,"第一个")中前面的参数为sheetNo,就是第几张sheet
        //第二参数为sheet名称
        //不写就是默认
        WriteSheet writeSheet  = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        //.direction(WriteDirectionEnum.VERTICAL)这个是设置list填入的时候是纵向填入
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).forceNewRow(Boolean.TRUE).build();
        //这里是将list填充到excel中。
        //会去找模板上对应的数据填入，例如模板中的{list.getGoodsName}就是下面List集合中名为goodsName字段对应的数据
        //new FillWrapper("list", selectOrderDTO.getSelectOrderGoodsDTOS())前面的参数是设置一个填入的list名
        //后面的参数是获得的list，里面就包含了要填入的数据
        //.fill()主要就是将数据填入excel中
        if(CollectionUtils.isNotEmpty(list)){
            excelWriter.fill(new FillWrapper(list), fillConfig, writeSheet);
            //这里是将一些普通数据放到map中，方便填入，可以看getStringObjectMap()。
            //map的String是对应的名称，Object就是数据了。
            //将数据填入
            if(map.isEmpty()){
                excelWriter.fill(map, writeSheet);
            }
        }
        //关闭
        excelWriter.finish();
    }
    /**
     * 这是ExcelUtil.getOutputStream
     * 这里就是将文件下载交给了浏览器
     * @return
     */
    public static OutputStream getOutputStream(String Name, HttpServletResponse response) throws Exception {
        //这里是对文件的重命名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String date = sdf.format(new Date());
        String fileName = new String(Name.getBytes()) + date + ".xlsx";
        // 这里文件名如果涉及中文一定要使用URL编码,否则会乱码
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return response.getOutputStream();
    }
}

