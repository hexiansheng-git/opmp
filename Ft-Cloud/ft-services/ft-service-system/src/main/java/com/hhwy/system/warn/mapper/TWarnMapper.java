package com.hhwy.system.warn.mapper;

import com.hhwy.domain.base.system.warn.TWarn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
@Repository
public interface TWarnMapper {

    TWarn getTWarn(TWarn tWarn);

    TWarn selectWarnById(@Param("warnId") Long warnId);

    List<TWarn> getTWarnList(TWarn tWarn);

    int insertTWarn(TWarn tWarn);

    int insertTWarnList(@Param("tWarnList") List<TWarn> tWarnList);

    int updateTWarn(TWarn tWarn);

    int updateTWarnList(@Param("tWarnList") List<TWarn> tWarnList);

    int deleteTWarn(TWarn tWarn);

    int deleteTWarnByPks(@Param("tWarnPkList") List<Long> tWarnPkList);

    List<TWarn> selectWarnListForSelf(TWarn warn);
}
