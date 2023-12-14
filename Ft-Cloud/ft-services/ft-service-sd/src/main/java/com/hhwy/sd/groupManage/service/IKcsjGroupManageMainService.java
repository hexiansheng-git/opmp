package com.hhwy.sd.groupManage.service;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageMain;
import com.hhwy.sd.groupManage.domain.vo.KcsjGroupManageMainVo;

import java.util.List;


/**
 * @author han
 * @date 2023-12-13 15:27:15
 * @remark
 */
public interface IKcsjGroupManageMainService {

    KcsjGroupManageMain getKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    List<KcsjGroupManageMain> getKcsjGroupManageMainList(KcsjGroupManageMain kcsjGroupManageMain);

    int insertKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int insertKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList);

    int updateKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int updateKcsjGroupManageMainList(List<KcsjGroupManageMain> kcsjGroupManageMainList);

    int deleteKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int deleteKcsjGroupManageMainByPks(List<Long> kcsjGroupManageMainPkList);

    KcsjGroupManageMainVo getKcsjGroupManageMainVo(KcsjGroupManageMain kcsjGroupManageMain);
}
