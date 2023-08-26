package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.dto.SbchEquipmentAllotDTO;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper.SbchEquipmentAllotMapper;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.ISbchEquipmentAllotDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.ISbchEquipmentAllotService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 同国别设备Service业务层处理
 *
 * @author hwj
 * @date 2022-11-25
 */
@Service
@Slf4j
public class SbchEquipmentAllotServiceImpl implements ISbchEquipmentAllotService {
    @Autowired
    private SbchEquipmentAllotMapper sbchEquipmentAllotMapper;

    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private ISbchEquipmentAllotDetailsService sbchEquipmentAllotDetailsService;


    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";

    /**
     * 查询同国别设备列表
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 同国别设备
     */
    @SelfEmpty(clazz = SbchEquipmentAllot.class)
    @Override
    public List<SbchEquipmentAllot> selectSbchEquipmentAllotList(SbchEquipmentAllot sbchEquipmentAllot) {
        return sbchEquipmentAllotMapper.selectSbchEquipmentAllotList(sbchEquipmentAllot);
    }

    /**
     * 新增同国别设备
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 结果
     */
    @Override
    @Transactional
    public String insertSbchEquipmentAllotAndDetails(SbchEquipmentAllotDTO sbchEquipmentAllot) {
        return "";
    }

}
