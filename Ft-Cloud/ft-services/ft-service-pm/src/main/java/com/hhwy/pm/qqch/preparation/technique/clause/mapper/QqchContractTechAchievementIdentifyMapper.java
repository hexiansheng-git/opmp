package com.hhwy.pm.qqch.preparation.technique.clause.mapper;

import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechAchievementIdentify;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
public interface QqchContractTechAchievementIdentifyMapper {

    QqchContractTechAchievementIdentify getQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify);

    List<QqchContractTechAchievementIdentify> getQqchContractTechAchievementIdentifyList(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify);

    int insertQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify);

    int insertQqchContractTechAchievementIdentifyList(
        @Param("qqchContractTechAchievementIdentifyList") List<QqchContractTechAchievementIdentify> qqchContractTechAchievementIdentifyList);

    int updateQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify);

    int updateQqchContractTechAchievementIdentifyList(
        @Param("list") List<QqchContractTechAchievementIdentify> qqchContractTechAchievementIdentifyList);

    int deleteQqchContractTechAchievementIdentify(
        QqchContractTechAchievementIdentify qqchContractTechAchievementIdentify);

    int deleteQqchContractTechAchievementIdentifyByPks(
        @Param("qqchContractTechAchievementIdentifyPkList") List<Long> qqchContractTechAchievementIdentifyPkList);
}
