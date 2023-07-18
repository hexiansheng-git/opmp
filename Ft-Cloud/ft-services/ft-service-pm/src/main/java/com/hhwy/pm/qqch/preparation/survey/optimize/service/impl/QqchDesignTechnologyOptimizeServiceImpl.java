package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchDesignTechnologyOptimizeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Service
public class QqchDesignTechnologyOptimizeServiceImpl implements IQqchDesignTechnologyOptimizeService {

    @Autowired
    private QqchDesignTechnologyOptimizeMapper qqchDesignTechnologyOptimizeMapper;

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 获取设计技术优化要点集合
     * @return
     */
    @Override
    @Transactional
    public QqchDesignTechnologyOptimizeVo getQqchDesignTechnologyOptimizeVo() {
        QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo = new QqchDesignTechnologyOptimizeVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_design_technology_optimize");
        qqchDesignTechnologyOptimizeVo.setVersion(version);

        List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList = qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimizeList(version);
        qqchDesignTechnologyOptimizeVo.setQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);

        //TODO 获取确认状态

        return qqchDesignTechnologyOptimizeVo;
    }

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    @Transactional
    public void save(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        //删除旧数据
        QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize = new QqchDesignTechnologyOptimize();
        qqchDesignTechnologyOptimize.setVersion(qqchDesignTechnologyOptimizeVo.getVersion());
        qqchDesignTechnologyOptimizeMapper.deleteQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);

        //插入新数据
        this.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeVo.getQqchDesignTechnologyOptimizeList(), qqchDesignTechnologyOptimizeVo.getVersion());
    }

    /**
     * 确认
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    @Transactional
    public void confirm(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        this.save(qqchDesignTechnologyOptimizeVo);

        //TODO 修改确认状态

    }

    /**
     * 批量插入
     * @param qqchDesignTechnologyOptimizeList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList, BigDecimal version) {
        for (QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize : qqchDesignTechnologyOptimizeList) {
            qqchDesignTechnologyOptimize.setId(IdWorker.createId());
            qqchDesignTechnologyOptimize.setVersion(version);
            qqchDesignTechnologyOptimize.setValid(Valid.YES);
            qqchDesignTechnologyOptimize.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchDesignTechnologyOptimize.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignTechnologyOptimize.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignTechnologyOptimizeMapper.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);
    }
}
