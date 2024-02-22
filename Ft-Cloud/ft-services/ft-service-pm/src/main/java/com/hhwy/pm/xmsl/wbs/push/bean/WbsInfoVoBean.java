package com.hhwy.pm.xmsl.wbs.push.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：wbs信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
@Data
public class WbsInfoVoBean {


    //WBS 分类码
    private String wbsCode;
    //WBS 名称
    private String wbsName;
    //p6 编码
    private String objectId;
    //p6 父级编码
    private String parentObjectId;

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String pid;

    private List<WbsInfoVoBean> children;

    public static WbsInfoVoBean parseWbs(XmslWbs wbs){
        WbsInfoVoBean vo = new WbsInfoVoBean();
        vo.setWbsCode(wbs.getSelfCode());
        vo.setWbsName(wbs.getName());
        vo.setObjectId(wbs.getPtVar4());
        vo.setChildren(new ArrayList<>(10));
        vo.setId(wbs.getId());
        vo.setPid(wbs.getParentId());
        return vo;
    }

}