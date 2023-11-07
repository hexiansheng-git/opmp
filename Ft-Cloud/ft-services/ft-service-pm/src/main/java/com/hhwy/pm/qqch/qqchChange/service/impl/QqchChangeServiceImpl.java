package com.hhwy.pm.qqch.qqchChange.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.qqch.qqchChange.mapper.QqchChangeMapper;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeDetailService;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;
import com.hhwy.pm.qqch.qqchChange.vo.QqchChangeVo;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
@Service
public class QqchChangeServiceImpl implements IQqchChangeService {

    @Autowired
    private QqchChangeMapper qqchChangeMapper;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private IQqchChangeDetailService detailService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IQqchWorkPlanDetailService workPlanDetailService;


    public QqchChange getQqchChange(QqchChange qqchChange) {
        return qqchChangeMapper.getQqchChange(qqchChange);
    }

    @Override
    public List<QqchChange> list(QqchChange qqchChange) {
        List<QqchChange> list = qqchChangeMapper.getQqchChangeList(qqchChange);
        for (int i = 0; i < list.size(); i++) {
            QqchChange temp = list.get(i);
            temp.setVersionStr("V"+temp.getVersion());
        }
        return list;
    }

    public List<QqchChange> getQqchChangeList(QqchChange qqchChange) {
        return qqchChangeMapper.getQqchChangeList(qqchChange);
    }

    @Override
    public QqchChangeVo adjustDetail() {
        //判断是否已结束前期评审
        Integer unValidCount = qqchChangeMapper.countUnValidReview();
        Assert.isTrue(unValidCount==null||unValidCount < 1,"前期策划评审未结束,无法进行调整");
        //判断是否有未生效的前期策划变更
        QqchChange query = new QqchChange();
        query.setValid(Constant.NO_INT);
        Integer count = qqchChangeMapper.countQqchChange(query);
        Assert.isTrue(count ==null||count < 1,"已包含未生效的前期策划变更,无法进行调整");
        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
        QqchChangeVo change = new QqchChangeVo();
        change.setValid(Constant.NO_INT);
        change.setProjectCode(SecurityUtils.getTenantKey());
        change.setProjectName(projectBasicInfo.getProjectName());

        BigDecimal maxVersion = VersionUtil.getMaxVersion(FlowEnum.QQCH_CHANGE.getTableName());
        change.setVersion(ObjectUtils.nvlBigDecimal(maxVersion,BigDecimal.ONE));
        change.setChangeUser(SecurityUtils.getUserId());
        change.setChangeUserName(SecurityUtils.getSysUser().getNickName());
        //如果是版本一，加载工作计划的编制人
        List<QqchChangeDetail> detailList = loadDetail(maxVersion);

        return null;
    }

    private List<QqchChangeDetail> loadDetail(BigDecimal version){
        List<SysMenu> list = getMenuList();
        //工作计划的配置信息
        Map<String,QqchChangeDetail> planConfMap = new HashMap<>();
        //如果为空，加载工作计划
        if(version == null){
            Long maxId = qqchChangeMapper.getWorkplanMaxVersion();
            QqchWorkPlanDetail query = new QqchWorkPlanDetail();
            query.setMainId(maxId);
            List<QqchWorkPlanDetail> planDetailList = workPlanDetailService.getQqchWorkPlanDetailList(query);
            //取第三阶段的编制人吧，没取到就取第二阶段的,第二阶段没取到就第一阶段的
            for (int i = 0; i < planDetailList.size(); i++) {
                QqchWorkPlanDetail temp = planDetailList.get(i);
                QqchChangeDetail tempDetail = new QqchChangeDetail();
                if(StringUtils.equals(temp.getIsThird(),"1")){
                    tempDetail = new QqchChangeDetail(temp.getIsThird(),Long.valueOf(temp.getEditorThird()),temp.getEditorThirdName(),temp.getFinishTimeThird());
                }else if(StringUtils.equals(temp.getIsSecond(),"1")){
                    tempDetail = new QqchChangeDetail(temp.getIsSecond(),Long.valueOf(temp.getEditorSecond()),temp.getEditorSecondName(),temp.getFinishTimeSecond());
                }else if(StringUtils.equals(temp.getIsFirst(),"1")){
                    tempDetail = new QqchChangeDetail(temp.getIsFirst(),Long.valueOf(temp.getEditorFirst()),temp.getEditorFirstName(),temp.getFinishTimeFirst());
                }
                planConfMap.put(temp.getItemName(),tempDetail);
            }
        }else{ //加载上一版本数据
            QqchChange change = this.qqchChangeMapper.getQqchChange(new QqchChange(version));
            Assert.notNull(change,"获取版本"+version+"的前期策划变更失败");
            List<QqchChangeDetail> detailList = detailService.getQqchChangeDetailList(new QqchChangeDetail(change.getId()));
            for (int i = 0; i < detailList.size(); i++) {
                QqchChangeDetail temp = detailList.get(i);
                
            }
        }


//        Assert.notEmpty(detailList,"版本"+version+"前期策划变更的工作安排为空");

        return null;
    }


    /**获取前期策划菜单
     * @return
     */
    private List<SysMenu> getMenuList() {
        AjaxResult ajaxResult = systemServiceApi.getQqchMenu("前期策划编制");
        if(!ajaxResult.get("code").toString().equals("200")){
            throw new CustomException("根据菜单名【前期策划编制】查询菜单信息异常");
        }
        List<SysMenu> menuList = JSONArray.parseArray(JSON.toJSONString(ajaxResult.get("data")), SysMenu.class);
        return menuList;
    }

    @Transactional
    public int insertQqchChange(QqchChange qqchChange) {
        qqchChange.setId(IdWorker.createId());
        qqchChange.setCreateUser(SecurityUtils.getUserName());
        qqchChange.setCreateTime(DateUtils.getNowDate());
        return qqchChangeMapper.insertQqchChange(qqchChange);
    }

    @Transactional
    public int insertQqchChangeList(List<QqchChange> qqchChangeList) {
        for (QqchChange qqchChange : qqchChangeList) {
            qqchChange.setId(IdWorker.createId());
            qqchChange.setCreateUser(SecurityUtils.getUserName());
            qqchChange.setCreateTime(DateUtils.getNowDate());
        }
        return qqchChangeMapper.insertQqchChangeList(qqchChangeList);
    }

    @Transactional
    public int updateQqchChange(QqchChange qqchChange) {
        qqchChange.setUpdateUser(SecurityUtils.getUserName());
        qqchChange.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeMapper.updateQqchChange(qqchChange);
    }

    @Transactional
    public int updateQqchChangeList(List<QqchChange> qqchChangeList) {
        for (QqchChange qqchChange : qqchChangeList) {
            qqchChange.setUpdateUser(SecurityUtils.getUserName());
            qqchChange.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchChangeMapper.updateQqchChangeList(qqchChangeList);
    }

    @Transactional
    public int deleteQqchChange(QqchChange qqchChange) {
        qqchChange.setUpdateUser(SecurityUtils.getUserName());
        qqchChange.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeMapper.deleteQqchChange(qqchChange);
    }

    @Transactional
    public int deleteQqchChangeByPks(List<Long> qqchChangePkList) {
        return qqchChangeMapper.deleteQqchChangeByPks(qqchChangePkList);
    }
}
