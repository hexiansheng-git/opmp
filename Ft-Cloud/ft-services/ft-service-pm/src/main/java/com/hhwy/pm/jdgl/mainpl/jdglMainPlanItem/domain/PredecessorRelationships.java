package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;


/**
 * 功能：紧前逻辑关系信息
 * 作者: fushudong
 * 时间: 2023/09/13
 */
public class PredecessorRelationships {

    //unique ID
    private String objectId;
    //紧前作业唯一标识
    private String predecessorActivityObjectId;
    //紧前项目唯一标识
    private String predecessorProjectObjectId;
    //紧前作业代码
    private String predecessorActivityId;
    //紧前作业名称
    private String predecessorActivityName;
    //后置作业代码
    private String successorActivityId;
    //后置作业名称
    private String successorActivityName;
    //紧前作业唯一标识
    private String successorActivityObjectId;
    //紧前项目唯一标识
    private String successorProjectObjectId;
    //逻辑关系类型（finish to start,finish to finish,start to start,start to finish）
    private String type;
    //延时
    private String lag;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPredecessorActivityObjectId() {
        return predecessorActivityObjectId;
    }

    public void setPredecessorActivityObjectId(String predecessorActivityObjectId) {
        this.predecessorActivityObjectId = predecessorActivityObjectId;
    }

    public String getPredecessorActivityId() {
        return predecessorActivityId;
    }

    public void setPredecessorActivityId(String predecessorActivityId) {
        this.predecessorActivityId = predecessorActivityId;
    }

    public String getPredecessorActivityName() {
        return predecessorActivityName;
    }

    public void setPredecessorActivityName(String predecessorActivityName) {
        this.predecessorActivityName = predecessorActivityName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLag() {
        return lag;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }

    public String getPredecessorProjectObjectId() {
        return predecessorProjectObjectId;
    }

    public void setPredecessorProjectObjectId(String predecessorProjectObjectId) {
        this.predecessorProjectObjectId = predecessorProjectObjectId;
    }

    public String getSuccessorActivityId() {
        return successorActivityId;
    }

    public void setSuccessorActivityId(String successorActivityId) {
        this.successorActivityId = successorActivityId;
    }

    public String getSuccessorActivityName() {
        return successorActivityName;
    }

    public void setSuccessorActivityName(String successorActivityName) {
        this.successorActivityName = successorActivityName;
    }

    public String getSuccessorActivityObjectId() {
        return successorActivityObjectId;
    }

    public void setSuccessorActivityObjectId(String successorActivityObjectId) {
        this.successorActivityObjectId = successorActivityObjectId;
    }

    public String getSuccessorProjectObjectId() {
        return successorProjectObjectId;
    }

    public void setSuccessorProjectObjectId(String successorProjectObjectId) {
        this.successorProjectObjectId = successorProjectObjectId;
    }
}