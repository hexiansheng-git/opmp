package com.hhwy.sp.designChangeList.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.designChangeList.mapper.SgjsDesignChangeListMapper;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设计变更清单Service业务层处理
 * 
 * @author wk
 * @date 2024-04-16
 */
@Service
public class SgjsDesignChangeListServiceImpl implements ISgjsDesignChangeListService {
    @Autowired
    private SgjsDesignChangeListMapper sgjsDesignChangeListMapper;

    @Override
    public List<SgjsDesignChangeList> treeList(Long mainId, Integer type, String wbsCode) {
        if(mainId == null || StringUtils.isBlank(wbsCode))
            return new ArrayList<>(2);
        type = ObjectUtils.nvl(type,1);
        SgjsDesignChangeList query = new SgjsDesignChangeList();
        query.setMainId(mainId);
        query.setType(type);
        query.setWbsCode(wbsCode);
        List<SgjsDesignChangeList> list = this.sgjsDesignChangeListMapper.selectSgjsDesignChangeListList(query);
        List<SgjsDesignChangeList> firstList = new ArrayList<>();
        Map<Long,SgjsDesignChangeList> listMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            SgjsDesignChangeList temp = list.get(i);
            listMap.put(temp.getId(),temp);
            if(temp.getPid() == null || temp.getPid() < 1){
                firstList.add(temp);
            }else{
                SgjsDesignChangeList parent = listMap.get(temp.getId());
                parent.setChildren(CollectionUtils.isEmpty(parent.getChildren())?new ArrayList<>():parent.getChildren());
                parent.getChildren().add(temp);
            }
        }
        return firstList;
    }

    /**
     * 查询设计变更清单
     * 
     * @param id 设计变更清单ID
     * @return 设计变更清单
     */
    @Override
    public SgjsDesignChangeList selectSgjsDesignChangeListById(Long id) {
        return sgjsDesignChangeListMapper.selectSgjsDesignChangeListById(id);
    }

    /**
     * 查询设计变更清单列表
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 设计变更清单
     */
    @Override
    public List<SgjsDesignChangeList> selectSgjsDesignChangeListList(SgjsDesignChangeList sgjsDesignChangeList) {
        return sgjsDesignChangeListMapper.selectSgjsDesignChangeListList(sgjsDesignChangeList);
    }

    /**
     * 新增设计变更清单
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 结果
     */
    @Override
    public int insertSgjsDesignChangeList(SgjsDesignChangeList sgjsDesignChangeList) {
        sgjsDesignChangeList.setCreateTime(DateUtils.getNowDate());
        return sgjsDesignChangeListMapper.insertSgjsDesignChangeList(sgjsDesignChangeList);
    }

    /**
     * 修改设计变更清单
     * 
     * @param sgjsDesignChangeList 设计变更清单
     * @return 结果
     */
    @Override
    public int updateSgjsDesignChangeList(SgjsDesignChangeList sgjsDesignChangeList) {
        sgjsDesignChangeList.setUpdateTime(DateUtils.getNowDate());
        return sgjsDesignChangeListMapper.updateSgjsDesignChangeList(sgjsDesignChangeList);
    }

    /**
     * 删除设计变更清单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsDesignChangeListByIds(String ids) {
        return sgjsDesignChangeListMapper.deleteSgjsDesignChangeListByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设计变更清单信息
     * 
     * @param id 设计变更清单ID
     * @return 结果
     */
    public int deleteSgjsDesignChangeListById(Long id) {
        return sgjsDesignChangeListMapper.deleteSgjsDesignChangeListById(id);
    }

    @Override
    @Transactional
    public int batchInsert(List<SgjsDesignChangeList> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return sgjsDesignChangeListMapper.batchInsert(list);
    }

    @Override
    @Transactional
    public int deleteByWbsCodes(Long mainId, String type, List<String> wbsCodes) {
        if(CollectionUtils.isEmpty(wbsCodes))
            return 0;
        return sgjsDesignChangeListMapper.deleteByWbsCodes(ObjectUtils.toMap("mainId",mainId,"type",type,"wbsCodes",wbsCodes));
    }
}
