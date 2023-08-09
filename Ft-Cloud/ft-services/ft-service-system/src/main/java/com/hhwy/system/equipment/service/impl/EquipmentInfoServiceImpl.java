package com.hhwy.system.equipment.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.domain.base.system.equipment.EquipmentInfo;
import com.hhwy.system.equipment.mapper.EquipmentInfoMapper;
import com.hhwy.system.equipment.service.IEquipmentInfoService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础模块设备分类子表Service业务层处理
 * 
 * @author jzq
 * @date 2023-03-03
 */
@Service
public class EquipmentInfoServiceImpl implements IEquipmentInfoService {

    @Autowired
    private EquipmentInfoMapper equipmentInfoMapper;

    /**
     * 查询基础模块设备分类子表
     * 
     * @param id 基础模块设备分类子表ID
     * @return 基础模块设备分类子表
     */
    @Override
    public EquipmentInfo selectEquipmentInfoById(Long id) {
        return equipmentInfoMapper.selectEquipmentInfoById(id);
    }

    /**
     * 查询基础模块设备分类子表列表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 基础模块设备分类子表
     */
    @Override
    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo) {
        return equipmentInfoMapper.selectEquipmentInfoList(equipmentInfo);
    }

    /**
     * 新增基础模块设备分类子表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 结果
     */
    @Override
    public int insertEquipmentInfo(EquipmentInfo equipmentInfo) {
        //判断编码是否重复
        EquipmentInfo info=new EquipmentInfo();
        info.setNewEquipCode(equipmentInfo.getEquipCode());
        List<EquipmentInfo> list = equipmentInfoMapper.selectEquipmentInfoList(info);
        if(!CollectionUtils.isEmpty(list)){
            return -1;
        }
        equipmentInfo.setId(IdWorker.createId());
        equipmentInfo.setCreateTime(DateUtils.getNowDate());
        equipmentInfo.setCreateUser(SecurityUtils.getUserId().toString());
        equipmentInfo.setPtVar1(SecurityUtils.getUserName());
        return equipmentInfoMapper.insertEquipmentInfo(equipmentInfo);
    }

    /**
     * 修改基础模块设备分类子表
     * 
     * @param equipmentInfo 基础模块设备分类子表
     * @return 结果
     */
    @Override
    public int updateEquipmentInfo(EquipmentInfo equipmentInfo) {
        //TODO 倩子欠我一个接口
        equipmentInfo.setUpdateTime(DateUtils.getNowDate());
        equipmentInfo.setUpdateUser(SecurityUtils.getUserName());
        equipmentInfo.setPtVar1(SecurityUtils.getUserId().toString());
        //查询编码是否存在
        EquipmentInfo info=new EquipmentInfo();
        info.setEquipCode(equipmentInfo.getEquipCode());
        List<EquipmentInfo> list = equipmentInfoMapper.selectEquipmentInfoList(info);
        List<EquipmentInfo> collect = list.stream().filter(e -> !e.getEquipCode().equals(equipmentInfo.getEquipCode())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(collect)){
            return -1;
        }
        return equipmentInfoMapper.updateEquipmentInfo(equipmentInfo);
    }

    /**
     * 删除基础模块设备分类子表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEquipmentInfoByIds(String ids) {
        //根据ids查询设备分类编码信息
//        List<String> list = Arrays.asList(StringUtils.split(","));
//        if(CollectionUtils.isEmpty(list)){
//            return 0;
//        }
//        List<Long> idsList = list.stream().map(e -> Long.parseLong(e)).collect(Collectors.toList());
//        //根据id查询项目信息
//        List<EquipmentInfo> equipmentInfoList=equipmentInfoMapper.selectInfoByIds(idsList);
//        if(CollectionUtils.isEmpty(equipmentInfoList)){
//            return 0;
//        }
//        List<String> equipCodeList = equipmentInfoList.stream().map(e -> e.getEquipCode()).collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(equipCodeList)){
//            return 0;
//        }
//        //查看现场验收那是否有用  有了则不删除 TODO 倩子欠我接口
//        Map<String,List<String>> map=new HashMap();
//        map.put("categoryList",equipCodeList);
//        AjaxResult result = fmsServiceApi.getListByCategoryList(map);
//        if(!result.get("code").toString().equals("200")){
//            return 0;
//        }
//        List<Map> array = JSONArray.parseArray(JSON.toJSONString(result.get("data")), Map.class);
        return equipmentInfoMapper.deleteEquipmentInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除基础模块设备分类子表信息
     * 
     * @param id 基础模块设备分类子表ID
     * @return 结果
     */
    public int deleteEquipmentInfoById(Long id) {
        return equipmentInfoMapper.deleteEquipmentInfoById(id);
    }

    @Override
    public AjaxResult importData(List<EquipmentInfo> list, Long parentId) {
        if(null==parentId){
            return AjaxResult.error("请选中左侧分类");
        }
        String msg="";
        int t=1;
        for (int i = 0; i < list.size(); i++) {
            t=t+i;
            String equipCode = list.get(i).getEquipCode();
            String equipName = list.get(i).getEquipName();
            if(StringUtils.isBlank(equipCode)){
                msg=msg+"第"+t+"行编码不能为空;";
            }
            if(StringUtils.isBlank(equipName)){
                msg=msg+"第"+t+"行名称不能为空;";
            }
        }
        if(StringUtils.isNotBlank(msg)){
            return new AjaxResult(Constant.WARN_CODE,msg);
        }
        //数据处理并批量插入
        List<String> equipCodeList = list.stream().map(e -> e.getEquipCode()).collect(Collectors.toList());
        //查看编码是否存在
        List<EquipmentInfo> equipmentInfoList = equipmentInfoMapper.selectInfoByEquipmenInfo(equipCodeList);
        if(!CollectionUtils.isEmpty(equipCodeList)){
            List<String> collect = equipmentInfoList.stream().map(e -> e.getEquipCode()).collect(Collectors.toList());
            collect = collect.stream().distinct().collect(Collectors.toList());
            msg = StringUtils.join(collect, ",");
        }
        if(StringUtils.isNotBlank(msg)){
            return new AjaxResult(301,"编码："+msg+"已存在");
        }
        for (int i = 0; i < list.size(); i++) {
            EquipmentInfo info = list.get(i);
            info.setCreateUser(SecurityUtils.getUserId().toString());
            info.setCreateTime(DateUtils.getNowDate());
            info.setPtVar1(SecurityUtils.getUserName());
            info.setId(IdWorker.createId());
            info.setParentId(parentId);
        }
        int i = equipmentInfoMapper.insertBath(list);
        return AjaxResult.success(i);
    }


}
