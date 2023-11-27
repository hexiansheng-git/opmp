package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMainMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsListRelationService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.*;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
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
    private IXmslEngineeringReportService engineeringReportService;


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
        Assert.isTrue(wbsMain.getValid()==Constant.NO_INT,"已生效数据无法删除");
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
        //2、修改main表状态
        this.xmslWbsMainMapper.updateValid(id);
        //异步处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容
        asyncHandler(main,effect);
//        //4、工程量报表生成
//        ThreadPoolUtil.getThreadPool().execute(()->{
//            engineeringReportService.sync();
//        });
    }
    //异步处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容
    @Override
    public void asyncHandler(XmslWbsMain main,XmslWbsMain effect){
        String tenantKey = MySecurityUtils.getTenantKey();
        //3、处理祖级ID、祖级名称(wbs清单关联关系) &  挂接清单数据 & 加载版本变更内容
        ThreadPoolUtil.getThreadPool().execute(()->{
            long beginMills = System.currentTimeMillis();
            //切换租户
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try{
                Map<String,XmslWbs> lastWbsMap = new HashMap<>(10000);
                //加载上一版本的wbs
                if(effect != null){
                    List<XmslWbs> lastList = wbsService.getXmslWbsHistoryList(effect.getId());
                    if(CollectionUtils.isEmpty(lastList))
                        lastList = wbsService.getByMainId(effect.getId());
//                    List<XmslWbs> lastList = wbsService.getByMainId(effect.getId());
                    for (int i = 0; i < lastList.size(); i++) {
                        XmslWbs temp = lastList.get(i);
                        lastWbsMap.put(temp.getCode(), temp);
                    }
                }
                List<XmslWbsListRelation> relationList = new ArrayList<>();
                //需要修改版本标识(ptVar2)
                List<XmslWbs> updateFlagList = new ArrayList<>();
                Function<XmslWbs,XmslWbs> iteratFunc = (r)->{
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
            }catch(Exception e){
                e.printStackTrace();
                log.error("wbs加载祖级名称&塞redis失败，mainid:{},消息：{}",main.getId(),e.getMessage());
                throw e;
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
                log.debug("wbs加载祖级名称&塞redis完成,耗时：{}",System.currentTimeMillis()-beginMills);
            }
        });
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
//        if(wbs.getCode().equals("0") || wbs.getCode().equals("777") || wbs.getCode().equals("0-1") )
//            System.out.println(1);
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
        if(flag != null){
            wbs.setPtVar2(flag);
            updateList.add(wbs);
        }
    }
}
