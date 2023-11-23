package com.hhwy.pm.qqch.sgch.dataShare.mapper;

import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DataShareDevicePlanMapper {


    void dataPush();

    void deleteByOneVersion();

    void updateValidFlag();
}
