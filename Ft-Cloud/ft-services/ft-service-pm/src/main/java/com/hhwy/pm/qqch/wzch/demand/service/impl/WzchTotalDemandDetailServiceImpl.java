package com.hhwy.pm.qqch.wzch.demand.service.impl;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.QqchTotalDemand;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.vo.QqchTotalDemandVo;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.service.IQqchTotalDemandService;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service.IQqchTotalDemandTimeCountService;
import com.hhwy.pm.qqch.utils.EasyExeclUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import com.hhwy.pm.qqch.wzch.demand.enums.*;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandDetailMapper;
import com.hhwy.pm.qqch.wzch.demand.mapper.WzchTotalDemandMapper;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandTimeCountService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailExportRequest;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailRequest;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandValidVO;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.xmlbeans.impl.xb.ltgfmt.impl.TestsDocumentImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 物资总需用详情Service业务层处理
 *
 * @author mls
 * @date 2022-11-15
 */
@Service
public class WzchTotalDemandDetailServiceImpl implements IWzchTotalDemandDetailService {
    @Resource
    private WzchTotalDemandDetailMapper wzchTotalDemandDetailMapper;
    @Resource
    private IWzchTotalDemandService wzchTotalDemandService;
    @Resource
    private IWzchTotalDemandTimeCountService wzchTotalDemandTimeCountService;
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IWzchSourceService wzchSourceService;
    @Resource
    private SystemApiService systemApiService;
    @Resource
    private WzchTotalDemandMapper wzchTotalDemandMapper;
    @Resource
    private IQqchTotalDemandService qqchTotalDemandService;
    @Resource
    private IQqchTotalDemandTimeCountService qqchTotalDemandTimeCountService;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    /**
     * 查询物资总需用详情
     *
     * @param id 物资总需用详情ID
     * @return 物资总需用详情
     */
    @Override
    public WzchTotalDemandDetail selectWzchTotalDemandDetailById(Long id) {
        return wzchTotalDemandDetailMapper.selectWzchTotalDemandDetailById(id);
    }

    /**
     * 查询物资总需用详情列表
     *
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 物资总需用详情
     */
    @Override
    public List<WzchTotalDemandDetail> selectWzchTotalDemandDetailList(WzchTotalDemandDetail wzchTotalDemandDetail) {
        return wzchTotalDemandDetailMapper.selectWzchTotalDemandDetailList(wzchTotalDemandDetail);
    }

    /**
     * 新增物资总需用详情
     *
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 结果
     */
    @Override
    public int insertWzchTotalDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail) {

        wzchTotalDemandDetail.setId(IdWorker.createId());

        wzchTotalDemandDetail.setCreateTime(DateUtils.getNowDate());

        return wzchTotalDemandDetailMapper.insertWzchTotalDemandDetail(wzchTotalDemandDetail);
    }

    /**
     * 修改物资总需用详情
     *
     * @param wzchTotalDemandDetail 物资总需用详情
     * @return 结果
     */
    @Override
    public int updateWzchTotalDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail) {
        wzchTotalDemandDetail.setUpdateTime(DateUtils.getNowDate());
        return wzchTotalDemandDetailMapper.updateWzchTotalDemandDetail(wzchTotalDemandDetail);
    }

    /**
     * 删除物资总需用详情对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandDetailByIds(List<Long> ids) {
        return wzchTotalDemandDetailMapper.deleteWzchTotalDemandDetailByIds(ids);
    }

    /**
     * 删除物资总需用详情信息
     *
     * @param id 物资总需用详情ID
     * @return 结果
     */
    @Override
    public int deleteWzchTotalDemandDetailById(Long id) {
        return wzchTotalDemandDetailMapper.deleteWzchTotalDemandDetailById(id);
    }

    /**
     * 保存
     *
     * @param wzchTotalDemand
     * @return AjaxResult
     */
    @Override
    @Transactional
    public Long save(WzchTotalDemand wzchTotalDemand) {

        checkWzchTotalDemand(wzchTotalDemand);
        fillWzchTotalDemand(wzchTotalDemand);
        fillWzchTotalDemandDetail(wzchTotalDemand);

//        WzchTotalDemand demand = wzchTotalDemandService.selectWzchTotalDemandById(wzchTotalDemand.getId());
//        if (demand != null) {
//            wzchTotalDemandService.updateWzchTotalDemand(wzchTotalDemand);
//        } else {
//            wzchTotalDemandService.insertWzchTotalDemand(wzchTotalDemand);
//        }
        wzchTotalDemandDetailMapper.deleteByVersion(wzchTotalDemand.getVersion());
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = wzchTotalDemand.getWzchTotalDemandDetailList();
        wzchTotalDemandDetailList = wzchCommonService.setTotalDemadCategoryCode(wzchTotalDemandDetailList);
        List<WzchTotalDemandTimeCount> totalDemandTimeCounts = new ArrayList<>();
        //设置version
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetailList) {
            totalDemandTimeCounts.addAll(wzchTotalDemandDetail.getWzchTotalDemandTimeCountList());
            List<WzchTotalDemandTimeCount> timeCountList = wzchTotalDemandDetail.getWzchTotalDemandTimeCountList();
            for (int i = 0; i < timeCountList.size(); i++) {
                WzchTotalDemandTimeCount time = timeCountList.get(i);
                time.setVersion(wzchTotalDemand.getVersion());
            }
            wzchTotalDemandDetail.setVersion(wzchTotalDemand.getVersion());
        }
        wzchTotalDemandDetailMapper.batchInsert(wzchTotalDemandDetailList);
        List<Long> detailIds = wzchTotalDemandDetailList.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        wzchTotalDemandTimeCountService.deleteByTotalDemandDetailIds(detailIds);
        wzchTotalDemandTimeCountService.batchInsert(totalDemandTimeCounts);
        //确认处理
        String buttonMark = wzchTotalDemand.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = wzchTotalDemand.getMenuId();
            String stageIdentity = wzchTotalDemand.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            //修改wzch_source
