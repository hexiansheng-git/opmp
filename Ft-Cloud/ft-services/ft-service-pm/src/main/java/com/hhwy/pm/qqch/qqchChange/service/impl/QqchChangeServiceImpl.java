package com.hhwy.pm.qqch.qqchChange.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
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
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.PlatMenuTreeUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
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
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IXmslContractInfoService contractInfoService;


    public QqchChange getQqchChange(QqchChange qqchChange) {
        return qqchChangeMapper.getQqchChange(qqchChange);
    }

    @Override
    public BigDecimal effectVersion() {
        final String key = "qqchValidVersion::"+SecurityUtils.getTenantKey();
        if(redisUtils.hasKey(key)){
            return ObjectUtils.nvlBigDecimal(redisUtils.get(key),BigDecimal.ONE);
        }
        BigDecimal version =qqchChangeMapper.effectVersion();
        version = ObjectUtils.nvlBigDecimal(version,BigDecimal.ONE);
        redisUtils.setAndExpire(key,version+"",1, TimeUnit.HOURS);
        return version;
    }
    @Override
    public List<QqchChange> list(QqchChange qqchChange) {
        List<QqchChange> list = qqchChangeMapper.getQqchChangeList(qqchChange);
        //计算完成百分比
        for (int i = 0; i < list.size(); i++) {
            QqchChange temp = list.get(i);
            temp.setPlanNum(ObjectUtils.nvl(temp.getPlanNum()));
            temp.setFinishNum(ObjectUtils.nvl(temp.getFinishNum()));
            BigDecimal ratio = BigDecimalUtils.divideMay0(temp.getFinishNum(),temp.getPlanNum(),2);
            temp.setFinishRatio(ObjectUtils.nvlBigDecimal(ratio).multiply(new BigDecimal("100")));;
        }
        return list;
    }

    public List<QqchChange> getQqchChangeList(QqchChange qqchChange) {
        return qqchChangeMapper.getQqchChangeList(qqchChange);
    }

    @Override
    public QqchChangeVo adjustDetail() {
        QqchChangeVo change = new QqchChangeVo();
        //判断是否已结束前期评审
        Integer unValidCount = qqchChangeMapper.countUnValidReview();
        Assert.isTrue(unValidCount==null||unValidCount < 1,"前期策划评审未结束,无法进行调整");
        //判断是否有未生效的前期策划变更
        QqchChange query = new QqchChange();
        query.setValid(Constant.NO_INT);
        Integer count = qqchChangeMapper.countQqchChange(query);
        Assert.isTrue(count ==null||count < 1,"已包含未生效的前期策划变更,无法进行调整");
        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
        change.setValid(Constant.NO_INT);
        change.setProjectCode(SecurityUtils.getTenantKey());
        change.setProjectName(projectBasicInfo.getProjectName());
        BigDecimal maxVersion = VersionUtil.getMaxVersion(FlowEnum.QQCH_CHANGE.getTableName());
        maxVersion = maxVersion.compareTo(BigDecimal.ONE)==0?new BigDecimal("2.0"):maxVersion.add(BigDecimal.ONE);
        change.setVersion(maxVersion);
        change.setChangeUser(SecurityUtils.getUserId());
        change.setChangeUserName(SecurityUtils.getSysUser().getNickName());
        //如果是版本一，加载工作计划的编制人
        List<QqchChangeDetail> detailList = loadDetail(maxVersion);
        change.setDetailList(detailList);
        //
        setAmtInfo(change);
        return change;
    }

    @Override
    public QqchChangeVo detail(Long id) {
        QqchChangeVo vo = new QqchChangeVo();
        QqchChange query = new QqchChange();
        query.setId(id);
        QqchChange change = qqchChangeMapper.getQqchChange(query);
        Assert.notNull(change,"获取变更信息失败");
        BeanUtils.copyProperties(change,vo);
        //
        List<QqchChangeDetail> detailList = loadDetail(change.getVersion());
        vo.setDetailList(detailList);
        vo.setProjectCode(SecurityUtils.getTenantKey());
        //
        setAmtInfo(vo);
        return vo;
    }

    private List<QqchChangeDetail> loadDetail(BigDecimal version){
        List<SysMenu> menuList = getMenuList();
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
            if(change != null){
                List<QqchChangeDetail> detailList = detailService.getQqchChangeDetailList(new QqchChangeDetail(change.getId()));
                for (int i = 0; i < detailList.size(); i++) {
                    QqchChangeDetail temp = detailList.get(i);
                    planConfMap.put(temp.getItemName(),temp);
                }
            }
        }
        //加载menu
        List<QqchChangeDetail> list = new ArrayList<>();
        if (!ObjectNullUtil.isEmpty(menuList)) {
            List<SysMenu> sysMenuList = ListTreeUtil.formatListPidNull(menuList,SysMenu::setMenuId,SysMenu::setParentId,SysMenu::setPtVar1,SysMenu::getChildren, SysMenu::setChildren);
            if (!ObjectNullUtil.isEmpty(sysMenuList)) {
                sysMenuList.stream().forEach(item -> {
                    QqchChangeDetail qqchWorkPlanDetail = new QqchChangeDetail();
                    qqchWorkPlanDetail.setId(item.getMenuId());
                    qqchWorkPlanDetail.setItemId(item.getPath());
                    qqchWorkPlanDetail.setPid(item.getParentId());
                    qqchWorkPlanDetail.setItemName(item.getTitle());
                    qqchWorkPlanDetail.setPtVar1(item.getPtVar1());//是否叶子节点
                    qqchWorkPlanDetail.setSort(item.getSortCode() != null ? item.getSortCode().intValue() : null);
                    list.add(qqchWorkPlanDetail);
                });
            }
        }
        if (!ObjectNullUtil.isEmpty(list)) {
            list.stream().forEach(item -> {
                String itemId = item.getItemId();
                String itemName = item.getItemName();
                String ptVar1 = item.getPtVar1();//是否叶子节点
                Integer sort = item.getSort();
                QqchChangeDetail detail = planConfMap.get(item.getItemName());
                if (detail != null) {
                    BeanUtils.copyProperties(detail, item);
                    item.setId(item.getId());
                    item.setPid(item.getPid());
                    item.setItemId(itemId);
                    item.setItemName(itemName);
                    item.setSort(sort);
                    item.setPtVar1(ptVar1);//是否叶子节点
                }
            });
            List<QqchChangeDetail> tree = ListTreeUtil.formatTree(list, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()),
                    QqchChangeDetail::getChildren, QqchChangeDetail::setChildren);
            return tree;
        }
