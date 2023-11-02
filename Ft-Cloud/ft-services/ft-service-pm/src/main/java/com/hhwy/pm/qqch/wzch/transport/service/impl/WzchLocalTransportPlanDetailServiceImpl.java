package com.hhwy.pm.qqch.wzch.transport.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlanDetail;
import com.hhwy.pm.qqch.wzch.transport.mapper.WzchLocalTransportPlanDetailMapper;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanDetailService;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanService;
import com.hhwy.pm.qqch.wzch.transport.vo.WzchLocalTransportPlanDetailImportVO;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当地运输方案策划详情Service业务层处理
 * 
 * @author mls
 * @date 2022-12-06
 */
@Service
public class WzchLocalTransportPlanDetailServiceImpl implements IWzchLocalTransportPlanDetailService {
    @Autowired
    private WzchLocalTransportPlanDetailMapper wzchLocalTransportPlanDetailMapper;
    @Resource
    private IWzchLocalTransportPlanService wzchLocalTransportPlanService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 查询当地运输方案策划详情
     * 
     * @param id 当地运输方案策划详情ID
     * @return 当地运输方案策划详情
     */
    @Override
    public WzchLocalTransportPlanDetail selectWzchLocalTransportPlanDetailById(Long id) {
        return wzchLocalTransportPlanDetailMapper.selectWzchLocalTransportPlanDetailById(id);
    }

    /**
     * 查询当地运输方案策划详情列表
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 当地运输方案策划详情
     */
    @Override
    public List<WzchLocalTransportPlanDetail> selectWzchLocalTransportPlanDetailList(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail) {
        return wzchLocalTransportPlanDetailMapper.selectWzchLocalTransportPlanDetailList(wzchLocalTransportPlanDetail);
    }

