package com.hhwy.pm.qqch.preparation.sbch.single.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheck;
import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheckDetail;
import com.hhwy.pm.qqch.preparation.sbch.single.mapper.SbchSingleCheckMapper;
import com.hhwy.pm.qqch.preparation.sbch.single.service.ISbchSingleCheckDetailService;
import com.hhwy.pm.qqch.preparation.sbch.single.service.ISbchSingleCheckService;
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
import java.util.Date;
import java.util.List;

/**
 * 单机核算策划Service业务层处理
 * 
 * @author zq
 * @date 2022-12-22
 */
@Service
public class SbchSingleCheckServiceImpl implements ISbchSingleCheckService {
    @Autowired
    private SbchSingleCheckMapper sbchSingleCheckMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchSingleCheckDetailService sbchSingleCheckDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 单机核算策划
     */
    @Override
    public SbchSingleCheck selectSbchSingleCheckById(Long id) {
        return sbchSingleCheckMapper.selectSbchSingleCheckById(id);
    }

    /**
     * 查询单机核算策划列表
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 单机核算策划
     */
    @Override
    @SelfEmpty(clazz = SbchSingleCheck.class)
    //@CustomDatascope(alias = "single")
    public List<SbchSingleCheck> selectSbchSingleCheckList(SbchSingleCheck sbchSingleCheck) {
        return sbchSingleCheckMapper.selectSbchSingleCheckList(sbchSingleCheck);
    }

    /**
     * 新增单机核算策划
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchSingleCheck(SbchSingleCheck sbchSingleCheck) {
//        EntityUtils.setCreateInfo(sbchSingleCheck);
        sbchSingleCheck.setId(IdWorker.createId());
        String code = genCodeService.getSetCode(CodeEnum.EQU_SINGLE_CHECK);
        sbchSingleCheck.setFormNo(code);
        List<SbchSingleCheckDetail> detailList = sbchSingleCheck.getDetailList();
        for (SbchSingleCheckDetail sbchSingleCheckDetail : detailList) {
            BeanUtils.copyProperties(sbchSingleCheck,sbchSingleCheckDetail);
            sbchSingleCheckDetail.setInfoId(sbchSingleCheck.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchSingleCheck);
        //批量添加子表
        sbchSingleCheckDetailService.batchInsert(detailList);
        return sbchSingleCheckMapper.insertSbchSingleCheck(sbchSingleCheck);
    }

    private void checkUniqueValid(SbchSingleCheck sbchSingleCheck){
        if("1".equals(sbchSingleCheck.getIsValid())){
            sbchSingleCheckMapper.updateInfoNotValid(sbchSingleCheck.getProjectId());
        }
    }

    /**
     * 修改单机核算策划
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchSingleCheck(SbchSingleCheck sbchSingleCheck) {
//        EntityUtils.setUpdateInfo(sbchSingleCheck);
        List<SbchSingleCheckDetail> detailList = sbchSingleCheck.getDetailList();
        for (SbchSingleCheckDetail sbchSingleCheckDetail : detailList) {
            BeanUtils.copyProperties(sbchSingleCheck,sbchSingleCheckDetail);
            sbchSingleCheckDetail.setInfoId(sbchSingleCheck.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchSingleCheck);
        //删除旧的数据
        sbchSingleCheckDetailService.deleteSbchSingleCheckDetailByInfoId(sbchSingleCheck.getId());
        //添加新的数据
        sbchSingleCheckDetailService.batchInsert(detailList);
        return sbchSingleCheckMapper.updateSbchSingleCheck(sbchSingleCheck);
    }

    /**
     * 删除单机核算策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchSingleCheckByIds(String ids) {
        SbchSingleCheck sbchSingleCheck = new SbchSingleCheck();
        sbchSingleCheck.setIsValid("1");
        sbchSingleCheck.setIds(Convert.toStrArray(ids));
        List<SbchSingleCheck> sbchSingleChecks = sbchSingleCheckMapper.selectSbchSingleCheckList(sbchSingleCheck);

        if(!ObjectNullUtil.isEmpty(sbchSingleChecks)){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可删除");
        }
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        return sbchSingleCheckMapper.deleteSbchSingleCheckByIds(Convert.toStrArray(ids),userId,date);
    }

    /**
     * 删除单机核算策划信息
     * 
     * @param id 单机核算策划ID
     * @return 结果
     */
    public int deleteSbchSingleCheckById(Long id) {
        return sbchSingleCheckMapper.deleteSbchSingleCheckById(id);
    }

