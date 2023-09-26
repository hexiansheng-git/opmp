package com.hhwy.system.warn.service;

import com.hhwy.system.warn.domain.TWarn;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
public interface ITWarnService {

    TWarn getTWarn(TWarn tWarn);

    List<TWarn> getTWarnList(TWarn tWarn);

    int insertTWarn(TWarn tWarn);

    int insertTWarnList(List<TWarn> tWarnList);

    int updateTWarn(TWarn tWarn);

    int updateTWarnList(List<TWarn> tWarnList);

    int deleteTWarn(TWarn tWarn);

    int deleteTWarnByPks(List<Long> tWarnPkList);
}
