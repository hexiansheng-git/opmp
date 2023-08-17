package com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.QqchTrafficCar;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.vo.QqchTrafficCarVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.mapper.QqchTrafficCarMapper;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.service.IQqchTrafficCarService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:12:57
 * @remark 
 */
@Service
public class QqchTrafficCarServiceImpl implements IQqchTrafficCarService{

    @Autowired
    private QqchTrafficCarMapper qqchTrafficCarMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    public QqchTrafficCar getQqchTrafficCar(QqchTrafficCar qqchTrafficCar) {
        return qqchTrafficCarMapper.getQqchTrafficCar(qqchTrafficCar);
    }

    /**
     *  列表接口
     *
     * @param qqchTrafficCar
     * @return
     */
    public QqchTrafficCarVo getQqchTrafficCarList(QqchTrafficCar qqchTrafficCar) {
        QqchTrafficCarVo vo = new QqchTrafficCarVo();
        BigDecimal version = qqchTrafficCar.getVersion();
        version = VersionUtil.getVersion("qqch_traffic_car", version);
        qqchTrafficCar.setVersion(version);
        List<QqchTrafficCar> qqchTrafficCarList = qqchTrafficCarMapper.getQqchTrafficCarList(qqchTrafficCar);
        List<QqchTrafficCar> treeList = TreeUtil.build(qqchTrafficCarList, 0l);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchTrafficCarList(treeList);
        return vo;

    }

    /**
     *  保存/确认/提交
     * @param qqchTrafficCarVo
     */
    @Override
    public void save(QqchTrafficCarVo qqchTrafficCarVo) {
        String buttonMark = qqchTrafficCarVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTrafficCarVo.getVersion();
        List<QqchTrafficCar> carList = qqchTrafficCarVo.getQqchTrafficCarList();

        this.insertQqchTrafficCarList(carList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchTrafficCarVo.getMenuId();
            String stageIdentity = qqchTrafficCarVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void insertQqchTrafficCarList(List<QqchTrafficCar> qqchTrafficCarList,BigDecimal version) {

        //删除旧数据
        QqchTrafficCar qqchTrafficCar = new QqchTrafficCar();
        qqchTrafficCar.setVersion(version);
        qqchTrafficCarMapper.deleteQqchTrafficCar(qqchTrafficCar);

        if (CollectionUtils.isEmpty(qqchTrafficCarList)) {
            return;
        }

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchTrafficCar> qqchTrafficCars = TreeUtil.treeToList(qqchTrafficCarList);
        for (QqchTrafficCar car : qqchTrafficCars) {
            car.setValid(valid);
            car.setVersion(version);
            car.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            car.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            car.setCreateTime(DateUtils.getNowDate());
            if(car.getPid()==null){
                car.setPid(0l);
            }
        }
        qqchTrafficCarMapper.insertQqchTrafficCarList(qqchTrafficCars);
    }


    @Transactional
    public int insertQqchTrafficCar(QqchTrafficCar qqchTrafficCar) {
        qqchTrafficCar.setId(IdWorker.createId());
        qqchTrafficCar.setCreateUser(SecurityUtils.getUserName());
        qqchTrafficCar.setCreateTime(DateUtils.getNowDate());
        return qqchTrafficCarMapper.insertQqchTrafficCar(qqchTrafficCar);
    }




    @Transactional
    public int updateQqchTrafficCar(QqchTrafficCar qqchTrafficCar) {
        qqchTrafficCar.setUpdateUser(SecurityUtils.getUserName());
        qqchTrafficCar.setUpdateTime(DateUtils.getNowDate());
        return qqchTrafficCarMapper.updateQqchTrafficCar(qqchTrafficCar);
    }

            @Transactional
        public int updateQqchTrafficCarList(List<QqchTrafficCar> qqchTrafficCarList) {
            for (QqchTrafficCar qqchTrafficCar : qqchTrafficCarList) {
                qqchTrafficCar.setUpdateUser(SecurityUtils.getUserName());
                qqchTrafficCar.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTrafficCarMapper.updateQqchTrafficCarList(qqchTrafficCarList);
        }
    
    @Transactional
    public int deleteQqchTrafficCar(QqchTrafficCar qqchTrafficCar) {
        qqchTrafficCar.setUpdateUser(SecurityUtils.getUserName());
        qqchTrafficCar.setUpdateTime(DateUtils.getNowDate());
        return qqchTrafficCarMapper.deleteQqchTrafficCar(qqchTrafficCar);
    }

            @Transactional
        public int deleteQqchTrafficCarByPks(List<Long> qqchTrafficCarPkList) {
            return qqchTrafficCarMapper.deleteQqchTrafficCarByPks(qqchTrafficCarPkList);
        }


}
