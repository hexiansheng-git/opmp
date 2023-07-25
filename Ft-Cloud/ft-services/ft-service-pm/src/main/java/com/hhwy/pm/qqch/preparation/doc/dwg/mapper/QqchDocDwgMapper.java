package com.hhwy.pm.qqch.preparation.doc.dwg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark 
 */
public interface QqchDocDwgMapper {
                                                                                                                                                                                                                                                                                                                                        
    QqchDocDwg getQqchDocDwg(QqchDocDwg qqchDocDwg);

    List<QqchDocDwg> getQqchDocDwgList(QqchDocDwg qqchDocDwg);

    int insertQqchDocDwg(QqchDocDwg qqchDocDwg);

    int insertQqchDocDwgList(@Param("qqchDocDwgList") List<QqchDocDwg> qqchDocDwgList);

    int updateQqchDocDwg(QqchDocDwg qqchDocDwg);

            int updateQqchDocDwgList(@Param("qqchDocDwgList") List<QqchDocDwg> qqchDocDwgList);
    
    int deleteQqchDocDwg(QqchDocDwg qqchDocDwg);

            int deleteQqchDocDwgByPks(@Param("qqchDocDwgPkList") List<Long> qqchDocDwgPkList);
    }
