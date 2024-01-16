package com.hhwy.pm.qqch.preparation.quality.emp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import com.hhwy.pm.qqch.preparation.quality.emp.mapper.QqchEmpItemMapper;
import com.hhwy.pm.qqch.preparation.quality.emp.service.IQqchEmpItemService;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.domain.QyzsQualitySpecialInspection;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.system.api.RemoteTenantService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.java.Log;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
@Service
@Log
public class QqchEmpItemServiceImpl implements IQqchEmpItemService {
    @Value("${gm.back-url}")
    private String gmUrl;

    @Autowired
    private IQqchModuleConfirmCaseService moduleConfirmCaseService;
    @Autowired
    private IQqchReviewService reviewService;
    @Autowired
    private ITWbsService wbsService;

    @Autowired
    private IQqchWeightEngineeringListService weightEngineeringListService;
    @Autowired
    private QqchEmpItemMapper qqchEmpItemMapper;
    @Autowired
    private RemoteTenantService remoteTenantService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

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

    @Override
    public Object getQyzsQualitySpecialInspectionList(Long standardId,String inspectionProject,String inspectionName) {
        if(standardId == null)
            return new ArrayList(2);
        //查询标准wbs对应的工程类型
        TWbs tWbs =  wbsService.getTWbsById(standardId);
        if(tWbs == null)
            return new ArrayList(2);
        String engineeringType = this.wbsService.getEngineeringTypeByMainId(tWbs.getMainId());
        //请求总部版数据
        String url = ObjectUtils.concatUrl(gmUrl,"gm/qyzsQualitySpecialInspection/list?projectType="
                +engineeringType+"&wbsCode="+tWbs.getCode()+"&inspectionProject="+inspectionProject+"&inspectionName="+inspectionName);
        String res = HttpRequest.get(url)
                .header("Content-Type", "application/json")
                .header(HttpHeadersUtils.getCommonHeaders())
                .execute().body();
        JSONObject resObj = JSON.parseObject(res);
        if(!resObj.get("code").equals(200)){
            log.info("获取总部版质量专项检查表失败，接口返回:"+resObj.get("msg"));
            return new ArrayList();
        }
        Object dataObj = resObj.get("data");
        return dataObj;

    }

    @Override
    public List<QqchEmpItem> merge(QqchEmpItem item,List<QyzsQualitySpecialInspection> inspectionList, List<QqchEmpItem> sourceList) {
        if(CollectionUtils.isEmpty(inspectionList))
            return sourceList;
        inspectionList.sort((v1,v2)->{
            return v1.getId()==v2.getId()?0:(v1.getId()>v2.getId()?1:-1);
        });
        List<QqchEmpItem> list = new ArrayList<>();
        iter(sourceList,list);
        Map<Long,QqchEmpItem> insIdMap = list.stream().filter(r->r.getInspectionId()!=null).collect(Collectors.toMap(r->r.getInspectionId(),r->r));
        //替换的旧ID : 新id
        Map<Long,Long> oldIdRelateMap = new HashMap<>();
        for (int i = 0; i < inspectionList.size(); i++) {
            QyzsQualitySpecialInspection temp = inspectionList.get(i);
            QqchEmpItem tempItem = new QqchEmpItem();
            if(insIdMap.containsKey(temp.getId())) //已存在则跳过
                continue;
            if(insIdMap.containsKey(temp.getPid())){ //父级存在，则放到父级下面
                QqchEmpItem parent = insIdMap.get(temp.getPid());
                parent.getChildren().add(tempItem);
                tempItem.setPid(parent.getId());
            }else{
                sourceList.add(tempItem);
            }
            //直接替换
            Long newId = IdWorker.createId();
            oldIdRelateMap.put(temp.getId(),newId);
            tempItem.setId(newId);
            tempItem.setWbsId(item.getWbsId());
            tempItem.setWbsCode(item.getWbsCode());
            tempItem.setAncestorsWbsId(item.getAncestorsWbsId());
            tempItem.setCheckCode(temp.getInspectionNo());
            tempItem.setCheckName(temp.getInspectionName());
            tempItem.setCheckItem(temp.getInspectionProject());
            tempItem.setStipulate(temp.getSpecifiedValue());
            tempItem.setCheckMethod(temp.getInspectionMethodFrequency());
            tempItem.setStoreFlag("1");
            tempItem.setInspectionId(temp.getId());
            tempItem.setVersion(item.getVersion());
            tempItem.setDelFlag("0");
            tempItem.setValid("1");
            new AddBaseInfoUtil<>().addBaseEntity(tempItem);
            insIdMap.put(tempItem.getInspectionId(),tempItem);
        }
        return sourceList;
    }

