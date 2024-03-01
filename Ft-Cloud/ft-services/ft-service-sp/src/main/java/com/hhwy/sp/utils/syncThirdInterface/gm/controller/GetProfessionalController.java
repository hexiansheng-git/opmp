package com.hhwy.sp.utils.syncThirdInterface.gm.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.core.ziyuanku.vo.TProfessionalInfo;
import com.hhwy.sp.utils.syncThirdInterface.gm.service.GetProfessionalInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 获取专家库
 *
 * @author lcf
 * @date 2024-02-28
 */
@RestController
@RequestMapping("getGm")
public class GetProfessionalController {

    @Autowired
    private GetProfessionalInterface getProfessionalInterface;

    @PostMapping("/getDataList")
    public AjaxResult getDataList(@RequestBody Map<String,Object> map){
        AjaxResult result = getProfessionalInterface.syncGmProfessional(map);
        return result;
    }
}
