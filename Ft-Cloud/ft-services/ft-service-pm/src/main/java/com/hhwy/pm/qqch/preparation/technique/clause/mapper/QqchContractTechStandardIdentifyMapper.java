package com.hhwy.pm.qqch.preparation.technique.clause.mapper;

import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
public interface QqchContractTechStandardIdentifyMapper {

    QqchContractTechStandardIdentify getQqchContractTechStandardIdentify(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    List<QqchContractTechStandardIdentify> getQqchContractTechStandardIdentifyList(
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    int insertQqchContractTechStandardIdentify(QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    int insertQqchContractTechStandardIdentifyList(
        @Param("qqchContractTechStandardIdentifyList") List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList);

    int updateQqchContractTechStandardIdentify(QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    int updateQqchContractTechStandardIdentifyList(
        @Param("list") List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList);

    int deleteQqchContractTechStandardIdentify(QqchContractTechStandardIdentify qqchContractTechStandardIdentify);

    int deleteQqchContractTechStandardIdentifyByPks(
        @Param("qqchContractTechStandardIdentifyPkList") List<Long> qqchContractTechStandardIdentifyPkList);
}
