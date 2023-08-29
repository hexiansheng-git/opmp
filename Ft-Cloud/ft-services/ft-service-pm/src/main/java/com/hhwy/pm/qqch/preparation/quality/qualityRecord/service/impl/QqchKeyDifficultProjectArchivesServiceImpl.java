package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.KeyDifficultWbs;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.KeyDifficultWbsVo;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper.QqchKeyDifficultProjectArchivesMapper;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchKeyDifficultProjectArchivesService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark
 */
@Service
public class QqchKeyDifficultProjectArchivesServiceImpl implements IQqchKeyDifficultProjectArchivesService {

    @Autowired
    private QqchKeyDifficultProjectArchivesMapper qqchKeyDifficultProjectArchivesMapper;

    @Autowired
    private IQqchWeightEngineeringListService qqchWeightEngineeringListService;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchKeyDifficultProjectArchives getQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        return qqchKeyDifficultProjectArchivesMapper.getQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchives);
    }

    public List<QqchKeyDifficultProjectArchives> getQqchKeyDifficultProjectArchivesList(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        return qqchKeyDifficultProjectArchivesMapper.getQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchives);
    }

    @Transactional
    public int insertQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        qqchKeyDifficultProjectArchives.setId(IdWorker.createId());
        qqchKeyDifficultProjectArchives.setCreateUser(SecurityUtils.getUserName());
        qqchKeyDifficultProjectArchives.setCreateTime(DateUtils.getNowDate());
        return qqchKeyDifficultProjectArchivesMapper.insertQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchives);
    }

    @Transactional
    public int updateQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        qqchKeyDifficultProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyDifficultProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyDifficultProjectArchivesMapper.updateQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchives);
    }

    @Transactional
    public int updateQqchKeyDifficultProjectArchivesList(List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList) {
        for (QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives : qqchKeyDifficultProjectArchivesList) {
            qqchKeyDifficultProjectArchives.setUpdateUser(SecurityUtils.getUserName());
            qqchKeyDifficultProjectArchives.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchKeyDifficultProjectArchivesMapper.updateQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchivesList);
    }

    @Transactional
    public int deleteQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        qqchKeyDifficultProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyDifficultProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyDifficultProjectArchivesMapper.deleteQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchives);
    }

    @Transactional
    public int deleteQqchKeyDifficultProjectArchivesByPks(List<Long> qqchKeyDifficultProjectArchivesPkList) {
        return qqchKeyDifficultProjectArchivesMapper.deleteQqchKeyDifficultProjectArchivesByPks(qqchKeyDifficultProjectArchivesPkList);
    }

    /**
     * 获取最新版本的重难点工程清单数据
     * @return
     */
    public List<QqchKeyDifficultProjectArchives> getValidMaxVersionData() {
        BigDecimal version = VersionUtil.getVersion("qqch_key_difficult_project_archives",null);
        QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives = new QqchKeyDifficultProjectArchives();
        qqchKeyDifficultProjectArchives.setVersion(version);
        return qqchKeyDifficultProjectArchivesMapper.getQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchives);
    }

    /**
     * 获取台账页Vo
     * @param qqchKeyDifficultProjectArchives
     * @return
     */
    @Override
    public KeyDifficultWbsVo getKeyDifficultWbsVo(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        KeyDifficultWbsVo keyDifficultWbsVo = new KeyDifficultWbsVo();

        //获取重难点工程清单wbs
        List<XmslWbs> keyDifficultProjectInventoryWbsList = qqchWeightEngineeringListService.keyDifficultProjectInventoryWbsList();

        //获取重难点工程档案清单
        BigDecimal version = qqchKeyDifficultProjectArchives.getVersion();
        version = VersionUtil.getVersion("qqch_key_difficult_project_archives",version);
        qqchKeyDifficultProjectArchives.setVersion(version);
        List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList = qqchKeyDifficultProjectArchivesMapper.getQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchives);

        List<KeyDifficultWbs> keyDifficultWbsList = new ArrayList<>();
        for (XmslWbs xmslWbs : keyDifficultProjectInventoryWbsList) {
            KeyDifficultWbs keyDifficultWbs = new KeyDifficultWbs();

            keyDifficultWbs.setId(Long.valueOf(xmslWbs.getId()));
            keyDifficultWbs.setPid(Long.valueOf(xmslWbs.getParentId()));
            keyDifficultWbs.setWbsCode(xmslWbs.getCode());
            keyDifficultWbs.setWbsName(xmslWbs.getName());

            List<QqchKeyDifficultProjectArchives> sublist = new ArrayList<>();
            for (QqchKeyDifficultProjectArchives keyDifficultProjectArchives : qqchKeyDifficultProjectArchivesList) {
                if(xmslWbs.getCode().equals(keyDifficultProjectArchives.getWbsCode())){
                    sublist.add(keyDifficultProjectArchives);
                }
            }

            keyDifficultWbs.setSublist(sublist);
            keyDifficultWbsList.add(keyDifficultWbs);
        }

        //构建树
        keyDifficultWbsList = ListTreeUtil.formatTree(
                keyDifficultWbsList,
                o -> o.getPid() == -1,
                (r, n) -> r.getId().equals(n.getPid()),
                KeyDifficultWbs::getChildren,
                KeyDifficultWbs::setChildren);

        keyDifficultWbsVo.setVersion(version);
        keyDifficultWbsVo.setStageIdentity(qqchReviewService.getStage());
        keyDifficultWbsVo.setList(keyDifficultWbsList);
        return keyDifficultWbsVo;
    }

    /**
     * 保存/确认/提交
     * @param keyDifficultWbsVo
     * @return
     */
    @Override
    @Transactional
    public void save(KeyDifficultWbsVo keyDifficultWbsVo) {
        String buttonMark = keyDifficultWbsVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = keyDifficultWbsVo.getVersion();
        List<KeyDifficultWbs> list = keyDifficultWbsVo.getList();

        //处理数据
        this.disposeData(list,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = keyDifficultWbsVo.getMenuId();
            String stageIdentity = keyDifficultWbsVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param list
     * @param version
     */
    @Transactional
    public void disposeData(List<KeyDifficultWbs> list, BigDecimal version) {
        //删除旧数据
        QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives = new QqchKeyDifficultProjectArchives();
        qqchKeyDifficultProjectArchives.setVersion(version);
        qqchKeyDifficultProjectArchivesMapper.deleteQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchives);

        //处理数据
        this.disposeKeyDifficultWbsList(list,version);
    }

    /**
     * 处理列表数据
     * @param list
     * @param version
     */
    @Transactional
    public void disposeKeyDifficultWbsList(List<KeyDifficultWbs> list, BigDecimal version) {
        //拆树
        list = ListTreeUtil.formatList(list,KeyDifficultWbs::getChildren,KeyDifficultWbs::setChildren);

        List<QqchKeyDifficultProjectArchives> insertList = new ArrayList<>();

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (KeyDifficultWbs keyDifficultWbs : list) {
            List<QqchKeyDifficultProjectArchives> sublist = keyDifficultWbs.getSublist();
            if(CollectionUtils.isNotEmpty(sublist)){
                for (QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives : sublist) {
                    qqchKeyDifficultProjectArchives.setId(IdWorker.createId());
                    qqchKeyDifficultProjectArchives.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchKeyDifficultProjectArchives.setCreateUserName(SecurityUtils.getUserName());
                    qqchKeyDifficultProjectArchives.setCreateTime(DateUtils.getNowDate());
                    qqchKeyDifficultProjectArchives.setWbsId(keyDifficultWbs.getId());
                    qqchKeyDifficultProjectArchives.setWbsCode(keyDifficultWbs.getWbsCode());
                    qqchKeyDifficultProjectArchives.setWbsName(keyDifficultWbs.getWbsName());
                    qqchKeyDifficultProjectArchives.setVersion(version);
                    qqchKeyDifficultProjectArchives.setValid(valid);
                    insertList.add(qqchKeyDifficultProjectArchives);
                }
            }
        }

        qqchKeyDifficultProjectArchivesMapper.insertQqchKeyDifficultProjectArchivesList(insertList);
    }
}
