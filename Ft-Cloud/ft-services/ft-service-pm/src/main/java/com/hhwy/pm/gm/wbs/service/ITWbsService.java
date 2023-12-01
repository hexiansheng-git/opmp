package com.hhwy.pm.gm.wbs.service;

import com.hhwy.pm.gm.wbs.domain.TWbs;

import java.util.List;
import java.util.Map;

/**
 * wbs
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark
 */
public interface ITWbsService {

    TWbs getTWbs(TWbs tWbs);

    List<TWbs> getTWbsListByMainId(Long id);

    List<TWbs> getTWbsList(TWbs tWbs);

    /**
     * 获取工程类型、产品类型下的wbs列表
     * @param name              wbs名称
     * @param nodeType          节点类型
     * @param parentId          父级Id
     * @return
     */
    List<TWbs> wbsListByType(String type,String name,String nodeType,Long parentId);

    /**
     * 懒加载树形
     * @param tWbs {mainId,parentId,name,nodeType}
     * @return
     */
    List<TWbs> lazySearchList(TWbs tWbs);

    /**
     * wbs全量树形
     * @param map
     * @return
     */
    public List<TWbs> wbsTreeList(Map map);

    /**
     * 拷贝数据
     * @param ids
     * @return
     */
    public Map<String,List<TWbs>> copyChildList(Long[] ids);

    /**
     * 获取默认的工程类型
     * @return
     */
    String getDefaultEngineeringType();

    int insertTWbs(TWbs tWbs);

    int insertTWbsMain(Map map);

    int insertTWbsList(List<TWbs> tWbsList);

    int updateTWbs(TWbs tWbs);

    int updateTWbsList(List<TWbs> tWbsList);

    int deleteTWbsMain(Long id);
    
    int deleteTWbs(TWbs tWbs);
    
    int deleteTWbsByPks(List<Long> tWbsPkList);
}
