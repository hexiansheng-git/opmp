package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
public interface IQqchDangerConstructionListService {

    QqchDangerConstructionListVo getQqchDangerConstructionListList();

    void syncData();

    int insertQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int insertQqchDangerConstructionListList(List<QqchDangerConstructionList> qqchDangerConstructionListList);

    int updateQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int updateQqchDangerConstructionListList(List<QqchDangerConstructionList> qqchDangerConstructionListList);

    int deleteQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int deleteQqchDangerConstructionListByPks(List<Long> qqchDangerConstructionListPkList);
}
