package com.hhwy.pm.qqch.group.mapper;

import java.util.List;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
@Repository
public interface QqchWorkGroupMapper {

    QqchWorkGroup getQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup);

    int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int insertQqchWorkGroupList(@Param("qqchWorkGroupList") List<QqchWorkGroup> qqchWorkGroupList);

    int updateQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int updateQqchWorkGroupList(@Param("list") List<QqchWorkGroup> qqchWorkGroupList);

    int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    int deleteQqchWorkGroupByPks(@Param("qqchWorkGroupPkList") List<Long> qqchWorkGroupPkList);
}
