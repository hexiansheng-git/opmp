package com.hhwy.pm.qqch.common.aspect;


import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Aspect
@Order // 保证此切面在事务开始之后执行,在事务结束之前执行完成
public class CompileAspectImpl {

    private static CommonMapper commonMapper;
    private static IQqchModuleConfirmCaseService moduleConfirmCaseService;
    private static IQqchReviewService reviewService;
    private static RedisUtils redisUtils;


    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
        moduleConfirmCaseService = SpringUtils.getBean(IQqchModuleConfirmCaseService.class);
        reviewService = SpringUtils.getBean(IQqchReviewService.class);
        redisUtils = SpringUtils.getBean(RedisUtils.class);
    }

    /**
     * 定义切点
     */

    @Pointcut("@annotation(com.hhwy.pm.qqch.common.aspect.CompileAspect)")
    public void doAspect() {
    }

    public void beforeProceed(JoinPoint joinPoint, CompileAspect compileAspect) {
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
                if (CompileOptEnum.LIST.equals(compileAspect.type()) || CompileOptEnum.TREE.equals(compileAspect.type())) {
                    beforeList(arg1, tableName);
                }
                if (CompileOptEnum.SAVE.equals(compileAspect.type()) && compileAspect.delFlag()) {
                    this.addConfirmAndUpdateFinishNum(arg1);

                    commonMapper.deleteByVersion(tableName, (arg1).getVersion());
                }
            }

            if (arg instanceof List) {
                List list = (List) arg;
                if (list.get(0) instanceof CompileEntity) {
                    List<CompileEntity> compileEntityList = (List<CompileEntity>) arg;
                    CompileEntity compileEntity = compileEntityList.get(0);
                    if (CompileOptEnum.SAVE_LIST.equals(compileAspect.type()) && compileAspect.delFlag()) {
                        this.addConfirmAndUpdateFinishNum(compileEntity);
                        commonMapper.deleteByVersion(tableName, compileEntity.getVersion());
                    }
                    if (compileEntityList.size() == 1 && PmConstant.MINUS_ONE.equals(compileEntity.getSubmitFlag()) && compileAspect.delFlag()) {
                        args[i] = Collections.emptyList();
                    }
                }
            }


            sb.append(i == args.length - 1 ? arg.toString() : arg.toString() + ", ");
        }
        log.info("{}开始执行了, 参数是{}, 当前线程是{}",
                joinPoint.getTarget().getClass().getName() + "." + methodName,
                sb,
                Thread.currentThread().getId());
    }

    /**
     * 添加确认记录 只有点击确认的时候需要添加确认记录
     *
     * @param compileEntity
     */
    private void addConfirmAndUpdateFinishNum(CompileEntity compileEntity) {
        String reqId = compileEntity.getReqId();
        if (!redisUtils.hasKey(reqId) && "1".equals(compileEntity.getSubmitFlag())) {
            String menuId = compileEntity.getMenuId();
            if(StringUtils.isBlank(menuId)){
                menuId = compileEntity.getModuleIdentity();
            }
            moduleConfirmCaseService.addConfirmRecord(menuId, compileEntity.getStageIdentity());
            redisUtils.setEx(reqId, reqId, 60000);
        }
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
        this.beforeProceed(joinPoint, compileAspect);
        Object result = joinPoint.proceed(args);
        log.info("方法响应结果为{}", result);
        Object o = this.afterProcessd(joinPoint, compileAspect, result);
        return o;

    }

    private Object afterProcessd(ProceedingJoinPoint joinPoint, CompileAspect compileAspect, Object result) {
        BigDecimal version = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof CompileEntity) {
                CompileEntity a = (CompileEntity) arg;
                version = a.getVersion();
            }
        }


        String tableName = compileAspect.tableName();
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
            res.setVersion(VersionUtil.getVersion(tableName, version));
            return res;
        }
        return result;
    }

}
