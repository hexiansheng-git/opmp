package com.hhwy.pm.qqch.wzch.source.service;

import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.vo.ProjectOfChangeInfoRequest;
import com.hhwy.pm.qqch.wzch.source.vo.ReminderOfChangeResponse;

import java.util.List;

/**
 * 来源策划Service接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface IWzchSourceService {
    /**
     * 查询来源策划
     * 
     * @param id 来源策划ID
     * @return 来源策划
     */
    WzchSource selectWzchSourceById(Long id);

    /**
     * 查询来源策划列表
     * 
     * @param wzchSource 来源策划
     * @return 来源策划集合
     */
    List<WzchSource> selectWzchSourceList(WzchSource wzchSource);

    /**
     * 新增来源策划
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    int insertWzchSource(WzchSource wzchSource);

    /**
     * 修改来源策划
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    int updateWzchSource(WzchSource wzchSource);

    /**
     * 批量删除来源策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSourceByIds(String ids);

    /**
     * 删除来源策划信息
     * 
     * @param id 来源策划ID
     * @return 结果
     */
    int deleteWzchSourceById(Long id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    WzchSource detail(Long id);

    /**
     * 变更情况提醒
     * @return
     */
    List<ReminderOfChangeResponse> reminderOfChange();

    /**
     * 获取物资总需项目变更信息
     * @param request
     * @return
     */
    List<WzchSourceTotalDemandDetailVO> projectOfChangeInfo(ProjectOfChangeInfoRequest request);

    boolean remove(String id);
}
