package com.hhwy.sp.designChangeList.service;

import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageByIds(Long[] ids);

    /**
     * 查询施工技术管理-设计变更管理列表
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 施工技术管理-设计变更管理集合
     */
    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageList(SgjsDesignChangeManage sgjsDesignChangeManage);

    public Long save(ChangeManagSaveVo changeManagSaveVo);
    
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

    /**
     * 查询wbs挂接的清单
     * @param wbs
     * @return {list: 挂接的清单树形集合,wbsList: wbs树形 }
     */
    public Map<String,Object> relateList(SgjsDesignChangeWbs wbs);

    public List<SgjsDesignChangeList> importData(MultipartFile file) throws Exception;

    /**
     * 当前项目是否为直属项目
     * @return 0:否，1：是
     */
    public Integer isDirectProject();

    /**
     * 同步原数据
     * @param mainId 
     */
    public void sync(Long mainId);
    void  finishFlow(Long id);
    
    public void pushMsg(Long id);
}
