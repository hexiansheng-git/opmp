package com.hhwy.pm.qqch.wzch.specialmaterial.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.mapper.WzchSpecialMaterialRequestDetailMapper;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialRequestDetailService;
import com.hhwy.pm.qqch.wzch.specialmaterial.vo.WzchSpecialMaterialRequestDetailImportVO;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 专项物资发运策划-发运要求Service业务层处理
 * 
 * @author mls
 * @date 2022-12-07
 */
@Service
public class WzchSpecialMaterialRequestDetailServiceImpl implements IWzchSpecialMaterialRequestDetailService {
    @Autowired
    private WzchSpecialMaterialRequestDetailMapper wzchSpecialMaterialRequestDetailMapper;

    /**
     * 查询专项物资发运策划-发运要求
     * 
     * @param id 专项物资发运策划-发运要求ID
     * @return 专项物资发运策划-发运要求
     */
    @Override
    public WzchSpecialMaterialRequestDetail selectWzchSpecialMaterialRequestDetailById(Long id) {
        return wzchSpecialMaterialRequestDetailMapper.selectWzchSpecialMaterialRequestDetailById(id);
    }

    /**
     * 查询专项物资发运策划-发运要求列表
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 专项物资发运策划-发运要求
     */
    @Override
    public List<WzchSpecialMaterialRequestDetail> selectWzchSpecialMaterialRequestDetailList(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail) {
        return wzchSpecialMaterialRequestDetailMapper.selectWzchSpecialMaterialRequestDetailList(wzchSpecialMaterialRequestDetail);
    }

    /**
     * 新增专项物资发运策划-发运要求
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 结果
     */
    @Override
    public int insertWzchSpecialMaterialRequestDetail(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail) {

    wzchSpecialMaterialRequestDetail.setId(IdWorker.createId());

        wzchSpecialMaterialRequestDetail.setCreateTime(DateUtils.getNowDate());

        return wzchSpecialMaterialRequestDetailMapper.insertWzchSpecialMaterialRequestDetail(wzchSpecialMaterialRequestDetail);
    }

    /**
     * 修改专项物资发运策划-发运要求
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 结果
     */
    @Override
    public int updateWzchSpecialMaterialRequestDetail(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail) {
        wzchSpecialMaterialRequestDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchSpecialMaterialRequestDetailMapper.updateWzchSpecialMaterialRequestDetail(wzchSpecialMaterialRequestDetail);
    }

    /**
     * 删除专项物资发运策划-发运要求对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialRequestDetailByIds(List<Long> ids) {
        return wzchSpecialMaterialRequestDetailMapper.deleteWzchSpecialMaterialRequestDetailByIds(ids);
    }

    /**
     * 删除专项物资发运策划-发运要求信息
     * 
     * @param id 专项物资发运策划-发运要求ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialRequestDetailById(Long id) {
        return wzchSpecialMaterialRequestDetailMapper.deleteWzchSpecialMaterialRequestDetailById(id);
    }

    @Override
    public List<WzchSpecialMaterialRequestDetail> importRequestDetail(MultipartFile file) throws IOException {
        if(file==null){
            throw new BaseException("入参缺失");
        }
        List<WzchSpecialMaterialRequestDetail> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), WzchSpecialMaterialRequestDetailImportVO.class, new ReadListener<WzchSpecialMaterialRequestDetailImportVO>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                e.printStackTrace();
            }

//            @Override
//            public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
//                System.out.println(JSONObject.toJSONString(map));
//            }

            @Override
            public void invoke(WzchSpecialMaterialRequestDetailImportVO importVo, AnalysisContext analysisContext) {
                WzchSpecialMaterialRequestDetail detail = new WzchSpecialMaterialRequestDetail();
                BeanUtils.copyProperties(importVo,detail);
                detail.setWarn(YesOrNoEnum.parseValue(detail.getWarn()));
                list.add(detail);
                if(list.size() > 500){
                    throw new BaseException("单次导入仅能500条");
                }
            }

            @Override
            public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }

            @Override
            public boolean hasNext(AnalysisContext analysisContext) {
                return true;
            }

        }).sheet("发运要求").doReadSync();
        return list;
    }

    @Override
    public int batchInsert(List<WzchSpecialMaterialRequestDetail> requestDetails) {
        if(CollectionUtils.isEmpty(requestDetails)){
            throw new BaseException("入参缺失");
        }
        return wzchSpecialMaterialRequestDetailMapper.batchInsert(requestDetails);
    }

    @Override
    public int deleteByPlanId(Long planId){
       return wzchSpecialMaterialRequestDetailMapper.deleteByPlanId(planId);
    }

    @Override
    public int updateValidByPlanId(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail){
        return wzchSpecialMaterialRequestDetailMapper.updateValidByPlanId(wzchSpecialMaterialRequestDetail);
    }

}
