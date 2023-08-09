package com.hhwy.system.equipment.service.impl;


import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.domain.base.system.equipment.EquipmentInfo;
import com.hhwy.domain.base.system.equipment.EquipmentType;
import com.hhwy.system.equipment.mapper.EquipmentInfoMapper;
import com.hhwy.system.equipment.mapper.EquipmentTypeMapper;
import com.hhwy.system.equipment.service.IEquipmentTypeService;
import com.hhwy.system.equipment.vo.EquipmentTreeVo;
import com.hhwy.system.utils.TreeObject;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基础模块---设备分类Service业务层处理
 * 
 * @author lcf
 * @date 2023-03-03
 */
@Service
public class EquipmentTypeServiceImpl implements IEquipmentTypeService {
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;
    @Autowired
    private EquipmentInfoMapper equipmentInfoMapper;

    /**
     * 查询基础模块---设备分类
     * 
     * @param id 基础模块---设备分类ID
     * @return 基础模块---设备分类
     */
    @Override
    public EquipmentType selectEquipmentTypeById(Long id) {
        return equipmentTypeMapper.selectEquipmentTypeById(id);
    }

    /**
     * 查询基础模块---设备分类列表
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 基础模块---设备分类
     */
    @Override
    public List<EquipmentType> selectEquipmentTypeList(EquipmentType equipmentType) {
        return equipmentTypeMapper.selectEquipmentTypeList(equipmentType);
    }

    /**
     * 新增基础模块---设备分类
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 结果
     */
    @Override
    public int insertEquipmentType(EquipmentType equipmentType) {
        EquipmentType info=new EquipmentType();
        info.setSearchName(equipmentType.getEquipName());
        List<EquipmentType> list = equipmentTypeMapper.selectEquipmentTypeList(info);
        if(!CollectionUtils.isEmpty(list)){
            return  -1;
        }
        equipmentType.setId(IdWorker.createId());
        equipmentType.setCreateTime(DateUtils.getNowDate());
        equipmentType.setCreateUser(SecurityUtils.getUserId().toString());
        equipmentType.setPtVar1(SecurityUtils.getUserName());
        return equipmentTypeMapper.insertEquipmentType(equipmentType);
    }

    /**
     * 修改基础模块---设备分类
     * 
     * @param equipmentType 基础模块---设备分类
     * @return 结果
     */
    @Override
    public int updateEquipmentType(EquipmentType equipmentType) {
        EquipmentType info=new EquipmentType();
        info.setEquipName(equipmentType.getEquipName());
        List<EquipmentType> list = equipmentTypeMapper.selectEquipmentTypeList(info);
        //去重当前修改的数据
        List<EquipmentType> collect = list.stream().filter(e -> e.getId().intValue() != equipmentType.getId().intValue()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(collect)){
            return  -1;
        }
        equipmentType.setUpdateTime(DateUtils.getNowDate());
        equipmentType.setUpdateUser(SecurityUtils.getUserId().toString());
        equipmentType.setPtVar1(SecurityUtils.getUserName());
        return equipmentTypeMapper.updateEquipmentType(equipmentType);
    }

    /**
     * 删除基础模块---设备分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEquipmentTypeByIds(String ids) {
        //单个删除
        //根据id查询改id下是否有设备信息  如果有则提示 该分类下存在设备
        EquipmentInfo info=new EquipmentInfo();
        info.setParentId(Long.parseLong(ids));
        List<EquipmentInfo> list = equipmentInfoMapper.selectEquipmentInfoList(info);
        if(!CollectionUtils.isEmpty(list)){
            return -1;
        }
        return equipmentTypeMapper.deleteEquipmentTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除基础模块---设备分类信息
     * 
     * @param id 基础模块---设备分类ID
     * @return 结果
     */
    public int deleteEquipmentTypeById(Long id) {
        return equipmentTypeMapper.deleteEquipmentTypeById(id);
    }

