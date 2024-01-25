package com.hhwy.pm.xmsl.wbs.service;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
public interface IXmslWbsService {

    XmslWbs getXmslWbs(XmslWbs xmslWbs);

    List<XmslWbs> getByMainId(Long mainId);

    XmslWbs getByCode(String code);

    /**
     * 查询生效版本wbs
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> latestWbsList(XmslWbs xmslWbs);

    List<XmslWbs> latestWbsListSortLevel();

    /**
     * 台账也数据
     * @param xmslWbs
     * @return {list,mainId}
     */
    Map listData(XmslWbs xmslWbs);

    /**
     * 获取最新数据，支持搜索
     * @param wbs
     * @return
     */
    List<XmslWbs> latestData(XmslWbs wbs);

    /**
     * 查询wbs，根据params.tname决定查询历史还是wbs
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> getXmslWbsListByTname(XmslWbs xmslWbs);

    /**
     * 强制查询wbs
     * @param xmslWbs
     * @return
     */
    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    List<XmslWbs> getXmslWbsHistoryList(Long mainId);

    /**
     * 获取wbs的所有子级
     * @param ids wbsId数组
     * @return
     */
    List<XmslWbs> childListByIds(Long[] ids);
    List<XmslWbs> childListById(Long id);
    List<XmslWbs> childListByIds(Long[] ids,boolean containSelf);

    /**
     * 获取wbs的所有子级，替换掉其id,父id
     * @param ids
     * @return
     */
    Map<String,List<XmslWbsHistory>> copyChildList(Long[] ids,Long mainId);

    /**
     * 获取wbs简要信息
     * @return
     */
    List<XmslWbs> latestWbsSimpleAllList();

    /**
     * 导入数据
     * @param file
     */
    Long importData(Long id,MultipartFile file) throws Exception;
    
    /**
     * 导出数据
     * @param main 
     * @return
     */
    List<XmslWbs> exportData(XmslWbsMain main);

    /**
     * 处理wbs祖级信息(祖级id,祖级名称)
     *
     */
    void handlerAncestors();
    void handlerAncestors(Function<XmslWbs,XmslWbs> func);

    /**
     * 初始化wbs到redis（异步）
     */
    public void initWbs2Redis();

    /**
     * 初始化wbs到redis（异步）
     */
    public void initWbs2Redis(String tenantKey);

    Long countByWbs(XmslWbs wbs);

    /**
     * 判断是否有生效的wbs
     * @return { hasEffect 是否有生效,hasChange :是否有调整  }
     */
    Map hasEffectWbs();
    
    void save(XmslWbsDto dto);

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(List<XmslWbs> xmslWbsList);

    int updatePtVar2List(List<XmslWbs> list);

    int clearPtVar4(Set<Long> set);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(List<Long> xmslWbsPkList);
    
}
