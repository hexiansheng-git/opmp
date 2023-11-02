package com.hhwy.pm.qqch.wzch.importplan.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlanDetail;
import com.hhwy.pm.qqch.wzch.importplan.mapper.WzchImportExportPlanDetailMapper;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanDetailService;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanService;
import com.hhwy.pm.qqch.wzch.importplan.vo.WzchImportExportPlanDetailImportVO;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 进出口策划详情Service业务层处理
 * 
 * @author mls
 * @date 2022-12-05
 */
@Service
public class WzchImportExportPlanDetailServiceImpl implements IWzchImportExportPlanDetailService {
    @Resource
    private WzchImportExportPlanDetailMapper wzchImportExportPlanDetailMapper;
    @Resource
    private IWzchImportExportPlanService wzchImportExportPlanService;
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private SystemApiService dictTypeService;
    @Resource
    private WzchCommonService wzchCommonService;

    /**
     * 查询进出口策划详情
     * 
     * @param id 进出口策划详情ID
     * @return 进出口策划详情
     */
    @Override
    public WzchImportExportPlanDetail selectWzchImportExportPlanDetailById(Long id) {
        return wzchImportExportPlanDetailMapper.selectWzchImportExportPlanDetailById(id);
    }

    /**
     * 查询进出口策划详情列表
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 进出口策划详情
     */
    @Override
    public List<WzchImportExportPlanDetail> selectWzchImportExportPlanDetailList(WzchImportExportPlanDetail wzchImportExportPlanDetail) {
        return wzchImportExportPlanDetailMapper.selectWzchImportExportPlanDetailList(wzchImportExportPlanDetail);
    }

