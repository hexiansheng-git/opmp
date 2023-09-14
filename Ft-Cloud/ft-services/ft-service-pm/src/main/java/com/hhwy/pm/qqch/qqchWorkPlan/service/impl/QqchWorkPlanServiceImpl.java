package com.hhwy.pm.qqch.qqchWorkPlan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.mapper.QqchWorkPlanMapper;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
@Service
public class QqchWorkPlanServiceImpl implements IQqchWorkPlanService {

    @Autowired
    private QqchWorkPlanMapper qqchWorkPlanMapper;
    @Autowired
    private CommonMapper wzchCommonMapper;
    @Autowired
    private IQqchWorkPlanDetailService qqchWorkPlanDetailService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    private final static String ONE = "1";//菜单进入
    private final static String TWO = "2";//详情和编辑
    private final static String THREE = "3";//调整
    @Override
    public BaseEntity baseInfo(Map<String, String> map) {
        /*菜单进入，version为1.0不显示历史记录*/
        /*详情/编辑/调整，显示历史记录*/
        // 主键
        String id = map.get("id");
        // 操作类型 1-菜单进入; 2-详情和编辑; 3-调整
        String type = map.get("type");
        CommonAssert.notBlank(type, "类型不能为空");

        QqchWorkPlan busData = new QqchWorkPlan();
        //获取项目中最大版本数据id
        Long idMax = wzchCommonMapper.selectCanAdjustOnly("qqch_work_plan");
        if (ONE.equals(type)) {
            /*此项目中的最大版本*/
            if (ObjectNullUtil.isEmpty(idMax)) {//代表 新增
//                busData.setId(IdWorker.createId());
                busData.setVersion(new BigDecimal("1.0"));
                //获取最新的菜单，并整合原来的数据
                List<QqchWorkPlanDetail> tree = buildTreeList(null,0);
                busData.setDetailList(tree);
            } else {//代表编辑
                //最大生效版本数据
                Long idMaxVal = wzchCommonMapper.selectCanAdjustOnlyValid("qqch_work_plan");
                Long idSel = null;
                if (idMaxVal == null) {//代表没有生效的数据
                    idSel = idMax;
                } else {
                    idSel = idMaxVal;
                }
                QqchWorkPlan workPlan = new QqchWorkPlan();
                workPlan.setId(idSel);
                QqchWorkPlan plan = this.getQqchWorkPlan(workPlan);
                BeanUtils.copyProperties(plan,busData);
                QqchWorkPlanDetail detail = new QqchWorkPlanDetail();
                detail.setMainId(plan.getId());
                detail.setDelFlag("0");
                List<QqchWorkPlanDetail> detailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(detail);
                if (!ObjectNullUtil.isEmpty(detailList)) {
                    List<QqchWorkPlanDetail> planDetailsTree = TreeUtil.build(detailList,null);
                    busData.setDetailList(planDetailsTree);
                }
            }
            busData.setVersionStr("V" + busData.getVersion());
            // 设置创建信息
            EntityUtils.setCreateUpdateInfo(busData);
        } else {
            if (THREE.equals(type)) {//代表调整数据
                if (idMax == null) {//代表没有数据
                    throw new CustomBusinessException("调整之前必须有生效数据");
                }
                //判断评审数据是否有审批中，若有就报异常
//                qqchReviewService.canAdjust();
                QqchWorkPlan qqchWorkPlan = new QqchWorkPlan();
                qqchWorkPlan.setId(idMax);
                BeanUtils.copyProperties(this.getQqchWorkPlan(qqchWorkPlan), busData);

                QqchWorkPlanDetail detail = new QqchWorkPlanDetail();
                detail.setMainId(idMax);
                detail.setDelFlag("0");
                List<QqchWorkPlanDetail> detailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(detail);
                // 如果用户的操作类型是调整就需要将单据编号的版本+1
                if ("1".equals(busData.getValid())) {
                    busData.setId(null);//调整 把id置为null
                    EntityUtils.setCreateInfo(busData);
                    BigDecimal versionCode = busData.getVersion().add(BigDecimal.ONE);
                    busData.setVersion(versionCode);
                    busData.setVersionStr("V" + versionCode);
                    busData.setTaskStatus("0");
                    busData.setValid("0");
                    //获取最新的菜单，并整合原来的数据
                    List<QqchWorkPlanDetail> tree = buildTreeList(detailList, 1);
                    busData.setDetailList(tree);
                } else {
                    busData.setVersionStr("V" + busData.getVersion().toString());
                    if (!ObjectNullUtil.isEmpty(detailList)) {
                        List<QqchWorkPlanDetail> planDetailsTree = TreeUtil.build(detailList, null);
                        busData.setDetailList(planDetailsTree);
                    }
                }
            } else {
                CommonAssert.notBlank(id, "id不能为空");
                long busId = Long.parseLong(id);
                QqchWorkPlan qqchWorkPlan = new QqchWorkPlan();
                qqchWorkPlan.setId(busId);
                BeanUtils.copyProperties(this.getQqchWorkPlan(qqchWorkPlan), busData);
                QqchWorkPlanDetail detail = new QqchWorkPlanDetail();
                detail.setMainId(busId);
                detail.setDelFlag("0");
                List<QqchWorkPlanDetail> detailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(detail);
                busData.setVersionStr("V" + busData.getVersion().toString());
                if (!ObjectNullUtil.isEmpty(detailList)) {
                    List<QqchWorkPlanDetail> planDetailsTree = TreeUtil.build(detailList, null);
                    busData.setDetailList(planDetailsTree);
                }
            }
        }
        /*查询台账数量*/
        int listCount = qqchWorkPlanMapper.getQqchWorkPlanListCount(new QqchWorkPlan());
        if (listCount > 1) {
            busData.setIsShowRecord(1);
        }
        //查询最新生效版本前期策划工作小组
        QqchWorkGroup workGroup = qqchWorkGroupService.getValidMaxVersionQqchWorkGroup();
        if(workGroup != null){
            busData.setPlanApprovalUnit(workGroup.getPlanApprovalUnit());
        }
        return busData;
    }

