package com.hhwy.pm.qqch.wzch.puchasesupply.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.mapper.WzchPurchaseSupplyDetailMapper;
import com.hhwy.pm.qqch.wzch.puchasesupply.mapper.WzchPurchaseSupplyMapper;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyDetailService;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.BeanValidationResult;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 采购供应策划Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchPurchaseSupplyServiceImpl implements IWzchPurchaseSupplyService {
    @Resource
    private WzchPurchaseSupplyMapper wzchPurchaseSupplyMapper;

    @Resource
    private WzchPurchaseSupplyDetailMapper wzchPurchaseSupplyDetailMapper;

    @Resource
    private IWzchPurchaseSupplyDetailService detailService;

    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private GenCodeService genCodeService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;



    /**
     * 业务表名称
     */
    private final static String TABLE_NAME = FlowEnum.WPS.getTableName();

    /**
     * 查询采购供应策划
     *
     * @param id 采购供应策划ID
     * @return 采购供应策划
     */
    @Override
    public WzchPurchaseSupply selectWzchPurchaseSupplyById(Long id) {
        return wzchPurchaseSupplyMapper.selectWzchPurchaseSupplyById(id);
    }

    /**
     * 查询采购供应策划列表
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 采购供应策划
     */
    @Override
//    @CustomDatascope(alias = "wps")
    public List<WzchPurchaseSupply> selectWzchPurchaseSupplyList(WzchPurchaseSupply wzchPurchaseSupply) {
        return wzchPurchaseSupplyMapper.selectWzchPurchaseSupplyList(wzchPurchaseSupply);
    }

    /**
     * 新增采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    @Override
    public int insertWzchPurchaseSupply(WzchPurchaseSupply wzchPurchaseSupply) {

        wzchPurchaseSupply.setId(IdWorker.createId());

        wzchPurchaseSupply.setCreateTime(DateUtils.getNowDate());

        return wzchPurchaseSupplyMapper.insertWzchPurchaseSupply(wzchPurchaseSupply);
    }

    /**
     * 修改采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    @Override
    public int updateWzchPurchaseSupply(WzchPurchaseSupply wzchPurchaseSupply) {
        wzchPurchaseSupply.setUpdateTime(DateUtils.getNowDate());
        return wzchPurchaseSupplyMapper.updateWzchPurchaseSupply(wzchPurchaseSupply);
    }

    /**
     * 删除采购供应策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWzchPurchaseSupplyByIds(String ids) {
        detailService.deleteBySupplyIds(ids);
        return wzchPurchaseSupplyMapper.deleteWzchPurchaseSupplyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购供应策划信息
     *
     * @param id 采购供应策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchPurchaseSupplyById(Long id) {
        return wzchPurchaseSupplyMapper.deleteWzchPurchaseSupplyById(id);
    }

    /**
     * 新增 编辑 详情数据回显
     *
     * @param vo
     * @return
     */
    @Override
    public WzchPurchaseSupplyDTO baseInfo(WzchPurchaseSupplyDTO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_purchase_supply", vo.getVersion());
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        List<WzchPurchaseSupply> list = this.wzchPurchaseSupplyMapper.selectWzchPurchaseSupplyList(new WzchPurchaseSupply(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0), vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        WzchPurchaseSupplyDetail detail = new WzchPurchaseSupplyDetail();
        detail.setPurchaseSupplyId(vo.getId());
        detail.setDelFlag("0");
        List<WzchPurchaseSupplyDetailDTO> detailList = detailService.selectWzchPurchaseSupplyDetailList(detail);
        // 获取物资code
        List<String> materialCodeList = detailList.stream().map(WzchPurchaseSupplyDetailDTO::getMaterialCode).distinct().collect(Collectors.toList());

        // 查询来源策划 查询采购来源
        if (CollectionUtils.isNotEmpty(materialCodeList)) {
            List<WzchPurchaseSupplyDetailDTO> purchaseSourceList = wzchPurchaseSupplyDetailMapper.selectPurchaseSource(materialCodeList);

            long detailId = 0;
            // 处理物资的采购来源
            for (WzchPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : detailList) {
                // 生成id 前端会用到
                wzchPurchaseSupplyDetailDTO.setPurchaseSupplyDetailId(detailId++);
                String materialCode = wzchPurchaseSupplyDetailDTO.getMaterialCode();
                String materialStandard = wzchPurchaseSupplyDetailDTO.getMaterialStandard();
                // 来源
                StringBuilder source = new StringBuilder("");
                StringBuilder sourceName = new StringBuilder("");
                List<Map<String, String>> sourceMapList = new ArrayList<>();
                // 筛选出数据
                purchaseSourceList.stream().filter(
                        item -> StringUtils.isNotEmpty(item.getMaterialCode())
                                && StringUtils.isNotEmpty(item.getMaterialStandard())
                                && item.getMaterialCode().equals(materialCode)
                                && item.getMaterialStandard().equals(materialStandard)
                ).findFirst().ifPresent(purchaseSource -> {
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
                List<WzchPurchaseSupplyDetailDTO> children = wzchPurchaseSupplyDetailDTO.getChildren();
                for (WzchPurchaseSupplyDetailDTO child : children) {
                    child.setPlanTime(DateUtils.parseDate(child.getPtVar1()));
                    child.setSourceMap(sourceMapList);
                }

                wzchPurchaseSupplyDetailDTO.setSourceMap(sourceMapList);
                wzchPurchaseSupplyDetailDTO.setSource(source.toString());
                wzchPurchaseSupplyDetailDTO.setSourceName("-");
            }
        }
        vo.setDetailList(detailList);
        return vo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insert(WzchPurchaseSupplyDTO dto) {
        Long supplyId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = "";
        String code = "";
        if (dto.getParams() != null && TWO.equals(dto.getParams().get("dataType"))) {
            title = dto.getProjectName() + "-" + "属地化采购供应策划";
//            code = genCodeService.getSetCode(CodeEnum.WLPS);
        } else {
//            code = genCodeService.getSetCode(CodeEnum.WPS);
            title = dto.getProjectName() + "-" + "采购供应策划";
        }
        dto.setTitle(title);
        // 设置主键
        dto.setId(supplyId);
        // 设置单据编码
        dto.setSupplyCode(code);
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);

        // 新增
        int i = this.wzchPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, supplyId);
        // 返回主键
        return supplyId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long edit(WzchPurchaseSupplyDTO dto) {
        Long supplyId = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, supplyId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, supplyId,dto.getActCode(),"supply_code");
        // 获取前端传入的物资明细
        List<WzchPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        // 校验
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(dto);

        // 编辑
        int i = this.wzchPurchaseSupplyMapper.updateWzchPurchaseSupply(dto);
        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, supplyId);
        // 返回主键
        return supplyId;
    }

    @Override
    @Transactional
    public Long save(WzchPurchaseSupplyDTO dto) {
        // 获取前端传入的物资明细
        List<WzchPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = "";
        String code = "";
        if (dto.getParams() != null && TWO.equals(dto.getParams().get("dataType"))) {
            title = dto.getProjectName() + "-" + "属地化采购供应策划";
//            code = genCodeService.getSetCode(CodeEnum.WLPS);
        } else {
//            code = genCodeService.getSetCode(CodeEnum.WPS);
            title = dto.getProjectName() + "-" + "采购供应策划";
        }
        dto.setTitle(title);
        // 设置主键
//        dto.setId(supplyId);
        // 设置单据编码
        dto.setSupplyCode(code);
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);
        if(dto.getId()==null){
            dto.setId(IdWorker.createId());
            int i = this.wzchPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            wzchPurchaseSupplyMapper.updateWzchPurchaseSupply(dto);
        }
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, dto.getId());
        // 返回主键
        if (ButtonMark.CONFIRM.equals(dto.getButtonMark())) {
            // 插入确认状态
            String menuId = dto.getMenuId();
            String stageIdentity = dto.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return dto.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long adjust(WzchPurchaseSupplyDTO dto) {
        // 主键id
        Long oldId = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId,dto.getActCode(),"supply_code");
        // 校验是否能被调整
        wzchCommonService.canAdjust(oldId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        // 校验
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchPurchaseSupplyDTO oldVersionData = (WzchPurchaseSupplyDTO) this.selectWzchPurchaseSupplyById(oldId);
        Assert.notNull(oldVersionData, "调整失败, 没有查询到要调整的单据");
        // 新版本的数据id
        Long nSupplyId = IdWorker.createId();
        // 设置id
        dto.setId(nSupplyId);
        // 设置编码
        dto.setSupplyCode(oldVersionData.getActCode());
        // 设置版本号
        dto.setVersionCode(oldVersionData.getVersionCode().add(BigDecimal.ONE));
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(dto);
        dto.setValid("0");
        // 新增
        int i = this.wzchPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);

        if (i != 1) throw new RuntimeException("调整异常");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, nSupplyId);


        return nSupplyId;
    }

    @Override
    @Transactional
    public void sync(WzchPurchaseSupply purchaseSupply) {
        List<WzchPurchaseSupply> masterList = this.wzchPurchaseSupplyMapper.selectWzchPurchaseSupplyList(new WzchPurchaseSupply(purchaseSupply.getVersion()));
        boolean isNew = CollectionUtils.isEmpty(masterList);
        if(isNew){
            purchaseSupply.setId(IdWorker.createId());
            new AddBaseInfoUtil().addBaseEntity(purchaseSupply);
            this.wzchPurchaseSupplyMapper.insertWzchPurchaseSupply(purchaseSupply);
        }else{
            masterList.get(0).setLimitPriceDesc(purchaseSupply.getLimitPriceDesc());
            purchaseSupply.setId(masterList.get(0).getId());
            wzchPurchaseSupplyMapper.updateWzchPurchaseSupply(masterList.get(0));
        }                             
        //1、从来源策划中获取来源为国内采购、第三国采购、当地采购的数据
        List<WzchPurchaseSupplyDetailDTO> list = detailService.getListByPrjId(new WzchPurchaseSupplyDetailDTO());
        //2、删除
        if(!isNew){
            this.wzchPurchaseSupplyMapper.deleteDirectByMasterId(purchaseSupply.getId());
            this.wzchPurchaseSupplyMapper.deleteBatchDirectByMasterId(purchaseSupply.getId());
        }
        //3、插入明细
        if(CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                WzchPurchaseSupplyDetailDTO temp = list.get(i);
                temp.setPurchaseSupplyId(purchaseSupply.getId());
                temp.setDelFlag("0");
            }
            this.wzchPurchaseSupplyDetailMapper.insertOrUpdateBatch(list);
        }
            
    }

    /**
     * 根据物资类型和拟采购时间查询物资信息
     * <p>
     * categoryName          - 类型;
     * projectId             - 项目id;
     * batch                 - 批次;
     * planPurchaseDateStart - 拟采购开始时间
     * planPurchaseDateEnd   - 拟采购结束时间
     *
     * @return materialCode - 物资编码
     * materialTechParam - 技术参数
     * materialStandard - 执行标准
     * totalDemandAmount - 总需用量
     * planPurchaseDate - 拟采购时间
     * batch - 批次
     */
    @Override
    public List<WzchPurchaseSupplyDetail> getMaterialsByTypeAndPlanTime(WzchPurchaseSupplyDetailDTO dto) {
        BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(dto, ValidationGroups.Other.class);
        // 校验失败 抛异常
        if (!beanValidationResult.isSuccess()) {
            String errorMsg = beanValidationResult.getErrorMessages().stream().map(BeanValidationResult.ErrorMessage::getMessage).collect(Collectors.joining(","));
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, errorMsg);
        }
        // 开启分页
//        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        // 查询数据
        List<WzchPurchaseSupplyDetail> list = this.wzchPurchaseSupplyMapper.getMaterialsByTypeAndPlanTime(dto);
        return list;
    }

    @Override
    public int updateValidStatus(String id) {
        Long idl = Long.valueOf(id);
        WzchPurchaseSupply wzchPurchaseSupply = selectWzchPurchaseSupplyById(idl);
        Long projectId = wzchPurchaseSupply.getProjectId();

//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_purchase_supply_detail", projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_purchase_supply_batch_detail", projectId);
//
//        wzchCommonService.updateValidStatus(TABLE_NAME, "id", idl);
//        wzchCommonService.updateValidStatus("wzch_purchase_supply_detail", "purchase_supply_id", idl);
//        wzchCommonService.updateValidStatus("wzch_purchase_supply_batch_detail", "purchase_supply_id", idl);


        return 1;
    }
}
