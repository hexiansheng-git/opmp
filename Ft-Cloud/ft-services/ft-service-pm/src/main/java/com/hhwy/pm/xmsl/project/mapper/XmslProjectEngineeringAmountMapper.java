package com.hhwy.pm.xmsl.project.mapper;

import java.util.List;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.vo.XmslProjectEngineeringAmountExportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量
 */
@Repository
public interface XmslProjectEngineeringAmountMapper {

    XmslProjectEngineeringAmount getProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    List<XmslProjectEngineeringAmount> getProjectEngineeringAmountList(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int insertProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int insertProjectEngineeringAmountList(@Param("xmslProjectEngineeringAmountList") List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList);

    int updateProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int updateProjectEngineeringAmountList(@Param("list") List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList);

    int deleteProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int deleteProjectEngineeringAmountByPks(@Param("projectEngineeringAmountPkList") List<Long> projectEngineeringAmountPkList);

    List<XmslProjectEngineeringAmountExportVo> getProjectEngineeringAmountExportVoList(XmslProjectEngineeringAmount projectEngineeringAmountParam);
}
