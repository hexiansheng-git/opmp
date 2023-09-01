package com.hhwy.pm.qqch.preparation.sbch.imported.material.controller;/**
 * @description TODO
 * @date 2022-12-12 15:33
 * @author zq
 */

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.ISbchTotalDemandPlanDetailService;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zq
 * @date 2022年12月12日 15:33
 */
@Controller
@RequestMapping("/train/detail")
public class SbchMaterialTranPlanDetailController {
    @Autowired
    private ISbchTotalDemandPlanDetailService sbchTotalDemandPlanDetailService;

    /**
     * 大型成套设备运输方案详情导入
     * @author zq
     * @date 2022/12/12 15:40
     * @param file
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file){
        try{
            ExcelUtils<SbchMaterialTranPlanDetail> util = new ExcelUtils(SbchMaterialTranPlanDetail.class);
            List<SbchMaterialTranPlanDetail> list = util.importExcel(file.getInputStream());
            Map<String, String> boxTypeMap = DictUtil.getDictData("box_type");
            Map<String, String> trainWayMap = DictUtil.getDictData("train_way");

            HashSet<String> materialCodes = new HashSet<>();
            for (SbchMaterialTranPlanDetail detail : list) {
                detail.setBoxType(boxTypeMap.get(detail.getBoxType()));
                detail.setTrainWay(trainWayMap.get(detail.getTrainWay()));
                materialCodes.add(detail.getMaterialCode());
            }
            SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail = new SbchTotalDemandPlanDetail();
            sbchTotalDemandPlanDetail.setMaterialCodeList(new ArrayList<>(materialCodes));
            List<SbchTotalDemandPlanDetail> detailList = sbchTotalDemandPlanDetailService.selectSbchTotalDemandPlanDetailLeaderList(sbchTotalDemandPlanDetail);
            Map<String, String> materialNameMap = detailList.stream().collect(Collectors.groupingBy(t -> t.getMaterialCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getMaterialName() + "&" + v.get(0).getMaterialSpec())));
            for (SbchMaterialTranPlanDetail detail : list) {
                String s = materialNameMap.get(detail.getMaterialCode());
                if (!ObjectNullUtil.isEmpty(s)) {
                    String[] split = s.split("&");
                    detail.setMaterialName(split[0]);
                    detail.setMaterialSpec(split.length>1 ? split[1] : "");
                }
            }
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
