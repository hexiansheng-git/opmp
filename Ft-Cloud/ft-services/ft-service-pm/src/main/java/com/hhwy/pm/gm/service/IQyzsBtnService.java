package com.hhwy.pm.gm.service;

import com.hhwy.pm.gm.enums.QyzsBtnEnum;

import java.util.Map;

/**
 *  获取知识库按钮标志 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/3/19 16:58   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/3/19 16:58    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public interface IQyzsBtnService {

    /**
     * 勘察设计知识库-制度及管理方法库
     * @return {btnName:按钮名称,filegroupid:附件id}
     */
    public Map qyzsSystemManageMethod(QyzsBtnEnum e);
    /**
     *  勘察设计知识库-文件模板库
     *  @return {btnName:按钮名称,filegroupid:附件id}
     */
    public Map qyzsFileMode(QyzsBtnEnum e);
    /**
     *  施工技术知识库-制度及管理方法库
     *  @return {btnName:按钮名称,filegroupid:附件id}
     */
    public Map qyzsConstructionManageMethod(QyzsBtnEnum e);
    /**
     *  施工技术知识库-文件模板库
     *  @return {btnName:按钮名称,filegroupid:附件id}
     */
    public Map qyzsConstructionFileMode(QyzsBtnEnum e);
}
