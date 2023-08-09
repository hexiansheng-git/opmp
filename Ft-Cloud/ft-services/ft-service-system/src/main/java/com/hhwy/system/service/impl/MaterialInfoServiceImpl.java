package com.hhwy.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.domain.base.system.material.MaterialInfoVo;
import com.hhwy.system.mapper.MaterialCategoryMapper;
import com.hhwy.system.mapper.MaterialInfoMapper;
import com.hhwy.system.service.IMaterialInfoService;
import com.hhwy.system.vo.ImportMaterialInfo;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.common.PmsUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import io.seata.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 中交同步物资设备库Service业务层处理
 * 
 * @author lcf
 * @date 2022-10-21
 */
@Service
public class MaterialInfoServiceImpl implements IMaterialInfoService {
    private Logger logger= LoggerFactory.getLogger(MaterialInfoServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MaterialInfoMapper materialInfoMapper;
    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;


    @Override
    public List<MaterialInfoVo> getMaterialInfoPage(MaterialInfo materialInfo) {
        int pageNum = materialInfo.getPageNum() == null ? 1 : materialInfo.getPageNum();
        int pageSize = (materialInfo.getPageSize() == null || materialInfo.getPageSize() > 200) ? 200 : materialInfo.getPageSize();
        pageNum = (pageNum - 1) * pageSize;
        materialInfo.setPageNum(pageNum);
        materialInfo.setPageSize(pageSize);
        materialInfo.setIsFalg("1");
        List<MaterialInfoVo> list = materialInfoMapper.getMaterialInfoPage(materialInfo);
        if(list != null){
            list = JSONArray.parseArray(JSONObject.toJSONString(list), MaterialInfoVo.class);
        }
        return list;
    }

    @Override
    public int getMaterialInfoPageCount(MaterialInfo materialInfo) {
        return materialInfoMapper.getMaterialInfoPageCount(materialInfo);
    }

    /**
     * 查询中交同步物资设备库
     * 
     * @param id 中交同步物资设备库ID
     * @return 中交同步物资设备库
     */
    @Override
    public MaterialInfo selectMaterialInfoById(String id) {
        return materialInfoMapper.selectMaterialInfoById(id);
    }

    /**
     * 查询中交同步物资设备库列表
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 中交同步物资设备库
     */
    @Override
    public List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo) {
        return materialInfoMapper.selectMaterialInfoList(materialInfo);
    }


    /**
     * 查询中交同步物资设备库列表
     *
     * @param materialInfo 中交同步物资设备库
     * @return 中交同步物资设备库
     */
    @Override
    public List<MaterialInfo> newSelectMaterialInfoList(MaterialInfo materialInfo) {
        return materialInfoMapper.newSelectMaterialInfoList(materialInfo);
    }


