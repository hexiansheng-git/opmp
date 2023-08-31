package com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClear;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClearDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.mapper.SbchImportCustomsClearMapper;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 清关档案策划Service业务层处理
 * 
 * @author zq
 * @date 2022-12-14
 */
@Service
public class SbchImportCustomsClearServiceImpl implements ISbchImportCustomsClearService {
    @Autowired
    private SbchImportCustomsClearMapper sbchImportCustomsClearMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchImportCustomsClearDetailService sbchImportCustomsClearDetailService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    /**
     * 查询清关档案策划
     * 
     * @param id 清关档案策划ID
     * @return 清关档案策划
     */
    @Override
    public SbchImportCustomsClear selectSbchImportCustomsClearById(Long id) {
        return sbchImportCustomsClearMapper.selectSbchImportCustomsClearById(id);
    }

    /**
     * 查询清关档案策划列表
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 清关档案策划
     */
    @Override
    @SelfEmpty(clazz = SbchImportCustomsClear.class)
    //@CustomDatascope(alias = "clear")
    public List<SbchImportCustomsClear> selectSbchImportCustomsClearList(SbchImportCustomsClear sbchImportCustomsClear) {
        return sbchImportCustomsClearMapper.selectSbchImportCustomsClearList(sbchImportCustomsClear);
    }

