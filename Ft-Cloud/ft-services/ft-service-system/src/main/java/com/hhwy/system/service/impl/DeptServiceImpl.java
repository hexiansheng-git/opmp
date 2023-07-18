package com.hhwy.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.domain.base.system.TreeObject;
import com.hhwy.domain.base.system.TreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.mapper.DeptMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.utils.redis.SysRedisUtils;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public List<TreeUtil> getDeptByTree() {
        List<TreeUtil> list = deptMapper.getDeptByTree();
        List<TreeUtil> deptTree = TreeObject.getDeptTree(list);
        return deptTree;
    }

}
