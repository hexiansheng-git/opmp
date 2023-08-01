package com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.service;

import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.QqchTrafficCar;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.vo.QqchTrafficCarVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:12:57
 * @remark 
 */
public interface IQqchTrafficCarService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchTrafficCar getQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

   QqchTrafficCarVo getQqchTrafficCarList(QqchTrafficCar qqchTrafficCar);

    int insertQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

    int updateQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

            int updateQqchTrafficCarList(List<QqchTrafficCar> qqchTrafficCarList);
    
    int deleteQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

            int deleteQqchTrafficCarByPks(List<Long> qqchTrafficCarPkList);

    void save(QqchTrafficCarVo qqchTrafficCarParam);
}
