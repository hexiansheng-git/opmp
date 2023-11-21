package com.hhwy.pm.qqch.qqchChange.service;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.vo.QqchChangeVo;
import com.hhwy.system.api.domain.SysMenu;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前期策划变更
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
public interface IQqchChangeService {

    /**
     * 获取最新版version
     * @return
     */
    BigDecimal effectVersion();

    BigDecimal effectVersion(String tenantKey);

    QqchChange getQqchChange(QqchChange qqchChange);

    List<QqchChange> list(QqchChange qqchChange);

    List<QqchChange> getQqchChangeList(QqchChange qqchChange);

    /**
     * 获取调整明细，如果不可调整会抛出异常
     * @return
     */
    QqchChangeVo adjustDetail();

    QqchChangeVo detail(Long id);

    /**
     * 保存
     * @param vo
     */
    void save(QqchChangeVo vo);

    /**
     * 流程结束触发
     * @param businessId
     */
    void finishFlow(Long businessId);

    /**
     * 编制人审批通过后触发（变更内容调整节点）
     * @param businessId
     */
    void editingFinishFlow(Long businessId);

    /**
     * 评审人审批通过后触发
     * 更新子表评审完成时间
     * @param businessId
     */
    void reviewFinishFlow(Long businessId);
    /**
     * 全部评审人审批通过后触发
     * 更新主表评审完成时间
     * @param businessId
     */
    void reviewAllFinishFlow(Long businessId);

    /**
     * 前期策划变更权限菜单
     * @param mainId
     * @param authFlag  传1获取权限菜单，否则获取全部
     * @return
     */
    List<SysMenu> authMenuList(Long mainId,String authFlag);

    int insertQqchChange(QqchChange qqchChange);

    int insertQqchChangeList(List<QqchChange> qqchChangeList);

    int updateQqchChange(QqchChange qqchChange);

    int updateQqchChangeList(List<QqchChange> qqchChangeList);

    int deleteQqchChange(QqchChange qqchChange);

    int deleteQqchChangeByPks(List<Long> qqchChangePkList);
}
