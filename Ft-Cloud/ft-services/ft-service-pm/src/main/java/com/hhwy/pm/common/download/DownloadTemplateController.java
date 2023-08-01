package com.hhwy.pm.common.download;

import com.alibaba.cloud.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("template/" + fileName);
        if(inStream == null){
            throw new RuntimeException("找不到指定文件!");
        }
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

}
