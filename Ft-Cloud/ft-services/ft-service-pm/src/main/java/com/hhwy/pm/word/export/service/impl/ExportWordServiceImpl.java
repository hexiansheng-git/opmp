package com.hhwy.pm.word.export.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.hhwy.pm.word.export.domain.FileDto;
import com.hhwy.pm.word.export.domain.ProjectWordData;
import com.hhwy.pm.word.export.service.ExportWordService;
import com.hhwy.pm.xmsl.implement.domain.*;
import com.hhwy.pm.xmsl.implement.domain.vo.ImplementVo;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.pm.xmsl.project.service.IXmslProjectEngineeringAmountService;
import com.hhwy.pm.xmsl.project.service.IXmslProjectMaterialsAmountService;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExportWordServiceImpl implements ExportWordService {

    @Value("${fileService.fileUrl}")
    private String fileUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private IXmslProjectMaterialsAmountService xmslProjectMaterialsAmountService;

    @Autowired
    private IXmslProjectEngineeringAmountService xmslProjectEngineeringAmountService;

    @Autowired
    private IXmslTerrainLandformsService xmslTerrainLandformsService;


    @Override
    public void exportProjectQqch(HttpServletResponse response) throws UnsupportedEncodingException {
//        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("项目前期策划.docx", "UTF-8"));

        InputStream inputStream = null;
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("template/项目前期策划.docx");
            if(inputStream == null){
                throw new RuntimeException("获取文件失败！");
            }

            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder().bind("materialsAmountList",policy).bind("engineeringAmountList",policy)
                    .bind("terrainLandformsList",policy) // 地形地貌
                    .bind("mainTypicalGeologySurveyList",policy) // 主线典型地质勘察表
                    .bind("badGeologySurveyList",policy) // 不良地质调查表
                    .bind("mainStructureHydrologyList",policy) // 主要构造物水文条件
                    .bind("climateConditionList",policy) // 气候条件
                    .bind("basicFacilitiesConditionsList",policy) // 水、电、交通、通讯条件
                    .bind("constructionInterferenceList",policy) // 施工干扰
                    .build();
            XWPFTemplate template = XWPFTemplate.compile(inputStream,config);

            ProjectWordData projectWordData = new ProjectWordData();

            /*设置项目基本信息*/
            this.setProjectInfo(projectWordData);

            template.render(projectWordData);

            out = response.getOutputStream();
            bos = new BufferedOutputStream(out);
            template.write(bos);
            bos.flush();
            out.flush();
            PoitlIOUtils.closeQuietlyMulti(template, bos, out, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProjectInfo(ProjectWordData projectWordData){
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        Long id = projectInfo.getId();

        BeanUtils.copyProperties(projectInfo, projectWordData);

        /*设置项目开竣工日期*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startFormat = "";
        String completionFormat = "";
        Date startDate = projectInfo.getStartDate();
        Date completionDate = projectInfo.getCompletionDate();
        if(startDate != null){
            startFormat = sdf.format(startDate);
        }
        if(completionDate != null){
            completionFormat = sdf.format(completionDate);
        }
        projectWordData.setStartCompletionDate(startFormat + " 至 " + completionFormat);

        /*获取主要材料数据数据*/
        List<XmslProjectMaterialsAmount> materialsAmountList = xmslProjectMaterialsAmountService.getListByProjectInfoId(id);
        ListTreeUtil.preserveSerialNumber(materialsAmountList, XmslProjectMaterialsAmount::setSerialNumber);
        /*获取主要工程数量*/
        List<XmslProjectEngineeringAmount> engineeringAmountList = xmslProjectEngineeringAmountService.getListByProjectInfoId(id);
        engineeringAmountList = ListTreeUtil.preserveSerialNumber(
                engineeringAmountList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                XmslProjectEngineeringAmount::getChildren,
                XmslProjectEngineeringAmount::setChildren,
                XmslProjectEngineeringAmount::getSerialNumber,
                XmslProjectEngineeringAmount::setSerialNumber);
        projectWordData.setMaterialsAmountList(materialsAmountList);
        projectWordData.setEngineeringAmountList(engineeringAmountList);

        /*设置工程地理位置附件*/
        String locationImageGroupId = projectInfo.getLocationImageGroupId();
        List<PictureRenderData> locationPictureList = this.getPictureRenderDataList(locationImageGroupId);
        List<Map<String,PictureRenderData>> locationList = new ArrayList<>();
        for (PictureRenderData pictureRenderData : locationPictureList) {
            Map<String,PictureRenderData> map = new HashMap<>();
            map.put("locationPicture",pictureRenderData);
            locationList.add(map);
        }
        projectWordData.setLocationPictureList(locationList);

        /*设置工程结构形式附件*/
        String structureImageGroupId = projectInfo.getStructureImageGroupId();
        List<PictureRenderData> structurePictureList = this.getPictureRenderDataList(structureImageGroupId);
        List<Map<String,PictureRenderData>> structureList = new ArrayList<>();
        for (PictureRenderData pictureRenderData : structurePictureList) {
            Map<String,PictureRenderData> map = new HashMap<>();
            map.put("structurePicture",pictureRenderData);
            structureList.add(map);
        }
        projectWordData.setStructurePictureList(structureList);

        /*--实施条件--*/
        initImplement(projectWordData);


    }

    /*图片基础宽度*/
    private static final int BASE_WIDTH = 500;

    /**
     * 获取附件组id关联的所有图片流
     * @param fileGroupId
     * @return
     */
    public List<PictureRenderData> getPictureRenderDataList(String fileGroupId){
        List<PictureRenderData> pictureRenderDataList = new ArrayList<>();
        if(StringUtils.isBlank(fileGroupId)){
            return pictureRenderDataList;
        }
        String url = fileUrl + "list/" + fileGroupId;
        String jsonString = restTemplate.getForObject(url, String.class);
        List<FileDto> fileDtoList = JSONObject.parseArray(jsonString, FileDto.class);
        if(CollectionUtils.isEmpty(fileDtoList)){
            return pictureRenderDataList;
        }
        for (FileDto fileDto : fileDtoList) {
            String fileId = fileDto.getFileId();
            String urlF = fileUrl + fileId;
            ResponseEntity<byte[]> entity = restTemplate.getForEntity(urlF, byte[].class);
            byte[] body = entity.getBody();
            if(body == null){
                continue;
            }
            InputStream is = new ByteArrayInputStream(body);
            try {
                BufferedImage image = ImageIO.read(is);
                int width = image.getWidth();
                int height = image.getHeight();
                if(width > BASE_WIDTH){
                    double times = (double) width / BASE_WIDTH;
                    width = (int) Math.round(width / times);
                    height = (int) Math.round(height / times);
                }
                PictureRenderData pictureRenderData = Pictures.ofBufferedImage(image, PictureType.suggestFileType(fileDto.getExtension())).size(width, height).create();
                pictureRenderDataList.add(pictureRenderData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pictureRenderDataList;
    }

    /**
     * 初始实施条件数据
     */
    public void initImplement(ProjectWordData projectWordData) {
        ImplementVo implementVo = xmslTerrainLandformsService.getAllList();
        if(null == implementVo) {
            return;
        }
        /*地形地貌*/
        List<XmslTerrainLandforms> terrainLandformsList = implementVo.getTerrainLandformsList();

        if(CollectionUtils.isNotEmpty(terrainLandformsList)) {
            projectWordData.setTerrainLandformsList(terrainLandformsList);
            List<PictureRenderData> terrainLandformsPictureList = new ArrayList<>();
            for (XmslTerrainLandforms xmslTerrainLandforms : terrainLandformsList) {
                String fileGroupId = xmslTerrainLandforms.getFileGroupId();
                terrainLandformsPictureList.addAll(this.getPictureRenderDataList(fileGroupId));
            }
            List<Map<String,PictureRenderData>> terrainLandformsList4Picture = new ArrayList<>();
            for (PictureRenderData pictureRenderData : terrainLandformsPictureList) {
                Map<String,PictureRenderData> map = new HashMap<>();
                map.put("terrainLandformsPicture",pictureRenderData);
                terrainLandformsList4Picture.add(map);
            }
            projectWordData.setTerrainLandformsList4Picture(terrainLandformsList4Picture);
        }

        /*主线典型地质勘察表*/
        GeologicalCondition geologicalCondition = implementVo.getGeologicalCondition();
        if(null != geologicalCondition) {
            List<XmslMainTypicalGeologySurvey> mainTypicalGeologySurveyList = geologicalCondition.getMainTypicalGeologySurveyList();
            if(CollectionUtils.isNotEmpty(mainTypicalGeologySurveyList)) {
                projectWordData.setMainTypicalGeologySurveyList(mainTypicalGeologySurveyList);
                List<PictureRenderData> mainTypicalGeologySurveyPictureList = new ArrayList<>();
                for (XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurvey : mainTypicalGeologySurveyList) {
                    String fileGroupId = xmslMainTypicalGeologySurvey.getFileGroupId();
                    mainTypicalGeologySurveyPictureList.addAll(this.getPictureRenderDataList(fileGroupId));
                }
                List<Map<String,PictureRenderData>> mainTypicalGeologySurveyList4Picture = new ArrayList<>();
                for (PictureRenderData pictureRenderData : mainTypicalGeologySurveyPictureList) {
                    Map<String,PictureRenderData> map = new HashMap<>();
                    map.put("mainTypicalGeologySurveyPicture",pictureRenderData);
                    mainTypicalGeologySurveyList4Picture.add(map);
                }
                projectWordData.setMainTypicalGeologySurveyList4Picture(mainTypicalGeologySurveyList4Picture);
            }
        }

        /*不良地质调查表*/
        if(null != geologicalCondition) {
            List<XmslBadGeologySurvey> badGeologySurveyList = geologicalCondition.getBadGeologySurveyList();
            if(CollectionUtils.isNotEmpty(badGeologySurveyList)) {
                projectWordData.setBadGeologySurveyList(badGeologySurveyList);
                List<PictureRenderData> badGeologySurveyPictureList = new ArrayList<>();
                for (XmslBadGeologySurvey xmslBadGeologySurvey : badGeologySurveyList) {
                    String fileGroupId = xmslBadGeologySurvey.getFileGroupId();
                    badGeologySurveyPictureList.addAll(this.getPictureRenderDataList(fileGroupId));
                }
                List<Map<String,PictureRenderData>> badGeologySurveyList4Picture = new ArrayList<>();
                for (PictureRenderData pictureRenderData : badGeologySurveyPictureList) {
                    Map<String,PictureRenderData> map = new HashMap<>();
                    map.put("badGeologySurveyPicture",pictureRenderData);
                    badGeologySurveyList4Picture.add(map);
                }
                projectWordData.setBadGeologySurveyList4Picture(badGeologySurveyList4Picture);
            }
        }

        /*主要构造物水文条件*/
        List<XmslMainStructureHydrology> mainStructureHydrologyList = implementVo.getMainStructureHydrologyList();
        if(CollectionUtils.isNotEmpty(mainStructureHydrologyList)) {
            projectWordData.setMainStructureHydrologyList(mainStructureHydrologyList);
            List<PictureRenderData> mainStructureHydrologyPictureList = new ArrayList<>();
            for (XmslMainStructureHydrology xmslMainStructureHydrology : mainStructureHydrologyList) {
                String fileGroupId = xmslMainStructureHydrology.getFileGroupId();
                mainStructureHydrologyPictureList.addAll(this.getPictureRenderDataList(fileGroupId));
            }
            List<Map<String,PictureRenderData>> mainStructureHydrologyList4Picture = new ArrayList<>();
            for (PictureRenderData pictureRenderData : mainStructureHydrologyPictureList) {
                Map<String,PictureRenderData> map = new HashMap<>();
                map.put("mainStructureHydrologyPicture",pictureRenderData);
                mainStructureHydrologyList4Picture.add(map);
            }
            projectWordData.setMainStructureHydrologyList4Picture(mainStructureHydrologyList4Picture);
        }

        /*气候条件*/
        List<XmslClimateCondition> climateConditionList = implementVo.getClimateConditionList();
        if(CollectionUtils.isNotEmpty(climateConditionList)) {
            projectWordData.setClimateConditionList(climateConditionList);
//            List<PictureRenderData> climateConditionPictureList = new ArrayList<>();
//            for (XmslClimateCondition xmslClimateCondition : climateConditionList) {
//                String fileGroupId = xmslClimateCondition.getFileGroupId();
//                climateConditionPictureList.addAll(this.getPictureRenderDataList(fileGroupId));
//            }
//            List<Map<String,PictureRenderData>> climateConditionList4Picture = new ArrayList<>();
//            for (PictureRenderData pictureRenderData : climateConditionPictureList) {
//                Map<String,PictureRenderData> map = new HashMap<>();
//                map.put("climateConditionPicture",pictureRenderData);
//                climateConditionList4Picture.add(map);
//            }
//            projectWordData.setClimateConditionList4Picture(climateConditionList4Picture);
        }

        /*水、电、交通、通讯条件*/
        List<XmslBasicFacilitiesConditions> basicFacilitiesConditionsList = implementVo.getBasicFacilitiesConditionsList();
        if(CollectionUtils.isNotEmpty(basicFacilitiesConditionsList)) {
            projectWordData.setBasicFacilitiesConditionsList(basicFacilitiesConditionsList);
        }

        /*施工干扰*/
        List<XmslConstructionInterference> constructionInterferenceList = implementVo.getConstructionInterferenceList();
        if(CollectionUtils.isNotEmpty(constructionInterferenceList)) {
            projectWordData.setConstructionInterferenceList(constructionInterferenceList);
            List<PictureRenderData> constructionInterferencePictureList = new ArrayList<>();
            for (XmslConstructionInterference xmslConstructionInterference : constructionInterferenceList) {
                String fileGroupId = xmslConstructionInterference.getFileGroupId();
                constructionInterferencePictureList.addAll(this.getPictureRenderDataList(fileGroupId));
            }
            List<Map<String,PictureRenderData>> constructionInterferenceList4Picture = new ArrayList<>();
            for (PictureRenderData pictureRenderData : constructionInterferencePictureList) {
                Map<String,PictureRenderData> map = new HashMap<>();
                map.put("constructionInterferencePicture",pictureRenderData);
                constructionInterferenceList4Picture.add(map);
            }
            projectWordData.setConstructionInterferenceList4Picture(constructionInterferenceList4Picture);
        }

    }

}
