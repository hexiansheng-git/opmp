package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrangeVo;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlaningArrangeMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlaningArrangeService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark 
 */
@Service
public class QqchWorkPlaningArrangeServiceImpl implements IQqchWorkPlaningArrangeService {

    @Autowired
    private QqchWorkPlaningArrangeMapper qqchWorkPlaningArrangeMapper;
    @Autowired
    private CommonMapper commonMapper;

                                                                                                                                                                                                                                                                                                                
    public QqchWorkPlaningArrange getQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    public List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeList(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrangeList(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setId(IdWorker.createId());
        qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrangeList(QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeVo) {
        //判断是确认还是保存
        if("0".equals(qqchWorkPlaningArrangeVo.getSubmitFlag())){
            //先删除旧的 再添加新的
            QqchWorkPlaningArrange temp = new QqchWorkPlaningArrange();
            temp.setVersion(qqchWorkPlaningArrangeVo.getVersion());
            qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrange(temp);
            String valid = "1";
            if(!InitVersionConstant.INIT_VERSION.equals(String.valueOf(qqchWorkPlaningArrangeVo.getVersion()))){
                valid = "0";
            }
            List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeVo.getDataList();
            if(!ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
                for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                    qqchWorkPlaningArrange.setId(IdWorker.createId());
                    qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlaningArrange.setValid(valid);
                    qqchWorkPlaningArrange.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlaningArrangeVo.getVersion());
                }
                qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
                return 1;
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"便道部署不可为空");
            }
        }else{
            //确认
            //新增一条确认记录

            return 1;
        }


    }

    @Transactional
    public int updateQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int updateQqchWorkPlaningArrangeList(List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList) {
            for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
        }
    
    @Transactional
    public int deleteQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int deleteQqchWorkPlaningArrangeByPks(List<Long> qqchWorkPlaningArrangePkList) {
            return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrangeByPks(qqchWorkPlaningArrangePkList);
        }

    @Override
    public List<QqchWorkPlaningArrange> getMaxVVData(QqchWorkPlaningArrange arrangeVo) {
        arrangeVo.setValid("1");
        BigDecimal version = commonMapper.selectMaxVersion("qqch_work_planing_arrange");
        arrangeVo.setVersion(version);
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = getQqchWorkPlaningArrangeList(arrangeVo);
        return qqchWorkPlaningArrangeList;
    }

    @Override
    public List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeListHistory(QqchWorkPlaningArrange arrangeVo) {
        return null;
    }
}