    @Override
    public List<SysTreeUtil> treeList(EquipmentType equipmentType) {
        List<EquipmentType> list = equipmentTypeMapper.selectEquipmentTypeList(equipmentType);
        ArrayList<SysTreeUtil> treeVoList = new ArrayList<>();
        List<SysTreeUtil> result=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysTreeUtil TreeUtil=new SysTreeUtil();
            TreeUtil.setId(String.valueOf(list.get(i).getId()));
            TreeUtil.setPId(list.get(i).getParentId()+"");
            TreeUtil.setName(list.get(i).getEquipName());
            TreeUtil.setLabel(list.get(i).getEquipName());
            treeVoList.add(TreeUtil);
        }
        List deptTree = TreeObject.getDeptTree(treeVoList);
        SysTreeUtil treeUtil=new SysTreeUtil();
        treeUtil.setId("root");
        treeUtil.setPId("0");
        treeUtil.setLabel("设备分类");
        treeUtil.setName("设备分类");
        treeUtil.setChildren(deptTree);
        result.add(treeUtil);
        return result;
    }


    /**
     * 提供给融智的接口
     * 需要把equipment_type和equipment_info
     * 结合成一个树形结构返回
     *
     * @param equipmentType
     * @return
     */
    @Override
    public List<EquipmentTreeVo> treeInfoList(EquipmentType equipmentType) {
        List<EquipmentTreeVo> list = equipmentTypeMapper.selectEquipmentTypeSCList(equipmentType);
        List<EquipmentTreeVo> newData = new ArrayList<>();
        /* List<TreeUtil> list = myCommonMapper.getDeptTree(where);*/
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<String,EquipmentTreeVo> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId() + "", temp);
            });
            for(EquipmentTreeVo temp : list){
                if(!map.containsKey(temp.getPId() + "")){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(EquipmentTreeVo temp : list){
                EquipmentTreeVo parent = map.get(temp.getPId() + "");
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<EquipmentTreeVo>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPId() + "",parent);  // 把放好的数据放回到map中
                }
            }
        }
        return newData;
//        //查询左侧树形数据
//        List<EquipmentType> list = equipmentTypeMapper.selectEquipmentTypeList(equipmentType);
//        //根据type表id 查询info表信息 右侧表格
//        List<Long> equipTypeId = list.stream().map(e -> e.getId()).collect(Collectors.toList());
//        List<EquipmentInfo> infoList= equipmentInfoMapper.selectByTypeIds(equipTypeId);
//        //两个表数据融合 盲写的 没测 坐等有网了测试
//        for (int i = 0; i < infoList.size(); i++) {
//            EquipmentType type=new EquipmentType();
//            EquipmentInfo info = infoList.get(i);
//            type.setId(info.getId());
//            type.setParentId(info.getParentId());
//            type.setPtVar5(info.getEquipCode());
//            type.setEquipName(info.getEquipName());
//            list.add(type);
//        }
//        List<EquipmentTreeVo> treeVoList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            EquipmentTreeVo vo=new EquipmentTreeVo();
//            vo.setId(list.get(i).getId()+"");
//            vo.setPId(list.get(i).getParentId()+"");
//            vo.setName(list.get(i).getEquipName());
//            vo.setLabel(list.get(i).getEquipName());
//            vo.setCode(list.get(i).getPtVar5());
//            treeVoList.add(vo);
//        }
//        List tree = getEquipTypeTree(treeVoList);
//        return tree;
    }

    public static List getEquipTypeTree(List<EquipmentTreeVo> list) {
        List<EquipmentTreeVo> newData = new ArrayList<>();
        /* List<TreeUtil> list = myCommonMapper.getDeptTree(where);*/
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<String,EquipmentTreeVo> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId() + "", temp);
            });
            for(EquipmentTreeVo temp : list){
                if(!map.containsKey(temp.getPId() + "")){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(EquipmentTreeVo temp : list){
                EquipmentTreeVo parent = map.get(temp.getPId() + "");
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<EquipmentTreeVo>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPId() + "",parent);  // 把放好的数据放回到map中
                }
            }
        }
        return newData;
    }
}
