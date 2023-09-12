package com.hhwy.pm.qqch.wzch.demand.service;

import java.io.IOException;
import java.util.List;

import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailRequest;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandValidVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 物资总需用详情Service接口
 * 
 * @author mls
 * @date 2022-11-15
 */
public interface IWzchTotalDemandDetailService {
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
     * 批量删除物资总需用详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandDetailByIds(List<Long> ids);

    /**
     * 删除物资总需用详情信息
     * 
     * @param id 物资总需用详情ID
     * @return 结果
     */
    int deleteWzchTotalDemandDetailById(Long id);

    /**
     * 保存
     * @param wzchTotalDemand
     * @return
     */
    Long save(WzchTotalDemand wzchTotalDemand);

    /**
     * 查询详情
     * @param wzchTotalDemandDetail
     * @return
     */
    List<WzchTotalDemandDetail> selectDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail);


    /**
     * 物资详情导出
     * @param demand
     * @return
     */
    void export(WzchTotalDemand demand,String leaderFlag,HttpServletResponse response);

    /**
     * 物资策划详情-领导视角
     * @param request
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchTotalDemandDetailListOfLeaderView(WzchTotalDemandDetailRequest request);

    /**
     * 查询物资总需详情列表-领导视角
     * @param request
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchTotalDemandDetails(WzchTotalDemandDetailRequest request);

    /**
     * 通过物资总需ID列表获取总需详情列表
     * @param totalDemandIds
     * @return
     */
    List<WzchTotalDemandDetail> selectDetailByTotalDemandIds(List<Long> totalDemandIds);

    /**
     * 通过项目ID获取物资总需详情
     * @param projectId
     * @return
     */
    List<WzchTotalDemandDetail> selectWzchSourceDetailByProjectId(Long projectId);


    void leaderViewExport(WzchTotalDemandDetailRequest wzchTotalDemandDetailRequest, HttpServletResponse response);

    /**
     * 领导视角汇总
     * @param request
     * @return
     */
    List<WzchTotalDemandDetail> total(WzchTotalDemandDetailRequest request);

    int updateOfValid(String value, List<Long> detailIds);

    List<WzchTotalDemandValidVO> validMaterial(WzchTotalDemandDetail wzchTotalDemandDetail);

    /**
     * 导入
     * @param file
     * @param viewType
     * @return
     * @throws IOException
     */
    List<WzchTotalDemandDetail> importData(MultipartFile file,String viewType) throws IOException;
}
