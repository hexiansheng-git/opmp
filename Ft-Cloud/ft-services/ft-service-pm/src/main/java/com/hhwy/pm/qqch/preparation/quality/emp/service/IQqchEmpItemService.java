package com.hhwy.pm.qqch.preparation.quality.emp.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.domain.QyzsQualitySpecialInspection;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
public interface IQqchEmpItemService {

    QqchEmpItem getQqchEmpItem(QqchEmpItem qqchEmpItem);

    List<QqchEmpItem> getQqchEmpItemList(QqchEmpItem qqchEmpItem);

    /**
     * 获取标准wbs下的
     * @param standardId,inspectionProject,inspectionName
     * @return {list:[],total}
     */
    Object getQyzsQualitySpecialInspectionList(Long standardId,String inspectionProject,String inspectionName);

    /**
     * 合并用户选择的检查表与当前选中数据
     * @return
     */
    List<QqchEmpItem> merge(QqchEmpItem item,List<QyzsQualitySpecialInspection> inspectionList,List<QqchEmpItem> list);

    int insertQqchEmpItem(QqchEmpItem qqchEmpItem);

    int insertQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList);

    int updateQqchEmpItem(QqchEmpItem qqchEmpItem);

    int updateQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList);

    int deleteQqchEmpItem(QqchEmpItem qqchEmpItem);

    int deleteQqchEmpItemByPks(List<Long> qqchEmpItemPkList);

    void save(CompileEntity<List<List<QqchEmpItem>>> dto);

    List<XmslWbs> wbsList(CompileEntity dto);

    CompileEntity<List<QqchEmpItem>> itemList(QqchEmpItem dto);
    
}
