package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.PageObjectUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMainMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
            if(RedissonLockUtil.lock(SecurityUtils.getTenantKey()+"wbsAdjust")){
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
                ThreadPoolUtil.execute(()->{
                    try{
                        for (int i = 4; i < 10; i++) {
                            xmslWbsMainMapper.insertWbsToHistory(ObjectUtils.toMap("mainId",wbsMain.getId(),"levels",new Integer[]{i}));
                        }
                    }catch(Exception e){
                        log.info("异步处理wbs层级数据出错:{}",e.getMessage());
                    }
                });
            }
        }finally {
            RedissonLockUtil.unlock(SecurityUtils.getTenantKey()+"wbsAdjust");
        }
        return mainId;
    }

    @Transactional
    public int insertXmslWbsMain(XmslWbsMain xmslWbsMain) {
        xmslWbsMain.setId(IdWorker.createId());
        xmslWbsMain.setCreateUser(SecurityUtils.getUserName());
        xmslWbsMain.setCreateTime(DateUtils.getNowDate());
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
    public void finishFlow(Long id) {
        XmslWbsMain main = getById(id);
        Assert.notNull(main,"获取数据失败");
        if(main.getValid() == Constant.YES_INT)
            return ;
        //1、wbs迁移到历史数据、历史数据迁移到wbs
        this.xmslWbsMainMapper.insertWbsToHistory(ObjectUtils.toMap("mainId",id));
        this.xmslWbsMainMapper.deleteWbs();
        xmslWbsMainMapper.insertHistoryToWbs(id);
        this.xmslWbsMainMapper.deleteWbsHitoryByMainId(id);
        //2、处理祖级ID、祖级名称(wbs清单关联关系)
        List<XmslWbsListRelation> relationList = new ArrayList<>();
        Function<XmslWbs,XmslWbs> iteratFunc = (r)->{
            if(StringUtils.isBlank(r.getListCode()))
                return r;
            String[] listCodes = r.getListCode().split(",");

//            Long wbsId, String listCode, Long listId
//            XmslWbsListRelation relation = new XmslWbsListRelation(id,);
            return r;
        };
        wbsService.handlerAncestors();
        //3、修改main表状态
        this.xmslWbsMainMapper.updateValid(id);
        //4、


        //5、wbs塞入redis
        wbsService.initWbs2Redis();
    }

    private void syncListRelation(){

    }

}
