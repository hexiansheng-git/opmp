package com.hhwy.pm.warn.impl;

import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarnServiceImpl implements WarnService {

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    private static final String WARN_TEMPLATE = "您好，【项目名称】的【预警项名称】未能按要求完成，请及时进行查看。\n" + "预警规则：【预警规则】";


    @Override
    public void addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnUrl, String warnScope, String tenantKey) {
        TWarn tWarn = new TWarn();
        tWarn.setWarnItem(warnItem.getWarnItem());
        tWarn.setWarnItemId(warnItem.getWarnItemId());
        tWarn.setWarnScopeType(warnScopeType.getWarnScopeType());
        tWarn.setWarnScope(warnScope);
        tWarn.setWarnUrl(warnUrl);
        tWarn.setTenantKey(tenantKey);

        String projectName = "";
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        if(projectInfo != null){
            projectName = projectInfo.getProjectName();
        }
        String warnContent = this.buildWarnContent(projectName, warnItem.getWarnItem(), warnItem.getWarnRule());
        tWarn.setWarnContent(warnContent);

        systemServiceApi.addWarn(tWarn);
    }


    private String buildWarnContent(String projectName,String warnItem,String warnRule){
        return WARN_TEMPLATE.replace("【项目名称】",projectName).replace("【预警项名称】",warnItem).replace("【预警规则】",warnRule);
    }
}
