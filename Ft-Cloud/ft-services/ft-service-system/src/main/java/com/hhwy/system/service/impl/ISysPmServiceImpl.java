package com.hhwy.system.service.impl;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.core.domain.SysDictData;
import com.hhwy.system.mapper.SysPmMapper;
import com.hhwy.system.service.ISysPmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISysPmServiceImpl implements ISysPmService {

    @Autowired
    SysPmMapper sysPmMapper;

    @Override
    public List<SysDictData> selectDictValueByTypeAndLabel(String dictType, String dictLabel) {
        return sysPmMapper.selectDictValueByTypeAndLabel(dictType,dictLabel);
    }
}
