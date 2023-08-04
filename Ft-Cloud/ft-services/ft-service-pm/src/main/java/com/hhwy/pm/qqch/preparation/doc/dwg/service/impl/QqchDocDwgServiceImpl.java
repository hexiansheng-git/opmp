package com.hhwy.pm.qqch.preparation.doc.dwg.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwgVo;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.doc.dwg.mapper.QqchDocDwgMapper;
import com.hhwy.pm.qqch.preparation.doc.dwg.service.IQqchDocDwgService;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark 
 */
@Service
public class QqchDocDwgServiceImpl implements IQqchDocDwgService{

    @Autowired
    private QqchDocDwgMapper qqchDocDwgMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

                                                                                                                                                                                                                                                                                                                                        
    public QqchDocDwg getQqchDocDwg(QqchDocDwg qqchDocDwg) {
        return qqchDocDwgMapper.getQqchDocDwg(qqchDocDwg);
    }

    public List<QqchDocDwg> getQqchDocDwgList(QqchDocDwg qqchDocDwg) {
        return qqchDocDwgMapper.getQqchDocDwgList(qqchDocDwg);
    }

    @Transactional
    public int insertQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setId(IdWorker.createId());
        qqchDocDwg.setCreateUser(SecurityUtils.getUserName());
        qqchDocDwg.setCreateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.insertQqchDocDwg(qqchDocDwg);
    }

    @Transactional
    public int insertQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList) {
        for (QqchDocDwg qqchDocDwg : qqchDocDwgList) {
            qqchDocDwg.setId(IdWorker.createId());
            qqchDocDwg.setCreateUser(SecurityUtils.getUserName());
            qqchDocDwg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDocDwgMapper.insertQqchDocDwgList(qqchDocDwgList);
    }

    @Transactional
    public int updateQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
        qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.updateQqchDocDwg(qqchDocDwg);
    }

            @Transactional
        public int updateQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList) {
            for (QqchDocDwg qqchDocDwg : qqchDocDwgList) {
                qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
                qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDocDwgMapper.updateQqchDocDwgList(qqchDocDwgList);
        }
    
    @Transactional
    public int deleteQqchDocDwg(QqchDocDwg qqchDocDwg) {
        qqchDocDwg.setUpdateUser(SecurityUtils.getUserName());
        qqchDocDwg.setUpdateTime(DateUtils.getNowDate());
        return qqchDocDwgMapper.deleteQqchDocDwg(qqchDocDwg);
    }

            @Transactional
        public int deleteQqchDocDwgByPks(List<Long> qqchDocDwgPkList) {
            return qqchDocDwgMapper.deleteQqchDocDwgByPks(qqchDocDwgPkList);
        }

    @Override
    public QqchDocDwgVo getQqchDocDwgVo(BigDecimal version) {
        version = VersionUtil.getVersion("qqch_doc_tech", version);

        QqchDocDwg qqchOrganizationList = new QqchDocDwg();
        qqchOrganizationList.setVersion(version);
        List<QqchDocDwg> qqchOrganizationListList = qqchDocDwgMapper.getQqchDocDwgList(qqchOrganizationList);

        QqchDocDwgVo organizationListVo = new QqchDocDwgVo();
        organizationListVo.setVersion(version);
        organizationListVo.setDataList(qqchOrganizationListList);
        //查询阶段
        organizationListVo.setStageIdentity(qqchReviewService.getStage());
        organizationListVo.setCreateUser(SecurityUtils.getSysUser().getUserId()+"");
        organizationListVo.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        return organizationListVo;
    }

    @Override
    public int insertQqchDocDwgVo(QqchDocDwgVo qqchDocDwgVo) {
        List<QqchDocDwg> dataList = qqchDocDwgVo.getDataList();
        if (ObjectNullUtil.isEmpty(dataList)) {
            return 1;
        } else {
            //校验数据必填
            if("1".equals(qqchDocDwgVo.getButtonMark())||"2".equals(qqchDocDwgVo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(dataList, ValidationGroups.Save.class);
            }
        }
        String valid = "";//是否有效
        //判断是确认还是保存
        if("0".equals(qqchDocDwgVo.getButtonMark())){//保存（判断是业务保存还是变更保存）
            if(qqchDocDwgVo.getVersion().intValue()==new BigDecimal(InitVersionConstant.INIT_VERSION).intValue()){//业务保存
                valid = "1";
            }else{//变更保存
                valid = "0";
            }
        }else if("1".equals(qqchDocDwgVo.getButtonMark())){//确认
            valid = "1";
            //插入确认状态
            String menuId = qqchDocDwgVo.getMenuId();
            String stageIdentity = qqchDocDwgVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }else if("2".equals(qqchDocDwgVo.getButtonMark())){//提交
            valid = "0";
        }else{
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"标识不符合规范");
        }
        String finalValid = valid;

        dataList.stream().forEach(item->{
            item.setId(IdWorker.createId());
            item.setVersion(qqchDocDwgVo.getVersion());
            item.setValid(finalValid);
            item.setCreateUser(SecurityUtils.getSysUser().getUserId()+"");
            item.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            item.setCreateTime(DateUtils.getNowDate());
        });
        //先删除旧的 再添加新的
        QqchDocDwg temp = new QqchDocDwg();
        temp.setVersion(qqchDocDwgVo.getVersion());
        qqchDocDwgMapper.deleteQqchDocDwg(temp);

        qqchDocDwgMapper.insertQqchDocDwgList(dataList);
        return 1;
    }
}
