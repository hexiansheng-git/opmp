package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalBankSituation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark 10.2.5当地银行情况描述
 */
public interface QqchLocalBankSituationMapper {

    QqchLocalBankSituation getQqchLocalBankSituation(QqchLocalBankSituation qqchLocalBankSituation);

    List<QqchLocalBankSituation> getQqchLocalBankSituationList(QqchLocalBankSituation qqchLocalBankSituation);

    int insertQqchLocalBankSituation(QqchLocalBankSituation qqchLocalBankSituation);

    int insertQqchLocalBankSituationList(
        @Param("qqchLocalBankSituationList") List<QqchLocalBankSituation> qqchLocalBankSituationList);

    int updateQqchLocalBankSituation(QqchLocalBankSituation qqchLocalBankSituation);

    int updateQqchLocalBankSituationList(@Param("list") List<QqchLocalBankSituation> qqchLocalBankSituationList);

    int deleteQqchLocalBankSituation(QqchLocalBankSituation qqchLocalBankSituation);

    int deleteQqchLocalBankSituationByPks(
        @Param("qqchLocalBankSituationPkList") List<Long> qqchLocalBankSituationPkList);
}
