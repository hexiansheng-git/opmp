package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.domain.base.system.material.MaterialInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 中交同步物资设备库Mapper接口
 * 
 * @author lcf
 * @date 2022-10-21
 */
public interface MaterialInfoMapper {


//    //分页获取数据
    List<MaterialInfoVo> getMaterialInfoPage(MaterialInfo materialInfo);
//    //分页获取数据
    int getMaterialInfoPageCount(MaterialInfo materialInfo);


    int batchInsert(@Param("dataList") List<MaterialInfo> dataList);

    /**
     * 查询中交同步物资设备库
     * 
     * @param id 中交同步物资设备库ID
     * @return 中交同步物资设备库
     */
    MaterialInfo selectMaterialInfoById(String id);

    /**
     * 查询中交同步物资设备库列表
     * 
     * @param MaterialInfo 中交同步物资设备库
     * @return 中交同步物资设备库集合
     */
    List<MaterialInfo> selectMaterialInfoList(MaterialInfo MaterialInfo);

    /**
     * 新增中交同步物资设备库
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 结果
     */
    int insertMaterialInfo(MaterialInfo materialInfo);

    /**
     * 修改中交同步物资设备库
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 结果
     */
    int updateMaterialInfo(MaterialInfo materialInfo);

    /**
     * 删除中交同步物资设备库
     * 
     * @param id 中交同步物资设备库ID
     * @return 结果
     */
    int deleteMaterialInfoById(String id);

    /**
     * 批量删除中交同步物资设备库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMaterialInfoByIds(String[] ids);

    /**
     * 数据修改
     *
     * @param list
     * @return
     */
    int batchUpdate(List<MaterialInfo> list);

    /**
     * 校验材料编码是否重复
     *
     * @param materialInfo
     * @return
     */
    MaterialInfo validRepetion(MaterialInfo materialInfo);

    /**
     * 校验
     *
     * @param materialInfo
     * @return
     */
    List<MaterialInfo> newValidRepetion(MaterialInfo materialInfo);

    /**
     * 列表查询
     *
     * @param MaterialInfo
     * @return
     */
    List<MaterialInfo> newSelectMaterialInfoList(MaterialInfo MaterialInfo);

    /**
     * 查总数
     *
     * @return
     */
    int selectCount();

    List<MaterialInfo> selectMaterialInfoListByCodes(@Param("materialCodes") ArrayList<String> materialCodeList);

    /**
     * 求最大的materialCode
     *
     * @param mInfo
     * @return
     */
    MaterialInfo selectMaxMaterialCode(MaterialInfo mInfo);
}
