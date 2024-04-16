package com.hhwy.system.controller;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.SelfUserInfo;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.api.model.LoginUser;
import com.hhwy.system.core.service.ISysUserService;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.service.IUserService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : UserController
 * @Description : TODO
 * @Author : zxb
 * @Date :  15:01
 * @Version : V1.0
 **/
@RestController
@RequestMapping({"/selfSysUser"})
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ISysUserService userService;


    @GetMapping({"/selectUserListByUsernames"})
    public R<List<SysUser>> selectUserListByUsernames(@RequestParam("tenantKey") String tenantKey,
                                                      @RequestParam("usernames") String usernames) {
        List<SysUser> sysUsers = this.userService.selectUserListByUsernames(tenantKey, usernames);
        return R.ok(sysUsers);
    }


//    @CustomLogger(title = "根据4A编码获取人员信息", businessType = CustomBusinessType.SELECT)
    @PostMapping("/selectUserBy4A")
    public AjaxResult selectSysUserInfo(@RequestBody Map<String, String> params){
        String tokenId4A = params.get("tokenId4A");
        Map<String, String> user = userMapper.selectUserBy4A(tokenId4A);
        return AjaxResult.success(user);
    }

    //根据组织机构获取人员
//    @CustomLogger(title = "公共接口-根据组织机构获取人员", businessType = CustomBusinessType.SELECT)
    @PostMapping("/getUserInfoBy")
    public AjaxResult getUserInfoBy(@RequestBody SelfUserInfo selfUserInfo){
        return AjaxResult.success("查询成功!", iUserService.getUserInfoBy(selfUserInfo));
    }

    //获取同部门的人员  202401 修改为获取本项目人员
//    @CustomLogger(title = "公共接口-获取同部门人员信息", businessType = CustomBusinessType.SELECT)
    @PostMapping("/getUserInfoBySameDept")
    public AjaxResult getUserInfoBySameDept(@RequestBody SelfUserInfo selfUserInfo){
        //获取当前登录人的部门id
        LoginUser loginUser = this.tokenService.getLoginUser();
        selfUserInfo.setDeptId(loginUser.getSysUser().getDeptId());
        return AjaxResult.success("查询成功!", iUserService.getUserInfoBySameDept(selfUserInfo));
    }

    //最近选择的用户RecentSelectUser
//    @CustomLogger(title = "公共接口-更新最近选择人员", businessType = CustomBusinessType.OTHER)
    @PostMapping("/updateRecentSelectUser")
    public AjaxResult updateRecentSelectUser(@RequestBody Map<String, Object> params){
        List<String> userIds = (List<String>) params.get("userIds");
        if(CollectionUtils.isNotEmpty(userIds)){
           iUserService.updateRecentSelectUser(userIds);
        }
        return AjaxResult.success("操作成功！");
    }

//    @CustomLogger(title = "公共接口-获取最近选择人员", businessType = CustomBusinessType.SELECT)
    @PostMapping("/getRecentSelectUser")
    public AjaxResult getRecentSelectUser(){
        return AjaxResult.success("查询成功!", iUserService.getRecentSelectUser());
    }

    /**
     * 根据用户ids获取用户信息
     *
     * @param userIdList
     * @return
     */
//    @CustomLogger(title = "公共接口-根据用户ids获取用户信息", businessType = CustomBusinessType.SELECT)
    @PostMapping("/selectByUserIds")
    public AjaxResult selectByUserIds(@RequestBody List<String> userIdList){
        List<SysUser> list = iUserService.selectByUserIds(userIdList);
        return AjaxResult.success(list);
    }

    /**
     * 获取用户信息
     *
     * @param sysUser
     * @return
     */
//    @CustomLogger(title = "公共接口-获取用户信息", businessType = CustomBusinessType.SELECT)
    @PostMapping("/selectSysUserInfo")
    public AjaxResult selectSysUserInfo(@RequestBody SysUser sysUser){
        List<SysUser> userList = iUserService.selectSysUserInfo(sysUser);
        return AjaxResult.success(userList);
    }

    @PostMapping("/selectSysUserInfoList")
    public List<SysUser> selectSysUserInfoList(@RequestBody SysUser sysUser) {
        List<SysUser> userList = iUserService.selectSysUserInfo(sysUser);
        return userList;
    }

    @PostMapping("/select4AByUserNames")
    public AjaxResult select4AByUserNames(@RequestBody Map<String,String> map){
        List<SysUser> userList= iUserService.select4AByUserNames(map);
        return AjaxResult.success(userList);
    }

    //组件选择人员后添加人员;并且对总部版本该用户进行授权
    @PostMapping("/add")
    @CustomLogger(title = "选择用户进行授权", name = "选择用户进行授权" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult add(@RequestBody List<SysUser> userList) {
        String sb=iUserService.batchInsert(userList);
        return AjaxResult.success(sb.toString());
    }
    //流程组件选择所有人员
    @GetMapping({"/list"})
    public AjaxResult list(SysUser user) {
        this.startPage();
        List<SysUser> list = iUserService.selectUserList(user);
        return this.getDataTableAjaxResult(list);
    }

    @PostMapping("/selectUserInfoByUserNameAndTenant")
    public List<SysUser> selectUserInfoByUserNameAndTenant(@RequestBody Map<String,String> map) {
        List<SysUser> userList = iUserService.selectUserInfoByUserNameAndTenant(map);
        return userList;
    }
}
