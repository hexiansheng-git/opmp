package com.hhwy.pm.qqch.wzch.puchasesupply.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseViewDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.mapper.WzchPurchaseSupplyDetailMapper;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyDetailService;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购供应策划材料视角物资详情Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchPurchaseSupplyDetailServiceImpl implements IWzchPurchaseSupplyDetailService {
    @Resource
    private WzchPurchaseSupplyDetailMapper wzchPurchaseSupplyDetailMapper;
    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private SystemApiService dictTypeService;

    @Resource
    private RedisUtils redisUtils;


    private final static String TO = "至";


    /**
     * 查询采购供应策划材料视角物资详情
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 采购供应策划材料视角物资详情
     */
    @Override
    public WzchPurchaseSupplyDetail selectWzchPurchaseSupplyDetailById(Long id) {
        return wzchPurchaseSupplyDetailMapper.selectWzchPurchaseSupplyDetailById(id);
    }

    /**
     * 查询采购供应策划材料视角物资详情列表
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 采购供应策划材料视角物资详情
     */
    @Override
    public List<WzchPurchaseSupplyDetailDTO> selectWzchPurchaseSupplyDetailList(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail) {
        List<WzchPurchaseSupplyDetailDTO> detailDTOS = wzchPurchaseSupplyDetailMapper.selectWzchPurchaseSupplyDetailList(wzchPurchaseSupplyDetail);


        Map<String, String> dictMa = new HashMap<>(4);
        dictMa.put("materialStandard_materialStandardName", "material_standard");
        dictMa.put("categoryName_categoryNameName", "total_demand_category_name");
        dictMa.put("source_sourceName", "wzch_purchase_source");
        // 设置父级的字典项目
        wzchCommonService.setDicValue(detailDTOS, dictMa);


        // 获取所有的子集
        List<WzchPurchaseSupplyDetailDTO> allChildren = new ArrayList<>();
        detailDTOS.forEach(item->{
            allChildren.addAll(item.getChildren());
        });
        wzchCommonService.setCurrentName(allChildren);
        wzchCommonService.setDicValue(allChildren,dictMa);
        wzchCommonService.setWzchtMaterialInfo(allChildren);
        return wzchCommonService.setWzchtMaterialInfo(detailDTOS);
    }

    /**
     * 新增采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    @Override
    public int insertWzchPurchaseSupplyDetail(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail) {

        wzchPurchaseSupplyDetail.setId(IdWorker.createId());

        wzchPurchaseSupplyDetail.setCreateTime(DateUtils.getNowDate());

        return wzchPurchaseSupplyDetailMapper.insertWzchPurchaseSupplyDetail(wzchPurchaseSupplyDetail);
    }

    /**
     * 修改采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    @Override
    public int updateWzchPurchaseSupplyDetail(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail) {
        wzchPurchaseSupplyDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchPurchaseSupplyDetailMapper.updateWzchPurchaseSupplyDetail(wzchPurchaseSupplyDetail);
    }

    /**
     * 删除采购供应策划材料视角物资详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchPurchaseSupplyDetailByIds(String ids) {
        return wzchPurchaseSupplyDetailMapper.deleteWzchPurchaseSupplyDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购供应策划材料视角物资详情信息
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchPurchaseSupplyDetailById(Long id) {
        return wzchPurchaseSupplyDetailMapper.deleteWzchPurchaseSupplyDetailById(id);
    }

    @Override
    public List<WzchPurchaseSupplyDetailDTO> getListByPrjId(WzchPurchaseSupplyDetailDTO dto) {
       return getListByPrjId(dto, "1");
    }

    /**
     * 根据项目id获取物资详情 获取的是来源策划的数据  如果物资在有采购相关的数据 就拿取物资 如果没有 就不用拿取
     *
     * @param dto
     * @return
     */
    @Override
    public List<WzchPurchaseSupplyDetailDTO> getListByPrjId(WzchPurchaseSupplyDetailDTO dto,String dataType) {

        // 详情 关联物资总需 关联优先采购物资台账 关联优先采购物资策划 关联物资信息
        List<WzchPurchaseSupplyDetailDTO> listByPrjId = new ArrayList<>();
        if (IWzchPurchaseSupplyService.TWO.equals(dataType)) {
            listByPrjId = wzchPurchaseSupplyDetailMapper.getLocalListByPrjId(dto);
        } else {
            listByPrjId = wzchPurchaseSupplyDetailMapper.getListByPrjId(dto);
        }

        // 为空直接返回
        if (CollectionUtils.isEmpty(listByPrjId)) return listByPrjId;

        listByPrjId = wzchCommonService.setWzchtMaterialInfo(listByPrjId);
        // 获取物资code
        List<String> materialCodeList = listByPrjId.stream().map(WzchPurchaseSupplyDetailDTO::getMaterialCode).collect(Collectors.toList());
        // 查询来源策划 查询采购来源
        List<WzchPurchaseSupplyDetailDTO> purchaseSourceList = wzchPurchaseSupplyDetailMapper.selectPurchaseSource(materialCodeList);

        int idSer = 0;
        // 处理物资的采购来源
        for (WzchPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : listByPrjId) {
            // 生成id 前端会用到
            Long detailId = wzchPurchaseSupplyDetailDTO.getPurchaseSupplyDetailId() == null ? idSer++ : wzchPurchaseSupplyDetailDTO.getPurchaseSupplyDetailId();
            wzchPurchaseSupplyDetailDTO.setPurchaseSupplyDetailId(detailId);
            String materialCode = wzchPurchaseSupplyDetailDTO.getMaterialCode();
            String materialStandard = wzchPurchaseSupplyDetailDTO.getMaterialStandard();

            // 来源
            StringBuilder source = new StringBuilder("");
            StringBuilder sourceName = new StringBuilder("");
            List<Map<String, String>> sourceMapList = new ArrayList<>();

            // 筛选出数据
            purchaseSourceList.stream().filter(item ->
                    StringUtils.isNotEmpty(item.getMaterialCode())
                            && StringUtils.isNotEmpty(item.getMaterialStandard())
                            && item.getMaterialCode().equals(materialCode)
                            && item.getMaterialStandard().equals(materialStandard)).findFirst().ifPresent(purchaseSource -> {
                BigDecimal localNum = purchaseSource.getLocalNum();
                BigDecimal internalNum = purchaseSource.getInternalNum();
                BigDecimal otherStaseNum = purchaseSource.getOtherStateNum();
                if (localNum != null && localNum.compareTo(BigDecimal.ZERO) > 0) {
                    source.append("0");
                    sourceName.append("当地采购");

                    Map<String, String> sourceMap = new HashMap<>(2);
                    sourceMap.put("sourceName", "当地采购");
                    sourceMap.put("sourceValue", "0");
                    sourceMapList.add(sourceMap);
                }
                if (internalNum != null && internalNum.compareTo(BigDecimal.ZERO) > 0) {
                    source.append(StringUtils.isEmpty(source.toString()) ? "1" : ",1");
                    sourceName.append(StringUtils.isEmpty(sourceName.toString()) ? "国内采购" : ",国内采购");

                    Map<String, String> sourceMap = new HashMap<>(2);
                    sourceMap.put("sourceName", "国内采购");
                    sourceMap.put("sourceValue", "1");
                    sourceMapList.add(sourceMap);
                }
                if (otherStaseNum != null && otherStaseNum.compareTo(BigDecimal.ZERO) > 0) {
                    source.append(StringUtils.isEmpty(source.toString()) ? "2" : ",2");
                    sourceName.append(StringUtils.isEmpty(sourceName.toString()) ? "第三国采购" : ",第三国采购");

                    Map<String, String> sourceMap = new HashMap<>(2);
                    sourceMap.put("sourceName", "第三国采购");
                    sourceMap.put("sourceValue", "2");
                    sourceMapList.add(sourceMap);
                }

            });
            wzchPurchaseSupplyDetailDTO.setSourceMap(sourceMapList);
            wzchPurchaseSupplyDetailDTO.setSource(source.toString());
            wzchPurchaseSupplyDetailDTO.setSourceName("-");
        }

        return this.dealList(listByPrjId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrUpdateBatch(List<WzchPurchaseSupplyDetailDTO> detailList, Long supplyId) {

        if (CollectionUtils.isEmpty(detailList)) {
            // TODO throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
        }
        Assert.notNull(supplyId, "采购供应策划id不能为空");
        // 用于存放所有的物资的批次信息 然后入库
        List<WzchPurchaseSupplyDetailDTO> saveBachDetails = new ArrayList<>();

        // 先将数据进行处理
        for (WzchPurchaseSupplyDetailDTO detail : detailList) {
            Long detailId = IdWorker.createId();
            String materialCode = detail.getMaterialCode();
            detail.setId(detailId);
            detail.setPurchaseSupplyId(supplyId);
            detail.setValid("0");
            // 设置创建信息
            EntityUtils.setCreateUpdateInfo(detail);
            List<WzchPurchaseSupplyDetailDTO> batchDetails = detail.getChildren();
            if (CollectionUtils.isEmpty(batchDetails)) {
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资批次信息不能为空");
            }
            for (WzchPurchaseSupplyDetailDTO batch : batchDetails) {
                batch.setId(IdWorker.createId());
                batch.setMaterialCode(materialCode);
                batch.setPurchaseSupplyDetailId(detailId);
                batch.setPurchaseSupplyId(supplyId);
                batch.setValid("0");
                batch.setPtVar1(FtDateUtils.formatDate(batch.getPlanTime()));
                // 设置创建信息
                EntityUtils.setCreateUpdateInfo(batch);
            }
            // 将批次数据封装
            Optional.of(batchDetails).ifPresent(saveBachDetails::addAll);
        }


        // 根据采购供应id删除物资详情
        wzchPurchaseSupplyDetailMapper.deleteDetailBySupplyId(supplyId);

        // 根据采购供应id删除物资批次详情
        wzchPurchaseSupplyDetailMapper.deleteBatchDetailBySupplyId(supplyId);

        // 批量插入物资详情
        int i = wzchPurchaseSupplyDetailMapper.insertOrUpdateBatch(detailList);

        // 批量插入物资批次详情
        wzchPurchaseSupplyDetailMapper.batchInsertOrUpdateBatchDetails(saveBachDetails);

        return i;
    }

    @Override
    public List<WzchPurchaseSupplyDetailDTO> selectSupplyDetailListBySupplyId(WzchPurchaseSupplyDetailDTO dto) {
        List<WzchPurchaseSupplyDetailDTO> detailList = this.selectWzchPurchaseSupplyDetailList(dto);
        // 判断为空
        if (CollectionUtils.isEmpty(detailList)) throw new RuntimeException("没有查询到物资采购详情");

        return this.dealList(detailList);
    }

    @Override
    public List<WzchPurchaseSupplyDetailDTO> getLevelList(List<WzchPurchaseSupplyDetailDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) throw new RuntimeException("数据不能为空");
        StringBuilder msg = new StringBuilder("");
        // 将数据按照物资编码进行分组
        Map<Integer, List<WzchPurchaseSupplyDetailDTO>> groupMap = dtoList.stream().collect(Collectors.groupingBy(item->new BigDecimal(item.getSerialNum()).intValue()));

        List<WzchPurchaseSupplyDetailDTO> res = new ArrayList<>(groupMap.size());
        // 将分组中的数据进行处理 分成父子级
        for (Integer s : groupMap.keySet()) {
            List<WzchPurchaseSupplyDetailDTO> detailDTOS = groupMap.get(s);
            // 将分组中的数据按照序号大小进行排序
            List<WzchPurchaseSupplyDetailDTO> sortedList = detailDTOS.stream().sorted(Comparator.comparing(i -> new BigDecimal(i.getSerialNum()))).collect(Collectors.toList());
            // 拿到第一条数据 为父级
            WzchPurchaseSupplyDetailDTO parentDetail = sortedList.get(0);
            // 子集数据
            List<WzchPurchaseSupplyDetailDTO> children = sortedList.subList(1, sortedList.size());
            // 将父级分的批次数量和子集数据数量进行比较 相等可以继续 不相等 抛出异常
            String pBatch = parentDetail.getBatch();
            if (StringUtils.isEmpty(pBatch)) throw new RuntimeException("批次不能为空");
            int batch = Integer.parseInt(pBatch);
            if (batch != children.size()) {
                msg.append("物资编码为:").append(s).append("的批次数量和其子集具体批次不匹配;\n");
            } else {
                // 将子集数据放在父级上面
                parentDetail.setChildren(children);
                res.add(parentDetail);
            }
        }
        if (!"".equals(msg.toString())) throw new RuntimeException(msg.toString());
        List<WzchPurchaseSupplyDetailDTO> collect = res.stream().sorted(Comparator.comparing(WzchPurchaseSupplyDetailDTO::getMaterialCode)).collect(Collectors.toList());
        this.dealTreeList(collect);
        return collect;
    }

    @Override
    public List<WzchPurchaseViewDetailDTO> purchaseView(Map<String, String> map) {
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        String projectId = map.get("projectId");
        Assert.notNull(projectId, "项目id不能为空");

        String searchTimeGroup = "";
        String timeGroupsFromRedis;
        String dataType = map.get(IWzchPurchaseSupplyService.DATATYPE);

        if (IWzchPurchaseSupplyService.TWO.equals(dataType)) {
            timeGroupsFromRedis = (String) redisUtils.hGet(PmsConstant.WPP_LOCAL_PURCHASE_VIEW + projectId, String.valueOf(SecurityUtils.getUserId()));
        } else {
            timeGroupsFromRedis = (String) redisUtils.hGet(PmsConstant.WPP_PURCHASE_VIEW + projectId, String.valueOf(SecurityUtils.getUserId()));
        }


        // 如果前端传入的条件不为空 就先将searchTimeGroup赋值为前端传入的条件
        searchTimeGroup = StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(startTime) ? startTime + TO + endTime : searchTimeGroup;

        // 只有当传入的条件和从缓存中拿取的条件都不为空的时候才能进行拼接
        searchTimeGroup = StringUtils.isNotEmpty(searchTimeGroup) && StringUtils.isNotEmpty(timeGroupsFromRedis) ? searchTimeGroup + "," + timeGroupsFromRedis : searchTimeGroup;

        // 如果前端参数为空 但是redis中条件不为空 就将条件赋值为redis中的条件
        searchTimeGroup = StringUtils.isEmpty(searchTimeGroup) && StringUtils.isNotEmpty(timeGroupsFromRedis) ? timeGroupsFromRedis : searchTimeGroup;

        // 在查询数据之前条件还是空的 就直接返回
        if (StringUtils.isEmpty(searchTimeGroup)) return new ArrayList<>();

        List<Map<String, Object>> sqlConditions = this.getSqlConditions(searchTimeGroup);

        // 查询数据
        List<WzchPurchaseSupplyDetailDTO> resFromDB = this.wzchPurchaseSupplyDetailMapper.selectPurchaseView(sqlConditions, projectId,dataType);

        // 结果为空直接返回
        if (CollectionUtils.isEmpty(resFromDB)) return new ArrayList<>();

        // 从redis中获取物资名称等信息
        this.wzchCommonService.setWzchtMaterialInfo(resFromDB);

        // 字典项目
        HashMap<String, String> dictMap = new HashMap<>();
        dictMap.put("categoryName_categoryNameName", "total_demand_category_name");
        dictMap.put("materialStandard_materialStandardName", "material_standard");
        this.wzchCommonService.setDicValue(resFromDB, dictMap);


        // 结果封装
        List<WzchPurchaseViewDetailDTO> resList = new ArrayList<>();

        // 分组
        LinkedHashMap<String, List<WzchPurchaseSupplyDetailDTO>> timeGroupMap = resFromDB.stream()
                // 数据根据时间组分组 并返回有序集合
                .collect(Collectors.groupingBy(WzchPurchaseSupplyDetailDTO::getPlanPurchaseDateGroup, LinkedHashMap::new, Collectors.toList()));

        List<SysDictData> wzchPurchaseSource = dictTypeService.selectDictDataByType("wzch_purchase_source");
        int idSer = 0;
        for (String timeGroup : timeGroupMap.keySet()) {
            WzchPurchaseViewDetailDTO view = new WzchPurchaseViewDetailDTO();
            // 前端用
            view.setId((long) idSer++);
            view.setPlanPurchaseDateGroup(timeGroup);
            // 采购供应策划详情
            List<WzchPurchaseSupplyDetailDTO> detailDTOS = timeGroupMap.get(timeGroup);
            // 一条物资可能会有多个来源 需要将物资来源拆开
            List<WzchPurchaseSupplyDetailDTO> sourcesDetaiList = this.getSourcesDetailList(detailDTOS, wzchPurchaseSource);
            for (WzchPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : sourcesDetaiList) {
                if (wzchPurchaseSupplyDetailDTO.getSourceName() == null) wzchPurchaseSupplyDetailDTO.setSourceName("");
            }

            // 根据来源分组
            Map<String, List<WzchPurchaseSupplyDetailDTO>> sourcesMap = sourcesDetaiList.stream().collect(Collectors.groupingBy(WzchPurchaseSupplyDetailDTO::getSourceName));

            // 根据来源分组的数据集合
            List<WzchPurchaseViewDetailDTO.PurchaseView> purchaseViews = new ArrayList<>();
            for (String source : sourcesMap.keySet()) {
                WzchPurchaseViewDetailDTO.PurchaseView purchaseView = new WzchPurchaseViewDetailDTO.PurchaseView();
                // 前端用
                purchaseView.setId((long) idSer++);
                purchaseView.setSource(source);
                List<WzchPurchaseSupplyDetailDTO> details = sourcesMap.get(source);
                purchaseView.setChildren(details);
                purchaseViews.add(purchaseView);
            }
            view.setChildren(purchaseViews);

            resList.add(view);
        }

        return resList;
    }

    private List<WzchPurchaseSupplyDetailDTO> getSourcesDetailList(List<WzchPurchaseSupplyDetailDTO> detailDTOS, List<SysDictData> wzchPurchaseSource) {
        List<WzchPurchaseSupplyDetailDTO> sourcesDetailList = new ArrayList<>();

        for (WzchPurchaseSupplyDetailDTO detailDTO : detailDTOS) {
            String source = detailDTO.getSource();
            if (StringUtils.isEmpty(source)) {
                sourcesDetailList.add(detailDTO);
                continue;
            }
            // 将多个来源拆成多条数据
            String[] split = source.split(",");
            for (String s : split) {
                WzchPurchaseSupplyDetailDTO dto = new WzchPurchaseSupplyDetailDTO();
                BeanUtils.copyProperties(detailDTO, dto);
                dto.setSource(s);
                wzchPurchaseSource.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(s))
                        .findFirst().ifPresent(val -> dto.setSourceName(val.getDictLabel()));
                sourcesDetailList.add(dto);
            }
        }
        return sourcesDetailList;
    }

    /**
     * 保存采购视角
     *
     * @param list
     * @param projectId
     */
    @Override
    public void savePurchaseView(List<WzchPurchaseViewDetailDTO> list, String projectId) {
        String collect = "";
        if (!CollectionUtils.isEmpty(list)) {
            collect = list.stream().map(WzchPurchaseViewDetailDTO::getPlanPurchaseDateGroup).collect(Collectors.joining(","));
        }
        redisUtils.hPut(PmsConstant.WPP_PURCHASE_VIEW + projectId, String.valueOf(SecurityUtils.getUserId()), collect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBySupplyIds(String ids) {
        wzchPurchaseSupplyDetailMapper.deleteBatchBySupplyIds(Convert.toStrArray(ids));
        return wzchPurchaseSupplyDetailMapper.deleteBySupplyIds(Convert.toStrArray(ids));
    }

    /**
     * 获取时间sql
     *
     * @param tgs
     * @return
     */
    private List<Map<String, Object>> getSqlConditions(String tgs) {
        List<Map<String, Object>> sqlCondition = new ArrayList<>();

        String[] timeGroupArr = tgs.split(",");
        for (String timeGroup : timeGroupArr) {
            HashMap<String, Object> sqlItem = new HashMap<>();
            // 时间常量
            sqlItem.put("timeConstant", timeGroup);
            // 将timeGroup使用 '至' 分割 分割成开始时间和结束时间
            String[] tg = timeGroup.split(TO);
            // 开始
            sqlItem.put("startTime", tg[0]);
            // 结束
            sqlItem.put("endTime", tg[1]);
            // 加入集合
            sqlCondition.add(sqlItem);
        }
        return sqlCondition;
    }


    // 获取时间sql


    @Override
    public List<WzchPurchaseSupplyDetailDTO> dealList(List<WzchPurchaseSupplyDetailDTO> detailList) {

        List<WzchPurchaseSupplyDetailDTO> allList = new ArrayList<>();
        // 将查询出来的结果进行分解
        for (int i = 0; i < detailList.size(); i++) {
            WzchPurchaseSupplyDetailDTO detail = detailList.get(i);
            // 总序号 即第一层物资数据的序号
            BigDecimal totalSerialNum = BigDecimal.ONE.add(BigDecimal.valueOf(i));
            // 设置序号
            detail.setSerialNum(String.valueOf(totalSerialNum.intValue()));
            allList.add(detail);
            // 不为空才进行处理批次详情
            Optional.ofNullable(detail.getChildren()).ifPresent(batchList -> {
                // 用来记录批次累积
                BigDecimal accumulation = new BigDecimal("0");
                // 加数 每循环一次 就加一
                BigDecimal addend = new BigDecimal("0.1");
                for (WzchPurchaseSupplyDetailDTO detailDTO : batchList) {
                    accumulation = accumulation.add(addend);
                    detailDTO.setSerialNum(totalSerialNum.add(accumulation).toString());
                }
                allList.addAll(batchList);
            });
        }

        return allList;
    }

    private void dealTreeList(List<WzchPurchaseSupplyDetailDTO> detailList) {
        List<Map<String, String>> sourceMap = getSourceMap();

        // 将查询出来的结果进行分解
        for (int i = 0; i < detailList.size(); i++) {
            WzchPurchaseSupplyDetailDTO detail = detailList.get(i);
            // 总序号 即第一层物资数据的序号
            BigDecimal totalSerialNum = BigDecimal.ONE.add(BigDecimal.valueOf(i));
            // 设置序号
            detail.setSerialNum(String.valueOf(totalSerialNum.intValue()));
            detail.setSourceMap(sourceMap);
            // 不为空才进行处理批次详情
            Optional.ofNullable(detail.getChildren()).ifPresent(batchList -> {
                // 用来记录批次累积
                BigDecimal accumulation = new BigDecimal("0");
                // 加数 每循环一次 就加一
                BigDecimal addend = new BigDecimal("0.1");
                for (WzchPurchaseSupplyDetailDTO detailDTO : batchList) {
                    accumulation = accumulation.add(addend);
                    detailDTO.setSerialNum(totalSerialNum.add(accumulation).toString());
                    detailDTO.setSourceMap(sourceMap);
                }
            });
        }

    }


    private List<Map<String,String>> getSourceMap(){
        List<Map<String,String>> res = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("sourceValue","1");
        map.put("sourceName","国内采购");

        res.add(map);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("sourceValue","2");
        map1.put("sourceName","第三国采购");

        res.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("sourceValue","0");
        map2.put("sourceName","当地采购");

        res.add(map2);


        return res;

    }


}
