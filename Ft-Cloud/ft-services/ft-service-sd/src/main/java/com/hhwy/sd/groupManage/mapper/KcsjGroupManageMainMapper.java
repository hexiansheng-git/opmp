package com.hhwy.sd.groupManage.mapper;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:15
 * @remark
 */
@Repository
public interface KcsjGroupManageMainMapper {

    KcsjGroupManageMain getKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    List<KcsjGroupManageMain> getKcsjGroupManageMainList(KcsjGroupManageMain kcsjGroupManageMain);

    int insertKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int insertKcsjGroupManageMainList(@Param("kcsjGroupManageMainList") List<KcsjGroupManageMain> kcsjGroupManageMainList);

    int updateKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int updateKcsjGroupManageMainList(@Param("list") List<KcsjGroupManageMain> kcsjGroupManageMainList);

    int deleteKcsjGroupManageMain(KcsjGroupManageMain kcsjGroupManageMain);

    int deleteKcsjGroupManageMainByPks(@Param("kcsjGroupManageMainPkList") List<Long> kcsjGroupManageMainPkList);
}
