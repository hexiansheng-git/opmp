package com.hhwy.system.market.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.system.mapper.MaterialInfoMapper;
import com.hhwy.system.market.MarketSurvey;
import com.hhwy.system.market.mapper.MarketSurveyMapper;
import com.hhwy.system.market.service.IMarketSurveyService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 市场调查Service业务层处理
 * 
 * @author lcf
 * @date 2022-11-30
 */
@Service
public class MarketSurveyServiceImpl implements IMarketSurveyService {
    @Autowired
    private MarketSurveyMapper marketSurveyMapper;
    @Autowired
    private MaterialInfoMapper materialInfoMapper;
    @Autowired
    private RedisUtils redisUtils;

    private Logger logger= LoggerFactory.getLogger(MarketSurveyServiceImpl.class);

    /**
     * 查询市场调查
     * 
     * @param id 市场调查ID
     * @return 市场调查
     */
    @Override
    public MarketSurvey selectMarketSurveyById(Long id) {
        return marketSurveyMapper.selectMarketSurveyById(id);
    }

    /**
     * 查询市场调查列表
     * 
     * @param marketSurvey 市场调查
     * @return 市场调查
     */
    @Override
    public List<MarketSurvey> selectMarketSurveyList(MarketSurvey marketSurvey) {
        return marketSurveyMapper.selectMarketSurveyList(marketSurvey);
    }

    /**
     * 新增市场调查
     * 
     * @param marketSurvey 市场调查
     * @return 结果
     */
    @Override
    public int insertMarketSurvey(MarketSurvey marketSurvey) {
        marketSurvey.setId(IdWorker.createId());
        marketSurvey.setCreateTime(DateUtils.getNowDate());
        marketSurvey.setPtVar1(SecurityUtils.getUserName());
        marketSurvey.setCreateUser(SecurityUtils.getUserId().toString());
        return marketSurveyMapper.insertMarketSurvey(marketSurvey);
    }

    /**
     * 修改市场调查
     * 
     * @param marketSurvey 市场调查
     * @return 结果
     */
    @Override
    public int updateMarketSurvey(MarketSurvey marketSurvey) {
        marketSurvey.setUpdateTime(DateUtils.getNowDate());
        return marketSurveyMapper.updateMarketSurvey(marketSurvey);
    }

    /**
     * 删除市场调查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMarketSurveyByIds(String ids) {
        return marketSurveyMapper.deleteMarketSurveyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除市场调查信息
     * 
     * @param id 市场调查ID
     * @return 结果
     */
    public int deleteMarketSurveyById(Long id) {
        return marketSurveyMapper.deleteMarketSurveyById(id);
    }

    /**
     * 市场信息查询
     *
     * @param marketSurvey
     * @return
     */
    @Override
    public List<MarketSurvey> selectMarketInfo(MarketSurvey marketSurvey) {
        return marketSurveyMapper.selectMarketInfo(marketSurvey);
    }

    @Override
    public AjaxResult importData(List<MarketSurvey> list) {
        if(CollectionUtils.isEmpty(list)){
            return new AjaxResult(PmsConstant.WARN_CODE,"导入数据不能为空");
        }
        String msg="";
        int t=1;
        for (int i = 0; i < list.size(); i++) {
            t=t+i;
            MarketSurvey marketSurvey = list.get(i);
            String materialCode = marketSurvey.getMaterialCode();
            if(StringUtils.isBlank(materialCode)){
                msg=msg+"第"+t+"行设备编码不能为空;";
            }
            String place = marketSurvey.getPlace();
            if(StringUtils.isBlank(place)){
                msg=msg+"第"+t+"行调查地点不能为空;";
            }
            Date surveyDate = marketSurvey.getSurveyDate();
            if(null==surveyDate){
                msg=msg+"第"+t+"行调查日期不能为空;";
            }
            String factory = marketSurvey.getFactory();
            if(StringUtils.isBlank(factory)){
                msg=msg+"第"+t+"行生产厂家不能为空;";
            }
            BigDecimal marketPrice = marketSurvey.getMarketPrice();
            if(null==marketPrice){
                msg=msg+"第"+t+"行市场报价不能为空;";
            }
        }

        //校验设备编码是否正确
        ArrayList<String> materialCodeList = (ArrayList)list.stream().map(e -> e.getMaterialCode()).collect(Collectors.toList());
        for (int i = 0; i < materialCodeList.size(); i++) {
            String s = materialCodeList.get(i);
            Object materialInfoRedis = redisUtils.hGet("materialInfoRedis", s);
            if(null==materialInfoRedis){
                msg="设备编码"+s+"不正确";
            }
        }
        if(StringUtils.isNotBlank(msg)){
            logger.info("【市场调查】导入异常数据信息："+msg);
            return new AjaxResult(PmsConstant.WARN_CODE,msg);
        }
        List<MarketSurvey> data = handleData(materialCodeList ,list);
        //数据批量入库
        int i = marketSurveyMapper.batchInsert(data);
        return AjaxResult.success(i);
    }

    /**
     * 数据处理
     *
     * @param materialCodeList
     * @param list
     * @return
     */
    private List<MarketSurvey> handleData(ArrayList<String> materialCodeList ,List<MarketSurvey> list) {
        //批量查询信息
        List<MaterialInfo> materialInfoList = materialInfoMapper.selectMaterialInfoListByCodes(materialCodeList);
        Map<String, List<MaterialInfo>> map = materialInfoList.stream().collect(Collectors.groupingBy(MaterialInfo::getMaterialCode));
        for (int i = 0; i < list.size(); i++) {
            MarketSurvey survey = list.get(i);
            survey.setCreateUser(SecurityUtils.getUserId().toString());
            survey.setCreateTime(DateUtils.getNowDate());
            survey.setPtVar1(SecurityUtils.getUserName());
            String materialCode = list.get(i).getMaterialCode();
            List<MaterialInfo> infoList = map.get(materialCode);
            if(CollectionUtils.isNotEmpty(infoList)){
                survey.setMaterialName(infoList.get(0).getMaterialName());
            }
            survey.setId(IdWorker.createId());
        }
        return list;
    }

}
