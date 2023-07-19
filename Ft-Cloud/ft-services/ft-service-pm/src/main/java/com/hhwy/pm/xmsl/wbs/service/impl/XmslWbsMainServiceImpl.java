package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.PageObjectUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMainMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
@Service
public class XmslWbsMainServiceImpl implements IXmslWbsMainService {

    @Autowired
    private XmslWbsMainMapper xmslWbsMainMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private IXmslWbsService wbsService;
    @Resource
    private RedisUtils redisUtils;


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
    public int deleteXmslWbsMain(XmslWbsMain xmslWbsMain) {
        xmslWbsMain.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsMain.setUpdateTime(DateUtils.getNowDate());
        int result = xmslWbsMainMapper.deleteXmslWbsMain(xmslWbsMain);
        return result;
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
        List<XmslWbs> list = wbsService.getByMainId(id);
        for (int i = 0; i < list.size(); i++) {
            new AddBaseInfoUtil<>().updateBaseEntity(list.get(i));
        }
        this.xmslWbsMainMapper.insertWbsToHistory();
        this.xmslWbsMainMapper.deleteWbs();
        xmslWbsMainMapper.insertHistoryToWbs(id);
        this.xmslWbsMainMapper.deleteWbsHitoryByMainId(id);
        //2、修改main表状态
        this.xmslWbsMainMapper.updateValid(id);
        //3、wbs塞入redis
        initWbs2Redis();
    }

    //塞wbs到缓存 wbs::项目id  wbsId  wbsjson
    public void initWbs2Redis(){
        String tenantKey = tokenService.getTenantKey();
        ThreadPoolUtil.execute(()->{
            String key = WbsRedisUtils.getKey(tenantKey);
            try{
                if(RedissonLockUtil.lock(key)){
                    Long count = wbsService.countByWbs(new XmslWbs());
                    int limitSize = 3;
                    Long pages = count/limitSize+(count%limitSize>0?1:0);
                    Map<String,String> redisMap = new ConcurrentHashMap<>(limitSize);
                    redisUtils.delete(key);
                    for (int i = 0; i < pages.intValue(); i++) {
                        PageHelper.startPage(i+1,limitSize,false);
                        List<XmslWbs> allList = wbsService.getXmslWbsList(new XmslWbs());
                        //遍历塞入map
                        allList.parallelStream().forEach(r->{
                            redisMap.put(r.getId(),JSONObject.toJSONString(r));
                        });
                        redisUtils.hPutAll(key,redisMap);
                        redisMap.clear();
                    }
                }
            }finally {
                RedissonLockUtil.unlock(key);
            }
        });
    }
}
