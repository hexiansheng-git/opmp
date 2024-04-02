package com.hhwy.flowable.service.fanwei;

/**
 * @author Forever
 *
 */
public class Commission {
	

    /**消息类型区分。
	    0：待阅；
	    1：待办；
	    2：预警；
	    3：日程；
	    4：订单；
	    5：消息。
	    6：报销
	    注：传相应数字即可*/
    private String contentType;
  
    
    /**
     * 只是待办、待阅2中状态的创建时间
		业务系统创建待办的时间，更新办理状态时，此时间不变。
     */
    private Long createdTime;
  
    
    /**
     *每条个人待办/已办的标识ID （应一致），作为变更标识【number类型，默认为0】
	   注：DataId和DataNo二选一，选择DataNO时DataId必须为0
     */
    private int dataId;

    /**
     * 每条个人待办/已办的标识ID （应一致），作为变更标识【String类型】
    	注：DataId和DataNo二选一
     */
    private String dataNo;
   
    
    /**
     *  0：待阅
		1：待办；
		2：已办；
		3：已阅
		4：挂起
		5：签收
		6：恢复
		7：废弃/删除
		8：订单推送
		9：结束
		10:报销推送
		注：传相应数字即可
     */
    private String doType;
    
    //当前审批人工号EMPCODE
    private String doUserId;
    
    /**
     * 流程名称
     */
    private String flowName;
    
    /**
     * 流程ID
     */
    private String flowId;
    
    /**
     * 流程实例id
     */
    private String flowInstid;
    
    /**
     * 节点id
     */
    private String nodeID;
    
    /**
     * 节点名称
     */
    private String nodeName;
    
    /**
     * PC门户待办链接
		注：PcActUrl和OtherActUrl传一致即可
		（类似于单点地址和功能连接地址，分为两个部分前半部分4A代理地址由门户配置，后半部分的具体跳转地址参数需要业务部门填写<此处填写的就是后半部分>
		例：worktype=daiqian&instanceid=&userid=zhoujc&nid=256405
		）
     */
    private String otherActUrl;
    
    /**
     * PC门户待办链接
     */
    private String pcActUrl;
    
    /**
     * 业务系统标识:
		单位代码_系统名称
		某OA：XXX_OA
		单位代码详见附件表1-推广单位对应代码
     *
     */
    private String sysType;
    
    /**
     * 同writetime
     */
    private long updateTime;
    
    /**
     *  业务系统特定模块
		直接以中文描述
     */
    private String sysModel;
    
    /**
     *  正常：0
		紧急：1
		重要：2
		加急：3
		注：传相应数字即可（不够协商，没有赋0）
     */
    private String urgentLevel;
    
    /**
     * 单位名称
     */
    private String corpName;
    /**
     * 代办标题
     */
    private String title;
    
    /**
     * 待办、待阅、已办、已阅等状态更新的时间。
		业务系统创建待办或更新待办状态的的时间。
		创建待办时和createTime时间一致，更新（变已办）时，就是更新的时间。

     */
    private long writeTime;
    
    /**
     *  由门户提供的授权码
		授权码+系统标识判断数据合法性
		建议对方可配置
	
     */
    private String verifykey;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getDataNo() {
		return dataNo;
	}

	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getDoUserId() {
		return doUserId;
	}

	public void setDoUserId(String doUserId) {
		this.doUserId = doUserId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowInstid() {
		return flowInstid;
	}

	public void setFlowInstid(String flowInstid) {
		this.flowInstid = flowInstid;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getOtherActUrl() {
		return otherActUrl;
	}

	public void setOtherActUrl(String otherActUrl) {
		this.otherActUrl = otherActUrl;
	}

	public String getPcActUrl() {
		return pcActUrl;
	}

	public void setPcActUrl(String pcActUrl) {
		this.pcActUrl = pcActUrl;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getSysModel() {
		return sysModel;
	}

	public void setSysModel(String sysModel) {
		this.sysModel = sysModel;
	}

	public String getUrgentLevel() {
		return urgentLevel;
	}

	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}

	public String getVerifykey() {
		return verifykey;
	}

	public void setVerifykey(String verifykey) {
		this.verifykey = verifykey;
	}

	

}
