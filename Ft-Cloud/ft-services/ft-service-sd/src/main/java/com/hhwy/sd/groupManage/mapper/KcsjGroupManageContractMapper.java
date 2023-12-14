package com.hhwy.sd.groupManage.mapper;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:09
 * @remark
 */
public interface KcsjGroupManageContractMapper {

    KcsjGroupManageContract getKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    List<KcsjGroupManageContract> getKcsjGroupManageContractList(KcsjGroupManageContract kcsjGroupManageContract);

    int insertKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int insertKcsjGroupManageContractList(@Param("kcsjGroupManageContractList") List<KcsjGroupManageContract> kcsjGroupManageContractList);

    int updateKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int updateKcsjGroupManageContractList(@Param("list") List<KcsjGroupManageContract> kcsjGroupManageContractList);

    int deleteKcsjGroupManageContract(KcsjGroupManageContract kcsjGroupManageContract);

    int deleteKcsjGroupManageContractByPks(@Param("kcsjGroupManageContractPkList") List<Long> kcsjGroupManageContractPkList);
}