    /**
     * 新增清关档案策划
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSbchImportCustomsClear(SbchImportCustomsClear sbchImportCustomsClear) {
//        EntityUtils.setCreateInfo(sbchImportCustomsClear);
        sbchImportCustomsClear.setId(IdWorker.createId());
        String code = genCodeService.getSetCode(CodeEnum.EQU_CUSTOMS_CLEAR);
        sbchImportCustomsClear.setFormNo(code);

        List<SbchImportCustomsClearDetail> detailList = sbchImportCustomsClear.getDetailList();
        for (SbchImportCustomsClearDetail sbchImportCustomsClearDetail : detailList) {
            BeanUtils.copyProperties(sbchImportCustomsClear,sbchImportCustomsClearDetail);
            sbchImportCustomsClearDetail.setInfoId(sbchImportCustomsClear.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchImportCustomsClear);
        sbchImportCustomsClearDetailService.batchInsert(detailList);
        return sbchImportCustomsClearMapper.insertSbchImportCustomsClear(sbchImportCustomsClear);
    }

    /**
     * 修改清关档案策划
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchImportCustomsClear(SbchImportCustomsClear sbchImportCustomsClear) {
//        EntityUtils.setUpdateInfo(sbchImportCustomsClear);
        List<SbchImportCustomsClearDetail> detailList = sbchImportCustomsClear.getDetailList();
        for (SbchImportCustomsClearDetail sbchImportCustomsClearDetail : detailList) {
            BeanUtils.copyProperties(sbchImportCustomsClear,sbchImportCustomsClearDetail);
            sbchImportCustomsClearDetail.setInfoId(sbchImportCustomsClear.getId());
        }
        /*保证只有唯一一条数据有效*/
        checkUniqueValid(sbchImportCustomsClear);
        //删除旧的数据
        sbchImportCustomsClearDetailService.deleteSbchImportCustomsClearDetailByInfoId(sbchImportCustomsClear.getId());
        //添加新的数据
        sbchImportCustomsClearDetailService.batchInsert(detailList);
        return sbchImportCustomsClearMapper.updateSbchImportCustomsClear(sbchImportCustomsClear);
    }

    /**
     * 删除清关档案策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSbchImportCustomsClearByIds(String ids) {
        SbchImportCustomsClear sbchImportCustomsClear = new SbchImportCustomsClear();
        sbchImportCustomsClear.setIsValid("1");
        sbchImportCustomsClear.setIds(Convert.toStrArray(ids));
        List<SbchImportCustomsClear> sbchImportCustomsClears = sbchImportCustomsClearMapper.selectSbchImportCustomsClearList(sbchImportCustomsClear);
        if(!ObjectNullUtil.isEmpty(sbchImportCustomsClears)){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"有效版本不可删除");
        }
        return sbchImportCustomsClearMapper.deleteSbchImportCustomsClearByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除清关档案策划信息
     * 
     * @param id 清关档案策划ID
     * @return 结果
     */
    public int deleteSbchImportCustomsClearById(Long id) {
        return sbchImportCustomsClearMapper.deleteSbchImportCustomsClearById(id);
    }

    @Override
    public SbchImportCustomsClear getList(BigDecimal version) {
        SbchImportCustomsClear returnVo = new SbchImportCustomsClear();
        version = VersionUtil.getVersion("sbch_import_customs_clear", version);
        SbchImportCustomsClear sbchImportCustomsClear = new SbchImportCustomsClear();
        sbchImportCustomsClear.setVersionNo(version);
        List<SbchImportCustomsClear> sbchImportCustomsClears = sbchImportCustomsClearMapper.selectSbchImportCustomsClearList(sbchImportCustomsClear);
        if(!ObjectNullUtil.isEmpty(sbchImportCustomsClears)){
            SbchImportCustomsClear sbchImportCustomsClear1 = sbchImportCustomsClears.get(0);
            returnVo = sbchImportCustomsClear1;
            //详情列表
            SbchImportCustomsClearDetail sbchImportCustomsClearDetail = new SbchImportCustomsClearDetail();
            sbchImportCustomsClearDetail.setInfoId(sbchImportCustomsClear1.getId());
            List<SbchImportCustomsClearDetail> sbchImportCustomsClearDetails = sbchImportCustomsClearDetailService.selectSbchImportCustomsClearDetailList(sbchImportCustomsClearDetail);
            returnVo.setDetailList(sbchImportCustomsClearDetails);
        }else{
            ArrayList<SbchImportCustomsClearDetail> detailArrayList = new ArrayList<>();
            List<String> cuntomsClearNames = DictUtil.getDictLableList("cuntoms_clear_names");
            for (String cuntomsClearName : cuntomsClearNames) {
                SbchImportCustomsClearDetail sbchImportCustomsClearDetail = new SbchImportCustomsClearDetail();
                sbchImportCustomsClearDetail.setFileName(cuntomsClearName);
                detailArrayList.add(sbchImportCustomsClearDetail);
            }
            returnVo.setDetailList(detailArrayList);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchSave(SbchImportCustomsClear sbchImportCustomsClear) {
        List<SbchImportCustomsClearDetail> detailList = sbchImportCustomsClear.getDetailList();
        SbchImportCustomsClear temp = new SbchImportCustomsClear();
        temp.setVersion(sbchImportCustomsClear.getVersion());
        List<SbchImportCustomsClear> sbchImportCustomsClears = sbchImportCustomsClearMapper.selectSbchImportCustomsClearList(temp);
        if(!ObjectNullUtil.isEmpty(sbchImportCustomsClears)){
            sbchImportCustomsClear.setId(sbchImportCustomsClears.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchImportCustomsClear);
            sbchImportCustomsClearMapper.updateSbchImportCustomsClear(sbchImportCustomsClear);
        }else{
            sbchImportCustomsClear.setId(IdWorker.createId());
            String code = genCodeService.getSetCode(CodeEnum.EQU_CUSTOMS_CLEAR);
            sbchImportCustomsClear.setUnicode(code);
            sbchImportCustomsClear.setTitleName("进口方案");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchImportCustomsClear);
            sbchImportCustomsClearMapper.insertSbchImportCustomsClear(sbchImportCustomsClear);
        }
        //删除
        sbchImportCustomsClearDetailService.deleteSbchImportCustomsClearDetailByInfoId(sbchImportCustomsClear.getId());
        if(!ObjectNullUtil.isEmpty(detailList)){
            //校验数据必填
            if("1".equals(sbchImportCustomsClear.getButtonMark())||"2".equals(sbchImportCustomsClear.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            }
            for (SbchImportCustomsClearDetail sbchImportCustomsClearDetail : detailList) {
                BeanUtils.copyProperties(sbchImportCustomsClear,sbchImportCustomsClearDetail);
                sbchImportCustomsClearDetail.setInfoId(sbchImportCustomsClear.getId());
                EntityUtils.setCreateInfo(sbchImportCustomsClearDetail);
                sbchImportCustomsClearDetail.setId(IdWorker.createId());
            }
            //添加新的子表数据
            sbchImportCustomsClearDetailService.batchInsert(detailList);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchImportCustomsClear.getButtonMark())){
            //插入确认记录
            String menuId = sbchImportCustomsClear.getMenuId();
            String stageIdentity = sbchImportCustomsClear.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    private void checkUniqueValid(SbchImportCustomsClear sbchImportCustomsClear){
        if("1".equals(sbchImportCustomsClear.getIsValid())){
            sbchImportCustomsClearMapper.updateInfoNotValid();
        }
    }
}
