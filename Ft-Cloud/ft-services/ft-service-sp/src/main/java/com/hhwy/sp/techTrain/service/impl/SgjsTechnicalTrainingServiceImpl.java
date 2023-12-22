package com.hhwy.sp.techTrain.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;
import com.hhwy.sp.techTrain.mapper.SgjsTechnicalTrainingMapper;
import com.hhwy.sp.techTrain.service.ISgjsTechnicalTrainingService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wll-技术培训管理
 * @date 2023-12-07 18:05:53
 * @remark
 */
@Service
public class SgjsTechnicalTrainingServiceImpl implements ISgjsTechnicalTrainingService {

    @Autowired
    private SgjsTechnicalTrainingMapper sgjsTechnicalTrainingMapper;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsTechnicalTraining getSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining) {
        return sgjsTechnicalTrainingMapper.getSgjsTechnicalTraining(sgjsTechnicalTraining);
    }

    /**
     * 列表查询
     *
     * @param sgjsTechnicalTraining
     * @return
     */
    public List<SgjsTechnicalTraining> getSgjsTechnicalTrainingList(SgjsTechnicalTraining sgjsTechnicalTraining) {

        //查询技术培训列表数据
        List<SgjsTechnicalTraining> list = sgjsTechnicalTrainingMapper.getSgjsTechnicalTrainingList(sgjsTechnicalTraining);

        handleDict(list);

        return list;

    }

    private void handleDict(List<SgjsTechnicalTraining> list) {
        //字典项查询
        AjaxResult result = systemServiceApi.dictType(DictType.Technical_Training_Type);

        //存放字典项查询结果
        List<Map<String, Object>> dictDataList = null;

        if (result.get("code").toString().equals(Constant.SUCCESS_CODE)) {
            //data里面存放的是字典项对象（SysDictData）的集合 ：分别是专业培训和岗位培训对应的字典项对象
            //dictDataList集合来将字典项对象以键值的形式存在集合里面，键是字典项的属性，值是字典项的属性值（类型太多用的Object统一）

            dictDataList = (List<Map<String, Object>>) result.get("data");

        }

        //分别过滤岗位培训和专业培训所对应的字典项对象
        List<Map<String, Object>> oneList = dictDataList.stream().filter(e -> e.get("dictValue").equals("1")).collect(Collectors.toList());
        List<Map<String, Object>> twoList = dictDataList.stream().filter(e -> e.get("dictValue").equals("2")).collect(Collectors.toList());
        for (SgjsTechnicalTraining info : list) {

            String one = (String) oneList.get(0).get("dictValue");
            String two = (String) twoList.get(0).get("dictValue");
            String trainingType = info.getTrainingType();
            if (StringUtils.isEmpty(trainingType)) {
                continue;
            }
            if (trainingType.equals(one)) {
                String oneDictLabel = (String) oneList.get(0).get("dictLabel");
                info.setTrainingType(oneDictLabel);
            }
            if (trainingType.equals(two)) {
                String twoDictLabel = (String) twoList.get(0).get("dictLabel");
                info.setTrainingType(twoDictLabel);
            }

        }
    }

    @Transactional
    public AjaxResult insertSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining) {

        //获取项目信息
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        if (CollectionUtils.isEmpty(prjInfo)) {
            return AjaxResult.error("获取项目信息异常");
        }

        Long projectId = Long.parseLong(prjInfo.get("projectId") + "");
        sgjsTechnicalTraining.setProjectId(projectId);
        sgjsTechnicalTraining.setProjectName((String) prjInfo.get("projectName"));

        //id没有默认值会报错
        sgjsTechnicalTraining.setId(IdWorker.createId());
        //用工具填充创建人，创建时间等字段
        sgjsTechnicalTraining = (SgjsTechnicalTraining) new AddBaseInfoUtil<SgjsTechnicalTraining>().addBaseEntity(sgjsTechnicalTraining);

        return AjaxResult.success(sgjsTechnicalTrainingMapper.insertSgjsTechnicalTraining(sgjsTechnicalTraining));
    }

    @Transactional
    public int insertSgjsTechnicalTrainingList(List<SgjsTechnicalTraining> sgjsTechnicalTrainingList) {

        for (SgjsTechnicalTraining sgjsTechnicalTraining : sgjsTechnicalTrainingList) {
            sgjsTechnicalTraining.setId(IdWorker.createId());
            sgjsTechnicalTraining.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalTraining.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalTrainingMapper.insertSgjsTechnicalTrainingList(sgjsTechnicalTrainingList);
    }

    @Transactional
    public int updateSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining) {
        sgjsTechnicalTraining = (SgjsTechnicalTraining) new AddBaseInfoUtil<SgjsTechnicalTraining>().updateBaseEntity(sgjsTechnicalTraining);
        return sgjsTechnicalTrainingMapper.updateSgjsTechnicalTraining(sgjsTechnicalTraining);
    }

    @Transactional
    public int updateSgjsTechnicalTrainingList(List<SgjsTechnicalTraining> sgjsTechnicalTrainingList) {
        for (SgjsTechnicalTraining sgjsTechnicalTraining : sgjsTechnicalTrainingList) {
            sgjsTechnicalTraining.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalTraining.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalTrainingMapper.updateSgjsTechnicalTrainingList(sgjsTechnicalTrainingList);
    }

    @Transactional
    public int deleteSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining) {

        sgjsTechnicalTraining.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalTraining.setUpdateTime(DateUtils.getNowDate());
        sgjsTechnicalTraining.setDelTime(DateUtils.getNowDate());
        sgjsTechnicalTraining.setDelUser(SecurityUtils.getUserName());
        return sgjsTechnicalTrainingMapper.deleteSgjsTechnicalTraining(sgjsTechnicalTraining);
    }

    @Transactional
    public int deleteSgjsTechnicalTrainingByPks(List<Long> sgjsTechnicalTrainingPkList) {

        //设置数据删除人
        String delUser = SecurityUtils.getSysUser().getNickName();
        return sgjsTechnicalTrainingMapper.deleteSgjsTechnicalTrainingByPks(sgjsTechnicalTrainingPkList, delUser);
    }


    /**
     * 根据Id集合查询数据
     * @param ids
     * @return
     */
    @Override
    public List<SgjsTechnicalTraining> getIds(List<Long> ids) {
        List<SgjsTechnicalTraining> byIds = sgjsTechnicalTrainingMapper.getByIds(ids);
        handleDict(byIds);
        return byIds;

    }
}
