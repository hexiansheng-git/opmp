package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeam;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetailsDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.dto.SbchEquipmentTeamDTO;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper.SbchEquipmentTeamMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamDetailsDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.myUtilPrepare.SetMaterialNameUtils;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 协作单位设备管理Service业务层处理
 * 
 * @author hwj
 * @date 2022-11-30
 */
@Service
public class SbchEquipmentTeamServiceImpl implements ISbchEquipmentTeamService {
    @Autowired
    private SbchEquipmentTeamMapper sbchEquipmentTeamMapper;
    
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchEquipmentTeamDetailsService detailsService;
    @Autowired
    private ISbchEquipmentTeamDetailsDetailsService detailsDetailsService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private SetMaterialNameUtils setMaterialNameUtils;
    
    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";
    
    

    /**
     * 查询协作单位设备管理
     * 
     * @param id 协作单位设备管理ID
     * @return 协作单位设备管理
     */
    @Override
    public SbchEquipmentTeam selectSbchEquipmentTeamById(Long id) {
        return sbchEquipmentTeamMapper.selectSbchEquipmentTeamById(id);
    }

    /**
     * 查询协作单位设备管理列表
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 协作单位设备管理
     */
    @SelfEmpty(clazz = SbchEquipmentTeam.class)
    @Override
    //@CustomDatascope(alias = "c")
    public List<SbchEquipmentTeam> selectSbchEquipmentTeamList(SbchEquipmentTeam sbchEquipmentTeam) {
        return sbchEquipmentTeamMapper.selectSbchEquipmentTeamList(sbchEquipmentTeam);
    }

