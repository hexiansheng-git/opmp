package com.hhwy.sp.sciTech.sgjsTechMethod.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark
 */
public interface SgjsTechMethodMapper {

    SgjsTechMethod getSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    List<SgjsTechMethod> getSgjsTechMethodList(SgjsTechMethod sgjsTechMethod);

    int insertSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int insertSgjsTechMethodList(@Param("sgjsTechMethodList") List<SgjsTechMethod> sgjsTechMethodList);

    int updateSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int updateSgjsTechMethodList(@Param("sgjsTechMethodList") List<SgjsTechMethod> sgjsTechMethodList);

    int deleteSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int deleteSgjsTechMethodByPks(@Param("sgjsTechMethodPkList") List<Long> sgjsTechMethodPkList);
}
