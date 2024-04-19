package com.hhwy.sp.designChangeList.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.designChangeList.mapper.SgjsDesignChangeManageRecordMapper;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManageRecord;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageRecordService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 施工技术管理-设计变更管理-过程记录Service业务层处理
 * 
 * @author wk
 * @date 2024-04-16
 */
@Service
public class SgjsDesignChangeManageRecordServiceImpl implements ISgjsDesignChangeManageRecordService {
    @Autowired
    private SgjsDesignChangeManageRecordMapper sgjsDesignChangeManageRecordMapper;

    /**
     * 查询施工技术管理-设计变更管理-过程记录
     * 
     * @param id 施工技术管理-设计变更管理-过程记录ID
     * @return 施工技术管理-设计变更管理-过程记录
     */
    @Override
    public SgjsDesignChangeManageRecord selectSgjsDesignChangeManageRecordById(Long id) {
        return sgjsDesignChangeManageRecordMapper.selectSgjsDesignChangeManageRecordById(id);
    }

    /**
     * 查询施工技术管理-设计变更管理-过程记录列表
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 施工技术管理-设计变更管理-过程记录
     */
    @Override
    public List<SgjsDesignChangeManageRecord> selectSgjsDesignChangeManageRecordList(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord) {
        return sgjsDesignChangeManageRecordMapper.selectSgjsDesignChangeManageRecordList(sgjsDesignChangeManageRecord);
    }

    /**
     * 新增施工技术管理-设计变更管理-过程记录
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 结果
     */
    @Override
    public int insertSgjsDesignChangeManageRecord(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord) {
        sgjsDesignChangeManageRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageRecordMapper.insertSgjsDesignChangeManageRecord(sgjsDesignChangeManageRecord);
    }

    @Override
    @Transactional
    public int batchInsert(List<SgjsDesignChangeManageRecord> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return sgjsDesignChangeManageRecordMapper.batchInsert(list);
    }

    /**
     * 修改施工技术管理-设计变更管理-过程记录
     * 
     * @param sgjsDesignChangeManageRecord 施工技术管理-设计变更管理-过程记录
     * @return 结果
     */
    @Override
    public int updateSgjsDesignChangeManageRecord(SgjsDesignChangeManageRecord sgjsDesignChangeManageRecord) {
        sgjsDesignChangeManageRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageRecordMapper.updateSgjsDesignChangeManageRecord(sgjsDesignChangeManageRecord);
    }

    /**
     * 删除施工技术管理-设计变更管理-过程记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsDesignChangeManageRecordByIds(String ids) {
        return sgjsDesignChangeManageRecordMapper.deleteSgjsDesignChangeManageRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除施工技术管理-设计变更管理-过程记录信息
     * 
     * @param id 施工技术管理-设计变更管理-过程记录ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeManageRecordById(Long id) {
        return sgjsDesignChangeManageRecordMapper.deleteSgjsDesignChangeManageRecordById(id);
    }

    @Override
    public int deleteByMainId(Long mainId){
        return sgjsDesignChangeManageRecordMapper.deleteByMainId(mainId);
    }
}
