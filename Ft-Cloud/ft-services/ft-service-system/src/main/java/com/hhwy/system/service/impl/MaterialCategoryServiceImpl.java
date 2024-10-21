package com.hhwy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialCategoryVo;
import com.hhwy.domain.base.system.material.MaterialCategoryVo2;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.system.controller.InitMaterialController;
import com.hhwy.system.mapper.MaterialCategoryMapper;
import com.hhwy.system.mapper.MaterialInfoMapper;
import com.hhwy.system.service.IMaterialCategoryService;
import com.hhwy.system.utils.TreeNodeUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.common.PmsUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料分类名称Service业务层处理
 * 
 * @author lcf
 * @date 2022-10-21
 */
@Service
public class MaterialCategoryServiceImpl implements IMaterialCategoryService {

    private static Logger logger= LoggerFactory.getLogger(MaterialCategoryServiceImpl.class);

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    //材料分类名称模糊搜索
    public List<MaterialCategoryVo2> getTreeListByCategoryName(MaterialCategoryVo materialCategoryVo) {
        Assert.isTrue(StrUtil.isNotBlank(materialCategoryVo.getCategoryName()), "搜索名称不能为空");
        Assert.isTrue(StrUtil.isNotBlank(materialCategoryVo.getType()), "类型不能为空");
        List<MaterialCategoryVo2> resultList = new ArrayList<>();
        MaterialCategoryVo materialCategory = new MaterialCategoryVo();
        materialCategory.setStatus("0");
        materialCategory.setType(materialCategoryVo.getType());
        materialCategory.setCategoryName(materialCategoryVo.getCategoryName());
        List<MaterialCategoryVo2> list = materialCategoryMapper.getTreeListByCategoryName(materialCategory);
        if (CollUtil.isNotEmpty(list)) {
            materialCategory.setCategoryName(null);
            List<MaterialCategoryVo2> allList = materialCategoryMapper.getTreeListByCategoryName(materialCategory);
            resultList = TreeNodeUtil.getAncestral(allList, list);

        }
        return TreeUtil.build(resultList, 0L);
    }