//        Assert.notEmpty(detailList,"版本"+version+"前期策划变更的工作安排为空");
        return new ArrayList<>();
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

    @Override
    @Transactional
    public void save(QqchChangeVo vo) {
        //校验
        List<QqchChangeDetail> detailList = check(vo);
        //处理明细
        int planNum = 0;
        for (int i = 0; i < detailList.size(); i++) {
            QqchChangeDetail temp = detailList.get(i);
            new AddBaseInfoUtil().addBaseEntity(temp);
            temp.setMainId(vo.getId());
            temp.setValid(Constant.NO_INT);
            if(StringUtils.equals(temp.getLeaf(),"1") && temp.getIsFirst()==Constant.YES_INT)
                planNum++;
        }
        vo.setPlanNum(planNum);
        boolean isNew = vo.getId() == null;
        if(isNew){
            new AddBaseInfoUtil<>(vo);
            vo.setValid(Constant.NO_INT);
            this.qqchChangeMapper.insertQqchChange(vo);
        }else{
            new AddBaseInfoUtil<>().update(vo);
            this.qqchChangeMapper.updateQqchChange(vo);
            this.qqchChangeMapper.deleteDetail(vo.getId());
        }
        for (int i = 0; i < detailList.size(); i++) {
            QqchChangeDetail temp = detailList.get(i);
            temp.setMainId(vo.getId());
        }
        detailService.insertQqchChangeDetailList(detailList);
    }

    private List<QqchChangeDetail> check(QqchChangeVo vo){
        Assert.notNull(vo,"数据缺失");
        Assert.notNull(vo.getVersion(),"version不能为空");
        Assert.notEmpty(vo.getDetailList(),"工作安排不能为空");
        //判断是否已经有存在的版本
        QqchChange query = new QqchChange();
        query.setVersion(vo.getVersion());
        query.setId(vo.getId());
        Integer count = qqchChangeMapper.countQqchChange(query);
        Assert.isTrue(count < 1,"已存在版本:"+vo.getVersionStr()+"的前期策划变更，请返回台账刷新。");

        List<QqchChangeDetail> detailList = TreeUtil.treeToList(vo.getDetailList());
        Assert.notEmpty(detailList,"工作安排格式不正确，解析结果为空");
        if(StringUtils.equals(vo.getSubmitFlag(),"1")){
            JyDetailsUtil.jyDetails(Arrays.asList(vo),ValidationGroups.Save.class);
            int editingNum = 0;
            //若为提交，工作安排至少得有一个编制内容、校验编制人、计划完成日期
            for (int i = 0; i < detailList.size(); i++) {
                QqchChangeDetail temp = detailList.get(i);
                if(temp.getIsFirst()==Constant.NO_INT || temp.getIsFirst() == null)
                    continue;
                editingNum++;
                Assert.isTrue(StringUtils.isNotBlank(temp.getItemId()),"工作安排，"+temp.getItemName()+":itemId不能为空");
                Assert.isTrue(StringUtils.isNotBlank(temp.getItemName()),"工作安排，"+temp.getItemName()+":itemName不能为空");
                Assert.notNull(temp.getEditorFirst(),"工作安排，"+temp.getItemName()+":编制人ID不能为空");
                Assert.notNull(temp.getEditorFirstName(),"工作安排，"+temp.getItemName()+":编制人不能为空");
                Assert.notNull(temp.getFinishTimeFirst(),"工作安排，"+temp.getItemName()+":计划完成日期不能为空");
            }
            Assert.isTrue(editingNum>0,"工作安排至少得有一个编制内容项!");
            //设置变更提交时间
            vo.setChangeSubmitDate(new Date());
        }
        return detailList;
    }

    @Override
    @Transactional
    public void finishFlow(Long businessId) {
        Assert.notNull(businessId,"业务ID不能为空");
        QqchChange query = new QqchChange();
        query.setId(businessId);
        QqchChange qqchChange = this.getQqchChange(query);
        Assert.notNull(qqchChange,"获取前期策划变更失败");
        //1 修改valid
        query.setValid(Constant.YES_INT);
        new AddBaseInfoUtil<>().update(query);
        this.qqchChangeMapper.updateQqchChange(query);
        //version 扔redis
        final String key = "qqchValidVersion";
        redisUtils.setAndExpire(key,qqchChange.getVersion()+"",1, TimeUnit.HOURS);
    }

    @Override
    @Transactional
    public void editingFinishFlow(Long businessId) {
        Assert.notNull(businessId,"业务ID不能为空");
        QqchChange query = new QqchChange();
        query.setId(businessId);
        QqchChange qqchChange = this.getQqchChange(query);
        Assert.notNull(qqchChange,"获取前期策划变更失败");
        //1 获取
        QqchChangeDetail qqchChangeDetail = new QqchChangeDetail();
        qqchChangeDetail.setMainId(businessId);
        qqchChangeDetail.setEditorFirst(SecurityUtils.getUserId());
        qqchChangeDetail.setPtVar1("1");
        Integer count = this.qqchChangeMapper.countEditQqchChangeDetail(qqchChangeDetail);
        if(count < 1)
            return;
        query.setFinishNum(count);
        qqchChangeMapper.updateSubFinishNum(query);
    }

    @Override
    @Transactional
    public void reviewFinishFlow(Long businessId) {
        QqchChange qqchChange= getWithValid(businessId);
        //1 获取
        QqchChangeDetail qqchChangeDetail = new QqchChangeDetail();
        qqchChangeDetail.setMainId(businessId);
        qqchChangeDetail.setReviewerId(SecurityUtils.getUserId());
        List<QqchChangeDetail> list = detailService.getQqchChangeDetailList(qqchChangeDetail);
        if(CollectionUtils.isEmpty(list))
            return ;
        List<Long> idList = list.stream().map(r->r.getId()).collect(Collectors.toList());
        qqchChangeMapper.updateReviewFinishTime(idList);
    }

    @Override
    @Transactional
    public void reviewAllFinishFlow(Long businessId) {
        QqchChange change= getWithValid(businessId);
        change.setReviewFinishDate(new Date());
        this.qqchChangeMapper.updateQqchChange(change);
    }


    private QqchChange getWithValid(Long businessId){
        Assert.notNull(businessId,"业务ID不能为空");
        QqchChange query = new QqchChange();
        query.setId(businessId);
        QqchChange qqchChange = this.getQqchChange(query);
        Assert.notNull(qqchChange,"获取前期策划变更失败");
        return qqchChange;
    }

    @Override
    public List<SysMenu> authMenuList(Long mainId, String authFlag) {
        List<SysMenu> menuTreeList = getMenuList();
        if(!StringUtils.equals(authFlag,"1"))
            return menuTreeList;
        QqchChangeDetail query = new QqchChangeDetail();
        query.setMainId(mainId);
        query.setEditorFirst(SecurityUtils.getUserId());
        List<QqchChangeDetail> list = detailService.getQqchChangeDetailList(query);
        if(CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        //过掉出编制人的菜单
        Set<String> authNameSet = list.stream().map(r->r.getItemName()).collect(Collectors.toSet());
        List<SysMenu> menuList = PlatMenuTreeUtils.menuTree2List(menuTreeList);
        //全部前期策划编制菜单 map
        Map<Long,SysMenu> menuMap = menuList.stream().collect(Collectors.toMap(r->r.getMenuId(),r->r));
        //获取用户授权菜单
        List<SysMenu> authMenuList = menuList.stream().filter(r->authNameSet.contains(r.getTitle())).collect(Collectors.toList());
        List<SysMenu> authAllList = new ArrayList<>();
        Set<Long> existsMenuId = new HashSet<>();
        for (int i = 0; i < authMenuList.size(); i++) {
            putParent(authMenuList.get(i),menuMap,authAllList,existsMenuId);
        }
        //转树形
        List<SysMenu> finalTreeList = (new PlatMenuTreeUtils()).menuList(authAllList);
        return finalTreeList;
    }

    private void putParent(SysMenu menu,Map<Long,SysMenu> menuMap,List<SysMenu> list,Set<Long> existsMenuIdSet){
        if(!existsMenuIdSet.contains(menu.getMenuId())){
            list.add(menu);
            existsMenuIdSet.add(menu.getMenuId());
        }
        if(menu.getParentId() ==null)
            return ;
        SysMenu p = menuMap.get(menu.getParentId());
        if(p != null){
            putParent(p,menuMap,list,existsMenuIdSet);
        }
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
        int result = qqchChangeMapper.deleteQqchChange(qqchChange);
        //删除子级
        qqchChangeMapper.deleteDetail(qqchChange.getId());
        return result;
    }

    @Transactional
    public int deleteQqchChangeByPks(List<Long> qqchChangePkList) {
        return qqchChangeMapper.deleteQqchChangeByPks(qqchChangePkList);
    }

    /**
     * 设置项目分类、合同有效金额万美元
     * @param qqchChange
     */
    private void setAmtInfo(QqchChangeVo qqchChange){
        //如果为提交，返回合同金额、项目分类
        XmslContractInfo contractInfo = contractInfoService.getValidMaxVersionContractInfo();
        //获取有效金额万美元
        contractInfoService.setEffectiveAmountDollar(contractInfo);
        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
        qqchChange.setProjectCategory(projectBasicInfo.getProjectCategory());
        qqchChange.setAmount(ObjectUtils.nvlBigDecimal(contractInfo.getEffectiveAmountDollar()).divide(new BigDecimal(10000),4, RoundingMode.HALF_UP));
    }
}
