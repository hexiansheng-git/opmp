package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.convert.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Service
public class XmslWbsServiceImpl implements IXmslWbsService {

    @Autowired
    private XmslWbsMapper xmslWbsMapper;
    @Resource
    private IXmslWbsMainService wbsMainService;


    public XmslWbs getXmslWbs(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbs(xmslWbs);
    }

    @Override
    public List<XmslWbs> getByMainId(Long mainId) {
        XmslWbs query = new XmslWbs();
        query.setMainId(mainId);
        List<XmslWbs> list = xmslWbsMapper.getXmslWbsList(query);
        return list;
    }

    @Override
    public Map listData(XmslWbs xmslWbs) {
        if(StringUtils.isBlank(xmslWbs.getParentId()) )
            xmslWbs.setParentId("-1");
        //判断查询历史还是查询当前
        XmslWbsMain main = wbsMainService.getById(xmslWbs.getMainId());
        xmslWbs.setParams(xmslWbs.getParams()==null?new HashMap<>(1):xmslWbs.getParams());
        xmslWbs.getParams().put("tableName",main.getValid()==Constant.NO_INT?"xmsl_wbs_history":"xmsl_wbs");
        List<XmslWbs> list = xmslWbsMapper.getXmslWbsList(xmslWbs);
        return ObjectUtils.toMap("list",list,"mainId",main.getId());
    }

    @Override
    public List<XmslWbs> getXmslWbsListByTname(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbsList(xmslWbs);
    }

    @Override
    public List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs) {
        xmslWbs.setParams(ObjectUtils.toMap("tableName","xmsl_wbs"));
        return getXmslWbsListByTname(xmslWbs);
    }

    @Override
    public Long countByWbs(XmslWbs wbs) {
        return this.xmslWbsMapper.countByWbs(wbs);
    }

    @Override
    public Map hasEffectWbs() {
        XmslWbsMain main = wbsMainService.getEffect();
        Long count = wbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        return ObjectUtils.toMap("hasEffect",main!=null?1:0,"hasChange",count>1?1:0);
    }

    @Override
    public void save(XmslWbsDto dto) {
        //保存校验
        saveCheck(dto);
        //1、明细数据
        List<XmslWbs> list = dto.getList();
        if(CollectionUtils.isEmpty(list) && StringUtils.isBlank(dto.getDelIds()))
            throw new CustomBusinessException("要保存的数据为空");
        List<XmslWbs> addList = new ArrayList<>();
        List<XmslWbs> updateList = new ArrayList<>();
        //前端新增数据的ID都为uid,需要替换为后端生成的id
        Map<String,String> idRepalceMap = new ConcurrentHashMap<>(list.size()/2);
        list.parallelStream().forEach(temp->{
            if(temp.getId().length() < 21){
                new AddBaseInfoUtil<>().updateBaseEntity(temp);
                updateList.add(temp);
                return;
            }
            String id = getSnowId(temp.getId(),idRepalceMap);
            temp.setId(id);
            //替换祖级id
            String[] ances = temp.getAncestors().split(",");
            List<String> anceList = new ArrayList<>(ances.length);
            for (int i = 0; i < ances.length; i++) {
                String snowId = getSnowId(ances[i],idRepalceMap);
                anceList.add(snowId);
            }
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            String charStr = StringUtils.isBlank(temp.getAncestors())?"":",";
            temp.setAncestors(temp.getAncestors()+charStr+temp.getId());
            addList.add(temp);
        });
        if(ObjectUtils.isNotEmpty(addList))
            this.xmslWbsMapper.insertXmslWbsList(addList);
        if(ObjectUtils.isNotEmpty(updateList))
            this.xmslWbsMapper.updateXmslWbsList(updateList);
        //删除
        if(StringUtils.isNotBlank(dto.getDelIds())){
            this.xmslWbsMapper.deleteByParentIds(Arrays.asList(Convert.toLongArray(dto.getDelIds())));
        }
        //2、主表数据
        saveMain(dto);
    }

    private void saveMain(XmslWbsDto dto){
        //mainID不为空直接更新
        if(dto.getMainId() != null){
            XmslWbsMain main = wbsMainService.getById(dto.getMainId());
            new AddBaseInfoUtil<>().update(main);
            this.wbsMainService.updateXmslWbsMain(main);
            return;
        }
        XmslWbsMain lastMain = wbsMainService.getLast();
        XmslWbsMain main = null;
        if(lastMain == null){
            main = new XmslWbsMain();
            main.setValid(Constant.NO_INT);
            main.setVersion(1);
            new AddBaseInfoUtil<>(main);
            wbsMainService.insertXmslWbsMain(main);
        }else{
            main = lastMain;
            main.setId(IdWorker.createId());
            main.setVersion(lastMain.getVersion()+1);
            main.setValid(Constant.NO_INT);
            new AddBaseInfoUtil<>(main);
        }
        this.wbsMainService.insertXmslWbsMain(main);
    }

    private void saveCheck(XmslWbsDto dto){
        if(dto.getMainId() == null){
            //为空则判断是否已经有未完成的数据
            XmslWbsMain query = new XmslWbsMain();
            query.setValid(Constant.NO_INT);
            Long count = wbsMainService.getXmslWbsMainCount(query);
            Assert.isTrue(count!= null && count > 0,"已存在未生效的历史，无法再新增新数据");
            return;
        }
        XmslWbsMain wbsMain = this.wbsMainService.getById(dto.getMainId());
        Assert.isNull(wbsMain,"mainId有误，获取主数据失败");
        Assert.isTrue(wbsMain.getValid()==Constant.YES_INT,"已生效的数据无法编辑");
    }

    public String getSnowId(String id,Map<String,String> idRepalceMap){
        if(id.length() < 21)
            return id;
        String temp = idRepalceMap.get(id);
        return temp == null?IdWorker.createId()+"":temp;
    }


    @Transactional
    public int insertXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setId(IdWorker.createId()+"");
        xmslWbs.setCreateUser(SecurityUtils.getUserName());
        xmslWbs.setCreateTime(DateUtils.getNowDate());
        return xmslWbsMapper.insertXmslWbs(xmslWbs);
    }

    @Transactional
    public int insertXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setId(IdWorker.createId()+"");
            xmslWbs.setCreateUser(SecurityUtils.getUserName());
            xmslWbs.setCreateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.insertXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int updateXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.updateXmslWbs(xmslWbs);
    }

    @Transactional
    public int updateXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setUpdateUser(SecurityUtils.getUserName());
            xmslWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.updateXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int deleteXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.deleteXmslWbs(xmslWbs);
    }

    @Transactional
    public int deleteXmslWbsByPks(List<Long> xmslWbsPkList) {
        return xmslWbsMapper.deleteXmslWbsByPks(xmslWbsPkList);
    }
}
