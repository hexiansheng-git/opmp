package com.hhwy.pm.qqch.wzch.fund.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFund;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFundDetail;
import com.hhwy.pm.qqch.wzch.fund.dto.WzchFundDTO;
import com.hhwy.pm.qqch.wzch.fund.mapper.WzchFundMapper;
import com.hhwy.pm.qqch.wzch.fund.service.IWzchFundDetailService;
import com.hhwy.pm.qqch.wzch.fund.service.IWzchFundService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
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
import java.util.*;

/**
 * 资金策划Service业务层处理
 *
 * @author mls
 * @date 2022-12-08
 */
@Service
public class WzchFundServiceImpl implements IWzchFundService {
    @Resource
    private WzchFundMapper wzchFundMapper;

    @Resource
    private IWzchFundDetailService detailService;

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
//    private final static String TABLE_NAME = FlowEnum.WF.getTableName();
    private final static String DETAIL_TABLE_NAME = "wzch_fund_detail";

    /**
     * 查询资金策划
     *
     * @param id 资金策划ID
     * @return 资金策划
     */
    @Override
    public WzchFund selectWzchFundById(Long id) {
        return wzchFundMapper.selectWzchFundById(id);
    }

    /**
     * 查询资金策划列表
     *
     * @param wzchFund 资金策划
     * @return 资金策划
     */
    @Override
//    @CustomDatascope(alias = "wf")
    public List<WzchFund> selectWzchFundList(WzchFund wzchFund) {
        return wzchFundMapper.selectWzchFundList(wzchFund);
    }

    /**
     * 新增资金策划
     *
     * @param wzchFund 资金策划
     * @return 结果
     */
    @Override
    public int insertWzchFund(WzchFund wzchFund) {

        wzchFund.setId(IdWorker.createId());

        wzchFund.setCreateTime(DateUtils.getNowDate());

        return wzchFundMapper.insertWzchFund(wzchFund);
    }

    /**
     * 修改资金策划
     *
     * @param wzchFund 资金策划
     * @return 结果
     */
    @Override
    public int updateWzchFund(WzchFund wzchFund) {
        wzchFund.setUpdateTime(DateUtils.getNowDate());
        return wzchFundMapper.updateWzchFund(wzchFund);
    }

    /**
     * 删除资金策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWzchFundByIds(String ids) {
        wzchFundMapper.deleteDetailsByFundId(Convert.toStrArray(ids));
        return wzchFundMapper.deleteWzchFundByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资金策划信息
     *
     * @param id 资金策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchFundById(Long id) {
        return wzchFundMapper.deleteWzchFundById(id);
    }

    /**
     * @param vo
     * @return
     */
    @Override
    public WzchFundDTO baseInfo(WzchFundDTO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_purchase_supply", vo.getVersion());
        vo.setVersion(ObjectUtils.nvlBigDecimal(vo.getVersion(),version));
        vo.setStageIdentity(qqchReviewService.getStage());
        
        List<WzchFund> list = this.wzchFundMapper.selectWzchFundList(new WzchFund(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0), vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        WzchFundDetail detail = new WzchFundDetail();
        detail.setFundId(vo.getId());
        detail.setDelFlag("0");
        List<WzchFundDetail> detailList = detailService.selectWzchFundDetailList(detail);
        HashMap<String, String> dictMap = new HashMap<>(1);
        dictMap.put("warnFlag_warnFlagName", "warn_flag");
        wzchCommonService.setDicValue(detailList, dictMap);
        vo.setDetailList(detailList);
        return vo;
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @Override
    public long insert(WzchFundDTO dto) {
        Long fundId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchFundDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        String title = dto.getProjectName() + "-" + "资金策划";
        dto.setTitle(title);
        // 设置主键
        dto.setId(fundId);
        // 设置单据编码
//        dto.setFundCode(genCodeService.getSetCode(CodeEnum.WF));
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);


        int i = this.wzchFundMapper.insertWzchFund(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        WzchFundDetail[] as = detailList.toArray(new WzchFundDetail[]{});
        CollectionUtils.reverseArray(as);
        this.detailService.insertOrUpdateBatch(Arrays.asList(as), fundId);

        return fundId;
    }

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    @Override
    public long edit(WzchFundDTO dto) {
        Long fundId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, fundId, dto.getActCode(), "fund_code");
        // 获取前端传入的物资明细
        List<WzchFundDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(dto);
        // 编辑
        int i = this.wzchFundMapper.updateWzchFund(dto);

        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, fundId);
        return fundId;
    }

    /**
     * 调整
     *
     * @param dto
     * @return
     */
    @Override
    public long adjust(WzchFundDTO dto) {

        // 主键id
        Long oldId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId);
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId, dto.getActCode(), "fund_code");
        // 校验是否能被调整
//        this.wzchCommonService.canAdjust(oldId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchFundDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchFundDTO oldVersionData = (WzchFundDTO) this.selectWzchFundById(oldId);
        Assert.notNull(oldVersionData, "调整失败, 没有查询到要调整的单据");
        // 新版本的数据id
        Long nRentId = IdWorker.createId();
        // 设置id
        dto.setId(nRentId);
        // 设置编码
        dto.setFundCode(oldVersionData.getActCode());
        // 设置版本号
        dto.setVersionCode(oldVersionData.getVersionCode().add(BigDecimal.ONE));
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(dto);
        dto.setValid("0");
        int i = this.wzchFundMapper.insertWzchFund(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new RuntimeException("调整异常");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, nRentId);

        return nRentId;
    }

    @Override
    @Transactional
    public long save(WzchFundDTO dto) {
        Assert.notNull(dto.getVersion(),"版本号不能为空");
        List<WzchFundDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);
        if(dto.getId()==null) {
            dto.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(dto);
            dto.setTitle("");
            dto.setFundCode(genCodeService.getSetCode(CodeEnum.WF));
            int i = this.wzchFundMapper.insertWzchFund(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(dto);
            this.wzchFundMapper.updateWzchFund(dto);    
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

    /**
     * 更新状态
     *
     * @param busId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateValidStatus(String busId) {
        WzchFund wzchFund = this.wzchFundMapper.selectWzchFundById(Long.valueOf(busId));
        Long projectId = wzchFund.getProjectId();

//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId(DETAIL_TABLE_NAME, projectId);


        wzchFundMapper.updateDetailValidStatus(busId);
        return wzchFundMapper.updateValidStatus(busId);
    }


}
