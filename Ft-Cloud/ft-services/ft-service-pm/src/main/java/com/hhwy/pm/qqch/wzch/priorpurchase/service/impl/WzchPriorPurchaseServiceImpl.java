package com.hhwy.pm.qqch.wzch.priorpurchase.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchase;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDTO;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDetailDTO;
import com.hhwy.pm.qqch.wzch.priorpurchase.mapper.WzchPriorPurchaseMapper;
import com.hhwy.pm.qqch.wzch.priorpurchase.service.IWzchPriorPurchaseDetailService;
import com.hhwy.pm.qqch.wzch.priorpurchase.service.IWzchPriorPurchaseService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 优先进场物资设备采购策划Service业务层处理
 *
 * @author mls
 * @date 2022-11-17
 */
@Slf4j
@Service
public class WzchPriorPurchaseServiceImpl implements IWzchPriorPurchaseService {
    @Resource
    private WzchPriorPurchaseMapper wzchPriorPurchaseMapper;

    @Resource
    private IWzchPriorPurchaseDetailService detailService;

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
//    private final static String TABLE_NAME = FlowEnum.WZCH_PRIOR_PURCHASE.getTableName();

    /**
     * 查询优先进场物资设备采购策划
     *
     * @param id 优先进场物资设备采购策划ID
     * @return 优先进场物资设备采购策划
     */
    @Override
    public WzchPriorPurchase selectWzchPriorPurchaseById(Long id) {
        return wzchPriorPurchaseMapper.selectWzchPriorPurchaseById(id);
    }

    /**
     * 查询优先进场物资设备采购策划列表
     *
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 优先进场物资设备采购策划
     */
    @Override
//    @CustomDatascope(alias = "wpp")
    public List<WzchPriorPurchase> selectWzchPriorPurchaseList(WzchPriorPurchase wzchPriorPurchase) {
        return wzchPriorPurchaseMapper.selectWzchPriorPurchaseList(wzchPriorPurchase);
    }


    /**
     * 新增优先进场物资设备采购策划
     *
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWzchPriorPurchase(WzchPriorPurchase wzchPriorPurchase) {

        wzchPriorPurchase.setId(IdWorker.createId());

        wzchPriorPurchase.setCreateTime(DateUtils.getNowDate());

        return wzchPriorPurchaseMapper.insertWzchPriorPurchase(wzchPriorPurchase);
    }

    /**
     * 修改优先进场物资设备采购策划
     *
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 结果
     */
    @Override
    public int updateWzchPriorPurchase(WzchPriorPurchase wzchPriorPurchase) {
        wzchPriorPurchase.setUpdateTime(DateUtils.getNowDate());
        return wzchPriorPurchaseMapper.updateWzchPriorPurchase(wzchPriorPurchase);
    }

