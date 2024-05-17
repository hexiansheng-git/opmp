package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.poi.ss.formula.functions.MinaMaxa;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.mapper.KcsjDesignOptimizeItemMapper;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.IKcsjDesignOptimizeItemService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.Assert;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark
 */
@Service
public class KcsjDesignOptimizeItemServiceImpl implements IKcsjDesignOptimizeItemService {

    @Autowired
    private KcsjDesignOptimizeItemMapper kcsjDesignOptimizeItemMapper;


    public KcsjDesignOptimizeItem getKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.getKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    public List<KcsjDesignOptimizeItem> getKcsjDesignOptimizeItemList(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.getKcsjDesignOptimizeItemList(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int insertKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        kcsjDesignOptimizeItem.setId(IdWorker.createId());
        kcsjDesignOptimizeItem.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignOptimizeItem.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignOptimizeItemMapper.insertKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int insertKcsjDesignOptimizeItemList(List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList) {
//        for (KcsjDesignOptimizeItem kcsjDesignOptimizeItem : kcsjDesignOptimizeItemList) {
//            kcsjDesignOptimizeItem.setId(IdWorker.createId());
//            kcsjDesignOptimizeItem.setCreateUser(SecurityUtils.getUserName());
//            kcsjDesignOptimizeItem.setCreateTime(DateUtils.getNowDate());
//        }
        return kcsjDesignOptimizeItemMapper.insertKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemList);
    }

    @Transactional
    public int updateKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        kcsjDesignOptimizeItem.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignOptimizeItem.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignOptimizeItemMapper.updateKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int updateKcsjDesignOptimizeItemList(Long optimizeId, List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList
                ,KcsjDesignOptimize main) {
        KcsjDesignOptimizeItem kcsjDesignOptimizeItem = new KcsjDesignOptimizeItem();
        kcsjDesignOptimizeItem.setOptimizeId(optimizeId);
        deleteKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
        BigDecimal beforeSum = BigDecimal.ZERO,afterSum = BigDecimal.ZERO;
        Set<String> codeSet = new HashSet<>();
        //计算变更前、变更后总金额
        for (KcsjDesignOptimizeItem vo : kcsjDesignOptimizeItemList) {
            JyDetailsUtil.jy(vo, new Class[]{ValidationGroups.Save.class});
            vo.setOptimizeId(optimizeId);
            vo.setId(IdWorker.createId());
            new AddBaseInfoUtil<>().addBaseEntity(vo);
            //计算预估优化金额
            vo.setEstimateAmt(ObjectUtils.nvlBigDecimal(vo.getAfterPrice()).subtract(
                    ObjectUtils.nvlBigDecimal(vo.getBeforePrice())));
            beforeSum = beforeSum.add(ObjectUtils.nvlBigDecimal(vo.getBeforePrice()));
            afterSum = afterSum.add(ObjectUtils.nvlBigDecimal(vo.getAfterPrice()));
            Assert.isTrue(!codeSet.contains(vo.getItemCode()), "主材/清单编码["+vo.getItemCode()+"]已存在，不可重复添加");
        }
        BigDecimal changeSum = afterSum.subtract(beforeSum);
        main.setBeforeSumPrice(beforeSum);
        main.setAfterSumPrice(afterSum);
        main.setChangeSumPrice(changeSum);
        return insertKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemList);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItem(Long optimizeId) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItemByOptimizeId(optimizeId);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItemByPks(List<Long> kcsjDesignOptimizeItemPkList) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItemByPks(kcsjDesignOptimizeItemPkList);
    }
}
