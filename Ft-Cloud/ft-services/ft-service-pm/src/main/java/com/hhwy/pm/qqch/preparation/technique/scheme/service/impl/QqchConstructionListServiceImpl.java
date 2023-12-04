package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
@Service
public class QqchConstructionListServiceImpl implements IQqchConstructionListService {

    @Autowired
    private QqchConstructionListMapper qqchConstructionListMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;

    private static final String TN = "qqch_construction_list";

    @Override
    public QqchConstructionListVo getQqchConstructionListList(QqchConstructionListVo paramVo) {
        QqchConstructionListVo vo = new QqchConstructionListVo();

        BigDecimal version = paramVo.getVersion();
        this.checkExistsByVersion(version);
        version = VersionUtil.getVersion(TN, version);
        vo.setVersion(version);
        if(StringUtils.isNotBlank(paramVo.getWbsCode())){
            QqchConstructionList qryParam = new QqchConstructionList();
            qryParam.setVersion(version);
            qryParam.setSchemeName(paramVo.getSchemeName());
            qryParam.setSchemeLevel(paramVo.getSchemeType());
            qryParam.setWbsCode(paramVo.getWbsCode());
            List<QqchConstructionList> list = qqchConstructionListMapper.getQqchConstructionListList(qryParam);
            vo.setList(list);
        }else{
            vo.setList(new ArrayList<>(2));
        }
        vo.setStageIdentity(qqchReviewService.getStage());
        //返回最大流水号，方便前端生成
        String flowCode = qqchConstructionListMapper.selectMaxFlowCode();
        vo.setProjectCode(SecurityUtils.getTenantKey());
        vo.setFlowCode(ObjectUtils.nvlString(flowCode,"001"));
        return vo;
    }

    public void checkExistsByVersion(BigDecimal version){
        if(version == null){
            return;
        }
        boolean exists = CommonServiceUtil.checkExistsByVersion(TN, version);
        if(exists){
            return;
        }
        BigDecimal oldVersion = VersionUtil.getVersion(TN, version);
        if(oldVersion.equals(version)){
            return;
        }
        //获取该版本全量数据
        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(oldVersion);
        List<QqchConstructionList> list = qqchConstructionListMapper.getQqchConstructionListList(qryParam);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (QqchConstructionList qqchConstructionList : list) {
            qqchConstructionList.setId(IdWorker.createId());
            qqchConstructionList.setVersion(version);
            qqchConstructionList.setValid("0");
            qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
            qqchConstructionList.setCreateTime(DateUtils.getNowDate());
        }
        qqchConstructionListMapper.insertQqchConstructionListList(list);
    }

    @Override
    public List<QqchConstructionList> list(QqchConstructionList list) {
        return qqchConstructionListMapper.getQqchConstructionListList(list);
    }

    /**
     * 获取最新的施工方案清单数据
     * @return
     */
    @Override
    public List<QqchConstructionList> getLatest(){
        // 获取方案清单最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion(TN);
        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(maxVersion);
        // 获取方案清单数据
        return qqchConstructionListMapper.getQqchConstructionListList(qryParam);
    }

    @Transactional
    public void batchSave(QqchConstructionListVo qqchConstructionListVo) {
        // 先批量删除当前版本所有数据
//        QqchConstructionList deleteParam = new QqchConstructionList();
//        deleteParam.setVersion(qqchConstructionListVo.getVersion());
//        deleteParam.setDelFlag("1");
//        qqchConstructionListMapper.updateQqchConstructionList(deleteParam);
        Set<String> delWbsCodeSet = new HashSet<>();
        Date passTime = null;
        if (!CollectionUtils.isEmpty(qqchConstructionListVo.getList())) {
            for (int i = 0; i < qqchConstructionListVo.getList().size(); i++) {
                QqchConstructionList qqchConstructionList = qqchConstructionListVo.getList().get(i);
                if(passTime != null){
                    if(i==0)
                        passTime = qqchConstructionList.getListPassTime();
                    else
                        Assert.isTrue(passTime.getTime() == qqchConstructionList.getListPassTime().getTime(), "清单通过时间必须一致");
                }
                if (StringUtils.isBlank(qqchConstructionList.getSchemeCode())) {
                    // 方案编号 = 项目编码 + 三位流水号
                    String code = genCodeService.getSetCode(CodeEnum.QQCH_CONSTRUCTION_LIST);
                    String newCode = code.replace(CodeEnum.QQCH_CONSTRUCTION_LIST.prefix(), "");
                    qqchConstructionList.setSchemeCode(SecurityUtils.getSysUser().getTenantKey() + newCode);
                }
                qqchConstructionList.setId(IdWorker.createId());
                qqchConstructionList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchConstructionList.setCreateUserName(SecurityUtils.getUserName());
                qqchConstructionList.setCreateTime(DateUtils.getNowDate());
                qqchConstructionList.setSort(i + 1);
                qqchConstructionList.setVersion(qqchConstructionListVo.getVersion());
                if (qqchConstructionListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchConstructionList.setValid(Valid.YES);
                }
                qqchConstructionList.setPtVar1(StringUtils.substring(qqchConstructionList.getSchemeCode(), qqchConstructionList.getSchemeCode().length()-3,qqchConstructionList.getSchemeCode().length())); //截取出流水号，方便统计最大流水
                delWbsCodeSet.add(qqchConstructionList.getWbsCode());
            }
            //删除原wbsCode对应的数据
            qqchConstructionListMapper.deleteByWbsCode(delWbsCodeSet);
            qqchConstructionListMapper.insertQqchConstructionListList(qqchConstructionListVo.getList());
        }
        //删除
        if(StringUtils.isNotBlank(qqchConstructionListVo.getDelIds())){
            Long[] delIds = Convert.toLongArray(qqchConstructionListVo.getDelIds());
            qqchConstructionListMapper.deleteQqchConstructionListByPks(Arrays.asList(delIds));
        }
        String buttonMark = qqchConstructionListVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchConstructionListVo.getMenuId();
            String stageIdentity = qqchConstructionListVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
        //更新清单通过时间
        if(passTime != null)
            qqchConstructionListMapper.updatePassTime(passTime,qqchConstructionListVo.getVersion());
    }

