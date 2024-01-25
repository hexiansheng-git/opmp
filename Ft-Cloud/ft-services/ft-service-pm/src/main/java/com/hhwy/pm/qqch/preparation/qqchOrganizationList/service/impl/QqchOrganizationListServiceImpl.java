package com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.mapper.QqchOrganizationListMapper;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.IQqchOrganizationListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeNodeBase;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hwj
 * @date 2023-07-24 17:02:12
 * @remark
 */
@Service
public class QqchOrganizationListServiceImpl implements IQqchOrganizationListService {

    @Autowired
    private QqchOrganizationListMapper qqchOrganizationListMapper;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchOrganizationList getQqchOrganizationList(QqchOrganizationList qqchOrganizationList) {
        return qqchOrganizationListMapper.getQqchOrganizationList(qqchOrganizationList);
    }

    @Override
    public List<QqchOrganizationList> getQqchOrganizationListList2(QqchOrganizationList qqchOrganizationList) {
        return qqchOrganizationListMapper.getQqchOrganizationListList2(qqchOrganizationList);
    }

    public List<QqchOrganizationList> getQqchOrganizationListList(QqchOrganizationList qqchOrganizationList) {
        return qqchOrganizationListMapper.getQqchOrganizationListList(qqchOrganizationList);
    }

    /**
     * 获取变更程序策划
     * @param version
     * @return
     */
    public QqchOrganizationListVo getQqchOrganizationListVo(BigDecimal version) {
        version = VersionUtil.getVersion("qqch_organization_list", version);
        version = version == null ? new BigDecimal("1.0") : version;
        QqchOrganizationList qqchOrganizationList = new QqchOrganizationList();
        qqchOrganizationList.setVersion(version);
        List<QqchOrganizationList> qqchOrganizationListList = qqchOrganizationListMapper.getQqchOrganizationListList(qqchOrganizationList);
        List<QqchOrganizationList> dateList = new ArrayList<>();
        dateList = TreeUtil.buildLeaf(qqchOrganizationListList, null);
        //初始化数据 F1 F2。。。。
        if (dateList.size() == 0) {
            //获取字典项 组织架构设置
            AjaxResult resultQualified = systemServiceApi.dictType("organization_cat");
            List<Map> listDictQualified = (List<Map>) resultQualified.get("data");
            List<QqchOrganizationList> finalDateList = dateList;
            listDictQualified.stream().forEach(temp -> {
                QqchOrganizationList organizationList = new QqchOrganizationList();
                organizationList.setOrganization(temp.get("dictLabel") + "");
                organizationList.setId(IdWorker.createId());
                organizationList.setSort(Integer.valueOf(temp.get("dictValue") + ""));
                finalDateList.add(organizationList);
            });
            dateList = dateList.stream().sorted(Comparator.comparing(QqchOrganizationList::getSort)).collect(Collectors.toList());
        }
        QqchOrganizationListVo organizationListVo = new QqchOrganizationListVo();
        organizationListVo.setDataList(dateList);
        organizationListVo.setVersion(version);
        //查询阶段
        organizationListVo.setStageIdentity(qqchReviewService.getStage());
        return organizationListVo;
    }

    @Override
    public List<QqchOrganizationList> getList4Word() {
        BigDecimal version = VersionUtil.getVersion("qqch_organization_list", null);
        QqchOrganizationList qqchOrganizationList = new QqchOrganizationList();
        qqchOrganizationList.setVersion(version);
        List<QqchOrganizationList> qqchOrganizationListList = qqchOrganizationListMapper.getQqchOrganizationListList(qqchOrganizationList);
        qqchOrganizationListList = ListTreeUtil.preserveSerialNumber(
                qqchOrganizationListList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchOrganizationList::getChildren,
                QqchOrganizationList::setChildren,
                QqchOrganizationList::getSerNum,
                QqchOrganizationList::setSerNum);
        return qqchOrganizationListList;
    }

    @Transactional
    public int insertQqchOrganizationList(QqchOrganizationList qqchOrganizationList) {
        qqchOrganizationList.setId(IdWorker.createId());
        qqchOrganizationList.setCreateUser(SecurityUtils.getUserName());
        qqchOrganizationList.setCreateTime(DateUtils.getNowDate());
        return qqchOrganizationListMapper.insertQqchOrganizationList(qqchOrganizationList);
    }

    @Transactional
    public int insertQqchOrganizationListList(List<QqchOrganizationList> qqchOrganizationListList) {
        for (QqchOrganizationList qqchOrganizationList : qqchOrganizationListList) {
            qqchOrganizationList.setId(IdWorker.createId());
            qqchOrganizationList.setCreateUser(SecurityUtils.getUserName());
            qqchOrganizationList.setCreateTime(DateUtils.getNowDate());
        }
        return qqchOrganizationListMapper.insertQqchOrganizationListList(qqchOrganizationListList);
    }

    @Transactional
    public int updateQqchOrganizationList(QqchOrganizationList qqchOrganizationList) {
        qqchOrganizationList.setUpdateUser(SecurityUtils.getUserName());
        qqchOrganizationList.setUpdateTime(DateUtils.getNowDate());
        return qqchOrganizationListMapper.updateQqchOrganizationList(qqchOrganizationList);
    }

