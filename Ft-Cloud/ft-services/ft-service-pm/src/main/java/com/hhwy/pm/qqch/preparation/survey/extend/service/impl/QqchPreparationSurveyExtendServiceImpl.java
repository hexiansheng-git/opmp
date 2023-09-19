package com.hhwy.pm.qqch.preparation.survey.extend.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import com.hhwy.pm.qqch.preparation.survey.extend.mapper.QqchPreparationSurveyExtendMapper;
import com.hhwy.pm.qqch.preparation.survey.extend.service.IQqchPreparationSurveyExtendService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
@Service
public class QqchPreparationSurveyExtendServiceImpl implements IQqchPreparationSurveyExtendService {

    @Autowired
    private QqchPreparationSurveyExtendMapper qqchPreparationSurveyExtendMapper;


    /**
     * 根据页面标识和版本获取扩展数据
     * @return
     */
    public QqchPreparationSurveyExtend getQqchPreparationSurveyExtend(String moduleIdentity, BigDecimal version){
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
        qqchPreparationSurveyExtend.setModuleIdentity(moduleIdentity);
        qqchPreparationSurveyExtend.setVersion(version);
        return qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    /**
     * 根据模块名和版本删除附件信息
     * @param moduleIdentity
     * @param version
     */
    public void deleteBy(String moduleIdentity, BigDecimal version){
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
        qqchPreparationSurveyExtend.setModuleIdentity(moduleIdentity);
        qqchPreparationSurveyExtend.setVersion(version);
        qqchPreparationSurveyExtendMapper.deleteQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }


    /**
     * 维护附件
     * @param moduleIdentity
     * @param version
     * @param fileGroupId
     */
    @Transactional
    public void preserveFile(String moduleIdentity, BigDecimal version, String fileGroupId){
        if(StringUtils.isBlank(fileGroupId)){
            return;
        }
        //查询当前是否已插入附件数据
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
        qqchPreparationSurveyExtend.setModuleIdentity(moduleIdentity);
        qqchPreparationSurveyExtend.setVersion(version);
        qqchPreparationSurveyExtend.setFileGroupId(fileGroupId);
        QqchPreparationSurveyExtend extend = qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);

        if(extend == null){
            qqchPreparationSurveyExtend.setValid(Valid.YES);
            qqchPreparationSurveyExtend.setValid(Valid.YES);
            this.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }
    }

    /**
     * 获取扩展数据
     * @return
     */
    @Override
    public QqchPreparationSurveyExtend getQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        return qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    public List<QqchPreparationSurveyExtend> getQqchPreparationSurveyExtendList(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        return qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtendList(qqchPreparationSurveyExtend);
    }

    /**
     * 插入数据
     * @param qqchPreparationSurveyExtend
     * @return
     */
    @Transactional
    public int insertQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setId(IdWorker.createId());
        qqchPreparationSurveyExtend.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchPreparationSurveyExtend.setCreateUserName(SecurityUtils.getUserName());
        qqchPreparationSurveyExtend.setCreateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int insertQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList) {
        for (QqchPreparationSurveyExtend qqchPreparationSurveyExtend : qqchPreparationSurveyExtendList) {
            qqchPreparationSurveyExtend.setId(IdWorker.createId());
            qqchPreparationSurveyExtend.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchPreparationSurveyExtend.setCreateUserName(SecurityUtils.getUserName());
            qqchPreparationSurveyExtend.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPreparationSurveyExtendMapper.insertQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendList);
    }

    /**
     * 修改数据
     * @param qqchPreparationSurveyExtend
     * @return
     */
    @Transactional
    public int updateQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.updateQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int updateQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList) {
        for (QqchPreparationSurveyExtend qqchPreparationSurveyExtend : qqchPreparationSurveyExtendList) {
            qqchPreparationSurveyExtend.setUpdateUser(SecurityUtils.getUserName());
            qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPreparationSurveyExtendMapper.updateQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendList);
    }

    @Transactional
    public int deleteQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.deleteQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int deleteQqchPreparationSurveyExtendByPks(List<Long> qqchPreparationSurveyExtendPkList) {
        return qqchPreparationSurveyExtendMapper.deleteQqchPreparationSurveyExtendByPks(qqchPreparationSurveyExtendPkList);
    }
}
