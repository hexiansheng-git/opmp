//package com.hhwy.pm.qqch.common.aspect;
//
//
//import com.hhwy.common.core.utils.SpringUtils;
//import com.hhwy.common.core.utils.StringUtils;
//import com.hhwy.pm.common.mapper.CommonMapper;
//import com.hhwy.pm.qqch.common.domain.CompileEntity;
//import com.hhwy.pm.qqch.utils.VersionUtil;
//import com.hhwy.utils.EntityUtils;
//import com.hhwy.utils.exception.CustomBusinessException;
//import com.hhwy.utils.myEnum.InitVersionConstant;
//import com.hhwy.utils.objectUtil.ObjectNullUtil;
//import com.hhwy.utils.tree.TreeUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Slf4j
//@Component
//@Aspect
//public class CompileAspectImpl1 {
//
//    private static CommonMapper commonMapper;
//
//    static {
//        commonMapper = SpringUtils.getBean(CommonMapper.class);
//    }
//
//    /**
//     * 定义切点
//     */
//
//    @Pointcut("@annotation(com.hhwy.pm.qqch.common.aspect.CompileAspect)")
//    public void doAspect() {
//    }
//
//    @Before("doAspect() && @annotation(compileAspect)")
//    public void doBefore(JoinPoint joinPoint, CompileAspect compileAspect) {
//        String methodName = joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//        StringBuffer sb = new StringBuffer();
//        String tableName = compileAspect.tableName();
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            // 如果参数类型属于前期策划编制模块
//            if (arg instanceof CompileEntity) {
//
//                if (CompileOptEnum.LIST.equals(compileAspect.type())) {
//                    beforeList((CompileEntity) arg, tableName);
//                }
//                if (CompileOptEnum.SAVE.equals(compileAspect.type())) {
//                    beforeSave((CompileEntity) arg, tableName);
//                }
//            }
//            
//            if (arg instanceof List) {
//                List list = (List) arg;
//                // 为空 返回
//                if (CollectionUtils.isEmpty(list)) return;
//                Object o = list.get(0);
//                if (o instanceof CompileEntity) {
//                    List<CompileEntity> compileEntityList = (List<CompileEntity>) arg;
//                    if (CompileOptEnum.SAVE_LIST.equals(compileAspect.type())) {
//                        beforeSaveList(compileEntityList, tableName);
//                    }
//                    if (CompileOptEnum.SAVE_TREE.equals(compileAspect.type())) {
//                        compileEntityList = TreeUtil.treeToList(compileEntityList);
//                        beforeSaveList(compileEntityList, tableName);
//                    }
//                    arg = compileEntityList;
//                }
//            }
//
//
//            sb.append(i == args.length - 1 ? arg.toString() : arg.toString() + ", ");
//        }
//        log.info("{}开始执行了, 参数是{}, 当前线程是{}",
//                joinPoint.getTarget().getClass().getName() + "." + methodName,
//                sb,
//                Thread.currentThread().getId());
//    }
//
//    private void beforeSaveList(List<CompileEntity> compileEntityList, String tableName) {
//        EntityUtils.setCreateUpdateInfo(compileEntityList);
//        CompileEntity compileEntity = compileEntityList.get(0);
//        String submitFlag = compileEntity.getSubmitFlag();
//        for (CompileEntity entity : compileEntityList) {
//            entity.setVersion(ObjectNullUtil.isEmpty(entity.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : entity.getVersion());
//            entity.setSubmitFlag(entity.getSubmitFlag() == null ? submitFlag : entity.getSubmitFlag());
//            this.setValidVersion(entity);
//        }
//        commonMapper.deleteByVersion(tableName, compileEntity.getVersion());
//    }
//
//
//    private void setValidVersion(CompileEntity entity) {
//        String submitFlag = entity.getSubmitFlag();
//        if (StringUtils.isEmpty(submitFlag)) throw new RuntimeException("提交状态不能为空");
//
//        switch (submitFlag) {
//            case "0":
//                //业务保存
//                entity.setValid(entity.getVersion().compareTo(BigDecimal.ONE) == 0 ? "1" : "0");
//                break;
//            case "1":
//                // 业务确认
//                entity.setValid("1");
//                break;
//            case "2":
//                // 提交
//                entity.setValid("0");
//                break;
//            default:
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "标识不符合规范");
//        }
//    }
//
//    private void beforeSave(CompileEntity arg, String tn) {
//        EntityUtils.setCreateUpdateInfo(arg);
//        arg.setVersion(ObjectNullUtil.isEmpty(arg.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : arg.getVersion());
//        this.setValidVersion(arg);
//        commonMapper.deleteByVersion(tn, arg.getVersion());
//    }
//
//
//    private void beforeList(CompileEntity arg, String tableName) {
//        arg.setVersion(VersionUtil.getVersion(tableName, null));
//    }
//
//    @After("doAspect() && @annotation(compileAspect)")
//    public void doAfter(JoinPoint joinPoint, CompileAspect compileAspect) {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("{}执行结束了, 当前线程是{}",
//                joinPoint.getTarget().getClass().getName() + "." + methodName,
//                Thread.currentThread().getId());
//    }
//
//
//    @Around("@annotation(compileAspect)")
//    public Object doAround(ProceedingJoinPoint joinPoint, CompileAspect compileAspect) throws Throwable {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("{}环绕方法开始执行, 当前线程是{}",
//                joinPoint.getTarget().getClass().getName() + "." + methodName,
//                Thread.currentThread().getId());
//        //获取方法参数值数组
//        Object[] args = joinPoint.getArgs();
//        Object result = joinPoint.proceed(args);
//        log.info("方法响应结果为{}", result);
//        if (CompileOptEnum.TREE.equals(compileAspect.type())) {
//            if (result instanceof List) {
//                List list = (List) result;
//                // 为空 返回
//                if (CollectionUtils.isEmpty(list)) return list;
//                Object o = list.get(0);
//                if (o instanceof CompileEntity) {
//                    List<CompileEntity> compileEntityList = (List<CompileEntity>) result;
//                    // 集合转树形结构
//                    return TreeUtil.build(compileEntityList, 0L);
//                }
//            }
//        }
//        //如果这里不返回result，则目标对象实际返回值会被置为null
//        return result;
//    }
//
//}
