package com.hhwy.pm.common.controller;

import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.pm.common.domain.PermissionMark;
import com.hhwy.pm.common.service.CommonService;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pmcommon")
public class PmCommonController {

    @Resource
    private CommonService commonService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getMaterial")
    public AjaxResult getMaterial(){
        String[] strings = {"00000195029"};
        List<Object> materialListRedis = this.redisUtils.hMultiGet(PmsConstant.MATERIALREDISKEY, Arrays.asList(strings));
        return AjaxResult.success(materialListRedis.get(0));
    }

    @GetMapping("/getMaterialList")
    public AjaxResult getMaterialList(){
        List<String> materialInfoLrange = redisUtils.lRange("materialInfoLrange", 0, 10);
        String s = JSON.toJSONString(materialInfoLrange);
        return AjaxResult.success(s);
    }

    @GetMapping("getCountryInfoByCodes")
    public AjaxResult getCountryInfoByCodes(){
        List<CountryInfo> countryInfoByCodes = CommonServiceUtil.getCountryInfoByCodes("AFG,BHR");
        return AjaxResult.success(countryInfoByCodes);
    }


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
