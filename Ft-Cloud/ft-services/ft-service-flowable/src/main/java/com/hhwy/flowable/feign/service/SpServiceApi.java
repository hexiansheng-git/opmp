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
    AjaxResult updatePaperPublishProcess(@RequestParam("id") Long id,@RequestParam("pass") String pass);

    @PostMapping("/sgjsPaperPublish/submit")
    AjaxResult submitPaperPublishProcess(@RequestParam("id") Long id);
    /**
     * 科技管理 - 科研课题管理
     */
    @RequestMapping(value = "/sgsjTechnicalScienceTopic/listener", method = RequestMethod.POST)
    void updateTaskStatus(@RequestParam("id") Long id);
}
