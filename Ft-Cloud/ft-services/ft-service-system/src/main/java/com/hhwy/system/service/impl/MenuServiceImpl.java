package com.hhwy.system.service.impl;

import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.mapper.MenuMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<SysMenu> getMenuId(String tenantKey, String component) {

        return menuMapper.getMenuId(tenantKey, component);
    }

}
