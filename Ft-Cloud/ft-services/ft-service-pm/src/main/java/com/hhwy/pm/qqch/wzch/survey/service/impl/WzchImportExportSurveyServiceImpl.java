package com.hhwy.pm.qqch.wzch.survey.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurvey;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCountry;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCustoms;
import com.hhwy.pm.qqch.wzch.survey.mapper.WzchImportExportSurveyMapper;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCountryService;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCustomsService;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 进出口调查Service业务层处理
 * 
 * @author mls
 * @date 2022-12-05
 */
@Service
public class WzchImportExportSurveyServiceImpl implements IWzchImportExportSurveyService {
    @Autowired
    private WzchImportExportSurveyMapper wzchImportExportSurveyMapper;
    @Resource
    private IWzchImportExportSurveyCountryService wzchImportExportSurveyCountryService;
    @Resource
    private IWzchImportExportSurveyCustomsService wzchImportExportSurveyCustomsService;
    @Resource
    private IQqchReviewService qqchReviewService;
    @Resource
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Resource
    private GenCodeService genCodeService;
    /**
     * 查询进出口调查
     * 
     * @param id 进出口调查ID
     * @return 进出口调查
     */
    @Override
    public WzchImportExportSurvey selectWzchImportExportSurveyById(Long id) {
        return wzchImportExportSurveyMapper.selectWzchImportExportSurveyById(id);
    }

    /**
     * 查询进出口调查列表
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 进出口调查
     */
    @Override
//    @CustomDatascope(alias = "wzch_import_export_survey")
    public List<WzchImportExportSurvey> selectWzchImportExportSurveyList(WzchImportExportSurvey wzchImportExportSurvey) {
        return wzchImportExportSurveyMapper.selectWzchImportExportSurveyList(wzchImportExportSurvey);
    }

