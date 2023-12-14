package com.hhwy.sd.groupManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageContractMapper;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageContractService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:09
 * @remark
 */
@Service
public class KcsjGroupManageContractServiceImpl implements IKcsjGroupManageContractService {

    @Autowired
    private KcsjGroupManageContractMapper kcsjGroupManageContractMapper;


    public KcsjGroupManageContract getKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract) {
        return kcsjGroupManageContractMapper.getKcsjGroupManageContract(kcsjGroupManageContract);
    }

    public List<KcsjGroupManageContract> getKcsjGroupManageContractList(KcsjGroupManageContract kcsjGroupManageContract) {
        return kcsjGroupManageContractMapper.getKcsjGroupManageContractList(kcsjGroupManageContract);
    }

    @Override
    public List<KcsjGroupManageContract> getListByMainId(Long mainId) {
        KcsjGroupManageContract query = new KcsjGroupManageContract();
        query.setMainId(mainId);
        return kcsjGroupManageContractMapper.getKcsjGroupManageContractList(query);
    }

    @Transactional
    public int insertKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract) {
        kcsjGroupManageContract.setId(IdWorker.createId());
        kcsjGroupManageContract.setCreateUser(SecurityUtils.getUserName());
        kcsjGroupManageContract.setCreateTime(DateUtils.getNowDate());
        return kcsjGroupManageContractMapper.insertKcsjGroupManageContract(kcsjGroupManageContract);
    }

    @Transactional
    public int insertKcsjGroupManageContractList(List<KcsjGroupManageContract> kcsjGroupManageContractList) {
        for (KcsjGroupManageContract kcsjGroupManageContract : kcsjGroupManageContractList) {
            kcsjGroupManageContract.setId(IdWorker.createId());
            kcsjGroupManageContract.setCreateUser(SecurityUtils.getUserName());
            kcsjGroupManageContract.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageContractMapper.insertKcsjGroupManageContractList(kcsjGroupManageContractList);
    }

    @Transactional
    public int updateKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract) {
        kcsjGroupManageContract.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageContract.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageContractMapper.updateKcsjGroupManageContract(kcsjGroupManageContract);
    }

    @Transactional
    public int updateKcsjGroupManageContractList(List<KcsjGroupManageContract> kcsjGroupManageContractList) {
        for (KcsjGroupManageContract kcsjGroupManageContract : kcsjGroupManageContractList) {
            kcsjGroupManageContract.setUpdateUser(SecurityUtils.getUserName());
            kcsjGroupManageContract.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageContractMapper.updateKcsjGroupManageContractList(kcsjGroupManageContractList);
    }

    @Transactional
    public int deleteKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract) {
        kcsjGroupManageContract.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageContract.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageContractMapper.deleteKcsjGroupManageContract(kcsjGroupManageContract);
    }

    @Override
    public void deleteByMainId(Long mainId) {
        KcsjGroupManageContract delParam = new KcsjGroupManageContract();
        delParam.setMainId(mainId);
        kcsjGroupManageContractMapper.deleteKcsjGroupManageContract(delParam);
    }

    @Transactional
    public int deleteKcsjGroupManageContractByPks(List<Long> kcsjGroupManageContractPkList) {
        return kcsjGroupManageContractMapper.deleteKcsjGroupManageContractByPks(kcsjGroupManageContractPkList);
    }
}
