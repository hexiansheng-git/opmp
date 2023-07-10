package com.hhwy.pm.qqch.group.service;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
public interface IQqchWorkGroupService {

    QqchWorkGroup getQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup);

    /**
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    /**
     * 修改工作小组
     * @param qqchWorkGroup
     * @return
     */
    int updateQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    /**
     * 删除工作小组
     * @param qqchWorkGroup
     * @return
     */
    int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int deleteQqchWorkGroupByPks(List<Long> qqchWorkGroupPkList);

    /**
     * 根据id获取工作小组信息
     * @param id
     * @return
     */
    QqchWorkGroup getQqchWorkGroupById(Long id);
}
