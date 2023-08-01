package com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.QqchTrafficCar;

/**
 * @author ldd
 * @date 2023-08-01 16:12:57
 * @remark 
 */
public interface QqchTrafficCarMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchTrafficCar getQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

    List<QqchTrafficCar> getQqchTrafficCarList(QqchTrafficCar qqchTrafficCar);

    int insertQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

    int insertQqchTrafficCarList(@Param("qqchTrafficCarList") List<QqchTrafficCar> qqchTrafficCarList);

    int updateQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

            int updateQqchTrafficCarList(@Param("qqchTrafficCarList") List<QqchTrafficCar> qqchTrafficCarList);
    
    int deleteQqchTrafficCar(QqchTrafficCar qqchTrafficCar);

            int deleteQqchTrafficCarByPks(@Param("qqchTrafficCarPkList") List<Long> qqchTrafficCarPkList);
    }
