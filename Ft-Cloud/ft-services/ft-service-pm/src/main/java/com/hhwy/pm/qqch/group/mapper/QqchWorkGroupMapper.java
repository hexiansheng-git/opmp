package com.hhwy.pm.qqch.group.mapper;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
@Repository
public interface QqchWorkGroupMapper {
    /**
     * 获取最大有效版本数据
     * @return
     */
    QqchWorkGroup getValidMaxVersionQqchWorkGroup();

    /**
     * 获取最大未生效版本数据
     * @return
     */
    QqchWorkGroup getNoValidMaxVersionQqchWorkGroup();

    /**
     * 获取前期策划小组数据数量
     * @return
     */
    int getWorkGroupCount();

    QqchWorkGroup getQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup);

    int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int insertQqchWorkGroupList(@Param("qqchWorkGroupList") List<QqchWorkGroup> qqchWorkGroupList);

    int updateQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int updateQqchWorkGroupList(@Param("list") List<QqchWorkGroup> qqchWorkGroupList);

    int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int deleteQqchWorkGroupByPks(@Param("qqchWorkGroupPkList") List<Long> qqchWorkGroupPkList);
}