    private static void iter(List<QqchEmpItem> list,List<QqchEmpItem> resuList){
        if(CollectionUtils.isEmpty(list))
            return ;
        for (int i = 0; i < list.size(); i++) {
            resuList.add(list.get(i));
            if(list.get(i).getChildren() != null) {
                iter(list.get(i).getChildren(),resuList);
            }
        }
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

        ArrayList<String> wbsIds = new ArrayList<>();

        for (List<QqchEmpItem> qqchEmpItems : empItemListList) {
            List<String> collect = qqchEmpItems.stream().map(i -> String.valueOf(i.getWbsId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) wbsIds.addAll(collect);

        }

        Map<String, String> wbsMap = new HashMap<>();
        List<XmslWbs> wbs = WbsRedisUtils.getWbs(wbsIds);
        if (!CollectionUtils.isEmpty(wbs)) {
            wbsMap = wbs.stream().collect(Collectors.toMap(XmslWbs::getId, XmslWbs::getAncestors, (r1, r2) -> r1));
        }


        String itemIds = (String) dto.getParams().get("delItemIds");
        if (StringUtils.isNotEmpty(itemIds)) {
            List<Long> collect = Arrays.stream(itemIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                this.qqchEmpItemMapper.deleteQqchEmpItemByPks(collect);
            }
        }

        // 处理要保存的数据
        for (List<QqchEmpItem> qqchEmpItemList : empItemListList) {
            List<QqchEmpItem> qqchEmpItems = TreeUtil.treeToList(qqchEmpItemList);
            for (QqchEmpItem qqchEmpItem : qqchEmpItems) {
                qqchEmpItem.setAncestorsWbsId(wbsMap.get("" + qqchEmpItem.getWbsId()));
                qqchEmpItem.setStoreFlag((qqchEmpItem.getBstoreFlag() == null || !qqchEmpItem.getBstoreFlag()) ? PmConstant.ZERO : PmConstant.ONE);
                wbsCodeList.add(qqchEmpItem.getWbsCode());
                CompileEntity.dealSaveDto(dto, qqchEmpItem, false);
                EntityUtils.setCreateUpdateInfo(qqchEmpItem);
                iDatas.add(qqchEmpItem);
            }
        }
        pushData(iDatas);

        if (PmConstant.ONE.equals(dto.getSubmitFlag())) {
            String stage = reviewService.getStage();
            moduleConfirmCaseService.addConfirmRecord(dto.getModuleIdentity(), stage);
            reviewService.updateFinishNum();
            //数据推送总部版
            pushData(iDatas);

        }
        // 将当前版本的做出变更的wbs进行删除
        if (!CollectionUtils.isEmpty(wbsCodeList))
            this.qqchEmpItemMapper.deleteByWbsCodeAndVersion(wbsCodeList, version);
        if (!CollectionUtils.isEmpty(iDatas)) this.qqchEmpItemMapper.insertQqchEmpItemList(iDatas);

    }

    /***
     * 功能描述: 数据推送总版
     * 作者: fushudong
     * 时间: 2023/12/4
     */
    private void pushData(List<QqchEmpItem> list) {
        if (CollectionUtil.isEmpty(list))
            return;
        Map<Long,QqchEmpItem> map = list.stream().collect(Collectors.toMap(r->r.getId(),r->r));
        SysTenant tenant = remoteTenantService.getTenantByTenantKey(SecurityUtils.getTenantKey()).getData();
        List<QqchEmpItem> nonNullList = list.stream()
                .filter(p -> p.getInspectionId()==null && p.getStoreFlag().equals("1") )
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(nonNullList)) return;
        //推送总部版知识库
        List<QyzsQualitySpecialInspection> pushData = new ArrayList<>();
        nonNullList.forEach(p -> {
            QyzsQualitySpecialInspection bean = new QyzsQualitySpecialInspection();
            bean.setId(p.getId());
            QqchEmpItem temp = map.get(p.getPid());
            Long pid = temp==null?p.getPid():(temp.getInspectionId()!=null?temp.getInspectionId():temp.getId());
            bean.setPid(pid);
            bean.setWbsCode(p.getWbsCode());
            bean.setInspectionNo(p.getCheckCode());
            bean.setInspectionName(p.getCheckName());
            bean.setInspectionProject(p.getCheckItem());
            bean.setSpecifiedValue(p.getStipulate());
            bean.setInspectionMethodFrequency(p.getCheckMethod());
            bean.setDataFrom(tenant.getTenantName()+"推送");
            bean.setEditer(SecurityUtils.getUserName());
            bean.setEditDate(DateUtil.date());
            bean.setRemark(p.getRemark());
            pushData.add(bean);
        });
        System.out.println(JSONObject.toJSONString(pushData));
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
    public CompileEntity<List<QqchEmpItem>> itemList(QqchEmpItem dto) {
        CompileEntity entity = new CompileEntity();
        XmslWbs wbsByCodes = WbsRedisUtils.getWbsByCode(dto.getWbsCode());
        dto.setWbsId(Long.valueOf(wbsByCodes.getId()));
        dto.setWbsCode(null);
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


        List<XmslWbs> wbs = WbsRedisUtils.getWbs(wbsIdList);

        StringBuilder sb = new StringBuilder();
        for (XmslWbs wb : wbs) {
            String ancestors = wb.getAncestors();
            if (StringUtils.isNotEmpty(ancestors)) {
                sb.append(ancestors).append(",");
            }
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            String s = sb.toString();
            List<String> split = Arrays.stream(s.split(",")).distinct().collect(Collectors.toList());
            // 清空一下没有用的数据  不然前端不回显
            List<XmslWbs> collect = WbsRedisUtils.getWbs(split).stream().map(i -> {
                XmslWbs xmslWbs = new XmslWbs();
                xmslWbs.setId(i.getId());
                xmslWbs.setAncestorsName(i.getAncestorsName());
                xmslWbs.setAncestors(i.getAncestors());
                xmslWbs.setParentId(i.getParentId());
                xmslWbs.setCode(i.getCode());
                xmslWbs.setName(i.getName());
                xmslWbs.setStandardId(i.getStandardId());
                xmslWbs.setStandardCode(i.getStandardCode());
                return xmslWbs;
            }).collect(Collectors.toList());
            return ListTreeUtil.formatTree(collect, i -> PmConstant.MINUS_ONE.equals(i.getParentId()), (r, n) -> r.getId().equals(n.getParentId()), XmslWbs::getChildren, XmslWbs::setChildren);
        }
        return new ArrayList<>();

    }


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(XmslWbs.class);
    }
}
