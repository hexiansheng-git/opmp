package com.hhwy.system.warn.service;

import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.api.domain.SysUser;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
public interface ITWarnService {

    TWarn getTWarn(TWarn tWarn);

    List<TWarn> getTWarnList(TWarn tWarn);

    int addWarn(TWarn tWarn);

    int insertTWarnList(List<TWarn> tWarnList);

    int updateTWarn(TWarn tWarn);

    int updateTWarnList(List<TWarn> tWarnList);

    int deleteTWarn(TWarn tWarn);

    int deleteTWarnByPks(List<Long> tWarnPkList);

    int addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnScope,String warnUrl, String projectName, String tenantKey);

    List<TWarn> selectWarnListForSelf(TWarn warn);

    int changeHandleStatus(TWarnRecord record);

    void batchChangeHandleStatus(Long[] warnIds, String status);

    void pushTWarn(TWarn tWarn);

    List<SysUser> selectByRoleKeyList(String[] roleKeyList);
}
