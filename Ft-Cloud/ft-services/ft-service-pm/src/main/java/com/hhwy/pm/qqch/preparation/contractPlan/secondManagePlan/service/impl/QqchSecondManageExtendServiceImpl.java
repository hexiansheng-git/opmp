package com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.QqchSecondManageExtend;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.mapper.QqchSecondManageExtendMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.service.IQqchSecondManageExtendService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-10 09:14:55
 * @remark
 */
@Service
public class QqchSecondManageExtendServiceImpl implements IQqchSecondManageExtendService {

    @Autowired
    private QqchSecondManageExtendMapper qqchSecondManageExtendMapper;


    public QqchSecondManageExtend getQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend) {
        return qqchSecondManageExtendMapper.getQqchSecondManageExtend(qqchSecondManageExtend);
    }

    public List<QqchSecondManageExtend> getQqchSecondManageExtendList(QqchSecondManageExtend qqchSecondManageExtend) {
        return qqchSecondManageExtendMapper.getQqchSecondManageExtendList(qqchSecondManageExtend);
    }

    @Transactional
    public int insertQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend) {
        qqchSecondManageExtend.setId(IdWorker.createId());
        qqchSecondManageExtend.setCreateUser(SecurityUtils.getUserName());
        qqchSecondManageExtend.setCreateTime(DateUtils.getNowDate());
        return qqchSecondManageExtendMapper.insertQqchSecondManageExtend(qqchSecondManageExtend);
    }

    @Transactional
    public int insertQqchSecondManageExtendList(List<QqchSecondManageExtend> qqchSecondManageExtendList) {
        for (QqchSecondManageExtend qqchSecondManageExtend : qqchSecondManageExtendList) {
            qqchSecondManageExtend.setId(IdWorker.createId());
            qqchSecondManageExtend.setCreateUser(SecurityUtils.getUserName());
            qqchSecondManageExtend.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSecondManageExtendMapper.insertQqchSecondManageExtendList(qqchSecondManageExtendList);
    }

    @Transactional
    public int updateQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend) {
        qqchSecondManageExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchSecondManageExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchSecondManageExtendMapper.updateQqchSecondManageExtend(qqchSecondManageExtend);
    }

    @Transactional
    public int updateQqchSecondManageExtendList(List<QqchSecondManageExtend> qqchSecondManageExtendList) {
        for (QqchSecondManageExtend qqchSecondManageExtend : qqchSecondManageExtendList) {
            qqchSecondManageExtend.setUpdateUser(SecurityUtils.getUserName());
            qqchSecondManageExtend.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSecondManageExtendMapper.updateQqchSecondManageExtendList(qqchSecondManageExtendList);
    }

    @Transactional
    public int deleteQqchSecondManageExtend(QqchSecondManageExtend qqchSecondManageExtend) {
        qqchSecondManageExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchSecondManageExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchSecondManageExtendMapper.deleteQqchSecondManageExtend(qqchSecondManageExtend);
    }

    @Transactional
    public int deleteQqchSecondManageExtendByPks(List<Long> qqchSecondManageExtendPkList) {
        return qqchSecondManageExtendMapper.deleteQqchSecondManageExtendByPks(qqchSecondManageExtendPkList);
    }

    /**
     * 根据类型获取版本号
     * @param version
     * @param keyPointType
     * @return
     */
    public BigDecimal getVersion(BigDecimal version,String keyPointType) {
        if (version == null) {
            /*查询当前最大有效版本*/
            version = qqchSecondManageExtendMapper.selectMaxVersion(version);
        } else {
            /*查询当前最接近（小于等于）指定版本的版本号*/
            version = qqchSecondManageExtendMapper.selectLessOrEqualAssignVersion(keyPointType, version);
        }

        return version;
    }
}
