package com.hhwy.system.country.service.impl;


import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.system.country.mapper.CountryInfoMapper;
import com.hhwy.system.country.service.ICountryInfoService;
import com.hhwy.utils.idworker.IdWorker;

import com.hhwy.utils.selfEmpty.SelfEmpty;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 国别Service业务层处理
 * 
 * @author jzq
 * @date 2022-11-01
 */
@Service
public class CountryInfoServiceImpl implements ICountryInfoService {
    @Autowired
    private CountryInfoMapper countryInfoMapper;
//    @Autowired
//    private IProjectInfoService projectInfoService;

    /**
     * 查询国别
     * 
     * @param id 国别ID
     * @return 国别
     */
    @Override
    public CountryInfo selectCountryInfoById(Long id) {
        return countryInfoMapper.selectCountryInfoById(id);
    }

    /**
     * 查询国别列表
     * 
     * @param countryInfo 国别
     * @return 国别
     */
    @Override
//    @SelfEmpty(clazz =CountryInfo.class )
    public List<CountryInfo> selectCountryInfoList(CountryInfo countryInfo) {
        return countryInfoMapper.selectCountryInfoList(countryInfo);
    }

    /**
     * 新增国别
     * 
     * @param countryInfo 国别
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz =CountryInfo.class )
    public int insertCountryInfo(CountryInfo countryInfo) {
        boolean bool = validRepeat(countryInfo);
        if(bool==false){
            return -1;
        }
        countryInfo.setId(IdWorker.createId());
        countryInfo.setCreateTime(DateUtils.getNowDate());
        countryInfo.setCreateUser(SecurityUtils.getUserId().toString());
        countryInfo.setPtVar1(SecurityUtils.getUserName());
        return countryInfoMapper.insertCountryInfo(countryInfo);
    }

    /**
     * 校验是否重复
     *
     * @param countryInfo
     * @return
     */
    public boolean validRepeat(CountryInfo countryInfo){
        List<CountryInfo> list = countryInfoMapper.selectCountryInfoList(countryInfo);
        if(null!=list && list.size()!=0){
            return false;
        }
        return true;
    }

    /**
     * 修改国别
     * 
     * @param countryInfo 国别
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz =CountryInfo.class )
    public int updateCountryInfo(CountryInfo countryInfo) {
        boolean bool = validRepeat(countryInfo);
        if(bool==false){
            return -1;
        }
        countryInfo.setUpdateTime(DateUtils.getNowDate());
        countryInfo.setUpdateUser(SecurityUtils.getUserId().toString());
        return countryInfoMapper.updateCountryInfo(countryInfo);
    }

    /**
     * 删除国别对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCountryInfoByIds(String ids) {
        return countryInfoMapper.deleteCountryInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除国别信息
     * 
     * @param id 国别ID
     * @return 结果
     */
    @Override
    public int deleteCountryInfoById(Long id) {
        return countryInfoMapper.deleteCountryInfoById(id);
    }

    @Override
    public List<CountryInfo> selectCountryInfoByNames(String name) {
        return countryInfoMapper.selectCountryInfoByNames(name);
    }

    /* *//**
     * 国家下的项目（级联）
     *
     * @param countryInfo
     * @return
     *//*
    @Override
    public List<TreeUtil> countryProjectCascade(CountryInfo countryInfo) {
        List<CountryInfo> countryInfoList = countryInfoMapper.selectCountryInfoList(new CountryInfo());
        List<ProjectInfo> list = projectInfoService.selectProjectInfoList(new ProjectInfo());
        List<ProjectInfo> collect = list.stream().filter(e -> StringUtils.isNotBlank(e.getCountryCode())).collect(Collectors.toList());
        Map<String, List<ProjectInfo>> listMap = collect.stream().collect(Collectors.groupingBy(t -> t.getCountryCode()));
        List<TreeUtil> rstList=new ArrayList<>();
        for (int i = 0; i < countryInfoList.size(); i++) {
            TreeUtil treeUtil=new TreeUtil();
            treeUtil.setName(countryInfoList.get(i).getCountryName());
            treeUtil.setId(countryInfoList.get(i).getId()+"");
            treeUtil.setPId(countryInfoList.get(i).getCountryCode());
            List<ProjectInfo> infoList = listMap.get(countryInfoList.get(i).getCountryCode());
            if(CollectionUtils.isNotEmpty(infoList)){
                List<TreeUtil> utilList=new ArrayList<>();
                for (int j = 0; j < infoList.size(); j++) {
                    TreeUtil t=new TreeUtil();
                    t.setId(infoList.get(j).getId()+"");
                    t.setPId(infoList.get(j).getProjectCode());
                    t.setName(infoList.get(j).getProjectName());
                    utilList.add(t);
                }
                treeUtil.setChildren(utilList);
            }
            rstList.add(treeUtil);
        }

        return rstList;
    }*/
}
