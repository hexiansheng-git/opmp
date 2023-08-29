package com.hhwy.utils.myUtilPrepare;

import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**推送给物设前的基础信息封装
 * @author zqq
 * @create 2023-08-24 10:39
 */
@Slf4j
public class MyUtilPrepareUtil {

    public static final String YES = "1";
    public static final String NO = "0";

    public static  <T extends MyPrepareBaseEntity> void setCreateInfoBase(T t){
        try{
            t.setVersionNo(t.getVersion());
            t.setFormNo(t.getUnicode()+"-"+t.getVersionNo());
            t.setTitleName(t.getProjectName()+"-"+t.getTitleName());
            String isValid = NO;
            if (t.getVersion().compareTo(BigDecimal.ONE) == 0) {
                isValid = YES;
            }
            t.setIsValid(isValid);
            t.setValid(isValid);
            SysUser sysUser = SecurityUtils.getSysUser();
            Long deptId = sysUser == null ? null : sysUser.getDeptId();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 创建信息
            t.setCreateUser(userId);
            t.setCreateUserName(userName);
            t.setCreateTime(date);
            t.setDeptId(deptId);
            // 删除标识
            t.setDelFlag("0");
        } catch (Exception e){
            log.error("设置创建信息异常", e);
        }
    }

    public static  <T extends MyPrepareBaseEntity> void setUpdateInfoBase(T t){
        try{
            t.setVersionNo(t.getVersion());
//            t.setFormNo(t.getUnicode()+"-"+t.getVersionNo());
//            t.setTitleName(t.getProjectName()+"-"+t.getTitleName());
            String isValid = NO;
            if (t.getVersion().compareTo(BigDecimal.ONE) == 0) {
                isValid = YES;
            }
            t.setIsValid(isValid);
            t.setValid(isValid);
            SysUser sysUser = SecurityUtils.getSysUser();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 更新信息
            t.setUpdateUser(userId);
            t.setUpdateUserName(userName);
            t.setUpdateTime(date);
        } catch (Exception e){
            log.error("设置创建信息异常", e);
        }
    }

    public static  <T extends MyPrepareBaseEntity> void setCreateUpdateInfo(T t){
        try{
            t.setVersionNo(t.getVersion());
            t.setFormNo(t.getUnicode()+"-"+t.getVersionNo());
            t.setTitleName(t.getProjectName()+"-"+t.getTitleName());
            String isValid = NO;
            if (t.getVersion().compareTo(BigDecimal.ONE) == 0) {
                isValid = YES;
            }
            t.setIsValid(isValid);
            t.setValid(isValid);
            SysUser sysUser = SecurityUtils.getSysUser();
            Long deptId = sysUser == null ? null : sysUser.getDeptId();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 创建信息
            t.setCreateUser(userId);
            t.setCreateUserName(userName);
            t.setCreateTime(date);
            t.setDeptId(deptId);
            // 删除标识
            t.setDelFlag("0");


            // 更新信息
            t.setUpdateUser(userId);
            t.setUpdateUserName(userName);
            t.setUpdateTime(date);
        } catch (Exception e){
            log.error("设置创建信息异常", e);
        }
    }
}
