package com.hhwy.pm.qqch.common.defaultData.mapper;

import com.hhwy.pm.qqch.common.defaultData.domain.QqchDefaultDataInitialize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-21 10:02:06
 * @remark 默认数据初始化状态
 */
@Repository
public interface QqchDefaultDataInitializeMapper {

    QqchDefaultDataInitialize getQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    List<QqchDefaultDataInitialize> getQqchDefaultDataInitializeList(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int insertQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int insertQqchDefaultDataInitializeList(@Param("qqchDefaultDataInitializeList") List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList);

    int updateQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int updateQqchDefaultDataInitializeList(@Param("list") List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList);

    int deleteQqchDefaultDataInitialize(QqchDefaultDataInitialize qqchDefaultDataInitialize);

    int deleteQqchDefaultDataInitializeByPks(@Param("qqchDefaultDataInitializePkList") List<Long> qqchDefaultDataInitializePkList);
}
