package com.hhwy.pm.word.export.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.hhwy.pm.word.export.domain.FileDto;
import com.hhwy.pm.word.export.domain.ProjectWordData;
import com.hhwy.pm.word.export.service.ExportWordService;
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

import javax.servlet.http.HttpServletResponse;
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
            Configure config = Configure.builder().bind("materialsAmountList",policy).bind("engineeringAmountList",policy).build();
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
        List<Map<String,PictureRenderData>> listMap = new ArrayList<>();
        for (PictureRenderData pictureRenderData : locationPictureList) {
            Map<String,PictureRenderData> map = new HashMap<>();
            map.put("locationPicture",pictureRenderData);
            listMap.add(map);
        }
        projectWordData.setLocationPictureList(listMap);
    }

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
            PictureRenderData pictureRenderData = Pictures.ofStream(is).size(180, 180).create();
            pictureRenderDataList.add(pictureRenderData);
        }
        return pictureRenderDataList;
    }
}
