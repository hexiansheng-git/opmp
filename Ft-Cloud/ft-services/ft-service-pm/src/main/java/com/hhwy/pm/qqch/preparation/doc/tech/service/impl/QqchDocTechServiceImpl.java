package com.hhwy.pm.qqch.preparation.doc.tech.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.doc.tech.mapper.QqchDocTechMapper;
import com.hhwy.pm.qqch.preparation.doc.tech.service.IQqchDocTechService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark
 */
@Service
public class QqchDocTechServiceImpl implements IQqchDocTechService {

    @Autowired
    private QqchDocTechMapper qqchDocTechMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchDocTech getQqchDocTech(QqchDocTech qqchDocTech) {
        return qqchDocTechMapper.getQqchDocTech(qqchDocTech);
    }

    public List<QqchDocTech> getQqchDocTechList(QqchDocTech qqchDocTech) {
        return qqchDocTechMapper.getQqchDocTechList(qqchDocTech);
    }

    /**
     * 获取变更程序策划
     *
     * @param version
     * @return
     */
    public QqchDocTechVo getQqchDocTechListVo(BigDecimal version) {
        version = VersionUtil.getVersion("qqch_doc_tech", version);

        QqchDocTech qqchOrganizationList = new QqchDocTech();
        qqchOrganizationList.setVersion(version);
        List<QqchDocTech> qqchOrganizationListList = qqchDocTechMapper.getQqchDocTechList(qqchOrganizationList);

        QqchDocTechVo organizationListVo = new QqchDocTechVo();
        organizationListVo.setVersion(version);
        organizationListVo.setDataList(qqchOrganizationListList);
        //查询阶段
        organizationListVo.setStageIdentity(qqchReviewService.getStage());
        organizationListVo.setCreateUser(SecurityUtils.getSysUser().getUserId()+"");
        organizationListVo.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        return organizationListVo;
    }

    @Transactional
    public int insertQqchDocTechListVo(QqchDocTechVo qqchDocTechVo) {
        List<QqchDocTech> dataList = qqchDocTechVo.getDataList();
        if (ObjectNullUtil.isEmpty(dataList)) {
            return 1;
        } else {
            //校验数据必填
            if("1".equals(qqchDocTechVo.getButtonMark())||"2".equals(qqchDocTechVo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(dataList, ValidationGroups.Save.class);
            }
        }
        String valid = "";//是否有效
        //判断是确认还是保存
        if("0".equals(qqchDocTechVo.getButtonMark())){//保存（判断是业务保存还是变更保存）
            if(qqchDocTechVo.getVersion().intValue()==new BigDecimal(InitVersionConstant.INIT_VERSION).intValue()){//业务保存
                valid = "1";
            }else{//变更保存
                valid = "0";
            }
        }else if("1".equals(qqchDocTechVo.getButtonMark())){//确认
            valid = "1";
            //插入确认状态
            String menuId = qqchDocTechVo.getMenuId();
            String stageIdentity = qqchDocTechVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }else if("2".equals(qqchDocTechVo.getButtonMark())){//提交
            valid = "0";
        }else{
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"标识不符合规范");
        }
        String finalValid = valid;

        dataList.stream().forEach(item->{
            item.setId(IdWorker.createId());
            item.setVersion(qqchDocTechVo.getVersion());
            item.setValid(finalValid);
            item.setCreateUser(SecurityUtils.getSysUser().getUserId()+"");
            item.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            item.setCreateTime(DateUtils.getNowDate());
        });
        //先删除旧的 再添加新的
        QqchDocTech temp = new QqchDocTech();
        temp.setVersion(qqchDocTechVo.getVersion());
        qqchDocTechMapper.deleteQqchDocTech(temp);

        qqchDocTechMapper.insertQqchDocTechList(dataList);
        return 1;
    }

    @Transactional
    public int insertQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setId(IdWorker.createId());
        qqchDocTech.setCreateUser(SecurityUtils.getUserName());
        qqchDocTech.setCreateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.insertQqchDocTech(qqchDocTech);
    }

    @Transactional
    public int insertQqchDocTechList(List<QqchDocTech> qqchDocTechList) {
        for (QqchDocTech qqchDocTech : qqchDocTechList) {
            qqchDocTech.setId(IdWorker.createId());
            qqchDocTech.setCreateUser(SecurityUtils.getUserName());
            qqchDocTech.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMapper.insertQqchDocTechList(qqchDocTechList);
    }

    @Transactional
    public int updateQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTech.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.updateQqchDocTech(qqchDocTech);
    }

    @Transactional
    public int updateQqchDocTechList(List<QqchDocTech> qqchDocTechList) {
        for (QqchDocTech qqchDocTech : qqchDocTechList) {
            qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
            qqchDocTech.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMapper.updateQqchDocTechList(qqchDocTechList);
    }

    @Transactional
    public int deleteQqchDocTech(QqchDocTech qqchDocTech) {
        qqchDocTech.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTech.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMapper.deleteQqchDocTech(qqchDocTech);
    }

    @Transactional
    public int deleteQqchDocTechByPks(List<Long> qqchDocTechPkList) {
        return qqchDocTechMapper.deleteQqchDocTechByPks(qqchDocTechPkList);
    }

}