    /**
     * 删除优先进场物资设备采购策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWzchPriorPurchaseByIds(String ids) {
        detailService.deleteByPriorPurchaseIds(ids);
        return wzchPriorPurchaseMapper.deleteWzchPriorPurchaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优先进场物资设备采购策划信息
     *
     * @param id 优先进场物资设备采购策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchPriorPurchaseById(Long id) {
        return wzchPriorPurchaseMapper.deleteWzchPriorPurchaseById(id);
    }


    /**
     * 新增
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insert(WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        Long id = IdWorker.createId();
        Long projectId = wzchPriorPurchaseDTO.getProjectId();
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchPriorPurchaseDetailDTO> detailList = wzchPriorPurchaseDTO.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        // 设置id
        wzchPriorPurchaseDTO.setId(id);
        // 设置单据编码
//        wzchPriorPurchaseDTO.setPriorPurchaseCode(genCodeService.getSetCode(CodeEnum.WPP));
        // 设置版本号码
        wzchPriorPurchaseDTO.setVersionCode(new BigDecimal("1.0"));
        // 是否生效
        wzchPriorPurchaseDTO.setValid("0");
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(wzchPriorPurchaseDTO);

        String title = wzchPriorPurchaseDTO.getProjectName() + "-" + "优先进场物资设备采购策划";
        wzchPriorPurchaseDTO.setTitle(title);
        // 新增
        int i = this.wzchPriorPurchaseMapper.insertWzchPriorPurchase(wzchPriorPurchaseDTO);
        if (i != 1) throw new RuntimeException("新增失败");
        // 明细
        detailService.insertOrEditBatchByPurchaseId(detailList, id);
        return id;
    }

    /**
     * 编辑
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long edit(WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        Long id = wzchPriorPurchaseDTO.getId();
        Long projectId = wzchPriorPurchaseDTO.getProjectId();

        // 校验项目是否能被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, id);
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, id, wzchPriorPurchaseDTO.getActCode(), "prior_purchase_code");
        // 获取前端传入的物资明细
        List<WzchPriorPurchaseDetailDTO> detailList = wzchPriorPurchaseDTO.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
//        EntityUtils.setUpdateInfo(wzchPriorPurchaseDTO);
        // 编辑
        int i = this.wzchPriorPurchaseMapper.updateWzchPriorPurchase(wzchPriorPurchaseDTO);
        if (i != 1) throw new RuntimeException("编辑失败");
        // 明细
        this.detailService.insertOrEditBatchByPurchaseId(detailList, id);
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        // 获取前端传入的物资明细
        List<WzchPriorPurchaseDetailDTO> detailList = wzchPriorPurchaseDTO.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        // 设置id
//        wzchPriorPurchaseDTO.setId(id);
        // 设置单据编码
        wzchPriorPurchaseDTO.setPriorPurchaseCode(genCodeService.getSetCode(CodeEnum.WPP));
        // 设置版本号码
        wzchPriorPurchaseDTO.setVersionCode(wzchPriorPurchaseDTO.getVersion());
        // 是否生效
        wzchPriorPurchaseDTO.setValid("0");
        wzchPriorPurchaseDTO.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(wzchPriorPurchaseDTO);

        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        wzchPriorPurchaseDTO.setTitle(tenantName+"-"+"优先进场物资设备采购策划");
        // 新增
        if(wzchPriorPurchaseDTO.getId() == null){
            wzchPriorPurchaseDTO.setId(IdWorker.createId());
            int i = this.wzchPriorPurchaseMapper.insertWzchPriorPurchase(wzchPriorPurchaseDTO);
            if (i != 1) throw new RuntimeException("新增失败");
        }else{
            wzchPriorPurchaseMapper.updateWzchPriorPurchase(wzchPriorPurchaseDTO);
        }
        // 明细
        detailService.insertOrEditBatchByPurchaseId(detailList, wzchPriorPurchaseDTO.getId());
        if (ButtonMark.CONFIRM.equals(wzchPriorPurchaseDTO.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchPriorPurchaseDTO.getMenuId();
            String stageIdentity = wzchPriorPurchaseDTO.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return wzchPriorPurchaseDTO.getId();
    }
    
    /**
     * 调整
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long adjust(WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {

        Long dtoId = wzchPriorPurchaseDTO.getId();
        Long projectId = wzchPriorPurchaseDTO.getProjectId();

        // 校验项目是否能被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, dtoId);
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, dtoId, wzchPriorPurchaseDTO.getActCode(), "prior_purchase_code");
        // 校验能被调整
//        wzchCommonService.canAdjust(dtoId, TABLE_NAME);

        // 获取前端传入的物资明细
        List<WzchPriorPurchaseDetailDTO> detailList = wzchPriorPurchaseDTO.getDetailList();
        // 校验详情是否填写
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchPriorPurchaseDTO wzchPriorPurchase = (WzchPriorPurchaseDTO) this.selectWzchPriorPurchaseById(dtoId);
        Assert.notNull(wzchPriorPurchase, "调整失败, 没有查询到要调整的单据");
        Long nId = IdWorker.createId();
        // 设置id
        wzchPriorPurchaseDTO.setId(nId);
        // 设置编码
        wzchPriorPurchaseDTO.setPriorPurchaseCode(wzchPriorPurchase.getActCode());
        // 设置版本号
        wzchPriorPurchaseDTO.setVersionCode(wzchPriorPurchase.getVersionCode().add(BigDecimal.ONE));

        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(wzchPriorPurchaseDTO);
        wzchPriorPurchaseDTO.setValid("0");
        // 新增或者编辑
        this.wzchPriorPurchaseMapper.insertWzchPriorPurchase(wzchPriorPurchaseDTO);
        // 明细
        detailService.insertOrEditBatchByPurchaseId(detailList, nId);

        return nId;
    }


    @Override
    public WzchPriorPurchaseDTO baseInfo(WzchPriorPurchaseDTO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_prior_purchase", vo.getVersion());
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        List<WzchPriorPurchase> list = this.wzchPriorPurchaseMapper.selectWzchPriorPurchaseList(new WzchPriorPurchase(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0), vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        WzchPriorPurchaseDetail detail = new WzchPriorPurchaseDetail();
        detail.setPriorPurchaseId(vo.getId());
        detail.setDelFlag("0");
        List<WzchPriorPurchaseDetailDTO> detailList = detailService.selectWzchPriorPurchaseDetailList(detail);
        Map<String, String> dictMap = new HashMap<>(2);
        dictMap.put("materialStandard_materialStandardName", "material_standard");
        dictMap.put("categoryName_categoryNameName", "total_demand_category_name");
        // dictMap.put("source_sourceName", "wzch_purchase_source");
        detailList = wzchCommonService.setDicValue(detailList, dictMap);

        try {
            for (WzchPriorPurchaseDetailDTO detailDTO : detailList) {
                if(StringUtils.isBlank(detailDTO.getSource()))
                    continue;
                String[] sourceArr = detailDTO.getSource().split(",");
                StringBuilder sourceStr = new StringBuilder("");
                for (String source : sourceArr) {
                    switch (source) {
                        case "0":
                            sourceStr.append("当地采购;");
                            break;
                        case "1":
                            sourceStr.append("国内采购;");
                            break;
                        case "2":
                            sourceStr.append("第三国采购;");
                            break;
                        default:
                    }
                }
                detailDTO.setSourceName(sourceStr.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        vo.setDetailList(detailList);
        return vo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateValidStatus(String busId) {
        WzchPriorPurchase wzchPriorPurchase = this.selectWzchPriorPurchaseById(Long.parseLong(busId));
        Long projectId = wzchPriorPurchase.getProjectId();
//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_prior_purchase_detail", projectId);
//        wzchCommonService.updateValidStatus(TABLE_NAME, "id", Long.parseLong(busId));
//        wzchCommonService.updateValidStatus("wzch_prior_purchase_detail", "prior_purchase_id", Long.parseLong(busId));
        return 1;
        // 将本条数据设置为有效
    }

    @Override
    @Transactional
    public void sync(WzchPriorPurchaseDTO dto) {
        //1、获取优先进场物资
        List<WzchPriorPurchaseDetailDTO> list = detailService.getMtlDetailList(new WzchPriorPurchaseDetail());
        //2、新增主表
        BigDecimal version = VersionUtil.getVersion("wzch_prior_purchase", dto.getVersion());
        dto.setVersion(version);
        List<WzchPriorPurchase> mainList = this.wzchPriorPurchaseMapper.selectWzchPriorPurchaseList(new WzchPriorPurchase(version));
        boolean isNew = CollectionUtils.isEmpty(mainList);
        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        dto.setTitle(tenantName+"-"+"优先进场物资设备采购策划");
        if(isNew){
            dto.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(dto);
            dto.setPriorPurchaseCode(genCodeService.getSetCode(CodeEnum.WPP));
            wzchPriorPurchaseMapper.insertWzchPriorPurchase(dto);    
        }else{
            BeanUtils.copyProperties(mainList.get(0), dto);
            wzchPriorPurchaseMapper.updateWzchPriorPurchase(dto);
            //3、删除子表
            wzchPriorPurchaseMapper.deleteDetailDirectById(dto.getId());    
        }
        if(CollectionUtils.isNotEmpty(list))
            detailService.insertOrEditBatchByPurchaseId(list,dto.getId()); 
    }
}
