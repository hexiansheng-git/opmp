package com.hhwy.pm.qqch.sgch.mainpl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItemPre;
import com.hhwy.pm.qqch.sgch.mainpl.mapper.QqchMainPlanItemPreMapper;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemPreService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
@Service
public class QqchMainPlanItemPreServiceImpl implements IQqchMainPlanItemPreService {

    private static Map<String,String> typeMap = new HashMap<>();
    static {
        typeMap.put("Finish to Start", "0");
        typeMap.put("Start to Start", "1");
        typeMap.put("Finish to Finish", "2");
        typeMap.put("Start to Finish", "3");
    }

    @Autowired
    private QqchMainPlanItemPreMapper qqchMainPlanItemPreMapper;


    public QqchMainPlanItemPre getQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre) {
        return qqchMainPlanItemPreMapper.getQqchMainPlanItemPre(qqchMainPlanItemPre);
    }

    public List<QqchMainPlanItemPre> getQqchMainPlanItemPreList(QqchMainPlanItemPre qqchMainPlanItemPre) {
        List<QqchMainPlanItemPre> qqchMainPlanItemPreList = qqchMainPlanItemPreMapper.getQqchMainPlanItemPreList(qqchMainPlanItemPre);
        if(!CollectionUtils.isEmpty(qqchMainPlanItemPreList)) {
            for (QqchMainPlanItemPre qqchMainPlanItemPre1 : qqchMainPlanItemPreList) {
                qqchMainPlanItemPre1.setTarget(qqchMainPlanItemPre1.getItemId());
                qqchMainPlanItemPre1.setSource(qqchMainPlanItemPre1.getPredecessorItemId());
                qqchMainPlanItemPre1.setType(typeMap.get(qqchMainPlanItemPre1.getType()));
            }
        }
        return qqchMainPlanItemPreList;
    }

    @Transactional
    public int insertQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre) {
        qqchMainPlanItemPre.setId(IdWorker.createId());
        qqchMainPlanItemPre.setCreateUser(SecurityUtils.getUserName());
        qqchMainPlanItemPre.setCreateTime(DateUtils.getNowDate());
        return qqchMainPlanItemPreMapper.insertQqchMainPlanItemPre(qqchMainPlanItemPre);
    }

    @Transactional
    public int insertQqchMainPlanItemPreList(List<QqchMainPlanItemPre> qqchMainPlanItemPreList) {
        if(CollectionUtils.isEmpty(qqchMainPlanItemPreList)) {
            return 0;
        }
        for (QqchMainPlanItemPre qqchMainPlanItemPre : qqchMainPlanItemPreList) {
            qqchMainPlanItemPre.setId(IdWorker.createId());
            qqchMainPlanItemPre.setCreateUser(SecurityUtils.getUserName());
            qqchMainPlanItemPre.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanItemPreMapper.insertQqchMainPlanItemPreList(qqchMainPlanItemPreList);
    }

    @Transactional
    public int updateQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre) {
        qqchMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanItemPreMapper.updateQqchMainPlanItemPre(qqchMainPlanItemPre);
    }

    @Transactional
    public int updateQqchMainPlanItemPreList(List<QqchMainPlanItemPre> qqchMainPlanItemPreList) {
        for (QqchMainPlanItemPre qqchMainPlanItemPre : qqchMainPlanItemPreList) {
            qqchMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
            qqchMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanItemPreMapper.updateQqchMainPlanItemPreList(qqchMainPlanItemPreList);
    }

    @Transactional
    public int deleteQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre) {
        qqchMainPlanItemPre.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanItemPre.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanItemPreMapper.deleteQqchMainPlanItemPre(qqchMainPlanItemPre);
    }

    @Transactional
    public int deleteQqchMainPlanItemPreByPks(List<Long> qqchMainPlanItemPrePkList) {
        return qqchMainPlanItemPreMapper.deleteQqchMainPlanItemPreByPks(qqchMainPlanItemPrePkList);
    }
}
