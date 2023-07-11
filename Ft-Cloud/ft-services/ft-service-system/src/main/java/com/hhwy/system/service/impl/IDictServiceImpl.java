package com.hhwy.system.service.impl;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.core.domain.SysDictData;
import com.hhwy.system.mapper.DictMapper;
import com.hhwy.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IDictServiceImpl implements IDictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    public List<SysDictData> selectDictValueByTypeAndLabel(String dictType, String dictLabel) {
        return dictMapper.selectDictValueByTypeAndLabel(dictType,dictLabel);
    }
}
