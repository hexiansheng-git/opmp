package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlanningPrjImgMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningPrjImgService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-07-17 14:18:26
 * @remark 
 */
@Service
public class QqchWorkPlanningPrjImgServiceImpl implements IQqchWorkPlanningPrjImgService {

    @Autowired
    private QqchWorkPlanningPrjImgMapper qqchWorkPlanningPrjImgMapper;

                                                                                                                                                                
    public QqchWorkPlanningPrjImg getQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

    public List<QqchWorkPlanningPrjImg> getQqchWorkPlanningPrjImgList(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImg);
    }

    @Transactional
    public int insertQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        //查询是否有历史数据
        List<QqchWorkPlanningPrjImg> list = qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjImgList(new QqchWorkPlanningPrjImg());
        if(!ObjectNullUtil.isEmpty(list)){//说明之前上传过 直接修改
            QqchWorkPlanningPrjImg img = list.get(0);
            qqchWorkPlanningPrjImg.setId(img.getId());
            qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
            qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
            qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
            return 1;
        }else{//新增
            qqchWorkPlanningPrjImg.setId(IdWorker.createId());
            qqchWorkPlanningPrjImg.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlanningPrjImg.setCreateTime(DateUtils.getNowDate());
            qqchWorkPlanningPrjImg.setValid("1");
            qqchWorkPlanningPrjImg.setVersion(new BigDecimal(0));
            return qqchWorkPlanningPrjImgMapper.insertQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
        }
    }

    @Transactional
    public int insertQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList) {
        for (QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg : qqchWorkPlanningPrjImgList) {
            qqchWorkPlanningPrjImg.setId(IdWorker.createId());
            qqchWorkPlanningPrjImg.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlanningPrjImg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanningPrjImgMapper.insertQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImgList);
    }

    @Transactional
    public int updateQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

            @Transactional
        public int updateQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList) {
            for (QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg : qqchWorkPlanningPrjImgList) {
                qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImgList);
        }
    
    @Transactional
    public int deleteQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningPrjImgMapper.deleteQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

            @Transactional
        public int deleteQqchWorkPlanningPrjImgByPks(List<Long> qqchWorkPlanningPrjImgPkList) {
            return qqchWorkPlanningPrjImgMapper.deleteQqchWorkPlanningPrjImgByPks(qqchWorkPlanningPrjImgPkList);
        }
    }
