package com.hhwy.pm.jdgl.diff.make.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.util.TreeNodeUtil;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.mapper.JdglCorrectionMeasuresMakeDetailMapper;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark 纠偏措施制定详情
 */
@Service
@Slf4j
public class JdglCorrectionMeasuresMakeDetailServiceImpl implements IJdglCorrectionMeasuresMakeDetailService {

    @Autowired
    private JdglCorrectionMeasuresMakeDetailMapper jdglCorrectionMeasuresMakeDetailMapper;
    @Autowired
    private SystemServiceApi systemServiceApi;

    public JdglCorrectionMeasuresMakeDetail getJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        return jdglCorrectionMeasuresMakeDetailMapper
            .getJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    public List<JdglCorrectionMeasuresMakeDetail> getJdglCorrectionMeasuresMakeDetailList(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        return jdglCorrectionMeasuresMakeDetailMapper.getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        jdglCorrectionMeasuresMakeDetail.setId(IdWorker.createId());
        jdglCorrectionMeasuresMakeDetail.setCreateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMakeDetail.setCreateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper
            .insertJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList) {
        for (JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail : jdglCorrectionMeasuresMakeDetailList) {
//            jdglCorrectionMeasuresMakeDetail.setId(IdWorker.createId());
//            jdglCorrectionMeasuresMakeDetail.setCreateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMakeDetail.setCreateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeDetailMapper
            .insertJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailList);
    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
        jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper
            .updateJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList) {
        for (JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail : jdglCorrectionMeasuresMakeDetailList) {
            jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeDetailMapper
            .updateJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailList);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail) {
//        jdglCorrectionMeasuresMakeDetail.setUpdateUser(SecurityUtils.getUserName());
//        jdglCorrectionMeasuresMakeDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeDetailMapper.deleteJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetail);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMakeDetailByPks(List<Long> jdglCorrectionMeasuresMakeDetailPkList) {
        return jdglCorrectionMeasuresMakeDetailMapper
            .deleteJdglCorrectionMeasuresMakeDetailByPks(jdglCorrectionMeasuresMakeDetailPkList);
    }

    @Override
    public List<JdglCorrectionMeasuresMakeDetail> getDetailListByMakeId(JdglCorrectionMeasuresMake make) {
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail = new JdglCorrectionMeasuresMakeDetail();
        jdglCorrectionMeasuresMakeDetail.setMakeId(make.getId());
        List<JdglCorrectionMeasuresMakeDetail> allList = jdglCorrectionMeasuresMakeDetailMapper.getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetail);
        if (CollectionUtil.isEmpty(allList))
            return new ArrayList<>();
        //只能查看、编辑自己负责的数据，除非当前记录流程已结束
        Long userId = SecurityUtils.getUserId();
        String userName = SecurityUtils.getUserName();
        log.info("用户名：{} ---- id：{}", userName, userId);
        log.info("流程状态：{} ----", make.getTaskStatus());
        //数据过滤
        List<JdglCorrectionMeasuresMakeDetail> afterFilterList = new ArrayList<>();
        if (StrUtil.isNotBlank(make.getTaskStatus()) &&  !make.getTaskStatus().equals("5") && !userName.equals("admin")) {
            afterFilterList = allList.stream()
                    .filter(p -> StrUtil.isNotBlank(p.getDirectorId()) && p.getDirectorId().equals(userName))
                    .collect(Collectors.toList());
        }
        List<JdglCorrectionMeasuresMakeDetail> resultList = TreeNodeUtil.getAncestral(allList, afterFilterList);
        //将所有责任人username和nickname返回前端，给流程审批用
        String loginAcccount = allList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getDirectorId()))
                .map(JdglCorrectionMeasuresMakeDetail::getDirectorId)
                .distinct().collect(Collectors.joining(","));
        if (StrUtil.isBlank(loginAcccount))
            return resultList;
        //username
        make.setPtVar1(loginAcccount);
        //nickname
        Map<String, String> parm = new HashMap<>();
        parm.put("userNames", loginAcccount);
        parm.put("tenantKey", SecurityUtils.getTenantKey());
        List<SysUser> userList = systemServiceApi.selectUserInfoByUserNameAndTenant(parm);
        if (CollectionUtil.isEmpty(userList)) {
            log.error("未查到用户信息：username--{}，tenantKey--{}", loginAcccount, SecurityUtils.getTenantKey());
            return resultList;
        }
        StringBuilder sb = new StringBuilder();
        Map<String,String> nickNameMap = userList.stream().collect(Collectors.toMap(SysUser::getUserName, SysUser::getNickName));
        String[] usernameArr = loginAcccount.split(",");
        for (String username : usernameArr) {
            sb.append(",").append(nickNameMap.get(username));
        }
        //nickname
        make.setPtVar2(StrUtil.isBlank(sb.toString())?"":sb.toString().substring(1));
        return resultList;
    }
}
