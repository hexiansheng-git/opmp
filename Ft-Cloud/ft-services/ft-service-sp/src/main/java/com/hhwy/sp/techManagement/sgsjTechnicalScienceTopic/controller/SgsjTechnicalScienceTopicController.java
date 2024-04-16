package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.utils.WordUtil;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.FileDto;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.utils.easyExcel.CustomMergeStrategy;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/***
 * 功能描述: 科技管理 - 科研课题研发管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgsjTechnicalScienceTopic")
public class SgsjTechnicalScienceTopicController extends BaseController {

    @Autowired
    private ISgsjTechnicalScienceTopicService sgsjTechnicalScienceTopicService;
    @Autowired
    private PmServiceApi pmServiceApi;


    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping
    public AjaxResult getSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Get.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopic);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/listPage")
    public AjaxResult listPage(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        startPage();
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicParam);
        return getDataTableAjaxResult(sgsjTechnicalScienceTopicList);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }
    //课题申请明细
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/applyDetail")
    public AjaxResult applyDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.applyDetail(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }

    //立项明细
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/lxDetail")
    public AjaxResult lxDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getDetail(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }

    //课题申请里的保存
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/applyAdd")
    public AjaxResult applyAdd(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicService.applyAdd(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicParam);
    }

    //课题立项里的保存
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/lxAdd")
    public AjaxResult insertSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic result = sgsjTechnicalScienceTopicService.lxAdd(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(result);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgsjTechnicalScienceTopicList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicListParam) {
        sgsjTechnicalScienceTopicService.insertSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicListParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicListParam);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:update")
    @PostMapping("/update")
    public AjaxResult updateSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Update.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        return toAjax(sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgsjTechnicalScienceTopicList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicListParam) {
        return toAjax(sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicListParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        return toAjax(sgsjTechnicalScienceTopicService.deleteSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/deleteById")
    public AjaxResult deleteById(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicService.deleteById(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgsjTechnicalScienceTopicByPks(@PathVariable Long[] ids) {
        List<Long> sgsjTechnicalScienceTopicPkList = Arrays.asList(ids);
        return toAjax(sgsjTechnicalScienceTopicService.deleteSgsjTechnicalScienceTopicByPks(sgsjTechnicalScienceTopicPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) throws IOException {
        List<SgsjTechnicalScienceTopicDTO> exportData = sgsjTechnicalScienceTopicService.export(sgsjTechnicalScienceTopicParam);
        if (CollUtil.isNotEmpty(exportData)){
            EasyExcel.write(response.getOutputStream()).sheet(DateUtil.today())
                    .head(SgsjTechnicalScienceTopicDTO.class)
                    .registerWriteHandler(new CustomMergeStrategy())
                    .doWrite(exportData);
        }
    }

    /**
     * 功能描述: 申请流程结束监听
     * @param id  业务id
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/appplyListener")
    public void appplyListener(@RequestParam("id") Long id){
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic.setPtVar1(String.valueOf(id));
        //走第三分支(修改后通过)，流程结束 最终状态为"通过"
        sgsjTechnicalScienceTopic.setApplyState("3");
        sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        //3代表流程结束，需要创建一条新数据给立项用
        SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
        param.setPtVar1(String.valueOf(id));
        SgsjTechnicalScienceTopic applyData = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopic(param);
        sgsjTechnicalScienceTopicService.addLxData(applyData);
    }

    /**
     * 功能描述: 立项流程结束监听
     * @param id  业务id
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/lxListener")
    public void updateTaskStatus(@RequestParam("id") Long id){
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic.setPtVar2(String.valueOf(id));
        //流程结束
        sgsjTechnicalScienceTopic.setTaskStatus("5");
        sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    /**
     * 功能描述: 消息发布监听
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public AjaxResult messagePublic(String title, String message){
        return sgsjTechnicalScienceTopicService.messagePublic(title, message);
    }

    /**
     * 功能描述: 获取消息发送的角色对象
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/getRoleName")
    public AjaxResult getRoleName(){
        String roleName = sgsjTechnicalScienceTopicService.getRoleName();
        return AjaxResult.success(roleName);
    }

    /**
     * 功能描述: 导出专家意见
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/exportExpertSuggest")
    public void exportExpertSuggest(HttpServletResponse response, SgsjTechnicalScienceTopic param) throws Exception{
//        Assert.isTrue(StrUtil.isNotBlank(param.getTopicCurentNode()), "课题进度不能为空");
        Assert.isTrue(ObjectUtil.isNotNull(param.getId()), "id不能为空");
        Map<String, Object> map = sgsjTechnicalScienceTopicService.getExpertSuggest(param);
        String templatePath = "0302-科研课题申请专家意见导出表.docx";
        String exportFileName = "科研课题申请专家意见";
        String topicCurentNode = param.getTopicCurentNode();
        if (StrUtil.isNotBlank(topicCurentNode)) {
            switch (topicCurentNode) {
                case "1":
                    templatePath = "0303-科研课题立项专家意见导出表.docx";
                    exportFileName = "科研课题立项专家意见";
                    break;
                case "2":
                    templatePath = "0304-科研课题大纲审查专家意见导出表.docx";
                    exportFileName = "科研课题大纲审查专家意见";
                    break;
                case "5":
                    templatePath = "0305-科研课题验收专家意见导出表.docx";
                    exportFileName = "科研课题验收专家意见";
                    break;
            }
        }
        WordUtil.responeDocxFile(response, map, "template/"+templatePath, exportFileName);
    }


    @Autowired
    private RestTemplate restTemplate;

    //获取文件名称测试
    @RequestMapping("/getFileName")
    public void getFileName(){
        String fileUrl = "http://10.0.1.118/fileservice/fileext/";
        String fileGroupId = "45045c5b6e29ac96a811fc969fb47784";
        String url = fileUrl + "list/" + fileGroupId;
        String jsonString = restTemplate.getForObject(url, String.class);
        List<FileDto> fileDtoList = JSONObject.parseArray(jsonString, FileDto.class);
        if(CollectionUtils.isEmpty(fileDtoList)){
            return;
        }
        Set<String> objects = new HashSet<>();
        for (FileDto fileDto : fileDtoList) {
            String fileName = fileDto.getFileName();
            String extension = fileDto.getExtension();
        }
    }

    //监听器，推送总部数据
    @RequestMapping("/doSendGmlistener")
    public void technicalTopicDoSendGm(@RequestParam("tenantKey") String tenantKey){
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        ThreadPoolUtil.execute(() -> sgsjTechnicalScienceTopicService.doSendGm(tenantKey, "admin", projectDto));

    }
}
