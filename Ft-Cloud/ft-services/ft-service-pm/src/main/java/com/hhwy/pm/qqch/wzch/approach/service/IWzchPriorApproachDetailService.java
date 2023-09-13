package com.hhwy.pm.qqch.wzch.approach.service;

import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachDetail;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 优先进场物资详情Service接口
 * 
 * @author mls
 * @date 2022-11-30
 */
public interface IWzchPriorApproachDetailService {
    /**
     * 查询优先进场物资详情
     * 
     * @param id 优先进场物资详情ID
     * @return 优先进场物资详情
     */
    WzchPriorApproachDetail selectWzchPriorApproachDetailById(Long id);

    /**
     * 查询优先进场物资详情列表
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 优先进场物资详情集合
     */
    List<WzchPriorApproachDetail> selectWzchPriorApproachDetailList(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 新增优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    int insertWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail);
    
    int batchInsert(List<WzchPriorApproachDetail> list);
    
    /**
     * 修改优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    int updateWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 批量删除优先进场物资详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachDetailByIds(List<Long> ids);

    /**
     * 删除优先进场物资详情信息
     * 
     * @param id 优先进场物资详情ID
     * @return 结果
     */
    int deleteWzchPriorApproachDetailById(Long id);

    /**
     * 保存
     * @param wzchPriorApproach
     */
    void save(WzchPriorApproach wzchPriorApproach);

    /**
     * 导出
     * @param wzchPriorApproachDetails
     * @param response
     */
    void export(List<WzchPriorApproachDetail> wzchPriorApproachDetails, HttpServletResponse response);

    List<WzchPriorApproachDetailResponse> selectList(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 导入
     * @param file
     * @return
     */
    List<WzchPriorApproachDetail> importData(MultipartFile file) throws IOException;

    int deleteDirectByVersion(BigDecimal version);
    int deleteYearDirectByVersion(BigDecimal version);
}
