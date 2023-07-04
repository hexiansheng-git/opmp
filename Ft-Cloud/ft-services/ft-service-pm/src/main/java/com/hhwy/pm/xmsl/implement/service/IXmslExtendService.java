package com.hhwy.pm.xmsl.implement.service;

import com.hhwy.pm.xmsl.implement.domain.XmslExtend;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:35
 * @remark 当地政策要点说明, 社会和人文条件说明, 气候条件附件
 */
public interface IXmslExtendService {

    XmslExtend getXmslExtend(XmslExtend xmslExtend);

    XmslExtend save(XmslExtend xmslExtend);
}
