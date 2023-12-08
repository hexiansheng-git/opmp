package com.hhwy.pm.xmsl.wbs.service;

import java.util.List;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark 
 */
public interface IXmslWbsMainService {

    XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain);

    XmslWbsMain getById(Long mainId);

    List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain);

    XmslWbsMain getEffect();

    Long getXmslWbsMainCount(XmslWbsMain xmslWbsMain);

    /**
     * 获取生效数据|最新版本数据
     * @return
     */
    XmslWbsMain getLast();

    /**
     * 获取当前调整中的数据
     * @return
     */
    XmslWbsMain getAdjustInfo();

    /**
     * 初始化调整
     */
    Long initAdjust();

    int insertXmslWbsMain(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList);

    int updateXmslWbsMain(XmslWbsMain xmslWbsMain);

    int updateXmslWbsMainList(List<XmslWbsMain> xmslWbsMainList);
    
    void deleteXmslWbsMain(XmslWbsMain xmslWbsMain);

    int deleteXmslWbsMainByPks(List<Long> xmslWbsMainPkList);

    /**
     * 完成流程
     * @param id
     */
    void finishFlow(Long id);

    /**
     * 流程结束后部分逻辑异步处理
     * @param main
     * @param effect
     * @param tenantKey
     */
    public void asyncHandler(String tenantKey,XmslWbsMain main,XmslWbsMain effect);

    /**
     * 更新p6编号
     * @param wbsInfoVo
     * @return
     */
    public int updateP6Code(WbsInfoVo wbsInfoVo);

}
