package com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.QqchSmallMachinery;

/**
 * @author ldd
 * @date 2023-08-02 10:50:59
 * @remark 
 */
public interface QqchSmallMachineryMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchSmallMachinery getQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

    List<QqchSmallMachinery> getQqchSmallMachineryList(QqchSmallMachinery qqchSmallMachinery);

    int insertQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

    int insertQqchSmallMachineryList(@Param("qqchSmallMachineryList") List<QqchSmallMachinery> qqchSmallMachineryList);

    int updateQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

            int updateQqchSmallMachineryList(@Param("qqchSmallMachineryList") List<QqchSmallMachinery> qqchSmallMachineryList);
    
    int deleteQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

            int deleteQqchSmallMachineryByPks(@Param("qqchSmallMachineryPkList") List<Long> qqchSmallMachineryPkList);
    }
