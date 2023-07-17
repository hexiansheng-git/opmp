package com.hhwy.utils.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 通用基础类
 *
 * @author mls
 */

@ToString
public class CommonBaseEntity extends BaseEntity {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @NotNull(message = "页码不能为空", groups = {ValidationGroups.Select.class})
    private Integer pageNum;
    @NotNull(message = "页数不能为空", groups = {ValidationGroups.Select.class})
    private Integer pageSize;
    private String orderByColumn;
    private String isAsc = "asc";


    /**
     * 下一步任务节点id
     */
    private String nextNodeId;
    /**
     * 下一步任务处理人
     */
    private String nextTaskAssignee;
    /**
     * 流程实例id(如果是已经发起过的流程，则是必填)
     */
    private String instanceId;
    /**
     * 当前任务id(如果是已发起的流程)，则是必填
     */
    private String currentTaskId;
    /**
     * 流程key(如果是未发起的流程)，则是必填
     */
    private String processKey;
    /**
     * 业务数据id
     */
    private String businessId;
    /**
     * 业务表名称
     */
    private String businessTableName;
    /**
     * 备注
     */
    private String comment;
    /**
     * 其他参数，map类型
     */
    private String variables;

    /**
     * 当前任务名
     */
    private String processTaskName;
    /**
     * 当前处理人
     */
    private String processTaskMan;
    private String processTaskManId;

    /**
     * '流程状态 0-未发起; 1审核中; 4-流程已结束,业务未结束; 5-流程和业务都已结束'
     */
    private String taskStatus;

    private String activityTableId;

    private String taskId;
    //是否归档0否1是
    private String isDoc;
    //表单配置地址
    private String formUrl;
    //0否1是   1代表是第一节点 并且发起人等于当前登录人
    private String isFirstNode;

    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 更新人姓名
     */
    private String updateUserName;


    private Long deptId;


    public String getIsFirstNode() {
        return isFirstNode;
    }

    public void setIsFirstNode(String isFirstNode) {
        this.isFirstNode = isFirstNode;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActivityTableId() {
        return activityTableId;
    }

    public void setActivityTableId(String activityTableId) {
        this.activityTableId = activityTableId;
    }

    public CommonBaseEntity() {
    }

    public String getOrderBy() {
        return StringUtils.isEmpty(this.orderByColumn) ? "" : StringUtils.toUnderScoreCase(this.orderByColumn) + " " + this.isAsc;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return this.orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return this.isAsc;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNextTaskAssignee() {
        return nextTaskAssignee;
    }

    public void setNextTaskAssignee(String nextTaskAssignee) {
        this.nextTaskAssignee = nextTaskAssignee;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessTableName() {
        return businessTableName;
    }

    public void setBusinessTableName(String businessTableName) {
        this.businessTableName = businessTableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getProcessTaskName() {
        return processTaskName;
    }

    public void setProcessTaskName(String processTaskName) {
        this.processTaskName = processTaskName;
    }

    public String getProcessTaskMan() {
        return processTaskMan;
    }

    public void setProcessTaskMan(String processTaskMan) {
        this.processTaskMan = processTaskMan;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setIsAsc(String isAsc) {
        if (StringUtils.isNotEmpty(isAsc)) {
            if ("ascending".equals(isAsc)) {
                isAsc = "asc";
            } else if ("descending".equals(isAsc)) {
                isAsc = "desc";
            }

            this.isAsc = isAsc;
        }

    }

    public String getIsDoc() {
        return isDoc;
    }

    public void setIsDoc(String isDoc) {
        this.isDoc = isDoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }


    public String getProcessTaskManId() {
        return processTaskManId;
    }

    public void setProcessTaskManId(String processTaskManId) {
        this.processTaskManId = processTaskManId;
    }
}