    @Override
    @Transactional
    public AjaxResult insertMaterialInfo(MaterialInfo materialInfo) {
        //1、查询前端节点是否有效
        MaterialCategory category=new MaterialCategory();
        category.setStatus(PmsConstant.VALID_STATUS);
        category.setCategoryCode(materialInfo.getCategoryCode());
        category.setType(materialInfo.getType());
        List<MaterialCategory> list = materialCategoryMapper.selectMaterialCategoryList(category);
        if(null==list || list.size()==0){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到节点信息");
        }
        Long categoryId = list.get(0).getId();
        materialInfo.setCategoryId(categoryId);

        //2、校验(规格型号+名称)不能重复
        String materialSpec = materialInfo.getMaterialSpec();
        String materialName = materialInfo.getMaterialName();
        MaterialInfo info=new MaterialInfo();
        info.setMaterialSpec(materialSpec);
        info.setMaterialName(materialName);
        MaterialInfo validRepetion = materialInfoMapper.validRepetion(info);
        if(null!=validRepetion){
            return new AjaxResult(PmsConstant.WARN_CODE,"规格型号+名称不能重复");
        }

        //2、入库规则  物资和设备 编码统计全局维护     配件则自己维护
        MaterialInfo mInfo=new MaterialInfo();
        if(materialInfo.getType().equals(2)){
            mInfo.setType(materialInfo.getType());
        }
        //根据类型求最大materilCode
        MaterialInfo maxMaterialCode=materialInfoMapper.selectMaxMaterialCode(mInfo);
        if(null==maxMaterialCode || null==maxMaterialCode.getMaterialCode()){
            if(materialInfo.getType()==2){//配件 00001
                materialInfo.setMaterialCode(materialInfo.getCategoryCode()+"00001");
            }else{//不是配件
                materialInfo.setMaterialCode(materialInfo.getCategoryCode()+"001");
            }
            //排序从1 开始
            materialInfo.setSort(1L);
        }else{
            //查询该节点下所有的物资信息  物资编码加1
            String materialCode = maxMaterialCode.getMaterialCode();
            if(materialInfo.getType()==2){ //配件
                String newMaterialCode = getPartsNewMaterialCode(materialCode, materialInfo.getCategoryCode());
                materialInfo.setMaterialCode(newMaterialCode);
            }else{
                String newMaterialCode = PmsUtils.strAddOne(materialCode);
                materialInfo.setMaterialCode(newMaterialCode);
            }
            //最大排序+1
            materialInfo.setSort(maxMaterialCode.getSort());

        }
        Long id = IdWorker.createId();
        materialInfo.setId(id);
        materialInfo.setCreateTime(DateUtils.getNowDate());
        materialInfo.setCreateUser(SecurityUtils.getUserId().toString());
        materialInfo.setPtVar3(PmsConstant.PTVAR_MYSELF);
        int i = materialInfoMapper.insertMaterialInfo(materialInfo);
        //应前端要求,返回新增的数据信息 TODO 需要跟前端再联调一下
        MaterialCategory categoryById = materialCategoryMapper.selectMaterialCategoryById(id);
        //放redis
        String userId = SecurityUtils.getUserId().toString();
        String key=userId+materialInfo.getMaterialCode();
        MaterialInfo infoById = materialInfoMapper.selectMaterialInfoById(String.valueOf(materialInfo.getId()));
        redisUtils.hPut(PmsConstant.RECENTMATERIALKEY,key,JSONObject.toJSONString(infoById));
        redisUtils.hPut(PmsConstant.MATERIALREDISKEY,materialInfo.getMaterialCode(),JSONObject.toJSONString(infoById));
        return AjaxResult.success(categoryById);
    }

    /**
     * 生成配件新的物资编码
     * 应周海涛要求 配件新的编码规则 categoryCode + 后五位
     *
     * @param materialCode
     * @param categoryCode
     * @return
     */
    private String getPartsNewMaterialCode(String materialCode,String categoryCode){
        String s = materialCode.replaceAll(categoryCode, "");
        String newMaterialCode=PmsUtils.strAddOne(s);
        return newMaterialCode;
    }

    public static void main(String[] args) {
        String str="130103001001001001899";
        String caC="130103001001001";
        String s = str.replaceAll(caC, "");
        System.out.println(s);
    }

    /**
     * 修改中交同步物资设备库
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 结果
     */
    @Override
    public AjaxResult updateMaterialInfo(MaterialInfo materialInfo) {
        //1、查询前端节点是否有效
        MaterialCategory category=new MaterialCategory();
        category.setStatus(PmsConstant.VALID_STATUS);
        category.setCategoryCode(materialInfo.getCategoryCode());
        List<MaterialCategory> list = materialCategoryMapper.selectMaterialCategoryList(category);
        if(null==list || list.size()==0){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到节点信息");
        }
        //2、校验(规格型号+名称)不能重复
        String materialSpec = materialInfo.getMaterialSpec();
        String materialName = materialInfo.getMaterialName();
        MaterialInfo info=new MaterialInfo();
        info.setMaterialSpec(materialSpec);
        info.setMaterialName(materialName);
        MaterialInfo validRepetion = materialInfoMapper.validRepetion(info);
        if(null!=validRepetion && validRepetion.getId().intValue()!=materialInfo.getId().intValue()){
            return new AjaxResult(PmsConstant.WARN_CODE,"规格型号+名称不能重复");
        }
        //修改
        materialInfo.setUpdateTime(DateUtils.getNowDate());
        materialInfo.setUpdateUser(SecurityUtils.getUserId().toString());
        int i = materialInfoMapper.updateMaterialInfo(materialInfo);
        //存redis
        String userId = SecurityUtils.getUserId().toString();
        String key=userId+materialInfo.getMaterialCode();
        MaterialInfo infoById = materialInfoMapper.selectMaterialInfoById(String.valueOf(materialInfo.getId()));
        redisUtils.hPut(PmsConstant.RECENTMATERIALKEY,key,JSONObject.toJSONString(infoById));
        redisUtils.hPut(PmsConstant.MATERIALREDISKEY,materialInfo.getMaterialCode(),JSONObject.toJSONString(infoById));
        return AjaxResult.success(i);
    }

