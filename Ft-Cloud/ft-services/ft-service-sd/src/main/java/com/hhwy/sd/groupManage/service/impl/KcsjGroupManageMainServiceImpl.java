package com.hhwy.sd.groupManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageMain;
import com.hhwy.sd.groupManage.domain.vo.KcsjGroupManageMainVo;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageMainMapper;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageMainService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:15
 * @remark
 */
@Service
public class KcsjGroupManageMainServiceImpl implements IKcsjGroupManageMainService {

    @Autowired
    private KcsjGroupManageMainMapper kcsjGroupManageMainMapper;


    public KcsjGroupManageMain getKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        return kcsjGroupManageMainMapper.getKcsjGroupManageMain(kcsjGroupManageMain);
    }

    public List<KcsjGroupManageMain> getKcsjGroupManageMainList(KcsjGroupManageMain kcsjGroupManageMain) {
        return kcsjGroupManageMainMapper.getKcsjGroupManageMainList(kcsjGroupManageMain);
    }

    @Transactional
    public int insertKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setId(IdWorker.createId());
        kcsjGroupManageMain.setCreateUser(SecurityUtils.getUserName());
        kcsjGroupManageMain.setCreateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.insertKcsjGroupManageMain(kcsjGroupManageMain);
    }

    @Transactional
    public int insertKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList) {
        for (KcsjGroupManageMain kcsjGroupManageMain : kcsjGroupManageMainList) {
            kcsjGroupManageMain.setId(IdWorker.createId());
            kcsjGroupManageMain.setCreateUser(SecurityUtils.getUserName());
            kcsjGroupManageMain.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageMainMapper.insertKcsjGroupManageMainList(kcsjGroupManageMainList);
    }

    @Transactional
    public int updateKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.updateKcsjGroupManageMain(kcsjGroupManageMain);
    }

    @Transactional
    public int updateKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList) {
        for (KcsjGroupManageMain kcsjGroupManageMain : kcsjGroupManageMainList) {
            kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
            kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageMainMapper.updateKcsjGroupManageMainList(kcsjGroupManageMainList);
    }

    @Transactional
    public int deleteKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain) {
        kcsjGroupManageMain.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageMain.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageMainMapper.deleteKcsjGroupManageMain(kcsjGroupManageMain);
    }

    @Transactional
    public int deleteKcsjGroupManageMainByPks(List<Long> kcsjGroupManageMainPkList) {
        return kcsjGroupManageMainMapper.deleteKcsjGroupManageMainByPks(kcsjGroupManageMainPkList);
    }

    @Override
    public KcsjGroupManageMainVo getKcsjGroupManageMainVo(KcsjGroupManageMain kcsjGroupManageMain) {
        return null;
    }
}
