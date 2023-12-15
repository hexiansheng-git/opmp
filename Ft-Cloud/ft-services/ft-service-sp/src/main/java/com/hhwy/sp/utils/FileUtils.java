//package com.hhwy.sp.utils;
//
//import cn.hutool.http.ContentType;
//import cn.hutool.http.HttpResponse;
//import com.alibaba.fastjson.JSONObject;
//import com.hhwy.common.core.utils.SpringUtils;
//import com.hhwy.common.core.utils.StringUtils;
//import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.utils.HttpClientUtil;
//import com.hhwy.utils.file.FtFile;
//import com.hhwy.utils.file.FtFileProperties;
//import com.hhwy.utils.http.HttpRequestUtils;
//import org.apache.commons.collections4.map.HashedMap;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.util.EntityUtils;
//import org.springframework.boot.system.ApplicationHome;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class FileUtils {
//
//    public static FtFileProperties bean;
//
//    static {
//        bean = SpringUtils.getBean(FtFileProperties.class);
//    }
//
//    /**
//     * 附件处理
//     *
//     * @param fileGroupIds
//     * @return
//     */
//    public  List<File>  getFileByGroupIds(List<String> fileGroupIds){
//        // 参数
//        Map<String, Object> params = new HashedMap<>(1);
//        params.put("fileGroupIds", fileGroupIds);
//        // 请求头
//        Map<String, String> headers = new HashedMap<>(1);
//        headers.put("content-type", "application/json");
//        // 请求接口 获取结果
//        String httpRes = HttpRequestUtils.post(bean.getUrl() + bean.getFileInfoByGroupIdsPath(), headers, params);
//        JSONObject jsonObject = JSONObject.parseObject(httpRes, JSONObject.class);
//        String code = jsonObject.getString(AjaxResult.CODE_TAG);
//        List<FtFile> list=new ArrayList<>();
//        if ("0".equals(code)) {
//            // 结果转map
//            JSONObject mapFileInfo = JSONObject.parseObject(jsonObject.getString(AjaxResult.DATA_TAG), JSONObject.class);
//            for (String k : mapFileInfo.keySet()) {
//                list.addAll(JSONObject.parseArray(mapFileInfo.getString(k), FtFile.class));
//            }
//        }
//        //附件地址
//        File templateDir = null;
//        final File finalTemplateDir = templateDir;
//        System.out.println(finalTemplateDir+"111");
//        String jarPath = new ApplicationHome(getClass()).getDir().getPath();
//        templateDir = new File(jarPath+"/home");
//        if(!templateDir.exists())
//            templateDir.mkdir();
//        List<File> fileList=new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//           String fileId="" ;
//           File file= getFile(list.get(i).getFileId(),new com.hhwy.utils.HttpClientUtil.HandlerResponse<File>(){
//                @Override
//                public File handler(CloseableHttpResponse response) throws IOException {
//                    String innerFileName = "2e92a0225f0546f99c976cf4cc4ebf64";
//                    //获取要保存的缓存路径
//                    File file = new File(finalTemplateDir.getPath()+"/"+innerFileName);
//                    return file;
//                }
//            });
//            fileList.add(file);
//        }
//
//        return fileList;
//    }
//
//
//
//
//    /**
//     * 根据附件id 获取该附件的inputstream
//     * @param fileId
//     * @param handlerResponse
//     * @return
//     */
//    public static  <T>T getFile(String fileId, com.hhwy.utils.HttpClientUtil.HandlerResponse<T> handlerResponse){
//        System.out.println("11"+bean.getUrl() + "/fileext/" + fileId);
//        T result = HttpClientUtil.send(bean.getUrl() + "/fileext/list/" + "2e92a0225f0546f99c976cf4cc4ebf64", "get", null, handlerResponse);
//        return result;
//    }
//}
//
