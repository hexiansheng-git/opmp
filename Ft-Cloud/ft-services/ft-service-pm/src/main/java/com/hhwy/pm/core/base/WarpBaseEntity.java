package com.hhwy.pm.core.base;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import lombok.Data;

/**
 * 不带流程信息的baseEntity
 */
@Data
public class WarpBaseEntity extends BaseEntity {
    Long id;

    public WarpBaseEntity initAdd(){
        this.setId(IdWorker.createId());
        new AddBaseInfoUtil().addBaseEntity(this);
        return this;
    }
}