    /**
     * 新增当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    @Override
    public int insertWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail) {

    wzchLocalTransportPlanDetail.setId(IdWorker.createId());

        wzchLocalTransportPlanDetail.setCreateTime(DateUtils.getNowDate());

        return wzchLocalTransportPlanDetailMapper.insertWzchLocalTransportPlanDetail(wzchLocalTransportPlanDetail);
    }

    /**
     * 修改当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    @Override
    public int updateWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail) {
        wzchLocalTransportPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchLocalTransportPlanDetailMapper.updateWzchLocalTransportPlanDetail(wzchLocalTransportPlanDetail);
    }

    /**
     * 删除当地运输方案策划详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchLocalTransportPlanDetailByIds(List<Long> ids) {
        return wzchLocalTransportPlanDetailMapper.deleteWzchLocalTransportPlanDetailByIds(ids);
    }

    /**
     * 删除当地运输方案策划详情信息
     * 
     * @param id 当地运输方案策划详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchLocalTransportPlanDetailById(Long id) {
        return wzchLocalTransportPlanDetailMapper.deleteWzchLocalTransportPlanDetailById(id);
    }

    @Override
    @Transactional
    public boolean save(WzchLocalTransportPlan wzchLocalTransportPlan){
        fillWzchLocalTransportPlan(wzchLocalTransportPlan);
        if(wzchLocalTransportPlan.getId() == null){
            wzchLocalTransportPlan.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(wzchLocalTransportPlan);
            wzchLocalTransportPlanService.insertWzchLocalTransportPlan(wzchLocalTransportPlan);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(wzchLocalTransportPlan);
            wzchLocalTransportPlanService.updateWzchLocalTransportPlan(wzchLocalTransportPlan);
            wzchLocalTransportPlanDetailMapper.deleteByPlanId(wzchLocalTransportPlan.getId());
        }
        wzchLocalTransportPlan.getWzchLocalTransportPlanDetailList().stream().forEach(r->{
            r.setPlanId(wzchLocalTransportPlan.getId());
        });
        if(CollectionUtils.isNotEmpty(wzchLocalTransportPlan.getWzchLocalTransportPlanDetailList()))
            wzchLocalTransportPlanDetailMapper.batchInsert(wzchLocalTransportPlan.getWzchLocalTransportPlanDetailList());

        if (ButtonMark.CONFIRM.equals(wzchLocalTransportPlan.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchLocalTransportPlan.getMenuId();
            String stageIdentity = wzchLocalTransportPlan.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return true;
    }

    @Override
    public List<WzchLocalTransportPlanDetail> importData(MultipartFile file) throws IOException {
        if(file==null){
            throw new BaseException("请选择需要导入的文件");
        }
        List<WzchLocalTransportPlanDetail> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), WzchLocalTransportPlanDetailImportVO.class, new ReadListener<WzchLocalTransportPlanDetailImportVO>() {

            @Override
            public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
                e.printStackTrace();
            }


            @Override
            public void invoke(WzchLocalTransportPlanDetailImportVO importVo, AnalysisContext analysisContext) {
                WzchLocalTransportPlanDetail detail = new WzchLocalTransportPlanDetail();
                BeanUtils.copyProperties(importVo,detail);
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

        }).sheet("当地运输方案策划").doReadSync();
        return list;
    }

    @Override
    public int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail){
        if(wzchLocalTransportPlanDetail== null || wzchLocalTransportPlanDetail.getId()==null){
            throw new BaseException("入参缺失");
        }
        return wzchLocalTransportPlanDetailMapper.updateValidByPlanId(wzchLocalTransportPlanDetail);
    }

    private void fillWzchLocalTransportPlan(WzchLocalTransportPlan wzchLocalTransportPlan) {
        if (wzchLocalTransportPlan==null) {
            throw new BaseException("入参缺失");
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getPlanCode())){
//            String code = genCodeService.getCode(CodeEnum.EQU_LOCAL_TRANSPORT_PLAN);
//            code += genCodeService.fillString(1, 2);
//            wzchLocalTransportPlan.setPlanCode(code);
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getCreateUser())){
            wzchLocalTransportPlan.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getCreateUserName())){
            wzchLocalTransportPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchLocalTransportPlan.getCreateTime())){
            wzchLocalTransportPlan.setCreateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getUpdateUser())){
            wzchLocalTransportPlan.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getUpdateUserName())){
            wzchLocalTransportPlan.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchLocalTransportPlan.getUpdateTime())){
            wzchLocalTransportPlan.setUpdateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getDelFlag())){
            wzchLocalTransportPlan.setDelFlag("0");
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getVersionCode())){
            wzchLocalTransportPlan.setVersionCode("1.0");
        }
        if(StringUtils.isBlank(wzchLocalTransportPlan.getValid())){
            wzchLocalTransportPlan.setValid("0");
        }
        if(CollectionUtils.isEmpty(wzchLocalTransportPlan.getWzchLocalTransportPlanDetailList())){
            return;
        }
        for (WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail : wzchLocalTransportPlan.getWzchLocalTransportPlanDetailList()) {
            if(wzchLocalTransportPlanDetail==null){
                throw new BaseException("【保存数据失败】请完善表格数据");
            }
            Long detailId = wzchLocalTransportPlanDetail.getId();
            if(detailId==null){
                wzchLocalTransportPlanDetail.setId( IdWorker.createId());
            }
            if(wzchLocalTransportPlanDetail.getPlanId()==null){
                wzchLocalTransportPlanDetail.setPlanId(wzchLocalTransportPlan.getId());
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getCreateUser())){
                wzchLocalTransportPlanDetail.setCreateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getCreateUserName())){
                wzchLocalTransportPlanDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchLocalTransportPlanDetail.getCreateTime())){
                wzchLocalTransportPlanDetail.setCreateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getUpdateUser())){
                wzchLocalTransportPlanDetail.setUpdateUser(SecurityUtils.getUserId().toString());
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getUpdateUserName())){
                wzchLocalTransportPlanDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if(ObjectUtils.isEmpty(wzchLocalTransportPlanDetail.getUpdateTime())){
                wzchLocalTransportPlanDetail.setUpdateTime(DateUtils.getNowDate());
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getDelFlag())){
                wzchLocalTransportPlanDetail.setDelFlag("0");
            }
            if(StringUtils.isBlank(wzchLocalTransportPlanDetail.getValid())){
                wzchLocalTransportPlanDetail.setValid("0");
            }
        }

    }

}
