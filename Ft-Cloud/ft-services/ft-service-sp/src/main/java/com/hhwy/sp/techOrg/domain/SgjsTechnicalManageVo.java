package com.hhwy.sp.techOrg.domain;

import lombok.Data;

import java.util.List;

/**
 * 返回结果
 *
 * @author lcf
 * @date 2023-11-17
 */
@Data
public class SgjsTechnicalManageVo{
    //新增数据
    private List<SgjsTechnicalManage> treeList;
    //删除id
    private List<String> delIdList;

}
