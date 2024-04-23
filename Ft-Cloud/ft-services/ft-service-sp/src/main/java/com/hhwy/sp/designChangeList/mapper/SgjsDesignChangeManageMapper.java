package com.hhwy.sp.designChangeList.mapper;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageByIds(@Param("ids") Long[] ids);

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

    int deleteWbsByType(@Param("mainId") Long mainId, @Param("type")String type);
    int deleteListByType(@Param("mainId") Long mainId,@Param("type")Integer type);

    @Update("update sgjs_design_change_wbs set del_flag = 1 where main_id = #{mainId}")
    int deleteWbsByMainIdVitual(@Param("mainId") Long mainId);
    @Update("update sgjs_design_change_list set del_flag = 1 where main_id = #{mainId}")
    int deleteListByMainIdVitual(@Param("mainId") Long mainId);
    @Update("update sgjs_design_change_manage_record set del_flag = 1 where main_id = #{mainId}")
    int deleteRecordByMainIdVitual(@Param("mainId") Long mainId);

    @Update("select count(1) from sgjs_design_change_manage where del_flag = 0 and main_id != #{mainId} and change_code = #{changeCode}")
    int checkExist(@Param("mainId") Long mainId,String changeCode);

    @Update("update sgjs_design_change_manage set pt_var1=1 where id = #{id}")
    int effect(@Param("id") Long id);
    
}
