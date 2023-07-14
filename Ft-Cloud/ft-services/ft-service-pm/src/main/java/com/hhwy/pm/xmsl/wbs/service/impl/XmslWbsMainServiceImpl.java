package com.hhwy.pm.xmsl.wbs.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMainMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark 
 */
@Service
public class XmslWbsMainServiceImpl implements IXmslWbsMainService{

    @Autowired
    private XmslWbsMainMapper xmslWbsMainMapper;

                                                                                                                                                    
    public XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain) {
        return xmslWbsMainMapper.getXmslWbsMain(xmslWbsMain);
    }

    public List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain) {
        return xmslWbsMainMapper.getXmslWbsMainList(xmslWbsMain);
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
        return xmslWbsMainMapper.deleteXmslWbsMain(xmslWbsMain);
    }

            @Transactional
        public int deleteXmslWbsMainByPks(List<Long> xmslWbsMainPkList) {
            return xmslWbsMainMapper.deleteXmslWbsMainByPks(xmslWbsMainPkList);
        }
    }
