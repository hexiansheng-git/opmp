package com.hhwy.pm.qqch.sgch.managementPersonConfig.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.ehr.service.IEhrService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.IQqchOrganizationListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.mapper.QqchManagementPersonConfigMapper;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.service.IQqchManagementPersonConfigService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import jdk.nashorn.internal.runtime.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark
 */
@Service
public class QqchManagementPersonConfigServiceImpl implements IQqchManagementPersonConfigService {

    @Autowired
    private QqchManagementPersonConfigMapper qqchManagementPersonConfigMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchOrganizationListService qqchOrganizationListService;
    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    IEhrService hrService;

    public QqchManagementPersonConfig getQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        return qqchManagementPersonConfigMapper.getQqchManagementPersonConfig(qqchManagementPersonConfig);
    }


    @Transactional
    public int insertQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setId(IdWorker.createId());
        qqchManagementPersonConfig.setCreateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setCreateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.insertQqchManagementPersonConfig(qqchManagementPersonConfig);
    }


    @Transactional
    public int updateQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.updateQqchManagementPersonConfig(qqchManagementPersonConfig);
    }

    @Transactional
    public int updateQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList) {
        for (QqchManagementPersonConfig qqchManagementPersonConfig : qqchManagementPersonConfigList) {
            qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
            qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchManagementPersonConfigMapper.updateQqchManagementPersonConfigList(qqchManagementPersonConfigList);
    }

    @Transactional
    public int deleteQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig) {
        qqchManagementPersonConfig.setUpdateUser(SecurityUtils.getUserName());
        qqchManagementPersonConfig.setUpdateTime(DateUtils.getNowDate());
        return qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfig(qqchManagementPersonConfig);
    }

    @Transactional
    public int deleteQqchManagementPersonConfigByPks(List<Long> qqchManagementPersonConfigPkList) {
        return qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfigByPks(qqchManagementPersonConfigPkList);
    }


    /***
     * 功能描述: 获取项目组织数据
     * 作者: fushudong
     * 时间: 2023/8/16
     */
    @Override
    public QqchManagementPersonConfigVo synchData(QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        //保存表格现有数据
        BigDecimal version = qqchManagementPersonConfigVo.getVersion();
        version = VersionUtil.getVersion("qqch_management_person_config", version);
        this.insertQqchManagementPersonConfigList(qqchManagementPersonConfigVo.getQqchManagementPersonConfigList(), version);

        //需求：1.1的项目组织的子集为本功能的父集
        //根据版本获取1.1项目组织数据,条件： pid!=''
        QqchOrganizationList qqchOrganizationList = new QqchOrganizationList();
        qqchOrganizationList.setVersion(version);
        List<QqchOrganizationList> organizationLists = qqchOrganizationListService.getQqchOrganizationListList2(qqchOrganizationList);

        //获取当前已有的组织数据
        List<QqchManagementPersonConfig> pageList = qqchManagementPersonConfigVo.getQqchManagementPersonConfigList();
        //collect用于判断拉取来的组织是否存在
        List<String> collect = pageList.stream().map(QqchManagementPersonConfig::getPost).collect(Collectors.toList());

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        //组装入库数据
        List<QqchManagementPersonConfig> list = new ArrayList<QqchManagementPersonConfig>();
        for (QqchOrganizationList organizationList : organizationLists) {
            String organization = organizationList.getOrganization();
            //判断当前岗位已存在，则不新增
            if (collect.contains(organization)) {
                continue;
            }
            QqchManagementPersonConfig entity = new QqchManagementPersonConfig();
//            entity.setId(organizationList.getId());
            entity.setId(IdWorker.createId());
            entity.setDuty(organizationList.getDutyDept());
            entity.setPost(organizationList.getOrganization());
            entity.setValid(valid);
            entity.setVersion(version);
            entity.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            entity.setCreateUserName(SecurityUtils.getUserName());
            entity.setCreateTime(DateUtils.getNowDate());
            entity.setDelFlag("0");
            list.add(entity);
        }
        if (list.size() > 0) {
            qqchManagementPersonConfigMapper.insertQqchManagementPersonConfigList(list);
        }
        QqchManagementPersonConfig qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setVersion(version);
        return this.getQqchManagementPersonConfigList(qqchManagementPersonConfig);
    }

    @Override
    public Map<String, Integer> personNumCalc(QqchManagementPersonConfig vo) {
        BigDecimal version = vo.getVersion();
        version = VersionUtil.getVersion("qqch_management_person_config", version);
        vo.setVersion(version);
        return qqchManagementPersonConfigMapper.personNumCalc(vo);
    }

    /**
     * 列表接口
     *
     * @param qqchManagementPersonConfig
     * @return
     */
    public QqchManagementPersonConfigVo getQqchManagementPersonConfigList(QqchManagementPersonConfig qqchManagementPersonConfig) {

        QqchManagementPersonConfigVo qqchManagementPersonConfigVo = new QqchManagementPersonConfigVo();
        BigDecimal version = qqchManagementPersonConfig.getVersion();
        version = VersionUtil.getVersion("qqch_management_person_config", version);
        qqchManagementPersonConfig.setVersion(version);
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(qqchManagementPersonConfig);
        List<QqchManagementPersonConfig> treeList = TreeUtil.build(qqchManagementPersonConfigList, null);
        qqchManagementPersonConfigVo.setVersion(version);
        qqchManagementPersonConfigVo.setStageIdentity(qqchReviewService.getStage());
        qqchManagementPersonConfigVo.setQqchManagementPersonConfigList(treeList);
        return qqchManagementPersonConfigVo;
    }


    /**
     * 保存/确认/提交
     *
     * @param qqchManagementPersonConfigVo
     */
    @Override
    public void save(QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        String buttonMark = qqchManagementPersonConfigVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchManagementPersonConfigVo.getVersion();
        List<QqchManagementPersonConfig> qqchManagementPersonConfigList = qqchManagementPersonConfigVo.getQqchManagementPersonConfigList();

        this.insertQqchManagementPersonConfigList(qqchManagementPersonConfigList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchManagementPersonConfigVo.getMenuId();
            String stageIdentity = qqchManagementPersonConfigVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /***
     * 功能描述:
     * @param username  人员账号
     * @return java.lang.String  人员类型
     * 作者: fushudong
     * 时间: 2023/10/25
     */
    public String getPersonType(String username) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Object> certList = hrService.getCertList(username);
        if (CollectionUtil.isEmpty(certList))
            return null;
        String employeeModle_name = (String) certList.get("employeeModle_name");
        if (StrUtil.isBlank(employeeModle_name))
            return null;
        //黄玉涛:
        //需要区分中方和外方
        //轻舟已过万重山:
        //带  属地  的是外方
        if (employeeModle_name.contains("属地")){
            return  "外方";
        }else {
            return "中方";
        }
    }

    /**
     * 获取 “项目领导层” 层级下的人员用户名
     *
     * @return
     */
    @Override
    public Map<String, String> getProjectLeadershipPersonUserNameMap() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        Map<String,String> userNameMap = new HashMap<>();
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                List<QqchManagementPersonConfig> projectLeadershipPersonList = this.getProjectLeadershipPersonList();
                String userNames = projectLeadershipPersonList.stream().map(QqchManagementPersonConfig::getPtVar1).distinct().collect(Collectors.joining(","));
                userNameMap.put(tenantKey,userNames);
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

        return userNameMap;
    }

    private void getPersonType() throws ParserConfigurationException, IOException, SAXException {
        List<QqchManagementPersonConfig> list = qqchManagementPersonConfigMapper.getNonPersonTyep();
        if (CollectionUtil.isEmpty(list))
            return ;
        List<QqchManagementPersonConfig> saveList = new ArrayList<>();
        for (QqchManagementPersonConfig perosonConfig : list) {
            Map<String, Object> certList = hrService.getCertList(perosonConfig.getPtVar1());
            if (CollectionUtil.isEmpty(certList))
                continue;
            String employeeModle_name = (String) certList.get("employeeModle_name");
            if (StrUtil.isBlank(employeeModle_name))
                continue;
            //黄玉涛:
            //需要区分中方和外方
            //轻舟已过万重山:
            //带  属地  的是外方
            QqchManagementPersonConfig bean = new QqchManagementPersonConfig();
            bean.setId(perosonConfig.getId());
            if (employeeModle_name.contains("属地")){
                bean.setPersonType("外方");
            }else {
                bean.setPersonType("中方");
            }
            saveList.add(bean);
        }
        qqchManagementPersonConfigMapper.savePersonType(saveList);
    }


    /**
     * 新增
     *
     * @param qqchManagementPersonConfigList
     * @param version
     */
    @Transactional
    public void insertQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList, BigDecimal version) {

        //删除旧数据
        QqchManagementPersonConfig qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setVersion(version);
        qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfig(qqchManagementPersonConfig);

        if (CollectionUtils.isEmpty(qqchManagementPersonConfigList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchManagementPersonConfig> configs = TreeUtil.treeToList(qqchManagementPersonConfigList);
        for (QqchManagementPersonConfig managementPersonConfig : configs) {
            managementPersonConfig.setValid(valid);
            managementPersonConfig.setVersion(version);
            if(managementPersonConfig.getRelevancyId() == null){
                managementPersonConfig.setRelevancyId(IdWorker.createId());
            }
            managementPersonConfig.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            managementPersonConfig.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            managementPersonConfig.setCreateTime(DateUtils.getNowDate());
        }
        qqchManagementPersonConfigMapper.insertQqchManagementPersonConfigList(configs);
    }

    private static final String PROJECT_LEADERSHIP = "项目领导层";

    /**
     * 获取 “项目领导层” 层级下的人员数据
     * @return
     */
    @Override
    public List<QqchManagementPersonConfig> getProjectLeadershipPersonList() {
        //获取当前最大生效版本
        BigDecimal version = VersionUtil.getVersion("qqch_management_person_config", null);
        //获取岗位为 “项目领导层” 的数据
        QqchManagementPersonConfig qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setPost(PROJECT_LEADERSHIP);
        qqchManagementPersonConfig.setVersion(version);
        QqchManagementPersonConfig projectLeadership = qqchManagementPersonConfigMapper.getQqchManagementPersonConfig(qqchManagementPersonConfig);

        if(projectLeadership == null){
            return new ArrayList<>();
        }

        //获取 “项目领导层” 的下级数据
        qqchManagementPersonConfig = new QqchManagementPersonConfig();
        qqchManagementPersonConfig.setPid(projectLeadership.getId());
        return qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(qqchManagementPersonConfig);
    }

    @Override
    public List<QqchManagementPersonConfig> getPopWindows(QqchManagementPersonConfig qqchManagementPersonConfig) {
        List<QqchManagementPersonConfig> resultList;
        BigDecimal version = VersionUtil.getVersion("qqch_management_person_config", qqchManagementPersonConfig.getVersion());
        QqchManagementPersonConfig query = new QqchManagementPersonConfig();
        query.setVersion(version);
        //全量数据
        List<QqchManagementPersonConfig> allList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(query);

        String post = qqchManagementPersonConfig.getPost();
        String name = qqchManagementPersonConfig.getName();

        if(StringUtils.isNotBlank(post) || StringUtils.isNotBlank(name)){
            query.setPost(post);
            query.setName(name);
            List<QqchManagementPersonConfig> subList = qqchManagementPersonConfigMapper.getQqchManagementPersonConfigList(query);
            resultList = ListTreeUtil.getUpListBySublistToTree(
                    subList,
                    allList,
                    QqchManagementPersonConfig::getId,
                    QqchManagementPersonConfig::getPid,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchManagementPersonConfig::getChildren,
                    QqchManagementPersonConfig::setChildren);
        }else {
            resultList = ListTreeUtil.formatTree(
                    allList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchManagementPersonConfig::getChildren,
                    QqchManagementPersonConfig::setChildren);
        }
        return resultList;
    }

    @Override
    public int removeById(String ids) {
        if (StrUtil.isBlank(ids)) return 0;
        String[] strings = StrUtil.splitToArray(ids, ',');
        List<Long> collect = Arrays.stream(strings).map(Long::valueOf).collect(Collectors.toList());
        return qqchManagementPersonConfigMapper.deleteQqchManagementPersonConfigByPks(collect);
    }
}
