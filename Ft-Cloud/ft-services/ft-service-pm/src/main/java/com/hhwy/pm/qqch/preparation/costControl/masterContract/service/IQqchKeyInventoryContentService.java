package com.hhwy.pm.qqch.preparation.costControl.masterContract.service;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchKeyInventoryContent;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo.QqchKeyInventoryContentVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark
 */
public interface IQqchKeyInventoryContentService {

    QqchKeyInventoryContent getQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    List<QqchKeyInventoryContent> getQqchKeyInventoryContentList(QqchKeyInventoryContent qqchKeyInventoryContent);

    int insertQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int updateQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int updateQqchKeyInventoryContentList(List<QqchKeyInventoryContent> qqchKeyInventoryContentList);

    int deleteQqchKeyInventoryContent(QqchKeyInventoryContent qqchKeyInventoryContent);

    int deleteQqchKeyInventoryContentByPks(List<Long> qqchKeyInventoryContentPkList);

    /**
     * 获取须重点关注的清单及内容Vo
     * @param qqchKeyInventoryContent
     * @return
     */
    QqchKeyInventoryContentVo getQqchKeyInventoryContentVo(QqchKeyInventoryContent qqchKeyInventoryContent);

    /**
     * 保存/确认/提交
     * @param qqchKeyInventoryContentVo
     * @return
     */
    void save(QqchKeyInventoryContentVo qqchKeyInventoryContentVo);
}
