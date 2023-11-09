package com.hhwy.pm.qqch.qqchChange.service;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.vo.QqchChangeVo;
import com.hhwy.system.api.domain.SysMenu;

import java.util.List;

/**
 * 前期策划变更
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
public interface IQqchChangeService {

    QqchChange getQqchChange(QqchChange qqchChange);

    List<QqchChange> list(QqchChange qqchChange);

    List<QqchChange> getQqchChangeList(QqchChange qqchChange);

    /**
     * 获取调整明细，如果不可调整会抛出异常
     * @return
     */
    QqchChangeVo adjustDetail();

    /**
     * 保存
     * @param vo
     */
    void save(QqchChangeVo vo);

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