    @Override
    public List<QqchConstructionList> getByWbsCodes(String[] wbsCodes) {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        return qqchConstructionListMapper.getByWbsCodes(wbsCodes, maxVersion);
    }

    @Override
    @Transactional
    public void importData(List<QqchConstructionList> list,BigDecimal version) {
        if(CollectionUtils.isEmpty(list))
            return;
        //导入的wbs编号
        Set<String> wbsCodeSet = new HashSet<>();
        //导入的方案数据
        Map<String,QqchConstructionList> map = new HashMap<>();
        Date passTime = null;
        //当前最大序号
        Integer maxFlow = ObjectUtils.toInteger(this.qqchConstructionListMapper.selectMaxFlowCode(),1);
        String prjCode = SecurityUtils.getTenantKey(); //租户key(项目编号)
        //1、遍历清单，汇总必要数据并校验
        for (int i = 0; i < list.size(); i++) {
            try{
                QqchConstructionList temp = list.get(i);
                Assert.isTrue(StringUtils.isNotBlank(temp.getSchemeName()), "方案名称不能为空");
                Assert.isTrue(StringUtils.isNotBlank(temp.getWbsCode()), "关联WBS编号不能为空");
                if(passTime != null){
                    if(i==0)
                        passTime = temp.getListPassTime();
                    else
                        Assert.isTrue(passTime.getTime() == temp.getListPassTime().getTime(), "清单通过时间必须一致");
                }
                wbsCodeSet.add(temp.getWbsCode());
                new AddBaseInfoUtil<>().addBaseEntity(temp);
                temp.setSchemeCode(prjCode+String.format("%03d",(++maxFlow)));
                XmslWbs wbs =WbsRedisUtils.getWbsByCode(temp.getWbsCode());
                Assert.notNull(wbs,"WBS编号["+temp.getWbsCode()+"]不存在");
                temp.setWbsName(wbs.getName());
                map.put(ObjectUtils.nvlString(temp.getWbsCode()+"_"+temp.getSchemeName().trim()),temp);
                //项目联系人默认当前登录用户
                temp.setContactPerson(SecurityUtils.getSysUser().getNickName());
                temp.setPtVar1(StringUtils.substring(temp.getSchemeCode(), temp.getSchemeCode().length()-3,temp.getSchemeCode().length())); //截取出流水号，方便统计最大流水
            }catch(Exception e){
                e.printStackTrace();
                throw new CustomBusinessException("第"+(i+2)+"行,"+e.getMessage());
            }
        }
        //2、查库中wbs对应的清单，覆盖到map中
        List<QqchConstructionList> dbList = this.qqchConstructionListMapper.getByWbsCodes(wbsCodeSet.toArray(new String[]{}),version);
        for (int i = 0; i < dbList.size(); i++) {
            QqchConstructionList temp = dbList.get(i);
            String key = ObjectUtils.nvlString(temp.getWbsCode()+"_"+temp.getSchemeName().trim());
            if(map.containsKey(key)){
                QqchConstructionList listTemp =  map.get(key);
                listTemp.setId(temp.getId());
            }
        }
        //3、获取新增数据，
        Iterator<QqchConstructionList> iterator = map.values().iterator();
        List<QqchConstructionList> addList = new ArrayList<>();
        List<QqchConstructionList> updateList = new ArrayList<>();
        while(iterator.hasNext()){
            QqchConstructionList temp = iterator.next();
            temp.setVersion(version);
            if(temp.getId() != null){
                updateList.add(temp);
                continue;
            }
            temp.setId(IdWorker.createId());
            addList.add(temp);
        }
        if(CollectionUtils.isNotEmpty(addList))
            qqchConstructionListMapper.insertQqchConstructionListList(addList);
        if(CollectionUtils.isNotEmpty(updateList))
            qqchConstructionListMapper.updateQqchConstructionListList(updateList);
        //更新清单通过时间
        if(passTime != null)
            qqchConstructionListMapper.updatePassTime(passTime,version);
    }

    /**
     * 获取施工方案清单中危大等级为危大、超危大的方案数据
     * @return
     */
    @Override
    public List<QqchConstructionList> getBigDangerLevelConstructionList() {
        // 获取方案清单最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion(TN);
        return qqchConstructionListMapper.getBigDangerLevelConstructionList(maxVersion);
    }
}
