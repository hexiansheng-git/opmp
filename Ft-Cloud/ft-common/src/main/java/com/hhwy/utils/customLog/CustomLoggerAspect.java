package com.hhwy.utils.customLog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.ServletUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.redis.service.RedisService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.InterfaceLog.InterfaceLog;
import com.hhwy.feign.service.ILogServiceApi;
import com.hhwy.system.api.domain.SysOperLog;
import com.hhwy.utils.idworker.IdWorker;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CustomLoggerAspect {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ILogServiceApi logServiceApi;

    private static final Logger log = LoggerFactory.getLogger(CustomLoggerAspect.class);


    @Pointcut("@annotation(com.hhwy.utils.customLog.CustomLogger)")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around(value = "logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Long st = System.currentTimeMillis();
        CustomLogger customLogger = this.getAnnotationLog(joinPoint);
        if(null==customLogger){
            return null;
        }

        String title = customLogger.title();
        Object result = null;
        //方法请求参数
        Object[] args = joinPoint.getArgs();
        //方法返回结果
        String url = request.getRequestURI().toString();
        try{
            result = joinPoint.proceed(args);
        }catch (Exception e){
            //日志操作处理
            Object finalResult = result;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //数据解析
                    handleLogOperate(st, finalResult,title,request,args,joinPoint, "500");
                }
            });
            thread.start();

            throw e;
        }

        //日志操作处理
        Object finalResult1 = result;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //数据解析
                handleLogOperate(st, finalResult1,title,request,args,joinPoint);
            }
        });
        thread.start();

        return result;
    }

    /**
     * 日志操作处理
     *
     * @param st 开始时间
     * @param result
     * @param title 方法标题
     * @param request request
     * @param args 请求参数
     * @param joinPoint
     */
    private void handleLogOperate(long st,Object result,String title,HttpServletRequest request,Object[] args,
                                  ProceedingJoinPoint joinPoint){
        long et = System.currentTimeMillis();
        long sec=et-st;
        String code ="";
        if(result==null){
            code="200";//导出方法一般是没有返回值的
        }else{
            if(result instanceof AjaxResult){
                code = ((AjaxResult) result).get("code").toString();
            }else if (result instanceof JSONObject){
                code = "200";
            }else if (result instanceof TableDataInfo){
                code = String.valueOf(((TableDataInfo) result).getCode());
            }else if (result instanceof R){
                code = String.valueOf(((R) result).getCode());
            }

        }

        InterfaceLog interfaceLog=new InterfaceLog();
        interfaceLog.setId(IdWorker.createId());
        interfaceLog.setInterfaceName(title);
        interfaceLog.setInterfaceStatus(Long.parseLong(code));
        try{
            interfaceLog.setReq(JSON.toJSONString(args[0]));
        }catch (Exception e1){
            //有可能出现没有传参的问题转json字符串会出现问题
            interfaceLog.setReq("");
        }
        interfaceLog.setRes(JSON.toJSONString(result));

        interfaceLog.setTimeConsume(sec+"");

        MethodSignature signature =(MethodSignature)joinPoint.getSignature();
        interfaceLog.setMethod(signature.getDeclaringTypeName()+ "." + signature.getName());
        interfaceLog.setRequestType(request.getMethod());
        interfaceLog.setRequestUrl(request.getRequestURL().toString());
        interfaceLog.setOperateIp(request.getHeader("requestRemoteIp"));
        interfaceLog.setOsName(System.getProperty("os.name"));
        interfaceLog.setOsVersion(System.getProperty("os.version"));
        interfaceLog.setBrowser(request.getHeader("user-agent"));
        interfaceLog.setInterfaceDesc("systemRequest");
        String userName = request.getHeader("userName");
        if(StringUtils.isNotBlank(userName)){
            interfaceLog.setUserName(userName);
        }
        //租户标识
        interfaceLog.setPtVar2(request.getHeader("tenantKey"));

        try{
            if(code.equals("200")){
                    logServiceApi.insertSuccessLog(interfaceLog);
            }else {
                    logServiceApi.insertFailLog(interfaceLog);
            }
        }catch (Exception e){

        }

    }


    private void handleLogOperate(long st,Object result,String title,HttpServletRequest request,Object[] args,
                                  ProceedingJoinPoint joinPoint, String code){
        long et = System.currentTimeMillis();
        long sec=et-st;

        InterfaceLog interfaceLog=new InterfaceLog();
        interfaceLog.setId(IdWorker.createId());
        interfaceLog.setInterfaceName(title);
        interfaceLog.setInterfaceStatus(Long.parseLong(code));
        try{
            interfaceLog.setReq(JSON.toJSONString(args[0]));
        }catch (Exception e1){
            //有可能出现没有传参的问题转json字符串会出现问题
            interfaceLog.setReq("");
        }
        interfaceLog.setRes(JSON.toJSONString(result));

        interfaceLog.setTimeConsume(sec+"");

        MethodSignature signature =(MethodSignature)joinPoint.getSignature();
        interfaceLog.setMethod(signature.getDeclaringTypeName()+ "." + signature.getName());
        interfaceLog.setRequestType(request.getMethod());
        interfaceLog.setRequestUrl(request.getRequestURL().toString());
        interfaceLog.setOperateIp(request.getHeader("requestRemoteIp"));
        interfaceLog.setOsName(System.getProperty("os.name"));
        interfaceLog.setOsVersion(System.getProperty("os.version"));
        interfaceLog.setBrowser(request.getHeader("user-agent"));
        interfaceLog.setInterfaceDesc("systemRequest");

        String userName = request.getHeader("userName");
        if(StringUtils.isNotBlank(userName)){
            interfaceLog.setUserName(userName);
        }
        //租户标识
        interfaceLog.setPtVar2(request.getHeader("tenantKey"));

        try{
            if(code.equals("200")){
                logServiceApi.insertSuccessLog(interfaceLog);
            }else {
                logServiceApi.insertFailLog(interfaceLog);
            }
        }catch (Exception e){

        }

    }

    @Before(value = "logPointCut()")
    public void before(JoinPoint joinPoint){

    }


    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = this.argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(params);
        }

    }

    private CustomLogger getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        return method != null ? (CustomLogger)method.getAnnotation(CustomLogger.class) : null;
    }

    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for(int i = 0; i < paramsArray.length; ++i) {
                if (StringUtils.isNotNull(paramsArray[i]) && !this.isFilterObject(paramsArray[i])) {
                    try {
                        Object jsonObj = JSON.toJSON(paramsArray[i]);
                        params = params + jsonObj.toString() + " ";
                    } catch (Exception var5) {
                    }
                }
            }
        }

        return params.trim();
    }

    public boolean isFilterObject(Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else {
            Iterator iter;
            if (Collection.class.isAssignableFrom(clazz)) {
                Collection collection = (Collection)o;
                iter = collection.iterator();
                if (iter.hasNext()) {
                    return iter.next() instanceof MultipartFile;
                }
            } else if (Map.class.isAssignableFrom(clazz)) {
                Map map = (Map)o;
                iter = map.entrySet().iterator();
                if (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry)iter.next();
                    return entry.getValue() instanceof MultipartFile;
                }
            }

            return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
        }
    }

}
