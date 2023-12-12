package com.hhwy.log.service.impl;

import com.hhwy.domain.base.InterfaceLog.InterfaceLog;
import com.hhwy.log.mapper.InterfaceLogMapper;
import com.hhwy.log.service.IInterfaceLogService;
import com.hhwy.log.utils.log.InterfaceLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterfaceLogServiceImpl implements IInterfaceLogService {

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    @Override
    public InterfaceLog selectInterfaceLogById(Long id) {
        return interfaceLogMapper.selectInterfaceLogById(id);
    }

    @Override
    public List<InterfaceLog> selectInterfaceLogList(InterfaceLog interfaceLog) {
        List<InterfaceLog> list = interfaceLogMapper.selectInterfaceLogList(interfaceLog);
        return list;
    }

    @Override
    public void insertSuccessLog(InterfaceLog interfaceLog) {
        InterfaceLogUtil.insertSuccessLog(interfaceLog);
    }

    @Override
    public void insertFailLog(InterfaceLog interfaceLog) {
        InterfaceLogUtil.insertFailLog(interfaceLog);
    }

    @Override
    public void insertFailLog(String interFaceName, String req, String res) {
        InterfaceLogUtil.insertFailLog(interFaceName,req,res);
    }

    @Override
    public void insertSuccessLog(String interFaceName, String req, String res) {
        InterfaceLogUtil.insertSuccessLog(interFaceName,req,res);
    }
}