    /**
     * 新增进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    @Override
    public int insertWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail) {

    wzchImportExportPlanDetail.setId(IdWorker.createId());

        wzchImportExportPlanDetail.setCreateTime(DateUtils.getNowDate());

        return wzchImportExportPlanDetailMapper.insertWzchImportExportPlanDetail(wzchImportExportPlanDetail);
    }

    /**
     * 修改进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    @Override
    public int updateWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail) {
        wzchImportExportPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchImportExportPlanDetailMapper.updateWzchImportExportPlanDetail(wzchImportExportPlanDetail);
    }

    /**
     * 删除进出口策划详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportPlanDetailByIds(List<Long> ids) {
        return wzchImportExportPlanDetailMapper.deleteWzchImportExportPlanDetailByIds(ids);
    }

    /**
     * 删除进出口策划详情信息
     * 
     * @param id 进出口策划详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportPlanDetailById(Long id) {
        return wzchImportExportPlanDetailMapper.deleteWzchImportExportPlanDetailById(id);
    }

    @Override
    public List<WzchImportExportPlanDetail> importData(MultipartFile file) throws IOException {
        List<WzchImportExportPlanDetail> list = new ArrayList<>();
        List<SysDictData> sysDictDataList = dictTypeService.selectDictDataByType("customs_mode");
        EasyExcel.read(file.getInputStream(), WzchImportExportPlanDetailImportVO.class, new ReadListener<WzchImportExportPlanDetailImportVO>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                e.printStackTrace();
            }

//            @Override
//            public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
//                System.out.println(JSONObject.toJSONString(map));
//            }

            @Override
            public void invoke(WzchImportExportPlanDetailImportVO importVo, AnalysisContext analysisContext) {
                WzchImportExportPlanDetail detail = new WzchImportExportPlanDetail();
                BeanUtils.copyProperties(importVo,detail);
                sysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(detail.getCustomsMode()))
                        .findFirst().ifPresent(val -> detail.setCustomsMode(val.getDictValue()));
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

        }).sheet("进出口策划").doReadSync();
        return list;
    }

    @Override
    @Transactional
    public Long save(WzchImportExportPlan wzchImportExportPlan) {
        if (wzchImportExportPlan==null || wzchImportExportPlan.getVersion() == null) {
            throw new BaseException("入参缺失");
        }
        fillwzchImportExportPlan(wzchImportExportPlan);
        if(wzchImportExportPlan.getId() == null){
            wzchImportExportPlan.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(wzchImportExportPlan);
            wzchImportExportPlanService.insertWzchImportExportPlan(wzchImportExportPlan);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(wzchImportExportPlan);
            wzchImportExportPlanService.updateWzchImportExportPlan(wzchImportExportPlan);
            wzchImportExportPlanDetailMapper.deleteByPlanId(wzchImportExportPlan.getId());
        }
        
        List<WzchImportExportPlanDetail> wzchImportExportPlanDetailList = wzchImportExportPlan.getWzchImportExportPlanDetailList();
        if (CollectionUtils.isEmpty(wzchImportExportPlanDetailList))
            return wzchImportExportPlan.getId();
        for (int i = 0; i < wzchImportExportPlanDetailList.size(); i++) {
            wzchImportExportPlanDetailList.get(i).setPlanId(wzchImportExportPlan.getId());
        }
        wzchImportExportPlanDetailMapper.batchInsert(wzchImportExportPlanDetailList);
        return wzchImportExportPlan.getId();
    }

    @Override
    public void export(List<WzchImportExportPlanDetail> list, HttpServletResponse response) throws IOException {
        if(CollectionUtils.isNotEmpty(list)){
            List<SysDictData> sysDictDataList = dictTypeService.selectDictDataByType("customs_mode");
            list.stream().forEach(a ->{
                sysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(a.getCustomsMode()))
                        .findFirst().ifPresent(val -> a.setCustomsMode(val.getDictLabel()));
            });

        }
        ExcelUtils<WzchImportExportPlanDetail> util = new ExcelUtils<WzchImportExportPlanDetail>(WzchImportExportPlanDetail.class);
        util.exportExcel(response,list, "出进出口策划详情");
    }

//    @Override
//    public int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail) {
//        return wzchImportExportPlanDetailMapper.updateValidByPlanId(wzchLocalTransportPlanDetail);
//    }

    private void fillwzchImportExportPlan(WzchImportExportPlan wzchImportExportPlan) {
        if (wzchImportExportPlan==null) {
            throw new BaseException("入参缺失");
        }
//        if(wzchImportExportPlan.getId()==null){
//            throw new BaseException("ID缺失");
//        }

        if(StringUtils.isBlank(wzchImportExportPlan.getPlanCode())){
//            String code = genCodeService.getSetCode(CodeEnum.EQU_IMPORT_EXPORT_PLAN);
//            code += genCodeService.fillString(1, 2);
//            wzchImportExportPlan.setPlanCode(code);
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getCreateUser())){
            wzchImportExportPlan.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getCreateUserName())){
            wzchImportExportPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchImportExportPlan.getCreateTime())){
            wzchImportExportPlan.setCreateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getUpdateUser())){
            wzchImportExportPlan.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getUpdateUserName())){
            wzchImportExportPlan.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchImportExportPlan.getUpdateTime())){
            wzchImportExportPlan.setUpdateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getDelFlag())){
            wzchImportExportPlan.setDelFlag("0");
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getVersionCode())){
            wzchImportExportPlan.setVersionCode("1.0");
        }
        if(StringUtils.isBlank(wzchImportExportPlan.getValid())){
            wzchImportExportPlan.setValid("0");
        }
        if(CollectionUtils.isEmpty(wzchImportExportPlan.getWzchImportExportPlanDetailList())){
            return;
        }
        for (WzchImportExportPlanDetail wzchImportExportPlanDetail : wzchImportExportPlan.getWzchImportExportPlanDetailList()) {
            if(wzchImportExportPlanDetail==null){
                throw new BaseException("【保存数据失败】请完善表格数据");
            }
            Long detailId = wzchImportExportPlanDetail.getId();
            if(detailId==null){
                wzchImportExportPlanDetail.setId( IdWorker.createId());
            }
            if(wzchImportExportPlanDetail.getPlanId()==null){
                wzchImportExportPlanDetail.setPlanId(wzchImportExportPlan.getId());
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getCreateUser())){
                wzchImportExportPlanDetail.setCreateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getCreateUserName())){
                wzchImportExportPlanDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchImportExportPlanDetail.getCreateTime())){
                wzchImportExportPlanDetail.setCreateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getUpdateUser())){
                wzchImportExportPlanDetail.setUpdateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getUpdateUserName())){
                wzchImportExportPlanDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchImportExportPlanDetail.getUpdateTime())){
                wzchImportExportPlanDetail.setUpdateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getDelFlag())){
                wzchImportExportPlanDetail.setDelFlag("0");
            }
            if(StringUtils.isBlank(wzchImportExportPlanDetail.getValid())){
                wzchImportExportPlanDetail.setValid("0");
            }
        }

    }

}
