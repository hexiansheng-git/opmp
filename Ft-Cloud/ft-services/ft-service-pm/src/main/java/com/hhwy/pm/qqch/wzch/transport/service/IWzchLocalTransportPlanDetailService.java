package com.hhwy.pm.qqch.wzch.transport.service;

import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlanDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 当地运输方案策划详情Service接口
 * 
 * @author mls
 * @date 2022-12-06
 */
public interface IWzchLocalTransportPlanDetailService {
    /**
     * 查询当地运输方案策划详情
     * 
     * @param id 当地运输方案策划详情ID
     * @return 当地运输方案策划详情
     */
    WzchLocalTransportPlanDetail selectWzchLocalTransportPlanDetailById(Long id);

    /**
     * 查询当地运输方案策划详情列表
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 当地运输方案策划详情集合
     */
    List<WzchLocalTransportPlanDetail> selectWzchLocalTransportPlanDetailList(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 新增当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    int insertWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 修改当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    int updateWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 批量删除当地运输方案策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanDetailByIds(List<Long> ids);

    /**
     * 删除当地运输方案策划详情信息
     * 
     * @param id 当地运输方案策划详情ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanDetailById(Long id);

    /**
     * 保存
     * @param wzchLocalTransportPlan
     * @return
     */
    boolean save(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 导入
     * @param file
     * @return
     */
    List<WzchLocalTransportPlanDetail> importData(MultipartFile file) throws IOException;

    int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);
}
