package com.hhwy.sp.designChangeList.service;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;

import java.util.List;

/**
 * 施工技术管理-设计变更管理Service接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface ISgjsDesignChangeManageService {
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

    public void save(ChangeManagSaveVo changeManagSaveVo);
    
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
     * 批量删除施工技术管理-设计变更管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageByIds(String ids);

    /**
     * 删除施工技术管理-设计变更管理信息
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageById(Long id);
}
