package com.hhwy.pm.qqch.wzch.internaladjust.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjust;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import com.hhwy.pm.qqch.wzch.internaladjust.dto.WzchInternalAdjustDTO;
import com.hhwy.pm.qqch.wzch.internaladjust.mapper.WzchInternalAdjustDetailMapper;
import com.hhwy.pm.qqch.wzch.internaladjust.mapper.WzchInternalAdjustMapper;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchInternalAdjustDetailService;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchInternalAdjustService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceDetailService;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * 内部调剂材料策划Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchInternalAdjustServiceImpl implements IWzchInternalAdjustService {
    @Resource
    private WzchInternalAdjustMapper wzchInternalAdjustMapper;
    @Resource
    private IWzchInternalAdjustDetailService detailService;

    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private GenCodeService genCodeService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private IWzchSourceService wzchSourceService;
    @Resource
    private IWzchSourceDetailService wzchSourceDetailService;
    @Resource
    private WzchInternalAdjustDetailMapper wzchInternalAdjustDetailMapper;


    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";

    /**
     * 查询内部调剂材料策划
     *
     * @param id 内部调剂材料策划ID
     * @return 内部调剂材料策划
     */
    @Override
    public WzchInternalAdjust selectWzchInternalAdjustById(Long id) {
        return wzchInternalAdjustMapper.selectWzchInternalAdjustById(id);
    }

    /**
     * 查询内部调剂材料策划列表
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 内部调剂材料策划
     */
    @Override
//    @CustomDatascope(alias = "ia")
    public List<WzchInternalAdjust> selectWzchInternalAdjustList(WzchInternalAdjust wzchInternalAdjust) {
        return wzchInternalAdjustMapper.selectWzchInternalAdjustList(wzchInternalAdjust);
    }

    @Override
    @Transactional
    public void sync(WzchInternalAdjust wzchInternalAdjust) {
        Assert.notNull(wzchInternalAdjust,"数据缺失");
        Assert.notNull(wzchInternalAdjust.getVersion(),"version不能为空");
        wzchInternalAdjustMapper.deleteWzchInternalAdjustByVersion(wzchInternalAdjust.getVersion());
        wzchInternalAdjustMapper.deleteWzchInternalAdjustDetailByVersion(wzchInternalAdjust.getVersion());
        //1、获取来源策划
        WzchSource wzchSource = wzchSourceService.selectWzchSourceByVersion(wzchInternalAdjust.getVersion());
        WzchInternalAdjust dbAdjust = getByVersion(wzchInternalAdjust.getVersion());
        Long id = save(dbAdjust,wzchInternalAdjust);
        if(wzchSource == null)
            return ;
        //2、同步来源策划明细
        List<WzchSourceDetail> list = wzchSourceDetailService.selectInnerAdjustList(wzchSource.getId());
        // > 内部调剂明细
        List<WzchInternalAdjustDetail> detailList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            WzchSourceDetail temp = list.get(i);
            WzchInternalAdjustDetail adjustDetail = new WzchInternalAdjustDetail();
            BeanUtils.copyProperties(temp,adjustDetail);
            adjustDetail.setInnerAdjustNum(temp.getSelfDemandAmount());
            try{
                Date date = new SimpleDateFormat("yyyy").parse(adjustDetail.getRemark());
                adjustDetail.setAdjustableDate(date);  //可调出日期
                adjustDetail.setPlanReqDate(date);     //计划需用日期
            }catch(Exception e){}
            new AddBaseInfoUtil<>().addBaseEntity(adjustDetail);
            adjustDetail.setId(IdWorker.createId());
            adjustDetail.setInternalAdjustId(id);
            adjustDetail.setVersion(wzchInternalAdjust.getVersion());
            adjustDetail.setAdjustableNum(adjustDetail.getAdjustableNum());
            detailList.add(adjustDetail);
        }
        if(CollectionUtils.isNotEmpty(detailList))
            wzchInternalAdjustDetailMapper.insertOrUpdateBatch(detailList);
    }

    private Long save(WzchInternalAdjust adjust,WzchInternalAdjust temp){
        if(adjust ==null){
            adjust = temp;
        }else{
            adjust.setVersion(temp.getVersion());
            adjust.setLimitPriceDesc(temp.getLimitPriceDesc());
        }
        if(adjust.getId() == null){
            adjust.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(adjust);
            adjust.setAdjustCode(genCodeService.getSetCode(CodeEnum.WPS));
            adjust.setTitle(adjust.getAdjustCode());
            this.wzchInternalAdjustMapper.insertWzchInternalAdjust(adjust);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(adjust);
            wzchInternalAdjustMapper.updateWzchInternalAdjust(adjust);
        }
        return adjust.getId();
    }


    private WzchInternalAdjust getByVersion(BigDecimal version){
        WzchInternalAdjust query = new WzchInternalAdjust();
        query.setVersion(version);
        List<WzchInternalAdjust> list = this.wzchInternalAdjustMapper.selectWzchInternalAdjustList(query);
        if(CollectionUtils.isEmpty(list))
            return null;
        return list.get(0);
    }

    /**
     * 新增内部调剂材料策划
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    @Override
    public int insertWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust) {

        wzchInternalAdjust.setId(IdWorker.createId());

        wzchInternalAdjust.setCreateTime(DateUtils.getNowDate());

        return wzchInternalAdjustMapper.insertWzchInternalAdjust(wzchInternalAdjust);
    }

    /**
     * 修改内部调剂材料策划
     *
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    @Override
    public int updateWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust) {
        wzchInternalAdjust.setUpdateTime(DateUtils.getNowDate());
        return wzchInternalAdjustMapper.updateWzchInternalAdjust(wzchInternalAdjust);
    }

    /**
     * 删除内部调剂材料策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchInternalAdjustByIds(String ids) {
        detailService.deleteByAdjustIds(ids);
        return wzchInternalAdjustMapper.deleteWzchInternalAdjustByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部调剂材料策划信息
     *
     * @param id 内部调剂材料策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchInternalAdjustById(Long id) {
        return wzchInternalAdjustMapper.deleteWzchInternalAdjustById(id);
    }

    /**
     * 查询 详情
     *
     * @param dto
     * @return
     */
    @Override
    public WzchInternalAdjustDTO baseInfo(WzchInternalAdjustDTO dto) {
        BigDecimal version = VersionUtil.getVersion("wzch_internal_adjust", dto.getVersion());
        dto.setVersion(ObjectUtils.nvlBigDecimal(dto.getVersion(),version));
        dto.setStageIdentity(qqchReviewService.getStage());
        
        List<WzchInternalAdjust> list = this.wzchInternalAdjustMapper.selectWzchInternalAdjustList(new WzchInternalAdjustDTO(version));
        if(CollectionUtils.isEmpty(list)){
            dto.setDetailList(new ArrayList<>());
            return dto;
        }
        WzchInternalAdjustDTO busData = new WzchInternalAdjustDTO();
        BeanUtils.copyProperties(list.get(0), dto);

        dto.setStageIdentity(qqchReviewService.getStage());
        
        WzchInternalAdjust lastVersionData = list.get(0);
        // 如果不为空 就给前端数据进行赋值
        if (lastVersionData != null) {
            WzchInternalAdjustDetail detail = new WzchInternalAdjustDetail();
            detail.setInternalAdjustId(dto.getId());
            detail.setDelFlag("0");
            //获取来源策划版本，否则关联来源策划会出多条数据
            BigDecimal sourceVersion = wzchInternalAdjustDetailMapper.selectWzchSourceVersion(dto.getVersion());
            detail.setVersion(ObjectUtils.nvlBigDecimal(sourceVersion,dto.getVersion()));
            List<WzchInternalAdjustDetail> detailList = detailService.selectWzchInternalAdjustDetailList(detail);
            HashMap<String, String> dictMap = new HashMap<>();
            dictMap.put("materialStandard_materialStandardName", "material_standard");
            dictMap.put("currency_currencyName", "currency");
            dictMap.put("categoryName_categoryNameName", "total_demand_category_name");
            detailList = wzchCommonService.setDicValue(detailList, dictMap);

            dto.setDetailList(detailList);
        }
        return dto;
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @Override
    public long insert(WzchInternalAdjustDTO dto) {
        Long adjustId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchInternalAdjustDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = dto.getProjectName() + "-" + "内部调剂材料策划";
        dto.setTitle(title);
        // 设置主键
        dto.setId(adjustId);
        // 设置单据编码
        dto.setAdjustCode(genCodeService.getSetCode(CodeEnum.WPS));
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);

        // 新增
        int i = this.wzchInternalAdjustMapper.insertWzchInternalAdjust(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, adjustId);
        // 返回主键
        return adjustId;
    }

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    @Override
    public long edit(WzchInternalAdjustDTO dto) {
        Long id = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否能被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, id);
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, id, dto.getActCode(), "adjust_code");
        // 获取前端传入的物资明细
        List<WzchInternalAdjustDetail> detailList = dto.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
