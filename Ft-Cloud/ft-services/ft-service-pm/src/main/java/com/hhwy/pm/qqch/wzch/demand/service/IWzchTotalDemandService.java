package com.hhwy.pm.qqch.wzch.demand.service;

import java.io.IOException;
import java.util.List;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandAddVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandExportRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 物资总需Service接口
 * 
 * @author mls
 * @date 2022-11-15
 */
public interface IWzchTotalDemandService {
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
     * 批量删除物资总需
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandByIds(String[] ids);

    /**
     * 删除物资总需信息
     * 
     * @param id 物资总需ID
     * @return 结果
     */
    int deleteWzchTotalDemandById(Long id);

    /**
     * 导出
     *
     * @param wzchTotalDemandExportRequest
     * @param response
     * @return 结果
     */
    void export(WzchTotalDemandExportRequest wzchTotalDemandExportRequest, HttpServletResponse response) throws IOException;

    /**
     * 新增
     * @return
     */
    WzchTotalDemandAddVO add();

    /**
     * 调整
     * @param wzchTotalDemand
     * @return
     */
    AjaxResult modify(WzchTotalDemand wzchTotalDemand);
    /**
     * 详情
     * @param vo
     * @return
     */
    WzchTotalDemandDetailVO detail(WzchTotalDemandDetailVO vo);

    /**
     * 查询物资总需列表 包含工作流信息
     *
     * @param wzchTotalDemand 物资总需
     * @return 物资总需集合
     */
    List<WzchTotalDemand> selectList(WzchTotalDemand wzchTotalDemand);

    /**
     * 通过项目ID查询物资总需信息
     * @param projectIds
     * @return
     */
    List<WzchTotalDemand> selectWzchTotalDemandsByProjectIds(List<Long> projectIds);

    /**
     * 通过项目ID和版本获取物资总需信息
     * @param projectId
     * @param demandVersion
     * @return
     */
    WzchTotalDemand selectByProjectIdAndVersionCode(Long projectId, String demandVersion);

    /**
     * 通过项目ID获取物资总需列表
     * @param projectId
     * @return
     */
    List<WzchTotalDemand> selectByProjectId(Long projectId);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long id);


    List<Long> selectIdsByProjectIdAndVersionCodes(Long projectId, List<String> asList);


    void processStatus(WzchTotalDemand wzchTotalDemand);

    boolean remove(String id);

    WzchTotalDemand selectMaxValidVersionCodeWzchTotalDemandByProjectId(Long projectId);
}
