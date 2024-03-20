package com.hhwy.sd.core;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 知识库对应施工技术按钮
 *
 * @author lcf
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/btnSdCon")
public class ZhishikuSdController {

    @Autowired
    private PmServiceApi pmServiceApi;

    @PostMapping("/getBtnNameByType")
    public AjaxResult getBtnNameByType(@RequestBody Map<String ,Object> map){
        AjaxResult result = pmServiceApi.qyzsBtnInfo(map);
        return result;
    }
}
