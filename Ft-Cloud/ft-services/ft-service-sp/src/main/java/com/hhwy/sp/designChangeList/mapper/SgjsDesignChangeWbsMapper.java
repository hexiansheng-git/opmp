package com.hhwy.sp.designChangeList.mapper;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * wbsMapper接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface SgjsDesignChangeWbsMapper {
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

    public int batchInsert(@Param("dataList") List<SgjsDesignChangeWbs> list);
    
    /**
     * 新增wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    public int insertSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs);

    /**
     * 修改wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    public int updateSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs);

    /**
     * 删除wbs
     * 
     * @param id wbsID
     * @return 结果
     */
    public int deleteSgjsDesignChangeWbsById(Long id);

    /**
     * 批量删除wbs
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeWbsByIds(String[] ids);
}