    @Override
    public List<MaterialCategoryVo> getPosition(Long categoryId, String type) {
        List<MaterialCategoryVo> list = materialCategoryMapper.getPosition(categoryId, type);
        List<MaterialCategoryVo> newData = new ArrayList<>();
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<Long,MaterialCategoryVo> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId(), temp);
            });
            for(MaterialCategoryVo temp : list){
                if(!map.containsKey(temp.getPid())){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(MaterialCategoryVo temp : list){
                MaterialCategoryVo parent = map.get(temp.getPid());
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<MaterialCategoryVo>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPid(),parent);  // 把放好的数据放回到map中
                }
            }
        }
        return newData;
    }

    @Override
    public List<MaterialCategoryVo> getTree(MaterialCategoryVo materialCategoryVo) {
//        materialCategoryVo.setPid(null);
        long st = System.currentTimeMillis();
        List<MaterialCategoryVo> list = materialCategoryMapper.getTree(materialCategoryVo);
        return list;
        /*List<MaterialCategoryVo> newData = new ArrayList<>();
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<Long,MaterialCategoryVo> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId(), temp);
            });
            for(MaterialCategoryVo temp : list){
                if(!map.containsKey(temp.getPid())){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(MaterialCategoryVo temp : list){
                MaterialCategoryVo parent = map.get(temp.getPid());
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<MaterialCategoryVo>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPid(),parent);  // 把放好的数据放回到map中
                }
            }
        }
        long et = System.currentTimeMillis();
        System.out.println((et- st)/1000);
        return newData;*/
    }

    /**
     * 查询物料分类名称
     * 
     * @param id 物料分类名称ID
     * @return 物料分类名称
     */
    @Override
    public MaterialCategory selectMaterialCategoryById(Long id) {
        return materialCategoryMapper.selectMaterialCategoryById(id);
    }

    /**
     * 查询物料分类名称列表
     * 
     * @param materialCategory 物料分类名称
     * @return 物料分类名称
     */
    @Override
    public List<MaterialCategory> selectMaterialCategoryList(MaterialCategory materialCategory) {
        return materialCategoryMapper.selectMaterialCategoryList(materialCategory);
    }

    @Override
    public List<MaterialCategory> newSelectMaterialCategoryList(MaterialCategory materialCategory) {
        return materialCategoryMapper.newSelectMaterialCategoryList(materialCategory);
    }

    /**
     * 新增物料分类名称
     * 
     * @param materialCategory 物料分类名称
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult insertMaterialCategory(MaterialCategory materialCategory) {
        //根据pcode查询材料编码
        MaterialCategory info=new MaterialCategory();
        info.setCategoryCode(materialCategory.getCategoryCode());
        info.setStatus(PmsConstant.VALID_STATUS);
        info.setType(materialCategory.getType());
        info.setPcode(materialCategory.getCategoryCode());
        Long id = IdWorker.createId();
        //根据父categoryCode查询  categoryCode下面所有子节点
        List<MaterialCategory> list = materialCategoryMapper.selectCategoryByPcode(info);
        if(null==list || list.size()==0){
             //未查到则查询前端传过来的节点信息
            MaterialCategory category=new MaterialCategory();
            category.setStatus("0");
            category.setType(materialCategory.getType());
            category.setCategoryCode(materialCategory.getCategoryCode());
            category.setPcode(materialCategory.getPcode());
            List<MaterialCategory> categoryList = materialCategoryMapper.selectMaterialCategoryList(category);
            if(null==categoryList || categoryList.size()==0){
                return new AjaxResult(PmsConstant.WARN_CODE,"未查询到用户点击的节点信息");
            }
            //新生成级别  新！！！
            String s = PmsUtils.strAddOne(categoryList.get(0).getLevel());
            materialCategory.setLevel(s);
            materialCategory.setPid(categoryList.get(0).getId());
            materialCategory.setPcode(materialCategory.getCategoryCode());//旧值
            materialCategory.setCategoryCode(materialCategory.getCategoryCode()+"001");//新值
            materialCategory.setSort(1L);
        }else{
            //有数据则拿最后一条数据
            MaterialCategory category= list.get(list.size() - 1);
            //新节点编码  新！！！！
            String t = PmsUtils.strAddOne(category.getCategoryCode());
            if(StringUtils.isBlank(t)){
                return AjaxResult.error("分类编码生成异常");
            }
            logger.info("新生成的分类编码为----->"+t);
            //父节点code
            materialCategory.setPcode(materialCategory.getCategoryCode());
            materialCategory.setCategoryCode(t);
            materialCategory.setLevel(category.getLevel());
            materialCategory.setPid(category.getPid());
            materialCategory.setSort(category.getSort()+1);
        }

        materialCategory.setCreateTime(DateUtils.getNowDate());
        materialCategory.setCreateUser(SecurityUtils.getUserId().toString());
        materialCategory.setId(id);
        materialCategoryMapper.insertMaterialCategory(materialCategory);
        //应要求,返回新创建的数据
        MaterialCategory categoryById = materialCategoryMapper.selectMaterialCategoryById(id);
        return AjaxResult.success(categoryById);
    }


    /**
     * 修改物料分类名称
     * 
     * @param materialCategory 物料分类名称
     * @return 结果
     */
    @Override
    public AjaxResult updateMaterialCategory(MaterialCategory materialCategory) {
        materialCategory.setUpdateTime(DateUtils.getNowDate());
        materialCategory.setUpdateUser(SecurityUtils.getUserId().toString());
        int i = materialCategoryMapper.updateMaterialCategory(materialCategory);
        return AjaxResult.success(i);
    }

    /**
     * 删除物料分类名称对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMaterialCategoryByIds(String id) {
        return materialCategoryMapper.deleteMaterialCategoryById(id);
    }

    /**
     * 删除物料分类名称信息
     *
     * 
     * @param id 物料分类名称ID
     * @return 结果
     */
    public int deleteMaterialCategoryById(String id) {
        return materialCategoryMapper.deleteMaterialCategoryById(id);
    }

    @Override
    @Transactional
    public AjaxResult updateNode(MaterialCategory materialCategory) {
        String nodeStatus = materialCategory.getNodeStatus();
        if(StringUtils.isBlank(nodeStatus)){
            return new AjaxResult(PmsConstant.WARN_CODE,"nodeStatus不能为空");
        }
        AjaxResult result=null;
        //同级 inner走inner
        if(materialCategory.getNodeStatus().equals("inner")){
            result = logicSecondCase(materialCategory);
        }else if((materialCategory.getNodeStatus().equals("after")
                || materialCategory.getNodeStatus().equals("before"))){
            result = logicFirstCase(materialCategory);
        }
        return result;
    }

    @Override
    public int selectCount() {
        return materialCategoryMapper.selectCount();
    }

    @Override
    public List<MaterialCategory> materialFistCategory(MaterialCategory category) {
        Integer type = category.getType();
        // 0物资  1设备  2配件库
        if(null==type){
            return null;
        }
        List<MaterialCategory> list = materialCategoryMapper.materialFistCategory(category);
        return list;
    }

    /**
     * node1插入在node2后面 after
     * 修改node1
     *
     * @param materialCategory
     * @return
     */
    public AjaxResult logicFirstCase(MaterialCategory materialCategory){
        //node1
        Long nodeId1 = materialCategory.getNodeId1();
        MaterialCategory node1Category = materialCategoryMapper.selectMaterialCategoryById(nodeId1);
        if(null==node1Category){
            return new AjaxResult(PmsConstant.WARN_CODE,"nodeId1未查询到节点信息");
        }
        //node2
        Long nodeId2 = materialCategory.getNodeId2();
        MaterialCategory node2Category = materialCategoryMapper.selectMaterialCategoryById(nodeId2);
        if(null==node2Category){
            return new AjaxResult(PmsConstant.WARN_CODE,"nodeId2未查询到节点信息");
        }
        //根据前端传pid查pcode
        if(materialCategory.getPid()==0L){
            node1Category.setPcode("0");
            node1Category.setPid(0l);
        }else{
            MaterialCategory category = materialCategoryMapper.selectMaterialCategoryById(materialCategory.getPid());
            if(null==category){
                return new AjaxResult(PmsConstant.WARN_CODE,"根据pid未查询到数据");
            }
            node1Category.setPcode(category.getCategoryCode());
            node1Category.setPid(category.getId());
        }

        if(materialCategory.getNodeStatus().equals("after")){
            node1Category.setSort(node2Category.getSort() + 1);
        }else if(materialCategory.getNodeStatus().equals("before")){
            node1Category.setSort(node2Category.getSort() - 1);
        }
        node1Category.setUpdateUser(SecurityUtils.getUserId().toString());
        node1Category.setUpdateTime(DateUtils.getNowDate());

        int i = materialCategoryMapper.updateMaterialCategory(node1Category);
        return AjaxResult.success(i);
    }


    /**
     * node1插入在node2里面 inner
     *
     * @param materialCategory
     * @return
     */
    public AjaxResult logicSecondCase(MaterialCategory materialCategory){
        Long nodeId1 = materialCategory.getNodeId1();
        MaterialCategory node1Category = materialCategoryMapper.selectMaterialCategoryById(nodeId1);
        if(null==node1Category){
            return new AjaxResult(PmsConstant.WARN_CODE,"nodeId1未查询到节点信息");
        }
        //根据前端传pid查pcode
        if(materialCategory.getPid()==0L){
            node1Category.setPcode("0");
            node1Category.setPid(0l);
        }else{
            MaterialCategory category = materialCategoryMapper.selectMaterialCategoryById(materialCategory.getPid());
            if(null==category){
                return new AjaxResult(PmsConstant.WARN_CODE,"根据pid未查询到数据");
            }
            node1Category.setPcode(category.getCategoryCode());
            node1Category.setPid(category.getId());
        }
        node1Category.setUpdateUser(SecurityUtils.getUserId().toString());
        node1Category.setUpdateTime(DateUtils.getNowDate());

        MaterialCategory info=new MaterialCategory();
        info.setType(PmsConstant.BASE_PART_INFO);
        info.setPid(materialCategory.getPid());
        info.setStatus(PmsConstant.VALID_STATUS);
        List<MaterialCategory> list = materialCategoryMapper.selectMaterialCategoryList(info);
        MaterialCategory category = list.get(list.size() - 1);
        //查询 pid下最后一个节点+1;
        node1Category.setSort(category.getSort()+1);
        int i = materialCategoryMapper.updateMaterialCategory(node1Category);
        return AjaxResult.success(i);
    }


    @Override
    @Transactional
    public int initMaterialCategory() {
        String filepath = "/Users/zxb/Desktop/java/init.txt";
        Map<String, Long> idsMap = new HashMap<>();
        List<InitMaterialController.MatCategory> list = readLineCategory(filepath, idsMap);
        long orderinit = 1l;
        //处理数据并且入库
        List<MaterialCategory> dataList = new ArrayList<>();
        for(InitMaterialController.MatCategory matCategory : list){
            MaterialCategory mc = new MaterialCategory();
            mc.setId(idsMap.get(matCategory.getId()));
            mc.setPid(idsMap.get(matCategory.getPid()));
            String code = matCategory.getCode();
            mc.setCategoryCode(code);
            if(code.length() == 2){
                mc.setLevel("1");
                mc.setPcode("0");
                mc.setPid(0l);
            }else if(code.length() == 4){
                mc.setLevel("2");
                mc.setPcode(code.substring(0, 2));
            }else if(code.length() == 6){
                mc.setLevel("3");
                mc.setPcode(code.substring(0, 4));
            }else if(code.length() == 11){
                mc.setLevel("4");
                mc.setPcode(code.substring(0, 6));
            }
            mc.setCategoryName(matCategory.getName().replace("(" + code + ")", ""));
            mc.setStatus("0");
            mc.setSort(orderinit);
            dataList.add(mc);
            orderinit ++;
        }
        //分片执行批处理
        int perBatchInsertNum = 1000;
        int insertCount = dataList.size()/perBatchInsertNum + 1;
        for (int i = 0; i < insertCount; i++) {
            List<MaterialCategory> newList = dataList.subList(i * perBatchInsertNum, (i+1)*perBatchInsertNum > dataList.size() ? dataList.size() : (i+1)*perBatchInsertNum);
            materialCategoryMapper.batchInsert(newList);
        }
        return 1;
    }

    @Override
