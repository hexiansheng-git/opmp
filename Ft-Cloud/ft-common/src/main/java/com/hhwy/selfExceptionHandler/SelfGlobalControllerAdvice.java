//package com.hhwy.selfExceptionHandler;
//
//import com.hhwy.common.core.exception.BaseException;
//import com.hhwy.common.core.exception.CustomException;
//import com.hhwy.common.core.exception.DemoModeException;
//import com.hhwy.common.core.exception.PreAuthorizeException;
//import com.hhwy.common.core.utils.StringUtils;
//import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.utils.exception.CustomBusinessException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.validation.BindException;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.validation.ConstraintViolationException;
//
//
//@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class SelfGlobalControllerAdvice {
//
//    private static final Logger log = LoggerFactory.getLogger(SelfGlobalControllerAdvice.class);
//
//    public SelfGlobalControllerAdvice() {
//    }
//
//    @ExceptionHandler({BaseException.class})
//    public AjaxResult baseException(BaseException e) {
//        return AjaxResult.error(e.getDefaultMessage());
//    }
//
//    @ExceptionHandler({CustomException.class})
//    public AjaxResult businessException(CustomException e) {
//        return StringUtils.isNull(e.getCode()) ? AjaxResult.error(e.getMessage()) : AjaxResult.error(e.getCode(), e.getMessage());
//    }
//
//
//    @ExceptionHandler({NullPointerException.class})
//    public AjaxResult handleException(NullPointerException e) {
//        log.error(e.getMessage(), e);
//        return AjaxResult.error("程序异常");
//    }
//
//    @ExceptionHandler({ConstraintViolationException.class})
//    public AjaxResult ConstraintViolationException(Exception e) {
//        String message = e.getMessage();
//        log.error(message, e);
//        if (StringUtils.isEmpty(message)) {
//            message = e.toString();
//        }
//        return new AjaxResult(301, message);
//    }
//
//    @ExceptionHandler({Exception.class})
//    public AjaxResult handleException(Exception e) {
//        String message = e.getMessage();
//        log.error(message, e);
//        if (StringUtils.isEmpty(message)) {
//            message = e.toString();
//        }
//        return AjaxResult.error(message);
//    }
//
//
//    @ExceptionHandler({BindException.class})
//    public AjaxResult validatedBindException(BindException e) {
//        log.error(e.getMessage(), e);
//        String message = ((ObjectError)e.getAllErrors().get(0)).getDefaultMessage();
//        return AjaxResult.error(message);
//    }
//
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public Object validExceptionHandler(MethodArgumentNotValidException e) {
//        String message = e.getBindingResult().getFieldError().getDefaultMessage();
//        return new AjaxResult(301, message);
//    }
//
//    @ExceptionHandler({PreAuthorizeException.class})
//    public AjaxResult preAuthorizeException(PreAuthorizeException e) {
//        String message = e.getMessage();
//        return StringUtils.isEmpty(message) ? AjaxResult.error("没有权限，请联系管理员授权") : AjaxResult.error(e.getMessage());
//    }
//
//    @ExceptionHandler({DemoModeException.class})
//    public AjaxResult demoModeException(DemoModeException e) {
//        return AjaxResult.error("演示模式，不允许操作");
//    }
//
//    @ExceptionHandler({CustomBusinessException.class})
//    public AjaxResult handleException(CustomBusinessException e) {
//        return AjaxResult.error(e.getMsg());
//    }
//}
