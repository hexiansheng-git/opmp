package com.hhwy.pm.qqch.wzch.demand.mapper;

import java.util.List;

import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import org.apache.ibatis.annotations.Param;

/**
 * 物资总需Mapper接口
 * 
 * @author mls
 * @date 2022-11-15
 */
public interface WzchTotalDemandMapper {
    /**
     * 查询物资总需
     * 
     * @param id 物资总需ID
     * @return 物资总需
     */
    WzchTotalDemand selectWzchTotalDemandById(Long id);

    /**
     * 查询物资总需列表
     * 
     * @param wzchTotalDemand 物资总需
     * @return 物资总需集合
     */
    List<WzchTotalDemand> selectWzchTotalDemandList(WzchTotalDemand wzchTotalDemand);

    /**
     * 查询物资总需列表 包含工作流信息
     *
     * @param wzchTotalDemand 物资总需
     * @return 物资总需集合
     */
    List<WzchTotalDemand> selectList(WzchTotalDemand wzchTotalDemand);

    /**
     * 新增物资总需
     * 
     * @param wzchTotalDemand 物资总需
     * @return 结果
     */
    int insertWzchTotalDemand(WzchTotalDemand wzchTotalDemand);

    /**
     * 修改物资总需
     * 
     * @param wzchTotalDemand 物资总需
     * @return 结果
     */
    int updateWzchTotalDemand(WzchTotalDemand wzchTotalDemand);

    /**
     * 删除物资总需
     * 
     * @param id 物资总需ID
     * @return 结果
     */
    int deleteWzchTotalDemandById(Long id);

    /**
     * 批量删除物资总需
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandByIds(String[] ids);

    /**
     * 通过id列表查询
     *
     * @param list
     * @return List<WzchTotalDemand>
     */
    List<WzchTotalDemand> selectByIds(List<Long> list);

    /**
     * 通过项目ID和版本查询无效的物资总需项目ID
     * @param list
     * @return
     */
    List<WzchTotalDemand> selectWzchTotalDemandsByProjectIds(List<Long> list);

    /**
     * 通过项目ID和版本获取物资总需信息
     * @param projectId
     * @param versionCode
     * @return
     */
    WzchTotalDemand selectByProjectIdAndVersionCode(@Param("projectId") Long projectId, @Param("versionCode") String versionCode);

    /**
     * 通过项目ID获取我自总需列表
     * @param projectId
     * @return
     */
    List<WzchTotalDemand> selectByProjectId(@Param("projectId") Long projectId);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    List<Long> selectIdsByProjectIdAndVersionCodes(@Param("projectId")Long projectId,@Param("versionCodes") List<String> versionCodes);

    int updateOfValid(@Param("valid") String valid,@Param("idList") List<Long> idList);

    WzchTotalDemand selectMaxValidVersionCodeWzchTotalDemandByProjectId(Long projectId);


    List<WzchTotalDemand> selectExportList(WzchTotalDemand wzchTotalDemand);
}
