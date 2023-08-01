package com.hhwy.pm.qqch.preparation.measureexp.range.service;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark
 */
public interface IQqchMeasureExpPersonService {

    QqchMeasureExpPerson getQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    List<QqchMeasureExpPerson> getQqchMeasureExpPersonList(QqchMeasureExpPerson qqchMeasureExpPerson);

    int insertQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    int insertQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList);

    int updateQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    int updateQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList);

    int deleteQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    int deleteQqchMeasureExpPersonByPks(List<Long> qqchMeasureExpPersonPkList);

    /**
     * 获取有效版本的数据
     *
     * @param qqchMeasureExpPerson
     * @return
     */
    List<QqchMeasureExpPerson> getQqchMeasureExpPersonListByVersionCode(QqchMeasureExpPerson qqchMeasureExpPerson);

    /**
     * 保存列表
     *
     * @param personList
     */
    void saveList(List<QqchMeasureExpPerson> personList);
}
