package com.hhwy.system.nacosconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * info对象 t_period_info
 * 
 * @author lcf
 * @date 2022-11-24
 */
@Data
public class NacosInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**配置文件名称 */
    private String dataId;

    /*空间*/
    private String namespace;

    /*参数*/
    private Map<String, Object> mapSon;
}
