package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.QqchSpecialBigProcessControl;

/**
 * @author ldd
 * @date 2023-08-08 16:17:26
 * @remark 
 */
public interface QqchSpecialBigProcessControlMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSpecialBigProcessControl getQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    List<QqchSpecialBigProcessControl> getQqchSpecialBigProcessControlList(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int insertQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int insertQqchSpecialBigProcessControlList(@Param("qqchSpecialBigProcessControlList") List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList);

    int updateQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

            int updateQqchSpecialBigProcessControlList(@Param("qqchSpecialBigProcessControlList") List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList);
    
    int deleteQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

            int deleteQqchSpecialBigProcessControlByPks(@Param("qqchSpecialBigProcessControlPkList") List<Long> qqchSpecialBigProcessControlPkList);
    }
