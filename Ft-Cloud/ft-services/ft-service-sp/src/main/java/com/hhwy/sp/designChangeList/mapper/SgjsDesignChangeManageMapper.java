package com.hhwy.sp.designChangeList.mapper;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import java.util.List;

/**
 * 施工技术管理-设计变更管理Mapper接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface SgjsDesignChangeManageMapper {
    /**
     * 查询施工技术管理-设计变更管理
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 施工技术管理-设计变更管理
     */
    public SgjsDesignChangeManage selectSgjsDesignChangeManageById(Long id);

    /**
     * 查询施工技术管理-设计变更管理列表
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 施工技术管理-设计变更管理集合
     */
    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageList(SgjsDesignChangeManage sgjsDesignChangeManage);

    /**
     * 获取最新的生效数据
     * @return
     */
    public Long lastEffectId();
    
    public Integer countSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage);

    /**
     * 新增施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    public int insertSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage);

    /**
     * 修改施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    public int updateSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage);

    /**
     * 删除施工技术管理-设计变更管理
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageById(Long id);

    /**
     * 批量删除施工技术管理-设计变更管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageByIds(String[] ids);

    int deleteWbsByMainId(Long mainId);
}
