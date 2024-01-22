package com.hhwy.sd.designFileManage.mapper;

import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManage;
import com.hhwy.sd.designFileManage.domain.vo.KcsjDesignFileManageQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark
 */
public interface KcsjDesignFileManageMapper {

    KcsjDesignFileManage getKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    List<KcsjDesignFileManage> getKcsjDesignFileManageList(KcsjDesignFileManageQueryVo queryVo);

    int insertKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    int insertKcsjDesignFileManageList(@Param("kcsjDesignFileManageList") List<KcsjDesignFileManage> kcsjDesignFileManageList);

    int updateKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    int updateKcsjDesignFileManageList(@Param("list") List<KcsjDesignFileManage> kcsjDesignFileManageList);

    int deleteKcsjDesignFileManage(KcsjDesignFileManage kcsjDesignFileManage);

    int deleteKcsjDesignFileManageByPks(@Param("kcsjDesignFileManagePkList") List<Long> kcsjDesignFileManagePkList);

    int deleteInfoData(List<KcsjDesignFileManage> list);

    List<KcsjDesignFileManage> getIds(@Param("ids") List<String> ids);
}
