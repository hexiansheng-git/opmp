package com.hhwy.sd.groupManage.service;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;

import java.util.List;


/**
 * @author han
 * @date 2023-12-13 15:27:09
 * @remark
 */
public interface IKcsjGroupManageContractService {

    KcsjGroupManageContract getKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    List<KcsjGroupManageContract> getKcsjGroupManageContractList(KcsjGroupManageContract kcsjGroupManageContract);

    int insertKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int insertKcsjGroupManageContractList(List<KcsjGroupManageContract> kcsjGroupManageContractList);

    int updateKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int updateKcsjGroupManageContractList(List<KcsjGroupManageContract> kcsjGroupManageContractList);

    int deleteKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int deleteKcsjGroupManageContractByPks(List<Long> kcsjGroupManageContractPkList);
}
