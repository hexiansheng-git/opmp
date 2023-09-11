package com.hhwy.pm.common.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.common.constant.PermissionMark;
import com.hhwy.pm.common.service.CommonService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common2")
public class CommonController2 {

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
        PermissionMark permissionMark = commonService.checkIsEditable(menuId);
        return AjaxResult.success(permissionMark.getMsg(),permissionMark);
    }

}
