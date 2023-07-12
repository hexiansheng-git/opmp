package com.hhwy.system.mapper;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.core.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPmMapper {

    List<SysDictData> selectDictValueByTypeAndLabel(@Param("dictType") String var1, @Param("dictLabels") String var2);

}
