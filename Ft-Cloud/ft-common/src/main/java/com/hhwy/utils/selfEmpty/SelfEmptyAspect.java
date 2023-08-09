package com.hhwy.utils.selfEmpty;

import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.BaseEntity;
import jdk.nashorn.internal.objects.annotations.Where;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MyDataScopeAspect
 * @Description: 自定义注解拦截
 * @author: zxb
 * @date: 2020/12/28
 * @version:V1.0
 */
@Aspect
@Component
public class SelfEmptyAspect {

    private Logger logger= LoggerFactory.getLogger(SelfEmptyAspect.class);

    public SelfEmptyAspect() {
    }

    @Pointcut("@annotation(com.hhwy.utils.selfEmpty.SelfEmpty)")
    public void selfEmpty() {
    }

    @Before("selfEmpty()")
    public void doBefore(JoinPoint point) throws Throwable {
        this.handleParamsData(point);
    }

    protected void handleParamsData(JoinPoint joinPoint) {
        SelfEmpty selfEmpty = this.getAnnotationLog(joinPoint);
        if (selfEmpty != null) {
            Object args = joinPoint.getArgs()[0];
            if(args instanceof Where){
                Where where = (Where) joinPoint.getArgs()[0];

            }else if(args instanceof BaseEntity){
                BaseEntity baseEntity = (BaseEntity)joinPoint.getArgs()[0];
                Class clazz = selfEmpty.clazz();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if(field.getType().toString().equals("class java.lang.String")){
                        //实体类字段类型为字符串，取出来去前后空格
                        String fieldName = field.getName();
                        String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try{
                            Method getMethod = clazz.getMethod(getMethodName);
                            String ss = (String) getMethod.invoke(baseEntity);//获取参数
                            if(StringUtils.isNotBlank(ss)){
                                ss = ss.trim();
                                String setMethodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);//
                                Method setMethod = clazz.getMethod(setMethodName, String.class);//获取set方法
                                setMethod.invoke(baseEntity, ss);//重新赋值
                            }
                        }catch (Exception e){
                            //反射异常，不做处理
                            //e.printStackTrace();
                            logger.warn("反射异常");
                        }
                    }
                }

            }else if(args instanceof Map){
                Signature signature = joinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature)signature;
                Method method = methodSignature.getMethod();
                //获取该方法中带有泛型的参数
                Type[] types = method.getGenericParameterTypes();
                logger.info("11"+JSON.toJSONString(types));
                for (Type type:types) {
                    if(type instanceof ParameterizedType){
                        ParameterizedType pType=(ParameterizedType)type;
                        //获取Map泛型
                        Type[] typeArguments = pType.getActualTypeArguments();
                        boolean equals = typeArguments[1].getTypeName().equals("java.lang.String");
                        if(equals){
                            //获取map
                            Map param = (HashMap)joinPoint.getArgs()[0];
                            for (Object map:param.keySet()) {
                                logger.info("key->"+map+"value--->"+param.get(map));
                                String str = (String)param.get(map);
                                if(StringUtils.isNotBlank(str)){
                                    str = str.trim();
                                    param.put(map,str);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private SelfEmpty getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        return method != null ? method.getAnnotation(SelfEmpty.class) : null;
    }
}
