package com.hhwy.pm.gm.qqch.service;

import com.hhwy.pm.qqch.review.domain.Review;

import java.util.List;
import java.util.Map;

/**
 * 为总部版前期策划提供的接口
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/24 15:55   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/24 15:55    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public interface IGmThirdService {

    /**
     * 前期策划评审
     * @param map {planStage,tenantKeys}
     * @return {租户标志:评审对象集合}
     */
    public Map<String, List<Review>> reviewList(Map map);
}
