package com.hhwy.sp.designChangeList.mapper;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设计变更清单Mapper接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface SgjsDesignChangeListMapper {
    /**
     * 查询设计变更清单
     * 
     * @param id 设计变更清单ID
     * @return 设计变更清单
     */
    public SgjsDesignChangeList selectSgjsDesignChangeListById(Long id);

    /**
     * 查询设计变更清单列表
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 设计变更清单集合
     */
    public List<SgjsDesignChangeList> selectSgjsDesignChangeListList(SgjsDesignChangeList sgjsDesignChangeList);

    public List<SgjsDesignChangeList> selectWbsAsDesignList(Long mainId);
    
    public int batchInsert(@Param("dataList") List<SgjsDesignChangeList> list);

    /**
     * 新增设计变更清单
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 结果
     */
    public int insertSgjsDesignChangeList(SgjsDesignChangeList sgjsDesignChangeList);

    /**
     * 修改设计变更清单
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 结果
     */
    public int updateSgjsDesignChangeList(SgjsDesignChangeList sgjsDesignChangeList);

    /**
     * 删除设计变更清单
     * 
     * @param id 设计变更清单ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeListById(Long id);

    /**
     * 批量删除设计变更清单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeListByIds(String[] ids);

    public int deleteByWbsCodes(Map map);
}
