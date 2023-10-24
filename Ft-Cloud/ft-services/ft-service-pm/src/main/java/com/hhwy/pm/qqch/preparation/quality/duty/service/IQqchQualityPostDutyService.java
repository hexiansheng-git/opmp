package com.hhwy.pm.qqch.preparation.quality.duty.service;

import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchQualityPostDutyVo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-03 14:28:57
 * @remark 9.1.1 质量岗位职责
 */
public interface IQqchQualityPostDutyService {

    QqchQualityPostDutyVo getQqchQualityPostDutyList(BigDecimal version);

    void batchSave(QqchQualityPostDutyVo qqchQualityPostDutyVo);

    List<QqchQualityPostDuty> getNewVersionList(BigDecimal version);

    /**
     * 9.1.2弹窗
     * @return
     */
    List<QqchQualityPostDuty> getPopWindows() throws ParserConfigurationException, IOException, SAXException;

}
