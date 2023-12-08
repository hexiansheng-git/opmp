package com.hhwy.pm.core.sync.service;


/**
 * 一律将日志塞入master,方便统一处理
 * 参数 > ptVar1
 * 结果 > ptVar2
 */
public interface ISyncLogMasterService {

    /**
     * 成功
     * @param busName  业务功能名称
     * @param param    参数 > ptVar1
     * @param result   结果 > ptVar2
     */
    public void success(String busName,String param,String result);

    /**
     * 失败
     * @param busName   业务功能名称
     * @param param     参数 > ptVar1
     * @param result    结果 > ptVar2
     * @param failMsg   失败消息
     */
    public void fail(String busName,String param,String result,String failMsg);

    public void save(String busName, String param, String result,Integer status,String failMsg);

    /**
     * 保存
     * @param busName  业务功能名称
     * @param param     参数 > ptVar1
     * @param result    结果 > ptVar2
     * @param status    0:失败，1：成功
     * @param failMsg   失败消息
     * @param useMills   耗时
     */
    public void save(String busName, String param, String result,Integer status,String failMsg,Long useMills);
}
