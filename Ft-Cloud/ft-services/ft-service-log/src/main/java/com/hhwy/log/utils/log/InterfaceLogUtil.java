package com.hhwy.log.utils.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.domain.base.InterfaceLog.InterfaceLog;
import com.hhwy.log.constant.InterfaceConstant;
import com.hhwy.log.mapper.InterfaceLogMapper;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @ClassName : InterfaceLogUtil
 * @Description : 记录外部接口调用日志
 * @Author : zxb
 * @Date :  19:46
 * @Version : V1.0
 **/
public class InterfaceLogUtil {

    private static String filepath;
    private static String linux_network = "ens192";

    private static final Logger logger = LoggerFactory.getLogger(InterfaceLogUtil.class);
    private static Environment environment;
    static {
        environment = SpringUtils.getBean(Environment.class);
        filepath = environment.getProperty("log.url");
        if(StringUtils.isBlank(filepath)){
            filepath = "Users/zxb/a_workspace/cfhec/logs/interfacelog";
        }
    }

    private static InterfaceLogMapper interfaceLogMapper;

    private static long success = 200;
    private static long fail = 500;


    //记录成功日志
    public static void insertSuccessLog(String interFaceName,String req, String res) {
        insertLog(interFaceName,req,res,success, new SysTenant());
    }
    public static void insertSuccessLog(String interFaceName, String req, String res, SysTenant sysTenant) {
        insertLog(interFaceName, req, res, success, sysTenant);
    }
    public static void insertSuccessLog(InterfaceLog interfaceLog) {
        insertLog(interfaceLog, success);
    }
    //记录失败日志
    public static void insertFailLog(String interFaceName, String req, String res){
        insertLog(interFaceName, req, res, fail,new SysTenant());
    }
    public static void insertFailLog(String interFaceName, String req, String res, SysTenant sysTenant){
        insertLog(interFaceName, req, res, fail, sysTenant);
    }
    public static void insertFailLog(InterfaceLog interfaceLog) {
        insertLog(interfaceLog, fail);
    }