//    @Transactional
    public int initMaterial() {
        for (int k = 7; k <=28 ; k++) {
            String filepath = "/Users/zxb/Desktop/java/minfo_"+k+".txt";
//        List<MaterialInfo> dataList = readLineMaterial(filepath);
            List<Map<String, Object>> categorys = materialCategoryMapper.getAllCategory();
            Map<String, Long> categoryMap = new HashMap<>();
            for(Map<String, Object> cg : categorys){
                categoryMap.put((String) cg.get("categoryCode"), Long.parseLong(String.valueOf(cg.get("id"))));
            }

            List<MaterialInfo> dataList = new ArrayList<>();
            RandomAccessFile randomFile = null;
            int count = 1;
            try {
                randomFile = new RandomAccessFile(filepath, "r");
                randomFile.seek(0);//开始读取的文件偏移量
                String tmp = null;
                while ((tmp = randomFile.readLine()) != null) {
                    String tt = new String(tmp.getBytes("ISO-8859-1"), "utf-8");
                    MaterialInfo maInfo = JSONObject.parseObject(tt, MaterialInfo.class);
                    maInfo.setId(IdWorker.createId());
                    maInfo.setCategoryId(categoryMap.get(maInfo.getCategoryCode()));
                    dataList.add(maInfo);
                /*System.out.println("读取第"+ count +"行：" + tt);
                List<MaterialInfo> newList = new ArrayList<>();
                newList.add(maInfo);
                materialInfoMapper.batchInsert(newList);
                System.out.println("插入成功;");
                count ++;*/
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //分片执行批处理
            int perBatchInsertNum = 1000;
            int insertCount = dataList.size()/perBatchInsertNum + 1;
            for (int i = 0; i < insertCount; i++) {
                int start = i * perBatchInsertNum;
                int end = (i+1)*perBatchInsertNum;
                if(start < end){
                    List<MaterialInfo> newList = dataList.subList(start, end > dataList.size() ? dataList.size() : end);
                    if(newList != null && newList.size() > 0){
                        int flg = materialInfoMapper.batchInsert(newList);
                        logger.error(String.format("插入%s成功, 结果%s;", newList.size(), flg));
                    }
                }
            }
        }


        return 1;
    }


    private static List<MaterialInfo> readLineMaterial(String filepath){
        /*List<Map<String, Object>> categorys = materialCategoryMapper.getAllCategory();
        Map<String, Long> categoryMap = new HashMap<>();
        for(Map<String, Object> cg : categorys){
            categoryMap.put((String) cg.get("categoryCode"), Long.parseLong(String.valueOf(cg.get("id"))));
        }*/
        long orderinit = 1l;
        RandomAccessFile randomFile = null;
        List<MaterialInfo> result = new ArrayList<>();


        try {
            randomFile = new RandomAccessFile(filepath, "r");
            randomFile.seek(0);//开始读取的文件偏移量

            String tmp = null;
            int line = 1;
            while ((tmp = randomFile.readLine()) != null) {

                File file = new File("/Users/zxb/Desktop/java/minfo_" + line + ".txt");
                FileWriter fileWriter = new FileWriter(file,true);



                String tt = new String(tmp.getBytes("ISO-8859-1"), "utf-8");
                JSONObject obj = JSONObject.parseObject(tt);
                JSONObject data = (JSONObject) obj.get("data");
                List<JSONObject> list = (List<JSONObject>) data.get("resultDate");
//                List<Material> list = array;
//                List<Material> list = JSONArray.parseArray(JSONObject.toJSONString(data.get("resultDate")), Material.class);
                if(list != null && list.size() > 0) {
                    for (JSONObject jsonObject : list) {
                        Material material = JSONObject.parseObject(jsonObject.toJSONString(), Material.class);
                        MaterialInfo maInfo = new MaterialInfo();
                        maInfo.setCategoryCode(material.getClassificationCode());
//                        maInfo.setCategoryId(categoryMap.get(material.getClassificationCode()));
                        maInfo.setMaterialEnName(material.getEnName());
                        maInfo.setMaterialName(material.getName());
                        maInfo.setMaterialSpec(material.getSpecification());
                        maInfo.setUnitCode(material.getUnit());
                        maInfo.setMaterialCode(material.getCode());
//                        maInfo.setId(IdWorker.createId());
                        maInfo.setSort(orderinit);
//                        result.add(maInfo);
                        String ss = JSONObject.toJSONString(maInfo);
                        logger.info("第" + orderinit + "个：" + ss);
                        orderinit ++;
                        fileWriter.write(ss +  "\r\n");
                    }
                }
                fileWriter.close();
                line ++;
            }

        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("异常"+e.getMessage());
        }finally {
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
//                    logger.error("异常"+e.getMessage());
                }
            }
        }
        return result;
    }

    public static class Material{

        private String id;
        private String code;
        private String name;
        private String enName;
        private String unit;
        private String specification;
        private String classificationId;
        private String classificationCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getClassificationId() {
            return classificationId;
        }

        public void setClassificationId(String classificationId) {
            this.classificationId = classificationId;
        }

        public String getClassificationCode() {
            return classificationCode;
        }

        public void setClassificationCode(String classificationCode) {
            this.classificationCode = classificationCode;
        }
    }

    private  List<InitMaterialController.MatCategory> readLineCategory(String filepath, Map<String, Long> idsMap){
        RandomAccessFile randomFile = null;
        List<InitMaterialController.MatCategory> result = new ArrayList<>();
        try {
            randomFile = new RandomAccessFile(filepath, "r");
            randomFile.seek(0);//开始读取的文件偏移量
            String tmp = null;
            while ((tmp = randomFile.readLine()) != null) {
                String tt = new String(tmp.getBytes("ISO-8859-1"), "utf-8");
                JSONObject obj = JSONObject.parseObject(tt);
                List<InitMaterialController.MatCategory> list = JSONArray.parseArray(JSONObject.toJSONString(obj.get("data")), InitMaterialController.MatCategory.class);
                if(list != null && list.size() > 0) {
                    for (InitMaterialController.MatCategory matCategory : list) {
                        String id = matCategory.getId();
                        if(idsMap.get(id) == null){
                            matCategory.setCode(matCategory.getParams().get("code"));
                            idsMap.put(id, IdWorker.createId());
                            result.add(matCategory);
                        }else{
                            logger.info("有id重复的数据：" + id);
                        }

                    }
                }
            }
        } catch (IOException e) {
            logger.error("异常"+e.getMessage());
        }finally {
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                    logger.error("异常"+e.getMessage());
                }
            }
        }
        return result;
    }

    @Override
    public List<Map> categoryTreeList() {
        List<MaterialCategory> categoryList = this.materialCategoryMapper.selectMaterialCategoryList(new MaterialCategory());
        //根级数据
        List<Map> rootList = new ArrayList<>();
        //将模板明细转换成树形
        Map<Long,Map> detailRelationMap = new HashMap<>();
        List<Map> categoryMapList = new ArrayList<>(categoryList.size());
        for (int i = 0; i < categoryList.size(); i++) {
            MaterialCategory category = categoryList.get(i);
            Map warpMap = ObjectUtils.toMap("id",category.getId()+"","pid",category.getPid(),
                    "value",category.getCategoryCode(),"label",category.getCategoryName());
            detailRelationMap.put(category.getId(), warpMap);
            if(category.getPid() == 0 || category.getPid() == null){
                rootList.add(warpMap);
            }
            categoryMapList.add(warpMap);
        }
        //将子级数据塞入到父级的child属性中
        for (int i = 0; i < categoryMapList.size(); i++) {
            Map map = categoryMapList.get(i);
            Long pid = ObjectUtils.toLong(map.get("pid"));
            if(pid == 0L)
                continue;
            Map parent = detailRelationMap.get(pid);
            if(parent == null){
//                logger.info("{}未能获取到父级,id:{}",parent.get("label"),parent.get("id"));
                continue;
            }
            if(parent.get("children") == null)
                parent.put("children",new ArrayList<>());
            ((List)parent.get("children")).add(map);
        }
        return rootList;
    }

    @Override
    public List<MaterialInfo> handleLogicCategoryIds(List<MaterialInfo> list) {
        if(CollectionUtils.isEmpty(list)){
            return list;
        }
        List<String> categoryCodeList = list.stream().map(e -> e.getCategoryCode()).collect(Collectors.toList());
        List<MaterialCategory> categoryList = materialCategoryMapper.selectInfoByCategoryCodes(categoryCodeList);
        Map<String, List<MaterialCategory>> listMap = categoryList.stream().collect(Collectors.groupingBy(MaterialCategory::getCategoryCode));
        for (int i = 0; i < list.size(); i++) {
            String categoryCode = list.get(i).getCategoryCode();
            List<MaterialCategory> maList = listMap.get(categoryCode);
            if(!CollectionUtils.isEmpty(maList)){
                list.get(i).setCategoryName(maList.get(0).getCategoryName());
                list.get(i).setCategoryId(maList.get(0).getId());
            }
        }
        return list;
    }

    @Override
    public List<MaterialCategory> selectMaterialCategoryByName(MaterialCategory category) {
        //根据名称查询分类 id
        List<MaterialCategory> list = materialCategoryMapper.selectMaterialCategoryList(category);
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        List<Long> idList = list.stream().map(e -> e.getId()).collect(Collectors.toList());
        //for 循环id  处理path
        List<MaterialCategory> rstList =materialCategoryMapper.selectBathByPath(idList);
        return rstList;
    }

    @Override
    @Transactional
    public void resetPath(MaterialCategory materialCategory) {
        List<MaterialCategory> list = materialCategoryMapper.selectMaterialCategoryList(materialCategory);
        Map<String,MaterialCategory> map = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            MaterialCategory temp = list.get(i);
            map.put(temp.getType()+"_"+temp.getCategoryCode(), temp);
        }
        for (int i = list.size()-1; i > -1; i--) {
            MaterialCategory temp = list.get(i);
            MaterialCategory p = temp;
            List<String> ancestorList = new ArrayList<>(8);
            while(true){
                p =map.get(temp.getType()+"_"+p.getPcode()); //类型_分类编码
                if(p == null)
                    break;
                ancestorList.add(p.getId()+"");
                if(StringUtils.equals(temp.getPcode(),"0") && StringUtils.equals(temp.getPcode(),"1301"))
                    break;
            }
            StringBuilder sb = new StringBuilder("0/");
            if(!CollectionUtils.isEmpty(ancestorList)){
                ancestorList.sort((v1,v2)->-1);
                sb.append(StringUtils.join(ancestorList, "/")+"/");
            }
            sb.append(temp.getId());
            temp.setPath(sb.toString());
        }
        int limit = 1000;
        int count = list.size()/limit+(list.size()%limit==0?0:1);
        for (int i = 0; i < count; i++) {
            int end = (i+1)*limit;
            List<MaterialCategory> tlist = list.subList(i*limit,end>list.size()?list.size():end);
            this.materialCategoryMapper.batchUpdatePath(tlist);        
        }
    }

    /**
     * 查询分类的第三、第四、层级
     *
     * @param list
     * @param type 0物资  1设备  2配件库
     * @return
     */
    @Override
    public List<Map> selectMaterialCategoryPath(List<String> list,Integer type) {
        MaterialCategory materialCategory=new MaterialCategory();
        materialCategory.setType(type);
        //查询改类型下所有的物资信息
        List<MaterialCategory> allList = materialCategoryMapper.selectMaterialCategoryList(materialCategory);
        if(CollectionUtils.isEmpty(allList)){
            logger.error("暂未查到改类型下的物资");
            return null;
        }
        Map<String, List<MaterialCategory>> listMap = allList.stream().collect(Collectors.groupingBy(e -> String.valueOf(e.getId())));
        //根据code查询3、4的分类层级信息
        List<MaterialCategory> rstList= materialCategoryMapper.selectBathByCode(list);
        List<Map> rstListMap=new ArrayList<>();
        for (int i = 0; i < rstList.size(); i++) {
            Map<String,String> map=new HashMap<>();
            String categoryCode = rstList.get(i).getCategoryCode();
            map.put("cagegoryCode",categoryCode);
            String path = rstList.get(i).getPath();
            if(StringUtils.isNotBlank(path)){
                String[] split = path.split("/");
                Integer length=split.length;
                //取第三、第四层级
                if(length>=4){
                    String thirdCode= split[2];
                    logger.info("fourCode【{}】",thirdCode);
                    List<MaterialCategory> thirdList = listMap.get(thirdCode);
                    if(!CollectionUtils.isEmpty(thirdList)){
                        map.put("thirdCode",thirdCode);
                        map.put("thirdName",thirdList.get(0).getCategoryName());
                    }
                    String fourCode= split[3];
                    logger.info("fourCode【{}】",fourCode);
                    List<MaterialCategory> fourList = listMap.get(fourCode);
                    if(!CollectionUtils.isEmpty(fourList)){
                        map.put("fourCode",thirdCode);
                        map.put("fourName",fourList.get(0).getCategoryName());
                    }
                }
            }
            rstListMap.add(map);
        }
        return rstListMap;
    }
}