    /**整合树形子表
     * @param detailList
     * @param type 0新增页面 1调整
     * @return
     */
    private List<QqchWorkPlanDetail> buildTreeList(List<QqchWorkPlanDetail> detailList,int type) {
        Map<String, List<QqchWorkPlanDetail>> collect = new HashMap<>();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            collect = detailList.stream().collect(Collectors.groupingBy(QqchWorkPlanDetail::getItemName));
        }
        AjaxResult ajaxResult = systemServiceApi.getQqchMenu("前期策划编制");
        if(!ajaxResult.get("code").toString().equals("200")){
            throw new CustomBusinessException("根据菜单名【前期策划编制】查询菜单信息异常");
        }
        List<QqchWorkPlanDetail> list = new ArrayList<>();
        List<SysMenu> menuList = JSONArray.parseArray(JSON.toJSONString(ajaxResult.get("data")), SysMenu.class);
        if (!ObjectNullUtil.isEmpty(menuList)) {
            List<SysMenu> sysMenuList = ListTreeUtil.formatListPidNull(menuList,SysMenu::setMenuId,SysMenu::setParentId,SysMenu::setPtVar1,SysMenu::getChildren, SysMenu::setChildren);
            if (!ObjectNullUtil.isEmpty(sysMenuList)) {
                sysMenuList.stream().forEach(item -> {
                    QqchWorkPlanDetail qqchWorkPlanDetail = new QqchWorkPlanDetail();
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
        if (type == 0) {
            List<QqchWorkPlanDetail> tree = ListTreeUtil.formatTree(list, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), QqchWorkPlanDetail::getChildren, QqchWorkPlanDetail::setChildren);
            return tree;
        }
        if (!ObjectNullUtil.isEmpty(list)) {
            Map<String, List<QqchWorkPlanDetail>> finalCollect = collect;
            list.stream().forEach(item -> {
                Long id = item.getId();
                Long pid = item.getPid();
                String itemId = item.getItemId();
                String itemName = item.getItemName();
                String ptVar1 = item.getPtVar1();//是否叶子节点
                Integer sort = item.getSort();
                List<QqchWorkPlanDetail> details = finalCollect.get(item.getItemName());
                if (!ObjectNullUtil.isEmpty(details)) {
                    BeanUtils.copyProperties(details.get(0), item);
                    item.setId(id);
                    item.setPid(pid);
                    item.setItemId(itemId);
                    item.setItemName(itemName);
                    item.setSort(sort);
                    item.setPtVar1(ptVar1);//是否叶子节点
                }
            });
            List<QqchWorkPlanDetail> tree = ListTreeUtil.formatTree(list, o -> o.getPid() == null, (r, n) -> r.getId().equals(n.getPid()), QqchWorkPlanDetail::getChildren, QqchWorkPlanDetail::setChildren);
            return tree;
        } else {
            return null;
        }
    }


    public QqchWorkPlan getQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        return qqchWorkPlanMapper.getQqchWorkPlan(qqchWorkPlan);
    }

    public List<QqchWorkPlan> getQqchWorkPlanList(QqchWorkPlan qqchWorkPlan) {
        return qqchWorkPlanMapper.getQqchWorkPlanList(qqchWorkPlan);
    }

    @Transactional
    @Override
    public Long insertQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        List<QqchWorkPlanDetail> detailListLast = null;
        // 获取前端传入的设备明细
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            detailListLast = TreeUtil.treeToList(detailList);
        }
//        JyDetailsUtil.jyDetails(detailListLast, ValidationGroups.Save.class);
        qqchWorkPlan.setId(IdWorker.createId());
        EntityUtils.setCreateUpdateInfo(qqchWorkPlan);
        // 设置版本号码
        if (ObjectNullUtil.isEmpty(qqchWorkPlan.getVersion())) {
            qqchWorkPlan.setVersion(new BigDecimal("1.0"));
        }
        // 是否生效
        qqchWorkPlan.setValid("0");
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        qqchWorkPlan.setProjectName(projectInfo.getProjectName());
        qqchWorkPlan.setProjectId(projectInfo.getProjectId());
        qqchWorkPlanMapper.insertQqchWorkPlan(qqchWorkPlan);
        // 明细
        qqchWorkPlanDetailService.insertOrEditBatchByMainId(detailListLast, qqchWorkPlan.getId());
        return qqchWorkPlan.getId();
    }

    public Long insertQqchWorkPlanSubmit(QqchWorkPlan qqchWorkPlan) {
        List<QqchWorkPlanDetail> detailListLast = null;
        // 获取前端传入的设备明细
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            detailListLast = TreeUtil.treeToList(detailList);
        }
        JyDetailsUtil.jyDetails(detailListLast, ValidationGroups.Save.class);
        qqchWorkPlan.setId(IdWorker.createId());
        EntityUtils.setCreateUpdateInfo(qqchWorkPlan);
        // 设置版本号码
        if (ObjectNullUtil.isEmpty(qqchWorkPlan.getVersion())) {
            qqchWorkPlan.setVersion(new BigDecimal("1.0"));
        }
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        qqchWorkPlan.setProjectName(projectInfo.getProjectName());
        qqchWorkPlan.setProjectId(projectInfo.getProjectId());
        qqchWorkPlanMapper.insertQqchWorkPlan(qqchWorkPlan);
        // 明细
        qqchWorkPlanDetailService.insertOrEditBatchByMainId(detailListLast, qqchWorkPlan.getId());
        return qqchWorkPlan.getId();
    }

    /**提交操作
     * @param qqchWorkPlan
     * @return
     */
    @Transactional
    @Override
    public Long submitQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        //TODO 提交立马生效
        qqchWorkPlan.setTaskStatus("5");
        qqchWorkPlan.setValid("1");

        if (ObjectNullUtil.isEmpty(qqchWorkPlan.getId())) {
            qqchWorkPlan.setId(this.insertQqchWorkPlanSubmit(qqchWorkPlan));
        } else {
            this.updateQqchWorkPlanSubmit(qqchWorkPlan);
        }
        return qqchWorkPlan.getId();
    }

    @Transactional
    public int insertQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList) {
        for (QqchWorkPlan qqchWorkPlan : qqchWorkPlanList) {
            qqchWorkPlan.setId(IdWorker.createId());
            qqchWorkPlan.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanMapper.insertQqchWorkPlanList(qqchWorkPlanList);
    }

    @Transactional
    public int updateQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        EntityUtils.setUpdateInfo(qqchWorkPlan);
        // 获取前端传入的明细
        List<QqchWorkPlanDetail> detailListLast = null;
        // 获取前端传入的设备明细
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            detailListLast = TreeUtil.treeToList(detailList);
        }
