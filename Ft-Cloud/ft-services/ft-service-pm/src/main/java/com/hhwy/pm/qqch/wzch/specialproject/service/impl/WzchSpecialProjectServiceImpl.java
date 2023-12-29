package com.hhwy.pm.qqch.wzch.specialproject.service.impl;

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
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProject;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProjectDetail;
import com.hhwy.pm.qqch.wzch.specialproject.dto.WzchSpecialProjectDTO;
import com.hhwy.pm.qqch.wzch.specialproject.mapper.WzchSpecialProjectMapper;
import com.hhwy.pm.qqch.wzch.specialproject.service.IWzchSpecialProjectDetailService;
import com.hhwy.pm.qqch.wzch.specialproject.service.IWzchSpecialProjectService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.BusinessTaskResultUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 专项物资策划Service业务层处理
 *
 * @author mls
 * @date 2022-12-11
 */
@Service
public class WzchSpecialProjectServiceImpl implements IWzchSpecialProjectService {
    @Resource
    private WzchSpecialProjectMapper wzchSpecialProjectMapper;

    @Resource
    private IWzchSpecialProjectDetailService detailService;

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
     * 查询专项物资策划
     *
     * @param id 专项物资策划ID
     * @return 专项物资策划
     */
    @Override
    public WzchSpecialProject selectWzchSpecialProjectById(Long id) {
        return wzchSpecialProjectMapper.selectWzchSpecialProjectById(id);
    }

    /**
     * 查询专项物资策划列表
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 专项物资策划
     */
    @Override
//    @CustomDatascope(alias = "sp")
    public List<WzchSpecialProject> selectWzchSpecialProjectList(WzchSpecialProject wzchSpecialProject) {
        List<WzchSpecialProject> wzchSpecialProjects = wzchSpecialProjectMapper.selectWzchSpecialProjectList(wzchSpecialProject);
//        BusinessTaskResultUtil.handleProcessData(wzchSpecialProjects, TABLE_NAME);
        return wzchSpecialProjects;
    }

    /**
     * 新增专项物资策划
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 结果
     */
    @Override
    public int insertWzchSpecialProject(WzchSpecialProject wzchSpecialProject) {

        wzchSpecialProject.setId(IdWorker.createId());

        wzchSpecialProject.setCreateTime(DateUtils.getNowDate());

        return wzchSpecialProjectMapper.insertWzchSpecialProject(wzchSpecialProject);
    }

    /**
     * 修改专项物资策划
     *
     * @param wzchSpecialProject 专项物资策划
     * @return 结果
     */
    @Override
    public int updateWzchSpecialProject(WzchSpecialProject wzchSpecialProject) {
        wzchSpecialProject.setUpdateTime(DateUtils.getNowDate());
        return wzchSpecialProjectMapper.updateWzchSpecialProject(wzchSpecialProject);
    }

    /**
     * 删除专项物资策划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialProjectByIds(String ids) {
        wzchSpecialProjectMapper.deleteDetailsBySpecialProjectIds(Convert.toStrArray(ids));
        return wzchSpecialProjectMapper.deleteWzchSpecialProjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除专项物资策划信息
     *
     * @param id 专项物资策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialProjectById(Long id) {
        return wzchSpecialProjectMapper.deleteWzchSpecialProjectById(id);
    }

    /**
     * 进入新增  编辑 调整页面时候查询数据
     *
     * @param vo
     * @return
     */
    @Override
    public WzchSpecialProjectDTO baseInfo(WzchSpecialProjectDTO vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_special_project", vo.getVersion());
//        vo.setStageIdentity(qqchReviewService.getStage());

        List<WzchSpecialProject> list =this.wzchSpecialProjectMapper.selectWzchSpecialProjectList(new WzchSpecialProject(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setDetailList(new ArrayList<>());
            vo.setVersion(version);
            vo.setStageIdentity(qqchReviewService.getStage());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0), vo);
        vo.setVersion(ObjectUtils.nvlBigDecimal(vo.getVersion(),version));
        vo.setStageIdentity(qqchReviewService.getStage());
        WzchSpecialProjectDetail detail = new WzchSpecialProjectDetail();
        detail.setSpecialProjectId(vo.getId());
        detail.setDelFlag("0");
        List<WzchSpecialProjectDetail> detailList = detailService.selectWzchSpecialProjectDetailList(detail);
        vo.setDetailList(detailList);
        return vo;
    }

