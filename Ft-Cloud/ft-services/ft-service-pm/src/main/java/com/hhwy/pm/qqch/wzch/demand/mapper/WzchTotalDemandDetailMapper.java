package com.hhwy.pm.qqch.wzch.demand.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailRequest;
import org.apache.ibatis.annotations.Param;

/**
 * 物资总需用详情Mapper接口
 * 
 * @author mls
 * @date 2022-11-15
 */
public interface WzchTotalDemandDetailMapper {
    /**
     * 查询物资总需用详情
     * 
     * @param id 物资总需用详情ID
     * @return 物资总需用详情
     */
    WzchTotalDemandDetail selectWzchTotalDemandDetailById(Long id);

    /**
     * 查询物资总需用详情列表
     * 
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 物资总需用详情集合
     */
    List<WzchTotalDemandDetail> selectWzchTotalDemandDetailList(WzchTotalDemandDetail wzchTotalDemandDetail);

    /**
     * 新增物资总需用详情
     * 
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 结果
     */
    int insertWzchTotalDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail);

    /**
     * 修改物资总需用详情
     * 
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 结果
     */
    int updateWzchTotalDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail);

    /**
     * 删除物资总需用详情
     * 
     * @param id 物资总需用详情ID
     * @return 结果
     */
    int deleteWzchTotalDemandDetailById(Long id);

    /**
     * 批量删除物资总需用详情
     * 
     * @param list 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandDetailByIds(List<Long> list);
    /**
     * 批量插入物资总需用详情
     *
     * @param list
     * @return 结果
     */
    int batchInsert(List<WzchTotalDemandDetail> list);

    /**
     * 批量修改物资总需用详情
     *
     * @param dataList
     * @return 结果
     */
    int batchUpdate(List<WzchTotalDemandDetail> dataList);

    /**
     * 查询物资总需详情列表-领导视角
     * @param request
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchTotalDemandDetails(WzchTotalDemandDetailRequest request);

    /**
     * 通过物资总需ID列表获取总需详情列表
     * @param list
     * @return
     */
    List<WzchTotalDemandDetail> selectDetailByTotalDemandIds(List<Long> list);

    /**
     * 通过项目ID获取物资总需详情
     * @param projectId
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchSourceDetailByProjectId(Long projectId);

    /**
     * 通过主表的ID删除
     * @param id
     * @return
     */
    int deleteByTotalDemandId(Long id);

    /**
     * 根据版本删除
     * @param version
     * @return
     */
    int deleteByVersion(BigDecimal version);

    /**
     * 领导视角
     * @param request
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchTotalDemandDetailListOfLeaderView(WzchTotalDemandDetailRequest request);

    List<WzchTotalDemandDetail> selectByProjectIds(List<Long> projectIds);

    int updateOfValid(@Param("valid")String valid, @Param("ids")List<Long> detailIds);

    List<WzchTotalDemandDetail> selectByProjectIdsOfTotal(@Param("projectIds") List<Long> projectIds,@Param("materialStandardList")  List<String> materialStandardList);
}
