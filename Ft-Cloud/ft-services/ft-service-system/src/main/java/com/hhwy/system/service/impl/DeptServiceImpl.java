package com.hhwy.system.service.impl;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
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
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.myUtilPrepare.SetMaterialNameUtils;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import jodd.util.ArraysUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.redisson.api.listener.ListSetListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Override
    public List<SysDept> lazySearch(SysDept dept) {
        Long beginMills = System.currentTimeMillis();
        try{
            List<SysDept> depts;
            if(StringUtils.isBlank(dept.getDeptName())){
                if (dept.getDeptId() == null) {
                    depts = this.selectOneLevelDeptList(dept);
                } else {
                    depts = this.selectChildrenDeptList(dept);
                }
                return depts;
            }
            String key = "dept_lazySearch::"+dept.getDeptName();
            Set<Long> matchIdSet = null;
            if(redisUtils.hasKey(key)){
                Set<String> matchIdStrSet = redisUtils.sMembers(key);
                matchIdSet = matchIdStrSet.stream().map(r->Long.valueOf(r)).collect(Collectors.toSet());
            }else{
                List<SysDept> deptList = this.deptMapper.selectDeptListByDeptName(dept,"master");
                matchIdSet = new HashSet<>();
                for (int i = 0; i < deptList.size(); i++) {
                    SysDept temp = deptList.get(i);
                    matchIdSet.addAll(parseAllId(temp));
                }
                try{
                    if(RedissonLockUtil.lock(key)){
                        matchIdSet = CollectionUtils.isEmpty(matchIdSet)?new HashSet(Arrays.asList(0L)):matchIdSet;
                        String[] perfectMatch = deptList.stream().map(r->r.getDeptId()+"").toArray(String[]::new);
                        redisUtils.delete(key);
                        redisUtils.sAdd(key, matchIdSet.stream().map(r->r+"").toArray(String[]::new));
                        redisUtils.sAdd("perfect_"+key, ArrayUtils.isEmpty(perfectMatch)?new String[]{"0"}:perfectMatch);
                        redisUtils.expire(key, 1, TimeUnit.HOURS);
                        redisUtils.expire("perfect_"+key, 1, TimeUnit.HOURS);
                    }
                }finally {
                    RedissonLockUtil.unlock(key);
                }
            }
            if(CollectionUtils.isEmpty(matchIdSet))
                return new ArrayList<>(2);
            SysDept query = new SysDept();
            query.setParentId(dept.getDeptId());
            query.setParams(ObjectUtils.toMap("deptIds", matchIdSet));
            if(dept.getDeptId() != null){
                final Set<Long> finalMatchIdSet = redisUtils.sMembers("perfect_"+key).stream().map(r->Long.valueOf(r)).collect(Collectors.toSet());
                SysDept nowDept = this.getByDeptId(dept.getDeptId());
                Set<Long> nowDeptAnces =  parseAllId(nowDept);
                boolean anceMatched = nowDeptAnces.stream().filter(r->finalMatchIdSet.contains(r)).findAny().isPresent();
                if(anceMatched){
                    query.setParams(null);
                }
            }
            List<SysDept> finalList = this.deptMapper.selectDeptListAll(query,"master");
            return finalList;
        }finally {
            logger.debug("搜索耗时:{},关键字:{}",System.currentTimeMillis()-beginMills,dept.getDeptName());
        }
    }
    
    private SysDept getByDeptId(Long deptId){
        SysDept query = new SysDept();
        query.setDeptId(deptId);
        List<SysDept> list = deptMapper.selectDeptListByDeptName(query,"master");
        return CollectionUtils.isEmpty(list)?null:list.get(0);
    }
    
    private Set<Long> parseAllId(SysDept dept){
        Set<Long> set = new HashSet<>(10);
        set.add(dept.getDeptId());
        if(StringUtils.isBlank(dept.getAncestors()))
            return set;
        String[] ances = dept.getAncestors().split(",");
        for (int i = 0; i < ances.length; i++) {
            if("".equals(ances[i]) || "null".equals(ances[i]))
                continue;
            set.add(Long.valueOf(ances[i]));
        }
        return set;
    }
}
