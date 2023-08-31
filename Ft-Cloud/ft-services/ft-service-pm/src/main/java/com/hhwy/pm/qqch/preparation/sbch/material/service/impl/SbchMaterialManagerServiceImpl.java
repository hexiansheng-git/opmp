package com.hhwy.pm.qqch.preparation.sbch.material.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.text.Convert;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlan;
import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManager;
import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManagerDetail;
import com.hhwy.pm.qqch.preparation.sbch.material.mapper.SbchMaterialManagerMapper;
import com.hhwy.pm.qqch.preparation.sbch.material.service.ISbchMaterialManagerDetailService;
import com.hhwy.pm.qqch.preparation.sbch.material.service.ISbchMaterialManagerService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;

import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备现场管理Service业务层处理
 * 
 * @author zq
 * @date 2022-12-20
 */
@Service
public class SbchMaterialManagerServiceImpl implements ISbchMaterialManagerService {
    @Autowired
    private SbchMaterialManagerMapper sbchMaterialManagerMapper;

    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private ISbchMaterialManagerDetailService sbchMaterialManagerDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 设备现场管理
     */
    @Override
    public SbchMaterialManager selectSbchMaterialManagerById(Long id) {
        return sbchMaterialManagerMapper.selectSbchMaterialManagerById(id);
    }

    /**
     * 查询设备现场管理列表
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 设备现场管理
     */
    @Override
    @SelfEmpty(clazz = SbchMaterialManager.class)
    //@CustomDatascope(alias = "manager")
    public List<SbchMaterialManager> selectSbchMaterialManagerList(SbchMaterialManager sbchMaterialManager) {
        return sbchMaterialManagerMapper.selectSbchMaterialManagerList(sbchMaterialManager);
    }

