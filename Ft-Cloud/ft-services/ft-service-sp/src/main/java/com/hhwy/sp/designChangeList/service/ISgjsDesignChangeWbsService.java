package com.hhwy.sp.designChangeList.service;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import java.util.List;

/**
 * wbsService接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface ISgjsDesignChangeWbsService {
    /**
     * 查询wbs
     * 
     * @param id wbsID
     * @return wbs
     */
    public SgjsDesignChangeWbs selectSgjsDesignChangeWbsById(Long id);

    /**
     * 查询wbs列表
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return wbs集合
     */
    public List<SgjsDesignChangeWbs> selectSgjsDesignChangeWbsList(SgjsDesignChangeWbs sgjsDesignChangeWbs);

    /**
     * wbs全量树形
     * @param mainId
     * @param type   类型，1：元数据/2:变更后
     * @return
     */
    public List<SgjsDesignChangeWbs> wbsTreeList(Long mainId,String type);

    /**
     * 新增wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    public int insertSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs);
    
    public int batchInsert(List<SgjsDesignChangeWbs> list);

    /**
     * 修改wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    public int updateSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs);

    /**
     * 批量删除wbs
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeWbsByIds(String ids);

    /**
     * 删除wbs信息
     * 
     * @param id wbsID
     * @return 结果
     */
    public int deleteSgjsDesignChangeWbsById(Long id);
}
