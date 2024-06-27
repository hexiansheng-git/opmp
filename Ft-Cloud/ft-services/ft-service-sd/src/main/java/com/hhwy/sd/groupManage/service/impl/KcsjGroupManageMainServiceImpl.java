package com.hhwy.sd.groupManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageApproachStaff;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageDetail;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageMain;
import com.hhwy.sd.groupManage.domain.vo.KcsjGroupManageMainVo;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageApproachStaffMapper;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageContractMapper;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageDetailMapper;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageMainMapper;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageApproachStaffService;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageContractService;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageDetailService;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageMainService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-12-13 15:27:15
 * @remark
 */
@Service
public class KcsjGroupManageMainServiceImpl implements IKcsjGroupManageMainService {

    @Autowired
    private KcsjGroupManageMainMapper kcsjGroupManageMainMapper;

    @Autowired
    private IKcsjGroupManageContractService contractService;

    @Autowired
    private KcsjGroupManageContractMapper contractMapper;

    @Autowired
    private IKcsjGroupManageDetailService detailService;

    @Autowired
    private KcsjGroupManageDetailMapper detailMapper;

    @Autowired
    private IKcsjGroupManageApproachStaffService staffService;

    @Autowired
    private KcsjGroupManageApproachStaffMapper staffMapper;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(KcsjGroupManageMainServiceImpl.class);


