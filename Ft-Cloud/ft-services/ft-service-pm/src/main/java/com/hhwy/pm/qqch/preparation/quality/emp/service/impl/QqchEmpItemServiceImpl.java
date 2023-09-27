package com.hhwy.pm.qqch.preparation.quality.emp.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import com.hhwy.pm.qqch.preparation.quality.emp.mapper.QqchEmpItemMapper;
import com.hhwy.pm.qqch.preparation.quality.emp.service.IQqchEmpItemService;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
@Service
public class QqchEmpItemServiceImpl implements IQqchEmpItemService {

    @Autowired
    private IQqchWeightEngineeringListService weightEngineeringListService;
    @Autowired
    private QqchEmpItemMapper qqchEmpItemMapper;


    public QqchEmpItem getQqchEmpItem(QqchEmpItem qqchEmpItem) {
        return qqchEmpItemMapper.getQqchEmpItem(qqchEmpItem);
    }

    public List<QqchEmpItem> getQqchEmpItemList(QqchEmpItem qqchEmpItem) {
        return qqchEmpItemMapper.getQqchEmpItemList(qqchEmpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchEmpItem(QqchEmpItem qqchEmpItem) {
        qqchEmpItem.setId(IdWorker.createId());
        qqchEmpItem.setCreateUser(SecurityUtils.getUserName());
        qqchEmpItem.setCreateTime(DateUtils.getNowDate());
        return qqchEmpItemMapper.insertQqchEmpItem(qqchEmpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList) {
        for (QqchEmpItem qqchEmpItem : qqchEmpItemList) {
            qqchEmpItem.setId(IdWorker.createId());
            qqchEmpItem.setCreateUser(SecurityUtils.getUserName());
            qqchEmpItem.setCreateTime(DateUtils.getNowDate());
        }
        return qqchEmpItemMapper.insertQqchEmpItemList(qqchEmpItemList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchEmpItem(QqchEmpItem qqchEmpItem) {
        qqchEmpItem.setUpdateUser(SecurityUtils.getUserName());
        qqchEmpItem.setUpdateTime(DateUtils.getNowDate());
        return qqchEmpItemMapper.updateQqchEmpItem(qqchEmpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList) {
        for (QqchEmpItem qqchEmpItem : qqchEmpItemList) {
            qqchEmpItem.setUpdateUser(SecurityUtils.getUserName());
            qqchEmpItem.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEmpItemMapper.updateQqchEmpItemList(qqchEmpItemList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchEmpItem(QqchEmpItem qqchEmpItem) {
        qqchEmpItem.setUpdateUser(SecurityUtils.getUserName());
        qqchEmpItem.setUpdateTime(DateUtils.getNowDate());
        return qqchEmpItemMapper.deleteQqchEmpItem(qqchEmpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchEmpItemByPks(List<Long> qqchEmpItemPkList) {
        return qqchEmpItemMapper.deleteQqchEmpItemByPks(qqchEmpItemPkList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CompileEntity<List<List<QqchEmpItem>>> dto) {
        // 获取到当前的版本
        BigDecimal version = dto.getVersion();
        // 用于存放更改的wbs
        HashSet<String> wbsCodeList = new HashSet<>();

        ArrayList<QqchEmpItem> iDatas = new ArrayList<>();

        // 获取要保存的数据
        List<List<QqchEmpItem>> empItemListList = dto.getDto();

        String itemIds = (String)dto.getParams().get("delItemIds");
        if (StringUtils.isNotEmpty(itemIds)) {
            List<Long> collect = Arrays.stream(itemIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
            this.qqchEmpItemMapper.deleteQqchEmpItemByPks(collect);
        }
        
        // 处理要保存的数据
        for (List<QqchEmpItem> qqchEmpItemList : empItemListList) {
            List<QqchEmpItem> qqchEmpItems = TreeUtil.treeToList(qqchEmpItemList);
            for (QqchEmpItem qqchEmpItem : qqchEmpItems) {
                qqchEmpItem.setStoreFlag((qqchEmpItem.getBstoreFlag() == null || !qqchEmpItem.getBstoreFlag()) ? PmConstant.ZERO : PmConstant.ONE);
                wbsCodeList.add(qqchEmpItem.getWbsCode());
                CompileEntity.dealSaveDto(dto, qqchEmpItem,false);
                EntityUtils.setCreateUpdateInfo(qqchEmpItem);
                iDatas.add(qqchEmpItem);
            }
        }


        // 将当前版本的做出变更的wbs进行删除
        this.qqchEmpItemMapper.deleteByWbsCodeAndVersion(wbsCodeList, version);
        this.qqchEmpItemMapper.insertQqchEmpItemList(iDatas);

    }

    @Override
    public List<XmslWbs> wbsList(CompileEntity dto) {
        QqchWeightEngineeringList qqchWeightEngineeringList = new QqchWeightEngineeringList();
        qqchWeightEngineeringList.setVersion(dto.getVersion());
        qqchWeightEngineeringList.setValid(PmConstant.ONE);
        qqchWeightEngineeringList.setDelFlag(PmConstant.ZERO);
        QqchWeightEngineeringListVo weightEngineeringListVo = weightEngineeringListService.getQqchWeightEngineeringListList(qqchWeightEngineeringList);
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = weightEngineeringListVo.getQqchWeightEngineeringListList();


        if (!CollectionUtils.isEmpty(qqchWeightEngineeringListList)) {
            List<String> wbsIdList = qqchWeightEngineeringListList.stream().map(QqchWeightEngineeringList::getWbsId).map(String::valueOf).collect(Collectors.toList());
            return this.getWbsList(wbsIdList);
        }
        return new ArrayList<>();
    }

    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = "qqch_emp_item")
    public CompileEntity<List<QqchEmpItem>> itemList(QqchEmpItem dto) {
        CompileEntity entity = new CompileEntity();
        List<QqchEmpItem> qqchEmpItemList = this.qqchEmpItemMapper.getQqchEmpItemList(dto);
        for (QqchEmpItem qqchEmpItem : qqchEmpItemList) {
            qqchEmpItem.setBstoreFlag(PmConstant.ONE.equals(qqchEmpItem.getStoreFlag()));
        }
        List<QqchEmpItem> build = TreeUtil.build(qqchEmpItemList, null);
        entity.setVersion(dto.getVersion());
        entity.setDto(build);
        return entity;
    }

    private List<XmslWbs> getWbsList(List<String> wbsIdList) {
        List<XmslWbs> res = new ArrayList<>();
        XmslWbs xmslWbs = new XmslWbs();
        xmslWbs.setId("4");
        xmslWbs.setCode("4444");
        xmslWbs.setName("名称1");

        List<XmslWbs> child = new ArrayList<>();
        XmslWbs xmslWbs1 = new XmslWbs();
        xmslWbs1.setId("5");
        xmslWbs1.setCode("5555");
        xmslWbs1.setName("名称2");
        xmslWbs.setChildren(child);
        child.add(xmslWbs1);
        res.add(xmslWbs);
        return res;
    }


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(XmslWbs.class);
    }
}
