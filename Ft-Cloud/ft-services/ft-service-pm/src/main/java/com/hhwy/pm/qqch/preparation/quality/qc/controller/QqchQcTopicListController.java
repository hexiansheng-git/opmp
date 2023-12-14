package com.hhwy.pm.qqch.preparation.quality.qc.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcTopicListVo;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcTopicListService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
@Validated
@RestController
@RequestMapping("/qqchQcTopicList")
public class QqchQcTopicListController extends BaseController {

    @Autowired
    private IQqchQcTopicListService qqchQcTopicListService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQcTopicList:list")
    @GetMapping("/getList")
    public AjaxResult getQqchQcTopicList(BigDecimal version) {
        QqchQcTopicListVo qqchQcTopicListVo = qqchQcTopicListService.getQqchQcTopicListList(version);
        return AjaxResult.success(qqchQcTopicListVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQcTopicListVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQcTopicList:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.6 QC活动", name = "\n" +
            "9.6.1 QC课题清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchQcTopicListVo qqchQcTopicListVo) {
        qqchQcTopicListService.batchSave(qqchQcTopicListVo);
        return AjaxResult.success();
    }

    /**
     * 查询历史同领域产品项目QC清单
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQcTopicList:list")
    @GetMapping("/getHistoryList")
    public AjaxResult getHistoryList(QqchQcTopicList qqchQcTopicList) {
        List<QqchQcTopicList> list = qqchQcTopicListService.getHistoryList(qqchQcTopicList);
        return AjaxResult.success(list);
    }
}
