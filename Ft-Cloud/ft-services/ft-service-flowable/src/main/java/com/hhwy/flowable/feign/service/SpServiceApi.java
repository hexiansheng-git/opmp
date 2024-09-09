package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 施工技术
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-sp")
public interface SpServiceApi {

    /**
     * 修改四新成果管理 - 推送总部数据
     */
    @RequestMapping(value = "/sgjsFourNewsAchievement/doSendGmlistener")
    void fourNewsDoSendGm(@RequestParam("tenantKey") String tenantKey);
    /**
     * 修改四新成果管理
     */
    @RequestMapping(value = "/sgjsFourNewsAchievement/listener", method = RequestMethod.POST)
    AjaxResult updateFourNewsAchievement(@RequestParam("id") Long id, @RequestParam("isPass") String isPass);

    /**
     * 修改工艺工法管理
     */
    @RequestMapping(value = "/sgjsTechMethod/listener", method = RequestMethod.POST)
    AjaxResult updateSgjsTechMethod(@RequestParam("id") Long id, @RequestParam("isPass") String isPass);

    /**
     * 修改专利申报管理
     * @param id
     * @param pass
     * @return
     */
    @PostMapping("/sgjsPatentDeclare/listener")
    AjaxResult updatePatentDeclareProcess(@RequestParam("id") Long id,@RequestParam("pass") String pass);

    @PostMapping("/sgjsPatentDeclare/submit")
    AjaxResult submitPatentDeclareProcess(@RequestParam("id") Long id);

    /**
     * 修改论文发表管理
     * @param id
     * @param pass
     * @return
     */
    @PostMapping("/sgjsPaperPublish/listener")
    AjaxResult updatePaperPublishProcess(@RequestParam("id") Long id,@RequestParam("isPass") String isPass);

    @PostMapping("/sgjsPaperPublish/submit")
    AjaxResult submitPaperPublishProcess(@RequestParam("id") Long id);

    /**
     * 科技管理 - 科研课题管理 推送总部数据
     */
    @RequestMapping(value = "/sgsjTechnicalScienceTopic/doSendGmlistener")
    void technicalTopicDoSendGm(@RequestParam("tenantKey") String tenantKey);
    /**
     * 科技管理 - 科研课题管理 申请流程状态修改
     */
    @RequestMapping(value = "/sgsjTechnicalScienceTopic/appplyListener")
    void appplyListener(@RequestParam("id") Long id);
    /**
     * 科技管理 - 科研课题管理 立项流程状态修改
     */
    @RequestMapping(value = "/sgsjTechnicalScienceTopic/lxListener")
    void updateTaskStatus(@RequestParam("id") Long id);

    /**
     * 科技管理 - 科研课题管理 知会消息发布
     */
    @RequestMapping(value = "/sgsjTechnicalScienceTopic/messagePublic")
    void messagePublic();

    /**
     * 施工方案管理 -  推送总部数据
     */
    @RequestMapping(value = "/sgjsBuildScheme/doSendGmlistener")
    void schemeListDoSendGm(@RequestParam("tenantKey") String tenantKey);
    /**
     * 施工方案管理 - 施工方案清单
     */
    @RequestMapping(value = "/sgjsBuildScheme/listener")
    void updateBuildScheme(@RequestParam("id") Long id, @RequestParam("isPass")String isPass);

    @GetMapping("/sgjsBuildSchemeReview/submit")
    AjaxResult submitBuildSchemeReviewProcess(@RequestParam("id") Long id);

    @GetMapping("/sgjsBuildSchemeReview/listener")
    AjaxResult updateBuildSchemeReviewProcess(@RequestParam("id") Long id);

    @GetMapping("/sgjsBuildSchemeReview/deleteProcess")
    AjaxResult updateBuildSchemeReviewProcess2Init(@RequestParam("id") Long id);

    @GetMapping("/sgjsBuildSchemeReview/getNameById")
    AjaxResult getBuildReviewNameById(@RequestParam("id") Long id);

    @RequestMapping(value = "/designChangeList/listener", method = RequestMethod.POST)
    AjaxResult designChangeListListener(@RequestParam("id") Long id);

    @RequestMapping(value = "/designChangeList/pushMsg", method = RequestMethod.POST)
    AjaxResult pushMsg(@RequestParam("id") Long id);

    /**
     * 一、二级方案安全交底流程监听
     * 发消息to项目总工（）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/sgjsDiscloseRecord/listener")
    AjaxResult disCloseRecordListener(@RequestParam("id") Long id,@RequestParam("status") String status);
}
