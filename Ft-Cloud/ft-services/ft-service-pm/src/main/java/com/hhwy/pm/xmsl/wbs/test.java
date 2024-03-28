package com.hhwy.pm.xmsl.wbs;


import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.executor.ExcelWriteAddExecutor;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.core.utils.file.FileUtils;
import com.hhwy.enums.QyzsBtnEnum;
import com.hhwy.pm.gencode.domain.GenCode;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.WeatherUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.batik.transcoder.keys.StringKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.omg.SendingContext.RunTime;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public class test {

    public static void main(String[] args) throws Exception {


//        WeatherUtil weatherUtil = new WeatherUtil();
//        WeatherUtil.Weather weather = weatherUtil.getWeatherByTude("37.42", "55.76");
//        System.out.println(weather.getWea());
//        System.out.println(weather.getTem());
        
        XmslWbs t = new XmslWbs();
        t.setLevel(1);
        XmslWbs t1 = new XmslWbs();
        t1.setLevel(5);
        XmslWbs t2 = new XmslWbs();
        t2.setLevel(3);
        List<XmslWbs> g = Arrays.asList(t,t1,t2);
        g.sort(Comparator.comparingInt(r->r.getLevel()));
        g.forEach(s->{
            System.out.println(s.getLevel());
        });
    }
    
    
    private static void kettleTest2Pro() throws Exception{
        String proDir = "E:\\Document\\项管\\物设数据同步\\pdi-ce-8.2.0.0-342\\fuck";
        File testDirFile = new File("E:\\Document\\项管\\物设数据同步\\pdi-ce-8.2.0.0-342\\test");
        File[] files = testDirFile.listFiles();
        BufferedReader reader = null;
        BufferedWriter writer = null;
        //
        ArrayList<String> nearList = new ArrayList<String>(4);
        Function<String,String> put = g->{
            if(nearList.size() > 3)
                nearList.remove(nearList.get(0));
            nearList.add(g);
            return g;
        };
        try{
            for (int i = 0; i < files.length; i++) {
                File temp = files[i];
                if(reader!=null) reader.close();
                if(writer!=null) writer.close();
                reader = new BufferedReader(new FileReader(temp));
                writer = new BufferedWriter(new FileWriter(proDir+"\\"+temp.getName()));
                while(true){
                    String line = reader.readLine();
                    if(line == null){
                        nearList.clear();
                        writer.flush();
                        break;
                    }
                    put.apply(line);
                    boolean isWushe = nearList.stream().anyMatch(r->r.indexOf("test_omems-system")>-1);
                    String uname = isWushe?"etl":"root";
//                    line = line.replace("物设测试", "物设生产")
//                        .replace("10.11.238.63", "10.11.239.28")
//                        .replace("test_omems-system", "omems-system")
//                        .replace("<username>root</username>","<username>"+uname+"</username>")
//                        .replace("<password>Encrypted 2be98afc86acf9a93b239ad76d697ac94</password>",
//                                 "<password>Encrypted 2be98afc86aa7f2b3984bfe228aad8f8b</password>");
//                    line = line.replace("项管测试", "项管生产")
//                            .replace("111.160.45.98", "10.11.239.194")
//                            .replace("<port>10006</port>", "<port>3306</port>")
//                            .replace("<attribute>10006</attribute>", "<attribute>3306</attribute>")
//                            .replace("<username>root</username>","<username>"+uname+"</username>")
//                            .replace("<password>Encrypted 2be98afc86aa7f28ca30eb750da95a394</password>",
//                                     "<password>Encrypted 2be98afc86aa79a8cbc008e218cc1fb94</password>");
                    if(isWushe){
                        line = line.replace("物设测试", "物设测试")
                            .replace("10.11.238.63", "10.11.238.63")
                            .replace("<username>root</username>","<username>etl</username>")
                            .replace("<password>Encrypted 2be98afc86acf9a93b239ad76d697ac94</password>",
                                     "<password>Encrypted 2be98afc86aa7f2e4a311b9698cc2fd8e</password>");
                    }else{
                        line = line.replace("项管测试", "项管测试")
                                .replace("111.160.45.98", "10.0.1.118")
                                .replace("<port>10006</port>", "<port>10001</port>")
                                .replace("<attribute>10006</attribute>", "<attribute>10001</attribute>")
                                .replace("<username>root</username>","<username>root</username>")
                                .replace("<password>Encrypted 2be98afc86aa7f28ca30eb750da95a394</password>",
                                        "<password>Encrypted 2be98afc86aa7f28ca30eb750da95a394</password>");    
                    }
                    
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
                writer.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
            writer.close();
        }
        
    }
    
    private static void manuPush(){
        String jsonStr = "{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"projectId\":\"PJ2018006076\",\"type\":null,\"wbsList\":[{\"wbsCode\":\"100\",\"wbsName\":\"埃塞MS项目通则\",\"parentObjectId\":\"\",\"objectId\":\"374793\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"埃塞MS项目保险费\",\"parentObjectId\":\"374793\",\"objectId\":\"374794\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"埃塞MS项目按合同条款规定，提供建筑工程一切保险\",\"parentObjectId\":\"374794\",\"objectId\":\"374795\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"埃塞MS项目按合同条款规定，提供第三方责任保险\",\"parentObjectId\":\"374794\",\"objectId\":\"374796\",\"children\":[]}]},{\"wbsCode\":\"002\",\"wbsName\":\"埃塞MS项目工程管理\",\"parentObjectId\":\"374793\",\"objectId\":\"374797\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"埃塞MS项目竣工文件\",\"parentObjectId\":\"374797\",\"objectId\":\"374798\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"埃塞MS项目施工环保费\",\"parentObjectId\":\"374797\",\"objectId\":\"374799\",\"children\":[]},{\"wbsCode\":\"003\",\"wbsName\":\"埃塞MS项目安全生产费\",\"parentObjectId\":\"374797\",\"objectId\":\"374800\",\"children\":[]},{\"wbsCode\":\"004\",\"wbsName\":\"埃塞MS项目信息化系统（暂估价）\",\"parentObjectId\":\"374797\",\"objectId\":\"374801\",\"children\":[]},{\"wbsCode\":\"005\",\"wbsName\":\"埃塞MS项目其他费用\",\"parentObjectId\":\"374797\",\"objectId\":\"374802\",\"children\":[]}]},{\"wbsCode\":\"003\",\"wbsName\":\"埃塞MS项目临时工程与设施\",\"parentObjectId\":\"374793\",\"objectId\":\"374803\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"埃塞MS项目临时道路修建、养护与拆除（包括原道路的养护）\",\"parentObjectId\":\"374803\",\"objectId\":\"374804\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"埃塞MS项目临时占地\",\"parentObjectId\":\"374803\",\"objectId\":\"374805\",\"children\":[]},{\"wbsCode\":\"003\",\"wbsName\":\"埃塞MS项目临时供电设施架设、维护与拆除\",\"parentObjectId\":\"374803\",\"objectId\":\"374806\",\"children\":[]},{\"wbsCode\":\"004\",\"wbsName\":\"埃塞MS项目电信设施的提供、维修与拆除\",\"parentObjectId\":\"374803\",\"objectId\":\"374807\",\"children\":[]},{\"wbsCode\":\"005\",\"wbsName\":\"埃塞MS项目临时供水与排污设施\",\"parentObjectId\":\"374803\",\"objectId\":\"374808\",\"children\":[]},{\"wbsCode\":\"006\",\"wbsName\":\"埃塞MS项目大型桥梁辅助措施（钢便桥、钢平台、围堰）\",\"parentObjectId\":\"374803\",\"objectId\":\"374809\",\"children\":[]}]}]},{\"wbsCode\":\"300\",\"wbsName\":\"K20-K30路面工程\",\"parentObjectId\":\"\",\"objectId\":\"374810\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"K20-K30垫层\",\"parentObjectId\":\"374810\",\"objectId\":\"374811\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"K20-K30底基层\",\"parentObjectId\":\"374810\",\"objectId\":\"374812\",\"children\":[]},{\"wbsCode\":\"003\",\"wbsName\":\"K20-K30基层\",\"parentObjectId\":\"374810\",\"objectId\":\"374813\",\"children\":[]},{\"wbsCode\":\"004\",\"wbsName\":\"K20-K30透层\",\"parentObjectId\":\"374810\",\"objectId\":\"374814\",\"children\":[]},{\"wbsCode\":\"005\",\"wbsName\":\"K20-K30粘层\",\"parentObjectId\":\"374810\",\"objectId\":\"374815\",\"children\":[]},{\"wbsCode\":\"006\",\"wbsName\":\"K20-K30封层\",\"parentObjectId\":\"374810\",\"objectId\":\"374816\",\"children\":[]},{\"wbsCode\":\"007\",\"wbsName\":\"K20-K30面层\",\"parentObjectId\":\"374810\",\"objectId\":\"374817\",\"children\":[]},{\"wbsCode\":\"008\",\"wbsName\":\"K20-K30路肩、中央分隔带回填土、及路缘石\",\"parentObjectId\":\"374810\",\"objectId\":\"374818\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"K20-K30路肩\",\"parentObjectId\":\"374818\",\"objectId\":\"374819\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"K20-K30中央分隔带回填\",\"parentObjectId\":\"374818\",\"objectId\":\"374820\",\"children\":[]},{\"wbsCode\":\"003\",\"wbsName\":\"K20-K30路缘石\",\"parentObjectId\":\"374818\",\"objectId\":\"374821\",\"children\":[]}]}]},{\"wbsCode\":\"500\",\"wbsName\":\"K35+900隧道工程\",\"parentObjectId\":\"\",\"objectId\":\"374822\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"K35+900洞口与明洞工程\",\"parentObjectId\":\"374822\",\"objectId\":\"374823\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"K35+900洞口、明洞开挖\",\"parentObjectId\":\"374823\",\"objectId\":\"374824\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"K35+900洞外排水\",\"parentObjectId\":\"374823\",\"objectId\":\"374825\",\"children\":[]},{\"wbsCode\":\"003\",\"wbsName\":\"K35+900洞口坡面防护\",\"parentObjectId\":\"374823\",\"objectId\":\"374826\",\"children\":[]},{\"wbsCode\":\"004\",\"wbsName\":\"K35+900洞门墙及装饰\",\"parentObjectId\":\"374823\",\"objectId\":\"374827\",\"children\":[]},{\"wbsCode\":\"005\",\"wbsName\":\"K35+900洞口套拱及管棚\",\"parentObjectId\":\"374823\",\"objectId\":\"374828\",\"children\":[]},{\"wbsCode\":\"006\",\"wbsName\":\"K35+900地基处理\",\"parentObjectId\":\"374823\",\"objectId\":\"374829\",\"children\":[]},{\"wbsCode\":\"007\",\"wbsName\":\"K35+900明洞衬砌\",\"parentObjectId\":\"374823\",\"objectId\":\"374830\",\"children\":[{\"wbsCode\":\"001\",\"wbsName\":\"K35+900明洞拱部及边墙\",\"parentObjectId\":\"374830\",\"objectId\":\"374831\",\"children\":[]},{\"wbsCode\":\"002\",\"wbsName\":\"K35+900明洞仰拱及铺底\",\"parentObjectId\":\"374830\",\"objectId\":\"374832\",\"children\":[]}]}]}]}]}}";
        JSONObject main =  (JSONObject)JSONObject.parse(jsonStr);
        JSONArray json =  (JSONArray)((JSONObject)main.get("data")).get("wbsList");
        Map<String,String> map = new HashMap<>();
        iter(null,json,map);
        for(String k : map.keySet()){
            System.out.println("update xmsl_wbs set pt_var4 = '"+map.get(k) +"' where code = '"+k+"';");
        }
    }
    private static void iter(String pcode,JSONArray array,Map<String,String> map){
        for (int i = 0; i < array.size(); i++) {
            JSONObject sb = (JSONObject)array.get(i);
            String prefix = pcode==null?"":pcode+"-";
            String code = prefix+sb.get("wbsCode");
            map.put(code,sb.get("objectId").toString());
            iter(code,(JSONArray)sb.get("children"),map);
        }
        
        
    }
    
    private static void checkKattle() throws Exception{
        File file = new File("E:\\Document\\项管\\物设数据同步\\pdi-ce-8.2.0.0-342\\test");
        File[] files = file.listFiles();
        BufferedReader reader = null;
        try{
            int i = -1;
            Set<String> exNameSet = new HashSet<>();
            for (File t : files) {
                reader = new BufferedReader(new FileReader(t));
                exNameSet.add(t.getName());
                String line = "";
                boolean find = false;
                while(true){
                    line = reader.readLine();
                    if(line == null)
                        break;
                    if(line.indexOf("del_flag=0") > -1
                            || line.indexOf("del_flag =0") > -1 || line.indexOf("del_flag = 0") > -1){
                        find= true;
                    }
                }
                if(!find)
                    System.out.println(t.getName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null)
                reader.close();
        }
    }
    
    private static void summaryZ(){
        //构建表列
//        String  string =  "CREATE TABLE `hehe` (\n" ;
//        String s = "交易流水号\t第三方交易流水号\t充电方式\t订单状态\t订单渠道\t订单来源\t业务类型\t交易电量（kwh）\t电费\t服务费\t交易金额\t实扣金额\t实扣电费\t实扣服务费\t优惠金额\t优惠电费\t优惠服务费\t支付流水号\t支付方式\t冻结金额\t订单支付时间\t优惠类型\t优惠名称\t优惠金额详情\t订单创建时间\t充电开始时间\t充电结束时间\t订单上送时间\t交易结束原因\t是否后付费\t用户类型\t产权单位名称\t产权单位编码\t运营单位编码\t监管单位编码\t充电桩编号\t充电站ID\t充电站\t电费计费模型Id\t服务费计费模型Id\t尖电量（kwh）\t峰电量（kwh）\t平电量（kwh）\t谷电量（kwh）\t深谷电量（kwh）\t抄表电量（kwh）\t电表总起值\t电表总止值\t用户编码\t卡号\t手机号\t单位用户证件号\tVIN码\t车牌号\t清分状态\t清分ID\t清分时间\t开票状态\t发票序列标识\t充电枪编号";
//        String[] ss = s.split("\t");
//        for (int i = 0; i < ss.length; i++) {
//            string+="   `a"+i+"` varchar(200) default NULL comment '"+ss[i]+"'";
//            if(i != ss.length-1)
//                string+=",\n";
//            else string+="\n";
//        }
//        string+=") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;";
//        System.out.println(string);
        //构建文档生成sql脚本
        StringBuilder sb = new StringBuilder();
        String[] ds = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        sb.append("=concat(\"insert into hehe values(\"");
        for (int i = 0; i < 61; i++) {
            int index = i/26;
            String prefix = index>0?ds[index-1]:"";
            String suf  = ds[i%26];
            String s =   prefix+suf;
            String gg = i==60?"":",";
            sb.append(",\"'\","+(s+2)+",\"'"+gg+"\"");
        }
        sb.append(",\");\"");
        System.out.println(sb);
    }
    
    //根据现有数据生成需要推送的json
    private static void formatPushData(){
        //json来源 : 
        //select concat('[',group_concat( concat("{code:'",self_code ,"',name:'",name,"',id:'",id,"',pid:'",parent_id ,"',objectId:'",ifnull(pt_var4,''),"'}") ) ,"]")
        //from xmsl_wbs where del_flag = 0 order by level,sort
        String s ="[{code:'100',name:'埃塞MS项目通则',id:'1767454257453338624',pid:'-1',objectId:''},{code:'300',name:'K20-K30路面工程',id:'1767454257457532928',pid:'-1',objectId:''},{code:'500',name:'K35+900隧道工程',id:'1767454257465921536',pid:'-1',objectId:''},{code:'001',name:'埃塞MS项目保险费',id:'1767454257465921537',pid:'1767454257453338624',objectId:''},{code:'002',name:'埃塞MS项目工程管理',id:'1767454257465921538',pid:'1767454257453338624',objectId:''},{code:'003',name:'埃塞MS项目临时工程与设施',id:'1767454257465921539',pid:'1767454257453338624',objectId:''},{code:'001',name:'K20-K30垫层',id:'1767454257465921540',pid:'1767454257457532928',objectId:''},{code:'002',name:'K20-K30底基层',id:'1767454257465921541',pid:'1767454257457532928',objectId:''},{code:'003',name:'K20-K30基层',id:'1767454257465921542',pid:'1767454257457532928',objectId:''},{code:'004',name:'K20-K30透层',id:'1767454257465921543',pid:'1767454257457532928',objectId:''},{code:'005',name:'K20-K30粘层',id:'1767454257465921544',pid:'1767454257457532928',objectId:''},{code:'006',name:'K20-K30封层',id:'1767454257465921545',pid:'1767454257457532928',objectId:''},{code:'007',name:'K20-K30面层',id:'1767454257465921546',pid:'1767454257457532928',objectId:''},{code:'008',name:'K20-K30路肩、中央分隔带回填土、及路缘石',id:'1767454257465921547',pid:'1767454257457532928',objectId:''},{code:'001',name:'K35+900洞口与明洞工程',id:'1767454257465921548',pid:'1767454257465921536',objectId:''},{code:'001',name:'埃塞MS项目按合同条款规定，提供建筑工程一切保险',id:'1767454257465921549',pid:'1767454257465921537',objectId:''},{code:'002',name:'埃塞MS项目按合同条款规定，提供第三方责任保险',id:'1767454257465921550',pid:'1767454257465921537',objectId:''},{code:'001',name:'埃塞MS项目竣工文件',id:'1767454257465921551',pid:'1767454257465921538',objectId:''},{code:'002',name:'埃塞MS项目施工环保费',id:'1767454257465921552',pid:'1767454257465921538',objectId:''},{code:'003',name:'埃塞MS项目安全生产费',id:'1767454257465921553',pid:'1767454257465921538',objectId:''},{code:'004',name:'埃塞MS项目信息化系统（暂估价）',id:'1767454257465921554',pid:'1767454257465921538',objectId:''},{code:'005',name:'埃塞MS项目其他费用',id:'1767454257465921555',pid:'1767454257465921538',objectId:''},{code:'001',name:'埃塞MS项目临时道路修建、养护与拆除（包括原道路的养护）',id:'1767454257465921556',pid:'1767454257465921539',objectId:''},{code:'002',name:'埃塞MS项目临时占地',id:'1767454257465921557',pid:'1767454257465921539',objectId:''},{code:'003',name:'埃塞MS项目临时供电设施架设、维护与拆除',id:'1767454257465921558',pid:'1767454257465921539',objectId:''},{code:'004',name:'埃塞MS项目电信设施的提供、维修与拆除',id:'1767454257465921559',pid:'1767454257465921539',objectId:''},{code:'005',name:'埃塞MS项目临时供水与排污设施',id:'1767454257465921560',pid:'1767454257465921539',objectId:''},{code:'006',name:'埃塞MS项目大型桥梁辅助措施（钢便桥、钢平台、围堰）',id:'1767454257465921561',pid:'1767454257465921539',objectId:''},{code:'001',name:'K20-K30路肩',id:'1767454257465921562',pid:'1767454257465921547',objectId:''},{code:'002',name:'K20-K30中央分隔带回填',id:'1767454257465921563',pid:'1767454257465921547',objectId:''},{code:'003',name:'K20-K30路缘石',id:'1767454257465921564',pid:'1767454257465921547',objectId:''},{code:'001',name:'K35+900洞口、明洞开挖',id:'1767454257465921565',pid:'1767454257465921548',objectId:''},{code:'002',name:'K35+900洞外排水',id:'1767454257465921566',pid:'1767454257465921548',objectId:''},{code:'003',name:'K35+900洞口坡面防护',id:'1767454257465921567',pid:'1767454257465921548',objectId:''},{code:'004',name:'K35+900洞门墙及装饰',id:'1767454257465921568',pid:'1767454257465921548',objectId:''},{code:'005',name:'K35+900洞口套拱及管棚',id:'1767454257465921569',pid:'1767454257465921548',objectId:''},{code:'006',name:'K35+900地基处理',id:'1767454257465921570',pid:'1767454257465921548',objectId:''},{code:'007',name:'K35+900明洞衬砌',id:'1767454257465921571',pid:'1767454257465921548',objectId:''},{code:'001',name:'K35+900明洞拱部及边墙',id:'1767454257465921572',pid:'1767454257465921571',objectId:''},{code:'002',name:'K35+900明洞仰拱及铺底',id:'1767454257465921573',pid:'1767454257465921571',objectId:''}]";
        List<Map> slist = JSONObject.parseArray(s, Map.class);
        List<Map> firstList = new ArrayList<>();
        Map<String,Map> idMap = new HashMap<>();
        System.out.println("sum:"+slist.size());
        for (int i = 0; i < slist.size(); i++) {
            Map temp = slist.get(i);
            String pid = temp.get("pid").toString();
            if(StringUtils.isBlank(pid) || pid.equals("-1"))
                firstList.add(temp);
            Map pmap = idMap.get(pid);
            String poid = ObjectUtils.nvlString(pmap==null?"":pmap.get("objectId"));
            temp.put("wbsCode", temp.get("code"));
            temp.put("wbsName", temp.get("name"));
            temp.put("parentObjectId", poid);
//            temp.put("parentObjectId", "");
//            temp.put("objectId", "");
            temp.put("children", new ArrayList<>());
            temp.remove("code");
            temp.remove("name");
            if(pmap != null){
                ((List)pmap.get("children")).add(temp);
            }
            if(StringUtils.isNotBlank(pid) && pmap == null){
                System.err.println(pid);
            }
            idMap.put(temp.get("id").toString(), temp);
        }
//        firstList.forEach(r->{
//            System.out.println(JSONObject.toJSONString(r));
//        });
        System.out.println(JSONObject.toJSONString(firstList));
    }
    
    //格式化已有数据
    private static Map<String,String> formatMain(String json){
        List<Map> list = JSONObject.parseArray(json, Map.class);
        Map<String,String> idMap = new HashMap<>();
        list.stream().forEach(r->{
            idMap.put(ObjectUtils.nvlString(r.get("name"))
                    ,ObjectUtils.nvlString(r.get("id")));
        });
        return idMap;
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
