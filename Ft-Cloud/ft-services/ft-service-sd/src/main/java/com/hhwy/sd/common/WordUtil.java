package com.hhwy.sd.common;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

public class WordUtil {

    /**
    * 功能描述: 导出word
    * @param: response servlet响应
    * @param: map 数据
    * @param: exportFileName 文件名
    */
    public static void responeDocxFile(HttpServletResponse response, Map<String, Object> map, String templatePath, String exportFileName) throws Exception {
        XWPFDocument doc = WordExportUtil.exportWord07(templatePath, map);
        response.reset();
        response.setHeader("Access-Control-Allow-Origin" , "*");
        response.setContentType("application/msexcel");
        response.setContentType("text/html; charset=UTF-8");
        // 4.设置响应类型为Word文档
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        // 5.中文文件名处理，否则报错
        String encodedFileName = URLEncoder.encode(exportFileName , "UTF-8");
        response.setHeader("Content-Disposition" , "attachment;filename=" + encodedFileName + ".docx");
        // 6.将Word文档发送到浏览器
        doc.write(response.getOutputStream());
    }

}
