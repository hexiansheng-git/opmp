package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.service;

import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.vo.QqchSafeThreeTypePersonVo;

import java.util.List;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark 
 */
public interface IQqchSafeThreeTypePersonService {
                                                                                                                                                                                                                                                                                                                                                                
    QqchSafeThreeTypePerson getQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    List<QqchSafeThreeTypePerson> getQqchSafeThreeTypePersonList(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int insertQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

    int insertQqchSafeThreeTypePersonList(QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo);

    int updateQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

            int updateQqchSafeThreeTypePersonList(List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList);
    
    int deleteQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson);

            int deleteQqchSafeThreeTypePersonByPks(List<Long> qqchSafeThreeTypePersonPkList);
    }