    /**
     * 删除中交同步物资设备库对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMaterialInfoByIds(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<MaterialInfo> list=new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            MaterialInfo info=new MaterialInfo();
            info.setdelUser(SecurityUtils.getUserId().toString());
            info.setdelTime(DateUtils.getNowDate());
            info.setId(Long.parseLong(idList.get(i)));
            info.setDelFlag(PmsConstant.NO_VALID_DELFLAG);
            list.add(info);
        }
        //删除redis
        String userId = SecurityUtils.getUserId().toString();
        //该方法只会一个一个删除，上面只是写了多种删除的方式
        MaterialInfo info = materialInfoMapper.selectMaterialInfoById(ids);
        String key=userId+info.getMaterialCode();
        redisUtils.hDelete(PmsConstant.RECENTMATERIALKEY,key);
        redisUtils.hDelete(PmsConstant.MATERIALREDISKEY,info.getMaterialCode());
        return materialInfoMapper.batchUpdate(list);
    }

    /**
     * 删除中交同步物资设备库信息
     * 
     * @param id 中交同步物资设备库ID
     * @return 结果
     */
    @Override
    public int deleteMaterialInfoById(String id) {
        int i = materialInfoMapper.deleteMaterialInfoById(id);
        MaterialInfo info = materialInfoMapper.selectMaterialInfoById(id);
        redisUtils.hDelete(PmsConstant.MATERIALREDISKEY,info.getMaterialCode());
        return i;
    }

    @Override
    public int selectCount() {
        return materialInfoMapper.selectCount();
    }

    /**
     * 新增最近选择
     *
     * @param materialInfo
     * @return
     */
    @Override
    public AjaxResult recentSelect(MaterialInfo materialInfo) {
        String userId = SecurityUtils.getUserId().toString();
        String materialCode = materialInfo.getMaterialCode();
        if(StringUtils.isBlank(materialCode)){
            return new AjaxResult(PmsConstant.WARN_CODE,"materialCode不能为空");
        }
        if(materialInfo.getType()==null){
            return new AjaxResult(PmsConstant.WARN_CODE,"type不能为空");
        }
        String redisKey=userId+materialInfo.getMaterialCode();
        Integer type = materialInfo.getType();
        String key=userId+String.valueOf(type)+PmsConstant.RECENTMATERIALKEY;
        //取出redis
        Map<Object, Object> map = redisUtils.hGetAll(key);
        List<MaterialInfo> list=new ArrayList<>();
        if(null!=map){
            for (Object mapKey: map.keySet()) {
                String mKey=(String)mapKey;
                String value = (String)map.get(mKey);
                MaterialInfo info = JSON.parseObject(value, MaterialInfo.class);
                list.add(info);
            }
        }
        //只存20个 大于20  忘了用有序类型的redis了  只能随便删除一个了
        if(list.size()>=20){
            String code = list.get(list.size() - 1).getMaterialCode();
            String delKey=userId+code;
            redisUtils.hDelete(redisKey,delKey);
        }
        redisUtils.hPut(key,redisKey, JSONObject.toJSONString(materialInfo));
        redisUtils.expire(redisKey,30*24*1000L, TimeUnit.SECONDS);
        return AjaxResult.success();
    }

    /**
     * 查询最近选择
     *
     * @return
     */
    @Override
    public AjaxResult selectRecentInfo(MaterialInfo materialInfo) {
        String userId = SecurityUtils.getUserId().toString();
        Integer type = materialInfo.getType();
        if(null==type){
            return new AjaxResult(PmsConstant.WARN_CODE,"type不能为空");
        }
        String key=userId+String.valueOf(type)+PmsConstant.RECENTMATERIALKEY;
        Map<Object, Object> map = redisUtils.hGetAll(key);
        if(null==map){
            return AjaxResult.success("redis中暂无最近选择数据");
        }
        List<MaterialInfo> list=new ArrayList<>();
        for (Object mapKey: map.keySet()) {
            String mKey=(String)mapKey;
            String value = (String)map.get(mKey);
            MaterialInfo info = JSON.parseObject(value, MaterialInfo.class);
            list.add(info);
        }
        if(null!=list && list.size()!=0){
            if(list.size()>20){
                List<MaterialInfo> result = list.subList(0, 20);
                return AjaxResult.success(result);
            }
            return AjaxResult.success(list);
        }
        return AjaxResult.success("未查询到数据");
    }

