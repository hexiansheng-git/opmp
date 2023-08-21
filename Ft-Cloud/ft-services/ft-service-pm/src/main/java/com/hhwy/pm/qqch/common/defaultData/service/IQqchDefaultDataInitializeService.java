package com.hhwy.pm.qqch.common.defaultData.service;

import com.hhwy.pm.qqch.common.defaultData.domain.QqchDefaultDataInitialize;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-21 10:02:06
 * @remark 默认数据初始化状态
 */
public interface IQqchDefaultDataInitializeService {

    /**
     * 判断默认数据是否已经初始化过
     * @param moduleIdentity
     * @param version
     * @return
     */
    boolean interpretInitializeStatus(String moduleIdentity, BigDecimal version);

    QqchDefaultDataInitialize getQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    List<QqchDefaultDataInitialize> getQqchDefaultDataInitializeList(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int insertQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int insertQqchDefaultDataInitializeList(List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList);

    int updateQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int updateQqchDefaultDataInitializeList(List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList);

    int deleteQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int deleteQqchDefaultDataInitializeByPks(List<Long> qqchDefaultDataInitializePkList);
}
