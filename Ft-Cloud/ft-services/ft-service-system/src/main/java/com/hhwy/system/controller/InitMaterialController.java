package com.hhwy.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.service.IMaterialCategoryService;
import com.hhwy.utils.http.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/initMaterial")
public class InitMaterialController {
    private Logger logger= LoggerFactory.getLogger(InitMaterialController.class);

    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiTDEwMDU1ODU0IiwiaXAiOiIxMjAuMjQ0LjIxOC42MSIsImp0aSI6ImYwNjEzNTI1LTFjNTItNGY2MS04MGU4LWQ0NjdjMTg5NmQ1NCIsImlhdCI6MTY2NzEwNTY1NSwic3ViIjoi55So5oi35L-h5oGvIn0.R1K8U8eDYI_JyJcD0h8fcc_yZ0R-U6ihWt3oQ5CAzBw";


    @Autowired
    private IMaterialCategoryService iMaterialCategoryService;

    @GetMapping("/initMaterialCategory")
    public AjaxResult initMaterialCategory(){
        iMaterialCategoryService.initMaterialCategory();
        return AjaxResult.success("初始化成功！");
    }

    @GetMapping("/initMaterial")
    public AjaxResult initMaterial(){
        iMaterialCategoryService.initMaterial();
        return AjaxResult.success("初始化成功！");
    }

    private static String root = "20190805001";
    public static void main(String[] args) throws InterruptedException {
//        requestCategory(root);
        requestMaterial("99");
    }

    public static void requestMaterial(String classificationCode) throws InterruptedException {
        int pageSize = 100000;
        for (int i = 0; i < 1; i++) {
            String url = "https://hwzjglpt.cfhec.net/ccmp_cfhec/api/materialDepot/permission/getMaterial";
            Map<String, String> headers = new HashMap<>();
            headers.put("token", token);
            headers.put("terminal", "1");
            Map<String, Object> requests = new HashMap<>();
            requests.put("classificationCode", classificationCode);
            requests.put("type", "1");
            requests.put("pageNum", (i + 1));
            requests.put("pageSize", pageSize);
            String str = HttpRequestUtils.post(url, headers, requests);
            JSONObject obj = JSONObject.parseObject(str);
            if("0".equals(String.valueOf(obj.get("status")))){
                //成功
                insertFilelog(str);
                Thread.sleep(1000);
            }
        }
    }

    public static void requestCategory(String pid) throws InterruptedException {
//        String url = "https://hwzjglpt.cfhec.net/ccmp_cfhec/api/materialDepot/permission/getMaterialTree";
        String url = "https://hwzjglpt.cfhec.net/ccmp_cfhec/api/accessories/permission/listAccessoriesTree?pid=7fee4e10-d0fd-11ec-b5f9-00ffa6ecc42d&_=1667283909179";
        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        headers.put("terminal", "1");
        Map<String, Object> requests = new HashMap<>();
        requests.put("pid", pid);
        requests.put("_", System.currentTimeMillis());
        String str = HttpRequestUtils.get(url, headers, requests);
        JSONObject obj = JSONObject.parseObject(str);
        if("0".equals(String.valueOf(obj.get("status")))){
            //成功
            List<MatCategory> list = JSONArray.parseArray(JSONObject.toJSONString(obj.get("data")), MatCategory.class);
            if(list != null && list.size() > 0){
                insertFilelog(str);
                for(MatCategory matCategory : list){
                    matCategory.setCode(matCategory.getParams().get("code"));
                    String id = matCategory.getId();
                    Thread.sleep(2000);
                    //递归请求
                    requestCategory(id);
                }
            }
        }
    }

    private static String filepath = "/Users/zxb/Desktop/java/initMaterial.txt";
    public static void insertFilelog(String obj) {
        long seek = 0;//获取当前文件的偏移量
        try {
            //写法1
            RandomAccessFile randomFile = new RandomAccessFile(filepath, "r");
            seek = randomFile.length();//文件当前偏移量
            randomFile.close();
            File file = new File(filepath);
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write( obj +  "\r\n");
            fileWriter.close();
            //写法2
            /*RandomAccessFile randomFile = new RandomAccessFile(path, "rw");
            seek = randomFile.length();//文件当前偏移量
            randomFile.writeBytes(obj.get("id") + ": " + obj.toJSONString() +  "\r\n");
            randomFile.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class MatCategory {
        private String id;
        private String code;
        private boolean isParent;
        private String name;
        private String pid;
        private String title;
        private Map<String, String> params;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isParent() {
            return isParent;
        }

        public void setParent(boolean parent) {
            isParent = parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }
    }


}
