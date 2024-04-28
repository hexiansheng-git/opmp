package com.hhwy.pm.qqch.wzch.revolverent.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRent;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDTO;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDetailDTO;
import com.hhwy.pm.qqch.wzch.revolverent.mapper.WzchRevolveRentMapper;
import com.hhwy.pm.qqch.wzch.revolverent.service.IWzchRevolveRentDetailService;
import com.hhwy.pm.qqch.wzch.revolverent.service.IWzchRevolveRentService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
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

/**
 * 周转材租赁策划Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Service
public class WzchRevolveRentServiceImpl implements IWzchRevolveRentService {
    @Resource
    private WzchRevolveRentMapper wzchRevolveRentMapper;
    @Resource
    private IWzchRevolveRentDetailService detailService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private GenCodeService genCodeService;


    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";

    /**
     * 业务表名称
     */
    private final static String TABLE_NAME = FlowEnum.WRR.getTableName();

    /**
     * 查询周转材租赁策划
     *
     * @param id 周转材租赁策划ID
     * @return 周转材租赁策划
     */
    @Override
    public WzchRevolveRent selectWzchRevolveRentById(Long id) {
        return wzchRevolveRentMapper.selectWzchRevolveRentById(id);
    }

    /**
     * 查询周转材租赁策划列表
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 周转材租赁策划
     */
    @Override
