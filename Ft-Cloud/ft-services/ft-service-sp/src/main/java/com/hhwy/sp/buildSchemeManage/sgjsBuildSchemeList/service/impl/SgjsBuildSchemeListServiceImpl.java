package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.mapper.SgjsBuildSchemeListMapper;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark
 */
@Service
public class SgjsBuildSchemeListServiceImpl implements ISgjsBuildSchemeListService {

    @Autowired
    private SgjsBuildSchemeListMapper sgjsBuildSchemeListMapper;
    @Autowired
    private ISgjsBuildSchemeService sgjsBuildSchemeService;

    //导入
    @Override
    public AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList) {
        List<SgjsBuildSchemeList> result = new ArrayList<>();
        LinkedHashMap<String, String> changeTypeDict = DictUtil.getDictData("change_type");
        LinkedHashMap<String, String> schemeLevelDict = DictUtil.getDictData("scheme_level");
//        LinkedHashMap<String, String> schemeTypeAllDict = DictUtil.getDictData("scheme_type_all");
        LinkedHashMap<String, String> dangerLevelDict = DictUtil.getDictData("danger_level");
        for (Map<Integer, String> map : dataList) {
            SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
            String schemeName = map.get(0);
            String changeType = map.get(1);
            String wbsId = map.get(2);
            String wbsName = map.get(3);
//            String schemeType = map.get(4);
            String schemeLevel = map.get(4);
            String riskLevel = map.get(5);
            String buildDifficult = map.get(6);
            String planImplementTime = map.get(7);
            String planImplementTimeChange = map.get(8);
//            String planComplationTime = map.get(9);
            if (StrUtil.isBlank(schemeName) || StrUtil.isBlank(schemeLevel) ||StrUtil.isBlank(riskLevel) || StrUtil.isBlank(buildDifficult)) {
                return AjaxResult.error("必填项为空，请检查:方案名称、方案分级、是否危大工程、施工重难点");
            }
            sgjsBuildSchemeList.setSchemeName(schemeName);
            sgjsBuildSchemeList.setRelationWbsId(wbsId);
            sgjsBuildSchemeList.setRelationWbsName(wbsName);
            sgjsBuildSchemeList.setBuildDifficult(buildDifficult);
            sgjsBuildSchemeList.setChangeType(changeTypeDict.get(changeType));
//            sgjsBuildSchemeList.setSchemeType(schemeTypeAllDict.get(schemeType));
            sgjsBuildSchemeList.setSchemeLevel(schemeLevelDict.get(schemeLevel));
            sgjsBuildSchemeList.setDangerLevel(dangerLevelDict.get(riskLevel));
            if (StrUtil.isNotBlank(planImplementTime)) sgjsBuildSchemeList.setPlanImplementTime(DateUtil.parseDate(planImplementTime));
            if (StrUtil.isNotBlank(planImplementTimeChange))  sgjsBuildSchemeList.setPlanImplementTimeChange(DateUtil.parseDate(planImplementTimeChange));
//            if (StrUtil.isNotBlank(planComplationTime))  sgjsBuildSchemeList.setPlanComplationTime(DateUtil.parseDate(planComplationTime));
            result.add(sgjsBuildSchemeList);
        }
        return AjaxResult.success(result);
    }

    //危大工程清单查询
    @Override
    public List<SgjsBuildSchemeList> getRiskList(SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setValid("1");
        SgjsBuildScheme sgjsBuildScheme1 = sgjsBuildSchemeService.getSgjsBuildScheme(sgjsBuildScheme);
        if (sgjsBuildScheme1 == null ) return  new ArrayList<>();
        sgjsBuildSchemeListParam.setForeignId(sgjsBuildScheme1.getId());
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListMapper.getSgjsBuildSchemeListList(sgjsBuildSchemeListParam);
        return sgjsBuildSchemeListList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getDangerLevel()) && (p.getDangerLevel().equals("1") || p.getDangerLevel().equals("2")))
                .collect(Collectors.toList());
    }

    //选择原有方案
    @Override
    public List<SgjsBuildSchemeList> getLastValidScheme(SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setValid("1");
        SgjsBuildScheme sgjsBuildScheme1 = sgjsBuildSchemeService.getSgjsBuildScheme(sgjsBuildScheme);
        if (null == sgjsBuildScheme1) return new ArrayList<>();
        if(sgjsBuildSchemeListParam == null){
            sgjsBuildSchemeListParam = new SgjsBuildSchemeList();
        }
        sgjsBuildSchemeListParam.setForeignId(sgjsBuildScheme1.getId());
        return sgjsBuildSchemeListMapper.getSgjsBuildSchemeListList(sgjsBuildSchemeListParam);
    }

    //查看本次变更的方案
    @Override
    public List<SgjsBuildSchemeList> getCurrentChangeScheme(SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        return sgjsBuildSchemeListMapper.getSgjsBuildSchemeListList(sgjsBuildSchemeListParam);
    }

    //保存
    @Transactional
    public void insertSgjsBuildSchemeList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList, SgjsBuildScheme basicInfo) {
        /*删除当前版本数据*/
        SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
        sgjsBuildSchemeList.setForeignId(basicInfo.getId());
        this.deleteSgjsBuildSchemeList(sgjsBuildSchemeList);
        /*保存*/
        //获取上一版本有效版本 清单
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setValid("1");
        List<SgjsBuildScheme> sgjsBuildSchemes = sgjsBuildSchemeService.getSgjsBuildSchemeList(sgjsBuildScheme);
        List<SgjsBuildSchemeList> originList = new ArrayList<>();
        if (CollUtil.isNotEmpty(sgjsBuildSchemes)) {
            SgjsBuildSchemeList param = new SgjsBuildSchemeList();
            param.setForeignId(sgjsBuildSchemes.get(0).getId());
            originList = sgjsBuildSchemeListMapper.getSgjsBuildSchemeListList(param);
        }
        /*入参为空（界面台账中的数据），只需保存:上一版本有效版本 清单*/
        if (CollUtil.isEmpty(sgjsBuildSchemeListList)) {
            if (CollUtil.isEmpty(originList)) return;
            //上一版本有效版本 清单不为空，走保存
            originList.forEach(p -> {
                p.setId(IdWorker.createId());
                p.setForeignId(basicInfo.getId());
                p.setPtVar3("0");
            });
            sgjsBuildSchemeListMapper.insertSgjsBuildSchemeListList(originList);
            return;
        }
        /*入参不为空（界面台账中的数据） 同 上一版本有效版本 清单合并保存*/
        //序号
        Integer serilizeNum = 0;
        sgjsBuildSchemeListList.forEach(p -> p.setPtVar3("1"));
        if (CollUtil.isNotEmpty(originList)) {
            //从上一版本有效版本清单中，过滤掉界面传过来的清单
            Set<String> collect = sgjsBuildSchemeListList.stream().map(SgjsBuildSchemeList::getSchemeNum).collect(Collectors.toSet());
            List<SgjsBuildSchemeList> collect1 = originList.stream().filter(p -> !collect.contains(p.getSchemeNum())).collect(Collectors.toList());
            collect1.forEach(p -> p.setPtVar3("0"));
            sgjsBuildSchemeListList.addAll(collect1);
        }
        //得到最大序列号
        sgjsBuildSchemeListList.stream().filter(p -> StrUtil.isNotBlank(p.getSchemeNum())).forEach(p -> {
            String schemeNum = p.getSchemeNum();
            Integer num = Integer.valueOf(schemeNum.substring(schemeNum.length() - 3));
            p.setSerialNum(num);
        });
        List<SgjsBuildSchemeList> collect = sgjsBuildSchemeListList.stream().filter(p -> null != p.getSerialNum()).collect(Collectors.toList());
        Optional<SgjsBuildSchemeList> max = collect.stream().max(Comparator.comparing(SgjsBuildSchemeList::getSerialNum));
        if (max.isPresent()) {
            serilizeNum = max.get().getSerialNum();
        }
        Integer serilize = serilizeNum;
        String userName = SecurityUtils.getUserName();
        for (SgjsBuildSchemeList param : sgjsBuildSchemeListList) {
            if (StrUtil.isBlank(param.getSchemeNum())) {
                param.setSchemeNum(getSerialNumber(serilize));
                serilize++;
            }
            param.setForeignId(basicInfo.getId());
            param.setProjectId(basicInfo.getProjectId());
            param.setProjectName(basicInfo.getProjectName());
            param.setRegionId(basicInfo.getRegionId());
            param.setRegionName(basicInfo.getRegionName());
            param.setPtVar5(basicInfo.getProjectCode());
            param.setId(IdWorker.createId());
            param.setCreateUser(userName);
            param.setCreateTime(DateUtils.getNowDate());
        }
        sgjsBuildSchemeListMapper.insertSgjsBuildSchemeListList(sgjsBuildSchemeListList);
    }

    private String getSerialNumber(Integer serialNum) {
        String tenantKey = SecurityUtils.getTenantKey();
        serialNum += 1;
        if (serialNum < 10) {
            return tenantKey + "00" + serialNum;
        } else if (serialNum < 100) {
            return tenantKey + "0" + serialNum;
        } else {
            return tenantKey + serialNum;
        }
    }

    public SgjsBuildSchemeList getSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList) {
        return sgjsBuildSchemeListMapper.getSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    //台账查询
    public List<SgjsBuildSchemeList> getSgjsBuildSchemeListList(SgjsBuildSchemeList sgjsBuildSchemeList) {
        if (sgjsBuildSchemeList.getForeignId() == null) {
            new ArrayList<>();
        }
        String dangerLevel = sgjsBuildSchemeList.getDangerLevel();
        if (StrUtil.isNotBlank(dangerLevel)) {
            String[] dangerLevelArr = dangerLevel.split(",");
            sgjsBuildSchemeList.setDangerLevelArr(dangerLevelArr);
            sgjsBuildSchemeList.setDangerLevel(null);
        }
        return sgjsBuildSchemeListMapper.getSgjsBuildSchemeListList(sgjsBuildSchemeList);
    }

    @Transactional
    public int insertSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList) {
        sgjsBuildSchemeList.setId(IdWorker.createId());
        sgjsBuildSchemeList.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeList.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeListMapper.insertSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int insertSgjsBuildSchemeListList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList) {
        if (CollUtil.isEmpty(sgjsBuildSchemeListList)) {
            SgjsBuildSchemeList sgjsBuildSchemeList = new SgjsBuildSchemeList();
            this.deleteSgjsBuildSchemeList(sgjsBuildSchemeList);
            return 0;
        }
        for (SgjsBuildSchemeList sgjsBuildSchemeList : sgjsBuildSchemeListList) {
            sgjsBuildSchemeList.setId(IdWorker.createId());
            sgjsBuildSchemeList.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeList.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeListMapper.insertSgjsBuildSchemeListList(sgjsBuildSchemeListList);
    }

    @Transactional
    public int updateSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList) {
        sgjsBuildSchemeList.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeList.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeListMapper.updateSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int updateSgjsBuildSchemeListList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList) {
        for (SgjsBuildSchemeList sgjsBuildSchemeList : sgjsBuildSchemeListList) {
            sgjsBuildSchemeList.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeList.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeListMapper.updateSgjsBuildSchemeListList(sgjsBuildSchemeListList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList) {
        return sgjsBuildSchemeListMapper.deleteSgjsBuildSchemeList(sgjsBuildSchemeList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeListByPks(List<Long> sgjsBuildSchemeListPkList) {
        return sgjsBuildSchemeListMapper.deleteSgjsBuildSchemeListByPks(sgjsBuildSchemeListPkList);
    }

    @Override
    public List<SgjsBuildSchemeList> getListByforeignList(Collection<Long> foreignId) {
        return sgjsBuildSchemeListMapper.getListByforeignList(foreignId);
    }

    @Override
    public List<SgjsBuildSchemeEvolve> getListByEvolveQueryVo(BuildSchemeEvolveQueryVo queryVo) {
        return sgjsBuildSchemeListMapper.getListByEvolveQueryVo(queryVo);
    }

    @Override
    public List<SgjsBuildSchemeEvolve> getEvolveListByIds(List<Long> ids) {
        return sgjsBuildSchemeListMapper.getEvolveListByIds(ids);
    }
}