//        EntityUtils.setUpdateInfo(dto);
        // 编辑
        int i = this.wzchInternalAdjustMapper.updateWzchInternalAdjust(dto);
        if (i != 1) throw new RuntimeException("编辑失败");
        // 明细
        this.detailService.insertOrUpdateBatch(detailList, id);
        return id;
    }

    /**
     * 调整
     *
     * @param dto
     * @return
     */
    @Override
    public long adjust(WzchInternalAdjustDTO dto) {

        Long dtoId = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否能被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, dtoId);
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, dtoId, dto.getActCode(), "adjust_code");
        // 校验能被调整
//        wzchCommonService.canAdjust(dtoId, TABLE_NAME);

        // 获取前端传入的物资明细
        List<WzchInternalAdjustDetail> detailList = dto.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchInternalAdjustDTO wzchPriorPurchase = (WzchInternalAdjustDTO) this.selectWzchInternalAdjustById(dtoId);
        Assert.notNull(wzchPriorPurchase, "调整失败, 没有查询到要调整的单据");
        Long nId = IdWorker.createId();
        // 设置id
        dto.setId(nId);
        // 设置编码
        dto.setAdjustCode(wzchPriorPurchase.getActCode());
        // 设置版本号
        dto.setVersionCode(wzchPriorPurchase.getVersionCode().add(BigDecimal.ONE));

        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(dto);
        dto.setValid("0");
        // 新增或者编辑
        this.wzchInternalAdjustMapper.insertWzchInternalAdjust(dto);
        // 明细
        detailService.insertOrUpdateBatch(detailList, nId);

        return nId;
    }

    @Override
    public int updateValidStatus(String busId) {
        WzchInternalAdjust wzchInternalAdjust = this.selectWzchInternalAdjustById(Long.valueOf(busId));
        Long projectId = wzchInternalAdjust.getProjectId();


//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_internal_adjust_detail", projectId);

        wzchInternalAdjustMapper.updateDetailValidStatus(busId);
        return wzchInternalAdjustMapper.updateValidStatus(busId);
    }

    @Override
    public void setAdjustProjectIds(List<WzchInternalAdjustDetail> dtoList) {
        // 获取项目名称集合
        String projectNames = dtoList.stream().map(WzchInternalAdjustDetail::getAdjustProjectName).distinct().collect(Collectors.joining(","));
//        ProjectInfo projectInfo = new ProjectInfo();
//        Map<String, Object> where = new HashMap<>();
//        where.put("projectNames", projectNames);
//        projectInfo.setParams(where);
//        List<ProjectInfo> projectInfos = projectInfoService.selectProjectInfoList(projectInfo);
//        StringBuilder errorMsg = new StringBuilder();
//        for (WzchInternalAdjustDetail detail : dtoList) {
//            // 根据项目名称筛选并设置调拨项目id
//            projectInfos.stream().filter(item -> item.getProjectName() != null && item.getProjectName().equals(detail.getAdjustProjectName()))
//                    .findFirst().ifPresent(d -> detail.setAdjustProjectId(d.getId()));
//            if (detail.getAdjustProjectId() == null && !StringUtils.isEmpty(detail.getAdjustProjectName())) {
//                errorMsg.append("项目：").append(detail.getAdjustProjectName()).append(" 不存在\n");
//            }
//        }
//        // 异常信息不为空 抛出异常
//        if (!StringUtils.isEmpty(errorMsg.toString())) throw new RuntimeException(errorMsg.toString());
    }

