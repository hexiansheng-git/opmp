package com.hhwy.sp.buildSchemeManage.review.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.flow.TaskResourceNew;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.feign.service.ILogServiceApi;
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
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.*;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
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
    private ILogServiceApi logServiceApi;
    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private SystemApiService systemApiService;


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
        //格式化方案类型
//        String schemeType = DictUtil.formatMultiDict("scheme_type_all",review.getSchemeType());
//        review.setSchemeType(schemeType);
        review.setFlowNodeMark(flowNodeMark);
        String schemeLevel = review.getSchemeLevel();
        if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
            FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
        }
        if("4".equals(schemeLevel)){
            FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
        }
        //总部版跳转过来传的type=handler
        type=StringUtils.equals(type,"handle")?"4":type;
        if("1".equals(type) || "2".equals(type)){
            //查看/发起审批
            return review;
        }
        if("3".equals(type)){
            //查看
            String uname = SecurityUtils.getUserName();
            if(StringUtils.equals(review.getTaskStatus(),TaskStatus.COMPLETED.getCode())
                || SecurityUtils.getSysUser().isAdmin() || isLeader()) //已结束、管理员、总部版领导角色
//            boolean s = SecurityUtils.getSysUser().isAdmin() || isLeader();
//            if(s || StringUtils.equals(review.getTaskStatus(),TaskStatus.COMPLETED.getCode())
                uname = null;
            BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(id,uname);
            review.setScore(reviewOpinionVo.getScore());
            review.setReviewOpinionVo(reviewOpinionVo);
        }

        if("4".equals(type)){
            //处理
            if(ReviewFlowNodeMark.FlowNodeMark1.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark2.equals(flowNodeMark)){
                //区域中心或海外事业部角色审批节点
                List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewId(id, flowNodeMark);
                List<SgjsBuildSchemeReviewStaff> staffListRes = this.assembleStaffList(staffList);
                review.setReviewStaffList(staffListRes);
            }else if(ReviewFlowNodeMark.FlowNodeMark3.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark4.equals(flowNodeMark) 
                    || ReviewFlowNodeMark.FlowNodeMark5.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark6.equals(flowNodeMark)){
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
            }else if(ReviewFlowNodeMark.FlowNodeMark7.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark8.equals(flowNodeMark)
                    || ReviewFlowNodeMark.FlowNodeMark11.equals(flowNodeMark) ){
                //区域中心只能看区域中心的意见，flowNodeMark = 1
                String flowNodeMarkParam = ReviewFlowNodeMark.FlowNodeMark7.equals(flowNodeMark)?"1":null;
                //区域总工审批节点  汇总 3 ，4节点数据    海外事业部总工审批节点  汇总 3 ，4 ，5 ，6节点数据
                BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(id,flowNodeMarkParam,null);
                review.setScore(reviewOpinionVo.getScore());
                review.setReviewOpinionVo(reviewOpinionVo);
            }else if(ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark) || ReviewFlowNodeMark.FlowNodeMark10.equals(flowNodeMark)){
                //海外事业部总工意见为修改后通过后的审批节点
                BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(id);
                review.setScore(reviewOpinionVo.getScore());
                review.setReviewOpinionVo(reviewOpinionVo);
            }else {
                //节点标识为空：当前为驳回后的发起人节点，需要查看历史数据
                SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.getMaxReviewOpinionRecord(id);
                review.setReviewOpinionRecord(reviewOpinionRecord);
                BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(id,null,null);
                review.setReviewOpinionVo(reviewOpinionVo);
            }
            //驳回到发起人节点后，可以看到所有意见
            if(StringUtils.equals(review.getIsFirstNode(),"1")){ 
                BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(id,null,null);
                review.setScore(reviewOpinionVo.getScore());
                review.setReviewOpinionVo(reviewOpinionVo);
            }
                
        }
        return review;
    }

    private BuildSchemeReviewOpinionVo getReviewOpinionVo(Long reviewId,String username){
        return getReviewOpinionVo(reviewId,null,username);
    }
    
    private BuildSchemeReviewOpinionVo getReviewOpinionVo(Long reviewId){
        return getReviewOpinionVo(reviewId,null,null);
    }
    /**
     * 
     * @param reviewId
     * @param flowNodeMark  为空:全查,1:查询区域中心,2:查询海外事业部
     * @return
     */
    private BuildSchemeReviewOpinionVo getReviewOpinionVo(Long reviewId,String flowNodeMark,String username){
        BuildSchemeReviewOpinionVo reviewOpinionVo = new BuildSchemeReviewOpinionVo();
        SgjsBuildSchemeReviewOpinion reviewOpinionQuery = new SgjsBuildSchemeReviewOpinion();
        reviewOpinionQuery.setReviewId(reviewId);
        SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(reviewOpinionQuery);
        if(reviewOpinion != null){
            reviewOpinionVo.setRegionChiefOpinion(reviewOpinion.getRegionChiefOpinion());
            reviewOpinionVo.setRegionChiefDetailOpinion(reviewOpinion.getRegionChiefDetailOpinion());
            reviewOpinionVo.setOverseasChiefOpinion(reviewOpinion.getOverseasChiefOpinion());
            reviewOpinionVo.setOverseasChiefDetailOpinion(reviewOpinion.getOverseasChiefDetailOpinion());
        }
        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(reviewId, flowNodeMark,username);
        reviewOpinionVo.setGatherVoList(staffOpinionGatherVoList);
        Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
        reviewOpinionVo.setScore(average);
        return reviewOpinionVo;
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
     * @param flowNodeMark 选择审批人的节点标识  1(区域中心)或2(海外事业部)
     * @param username 专家username                   
     * @return
     */
    private List<BuildSchemeStaffOpinionGatherVo> getStaffOpinionGatherVoList(Long reviewId,String flowNodeMark,String username){
        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = new ArrayList<>();
        List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewIdGroupByUser(reviewId, flowNodeMark,username);
        if(CollectionUtils.isEmpty(staffList)){
            return staffOpinionGatherVoList;
        }
        List<String> reviewStaffIdList = staffList.stream().map(SgjsBuildSchemeReviewStaff::getReviewStaffId).collect(Collectors.toList());
        List<SgjsBuildSchemeStaffOpinion> staffOpinionList = sgjsBuildSchemeStaffOpinionMapper.getListByReviewStaffIdList(reviewId, reviewStaffIdList);
        Map<String, List<SgjsBuildSchemeStaffOpinion>> staffOpinionMap = staffOpinionList.stream().collect(Collectors.groupingBy(SgjsBuildSchemeStaffOpinion::getReviewStaffId));
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        for (SgjsBuildSchemeReviewStaff staff : staffList) {
            BuildSchemeStaffOpinionGatherVo staffOpinionGatherVo = new BuildSchemeStaffOpinionGatherVo();
            staffOpinionGatherVo.setReviewStaffName(staff.getReviewStaffName());
            staffOpinionGatherVo.setReviewStaffId(staff.getReviewStaffId());
            staffOpinionGatherVo.setStaffType(staff.getStaffType());
            staffOpinionGatherVo.setScore(staff.getScore());
            staffOpinionGatherVo.setSubmitTime(staff.getSubmitTime());
            staffOpinionGatherVo.setUpdateResult(staff.getUpdateResult());
            if(StringUtils.equals(staff.getStaffType(),"1")){ //专家
                staffOpinionGatherVo.setRolePrefix("");
            }else { //部门
                staffOpinionGatherVo.setRolePrefix(ObjectUtils.nvlString(staff.getDescription()));
            }
            String suffix = StringUtils.equals(staff.getFlowNodeMark(),"1")?"-"+projectDto.getRegionName():"-海外事业部";
            staffOpinionGatherVo.setRoleSuffix(suffix);
            staffOpinionGatherVo.setFlowNodeMark(staff.getFlowNodeMark()); 
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
                String[] fileGroupIdArr = fileGroupIdList.split(",");
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

        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(reviewId, null,null);
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
                staffOpinionRecord.setUpdateResult(staffOpinionGatherVo.getUpdateResult());
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
        if(reviewOpinion != null){
            reviewOpinionRecord.setRegionChiefOpinion(reviewOpinion.getRegionChiefOpinion());
            reviewOpinionRecord.setRegionChiefDetailOpinion(reviewOpinion.getRegionChiefDetailOpinion());
            reviewOpinionRecord.setOverseasChiefOpinion(reviewOpinion.getOverseasChiefOpinion());
            reviewOpinionRecord.setOverseasChiefDetailOpinion(reviewOpinion.getOverseasChiefDetailOpinion());
        }
        return reviewOpinionRecord;
    }

    @Override
    @Transactional
    public Long save(SgjsBuildSchemeReview review) {
        saveLog(review);
        String saveType = review.getSaveType();
        CommonAssert.notBlank(saveType,"保存类型不能为空！");
        if("add".equals(saveType) || review.getId() == null){
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
                if(ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark)){
                    BuildSchemeReviewOpinionVo reviewOpinionVo = review.getReviewOpinionVo();
                    if (reviewOpinionVo != null) {
                        List<BuildSchemeStaffOpinionGatherVo> gatherVoList = reviewOpinionVo.getGatherVoList();
                        if(CollectionUtils.isNotEmpty(gatherVoList)){
                            sgjsBuildSchemeReviewStaffMapper.updateUpdateResultByUser(id,gatherVoList);
                        }
                    }
                }
            } else {
                //节点标识为空：当前为驳回后的发起人节点
                SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = review.getReviewOpinionRecord();
                if (reviewOpinionRecord != null) {
                    List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = reviewOpinionRecord.getStaffOpinionRecordList();
                    this.updateStaffOpinionRecordList(staffOpinionRecordList);
                }
            }
        }

        if(("1".equals(taskStatus) || "4".equals(taskStatus)) && StringUtils.isNotBlank(flowNodeMark) && 
                !ReviewFlowNodeMark.FlowNodeMark9.equals(flowNodeMark) && !ReviewFlowNodeMark.FlowNodeMark10.equals(flowNodeMark)){
            review.setProcessStatus("no");
            List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(review.getId(), null,null);
            Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
            review.setScore(average);
            sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
        }else{ //新需求，总部版需要看到待发起数据
            if("dispose".equals(saveType)){
                review.setProcessStatus("submit");    
            }else if("add".equals(saveType) || "edit".equals(saveType) || "save".equals(saveType)){
                review.setProcessStatus("save");
            }
            sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
        }
        return review.getId();
    }

    private void saveLog(SgjsBuildSchemeReview review){
        String json = JSONObject.toJSONString(review);
        try{
            SysSyncLog syncLog = new SysSyncLog();
            syncLog.setId(IdWorker.createId());
            syncLog.setInterfaceName("SgjsBuildSchemeReview");
            syncLog.setReq(json);
            syncLog.setBusinessUnique(review.getId()+"");
            syncLog.setPtVar1(SecurityUtils.getTenantKey());
            new AddBaseInfoUtil<>().addBaseEntity(syncLog);
            logServiceApi.insertSysSyncLog(syncLog);
        }catch(Exception e){
            e.printStackTrace();
            log.error("SP,施工方案清单日志记录失败,租户:{},json:{},",SecurityUtils.getTenantKey(),json);
        }
    }
    private void updateStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList) {
        if (CollectionUtils.isNotEmpty(staffOpinionRecordList)) {
            //校验修改结果不能为空
            for (int i = 0; i < staffOpinionRecordList.size(); i++) {
                Assert.isTrue(StringUtils.isNotBlank(staffOpinionRecordList.get(i).getUpdateResult()),"第"+(i+1)+"行,修改结果不能为空!" ); 
            }
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
                    //处理desciption,实际存的是部门名称
                    String desc = ObjectUtils.nvlString(staff.getDescription()).replace("选择","").replace("人员", "");
                    insertStaff.setDescription(desc);
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

        List<SgjsBuildSchemeReviewStaff> existStaffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewIdGroupByUser(reviewId, null,null);
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
        SgjsBuildSchemeList query = new SgjsBuildSchemeList();
        query.setParams(ObjectUtils.toMap("limitType","1"));
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(query);

        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            return "已同步 " + syncNumTotal + " 条数据！";
        }
        List<SgjsBuildSchemeList> filterList = lastValidSchemeListList.stream().filter(o -> !"1".equals(o.getSchemeLevel())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(filterList)){
            return "已同步 " + syncNumTotal + " 条数据！";
        }

        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewMapper.getListByQueryVo(new BuildSchemeReviewQueryVo());
        SysUser sysUser = SecurityUtils.getSysUser();
        String userName = sysUser.getUserName();
        String nickName = sysUser.getNickName();
        if(CollectionUtils.isEmpty(reviewList)){
            List<SgjsBuildSchemeReview> reviewListNew = new ArrayList<>();
            for (SgjsBuildSchemeList schemeList : filterList) {
                this.setInsertList(reviewListNew,schemeList,userName,nickName);
            }
            syncNumTotal = reviewListNew.size();
            sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(reviewListNew);
            //推送到总部
            push2Gm(reviewListNew);
            return "已同步 " + syncNumTotal + " 条数据！";
        }

        List<SgjsBuildSchemeReview> insertList = new ArrayList<>();
        List<SgjsBuildSchemeReview> updateList = new ArrayList<>();
        Map<String, SgjsBuildSchemeReview> reviewMap = reviewList.stream().collect(Collectors.toMap(SgjsBuildSchemeReview::getSchemeNum, o -> o));
        for (SgjsBuildSchemeList schemeList : filterList) {
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
        if(CollectionUtils.isNotEmpty(insertList))
            push2Gm(insertList);
        if(CollectionUtils.isNotEmpty(updateList))
            push2Gm(updateList);
        return "已同步 " + syncNumTotal + " 条数据！ 其中，新增 " + insertList.size() + " 条数据，修改 " + updateList.size() + "条数据！";
    }
    private void push2Gm(List<SgjsBuildSchemeReview> list){
        if(CollectionUtils.isEmpty(list))
            return ;
        for (int i = 0; i < list.size(); i++) {
            SgjsBuildSchemeReview temp = list.get(i);
            temp.setProcessStatus("save");
            sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(temp);
        }
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
        review.setTaskStatus("0");
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
    @Transactional
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

        List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(reviewId, null,null);
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
                staffOpinionRecord.setScore(staffOpinionGatherVo.getScore());
                staffOpinionRecord.setSubmitTime(staffOpinionGatherVo.getSubmitTime());
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
        schemeList.setParams(ObjectUtils.toMap("limitType","1"));
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(schemeList);
        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            return new ArrayList<>();
        }
        List<SgjsBuildSchemeList> filterList = lastValidSchemeListList.stream().filter(o -> !"1".equals(o.getSchemeLevel())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(filterList)){
            return new ArrayList<>();
        }
        List<SgjsBuildSchemeReview> reviewList1 = sgjsBuildSchemeReviewMapper.getListByQueryVo(new BuildSchemeReviewQueryVo());
        Set<String> schemeNumSet = reviewList1.stream().map(SgjsBuildSchemeReview::getSchemeNum).collect(Collectors.toSet());
        List<SgjsBuildSchemeList> resultList =  filterList.stream().filter(sgjsBuildSchemeList -> !schemeNumSet.contains(sgjsBuildSchemeList.getSchemeNum())).collect(Collectors.toList());
        formatSchemeType(resultList,
                r->r.getSchemeType(),
                (t,v)->{t.setSchemeType(v); return v;});
        return resultList;
    }

    @Override
    public BuildSchemeReviewOpinionRecordVo getSchemeReviewRecordVo(Long reviewId) {
        BuildSchemeReviewOpinionRecordVo recordVo = new BuildSchemeReviewOpinionRecordVo();
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(reviewId);
        SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = this.getMaxReviewOpinionRecord(reviewId);
        recordVo.setReview(review);
        recordVo.setRecord(reviewOpinionRecord);
        return recordVo;
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
    @Transactional
    public void updateBuildSchemeReviewProcess(Long id) {
        sgjsBuildSchemeReviewMapper.updateTaskStatus(id, TaskStatus.COMPLETED.getCode());
        sgjsBuildSchemeReviewMapper.updateApprovalTime(id);
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewMapper.getById(id);
        List<SgjsBuildSchemeReviewStaff> sumScoreList = sgjsBuildSchemeReviewStaffMapper.getSumScoreByReviewIdList(Arrays.asList(id));
        Map<Long, Double> sumSocreMap = sumScoreList.stream().filter(o -> o.getScore() != null).collect(Collectors.toMap(SgjsBuildSchemeReviewStaff::getReviewId, SgjsBuildSchemeReviewStaff::getScore));
        Double score = sumSocreMap.get(id);
        if(score != null){
            score = (double) Math.round(score);
        }
        review.setScore(score);
        //log.error("20240522:算分结果:{}",score );
        review.setProcessStatus("end");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
        //审批结果走预警,推送
        auditResultPush(review);
    }

    @Override
    public void updateBuildSchemeReviewProcess2Init(Long id) {
        SgjsBuildSchemeReview review = new SgjsBuildSchemeReview();
        review.setId(id);
        review.setProcessStatus("init");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
    }

    @Value("${gm.url}")
    private String gmUrl;
    @Value("${gm.back-url}")
    private String gmBackUrl;
    @Value("${warn.schemeReviewUrl}")
    private String schemeReviewUrl;
    @Value("${warn.schemeReviewFinishPush}")
    private boolean finishPush;

    //预警消息发送
    public void warnMessage() {
        //从总部获取预警配置信息
        String url = gmUrl + "/gm/sgjsWarnConfig/list?warnSubject={warnSubject}";
        SgjsWarnConfig sgjsWarnConfig = CommonBusiness.getSgjsWarnConfig(url, "施工方案评审-评审");
        if (null == sgjsWarnConfig) {
            log.error("获取施工方案评审预警配置无数据");
            return;
        }
        /*遍历所有租户发送预警*/
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
//            List<SysTenant> tenantList = new ArrayList<>();
//            SysTenant sysTenant = new SysTenant();
//            sysTenant.setTenantKey("PJ2020011492");
//            sysTenant.setTenantName("吉布提风力发电项目");
//            tenantList.add(sysTenant);
            this.warnHandler(tenantList, sgjsWarnConfig);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    //审批结束后，审批结果推送
    //推送消息给审批人，2、3级>发起人、区域中心。4级>项目上发起人、区域中心
    public void auditResultPush(SgjsBuildSchemeReview review){
        if(!finishPush)
            return ;
        AjaxResult ajaxResult = flowServiceApi.handleList(review.getId()+"",FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4.getTableName() );
        if(!AjaxResult.isSuccess(ajaxResult))
            throw new CustomException("获取流程处理列表失败");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(ajaxResult.getData()));
        JSONArray jsonArray = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("items")));
        Set<String> unameSet = new HashSet<>();
        Set<String> unicknameSet = new HashSet<>();
        if(CollectionUtils.isNotEmpty(jsonArray)){
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                String taskName = temp.getString("taskName");
                if(taskName.indexOf("发起人") > -1 || taskName.indexOf("区域中心") > -1){
                    unameSet.add(temp.getString("assignee"));
                    unicknameSet.add(temp.getString("assigneeNickName"));
                }
            }
        }
        if(CollectionUtils.isEmpty(unameSet)){
            log.info("施工方案评审,未能获取到发起人或区域中心审批人。租户key:{},id:{}",SecurityUtils.getTenantKey(),review.getId());
            return ;
        }
        //查询历史记录
        TWarn tWarn = new TWarn();
        tWarn.setWarnItem(WarnItem.SGJS_BUILD_SCHEME_REVIEW_REJECT.getWarnItem());
        tWarn.setWarnItemId(WarnItem.SGJS_BUILD_SCHEME_REVIEW_REJECT.getWarnItemId());
        tWarn.setWarnScope(org.apache.commons.lang3.StringUtils.join(unameSet, ","));
        tWarn.setWarnScopeName(org.apache.commons.lang3.StringUtils.join(unicknameSet, ","));
        tWarn.setWarnUrl(schemeReviewUrl);
        tWarn.setBusinessId(review.getId());
        tWarn.setWarnScopeType("3");
        tWarn.setProjectName(SecurityUtils.getSysUser().getTenant().getTenantName());
        //消息内容
        //4级取区域总工意见、2、3级拿海外事业部意见
        BuildSchemeReviewOpinionVo reviewOpinionVo = this.getReviewOpinionVo(review.getId(),null);
        String opinion = reviewOpinionVo.getOverseasChiefOpinion();
        if(StringUtils.equals(review.getSchemeLevel(),"4"))
            opinion = reviewOpinionVo.getRegionChiefOpinion();
        //项目名称-方案名称审批结果
        String warnContent = String.format("[项管系统],%s-%s%s",
                tWarn.getProjectName(),review.getSchemeName(),StringUtils.equals(opinion,"3")?"审批未通过":"审批通过");
        tWarn.setWarnContent(warnContent);            
        tWarn.setTenantKey(SecurityUtils.getTenantKey());
        //发送预警
        systemServiceApi.addWarn(tWarn);
    }
    
    /**
    * 功能描述: 所有租户发送预警
    * @param: tenantList 租户列表
     * @param: sgjsWarnConfig 预警配置信息
    * @return: void
    * 作者:
    * 时间: 2024/6/12
    */
    private void warnHandler(List<SysTenant> tenantList, SgjsWarnConfig sgjsWarnConfig) {
        for (SysTenant tenant : tenantList) {
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenant.getTenantKey()));
            try {
                /*查询施工方案评审数据*/
                // 2024年05月31日之前的数据不做预警
                String createTimeStr = "2024-06-01 00:00:00";
                SgjsBuildSchemeReview sgjsBuildSchemeReview = new SgjsBuildSchemeReview();
                sgjsBuildSchemeReview.setCreateTime(DateUtil.parse(createTimeStr, DatePattern.NORM_DATETIME_PATTERN));
                List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList = sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
                if (CollUtil.isEmpty(sgjsBuildSchemeReviewList)) {
                    log.info("租户：{}，施工方案评审数据无数据", tenant.getTenantName());
                    continue;
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
//                Set<String> userNameList = new HashSet<>();
                List<SysUser> todoWarnUserList = new ArrayList<>();
                Map<String, String> schemeName = new HashMap<>();
                Date nowDate = new Date();
                //遍历所有业务数据
                for (SgjsBuildSchemeReview schemeReview : flowList) {
                    String currentTaskIds = schemeReview.getCurrentTaskIds();
                    if (StrUtil.isBlank(currentTaskIds)) {
                        log.warn("currentTaskIds空，任务：{}", currentTaskIds);
                        continue;
                    }
                    String[] currentTaskIdArr = StrUtil.splitToArray(currentTaskIds, ",");
                    //遍历当前业务数据的所有流程任务
                    for (String currentTaskId : currentTaskIdArr) {
                        AjaxResult ajaxResult = flowServiceApi.taskInfoDetail(currentTaskId);
                        if (!AjaxResult.isSuccess(ajaxResult)) {
                            log.warn("获取任务失败， 任务id：{} --- 响应结果：{}", currentTaskId, ajaxResult);
                        }
                        String warnInfo = JSON.toJSONString(ajaxResult.get("data"));
                        if (StrUtil.isBlank(warnInfo)) {
                            log.warn("查询流程无数据，任务：{}", currentTaskId);
                            continue;
                        }
                        //得到流程任务详情
                        TaskResourceNew taskResource = JSON.parseObject(warnInfo, TaskResourceNew.class);
                        Date createTime = taskResource.getCreateTime();
                        if (null == createTime) {
                            log.warn("流程任务创建时间为空");
                            continue;
                        }
//                        long between = DateUtil.between(createTime, DateUtil.offsetDay(nowDate, 5), DateUnit.DAY, false);
                        long between = DateUtil.between(createTime, nowDate, DateUnit.DAY, false);
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
                                && between >= 5) {
                            //得到流程节点标识为 3，4，5，6的节点, 并且在此节点大于等于5天
                            SysUser sysUser = new SysUser();
                            sysUser.setUserName(assignee);
                            sysUser.setNickName(assigneeNickName);
                            todoWarnUserList.add(sysUser);
                            schemeName.put(assignee, schemeReview.getSchemeName());
                        }
                    }
                }
                if (CollUtil.isEmpty(todoWarnUserList)) {
                    log.info("租户：{}，施工方案评审预警，无需预警", tenant.getTenantName());
                    continue;
                }
                /*执行预警，保存预警记录*/
                //发送预警
                List<TWarn> tWarnList = new ArrayList<>();
                //预警记录
                List<SgjsWarnRecord> warnRecordList = new ArrayList<>();
                String userNames = todoWarnUserList.stream().map(SysUser::getUserName).collect(Collectors.joining(","));
                //您好，【项目名称】上的功能区【功能区名称】中的【施工方案名称】未能按要求完成，请及时进行查看。zhengjie 0812!
                String warnSubject = sgjsWarnConfig.getWarnSubject();
                String warnSubjectSub = warnSubject.substring(0, warnSubject.indexOf("-"));
                String warnContent = CommonBusiness.warnMessageHandle(sgjsWarnConfig.getWarnMassage(), tenant.getTenantName(), warnSubjectSub, sgjsWarnConfig.getWarnRule());
                for (SysUser user : todoWarnUserList) {
                    //业务表与预警表关联id
                    Long relationId = IdWorker.createId();
                    //预警消息组装
                    TWarn tWarn = new TWarn();
                    tWarn.setWarnItem("施工方案评审");
                    tWarn.setWarnItemId(WarnItem.SGJS_BUILD_SCHEME_REVIEW.getWarnItemId());
                    tWarn.setWarnScope(user.getUserName());
                    tWarn.setWarnUrl(schemeReviewUrl);
                    tWarn.setBusinessId(relationId);
                    tWarn.setWarnScopeType("3");
                    tWarn.setWarnContent(warnContent.replace("【施工方案名称】", schemeName.get(user.getUserName())));
                    tWarn.setProjectName(tenant.getTenantName());
                    tWarn.setTenantKey(tenant.getTenantKey());
                    tWarnList.add(tWarn);

                    //预警记录 总部看的数据
                    SgjsWarnRecord sgjsWarnRecord = new SgjsWarnRecord();
                    sgjsWarnRecord.setProjectCode(tenant.getTenantKey());
                    sgjsWarnRecord.setProjectName(tenant.getTenantName());
                    sgjsWarnRecord.setWarnContent(warnContent);
                    sgjsWarnRecord.setWarnUserId(user.getUserName());
                    sgjsWarnRecord.setWarnUser(user.getNickName());
                    sgjsWarnRecord.setWarnSubject(sgjsWarnConfig.getWarnSubject());
                    sgjsWarnRecord.setWarnTime(new Date());
                    sgjsWarnRecord.setStatus("1");
                    sgjsWarnRecord.setPtVar1(String.valueOf(relationId));
                    warnRecordList.add(sgjsWarnRecord);
                }
                //发送预警
                AjaxResult ajaxResult = systemServiceApi.addWarnListNonGm(tWarnList);
                log.info("施工方案编制预警执行完成。。。。预警服务响应：{} --- 租户：{}", JSON.toJSONString(ajaxResult), tenant.getTenantName());
                /*推送总部*/
                if (CollUtil.isNotEmpty(warnRecordList)) {
                    rocketMQTemplate.convertAndSend("sgjs_build_scheme_list_warn:tenantSuccess", warnRecordList);
                    log.info("租户：{}，施工方案清单预警记录推送数据：" + JSON.toJSONString(warnRecordList), tenant.getTenantName());
                }
                log.info("租户：{}，施工方案评审预警执行完成。。。。预警人: {}", tenant.getTenantName(), userNames);
            } catch (Exception e) {
                log.error("租户：{}, 异常", tenant.getTenantName());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        sgjsBuildSchemeReviewMapper.deleteById(id);
        //推送到总部，删除总部版对应数据
        SgjsBuildSchemeReview review = new SgjsBuildSchemeReview();
        review.setId(id);
        review.setProcessStatus("delete");
        sysSyncInfoService4Sp.pushSgjsBuildSchemeReview(review);
    }


    @Override
    public <T> void formatSchemeType(List<T> list, Function<T, String> function, BiFunction<T,String,String> setValFunc) {
        //格式化方案类型
        List<SysDictData> dictList = systemApiService.selectDictDataByType("scheme_type_all");
        Map<Long,SysDictData> dictMap = dictList.stream().collect(Collectors.toMap(r->r.getDictDataId(), r->r));
        Map<String,String> map = dictList.stream().collect(
                Collectors.toMap(r->{
                            String parent = (r.getParentId()==null || r.getParentId().equals(0L) )?"":dictMap.get(r.getParentId()).getDictValue()+",";
                            return parent+r.getDictValue();
                        }
                        , r->{
                            String parent = (r.getParentId()==null || r.getParentId().equals(0L) )?"":dictMap.get(r.getParentId()).getDictLabel()+"/";
                            return parent+r.getDictLabel();
                        })
        );
        for (int i = 0; i < list.size(); i++) {
            String sourceVal = function.apply(list.get(i));
            String fmtStr = ObjectUtils.nvlString(map.get(sourceVal),sourceVal);
            setValFunc.apply(list.get(i),fmtStr);
        }
    }


    private boolean isLeader(){
        Map map = new HashMap();
        String token = GmTokenUtils.getTokenWithUsername(SecurityUtils.getUserName());
        map.put(Constant.AUTHORIZATION,token);
        map.put(Constant.TENANT_KEY,"master");
//        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(typeStrSet), ContentType.APPLICATION_JSON);
        Object string = HttpClientUtil.send(gmBackUrl+"/system/selfSysUser/isLeader", HttpClientUtil.METHOD_GET, null, map, null, null);
        JSONObject resultObj = JSONObject.parseObject(string.toString());
        if(!org.apache.commons.lang3.StringUtils.equals(resultObj.get("code")+"","200")){
            throw new RuntimeException(resultObj.get("msg")+"");
        }
        return ObjectUtils.nvlString(resultObj.get("data")).equals("1");
    }

    @Override
    @Transactional
    public void transferTask(String taskId, String username,String nickName) {
        AjaxResult result = flowServiceApi.taskInfoDetail(taskId);
        Assert.isTrue(AjaxResult.isSuccess(result) ,"获取流程信息失败");
        TaskResourceNew taskResourceNew = JSONObject.parseObject(JSONObject.toJSONString(result.getData()), TaskResourceNew.class) ;
        Set<String> areaMarkSet = new HashSet<>(Arrays.asList("3","4"));
        Set<String> haiwaiMarkSet = new HashSet<>(Arrays.asList("5","6"));
        List<String> nodeMarkList = taskResourceNew.getCustomProperties().get("flowNodeMark");
        int type = 0;
        for (int i = 0; i < nodeMarkList.size(); i++) {
            if(areaMarkSet.contains(nodeMarkList.get(i))){
               type = 1;
               break;
            } 
            if( haiwaiMarkSet.contains(nodeMarkList.get(i)) ){
               type = 2;
               break;
            }
        }
        if(type == 0)  //非海外事业部、区域中心审批节点
            return ;
        Long reviewId = Long.valueOf(taskResourceNew.getBusinessKey());
        this.sgjsBuildSchemeReviewMapper.updateStaffUser(username,nickName,reviewId,type+"");
    }

    @Override
    public SgjsBuildSchemeReview exportSuggestion(HttpServletResponse response, BuildSchemeReviewDetailQueryVo detailQueryVo) throws IOException {
        SgjsBuildSchemeReview review = this.getDetail(detailQueryVo);
        BuildSchemeReviewOpinionVo reviewOpinionVo = review.getReviewOpinionVo();
        List<BuildSchemeStaffOpinionGatherVo> list = reviewOpinionVo.getGatherVoList();
        list = list==null?new ArrayList<>():list;
        int count = list.size();
        for (int i = 0; i < list.size(); i++) {
            BuildSchemeStaffOpinionGatherVo temp = list.get(i);
            temp.setReviewOpinionStr(StringUtils.join(temp.getReviewOpinionList(),"\n"));
        }
        //1：通过  2：修改后通过  3：不通过
        Map<String,String> opionDictMap = ObjectUtils.toMap(String.class, "1","通过","2","修改后通过","3","不通过");
        //方案总得分
        BuildSchemeStaffOpinionGatherVo vo = new BuildSchemeStaffOpinionGatherVo();
        vo.setReviewStaffName("方案总得分");
        vo.setStaffType(reviewOpinionVo.getScore()+"");
        list.add(vo);
        //区域中心审核结果
        String regionOpinion = opionDictMap.get(reviewOpinionVo.getRegionChiefOpinion());
        regionOpinion = opionDictMap.get(ObjectUtils.nvlString(regionOpinion,""));
        BuildSchemeStaffOpinionGatherVo vo1 = new BuildSchemeStaffOpinionGatherVo();
        vo1.setReviewStaffName("区域中心审核结果");
        vo1.setStaffType(regionOpinion);
        list.add(vo1);
        //区域中心总工意见
        BuildSchemeStaffOpinionGatherVo vo2 = new BuildSchemeStaffOpinionGatherVo();
        vo2.setReviewStaffName("区域中心总工意见");
        vo2.setStaffType(reviewOpinionVo.getRegionChiefDetailOpinion());
        list.add(vo2);
        //海外事业部审核结果
        String overOpinion = opionDictMap.get(reviewOpinionVo.getOverseasChiefOpinion());
        overOpinion = opionDictMap.get(ObjectUtils.nvlString(overOpinion,""));
        BuildSchemeStaffOpinionGatherVo vo3 = new BuildSchemeStaffOpinionGatherVo();
        vo3.setReviewStaffName("海外事业部审核结果");
        vo3.setStaffType(overOpinion);
        list.add(vo3);
        //海外事业部总工意见
        BuildSchemeStaffOpinionGatherVo vo4 = new BuildSchemeStaffOpinionGatherVo();
        vo4.setReviewStaffName("海外事业部总工意见");
        vo4.setStaffType(reviewOpinionVo.getOverseasChiefDetailOpinion());
        list.add(vo4);
        EasyExcel.write(response.getOutputStream())
                .head(BuildSchemeStaffOpinionGatherVo.class)
            .registerWriteHandler(new RowWriteHandler() {
                @Override
                public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, 
                            Row row, Integer relativeRowIndex, Boolean isHead) {
                     if(row.getRowNum() < count+1)
                         return;
                    CellRangeAddress addr1 = new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 4);
                    writeSheetHolder.getSheet().addMergedRegionUnsafe(addr1);
                }
            })
            .sheet("意见")
            .doWrite(list);
        return review;        
    }
}
