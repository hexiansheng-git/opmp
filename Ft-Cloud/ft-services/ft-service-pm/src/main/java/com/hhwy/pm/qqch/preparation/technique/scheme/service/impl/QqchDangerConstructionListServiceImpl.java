package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchDangerConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
@Service
public class QqchDangerConstructionListServiceImpl implements IQqchDangerConstructionListService {

    @Autowired
    private QqchDangerConstructionListMapper qqchDangerConstructionListMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private QqchConstructionListMapper qqchConstructionListMapper;

    public QqchDangerConstructionListVo getQqchDangerConstructionListList() {
        QqchDangerConstructionListVo vo = new QqchDangerConstructionListVo();

        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_danger_construction_list");
        vo.setVersion(maxVersion);
        QqchDangerConstructionList qryParam = new QqchDangerConstructionList();
        qryParam.setVersion(maxVersion);
        List<QqchDangerConstructionList> list = qqchDangerConstructionListMapper
            .getQqchDangerConstructionListList(qryParam);
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void syncData() {

        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(maxVersion);
        // 获取方案清单数据
        List<QqchConstructionList> constructionList = qqchConstructionListMapper
            .getBigDangerLevelConstructionList(qryParam);
        // todo

    }

    @Transactional
    public int insertQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList) {
        qqchDangerConstructionList.setId(IdWorker.createId());
        qqchDangerConstructionList.setCreateUser(SecurityUtils.getUserName());
        qqchDangerConstructionList.setCreateTime(DateUtils.getNowDate());
        return qqchDangerConstructionListMapper.insertQqchDangerConstructionList(qqchDangerConstructionList);
    }

    @Transactional
    public int insertQqchDangerConstructionListList(List<QqchDangerConstructionList> qqchDangerConstructionListList) {
        for (QqchDangerConstructionList qqchDangerConstructionList : qqchDangerConstructionListList) {
            qqchDangerConstructionList.setId(IdWorker.createId());
            qqchDangerConstructionList.setCreateUser(SecurityUtils.getUserName());
            qqchDangerConstructionList.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDangerConstructionListMapper.insertQqchDangerConstructionListList(qqchDangerConstructionListList);
    }

    @Transactional
    public int updateQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList) {
        qqchDangerConstructionList.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerConstructionList.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerConstructionListMapper.updateQqchDangerConstructionList(qqchDangerConstructionList);
    }

    @Transactional
    public int updateQqchDangerConstructionListList(List<QqchDangerConstructionList> qqchDangerConstructionListList) {
        for (QqchDangerConstructionList qqchDangerConstructionList : qqchDangerConstructionListList) {
            qqchDangerConstructionList.setUpdateUser(SecurityUtils.getUserName());
            qqchDangerConstructionList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDangerConstructionListMapper.updateQqchDangerConstructionListList(qqchDangerConstructionListList);
    }

    @Transactional
    public int deleteQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList) {
        qqchDangerConstructionList.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerConstructionList.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerConstructionListMapper.deleteQqchDangerConstructionList(qqchDangerConstructionList);
    }

    @Transactional
    public int deleteQqchDangerConstructionListByPks(List<Long> qqchDangerConstructionListPkList) {
        return qqchDangerConstructionListMapper.deleteQqchDangerConstructionListByPks(qqchDangerConstructionListPkList);
    }
}
