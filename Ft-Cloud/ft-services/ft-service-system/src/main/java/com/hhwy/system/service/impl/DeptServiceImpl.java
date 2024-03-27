package com.hhwy.system.service.impl;

import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.mapper.SysDeptMapper;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.core.service.ISysUserService;
import com.hhwy.system.utils.TreeObject;
import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.mapper.DeptMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.utils.redis.SysRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Service
public class DeptServiceImpl implements IDeptService {

    private Logger logger= LoggerFactory.getLogger(DeptServiceImpl.class);

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysRedisUtils redisUtils;
    public static final String DATA_SCOPE_ALL = "1";//全部数据权限

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private ISysUserService sysUserService;
    @Override
    public List<SysDept> list(SysDept dept) {
        
        return null;
    }

    @Override
    public List<SysTreeUtil> getDeptByTree() {
//        String tenantKey = SecurityUtils.getTenantKey();
        //组织机构修改为全部人员 ,业务人员选择后进行权限由向上分配
        String tenantKey = "master";
        List<SysTreeUtil> list = deptMapper.getDeptByTree(tenantKey);
        List<SysTreeUtil> deptTree = TreeObject.getDeptTree(list);
        return deptTree;
    }

    @Override
    public List<SysTreeUtil> getRegionInfo() {
        List<SysTreeUtil> regionList = deptMapper.selectRegionInfo();
        List<SysTreeUtil> deptTree = TreeObject.getDeptTree(regionList);
        return deptTree;
    }

    @Override
    public SysDept selectDeptIdByprojectId(String projectId) {
        return deptMapper.selectDeptIdByProjectId(projectId);
    }

    @Override
    public List<SysDept> selectAllDept(Long deptId, String ancestors) {
        return deptMapper.selectAllDept(deptId,ancestors);
    }

    @Override
    public List<SysDept> selectPrjInfo(Long deptId, String ancestors) {
        return deptMapper.selectPrjInfo(deptId,ancestors);
    }

    @Override
    public List<SysDept> getProjectOrgInfo() {
        return deptMapper.getProjectOrgInfo();
    }

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        ArrayList<String> list = new ArrayList<>();
        list.add("master");
        return sysDeptMapper.selectDeptList(dept, list);
    }

    @Override
    public List selectOneLevelDeptList(SysDept dept) {
        ArrayList<String> list = new ArrayList<>();
        list.add("master");
        return sysDeptMapper.selectOneLevelDeptList(dept, list);
    }

    @Override
    public List selectChildrenDeptList(SysDept dept) {
        ArrayList<String> list = new ArrayList<>();
        list.add("master");
        return sysDeptMapper.selectChildrenDeptList(dept, list);
    }

    @Override
    public String getAllDepNames() {
         String userName = SecurityUtils.getSysUser().getUserName();
        //1、根据用户账号查询master中 账号的信息
        SysUser user = sysUserService.selectUserByUserName("master", userName);
        //2、根据用户查询部门信息
        SysDept dept = user.getDept();
        String ancestors = dept.getAncestors();
        String deptName = dept.getDeptName();
        Long deptId = dept.getDeptId();
        //3、根据部门信息查询全部上级部门信息
        List<SysDept> deptList = this.selectAllDept(deptId, ancestors);
        //4、拼接数据
        StringBuffer sb = new StringBuffer();
        for(SysDept item:deptList){
            sb.append(item.getDeptName()).append("-");
        }
        sb.append(deptName);
        return sb.toString();
    }


}
