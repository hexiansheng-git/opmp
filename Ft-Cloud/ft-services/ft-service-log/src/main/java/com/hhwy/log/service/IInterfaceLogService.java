package com.hhwy.log.service;



import com.hhwy.domain.base.InterfaceLog.InterfaceLog;

import java.util.List;

public interface IInterfaceLogService {

    InterfaceLog selectInterfaceLogById(Long id);

    List<InterfaceLog> selectInterfaceLogList(InterfaceLog interfaceLog);
    /**
     * 成功日志插入
     *
     * @param interfaceLog
     * @return
     */
    void insertSuccessLog(InterfaceLog interfaceLog);

    /**
     * 失败日志插入
     *
     * @param interfaceLog
     * @return
     */
    void insertFailLog(InterfaceLog interfaceLog);

    /**
     * 日志插入
     *
     * @param interFaceName
     * @param req
     * @param res
     */
    void insertFailLog(String interFaceName,String req, String res);

    /**
     * 日志插入
     *
     * @param interFaceName
     * @param req
     * @param res
     * @param sysTenant
     */
    void insertSuccessLog(String interFaceName, String req, String res);
}
