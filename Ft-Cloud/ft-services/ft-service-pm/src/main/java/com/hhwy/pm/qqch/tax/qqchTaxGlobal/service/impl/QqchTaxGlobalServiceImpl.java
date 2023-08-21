package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper.QqchTaxGlobalMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark
 */
@Service
public class QqchTaxGlobalServiceImpl implements IQqchTaxGlobalService {

    @Autowired
    private QqchTaxGlobalMapper qqchTaxGlobalMapper;


    private static final String TN = "qqch_tax_global";


    public QqchTaxGlobal getQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobal(qqchTaxGlobal);
    }

    public List<QqchTaxGlobal> getQqchTaxGlobalList(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobalList(qqchTaxGlobal);
    }

    @Transactional
    public int insertQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setId(IdWorker.createId());
        qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.insertQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional
    public int insertQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
            qqchTaxGlobal.setId(IdWorker.createId());
            qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
            qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalMapper.insertQqchTaxGlobalList(qqchTaxGlobalList);
    }

    @Transactional
    public int updateQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.updateQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional
    public int updateQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
            qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalMapper.updateQqchTaxGlobalList(qqchTaxGlobalList);
    }

    @Transactional
    public int deleteQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.deleteQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional
    public int deleteQqchTaxGlobalByPks(List<Long> qqchTaxGlobalPkList) {
        return qqchTaxGlobalMapper.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchTaxGlobal> list(QqchTaxGlobal dto) throws IOException {
        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalMapper.getQqchTaxGlobalList(dto);
        if (CollectionUtils.isEmpty(qqchTaxGlobalList)){
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_4.json");
            String json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            System.out.println("stringBuilder = " + json);

        }
        
        
        return qqchTaxGlobalList;
    }
}
