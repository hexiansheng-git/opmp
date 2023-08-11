package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchKeyInventoryContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark
 */
@Repository
public interface QqchKeyInventoryContentMapper {

    QqchKeyInventoryContent getQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    List<QqchKeyInventoryContent> getQqchKeyInventoryContentList(QqchKeyInventoryContent qqchKeyInventoryContent);

    int insertQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int insertQqchKeyInventoryContentList(@Param("qqchKeyInventoryContentList") List<QqchKeyInventoryContent> qqchKeyInventoryContentList);

    int updateQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int updateQqchKeyInventoryContentList(@Param("list") List<QqchKeyInventoryContent> qqchKeyInventoryContentList);

    int deleteQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int deleteQqchKeyInventoryContentByPks(@Param("qqchKeyInventoryContentPkList") List<Long> qqchKeyInventoryContentPkList);
}
