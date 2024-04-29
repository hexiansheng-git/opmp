package com.hhwy.system.service;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.system.api.domain.SysMenu;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISysPmService {

    List<SysDictData> selectDictValueByTypeAndLabel(String dictType, String dictLabel);

    /**
     * 导入字典
     * @param file
     * @return
     */
    public String importDict(MultipartFile file);

    List<SysMenu> testTenant(String s);
}
