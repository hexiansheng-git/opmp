package com.hhwy.system.service;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.core.domain.SysDictData;

import java.util.List;

public interface IDictService {

    List<SysDictData> selectDictValueByTypeAndLabel(String dictType, String dictLabel);
}