//    @Override
//    @Transactional
//    public long sync(WzchInternalAdjustDTO dto) {
//        Assert.notNull(dto.getVersion(),"version不能为空");
//        List<WzchInternalAdjust> masterList = this.wzchInternalAdjustMapper.selectWzchInternalAdjustList(dto);
//        boolean isNew = CollectionUtils.isEmpty(masterList);
//        if(isNew){
//            dto.setId(IdWorker.createId());
//            new AddBaseInfoUtil().addBaseEntity(dto);
//            this.wzchInternalAdjustMapper.insertWzchInternalAdjust(dto);
//        }else{
//            masterList.get(0).setLimitPriceDesc(dto.getLimitPriceDesc());
//            dto.setId(masterList.get(0).getId());
//            wzchInternalAdjustMapper.updateWzchInternalAdjust(masterList.get(0));
//        }
//        //1、从来源策划中获取来源为当地采购的数据
//        WzchInternalAdjustDetail queryDetail = new WzchInternalAdjustDetail();
//        queryDetail.setVersion(dto.getVersion());
//        List<WzchInternalAdjustDetail> list = this.detailService.getMtlDetailList(queryDetail);
//        //2、删除
//        if(!isNew){
//            wzchInternalAdjustMapper.deleteDirectByMasterId(dto.getId());
//        }
////        //3、插入明细
//        if(CollectionUtils.isNotEmpty(list)){
////            for (int i = 0; i < list.size(); i++) {
////                WzchLocalPurchaseSupplyDetailDTO temp = list.get(i);
////                temp.setPurchaseSupplyId(purchaseSupply.getId());
////                temp.setDelFlag("0");
////            }
//            this.detailService.insertOrUpdateBatch(list, dto.getId());
//        }
//        return dto.getId();
//    }

    @Override
    @Transactional
    public long save(WzchInternalAdjustDTO dto) {
        // 获取前端传入的物资明细
        List<WzchInternalAdjustDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        if(dto.getId() == null){
            dto.setTitle("");
            // 设置单据编码
            dto.setAdjustCode("");
            // 设置版本号码
            dto.setVersionCode(new BigDecimal("1.0"));
            // 是否生效 新增不能生效
            dto.setValid("0");
            // 设置新增信息
            EntityUtils.setCreateUpdateInfo(dto);
            dto.setId(IdWorker.createId());
            // 新增
            int i = this.wzchInternalAdjustMapper.insertWzchInternalAdjust(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(dto);
            wzchInternalAdjustMapper.updateWzchInternalAdjust(dto);
        }
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, dto.getId());
        // 返回主键
        return dto.getId();
    }
}
