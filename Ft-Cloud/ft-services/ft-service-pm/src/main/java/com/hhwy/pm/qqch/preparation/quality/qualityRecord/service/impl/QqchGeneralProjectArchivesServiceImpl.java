package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.CommonYesNo;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbs;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbsVo;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper.QqchGeneralProjectArchivesMapper;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchGeneralProjectArchivesService;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchKeyDifficultProjectArchivesService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-08-24 15:32:35
 * @remark
 */
@Service
public class QqchGeneralProjectArchivesServiceImpl implements IQqchGeneralProjectArchivesService {

    @Autowired
    private QqchGeneralProjectArchivesMapper qqchGeneralProjectArchivesMapper;

    @Autowired
    private IXmslWbsService xmslWbsService;

    @Autowired
    private IQqchWeightEngineeringListService qqchWeightEngineeringListService;

    @Autowired
    private IQqchKeyDifficultProjectArchivesService qqchKeyDifficultProjectArchivesService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;

    private static final String TN = "qqch_general_project_archives";

    public QqchGeneralProjectArchives getQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        return qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    public List<QqchGeneralProjectArchives> getQqchGeneralProjectArchivesList(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        return qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchives);
    }

    @Transactional
    public int insertQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setId(IdWorker.createId());
        qqchGeneralProjectArchives.setCreateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setCreateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int insertQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList) {
        for (QqchGeneralProjectArchives qqchGeneralProjectArchives : qqchGeneralProjectArchivesList) {
            qqchGeneralProjectArchives.setId(IdWorker.createId());
            qqchGeneralProjectArchives.setCreateUser(SecurityUtils.getUserName());
            qqchGeneralProjectArchives.setCreateTime(DateUtils.getNowDate());
        }
        return qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesList);
    }

    @Transactional
    public int updateQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.updateQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int updateQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList) {
        for (QqchGeneralProjectArchives qqchGeneralProjectArchives : qqchGeneralProjectArchivesList) {
            qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
            qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGeneralProjectArchivesMapper.updateQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesList);
    }

    @Transactional
    public int deleteQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        qqchGeneralProjectArchives.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralProjectArchives.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralProjectArchivesMapper.deleteQqchGeneralProjectArchives(qqchGeneralProjectArchives);
    }

    @Transactional
    public int deleteQqchGeneralProjectArchivesByPks(List<Long> qqchGeneralProjectArchivesPkList) {
        return qqchGeneralProjectArchivesMapper.deleteQqchGeneralProjectArchivesByPks(qqchGeneralProjectArchivesPkList);
    }

    /**
     * 获取台账Vo
     * @param qqchGeneralProjectArchives
     * @return
     */
    @Override
    public GeneralProjectArchivesWbsVo getGeneralProjectArchivesWbsVo(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo = new GeneralProjectArchivesWbsVo();

        //获取一般工程档案清单
        BigDecimal version = qqchGeneralProjectArchives.getVersion();
        this.checkExistsData(version);
        version = VersionUtil.getVersion(TN,version);
        this.setList(generalProjectArchivesWbsVo,version);

        generalProjectArchivesWbsVo.setVersion(version);
        generalProjectArchivesWbsVo.setStageIdentity(qqchReviewService.getStage());
        return generalProjectArchivesWbsVo;
    }

    public void checkExistsData(BigDecimal version){
        if(version == null){
            return;
        }
        boolean exists = CommonServiceUtil.checkExistsByVersion(TN, version);
        if(exists){
            return;
        }
        BigDecimal oldVersion = VersionUtil.getVersion(TN,version);
        if(oldVersion.equals(version)){
            return;
        }

        //获取一般工程档案清单
        QqchGeneralProjectArchives query = new QqchGeneralProjectArchives();
        query.setVersion(version);
        List<QqchGeneralProjectArchives> archivesList = qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(query);

        if(CollectionUtils.isEmpty(archivesList)){
            return;
        }

        for (QqchGeneralProjectArchives archives : archivesList) {
            archives.setId(IdWorker.createId());
            archives.setVersion(version);
            archives.setValid("0");
            archives.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            archives.setCreateUserName(SecurityUtils.getUserName());
            archives.setCreateTime(DateUtils.getNowDate());
        }

        qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchivesList(archivesList);
    }

    /**
     * 设置三个list数据
     * @param generalProjectArchivesWbsVo
     * @param version
     */
    public void setList(GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo,BigDecimal version){
        //获取最顶级的wbs
        List<XmslWbs> wbsList = xmslWbsService.latestData(new XmslWbs());

        //获取一般工程档案清单
        QqchGeneralProjectArchives qqchGeneralProjectArchives = new QqchGeneralProjectArchives();
        qqchGeneralProjectArchives.setVersion(version);
        List<QqchGeneralProjectArchives> generalProjectArchivesList = qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchives);

        //获取最新版本的重难点工程档案数据
        List<QqchKeyDifficultProjectArchives> keyDifficultProjectArchivesList = qqchKeyDifficultProjectArchivesService.getValidMaxVersionData();

        List<GeneralProjectArchivesWbs> list = this.integrate(wbsList, generalProjectArchivesList, keyDifficultProjectArchivesList);
        generalProjectArchivesWbsVo.setList(list);
        generalProjectArchivesWbsVo.setAllGeneralSublist(generalProjectArchivesList);
        generalProjectArchivesWbsVo.setAllDifficultSublist(keyDifficultProjectArchivesList);
    }

    /**
     * 合并数据
     * @param wbsList
     * @param version
     * @return
     */
    private List<GeneralProjectArchivesWbs> integrateList(List<XmslWbs> wbsList, BigDecimal version) {
        //获取一般工程档案清单
        QqchGeneralProjectArchives qqchGeneralProjectArchives = new QqchGeneralProjectArchives();
        qqchGeneralProjectArchives.setVersion(version);
        List<QqchGeneralProjectArchives> generalProjectArchivesList = qqchGeneralProjectArchivesMapper.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchives);

        //获取最新版本的重难点工程档案数据
        List<QqchKeyDifficultProjectArchives> keyDifficultProjectArchivesList = qqchKeyDifficultProjectArchivesService.getValidMaxVersionData();
        return this.integrate(wbsList, generalProjectArchivesList, keyDifficultProjectArchivesList);
    }

    /**
     * 组装数据
     * @param wbsList
     * @param generalProjectArchivesList
     * @param keyDifficultProjectArchivesList
     * @return
     */
    public List<GeneralProjectArchivesWbs> integrate(List<XmslWbs> wbsList,List<QqchGeneralProjectArchives> generalProjectArchivesList,List<QqchKeyDifficultProjectArchives> keyDifficultProjectArchivesList){
        //获取重难点工程清单对应的wbsId
        Set<Long> keyPointWbsIds = qqchWeightEngineeringListService.getCurrentAndLowerLevelWbsIds();

        String wbsCodes = wbsList.stream().map(XmslWbs::getCode).collect(Collectors.joining(","));
        List<QqchMainPlanItem> planItemList = qqchMainPlanItemService.getListByItemCodes(wbsCodes);
        Map<String, QqchMainPlanItem> planItemMap = planItemList.stream().collect(Collectors.toMap(QqchMainPlanItem::getItemCode, o -> o));

        Calendar calendar = Calendar.getInstance();
        List<GeneralProjectArchivesWbs> list = new ArrayList<>();
        for (XmslWbs wbs : wbsList) {
            GeneralProjectArchivesWbs generalProjectArchivesWbs = new GeneralProjectArchivesWbs();

            String wbsCode = wbs.getCode();
            Long wbsId = Long.valueOf(wbs.getId());
            generalProjectArchivesWbs.setId(wbsId);
            generalProjectArchivesWbs.setHaveChildren(wbs.getHaveChildren());
            generalProjectArchivesWbs.setPid(Long.valueOf(wbs.getParentId()));
            generalProjectArchivesWbs.setWbsCode(wbsCode);
            generalProjectArchivesWbs.setWbsName(wbs.getName());

            //设置完工时间和资料完成时间
            QqchMainPlanItem planItem = planItemMap.get(wbsCode);
            if(planItem != null){
                Date finishDate = planItem.getFinishDate();
                if(finishDate != null){
                    calendar.setTime(finishDate);
                    calendar.add(Calendar.DATE,5);
                    Date after5Date = calendar.getTime();
                    generalProjectArchivesWbs.setCompleteTime(finishDate);
                    generalProjectArchivesWbs.setDataCompleteTime(after5Date);
                }
            }

            //判断当前wbs是否是重难点wbs
            if(keyPointWbsIds.contains(wbsId)) {
                //是重难点wbs
                generalProjectArchivesWbs.setKeyDifficultPointFlag(CommonYesNo.YES);
                List<QqchKeyDifficultProjectArchives> difficultSublist = new ArrayList<>();
                for (QqchKeyDifficultProjectArchives keyDifficultProjectArchives : keyDifficultProjectArchivesList) {
                    if(wbsId.equals(keyDifficultProjectArchives.getWbsId())){
                        difficultSublist.add(keyDifficultProjectArchives);
                    }
                }
                generalProjectArchivesWbs.setDifficultSublist(difficultSublist);
            }else {
                //是一般工程清单wbs
                generalProjectArchivesWbs.setKeyDifficultPointFlag(CommonYesNo.NO);
                List<QqchGeneralProjectArchives> generalSublist = new ArrayList<>();
                for (QqchGeneralProjectArchives generalProjectArchives : generalProjectArchivesList) {
                    if(wbsId.equals(generalProjectArchives.getWbsId())){
                        generalSublist.add(generalProjectArchives);
                    }
                }
                generalProjectArchivesWbs.setGeneralSublist(generalSublist);
            }
            list.add(generalProjectArchivesWbs);
        }
        return list;
    }

    /**
     * 点击获取下级
     * @param qqchGeneralProjectArchives
     * @return
     */
    @Override
    public List<GeneralProjectArchivesWbs> getLowerLevel(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        Long wbsId = qqchGeneralProjectArchives.getWbsId();
        BigDecimal version = qqchGeneralProjectArchives.getVersion();
        List<XmslWbs> wbsList = WbsRedisUtils.getDireChildWbs(String.valueOf(wbsId));
        return this.integrateList(wbsList, version);
    }

    /**
     * 保存/确认/提交
     * @param generalProjectArchivesWbsVo
     * @return
     */
    @Override
    @Transactional
    public void save(GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo) {
        String buttonMark = generalProjectArchivesWbsVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = generalProjectArchivesWbsVo.getVersion();
        List<GeneralProjectArchivesWbs> list = generalProjectArchivesWbsVo.getList();

        //处理数据
        this.disposeData(list,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = generalProjectArchivesWbsVo.getMenuId();
            String stageIdentity = generalProjectArchivesWbsVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param list
     * @param version
     */
    @Transactional
    public void disposeData(List<GeneralProjectArchivesWbs> list, BigDecimal version) {
        //一般工程档案列表
        List<QqchGeneralProjectArchives> insertList = new ArrayList<>();

        StringBuilder wbsCodes = new StringBuilder();

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (GeneralProjectArchivesWbs generalProjectArchivesWbs : list) {
            Long wbsId = generalProjectArchivesWbs.getId();
            String wbsCode = generalProjectArchivesWbs.getWbsCode();
            String wbsName = generalProjectArchivesWbs.getWbsName();
            String keyDifficultPointFlag = generalProjectArchivesWbs.getKeyDifficultPointFlag();

            if(CommonYesNo.YES.equals(keyDifficultPointFlag)){
                continue;
            }

            wbsCodes.append(wbsCode).append(",");

            List<QqchGeneralProjectArchives> generalSublist = generalProjectArchivesWbs.getGeneralSublist();
            for (QqchGeneralProjectArchives generalProjectArchives : generalSublist) {
                generalProjectArchives.setId(IdWorker.createId());
                generalProjectArchives.setWbsId(wbsId);
                generalProjectArchives.setWbsCode(wbsCode);
                generalProjectArchives.setWbsName(wbsName);
                generalProjectArchives.setVersion(version);
                generalProjectArchives.setValid(valid);
                generalProjectArchives.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                generalProjectArchives.setCreateUserName(SecurityUtils.getUserName());
                generalProjectArchives.setCreateTime(DateUtils.getNowDate());
                insertList.add(generalProjectArchives);
            }
        }

        //根据wbsCodes和版本删除数据
        qqchGeneralProjectArchivesMapper.deleteByWbsCodesAndVersion(wbsCodes.toString(),version);

        if(insertList.size() > 0){
            qqchGeneralProjectArchivesMapper.insertQqchGeneralProjectArchivesList(insertList);
        }
    }
}
