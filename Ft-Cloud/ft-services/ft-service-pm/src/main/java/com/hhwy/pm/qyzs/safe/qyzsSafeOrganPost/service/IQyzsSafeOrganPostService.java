package com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.domain.SafeOrganPostQueryVo;

/**
 * @author cjh
 * @date 2023-11-17 16:26:00
 * @remark
 */
public interface IQyzsSafeOrganPostService {

    AjaxResult getQyzsSafeOrganPostList(SafeOrganPostQueryVo queryVo);
}
