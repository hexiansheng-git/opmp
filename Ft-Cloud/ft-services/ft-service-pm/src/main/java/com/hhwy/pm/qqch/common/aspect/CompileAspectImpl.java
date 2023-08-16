package com.hhwy.pm.qqch.common.aspect;


import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
@Order // 保证此切面在事务开始之后执行,在事务结束之前执行完成
public class CompileAspectImpl {

    private static CommonMapper commonMapper;
    private static IQqchModuleConfirmCaseService moduleConfirmCaseService;
    private static IQqchReviewService reviewService;
    

    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
        moduleConfirmCaseService = SpringUtils.getBean(IQqchModuleConfirmCaseService.class);
        reviewService = SpringUtils.getBean(IQqchReviewService.class);
    }

    /**
     * 定义切点
     */

    @Pointcut("@annotation(com.hhwy.pm.qqch.common.aspect.CompileAspect)")
    public void doAspect() {
    }

    @Before("doAspect() && @annotation(compileAspect)")
    public void doBefore(JoinPoint joinPoint, CompileAspect compileAspect) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        StringBuffer sb = new StringBuffer();
        String tableName = compileAspect.tableName();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) continue;
            // 如果参数类型属于前期策划编制模块
            if (arg instanceof CompileEntity) {
                CompileEntity arg1 = (CompileEntity) arg;
                if (CompileOptEnum.LIST.equals(compileAspect.type())) {
                    beforeList(arg1, tableName);
                }
                if (CompileOptEnum.SAVE.equals(compileAspect.type())) {
                    commonMapper.deleteByVersion(tableName, (arg1).getVersion());
                    
                    // moduleConfirmCaseService.addConfirmRecord(arg1.getModuleIdentity(),reviewService.getStage());
                }
            }

//            if (arg instanceof List) {
//                List list = (List) arg;
//                // 为空 返回
//                if (CollectionUtils.isEmpty(list)) return;
//                Object o = list.get(0);
//                if (o instanceof CompileEntity) {
//                    List<CompileEntity> compileEntityList = (List<CompileEntity>) arg;
//                    if (CompileOptEnum.SAVE_LIST.equals(compileAspect.type())) {
//                        commonMapper.deleteByVersion(tableName, compileEntityList.get(0).getVersion());
//                    }
//                }
//            }


            sb.append(i == args.length - 1 ? arg.toString() : arg.toString() + ", ");
        }
        log.info("{}开始执行了, 参数是{}, 当前线程是{}",
                joinPoint.getTarget().getClass().getName() + "." + methodName,
                sb,
                Thread.currentThread().getId());
    }


    private void beforeList(CompileEntity arg, String tableName) {
        arg.setVersion(VersionUtil.getVersion(tableName, arg.getVersion()));
    }

    @After("doAspect() && @annotation(compileAspect)")
    public void doAfter(JoinPoint joinPoint, CompileAspect compileAspect) {
        String methodName = joinPoint.getSignature().getName();
        log.info("{}执行结束了, 当前线程是{}",
                joinPoint.getTarget().getClass().getName() + "." + methodName,
                Thread.currentThread().getId());
    }


    @Around("@annotation(compileAspect)")
    public Object doAround(ProceedingJoinPoint joinPoint, CompileAspect compileAspect) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("{}环绕方法开始执行, 当前线程是{}",
                joinPoint.getTarget().getClass().getName() + "." + methodName,
                Thread.currentThread().getId());
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        String tableName = compileAspect.tableName();
        // 空数据特殊处理
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof List) {
                List list = (List) arg;
                if (list.get(0) instanceof CompileEntity) {
                    List<CompileEntity> compileEntityList = (List<CompileEntity>) arg;
                    if (CompileOptEnum.SAVE_LIST.equals(compileAspect.type())) {
                        commonMapper.deleteByVersion(tableName, compileEntityList.get(0).getVersion());
                    }
                    if (compileEntityList.size() == 1 && PmConstant.MINUS_ONE.equals(compileEntityList.get(0).getSubmitFlag())) {
                        args[i] = Collections.emptyList();
                    }
                }
            }
        }
        
        Object result = joinPoint.proceed(args);
        log.info("方法响应结果为{}", result);
        if (CompileOptEnum.TREE.equals(compileAspect.type())) {
            if (result instanceof List) {
                List list = (List) result;
                // 为空 返回
                if (CollectionUtils.isEmpty(list)) return list;
                Object o = list.get(0);
                if (o instanceof CompileEntity) {
                    List<CompileEntity> compileEntityList = (List<CompileEntity>) result;
                    // 集合转树形结构
                    return TreeUtil.build(compileEntityList, null);
                }
            }
        }
        
        
        if (result instanceof CompileEntity) {
            CompileEntity res = (CompileEntity) result;
            // 设置当前阶段
            res.setStageIdentity(reviewService.getStage());
            return res;
        }
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

}
