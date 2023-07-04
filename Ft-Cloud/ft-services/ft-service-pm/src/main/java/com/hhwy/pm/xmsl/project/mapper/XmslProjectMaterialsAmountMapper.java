package com.hhwy.pm.xmsl.project.mapper;

import java.util.List;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-03 09:48:36
 * @remark 主要材料数量
 */
@Repository
public interface XmslProjectMaterialsAmountMapper {
                                                                                                                                                                                                                                                                                                    
    XmslProjectMaterialsAmount getProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    List<XmslProjectMaterialsAmount> getProjectMaterialsAmountList(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    int insertProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    int insertProjectMaterialsAmountList(@Param("xmslProjectMaterialsAmountList") List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList);

    int updateProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    int updateProjectMaterialsAmountList(@Param("list") List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList);
    
    int deleteProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    int deleteProjectMaterialsAmountByPks(@Param("projectMaterialsAmountPkList") List<Long> projectMaterialsAmountPkList);
}
