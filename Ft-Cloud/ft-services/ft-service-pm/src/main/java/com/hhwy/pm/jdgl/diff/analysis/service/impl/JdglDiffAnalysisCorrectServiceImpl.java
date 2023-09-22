package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisCorrectMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
import com.hhwy.pm.qqch.sgch.sche.vo.ScheFactorsVO;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:13
 * @remark
 */
@Service
public class JdglDiffAnalysisCorrectServiceImpl implements IJdglDiffAnalysisCorrectService {

    @Autowired
    private JdglDiffAnalysisCorrectMapper jdglDiffAnalysisCorrectMapper;

    @Autowired
    private IQqchScheService qqchScheService;

    @Autowired
    private IJdglDiffAnalysisService iJdglDiffAnalysisService;


    public JdglDiffAnalysisCorrect getJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {
        return jdglDiffAnalysisCorrectMapper.getJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrect);
    }

    public List<JdglDiffAnalysisCorrect> getJdglDiffAnalysisCorrectList(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {

        List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectMapper.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrect);
        List<JdglDiffAnalysisCorrect> returnList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jdglDiffAnalysisCorrectList)) {
            returnList = jdglDiffAnalysisCorrectList.stream().filter(vo -> StringUtils.isEmpty(vo.getSecondType())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(returnList)) {
                return returnList;
            }
            for (JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect1 : returnList) {
                List<JdglDiffAnalysisCorrect> collect = jdglDiffAnalysisCorrectList.stream().filter(vo -> vo.getFirstType().equals(jdglDiffAnalysisCorrect1.getFirstType()) && StringUtils.isNotEmpty(vo.getSecondType())).collect(Collectors.toList());
                jdglDiffAnalysisCorrect1.setChildren(collect);
            }
        }
        return returnList;
    }

    public Map<String, List<JdglDiffAnalysisCorrect>> getJdglDiffAnalysisCorrectMapList(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {
        Map<String, List<JdglDiffAnalysisCorrect>> returnMapList = new HashMap<>();
        List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectMapper.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrect);
        if(!CollectionUtils.isEmpty(jdglDiffAnalysisCorrectList)) {
            List<JdglDiffAnalysisCorrect> headerVos = jdglDiffAnalysisCorrectList.stream().filter(vo -> StringUtils.isEmpty(vo.getSecondType())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(headerVos)) {
                return returnMapList;
            }
            returnMapList.put("headerList", headerVos);

            for (JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect1 : headerVos) {
                List<JdglDiffAnalysisCorrect> collect = jdglDiffAnalysisCorrectList.stream().filter(vo -> vo.getFirstType().equals(jdglDiffAnalysisCorrect1.getFirstType())).collect(Collectors.toList());
                returnMapList.put(jdglDiffAnalysisCorrect1.getFirstType(), collect);
            }

        }
        return returnMapList;
    }

    @Transactional
    public int insertJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {
        jdglDiffAnalysisCorrect.setId(IdWorker.createId());
        jdglDiffAnalysisCorrect.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisCorrect.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisCorrectMapper.insertJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrect);
    }

    @Transactional
    public int insertJdglDiffAnalysisCorrectList(List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList) {
        if(CollectionUtils.isEmpty(jdglDiffAnalysisCorrectList)) {
            return 0;
        }
        BigDecimal correctGrade = new BigDecimal(0);
        for (JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect : jdglDiffAnalysisCorrectList) {
            if("1".equals(jdglDiffAnalysisCorrect.getIsSelect()) && jdglDiffAnalysisCorrect.getGrade() != null) {
                correctGrade = correctGrade.add(jdglDiffAnalysisCorrect.getGrade());
            }
            jdglDiffAnalysisCorrect.setId(IdWorker.createId());
            jdglDiffAnalysisCorrect.setCreateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisCorrect.setCreateTime(DateUtils.getNowDate());
        }
        iJdglDiffAnalysisService.updateGrage("correctGrade", jdglDiffAnalysisCorrectList.get(0).getDiffAnalysisId(), correctGrade);
        return jdglDiffAnalysisCorrectMapper.insertJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectList);
    }

    @Transactional
    public int updateJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {
        jdglDiffAnalysisCorrect.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisCorrect.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisCorrectMapper.updateJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrect);
    }

    @Transactional
    public int updateJdglDiffAnalysisCorrectList(List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList) {
        if(CollectionUtils.isEmpty(jdglDiffAnalysisCorrectList)) {
            return 0;
        }
        BigDecimal correctGrade = new BigDecimal(0);
        for (JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect : jdglDiffAnalysisCorrectList) {
            if("1".equals(jdglDiffAnalysisCorrect.getIsSelect()) && jdglDiffAnalysisCorrect.getGrade() != null) {
                correctGrade = correctGrade.add(jdglDiffAnalysisCorrect.getGrade());
            }
            jdglDiffAnalysisCorrect.setUpdateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisCorrect.setUpdateTime(DateUtils.getNowDate());
        }
        iJdglDiffAnalysisService.updateGrage("correctGrade", jdglDiffAnalysisCorrectList.get(0).getDiffAnalysisId(), correctGrade);
        return jdglDiffAnalysisCorrectMapper.updateJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectList);
    }

    @Transactional
    public int deleteJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect) {
        jdglDiffAnalysisCorrect.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisCorrect.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisCorrectMapper.deleteJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrect);
    }

    @Transactional
    public int deleteJdglDiffAnalysisCorrectByPks(List<Long> jdglDiffAnalysisCorrectPkList) {
        return jdglDiffAnalysisCorrectMapper.deleteJdglDiffAnalysisCorrectByPks(jdglDiffAnalysisCorrectPkList);
    }

    /**
     * 从进度差异化管控策划获取修正表单数据
     * @param jdglDiffAnalysisCorrectParam
     * @return
     */
    @Override
    public List<JdglDiffAnalysisCorrect> getInitDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {

//        Map<String, List<JdglDiffAnalysisCorrect>> returnMapList = new HashMap<>();

        List<JdglDiffAnalysisCorrect> headerVos = new ArrayList<JdglDiffAnalysisCorrect>();

        QqchScheDTO dto = new QqchScheDTO();

        // 调取获取进度差异化管控策划列表接口
        QqchScheDTO list = qqchScheService.list(dto);

        if(list == null) {
            return null;
        }

//        List<List<QqchScheFactors>> factorsVOList = list.getFactorsVOList();
        ScheFactorsVO scheFactorsVO = list.getScheFactorsVO();

        if(scheFactorsVO == null) {
            return null;
        }

        List<ScheFactorsVO.ScheFactorsHeader> headerList = scheFactorsVO.getHeaderList();
        List<List<QqchScheFactors>> factorsVOList = scheFactorsVO.getFactorsVOList();

        if(CollectionUtils.isEmpty(headerList) || CollectionUtils.isEmpty(factorsVOList)) {
            return null;
        }

        for (ScheFactorsVO.ScheFactorsHeader scheFactorsHeader : headerList) {
            List<JdglDiffAnalysisCorrect> dataVos = new ArrayList<JdglDiffAnalysisCorrect>();
            String headerName = scheFactorsHeader.getHeaderName();
            String headerValue = scheFactorsHeader.getHeaderValue();
            JdglDiffAnalysisCorrect vo = new JdglDiffAnalysisCorrect();
            vo.setFirstType(headerValue);
            vo.setFirstTypeValue(headerName);
            for (int i = 0; i < factorsVOList.size(); i++) {
                List<QqchScheFactors> qqchScheFactors = factorsVOList.get(i);
                if(!CollectionUtils.isEmpty(qqchScheFactors)) {
                    for (QqchScheFactors qqchScheFactors1 : qqchScheFactors) {
                        if(headerValue.equals(qqchScheFactors1.getFactorsType())) {
                            JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
                            jdglDiffAnalysisCorrect.setSort(i);
                            jdglDiffAnalysisCorrect.setFirstType(headerValue);
                            jdglDiffAnalysisCorrect.setSecondType(qqchScheFactors1.getFactorsDesc());
                            jdglDiffAnalysisCorrect.setGrade(qqchScheFactors1.getScore());
                            jdglDiffAnalysisCorrect.setIsSelect("0");
                            jdglDiffAnalysisCorrect.setFirstTypeValue(headerName);
                            jdglDiffAnalysisCorrect.setDiffAnalysisId(jdglDiffAnalysisCorrectParam.getDiffAnalysisId());
                            dataVos.add(jdglDiffAnalysisCorrect);
                        }
                    }
                }
            }
            vo.setChildren(dataVos);
            headerVos.add(vo);
//            returnMapList.put(headerValue,dataVos);
        }

//        returnMapList.put("headerList", headerVos);

        return headerVos;

    }
}
