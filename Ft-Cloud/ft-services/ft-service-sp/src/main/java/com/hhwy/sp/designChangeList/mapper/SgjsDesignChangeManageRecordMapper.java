package com.hhwy.sp.designChangeList.mapper;

import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManageRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工技术管理-设计变更管理-过程记录Mapper接口
 * 
 * @author wk
 * @date 2024-04-16
 */
public interface SgjsDesignChangeManageRecordMapper {
    /**
     * 查询施工技术管理-设计变更管理-过程记录
     * 
     * @param id 施工技术管理-设计变更管理-过程记录ID
     * @return 施工技术管理-设计变更管理-过程记录
     */
    public SgjsDesignChangeManageRecord selectSgjsDesignChangeManageRecordById(Long id);

    /**
     * 查询施工技术管理-设计变更管理-过程记录列表
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 施工技术管理-设计变更管理-过程记录集合
     */
    public List<SgjsDesignChangeManageRecord> selectSgjsDesignChangeManageRecordList(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord);

    /**
     * 新增施工技术管理-设计变更管理-过程记录
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 结果
     */
    public int insertSgjsDesignChangeManageRecord(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord);

    public int batchInsert(@Param("dataList") List<SgjsDesignChangeManageRecord> list);

    /**
     * 修改施工技术管理-设计变更管理-过程记录
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 结果
     */
    public int updateSgjsDesignChangeManageRecord(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord);

    /**
     * 删除施工技术管理-设计变更管理-过程记录
     * 
     * @param id 施工技术管理-设计变更管理-过程记录ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageRecordById(Long id);

    /**
     * 批量删除施工技术管理-设计变更管理-过程记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageRecordByIds(String[] ids);

    public int deleteByMainId(@Param("mainId") Long mainId);
}
