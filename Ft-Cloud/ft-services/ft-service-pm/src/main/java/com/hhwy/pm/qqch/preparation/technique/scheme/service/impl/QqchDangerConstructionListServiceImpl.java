package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchDangerConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
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

    public QqchDangerConstructionListVo getQqchDangerConstructionListList(BigDecimal version) {
        QqchDangerConstructionListVo vo = new QqchDangerConstructionListVo();
        if (version == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_danger_construction_list");
        }
        vo.setVersion(version);
        QqchDangerConstructionList qryParam = new QqchDangerConstructionList();
        qryParam.setVersion(version);
        List<QqchDangerConstructionList> list = qqchDangerConstructionListMapper
            .getQqchDangerConstructionListList(qryParam);
        vo.setList(list);
        return vo;
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
    public void syncData() {
        // 获取当前数据库表数据
        QqchDangerConstructionListVo dbVo = this.getQqchDangerConstructionListList(null);
        List<QqchDangerConstructionList> dbList = dbVo.getList();

        // 获取方案清单最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(maxVersion);
        // 获取施工方案清单中危大等级为危大、超危大的方案数据
        List<QqchConstructionList> constructionList = qqchConstructionListMapper
            .getBigDangerLevelConstructionList(qryParam);

        // 构造新的list
        List<QqchDangerConstructionList> insertList = new ArrayList<>();
        for (QqchConstructionList construction : constructionList) {
            QqchDangerConstructionList insert = new QqchDangerConstructionList();
            BeanUtils.copyProperties(construction, insert);
            insert.setId(IdWorker.createId());
            insert.setCreateUser(SecurityUtils.getUserName());
            insert.setCreateTime(DateUtils.getNowDate());
            insert.setVersion(dbVo.getVersion());
            insert.setValid(Valid.YES);
            for (QqchDangerConstructionList db : dbList) {
                if (insert.getSchemeCode().equals(db.getSchemeCode())) {
                    insert.setKeySpecialProcesses(db.getKeySpecialProcesses());
                    insert.setBriefDescription(db.getBriefDescription());
                    insert.setMainMeasure(db.getMainMeasure());
                }
            }
            insertList.add(insert);
        }

        // 先批量表中数据
        QqchDangerConstructionList deleteParam = new QqchDangerConstructionList();
        deleteParam.setVersion(dbVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchDangerConstructionListMapper.updateQqchDangerConstructionList(deleteParam);

        if (insertList.size() > 0) {
            qqchDangerConstructionListMapper.insertQqchDangerConstructionListList(insertList);
        }
    }
}