    /**
     * 新增设备现场管理
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchMaterialManager(SbchMaterialManager sbchMaterialManager) {
//        EntityUtils.setCreateInfo(sbchMaterialManager);
        sbchMaterialManager.setId(IdWorker.createId());
        String code = genCodeService.getSetCode(CodeEnum.EQU_MATERIAL_MANAGER);
        sbchMaterialManager.setFormNo(code);

        List<SbchMaterialManagerDetail> detailList = sbchMaterialManager.getDetailList();
        for (SbchMaterialManagerDetail sbchMaterialManagerDetail : detailList) {
            BeanUtils.copyProperties(sbchMaterialManager,sbchMaterialManagerDetail);
            sbchMaterialManagerDetail.setInfoId(sbchMaterialManager.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchMaterialManager);
        sbchMaterialManagerDetailService.batchInsert(detailList);
        return sbchMaterialManagerMapper.insertSbchMaterialManager(sbchMaterialManager);
    }

    private void checkUniqueValid(SbchMaterialManager sbchMaterialManager){
        if("1".equals(sbchMaterialManager.getIsValid())){
            sbchMaterialManagerMapper.updateInfoNotValid();
        }
    }

    /**
     * 修改设备现场管理
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchMaterialManager(SbchMaterialManager sbchMaterialManager) {
//        EntityUtils.setUpdateInfo(sbchMaterialManager);
        List<SbchMaterialManagerDetail> detailList = sbchMaterialManager.getDetailList();
        for (SbchMaterialManagerDetail sbchMaterialManagerDetail : detailList) {
            BeanUtils.copyProperties(sbchMaterialManager,sbchMaterialManagerDetail);
            sbchMaterialManagerDetail.setInfoId(sbchMaterialManager.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchMaterialManager);
        //删除旧的数据
        sbchMaterialManagerDetailService.deleteSbchMaterialManagerDetailByInfoId(sbchMaterialManager.getId());
        //添加新的数据
        sbchMaterialManagerDetailService.batchInsert(detailList);
        return sbchMaterialManagerMapper.updateSbchMaterialManager(sbchMaterialManager);
    }

    /**
     * 删除设备现场管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchMaterialManagerByIds(String ids) {
        SbchMaterialManager sbchMaterialManager = new SbchMaterialManager();
        sbchMaterialManager.setIsValid("1");
        sbchMaterialManager.setIds(Convert.toStrArray(ids));
        List<SbchMaterialManager> sbchMaterialManagers = sbchMaterialManagerMapper.selectSbchMaterialManagerList(sbchMaterialManager);
        if(!ObjectNullUtil.isEmpty(sbchMaterialManagers)){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可删除");
        }
        return sbchMaterialManagerMapper.deleteSbchMaterialManagerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备现场管理信息
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    public int deleteSbchMaterialManagerById(Long id) {
        return sbchMaterialManagerMapper.deleteSbchMaterialManagerById(id);
    }

    @Override
    public List<JSONObject> getTempleteList() {
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("controlContent","机械设备账、财务账相符率");
        jsonObject.put("controlTarget","100%");
        jsonObject.put("useMeasures","1.设备部门在设备进场后一个月内完成验收，并及时将验收单上报财务部门" +
                "2.每月底与财务进行对账，核对设备账与财务账中的设备数量、原值、净值。");
        list.add(jsonObject);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("controlContent","机械设备账、实物相符率");
        jsonObject1.put("controlTarget","100%");
        jsonObject1.put("useMeasures","1.设备部门每月对现场设备进行巡检，核对设备数量与台账的相符情况；"+
                "2.每半年组织一次全面的清查，核对台账信息与实物的相符情况。");
        list.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("controlContent","账内机械设备完好率");
        jsonObject2.put("controlTarget","≥90%");
        jsonObject2.put("useMeasures","1.定期做好维保，做好维保记录，以养代修；" +
                "2.发现问题及时停机处理，设备不带病工作；"+
                "3.科学制定配件储备。");
        list.add(jsonObject2);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("controlContent","机械事故率");
        jsonObject3.put("controlTarget","＜5%");
        jsonObject3.put("useMeasures","1.制定设备安全操作规程并张贴在设备明显位置；" +
                "2.对操作人员进行技术培训和交底，加强安全意识;"+
                "3.严格参照设备说明书使用操作，杜绝野蛮操作等责任机械事故发生。");
        list.add(jsonObject3);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("controlContent","设备进场验收覆盖率");
        jsonObject4.put("controlTarget","100%");
        jsonObject4.put("useMeasures","1.自有新设备验收要结合采购合同，确认设备技术规格和配置是否与合同一致，设备部门牵头技术、工程、安全、财务等部门联合验收，重点要对设备进行试运转，查看是否存在质量问题，进场一个月内完成验收并填写验收单。" +
                "2.协作单位设备进场后的验收要结合项目前期策划和工程分包合同，确认设备技术规格和配置是否一致，设备部门牵头技术、工程、安全、财务等部门联合验收，重点要对设备进行试运转，评估设备是否满足施工需要，进场一个月内完成验收并填写验收单。"+
                "3.租赁设备的验收要结合租赁合同，核对进场设备是否与租赁合同设备信息一致，重");
        list.add(jsonObject4);

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("controlContent","制定机械设备管理实施细则");
        jsonObject5.put("controlTarget","100%");
        jsonObject5.put("useMeasures","1. 负责贯彻执行海外事业部的机械设备管理实施细则及区域中心/国别公司的有关补充规定和细则；" +
                "2.制定本项目部机械设备管理补充规定和实施细则，并上报区域中心备案。");
        list.add(jsonObject5);

        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("controlContent","特种设备安全管理制度与操作规程");
        jsonObject6.put("controlTarget","100%");
        jsonObject6.put("useMeasures","1.必须使用取得许可生产并经检验合格的特种设备，建立特种设备台帐及技术档案。" +
                "2.确保特种设备操作人员持证上岗，建立人员管理台帐。");
        list.add(jsonObject6);
        return list;
    }

    @Override
    public SbchMaterialManager getList(BigDecimal version) {
        SbchMaterialManager returnVo = new SbchMaterialManager();
        version = VersionUtil.getVersion("sbch_material_manager", version);

        SbchMaterialManager sbchMaterialManager = new SbchMaterialManager();
        sbchMaterialManager.setVersionNo(version);
        List<SbchMaterialManager> sbchMaterialManagers = sbchMaterialManagerMapper.selectSbchMaterialManagerList(sbchMaterialManager);
        if(!ObjectNullUtil.isEmpty(sbchMaterialManagers)){
            SbchMaterialManager sbchMaterialManager1 = sbchMaterialManagers.get(0);
            returnVo = sbchMaterialManager1;
            //详情列表
            SbchMaterialManagerDetail sbchMaterialManagerDetail = new SbchMaterialManagerDetail();
            sbchMaterialManagerDetail.setInfoId(sbchMaterialManager1.getId());
            List<SbchMaterialManagerDetail> sbchMaterialManagerDetails = sbchMaterialManagerDetailService.selectSbchMaterialManagerDetailList(sbchMaterialManagerDetail);
            returnVo.setDetailList(sbchMaterialManagerDetails);
        }else{
            List<JSONObject> templeteList = getTempleteList();
            List<SbchMaterialManagerDetail> sbchMaterialManagerDetails = JSONArray.parseArray(JSON.toJSONString(templeteList), SbchMaterialManagerDetail.class);
            returnVo.setDetailList(sbchMaterialManagerDetails);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchMaterialManager sbchMaterialManager) {
        List<SbchMaterialManagerDetail> detailList = sbchMaterialManager.getDetailList();
        SbchMaterialManager temp = new SbchMaterialManager();
        temp.setVersion(sbchMaterialManager.getVersion());
        List<SbchMaterialManager> sbchMaterialManagers = sbchMaterialManagerMapper.selectSbchMaterialManagerList(temp);
        if(!ObjectNullUtil.isEmpty(sbchMaterialManagers)){
            sbchMaterialManager.setId(sbchMaterialManagers.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchMaterialManager);
            sbchMaterialManagerMapper.updateSbchMaterialManager(sbchMaterialManager);
        }else{
            sbchMaterialManager.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_MATERIAL_MANAGER);
            sbchMaterialManager.setUnicode(code);
            sbchMaterialManager.setTitleName("设备现场管理策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchMaterialManager);
            sbchMaterialManagerMapper.insertSbchMaterialManager(sbchMaterialManager);
        }
        //删除子表
        sbchMaterialManagerDetailService.deleteSbchMaterialManagerDetailByInfoId(sbchMaterialManager.getId());
        if(!ObjectNullUtil.isEmpty(detailList)){
            //校验数据必填
            if("1".equals(sbchMaterialManager.getButtonMark())||"2".equals(sbchMaterialManager.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchMaterialManagerDetail sbchMaterialManagerDetail : detailList) {
                BeanUtils.copyProperties(sbchMaterialManager,sbchMaterialManagerDetail);
                sbchMaterialManagerDetail.setInfoId(sbchMaterialManager.getId());
                EntityUtils.setCreateInfo(sbchMaterialManagerDetail);
                sbchMaterialManagerDetail.setId(IdWorker.createId());
            }
            //添加新的子表数据
            sbchMaterialManagerDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchMaterialManager.getButtonMark())){
            //插入确认记录
            String menuId = sbchMaterialManager.getMenuId();
            String stageIdentity = sbchMaterialManager.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