    @Transactional
    public int updateQqchOrganizationListList(List<QqchOrganizationList> qqchOrganizationListList) {
        for (QqchOrganizationList qqchOrganizationList : qqchOrganizationListList) {
            qqchOrganizationList.setUpdateUser(SecurityUtils.getUserName());
            qqchOrganizationList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOrganizationListMapper.updateQqchOrganizationListList(qqchOrganizationListList);
    }

    @Transactional
    public int deleteQqchOrganizationList(QqchOrganizationList qqchOrganizationList) {
        qqchOrganizationList.setUpdateUser(SecurityUtils.getUserName());
        qqchOrganizationList.setUpdateTime(DateUtils.getNowDate());
        return qqchOrganizationListMapper.deleteQqchOrganizationList(qqchOrganizationList);
    }

    @Transactional
    public int deleteQqchOrganizationListByPks(List<Long> qqchOrganizationListPkList) {
        return qqchOrganizationListMapper.deleteQqchOrganizationListByPks(qqchOrganizationListPkList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchOrganizationListVo(QqchOrganizationListVo qqchOrganizationListVo) {
        List<QqchOrganizationList> dataList = qqchOrganizationListVo.getDataList();
        this.checkData(dataList);
        List<QqchOrganizationList> organizationLists = TreeUtil.treeToListWithLevel(dataList);
        organizationLists.stream().map(TreeNodeBase::getLevel).max(Comparator.comparing(Integer::valueOf)).ifPresent(level -> {
            if (level > 2) throw new RuntimeException("组织结构最多只能有两级");
        });


        if (ObjectNullUtil.isEmpty(dataList)) {
            return 1;
        } else {
            //校验数据必填
            if ("1".equals(qqchOrganizationListVo.getButtonMark()) || "2".equals(qqchOrganizationListVo.getButtonMark())) {//确认
                List<QqchOrganizationList> collect = organizationLists.stream().filter(item -> item.getPid() != null).collect(Collectors.toList());
                JyDetailsUtil.jyDetails(collect, ValidationGroups.Save.class);
            }
        }
        String valid = "";//是否有效
        //判断是确认还是保存
        if ("0".equals(qqchOrganizationListVo.getButtonMark())) {//保存（判断是业务保存还是变更保存）
            if (qqchOrganizationListVo.getVersion().intValue() == new BigDecimal(InitVersionConstant.INIT_VERSION).intValue()) {//业务保存
                valid = "1";
            } else {//变更保存
                valid = "0";
            }
        } else if ("1".equals(qqchOrganizationListVo.getButtonMark())) {//确认
            valid = "1";
            //插入确认状态
            String menuId = qqchOrganizationListVo.getMenuId();
            String stageIdentity = qqchOrganizationListVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        } else if ("2".equals(qqchOrganizationListVo.getButtonMark())) {//提交
            valid = "0";
        } else {
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "标识不符合规范");
        }
        String finalValid = valid;

        organizationLists.stream().forEach(item -> {
            item.setVersion(qqchOrganizationListVo.getVersion());
            item.setValid(finalValid);
            item.setCreateUser(SecurityUtils.getSysUser().getUserId() + "");
            item.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            item.setCreateTime(DateUtils.getNowDate());
        });
        //先删除旧的 再添加新的
        QqchOrganizationList temp = new QqchOrganizationList();
        temp.setVersion(qqchOrganizationListVo.getVersion());
        qqchOrganizationListMapper.deleteQqchOrganizationList(temp);

        qqchOrganizationListMapper.insertQqchOrganizationListList(organizationLists);
        return 1;
    }

    @Override
    public List<QqchOrganizationList> mergeData(List<QqchOrganizationList> importDataList, List<QqchOrganizationList> oldDataList) {

        LinkedHashMap<String, String> organizationCat = DictUtil.getDictData("organization_cat");
        Set<String> strings = organizationCat.keySet();
        this.checkData(importDataList);
        for (String string : strings) {
            // 从老数据中获取数据
            oldDataList.stream().filter(item -> string.equals(item.getOrganization())).findFirst().ifPresent(old -> {
                importDataList.stream().filter(imp -> string.equals(imp.getOrganization())).findFirst().ifPresent(imp -> {
                    List<QqchOrganizationList> oldChildren = old.getChildren();
                    List<QqchOrganizationList> impChildren = imp.getChildren();
                    List<QqchOrganizationList> nChildren = new ArrayList<>();
                    if (CollectionUtils.isEmpty(oldChildren)) {
                        nChildren = impChildren;
                    } else {
                        for (QqchOrganizationList oldChild : oldChildren) {
                            nChildren = impChildren.stream().filter(i ->i.getOrganization() == null || !i.getOrganization().equals(oldChild.getOrganization())).collect(Collectors.toList());
                        }
                    }

                    oldChildren.addAll(nChildren);
                    // TreeSet<QqchOrganizationList> distinctList = oldChildren.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(QqchOrganizationList::getOrganization))));
                    old.setChildren(oldChildren);
                });
            });

        }


        return oldDataList;
    }

    private void checkData(List<QqchOrganizationList> dataList) {
        LinkedHashMap<String, String> organizationCat = DictUtil.getDictData("organization_cat");
        Set<String> strings = organizationCat.keySet();
        if (dataList.size() != strings.size()) throw new RuntimeException("不能新增或者删除组织机构顶级数据");
        StringBuilder stringBuilder = new StringBuilder();
        for (String label : strings) {
            List<QqchOrganizationList> collect = dataList.stream().filter(ite -> label.equals(ite.getOrganization())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                stringBuilder.append(label + "不能删除或者修改").append(";");
            }
        }
        if (!StringUtils.isEmpty(stringBuilder.toString())) {
            throw new RuntimeException(stringBuilder.toString());
        }

    }
}
