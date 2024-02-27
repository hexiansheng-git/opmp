package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark 勘察设计-设计工程量管理-工程量清单明细
 */
@Service
public class KcsjEngineeringQuantitiesBillDetailServiceImpl implements IKcsjEngineeringQuantitiesBillDetailService {

    @Autowired
    private KcsjEngineeringQuantitiesBillDetailMapper kcsjEngineeringQuantitiesBillDetailMapper;


    public KcsjEngineeringQuantitiesBillDetail getKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        return kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    public List<KcsjEngineeringQuantitiesBillDetail> getDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        List<Long> delIdList = kcsjEngineeringQuantitiesBillDetail.getDelIdList();
        List<KcsjEngineeringQuantitiesBillDetail> details = getIds(delIdList);
        return details;
    }

    public List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {

        return kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetail);
    }


    public List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailListByMainId(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {

        return kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailListByMainId(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int insertKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setId(IdWorker.createId());
        kcsjEngineeringQuantitiesBillDetail.setCreateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setCreateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int insertKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList) {
        for (KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail : kcsjEngineeringQuantitiesBillDetailList) {
            kcsjEngineeringQuantitiesBillDetail.setId(IdWorker.createId());
            kcsjEngineeringQuantitiesBillDetail.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            kcsjEngineeringQuantitiesBillDetail.setCreateUser(SecurityUtils.getUserId().toString());
            kcsjEngineeringQuantitiesBillDetail.setCreateTime(DateUtils.getNowDate());
            kcsjEngineeringQuantitiesBillDetail.setDelFlag("0");
        }
        return kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailList);
    }

    @Transactional
    public int updateKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int updateKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList) {
        for (KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail : kcsjEngineeringQuantitiesBillDetailList) {
            kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
            kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailList);
    }

    @Transactional
    public int deleteKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.deleteKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int deleteKcsjEngineeringQuantitiesBillDetailByPks(List<Long> kcsjEngineeringQuantitiesBillDetailPkList) {
        return kcsjEngineeringQuantitiesBillDetailMapper.deleteKcsjEngineeringQuantitiesBillDetailByPks(kcsjEngineeringQuantitiesBillDetailPkList);
    }

    @Transactional
    public void deleteByIds(List<Long> delIdList) {
        List<Long> list = new ArrayList<>();
        //获取所有父子级数据
        List<KcsjEngineeringQuantitiesBillDetail> ids = getIds(delIdList);
        for (int i = 0; i < ids.size(); i++) {
            KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
            detail.setId(ids.get(i).getId());
            detail.setUpdateUser(SecurityUtils.getUserId() + "");
            detail.setUpdateTime(DateUtils.getNowDate());
            detail.setDelFlag("1");
            list.add(detail.getId());
        }
        //删除
        if (!CollectionUtils.isEmpty(list)) {
            kcsjEngineeringQuantitiesBillDetailMapper.deleteInfoData(list, SecurityUtils.getUserId().toString());
        }

    }

    private List<KcsjEngineeringQuantitiesBillDetail> getIds(List<Long> delIdList) {

        //查询出所有数据
        KcsjEngineeringQuantitiesBillDetail detail = new KcsjEngineeringQuantitiesBillDetail();
        List<KcsjEngineeringQuantitiesBillDetail> manageList = kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(detail);
        Map<String, KcsjEngineeringQuantitiesBillDetail> map = new HashMap<>();
        //将所有数据放进集合，id为key,对象为value
        manageList.stream().forEach(temp -> {
            map.put(temp.getId() + "", temp);
        });
        //该集合存放所有父子级数据
        //查询所有符合条件的数据
        List<KcsjEngineeringQuantitiesBillDetail> list = kcsjEngineeringQuantitiesBillDetailMapper.getIds(delIdList);
        List<KcsjEngineeringQuantitiesBillDetail> total = new ArrayList<>(list);
        for (KcsjEngineeringQuantitiesBillDetail experProgressManage : list) {
            findTotal(map, total, experProgressManage);
        }
        if (total.size() > 0) {
            total = TreeUtil.treeToListWithoutId(total);
            //total = total.stream().distinct().sorted(Comparator.comparing(KcsjEngineeringQuantitiesBillDetail::getSerialNumber)).collect(Collectors.toList());
        }
        return total;

    }


    public void findTotal(Map<String, KcsjEngineeringQuantitiesBillDetail> map, List<KcsjEngineeringQuantitiesBillDetail> total, KcsjEngineeringQuantitiesBillDetail detail) {
        //查找以当前数据的id为pid的数据
        Set<Map.Entry<String, KcsjEngineeringQuantitiesBillDetail>> entries = map.entrySet();
        for (Map.Entry<String, KcsjEngineeringQuantitiesBillDetail> entry : entries) {
            if (entry.getValue().getPid() != null && entry.getValue().getPid().equals(detail.getId())) {
                KcsjEngineeringQuantitiesBillDetail billDetail = map.get(entry.getKey());
                total.add(billDetail);
                findTotal(map, total, billDetail);
            }
        }
    }


    @Override
    public AjaxResult importData(MultipartFile file) {

        FtExcelUtil<KcsjEngineeringQuantitiesBillDetail> util = new FtExcelUtil<>(KcsjEngineeringQuantitiesBillDetail.class);
        List<KcsjEngineeringQuantitiesBillDetail> recordList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            recordList = util.importTreeExcel(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("导入失败");
        }
        //数据校验
        List<KcsjEngineeringQuantitiesBillDetail> records = new ArrayList<>();
        for (KcsjEngineeringQuantitiesBillDetail detail : recordList) {
            KcsjEngineeringQuantitiesBillDetail newDetail = new KcsjEngineeringQuantitiesBillDetail();
            BeanUtils.copyProperties(detail, newDetail);
            records.add(newDetail);
        }
        records = ListTreeUtil.formatList(
                records,
                KcsjEngineeringQuantitiesBillDetail::getIsAdd,
                KcsjEngineeringQuantitiesBillDetail::getId,
                KcsjEngineeringQuantitiesBillDetail::setId,
                KcsjEngineeringQuantitiesBillDetail::setPid,
                KcsjEngineeringQuantitiesBillDetail::getChildren,
                KcsjEngineeringQuantitiesBillDetail::setChildren);
        //删除原数据
        for (KcsjEngineeringQuantitiesBillDetail detail : records) {
            detail.setIsAdd("1");
            if (StringUtils.isEmpty(detail.getSerialNumber())) {
                throw new BaseException("序号不能为空");
            }
        }
        //构建树形返回数据
        records = ListTreeUtil.formatTree(
                records,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                KcsjEngineeringQuantitiesBillDetail::getChildren,
                KcsjEngineeringQuantitiesBillDetail::setChildren);

        return AjaxResult.success(records);
    }

}