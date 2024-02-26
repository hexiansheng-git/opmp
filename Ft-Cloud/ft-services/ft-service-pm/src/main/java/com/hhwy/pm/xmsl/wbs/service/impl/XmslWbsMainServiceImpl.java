package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMainMapper;
import com.hhwy.pm.xmsl.wbs.push.WbsPushP6;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsListRelationService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.*;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
@Service
public class XmslWbsMainServiceImpl implements IXmslWbsMainService {
    private static final Logger log = LoggerFactory.getLogger(XmslWbsMainServiceImpl.class);
    @Autowired
    private XmslWbsMainMapper xmslWbsMainMapper;
    @Resource
    private IXmslWbsService wbsService;
    @Resource
    private IXmslWbsListRelationService wbsListRelationService;
    @Resource
    private WbsPushP6 wbsPushP6;


    public XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain) {
        return xmslWbsMainMapper.getXmslWbsMain(xmslWbsMain);
    }

    @Override
    public XmslWbsMain getById(Long mainId) {
        if (mainId == null)
            return null;
        XmslWbsMain query = new XmslWbsMain();
        query.setId(mainId);
        XmslWbsMain main = xmslWbsMainMapper.getXmslWbsMain(query);
        return main;
    }

    @Override
    public XmslWbsMain getEffect() {
        XmslWbsMain query = new XmslWbsMain();
        query.setValid(Constant.YES_INT);
        return xmslWbsMainMapper.getLast(query);
    }

    @Override
    public Long getXmslWbsMainCount(XmslWbsMain xmslWbsMain) {
        return xmslWbsMainMapper.getXmslWbsMainCount(xmslWbsMain);
    }

    public List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain) {
        return xmslWbsMainMapper.getXmslWbsMainList(xmslWbsMain);
    }

    @Override
    public XmslWbsMain getLast() {
        XmslWbsMain effect = this.getEffect();
        if(effect != null)
            return effect;
        XmslWbsMain last = this.xmslWbsMainMapper.getLast(new XmslWbsMain());
        return last;
    }

    @Override
    public XmslWbsMain getAdjustInfo() {
        XmslWbsMain query = new XmslWbsMain();
        query.setValid(Constant.NO_INT);
        XmslWbsMain main = xmslWbsMainMapper.getLast(query);
        return main;
    }

    @Override
    @Transactional
    public Long initAdjust() {
        //仅在有生效数据的情况下，进行调整
        XmslWbsMain query = new XmslWbsMain();
        query.setValid(Constant.YES_INT);
        Long count = xmslWbsMainMapper.getXmslWbsMainCount(query);
        if(count < 1)
            return null;
        Long mainId = null;
        try{
            if(RedissonLockUtil.lock(MySecurityUtils.getTenantKey()+"wbsAdjust")){
                Integer maxVersion = this.xmslWbsMainMapper.getMaxVersion()+1;
                //1、插入历史汇总信息
                XmslWbsMain wbsMain = new XmslWbsMain();
                wbsMain.setId(IdWorker.createId());
                wbsMain.setVersion(maxVersion);
                new AddBaseInfoUtil<>(wbsMain);
                wbsMain.setDelFlag(Constant.NO_INT+"");
                wbsMain.setValid(Constant.NO_INT);
                wbsMain.setPtVar3((maxVersion-1)+"");
                this.xmslWbsMainMapper.insertXmslWbsMain(wbsMain);
                mainId = wbsMain.getId();
                //2、先同步前三级到历史，其他层级交给线程处理
                xmslWbsMainMapper.insertWbsToHistory(ObjectUtils.toMap("mainId",wbsMain.getId(),"levels",new Integer[]{1,2,3}));
                String tenantKeys = MySecurityUtils.getTenantKey();
                ThreadPoolUtil.execute(()->{
                    //切换租户
                    String oldDataSource = DynamicDataSourceContextHolder.peek();
                    DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKeys));
                    try {
                        for (int i = 4; i < 10; i++) {
                            xmslWbsMainMapper.insertWbsToHistory(ObjectUtils.toMap("mainId",wbsMain.getId(),"levels",new Integer[]{i}));
                        }
                    }catch (Exception e){
                        log.info("异步处理wbs层级数据出错:{}",e.getMessage());
                        e.printStackTrace();
                        throw new CustomBusinessException(e.getMessage());
                    }finally {
                        DynamicDataSourceContextHolder.poll();
                        DynamicDataSourceContextHolder.push(oldDataSource);
                    }
                });
            }
        }finally {
            RedissonLockUtil.unlock(MySecurityUtils.getTenantKey()+"wbsAdjust");
        }
        return mainId;
    }

    @Transactional
    public int insertXmslWbsMain(XmslWbsMain xmslWbsMain) {
//        xmslWbsMain.setId(IdWorker.createId());
//        xmslWbsMain.setCreateUser(SecurityUtils.getUserName());
//        xmslWbsMain.setCreateTime(DateUtils.getNowDate());
        return xmslWbsMainMapper.insertXmslWbsMain(xmslWbsMain);
    }

    @Transactional
    public int insertXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList) {
        for (XmslWbsMain xmslWbsMain : xmslWbsMainList) {
            xmslWbsMain.setId(IdWorker.createId());
            xmslWbsMain.setCreateUser(SecurityUtils.getUserName());
            xmslWbsMain.setCreateTime(DateUtils.getNowDate());
        }
        return xmslWbsMainMapper.insertXmslWbsMainList(xmslWbsMainList);
    }

    @Transactional
    public int updateXmslWbsMain(XmslWbsMain xmslWbsMain) {
        xmslWbsMain.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsMain.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMainMapper.updateXmslWbsMain(xmslWbsMain);
    }

    @Transactional
    public int updateXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList) {
        for (XmslWbsMain xmslWbsMain : xmslWbsMainList) {
            xmslWbsMain.setUpdateUser(SecurityUtils.getUserName());
            xmslWbsMain.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslWbsMainMapper.updateXmslWbsMainList(xmslWbsMainList);
    }

    @Transactional
    public void deleteXmslWbsMain(XmslWbsMain xmslWbsMain) {
        XmslWbsMain wbsMain = this.getById(xmslWbsMain.getId());
        Assert.isTrue(Constant.NO_INT.equals(wbsMain.getValid()),"已生效数据无法删除");
        xmslWbsMainMapper.deleteLogic(wbsMain.getId());
        xmslWbsMainMapper.deleteHistoryLogic(wbsMain.getId());
        xmslWbsMainMapper.deleteRelation(wbsMain.getId());
    }

    @Transactional
    public int deleteXmslWbsMainByPks(List<Long> xmslWbsMainPkList) {
        return xmslWbsMainMapper.deleteXmslWbsMainByPks(xmslWbsMainPkList);
    }

    @Override
    @Transactional
    public void finishFlow(Long id) {
        XmslWbsMain main = getById(id);
        Assert.notNull(main,"获取数据失败");
        if(main.getValid() == Constant.YES_INT)
            return ;
        //1、wbs迁移到历史数据、历史数据迁移到wbs
        XmslWbsMain effect = this.getEffect();
        if(effect != null){
            this.xmslWbsMainMapper.insertWbsToHistory(ObjectUtils.toMap("mainId",effect.getId()));
            this.xmslWbsMainMapper.deleteWbs();
        }
        xmslWbsMainMapper.insertHistoryToWbs(id);
        this.xmslWbsMainMapper.deleteWbsHitoryByMainId(id);
//        //2、修改main表状态
        this.xmslWbsMainMapper.updateValid(id);
        //3、异步处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容& 推送p6
        String tenantKey = SecurityUtils.getTenantKey();
        ThreadPoolUtil.getThreadPool().execute(()-> {
            MySecurityUtils.set(tenantKey);
            try {
                Thread.sleep(700L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try{
                asyncHandler(tenantKey,main, effect);
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
        
    }
    //异步处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容
    @Override
    @Transactional
    public void asyncHandler(String tenantKey,XmslWbsMain main,XmslWbsMain effect){
        //3、处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容
        long beginMills = System.currentTimeMillis();
        try{
            Map<String,XmslWbs> lastWbsMap = new HashMap<>(10000);
            //加载上一版本的wbs
            if(effect != null){
                List<XmslWbs> lastList = wbsService.getXmslWbsHistoryList(effect.getId());
                if(CollectionUtils.isEmpty(lastList))
                    lastList = wbsService.getByMainId(effect.getId());
                for (int i = 0; i < lastList.size(); i++) {
                    XmslWbs temp = lastList.get(i);
                    lastWbsMap.put(temp.getCode(), temp);
                }
            }
            List<XmslWbsListRelation> relationList = new ArrayList<>();
            //需要修改版本标识(ptVar2)
            List<XmslWbs> updateFlagList = new ArrayList<>();
            List<XmslWbs> allList = new ArrayList<>();
            Set<String> invalidIdSet = new HashSet<>(); //失效的wbsId (父级失效，需要将其所有子级状态改为失效)
            Function<XmslWbs,XmslWbs> iteratFunc = (r)->{
                allList.add(r);
                if(r.getStatus() == Constant.NO_INT)
                    invalidIdSet.add(r.getId());
                String[] pids = r.getAncestors().split(",");
                for (int i = 0; i < pids.length; i++) {
                    if(invalidIdSet.contains(pids[i])){
                        invalidIdSet.add(r.getId());
                        break;
                    }
                }
                //对比状态,如果需要修改标识，放入updateFlagList
                compareVersionFlag(r,lastWbsMap,updateFlagList);
                if(StringUtils.isBlank(r.getListCode()))
                    return r;
                Long[] listIds = Convert.toLongArray(r.getListIds());
                String[] listCodes = Convert.toStrArray(r.getListCode());
                Set<String> listCodeSet = SetUtils.hashSet(listCodes);
                int i=0;
                for (String code : listCodeSet) {
                    XmslWbsListRelation temp = new XmslWbsListRelation(main.getId(),Long.valueOf(r.getId()),code,ArrayUtils.get(listIds,i));
                    relationList.add(temp);
                    i++;
                }
                return r;
            };

            wbsService.handlerAncestors(iteratFunc);
            wbsListRelationService.insertXmslWbsListRelationList(relationList);
            //4、修改版本变更标志
            wbsService.updatePtVar2List(updateFlagList);
            //5、wbs塞入redis
            wbsService.initWbs2Redis(tenantKey);
            //6、更新子级状态
            updateChildStatus(invalidIdSet);
            //7、推送到p6  
            wbsPushP6.push2P6(main.getId(),tenantKey,allList,invalidIdSet);
        }catch(Exception e){
            e.printStackTrace();
            log.error("wbs加载祖级名称&塞redis失败，mainid:{},消息：{}",main.getId(),e.getMessage());
            throw e;
        }finally {
            log.debug("wbs加载祖级名称&塞redis完成,耗时：{}",System.currentTimeMillis()-beginMills);
        }
    }

    /**
     * 对比wbs和上一个版本，获取修改状态
     * 版本修改状态，1:原数据修改,2:新增数据，3：禁用（仅生效数据）
     * @param wbs
     * @param lastWbsMap
     * @param updateList
     */
    private void compareVersionFlag(XmslWbs wbs,Map<String,XmslWbs> lastWbsMap,List<XmslWbs> updateList){
        if(MapUtils.isEmpty(lastWbsMap))
            return ;
        XmslWbs oldWbs = lastWbsMap.get(wbs.getCode());
        //版本修改状态，1:原数据修改,2:新增数据，3：禁用（仅生效数据）
        String flag = null;
        if(oldWbs == null ){  //新增数据
            flag = "2";
        }else if(oldWbs.getStatus() == Constant.YES_INT && wbs.getStatus() == Constant.NO_INT){
            flag = "3";
        }else if(!StringUtils.equals(oldWbs.toString(), wbs.toString())){
            flag = "1";
        }
        if(StringUtils.isNotBlank(wbs.getPtVar4()) && !StringUtils.equals(oldWbs.getName(), wbs.getName())){ //给推送p6准备的，若改了名称需要推送p6修改接口
            wbs.setPtVar5("1");
        }
        if(flag != null){
            wbs.setPtVar2(flag);
            updateList.add(wbs);
        }
    }

    /**
     * 修改子级状态为禁用
     * @param invalidIdSet
     */
    private void updateChildStatus(Set<String> invalidIdSet){
        if(CollectionUtils.isEmpty(invalidIdSet))
            return;
        List<String> invalidIdList = new ArrayList<>(invalidIdSet);
        PageFuncUtils.exec(invalidIdList.size(),500,(start,end)->{
            List<String> tempList = invalidIdList.subList(start, end);
            List<Long> idList = tempList.stream().map(r->Long.valueOf(r)).collect(Collectors.toList());
            this.xmslWbsMainMapper.updateWbsStatus(idList);
            return true;
        });
    }

    @Override
    @Transactional
    public int updateP6Code(WbsInfoVo wbsInfoVo) {
        org.springframework.util.Assert.isTrue(StringUtils.isNotBlank(wbsInfoVo.getProjectId()),"项目ID不能为空");
        //树形转集合
        List<WbsInfoVoBean> treeList = wbsInfoVo.getWbsList();
        if(CollectionUtils.isEmpty(treeList)){
            log.debug("项目WBS更新p6编号接口，Wbs集合为空");
            return 0;
        }
        List<WbsInfoVoBean> list = ListTreeUtil.formatList(treeList, WbsInfoVoBean::getChildren,WbsInfoVoBean::setChildren);
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(wbsInfoVo.getProjectId()));
        int result = 0;
        try {
            result = xmslWbsMainMapper.updateWbsP6Code(list);
            xmslWbsMainMapper.updateWbsHisP6Code(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return result;
    }



}
