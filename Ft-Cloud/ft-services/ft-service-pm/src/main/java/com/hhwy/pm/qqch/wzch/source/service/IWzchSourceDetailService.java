package com.hhwy.pm.qqch.wzch.source.service;


import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailReminderOfChangeRequest;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 来源策划物资详情Service接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface IWzchSourceDetailService {
    /**
     * 查询来源策划物资详情
     * 
     * @param id 来源策划物资详情ID
     * @return 来源策划物资详情
     */
    WzchSourceDetail selectWzchSourceDetailById(Long id);

    /**
     * 查询来源策划物资详情列表
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 来源策划物资详情集合
     */
    List<WzchSourceDetail> selectWzchSourceDetailList(WzchSourceDetail wzchSourceDetail);

    /**
     * 获取内部调剂物资信息
     * @param id 来源策划Id
     * @return
     */
    List<WzchSourceDetail> selectInnerAdjustList(Long id);

    /**
     * 新增来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    int insertWzchSourceDetail(WzchSourceDetail wzchSourceDetail);

    /**
     * 修改来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    int updateWzchSourceDetail(WzchSourceDetail wzchSourceDetail);

    /**
     * 获取详情列表
     * @param wzchSourceDetail
     * @return
     */
    List<WzchSourceDetail> selectDetailList(WzchSourceDetail wzchSourceDetail);

    /**
     * 保存接口
     * @param wzchSource
     * @return
     */
    Long save(WzchSource wzchSource);

    /**
     * 同步物资总需用
     * @param version
     */
    void sync(BigDecimal version);

    /**
     * 通过项目ID获取物资总需详情
     * @return
     */
    WzchSourceTotalDemandVO getProjectTotalDemandDetail(WzchSourceTotalDemandVO vo);

    /**
     * 总需变更提醒
     * @param reminderOfChangeRequest
     * @return
     */
    boolean reminderOfChange(WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest);

    /**
     * 导出
     * @param request
     * @return
     */
    void export(List<WzchSourceDetail> request, HttpServletResponse response) ;

    List<WzchSourceTotalDemandDetailVO> reminderOfChangeDetail(WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest);

    /**
     * 导入
     * @param file
     * @return
     */
    List<WzchSourceDetail> importData(MultipartFile file) throws IOException;
}