    @Override
    public List<JSONObject> getTempleteList() {
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("workContent","折旧费归集");
        jsonObject1.put("middleDept","机械部");
        jsonObject1.put("mainDept","财务部");
        jsonObject1.put("dataGetTime","月底采集");
        jsonObject1.put("useWay","财务部门按月对项目机械设备计提折旧，月底将折旧表提供给机械部门。成本系统正式上线运行后，从成本系统取值");
        list.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("workContent","人工费采集");
        jsonObject2.put("middleDept","机械部");
        jsonObject2.put("mainDept","综合办");
        jsonObject2.put("dataGetTime","月底采集");
        jsonObject2.put("useWay","机械部配合综合办将项目设备和操作手、修理工做好对应关系，综合办月底将相关人员的费用统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值");
        list.add(jsonObject2);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("workContent","燃油费采集");
        jsonObject3.put("middleDept","机械部");
        jsonObject3.put("mainDept","材料部");
        jsonObject3.put("dataGetTime","实时采集");
        jsonObject3.put("useWay","材料部门对项目设备逐台建立加油记录，发生加油时如实做好登记，月底将加油数量和加油金额统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值");
        list.add(jsonObject3);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("workContent","配件费采集");
        jsonObject4.put("middleDept","机械部");
        jsonObject4.put("mainDept","材料部");
        jsonObject4.put("dataGetTime","实时采集");
        jsonObject4.put("useWay","材料部门对项目设备逐台建立配件领用记录，发生配件领用时如实做好登记，月底将配件领用明细和配件领用金额统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值");
        list.add(jsonObject4);

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("workContent","轮胎费采集");
        jsonObject5.put("middleDept","机械部");
        jsonObject5.put("mainDept","材料部");
        jsonObject5.put("dataGetTime","实时采集");
        jsonObject5.put("useWay","材料部门对项目设备逐台建立轮胎领用记录，发生轮胎领用时如实做好登记，月底将轮胎领用明细和轮胎领用金额统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject5);

        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("workContent","电瓶费采集");
        jsonObject6.put("middleDept","机械部");
        jsonObject6.put("mainDept","材料部");
        jsonObject6.put("dataGetTime","实时采集");
        jsonObject6.put("useWay","材料部门对项目设备逐台建立电瓶领用记录，发生电瓶领用时如实做好登记，月底将电瓶领用明细和电瓶领用金额统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject6);

        JSONObject jsonObject7 = new JSONObject();
        jsonObject7.put("workContent","润滑油费采集");
        jsonObject7.put("middleDept","机械部");
        jsonObject7.put("mainDept","材料部");
        jsonObject7.put("dataGetTime","实时采集");
        jsonObject7.put("useWay","材料部门对项目设备逐台建立润滑油领用记录，发生润滑油领用时如实做好登记，月底将润滑油领用明细和润滑油领用金额统计表提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject7);

        JSONObject jsonObject8 = new JSONObject();
        jsonObject8.put("workContent","外修费采集");
        jsonObject8.put("middleDept","机械部");
        jsonObject8.put("mainDept","材料部");
        jsonObject8.put("dataGetTime","实时采集");
        jsonObject8.put("useWay","机械部门对项目设备逐台建立外修费采集记录，发生设备外修时，如实做好统计，月底进行汇总。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject8);

        JSONObject jsonObject9 = new JSONObject();
        jsonObject9.put("workContent","水电费采集");
        jsonObject9.put("middleDept","机械部");
        jsonObject9.put("mainDept","材料部");
        jsonObject9.put("dataGetTime","实时采集");
        jsonObject9.put("useWay","材料部门对项目有水电消耗的设备逐台建立数据采集台账，月底进行统计汇总后提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject9);

        JSONObject jsonObject10 = new JSONObject();
        jsonObject10.put("workContent","其他费采集");
        jsonObject10.put("middleDept","机械部");
        jsonObject10.put("mainDept","材料部");
        jsonObject10.put("dataGetTime","实时采集");
        jsonObject10.put("useWay","材料部门对项目设备逐台建立其他费采集台账，发生对应费用时，如实进行登记并在月底汇总提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject10);

        JSONObject jsonObject11 = new JSONObject();
        jsonObject11.put("workContent","运转时间采集");
        jsonObject11.put("middleDept","机械部");
        jsonObject11.put("mainDept","材料部");
        jsonObject11.put("dataGetTime","实时采集");
        jsonObject11.put("useWay","设备使用工长负责记录设备运转时间，每天如实登记设备实际工作时长，月底将运转记录表提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject11);

        JSONObject jsonObject12 = new JSONObject();
        jsonObject12.put("workContent","行驶里程采集");
        jsonObject12.put("middleDept","机械部");
        jsonObject12.put("mainDept","材料部");
        jsonObject12.put("dataGetTime","月底采集");
        jsonObject12.put("useWay","设备使用工长负责如实记录设备行驶里程，月底将设备行驶里程表提供给机械部门。成本系统正式上线运行后，从成本系统取值。");
        list.add(jsonObject12);

        JSONObject jsonObject13 = new JSONObject();
        jsonObject13.put("workContent","单机成本汇总");
        jsonObject13.put("middleDept","机械部");
        jsonObject13.put("mainDept","材料部");
        jsonObject13.put("dataGetTime","下月3日前完成汇总");
        jsonObject13.put("useWay","机械部门将其他部门采集的数据收集汇总，逐台设备核算当月发生的实际费用。");
        list.add(jsonObject13);

        JSONObject jsonObject14 = new JSONObject();
        jsonObject14.put("workContent","单机核算分析");
        jsonObject14.put("middleDept","机械部");
        jsonObject14.put("mainDept","材料部");
        jsonObject14.put("dataGetTime","下月5日前完成分析");
        jsonObject14.put("useWay","机械部门根据当月运转时间、行驶里程和油耗，核算出单机百公里油耗或小时油耗，与理论油耗及上月油耗进行比对，并得出油耗是否异常的结论，如存在问题，及时查找分析异常原因，制定解决措施。");
        list.add(jsonObject14);

        JSONObject jsonObject15 = new JSONObject();
        jsonObject15.put("workContent","工作量");
        jsonObject15.put("middleDept","xxx");
        jsonObject15.put("mainDept","xxx");
        jsonObject15.put("dataGetTime","月底采集");
        jsonObject15.put("useWay","具体部位上的工作量核算。");
        list.add(jsonObject15);
        return list;
    }

    @Override
    public SbchSingleCheck getList(BigDecimal version) {
        SbchSingleCheck returnVo = new SbchSingleCheck();
        version = VersionUtil.getVersion("sbch_single_check", version);
        SbchSingleCheck sbchSingleCheck = new SbchSingleCheck();
        sbchSingleCheck.setVersionNo(version);
        List<SbchSingleCheck> sbchSingleChecks = sbchSingleCheckMapper.selectSbchSingleCheckList(sbchSingleCheck);
        if(!ObjectNullUtil.isEmpty(sbchSingleChecks)){
            SbchSingleCheck sbchSingleCheck1 = sbchSingleChecks.get(0);
            returnVo = sbchSingleCheck1;
            //详情列表
            SbchSingleCheckDetail sbchSingleCheckDetail = new SbchSingleCheckDetail();
            sbchSingleCheckDetail.setInfoId(sbchSingleCheckDetail.getId());
            List<SbchSingleCheckDetail> sbchSingleCheckDetails = sbchSingleCheckDetailService.selectSbchSingleCheckDetailList(sbchSingleCheckDetail);
            returnVo.setDetailList(sbchSingleCheckDetails);
        }else{
            List<JSONObject> templeteList = getTempleteList();
            List<SbchSingleCheckDetail> r = JSONArray.parseArray(JSON.toJSONString(templeteList), SbchSingleCheckDetail.class);
            returnVo.setDetailList(r);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchSingleCheck sbchSingleCheck) {
        List<SbchSingleCheckDetail> detailList = sbchSingleCheck.getDetailList();
        SbchSingleCheck temp = new SbchSingleCheck();
        temp.setVersion(sbchSingleCheck.getVersion());
        List<SbchSingleCheck> sbchSingleChecks = sbchSingleCheckMapper.selectSbchSingleCheckList(temp);
        if(!ObjectNullUtil.isEmpty(sbchSingleChecks)){
            sbchSingleCheck.setId(sbchSingleChecks.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchSingleCheck);
            sbchSingleCheckMapper.updateSbchSingleCheck(sbchSingleCheck);
        }else{
            sbchSingleCheck.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_SINGLE_CHECK);
            sbchSingleCheck.setUnicode(code);
            sbchSingleCheck.setTitleName("单级核算策划");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchSingleCheck);
            sbchSingleCheckMapper.insertSbchSingleCheck(sbchSingleCheck);
        }
        // 清空数据库表中数据
        sbchSingleCheckDetailService.deleteSbchSingleCheckDetailByInfoId(sbchSingleCheck.getId());
        if(!ObjectNullUtil.isEmpty(detailList)) {
            //校验数据必填
            if("1".equals(sbchSingleCheck.getButtonMark())||"2".equals(sbchSingleCheck.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchSingleCheckDetail sbchSingleCheckDetail : detailList) {
                BeanUtils.copyProperties(sbchSingleCheck,sbchSingleCheckDetail);
                sbchSingleCheckDetail.setId(IdWorker.createId());
                sbchSingleCheckDetail.setDeptId(sbchSingleCheck.getDeptId());
                sbchSingleCheckDetail.setInfoId(sbchSingleCheck.getId());
            }
            sbchSingleCheckDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchSingleCheck.getButtonMark())){
            //插入确认记录
            String menuId = sbchSingleCheck.getMenuId();
            String stageIdentity = sbchSingleCheck.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
