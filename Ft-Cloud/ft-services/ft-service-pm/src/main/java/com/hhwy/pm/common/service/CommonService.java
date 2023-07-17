package com.hhwy.pm.common.service;

import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.utils.exception.CustomBusinessException;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
 * 物资策划通用业务类
 *
 * @author mls
 */
@Slf4j
@Service
public class CommonService {
    @Resource
    private CommonMapper commonMapper;

    /**
     * 校验单据能否被调整 (单条数据只能调整一次)
     *
     * @param tableName  表名称
     * @param businessId 业务id (编辑数据的时候会进行id查询, 调整的时候也会根据id查询)
     * @return
     */
    public void canAdjustOnly(Long businessId, String tableName) {
        Assert.notNull(businessId, "主键不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        String valid = this.commonMapper.selectCanAdjust(businessId, tableName);
        if (!"1".equals(valid)) {
            throw new CustomBusinessException("只能调整生效的单据");
        }
        //项目中 版本号最大的id
        Long idMax = this.commonMapper.selectCanAdjustOnly(tableName);
        if (businessId.longValue() != idMax.longValue()) {
            throw new CustomBusinessException("此条数据只能调整一次");
        }
    }


    /**根据主id，逻辑删除子表数据
     * @param mainId
     * @param tableName
     */
    public void deleteDetailsByMainId(Long mainId, String tableName) {
        Assert.notNull(mainId, "主表id不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        this.commonMapper.deleteDetailsByMainId(mainId, tableName);
    }
}