//        JyDetailsUtil.jyDetails(detailListLast, ValidationGroups.Update.class);
        // 修改
        qqchWorkPlanMapper.updateQqchWorkPlan(qqchWorkPlan);
        qqchWorkPlanDetailService.insertOrEditBatchByMainId(detailListLast,qqchWorkPlan.getId());
        return 1;
    }

    @Transactional
    public int updateQqchWorkPlanSubmit(QqchWorkPlan qqchWorkPlan) {
        EntityUtils.setUpdateInfo(qqchWorkPlan);
        // 获取前端传入的明细
        List<QqchWorkPlanDetail> detailListLast = null;
        // 获取前端传入的设备明细
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            detailListLast = TreeUtil.treeToList(detailList);
        }
        JyDetailsUtil.jyDetails(detailListLast, ValidationGroups.Save.class);
        // 修改
        qqchWorkPlanMapper.updateQqchWorkPlan(qqchWorkPlan);
        qqchWorkPlanDetailService.insertOrEditBatchByMainId(detailListLast,qqchWorkPlan.getId());
        return 1;
    }


    /**调整
     * @param qqchWorkPlan
     * @return
     */
    @Override
    @Transactional
    public Long adjustQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        Long id = IdWorker.createId();
        // 设置id
        qqchWorkPlan.setId(id);
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(qqchWorkPlan);

        //置为无效
        qqchWorkPlan.setValid("0");

        // 获取前端传入的明细
        List<QqchWorkPlanDetail> detailListLast = null;
        // 获取前端传入的设备明细
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        if (!ObjectNullUtil.isEmpty(detailList)) {
            detailListLast = TreeUtil.treeToList(detailList);
        }
        JyDetailsUtil.jyDetails(detailListLast, ValidationGroups.Update.class);
        // 修改
        qqchWorkPlanMapper.updateQqchWorkPlan(qqchWorkPlan);
        qqchWorkPlanDetailService.insertOrEditBatchByMainId(detailListLast,qqchWorkPlan.getId());
        return id;
    }

    @Transactional
    public int updateQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList) {
        for (QqchWorkPlan qqchWorkPlan : qqchWorkPlanList) {
            qqchWorkPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchWorkPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanMapper.updateQqchWorkPlanList(qqchWorkPlanList);
    }

    @Transactional
    public int deleteQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        qqchWorkPlan.setDelUser(SecurityUtils.getSysUser().getUserId()+"");
        return qqchWorkPlanMapper.deleteQqchWorkPlan(qqchWorkPlan);
    }

    @Transactional
    public int deleteQqchWorkPlanByPks(List<Long> qqchWorkPlanPkList) {
        return qqchWorkPlanMapper.deleteQqchWorkPlanByPks(qqchWorkPlanPkList);
    }

    @Override
    public List<QqchWorkPlan> planListByTenantKey(QqchWorkPlan plan) {
        if(StringUtils.isBlank(plan.getPtVar5()))
            return new ArrayList<>(2);
        List<QqchWorkPlan> list = null;
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(plan.getPtVar5());
        try {
            plan.setPtVar5(null);
            list = qqchWorkPlanMapper.getQqchWorkPlanList(plan);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return list;
    }
}