    @Override
    public int updateValidStatus(String businessId) {
        Long id = Long.parseLong(businessId);
        WzchSpecialProject wzchSpecialProject = selectWzchSpecialProjectById(id);
        Long projectId = wzchSpecialProject.getProjectId();

//        wzchCommonService.updateValidStatusByProjectId(TABLE_NAME, projectId);
//        wzchCommonService.updateValidStatusByProjectId("wzch_special_project_detail", projectId);
//
//        wzchCommonService.updateValidStatus(TABLE_NAME, "id", id);
//        wzchCommonService.updateValidStatus("wzch_special_project_detail", "special_project_id", id);

        return 1;
    }


    /**
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long insert(WzchSpecialProjectDTO dto) {
        Long specialProjectId = IdWorker.createId();
        Long projectId = dto.getProjectId();

        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchSpecialProjectDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);

        String title = dto.getProjectName() + "-" + "专项物资策划";
        dto.setTitle(title);

        // 设置主键
        dto.setId(specialProjectId);
        // 设置单据编码
//        dto.setSpecialProjectCode(genCodeService.getSetCode(CodeEnum.WSP));
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);


        int i = this.wzchSpecialProjectMapper.insertWzchSpecialProject(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        // 新增详情
        if(CollectionUtils.isNotEmpty(detailList))
            this.detailService.insertOrUpdateBatch(detailList, specialProjectId);
        return specialProjectId;
    }

    @Override
    public long edit(WzchSpecialProjectDTO dto) {
        Long specialProjectId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, specialProjectId);
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, specialProjectId,dto.getActCode(),"special_project_code");
        // 获取前端传入的物资明细
        List<WzchSpecialProjectDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(dto);
        // 编辑
        int i = this.wzchSpecialProjectMapper.updateWzchSpecialProject(dto);

        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, specialProjectId);
        return specialProjectId;
    }

    @Override
    public long adjust(WzchSpecialProjectDTO dto) {
        // 主键id
        Long oldId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId);
//        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, oldId,dto.getActCode(),"special_project_code");
//        // 校验是否能被调整
//        this.wzchCommonService.canAdjust(oldId, TABLE_NAME);
        // 获取前端传入的物资明细
        List<WzchSpecialProjectDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        WzchSpecialProjectDTO oldVersionData = (WzchSpecialProjectDTO) this.selectWzchSpecialProjectById(oldId);
        Assert.notNull(oldVersionData, "调整失败, 没有查询到要调整的单据");
        // 新版本的数据id
        Long nId = IdWorker.createId();
        // 设置id
        dto.setId(nId);
        // 设置编码
        dto.setSpecialProjectCode(oldVersionData.getActCode());
        // 设置版本号
        dto.setVersionCode(oldVersionData.getVersionCode().add(BigDecimal.ONE));
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(dto);
        dto.setValid("0");
        int i = this.wzchSpecialProjectMapper.insertWzchSpecialProject(dto);
        // 新增条数不为 1, 失败
        if (i != 1) throw new RuntimeException("调整异常");
        // 新增详情
        this.detailService.insertOrUpdateBatch(detailList, nId);

        return nId;
    }

    @Override
    @Transactional
    public long save(WzchSpecialProjectDTO dto) {
        // 获取前端传入的物资明细
        List<WzchSpecialProjectDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        // 设置版本号码
        dto.setVersionCode(new BigDecimal("1.0"));
        // 是否生效 新增不能生效
        dto.setValid("0");
        // 设置新增信息
        EntityUtils.setCreateUpdateInfo(dto);

        if(dto.getId() == null){
            dto.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(dto);
            dto.setSpecialProjectCode(genCodeService.getSetCode(CodeEnum.WSP));
            dto.setTitle("");
            int i = this.wzchSpecialProjectMapper.insertWzchSpecialProject(dto);
            // 新增条数不为 1, 失败
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "保存失败");
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(dto);
            this.wzchSpecialProjectMapper.updateWzchSpecialProject(dto);
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
}
