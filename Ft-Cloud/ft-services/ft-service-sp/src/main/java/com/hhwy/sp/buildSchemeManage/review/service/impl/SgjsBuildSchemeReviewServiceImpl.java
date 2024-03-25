package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.constant.TaskStatus;
import com.hhwy.sp.buildSchemeManage.review.domain.*;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.*;
import com.hhwy.sp.buildSchemeManage.review.mapper.*;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Service
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


    public SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    public List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
    }

    @Transactional
    public int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setId(IdWorker.createId());
        sgjsBuildSchemeReview.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        sgjsBuildSchemeReview.setCreateUserName(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setCreateTime(DateUtils.getNowDate());
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
                if("1".equals(flowNodeMark) || "2".equals(flowNodeMark)){
                    //区域中心或海外事业部角色审批节点
                    List<SgjsBuildSchemeReviewStaff> staffList = sgjsBuildSchemeReviewStaffMapper.getListByReviewId(id, flowNodeMark);
                    List<SgjsBuildSchemeReviewStaff> staffListRes = this.assembleStaffList(staffList);
                    review.setReviewStaffList(staffListRes);
                }else if("3".equals(flowNodeMark) || "4".equals(flowNodeMark) || "5".equals(flowNodeMark) || "6".equals(flowNodeMark)){
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
                }else if("7".equals(flowNodeMark) || "8".equals(flowNodeMark)){
                    //区域总工审批节点  汇总 3 ，4节点数据    海外事业部总工审批节点  汇总 3 ，4 ，5 ，6节点数据
                    BuildSchemeReviewOpinionVo reviewOpinionVo = new BuildSchemeReviewOpinionVo();
                    SgjsBuildSchemeReviewOpinion reviewOpinionQuery = new SgjsBuildSchemeReviewOpinion();
                    reviewOpinionQuery.setReviewId(id);
                    String flowNodeMarkQuery;
                    if("7".equals(flowNodeMark)){
                        reviewOpinionQuery.setType("1");
                        flowNodeMarkQuery = "1";
                    }else {
                        reviewOpinionQuery.setType("2");
                        flowNodeMarkQuery = "2";
                    }
                    SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(reviewOpinionQuery);
                    if(reviewOpinion != null){
                        reviewOpinionVo.setType(reviewOpinion.getType());
                        reviewOpinionVo.setChiefOpinion(reviewOpinion.getChiefOpinion());
                        reviewOpinionVo.setDetailOpinion(reviewOpinion.getDetailOpinion());
                    }
                    List<BuildSchemeStaffOpinionGatherVo> staffOpinionGatherVoList = this.getStaffOpinionGatherVoList(id, flowNodeMarkQuery);
                    reviewOpinionVo.setGatherVoList(staffOpinionGatherVoList);
                    Double average = staffOpinionGatherVoList.stream().filter(o -> o.getScore() != null).collect(Collectors.averagingDouble(BuildSchemeStaffOpinionGatherVo::getScore));
                    reviewOpinionVo.setScore(average);
                    review.setReviewOpinionVo(reviewOpinionVo);
                }else if("9".equals(flowNodeMark) || "10".equals(flowNodeMark)){
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
        List<SgjsBuildSchemeReviewOpinion> reviewOpinionList = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinionList(reviewOpinionQuery);
        Map<String, SgjsBuildSchemeReviewOpinion> typeReviewOpinionMap = reviewOpinionList.stream().collect(Collectors.toMap(SgjsBuildSchemeReviewOpinion::getType, o -> o));

        if(typeReviewOpinionMap.containsKey("1")){
            SgjsBuildSchemeReviewOpinion regionChief = typeReviewOpinionMap.get("1");
            reviewOpinionRecord.setRegionChiefOpinion(regionChief.getChiefOpinion());
            reviewOpinionRecord.setRegionChiefDetailOpinion(regionChief.getDetailOpinion());
        }
        if(typeReviewOpinionMap.containsKey("2")){
            SgjsBuildSchemeReviewOpinion regionChief = typeReviewOpinionMap.get("2");
            reviewOpinionRecord.setOverseasChiefOpinion(regionChief.getChiefOpinion());
            reviewOpinionRecord.setOverseasChiefDetailOpinion(regionChief.getDetailOpinion());
        }
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

        if("edit".equals(saveType)){
            //编辑保存
            this.updateSgjsBuildSchemeReview(review);
        }

        if("dispose".equals(saveType)){
            //处理保存
            Long id = review.getId();
            String flowNodeMark = review.getFlowNodeMark();
            String schemeLevel = review.getSchemeLevel();
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                //二三级方案
                if("1".equals(flowNodeMark) || "2".equals(flowNodeMark)){
                    //区域中心或海外事业部角色审批节点
                    List<SgjsBuildSchemeReviewStaff> staffList = review.getReviewStaffList();
                    this.saveStaffList(id,flowNodeMark,staffList);
                }else if("3".equals(flowNodeMark) || "4".equals(flowNodeMark) || "5".equals(flowNodeMark) || "6".equals(flowNodeMark)){
                    //专家或部门审批节点
                    String userName = SecurityUtils.getUserName();
//                    String userName = review.getUserName();
                    BuildSchemeStaffOpinionVo staffOpinionVo = review.getStaffOpinionVo();
                    this.saveStaffOpinionVo(id,flowNodeMark,userName,staffOpinionVo);
                }else if("7".equals(flowNodeMark) || "8".equals(flowNodeMark)){
                    //区域总工审批节点 / 海外事业部总工审批节点
                    BuildSchemeReviewOpinionVo reviewOpinionVo = review.getReviewOpinionVo();
                    this.saveReviewOpinionVo(id,flowNodeMark,reviewOpinionVo);
                }else if("9".equals(flowNodeMark) || "10".equals(flowNodeMark)){
                    //海外事业部总工意见为修改后通过后的审批节点：需要修改字段-修改结果
                    List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = review.getReviewOpinionRecord().getStaffOpinionRecordList();
                    this.updateStaffOpinionRecordList(staffOpinionRecordList);
                }else {
                    //节点标识为空：当前为驳回后的发起人节点
                    List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList = review.getReviewOpinionRecord().getStaffOpinionRecordList();
                    this.updateStaffOpinionRecordList(staffOpinionRecordList);
                }
            }else if("4".equals(schemeLevel) && "1".equals(flowNodeMark)){
                //四级方案区域中心审批节点
                List<SgjsBuildSchemeReviewStaff> staffList = review.getReviewStaffList();
                this.saveStaffList(id,flowNodeMark,staffList);
            }
        }
        return review.getId();
    }

    private void updateStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> staffOpinionRecordList) {
        if(CollectionUtils.isNotEmpty(staffOpinionRecordList)){
            sgjsBuildSchemeStaffOpinionRecordMapper.updateUpdateResult(staffOpinionRecordList);
        }
    }

    /**
     * 针对7,8节点的意见详情
     * @param reviewId
     * @param flowNodeMark
     * @param reviewOpinionVo
     */
    private void saveReviewOpinionVo(Long reviewId, String flowNodeMark, BuildSchemeReviewOpinionVo reviewOpinionVo) {
        SgjsBuildSchemeReviewOpinion queryParam = new SgjsBuildSchemeReviewOpinion();
        queryParam.setReviewId(reviewId);
        queryParam.setFlowNodeMark(flowNodeMark);
        SgjsBuildSchemeReviewOpinion reviewOpinion = sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(queryParam);
        if(reviewOpinion != null){
            reviewOpinion.setUpdateUser(SecurityUtils.getUserName());
            reviewOpinion.setUpdateTime(DateUtils.getNowDate());
            reviewOpinion.setScore(reviewOpinionVo.getScore());
            reviewOpinion.setChiefOpinion(reviewOpinionVo.getChiefOpinion());
            reviewOpinion.setDetailOpinion(reviewOpinionVo.getDetailOpinion());
            sgjsBuildSchemeReviewOpinionMapper.updateSgjsBuildSchemeReviewOpinion(reviewOpinion);
        }else {
            reviewOpinion = new SgjsBuildSchemeReviewOpinion();
            reviewOpinion.setId(IdWorker.createId());
            reviewOpinion.setReviewId(reviewId);
            reviewOpinion.setFlowNodeMark(flowNodeMark);
            reviewOpinion.setType("7".equals(flowNodeMark)?"1":"2");
            reviewOpinion.setChiefOpinion(reviewOpinionVo.getChiefOpinion());
            reviewOpinion.setDetailOpinion(reviewOpinionVo.getDetailOpinion());
            reviewOpinion.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            reviewOpinion.setCreateUserName(SecurityUtils.getUserName());
            reviewOpinion.setCreateTime(DateUtils.getNowDate());
            sgjsBuildSchemeReviewOpinionMapper.insertSgjsBuildSchemeReviewOpinion(reviewOpinion);
        }
    }

    /**
     * 针对3,4,5,6节点的意见详情数据
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
        for (SgjsBuildSchemeStaffOpinion staffOpinion : staffOpinionList) {
            staffOpinion.setId(IdWorker.createId());
            staffOpinion.setReviewId(reviewId);
            staffOpinion.setReviewStaffId(userName);
            staffOpinion.setReviewStaffName(SecurityUtils.getSysUser().getNickName());
            staffOpinion.setFlowNodeMark(flowNodeMark);
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
            this.checkSignle(reviewId,flowNodeMark,insertList);
            sgjsBuildSchemeReviewStaffMapper.insertSgjsBuildSchemeReviewStaffList(insertList);
        }
    }

    private void checkSignle(Long reviewId, String flowNodeMark, List<SgjsBuildSchemeReviewStaff> insertList) {
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
            String name = "1".equals(flowNodeMark)?"海外事业部":"区域中心";
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
        int syncNum;
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(null);
        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            syncNum = 0;
            return "已同步 " + syncNum + " 条数据！";
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
            syncNum = reviewListNew.size();
            sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(reviewListNew);
            return "已同步 " + syncNum + " 条数据！";
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
                this.putSchemeListToReview(schemeList, review);
                review.setUpdateUser(SecurityUtils.getUserName());
                review.setUpdateTime(DateUtils.getNowDate());
                updateList.add(review);
            }else {
                this.setInsertList(insertList,schemeList,userName,nickName);
            }
        }

        if(CollectionUtils.isNotEmpty(insertList)){
            sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(insertList);
        }

        if(CollectionUtils.isNotEmpty(updateList)){
            sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReviewList(updateList);
        }
        return "";
    }

    private void setInsertList(List<SgjsBuildSchemeReview> insertList,SgjsBuildSchemeList schemeList,String userName,String nickName){
        SgjsBuildSchemeReview review = new SgjsBuildSchemeReview();
        review.setId(IdWorker.createId());
        this.putSchemeListToReview(schemeList, review);
        review.setSchemeInitiatorId(userName);
        review.setSchemeInitiatorName(nickName);
        review.setSubmitDate(DateUtils.getNowDate());
        review.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        review.setCreateUserName(SecurityUtils.getUserName());
        review.setCreateTime(DateUtils.getNowDate());
        insertList.add(review);
    }

    private void putSchemeListToReview(SgjsBuildSchemeList schemeList,SgjsBuildSchemeReview review){
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
    public List<SgjsBuildSchemeList> getSchemeList() {
        List<SgjsBuildSchemeList> lastValidSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(null);
        if(CollectionUtils.isEmpty(lastValidSchemeListList)){
            return lastValidSchemeListList;
        }

        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewMapper.getListByQueryVo(new BuildSchemeReviewQueryVo());
        Set<String> schemeNumSet = reviewList.stream().map(SgjsBuildSchemeReview::getSchemeNum).collect(Collectors.toSet());
        List<SgjsBuildSchemeList> resultList = lastValidSchemeListList.stream().filter(sgjsBuildSchemeList -> !schemeNumSet.contains(sgjsBuildSchemeList.getSchemeNum())).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public void submitBuildSchemeReviewProcess(Long id) {
        sgjsBuildSchemeReviewMapper.updateTaskStatus(id, TaskStatus.IN_PROGRESS.getCode());
    }

    @Override
    public void updateBuildSchemeReviewProcess(Long id) {
        sgjsBuildSchemeReviewMapper.updateTaskStatus(id, TaskStatus.COMPLETED.getCode());
        sgjsBuildSchemeReviewMapper.updateApprovalTime(id);
    }

    @Override
    public void approvedAfterModification(Long id) {
        this.recordData(id);
    }
}
