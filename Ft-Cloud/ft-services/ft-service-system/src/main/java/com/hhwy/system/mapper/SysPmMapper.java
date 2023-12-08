package com.hhwy.system.mapper;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.system.api.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SysPmMapper {

    List<SysDictData> selectDictValueByTypeAndLabel(@Param("dictType") String var1, @Param("dictLabels") String var2);

    int selectDbCount(@Param("db") String db,@Param("table") String table);

    List<Map> selectCountLogin();

    void batchInsert(@Param("list")  ArrayList<Map> list);

    void delete();
}
