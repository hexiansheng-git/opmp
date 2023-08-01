package com.hhwy.utils.excelUtil;/**
 * @description TODO
 * @date 2022-11-24 16:42
 * @author zq
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zq
 * @date 2022年11月24日 16:42
 */
@RestController
@RequestMapping("/exports")
public class ExcelUtilController {
    @Autowired
    private DownTemplate downTemplate;
    /**
     * 下载模板
     * @author zq
     * @date 2022/11/24 16:43
     * @param templateName
     * @param name
     * @param request
     * @param response
     */
    @GetMapping( "/downloadTemplate")
    public void newDownloadTemplate(@RequestParam String templateName, @RequestParam String name,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        downTemplate.downloadExcel(request,response,templateName + ".xls",name+".xls");
    }

    /**
     * 下载模板，.xls或.xlsx
     * @param templateName 模板名称，带后缀
     * @param exportName 导出名称，不带后缀
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping( "/downloadTemplateWithSuffix")
    public void downloadTemplateTest(@RequestParam String templateName,
                                     @RequestParam String exportName,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        downTemplate.downloadTemplateWithSuffix(request,response,templateName,exportName);
    }


    /**
     * 下载模板
     * @author zq
     * @date 2022/11/24 16:43
     * @param params
     * @param request
     * @param response
     */
    @GetMapping( "/downloadTemplateWithParam")
    public void downloadTemplateWithParam(@RequestParam Map<String,String> params,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        downTemplate.downloadTemplateWithParam(request,response,params);
    }
}
