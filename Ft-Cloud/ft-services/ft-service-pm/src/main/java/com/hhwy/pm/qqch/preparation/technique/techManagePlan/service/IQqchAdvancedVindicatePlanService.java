package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
public interface IQqchAdvancedVindicatePlanService {

    QqchAdvancedVindicatePlan getQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    List<QqchAdvancedVindicatePlan> getQqchAdvancedVindicatePlanList(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int insertQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int updateQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int updateQqchAdvancedVindicatePlanList(List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList);

    int deleteQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int deleteQqchAdvancedVindicatePlanByPks(List<Long> qqchAdvancedVindicatePlanPkList);

    /**
     * 导入
     * @param file
     * @return
     */
    List<QqchAdvancedVindicatePlanImportVo> importExcel(MultipartFile file) throws FileNotFoundException, IllegalAccessException;

    /**
     * 导出
     * @param response
     * @param qqchAdvancedVindicatePlan
     * @throws IOException
     */
    void export(HttpServletResponse response, QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    /**
     * 获取高新维护计划Vo
     * @param qqchAdvancedVindicatePlan
     * @return
     */
    QqchAdvancedVindicatePlanVo getQqchAdvancedVindicatePlanVo(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    /**
     * 保存/确认/提交
     * @param qqchAdvancedVindicatePlanVo
     * @return
     */
    void save(QqchAdvancedVindicatePlanVo qqchAdvancedVindicatePlanVo);
}
