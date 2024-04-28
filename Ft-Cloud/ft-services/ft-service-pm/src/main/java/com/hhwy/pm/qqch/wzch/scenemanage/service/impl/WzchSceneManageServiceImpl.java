package com.hhwy.pm.qqch.wzch.scenemanage.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManage;
import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;
import com.hhwy.pm.qqch.wzch.scenemanage.dto.WzchSceneManageDTO;
import com.hhwy.pm.qqch.wzch.scenemanage.mapper.WzchSceneManageMapper;
import com.hhwy.pm.qqch.wzch.scenemanage.service.WzchSceneManageDetailService;
import com.hhwy.pm.qqch.wzch.scenemanage.service.WzchSceneManageService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
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
 * 现场物资管控策划(WzchSceneManage)表服务实现类
 *
 * @author makejava
 * @since 2022-12-18 11:24:41
 */
@Service
public class WzchSceneManageServiceImpl implements WzchSceneManageService {

    @Resource
    private WzchSceneManageMapper wzchSceneManageMapper;

    @Resource
    private WzchSceneManageDetailService detailService;


    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Resource
    private GenCodeService genCodeService;


    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";
    private final static String TABLE_NAME = "wzch_scene_manage";


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WzchSceneManage selectWzchSceneManageById(Long id) {
        return this.wzchSceneManageMapper.selectWzchSceneManageById(id);
    }


    /**
     * 修改数据
     *
     * @param wzchSceneManage 实例对象
     * @return 实例对象
     */
    @Override
    public WzchSceneManage update(WzchSceneManage wzchSceneManage) {
        this.wzchSceneManageMapper.update(wzchSceneManage);
        return this.selectWzchSceneManageById(wzchSceneManage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wzchSceneManageMapper.deleteById(id) > 0;
    }

    @Override
//    @CustomDatascope(alias = "sm")
    public List<WzchSceneManage> selectWzchSceneManageList(WzchSceneManage wzchSceneManage) {
        List<WzchSceneManage> wzchSceneManages = this.wzchSceneManageMapper.selectWzchSceneManageList(wzchSceneManage);
        return wzchSceneManages;
    }

    /**
     * 新增 编辑 详情查询数据
     *
     * @param dto
     * @return
     */
    @Override
    public WzchSceneManageDTO baseInfo(WzchSceneManageDTO dto) {
        BigDecimal version = VersionUtil.getVersion("wzch_scene_manage", dto.getVersion());
        boolean isMatchVersion = BigDecimalUtils.equals(version,dto.getVersion());
        dto.setVersion(ObjectUtils.nvlBigDecimal(dto.getVersion(),version));
        dto.setStageIdentity(qqchReviewService.getStage());

        List<WzchSceneManage> list = wzchSceneManageMapper.selectWzchSceneManageList(new WzchSceneManage(version));
        if(CollectionUtils.isEmpty(list)){
            dto.setDetailList(new ArrayList<>(2));
            return dto;
        }
        dto.setId(list.get(0).getId());
        dto.setId(isMatchVersion?dto.getId():null); //若取得不是本版本，将id滞空，
        WzchSceneManageDetail queryDetail = new WzchSceneManageDetail();
        queryDetail.setSceneManageId(dto.getId());
        List<WzchSceneManageDetail> detailList =detailService.selectDetailList(queryDetail);
        dto.setDetailList(detailList);
        return dto;
    }


    /**
     * 新增数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public long insert(WzchSceneManageDTO dto) {
        if(dto.getVersion() == null) dto.setVersion(new BigDecimal("1"));
        dto.setVersionCode(Double.parseDouble(dto.getVersion().toString()));
        dto.setVersionCodeStr(dto.getVersion()+"");
        List<WzchSceneManageDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
        String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
        dto.setTitle("现场管理策划"+"-"+tenantName+"-");
        dto.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        if(dto.getId()==null){
            dto.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(dto);
            dto.setValid("0"); 
            dto.setSceneCode(genCodeService.getSetCode(CodeEnum.WF));
            int i = this.wzchSceneManageMapper.insert(dto);
            if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "新增失败");
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(dto);
            wzchSceneManageMapper.update(dto);
        }
        this.detailService.insertOrUpdateBatch(detailList, dto.getId());
        if (ButtonMark.CONFIRM.equals(dto.getButtonMark())) {
            String menuId = dto.getMenuId();
            String stageIdentity = dto.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return dto.getId();
    }

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    @Override
    public long edit(WzchSceneManageDTO dto) {
        Long sceneId = dto.getId();
        Long projectId = dto.getProjectId();
        // 校验项目是否被选择
        this.wzchCommonService.verifyProjectSelected(projectId, TABLE_NAME, sceneId);
        // 获取前端传入的物资明细
        List<WzchSceneManageDetail> detailList = dto.getDetailList();
        JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);

        // 编辑
        int i = this.wzchSceneManageMapper.update(dto);

        // 如果根据id更新的数据条数不为 1, 失败 , 直接抛出异常
        if (i != 1) throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "编辑失败");
        // 编辑明细
        this.detailService.insertOrUpdateBatch(detailList, sceneId);
        return sceneId;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(String ids) {
        this.wzchSceneManageMapper.deleteDetailsBySceneIds(Convert.toStrArray(ids));
        return wzchSceneManageMapper.deleteByIds(Convert.toStrArray(ids));
    }
}
