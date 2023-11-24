package com.hhwy.pm.word.export.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.word.export.service.ExportWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/exportWord")
public class ExportWordController {

    @Autowired
    private ExportWordService exportWordService;

    @PostMapping("exportProjectQqch")
    public AjaxResult exportProjectQqch(HttpServletResponse response) throws UnsupportedEncodingException {
        exportWordService.exportProjectQqch(response);
        return AjaxResult.success();
    }

    @GetMapping("testFile")
    public AjaxResult testFile(String fileGroupId){
        return AjaxResult.success(exportWordService.getPictureRenderDataList(fileGroupId));
    }
}
