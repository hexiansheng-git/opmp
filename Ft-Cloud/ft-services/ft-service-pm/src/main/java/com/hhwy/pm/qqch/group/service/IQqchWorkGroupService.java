package com.hhwy.pm.qqch.group.service;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
public interface IQqchWorkGroupService {

    /**
     * 根据id获取工作小组信息
     * @param id
     * @return
     */
    QqchWorkGroup getQqchWorkGroupById(Long id);

    /**
     * 台账（历史记录）
     * @param qqchWorkGroup
     * @return
     */
    List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup);

    /**
     * 调整
     * @param id
     * @return
     */
    QqchWorkGroup adjustQqchWorkGroup(Long id);

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
     * 提交
     * @param qqchWorkGroup
     * @return
     */
    void submit(QqchWorkGroup qqchWorkGroup);

    /**
     * 删除工作小组
     * @param qqchWorkGroup
     * @return
     */
    int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup);
}
