package com.hhwy.pm.qqch.wzch.importplan.service;

import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlanDetail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 进出口策划详情Service接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface IWzchImportExportPlanDetailService {
    /**
     * 查询进出口策划详情
     * 
     * @param id 进出口策划详情ID
     * @return 进出口策划详情
     */
    WzchImportExportPlanDetail selectWzchImportExportPlanDetailById(Long id);

    /**
     * 查询进出口策划详情列表
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 进出口策划详情集合
     */
    List<WzchImportExportPlanDetail> selectWzchImportExportPlanDetailList(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 新增进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    int insertWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 修改进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    int updateWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 批量删除进出口策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportPlanDetailByIds(List<Long> ids);

    /**
     * 删除进出口策划详情信息
     * 
     * @param id 进出口策划详情ID
     * @return 结果
     */
    int deleteWzchImportExportPlanDetailById(Long id);

    List<WzchImportExportPlanDetail> importData(MultipartFile file) throws IOException;

    Long save(WzchImportExportPlan wzchImportExportPlan);

    void export(List<WzchImportExportPlanDetail> list, HttpServletResponse response) throws IOException;

//    int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

}
