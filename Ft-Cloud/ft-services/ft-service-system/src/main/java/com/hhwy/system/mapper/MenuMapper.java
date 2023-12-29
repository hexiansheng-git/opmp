package com.hhwy.system.mapper;

import com.hhwy.system.api.domain.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper {

    //查询部门树形结构 update: zxb 2022-12-30
    @Select("SELECT tenant_key as tenantKey, menu_id as menuId FROM sys_menu_v2 where del_flag = '0' and component = #{component} and FIND_IN_SET(tenant_key, #{tenantKey})")
    List<SysMenu> getMenuId(@Param("tenantKey") String tenantKey, @Param("component") String component);

}