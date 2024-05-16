package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.controller;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo.QqchSpecialBigEquListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchSpecialBigEquListService;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:39:00
 * @remark 8.4.1
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialBigEquList")
public class QqchSpecialBigEquListController extends BaseController {

    @Autowired
    private IQqchSpecialBigEquListService qqchSpecialBigEquListService;


    /**
     *  列表页面
     * @param qqchSpecialBigEquListParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-特种设备管控策划", name = "8.4.1 特种设备及大型设备清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSpecialBigEquListList(@Validated(ValidationGroups.Select.class) QqchSpecialBigEquList qqchSpecialBigEquListParam) {
        QqchSpecialBigEquListVo vo = qqchSpecialBigEquListService.getQqchSpecialBigEquListList(qqchSpecialBigEquListParam);
        return AjaxResult.success(vo);
    }

    /**
     *  保存/确认/提交
     * @param vo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-特种设备管控策划", name = "8.4.1 特种设备及大型设备清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSpecialBigEquListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigEquListVo vo) {
        qqchSpecialBigEquListService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-特种设备管控策划", name = "8.4.1 特种设备及大型设备清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSpecialBigEquList(@Validated(ValidationGroups.Get.class) QqchSpecialBigEquList qqchSpecialBigEquListParam) {
        QqchSpecialBigEquList qqchSpecialBigEquList = qqchSpecialBigEquListService.getQqchSpecialBigEquList(qqchSpecialBigEquListParam);
        return AjaxResult.success(qqchSpecialBigEquList);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-特种设备管控策划", name = "8.4.1 特种设备及大型设备清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSpecialBigEquList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigEquList qqchSpecialBigEquListParam) {
        qqchSpecialBigEquListService.insertQqchSpecialBigEquList(qqchSpecialBigEquListParam);
        return AjaxResult.success(qqchSpecialBigEquListParam);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSpecialBigEquList(@Validated(ValidationGroups.Update.class) @RequestBody QqchSpecialBigEquList qqchSpecialBigEquListParam) {
        return toAjax(qqchSpecialBigEquListService.updateQqchSpecialBigEquList(qqchSpecialBigEquListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSpecialBigEquListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSpecialBigEquList> qqchSpecialBigEquListListParam) {
        return toAjax(qqchSpecialBigEquListService.updateQqchSpecialBigEquListList(qqchSpecialBigEquListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSpecialBigEquList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSpecialBigEquList qqchSpecialBigEquListParam) {
        return toAjax(qqchSpecialBigEquListService.deleteQqchSpecialBigEquList(qqchSpecialBigEquListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSpecialBigEquListByPks(@PathVariable Long[] ids) {
        List<Long> qqchSpecialBigEquListPkList = Arrays.asList(ids);
        return toAjax(qqchSpecialBigEquListService.deleteQqchSpecialBigEquListByPks(qqchSpecialBigEquListPkList));
    }

    /***
     * 功能描述: 查询自有设备进场验收模块  调用物设接口（现场设备）
     * 作者: 何文杰
     * 时间: 2024/5/13
     */
    @PostMapping("/selfEquDetail")
    public AjaxResult selfEquDetail(@RequestBody QqchSpecialBigEquList qqchSpecialBigEquListParam){
        if (StringUtils.isBlank(qqchSpecialBigEquListParam.getManageCode())) {
            return AjaxResult.error("管理编号不能为空");
        }
        return qqchSpecialBigEquListService.selfEquDetail(qqchSpecialBigEquListParam.getManageCode());
    }


}
