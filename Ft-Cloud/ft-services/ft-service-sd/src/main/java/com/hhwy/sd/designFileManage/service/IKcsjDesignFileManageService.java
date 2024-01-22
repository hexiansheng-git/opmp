package com.hhwy.sd.designFileManage.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManage;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManageVo;
import com.hhwy.sd.designFileManage.domain.vo.KcsjDesignFileManageQueryVo;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark
 */
public interface IKcsjDesignFileManageService {

    KcsjDesignFileManage getKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    KcsjDesignFileManageVo getKcsjDesignFileManageList(KcsjDesignFileManageQueryVo queryVo);

    int insertKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    AjaxResult insertKcsjDesignFileManageList(KcsjDesignFileManageVo kcsjDesignFileManageVo);

    int updateKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    int updateKcsjDesignFileManageList(List<KcsjDesignFileManage> kcsjDesignFileManageList);

    int deleteKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    int deleteKcsjDesignFileManageByPks(List<Long> kcsjDesignFileManagePkList);
}
