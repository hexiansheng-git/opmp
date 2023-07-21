package com.hhwy.pm.common.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.common.service.CommonService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private CommonService commonService;


    /**校验数据是否能进行调整
     * @param tn
     * @param businessId
     * @return
     */
    @GetMapping("/checkProjectAdjust")
    public AjaxResult checkProjectAdjust(String tn,Long businessId){
        try{
            commonService.canAdjustOnly(businessId,tn);
            return  new AjaxResult(200,"成功");
        }catch (CustomBusinessException e){
            e.printStackTrace();
            throw new BaseException(e.getMsg());
        }catch (RuntimeException b){
            b.printStackTrace();
            throw new BaseException(b.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询项目异常");
        }
    }

    /**
     * 检验菜单是否有编辑权限
     * @param menuId
     * @return
     */
    @GetMapping("checkIsEditable")
    public AjaxResult checkIsEditable(String menuId){
        boolean isEditable = commonService.checkIsEditable(menuId);
        return AjaxResult.success(isEditable);
    }

}
