package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * wbs-最新
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
public interface XmslWbsMapper {

    XmslWbs getXmslWbs(XmslWbs xmslWbs);

    /**
     * 最新数据
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> latestWbsList(XmslWbs xmslWbs);

    List<XmslWbs> latestWbsSimpleAllList();

    List<XmslWbs> latestWbsId(XmslWbs xmslWbs);

    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    List<XmslWbs> getByIds(@Param("ids") Long[] ids);

    Long countByWbs(XmslWbs wbs);

    /**
     * 获取wbs下部位编码或名称为空的数组条目（最多为1）
     * @param mainId
     * @return
     */
    String countWbsOnlyOne(Long mainId);

    /**
     * 判断当前是否有生效的wbs
     * @return
     */
    int hasEffectWbs();

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(@Param("xmslWbsList") List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(List<XmslWbs> xmslWbsList);

    int updateXmslWbsAncestorList(List<XmslWbs> list);

    /**
     * 修改版本修改状态
     * 版本修改状态，1:原数据修改,2:新增数据，3：禁用（仅生效数据）
     * @param list
     * @return
     */
    int updatePtVar2List(List<XmslWbs> list);

    /**
     * 清空ptVar4(p6ID)
     * @param ids
     * @return
     */
    int clearPtVar4(Set<Long> ids);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(@Param("xmslWbsPkList") List<Long> xmslWbsPkList);

    int deleteByParentIds(@Param("list") Collection<Long> list);
}
