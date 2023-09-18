package com.hhwy.pm.qqch.wzch.localpuchasesupply.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.mapper.WzchLocalPurchaseSupplyDetailMapper;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.mapper.WzchLocalPurchaseSupplyMapper;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.service.IWzchLocalPurchaseSupplyDetailService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.service.IWzchLocalPurchaseSupplyService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 采购供应策划Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchLocalPurchaseSupplyServiceImpl implements IWzchLocalPurchaseSupplyService {
    @Resource
    private WzchLocalPurchaseSupplyMapper localPurchaseSupplyMapper;

    @Resource
    private WzchLocalPurchaseSupplyDetailMapper localPurchaseSupplyDetailMapper;

    @Resource
    private IWzchLocalPurchaseSupplyDetailService detailService;

    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private GenCodeService genCodeService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";
    /**
     * 业务表名称
     */
    private final static String TABLE_NAME = FlowEnum.WLPS.getTableName();

    /**
     * 查询采购供应策划
     *
     * @param id 采购供应策划ID
     * @return 采购供应策划
     */
    @Override
    public WzchLocalPurchaseSupply selectWzchPurchaseSupplyById(Long id) {
        return localPurchaseSupplyMapper.selectWzchPurchaseSupplyById(id);
    }

    /**
     * 查询采购供应策划列表
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 采购供应策划
     */
    @Override
//    @CustomDatascope(alias = "wps")
    public List<WzchLocalPurchaseSupply> selectWzchPurchaseSupplyList(WzchLocalPurchaseSupply wzchPurchaseSupply) {
        return localPurchaseSupplyMapper.selectWzchPurchaseSupplyList(wzchPurchaseSupply);
    }

    /**
     * 新增采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    @Override
    public int insertWzchPurchaseSupply(WzchLocalPurchaseSupply wzchPurchaseSupply) {

        wzchPurchaseSupply.setId(IdWorker.createId());

//        wzchPurchaseSupply.setCreateTime(DateUtils.getNowDate());

        return localPurchaseSupplyMapper.insertWzchPurchaseSupply(wzchPurchaseSupply);
    }

    /**
     * 修改采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    @Override
    public int updateWzchPurchaseSupply(WzchLocalPurchaseSupply wzchPurchaseSupply) {
        wzchPurchaseSupply.setUpdateTime(DateUtils.getNowDate());
        return localPurchaseSupplyMapper.updateWzchPurchaseSupply(wzchPurchaseSupply);
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
        return localPurchaseSupplyMapper.deleteWzchPurchaseSupplyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购供应策划信息
     *
     * @param id 采购供应策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchPurchaseSupplyById(Long id) {
        return localPurchaseSupplyMapper.deleteWzchPurchaseSupplyById(id);
    }

    /**
     * 新增 编辑 详情数据回显
     *
     * @param vo
     * @return
     */
    @Override
    public WzchLocalPurchaseSupplyDTO baseInfo(WzchLocalPurchaseSupplyDTO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_local_purchase_supply", vo.getVersion());
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());

        
        List<WzchLocalPurchaseSupply> list = localPurchaseSupplyMapper.selectWzchPurchaseSupplyList(new WzchLocalPurchaseSupply(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            return vo;
        }
        WzchLocalPurchaseSupply lastVersionData = list.get(0);
        BeanUtils.copyProperties(lastVersionData, vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        WzchLocalPurchaseSupplyDetail detail = new WzchLocalPurchaseSupplyDetail();
        detail.setPurchaseSupplyId(vo.getId());
        detail.setDelFlag("0");
        List<WzchLocalPurchaseSupplyDetailDTO> detailList = detailService.selectWzchPurchaseSupplyDetailList(detail);

        // 获取物资code
        List<String> materialCodeList = detailList.stream().map(WzchLocalPurchaseSupplyDetailDTO::getMaterialCode).distinct().collect(Collectors.toList());

        // 查询来源策划 查询采购来源
        List<WzchLocalPurchaseSupplyDetailDTO> purchaseSourceList = localPurchaseSupplyDetailMapper.selectPurchaseSource(materialCodeList);

        int idSer = 0;
        // 处理物资的采购来源
        for (WzchLocalPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : detailList) {
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
            List<WzchLocalPurchaseSupplyDetailDTO> children = wzchPurchaseSupplyDetailDTO.getChildren();
            for (WzchLocalPurchaseSupplyDetailDTO child : children) {
                child.setPlanTime(DateUtils.parseDate(child.getPtVar1()));
                child.setSourceMap(sourceMapList);
            }

            wzchPurchaseSupplyDetailDTO.setSourceMap(sourceMapList);
            wzchPurchaseSupplyDetailDTO.setSource(source.toString());
            wzchPurchaseSupplyDetailDTO.setSourceName("-");
        }

        vo.setDetailList(detailList);
        return vo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insert(WzchLocalPurchaseSupplyDTO dto) {
        Long supplyId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchLocalPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = dto.getProjectName() + "-" + "属地化采购供应策划";
        dto.setTitle(title);
        // 设置主键
        dto.setId(supplyId);
        // 设置单据编码
//        dto.setSupplyCode(genCodeService.getSetCode(CodeEnum.WLPS));
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);

        // 新增
        int i = this.localPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, supplyId,false);
        // 返回主键
        return supplyId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long edit(WzchLocalPurchaseSupplyDTO dto) {
        Long supplyId = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, supplyId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, supplyId, dto.getActCode(), "supply_code");
        // 获取前端传入的物资明细
        List<WzchLocalPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        // 校验
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(dto);

        // 编辑
        int i = this.localPurchaseSupplyMapper.updateWzchPurchaseSupply(dto);
        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, supplyId,false);
        // 返回主键
        return supplyId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long adjust(WzchLocalPurchaseSupplyDTO dto) {
        // 主键id
        Long oldId = dto.getId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId, dto.getActCode(), "supply_code");
        // 校验是否能被调整
        wzchCommonService.canAdjust(oldId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchLocalPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        // 校验
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchLocalPurchaseSupplyDTO oldVersionData = (WzchLocalPurchaseSupplyDTO) this.selectWzchPurchaseSupplyById(oldId);
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
        int i = this.localPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);

        if (i != 1) throw new RuntimeException("调整异常");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, nSupplyId,false);


        return nSupplyId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(WzchLocalPurchaseSupplyDTO dto) {
        // 获取前端传入的物资明细
        List<WzchLocalPurchaseSupplyDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        dto.setTitle("");
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);
        if(dto.getId()==null){
            dto.setId(IdWorker.createId());
            int i = this.localPurchaseSupplyMapper.insertWzchPurchaseSupply(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            localPurchaseSupplyMapper.updateWzchPurchaseSupply(dto);
        }
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, dto.getId(),false);
        if (ButtonMark.CONFIRM.equals(dto.getButtonMark())) {
            // 插入确认状态
            String menuId = dto.getMenuId();
            String stageIdentity = dto.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        // 返回主键
        return dto.getId();
    }

    @Override
    @Transactional
    public void sync(WzchLocalPurchaseSupply purchaseSupply) {
        List<WzchLocalPurchaseSupply> masterList = this.localPurchaseSupplyMapper.selectWzchPurchaseSupplyList(purchaseSupply);
        boolean isNew = CollectionUtils.isEmpty(masterList);
        if(isNew){
            purchaseSupply.setId(IdWorker.createId());
            new AddBaseInfoUtil().addBaseEntity(purchaseSupply);
            this.localPurchaseSupplyMapper.insertWzchPurchaseSupply(purchaseSupply);
        }else{
            masterList.get(0).setLimitPriceDesc(purchaseSupply.getLimitPriceDesc());
            purchaseSupply.setId(masterList.get(0).getId());
            localPurchaseSupplyMapper.updateWzchPurchaseSupply(masterList.get(0));
        }
        //1、从来源策划中获取来源为当地采购的数据
        List<WzchLocalPurchaseSupplyDetailDTO> list =detailService.getListByPrjId(new WzchLocalPurchaseSupplyDetailDTO());
        //2、删除
        if(!isNew){
            this.localPurchaseSupplyMapper.deleteDirectByMasterId(purchaseSupply.getId());
            this.localPurchaseSupplyMapper.deleteBatchDirectByMasterId(purchaseSupply.getId());
        }
        //3、插入明细
        if(CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                WzchLocalPurchaseSupplyDetailDTO temp = list.get(i);
                temp.setPurchaseSupplyId(purchaseSupply.getId());
                temp.setDelFlag("0");
            }
            this.detailService.insertOrUpdateBatch(list, purchaseSupply.getId(),true);
        }
    }

    @Override
    public int updateValidStatus(String businessId) {
        WzchLocalPurchaseSupply wzchLocalPurchaseSupply = selectWzchPurchaseSupplyById(Long.valueOf(businessId));
        Long projectId = wzchLocalPurchaseSupply.getProjectId();

//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_local_purchase_supply_detail", projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_local_purchase_supply_batch_detail", projectId);
//
//        wzchCommonService.updateValidStatus(TABLE_NAME, "id", Long.parseLong(businessId));
//        wzchCommonService.updateValidStatus("wzch_local_purchase_supply_detail", "purchase_supply_id", Long.parseLong(businessId));
//        wzchCommonService.updateValidStatus("wzch_local_purchase_supply_batch_detail", "purchase_supply_id", Long.parseLong(businessId));

        return 1;
    }
}
