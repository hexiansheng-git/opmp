package com.hhwy.pm.qqch.preparation.doc.techmae.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMaeV0;
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
import com.hhwy.pm.qqch.preparation.doc.techmae.mapper.QqchDocTechMaeMapper;
import com.hhwy.pm.qqch.preparation.doc.techmae.service.IQqchDocTechMaeService;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark
 */
@Service
public class QqchDocTechMaeServiceImpl implements IQqchDocTechMaeService {

    @Autowired
    private QqchDocTechMaeMapper qqchDocTechMaeMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchDocTechMae getQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        return qqchDocTechMaeMapper.getQqchDocTechMae(qqchDocTechMae);
    }

    public List<QqchDocTechMae> getQqchDocTechMaeList(QqchDocTechMae qqchDocTechMae) {
        return qqchDocTechMaeMapper.getQqchDocTechMaeList(qqchDocTechMae);
    }

    @Transactional
    public int insertQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setId(IdWorker.createId());
        qqchDocTechMae.setCreateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setCreateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.insertQqchDocTechMae(qqchDocTechMae);
    }

    @Transactional
    public int insertQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList) {
        for (QqchDocTechMae qqchDocTechMae : qqchDocTechMaeList) {
            qqchDocTechMae.setId(IdWorker.createId());
            qqchDocTechMae.setCreateUser(SecurityUtils.getUserName());
            qqchDocTechMae.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMaeMapper.insertQqchDocTechMaeList(qqchDocTechMaeList);
    }

    @Transactional
    public int updateQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.updateQqchDocTechMae(qqchDocTechMae);
    }

    @Transactional
    public int updateQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList) {
        for (QqchDocTechMae qqchDocTechMae : qqchDocTechMaeList) {
            qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
            qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDocTechMaeMapper.updateQqchDocTechMaeList(qqchDocTechMaeList);
    }

    @Transactional
    public int deleteQqchDocTechMae(QqchDocTechMae qqchDocTechMae) {
        qqchDocTechMae.setUpdateUser(SecurityUtils.getUserName());
        qqchDocTechMae.setUpdateTime(DateUtils.getNowDate());
        return qqchDocTechMaeMapper.deleteQqchDocTechMae(qqchDocTechMae);
    }

    @Transactional
    public int deleteQqchDocTechMaeByPks(List<Long> qqchDocTechMaePkList) {
        return qqchDocTechMaeMapper.deleteQqchDocTechMaeByPks(qqchDocTechMaePkList);
    }

    @Override
    public QqchDocTechMaeV0 geteQqchDocTechMaeVo(BigDecimal version) {
        version = VersionUtil.getVersion("qqch_doc_tech_mae", version);

        QqchDocTechMae qqchOrganizationList = new QqchDocTechMae();
        qqchOrganizationList.setVersion(version);
        List<QqchDocTechMae> qqchOrganizationListList = qqchDocTechMaeMapper.getQqchDocTechMaeList(qqchOrganizationList);
        qqchOrganizationListList = TreeUtil.build(qqchOrganizationListList, null);
        QqchDocTechMaeV0 organizationListVo = new QqchDocTechMaeV0();
        organizationListVo.setVersion(version);
        organizationListVo.setDataList(qqchOrganizationListList);
        //查询阶段
        organizationListVo.setStageIdentity(qqchReviewService.getStage());
        return organizationListVo;
    }

    @Override
    public int inserteQqchDocTechMaeVo(QqchDocTechMaeV0 qqchDocTechVo) {
        List<QqchDocTechMae> dataList = qqchDocTechVo.getDataList();
        List<QqchDocTechMae> qqchDocTechMaes = TreeUtil.treeToList(dataList);
        if (ObjectNullUtil.isEmpty(dataList)) {
            //先删除旧的 再添加新的
            QqchDocTechMae temp = new QqchDocTechMae();
            temp.setVersion(qqchDocTechVo.getVersion());
            qqchDocTechMaeMapper.deleteQqchDocTechMae(temp);
            return 1;
        } else {
            //校验数据必填
            if("1".equals(qqchDocTechVo.getButtonMark())||"2".equals(qqchDocTechVo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchDocTechMaes, ValidationGroups.Save.class);
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

        qqchDocTechMaes.stream().forEach(item->{
            item.setVersion(qqchDocTechVo.getVersion());
            item.setValid(finalValid);
            item.setCreateUser(SecurityUtils.getSysUser().getUserId()+"");
            item.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            item.setCreateTime(DateUtils.getNowDate());
        });
        //先删除旧的 再添加新的
        QqchDocTechMae temp = new QqchDocTechMae();
        temp.setVersion(qqchDocTechVo.getVersion());
        qqchDocTechMaeMapper.deleteQqchDocTechMae(temp);

        qqchDocTechMaeMapper.insertQqchDocTechMaeList(qqchDocTechMaes);
        return 1;
    }
}