    /**
     * 新增进出口调查
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 结果
     */
    @Override
    public int insertWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey) {

    wzchImportExportSurvey.setId(IdWorker.createId());

        wzchImportExportSurvey.setCreateTime(DateUtils.getNowDate());

        return wzchImportExportSurveyMapper.insertWzchImportExportSurvey(wzchImportExportSurvey);
    }

    /**
     * 修改进出口调查
     * 
     * @param wzchImportExportSurvey 进出口调查
     * @return 结果
     */
    @Override
    public int updateWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey) {
        wzchImportExportSurvey.setUpdateTime(DateUtils.getNowDate());
        return wzchImportExportSurveyMapper.updateWzchImportExportSurvey(wzchImportExportSurvey);
    }

    /**
     * 删除进出口调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyByIds(String ids) {
        return wzchImportExportSurveyMapper.deleteWzchImportExportSurveyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除进出口调查信息
     * 
     * @param id 进出口调查ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportSurveyById(Long id) {
        return wzchImportExportSurveyMapper.deleteWzchImportExportSurveyById(id);
    }

    @Override
    public WzchImportExportSurvey edit(WzchImportExportSurvey vo) {
        BigDecimal version = VersionUtil.getVersion("wzch_import_export_survey", vo.getVersion());
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        
        List<WzchImportExportSurvey> list = wzchImportExportSurveyMapper.selectWzchImportExportSurveyList(new WzchImportExportSurvey(version));
        if(CollectionUtils.isEmpty(list)){
            vo.setWzchImportExportSurveyCountryList(new ArrayList<>());
            vo.setWzchImportExportSurveyCustomsList(new ArrayList<>());
            return vo;
        }
        BeanUtils.copyProperties(list.get(0),vo);
        vo.setStageIdentity(qqchReviewService.getStage());
        List<WzchImportExportSurveyCountry> wzchImportExportSurveyCountries = wzchImportExportSurveyCountryService.selectWzchImportExportSurveyCountryList(new WzchImportExportSurveyCountry(vo.getId()));
        List<WzchImportExportSurveyCustoms> wzchImportExportSurveyCustoms = wzchImportExportSurveyCustomsService.selectWzchImportExportSurveyCustomsList(new WzchImportExportSurveyCustoms(vo.getId()));
        vo.setWzchImportExportSurveyCountryList(wzchImportExportSurveyCountries);
        if(CollectionUtils.isNotEmpty(wzchImportExportSurveyCustoms)){
            wzchImportExportSurveyCustoms = wzchImportExportSurveyCustoms.stream().sorted(Comparator.comparing(WzchImportExportSurveyCustoms::getId)).collect(Collectors.toList());
        }
        vo.setWzchImportExportSurveyCustomsList(wzchImportExportSurveyCustoms);
        return vo;
    }

    @Override
    @Transactional
    public Long save(WzchImportExportSurvey wzchImportExportSurvey) {
        checkWzchImportExportSurvey(wzchImportExportSurvey);

        if(wzchImportExportSurvey.getId()==null){
            wzchImportExportSurvey.setId(IdWorker.createId());
            fillWzchImportExportSurvey(wzchImportExportSurvey);
            wzchImportExportSurveyMapper.insertWzchImportExportSurvey(wzchImportExportSurvey);
        }else{
            fillWzchImportExportSurvey(wzchImportExportSurvey);
            wzchImportExportSurveyMapper.updateWzchImportExportSurvey(wzchImportExportSurvey);
            wzchImportExportSurveyCountryService.deleteBySurveyId(wzchImportExportSurvey.getId());
            wzchImportExportSurveyCustomsService.deleteBySurveyId(wzchImportExportSurvey.getId());
        }
        
        if (CollectionUtils.isNotEmpty(wzchImportExportSurvey.getWzchImportExportSurveyCountryList())) {
            wzchImportExportSurveyCountryService.batchInsert(wzchImportExportSurvey.getWzchImportExportSurveyCountryList());
        }
        if (CollectionUtils.isNotEmpty(wzchImportExportSurvey.getWzchImportExportSurveyCustomsList())) {
            wzchImportExportSurveyCustomsService.batchInsert(wzchImportExportSurvey.getWzchImportExportSurveyCustomsList());
        }
        if (ButtonMark.CONFIRM.equals(wzchImportExportSurvey.getButtonMark())) {
            // 插入确认状态
            String menuId = wzchImportExportSurvey.getMenuId();
            String stageIdentity = wzchImportExportSurvey.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        return wzchImportExportSurvey.getId();
    }

    private void fillWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey) {
        if(wzchImportExportSurvey.getTitle() == null)
            wzchImportExportSurvey.setTitle("");
        if(StringUtils.isBlank(wzchImportExportSurvey.getSurveyCode())){
            String code = genCodeService.getSetCode(CodeEnum.EQU_SURVEY);
            code += genCodeService.fillString(1, 2);
            wzchImportExportSurvey.setSurveyCode(code);
        }
        if(StringUtils.isBlank(wzchImportExportSurvey.getCreateUser())){
            wzchImportExportSurvey.setCreateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchImportExportSurvey.getCreateUserName())){
            wzchImportExportSurvey.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchImportExportSurvey.getCreateTime())){
            wzchImportExportSurvey.setCreateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchImportExportSurvey.getUpdateUser())){
            wzchImportExportSurvey.setUpdateUser(SecurityUtils.getUserId().toString());
        }
        if(StringUtils.isBlank(wzchImportExportSurvey.getUpdateUserName())){
            wzchImportExportSurvey.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
        }
        if(ObjectUtils.isEmpty(wzchImportExportSurvey.getUpdateTime())){
            wzchImportExportSurvey.setUpdateTime(DateUtils.getNowDate());
        }
        if(StringUtils.isBlank(wzchImportExportSurvey.getDelFlag())){
            wzchImportExportSurvey.setDelFlag("0");
        }
        if(CollectionUtils.isNotEmpty(wzchImportExportSurvey.getWzchImportExportSurveyCustomsList())){
            wzchImportExportSurvey.getWzchImportExportSurveyCustomsList().forEach(c->{
                if(c.getId()==null){
                    c.setId(IdWorker.createId());
                }
                if(c.getSurveyId()==null){
                    c.setSurveyId(wzchImportExportSurvey.getId());
                }
                if(StringUtils.isBlank(c.getCreateUser())){
                    c.setCreateUser(SecurityUtils.getUserId().toString());
                }
                if(StringUtils.isBlank(c.getCreateUserName())){
                    c.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if(ObjectUtils.isEmpty(c.getCreateTime())){
                    c.setCreateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(c.getUpdateUser())){
                    c.setUpdateUser(SecurityUtils.getUserId().toString());
                }
                if(StringUtils.isBlank(c.getUpdateUserName())){
                    c.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if(ObjectUtils.isEmpty(c.getUpdateTime())){
                    c.setUpdateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(c.getDelFlag())){
                    c.setDelFlag("0");
                }
            });
        }
        if(CollectionUtils.isNotEmpty(wzchImportExportSurvey.getWzchImportExportSurveyCountryList())){
            wzchImportExportSurvey.getWzchImportExportSurveyCountryList().forEach(y->{
                if(y.getId()==null){
                    y.setId(IdWorker.createId());
                }
                if(y.getSurveyId()==null){
                    y.setSurveyId(wzchImportExportSurvey.getId());
                }
                if(StringUtils.isBlank(y.getCreateUser())){
                    y.setCreateUser(SecurityUtils.getUserId().toString());
                }
                if(StringUtils.isBlank(y.getCreateUserName())){
                    y.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if(ObjectUtils.isEmpty(y.getCreateTime())){
                    y.setCreateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(y.getUpdateUser())){
                    y.setUpdateUser(SecurityUtils.getUserId().toString());
                }
                if(StringUtils.isBlank(y.getUpdateUserName())){
                    y.setUpdateUserName(SecurityUtils.getSysUser().getNickName());
                }
                if(ObjectUtils.isEmpty(y.getUpdateTime())){
                    y.setUpdateTime(DateUtils.getNowDate());
                }
                if(StringUtils.isBlank(y.getDelFlag())){
                    y.setDelFlag("0");
                }
            });
        }
    }

    private void checkWzchImportExportSurvey(WzchImportExportSurvey wzchImportExportSurvey){
//        if(wzchImportExportSurvey==null || wzchImportExportSurvey.getId()==null){
//            throw new BaseException("入参缺失");
//        }
    }
}
