package com.hhwy.sp.designChangeList.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.sp.utils.TreeNodeUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.stereotype.Service;
import com.hhwy.sp.designChangeList.mapper.SgjsDesignChangeWbsMapper;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * wbsService业务层处理
 * 
 * @author wk
 * @date 2024-04-16
 */
@Service
public class SgjsDesignChangeWbsServiceImpl implements ISgjsDesignChangeWbsService {
    @Autowired
    private SgjsDesignChangeWbsMapper sgjsDesignChangeWbsMapper;

    /**
     * 查询wbs
     * 
     * @param id wbsID
     * @return wbs
     */
    @Override
    public SgjsDesignChangeWbs selectSgjsDesignChangeWbsById(Long id) {
        return sgjsDesignChangeWbsMapper.selectSgjsDesignChangeWbsById(id);
    }

    @Override
    public List<SgjsDesignChangeWbs> wbsTreeList(Long mainId) {
        SgjsDesignChangeWbs query = new SgjsDesignChangeWbs();
        query.setMainId(mainId);
        List<SgjsDesignChangeWbs> list = sgjsDesignChangeWbsMapper.selectSgjsDesignChangeWbsList(query);
        List<SgjsDesignChangeWbs> firstList = new ArrayList<>();
        Map<Long,SgjsDesignChangeWbs> wbsMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            SgjsDesignChangeWbs temp = list.get(i);
            wbsMap.put(temp.getId(),temp);
            if(temp.getParentId() == null || temp.getParentId() < 1){
                firstList.add(temp);
            }else{
                SgjsDesignChangeWbs parent = wbsMap.get(temp.getParentId());
                parent.setChildren(CollectionUtils.isEmpty(parent.getChildren())?new ArrayList<>():parent.getChildren());
                parent.getChildren().add(temp);
            }
        }
        return firstList;
    }

    /**
     * 查询wbs列表
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return wbs
     */
    @Override
    public List<SgjsDesignChangeWbs> selectSgjsDesignChangeWbsList(SgjsDesignChangeWbs sgjsDesignChangeWbs) {
        return sgjsDesignChangeWbsMapper.selectSgjsDesignChangeWbsList(sgjsDesignChangeWbs);
    }

    /**
     * 新增wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    @Override
    public int insertSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs) {
        sgjsDesignChangeWbs.setCreateTime(DateUtils.getNowDate());
        return sgjsDesignChangeWbsMapper.insertSgjsDesignChangeWbs(sgjsDesignChangeWbs);
    }

    /**
     * 修改wbs
     * 
     * @param sgjsDesignChangeWbs wbs
     * @return 结果
     */
    @Override
    public int updateSgjsDesignChangeWbs(SgjsDesignChangeWbs sgjsDesignChangeWbs) {
        sgjsDesignChangeWbs.setUpdateTime(DateUtils.getNowDate());
        return sgjsDesignChangeWbsMapper.updateSgjsDesignChangeWbs(sgjsDesignChangeWbs);
    }

    /**
     * 删除wbs对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsDesignChangeWbsByIds(String ids) {
        return sgjsDesignChangeWbsMapper.deleteSgjsDesignChangeWbsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除wbs信息
     * 
     * @param id wbsID
     * @return 结果
     */
    public int deleteSgjsDesignChangeWbsById(Long id) {
        return sgjsDesignChangeWbsMapper.deleteSgjsDesignChangeWbsById(id);
    }

    @Override
    @Transactional
    public int batchInsert(List<SgjsDesignChangeWbs> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return sgjsDesignChangeWbsMapper.batchInsert(list);
    }
}
