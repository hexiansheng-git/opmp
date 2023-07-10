package com.hhwy.gencode.mapper;

/**
* @author Administrator
 * @Entity com.mu.order.domain.GenCode
*/

import com.hhwy.gencode.domain.GenCode;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author jzq
 * @date 2022-11-18
 */
public interface GenCodeMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    GenCode selectGenCodeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param wzchGenCode 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<GenCode> selectGenCodeList(GenCode wzchGenCode);

    /**
     * 新增【请填写功能名称】
     *
     * @param wzchGenCode 【请填写功能名称】
     * @return 结果
     */
    int insertGenCode(GenCode wzchGenCode);

    /**
     * 修改【请填写功能名称】
     *
     * @param wzchGenCode 【请填写功能名称】
     * @return 结果
     */
    int updateGenCode(GenCode wzchGenCode);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    int deleteGenCodeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGenCodeByIds(String[] ids);
}