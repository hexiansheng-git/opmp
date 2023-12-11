package com.hhwy.pm.xmsl.wbs;


import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.core.utils.file.FileUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class test {

    public static void main(String[] args) throws Exception {
//        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
//                "projectId","sssss",
//                "wbsList","111")), ContentType.APPLICATION_JSON);
//        System.out.println(FileUtils.readFile(stringEntity.getContent()));
        List<QqchOrganizationList> list  = new ArrayList<>();
        QqchOrganizationList  l = new QqchOrganizationList();
        l.setId(1L);
        l.setPid(null);


        QqchOrganizationList  l2 = new QqchOrganizationList();
        l2.setId(2L);
        l2.setPid(1L);
        list.add(l2);
        list.add(l);

        TreeUtil.buildLeaf(list, null).stream().forEach(r->{
            System.out.println(r.getId());
            System.out.println(r.getChildren()==null?"0":r.getChildren().size());
        });

//        QqchChangeDetail t = new QqchChangeDetail();
//        QqchChangeDetail t1 = new QqchChangeDetail();
//        QqchChangeDetail t2 = new QqchChangeDetail();
//        t.setChildren(Arrays.asList(t1,t2));
//        t.setId(1L);
//        t1.setId(11L);
//        t2.setId(12L);
//
//        QqchChangeDetail s = new QqchChangeDetail();
//        QqchChangeDetail s1 = new QqchChangeDetail();
//        QqchChangeDetail s11 = new QqchChangeDetail();
//        s.setChildren(Arrays.asList(s1));
//        s1.setChildren(Arrays.asList(s11));
//        s.setId(2L);
//        s1.setId(22L);
//        s11.setId(222L);
//
//        List<QqchChangeDetail> list = Arrays.asList(t,s);
//        List<QqchChangeDetail> list1 = new ArrayList<>();
//         menuTree2List(list,list1);
//        list1.stream().forEach(r-> System.out.println(r.getId()));


    }

    private static void menuTree2List(List<QqchChangeDetail> list, List<QqchChangeDetail> resuList){
        if(CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            QqchChangeDetail temp = list.get(i);
            resuList.add(temp);
            menuTree2List(temp.getChildren(),resuList);
        }
    }

    //标准wbs转项目wbs
    private static void Twbs2Xmsl(){
        List<XmslWbs> list = new ArrayList<>();
        ZipSecureFile.setMinInflateRatio(0.001);
        EasyExcel.read("f:/f.xlsx", TWbs.class, new ReadListener<TWbs>() {
            @Override
            public void invoke(TWbs tWbs, AnalysisContext analysisContext) {
                XmslWbs wbs = new XmslWbs();
                wbs.setPartCode(tWbs.getCode());
                wbs.setName(tWbs.getName());
                wbs.setCode( tWbs.getCode().replace("JZ-","100").replaceAll("[U,P,S]","-"));;
                //JZ-U01P06S04
                if(wbs.getPartCode().length() == 15){
                    int len = wbs.getCode().length();
                    wbs.setCode(wbs.getCode().substring(0,len-3)+"-"+wbs.getCode().substring(len-3));
                }
                list.add(wbs);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doReadSync();

        EasyExcel.write(new File("f:/resu.xlsx"),XmslWbs.class).sheet().doWrite(list);
    }

    private static void buildXmWbs(int size){
        int startCode = 100;
        int nowSize = 0;
//        List<String> pcodeList = new ArrayList<String>;
//        Map<String,XmslWbs>
    }
}
