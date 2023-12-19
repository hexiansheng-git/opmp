package com.hhwy.sp.techOrg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;

/**
 * @author lcf
 * @date 2023-11-17 11:29:23
 * @remark
 */
public interface SgjsTechnicalManageMapper {

    SgjsTechnicalManage getSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    List<SgjsTechnicalManage> getSgjsTechnicalManageList(SgjsTechnicalManage sgjsTechnicalManage);

    int insertSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int insertSgjsTechnicalManageList(@Param("sgjsTechnicalManageList") List<SgjsTechnicalManage> sgjsTechnicalManageList);

    int updateSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int updateSgjsTechnicalManageList(@Param("list") List<SgjsTechnicalManage> sgjsTechnicalManageList);

    int deleteSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int deleteSgjsTechnicalManageByPks(@Param("sgjsTechnicalManagePkList") List<Long> sgjsTechnicalManagePkList);

    /**
     * 批量查询根据id
     * 有子节点 返回子节点信息
     *
     * @param sgjsTechnicalManagePkList
     */
    List<SgjsTechnicalManage> batchSelect(List<Long> sgjsTechnicalManagePkList);

    /**
     * 删除所有数据
     *
     */
    void delectAll(SgjsTechnicalManage info);

    /**
     * 删除根据id
     *
     * @param list
     * @return
     */
    int deleteInfoData(List<SgjsTechnicalManage> list);
}
