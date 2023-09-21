package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Service
public class QqchComparisonSchemeServiceImpl implements IQqchComparisonSchemeService {

    @Autowired
    private QqchComparisonSchemeMapper qqchComparisonSchemeMapper;

    @Autowired
    private QqchComparisonSchemeHeaderServiceImpl qqchComparisonSchemeHeaderService;

    @Autowired
    private QqchComparisonSchemeHeaderMapper qqchComparisonSchemeHeaderMapper;

    @Autowired
    private QqchComparisonSchemeContentServiceImpl qqchComparisonSchemeContentService;

    @Autowired
    private QqchComparisonSchemeContentMapper qqchComparisonSchemeContentMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 获取方案集合
     * @return
     * @param version
     */
    @Override
    @Transactional
    public QqchComparisonSchemeVo getQqchComparisonSchemeVo(BigDecimal version) {
        QqchComparisonSchemeVo qqchComparisonSchemeVo = new QqchComparisonSchemeVo();

        version = VersionUtil.getVersion("qqch_comparison_scheme",version);

        //获取方案集合
        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeMapper.getQqchComparisonSchemeList(version);

        //获取所有表头
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeaderList(version);

        //获取所有单元格
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContentList(version);


        if(CollectionUtils.isEmpty(qqchComparisonSchemeList)){
            //当前没有方案，需要初始化
            QqchComparisonScheme init = this.init();
            qqchComparisonSchemeList.add(init);
        }else {
            for (QqchComparisonScheme scheme : qqchComparisonSchemeList) {
                Long id = scheme.getId();
                //行数
                Integer rowCount = scheme.getRowCount();

                //组装表头
                List<QqchComparisonSchemeHeader> headerList = new ArrayList<>();
                for (QqchComparisonSchemeHeader comparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
                    Long schemeId = comparisonSchemeHeader.getSchemeId();
                    if(id.equals(schemeId)){
                        headerList.add(comparisonSchemeHeader);
                    }
                }
                scheme.setHeaderList(headerList);

                //组装每一行
                List<List<QqchComparisonSchemeContent>> contentListList = new ArrayList<>();
                for (int i = 1; i <= rowCount; i++) {
                    List<QqchComparisonSchemeContent> row = new ArrayList<>();
                    for (QqchComparisonSchemeContent content : qqchComparisonSchemeContentList) {
                        Long schemeId = content.getSchemeId();
                        /*行号*/
                        int rownum = content.getRownum();
                        if(schemeId.equals(id) && rownum == i){
                            row.add(content);
                        }
                    }
                    contentListList.add(row);
                }
                scheme.setContentListList(contentListList);
            }
        }

        qqchComparisonSchemeVo.setVersion(version);
        qqchComparisonSchemeVo.setStageIdentity(qqchReviewService.getStage());
        qqchComparisonSchemeVo.setQqchComparisonSchemeList(qqchComparisonSchemeList);
        return qqchComparisonSchemeVo;
    }


    /**
     * 初始化数据
     */
    @Override
    @Transactional
    public QqchComparisonScheme init(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/2_5_5_scheme.json");
            String json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            return JSONObject.parseObject(json, QqchComparisonScheme.class);
        }catch (IOException e){
            throw new RuntimeException("初始化数据失败！");
        }
    }

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        //删除旧数据
        this.deleteOldDataByVersion(qqchComparisonSchemeVo.getVersion());

        //插入新数据
        this.insertQqchComparisonSchemeList(qqchComparisonSchemeVo.getQqchComparisonSchemeList(),qqchComparisonSchemeVo.getVersion());
    }

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        this.save(qqchComparisonSchemeVo);

        String buttonMark = qqchComparisonSchemeVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchComparisonSchemeVo.getMenuId();
            String stageIdentity = qqchComparisonSchemeVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入方案
     * @param qqchComparisonSchemeList
     * @return
     */
    @Transactional
    public void insertQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchComparisonSchemeList)){
            return;
        }

        for (QqchComparisonScheme scheme : qqchComparisonSchemeList) {
            Long schemeId = IdWorker.createId();

            //表头
            List<QqchComparisonSchemeHeader> headerList = scheme.getHeaderList();
            //插入表头
            qqchComparisonSchemeHeaderService.insertQqchComparisonSchemeHeaderList(headerList, schemeId, version);

            //每一行
            List<List<QqchComparisonSchemeContent>> contentListList = scheme.getContentListList();
            //插入单元格
            int rownum = 1;
            for (List<QqchComparisonSchemeContent> contentList : contentListList) {
                qqchComparisonSchemeContentService.insertQqchComparisonSchemeContentList(contentList,schemeId,rownum,version);
                rownum++;
            }

            scheme.setId(schemeId);
            scheme.setVersion(version);
            scheme.setRowCount(contentListList.size());
            if(version.compareTo(BigDecimal.ONE) == 0){
                scheme.setValid(Valid.YES);
            }
            scheme.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            scheme.setCreateUserName(SecurityUtils.getUserName());
            scheme.setCreateTime(DateUtils.getNowDate());
        }
        //插入方案
        qqchComparisonSchemeMapper.insertQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    /**
     * 根据版本删除旧数据
     * @param version 版本
     */
    @Transactional
    public void deleteOldDataByVersion(BigDecimal version) {
        //删除表头
        QqchComparisonSchemeHeader qqchComparisonSchemeHeader = new QqchComparisonSchemeHeader();
        qqchComparisonSchemeHeader.setVersion(version);
        qqchComparisonSchemeHeader.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);

        //删除单元格
        QqchComparisonSchemeContent qqchComparisonSchemeContent = new QqchComparisonSchemeContent();
        qqchComparisonSchemeContent.setVersion(version);
        qqchComparisonSchemeContent.setDelUser(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeContentMapper.deleteQqchComparisonSchemeContent(qqchComparisonSchemeContent);

        //删除方案
        QqchComparisonScheme qqchComparisonScheme = new QqchComparisonScheme();
        qqchComparisonScheme.setVersion(version);
        qqchComparisonScheme.setDelUser(SecurityUtils.getUserName());
        qqchComparisonScheme.setDelTime(DateUtils.getNowDate());
        qqchComparisonSchemeMapper.deleteQqchComparisonScheme(qqchComparisonScheme);
    }
}