//    @CustomDatascope(alias = "rr")
    public List<WzchRevolveRent> selectWzchRevolveRentList(WzchRevolveRent wzchRevolveRent) {
        return wzchRevolveRentMapper.selectWzchRevolveRentList(wzchRevolveRent);
    }

    /**
     * 新增周转材租赁策划
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 结果
     */
    @Override
    public int insertWzchRevolveRent(WzchRevolveRent wzchRevolveRent) {

        wzchRevolveRent.setId(IdWorker.createId());

        wzchRevolveRent.setCreateTime(DateUtils.getNowDate());

        return wzchRevolveRentMapper.insertWzchRevolveRent(wzchRevolveRent);
    }

    /**
     * 修改周转材租赁策划
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 结果
     */
    @Override
    public int updateWzchRevolveRent(WzchRevolveRent wzchRevolveRent) {
        wzchRevolveRent.setUpdateTime(DateUtils.getNowDate());
        return wzchRevolveRentMapper.updateWzchRevolveRent(wzchRevolveRent);
    }

    /**
     * 删除周转材租赁策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWzchRevolveRentByIds(String ids) {
        detailService.deleteByRentIds(ids);
        return wzchRevolveRentMapper.deleteWzchRevolveRentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周转材租赁策划信息
     *
     * @param id 周转材租赁策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchRevolveRentById(Long id) {
        return wzchRevolveRentMapper.deleteWzchRevolveRentById(id);
    }


    /**
     * 新增 编辑 详情数据回显
     *
     * @param vo
     * @return
     */
    @Override
    public WzchRevolveRentDTO baseInfo(WzchRevolveRentDTO vo){
        BigDecimal version = VersionUtil.getVersion("wzch_revolve_rent", vo.getVersion());
        vo.setVersion(ObjectUtils.nvlBigDecimal(vo.getVersion(),version));
        vo.setStageIdentity(qqchReviewService.getStage());

        List<WzchRevolveRent> list = wzchRevolveRentMapper.selectWzchRevolveRentList(new WzchRevolveRent(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0),vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setVersion(ObjectUtils.nvlBigDecimal(vo.getVersion(),version));
        WzchRevolveRentDetail detail = new WzchRevolveRentDetail();
        detail.setRevolveRentId(vo.getId());
        detail.setDelFlag("0");
        List<WzchRevolveRentDetailDTO> detailList = detailService.selectWzchRevolveRentDetailList(detail);

        HashMap<String, String> dicMap = new HashMap<>(3);
        dicMap.put("materialStandard_materialStandardName", "material_standard");
        dicMap.put("categoryName_categoryNameName", "total_demand_category_name");
        dicMap.put("currency_currencyName", "remittance_currency_type");
        detailList = wzchCommonService.setDicValue(detailList, dicMap);
        vo.setDetailList(detailList);
        return vo;
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long insert(WzchRevolveRentDTO dto) {
        Long rentId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchRevolveRentDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = dto.getProjectName() + "-" + "周转材租赁策划";
        dto.setTitle(title);
        // 设置主键
        dto.setId(rentId);
        // 设置单据编码
        dto.setRentCode(genCodeService.getSetCode(CodeEnum.WRR));
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);


        int i = this.wzchRevolveRentMapper.insertWzchRevolveRent(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, rentId);

        return rentId;
    }

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long edit(WzchRevolveRentDTO dto) {
        Long rentId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, rentId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, rentId,dto.getActCode(),"rent_code");
        // 获取前端传入的物资明细
        List<WzchRevolveRentDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(dto);
        // 编辑
        int i = this.wzchRevolveRentMapper.updateWzchRevolveRent(dto);

        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, rentId);
        return rentId;
    }

    /**
     * 调整
     *
     * @param dto
     * @return
     */
    @Override
    public long adjust(WzchRevolveRentDTO dto) {
        // 主键id
        Long oldId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId);
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId,dto.getActCode(),"rent_code");
        // 校验是否能被调整
        this.wzchCommonService.canAdjust(oldId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchRevolveRentDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchRevolveRentDTO oldVersionData = (WzchRevolveRentDTO) this.selectWzchRevolveRentById(oldId);
        Assert.notNull(oldVersionData, "调整失败, 没有查询到要调整的单据");
        // 新版本的数据id
        Long nRentId = IdWorker.createId();
        // 设置id
        dto.setId(nRentId);
        // 设置编码
        dto.setRentCode(oldVersionData.getActCode());
        // 设置版本号
        dto.setVersionCode(oldVersionData.getVersionCode().add(BigDecimal.ONE));
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(dto);
        dto.setValid("0");
        int i = this.wzchRevolveRentMapper.insertWzchRevolveRent(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new RuntimeException("调整异常");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, nRentId);

        return nRentId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long save(WzchRevolveRentDTO dto) {
        // 获取前端传入的物资明细
        List<WzchRevolveRentDetailDTO> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        dto.setRentCode(genCodeService.getSetCode(CodeEnum.WRR));
        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        dto.setTitle(tenantName+"-"+"周转材租赁策划");
        dto.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        // 设置新增信息
        if(dto.getId()==null){
            new AddBaseInfoUtil<>().addBaseEntity(dto);
            dto.setId(IdWorker.createId());

            int i = this.wzchRevolveRentMapper.insertWzchRevolveRent(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(dto);
            wzchRevolveRentMapper.updateWzchRevolveRent(dto);
        }
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, dto.getId());
        if (ButtonMark.CONFIRM.equals(dto.getButtonMark())) {
            // 插入确认状态
            String menuId = dto.getMenuId();
            String stageIdentity = dto.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return dto.getId();
    }

    @Override
    @Transactional
    public void sync(WzchRevolveRentDTO wzchRevolveRent) {
        wzchRevolveRent.setLimitPriceDesc(StringUtils.equals("null",wzchRevolveRent.getLimitPriceDesc())?"":wzchRevolveRent.getLimitPriceDesc());
        List<WzchRevolveRent> masterList = this.wzchRevolveRentMapper.selectWzchRevolveRentList(wzchRevolveRent);
        boolean isNew = CollectionUtils.isEmpty(masterList);
        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        if(isNew){
            wzchRevolveRent.setId(IdWorker.createId());
            wzchRevolveRent.setRentCode(genCodeService.getSetCode(CodeEnum.WRR));
            wzchRevolveRent.setTitle("");
            new AddBaseInfoUtil().addBaseEntity(wzchRevolveRent);
            wzchRevolveRent.setTitle(tenantName+"-"+"周转材租赁策划");
            this.wzchRevolveRentMapper.insertWzchRevolveRent(wzchRevolveRent);
        }else{
            masterList.get(0).setLimitPriceDesc(wzchRevolveRent.getLimitPriceDesc());
            wzchRevolveRent.setId(masterList.get(0).getId());
            masterList.get(0).setTitle(tenantName+"-"+"周转材租赁策划");
            wzchRevolveRentMapper.updateWzchRevolveRent(masterList.get(0));
        }
        //1、从来源策划中获取来源为当地采购的数据
        List<WzchRevolveRentDetailDTO> list = detailService.getMtlDetailList(new WzchRevolveRentDetailDTO());
        //2、删除
        if(!isNew){
            this.wzchRevolveRentMapper.deleteDirectByMasterId(wzchRevolveRent.getId());
        }
        //3、插入明细
        if(CollectionUtils.isNotEmpty(list)){
//            for (int i = 0; i < list.size(); i++) {
//                WzchLocalPurchaseSupplyDetailDTO temp = list.get(i);
//                temp.setPurchaseSupplyId(purchaseSupply.getId());
//                temp.setDelFlag("0");
//            }
            this.detailService.insertOrUpdateBatch(list, wzchRevolveRent.getId());
        }
        if (ButtonMark.CONFIRM.equals(wzchRevolveRent.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchRevolveRent.getMenuId();
            String stageIdentity = wzchRevolveRent.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateValidStatus( String busId) {
        Long id = Long.parseLong(busId);
        WzchRevolveRent wzchRevolveRent = selectWzchRevolveRentById(id);
        Long projectId = wzchRevolveRent.getProjectId();
//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_revolve_rent_detail", projectId);
//        wzchCommonService.updateValidStatus(TABLE_NAME, "id", id);
//        wzchCommonService.updateValidStatus("wzch_revolve_rent_detail", "revolve_rent_id", id);
        return 1;
    }
}
