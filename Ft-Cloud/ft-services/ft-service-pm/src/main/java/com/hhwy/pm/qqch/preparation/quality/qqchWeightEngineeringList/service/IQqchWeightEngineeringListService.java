package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListHistory;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark 
 */
public interface IQqchWeightEngineeringListService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchWeightEngineeringList getQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    /**
     * 获取最新生效版本重难点工程清单中选择的wbs以及其所有父级结构的集合
     * @return
     */
    Set<Long> getCurrentAndLowerLevelWbsIds();

    /**
     * 获取最新生效版本重难点工程清单中选择的wbs以及其所有父级结构的集合
     * @return
     */
    List<XmslWbs> keyDifficultProjectInventoryWbsList();

    QqchWeightEngineeringListVo getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int insertQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);


    int updateQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int updateQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList);
    
    int deleteQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int deleteQqchWeightEngineeringListByPks(List<Long> qqchWeightEngineeringListPkList);

    void save(QqchWeightEngineeringListVo vo);

    Map<String, List<QqchWeightEngineeringListHistory>> querySameProject(QqchWeightEngineeringListHistory param);
}
