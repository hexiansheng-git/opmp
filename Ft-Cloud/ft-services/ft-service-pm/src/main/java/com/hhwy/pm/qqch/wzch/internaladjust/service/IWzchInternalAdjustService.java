package com.hhwy.pm.qqch.wzch.internaladjust.service;

import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjust;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import com.hhwy.pm.qqch.wzch.internaladjust.dto.WzchInternalAdjustDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 内部调剂材料策划Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchInternalAdjustService {
    /**
     * 查询内部调剂材料策划
     *
     * @param id 内部调剂材料策划ID
     * @return 内部调剂材料策划
     */
    WzchInternalAdjust selectWzchInternalAdjustById(Long id);

    /**
     * 查询内部调剂材料策划列表
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 内部调剂材料策划集合
     */
    List<WzchInternalAdjust> selectWzchInternalAdjustList(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 新增内部调剂材料策划
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    int insertWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 修改内部调剂材料策划
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    int updateWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 批量删除内部调剂材料策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchInternalAdjustByIds(String ids);

    /**
     * 删除内部调剂材料策划信息
     *
     * @param id 内部调剂材料策划ID
     * @return 结果
     */
    int deleteWzchInternalAdjustById(Long id);

    /**
     * 查询 详情
     *
     * @param dto
     * @return
     */
    WzchInternalAdjustDTO baseInfo(WzchInternalAdjustDTO dto);


    /**
     * 新增
     *
     * @param dto
     * @return
     */
    long insert(WzchInternalAdjustDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    long edit(WzchInternalAdjustDTO dto);


    /**
     * 调整
     *
     * @param dto
     * @return
     */
    long adjust(WzchInternalAdjustDTO dto);

    long save(WzchInternalAdjustDTO dto);
    
    long sync(WzchInternalAdjustDTO dto);


    int updateValidStatus(String id);

    /**
     * 设置项目id
     *
     * @param dtoList
     */
    void setAdjustProjectIds(List<WzchInternalAdjustDetail> dtoList);
}
