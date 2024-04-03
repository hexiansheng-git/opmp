package com.hhwy.sp.buildSchemeManage.review.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.flow.TaskResource;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.buildSchemeManage.review.constant.ReviewFlowNodeMark;
import com.hhwy.sp.buildSchemeManage.review.constant.TaskStatus;
import com.hhwy.sp.buildSchemeManage.review.domain.*;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.*;
import com.hhwy.sp.buildSchemeManage.review.mapper.*;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.common.warn.SgjsWarnRecord;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Service
@Slf4j
public class SgjsBuildSchemeReviewServiceImpl implements ISgjsBuildSchemeReviewService {

    @Autowired
    private SgjsBuildSchemeReviewMapper sgjsBuildSchemeReviewMapper;

    @Autowired
    private SgjsBuildSchemeReviewStaffMapper sgjsBuildSchemeReviewStaffMapper;

    @Autowired
    private SgjsBuildSchemeStaffOpinionMapper sgjsBuildSchemeStaffOpinionMapper;

    @Autowired
    private SgjsBuildSchemeReviewOpinionMapper sgjsBuildSchemeReviewOpinionMapper;

    @Autowired
    private SgjsBuildSchemeReviewOpinionRecordMapper sgjsBuildSchemeReviewOpinionRecordMapper;

