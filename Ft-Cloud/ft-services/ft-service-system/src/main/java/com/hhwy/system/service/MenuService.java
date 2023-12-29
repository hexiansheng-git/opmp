package com.hhwy.system.service;

import com.hhwy.system.api.domain.SysMenu;

import java.util.List;

public interface MenuService {


    List<SysMenu> getMenuId(String tenantKey, String component);
}
