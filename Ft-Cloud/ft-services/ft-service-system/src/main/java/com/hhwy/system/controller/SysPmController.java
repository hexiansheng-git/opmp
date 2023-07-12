package com.hhwy.system.controller;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.core.domain.SysDictData;
import com.hhwy.system.core.service.ISysDictTypeService;
import com.hhwy.system.service.ISysPmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/dict")
public class SysPmController {
    @Autowired
    ISysDictTypeService dictTypeService;

    @Autowired
    ISysPmService dictService;

    @GetMapping("/resolveDict")
    public AjaxResult resolveDictList(@RequestParam("dictType") String dictType, @RequestParam("dictValue") String dictValue) {
        List<SysDictData> list = dictTypeService.selectDictDataByTypeAndValues(dictType, dictValue);
        return AjaxResult.success(list);
    }

    @GetMapping("/reverseDict")
    public AjaxResult reverseDictList(@RequestParam("dictType") String dictType, @RequestParam("dictValue") String dictLabel) {
        List<SysDictData> list = dictService.selectDictValueByTypeAndLabel(dictType, dictLabel);
        return AjaxResult.success(list);
    }

    @GetMapping("/typeData")
    public AjaxResult dictType(@RequestParam String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList();
        }
        return AjaxResult.success(data);
    }
}
