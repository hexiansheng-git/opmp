package com.hhwy.utils.validation;

import com.hhwy.utils.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 子表校验
 *
 * @author hwj
 */
@Slf4j
public class JyDetailsUtil {
    /**
     * 单层子表校验
     * @param detailList 子表对象list
     * @param groups class对象
     */
    public static <T> Boolean jyDetails(List<T> detailList,Class<?>... groups) {
        StringBuffer str = new StringBuffer("");
        for (T t : detailList) {
            BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(t, groups);
            if (!beanValidationResult.isSuccess()) {
                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                    str = str.append(errorMessage.getMessage() + ",");
                }
            }
        }
        if (!"".equals(str.toString())) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
        return true;
    }

    /**
     * 双层子表校验
     * @param detailList 子表对象list
     * @param key 第二层子表集合get方法
     * @param groups class对象
     */
    public static <T,E> Boolean jyDetailsDetails(List<T> detailList,String key, Class<?>... groups){
        StringBuffer str = new StringBuffer("");
        StringBuffer strDetails = new StringBuffer("");
        for (T t : detailList) {
            BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(t, groups);
            if (!beanValidationResult.isSuccess()) {
                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                    str = str.append(errorMessage.getMessage() + ",");
                }
            }

            // 一般而言每个属性都有其get和set方法
            // 通过方法名获取get方法
            Method getMethod = null;
            try {
                getMethod = t.getClass().getMethod(key);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            // 调用get方法
            List<E> detailsDetailsList = null;
            try {
                detailsDetailsList = (List<E>) getMethod.invoke(t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if (detailsDetailsList != null) {
                for (E e : detailsDetailsList) {
                    BeanValidationResult beanValidationResultDetails = ValidationUtil.warpValidate(e, groups);
                    if (!beanValidationResultDetails.isSuccess()) {
                        List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResultDetails.getErrorMessages();
                        for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                            strDetails = strDetails.append(errorMessage.getMessage() + ",");
                        }
                    }
                }
            }
        }

        if (!"".equals(str.toString())) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
        }
        if (!"".equals(strDetails.toString())) {
            log.error(str.toString());
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, strDetails.toString());
        }
        return true;
    }


}
