package com.hhwy.pm.qqch.tax.qqchTaxCost.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCost.vo.TaxCostVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
public interface IQqchTaxCostService {

    QqchTaxCost getQqchTaxCost(QqchTaxCost qqchTaxCost);

    List<QqchTaxCost> getQqchTaxCostList(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCost(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList);

    int updateQqchTaxCost(QqchTaxCost qqchTaxCost);

    int updateQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList);

    int deleteQqchTaxCost(QqchTaxCost qqchTaxCost);

    int deleteQqchTaxCostByPks(List<Long> qqchTaxCostPkList);

    CompileEntity<TaxCostVO> getList(QqchTaxCost taxCost);

    void save(CompileEntity<TaxCostVO> dto);


    public List<QqchTaxCost> getCostList(QqchTaxCost dto);

    List<QqchTaxCostDetail> saveCostList(List<QqchTaxCost> qqchTaxCosts);

    CompileEntity<TaxCostVO> taxList(QqchTaxCost dealListDto);

    void downTemp(HttpServletResponse response, QqchTaxCost params) throws IOException;

    List<QqchTaxCost> importData(MultipartFile file, Map<String,Object> params) throws IOException;
}