    @Autowired
    private SgjsBuildSchemeStaffOpinionRecordMapper sgjsBuildSchemeStaffOpinionRecordMapper;

    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;

    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private FlowServiceApi flowServiceApi;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    public List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
    }

    @Transactional
    public int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setId(IdWorker.createId());
        sgjsBuildSchemeReview.setSchemeInitiatorName(SecurityUtils.getSysUser().getNickName());
        sgjsBuildSchemeReview.setSchemeInitiatorId(SecurityUtils.getSysUser().getUserName());
        sgjsBuildSchemeReview.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        sgjsBuildSchemeReview.setCreateUserName(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setCreateTime(DateUtils.getNowDate());
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        sgjsBuildSchemeReview.setProjectName(projectDto.getProjectName());
        sgjsBuildSchemeReview.setProjectId(projectDto.getProjectId());
        sgjsBuildSchemeReview.setRegionId(projectDto.getRegionId());
        sgjsBuildSchemeReview.setRegionName(projectDto.getRegionName());
        sgjsBuildSchemeReview.setProjectCode(projectDto.getProjectCode());
        return sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList) {
        for (SgjsBuildSchemeReview sgjsBuildSchemeReview : sgjsBuildSchemeReviewList) {
            sgjsBuildSchemeReview.setId(IdWorker.createId());
            sgjsBuildSchemeReview.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReview.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewList);
    }

    @Transactional
    public int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList) {
        for (SgjsBuildSchemeReview sgjsBuildSchemeReview : sgjsBuildSchemeReviewList) {
            sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewMapper.deleteSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewByPks(List<Long> sgjsBuildSchemeReviewPkList) {
        return sgjsBuildSchemeReviewMapper.deleteSgjsBuildSchemeReviewByPks(sgjsBuildSchemeReviewPkList);
    }

    /**
     * 台账
     * @param queryVo
     * @return
     */
    @Override
    public List<SgjsBuildSchemeReview> getListByQueryVo(BuildSchemeReviewQueryVo queryVo) {
        return sgjsBuildSchemeReviewMapper.getListByQueryVo(queryVo);
    }

    /**
     * 详情
     * @param detailQueryVo
     * @return
     */
    @Override
    public SgjsBuildSchemeReview getDetail(BuildSchemeReviewDetailQueryVo detailQueryVo) {
        Long id = detailQueryVo.getId();
        String type = detailQueryVo.getType();
        String flowNodeMark = detailQueryVo.getFlowNodeMark();

        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(id);
        review.setFlowNodeMark(flowNodeMark);
        String schemeLevel = review.getSchemeLevel();

        if("1".equals(type) || "2".equals(type) || "1".equals(schemeLevel)){
            //查看/发起审批/方案分级为1或4
            return review;
        }
        if("3".equals(type)){
            //查看
            SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.getLookOverData(id);
            review.setReviewOpinionRecord(reviewOpinionRecord);
        }

        if("4".equals(type)){
            //处理
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                //二三级方案
                if(ReviewFlowNodeMark.FlowNodeMark1.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark2.equals(flowNodeMark)){
                    //区域中心或海外事业部角色审批节点
                    List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewId(id, flowNodeMark);
                    List<SgjsBuildSchemeReviewStaff> staffListRes = this.assembleStaffList(staffList);
                    review.setReviewStaffList(staffListRes);
                }else if(ReviewFlowNodeMark.FlowNodeMark3.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark4.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark5.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark6.equals(flowNodeMark)){
                    //专家或部门审批节点：根据当前登录人查询各自数据
                    String userName = SecurityUtils.getUserName();
//                    String userName = detailQueryVo.getUserName();
                    SgjsBuildSchemeReviewStaff staffQuery = new SgjsBuildSchemeReviewStaff();
                    staffQuery.setReviewStaffId(userName);
                    staffQuery.setReviewId(id);
                    SgjsBuildSchemeReviewStaff staff = sgjsBuildSchemeReviewStaffMapper.getSgjsBuildSchemeReviewStaff(staffQuery);

                    BuildSchemeStaffOpinionVo staffOpinionVo = new BuildSchemeStaffOpinionVo();
                    if(staff != null){
                        staffOpinionVo.setReviewStaffId(staff.getReviewStaffId());
                        staffOpinionVo.setScore(staff.getScore());
                    }
                    staffOpinionVo.setFlowNodeMark(flowNodeMark);

                    SgjsBuildSchemeStaffOpinion staffOpinionQuery = new SgjsBuildSchemeStaffOpinion();
                    staffOpinionQuery.setReviewId(id);
                    staffOpinionQuery.setReviewStaffId(userName);
                    List<SgjsBuildSchemeStaffOpinion> staffOpinionList = sgjsBuildSchemeStaffOpinionMapper.getSgjsBuildSchemeStaffOpinionList(staffOpinionQuery);
                    staffOpinionVo.setStaffOpinionList(staffOpinionList);
                    review.setStaffOpinionVo(staffOpinionVo);
                }else if(ReviewFlowNodeMark.FlowNodeMark7.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark8.equals(flowNodeMark)){
                    //区域总工审批节点  汇总 3 ，4节点数据    海外事业部总工审批节点  汇总 3 ，4 ，5 ，6节点数据
                    BuildSchemeReviewOpinionVo reviewOpinionVo = new BuildSchemeReviewOpinionVo();
                    SgjsBuildSchemeReviewOpinion reviewOpinionQuery = new SgjsBuildSchemeReviewOpinion();
                    reviewOpinionQuery.setReviewId(id);
                    String flowNodeMarkQuery;
                    if("7".equals(flowNodeMark)){
                        flowNodeMarkQuery = "1";
                    }else {
                        flowNodeMarkQuery = "2";
                    }
                    SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(reviewOpinionQuery);
                    if(reviewOpinion != null){
                        reviewOpinionVo.setRegionChiefOpinion(reviewOpinion.getRegionChiefOpinion());
                        reviewOpinionVo.setRegionChiefDetailOpinion(reviewOpinion.getRegionChiefDetailOpinion());
                        reviewOpinionVo.setOverseasChiefOpinion(reviewOpinion.getOverseasChiefOpinion());
                        reviewOpinionVo.setOverseasChiefDetailOpinion(reviewOpinion.getOverseasChiefDetailOpinion());
                    }
                    List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(id, flowNodeMarkQuery);
                    reviewOpinionVo.setGatherVoList(staffOpinionGatherVoList);
                    Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
                    reviewOpinionVo.setScore(average);
                    review.setReviewOpinionVo(reviewOpinionVo);
                }else if(ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark10.equals(flowNodeMark)){
                    //海外事业部总工意见为修改后通过后的审批节点
                    SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.getMaxReviewOpinionRecord(id);
                    review.setReviewOpinionRecord(reviewOpinionRecord);
                }else {
                    //节点标识为空：当前为驳回后的发起人节点，需要查看历史数据
                    SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.getMaxReviewOpinionRecord(id);
                    review.setReviewOpinionRecord(reviewOpinionRecord);
                }
            }else if("4".equals(schemeLevel) && "1".equals(flowNodeMark)){
                //四级方案区域中心审批节点
                List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewId(id, flowNodeMark);
                List<SgjsBuildSchemeReviewStaff> staffListRes = this.assembleStaffList(staffList);
                review.setReviewStaffList(staffListRes);
            }
        }
        return review;
    }

    private List<SgjsBuildSchemeReviewStaff> assembleStaffList(List<SgjsBuildSchemeReviewStaff> staffList){
        List<SgjsBuildSchemeReviewStaff> resultList = new ArrayList<>();
        Map<String, List<SgjsBuildSchemeReviewStaff>> staffMap = staffList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeReviewStaff::getStaffMark));
        for (Map.Entry<String, List<SgjsBuildSchemeReviewStaff>> entry : staffMap.entrySet()) {
            List<SgjsBuildSchemeReviewStaff> value = entry.getValue();
            if(value.size() == 1){
                SgjsBuildSchemeReviewStaff staff = value.get(0);
                ReviewStaff reviewStaff = new ReviewStaff(staff.getReviewStaffId(), staff.getReviewStaffName());
                staff.setReviewStaffList(Collections.singletonList(reviewStaff));
                resultList.add(staff);
            }else {
                SgjsBuildSchemeReviewStaff staff = value.get(0);
                String reviewStaffNames = value.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffName).collect(Collectors.joining(","));
                String reviewStaffIds = value.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.joining(","));
                List<ReviewStaff> reviewStaffList = new ArrayList<>();
                for (SgjsBuildSchemeReviewStaff reviewStaff : value) {
                    ReviewStaff staff1 = new ReviewStaff(reviewStaff.getReviewStaffId(), reviewStaff.getReviewStaffName());
                    reviewStaffList.add(staff1);
                }
                staff.setReviewStaffList(reviewStaffList);
                staff.setReviewStaffName(reviewStaffNames);
                staff.setReviewStaffId(reviewStaffIds);
                resultList.add(staff);
            }
        }
        return resultList;
    }

    /**
     * 获取选择的审批人信息
     * @param reviewId
     * @param flowNodeMark 选择审批人的节点标识  1或2
     * @return
     */
    private List<BuildSchemeStaffOpinionGatherVo> getStaffOpinionGatherVoList(Long reviewId,String flowNodeMark){
        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = new ArrayList<>();
        List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewIdGroupByUser(reviewId, flowNodeMark);
        if(CollectionUtils.isEmpty(staffList)){
            return staffOpinionGatherVoList;
        }
        List<String> reviewStaffIdList = staffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toList());
        List<SgjsBuildSchemeStaffOpinion> staffOpinionList = sgjsBuildSchemeStaffOpinionMapper.getListByReviewStaffIdList(reviewId, reviewStaffIdList);
        Map<String, List<SgjsBuildSchemeStaffOpinion>> staffOpinionMap = staffOpinionList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeStaffOpinion::getReviewStaffId));
        for (SgjsBuildSchemeReviewStaff staff : staffList) {
            BuildSchemeStaffOpinionGatherVo staffOpinionGatherVo = new BuildSchemeStaffOpinionGatherVo();
            staffOpinionGatherVo.setReviewStaffName(staff.getReviewStaffName());
            staffOpinionGatherVo.setReviewStaffId(staff.getReviewStaffId());
            staffOpinionGatherVo.setStaffType(staff.getStaffType());
            staffOpinionGatherVo.setScore(staff.getScore());
            staffOpinionGatherVo.setSubmitTime(staff.getSubmitTime());

            List<SgjsBuildSchemeStaffOpinion> staffOpinions = staffOpinionMap.get(staff.getReviewStaffId());
            if(CollectionUtils.isNotEmpty(staffOpinions)){
                List<String> reviewOpinionList = staffOpinions.stream().map(SgjsBuildSchemeStaffOpinion::getReviewOpinion).collect(Collectors.toList());
                List<String> fileGroupIdList = staffOpinions.stream().map(SgjsBuildSchemeStaffOpinion::getFileGroupId).filter(Objects::nonNull).collect(Collectors.toList());
                staffOpinionGatherVo.setReviewOpinionList(reviewOpinionList);
                staffOpinionGatherVo.setFileGroupIdList(fileGroupIdList);
            }
            staffOpinionGatherVoList.add(staffOpinionGatherVo);
        }
        return staffOpinionGatherVoList;
    }

    /**
     * 获取最新的记录数据
     * @param reviewId
     * @return
     */
    private SgjsBuildSchemeReviewOpinionRecord getMaxReviewOpinionRecord(Long reviewId){
        int maxSerialNumber = sgjsBuildSchemeReviewOpinionRecordMapper.getMaxSerialNumber(reviewId);
        SgjsBuildSchemeReviewOpinionRecord query = new SgjsBuildSchemeReviewOpinionRecord();
        query.setReviewId(reviewId);
        query.setSerialNumber(maxSerialNumber);
        SgjsBuildSchemeReviewOpinionRecord opinionRecord = sgjsBuildSchemeReviewOpinionRecordMapper.getSgjsBuildSchemeReviewOpinionRecord(query);
        if(opinionRecord == null){
            return new SgjsBuildSchemeReviewOpinionRecord();
        }

        SgjsBuildSchemeStaffOpinionRecord staffOpinionRecordQuery = new SgjsBuildSchemeStaffOpinionRecord();
        staffOpinionRecordQuery.setOpinionRecordId(opinionRecord.getId());
        List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = sgjsBuildSchemeStaffOpinionRecordMapper.getSgjsBuildSchemeStaffOpinionRecordList(staffOpinionRecordQuery);
        for (SgjsBuildSchemeStaffOpinionRecord staffOpinionRecord : staffOpinionRecordList) {
            String reviewOpinion = staffOpinionRecord.getReviewOpinion();
            if(StringUtils.isNotBlank(reviewOpinion)){
                String[] reviewOpinionArr = reviewOpinion.split("==>");
                staffOpinionRecord.setReviewOpinionList(Arrays.asList(reviewOpinionArr));
            }
            String fileGroupIdList = staffOpinionRecord.getFileGroupId();
            if(StringUtils.isNotBlank(fileGroupIdList)){
                String[] fileGroupIdArr = reviewOpinion.split(",");
                staffOpinionRecord.setFileGroupIdList(Arrays.asList(fileGroupIdArr));
            }
        }
        opinionRecord.setStaffOpinionRecordList(staffOpinionRecordList);
        return opinionRecord;
    }

    /**
     * 点击查看获取数据
     * @param reviewId
     * @return
     */
    private SgjsBuildSchemeReviewOpinionRecord getLookOverData(Long reviewId){
        SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.initReviewOpinionRecord(reviewId);

        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(reviewId, null);
        if(CollectionUtils.isNotEmpty(staffOpinionGatherVoList)){
            Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
            reviewOpinionRecord.setScore(average);
            List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = new ArrayList<>();
            for (BuildSchemeStaffOpinionGatherVo staffOpinionGatherVo : staffOpinionGatherVoList) {
                SgjsBuildSchemeStaffOpinionRecord staffOpinionRecord = new SgjsBuildSchemeStaffOpinionRecord();
                staffOpinionRecord.setReviewId(reviewId);
                staffOpinionRecord.setReviewStaffName(staffOpinionGatherVo.getReviewStaffName());
                staffOpinionRecord.setReviewStaffId(staffOpinionGatherVo.getReviewStaffId());
                staffOpinionRecord.setStaffType(staffOpinionGatherVo.getStaffType());
                List<String> opinionList = staffOpinionGatherVo.getReviewOpinionList();
                if(CollectionUtils.isNotEmpty(opinionList)){
                    String opinionStr = String.join("==>", opinionList);
                    staffOpinionRecord.setReviewOpinion(opinionStr);
                    staffOpinionRecord.setReviewOpinionList(opinionList);
                }
                List<String> fileGroupIdList = staffOpinionGatherVo.getFileGroupIdList();
                if(CollectionUtils.isNotEmpty(fileGroupIdList)){
                    String fileGroupIdStr = String.join(",", fileGroupIdList);
                    staffOpinionRecord.setFileGroupId(fileGroupIdStr);
                    staffOpinionRecord.setFileGroupIdList(fileGroupIdList);
                }
                staffOpinionRecordList.add(staffOpinionRecord);
            }
            reviewOpinionRecord.setStaffOpinionRecordList(staffOpinionRecordList);
        }
        return reviewOpinionRecord;
    }

    private SgjsBuildSchemeReviewOpinionRecord initReviewOpinionRecord(Long reviewId){
        SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = new SgjsBuildSchemeReviewOpinionRecord();
        SgjsBuildSchemeReviewOpinion reviewOpinionQuery = new SgjsBuildSchemeReviewOpinion();
        reviewOpinionQuery.setReviewId(reviewId);
        SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(reviewOpinionQuery);
        reviewOpinionRecord.setRegionChiefOpinion(reviewOpinion.getRegionChiefOpinion());
        reviewOpinionRecord.setRegionChiefDetailOpinion(reviewOpinion.getRegionChiefDetailOpinion());
        reviewOpinionRecord.setOverseasChiefOpinion(reviewOpinion.getOverseasChiefOpinion());
        reviewOpinionRecord.setOverseasChiefDetailOpinion(reviewOpinion.getOverseasChiefDetailOpinion());
        return reviewOpinionRecord;
    }

    @Override
    @Transactional
    public Long save(SgjsBuildSchemeReview review) {
        String saveType = review.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空！");
        if("add".equals(saveType) && review.getId() == null){
            //新增
            this.insertSgjsBuildSchemeReview(review);
        }
        String taskStatus = review.getTaskStatus();
        if("edit".equals(saveType) || "0".equals(taskStatus)){
            //编辑保存
            this.updateSgjsBuildSchemeReview(review);
        }
        String flowNodeMark = review.getFlowNodeMark();
        if("dispose".equals(saveType) && !"0".equals(taskStatus)){
            //处理保存
            Long id = review.getId();

            String schemeLevel = review.getSchemeLevel();
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                //二三级方案
                if(ReviewFlowNodeMark.FlowNodeMark1.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark2.equals(flowNodeMark)){
                    //区域中心或海外事业部角色审批节点
                    List<SgjsBuildSchemeReviewStaff> staffList = review.getReviewStaffList();
                    this.saveStaffList(id,flowNodeMark,staffList);
                }else if(ReviewFlowNodeMark.FlowNodeMark3.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark4.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark5.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark6.equals(flowNodeMark)){
                    //专家或部门审批节点
                    String userName = SecurityUtils.getUserName();
//                    String userName = review.getUserName();
                    review.setUserName(userName);
                    BuildSchemeStaffOpinionVo staffOpinionVo = review.getStaffOpinionVo();
                    this.saveStaffOpinionVo(id, flowNodeMark, userName, staffOpinionVo);
                } else if (ReviewFlowNodeMark.FlowNodeMark7.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark8.equals(flowNodeMark)) {
                    //区域总工审批节点 / 海外事业部总工审批节点
                    BuildSchemeReviewOpinionVo reviewOpinionVo = review.getReviewOpinionVo();
                    this.saveReviewOpinionVo(id, flowNodeMark, reviewOpinionVo);
                } else if (ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark10.equals(flowNodeMark)) {
                    //海外事业部总工意见为修改后通过后的审批节点：需要修改字段-修改结果
                    SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = review.getReviewOpinionRecord();
                    if (reviewOpinionRecord != null) {
                        List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = reviewOpinionRecord.getStaffOpinionRecordList();
                        this.updateStaffOpinionRecordList(staffOpinionRecordList);
                    }
                } else {
                    //节点标识为空：当前为驳回后的发起人节点
                    SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = review.getReviewOpinionRecord();
                    if (reviewOpinionRecord != null) {
                        List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = reviewOpinionRecord.getStaffOpinionRecordList();
                        this.updateStaffOpinionRecordList(staffOpinionRecordList);
                    }
                }
            } else if ("4".equals(schemeLevel) && ReviewFlowNodeMark.FlowNodeMark1.equals(flowNodeMark)) {
                //四级方案区域中心审批节点
                List<SgjsBuildSchemeReviewStaff> staffList = review.getReviewStaffList();
                this.saveStaffList(id, flowNodeMark, staffList);
            }
        }

        if(("1".equals(taskStatus) || "4".equals(taskStatus)) && StringUtils.isNotBlank(flowNodeMark) && !ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark) && !ReviewFlowNodeMark.FlowNodeMark10.equals(flowNodeMark)){
            review.setProcessStatus("no");
            sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
        }
        return review.getId();
    }

    private void updateStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList) {
        if (CollectionUtils.isNotEmpty(staffOpinionRecordList)) {
            sgjsBuildSchemeStaffOpinionRecordMapper.updateUpdateResult(staffOpinionRecordList);
        }
    }

    /**
     * 针对7,8节点的意见详情
     *
     * @param reviewId
     * @param flowNodeMark
     * @param reviewOpinionVo
     */
    private void saveReviewOpinionVo(Long reviewId, String flowNodeMark, BuildSchemeReviewOpinionVo reviewOpinionVo) {
        SgjsBuildSchemeReviewOpinion queryParam = new SgjsBuildSchemeReviewOpinion();
        queryParam.setReviewId(reviewId);
//        queryParam.setFlowNodeMark(flowNodeMark);
        SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(queryParam);
        if (reviewOpinion != null) {
            reviewOpinion.setUpdateUser(SecurityUtils.getUserName());
            reviewOpinion.setUpdateTime(DateUtils.getNowDate());
            reviewOpinion.setScore(reviewOpinionVo.getScore());
            reviewOpinion.setRegionChiefOpinion(reviewOpinionVo.getRegionChiefOpinion());
            reviewOpinion.setRegionChiefDetailOpinion(reviewOpinionVo.getRegionChiefDetailOpinion());
            reviewOpinion.setOverseasChiefOpinion(reviewOpinionVo.getOverseasChiefOpinion());
            reviewOpinion.setOverseasChiefDetailOpinion(reviewOpinionVo.getOverseasChiefDetailOpinion());
            sgjsBuildSchemeReviewOpinionMapper.updateSgjsBuildSchemeReviewOpinion(reviewOpinion);
        } else {
            reviewOpinion = new SgjsBuildSchemeReviewOpinion();
            reviewOpinion.setId(IdWorker.createId());
            reviewOpinion.setReviewId(reviewId);
            reviewOpinion.setRegionChiefOpinion(reviewOpinionVo.getRegionChiefOpinion());
            reviewOpinion.setRegionChiefDetailOpinion(reviewOpinionVo.getRegionChiefDetailOpinion());
            reviewOpinion.setOverseasChiefOpinion(reviewOpinionVo.getOverseasChiefOpinion());
            reviewOpinion.setOverseasChiefDetailOpinion(reviewOpinionVo.getOverseasChiefDetailOpinion());
            reviewOpinion.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            reviewOpinion.setCreateUserName(SecurityUtils.getUserName());
            reviewOpinion.setCreateTime(DateUtils.getNowDate());
            sgjsBuildSchemeReviewOpinionMapper.insertSgjsBuildSchemeReviewOpinion(reviewOpinion);
        }
    }

    /**
     * 针对3,4,5,6节点的意见详情数据
     *
     * @param reviewId
     * @param flowNodeMark
     * @param userName
     * @param staffOpinionVo
     */
    private void saveStaffOpinionVo(Long reviewId, String flowNodeMark, String userName, BuildSchemeStaffOpinionVo staffOpinionVo) {
        //删除当前登录人旧意见
        SgjsBuildSchemeStaffOpinion delParam = new SgjsBuildSchemeStaffOpinion();
        delParam.setReviewId(reviewId);
        delParam.setReviewStaffId(userName);
        sgjsBuildSchemeStaffOpinionMapper.deleteSgjsBuildSchemeStaffOpinion(delParam);

        //修改分数
        sgjsBuildSchemeReviewStaffMapper.updateScoreByUser(reviewId,userName,staffOpinionVo.getScore());

        //保存意见
        List<SgjsBuildSchemeStaffOpinion> staffOpinionList = staffOpinionVo.getStaffOpinionList();
        if(CollectionUtils.isEmpty(staffOpinionList)){
            return;
        }
        int sort = 1;
        for (SgjsBuildSchemeStaffOpinion staffOpinion : staffOpinionList) {
            staffOpinion.setId(IdWorker.createId());
            staffOpinion.setReviewId(reviewId);
            staffOpinion.setReviewStaffId(userName);
            staffOpinion.setReviewStaffName(SecurityUtils.getSysUser().getNickName());
            staffOpinion.setFlowNodeMark(flowNodeMark);
            staffOpinion.setSort(sort++);
            staffOpinion.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            staffOpinion.setCreateUserName(SecurityUtils.getUserName());
            staffOpinion.setCreateTime(DateUtils.getNowDate());
        }
        sgjsBuildSchemeStaffOpinionMapper.insertSgjsBuildSchemeStaffOpinionList(staffOpinionList);
    }

    /**
     * 针对1，2节点保存选择的审批人
     * @param reviewId
     * @param flowNodeMark
     * @param staffList
     */
    @Transactional
    public void saveStaffList(Long reviewId, String flowNodeMark, List<SgjsBuildSchemeReviewStaff> staffList) {
        //根据reviewId和节点标识删除原有数据
        SgjsBuildSchemeReviewStaff delParam = new SgjsBuildSchemeReviewStaff();
        delParam.setReviewId(reviewId);
        delParam.setFlowNodeMark(flowNodeMark);
        sgjsBuildSchemeReviewStaffMapper.deleteSgjsBuildSchemeReviewStaff(delParam);

        //保存审批人
        if(CollectionUtils.isEmpty(staffList)){
            return;
        }
        List<SgjsBuildSchemeReviewStaff> insertList = new ArrayList<>();
        for (SgjsBuildSchemeReviewStaff staff : staffList) {
            List<ReviewStaff> reviewStaffList = staff.getReviewStaffList();
            if(reviewStaffList != null && reviewStaffList.size() > 0){
                int sort = 1;
                for (ReviewStaff reviewStaff : reviewStaffList) {
                    SgjsBuildSchemeReviewStaff insertStaff = new SgjsBuildSchemeReviewStaff();
                    insertStaff.setId(IdWorker.createId());
                    insertStaff.setReviewId(reviewId);
                    insertStaff.setStaffMark(staff.getStaffMark());
                    insertStaff.setDescription(staff.getDescription());
                    insertStaff.setFlowNodeMark(flowNodeMark);
                    insertStaff.setReviewStaffId(reviewStaff.getReviewStaffId());
                    insertStaff.setReviewStaffName(reviewStaff.getReviewStaffName());
                    insertStaff.setStaffType(staff.getStaffType());
                    insertStaff.setSort(sort++);
                    insertStaff.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    insertStaff.setCreateUserName(SecurityUtils.getUserName());
                    insertStaff.setCreateTime(DateUtils.getNowDate());
                    insertList.add(insertStaff);
                }
            }
        }
        if(insertList.size() > 0){
            this.checkSingle(reviewId,flowNodeMark,insertList);
            sgjsBuildSchemeReviewStaffMapper.insertSgjsBuildSchemeReviewStaffList(insertList);
        }
    }

    private void checkSingle(Long reviewId, String flowNodeMark, List<SgjsBuildSchemeReviewStaff> insertList) {
        Map<String, List<SgjsBuildSchemeReviewStaff>> reviewStaffMap = insertList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeReviewStaff::getStaffType));
        if(reviewStaffMap.size() > 1){
            List<SgjsBuildSchemeReviewStaff> expertStaffList = null;
            List<SgjsBuildSchemeReviewStaff> deptStaffList = null;
            if(reviewStaffMap.containsKey("1")){
                expertStaffList = reviewStaffMap.get("1");
            }

            if(reviewStaffMap.containsKey("2")){
                deptStaffList = reviewStaffMap.get("2");
            }
            if(CollectionUtils.isNotEmpty(expertStaffList) && CollectionUtils.isNotEmpty(deptStaffList)){
                Set<String> expertStaffIdSet = expertStaffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toSet());
                Set<String> deptStaffIdSet = deptStaffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toSet());
                if(CollectionUtils.containsAny(expertStaffIdSet,deptStaffIdSet)){
                    throw new RuntimeException("评审专家与部门评审人员不能相同！");
                }
            }
        }

        List<SgjsBuildSchemeReviewStaff> existStaffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewIdGroupByUser(reviewId, null);
        if(CollectionUtils.isEmpty(existStaffList)){
            return;
        }
        Set<String> insertStaffIdSet = insertList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toSet());
        Set<String> existStaffSet = existStaffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toSet());
        if(CollectionUtils.containsAny(insertStaffIdSet,existStaffSet)){
            String name = ReviewFlowNodeMark.FlowNodeMark1.equals(flowNodeMark)?"海外事业部":"区域中心";
            String users = existStaffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffName).collect(Collectors.joining(","));
            throw new RuntimeException("当前页面评审人员与 " + name + " 节点评审人员不能相同！ " + name + "节点审批人员有 " + users);
        }
    }

    @Override
    public List<SgjsBuildSchemeReview> getListByIds(List<Long> ids) {
        return sgjsBuildSchemeReviewMapper.getListByIds(ids);
    }

    @Override
    public String sync() {
        int syncNumTotal = 0;
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(null);
        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            return "已同步 " + syncNumTotal + " 条数据！";
        }


        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewMapper.getListByQueryVo(new BuildSchemeReviewQueryVo());
        SysUser sysUser = SecurityUtils.getSysUser();
        String userName = sysUser.getUserName();
        String nickName = sysUser.getNickName();
        if(CollectionUtils.isEmpty(reviewList)){
            List<SgjsBuildSchemeReview> reviewListNew = new ArrayList<>();
            for (SgjsBuildSchemeList schemeList : lastValidSchemeListList) {
                this.setInsertList(reviewListNew,schemeList,userName,nickName);
            }
            syncNumTotal = reviewListNew.size();
            sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(reviewListNew);
            return "已同步 " + syncNumTotal + " 条数据！";
        }

        List<SgjsBuildSchemeReview> insertList = new ArrayList<>();
        List<SgjsBuildSchemeReview> updateList = new ArrayList<>();
        Map<String, SgjsBuildSchemeReview> reviewMap = reviewList.stream().collect(Collectors.toMap(SgjsBuildSchemeReview::getSchemeNum, o -> o));
        for (SgjsBuildSchemeList schemeList : lastValidSchemeListList) {
            String schemeNum = schemeList.getSchemeNum();
            if(reviewMap.containsKey(schemeNum)){
                SgjsBuildSchemeReview review = reviewMap.get(schemeNum);
                if("1".equals(review.getTaskStatus()) || "4".equals(review.getTaskStatus())){
                    continue;
                }
                syncNumTotal++;
                this.putSchemeListToReview(schemeList, review);
                review.setUpdateUser(SecurityUtils.getUserName());
                review.setUpdateTime(DateUtils.getNowDate());
                updateList.add(review);
            }else {
                syncNumTotal++;
                this.setInsertList(insertList,schemeList,userName,nickName);
            }
        }

        if(CollectionUtils.isNotEmpty(insertList)){
            sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(insertList);
        }

        if(CollectionUtils.isNotEmpty(updateList)){
            sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReviewList(updateList);
        }
        return "已同步 " + syncNumTotal + " 条数据！ 其中，新增 " + insertList.size() + " 条数据，修改 " + updateList.size() + "条数据！";
    }

    private void setInsertList(List<SgjsBuildSchemeReview> insertList,SgjsBuildSchemeList schemeList,String userName,String nickName){
        SgjsBuildSchemeReview review = new SgjsBuildSchemeReview();
        review.setId(IdWorker.createId());
        this.putSchemeListToReview(schemeList, review);
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        review.setProjectName(projectDto.getProjectName());
        review.setProjectId(projectDto.getProjectId());
        review.setRegionId(projectDto.getRegionId());
        review.setRegionName(projectDto.getRegionName());
        review.setProjectCode(projectDto.getProjectCode());
        review.setSchemeInitiatorId(userName);
        review.setSchemeInitiatorName(nickName);
        review.setSubmitDate(DateUtils.getNowDate());
        review.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        review.setCreateUserName(SecurityUtils.getUserName());
        review.setCreateTime(DateUtils.getNowDate());
        insertList.add(review);
    }

    private void putSchemeListToReview(SgjsBuildSchemeList schemeList,SgjsBuildSchemeReview review){
        review.setSchemeNum(schemeList.getSchemeNum());
        review.setSchemeName(schemeList.getSchemeName());
        review.setRelationWbsId(schemeList.getRelationWbsId());
        review.setRelationWbsName(schemeList.getRelationWbsName());
        review.setSchemeType(schemeList.getSchemeType());
        review.setSchemeLevel(schemeList.getSchemeLevel());
        review.setDangerLevel(schemeList.getDangerLevel());
        review.setPlanCompletionTime(schemeList.getPlanComplationTime());
        review.setPlanImplementTime(schemeList.getPlanImplementTime());
    }

    @Override
    public void turnDown(Long reviewId) {
        this.recordData(reviewId);
        //删除上一流程所有数据
        this.deleteAllDataByReviewId(reviewId);
        //驳回数据
        sgjsBuildSchemeReviewMapper.dismissedSchemeReview(reviewId);

        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(reviewId);
        review.setProcessStatus("turnDown");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
    }

    private void recordData(Long reviewId){
        SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.initReviewOpinionRecord(reviewId);

        Long reviewOpinionRecordId = IdWorker.createId();
        reviewOpinionRecord.setId(reviewOpinionRecordId);
        reviewOpinionRecord.setReviewId(reviewId);

        int serialNumber = sgjsBuildSchemeReviewOpinionRecordMapper.getMaxSerialNumber(reviewId) + 1;
        reviewOpinionRecord.setSerialNumber(serialNumber);
        reviewOpinionRecord.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        reviewOpinionRecord.setCreateUserName(SecurityUtils.getUserName());
        reviewOpinionRecord.setCreateTime(DateUtils.getNowDate());

        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(reviewId, null);
        if(CollectionUtils.isNotEmpty(staffOpinionGatherVoList)){
            Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
            reviewOpinionRecord.setScore(average);
            List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = new ArrayList<>();
            for (BuildSchemeStaffOpinionGatherVo staffOpinionGatherVo : staffOpinionGatherVoList) {
                SgjsBuildSchemeStaffOpinionRecord staffOpinionRecord = new SgjsBuildSchemeStaffOpinionRecord();
                staffOpinionRecord.setId(IdWorker.createId());
                staffOpinionRecord.setOpinionRecordId(reviewOpinionRecordId);
                staffOpinionRecord.setReviewId(reviewId);
                staffOpinionRecord.setReviewStaffName(staffOpinionGatherVo.getReviewStaffName());
                staffOpinionRecord.setReviewStaffId(staffOpinionGatherVo.getReviewStaffId());
                staffOpinionRecord.setStaffType(staffOpinionGatherVo.getStaffType());
                staffOpinionRecord.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                staffOpinionRecord.setCreateUserName(SecurityUtils.getUserName());
                staffOpinionRecord.setCreateTime(DateUtils.getNowDate());
                List<String> opinionList = staffOpinionGatherVo.getReviewOpinionList();
                if(CollectionUtils.isNotEmpty(opinionList)){
                    String opinionStr = String.join("==>", opinionList);
                    staffOpinionRecord.setReviewOpinion(opinionStr);
                }
                List<String> fileGroupIdList = staffOpinionGatherVo.getFileGroupIdList();
                if(CollectionUtils.isNotEmpty(fileGroupIdList)){
                    String fileGroupIdStr = String.join(",", fileGroupIdList);
                    staffOpinionRecord.setFileGroupId(fileGroupIdStr);
                }
                staffOpinionRecordList.add(staffOpinionRecord);
            }
            sgjsBuildSchemeStaffOpinionRecordMapper.insertSgjsBuildSchemeStaffOpinionRecordList(staffOpinionRecordList);
        }
        sgjsBuildSchemeReviewOpinionRecordMapper.insertSgjsBuildSchemeReviewOpinionRecord(reviewOpinionRecord);
    }

    private void deleteAllDataByReviewId(Long reviewId){
        //删除意见
        sgjsBuildSchemeReviewOpinionMapper.deleteByReviewId(reviewId);
        //删除评审人员
        sgjsBuildSchemeReviewStaffMapper.deleteByReviewId(reviewId);
        //删除评审人员意见
        sgjsBuildSchemeStaffOpinionMapper.deleteByReviewId(reviewId);
    }

    @Override
    public List<SgjsBuildSchemeList> getSchemeList(SgjsBuildSchemeList schemeList) {
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(schemeList);
        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            return lastValidSchemeListList;
        }

        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewMapper.getListByQueryVo(new BuildSchemeReviewQueryVo());
        Set<String> schemeNumSet = reviewList.stream().map(SgjsBuildSchemeReview::getSchemeNum).collect(Collectors.toSet());
        List<SgjsBuildSchemeList> resultList = lastValidSchemeListList.stream().filter(sgjsBuildSchemeList -> !schemeNumSet.contains(sgjsBuildSchemeList.getSchemeNum())).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public SgjsBuildSchemeReviewOpinionRecord getSchemeReviewRecord(Long reviewId) {
        return this.getMaxReviewOpinionRecord(reviewId);
    }

    @Override
    public void submitBuildSchemeReviewProcess(Long id) {
        sgjsBuildSchemeReviewMapper.updateTaskStatus(id, TaskStatus.IN_PROGRESS.getCode());
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(id);
        review.setSubmitDate(DateUtils.getNowDate());
        SysUser sysUser = SecurityUtils.getSysUser();
        review.setSchemeInitiatorId(sysUser.getUserName());
        review.setSchemeInitiatorName(sysUser.getNickName());
        sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReview(review);
        review.setProcessStatus("submit");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
    }

    @Override
    public void updateBuildSchemeReviewProcess(Long id) {
        sgjsBuildSchemeReviewMapper.updateTaskStatus(id, TaskStatus.COMPLETED.getCode());
        sgjsBuildSchemeReviewMapper.updateApprovalTime(id);
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(id);
        review.setProcessStatus("end");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
    }

    @Override
    public void approvedAfterModification(Long id) {
        this.recordData(id);
    }


    @Value("${gm.url}")
    private String gmUrl;
    @Value("${warn.schemeReviewUrl}")
    private String schemeReviewUrl;

    //预警消息发送
    public void warnMessage() {
        //从总部获取预警配置信息
        String url = gmUrl + "/gm/sgjsWarnConfig?warnSubject={warnSubject}";
        String warnItemId = WarnItem.SGJS_BUILD_SCHEME_REVIEW.getWarnItemId();
        SgjsWarnConfig sgjsWarnConfig = CommonBusiness.getSgjsWarnConfig(url, warnItemId);
        if (null == sgjsWarnConfig) return;
        /*遍历所有租户发送预警*/
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
            for (SysTenant tenant : tenantList) {
                /*查询施工方案评审数据*/
                SgjsBuildSchemeReview sgjsBuildSchemeReview = new SgjsBuildSchemeReview();
                List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList = sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
                if (CollUtil.isEmpty(sgjsBuildSchemeReviewList)) {
                    log.info("施工方案评审数据无数据");
                    return;
                }
                /*查询流程，过滤得到未发起审批的数据*/
                List<SgjsBuildSchemeReview> list23 = sgjsBuildSchemeReviewList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getSchemeLevel()) && (p.getSchemeLevel().equals("2") || p.getSchemeLevel().equals("3")))
                        .collect(Collectors.toList());
                List<SgjsBuildSchemeReview> list4 = sgjsBuildSchemeReviewList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getSchemeLevel()) && (p.getSchemeLevel().equals("4")))
                        .collect(Collectors.toList());
                //不同方案级别走不同的流程,级别1不走流程
                FlowInfoSearchUtil.getFlowInfo(list23, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
                FlowInfoSearchUtil.getFlowInfo(list4, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
                ArrayList<SgjsBuildSchemeReview> allList = new ArrayList<>();
                allList.addAll(list23);
                allList.addAll(list4);
                //审批中的数据
                List<SgjsBuildSchemeReview> flowList = allList.stream()
                        .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()))
                        .filter(p -> !p.getTaskStatus().equals("0") && !p.getTaskStatus().equals("4"))
                        .collect(Collectors.toList());
                //获取任务详情，得到当前任务节点; 只需处理专家、部门评审人员节点
                List<SysUser> userList = new ArrayList<>();
                Date nowDate = new Date();
                //遍历所有业务数据
                for (SgjsBuildSchemeReview schemeReview : flowList) {
                    String currentTaskIds = schemeReview.getCurrentTaskIds();
                    if (StrUtil.isBlank(currentTaskIds)) continue;
                    String[] currentTaskIdArr = StrUtil.splitToArray(currentTaskIds, ",");
                    //遍历当前业务数据的所有流程任务
                    for (String currentTaskId : currentTaskIdArr) {
                        AjaxResult ajaxResult = flowServiceApi.taskInfoDetail(currentTaskId);
                        Integer code = (Integer) ajaxResult.get("code");
                        if (!code.equals(200)) continue;
                        String warnInfo = JSON.toJSONString(ajaxResult.get("data"));
                        if (StrUtil.isBlank(warnInfo)) continue;
                        //得到流程任务详情
                        TaskResource taskResource = JSON.parseObject(warnInfo, TaskResource.class);
                        Date createTime = taskResource.getCreateTime();
                        if (null == createTime) continue;
                        long between = DateUtil.between(nowDate, createTime, DateUnit.DAY, false);
                        Map<String, Object> variables = taskResource.getVariables();
                        String assignee = taskResource.getAssignee();
                        String assigneeNickName = taskResource.getAssigneeNickName();
                        //自定义属性
                        Map<String, List<String>> customProperties = taskResource.getCustomProperties();
                        List<String> flowNodeMarkList = customProperties.get("flowNodeMark");
                        if (CollUtil.isEmpty(flowNodeMarkList)) continue;
                        String flowNodeMark = flowNodeMarkList.get(0);
                        if (StrUtil.isNotBlank(flowNodeMark)
                                && (flowNodeMark.equals("3") || flowNodeMark.equals("4") || flowNodeMark.equals("5") || flowNodeMark.equals("6"))
                                && between >= 5 ) {
                            //得到流程节点标识为 3，4，5，6的节点, 并且在此节点大于等于5天
                            SysUser sysUser = new SysUser();
                            sysUser.setUserName(assignee);
                            sysUser.setNickName(assigneeNickName);
                            userList.add(sysUser);
                        }
                    }
                }
                if (CollUtil.isEmpty(userList)) {
                    log.info("施工方案评审预警，无需预警");
                    return;
                }
                /*执行预警，保存预警记录*/
                //预警消息组装
                TWarn tWarn = new TWarn();
                tWarn.setWarnItem(sgjsWarnConfig.getWarnSubject());
                tWarn.setWarnItemId(WarnItem.SGJS_BUILD_SCHEME_REVIEW.getWarnItemId());
                String userNames = userList.stream().map(SysUser::getUserName).collect(Collectors.joining());
                tWarn.setWarnScope(userNames);
                tWarn.setWarnUrl(schemeReviewUrl);
                tWarn.setWarnScopeType("3");
                String warnContent = CommonBusiness.warnMessageHandle(sgjsWarnConfig.getWarnMassage(), tenant.getTenantName(), sgjsWarnConfig.getWarnSubject(), sgjsWarnConfig.getWarnRule());
                tWarn.setWarnContent(warnContent);
                tWarn.setProjectName(tenant.getTenantName());
                tWarn.setTenantKey(tenant.getTenantKey());
                //发送预警
                systemServiceApi.addWarnNonGm(tWarn);
                //预警记录保存
                List<SgjsWarnRecord> warnRecordList = new ArrayList<>();
                for (SysUser user : userList) {
                    //预警记录
                    SgjsWarnRecord sgjsWarnRecord = new SgjsWarnRecord();
                    sgjsWarnRecord.setProjectCode(tenant.getTenantKey());
                    sgjsWarnRecord.setProjectName(tenant.getTenantName());
                    sgjsWarnRecord.setWarnContent(warnContent);
//                    sgjsWarnRecord.setWarnUserId(String.valueOf(p.getUserId()));
                    sgjsWarnRecord.setWarnUser(user.getUserName());
                    warnRecordList.add(sgjsWarnRecord);
                }
                /*推送总部*/
                if (CollUtil.isNotEmpty(warnRecordList)) {
                    rocketMQTemplate.convertAndSend("sgjs_build_scheme_list_warn:tenantSuccess", warnRecordList);
                    log.info("施工方案清单预警记录推送数据：" + JSON.toJSONString(warnRecordList));
                }
                log.info("施工方案评审预警执行完成。。。。: {}", userNames);
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