    /**
     * 新增协作单位设备管理
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentTeam(SbchEquipmentTeam sbchEquipmentTeam) {

    sbchEquipmentTeam.setId(IdWorker.createId());

        sbchEquipmentTeam.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentTeamMapper.insertSbchEquipmentTeam(sbchEquipmentTeam);
    }

    /**
     * 修改协作单位设备管理
     * 
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentTeam(SbchEquipmentTeam sbchEquipmentTeam) {
        sbchEquipmentTeam.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentTeamMapper.updateSbchEquipmentTeam(sbchEquipmentTeam);
    }
    /**
     * 新增协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public String insertSbchEquipmentTeamAndDetails(SbchEquipmentTeam sbchEquipmentTeam) {
        //协作单位详情
        List<SbchEquipmentTeamDetails> detailsList = sbchEquipmentTeam.getDetailsList();
        SbchEquipmentTeam team = new SbchEquipmentTeam();
        team.setVersionCode(sbchEquipmentTeam.getVersion());
        List<SbchEquipmentTeam> sbchEquipmentTeams = sbchEquipmentTeamMapper.selectSbchEquipmentTeamList(team);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentTeams)){
            sbchEquipmentTeam.setId(sbchEquipmentTeams.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentTeam);
            sbchEquipmentTeamMapper.updateSbchEquipmentTeam(sbchEquipmentTeam);
        }else{
            sbchEquipmentTeam.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_XZDWSB);
            sbchEquipmentTeam.setUnicode(setCode);
            sbchEquipmentTeam.setTitleName("协作单位设备管理");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentTeam);
            // 设置单据编码
            sbchEquipmentTeam.setTitle(sbchEquipmentTeam.getTitleName());
            sbchEquipmentTeam.setVersionCode(sbchEquipmentTeam.getVersion());
            sbchEquipmentTeam.setAdjustCode(setCode+"-"+sbchEquipmentTeam.getVersionCode().setScale(0));
            // 新增
            this.sbchEquipmentTeamMapper.insertSbchEquipmentTeam(sbchEquipmentTeam);
        }

        if(!ObjectNullUtil.isEmpty(detailsList)){
            //校验数据必填
            if("1".equals(sbchEquipmentTeam.getButtonMark())||"2".equals(sbchEquipmentTeam.getButtonMark())){//确认
                JyDetailsUtil.jyDetailsDetails(detailsList,"getDetailsDetailsList", ValidationGroups.Save.class);
            }
            // 协作单位详情
            detailsService.insertOrEditBatchByMainId(detailsList, sbchEquipmentTeam.getId(), false);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchEquipmentTeam.getButtonMark())){
            //插入确认记录
            String menuId = sbchEquipmentTeam.getMenuId();
            String stageIdentity = sbchEquipmentTeam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
        return sbchEquipmentTeam.getId().toString();
    }

    /**
     * 修改协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchEquipmentTeamAndDetails(SbchEquipmentTeamDTO sbchEquipmentTeam) {
        // 获取前端传入的设备明细
        List<SbchEquipmentTeamDetails> detailList = sbchEquipmentTeam.getDetailsList();
        /*校验字表*/
        Boolean aBoolean = JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsDetailsList", ValidationGroups.Update.class);

//        EntityUtils.setUpdateInfo(sbchEquipmentTeam);
        // 修改
        sbchEquipmentTeamMapper.updateSbchEquipmentTeam(sbchEquipmentTeam);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentTeam.getId(),false);
    }

    /**
     * 调整协作单位设备管理
     *
     * @param sbchEquipmentTeam 协作单位设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public String adjustSbchEquipmentTeamAndDetails(SbchEquipmentTeamDTO sbchEquipmentTeam) {
        // 获取前端传入的设备明细
        List<SbchEquipmentTeamDetails> detailList = sbchEquipmentTeam.getDetailsList();

        /*校验字表*/
        Boolean aBoolean = JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsDetailsList", ValidationGroups.Update.class);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentTeam.setId(id);
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(sbchEquipmentTeam);

        //置为无效
        sbchEquipmentTeam.setValid("0");

        // 修改
        sbchEquipmentTeamMapper.insertSbchEquipmentTeam(sbchEquipmentTeam);
        // 明细
        detailsService.insertOrEditBatchByMainId(detailList, sbchEquipmentTeam.getId(), true);
        return id.toString();
    }

    /**
     * 删除协作单位设备管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentTeamByIds(String ids) {
        return sbchEquipmentTeamMapper.deleteSbchEquipmentTeamByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除协作单位设备管理信息
     * 
     * @param id 协作单位设备管理ID
     * @return 结果
     */
    public int deleteSbchEquipmentTeamById(Long id) {
        return sbchEquipmentTeamMapper.deleteSbchEquipmentTeamById(id);
    }
    @Transactional
    @Override
    public int updateValidStatus(String mainId) {

        SbchEquipmentTeam sbchEquipmentTeam = this.selectSbchEquipmentTeamById(Long.parseLong(mainId));
        Long projectId = sbchEquipmentTeam.getProjectId();
//        String tableName = FlowEnum.XZSBGL.getTableName();
//        sbchCommonService.updateValidStatusValid(tableName,Long.parseLong(mainId),projectId);
        return 1;
    }

    @Override
    public SbchEquipmentTeam getList(BigDecimal version) {
        SbchEquipmentTeam returnVo = new SbchEquipmentTeam();
        SbchEquipmentTeam team = new SbchEquipmentTeam();
        version = VersionUtil.getVersion("sbch_equipment_team", version);
        team.setVersionCode(version);
        List<SbchEquipmentTeam> sbchEquipmentTeams = sbchEquipmentTeamMapper.selectSbchEquipmentTeamList(team);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentTeams)){
            SbchEquipmentTeam team1 = sbchEquipmentTeams.get(0);
            returnVo = team1;
            SbchEquipmentTeamDetails detail = new SbchEquipmentTeamDetails();
            detail.setMainId(team1.getId());
            /*协作单位详情*/
            List<SbchEquipmentTeamDetails> detailList = detailsService.selectSbchEquipmentTeamDetailsList(detail);
            returnVo.setDetailsList(detailList);
            if(!ObjectNullUtil.isEmpty(detailList)){
                detailList.stream().forEach(item->{
                    SbchEquipmentTeamDetailsDetails sbchEquipmentTeamDetailsDetails = new SbchEquipmentTeamDetailsDetails();
                    sbchEquipmentTeamDetailsDetails.setMainId(item.getId());
                    sbchEquipmentTeamDetailsDetails.setDelFlag("0");
                    /*协作单位设备详情*/
                    List<SbchEquipmentTeamDetailsDetails> detailsDetailsList = detailsDetailsService.selectSbchEquipmentTeamDetailsDetailsList(sbchEquipmentTeamDetailsDetails);
                    //redis中获取设备name
                    Map<String, String> busAndMaterialMap = new HashMap<>();
                    busAndMaterialMap.put("materialName", "materialName");
                    List<SbchEquipmentTeamDetailsDetails> detailsDetailsListVersion1 = setMaterialNameUtils.setMaterialInfo(detailsDetailsList, "materialCode", busAndMaterialMap);
                    item.setDetailsDetailsList(detailsDetailsListVersion1);
                });
            }

        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }
}