//            List<WzchSource> wzchSources = wzchSourceService.selectWzchSourceList(new WzchSource(null, wzchTotalDemand.getProjectId()));
//            if (CollectionUtils.isEmpty(wzchSources)) {
//                log.error("wzchSources为空");
//                return wzchTotalDemand.getId();
//            }
//            WzchSource wzchSource = wzchSources.get(0);
//            wzchSource.setDemandNewVersion(demand.getVersionCode());
//            wzchSource.setDemandValidDate(new Date());
        }
        return wzchTotalDemand.getId();
    }

    
    @Override
    @Transactional
    public void syncQqchTotal(BigDecimal version) {
        //1、删除物资总需、日期明细
        this.wzchTotalDemandDetailMapper.deleteByVersion(version);
        wzchTotalDemandTimeCountService.deleteByVersion(version);
        //2、获取施工策划  
        QqchTotalDemand queryDemand = new QqchTotalDemand();
        queryDemand.setVersion(version);
        List<QqchTotalDemand> totalDemandList = qqchTotalDemandService.getQqchTotalDemandListSource(queryDemand);
        if(CollectionUtils.isEmpty(totalDemandList))
            return ;
        QqchTotalDemandTimeCount query = new QqchTotalDemandTimeCount();
        query.setVersion(version);
        List<QqchTotalDemandTimeCount> timeCountList = qqchTotalDemandTimeCountService.getQqchTotalDemandTimeCountList(query);
        //3、转换为物资总需
        List<WzchTotalDemandDetail> addList = new ArrayList<>(totalDemandList.size());
        List<WzchTotalDemandTimeCount> addTimeList = new ArrayList<>(timeCountList.size());
        for (int i = 0; i < totalDemandList.size(); i++) {
            QqchTotalDemand temp = totalDemandList.get(i);
            WzchTotalDemandDetail tempTotal = new WzchTotalDemandDetail();
            BeanUtils.copyProperties(temp, tempTotal);
            tempTotal.setCategoryName(temp.getMaterialType());
            new AddBaseInfoUtil().addBaseEntity(tempTotal);
            addList.add(tempTotal);
        }
        for (int i = 0; i < timeCountList.size(); i++) {
            QqchTotalDemandTimeCount temp = timeCountList.get(i);
            WzchTotalDemandTimeCount tempTotal = new WzchTotalDemandTimeCount();
            BeanUtils.copyProperties(temp, tempTotal);
            tempTotal.setTotalDemandDetailId(temp.getDemandId());
            //计算季度和年数量
            BigDecimal[] months = new BigDecimal[]{tempTotal.getJanNum(),tempTotal.getFebNum(),tempTotal.getMarNum(),tempTotal.getAprNum()
                    ,tempTotal.getMayNum(),tempTotal.getJunNum(),temp.getJulNum(),temp.getAugNum()
                    ,tempTotal.getSeptNum(),tempTotal.getOctNum(),temp.getNovNum(),temp.getDecNum()};
            BigDecimal quarter1 = BigDecimalUtils.sum(ArrayUtils.subarray(months, 0, 3));
            BigDecimal quarter2 = BigDecimalUtils.sum(ArrayUtils.subarray(months, 3, 6));
            BigDecimal quarter3 = BigDecimalUtils.sum(ArrayUtils.subarray(months, 6, 9));
            BigDecimal quarter4 = BigDecimalUtils.sum(ArrayUtils.subarray(months, 9, 12));
            BigDecimal yearSum = BigDecimalUtils.sum(quarter1,quarter2,quarter3,quarter4);
            tempTotal.setFirstQuarterNum(quarter1);
            tempTotal.setSecondQuarterNum(quarter2);
            tempTotal.setThirdQuarterNum(quarter3);
            tempTotal.setFourthQuarterNum(quarter4);
            tempTotal.setYearNum(yearSum);
            new AddBaseInfoUtil().addBaseEntity(tempTotal);
            addTimeList.add(tempTotal);
        }
        this.wzchTotalDemandDetailMapper.batchInsert(addList);
        this.wzchTotalDemandTimeCountService.batchInsert(addTimeList);
        
    }

    /**
     * 校验数据
     *
     * @param wzchTotalDemand
     */
    private void checkWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        if (wzchTotalDemand == null || CollectionUtils.isEmpty(wzchTotalDemand.getWzchTotalDemandDetailList())) {
            throw new BaseException("保存数据失败，请确认数据是否完整！");
        }
        StringBuilder errorMessage = new StringBuilder("提示：序号");
        Map<String, List<WzchTotalDemandDetail>> map = wzchTotalDemand.getWzchTotalDemandDetailList().stream().collect(Collectors.groupingBy(this::getKey));
        String errorFlag = null;
        for (Map.Entry<String, List<WzchTotalDemandDetail>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                errorFlag = "1";
                for (WzchTotalDemandDetail wzchTotalDemandDetail : entry.getValue()) {
                    errorMessage.append("【" + wzchTotalDemandDetail.getOrderNo() + "】");
                }
                errorMessage.append("相同物资编码执行标准相同;");
            }
        }
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        errorMessage.append("，请检查后提交！");
        if ("1".equals(errorFlag)) {
            throw new BaseException(errorMessage.toString());
        }

    }

    private String getKey(WzchTotalDemandDetail wzchTotalDemandDetail) {
        return wzchTotalDemandDetail.getMaterialCode() + "#" + wzchTotalDemandDetail.getMaterialStandard();
    }

    private void fillWzchTotalDemandDetail(WzchTotalDemand wzchTotalDemand) {
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemand.getWzchTotalDemandDetailList()) {
            if (wzchTotalDemandDetail == null ||
                    CollectionUtils.isEmpty(wzchTotalDemandDetail.getWzchTotalDemandTimeCountList())) {
                throw new BaseException("【保存数据失败】请完善表格数据");
            }
            Long detailId = wzchTotalDemandDetail.getId();
            if (detailId == null) {
                detailId = IdWorker.createId();
                wzchTotalDemandDetail.setId(detailId);
            }
            if (wzchTotalDemandDetail.getTotalDemandId() == null) {
                wzchTotalDemandDetail.setTotalDemandId(wzchTotalDemand.getId());
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getCreateUser())) {
                wzchTotalDemandDetail.setCreateUser(SecurityUtils.getUserId().toString());
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getCreateUserName())) {
                wzchTotalDemandDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if (ObjectUtils.isEmpty(wzchTotalDemandDetail.getCreateTime())) {
                wzchTotalDemandDetail.setCreateTime(DateUtils.getNowDate());
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getUpdateUser())) {
                wzchTotalDemandDetail.setUpdateUser(SecurityUtils.getUserId().toString());
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getUpdateUserName())) {
                wzchTotalDemandDetail.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
            }
            if (ObjectUtils.isEmpty(wzchTotalDemandDetail.getUpdateTime())) {
                wzchTotalDemandDetail.setUpdateTime(DateUtils.getNowDate());
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getDelFlag())) {
                wzchTotalDemandDetail.setDelFlag("0");
            }
            if (StringUtils.isBlank(wzchTotalDemandDetail.getValid())) {
                wzchTotalDemandDetail.setValid("0");
            }
            if (new BigDecimal(0).compareTo(Optional.ofNullable(wzchTotalDemandDetail.getTotalDemandAmount()).orElse(new BigDecimal(0))) != 0) {
                if (Optional.ofNullable(wzchTotalDemandDetail.getTotalDemandAmount()).orElse(new BigDecimal(0))
                        .compareTo(Optional.ofNullable(wzchTotalDemandDetail.getSelfDemandAmount()).orElse(new BigDecimal(0)).add(Optional.ofNullable(wzchTotalDemandDetail.getNonSelfAmount()).orElse(new BigDecimal(0)))) != 0) {
                    throw new BaseException("第【" + wzchTotalDemandDetail.getOrderNo() + "】行的自采需用量加非自采量与总需用量不符");
                }
            }
            BigDecimal big = new BigDecimal(0);
            for (WzchTotalDemandTimeCount wzchTotalDemandTimeCount : wzchTotalDemandDetail.getWzchTotalDemandTimeCountList()) {
                if (wzchTotalDemandTimeCount == null) {
                    throw new BaseException("【保存数据失败】请完善表格数据");
                }
                if (wzchTotalDemandTimeCount.getId() == null) {
                    wzchTotalDemandTimeCount.setId(IdWorker.createId());
                }
                if (wzchTotalDemandTimeCount.getTotalDemandDetailId() == null) {
                    wzchTotalDemandTimeCount.setTotalDemandDetailId(detailId);
                }
                if (StringUtils.isBlank(wzchTotalDemandTimeCount.getCreateUser())) {
                    wzchTotalDemandTimeCount.setCreateUser(SecurityUtils.getUserId().toString());
                }

                if (ObjectUtils.isEmpty(wzchTotalDemandTimeCount.getCreateTime())) {
                    wzchTotalDemandTimeCount.setCreateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchTotalDemandTimeCount.getUpdateUser())) {
                    wzchTotalDemand.setUpdateUser(SecurityUtils.getUserId().toString());
                }

                if (ObjectUtils.isEmpty(wzchTotalDemandTimeCount.getUpdateTime())) {
                    wzchTotalDemandTimeCount.setUpdateTime(DateUtils.getNowDate());
                }
                if (StringUtils.isBlank(wzchTotalDemandTimeCount.getMaterialStandard())) {
                    wzchTotalDemandTimeCount.setMaterialStandard(wzchTotalDemandDetail.getMaterialStandard());
                }
                if (StringUtils.isBlank(wzchTotalDemandTimeCount.getMaterialCode())) {
                    wzchTotalDemandTimeCount.setMaterialCode(wzchTotalDemandDetail.getMaterialCode());
                }
                if (wzchTotalDemandTimeCount.getProjectId() == null) {
                    wzchTotalDemandTimeCount.setProjectId(wzchTotalDemandDetail.getProjectId());
                }
                BigDecimal bigDecimal = new BigDecimal(0);
                if ("Y".equals(wzchTotalDemand.getViewType())) {
                    if (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getYearNum()).orElse(new BigDecimal(0))) == 0) {
                        continue;
                    }
                    bigDecimal = bigDecimal.add(Optional.ofNullable(wzchTotalDemandTimeCount.getYearNum()).orElse(new BigDecimal(0)));
                    big = big.add(bigDecimal);
                }
                if ("M".equals(wzchTotalDemand.getViewType())) {
                    if ((bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getJanNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getFebNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getMarNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getAprNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getMayNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getJunNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getJulNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getAugNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getSeptNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getOctNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getNovNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getDecNum()).orElse(new BigDecimal(0))) == 0)) {
                        continue;
                    }
                    bigDecimal = bigDecimal.add(Optional.ofNullable(wzchTotalDemandTimeCount.getJanNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getFebNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getMarNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getAprNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getMayNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getJunNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getJulNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getAugNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getSeptNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getOctNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getNovNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getDecNum()).orElse(new BigDecimal(0)));
                    big = big.add(bigDecimal);
                }
                if ("Q".equals(wzchTotalDemand.getViewType())) {
                    if ((bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getFirstQuarterNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getSecondQuarterNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getThirdQuarterNum()).orElse(new BigDecimal(0))) == 0)
                            && (bigDecimal.compareTo(Optional.ofNullable(wzchTotalDemandTimeCount.getFourthQuarterNum()).orElse(new BigDecimal(0))) == 0)) {
                        continue;
                    }
                    bigDecimal = bigDecimal.add(Optional.ofNullable(wzchTotalDemandTimeCount.getFirstQuarterNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getSecondQuarterNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getThirdQuarterNum()).orElse(new BigDecimal(0)))
                            .add(Optional.ofNullable(wzchTotalDemandTimeCount.getFourthQuarterNum()).orElse(new BigDecimal(0)));
                    big = big.add(bigDecimal);
                }

            }
            if (new BigDecimal(0).compareTo(big) == 0) {
                continue;
            }
            if (big.compareTo(Optional.ofNullable(wzchTotalDemandDetail.getTotalDemandAmount()).orElse(new BigDecimal(0))) != 0) {
                throw new BaseException("第【" + wzchTotalDemandDetail.getOrderNo() + "】行的总需用量与总需数量不符");
            }
        }

    }

    private void fillWzchTotalDemand(WzchTotalDemand wzchTotalDemand) {
        if (StringUtils.isBlank(wzchTotalDemand.getDemandCode())) {
            String code = genCodeService.getSetCode(CodeEnum.EQU_TOTAL_DEMAND);
            code += genCodeService.fillString(1, 2);
            wzchTotalDemand.setDemandCode(code);
        }
        if (StringUtils.isBlank(wzchTotalDemand.getCreateUser())) {
            wzchTotalDemand.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if (StringUtils.isBlank(wzchTotalDemand.getCreateUserName())) {
            wzchTotalDemand.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if (ObjectUtils.isEmpty(wzchTotalDemand.getCreateTime())) {
            wzchTotalDemand.setCreateTime(DateUtils.getNowDate());
        }
        if (StringUtils.isBlank(wzchTotalDemand.getUpdateUser())) {
            wzchTotalDemand.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if (StringUtils.isBlank(wzchTotalDemand.getUpdateUserName())) {
            wzchTotalDemand.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
//        if (ObjectUtils.isEmpty(wzchTotalDemand.getUpdateTime())) {
//            wzchTotalDemand.setUpdateTime(DateUtils.getNowDate());
//        }
        if (StringUtils.isBlank(wzchTotalDemand.getDelFlag())) {
            wzchTotalDemand.setDelFlag("0");
        }
        if (StringUtils.isBlank(wzchTotalDemand.getVersionCode())) {
            wzchTotalDemand.setVersionCode("1.0");
        }
        if (StringUtils.isBlank(wzchTotalDemand.getValid())) {
            wzchTotalDemand.setValid("0");
        }

        if (wzchTotalDemand.getDeptId() == null) {
            wzchTotalDemand.setDeptId(SecurityUtils.getSysUser().getDeptId());
        }
    }

    @Override
    public List<WzchTotalDemandDetail> selectDemandDetail(WzchTotalDemandDetail wzchTotalDemandDetail) {
        List<WzchTotalDemandDetail> wzchTotalDemandDetailList = wzchTotalDemandDetailMapper.selectWzchTotalDemandDetailList(wzchTotalDemandDetail);
        if (CollectionUtils.isEmpty(wzchTotalDemandDetailList)) {
            return null;
        }
        List<Long> totalDemandIds = wzchTotalDemandDetailList.stream().map(WzchTotalDemandDetail::getTotalDemandId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(totalDemandIds)) {
            return null;
        }
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(totalDemandIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return null;
        }
        WzchTotalDemandServiceImpl.wzchTotalDemandDetail(wzchTotalDemandDetailList, wzchTotalDemandTimeCounts);

        return wzchTotalDemandDetailList;
    }

    @Override
    public void export(WzchTotalDemand request, String leaderFlag, HttpServletResponse response) {
        if (request == null) {
            throw new BaseException("入参缺失");
        }
        //封装数据
        // List<WzchTotalDemandDetail> wzchTotalDemandDetails = fillWzchTotalDemandDetail(wzchTotalDemandDetailRequest);
        //获取年份
        List<String> yearList = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getWzchTotalDemandDetailList())) {
            totalYear(request, yearList);
        } else {
            for (WzchTotalDemandDetail wzchTotalDemandDetail : request.getWzchTotalDemandDetailList()) {
                List<WzchTotalDemandTimeCount> countList = wzchTotalDemandDetail.getWzchTotalDemandTimeCountList();
                if (CollectionUtils.isEmpty(countList)) {
                    continue;
                }
                yearList.addAll(countList.stream().map(WzchTotalDemandTimeCount::getYear).collect(Collectors.toList()));
            }
        }
        List<String> years = yearList.stream().distinct().sorted().collect(Collectors.toList());
        //对应的季度或者月份范围
        Map<String, List<String>> rangeMap = range(request, years);
        List<List<String>> heads = head(years, request, rangeMap, leaderFlag);
        List<List<Object>> data = getData(request.getWzchTotalDemandDetailList(), years, request.getViewType(), rangeMap, leaderFlag);
        
        EasyExeclUtil.export(response, heads, data, "物资总需想详情.xlsx", "物资总需想详情");

    }

    private void totalYear(WzchTotalDemand demand, List<String> yearList) {
        if (demand.getPlanEndTime() == null || demand.getPlanStartTime() == null) {
            throw new BaseException("请选择计划起止时间");
        }
        String startYear = DateUtils.parseDateToStr("yyyy", demand.getPlanStartTime());
        int sYear = Integer.parseInt(startYear);
        String endYear = DateUtils.parseDateToStr("yyyy", demand.getPlanEndTime());
        int eYear = Integer.parseInt(endYear);
        yearList.add(startYear);
        int newYear = sYear;
        while (eYear > newYear) {
            newYear += 1;
            yearList.add(newYear + "");
        }
        yearList.add(endYear);
    }

    /**
     * 季度或者月份范围
     *
     * @param demand
     * @return
     */
    private Map<String, List<String>> range(WzchTotalDemand demand, List<String> years) {
        if (demand.getPlanEndTime() == null || demand.getPlanStartTime() == null) {
            throw new BaseException("请选择计划起止时间");
        }
        String startYear = DateUtils.parseDateToStr("yyyy", demand.getPlanStartTime());
        String startMonth = DateUtils.parseDateToStr("MM", demand.getPlanStartTime());
        String endYear = DateUtils.parseDateToStr("yyyy", demand.getPlanEndTime());
        String endMonth = DateUtils.parseDateToStr("MM", demand.getPlanEndTime());

        Map<String, List<String>> map = new HashedMap<>();
        for (String year : years) {
            if (startYear.equals(year)) {
                if ("M".equals(demand.getViewType())) {
                    map.put(year, StartMonthEnum.parseMonthList(startMonth));
                }
                if ("Q".equals(demand.getViewType())) {
                    String s = MonthQuarterEnum.parseQdesc(startMonth);
                    map.put(year, StartQuarterEnum.parseQuarterList(s));
                }
                continue;
            }
            if (endYear.equals(year)) {
                if ("M".equals(demand.getViewType())) {
                    map.put(year, EndMonthEnum.parseMonthList(endMonth));
                }
                if ("Q".equals(demand.getViewType())) {
                    String s = MonthQuarterEnum.parseQdesc(endMonth);
                    map.put(year, EndQuarterEnum.parseQuarterList(s));
                }
                continue;
            }
            if ("M".equals(demand.getViewType())) {
                map.put(year, StartMonthEnum.ONE_TO_TWELVE.getMonthList());
            }
            if ("Q".equals(demand.getViewType())) {
                map.put(year, StartQuarterEnum.FIRST_TO_FOURTH.getQuarterList());
            }

        }
        return map;
    }

    private List<WzchTotalDemandDetail> fillWzchTotalDemandDetail(WzchTotalDemandDetailRequest wzchTotalDemandDetailRequest) {

        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailMapper.selectWzchTotalDemandDetails(wzchTotalDemandDetailRequest);
        if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) {
            return null;
        }
        List<Long> detailIdList = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(detailIdList);
        //List<String> yesrs = wzchTotalDemandTimeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            throw new BaseException("导出数据失败");
        }
        Map<Long, List<WzchTotalDemandTimeCount>> map = wzchTotalDemandTimeCounts.parallelStream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getTotalDemandDetailId));
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails) {
            for (Map.Entry<Long, List<WzchTotalDemandTimeCount>> entry : map.entrySet()) {
                if (wzchTotalDemandDetail.getId().equals(entry.getKey())) {
                    wzchTotalDemandDetail.setWzchTotalDemandTimeCountList(entry.getValue());
                }
            }
        }
        if (StringUtils.isNotBlank(wzchTotalDemandDetailRequest.getMaterialName()) || StringUtils.isNotBlank(wzchTotalDemandDetailRequest.getMaterialSpec())) {
            MaterialInfo materialInfo = new MaterialInfo();
            materialInfo.setMaterialName(wzchTotalDemandDetailRequest.getMaterialName());
            materialInfo.setMaterialSpec(wzchTotalDemandDetailRequest.getMaterialSpec());
            wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetails);
        } else {
            wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetails);
        }
        return wzchTotalDemandDetails;
    }

    @Override
    public List<WzchTotalDemandDetail> selectWzchTotalDemandDetailListOfLeaderView(WzchTotalDemandDetailRequest request) {
        if (request == null) {
            throw new BaseException("入参缺失");
        }
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailMapper.selectWzchTotalDemandDetailListOfLeaderView(request);
        // List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailMapper.selectWzchTotalDemandDetails(request);
        if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) {
            return new ArrayList<>();
        }
        List<Long> TotalDemandDetailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(TotalDemandDetailIds)) {
            return new ArrayList<>();
        }
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectTotalDemandTimeCounts(TotalDemandDetailIds, request.getViewType());
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return new ArrayList<>();
        }
        fillPlanTime(wzchTotalDemandDetails);
        wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandDetails);
        WzchTotalDemandServiceImpl.wzchTotalDemandDetail(wzchTotalDemandDetails, wzchTotalDemandTimeCounts);
        return screenWzchTotalDemandDetail(wzchTotalDemandDetails, request);
    }

    private void fillPlanTime(List<WzchTotalDemandDetail> wzchTotalDemandDetails) {
        List<Long> demandIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getTotalDemandId).distinct().collect(Collectors.toList());
        List<WzchTotalDemand> wzchTotalDemands = wzchTotalDemandMapper.selectByIds(demandIds);
        WzchTotalDemand demand = wzchTotalDemands.stream().min(Comparator.comparing(WzchTotalDemand::getPlanStartTime)).get();
        Date planStartTime = demand.getPlanStartTime();
        WzchTotalDemand demand2 = wzchTotalDemands.stream().max(Comparator.comparing(WzchTotalDemand::getPlanEndTime)).get();
        Date planEndTime = demand2.getPlanEndTime();
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails) {
            wzchTotalDemandDetail.setPlanStartTime(planStartTime);
            wzchTotalDemandDetail.setPlanEndTime(planEndTime);
        }
    }

    private List<WzchTotalDemandDetail> screenWzchTotalDemandDetail(List<WzchTotalDemandDetail> wzchTotalDemandDetails, WzchTotalDemandDetailRequest request) {
        if (StringUtils.isBlank(request.getMaterialName()) && StringUtils.isBlank(request.getMaterialSpec())) {
            return wzchTotalDemandDetails;
        }
        if (StringUtils.isNotBlank(request.getMaterialName()) && StringUtils.isNotBlank(request.getMaterialSpec())) {
            List<WzchTotalDemandDetail> collect = wzchTotalDemandDetails.stream().filter(d -> d.getMaterialName().contains(request.getMaterialName())
                    && d.getMaterialSpec().contains(request.getMaterialSpec())).collect(Collectors.toList());
            return collect;
        }
        if (StringUtils.isNotBlank(request.getMaterialName())) {
            List<WzchTotalDemandDetail> collect = wzchTotalDemandDetails.stream().filter(d -> d.getMaterialName().contains(request.getMaterialName())).collect(Collectors.toList());
            return collect;
        }
        if (StringUtils.isNotBlank(request.getMaterialSpec())) {
            List<WzchTotalDemandDetail> collect = wzchTotalDemandDetails.stream().filter(d -> d.getMaterialSpec().contains(request.getMaterialSpec())).collect(Collectors.toList());
            return collect;
        }
        return wzchTotalDemandDetails;
    }


    @Override
    public List<WzchTotalDemandDetail> selectWzchTotalDemandDetails(WzchTotalDemandDetailRequest request) {
        if (request == null) {
            return null;
        }
        return wzchTotalDemandDetailMapper.selectWzchTotalDemandDetails(request);
    }

    @Override
    public List<WzchTotalDemandDetail> selectDetailByTotalDemandIds(List<Long> totalDemandIds) {
        if (CollectionUtils.isEmpty(totalDemandIds)) {
            return null;
        }
        return wzchTotalDemandDetailMapper.selectDetailByTotalDemandIds(totalDemandIds);
    }

    @Override
    public List<WzchTotalDemandDetail> selectWzchSourceDetailByProjectId(Long projectId) {
        return wzchTotalDemandDetailMapper.selectWzchSourceDetailByProjectId(projectId);
    }

    @Override
    public void leaderViewExport(WzchTotalDemandDetailRequest wzchTotalDemandDetailRequest, HttpServletResponse response) {
        if (wzchTotalDemandDetailRequest == null) {
            throw new BaseException("入参缺失");
        }
        wzchTotalDemandDetailRequest.setValid("1");
        //封装数据
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = fillWzchTotalDemandDetail(wzchTotalDemandDetailRequest);
        //获取年份
        List<String> yearList = new ArrayList<>();
        for (WzchTotalDemandDetail wzchTotalDemandDetail : wzchTotalDemandDetails) {
            List<WzchTotalDemandTimeCount> countList = wzchTotalDemandDetail.getWzchTotalDemandTimeCountList();
            if (CollectionUtils.isEmpty(countList)) {
                continue;
            }
            yearList.addAll(countList.stream().map(WzchTotalDemandTimeCount::getYear).collect(Collectors.toList()));
        }
        List<String> years = yearList.stream().distinct().sorted().collect(Collectors.toList());
        List<List<Object>> data = getDataLeaderView(wzchTotalDemandDetails, years, wzchTotalDemandDetailRequest.getViewType());
        List<List<String>> heads = headLeaderView(years, wzchTotalDemandDetailRequest.getViewType());
        EasyExeclUtil.export(response, heads, data, "物资总需想详情.xlsx", "物资总需想详情");
    }


    public List<WzchTotalDemandDetail> total2(WzchTotalDemandDetailRequest request) {
        if (request == null) {
            throw new BaseException("入参缺失");
        }
        //现根据项目ID查已生效的物资总需详情
        List<Long> projectIds = null;
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailMapper.selectByProjectIds(projectIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) {
            return null;
        }
        List<Long> detailIds = wzchTotalDemandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIds(detailIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            throw new BaseException("查询数据异常");
        }
        Map<String, List<WzchTotalDemandDetail>> detailGroupMap = wzchTotalDemandDetails.stream().collect(Collectors.groupingBy(d -> d.getMaterialCode() + "_" + d.getMaterialStandard()));
        Map<Long, List<WzchTotalDemandTimeCount>> timeCountGroupMap = wzchTotalDemandTimeCounts.stream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getTotalDemandDetailId));

        Map<String, List<WzchTotalDemandTimeCount>> wbMap = new HashMap<>();
        for (Map.Entry<String, List<WzchTotalDemandDetail>> detailEntry : detailGroupMap.entrySet()) {
            //key的value的年数据需要相加
            List<WzchTotalDemandDetail> demandDetails = detailEntry.getValue();
            if (CollectionUtils.isEmpty(demandDetails)) {
                continue;
            }
            //物资编码和规格型号唯一标识
            for (WzchTotalDemandDetail demandDetail : demandDetails) {
                List<WzchTotalDemandTimeCount> counts = new ArrayList<>();
                for (Map.Entry<Long, List<WzchTotalDemandTimeCount>> countEntry : timeCountGroupMap.entrySet()) {
                    //详情ID和key相同 物资编码和规格型号 数据相同
                    if (demandDetail.getId().equals(countEntry.getKey())) {
                        counts.addAll(countEntry.getValue());
                    }
                }
                wbMap.put(detailEntry.getKey(), counts);
            }

        }
        if (MapUtils.isEmpty(wbMap)) {
            throw new BaseException("查询数据异常");
        }
        for (Map.Entry<String, List<WzchTotalDemandTimeCount>> entry : wbMap.entrySet()) {
            List<WzchTotalDemandTimeCount> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                continue;
            }
            //根据年分组
            Map<String, List<WzchTotalDemandTimeCount>> countMap = value.stream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getYear));
            Map<String, WzchTotalDemandTimeCount> totalMap = new HashMap<>();
            for (Map.Entry<String, List<WzchTotalDemandTimeCount>> countEntry : countMap.entrySet()) {
                List<WzchTotalDemandTimeCount> countValue = countEntry.getValue();
                if ("Y".equals(request.getViewType())) {
                    WzchTotalDemandTimeCount count = new WzchTotalDemandTimeCount();
                    BigDecimal yearNum = new BigDecimal(0);
                    for (WzchTotalDemandTimeCount wzchTotalDemandTimeCount : countValue) {
                        yearNum.add(Optional.ofNullable(wzchTotalDemandTimeCount.getYearNum()).orElse(new BigDecimal(0)));
                    }
                    count.setYearNum(yearNum);
                }

            }


        }

        return null;

    }

    @Override
    public List<WzchTotalDemandDetail> total(WzchTotalDemandDetailRequest request) {
        if (request == null) {
            throw new BaseException("入参缺失");
        }
        return totalInfo(request.getProjectIdList(), request);
    }

    private List<WzchTotalDemandDetail> totalInfo(List<Long> projectIds, WzchTotalDemandDetailRequest request) {
        List<WzchTotalDemandDetail> demandDetails = wzchTotalDemandDetailMapper.selectByProjectIdsOfTotal(projectIds, request.getMaterialStandardList());
        if (CollectionUtils.isEmpty(demandDetails)) {
            return null;
        }
        List<Long> detailIds = demandDetails.stream().map(WzchTotalDemandDetail::getId).collect(Collectors.toList());
        List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCounts = wzchTotalDemandTimeCountService.selectByTotalDemandDetailIdsOfTotal(detailIds);
        if (CollectionUtils.isEmpty(wzchTotalDemandTimeCounts)) {
            return demandDetails;
        }
        List<String> yearList = wzchTotalDemandTimeCounts.stream().map(WzchTotalDemandTimeCount::getYear).distinct().sorted().collect(Collectors.toList());
        Map<Long, List<WzchTotalDemandTimeCount>> map = wzchTotalDemandTimeCounts.stream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getTotalDemandDetailId));
        for (WzchTotalDemandDetail demandDetail : demandDetails) {
            List<WzchTotalDemandTimeCount> timeCounts = map.get(demandDetail.getId());
            Map<String, List<WzchTotalDemandTimeCount>> yearCountMap = timeCounts.stream().collect(Collectors.groupingBy(WzchTotalDemandTimeCount::getYear));
            List<WzchTotalDemandTimeCount> countRes = new ArrayList<>();
            for (String year : yearList) {
                List<WzchTotalDemandTimeCount> yearCountList = yearCountMap.get(year);
                if (CollectionUtils.isNotEmpty(yearCountList)) {
                    countRes.add(yearCountList.get(0));
                } else {
                    countRes.add(WzchTotalDemandTimeCount.initZero(year));
                }
            }
            demandDetail.setWzchTotalDemandTimeCountList(countRes);
            demandDetail.setViewYearList(yearList);


//            这是什么操作??
//            for(Map.Entry<Long, List<WzchTotalDemandTimeCount>> entry:map.entrySet()){
//                if(demandDetail.getId().equals(entry.getKey())){
//                    demandDetail.setWzchTotalDemandTimeCountList(entry.getValue());
//                    demandDetail.setViewYearList(yearList);
//                }
//            }
        }
        fillPlanTime(demandDetails);
        wzchCommonService.setWzchtMaterialInfo(demandDetails);
        return screenWzchTotalDemandDetail(demandDetails, request);
    }

    @Override
    public int updateOfValid(String valid, List<Long> detailIds) {
        if (StringUtils.isBlank(valid) && CollectionUtils.isEmpty(detailIds)) {
            throw new BaseException("数据缺失");
        }
        return wzchTotalDemandDetailMapper.updateOfValid(valid, detailIds);
    }

    @Override
    public List<WzchTotalDemandValidVO> validMaterial(WzchTotalDemandDetail wzchTotalDemandDetail) {
        if (wzchTotalDemandDetail == null || wzchTotalDemandDetail.getProjectId() == null) {
            throw new BaseException("入参缺失");
        }
        WzchTotalDemand wzchTotalDemand = wzchTotalDemandService.selectMaxValidVersionCodeWzchTotalDemandByProjectId(wzchTotalDemandDetail.getProjectId());
        if (wzchTotalDemand == null) {
            return new ArrayList<>();
        }
        List<WzchTotalDemandDetail> wzchTotalDemandDetails = wzchTotalDemandDetailMapper.selectWzchTotalDemandDetailList(new WzchTotalDemandDetail(wzchTotalDemand.getId()));
        if (CollectionUtils.isEmpty(wzchTotalDemandDetails)) {
            return new ArrayList<>();
        }
        List<WzchTotalDemandValidVO> wzchTotalDemandValidVOS = new ArrayList<>();
        wzchTotalDemandDetails.stream().forEach(w -> {
            WzchTotalDemandValidVO vo = new WzchTotalDemandValidVO();
            vo.setMaterialCode(w.getMaterialCode());
            vo.setMaterialTechParam(w.getMaterialTechParam());
            vo.setMaterialStandard(w.getMaterialStandard());
            vo.setTotalDemandAmount(w.getTotalDemandAmount());
            wzchTotalDemandValidVOS.add(vo);
        });
        wzchCommonService.setWzchtMaterialInfo(wzchTotalDemandValidVOS);
        return screenWzchTotalDemandDetail(wzchTotalDemandValidVOS, wzchTotalDemandDetail);
    }

    private List<WzchTotalDemandValidVO> screenWzchTotalDemandDetail(List<WzchTotalDemandValidVO> wzchTotalDemandValidVOS, WzchTotalDemandDetail wzchTotalDemandDetail) {
        if (StringUtils.isBlank(wzchTotalDemandDetail.getMaterialName()) && StringUtils.isBlank(wzchTotalDemandDetail.getMaterialSpec())) {
            wzchTotalDemandValidVOS = wzchTotalDemandValidVOS.stream().sorted(Comparator.comparing(WzchTotalDemandValidVO::getMaterialCode)).collect(Collectors.toList());
            return wzchTotalDemandValidVOS;
        }
        if (StringUtils.isNotBlank(wzchTotalDemandDetail.getMaterialName()) && StringUtils.isNotBlank(wzchTotalDemandDetail.getMaterialSpec())) {
            List<WzchTotalDemandValidVO> collect = wzchTotalDemandValidVOS.stream().filter(d -> d.getMaterialName().contains(wzchTotalDemandDetail.getMaterialName())
                    && d.getMaterialSpec().contains(wzchTotalDemandDetail.getMaterialSpec())).sorted(Comparator.comparing(WzchTotalDemandValidVO::getMaterialCode)).collect(Collectors.toList());
            return collect;
        }
        if (StringUtils.isNotBlank(wzchTotalDemandDetail.getMaterialName())) {
            List<WzchTotalDemandValidVO> collect = wzchTotalDemandValidVOS.stream().filter(d -> d.getMaterialName().contains(wzchTotalDemandDetail.getMaterialName())).sorted(Comparator.comparing(WzchTotalDemandValidVO::getMaterialCode)).collect(Collectors.toList());
            return collect;
        }
        if (StringUtils.isNotBlank(wzchTotalDemandDetail.getMaterialSpec())) {
            List<WzchTotalDemandValidVO> collect = wzchTotalDemandValidVOS.stream().filter(d -> d.getMaterialSpec().contains(wzchTotalDemandDetail.getMaterialSpec())).sorted(Comparator.comparing(WzchTotalDemandValidVO::getMaterialCode)).collect(Collectors.toList());
            return collect;
        }
        wzchTotalDemandValidVOS = wzchTotalDemandValidVOS.stream().sorted(Comparator.comparing(WzchTotalDemandValidVO::getMaterialCode)).collect(Collectors.toList());
        return wzchTotalDemandValidVOS;
    }

    private List<String> subList(List<String> list, int start, int end) {
        List<String> resultList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) {
            return resultList;
        }
        if (start >= list.size()) {
            return list;
        }
        if (start < list.size()) {
            return list.subList(start, end);
        }
        return resultList;
    }


    @Override
    public List<WzchTotalDemandDetail> importData(MultipartFile file, String viewType) throws IOException {
        List<SysDictData> tSysDictDataList = systemApiService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = systemApiService.selectDictDataByType("material_standard");
        List<WzchTotalDemandDetail> demandDetailList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        List<Map<String, String>> list = EasyExcel.read(inputStream).headRowNumber(0).sheet().doReadSync();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, String> head0Map = list.get(0);
        int head0Size = head0Map.size();
        List<String> head0List = new ArrayList<>();
        for (Map.Entry<String, String> entry : head0Map.entrySet()) {
            head0List.add(entry.getValue());
        }
        //截取一级头部 年
        List<String> head0s = subList(head0List, 11, head0List.size() - 2);
        //给年分组，可以得值年对应的月数据或季度数据的个数
        Map<String, Long> head0YearMap = head0s.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> head0YearTreeMap = new TreeMap<>();
        //年排序
        List<String> yearList = new ArrayList<>();
        head0YearTreeMap.putAll(head0YearMap);
        for (Map.Entry<String, Long> entry : head0YearTreeMap.entrySet()) {
            yearList.add(entry.getKey());
        }
        yearList = yearList.stream().sorted().collect(Collectors.toList());
        //年度
        if ("Y".equals(viewType)) {
            demandDetailList = yearView(demandDetailList, list, head0s, yearList, tSysDictDataList, mSysDictDataList, head0Size);
            return demandDetailList;
        }

        //二级头部 月份或季度
        Map<String, String> head1Map = list.get(1);
        int headSize = head1Map.size();
        List<String> head1List = new ArrayList<>();
        for (Map.Entry<String, String> entry : head1Map.entrySet()) {
            head1List.add(entry.getValue());
        }

        //截取二级头部
        List<String> head1s = subList(head1List, 11, head1List.size() - 2);
        Map<String, Map<String, Object>> headBig = new TreeMap<>();
        //给年分配二级头部
        int subStart = 0;
        for (String year : yearList) {
            int subEnd = head0YearMap.get(year).intValue() + subStart;
            List<String> heads = subList(head1s, subStart, subEnd);
            List<Integer> headList = new ArrayList<>();
            for (String a : heads) {
                if ("M".equals(viewType)) {
                    Integer value = MonthQuarterEnum.parseValueByMdesc(a);
                    headList.add(value);
                }
                if ("Q".equals(viewType)) {
                    Integer value = QuarterEnum.parseValue(a);
                    headList.add(value);
                }
            }
            headList = headList.stream().sorted().collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            map.put("subStart", subStart);
            map.put("subEnd", subEnd);
            map.put("data", headList);
            subStart = subEnd;
            headBig.put(year, map);
        }
        int dataFlag = 0;
        for (Map<String, String> param : list) {

            if (dataFlag <= 1 || dataFlag > list.size() - 1) {
                dataFlag++;
                continue;
            }
            WzchTotalDemandDetail detail = fillWzchTotalDemandDetailBaseInfo(yearList, param, tSysDictDataList, mSysDictDataList);
            List<WzchTotalDemandTimeCount> timeCountList = new ArrayList<>();

            Map<String, String> dataMap = list.get(dataFlag);
            List<String> dataList = new ArrayList<>();
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                dataList.add(entry.getValue());
            }
            if (dataList.size() < headSize) {
                int size = headSize - dataList.size();
                List<String> nList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    nList.add(null);
                }
                dataList.addAll(nList);
            }
            //截取数据
            List<String> datas = subList(dataList, 11, head1List.size() - 2);

            for (String year : yearList) {
                Map<String, Object> stringObjectMap = headBig.get(year);
                WzchTotalDemandTimeCount timeCount = new WzchTotalDemandTimeCount();
                timeCount.setYear(year);
                Integer sStart = (Integer) stringObjectMap.get("subStart");
                Integer sEnd = (Integer) stringObjectMap.get("subEnd");
                List<Integer> iList = (List<Integer>) stringObjectMap.get("data");
                List<String> vlist = subList(datas, sStart, sEnd);
                //循环头部
                for (int i = 0; i < iList.size(); i++) {
                    //年度
                    if ("Q".equals(viewType)) {
                        timeCount = fillQuarterCountData(timeCount, iList, vlist, i);
                    }
                    //季度
                    if ("M".equals(viewType)) {
                        timeCount = fillMonthCountData(timeCount, iList, vlist, i);
                    }

                }
                timeCountList.add(timeCount);

            }

            detail.setWzchTotalDemandTimeCountList(timeCountList);
            demandDetailList.add(detail);
            dataFlag++;
        }
        return demandDetailList;
    }

    private List<WzchTotalDemandDetail> yearView(List<WzchTotalDemandDetail> demandDetailList, List<Map<String, String>> list, List<String> head0s, List<String> yearList, List<SysDictData> tSysDictDataList, List<SysDictData> mSysDictDataList, int head0Size) {
        int dataFlag = 0;
        for (Map<String, String> param : list) {
            if (dataFlag <= 0 || dataFlag > list.size() - 1) {
                dataFlag++;
                continue;
            }
            Map<String, String> dataMap = list.get(dataFlag);
            List<String> dataList = new ArrayList<>();
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                dataList.add(entry.getValue());
            }
            if (dataList.size() < head0Size) {
                int size = head0Size - dataList.size();
                List<String> nList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    nList.add(null);
                }
                dataList.addAll(nList);
            }
            //截取数据
            List<String> datas = subList(dataList, 11, dataList.size() - 2);
            if (param.size() < list.get(0).size()) {
                int a = list.get(0).size() - param.size();
                for (int i = 0; i < a; i++) {
                    param.put(param.size() + "", null);
                }
            }
            WzchTotalDemandDetail detail = fillWzchTotalDemandDetailBaseInfo(yearList, param, tSysDictDataList, mSysDictDataList);
            List<WzchTotalDemandTimeCount> timeCountList = new ArrayList<>();
            for (int i = 0; i < yearList.size(); i++) {
                WzchTotalDemandTimeCount wzchTotalDemandTimeCount = new WzchTotalDemandTimeCount();
                wzchTotalDemandTimeCount.setYear(yearList.get(i));
                wzchTotalDemandTimeCount.setYearNum(StringUtils.isBlank(datas.get(i)) ? null : new BigDecimal(datas.get(i)));
                timeCountList.add(wzchTotalDemandTimeCount);
            }
            detail.setWzchTotalDemandTimeCountList(timeCountList);
            demandDetailList.add(detail);
            dataFlag++;
        }
        return demandDetailList;
    }


    private WzchTotalDemandDetail fillWzchTotalDemandDetailBaseInfo(List<String> yearList, Map<String, String> param, List<SysDictData> tSysDictDataList, List<SysDictData> mSysDictDataList) {
        WzchTotalDemandDetail detail = new WzchTotalDemandDetail();
        detail.setMaterialCode(param.get(0));
        detail.setMaterialName(param.get(1));
        detail.setMaterialSpec(param.get(2));
        detail.setMaterialTechParam(param.get(3));
        if (CollectionUtils.isNotEmpty(mSysDictDataList) && StringUtils.isNotBlank(param.get(4))) {
            mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(4)))
                    .findFirst().ifPresent(val -> detail.setMaterialStandard(val.getDictValue()));
            //  detail.setCategoryName(param.get(8));
        } else {
            detail.setMaterialStandard(param.get(4));
        }
        detail.setUnit(param.get(5));
        detail.setTotalDemandAmount(param.get(6) == null ? new BigDecimal(0) : new BigDecimal(param.get(6)));
        detail.setSelfDemandAmount(param.get(7) == null ? new BigDecimal(0) : new BigDecimal(param.get(7)));
        detail.setNonSelfAmount(param.get(8) == null ? new BigDecimal(0) : new BigDecimal(param.get(8)));
        if (CollectionUtils.isNotEmpty(tSysDictDataList) && StringUtils.isNotBlank(param.get(9))) {
            tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictLabel()) && i.getDictLabel().equals(param.get(9)))
                    .findFirst().ifPresent(val -> detail.setCategoryName(val.getDictValue()));
            //  detail.setCategoryName(param.get(8));
        } else {
            detail.setCategoryName(param.get(9));
        }

        detail.setFirstEnterFlag(YesOrNoEnum.parseValue(param.get(10)));
        detail.setContStandard(param.get(param.size() - 2));
        detail.setResourceSurvey(param.get(param.size() - 1));
        detail.setViewYearList(yearList);
        return detail;
    }

    private WzchTotalDemandTimeCount fillMonthCountData(WzchTotalDemandTimeCount timeCount, List<Integer> iList, List<String> vlist, int i) {
        if (1 == iList.get(i)) {
            timeCount.setJanNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (2 == iList.get(i)) {
            timeCount.setFebNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (3 == iList.get(i)) {
            timeCount.setMarNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (4 == iList.get(i)) {
            timeCount.setAprNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (5 == iList.get(i)) {
            timeCount.setMayNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (6 == iList.get(i)) {
            timeCount.setJunNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (7 == iList.get(i)) {
            timeCount.setJulNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (8 == iList.get(i)) {
            timeCount.setAugNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (9 == iList.get(i)) {
            timeCount.setSeptNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (10 == iList.get(i)) {
            timeCount.setOctNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (11 == iList.get(i)) {
            timeCount.setNovNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (12 == iList.get(i)) {
            timeCount.setDecNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        return timeCount;
    }

    private WzchTotalDemandTimeCount fillQuarterCountData(WzchTotalDemandTimeCount timeCount, List<Integer> iList, List<String> vlist, int i) {
        if (QuarterEnum.FIRET.getValue().equals(iList.get(i))) {
            timeCount.setFirstQuarterNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (QuarterEnum.SECOND.getValue().equals(iList.get(i))) {
            timeCount.setSecondQuarterNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (QuarterEnum.THIRD.getValue().equals(iList.get(i))) {
            timeCount.setThirdQuarterNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        if (QuarterEnum.FOURTH.getValue().equals(iList.get(i))) {
            timeCount.setFourthQuarterNum(StringUtils.isBlank(vlist.get(i)) ? null : new BigDecimal(vlist.get(i)));
        }
        return timeCount;
    }


    private List<List<String>> tableHead(List<WzchSourceTotalDemandDetailExportRequest> exportRequests) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> headNames = Stream.of("物资编码", "物资名称", "规格型号", "技术参数", "执行标准", "单位", "总需用量", "自采需用量", "非自采量", "类型", "是否优先进场").collect(Collectors.toList());
        for (String s : headNames) {
            List<String> head = new ArrayList<>();
            head.add(s);
            list.add(head);
        }
        return null;
    }


    /**
     * @param wzchTotalDemandTimeCount
     * @return
     */
    private HashMap fillQuarterData(WzchTotalDemandTimeCount wzchTotalDemandTimeCount) {
        HashMap map = new HashMap(8);
        map.put("第一季度", wzchTotalDemandTimeCount.getFirstQuarterNum());
        map.put("第二季度", wzchTotalDemandTimeCount.getSecondQuarterNum());
        map.put("第三季度", wzchTotalDemandTimeCount.getThirdQuarterNum());
        map.put("第四季度", wzchTotalDemandTimeCount.getFourthQuarterNum());
        return map;
    }

    private HashMap fillMonthData(WzchTotalDemandTimeCount wzchTotalDemandTimeCount) {
        HashMap map = new HashMap(12);
        map.put("一月", wzchTotalDemandTimeCount.getJanNum());
        map.put("二月", wzchTotalDemandTimeCount.getFebNum());
        map.put("三月", wzchTotalDemandTimeCount.getMarNum());
        map.put("四月", wzchTotalDemandTimeCount.getAprNum());
        map.put("五月", wzchTotalDemandTimeCount.getMayNum());
        map.put("六月", wzchTotalDemandTimeCount.getJunNum());
        map.put("七月", wzchTotalDemandTimeCount.getJulNum());
        map.put("八月", wzchTotalDemandTimeCount.getAugNum());
        map.put("九月", wzchTotalDemandTimeCount.getSeptNum());
        map.put("十月", wzchTotalDemandTimeCount.getOctNum());
        map.put("十一月", wzchTotalDemandTimeCount.getNovNum());
        map.put("十二月", wzchTotalDemandTimeCount.getDecNum());
        return map;
    }

    private List<List<String>> head(List<String> years, WzchTotalDemand wzchTotalDemand, Map<String, List<String>> rangeMap, String leaderFlag) {
        String viewType = wzchTotalDemand.getViewType();
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> headNames = null;
        //领导视角
        if ("1".equals(leaderFlag)) {
            headNames = Stream.of("物资编码", "物资名称", "规格型号", "单位", "总需用量", "自采需用量", "非自采量", "类型").collect(Collectors.toList());
        } else {
            headNames = Stream.of("物资编码", "物资名称", "规格型号", "技术参数", "执行标准", "单位", "总需用量", "自采需用量", "非自采量", "类型", "是否优先进场").collect(Collectors.toList());
        }
        for (String s : headNames) {
            List<String> head = new ArrayList<>();
            head.add(s);
            list.add(head);
        }

        if ("Y".equals(viewType)) {
            for (String s : years) {
                List<String> year = new ArrayList<>();
                year.add(s);
                list.add(year);
            }

        }
        if ("Q".equals(viewType)) {
            List<String> headDa = new ArrayList<>();
            for (String year : years) {
                for (Map.Entry<String, List<String>> entry : rangeMap.entrySet()) {
                    if (year.equals(entry.getKey())) {
                        List<String> qHead = entry.getValue();
                        for (String d : qHead) {
                            headDa = new ArrayList<>();
                            headDa.add(year);
                            headDa.add(d);
                            list.add(headDa);
                        }
                    }
                }
            }

        }
        if ("M".equals(viewType)) {
            List<String> headDa = new ArrayList<>();
            for (String year : years) {
                for (Map.Entry<String, List<String>> entry : rangeMap.entrySet()) {
                    if (year.equals(entry.getKey())) {
                        List<String> mHead = entry.getValue();
                        for (String d : mHead) {
                            headDa = new ArrayList<>();
                            headDa.add(year);
                            headDa.add(d);
                            list.add(headDa);
                        }
                    }
                }
            }
        }
        //领导视角
        if ("1".equals(leaderFlag)) {
            return list;
        }
        list.add(Stream.of("业主合同相关技术标准要求").collect(Collectors.toList()));
        list.add(Stream.of("资源调查").collect(Collectors.toList()));
        return list;
    }


    public List<List<Object>> getData(List<WzchTotalDemandDetail> wzchTotalDemandDetails, List<String> yesrs, String viewType, Map<String, List<String>> rangeMap, String leaderFlag) {
        List<List<Object>> data = new ArrayList<>();
        List<SysDictData> tSysDictDataList = systemApiService.selectDictDataByType("total_demand_category_name");
        List<SysDictData> mSysDictDataList = systemApiService.selectDictDataByType("material_standard");
        for (WzchTotalDemandDetail detail : wzchTotalDemandDetails) {
            List<Object> list = new ArrayList<>();
            list.add(detail.getMaterialCode());
            list.add(detail.getMaterialName());
            list.add(detail.getMaterialSpec());
            //非领导视角
            if ("0".equals(leaderFlag)) {
                list.add(detail.getMaterialTechParam());

                if (CollectionUtils.isNotEmpty(mSysDictDataList) && StringUtils.isNotBlank(detail.getMaterialStandard())) {
                    mSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getMaterialStandard()))
                            .findFirst().ifPresent(val -> list.add(val.getDictLabel()));
                } else {
                    list.add(detail.getMaterialStandard());
                }
            }
            list.add(detail.getUnit());
            list.add(detail.getTotalDemandAmount());
            list.add(detail.getSelfDemandAmount());
            list.add(detail.getNonSelfAmount());
            if (CollectionUtils.isNotEmpty(tSysDictDataList) && StringUtils.isNotBlank(detail.getCategoryName())) {
                tSysDictDataList.stream().filter(i -> StringUtils.isNotEmpty(i.getDictValue()) && i.getDictValue().equals(detail.getCategoryName()))
                        .findFirst().ifPresent(val -> list.add(val.getDictLabel()));
            } else {
                list.add(detail.getCategoryName());
            }

            if ("0".equals(leaderFlag)) {
                list.add(YesOrNoEnum.parseDesc(detail.getFirstEnterFlag()));
            }
            List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCountList = detail.getWzchTotalDemandTimeCountList();
            if (CollectionUtils.isEmpty(wzchTotalDemandTimeCountList)) {
                continue;
            }
            for (String year : yesrs) {
                List<WzchTotalDemandTimeCount> collect = wzchTotalDemandTimeCountList.stream().filter(w -> year.equals(w.getYear())).collect(Collectors.toList());
                if ("Y".equals(viewType)) {
                    if (CollectionUtils.isEmpty(collect)) {
                        list.add(null);
                        continue;
                    }
                    WzchTotalDemandTimeCount yearCount = collect.get(0);
                    if (year.equals(yearCount.getYear())) {
                        list.add(yearCount.getYearNum());
                    }
                }
                if ("Q".equals(viewType)) {
                    List<String> value = rangeMap.get(year);
                    if (CollectionUtils.isEmpty(collect)) {
                        for (int i = 0; i < value.size(); i++) {
                            list.add(null);
                        }
                        continue;
                    }
                    WzchTotalDemandTimeCount yearCount = collect.get(0);
                    for (String q : value) {
                        fillQList(q, list, yearCount);
                    }
                }
                if ("M".equals(viewType)) {
                    List<String> value = rangeMap.get(year);
                    if (CollectionUtils.isEmpty(collect)) {
                        for (int i = 0; i < value.size(); i++) {
                            list.add(null);
                        }
                        continue;
                    }
                    WzchTotalDemandTimeCount yearCount = collect.get(0);
                    for (String m : value) {
                        fillMList(m, list, yearCount);
                    }

                }

            }
            if ("0".equals(leaderFlag)) {
                list.add(detail.getContStandard());
                list.add(detail.getResourceSurvey());
            }
            data.add(list);
        }
        return data;
    }

    private void fillQList(String q, List<Object> list, WzchTotalDemandTimeCount yearCount) {
        if (q.equals("第一季度")) {
            list.add(yearCount.getFirstQuarterNum());
            return;
        }
        if (q.equals("第二季度")) {
            list.add(yearCount.getSecondQuarterNum());
            return;
        }
        if (q.equals("第三季度")) {
            list.add(yearCount.getThirdQuarterNum());
            return;
        }
        if (q.equals("第四季度")) {
            list.add(yearCount.getFourthQuarterNum());
            return;
        }
    }


    private void fillMList(String m, List<Object> list, WzchTotalDemandTimeCount yearCount) {
        if (m.equals(MonthQuarterEnum.JAN.getmDesc())) {
            list.add(yearCount.getJanNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.FEB.getmDesc())) {
            list.add(yearCount.getFebNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.MAR.getmDesc())) {
            list.add(yearCount.getMarNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.APR.getmDesc())) {
            list.add(yearCount.getAprNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.MAY.getmDesc())) {
            list.add(yearCount.getMayNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.JUN.getmDesc())) {
            list.add(yearCount.getJunNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.JUL.getmDesc())) {
            list.add(yearCount.getJulNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.AUG.getmDesc())) {
            list.add(yearCount.getAugNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.SEPT.getmDesc())) {
            list.add(yearCount.getSeptNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.OCT.getmDesc())) {
            list.add(yearCount.getOctNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.NOV.getmDesc())) {
            list.add(yearCount.getNovNum());
            return;
        }
        if (m.equals(MonthQuarterEnum.DEC.getmDesc())) {
            list.add(yearCount.getDecNum());
            return;
        }
    }


    public List<List<Object>> getDataLeaderView(List<WzchTotalDemandDetail> wzchTotalDemandDetails, List<String> yesrs, String viewType) {
        List<List<Object>> data = new ArrayList<>();
        for (WzchTotalDemandDetail detail : wzchTotalDemandDetails) {
            List<Object> list = new ArrayList<>();
            list.add(detail.getMaterialCode());
            list.add(detail.getMaterialName());
            list.add(detail.getMaterialSpec());
            list.add(detail.getUnit());
            list.add(detail.getTotalDemandAmount());
            list.add(detail.getSelfDemandAmount());
            list.add(detail.getNonSelfAmount());
            list.add(detail.getCategoryName());

            for (String year : yesrs) {
                if (CollectionUtils.isEmpty(detail.getWzchTotalDemandTimeCountList())) {
                    continue;
                }
                for (WzchTotalDemandTimeCount yearCount : detail.getWzchTotalDemandTimeCountList()) {
                    if ("Y".equals(viewType)) {
                        if (!yesrs.contains(yearCount.getYear())) {
                            list.add(null);
                        }
                        if (year.equals(yearCount.getYear())) {
                            list.add(yearCount.getYearNum());
                            break;
                        }
                    }
                    if ("Q".equals(viewType)) {
                        if (!yesrs.contains(yearCount.getYear())) {
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                        }
                        if (year.equals(yearCount.getYear())) {
                            list.add(yearCount.getFirstQuarterNum());
                            list.add(yearCount.getSecondQuarterNum());
                            list.add(yearCount.getThirdQuarterNum());
                            list.add(yearCount.getFourthQuarterNum());
                            break;
                        }
                    }
                    if ("M".equals(viewType)) {
                        if (!yesrs.contains(yearCount.getYear())) {
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                            list.add(null);
                        }
                        if (year.equals(yearCount.getYear())) {
                            list.add(yearCount.getJanNum());
                            list.add(yearCount.getFebNum());
                            list.add(yearCount.getMarNum());
                            list.add(yearCount.getAprNum());
                            list.add(yearCount.getMayNum());
                            list.add(yearCount.getJunNum());
                            list.add(yearCount.getJulNum());
                            list.add(yearCount.getAugNum());
                            list.add(yearCount.getSeptNum());
                            list.add(yearCount.getOctNum());
                            list.add(yearCount.getNovNum());
                            list.add(yearCount.getDecNum());
                            break;
                        }
                    }

                }
            }
            data.add(list);
        }
        return data;

    }

    private List<List<String>> headLeaderView(List<String> years, String viewType) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> headNames = null;
        headNames = Stream.of("物资编码", "物资名称", "规格型号", "单位", "总需用量", "自采需用量", "非自采量", "类型").collect(Collectors.toList());
        for (String s : headNames) {
            List<String> head = new ArrayList<>();
            head.add(s);
            list.add(head);
        }

        if ("Y".equals(viewType)) {
            for (String s : years) {
                List<String> year = new ArrayList<>();
                year.add(s);
                list.add(year);
            }

        }

        if ("Q".equals(viewType)) {
            List<String> qHead = Stream.of("第一季度", "第二季度", "第三季度", "第四季度").collect(Collectors.toList());
            List<String> headDa = new ArrayList<>();
            for (String year : years) {
                for (String d : qHead) {
                    headDa = new ArrayList<>();
                    headDa.add(year);
                    headDa.add(d);
                    list.add(headDa);
                }
            }

        }
        if ("M".equals(viewType)) {
            List<String> mHead = Stream.of("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月").collect(Collectors.toList());
            List<String> headDa = new ArrayList<>();
            for (String year : years) {
                for (String d : mHead) {
                    headDa = new ArrayList<>();
                    headDa.add(year);
                    headDa.add(d);
                    list.add(headDa);
                }
            }
        }

        return list;
    }


}
