package com.hhwy.pm.qqch.preparation.technique.disclose.mapper;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThird;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
public interface QqchDiscloseThirdMapper {

    QqchDiscloseThird getQqchDiscloseThird(QqchDiscloseThird qqchDiscloseThird);

    List<QqchDiscloseThird> getQqchDiscloseThirdList(QqchDiscloseThird qqchDiscloseThird);

    int insertQqchDiscloseThird(QqchDiscloseThird qqchDiscloseThird);

    int insertQqchDiscloseThirdList(@Param("qqchDiscloseThirdList") List<QqchDiscloseThird> qqchDiscloseThirdList);

    int updateQqchDiscloseThird(QqchDiscloseThird qqchDiscloseThird);

    int updateQqchDiscloseThirdList(@Param("list") List<QqchDiscloseThird> qqchDiscloseThirdList);

    int deleteQqchDiscloseThird(QqchDiscloseThird qqchDiscloseThird);

    int deleteQqchDiscloseThirdByPks(@Param("qqchDiscloseThirdPkList") List<Long> qqchDiscloseThirdPkList);
}
