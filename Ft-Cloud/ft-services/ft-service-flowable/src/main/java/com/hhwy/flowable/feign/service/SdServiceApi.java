package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 勘察设计
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-sd")
public interface SdServiceApi {

    /**
     * 大纲设计流程状态修改
     */
    @RequestMapping(value = "/kcsjOutlineReview/listener")
    void updateTaskStatus(@RequestParam("id") Long id);

    /**
     * 大纲设计 总部数据推送
     */
    @RequestMapping(value = "/kcsjOutlineReview/doSendGmlistener")
    void outLineDoSendGm(@RequestParam("tenantKey") String tenantKey);

    /**
     * 勘察设计成果评审提交监听器
     * @param id
     * @return
     */
    @PostMapping("/kcsjAchievementReview/submit")
    AjaxResult submitKcsjAchievementReviewProcess(@RequestParam("id") Long id);

    /**
     * 勘察设计成果评审结束监听器
     * @param id
     * @return
     */
    @PostMapping("/kcsjAchievementReview/listener")
    AjaxResult updateKcsjAchievementReviewProcess(@RequestParam("id") Long id);
}