    /**
     * 根据id、日期、偏移量，读取对应的某行的日志
     *
     * @param date 日期
     * @param seek 偏移量
     * @param id id
     * @return
     */
    public static String readLineLog(String date, long seek, long id){
        String data = "";
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(filepath + "/" + date + ".log", "r");
            randomFile.seek(seek);//开始读取的文件偏移量
            String tmp = null;
            while ((tmp = randomFile.readLine()) != null) {
                String tt = new String(tmp.getBytes("ISO-8859-1"), "utf-8");
                if(tt.contains(id+"")){
                    data = tt;
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("异常"+e.getMessage());
        }finally {
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                   logger.error("异常"+e.getMessage());
                }
            }
        }
        return data;
    }
    private static void insertLog(InterfaceLog interfaceLog, long status){
        new Thread(() -> {
            try{
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datestr = df.format(date);

                interfaceLog.setInterfaceStatus(status);
                interfaceLog.setCreateTime(date);
                interfaceLog.setVar1(getServerIp());

                JSONObject obj = new JSONObject();
                obj.put("id",interfaceLog.getId());
                obj.put("req", interfaceLog.getReq());
                obj.put("res", interfaceLog.getRes());
                obj.put("time", datestr);
                obj.put("interFaceName", interfaceLog.getInterfaceName());
                obj.put("interFaceDesc", interfaceLog.getInterfaceDesc());
                //磁盘记录
                long seek = insertFilelog(obj, datestr.substring(0, 10));

                interfaceLog.setOffset(seek+"");//记录某行数据的偏移量，用来读取某行数据

                getInterfaceLogMapper().insertInterfaceLog(interfaceLog);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("接口调用插入日志失败...");
            } finally {
            }
        }).start();


    }
    private static void insertLog(String interFaceName, String req, String res, long status, SysTenant sysTenant){
        new Thread(() -> {
            try{
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datestr = df.format(date);
                InterfaceLog interfaceLog = new InterfaceLog();
                interfaceLog.setId(IdWorker.createId());
                interfaceLog.setInterfaceName(interFaceName);
                interfaceLog.setInterfaceDesc(InterfaceConstant.INTERFACES.get(interFaceName));
                interfaceLog.setInterfaceStatus(status);
                interfaceLog.setCreateTime(date);
                interfaceLog.setUpdateTime(date);
                interfaceLog.setVar1(getServerIp());
                JSONObject obj = new JSONObject();
                obj.put("id",interfaceLog.getId());
                obj.put("req", req);
                obj.put("res", res);
                obj.put("time", datestr);
                obj.put("interFaceName", interFaceName);
                obj.put("interFaceDesc", InterfaceConstant.INTERFACES.get(interFaceName));
                long seek = insertFilelog(obj, datestr.substring(0, 10));
                //记录某行数据的偏移量，用来读取某行数据
                interfaceLog.setOffset(seek+"");
                interfaceLog.setVar2(seek+"");
                try{
                    Long userId = SecurityUtils.getUserId();
                    if(null!=userId){
                        interfaceLog.setUserId(userId);
                    }
                    String userName = SecurityUtils.getUserName();
                    if(StringUtils.isNotBlank(userName)){
                        interfaceLog.setUserName(userName);
                    }
                }catch (Exception e1){

                }

                getInterfaceLogMapper().insertInterfaceLog(interfaceLog);

            } catch (Exception e) {
                logger.error("接口调用插入日志失败...");
                e.printStackTrace();
            } finally {
                //清除切换的数据源
                //DynamicDataSourceContextHolder.clearDataSourceTypeAndChangeType();
            }
        }).start();


    }

    private static InterfaceLogMapper getInterfaceLogMapper(){
        if(null==interfaceLogMapper){
            synchronized (InterfaceLogUtil.class){
                if(null==interfaceLogMapper){
                    interfaceLogMapper = SpringUtils.getBean(InterfaceLogMapper.class);
                }
            }
        }
        return interfaceLogMapper;
    }

    public static void main(String[] args) throws IOException {

            String str = readLineLog("2021-09-23", 40000000, 1440918862433619968l);

            if(StringUtils.isNotBlank(str)){
                str = str.replace("1440918862433619968: ", "");
                JSONObject jsonObject = (JSONObject) JSONObject.parse(str);
                System.out.println(jsonObject.get("req"));
                JSONObject req1 = (JSONObject) JSONObject.parseObject((String) jsonObject.get("req"));
                JSONArray reqs = (JSONArray) req1.get("laborAttencesList");
                String wid = "1436245828422602752,1436264849796763648,1436265597792030720,1436265798430887936,1436316601778573312,1436319837629714432,1436328416269045760,1436330765037211648,1437208325296099328,1437208548147990528,1437208744630030336,1437208895117463552,1437208998452531200,1437210572507844608,1437314228989071360,1437314923477864448,1437315249807298560,1437326090535309312";
                JSONArray jsonArray1 = new JSONArray();
                reqs.forEach(t -> {
                    JSONObject t1 = (JSONObject) t;
                    String workTeamId = String.valueOf(t1.get("workTeamId"));
                    if(wid.contains(workTeamId)){
                        jsonArray1.add(t1);
                    }

                });

                System.out.println(jsonArray1.toJSONString());
            }

    }

    public static long insertFilelog(JSONObject obj, String date) throws IOException {
        try {
            String path = filepath + "/" + date + ".log";
            File file = new File(path);
            if(!file.exists()){
                synchronized (InterfaceLog.class){
                    if(!file.exists()){
                        File file1 = new File(filepath);
                        if(!file.exists()){
                            file1.mkdirs();
                        }
                        file.createNewFile();
                    }
                }
            }
            synchronized (InterfaceLog.class){
                long seek = 0;//获取当前文件的偏移量
                try {
                    //写法1
                    RandomAccessFile randomFile = new RandomAccessFile(path, "r");
                    seek = randomFile.length();//文件当前偏移量
                    randomFile.close();
                    FileWriter fileWriter = new FileWriter(file,true);
                    fileWriter.write(obj.get("id") + ": " + obj.toJSONString() +  "\r\n");
                    fileWriter.close();
                    //写法2
                    /*RandomAccessFile randomFile = new RandomAccessFile(path, "rw");
                    seek = randomFile.length();//文件当前偏移量
                    randomFile.writeBytes(obj.get("id") + ": " + obj.toJSONString() +  "\r\n");
                    randomFile.close();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return seek;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取服务器地址
     *
     * @return Ip地址
     */
    public static String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return e.getMessage();
            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getIpByEthNum(linux_network); // 兼容Linux
            if (ip != null) {
                if(ip.equals("获取服务器IP错误")){
                    return "192.168.1.47";
                }
                return ip;
            }
        }
        return "获取服务器IP错误";
    }

    /**
     * 根据网络接口获取IP地址
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    private static String getIpByEthNum(String ethNum) {
        try {
            //NetworkInterface可以通过getNetworkInterfaces方法来枚举本机所有的网络接口
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            return e.getMessage();
        }
        return "获取服务器IP错误";
    }

}
