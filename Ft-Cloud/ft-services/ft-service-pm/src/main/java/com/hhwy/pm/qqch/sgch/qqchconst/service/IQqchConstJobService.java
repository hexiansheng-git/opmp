package com.hhwy.pm.qqch.sgch.qqchconst.service;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:17
 * @remark
 */
public interface IQqchConstJobService {

    QqchConstJob getQqchConstJob(QqchConstJob qqchConstJob);

    List<QqchConstJob> getQqchConstJobList(QqchConstJob qqchConstJob);

    int insertQqchConstJob(QqchConstJob qqchConstJob);

    int insertQqchConstJobList(List<QqchConstJob> qqchConstJobList);

    int updateQqchConstJob(QqchConstJob qqchConstJob);

    int updateQqchConstJobList(List<QqchConstJob> qqchConstJobList);

    int deleteQqchConstJob(QqchConstJob qqchConstJob);

    int deleteQqchConstJobByPks(List<Long> qqchConstJobPkList);

    void saveList(List<QqchConstJob> iJobList);

    List<QqchConstJob> list(QqchConstJob dealListDto);
}