    public KcsjGroupManageMain getKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        return kcsjGroupManageMainMapper.getKcsjGroupManageMain(kcsjGroupManageMain);
    }

    /**
     * 根据分包类型获取主表数据
     * @param subpackageType
     * @return
     */
    public KcsjGroupManageMain getBySubpackageType(String subpackageType,Long projectId){
        KcsjGroupManageMain query = new KcsjGroupManageMain();
        query.setSubpackageType(subpackageType);
        query.setProjectId(projectId);
        return kcsjGroupManageMainMapper.getKcsjGroupManageMain(query);
    }

    public List<KcsjGroupManageMain> getKcsjGroupManageMainList(KcsjGroupManageMain kcsjGroupManageMain) {
        return kcsjGroupManageMainMapper.getKcsjGroupManageMainList(kcsjGroupManageMain);
    }

    @Transactional
    public int insertKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        kcsjGroupManageMain.setCreateUserName(SecurityUtils.getUserName());
        kcsjGroupManageMain.setCreateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.insertKcsjGroupManageMain(kcsjGroupManageMain);
    }

    @Transactional
    public int insertKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList) {
        for (KcsjGroupManageMain kcsjGroupManageMain : kcsjGroupManageMainList) {
            kcsjGroupManageMain.setId(IdWorker.createId());
            kcsjGroupManageMain.setCreateUser(SecurityUtils.getUserName());
            kcsjGroupManageMain.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageMainMapper.insertKcsjGroupManageMainList(kcsjGroupManageMainList);
    }

    @Transactional
    public int updateKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.updateKcsjGroupManageMain(kcsjGroupManageMain);
    }

    @Transactional
    public int updateKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList) {
        for (KcsjGroupManageMain kcsjGroupManageMain : kcsjGroupManageMainList) {
            kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
            kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageMainMapper.updateKcsjGroupManageMainList(kcsjGroupManageMainList);
    }

    @Transactional
    public int deleteKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.deleteKcsjGroupManageMain(kcsjGroupManageMain);
    }

    public void deleteById(Long id){
        KcsjGroupManageMain delParam = new KcsjGroupManageMain();
        delParam.setId(id);
        kcsjGroupManageMainMapper.deleteKcsjGroupManageMain(delParam);
    }

    @Transactional
    public int deleteKcsjGroupManageMainByPks(List<Long> kcsjGroupManageMainPkList) {
        return kcsjGroupManageMainMapper.deleteKcsjGroupManageMainByPks(kcsjGroupManageMainPkList);
    }

    @Override
    public KcsjGroupManageMainVo getKcsjGroupManageMainVo(KcsjGroupManageMain kcsjGroupManageMain) {
        String subpackageType = kcsjGroupManageMain.getSubpackageType();
        Long projectId = kcsjGroupManageMain.getProjectId();
        CommonAssert.notBlank(subpackageType,"分包类型不能为空！");

        KcsjGroupManageMainVo groupManageMainVo = new KcsjGroupManageMainVo();

        /*根据分包类型查询主表*/
        KcsjGroupManageMain groupManageMain = this.getBySubpackageType(subpackageType,projectId);
        if(groupManageMain == null){
            groupManageMainVo.setSubpackageType(subpackageType);
            groupManageMainVo.setContractList(new ArrayList<>());
            return groupManageMainVo;
        }

        Long mainId = groupManageMain.getId();

        /*获取合同集合*/
        List<KcsjGroupManageContract> contractList = contractService.getListByMainId(mainId);
        /*获取详情表集合*/
        List<KcsjGroupManageDetail> detailList = detailService.getListByMainId(mainId);
        /*获取进场人员表集合*/
        List<KcsjGroupManageApproachStaff> staffList = staffService.getListByMainId(mainId);

        Map<Long, List<KcsjGroupManageApproachStaff>> staffMap = staffList.stream().collect(Collectors.groupingBy(KcsjGroupManageApproachStaff::getDetailId));
        for (KcsjGroupManageDetail detail : detailList) {
            Long detailId = detail.getId();
            if(staffMap.containsKey(detailId)){
                detail.setStaffList(staffMap.get(detailId));
            }else {
                detail.setStaffList(new ArrayList<>());
            }
        }

        Map<Long, List<KcsjGroupManageDetail>> detailMap = detailList.stream().collect(Collectors.groupingBy(KcsjGroupManageDetail::getContractId));
        for (KcsjGroupManageContract contract : contractList) {
            Long contractId = contract.getId();
            if(detailMap.containsKey(contractId)){
                List<KcsjGroupManageDetail> list = detailMap.get(contractId);
                list = ListTreeUtil.formatTree(
                        list,
                        o -> o.getPid() == null,
                        (r, n) -> r.getId().equals(n.getPid()),
                        KcsjGroupManageDetail::getChildren,
                        KcsjGroupManageDetail::setChildren);
                contract.setDetailList(list);
            }else {
                contract.setDetailList(new ArrayList<>());
            }
        }

        groupManageMainVo.setSubpackageType(subpackageType);
        groupManageMainVo.setBusinessModel(groupManageMain.getBusinessModel());
        groupManageMainVo.setFileGroupId(groupManageMain.getFileGroupId());
        groupManageMainVo.setContractList(contractList);
        return groupManageMainVo;
    }

    @Override
    @Transactional
    public void save(KcsjGroupManageMainVo kcsjGroupManageMainVo) {
        List<KcsjGroupManageContract> contractList = kcsjGroupManageMainVo.getContractList();
        for (int i = 0; i < contractList.size(); i++) {
            List<KcsjGroupManageDetail> detailList = contractList.get(i).getDetailList();
            for (int j = 0; j < detailList.size(); j++) {
                List<KcsjGroupManageDetail> children = detailList.get(j).getChildren();
                detailList.get(j).setRst(children);
                digui(children);
            }
        }
        String subpackageType = kcsjGroupManageMainVo.getSubpackageType();
        CommonAssert.notBlank(subpackageType,"分包类型不能为空！");
        /*删除所有数据*/
        this.deleteAllData(subpackageType);
        /*插入所有数据*/
        this.addAllData(kcsjGroupManageMainVo);
        //数据同步总部
        logger.info("收到顶顶顶顶【{}】",JSONObject.toJSONString(kcsjGroupManageMainVo));
        syncDataToGm(kcsjGroupManageMainVo);
    }

    void digui(List<KcsjGroupManageDetail> rst){
        if(CollectionUtils.isEmpty(rst)){
            return;
        }
        for (KcsjGroupManageDetail detail:rst) {
            List<KcsjGroupManageDetail> children = detail.getChildren();
            if(CollectionUtils.isNotEmpty(children)){
                detail.setRst(children);
                digui(children);
            }

        }
    }

    @Override
    @Transactional
    public void syncDataToGm(KcsjGroupManageMainVo kcsjGroupManageMainVo) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            logger.info("作妖了又【{}】",JSONObject.toJSONString(kcsjGroupManageMainVo));
            rocketMQTemplate.convertAndSend("kcsj_group_manage_contract:tenantSuccess", JSONObject.toJSONString(kcsjGroupManageMainVo));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("kcsj_group_manage_contract");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(kcsjGroupManageMainVo));
            logger.error("kcsj_group_manage_contract同步失败【{}】,时间：【{}】",JSONObject.toJSONString(kcsjGroupManageMainVo),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    /**
     * 删除所有数据
     * @param subpackageType
     * @return
     */
    @Transactional
    public void deleteAllData(String subpackageType){
        KcsjGroupManageMain groupManageMain = this.getBySubpackageType(subpackageType,null);
        if(groupManageMain != null){
            Long id = groupManageMain.getId();
            this.deleteById(id);
            contractService.deleteByMainId(id);
            detailService.deleteByMainId(id);
            staffService.deleteByMainId(id);
        }
    }

    /**
     * 插入数据
     * @param kcsjGroupManageMainVo
     */
    @Transactional
    public void addAllData(KcsjGroupManageMainVo kcsjGroupManageMainVo){
        String businessModel = kcsjGroupManageMainVo.getBusinessModel();
        Long projectId = kcsjGroupManageMainVo.getProjectId();
        String projectName = kcsjGroupManageMainVo.getProjectName();
        String regionId = kcsjGroupManageMainVo.getRegionId();
        String regionName = kcsjGroupManageMainVo.getRegionName();
        List<KcsjGroupManageContract> contractList = kcsjGroupManageMainVo.getContractList();
        if(StringUtils.isBlank(businessModel) && CollectionUtils.isEmpty(contractList)){
            return;
        }
        String subpackageType = kcsjGroupManageMainVo.getSubpackageType();
        /*插入主表数据*/
        long mainId = this.addMain(subpackageType, businessModel, kcsjGroupManageMainVo.getFileGroupId(),projectId,projectName,regionId,regionName);
        /*插入子表数据*/
        this.addContractList(mainId,subpackageType,contractList);
    }

    /**
     * 插入主表数据
     * @param subpackageType
     * @param businessModel
     * @param fileGroupId
     * @return
     */
    @Transactional
    public long addMain(String subpackageType,String businessModel,String fileGroupId,Long projectId,String projectName,String regionId,String regionName){
        KcsjGroupManageMain groupManageMain = new KcsjGroupManageMain();
        long id = IdWorker.createId();
        groupManageMain.setId(id);
        groupManageMain.setSubpackageType(subpackageType);
        groupManageMain.setBusinessModel(businessModel);
        groupManageMain.setFileGroupId(fileGroupId);
        groupManageMain.setProjectId(projectId);
        groupManageMain.setProjectName(projectName);
        groupManageMain.setRegionId(Long.parseLong(regionId));
        groupManageMain.setRegionName(regionName);
        this.insertKcsjGroupManageMain(groupManageMain);
        return id;
    }

    /**
     * 插入合同数据
     * @param mainId
     * @param subpackageType
     * @param contractList
     */
    public void addContractList(Long mainId,String subpackageType,List<KcsjGroupManageContract> contractList){
        if(CollectionUtils.isEmpty(contractList)){
            return;
        }
        int sort = 1;
        for (KcsjGroupManageContract contract : contractList) {
            long contractId = IdWorker.createId();
            /*插入详情表数据*/
            this.addDetailList(mainId,contractId,subpackageType,contract.getDetailList());
            contract.setId(contractId);
            contract.setMainId(mainId);
            contract.setSort(sort++);
            contract.setSubpackageType(subpackageType);
            contract.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            contract.setCreateUser(SecurityUtils.getUserName());
            contract.setCreateTime(DateUtils.getNowDate());
        }

        /*插入合同数据*/
        contractMapper.insertKcsjGroupManageContractList(contractList);
    }

    /**
     * 插入详情表数据
     * @param mainId
     * @param contractId
     * @param subpackageType
     * @param detailList
     */
    public void addDetailList(Long mainId,Long contractId,String subpackageType,List<KcsjGroupManageDetail> detailList){
        if(CollectionUtils.isEmpty(detailList)) {
            return;
        }
        List<KcsjGroupManageDetail> tileList = ListTreeUtil.formatList(
                detailList,
                KcsjGroupManageDetail::setId,
                KcsjGroupManageDetail::setPid,
                KcsjGroupManageDetail::setSort,
                KcsjGroupManageDetail::setLeaf,
                KcsjGroupManageDetail::getChildren,
                KcsjGroupManageDetail::setChildren);

        List<KcsjGroupManageApproachStaff> staffAddList = new ArrayList<>();
        for (KcsjGroupManageDetail detail : tileList) {
            Long detailId = detail.getId();
            this.disposeStaffList(mainId,contractId,detailId,subpackageType,staffAddList,detail.getStaffList());
            detail.setMainId(mainId);
            detail.setContractId(contractId);
            detail.setSubpackageType(subpackageType);
            detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            detail.setCreateUser(SecurityUtils.getUserName());
            detail.setCreateTime(DateUtils.getNowDate());
        }

        /*插入详情表数据*/
        detailMapper.insertKcsjGroupManageDetailList(tileList);

        /*插入人员进出场数据*/
        if(CollectionUtils.isNotEmpty(staffAddList)){
            staffMapper.insertKcsjGroupManageApproachStaffList(staffAddList);
        }
    }

    /**
     * 处理进出场人员数据
     * @param mainId
     * @param contractId
     * @param detailId
     * @param subpackageType
     * @param staffAddList
     * @param staffList
     */
    public void disposeStaffList(Long mainId,Long contractId,Long detailId,String subpackageType,List<KcsjGroupManageApproachStaff> staffAddList,List<KcsjGroupManageApproachStaff> staffList){
        if(CollectionUtils.isEmpty(staffList)){
            return;
        }
        for (KcsjGroupManageApproachStaff staff : staffList) {
            staff.setId(IdWorker.createId());
            staff.setMainId(mainId);
            staff.setContractId(contractId);
            staff.setDetailId(detailId);
            staff.setSubpackageType(subpackageType);
            staff.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            staff.setCreateUserName(SecurityUtils.getUserName());
            staff.setCreateTime(DateUtils.getNowDate());
            staffAddList.add(staff);
        }
    }
}