    /**
     * 移除最近选择
     *
     * @param materialInfo
     * @return
     */
    @Override
    public AjaxResult removeRecentInfo(MaterialInfo materialInfo) {
        String materialCode = materialInfo.getMaterialCode();
        if(StringUtils.isBlank(materialCode)){
            return new AjaxResult(PmsConstant.WARN_CODE,"materialCode不能为空");
        }
        Integer type = materialInfo.getType();
        if(null==type){
            return new AjaxResult(PmsConstant.WARN_CODE,"type不能为空");
        }
        String userId = SecurityUtils.getUserId().toString();
        String redisKey=userId+PmsConstant.RECENTMATERIALKEY;
        String key=userId+String.valueOf(type)+materialInfo.getMaterialCode();
        redisUtils.hDelete(redisKey,key);
        return AjaxResult.success();
    }

    @Override
    public List<MaterialInfo> selectMaterialInfoListByCodes(ArrayList<String> materialCodeList) {
        return materialInfoMapper.selectMaterialInfoListByCodes(materialCodeList);
    }

    @Override
    public void importData(List<ImportMaterialInfo> importList, Integer type) {
        //根据分类编码查询分类id
        List<String> materialCodeList = importList.stream().map(e -> e.getCategoryCode()).collect(Collectors.toList());
        List<MaterialCategory> infoList = materialCategoryMapper.selectBathByCode(materialCodeList);
        Map<String, List<MaterialCategory>> listMap = infoList.stream().collect(Collectors.groupingBy(e -> e.getCategoryCode()));

        List<MaterialInfo> rstList=new ArrayList<>();
        for (int i = 0; i < importList.size(); i++) {
            MaterialInfo materialInfo=new MaterialInfo();
            ImportMaterialInfo info = importList.get(i);
            BeanUtils.copyProperties(info,materialInfo);
            List<MaterialCategory> list = listMap.get(info.getCategoryCode());
            if(CollectionUtils.isNotEmpty(list)){
                Long id = list.get(0).getId();
                materialInfo.setCategoryId(id);
            }
            materialInfo.setId(IdWorker.createId());
            materialInfo.setSort(info.getSort());
            if(StringUtils.isNotBlank(info.getMaterialCode())){
                materialInfo.setMaterialCode(info.getMaterialCode());
            }
            if(StringUtils.isNotBlank(info.getMaterialName())){
                materialInfo.setMaterialName(info.getMaterialName());
            }
            if(StringUtils.isNotBlank(info.getPartNo())){
                materialInfo.setPartNo(info.getPartNo());
            }
            if(StringUtils.isNotBlank(info.getUnit())){
                materialInfo.setUnit(info.getUnit());
            }
            if(StringUtils.isNotBlank(info.getOwnType())){
                materialInfo.setOwnType(info.getOwnType());
            }
            if(StringUtils.isNotBlank(info.getOwnSite())){
                materialInfo.setOwnSite(info.getOwnSite());
            }
            if(StringUtils.isNotBlank(info.getType())){
                materialInfo.setType(Integer.parseInt(info.getType()));
            }
            if(StringUtils.isNotBlank(info.getRemark())){
                materialInfo.setRemark(info.getRemark());
            }
            if(StringUtils.isNotBlank(info.getCategoryCode())){
                materialInfo.setCategoryCode(info.getCategoryCode());
            }
            materialInfo.setCreateTime(DateUtils.getNowDate());
            materialInfo.setCreateUser(SecurityUtils.getUserId().toString());
            materialInfo.setDelFlag("0");
            materialInfo.setStatus("0");
            rstList.add(materialInfo);
        }

        //批量新增
        int i = materialInfoMapper.batchInsert(rstList);
    }


}
