package com.hhwy.sp.utils;

import com.alibaba.cloud.commons.io.IOUtils;
import com.hhwy.utils.excelUtil.DownTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author zhenglili
 * @date 2023-07-05 15:50:00
 * @remark 下载
 */
@RestController
@RequestMapping("/download")
public class DownloadTemplateController {

    @Autowired
    private DownTemplate downTemplate;

    /**
     * 下载模板
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(@RequestParam(value = "fileName") String fileName, HttpServletResponse response)
        throws Exception {
        // 读取文件流（可从jar包取）
        InputStream inStream = this.getClass().getClassLoader()
            .getResourceAsStream("template/" + fileName);
        OutputStream outputStream = response.getOutputStream();

        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        IOUtils.copy(inStream, outputStream, 100);
        outputStream.flush();
        inStream.close();
        outputStream.close();
    }

    /**
     * 下载模板，.xls或.xlsx
     * @param fileName 模板名称，带后缀
     * @param exportName 导出名称，不带后缀
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping( "/downloadTemplateWithSuffix")
    public void downloadTemplateTest(@RequestParam String fileName,
                                     @RequestParam(required = false) String exportName,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        downTemplate.downloadTemplateWithSuffix(request,response,fileName,exportName);
    }


    /**
     * 下载模板，.xls或.xlsx
     * @param fileName 模板名称，带后缀
     * @param exportName 导出名称，不带后缀
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping( "/ftDownLoad")
    public void ftDownLoad(@RequestParam String fileName,
                                     @RequestParam(required = false) String exportName,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        downTemplate.ftDownLoad(request, response, fileName, exportName);
    }

}
